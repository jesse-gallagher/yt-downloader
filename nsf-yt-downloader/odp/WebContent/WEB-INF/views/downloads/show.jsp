<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<t:layout>
	<h1><c:out value="${translation.download}"/></h1>
	
	<dl>
		<dt><c:out value="${translation.url}"/></dt>
		<dd><c:out value="${download.url}"/></dd>
		
		<dt><c:out value="${translation.process}"/></dt>
		<dd><c:out value="${download.process}"/></dd>
		
		<dt><c:out value="${translation.command}"/></dt>
		<dd><c:out value="${download.command}"/></dd>
		
		<dt><c:out value="${translation.output}"/></dt>
		<dd style="white-space: pre-wrap"><c:out value="${download.output}"/></dd>
	</dl>
</t:layout>