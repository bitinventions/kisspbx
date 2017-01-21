<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:list>
    <jsp:attribute name="title">Extensions</jsp:attribute>
    <jsp:attribute name="buttons">
    	<a class="btn btn-default" href="create?type=SIP"><i class="fa fa-plus"></i> SIP</a>
    	<a class="btn btn-default" href="create?type=IAX2"><i class="fa fa-plus"></i> IAX2</a>
    </jsp:attribute>
    <jsp:body>
    	<table class="table table-bordered">
 			<tbody>
  				<tr>
  					<th>Extension</th>
  					<th>CallerId</th>
					<th>Template</th>
					<th width="40"></th>
				</tr>
				<c:choose>
					<c:when test="${extensions == null || extensions.size() == 0}">
						<tr>
							<td colspan="4">No extensions</td>
						</tr>
					</c:when>
					<c:when test="${extensions.size() > 0}">
			        	<c:forEach items="${extensions}" var="e">
					    <tr>      
					        <td><a href="<c:url value="/extension/edit?id=${e.id}"/>">${e.extension}</a></td>
					        <td>${e.callerid}</td>
					        <td><a href="<c:url value="/template/edit?id=${e.template.id}"/>">${e.template.name}</a></td>
					        <td><a onclick="return confirm('Are you sure?');" href="<c:url value="/extension/delete?id=${e.id}"/>"><i class="fa fa-trash">&nbsp;</i></a></td>
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