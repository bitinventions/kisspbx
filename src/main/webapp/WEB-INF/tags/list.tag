<%@tag description="Create layout template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="buttons" fragment="true" required="false"%>

<t:main>
    <jsp:attribute name="title"><jsp:invoke fragment="title"/></jsp:attribute>
    <jsp:body>
    	<div class="row">
    		<div class="col-xs-12">
    			<div class="box box-solid">
    				<div class="box-header">
    					<h3 class="box-title">&nbsp;</h3>
    					<div class="btn-group pull-right">
    						<jsp:invoke fragment="buttons" />
    					</div>
    				</div>
    				<div class="box-body">
	    				<jsp:doBody />
    				</div>
    			</div>
    		</div>
    	</div>
    </jsp:body>
</t:main>