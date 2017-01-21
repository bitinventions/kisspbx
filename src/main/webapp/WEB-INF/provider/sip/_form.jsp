<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>

<div class="nav-tabs-custom">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#tab_general" data-toggle="tab">General</a>
		<li><a href="#tab_advanced" data-toggle="tab">Advanced</a>
	</ul>
 	<div class="tab-content">
 		<div class="tab-pane active" id="tab_general">
			<div class="row">
				<div class="col-xs-12 col-md-6 col-sm-6">
					<u:input label="Name" field="name" value="${provider.name}"/>
					<u:input label="DID" field="did" value="${provider.did}"/>
					<u:input label="Host" field="host" value="${provider.host}"/>
					<u:input label="Username" field="username" value="${provider.username}"/>
					<u:input label="Password" field="password" value="${provider.password}"/>
				</div>
				<div class="col-xs-12 col-md-6 col-sm-6">
					<u:toggle label="Register" field="register" active="${provider.register?'true':'false'}" options="Yes,No" values="true,false"/>
					<u:toggle label="Auth inbound" field="insecure" active="${provider.insecure?'true':'false'}" options="Yes,No" values="false,true"/>
				</div>
			</div>
		</div>
		<div class="tab-pane" id="tab_advanced">
			<div class="row">
				<div class="col-xs-12 col-md-6 col-sm-6">
					<div class="form-group">
						<label class="col-md-3 control-label">Extra</label>
						<div class="col-md-9">
							<input type="hidden" name="parameters" id="parameters" value="" />
							<div class="form-control" id="extraEditor" style="height: 25em;">${provider.parameters}</div>
						</div>
					</div>
				</div>
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