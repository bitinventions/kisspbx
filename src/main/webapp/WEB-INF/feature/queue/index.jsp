<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Queues</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create"><i class="fa fa-plus"></i></a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Name</th>
  					<th>Description</th>
  					<th>Strategy</th>
  					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${queues == null || queues.size() == 0}">
						<tr>
							<td colspan="4">No queues defined</td>
						</tr>
					</c:when>
					<c:when test="${queues.size() > 0}">
			        	<c:forEach items="${queues}" var="q">
					    <tr>      
					        <td><a href="<c:url value="/feature/queue/edit?id=${q.id}"/>">${q.name}</a></td>
					        <td>${q.description}</td>
					        <td>${q.strategy.toString().toLowerCase()}</td>
					        <td><a onclick="return confirm('You are about to delete queue ${q.name}, are you sure?');" href="<c:url value="/feature/queue/delete?id=${q.id}"/>"><i class="fa fa-trash"></i></a></td>
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