package bean;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.commons.util.StringUtil;

import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.ServerConfig;

@ApplicationScoped
public class DownloaderBean {
	private static final Pattern COMPLETE_PATTERN = Pattern.compile("^\\[download\\]\\s+(\\d+\\.\\d+)%.*$");
	
	public class Download {
		private String id;
		private String url;
		private StringBuilder output;
		private Process process;
		private List<String> command;
		
		public Download(String url, Process process, List<String> command) {
			this.id = UUID.randomUUID().toString();
			this.url = url;
			this.process = process;
			this.command = command;
		}
		
		public String getId() {
			return id;
		}
		
		public String getUrl() {
			return url;
		}
		
		public StringBuilder getOutput() {
			return output;
		}
		public void setOutput(StringBuilder output) {
			this.output = output;
		}
		
		public Process getProcess() {
			return process;
		}
		
		public List<String> getCommand() {
			return command;
		}
		
		public String getLastLine() {
			String out = this.output.toString();
			if(out.length() < 2) {
				return "";
			}
			int lastpos = out.lastIndexOf('\n', out.length()-2);
			return out.substring(lastpos).trim();
		}
		
		public int getPercentComplete() {
			String out = this.output.toString();
			if(out.length() < 2) {
				return -1;
			}
			return out.lines()
				.parallel()
				.map(COMPLETE_PATTERN::matcher)
				.filter(Matcher::matches)
				.map(m -> m.group(1))
				.mapToDouble(Double::parseDouble)
				.mapToInt(d -> (int)d)
				.max()
				.orElse(0);
			
		}
	}
	
	@Inject @Named("java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService exec;
	
	private List<Download> downloads = new ArrayList<>();
	
	public List<Download> getDownloads() {
		return downloads;
	}
	
	@Asynchronous
	public CompletableFuture<?> runDownload(String url, ServerConfig serverConfig) throws IOException, InterruptedException {
		try {
			File downloadDir = new File(serverConfig.getDownloadPath());
			if(!downloadDir.exists()) {
				Files.createDirectories(downloadDir.toPath());
			}
			String ytDlpPath = serverConfig.getYtDlpPath();
			List<String> command = new ArrayList<>();
			if(StringUtil.isEmpty(ytDlpPath)) {
				command.add("yt-dlp");
			} else {
				command.add(ytDlpPath);
			}
			command.add("-t");
			command.add("mp4");
			
			var firefox = serverConfig.getFirefoxProfilePath();
			if(StringUtil.isNotEmpty(firefox)) {
				command.add("--cookies-from-browser");
				command.add("firefox:" + firefox);
			}
			
			command.add(url);
			Process proc = new ProcessBuilder(command)
				.directory(downloadDir)
				.redirectErrorStream(true)
				.redirectOutput(Redirect.PIPE)
				.start();
	
			var download = new Download(url, proc, command);
			this.downloads.add(download);
			
			var reader = proc.inputReader();
			var out = new StringBuilder();
			download.setOutput(out);
			exec.submit(() -> {
				String line;
				try {
					while((line = reader.readLine()) != null) {
						out.append(line);
						out.append('\n');
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			proc.waitFor();
			
			return Asynchronous.Result.complete(proc);
		} catch(Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
}
