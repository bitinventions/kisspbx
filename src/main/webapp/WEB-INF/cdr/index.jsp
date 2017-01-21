<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:main>
    <jsp:attribute name="title">CDR table</jsp:attribute>
    <jsp:body>
	    <div class="row">
    		<div class="col-xs-12">
    			<div class="box box-solid">
    				<div class="box-body">
				    	<table class="table table-bordered">
				 			<tbody>
				  				<tr>
				  					<th>Call date</th>
				  					<th>Id</th>
				  					<th>Src</th>
				  					<th>Channel</th>
				  					<th>Dst</th>
				  					<th>Dst Channel</th>
				  					<th>Dur</th>
				  					<th>Context</th>
				  					<th>App</th>
				  					<th>Data</th>
								</tr>
								<c:choose>
									<c:when test="${cdrs == null || cdrs.size() == 0}">
										<tr>
											<td colspan="10">No CDR register found</td>
										</tr>
									</c:when>
									<c:when test="${cdrs.size() > 0}">
							        	<c:forEach items="${cdrs}" var="cdr">
							        	<tr>      
									    	<td>${cdr.calldate}</td>
									    	<td>${cdr.uniqueid}</td>
									    	<td>${cdr.src}</td>
									    	<td>${cdr.channel}</td>
									    	<td>${cdr.dst}</td>
									    	<td>${cdr.dstchannel}</td>
									    	<td>${cdr.duration}</td>
									    	<td>${cdr.dcontext}</td>
									    	<td>${cdr.lastapp}</td>
									    	<td>${cdr.lastdata}</td>
									    </tr>
										</c:forEach>
										<tr>
											<td colspan="10"><u:paginate clazz="pagination pagination-sm no-margin pull-right" total="${total}" size="${max}" page="${page}"/></td>
										</tr>
									</c:when>
								</c:choose>
				 			</tbody>
				 		</table>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>
</t:main>