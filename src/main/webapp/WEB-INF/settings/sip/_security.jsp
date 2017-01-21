<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label">Allow guests</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label
						class="btn btn-default ${(settings['allowguest'] == 'yes')?'active':''}">
						<input type="radio" name="allowguest" value="yes"
						autocomplete="off"
						${(settings['allowguest'] == 'yes')?'checked':''} />yes
					</label> <label
						class="btn btn-default ${(settings['allowguest'] != 'yes')?'active':''}">
						<input type="radio" name="allowguest" value="no"
						autocomplete="off"
						${(settings['allowguest'] != 'yes')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="context"><strong>Default
					context</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="context"
					value="${settings['context']}" />
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6"></div>
</div>
