<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:toggle cols="5" label="Static" field="static" active="${dialplanSettings['static']}" options="Yes,No" values="yes,no" />
		<u:toggle cols="5" label="Write protected" field="writeprotect" active="${dialplanSettings['writeprotect']}" options="Yes,No" values="yes,no" />
		<u:toggle cols="5" label="New pattern match" field="extenpatternmatchnew" active="${dialplanSettings['extenpatternmatchnew']}" options="Yes,No" values="yes,no" />
		<u:toggle cols="5" label="Clear globals" field="clearglobalvars" active="${dialplanSettings['clearglobalvars']}" options="Yes,No" values="yes,no" />
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<div class="form-group">
			<label class="col-md-3 control-label">Globals</label>
			<div class="col-md-9">
				<input type="hidden" name="dialplanglobals" id="dialplanglobals" value="" />
				<div class="form-control" id="dialplanglobalsEditor" style="height: 15em;">${dialplanglobals}</div>
			</div>
		</div>
	</div>
</div>
