package bean;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.ibm.commons.util.StringUtil;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lotus.domino.NotesException;
import lotus.domino.Session;

@RequestScoped
@Named("encoder")
public class EncoderBean {
	@Inject @Named("dominoSession")
	private Session session;
	
	public String b64(String value) {
		return Base64.getUrlEncoder().encodeToString(value.getBytes());
	}
	
	public String urlEncode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}
	
	public String abbreviateName(String name) throws NotesException {
		if(StringUtil.isEmpty(name)) {
			return name;
		}
		var nameObj = session.createName(name);
		try {
			return nameObj.getAbbreviated();
		} finally {
			nameObj.recycle();
		}
	}
}
