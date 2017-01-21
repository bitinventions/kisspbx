<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">SIP providers</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create"><i class="fa fa-plus"></i></a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Name</th>
  					<th>DID</th>
  					<th>Host</th>
  					<th>Username</th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${providers == null || providers.size() == 0}">
						<tr>
							<td colspan="5">No providers defined</td>
						</tr>
					</c:when>
					<c:when test="${providers.size() > 0}">
			        	<c:forEach items="${providers}" var="p">
					    <tr>      
					        <td><a href="<c:url value="/provider/sip/edit?id=${p.id}"/>">${p.name}</a></td>
					        <td>${p.did}</td>
					        <td>${p.host}</td>
					        <td>${p.username}</td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/provider/sip/delete?id=${p.id}"/>"><i class="fa fa-trash">&nbsp;</i></a></td>
					    </tr>
						</c:forEach>
						<tr>
							<td colspan="6"><u:paginate clazz="pagination pagination-sm no-margin pull-right" total="${total}" size="${max}" page="${page}"/></td>
						</tr>
					</c:when>
				</c:choose>
 			</tbody>
 		</table>
    </jsp:body>
</t:list>