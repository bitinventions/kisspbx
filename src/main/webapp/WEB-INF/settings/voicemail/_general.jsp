<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="maxmsg">Mailbox size</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="maxmsg" value="${settings['maxmsg']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="maxsecs">Max length</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="maxsecs" value="${settings['maxsecs']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="minsecs">Min length</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="minsecs" value="${settings['minsecs']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="maxsilence">Silence length</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="maxsilence" value="${settings['maxsilence']}" />
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="format">Format</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="format" placeholder="ie. wav49|gsm|wav" value="${settings['format']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="maxlogins">Max logins</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="maxlogins" value="${settings['maxlogins']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Move heard</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${(settings['moveheard'] == 'yes')?'active':''}">
						<input type="radio" name="moveheard" value="yes" autocomplete="off" ${(settings['moveheard'] == 'yes')?'checked':''} />yes
					</label>
					 <label class="btn btn-default ${(settings['moveheard'] == 'no')?'active':''}">
						<input type="radio" name="moveheard" value="no" autocomplete="off" ${(settings['moveheard'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
	</div>
</div>
