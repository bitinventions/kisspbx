<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="tos_sip"><strong>TOS
					SIP</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="tos_sip"
					value="${settings['tos_sip']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="tos_audio"><strong>TOS
					Audio</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="tos_audio"
					value="${settings['tos_audio']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="tos_video"><strong>TOS
					Video</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="tos_video"
					value="${settings['tos_video']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="tos_text"><strong>TOS
					Text</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="tos_text"
					value="${settings['tos_text']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="cos_sip"><strong>COS
					SIP</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="cos_sip"
					value="${settings['cos_sip']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="cos_audio"><strong>COS
					Audio</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="cos_audio"
					value="${settings['cos_audio']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="cos_video"><strong>COS
					Video</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="cos_video"
					value="${settings['cos_video']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="cos_text"><strong>COS
					Text</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="cos_text"
					value="${settings['cos_text']}" />
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="qualify"><strong>Qualify</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="qualify"
					value="${settings['qualify']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="qualifyfreq"><strong>Qualify
					freq.</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="qualifyfreq"
					value="${settings['qualifyfreq']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="qualifygap"><strong>Qualify
					gap</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="qualifygap"
					value="${settings['qualifygap']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="keepalive"><strong>Keepalive</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="keepalive"
					value="${settings['keepalive']}" />
			</div>
		</div>
	</div>
</div>
