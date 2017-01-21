<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<script src="<c:url value="/js/ace/ace.js"/>" type="text/javascript" charset="utf-8"></script>

<t:main>
	<jsp:attribute name="title">PBX settings</jsp:attribute>
	<jsp:body>
	    <div class="row">
    		<div class="col-xs-12">
    			<div class="box box-solid">
    				<form id="form" action="save" method="post">
    					<div class="box-body" style="padding: 0;">
    						<div class="nav-tabs-custom">
    							<ul class="nav nav-tabs">
    								<li class="active"><a href="#tab_asterisk" data-toggle="tab">Asterisk</a></li>
    								<li><a href="#tab_dialplan" data-toggle="tab">Dialplan</a></li>
    								<li><a href="#tab_features" data-toggle="tab">Features</a></li>
    								<li><a href="#tab_advanced" data-toggle="tab">Advanced</a></li>
    							</ul>
    							<div class="tab-content">
    								<div class="tab-pane active" id="tab_asterisk">
    									<div class="row">
	    									<div class="col-xs-12 col-md-6 col-sm-6">
			    								<div class="form-group">
													<label class="control-label" for="host"><strong>Host</strong></label>
													<input type="text" class="form-control" name="asterisk.host"
																value="${pbxSettings['asterisk.host']}" id="name" required />
												</div>
												<div class="form-group">
													<label class="control-label" for="user"><strong>Username</strong></label>
													<input type="text" class="form-control" name="asterisk.user"
																value="${pbxSettings['asterisk.user']}" id="value"
																required />
												</div>
												<div class="form-group">
													<label class="control-label" for="pwd"><strong>Password</strong></label>
													<input type="text" class="form-control"
																name="asterisk.password"
																value="${pbxSettings['asterisk.password']}" id="value"
																required />
												</div>
											</div>
										</div>
    								</div>
    								<div class="tab-pane" id="tab_dialplan">
	    								<jsp:include page="_dialplan.jsp" />
    								</div>
    								<div class="tab-pane" id="tab_features">
    									<jsp:include page="_features.jsp" />
			    					</div>
    								<div class="tab-pane" id="tab_advanced">
    									<jsp:include page="_advanced.jsp" />
    								</div>
    								<script>
									    var editor1 = ace.edit("extraFeaturesEditor");
									    editor1.setTheme("ace/theme/cobalt");
									    editor1.getSession().setMode("ace/mode/asterisk");
									    var editor2 = ace.edit("dialplanglobalsEditor");
									    editor2.setTheme("ace/theme/cobalt");
									    editor2.getSession().setMode("ace/mode/asterisk");
									    $('#form').submit(function() {
									    	$('#extrafeatures').val(editor1.getValue());
									    	$('#dialplanglobals').val(editor2.getValue());
									    });
									</script>
    							</div>
							</div>
						</div>
						<div class="box-footer">
							<div class="btn-group pull-right">
								<a href="#" class="btn btn-success"
									onclick="$('#form').submit(); return false;"><i
									class="fa fa-save"></i> Save</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
    </jsp:body>
</t:main>