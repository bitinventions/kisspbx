<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:main>
    <jsp:attribute name="title">IAX settings</jsp:attribute>
    <jsp:body>
	    <div class="row">
    		<div class="col-xs-12">
    			<div class="box box-solid">
    				<form id="form" action="save" method="post" class="form-horizontal">
    					<div class="box-body" style="padding: 0;">
    						<div class="nav-tabs-custom">
    							<ul class="nav nav-tabs">
    								<li class="active"><a href="#tab_general" data-toggle="tab">General</a></li>
    								<li><a href="#tab_iax" data-toggle="tab">Protocol</a></li>
    								<li><a href="#tab_other" data-toggle="tab">Other</a></li>
    								<li><a href="#tab_advanced" data-toggle="tab">Advanced</a></li>
    							</ul>
    							<div class="tab-content">
    								<div class="tab-pane active" id="tab_general">
    									<jsp:include page="_general.jsp"/>
									</div>
									<div class="tab-pane" id="tab_iax">
    									<jsp:include page="_iax.jsp"/>
									</div>
    								<div class="tab-pane" id="tab_other">
    									<jsp:include page="_other.jsp"/>
									</div>
									<div class="tab-pane" id="tab_advanced">
    									<jsp:include page="_advanced.jsp"/>
									</div>
    							</div>
							</div>
						</div>
						<div class="box-footer">
							<div class="btn-group pull-right">
								<a href="#" class="btn btn-success" onclick="$('#form').submit(); return false;"><i class="fa fa-save"></i> Save</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
    </jsp:body>
</t:main>