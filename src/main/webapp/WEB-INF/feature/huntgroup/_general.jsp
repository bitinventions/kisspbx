<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-5 col-sm-5">
		<u:input label="Name" field="name" value="${huntgroup.name}" />
		<u:input label="Description" field="description" value="${huntgroup.description}" />
		<u:toggle label="Strategy" field="strategy" active="${huntgroup.strategy}" 
			options="Ringall,Sequential" values="ringall,sequential" />
		<u:input label="Dialing time" field="dialtime" value="${huntgroup.dialtime}" />
		<u:input label="Ringall timeout" field="timeout" value="${huntgroup.timeout}" />
		<u:input label="Sequential loops" field="times" value="${huntgroup.times}" />
	</div>
	<div class="col-xs-12 col-md-7 col-sm-7">
		<div class="form-group">
			<label class="col-md-4 control-label" for="priorityExtension">Priority extension</label>
			<div class="col-md-8">
			<select class="form-control" name="priorityExtension" id="priorityExtension">
				<option ${(huntgroup.priorityExtension == null)?"selected":""} value="">[ -- select extension -- ]</option>
			<c:forEach items="${extensions}" var="e">
				<option ${(huntgroup.priorityExtension.id == e.id)?"selected":""} value="${e.id}">${e}</option>
			</c:forEach>
			</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label" for="extensionGroup">Extension group</label>
			<div class="col-md-8">
			<select multiple class="form-control" name="extensionGroup" id="extensionGroup">
			<c:forEach items="${extensions}" var="e">
				<option ${huntgroup.extensionGroup.contains(e)?"selected":""} value="${e.id}">${e}</option>
			</c:forEach>
			</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label" for="voicemailExtension">Voicemail</label>
			<div class="col-md-8">
			<select class="form-control" name="voicemailExtension" id="voicemailExtension">
				<option ${(huntgroup.voicemailExtension == null)?"selected":""} value="">[ -- select extension -- ]</option>
			<c:forEach items="${extensions}" var="e">
				<option ${(huntgroup.voicemailExtension.id == e.id)?"selected":""} value="${e.id}">${e}</option>
			</c:forEach>
			</select>
			</div>
		</div>
	</div>
</div>