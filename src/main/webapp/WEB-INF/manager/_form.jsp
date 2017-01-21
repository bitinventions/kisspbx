<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Name" field="name" value="${user.name}"/>
		<u:input type="password" label="Password" field="password" value="${user.password}"/>
		<u:input label="Read profile" field="readPermission" value="${user.readPermission}"/>
		<u:input label="Write profile" field="writePermission" value="${user.writePermission}"/>
		<u:input label="Deny mask" field="deny" value="${user.deny}"/>
		<u:input label="Permit mask" field="permit" value="${user.permit}"/>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-md-3 control-label">Extra</label>
			<div class="col-md-9">
				<input type="hidden" name="parameters" id="parameters" value="" />
				<div class="form-control" id="extraEditor" style="height: 25em;">${user.parameters}</div>
			</div>
		</div>
	</div>
</div>
<script>
    var editor = ace.edit("extraEditor");
    editor.setTheme("ace/theme/cobalt");
    editor.getSession().setMode("ace/mode/asterisk");
    $('#form').submit(function() {
    	$('#parameters').val(editor.getValue());
    });
</script>		
