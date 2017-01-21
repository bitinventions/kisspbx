<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit: ${queue.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/feature/queue/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/feature/queue/delete?id=${queue.id}"/></jsp:attribute>
    <jsp:attribute name="nopadding">true</jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${queue.id}" />
		<input type="hidden" name="version" value="${queue.version}" />
		<jsp:include page="_form.jsp"/>
    </jsp:body>
</t:edit>