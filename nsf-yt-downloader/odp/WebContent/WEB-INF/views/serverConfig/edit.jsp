<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<t:layout>
	<h1><c:out value="${translation.serverConfig}"/></h1>
	
	<form method="POST" action="${mvc.basePath}/serverConfigs">
		<div class="field">
			<label class="label" for="serverName"><c:out value="${translation.serverName}"/></label>
			<div class="control">
				<input type="text" id="serverName" name="serverName" value="${fn:escapeXml(serverConfig.serverName)}" />
			</div>
		</div>
		<div class="field">
			<label class="label" for="ytDlpPath"><c:out value="${translation.ytDlpPath}"/></label>
			<div class="control">
				<input type="text" id="ytDlpPath" name="ytDlpPath" value="${fn:escapeXml(serverConfig.ytDlpPath)}" />
			</div>
		</div>
		<div class="field">
			<label class="label" for="downloadPath"><c:out value="${translation.downloadPath}"/></label>
			<div class="control">
				<input type="text" id="downloadPath" name="downloadPath" value="${fn:escapeXml(serverConfig.downloadPath)}" />
			</div>
		</div>
		<div class="field">
			<label class="label" for="firefoxProfilePath"><c:out value="${translation.firefoxProfilePath}"/></label>
			<div class="control">
				<input type="text" id="firefoxProfilePath" name="firefoxProfilePath" value="${fn:escapeXml(serverConfig.firefoxProfilePath)}" />
			</div>
		</div>
		
		<input type="hidden" name="documentId" value="${fn:escapeXml(serverConfig.documentId)}" />
		<input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
		<input type="submit" value="${translation.save}" />
	</form>
</t:layout>