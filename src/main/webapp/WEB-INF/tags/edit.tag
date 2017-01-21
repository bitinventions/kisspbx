<%@tag description="Create layout template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="cancelurl" fragment="true"%>
<%@attribute name="deleteurl" fragment="true"%>
<%@attribute name="nopadding" required="false" %>

<t:main>
    <jsp:attribute name="title"><jsp:invoke fragment="title"/></jsp:attribute>
    <jsp:body>
    	<div class="row">
    		<div class="col-xs-12">
    			<div class="box box-solid">
    				<form id="form" action="update" method="post" class="form-horizontal">
    					<c:choose>
    						<c:when test="${nopadding}">
    							<div class="box-body" style="padding: 0;">
    						</c:when>
    						<c:otherwise>
    							<div class="box-body">
    						</c:otherwise>
    					</c:choose>
    					<jsp:doBody />
    					</div>
	    				<div class="box-footer">
	    					<div class="btn-group pull-left">
		    					<a href="<jsp:invoke fragment="deleteurl"/>" 
		    						onclick="return confirm('You are about to delete an entity, are you sure?');"
		    						class="btn btn-danger"><i class="fa fa-trash"></i> Delete</a>
		    				</div>
	    					<div class="btn-group pull-right">
		    					<a onclick="$('#form').submit()" class="btn btn-success"><i class="fa fa-save"></i> Save</a>
		    					<a href="<jsp:invoke fragment="cancelurl"/>" class="btn btn-default"><i class="fa fa-close"></i> Cancel</a>
		    				</div>
	    				</div>
    				</form>
    			</div>
    		</div>
    	</div>
    	<script>
    	$(document).ready(function($){
	    	// Change 'form' to class or ID of your specific form
			$("#editform").submit(function() {
				$(this).find(":input").filter(function(){ return !this.value; }).attr("disabled", "disabled");
				return true; // ensure form still submits
			});
			
			// Un-disable form fields when page loads, in case they click back after submission
			$("#editform").find( ":input" ).prop( "disabled", false );
   		});
    	</script>
    </jsp:body>
</t:main>