<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1> Edit User Form</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active">Edit User </li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>


<!-- Main content -->
<section class="content">
	<div class="container-fluid">
		<!-- SELECT2 EXAMPLE -->
		<form id="quickForm" action="editUser" method="POST" modelAttribute="mstRolesBean" id="validate">
			<div class="card card-primary">
				<div class="card-header">
					<h3 class="card-title">Edit User</h3>


					<div class="card-tools">
						<button type="button" class="btn btn-tool" data-card-widget="collapse">
							<i class="fas fa-minus"></i>
						</button>
						<button type="button" class="btn btn-tool" data-card-widget="remove">
							<i class="fas fa-times"></i>
						</button>
					</div>
				</div>
				<!-- /.card-header -->
				<div class="card-body">
					<div class="row">
						<input type="hidden" name="mstRoleGroup.groupId"
							   value="${groupId}" /> <input type="hidden"
															name="mstRoleGroup.groupName" value="${groupName}" />
						<div class="col-md-6">
							<div class="form-group">
								<label>First Name</label>
								<input type="text" class="form-control required" value="${userdetails.firstnameEn}"
									   name="userdetails.firstnameEn" id="firstname" onkeypress="return onlyAlphabets(event);"
									   onkeyup=" AllowSingleSpaceNotInFirstAndLast(this);autoCaps(this);"/>


							</div>
							<input type="hidden" name="userdetails.userid" id="userId"
								value="${userdetails.userid}" />

							<!-- /.form-group -->
							<div class="form-group">
								<label>Last Name</label>
								<input type="text" class="form-control required" value="${userdetails.lastnameEn}"
									   name="userdetails.lastnameEn" onkeypress="return onlyAlphabets(event);"
									   onkeyup="AllowSingleSpaceNotInFirstAndLast(this);autoCaps(this);"/>
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.col -->
						<div class="col-md-6">
							<div class="form-group">
								<label>Middle Name</label>
								<input type="text" class="form-control" value="${userdetails.middleName}"
									   name="userdetails.middleName" onkeypress="return onlyAlphabets(event);"
									   onkeyup="AllowSingleSpaceNotInFirstAndLast(this);autoCaps(this);"/>
							</div>
							<!-- /.form-group -->
							<div class="form-group">
								<label>Organization</label>
								<input type="text" class="form-control required" value="${userdetails.organisation}"
									   name="userdetails.organisation" onkeypress="return onlyAlphabets(event);"
									   onkeyup="AllowSingleSpaceNotInFirstAndLast(this);autoCaps(this);"/>
							</div>
							<!-- /.form-group -->
						</div>

						<div class="col-md-6">
							<div class="form-group">
								<label>Email</label>
								<input type="email" class="form-control required" value="${userdetails.email}"
									   name="userdetails.email" onkeyup="return spaceNotAllowed(this);"/>
							</div>

						</div>
						<div class="col-12 col-sm-6">
							<div class="form-group">
								<label>Mobile No</label>
								<input type="text" class="form-control required" value="${userdetails.contactnumber}"
									   name="userdetails.contactnumber" id="contactnumber" maxlength="11" minlength="11" onchange="onlyNum('contactnumber'); phoneValidate();"
									   onkeypress="return isNumber(event);"/>
							</div>

						</div>

						<div class="col-md-6">
							<div class="form-group">
								<label>Designation</label>
								<input type="text" class="form-control required" value="${userdetails.designation}"
									   name="userdetails.designation" id="designation" onkeypress="return onlyAlphabets(event);"
									   onkeyup="AllowSingleSpaceNotInFirstAndLast(this);autoCaps(this);"/>
							</div>

						</div>
						<%--<div class="col-sm-6">
							<div class="form-group">
								<label>Group</label>
								&lt;%&ndash;<select name="groupId" id="groupName"
										class="form-control select required">
									<option value="" />
									<options items="${loadRoleGroup}" itemLabel="groupName"
											 itemValue="groupId" />
								</select>&ndash;%&gt;
								<select  class="form-control" name="groupName"  id="groupId"
								<c:forEach items="${loadRoleGroup}" var="element1">
									<option value="${element1.groupId}">${element1.groupName}</option>
								</c:forEach>
								</select>
							</div>

						</div>--%>

					</div>

				</div>

				<div class="card-footer">
					<button type="submit" class="btn btn-info">Submit</button>
					<button type="submit" class="btn btn-default float-right">Cancel</button>
				</div>
			</div>

		</form>
	</div>
</section>


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

<!-- jQuery -->
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Select2 -->
<script src="../../plugins/select2/js/select2.full.min.js"></script>
<!-- Bootstrap4 Duallistbox -->
<script src="../../plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
<!-- InputMask -->
<script src="../../plugins/moment/moment.min.js"></script>
<script src="../../plugins/inputmask/jquery.inputmask.min.js"></script>
<!-- date-range-picker -->
<script src="../../plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="../../plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="../../plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Bootstrap Switch -->
<script src="../../plugins/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<!-- BS-Stepper -->
<script src="../../plugins/bs-stepper/js/bs-stepper.min.js"></script>
<!-- dropzonejs -->
<script src="../../plugins/dropzone/min/dropzone.min.js"></script>
<!-- AdminLTE App -->
<script src="../../dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../../dist/js/demo.js"></script>

<script type="text/javascript">
	function validateOnsubmit() {
		if ($('#validate').valid()) {
			document.getElementById("validate").action = "<%=request.getContextPath()%>/editUser";
			document.getElementById("validate").submit();
		}
		return false;
	}

	function isNumber(evt) {
		var iKeyCode = (evt.which) ? evt.which : evt.keyCode
		if (iKeyCode != 46 && iKeyCode > 31 && (iKeyCode < 48 || iKeyCode > 57))
			return false;

		return true;
	}

	function  phoneValidate() {
		var phone = document.getElementById("contactnumber").value;
		var userId = document.getElementById("userId").value;
		$.ajax({
			type : "GET",
			datatype : "json",
			url : "contactnumById/" + phone +"/" + userId,
			success : function(result) {
				if(result == phone){
					swal("Mobile number already exist");
					document.getElementById("contactnumber").value = "";
					return false;
				}
			}
		})
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

			if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || (e.keyCode == 8) || (e.keyCode == 9) || (charCode == 32)) {
				return true;
			} else {
				return false;
			}
		} catch (err) {
			alert(err.Description);
		}
	}

	function autoCaps(input) {
		var $th = $(input);
		$th.val($th.val().toLowerCase().replace(/\b[a-z]/g, function(letter) {
			return letter.toUpperCase();
		}))
	}

	function AllowSingleSpaceNotInFirstAndLast(input) {
		var $th = $(input);
		$th.val($th.val().replace(/(\s{2,})|[^a-zA-Z']/g, ' '));
		$th.val($th.val().replace(/^\s*/, ''));
	}

	function spaceNotAllowed(input) {
		var $th = $(input);
		$th.val($th.val().replace(/^\s*/, ''));
	}

	function onlyNum(id) {
		if (id == 'contactnumber') {
			var $this = $('#contactnumber');
			var mob =  $this.val();
			var ex = /\D/g;
			if(!mob.match(ex)) {
				document.getElementById("contactnumber").value = mob;
			}else {
				document.getElementById("contactnumber").value = "";
			}
		}
	}
</script>
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