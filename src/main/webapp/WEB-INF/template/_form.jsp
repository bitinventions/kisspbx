<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Name" field="name" value="${template.name}"/>
		<u:select label="Type" field="type" active="${template.type}" options="SIP,IAX2" values="SIP,IAX2"/>
		<div class="form-group">
			<label class="col-md-3 control-label">Template</label>
			<div class="col-md-9">
				<input type="hidden" name="template" id="template" value="" />
				<div class="form-control" id="templateEditor" style="height: 25em;">${template.template}</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6"></div>
</div>

<script>
    var editor = ace.edit("templateEditor");
    editor.setTheme("ace/theme/cobalt");
    editor.getSession().setMode("ace/mode/asterisk");
    $('#form').submit(function() {
    	$('#template').val(editor.getValue());
    });
</script>