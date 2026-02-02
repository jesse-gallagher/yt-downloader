package controller;

import java.io.IOException;
import java.util.Base64;

import bean.DownloaderBean;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import lotus.domino.NotesException;
import lotus.domino.Session;
import model.ServerConfig;

@Controller
@Path("downloads")
public class DownloadController {
	@Inject
	private DownloaderBean downloaderBean;
	
	@Inject
	private Models models;

	@Inject
	private ServerConfig.Repository serverConfigs;
	
	@Inject @Named("dominoSession")
	private Session session;
	
	@GET
	@View("downloads/list.jsp")
	public void list() {
		models.put("downloads", downloaderBean.getDownloads());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String startDownload(@FormParam("url") String url) throws NotesException, IOException, InterruptedException {
		String serverName = session.getServerName();
		var config = serverConfigs.findByServerName(serverName)
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("No config found for " + serverName));
		
		downloaderBean.runDownload(url, config.getYtDlpPath(), config.getDownloadPath());
		return "redirect:downloads";
	}
	
	@Path("{downloadId}")
	@GET
	@View("downloads/show.jsp")
	public void showDownload(@PathParam("downloadId") String downloadId) {
		var url = new String(Base64.getUrlDecoder().decode(downloadId));
		var download = downloaderBean.getDownloads().stream()
			.filter(d -> url.equals(d.getUrl()))
			.findFirst()
			.orElseThrow(() -> new NotFoundException("Could not find download for provided ID"));
		
		models.put("download", download);
	}
}
