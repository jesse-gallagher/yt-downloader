package controller;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ibm.commons.util.StringUtil;

import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
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
import model.ServerConfig;

@Controller
@Path("serverConfigs")
public class ServerConfigController {
	@Inject
	private ServerConfig.Repository configRepository;
	
	@Inject
	private Models models;
	
	@Inject @ConfigProperty(name="ServerKeyFileName_Owner")
	private String serverName;
	
	@GET
	@View("serverConfig/list.jsp")
	public void list() {
		models.put("serverConfigs", configRepository.findAll(PageRequest.ofSize(Integer.MAX_VALUE), Order.by(Sort.asc("serverName"))).content());
	}
	
	@Path("@new")
	@GET
	@View("serverConfig/edit.jsp")
	public void composeConfig() {
		var config = new ServerConfig();
		config.setServerName(serverName);
		
		models.put("serverConfig", config);
	}
	
	@Path("{documentId}")
	@GET
	@View("serverConfig/edit.jsp")
	public void editConfig(@PathParam("documentId") String documentId) {
		var config = configRepository.findById(documentId).orElseThrow(() -> new NotFoundException("Unable to find server config for ID " + documentId));
		models.put("serverConfig", config);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveConfig(
			@FormParam("documentId") String documentId,
			@FormParam("serverName") String serverName,
			@FormParam("ytDlpPath") String ytDlpPath,
			@FormParam("downloadPath") String downloadPath,
			@FormParam("firefoxProfilePath") String firefoxProfilePath
	) {
		ServerConfig config;
		if(StringUtil.isEmpty(documentId)) {
			config = new ServerConfig();
		} else {
			config = configRepository.findById(documentId).orElseGet(ServerConfig::new);
		}
		
		config.setServerName(serverName);
		config.setYtDlpPath(ytDlpPath);
		config.setDownloadPath(downloadPath);
		config.setFirefoxProfilePath(firefoxProfilePath);
		config = configRepository.save(config);
		
		return "redirect:serverConfigs";
	}
}
