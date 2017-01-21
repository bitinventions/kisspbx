<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Applications</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create?type=queue"><i class="fa fa-list"></i> Queue</a>
    	<a class="btn btn-default" href="create?type=huntgroup"><i class="fa fa-group"></i> Huntgroup</a>
    	<a class="btn btn-default" href="create?type=custom"><i class="fa fa-random"></i> Custom</a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Code</th>
  					<th>Description</th>
  					<th>Application</th>
  					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${apps == null || apps.size() == 0}">
						<tr>
							<td colspan="4">No custom apps found</td>
						</tr>
					</c:when>
					<c:when test="${apps.size() > 0}">
			        	<c:forEach items="${apps}" var="a">
					    <tr>      
					        <td>${a.pattern}</td>
					        <td><a href="<c:url value="/app/edit?id=${a.id}"/>">${a.description}</a></td>
					        <td>${a.application}</td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/app/delete?id=${a.id}"/>"><i class="fa fa-trash">&nbsp;</i></a></td>
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