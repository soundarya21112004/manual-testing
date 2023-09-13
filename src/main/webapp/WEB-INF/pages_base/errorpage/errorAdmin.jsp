<%-- 
    Document   : error
    Created on : Jun 25, 2015, 2:35:06 PM
    Author     : Mano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <link rel="icon" href="index.ico" type="image/x-icon">
    <link href="<%=request.getContextPath()%>/plugins/material-design-iconic-font/css/material-design-iconic-font.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/material-design-icon/css/material-design-icon.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/node-waves/waves.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/plugins/animate-css/animate.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/plugins/bootstrap-colorpicker/css/bootstrap-colorpicker.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/plugins/dropzone/dropzone.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/multi-select/css/multi-select.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/jquery-spinner/css/bootstrap-spinner.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/plugins/nouislider/nouislider.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/themes/all-themes.css" rel="stylesheet" />

    <link href="<%=request.getContextPath()%>/plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">

    <link href="<%=request.getContextPath()%>/plugins/morrisjs/morris.css" rel="stylesheet" />

    <link href="<%=request.getContextPath()%>/plugins/sweetalert/sweetalert.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />

    <body class="theme-darkGreen" oncontextmenu="return false;">
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header"  style="margin-left: 35%">
                <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
                <a href="javascript:void(0);" class="bars"></a>
                <a  href="#">
                    <img src="<%=request.getContextPath()%>/header-logo2.png" alt="Nigeria" width="100%" height="75px">
                </a>
            </div>

        </div>
    </nav>
    <div class="five-zero-zero">
        <div class="five-zero-zero-container">
            <div class="error-code">&nbsp;</div>
            <div class="error-message"> YOU CANNOT VIEW THIS MODULE!</div>
            <div class="button-place">
                <a href="logout" class="btn bg-light-green btn-lg waves-effect">CLICK HERE TO LOGIN </a>
            </div>
        </div>
    </div>
</body>
</html>


