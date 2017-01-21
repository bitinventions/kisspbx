<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Templates</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create"><i class="fa fa-plus"></i></a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th width="15%">Type</th>
					<th>Name</th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${templates == null || templates.size() == 0}">
						<tr>
							<td colspan="3">No hay resultados</td>
						</tr>
					</c:when>
					<c:when test="${templates.size() > 0}">
			        	<c:forEach items="${templates}" var="t">
					    <tr>      
					        <td>${t.type}</td>
					        <td><a href="<c:url value="/template/edit?id=${t.id}"/>">${t.name}</a></td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/template/delete?id=${t.id}"/>"><i class="fa fa-trash">&nbsp;</i></a></td>
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