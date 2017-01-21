<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">${extension.type} extension: ${extension.extension}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/extension/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/extension/delete?id=${extension.id}"/></jsp:attribute>
   	<jsp:attribute name="nopadding">true</jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${extension.id}" />
		<input type="hidden" name="version" value="${extension.version}" />
		<jsp:include page="_form.jsp" />
    </jsp:body>
</t:edit>