
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>




<%--<script type="text/javascript" src="<%=request.getContextPath()%>/js/pages/forms/form-validation.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/sweetalert/sweetalert.min.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/js/pages/forms/form-wizard.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/js/new/jquery.plugin.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>--%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manual Verification System  Firsyt Login Password </title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
    <!-- icheck bootstrap -->
    <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/adminlte.min.css">
    <script src="plugins/sweetalert2/sweetalert2.js"></script>
    <link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.js">
<%--    <link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.css">--%>
<%--    <script src="sweetalert2.min.js"></script>--%>
<%--    <link rel="stylesheet" href="sweetalert2.min.css">--%>

</head>

<script>
    function click(){
        console.log('hi');

        window.alert('hi magesh');
        Swal.fire(
            'The Internet?',
            'That thing is still around?',
            'question'
        )
    }

</script>

<script>
    // $(document).ready(function () {
    //     $(".credit-card").inputmask('99999999-9999', {placeholder: '        -    '});
//        $("#onlineUserDiv").hide();
//        document.getElementById("staff").checked = true;
//
//        $("input[type='radio']").change(function(e) {
//
//            var staffStatus = document.getElementById("staff").checked;
//            if (staffStatus) {
//                $("#staffDiv").show();
//                $("#onlineUserDiv").hide();
//                document.getElementById("staffEmail").value = "";
//                document.getElementById("staffPwd").value = "";
//
//
//            } else {
//                $("#onlineUserDiv").show();
//                $("#staffDiv").hide();
//                document.getElementById("tinNo").value = "";
//                document.getElementById("userPwd").value = "";
//            }
//        });


    // });
    function checkLengthTinFirst(value) {
        for (var i = 0; i < 13; i++) {
            if (value[i] == "_") {
                alert("INVALID FIRS TIN")
                document.getElementById('tinNo').value = "";
                return false;
            }
        }

    }
</script>

<script type="text/javascript">
    function CheckPasswordStrength(password) {
        var password_strength = document.getElementById("password_strength");
        //TextBox left blank.
        if (password.length == 0) {
            password_strength.innerHTML = "";
            return;
        }

        //Regular Expressions.
        var regex = new Array();
        regex.push("[A-Z]"); //Uppercase Alphabet.
        regex.push("[a-z]"); //Lowercase Alphabet.
        regex.push("[0-9]"); //Digit.
        regex.push("[$@$!%*#?&]"); //Special Character.

        var passed = 0;
        //Validate for each Regular Expression.
        for (var i = 0; i < regex.length; i++) {
            if (new RegExp(regex[i]).test(password)) {
                passed++;
            }
        }

        //Validate for length of Password.
        if (passed > 2 && password.length >= 8) {
            passed++;
        }

        //Display status.
        var color = "";
        var strength = "";
        switch (passed) {
            case 0:
            case 1:
                strength = "Weak";
                color = "red";
                break;
            case 2:
                strength = "Good";
                color = "darkorange";
                break;
            case 3:
            case 4:
                strength = "Strong";
                color = "green";
                break;
            case 5:
                strength = "Very Strong";
                color = "darkgreen";
                break;
        }
        password_strength.innerHTML = strength;
        password_strength.style.color = color;
    }


    function pwdvalidCheck() {
        var password_strength = document.getElementById("password_strength").innerHTML;
        if (password_strength === "Very Strong") {

        } else {
            alert("Set valid password");
            document.getElementById("password").value = "";
            document.getElementById("password_strength").innerHTML = "";
            return false;
        }
    }
</script>

<script>
    function validateOnsubmit() {

        if ($('#validate').valid()) {

            swal({
                title: 'Are you sure?',
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#006a4d",
                confirmButtonText: "Yes, Confirm!",
                closeOnConfirm: false,
                showLoaderOnConfirm: true
            }, function () {
                setTimeout(function () {
                    if (cnfmpwdvalidCheck()){
                        alert("if...last")
                        <%--document.getElementById("validate").action = "<%=request.getContextPath()%>/forgotPasswordController";--%>
                        <%--document.getElementById("validate").submit();--%>
                    } else {
                        alert('else..')
                    }
                }, 1000);
            });
            return false;
        }
        alert("validate submit");
    }

    function spaceNotAllowed(input) {
        var $th = $(input);
        $th.val($th.val().replace(/^\s*/, ''));
    }

    function onlyAlphabets(e) {
        try {
            if (window.event) {
                var charCode = window.event.keyCode;
            } else if (e) {
                var charCode = e.which;
            } else {
                return true;
            }

            if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || (e.keyCode == 8) || (e.keyCode == 9)) {
                return true;
            } else {
                return false;
            }
        } catch (err) {
            alert(err.Description);
        }
    }
</script>

<script>

    function cnfmpwdvalidCheck() {
        var newpwd = document.getElementById('password').value;
        var cnfmpwd = document.getElementById('confirm').value;
        if (newpwd != cnfmpwd) {
            // alert('if');
            // $("#confirm").focus();
            // $("#confirm").val("");
            // alert("NEW PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME.");
            Swal.fire({
                title: 'Error!',
                text: 'NEW PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME.',
                confirmButtonText: 'OK'
            });
            return false;
        }else {
            // alert("else")
            document.getElementById("sign_in").action = "<%=request.getContextPath()%>/forgotPasswordController";
            document.getElementById("sign_in").submit();
            return true;
        }
    }
