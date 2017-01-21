<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Outbound routes</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create"><i class="fa fa-plus"></i></a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Name</th>
  					<th>Dial pattern</th>
  					<th>Provider</th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${routes == null || routes.size() == 0}">
						<tr>
							<td colspan="4">No outbound routes found</td>
						</tr>
					</c:when>
					<c:when test="${routes.size() > 0}">
			        	<c:forEach items="${routes}" var="r">
					    <tr>      
					        <td><a href="<c:url value="/outbound/edit?id=${r.id}"/>">${r.name}</a></td>
					        <td>${r.dialpattern}</td>
					        <td><a href="<c:url value="/provider/edit?id=${r.provider.id}"/>">${r.provider.name}</a></td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/outbound/delete?id=${r.id}"/>"><i class="fa fa-trash"></i></a></td>
					    </tr>
						</c:forEach>
						<tr>
							<td colspan="4"><u:paginate clazz="pagination pagination-sm no-margin pull-right" total="${total}" size="${max}" page="${page}"/></td>
						</tr>
					</c:when>
				</c:choose>
 			</tbody>
 		</table>
    </jsp:body>
</t:list>