<%@tag description="Main layout template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="/WEB-INF/utils.tld"%>
<%@attribute name="title" fragment="true"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><jsp:invoke fragment="title" /></title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- jQuery 2.1.4 -->
<script src="<c:url value="/js/jQuery-2.1.4.min.js"/>"></script>
<!-- Bootstrap 3.3.5 -->
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/js/app.js"/>"></script>
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="<c:url value="/css/AdminLTE.min.css"/>">
<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
		          page. However, you can choose any other skin. Make sure you
		          apply the skin class to the body tag so the changes take effect.
		    -->
<link rel="stylesheet"
	href="<c:url value="/css/skins/skin-green.min.css"/>">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		    <![endif]-->
</head>

<body class="skin-green sidebar-mini">
	<div class="wrapper">
		<!-- Main Header -->
		<header class="main-header">

			<!-- Logo -->
			<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><i class="fa fa-paper-plane-o" style="margin-top: 18px;"></i></span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><i class="fa fa-paper-plane-o"></i>&nbsp;&nbsp;kiss<b>pbx</b></span>
			</a>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<c:if test="${u:isChanged()}">
						<li><a class="btn btn-danger" href="<c:url value="/asterisk/apply"/>"><i class="fa fa-refresh"></i></a></li>
						</c:if>
					</ul>
				</div>
			</nav>
		</header>

		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">

			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar Menu -->
				<ul class="sidebar-menu">
					<li class="header">MENU</li>
					<li><a href="<c:url value="/"/>"><i class="fa fa-dashboard"></i> <span>Dashboard</span></a></li>
					<li><a href="<c:url value="/extension/"/>"><i class="fa fa-phone"></i> <span>Extensions</span></a></li>
					<li class="treeview">
						<a href="#">
							<i class="fa fa-plug"></i>
							<span>Providers</span>
							<i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<li><a href="<c:url value="/provider/sip/"/>"><i class="fa fa-circle-o"></i> <span>SIP</span></a></li>
						</ul>
					</li>
					<li><a href="<c:url value="/inbound/"/>"><i class="fa fa-sign-in"></i> <span>Inbound routes</span></a></li>
					<li><a href="<c:url value="/outbound/"/>"><i class="fa fa-road"></i> <span>Outbound routes</span></a></li>
					<li><a href="<c:url value="/app/"/>"><i class="fa fa-gears"></i> <span>Dialplan</span></a></li>
					<li class="treeview">
						<a href="#">
							<i class="fa fa-star-o"></i>
							<span>Features</span>
							<i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<li><a href="<c:url value="/feature/queue/"/>"><i class="fa fa-list"></i> <span>Queues</span></a></li>
							<li><a href="<c:url value="/feature/huntgroup/"/>"><i class="fa fa-group"></i> <span>Huntgroup</span></a></li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#">
							<i class="fa fa-pencil-square-o"></i>
							<span>Advanced</span>
							<i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<li><a href="<c:url value="/template/"/>"><i class="fa fa-file-text-o"></i> <span>Templates</span></a></li>
							<li><a href="<c:url value="/custom/"/>"><i class="fa fa-laptop"></i> <span>Code</span></a></li>
							<li><a href="<c:url value="/manager/"/>"><i class="fa fa-users"></i> <span>Manager users</span></a></li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#">
							<i class="fa fa-wrench"></i>
							<span>Settings</span>
							<i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<li><a href="<c:url value="/settings/pbx/"/>"><i class="fa fa-circle-o"></i> <span>PBX</span></a></li>
							<li><a href="<c:url value="/settings/sip/"/>"><i class="fa fa-circle-o"></i> <span>SIP</span></a></li>
							<li><a href="<c:url value="/settings/iax2/"/>"><i class="fa fa-circle-o"></i> <span>IAX2</span></a></li>
							<li><a href="<c:url value="/settings/queue/"/>"><i class="fa fa-circle-o"></i> <span>Queue</span></a></li>
							<li><a href="<c:url value="/settings/manager/"/>"><i class="fa fa-circle-o"></i> <span>Manager</span></a></li>
							<li><a href="<c:url value="/settings/voicemail/"/>"><i class="fa fa-circle-o"></i> <span>Voicemail</span></a></li>
							<li><a href="<c:url value="/settings/cdr/"/>"><i class="fa fa-circle-o"></i> <span>CDR</span></a></li>
						</ul>
					</li>
					<li><a href="<c:url value="/cdr/"/>"><i class="fa fa-database"></i> <span>CDR</span></a></li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					<jsp:invoke fragment="title" />
				</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<c:if test="${error != null}">
			    	<div class="row">
			    		<div class="col-xs-12">
			    			<div class="alert alert-danger alert-dimissible">
			    				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
			    				<i class="fa fa-warning"></i> ${error}
			    			</div>
			    		</div>
			    	</div>
			   	</c:if>
			   	<c:if test="${info != null}">
			    	<div class="row">
			    		<div class="col-xs-12">
			    			<div class="alert alert-success alert-dimissible">
			    				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
			    				<i class="fa fa-info"></i> ${info}
			    			</div>
    		    		</div>
			    	</div>
			   	</c:if>
			   <jsp:doBody />
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<footer class="main-footer">
			<!-- To the right -->
			<div class="pull-right hidden-xs">Asterisk Configuration UI</div>
			<!-- Default to the left -->
			<strong>Copyright &copy; 2015 <a href="#">Bit Inventions S.L.</a>.</strong> All rights reserved.
		</footer>
	</div>
</body>
</html>