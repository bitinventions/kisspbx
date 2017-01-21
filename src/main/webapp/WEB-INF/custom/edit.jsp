<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit: ${app.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/custom/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/custom/delete?id=${app.id}"/></jsp:attribute>
    <jsp:body>
    	<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>
		<input type="hidden" name="id" value="${app.id}" />
		<input type="hidden" name="version" value="${app.version}" />
		<jsp:include page="_form.jsp" />
    </jsp:body>
</t:edit>