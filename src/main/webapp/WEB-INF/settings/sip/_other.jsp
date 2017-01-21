<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="language"><strong>Language</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="language"
					value="${settings['language']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Pedantic</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label
						class="btn btn-default ${(settings['pedantic'] == 'yes')?'active':''}">
						<input type="radio" name="pedantic" value="yes" autocomplete="off"
						${(settings['pedantic'] == 'yes')?'checked':''} />yes
					</label> <label
						class="btn btn-default ${(settings['pedantic'] != 'yes')?'active':''}">
						<input type="radio" name="pedantic" value="no" autocomplete="off"
						${(settings['pedantic'] != 'yes')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="codecs"><strong>Codecs</strong></label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="codecs"
					value="${settings['codecs']}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Preferred codec only</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label
						class="btn btn-default ${(settings['preferred_codec_only'] == 'yes')?'active':''}">
						<input type="radio" name="preferred_codec_only" value="yes"
						autocomplete="off"
						${(settings['preferred_codec_only'] == 'yes')?'checked':''} />yes
					</label> <label
						class="btn btn-default ${(settings['preferred_codec_only'] != 'yes')?'active':''}">
						<input type="radio" name="preferred_codec_only" value="no"
						autocomplete="off"
						${(settings['preferred_codec_only'] != 'yes')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Autoframing</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label
						class="btn btn-default ${(settings['autoframing'] == 'yes')?'active':''}">
						<input type="radio" name="autoframing" value="yes"
						autocomplete="off"
						${(settings['autoframing'] == 'yes')?'checked':''} />yes
					</label> <label
						class="btn btn-default ${(settings['autoframing'] != 'yes')?'active':''}">
						<input type="radio" name="autoframing" value="no"
						autocomplete="off"
						${(settings['autoframing'] != 'yes')?'checked':''} />no
					</label>
				</div>
			</div>
		</div>
	</div>
</div>
