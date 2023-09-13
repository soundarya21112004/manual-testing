<!DOCTYPE html>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/pages/forms/form-validation.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/pages/forms/form-wizard.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/new/jquery.plugin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>



<script>
    $(document).ready(function () {
        $(".credit-card").inputmask('99999999-9999', {placeholder: '        -    '});
    });


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


<s:if test="successMessage neq null">
    <div class="alert bg-green alert-dismissible" role="alert" style="font-weight: bold;">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <s:property value="successMessage"/>
    </div>
</s:if>
<s:if test="errorMessage neq null">
    <div class="alert bg-pink alert-dismissible" role="alert" style="font-weight: bold;">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <s:property value="errorMessage"/>
        &nbsp;
        <s:if test="anotherUserLogin eq 'true'">
            <s:if test="logon eq 'staff'">
                <div align="center">
                    <u>  <a style="color: white" href="logout">Start New Session</a></u>
                </div>
            </s:if>
            <s:else>
                <div align="center">
                    <u>  <a style="color: white" href="logoutMfg">Start New Session</a></u>
                </div>
            </s:else>
        </s:if>

    </div>
</s:if>
<html>
    <!--<body class="login-page">-->
    <div class="login-box">
        <div class="card" style="border-radius: 20px;">
            <div class=""  style="text-align: center">
                <img src="<%=request.getContextPath()%>/images/sonproduct.png" alt="Nigeria" style="text-align: center">
            </div>
            <h4 class="msg">SIGN IN TO CONTINUE</h4>
            <h5 class="msg">APPLICANT LOG IN</h5>
            <div class="body">
                <s:form id="sign_in" method="POST" name="indexform" action="loginAction"   theme="simple">

                    <s:hidden name="logon" value="onlineUser"/>
                    <div id="onlineUserDiv">
                        <label class="form-label">FIRS TIN</label>
                        <div class="input-group">
                            <!--                            <span class="input-group-addon">
                                                            <i class="material-icons">border_color</i>
                                                        </span>-->

                            <input  name="tinNo" id="tinNo" type="text"  class="form-control credit-card" 
                                    onblur="return checkLengthTinFirst(this.value);"  required  >
                            <s:fielderror fieldName="tinNo" />


                        </div>
                        <label class="form-label">PASSWORD</label>
                        <div class="input-group">
                            <!--                            <span class="input-group-addon">
                                                            <i class="material-icons">lock</i>
                                                        </span>-->
                            <div class="form-line">
                                <input type="password" class="form-control" oncopy="return false" onpaste="return false" id="userPwd"  name="userPassword" 
                                       required>
                                <s:fielderror fieldName="userPassword" />
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                            <a href="forgotPasswordLink">Forgot Password?</a>
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-green waves-effect" type="submit">LOG IN</button>
                        </div>
                    </div>



                    <div class="row m-t-15 m-b--20">
                        <div class="col-xs-6">
                            <a href="companyRegistration">Register Now</a>
                        </div>
                        <div class="col-xs-6 align-right">
                            <a href="/NIGERIA_MOBILEAPP" target="_blank">Online Verification</a>
                        </div>
                    </div>

                </s:form>
            </div>
        </div>
    </div>

    <!-- Jquery Core Js -->
    <script src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="<%=request.getContextPath()%>/plugins/node-waves/waves.js"></script>

    <!-- Validation Plugin Js -->
    <script src="<%=request.getContextPath()%>/plugins/jquery-validation/jquery.validate.js"></script>

    <!-- Custom Js -->
    <script src="<%=request.getContextPath()%>/js/admin.js"></script>
    <script src="<%=request.getContextPath()%>/js/pages/examples/sign-in.js"></script>
    <!--</body>-->

</html>