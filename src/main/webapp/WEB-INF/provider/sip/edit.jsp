<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:edit>
    <jsp:attribute name="title">Edit SIP provider: ${provider.name}</jsp:attribute>
    <jsp:attribute name="cancelurl"><c:url value="/provider/sip/"/></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/provider/sip/delete?id=${provider.id}"/></jsp:attribute>
    <jsp:attribute name="nopadding">true</jsp:attribute>
    <jsp:body>
		<input type="hidden" name="id" value="${provider.id}" />
		<input type="hidden" name="version" value="${provider.version}" />
		<jsp:include page="_form.jsp" />
    </jsp:body>
</t:edit>