<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="bindaddr"><strong>Bind
					address</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="bindaddr"
					value="${settings['bindaddr']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="transport"><strong>Transports</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="transport"
					value="${settings['transport']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">SRV lookup</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label
						class="btn btn-default ${(settings['srvlookup'] == 'yes')?'active':''}">
						<input type="radio" name="srvlookup" value="yes"
						autocomplete="off"
						${(settings['srvlookup'] == 'yes')?'checked':''} />yes
					</label> <label
						class="btn btn-default ${(settings['srvlookup'] == 'no')?'active':''}">
						<input type="radio" name="srvlookup" value="no" autocomplete="off"
						${(settings['srvlookup'] == 'no')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label">Nat</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label
						class="btn btn-default ${(settings['nat'] == 'yes')?'active':''}">
						<input type="radio" name="nat" value="yes" autocomplete="off"
						${(settings['nat'] == 'yes')?'checked':''} />yes
					</label> <label
						class="btn btn-default ${(settings['nat'] == 'no')?'active':''}">
						<input type="radio" name="nat" value="no" autocomplete="off"
						${(settings['nat'] == 'no')?'checked':''} />no
					</label> <label
						class="btn btn-default ${(settings['nat'] == 'never')?'active':''}">
						<input type="radio" name="nat" value="never" autocomplete="off"
						${(settings['nat'] == 'never')?'checked':''} />never
					</label> <label
						class="btn btn-default ${(settings['nat'] == 'route')?'active':''}">
						<input type="radio" name="nat" value="route" autocomplete="off"
						${(settings['nat'] == 'route')?'checked':''} />route
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="stunaddr"><strong>Stun
					server</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="stunaddr"
					value="${settings['stunaddr']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="externhost"><strong>Extern
					host</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="externhost"
					value="${settings['externhost']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="localnet"><strong>Local
					network</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="localnet"
					value="${settings['localnet']}" />
			</div>
		</div>
	</div>
</div>
