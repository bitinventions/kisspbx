<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Bind address" field="bindaddr" value="${settings['bindaddr']}" />
		<u:input label="Port" field="bindport" value="${settings['bindport']}" />
		<u:toggle label="SRV lookup" field="srvlookup" active="${settings['srvlookup']}" options="Yes,No" values="yes,no"/>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="accountcode">Account code</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="accountcode" value="${settings['accountcode']}"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">AMA flags</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['amaflags'] == 'default')?'active':''}">
						<input type="radio" name="amaflags" value="default" autocomplete="off" ${(settings['amaflags'] == 'default')?'checked':''}/>default
					</label>
					<label class="btn btn-default ${(settings['amaflags'] == 'omit')?'active':''}">
						<input type="radio" name="amaflags" value="omit" autocomplete="off" ${(settings['amaflags'] == 'omit')?'checked':''}/>omit
					</label>
					<label class="btn btn-default ${(settings['amaflags'] == 'billing')?'active':''}">
						<input type="radio" name="amaflags" value="billing" autocomplete="off" ${(settings['amaflags'] == 'billing')?'checked':''}/>billing
					</label>
					<label class="btn btn-default ${(settings['amaflags'] == 'documentation')?'active':''}">
						<input type="radio" name="amaflags" value="documentation" autocomplete="off" ${(settings['amaflags'] == 'documentation')?'checked':''}/>documentation
					</label>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" title="Disable UDP checksums">No checksum</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['nochecksums'] == 'yes')?'active':''}">
						<input type="radio" name="nochecksums" value="yes" autocomplete="off" ${(settings['nochecksums'] == 'yes')?'checked':''}/>yes
					</label>
					<label class="btn btn-default ${(settings['nochecksums'] == 'no')?'active':''}">
						<input type="radio" name="nochecksums" value="no" autocomplete="off"${(settings['nochecksums'] == 'no')?'checked':''}/>no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Delay reject</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['delayreject'] == 'yes')?'active':''}">
						<input type="radio" name="delayreject" value="yes" autocomplete="off" ${(settings['delayreject'] == 'yes')?'checked':''}/>yes
					</label>
					<label class="btn btn-default ${(settings['delayreject'] == 'no')?'active':''}">
						<input type="radio" name="delayreject" value="no" autocomplete="off"${(settings['delayreject'] == 'no')?'checked':''}/>no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">ADSI</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['adsi'] == 'yes')?'active':''}">
						<input type="radio" name="adsi" value="yes" autocomplete="off" ${(settings['adsi'] == 'yes')?'checked':''}/>yes
					</label>
					<label class="btn btn-default ${(settings['adsi'] == 'no')?'active':''}">
						<input type="radio" name="adsi" value="no" autocomplete="off"${(settings['adsi'] == 'no')?'checked':''}/>no
					</label>
				</div>
			</div>
		</div>
	</div>
</div>
