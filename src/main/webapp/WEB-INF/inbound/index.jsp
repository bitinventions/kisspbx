<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Inbound routes</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create"><i class="fa fa-plus"></i></a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Name</th>
  					<th>DDI</th>
  					<th>Caller</th>
  					<th>Destination</th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${routes == null || routes.size() == 0}">
						<tr>
							<td colspan="5">No inbound routes found</td>
						</tr>
					</c:when>
					<c:when test="${routes.size() > 0}">
			        	<c:forEach items="${routes}" var="r">
					    <tr>      
					        <td><a href="<c:url value="/inbound/edit?id=${r.id}"/>">${r.name}</a></td>
					        <td>${r.ddi!=null?r.ddi:"any"}</td>
					        <td>${r.cli!=null?r.cli:"any"}</td>
					        <c:choose>
					        <c:when test="${destinations.containsKey(r.id)}">
					        	<td>${destinations.get(r.id).getDestinationName()}</td>
					        </c:when>
					        <c:otherwise>
					        	<td><p class="text-red">${r.destination} (${r.destinationType.toString()})</p></td>
					        </c:otherwise>
					        </c:choose>
					        <td><a onclick="return confirm('You are about to delete route ${r.name}, are you sure?');" href="<c:url value="/inbound/delete?id=${r.id}"/>"><i class="fa fa-trash"></i></a></td>
					    </tr>
						</c:forEach>
						<tr>
							<td colspan="5"><u:paginate clazz="pagination pagination-sm no-margin pull-right" total="${total}" size="${max}" page="${page}"/></td>
						</tr>
					</c:when>
				</c:choose>
 			</tbody>
 		</table>
    </jsp:body>
</t:list>