<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Manual Verification System  Log in </title>

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

</head>

<script>
  function click(){

    alert("hi magesh");
      // var myHeaders = new Headers();
      // myHeaders.append("Content-Type", "application/json");
      //
      // var raw = JSON.stringify({"username":"TestUser","password":"pwd"});
      //
      // var requestOptions = {
      //     method: 'POST',
      //     headers: myHeaders,
      //     body: raw,
      //     redirect: 'follow'
      // };
  }

</script>
<%--<script>--%>
<%--  $().ready(function() {--%>
<%--    if(document.referrer != 'http://localhost:8181/'){--%>
<%--      history.pushState(null, null, 'login');--%>
<%--      window.addEventListener('popstate', function () {--%>
<%--        history.pushState(null, null, 'login');--%>
<%--      });--%>
<%--    }--%>
<%--  });--%>
<%--</script>--%>

<c:if test="${successMessage != null}">
  <script>
    Swal.fire({
      title: 'Success!',
      text: '${successMessage}',
      confirmButtonText: 'OK'
    });
  </script>
</c:if>
<%--<c:if test="${unAuthorization != null}">--%>
<%--    <script>--%>
<%--        Swal.fire({--%>
<%--            title: 'Info!',--%>
<%--            text: '${unAuthorization}',--%>
<%--            confirmButtonText: 'OK'--%>
<%--        });--%>
<%--    </script>--%>
<%--</c:if>--%>

<c:if test="${errorMessage != null}">
  <script>
    Swal.fire({
      title: 'Error!',
      text: '${errorMessage}',
      confirmButtonText: 'OK'
    });
  </script>
</c:if>
<c:if test="${setWindowName != null}">
<%--  alert('HI');--%>
  <script>
    window.name = '${setWindowName}';

  </script>
</c:if>
<body class="hold-transition login-page">
<div class="login-box" style="position: center">
  <!-- /.login-logo -->
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
      <a  class="h1"><b>Manual Verification System</b></a>
    </div>
    <div class="card-body">
      <p class="login-box-msg">Sign in to start your session</p>

      <form action="login"  method="post">
        <div class="input-group mb-3">
          <input type="text" class="form-control" name="username"  pattern="([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\.[A-Za-z0-9]+)+)" required placeholder="Email">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
        <div class="input-group mb-3">
          <input type="password" class="form-control"  name="password" required placeholder="Password">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-lock"></span>
            </div>
          </div>
        </div>
        <div class="row"  style="height: 35px;  " modelAttribute="invalidPass">
          <div class="input-group mb-3">
<%--       "   <c:choose>--%>
<%--            <c:when test="${not empty invalidPass}"><p style="margin-left: 6px;">${invalidPass}</p></c:when>--%>
<%--         " </c:choose></div>--%>

        </div>

        <div class="row">
          <div class="col-8">

            <a href="forgotPasswordDetails">Forgot password</a>
<%--            <div class="icheck-primary">--%>
<%--              <input type="checkbox" id="remember">--%>
<%--              <label for="remember">--%>
<%--                Remember Me--%>
<%--              </label>--%>
<%--            </div>--%>
          </div>
          <!-- /.col -->
          <div class="col-4">
            <button type="submit" class="btn btn-primary btn-block" style="width: 99px;margin-left: 40px;">Sign In</button>

          </div>
          <!-- /.col -->
        </div>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>


      <!-- <div class="social-auth-links text-center mt-2 mb-3">
        <a href="#" class="btn btn-block btn-primary">
          <i class="fab fa-facebook mr-2"></i> Sign in using Facebook
        </a>
        <a href="#" class="btn btn-block btn-danger">
          <i class="fab fa-google-plus mr-2"></i> Sign in using Google+
        </a>
      </div> -->
      <!-- /.social-auth-links -->

<%--      <p class="mb-1">--%>
<%--        <a href="forgot-password.html">I forgot my password</a>--%>
<%--      </p>--%>
<%--      <p class="mb-0">--%>
<%--        <a href="register.html" class="text-center">Register a new membership</a>--%>
<%--      </p>--%>
    </div>
    <!-- /.card-body -->
  </div>
  <!-- /.card -->
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="../../dist/js/adminlte.min.js"></script>
</body>
</html>
