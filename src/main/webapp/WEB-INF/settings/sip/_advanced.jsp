<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-md-3 control-label">Extra</label>
			<div class="col-md-9">
				<input type="hidden" name="extra" id="extra" value="" />
				<div class="form-control" id="extraEditor" style="height: 25em;">${extra}</div>
			</div>
		</div>
	</div>
	<script>
	    var editor = ace.edit("extraEditor");
	    editor.setTheme("ace/theme/cobalt");
	    editor.getSession().setMode("ace/mode/asterisk");
	    $('#form').submit(function() {
	    	$('#extra').val(editor.getValue());
	    });
	</script>
</div>
