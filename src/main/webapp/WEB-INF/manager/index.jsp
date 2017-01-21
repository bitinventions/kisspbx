<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Manager users</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create"><i class="fa fa-plus"></i></a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Name</th>
  					<th>Read</th>
  					<th>Write</th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${users == null || users.size() == 0}">
						<tr>
							<td colspan="4">No hay resultados</td>
						</tr>
					</c:when>
					<c:when test="${users.size() > 0}">
			        	<c:forEach items="${users}" var="u">
					    <tr>      
					        <td><a href="<c:url value="/manager/edit?id=${u.id}"/>">${u.name}</a></td>
					        <td>${u.readPermission}</td>
					        <td>${u.writePermission}</td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/manager/delete?id=${u.id}"/>"><i class="fa fa-trash"></i></a></td>
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