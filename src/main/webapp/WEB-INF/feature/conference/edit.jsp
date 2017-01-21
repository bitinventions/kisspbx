<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit conference ${conference.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/conference/show?id=${conference.id}"/></jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${conference.id}" />
		<input type="hidden" name="version" value="${conference.version}" />
		<jsp:include page="_form.jsp"/>
    </jsp:body>
</t:edit>