<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>

<input type="hidden" name="type" value="${extension.type}"/>
		
<div class="nav-tabs-custom">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#tab_peer" data-toggle="tab">Extension</a>
		<li><a href="#tab_voicemail" data-toggle="tab">Voicemail</a>
		<li><a href="#tab_advanced" data-toggle="tab">Advanced</a>
	</ul>
 	<div class="tab-content">
 		<div class="tab-pane active" id="tab_peer">
 			<div class="row">
 				<div class="col-xs-12 col-md-6 col-sm-6">
		 			<u:input label="Extension" field="extension" value="${extension.extension}"/>
		 			<u:input label="Caller Id" field="callerid" value="${extension.callerid}"/>
		 			<u:input label="Call groups" field="callgroups" value="${extension.callgroups}"/>
		 			<u:input label="Pickup groups" field="pickupgroups" value="${extension.pickupgroups}"/>
				</div>
				<div class="col-xs-12 col-md-6 col-sm-6">
					<div class="form-group">
						<label class="col-md-3 control-label" for="template"><strong>Template</strong></label>
						<div class="col-md-9">
						<select class="form-control" name="template" id="template">
							<option selected value="">[ -- select template -- ]</option>
							<c:forEach items="${templates}" var="t">
								<option value="${t.id}" ${(extension.template.id == t.id)?"selected":""}>${t.name}</option>
							</c:forEach>
						</select>
						</div>
					</div>
					<u:input label="Username" field="username" value="${extension.username}"/>
		 			<u:input type="password" label="Password" field="password" value="${extension.password}"/>
				</div>
			</div>
		</div>
		<div class="tab-pane" id="tab_voicemail">
			<div class="row">
				<div class="col-xs-12 col-md-6 col-sm-6">
					<u:toggle label="Enabled" field="voicemail" active="${extension.voicemail?'true':'false'}" options="Yes,No" values="true,false"/>
		  			<u:input label="Pin" field="vmpin" value="${extension.vmpin}"/>
					<u:input label="Email" field="vmemail" value="${extension.vmemail}"/>
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
							<div class="form-control" id="extraEditor" style="height: 25em;">${extension.parameters}</div>
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