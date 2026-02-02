<%@tag description="Overall Page template" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="${translation._lang}">
	<head>
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
		
		<base href="${pageContext.request.contextPath}/" />
		
 		<link rel="shortcut icon" href="${pageContext.request.contextPath}/$Icon" />
 		<link rel="apple-touch-icon" sizes="32x32" href="${pageContext.request.contextPath}/$Icon" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bulma.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
		
		<title><c:out value="${translation.appTitle}"/></title>
	</head>
	<body>
		<nav id="main-nav" class="has-background-primary-10 has-text-primary-10-invert">
			<input id="navbar-toggle" class="mobile-nav" type="checkbox" aria-hidden="true" />
			
			<h1><a href="${mvc.basePath}"><c:out value="${translation.appTitle}"/></a></h1>
			
			<ul class="links">
				<li><a href="${mvc.basePath}/"><c:out value="${translation.home}"/></a></li>
				<li><a href="${mvc.basePath}/serverConfigs"><c:out value="${translation.serverConfigs}"/></a></li>
				<li><a href="${mvc.basePath}/downloads"><c:out value="${translation.downloads}"/></a></li>
			</ul>
			
			<hr />
			
			<p><c:out value="${encoder.abbreviateName(userBean.id)}"/></p>
			<a href="/names.nsf?Logout&RedirectTo=${encoder.urlEncode(mvc.basePath)}">
				<c:out value="${translation.logoutLink}"/>
			</a>
		</nav>
		<section id="main-body" class="content">
			<c:catch var="bodyException">
			<jsp:doBody />
			</c:catch>
			<c:if test="${bodyException != null}">
			<p><c:out value="${bodyException.message}"/></p>
			</c:if>
		</section>
	</body>
</html>