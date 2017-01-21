<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" title="Require calltoken">Req. calltoken</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['requirecalltoken'] == 'yes')?'active':''}">
						<input type="radio" name="requirecalltoken" value="yes" autocomplete="off" ${(settings['requirecalltoken'] == 'yes')?'checked':''}/>yes
					</label>
					<label class="btn btn-default ${(settings['requirecalltoken'] == 'no')?'active':''}">
						<input type="radio" name="requirecalltoken" value="no" autocomplete="off" ${(settings['requirecalltoken'] == 'no')?'checked':''}/>no
					</label>
					<label class="btn btn-default ${(settings['requirecalltoken'] == 'auto')?'active':''}">
						<input type="radio" name="requirecalltoken" value="auto" autocomplete="off" ${(settings['requirecalltoken'] == 'auto')?'checked':''}/>auto
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="calltokenoptional" title="Calltoken optional">Calltoken opt.</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="calltokenoptional" value="${settings['calltokenoptional']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="calltokenexpiration" title="Calltoken expiration">Calltoken exp.</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="calltokenexpiration" value="${settings['calltokenexpiration']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="regcontext" title="Registration context">Reg. context</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="regcontext" value="${settings['regcontext']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="miregexpire" title="Minimal registration interval">Min. reg.</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="miregexpire" value="${settings['minregexpire']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="maxregexpire" title="Maximum registration interval">Max. reg.</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="maxregexpire" value="${settings['maxregexpire']}"/></div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
	<div class="form-group">
		<label class="col-sm-3 control-label">IAX compat</label>
		<div class="col-sm-9">
			<div class="btn-group pull-right" data-toggle="buttons">
				<label class="btn btn-default ${(settings['iaxcompat'] == 'yes')?'active':''}">
					<input type="radio" name="iaxcompat" value="yes" autocomplete="off" ${(settings['iaxcompat'] == 'yes')?'checked':''}/>yes
				</label>
				<label class="btn btn-default ${(settings['iaxcompat'] == 'no')?'active':''}">
					<input type="radio" name="iaxcompat" value="no" autocomplete="off" ${(settings['iaxcompat'] == 'no')?'checked':''}/>no
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">Autokill</label>
		<div class="col-sm-9">
			<div class="btn-group pull-right" data-toggle="buttons">
				<label class="btn btn-default ${(settings['autokill'] == 'yes')?'active':''}">
					<input type="radio" name="autokill" value="yes" autocomplete="off" ${(settings['autokill'] == 'yes')?'checked':''}/>yes
				</label>
				<label class="btn btn-default ${(settings['autokill'] == 'no')?'active':''}">
					<input type="radio" name="autokill" value="no" autocomplete="off" ${(settings['autokill'] == 'no')?'checked':''}/>no
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">Encryption</label>
		<div class="col-sm-9">
			<div class="btn-group pull-right" data-toggle="buttons">
				<label class="btn btn-default ${(settings['encryption'] == 'yes')?'active':''}">
					<input type="radio" name="encryption" value="yes" autocomplete="off" ${(settings['encryption'] == 'yes')?'checked':''}/>yes
				</label>
				<label class="btn btn-default ${(settings['encryption'] == 'no')?'active':''}">
					<input type="radio" name="encryption" value="no" autocomplete="off" ${(settings['encryption'] == 'no')?'checked':''}/>no
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">Force encrypt.</label>
		<div class="col-sm-9">
			<div class="btn-group pull-right" data-toggle="buttons">
				<label class="btn btn-default ${(settings['forceencryption'] == 'yes')?'active':''}">
					<input type="radio" name="forceencryption" value="yes" autocomplete="off" ${(settings['forceencryption'] == 'yes')?'checked':''}/>yes
				</label>
				<label class="btn btn-default ${(settings['forceencryption'] == 'no')?'active':''}">
					<input type="radio" name="forceencryption" value="no" autocomplete="off" ${(settings['forceencryption'] == 'no')?'checked':''}/>no
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="iaxthreadcount" title="IAX helper threads">Threads</label>
		<div class="col-sm-9"><input type="text" class="form-control" name="iaxthreadcount" value="${settings['iaxthreadcount']}"/></div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="iaxmaxthreadcount " title="Maximum additional helper threads">Max. threads</label>
		<div class="col-sm-9"><input type="text" class="form-control" name="iaxmaxthreadcount " value="${settings['iaxmaxthreadcount ']}"/></div>
	</div>
</div>
</div>