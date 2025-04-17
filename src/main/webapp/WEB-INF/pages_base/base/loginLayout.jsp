<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@page contentType="text/html"
	errorPage="/pages_base/errorpage/error.jsp" pageEncoding="UTF-8"%>

<html>

<head>
<title><tiles:insertAttribute name="title" ignore="true"></tiles:insertAttribute></title>
<title>MVS</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!-- END META SECTION -->

<!-- CSS INCLUDE -->
<%--<link rel="stylesheet" type="text/css" id="theme"--%>
<%--	href="css/theme-default.css" />--%>
	<link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.css">

<script type="text/javascript">
	function preventBack() {
		window.history.forward(1);
	}
	setTimeout("preventBack()", 0);

</script>
</head>


<body class="login-page">

	<tiles:insertAttribute name="body" />

	<script src="plugins/sweetalert2/sweetalert2.js"></script>

</body>

<%--<script src="<%=request.getContextPath()%>/js/plugins/jquery/jquery.min.js"></script>--%>

