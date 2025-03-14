<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<!-- META SECTION -->
<%--<title>BRTA - Change Password</title>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
<%--<meta http-equiv="X-UA-Compatible" content="IE=edge" />--%>
<%--<meta name="viewport" content="width=device-width, initial-scale=1" />--%>

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!-- END META SECTION -->

<!-- CSS INCLUDE -->
	<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%--	<link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.js">--%>
<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="js/plugins/sweetalert/sweetalert.min.js"></script>
<script type='text/javascript'
	src='js/plugins/jquery-validation/jquery.validate.js'></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/xml2json.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
function validateOnSubmit() {
	var oldemail = document.getElementById('userPwd').value;
	if(oldemail === ''){
		Swal.fire(
				'please provide old password'
		)
		return;
	}
	var newemail = document.getElementById('password').value;
	if(newemail === ''){
		Swal.fire(
				'please provide new password'
		)
		return;
	}

	var oldpass = document.getElementById('oldpass').value;
	if(oldpass === oldemail) {
	if (oldemail === newemail) {
		$("#password").focus();
		$("#password").val("");
		// alert("OLD PASSWORD AND NEW PASSWORD SHOULD NOT BE SAME.");
		Swal.fire({
			title: 'Error!',
			text: 'OLD PASSWORD AND NEW PASSWORD SHOULD NOT BE SAME.',
			confirmButtonText: 'OK'
		});
		return false;
	}
	
	var newpwd = document.getElementById('password').value;
	var cnfmpwd = document.getElementById('confirm').value;
	if (newpwd != cnfmpwd) {
		$("#confirm").focus();
		$("#confirm").val("");
		// alert("NEW PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME.");
		Swal.fire({
			title: 'Error!',
			text: 'NEW PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME.',
			confirmButtonText: 'OK'
		});
		return false;
	}
	}else {
		// alert("PLEASE CHECK OLD PASSWORD");
		Swal.fire({
			title: 'Error!',
			text: 'PLEASE CHECK OLD PASSWORD',
			confirmButtonText: 'OK'
		});
		return false;
	}
}

function cnfmpwdvalidCheck() {
	var newpwd = document.getElementById('password').value;
	var cnfmpwd = document.getElementById('confirm').value;
	if (newpwd != cnfmpwd) {
		$("#confirm").focus();
		$("#confirm").val("");
		// alert("NEW PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME.");
		Swal.fire({
			title: 'Error!',
			text: 'NEW PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME.',
			confirmButtonText: 'OK'
		});
		return false;
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
		for(var i = 0; i < regex.length; i++) {
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

		switch (passed){
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
			strength = "Excellent";
			color = "darkgreen";
			break;
		}

		password_strength.innerHTML = strength;
		password_strength.style.color = color;

	}

	function pwdvalidCheck(){
		var password_strength = document.getElementById("password_strength").innerHTML;
		if (password_strength === "Excellent" || password_strength === "Strong") {
		} else {
			// alert("Set valid password");
			Swal.fire({
				title: 'Warning!',
				text: "set valid password",
				confirmButtonText: 'OK'
			});
			document.getElementById("password").value = "";
			document.getElementById("password_strength").innerHTML = "";
			return false;
		}
	}
</script>

<%--<c:if test="${errorMessage != null}">--%>
<%--	<div class="alert alert-danger" role="alert" style="font-weight: bold;">--%>
<%--		<button type="button" class="close" data-dismiss="alert"--%>
<%--			aria-label="Close">--%>
<%--			<span aria-hidden="true">&times;</span>--%>
<%--		</button>--%>
<%--		${errorMessage}--%>
<%--	</div>--%>
<%--</c:if>--%>
<%--<c:if test="${successMessage != null}">--%>
<%--	<div class="alert alert-success" role="alert"--%>
<%--		style="font-weight: bold;">--%>
<%--		<button type="button" class="close" data-dismiss="alert"--%>
<%--			aria-label="Close">--%>
<%--			<span aria-hidden="true">&times;</span>--%>
<%--		</button>--%>
<%--		${successMessage}--%>
<%--	</div>--%>
<%--</c:if>--%>

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

<div class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1 class="m-0"></h1>
			</div>
			<!-- /.col -->
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="dashBoard">Home</a></li>
					<li class="breadcrumb-item active">Change Password v2</li>
				</ol>
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container-fluid    onload="preventBack();" onpageshow="if (event.persisted) preventBack();" onunload=" "-->
</div>
<%--page-content--%>
<section class="content"  >
	<div class=" container-fluid">
		<!-- /.row -->
		<div class="row">
			<div class="col-md-12">
				<div class="card card-primary">
					<div class="card-header">
						<h3 class="card-title">CHANGE PASSWORD</h3>
					</div>
					<!-- /.card-header -->
					<form:form method="post" action="updatePassword" id="form_validation"
							   data-form-validate="true" class="form-horizontal">
						<div class="card-body">
							<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-2">
									<label class="form-label"><strong>USER EMAIL</strong></label>
									<p>${userdetail.email}</p>
									<input type="hidden" name="userid" value="${userdetail.userid}" />

									<input type="hidden" name="oldpass" id="oldpass" value="${userdetail.verifycodePwd}">

								</div>
								<div class="col-md-2">
									<label class="form-label">USER NAME</label>
									<p
											style="overflow: hidden; max-width: 350px; word-wrap: break-word;">
											${userdetail.firstnameEn}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3"></div>

								<div class="col-md-4">
									<div class="form-group">
										<input type="password" class="form-control" id="userPwd"
											   name="userOldPassword" placeholder="Old Password" required/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3"></div>

								<div class="col-md-4">
									<div class="form-group">
										<input type="password" class="form-control" id="password"
											   title="Password must be 8 characters including 1 uppercase letter,1 lowercase letter,numeric characters and Special characters"
											   name="password" placeholder="New Password"
											   onkeyup="CheckPasswordStrength(this.value)"
											   onblur="pwdvalidCheck()" required/>
										<div style="color: #006a4d">Note: Password must have
											1 uppercase,1 lowercase,numeric characters
											and Special characters.</div>

									</div>
									<span id="password_strength"></span>
								</div>
							</div>

							<div class="row">
								<div class="col-md-3"></div>

								<div class="col-md-4">
									<div class="form-group">
										<input type="password" class="form-control" name="confirm"
											   placeholder="Confirm Password" id="confirm"
											   onchange="cnfmpwdvalidCheck()" required>
									</div>
								</div>
							</div>




						<div class="row">

<%--								<div class="col-md-12" >--%>

<%--									align="center"--%>
									<div class="col-md-3"></div>
	<div class="col-md-4">
		<button class="btn btn-primary float-left" type="reset"
		>CANCEL</button>
		<button class="btn btn-primary float-right" type="submit"
				onclick="return validateOnSubmit()">UPDATE PASSWORD</button>
	</div>



<%--							</div>--%>
						</div>

						</div>
					</form:form>
					<!-- /.card-body -->

				</div>
			</div>
		</div>

	</div>
</section>
</html>