<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit: ${user.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/manager/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/manager/delete?id=${user.id}"/></jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${user.id}" />
		<input type="hidden" name="version" value="${user.version}" />
		<jsp:include page="_form.jsp"/>
    </jsp:body>
</t:edit>