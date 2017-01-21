<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<t:main>
    <jsp:attribute name="title">
      Welcome to asterisk gui
    </jsp:attribute>
    <jsp:body>
        <div class="row">
    		<div class="col-md-6">
    			<div class="box box-success">
    				<div class="box-header">
    					<h3 class="box-title">SIP peers</h3>
    				</div>
    				<div class="box-body">
	    				<u:AsHTML value="${sippeers}"/>
    				</div>
    			</div>
    		</div>
    		<div class="col-md-6">
    			<div class="box box-success">
    				<div class="box-header">
    					<h3 class="box-title">IAX2 peers</h3>
    				</div>
    				<div class="box-body">
	    				<u:AsHTML value="${iax2peers}"/>
    				</div>
    			</div>
    		</div>
    	</div>
    </jsp:body>
</t:main>