<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-5 control-label">Persistent members</label>
			<div class="col-sm-7">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['persistentmembers'] == 'yes')?'active':''}">
						<input type="radio" name="persistentmembers" value="yes" autocomplete="off" ${(settings['persistentmembers'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['persistentmembers'] == 'no')?'active':''}">
						<input type="radio" name="persistentmembers" value="no" autocomplete="off" ${(settings['persistentmembers'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label">Autofill</label>
			<div class="col-sm-7">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['autofill'] == 'yes')?'active':''}">
						<input type="radio" name="autofill" value="yes" autocomplete="off" ${(settings['autofill'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['autofill'] == 'no')?'active':''}">
						<input type="radio" name="autofill" value="no" autocomplete="off" ${(settings['autofill'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label">Monitor type</label>
			<div class="col-sm-7">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['monitor-type'] == 'MixMonitor')?'active':''}">
						<input type="radio" name="monitor-type" value="MixMonitor" autocomplete="off" ${(settings['monitor-type'] == 'MixMonitor')?'checked':''} />new
					</label>
					 <label class="btn btn-default ${(settings['monitor-type'] == 'Monitor')?'active':''}">
						<input type="radio" name="monitor-type" value="Monitor" autocomplete="off" ${(settings['monitor-type'] == 'Monitor')?'checked':''} />old
					</label>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-5 control-label">Update CDR</label>
			<div class="col-sm-7">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['updatecdr'] == 'yes')?'active':''}">
						<input type="radio" name="updatecdr" value="yes" autocomplete="off" ${(settings['updatecdr'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['updatecdr'] == 'no')?'active':''}">
						<input type="radio" name="updatecdr" value="no" autocomplete="off" ${(settings['updatecdr'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label">Shared last call</label>
			<div class="col-sm-7">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['shared_lastcall'] == 'yes')?'active':''}">
						<input type="radio" name="shared_lastcall" value="yes" autocomplete="off" ${(settings['shared_lastcall'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['shared_lastcall'] == 'no')?'active':''}">
						<input type="radio" name="shared_lastcall" value="no" autocomplete="off" ${(settings['shared_lastcall'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label">Negative penalty invalid</label>
			<div class="col-sm-7">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['negative_penalty_invalid'] == 'yes')?'active':''}">
						<input type="radio" name="negative_penalty_invalid" value="yes" autocomplete="off" ${(settings['negative_penalty_invalid'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['negative_penalty_invalid'] == 'no')?'active':''}">
						<input type="radio" name="negative_penalty_invalid" value="no" autocomplete="off" ${(settings['negative_penalty_invalid'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>		
	</div>
</div>
