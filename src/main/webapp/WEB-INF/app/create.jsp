<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:create>
	<jsp:attribute name="title">Create ${type} application</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/app/"/></jsp:attribute>
    <jsp:body>
    	<input type="hidden" name="type" value="${type}" />
		<c:choose>
    		<c:when test="${type.equals('queue')}">
    			<jsp:include page="_queue.jsp" />
    		</c:when>
    		<c:when test="${type.equals('huntgroup')}">
    			<jsp:include page="_huntgroup.jsp" />
    		</c:when>
    		<c:otherwise>
    			<jsp:include page="_custom.jsp" />
    		</c:otherwise>
    	</c:choose>
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