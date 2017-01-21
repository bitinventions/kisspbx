<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>

<div class="row">
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:input label="Name" field="name" value="${queue.name}" />
		<u:input label="Description" field="description" value="${queue.description}" />
		<u:select label="Strategy" field="strategy" active="${queue.strategy}" 
			options="ringall,leastrecent,fewestcalls,random,rrmemory,rrordered,linear,wrandom"
			values="RINGALL,LEASTRECENT,FEWESTCALLS,RANDOM,RRMEMORY,RRORDERED,LINEAR,WRANDOM" />
		<u:toggle label="Join empty" field="joinempty" active="${queue.joinempty?'true':'false'}"
			options="Yes,No" values="true,false" />
		<u:toggle label="Leave empty" field="leavewhenempty" active="${queue.leavewhenempty?'true':'false'}"
			options="Yes,No" values="true,false" />
		<u:input label="Max queued" field="maxlen" value="${queue.maxlen}" />
	</div>
	<div class="col-xs-12 col-md-6 col-sm-6">
		<u:area rows="5" label="Static members" field="members" value="${queue.members}" />
		<u:input type="number" label="Ringing time" field="timeout" value="${queue.timeout}" />
		<u:input type="number" label="Retry timeout" field="retry" value="${queue.retry}" />
		<u:input type="number" label="Wrapup time" field="wrapuptime" value="${queue.wrapuptime}" />
	</div>
</div>