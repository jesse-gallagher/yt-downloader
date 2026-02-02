<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<t:layout>
	<h1><c:out value="${translation.serverConfigs}"/></h1>
	
	<p><a href="${mvc.basePath}/serverConfigs/@new"><c:out value="${translation.createServerConfig}"/></a></p>
	
	<table>
		<thead>
			<tr>
				<th><c:out value="${translation.serverName}"/></th>
				<th><c:out value="${translation.downloadPath}"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${serverConfigs}" var="serverConfig">
			<tr>
				<td><a href="${mvc.basePath}/serverConfigs/${serverConfig.documentId}"><c:out value="${encoder.abbreviateName(serverConfig.serverName)}"/></a></td>
				<td><c:out value="${serverConfig.downloadPath}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</t:layout>