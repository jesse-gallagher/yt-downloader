package bean;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ibm.commons.util.StringUtil;

import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lotus.domino.NotesException;

@ApplicationScoped
public class DownloaderBean {
	public class Download {
		private String url;
		private StringBuilder output;
		private Process process;
		
		public Download(String url, Process process) {
			this.url = url;
			this.process = process;
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
	}
	
	@Inject @Named("java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService exec;
	
	private List<Download> downloads = new ArrayList<>();
	
	public List<Download> getDownloads() {
		return downloads;
	}
	
	@Asynchronous
	public CompletableFuture<?> runDownload(String url, String ytDlpPath, String downloadPath) throws NotesException, IOException, InterruptedException {
		try {
			File downloadDir = new File(downloadPath);
			if(!downloadDir.exists()) {
				Files.createDirectories(downloadDir.toPath());
			}
			String exeName = StringUtil.isEmpty(ytDlpPath) ? "yt-dlp" : ytDlpPath;
			Process proc = new ProcessBuilder(exeName, url)
				.directory(downloadDir)
				.redirectErrorStream(true)
				.redirectOutput(Redirect.PIPE)
				.start();
	
			var download = new Download(url, proc);
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
