<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Name" field="name" value="${route.name}"/>
		<u:input label="Dial pattern" field="dialpattern" value="${route.dialpattern}"/>
		<u:input label="Remove" field="prefix" value="${route.prefix}"/>
		<u:input label="Prepend" field="prepend" value="${route.prepend}"/>
		<div class="form-group">
			<label class="col-md-3 control-label" for="provider"><strong>Provider</strong></label>
			<div class="col-md-9">
			<select class="form-control" name="provider" id="provider">
				<option ${(route.provider == null)?"selected":""} value="">[ -- select provider -- ]</option>
				<c:forEach items="${providers}" var="p">
				<option ${(route.provider != null && route.provider.id == p.id)?"selected":""} value="${p.id}">${p.name}</option>
				</c:forEach>
			</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6"></div>
</div>