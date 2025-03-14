<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manual Verification System  Error </title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
    <!-- icheck bootstrap -->
    <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/adminlte.min.css">
    <script src="plugins/sweetalert2/sweetalert2.js"></script>
<%--    <link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.js">--%>

</head>


<body class="hold-transition login-page" style="margin-left: -200px; margin-top: -100px">
<div class="login-box">
    <!-- /.login-logo -->
    <div class="card card-outline card-primary" style="height: 300px; width: 500px;">
        <div class="card-header text-center">
            <a  class="h1"><b>Error</b></a>
        </div>
        <div class="card-body">
            <p class="login-box-msg">please go back to home page</p>

            <form action="login"  method="post">

                <div class="row" style="height: 50px"></div>

                <div class="row">
                    <div class="col-12" style="text-align: center">

                        <a href="dashBoard">Home</a>

                    </div>



                </div>
            </form>


        </div>
        <!-- /.card-body -->
    </div>
    <!-- /.card -->
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
</body>
</html>
