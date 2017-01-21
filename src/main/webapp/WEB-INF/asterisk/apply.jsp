<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:main>
    <jsp:attribute name="title">Configuration applied</jsp:attribute>
    <jsp:body>
    	<div class="row">
    		<div class="col-xs-12">
    			<div class="box box-success">
    				<div class="box-header">
    					<h3 class="box-title">Create configuration files</h3>
    				</div>
    				<div class="box-body">
	    				<u:AsHTML value="${result}"/>
    				</div>
    			</div>
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-xs-12">
    			<div class="box box-success">
    				<div class="box-header">
    					<h3 class="box-title">Reload configuration</h3>
    				</div>
    				<div class="box-body">
	    				<u:AsHTML value="${reload}"/>
    				</div>
    			</div>
    		</div>
    	</div>
    </jsp:body>
</t:main>