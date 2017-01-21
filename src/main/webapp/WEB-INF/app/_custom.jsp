<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Code/Pattern" field="pattern" value="${app.pattern}"/>
		<u:input label="Description" field="description" value="${app.description}"/>
		<div class="form-group">
			<label class="col-md-3 control-label" for="dialplan">Custom Code</label>
			<div class="col-md-9">
			<select class="form-control" name="dialplan" id="dialplan">
				<option ${(app.dialplan == null)?"selected":""} value="">[ -- select code -- ]</option>
				<c:forEach items="${dialplans}" var="d">
				<option ${(app.dialplan != null && app.dialplan.id == d.id)?"selected":""} value="${d.id}">${d.name}</option>
				</c:forEach>
			</select>
			</div>
		</div>
		<u:input label="Extension" field="extension" value="${app.extension}"/>
		<u:input label="Priority" field="priority" value="${app.priority}"/>
		<u:toggle values="true,false" options="Yes,No" label="Internal only" field="internalOnly"  active="${app.internalOnly}"/>
		<u:area rows="5" label="Variables" field="variables" value="${app.variables}"/>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<jsp:include page="_destination.jsp"/>
	</div>
</div>