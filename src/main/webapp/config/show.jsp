<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:main>
    <jsp:attribute name="title">
      Current configuration
    </jsp:attribute>
    <jsp:body>
        <div class="row">
    		<div class="col-md-8">
    			<div class="box box-primary">
    				<div class="box-header with-border">
    					<h3 class="box-title">Parameters</h3>
    				</div>
    				<div class="box-body">
	    				<table class="table table-bordered">
				        	<c:forEach items="${params}" var="p">
						    <tr>      
						        <td>${p.key}</td>
						        <td>${p.value}</td>
						    </tr>
							</c:forEach>
        				</table>
        			</div>
        		</div>
        	</div>
        </div>
    </jsp:body>
</t:main>