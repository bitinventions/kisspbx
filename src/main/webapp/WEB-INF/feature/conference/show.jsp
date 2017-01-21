<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:show>
    <jsp:attribute name="title">Show conference ${conference.name}</jsp:attribute>
    <jsp:attribute name="editurl"><c:url value="/conference/edit?id=${conference.id}" /></jsp:attribute>
    <jsp:attribute name="deleteurl"><c:url value="/conference/delete?id=${conference.id}" /></jsp:attribute>
    <jsp:body>
 		<table class="table">
 			<tr>
				<td width="150" align="right"><label>Extension</label></td>
				<td align="left">${conference.name}</td>
			</tr>
			<tr>
				<td width="150" align="right"><label>Description</label></td>
				<td align="left">${conference.description}</td>
			</tr>
		</table>
	</jsp:body>
</t:show>