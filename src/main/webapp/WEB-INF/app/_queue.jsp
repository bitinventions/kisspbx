<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Code/Pattern" field="pattern" value="${app.pattern}"/>
		<u:input label="Description" field="description" value="${app.description}"/>
		<div class="form-group">
			<label class="col-md-3 control-label" for="queue">Queue</label>
			<div class="col-md-9">
			<select class="form-control" name="queue" id="queue">
				<option ${(app.queue == null)?"selected":""} value="">[ -- select queue -- ]</option>
				<c:forEach items="${queues}" var="q">
				<option ${(app.queue != null && app.queue.id == q.id)?"selected":""} value="${q.id}">${q.name}</option>
				</c:forEach>
			</select>
			</div>
		</div>
		<u:toggle values="true,false" options="Yes,No" label="Internal only" field="internalOnly" active="${app.internalOnly}"/>
		<u:area rows="5" label="Variables" field="variables" value="${app.variables}"/>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<jsp:include page="_destination.jsp"/>
	</div>
</div>