<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:main>
	<jsp:attribute name="title">Dashboard</jsp:attribute>
	<jsp:body>
		<div class="row">
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box" id="asterisk">
	            	<span class="info-box-icon bg-red"><i class="fa fa-asterisk" style="margin-top: 22px"> </i></span>
	
		            <div class="info-box-content">
		              <span class="info-box-text">Asterisk</span>
		              <span class="info-box-number"></span>
		              <span class="progress-description"></span>
		          </div>
		          <!-- /.info-box-content -->
	          </div>
          	</div>
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box" id="calls">
	            	<span class="info-box-icon bg-orange"><i class="fa fa-phone" style="margin-top: 22px"> </i></span>
	
		            <div class="info-box-content">
		              <span class="info-box-text">Calls</span>
		              <span class="info-box-number"></span>
		              
		              <div class="progress"></div>
		              <span class="progress-description"></span>
		          </div>
		          <!-- /.info-box-content -->
	          </div>
          	</div>
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box" id="phonesOnline">
	            	<span class="info-box-icon bg-purple"><i class="fa fa-users" style="margin-top: 22px"> </i></span>
	
		            <div class="info-box-content">
		              <span class="info-box-text">Extensions</span>
		              <span class="info-box-number"></span>
		
		              <div class="progress">
		                <div class="progress-bar bg-purple"></div>
		              </div>
		              <span class="progress-description"></span>
		          </div>
		          <!-- /.info-box-content -->
	          </div>
          	</div>
          	<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box" id="trunksOnline">
	            	<span class="info-box-icon bg-aqua"><i class="fa fa-plug" style="margin-top: 22px"> </i></span>
	
		            <div class="info-box-content">
		              <span class="info-box-text">Trunks</span>
		              <span class="info-box-number"></span>
		
		              <div class="progress">
		                <div class="progress-bar bg-aqua"></div>
		              </div>
		              <span class="progress-description"></span>
		          </div>
		          <!-- /.info-box-content -->
	          </div>
          	</div>
		</div>
		<script type="text/javascript" src="js/dashboard.js"></script>
	</jsp:body>
</t:main>