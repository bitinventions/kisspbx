<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="announce-frequency">Frequency</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="announce-frequency" value="${queue.announcefrequency}" id="name" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="min-announce-frequency">Min. frequency</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="min-announce-frequency" value="${queue.minannouncefrequency}" id="name" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="periodic-announce-frequency">Periodic freq.</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="periodic-announce-frequency" value="${queue.periodicannouncefrequency}" id="name" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Random ann.</label>
			<div class="col-sm-9">
				<div class="btn-group pull-right" data-toggle="buttons">
					<label class="btn btn-default ${queue.randomperiodicannounce?'active':''}">
						<input type="radio" name="random-periodic-announce" value="yes" autocomplete="off" ${queue.randomperiodicannounce?'checked':''}/>yes
					</label>
					<label class="btn btn-default ${queue.randomperiodicannounce?'':'active'}">
						<input type="radio" name="random-periodic-announce" value="no" autocomplete="off"${queue.randomperiodicannounce?'':'checked'}/>no
					</label>
				</div>
			</div>
		</div>
		
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		
	</div>
</div>