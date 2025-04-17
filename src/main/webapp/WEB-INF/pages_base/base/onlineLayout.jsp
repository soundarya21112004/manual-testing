<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<!DOCTYPE html>
<html>
<head>
<title>MVS</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!-- END META SECTION -->

<!-- CSS INCLUDE -->
<link rel="stylesheet" type="text/css" id="theme"
	href="css/theme-default.css" />
<link href="js/plugins/sweetalert/sweetalert.css" rel="stylesheet" />

<!-- EOF CSS INCLUDE -->
</head>


<body>
	<div class="page-container page-navigation-top">
		<!-- PAGE CONTENT -->
		<div class="page-content">
			<tiles:insertAttribute name="header" />
			<div class="page-container page-navigation-top">
				<tiles:insertAttribute name="body" />
			</div>
			<tiles:insertAttribute name="footer" />
		</div>
	</div>



</body>
<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="js/plugins/bootstrap/bootstrap.min.js"></script>
<!-- END PLUGINS -->

<!-- START THIS PAGE PLUGINS-->
<script type='text/javascript' src='js/plugins/icheck/icheck.min.js'></script>
<script type="text/javascript"
	src="js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>

<script type="text/javascript"
	src="js/plugins/bootstrap/bootstrap-datepicker.js"></script>
<script type="text/javascript"
	src="js/plugins/bootstrap/bootstrap-timepicker.min.js"></script>
<script type="text/javascript"
	src="js/plugins/bootstrap/bootstrap-colorpicker.js"></script>
<script type="text/javascript"
	src="js/plugins/bootstrap/bootstrap-file-input.js"></script>
<script type="text/javascript"
	src="js/plugins/bootstrap/bootstrap-select.js"></script>
<script type="text/javascript"
	src="js/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/plugins/tableexport/tableExport.js"></script>
<script type="text/javascript" src="js/plugins/morris/raphael-min.js"></script>
<script type="text/javascript" src="js/plugins/morris/morris.min.js"></script>
<script type="text/javascript" src="js/plugins/rickshaw/d3.v3.js"></script>
<script type="text/javascript" src="js/plugins/rickshaw/rickshaw.min.js"></script>
<script type='text/javascript'
	src='js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js'></script>
<script type='text/javascript'
	src='js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js'></script>
<script type='text/javascript'
	src='js/plugins/bootstrap/bootstrap-datepicker.js'></script>
<script type="text/javascript" src="js/plugins/owl/owl.carousel.min.js"></script>

<script type="text/javascript" src="js/plugins/moment.min.js"></script>
<script type="text/javascript"
	src="js/plugins/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript"
	src="js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- END THIS PAGE PLUGINS-->

<!-- START TEMPLATE -->
<!--<script type="text/javascript" src="js/settings.js"></script>-->

<script type="text/javascript" src="js/plugins.js"></script>
<script type="text/javascript" src="js/actions.js"></script>

<script type='text/javascript'
	src='js/plugins/validationengine/languages/jquery.validationEngine-en.js'></script>
<script type='text/javascript'
	src='js/plugins/validationengine/jquery.validationEngine.js'></script>

<script type='text/javascript'
	src='js/plugins/jquery-validation/jquery.validate.js'></script>

<script type="text/javascript" src="js/demo_dashboard.js"></script>
</body>

</html>