<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> --%>


<head>
<!-- META SECTION -->
<title>BRTA- New Registration</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!-- END META SECTION -->

<!-- CSS INCLUDE -->
<link rel="stylesheet" type="text/css" id="theme"
	href="css/theme-default.css" />
<!-- EOF CSS INCLUDE -->
<!-- START PLUGINS -->
<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- END PLUGINS -->

<script type="text/javascript">
	function validateOnSubmit() {
		var utype = document.getElementById("groupName").value;
		if (utype !== '') {
		} else {
			$("#groupName").focus();
			swal('PLEASE SELECT GROUP NAME');
			return false;
		}

	}
</script>
</head>
<c:if test="${successMessage != null}">
	<script>
		swal({
			title : '',
			confirmButtonColor : '#006a4d',
			text : '${successMessage}',
			type : 'success'
		});
	</script>
</c:if>
<c:if test="${errorMessage != null}">
	<script>
		swal({
			title : 'Sorry!',
			confirmButtonColor : '#006a4d',
			text : '${errorMessage}',
			type : 'error'
		});
	</script>
</c:if>
<body>



	<div class="page-content">

		<div class="row">
			<div class="col-md-12">

				<form:form class="form-horizontal" action="searchRoleDetails"
					method="post" modelAttribute="mstRoleGroup">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								<strong>USER CREATION / ব্যবহারকারী ক্রিয়েশন</strong>
							</h3>
						</div>
						<div class="panel-body">

							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="col-md-3 control-label">GROUP NAME / দলের নাম</label>
										<div class="col-md-9">
											<form:select path="groupId" id="groupName"
												class="form-control select required">
												<form:option value="" label="SELECT GROUP NAME" />
												<form:options items="${loadRoleGroup}" itemLabel="groupName"
													itemValue="groupId" />
											</form:select>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<button class="btn btn-primary pull-left"
										onclick="return validateOnSubmit();" id="submit">Submit / জমা দিন
									</button>
								</div>
							</div>
						</div>

					</div>
				</form:form>

			</div>
		</div>


	</div>

</body>
</html>






