<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<t:layout>
	<h1><c:out value="${translation.downloads}"/></h1>
	
	<fieldset>
		<form method="POST" action="${mvc.basePath}/downloads">
			<p><input type="text" name="url" /></p>
		
			<input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
			<input type="submit" value="${translation.startDownload}" />
		</form>
	</fieldset>
	
	<table>
		<thead>
			<tr>
				<th><c:out value="${translation.url}"/></th>
				<th><c:out value="${translation.output}"/></th>
				<th><c:out value="${translation.process}"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${downloads}" var="download">
			<tr>
				<td><c:out value="${download.url}"/></td>
				<td style="white-space: pre-wrap"><c:out value="${download.output}"/></td>
				<td><c:out value="${download.process}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</t:layout>