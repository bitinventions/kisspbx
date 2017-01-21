<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit inbound route ${route.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/inbound/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/inbound/delete?id=${route.id}"/></jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${route.id}" />
		<input type="hidden" name="version" value="${route.version}" />
		<jsp:include page="_form.jsp"/>
		<script>
		<!--
			function refreshForm() {
				$('#form').attr('action', 'edit'); 
				$('#form').submit();
			}
		-->
		</script>
    </jsp:body>
</t:edit>