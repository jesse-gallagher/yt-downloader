package model;

import java.util.stream.Stream;

import jakarta.data.repository.CrudRepository;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

@Entity
public class ServerConfig {
	public interface Repository extends CrudRepository<ServerConfig, String> {
		Stream<ServerConfig> findByServerName(String serverName);
	}
	
	@Id private String documentId;
	@Column private String serverName;
	@Column private String ytDlpPath;
	@Column private String downloadPath;
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public String getYtDlpPath() {
		return ytDlpPath;
	}
	public void setYtDlpPath(String ytDlpPath) {
		this.ytDlpPath = ytDlpPath;
	}
	
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
}
