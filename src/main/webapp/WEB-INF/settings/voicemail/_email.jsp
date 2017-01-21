<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label">Attach audio</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['attach'] == 'yes')?'active':''}">
						<input type="radio" name="attach" value="yes" autocomplete="off" ${(settings['attach'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['attach'] == 'no')?'active':''}">
						<input type="radio" name="attach" value="no" autocomplete="off" ${(settings['attach'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="emailsubject">Subject</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" placeholder="[PBX]: New message $\{VM_MSGNUM\} in ..." name="emailsubject" value="${settings['emailsubject']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="emailbody">Body</label>
			<div class="col-sm-9">
				<textarea class="form-control" rows="3" placeholder="Dear $\{VM_NAME\}:\n\n\tjust wanted to let you know..." name="emailbody">${settings['emailbody']}</textarea>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="serveremail">From address</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="serveremail" value="${settings['serveremail']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="fromstring">From name</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="fromstring" value="${settings['fromstring']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="emaildateformat">Date format</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="emaildateformat" value="${settings['emaildateformat']}" />
			</div>
		</div>
	</div>
</div>
