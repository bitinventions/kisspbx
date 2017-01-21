<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:toggle label="Enabled" field="enabled" active="${settings['enabled']}" options="Yes,No" values="true,false"/>
		<u:input label="DSN" field="dsn" value="${settings['dsn']}" />
		<u:input label="Database" field="database" value="${settings['database']}" />
		<u:input label="Username" field="username" value="${settings['username']}" />
		<u:input label="Password" field="password" value="${settings['password']}" />
	</div>
</div>
