<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>
   
<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6"> 
		<u:input label="Name" field="name" value="${conference.name}"/>
		<u:input label="Description" field="description" value="${conference.description}"/>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6"></div> 
</div>