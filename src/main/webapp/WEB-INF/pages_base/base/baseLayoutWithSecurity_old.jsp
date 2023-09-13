<%-- 
    Document   : baseLayoutWithSecurity_old
    Created on : 24 Feb, 2020, 3:40:05 PM
    Author     : aruna.s
--%>

<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@page contentType="text/html" errorPage="/pages_base/errorpage/error.jsp" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

        <% response.setHeader("Pragma", "no-cache");%>
        <% response.setHeader("Cache-Control", "no-cache");%>
        <% response.setHeader("Cache-Control", "no-store");%>
        <% response.setHeader("Expires", "0");%>

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

    </head>
    <script type = "text/javascript">
        function preventBack() {
            window.history.forward(1);
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null
        };
    </script>


    <body class="theme-darkGreen" oncontextmenu="return false;">

        <s:if test="#session.userName.email neq null" >
            <s:if test="#session.userName.isloggedin neq '1'" > 
                <!--            <div class="page-loader-wrapper">
                                <div class="loader">
                                    <div class="preloader">
                                        <div class="spinner-layer pl-darkGreen">
                                            <div class="circle-clipper left">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="circle-clipper right">
                                                <div class="circle"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <p>Please wait...</p>
                                </div>
                            </div>-->
                <!--#END# Page Loader--> 
                <!--Overlay For Sidebars--> 
                <div class="overlay"></div>

                <nav class="navbar">
                    <div class="container-fluid">
                        <tiles:insertAttribute name="header" />
                    </div>
                </nav>
                <section>
                    <aside id="leftsidebar" class="sidebar">
                        <tiles:insertAttribute name="menu" />
                    </aside>

                </section>

                <section class="content">
                    <div class="container-fluid">
                        <tiles:insertAttribute name="body" />
                    </div>
                </section>
            </s:if>
            <s:else>
                <div class="five-zero-zero">
                    <div class="five-zero-zero-container">
                        <div class="error-code">&nbsp;</div>
                        <div class="error-message">Already LoggedIn</div>
                        <div class="button-place">
                            <a href="login" class="btn bg-light-green btn-lg waves-effect">GO TO HOMEPAGE</a>
                        </div>
                    </div>
                </div>
            </s:else>
        </s:if>
        <s:else>          
            <s:if test='#session.onlineRegStatus eq "YES"' >

                <!--#END# Page Loader--> 
                <!--Overlay For Sidebars--> 
                <div class="overlay"></div>

                <nav class="navbar">
                    <div class="container-fluid">
                        <tiles:insertAttribute name="header" />
                    </div>
                </nav>
                <section>
                    <aside id="leftsidebar" class="sidebar">
                        <tiles:insertAttribute name="menu" />
                    </aside>

                </section>

                <section class="content">
                    <!--<section class="content" style="margin: 150px 15px 10px 10px">-->
                    <div class="container-fluid">
                        <tiles:insertAttribute name="body" />
                    </div>
                </section>
            </s:if>
            <s:else>
                <!--<div class="card" style="border-radius: 20px;">-->
                <div class="five-zero-zero">
                    <div class="five-zero-zero-container">
                        <div class="error-code">&nbsp;</div>
                        <div class="error-message">Session Expired</div>
                        <div class="button-place">
                            <a href="login" class="btn bg-light-green btn-lg waves-effect">GO TO HOMEPAGE</a>
                        </div>
                    </div>
                </div>
                <!--</div>-->
            </s:else>          
        </s:else>
        <script type = "text/javascript">
            window.onload = function () {
//                OpenNonEditableWindow();
                if (window.IsDuplicate()) {

                    var usertype = '<s:property value="%{#session.logon}"/>';

                    if (usertype == "onlineUser") {                      
                        location.replace("http://localhost:8080/Nigeria_Tracker_V1/login");//local
                        //location.replace("http://10.10.10.212:8080/Nigeria_Tracker_V1/login");//local testing
                        //location.replace("https://pam.son.gov.ng/login");//live
                    } else {                       
                        location.replace("http://localhost:8080/Nigeria_Tracker_V1/admin");//local
                        //location.replace("http://10.10.10.212:8080/Nigeria_Tracker_V1/admin");//local testing
                        //location.replace("https://pam.son.gov.ng/admin");//live
                    }
                    // alert user the tab is duplicate

//                    location.replace("https://pam.son.gov.ng/");
//                     location.replace("http://10.10.10.212:8080/Nigeria_Tracker_V1/");

                    //                    window.open("http://localhost:8080/Nigeria_Tracker_V1/");
                    //alert("Window Already Opened in Another Tab\nPlease Click Ok");
                    // close the current tab
                    //                    window.top.close();
                }
            };

//            function OpenNonEditableWindow() {
//                window.open(
//                        'http://localhost:8080/Nigeria_Tracker_V1/admin',
//                        'AnyNameForNewWindow',
//                        'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=400,height=350'
//                        );
//            }

            //window.onload = function () {
            //  var myWindow;
            //myWindow = window.open(window.parent.location, "_blank");
            //myWindow = window.close(window.parent.location, "_blank");
            //  myWindow = window.close("http://localhost:8080/Nigeria_Tracker_V1/", "_blank");
            //window.history.pushState({}, document.title,"");
            //window.history.replaceState(null, null, window.location.href);
            //                var back = history.length;
            //                history.go(-back);
            //window.location.href = "http://localhost:8080/Nigeria_Tracker_V1/";
            //  };                        
        </script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-select/js/bootstrap-select.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/dropzone/dropzone.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-inputmask/jquery.inputmask.bundle.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/multi-select/js/jquery.multi-select.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-spinner/js/jquery.spinner.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/nouislider/nouislider.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/node-waves/waves.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/pages/forms/advanced-form-elements.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/demo.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-validation/jquery.validate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/sweetalert/sweetalert.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/pages/tables/jquery-datatable.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/jquery.dataTables.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/morrisjs/morris.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/raphael/raphael.min.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/momentjs/moment.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery-steps/jquery.steps.js"></script>
        <!--        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/slim.min.js"></script>-->
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/duplicate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/sessionStorage.js"></script>


    </body>
</html>