</script>

<c:if test="${successMessage != null}">
<script>
    Swal.fire({
        title: 'Success!',
        text: '${successMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${errorMessage != null}">
<script>
    Swal.fire({
        title: 'Error!',
        text: '${errorMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<body class="hold-transition login-page">


<!--<body class="login-page">-->
<div class="login-box">

    <%--        <div class="logo">--%>
    <%--            <img src="<%=request.getContextPath()%>/header-logo2.png" alt="Nigeria" width="100%">--%>
    <%--            <!--<small>Admin BootStrap Based - Material Design</small>-->--%>
    <%--     style="border-radius: 20px;"  style="color: #01a372"   </div>  sign_in--%>
    <div class="card card-outline card-primary">
        <div class="card-header text-center">
            <a  class="h1"><b>Manual Verification System</b></a>
        </div>
        <div class="body" >
<%--            action="forgotPasswordController"--%>
            <p class="login-box-msg">  Kindly change your password </p>
            <form id="sign_in" method="post" name="indexform"  theme="simple">

                <%--                        <div class="login-box-msg" >--%>
                <%--                            Kindly change your password for security reason.--%>
                <%--                        </div>--%>


                <s:hidden name="usernameID" value="%{usernameID}"/>
                <s:hidden name="logon" value="%{logon}"/>

                <%--<s:property value="usernameID" />--%>
                <%--<s:property value="logon" />--%>

                <div id="onlineUserDiv">
                    <div class="input-group mb-3">
                        <%--                            <span class="input-group-addon">--%>
                        <%--                                <i class="material-icons">lock</i>--%>
                        <%--                            </span>--%>
                        <%--    <span class="fas fa-lock"></span>     onkeypress="return onlyAlphabets(event);" autocomplete="nope"--%>

                            <input type="email"  style= "margin-left:20px"  class="form-control" pattern="[a-z0-9._%+-]+@[a-z0-9-]{3,}+\.[a-z]{2,}$" required name="email"

                                    placeholder="Email">
                            <div class="input-group-append " style= "margin-right:20px">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>

                    </div>
                    <div class="input-group mb-3">
                        <%--                            <span class="input-group-addon">                   --%>
                        <%--                                <i class="material-icons">lock</i>--%>
                        <%--                            </span>--%>
                        <%--    <span class="fas fa-lock"></span>--%>

                        <input type="password" style= "margin-left:20px" class="form-control" id="password"

                               title="Password must be 8 characters "
                               name="password" placeholder="New Password"  required
                               onkeyup="CheckPasswordStrength(this.value)"
                               onblur="pwdvalidCheck()" >
                        <div class="input-group-append"  style= "margin-right:20px">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                        <%--                                 <div style="color:black">--%>
                        <%--                              Note: Password must have 8 characters--%>
                        <%--                                </div>--%>



                    </div>
                    <div class="input-group mb-3">
                        <span style="margin-left: 20px" id="password_strength"></span>
                    </div>
                    <div class="input-group mb-3">
                        <%--                            <span class="input-group-addon">--%>
                        <%--                                <i class="material-icons">lock</i>--%>
                        <%--                            </span>--%>
                        <%--    <span class="fas fa-lock"></span>--%>

                        <input type="password" style= "margin-left:20px" class="form-control" id="confirm"
                               name="confirm" placeholder="Confirm Password"  required>
                        <div class="input-group-append" style= "margin-right:20px">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>

                    </div>
<%--                    <div class="input-group mb-3">--%>
<%--                        <c:if test="${not empty errorMessage}" >--%>
<%--                        <span style="margin-left: 20px;">${errorMessage}</span>--%>
<%--                        </c:if>--%>
<%--                    </div>--%>

                </div>
                <div class="row">
                    <div class="col-1"></div>
                    <div class="col-4">
                       <a href="loginPage"> <button class="btn btn-primary btn-block" style= "margin-right:20px" type="button" >CANCEL</button></a>
                    </div>
                    <div class="col-2"></div>
                    <div class="col-4">
                        <button class="btn btn-primary btn-block"   style= "margin-right:20px" type="submit" onclick="return cnfmpwdvalidCheck()" >SUBMIT</button>

                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>

<%--<!-- Jquery Core Js -->--%>
<%--<script src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script> onclick="validateOnsubmit();" id="validate"--%>

<%--<!-- Bootstrap Core Js -->--%>
<%--<script src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.js"></script>--%>

<%--<!-- Waves Effect Plugin Js -->--%>
<%--<script src="<%=request.getContextPath()%>/plugins/node-waves/waves.js"></script>--%>

<%--<!-- Validation Plugin Js -->--%>
<%--<script src="<%=request.getContextPath()%>/plugins/jquery-validation/jquery.validate.js"></script>--%>

<%--<!-- Custom Js -->--%>
<%--<script src="<%=request.getContextPath()%>/js/admin.js"></script>--%>
<%--<script src="<%=request.getContextPath()%>/js/pages/examples/sign-in.js"></script>--%>
<%--<!--</body>-->--%>

