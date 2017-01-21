<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit: ${huntgroup.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/feature/huntgroup/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/feature/huntgroup/delete?id=${huntgroup.id}"/></jsp:attribute>
    <jsp:attribute name="nopadding">true</jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${huntgroup.id}" />
		<input type="hidden" name="version" value="${huntgroup.version}" />
		<jsp:include page="_form.jsp"/>
    </jsp:body>
</t:edit>