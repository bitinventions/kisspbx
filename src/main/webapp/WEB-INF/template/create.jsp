<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:create>
	<jsp:attribute name="title">Create Template</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/template/"/></jsp:attribute>
    <jsp:body>
    	<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>
		<jsp:include page="_form.jsp"/>
	</jsp:body>
</t:create>