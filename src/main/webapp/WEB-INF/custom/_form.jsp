<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-10 col-sm-10">
		<u:input cols="2" label="Name" field="name" value="${app.name}"/>
		<div class="form-group">
			<label class="col-md-2 control-label">Dialplan</label>
			<div class="col-md-10">
				<input type="hidden" name="dialplan" id="dialplan" value="" />
				<div class="form-control" id="dialplanEditor" style="height: 32em;">${app.dialplan}</div>
			</div>
		</div>
	</div>
</div>
<script>
    var editor = ace.edit("dialplanEditor");
    editor.setTheme("ace/theme/cobalt");
    editor.getSession().setMode("ace/mode/asterisk");
    $('#form').submit(function() {
    	$('#dialplan').val(editor.getValue());
    });
</script>