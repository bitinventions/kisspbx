<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div class="nav-tabs-custom">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#tab_general" data-toggle="tab">General</a></li>
		<!-- li><a href="#tab_announcements" data-toggle="tab">Announcements</a></li-->
		<li><a href="#tab_advanced" data-toggle="tab">Advanced</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane active" id="tab_general">
			<jsp:include page="_general.jsp"/>
		</div>
		<!-- div class="tab-pane" id="tab_announcements">
			<jsp:include page="_announcements.jsp"/>
		</div-->
		<div class="tab-pane" id="tab_advanced">
			<jsp:include page="_advanced.jsp"/>
		</div>
	</div>
</div>
