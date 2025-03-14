<!DOCTYPE html>
<html lang="en">
<head>
	<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Manual Verification System</title>
	<link rel="icon" href="favicon.ico" type="image/x-icon" />

	<!-- Google Font: Source Sans Pro -->
	<link rel="stylesheet"
		  href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
	<!-- Font Awesome Icons -->
	<link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
	<!-- overlayScrollbars -->
	<link rel="stylesheet" href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
	<link rel="stylesheet" href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
	<link rel="stylesheet" href="plugins/datatables-buttons/css/buttons.bootstrap4.min.css">
	<link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="dist/css/adminlte.min.css">
	<link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.css">
<%--	<script type="text/javascript">
		window.history.forward();
		function preventBack() {
			window.history.forward();
		}
		setTimeout("preventBack()", 0);

	</script>--%>
	<script>
		window.onload = function() {
			checkwindow();

		}
		function checkwindow(){
			if(window.name != 'appname')
				window.location = "<c:url value='loginPage'/>";
			// window.location = "/MVS/loginPage";
		}
	</script>
</head>


<body
		class="hold-transition sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">

<div class="wrapper">
	<!-- PAGE CONTENT -->
	<tiles:insertAttribute name="sidemenu" />


	<div class="content-wrapper">
		<tiles:insertAttribute name="body" />
	</div>
	<tiles:insertAttribute name="footer" />

</div>

<script src="plugins/jquery/jquery.min.js"></script>

<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- DataTables  & Plugins -->
<script src="plugins/datatables/jquery.dataTables.min.js"></script>
<script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>

<script src="plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="plugins/jquery-validation/jquery.validate.min.js"></script>

<!-- overlayScrollbars -->
<script
		src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.js"></script>

<!-- PAGE PLUGINS -->
<!-- jQuery Mapael -->
<script src="plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
<script src="plugins/raphael/raphael.min.js"></script>
<script src="plugins/jquery-mapael/jquery.mapael.min.js"></script>
<script src="plugins/jquery-mapael/maps/usa_states.min.js"></script>
<!-- ChartJS -->
<script src="plugins/chart.js/Chart.min.js"></script>

<!-- AdminLTE for demo purposes -->

<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<%--	<script src="dist/js/pages/dashboard2.js"></script>--%>

<script src="plugins/sweetalert2/sweetalert2.js"></script>
<!-- Toastr -->

</body>

</html>