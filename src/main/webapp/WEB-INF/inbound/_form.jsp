<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Name" field="name" value="${route.name}"/>
		<u:input label="Ddi" field="ddi" value="${route.ddi}"/>
		<u:input label="Cli" field="cli" value="${route.cli}"/>
		<div class="form-group">
			<label class="col-md-3 control-label" for="destinationType"><strong>DestinationType</strong></label>
			<div class="col-md-9">
			<select onchange="refreshForm();" class="form-control" name="destinationType" id="destinationType">
				<option ${(route.destinationType == null)?"selected":""} value="">[ -- select type -- ]</option>
				<option ${route.destinationType.toString().equals("EXTENSION")?"selected":""} value="EXTENSION">EXTENSION</option>
				<option ${route.destinationType.toString().equals("DIALPLAN")?"selected":""} value="DIALPLAN">DIALPLAN</option>
			</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-3 control-label" for="destination">Destination</label>
			<div class="col-md-9">
			<select class="form-control" name="destination" id="destination">
				<c:forEach items="${destinationCandidates}" var="d">
					<option ${route.destination.equals(d.getDestination())?"selected":""} value="${d.getDestination()}">${d.getDestinationName()}</option>
				</c:forEach>
			</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6"></div>
</div>