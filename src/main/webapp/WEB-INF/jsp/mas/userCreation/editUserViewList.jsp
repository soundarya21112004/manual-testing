<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
	$(document).ready(function () {
		var val = document.getElementById("groupId").value;
		$.ajax({
			type : "GET",
			datatype : "json",
			url : "loadEditUserGroup/" + val,
			success : function(data) {
				$('#selectedRole').html('');
				$.each(${allRoles1}, function(i1, record1) {
					$.each(data, function(i, record) {
						if(record1 == record){
							$('input[value='+record1+']').prop("checked", true);
						}
					});
				});
			},
			error: function () {
				alert("Something went wrong! Please try later");
			}
		});
		return true;
	});
</script>

<script>
	function selectAllcheck() {

		if ($("#CheckAll").is(":checked")) {
			$('input[id=selectedRole]').prop("checked", true);
		} else {
			$('in<script>
			$(document).ready(function () {
				var val = document.getElementById("groupId").value;
				$.ajax({
					type : "GET",
					datatype : "json",
					url : "loadEditUserGroup/" + val,
					success : function(data) {
						$('#selectedRole').html('');
						$.each(${allRoles1}, function(i1, record1) {
							$.each(data, function(i, record) {
								if(record1 == record){
									$('input[value='+record1+']').prop("checked", true);
								}
							});
						});
					},
					error: function () {
						alert("Something went wrong! Please try later");
					}
				});
				return true;
			});
</script>

<script>
	function selectAllcheck() {

		if ($("#CheckAll").is(":checked")) {
			$('input[id=selectedRole]').prop("checked", true);
		} else {
			$('input[id=selectedRole]').prop("checked", false);
		}
	}

	function checkSelectedUser() {

		var flag = false;
		var checkedValue = document.getElementsByName("selectedRoleGroups").length;
		for (var i = 0; i < checkedValue; i++) {
			var valu = document.getElementsByName("selectedRoleGroups")[i];
			var element = $(valu);
			if (element.is(':checked')) {
				flag = true;
			}
		}
		if (!flag) {
			swal("PLEASE SELECT AT LEAST ONE ROLE ");
			return false;
		}
		document.getElementById("validate").action = "<%=request.getContextPath()%>/updateUserGroupEdit";
		document.getElementById("validate").submit();

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

<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>Edit User View List</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active"> Edit User  List</li>
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

			<form  action="updateUserGroupEdit" method="POST">
			<div class="card card-primary">
				<div class="card-header">
					<h3 class="card-title">Edit User Group</h3>


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
						<input type="hidden" name="mstRoleGroup.groupId" id="groupId"
							   value="${groupId}" />
						<div class="col-md-6">
							<div class="form-group">
								<label>GROUP NAME</label>
								<input type="text" class="form-control required"
									   id="newGroupName"
									   value="${groupName}" readonly="readonly" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class=" col-sm-6">
							<div class="form-group">
								<label>Select Role</label>
							<ul>

									<select class="form-control" multiple name="selectedRoleGroups" id="selectedRoleGroups" value="${element1.roleid}">
	           <c:forEach items="${allRoles}" var="element1" varStatus="status1">


		    	<option value="${element1.roleid}">${element1.roleDetails}</option>

									</c:forEach>

									</select>
								</ul>
							</div>
						</div>
					</div>
					<!-- /.col -->





				</div>

			</div>


			<div class="card-footer">
				<button type="submit" class="btn btn-info" onclick="$('#validate').valid(); checkSelectedUser()"
                id="createButton">Submit</button>
				<button type="submit" class="btn btn-info">Submit</button>
				<button type="submit" class="btn btn-default float-right">Cancel</button>
			</div>


		</form>
	</div>
</section>

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
<script>
	$(document).ready(function () {
		var val = document.getElementById("groupId").value;
		$.ajax({
			type : "GET",
			datatype : "json",
			url : "loadEditUserGroup/" + val,
			success : function(data) {
				$('#selectedRole').html('');
				$.each(${allRoles1}, function(i1, record1) {
					$.each(data, function(i, record) {
						if(record1 == record){
							$('input[value='+record1+']').prop("checked", true);
						}
					});
				});
			},
			error: function () {
				alert("Something went wrong! Please try later");
			}
		});
		return true;
	});
</script>

<script>
	function selectAllcheck() {

		if ($("#CheckAll").is(":checked")) {
			$('input[id=selectedRole]').prop("checked", true);
		} else {
			$('input[id=selectedRole]').prop("checked", false);
		}
	}

	function checkSelectedUser() {

		var flag = false;
		var checkedValue = document.getElementsByName("selectedRoleGroups").length;
		for (var i = 0; i < checkedValue; i++) {
			var valu = document.getElementsByName("selectedRoleGroups")[i];
			var element = $(valu);
			if (element.is(':checked')) {
				flag = true;
			}
		}
		if (!flag) {
			swal("PLEASE SELECT ATLEAST ONE ROLE ");
			return false;
		}
		document.getElementById("validate").action = "<%=request.getContextPath()%>/updateUserGroupEdit";
		document.getElementById("validate").submit();

	}
</script>
