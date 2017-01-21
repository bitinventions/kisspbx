<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Pickup exten" field="pickupexten" value="${featureSettings['pickupexten']}" placeholder="*8"/>
	</div>
</div>
