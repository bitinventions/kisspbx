<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:create>
	<jsp:attribute name="title">Create Inbound route</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/inbound/"/></jsp:attribute>
    <jsp:body>
		<jsp:include page="_form.jsp" />
		<script>
		<!--
			function refreshForm() {
				$('#form').attr('action', 'create'); 
				$('#form').submit();
			}
		-->
		</script>
 	</jsp:body>
</t:create>