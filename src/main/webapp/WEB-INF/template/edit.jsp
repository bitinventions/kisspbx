<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit: ${template.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/template/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/template/delete?id=${template.id}"/></jsp:attribute>
    <jsp:body>
    	<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>
		
		<input type="hidden" name="id" value="${template.id}" />
		<input type="hidden" name="version" value="${template.version}" />
		<jsp:include page="_form.jsp"/>
    </jsp:body>
</t:edit>