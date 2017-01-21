<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:toggle label="Enabled" field="enabled" active="${settings['enabled']}" options="Yes,No" values="true,false"/>
		<u:input label="Bind address" field="bindaddr" value="${settings['bindaddr']}" />
		<u:input label="Port" field="port" value="${settings['port']}" />
	</div>
</div>
