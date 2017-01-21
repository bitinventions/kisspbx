<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Conference list</jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Name</th>
  					<th>Description</th>
  					<th width="40"></th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${conferences == null || conferences.size() == 0}">
						<tr>
							<td colspan="4">No conferences defined</td>
						</tr>
					</c:when>
					<c:when test="${conferences.size() > 0}">
			        	<c:forEach items="${conferences}" var="c">
					    <tr>      
					        <td><a href="<c:url value="/conference/show?id=${c.id}"/>">${c.name}</a></td>
					        <td>${c.description}</td>
					        <td><a href="<c:url value="/conference/edit?id=${c.id}"/>"><i class="fa fa-edit">&nbsp;</i></a></td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/conference/delete?id=${c.id}"/>"><i class="fa fa-trash">&nbsp;</i></a></td>
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