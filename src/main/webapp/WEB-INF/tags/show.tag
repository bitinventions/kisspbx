<%@tag description="Create layout template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="editurl" fragment="true"%>
<%@attribute name="deleteurl" fragment="true"%>
<%@attribute name="nopadding" required="false" %>

<t:main>
    <jsp:attribute name="title"><jsp:invoke fragment="title"/></jsp:attribute>
    <jsp:body>
    	<div class="row">
    		<div class="col-xs-12">
    			<div class="box box-solid">
    				<div class="box-header">
    					<h3 class="box-title">&nbsp;</h3>
	    				<div class="box-tools">
    						<a href="<jsp:invoke fragment="editurl"/>" class="btn btn-primary"><i class="fa fa-edit"></i></a>
    						<a onclick="return confirm('Are you sure?');" href="<jsp:invoke fragment="deleteurl"/>" class="btn btn-danger"><i class="fa fa-trash"></i></a>
		   				</div>
		   			</div>
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
    			</div>
    		</div>
    	</div>
    </jsp:body>
</t:main>