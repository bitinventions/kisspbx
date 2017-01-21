<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="form-group">
	<label class="col-md-3 control-label" for="finalDestinationType"><strong>DestinationType</strong></label>
	<div class="col-md-9">
	<select onchange="refreshForm();" class="form-control" name="finalDestinationType" id="finalDestinationType">
		<option ${(app.finalDestinationType == null)?"selected":""} value="">[ -- select type -- ]</option>
		<option ${app.finalDestinationType.toString().equals("EXTENSION")?"selected":""} value="EXTENSION">EXTENSION</option>
		<option ${app.finalDestinationType.toString().equals("DIALPLAN")?"selected":""} value="DIALPLAN">DIALPLAN</option>
	</select>
	</div>
</div>
<div class="form-group">
	<label class="col-md-3 control-label" for="finalDestination">Destination</label>
	<div class="col-md-9">
	<select class="form-control" name="finalDestination" id="finalDestination">
		<c:forEach items="${destinationCandidates}" var="d">
			<option ${app.finalDestination.equals(d.getDestination())?"selected":""} value="${d.getDestination()}">${d.getDestinationName()}</option>
		</c:forEach>
	</select>
	</div>
</div>