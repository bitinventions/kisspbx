<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="language"><strong>Language</strong></label>
			<div class="col-sm-9"><input type="text" class="form-control" name="language" value="${settings['language']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="mohinterpret">Music onhold</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="mohinterpret" value="${settings['mohinterpret']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="mohsuggest">Moh suggest</label>
			<div class="col-sm-9"><input type="text" class="form-control" name="mohsuggest" value="${settings['mohsuggest']}"/></div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="codecs"><strong>Codecs</strong></label>
			<div class="col-sm-9"><input type="text" class="form-control" name="codecs" value="${settings['codecs']}"/></div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Bandwith</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['bandwith'] == 'low')?'active':''}">
						<input type="radio" name="bandwith" value="low" autocomplete="off" ${(settings['bandwith'] == 'low')?'checked':''}/>low
					</label>
					<label class="btn btn-default ${(settings['bandwith'] == 'medium')?'active':''}">
						<input type="radio" name="bandwith" value="medium" autocomplete="off"${(settings['bandwith'] == 'medium')?'checked':''}/>medium
					</label>
					<label class="btn btn-default ${(settings['bandwith'] == 'high')?'active':''}">
						<input type="radio" name="bandwith" value="high" autocomplete="off" ${(settings['bandwith'] == 'high')?'checked':''}/>high
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Codec priority</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['codecpriority'] == 'caller')?'active':''}">
						<input type="radio" name="codecpriority" value="caller" autocomplete="off" ${(settings['codecpriority'] == 'caller')?'checked':''}/>caller
					</label>
					<label class="btn btn-default ${(settings['codecpriority'] == 'host')?'active':''}">
						<input type="radio" name="codecpriority" value="host" autocomplete="off" ${(settings['codecpriority'] == 'host')?'checked':''}/>host
					</label>
					<label class="btn btn-default ${(settings['codecpriority'] == 'disabled')?'active':''}">
						<input type="radio" name="codecpriority" value="disabled" autocomplete="off" ${(settings['codecpriority'] == 'disabled')?'checked':''}/>disabled
					</label>
					<label class="btn btn-default ${(settings['codecpriority'] == 'reqonly')?'active':''}">
						<input type="radio" name="codecpriority" value="reqonly" autocomplete="off" ${(settings['codecpriority'] == 'reqonly')?'checked':''}/>reqonly
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Autoframing</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['autoframing'] == 'yes')?'active':''}">
						<input type="radio" name="autoframing" value="yes" autocomplete="off" ${(settings['autoframing'] == 'yes')?'checked':''}/>yes
					</label>
					<label class="btn btn-default ${(settings['autoframing'] == 'no')?'active':''}">
						<input type="radio" name="autoframing" value="no" autocomplete="off"${(settings['autoframing'] == 'no')?'checked':''}/>no
					</label>
				</div>
			</div>
		</div>
	</div>
</div>
