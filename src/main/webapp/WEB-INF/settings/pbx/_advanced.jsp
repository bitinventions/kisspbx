<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-md-3 control-label">Extra features</label>
			<div class="col-md-9">
				<input type="hidden" name="extrafeatures" id="extrafeatures" value="" />
				<div class="form-control" id="extraFeaturesEditor" style="height: 25em;">${extrafeatures}</div>
			</div>
		</div>
	</div>
</div>
