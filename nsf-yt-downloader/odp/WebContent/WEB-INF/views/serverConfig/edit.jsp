<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<t:layout>
	<h1><c:out value="${translation.serverConfig}"/></h1>
	
	<form method="POST" action="${mvc.basePath}/serverConfigs">
		<dl>
			<dt><c:out value="${translation.serverName}"/></dt>
			<dd><input name="serverName" value="${fn:escapeXml(serverConfig.serverName)}" /></dd>
			
			<dt><c:out value="${translation.ytDlpPath}"/></dt>
			<dd><input name="ytDlpPath" value="${fn:escapeXml(serverConfig.ytDlpPath)}" /></dd>
			
			<dt><c:out value="${translation.downloadPath}"/></dt>
			<dd><input name="downloadPath" value="${fn:escapeXml(serverConfig.downloadPath)}" /></dd>
		</dl>
		
		<input type="hidden" name="documentId" value="${fn:escapeXml(serverConfig.documentId)}" />
		<input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
		<input type="submit" value="${translation.save}" />
	</form>
</t:layout>