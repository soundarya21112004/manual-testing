<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>
<script type="text/javascript"
		src="js/plugins/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript"
		src="js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript"
		src="js/plugins/bootstrap/bootstrap-timepicker.min.js"></script>
<script>
	$(document).ready(function() {
		$('.datepicker').datepicker({
			format : 'mm/dd/yyyy',
			startDate : '0d',
			autoclose: true,
		});
		$('.timepicker').timepicker({

			min: '8:00 AM', max: '8:00 PM'
		});

	});

	function checkSelectedUser() {

		var checkedboxes = $('input:checkbox:checked').length //get count of checked list
		if (checkedboxes !== 0) {

		} else {
			swal("PLEASE SELECT APPLICANT.");
			return false;
		}

		var dateofschedule = document.getElementById('dateofschedule').value;
		if (dateofschedule == '') {
			swal("PLEASE SELECT SCHEDULE DATE");
			return false;
		}
		var timeofschedule = document.getElementById('timeData').value;
		if (timeofschedule == '') {
			swal("PLEASE SELECT SCHEDULE TIME");
			return false;
		}
	}
</script>
<script type="text/javascript">
	function diableKeypad(e)
	{
		$(e).keydown(function(event) {
			return false;
		});
		var regex= /[^a-z]/gi;
		e.value = e.value.replace(regex,"");
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

<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1> Edit User Form</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active"> User </li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>

<section class="content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-primary">
					<div class="card-header">
						<h3 class="card-title">List Of Users</h3>
					</div>
					<!-- /.card-header -->
              <form:form modelAttribute="exam">
					<div class="card-body">
						<table class="table table-bordered">
							<thead>
							<tr>
								<th>Sno </th>
								<th>User Id </th>
								<th>Applicant Name </th>

								<th>Email</th>
								<th>Created Date</th>
								<th>User Status </th>
								<th>Process </th>
							</tr>
							</thead>
							<tbody>
 <c:forEach items="${userDetails}" var="element" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${element.userid}</td>
								<td>${element.firstnameEn}</td>
								<td>${element.email}</td>
								<td><fmt:formatDate pattern="dd-MMM-yyyy"
													value="${element.enteredDate}" /></td>
								<td><c:if test="${element.activestatus eq '1'}">
									<font color="green">Active </font></c:if>
									<c:if test="${element.activestatus ne '1'}">
										<font color="red">InActive </font></c:if>
								</td>
								<td> <a href="<c:url value="userDetailsEdit"><c:param name="userId" value="${element.userid}"></c:param>
										</c:url>">  <i class="nav-icon fas fa-edit" aria-hidden="true"></i></a></td>

								<!-- <i class="fa fa-times" aria-hidden="true"></i><i class="fa fa-plus" aria-hidden="true"></i> -->


							</tr>
</c:forEach>


							</tbody>
						</table>
					</div>
</form:form>
					<!-- /.card-body -->
					<div class="card-footer clearfix">
						<ul class="pagination pagination-sm m-0 float-right">
							<li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
							<li class="page-item"><a class="page-link" href="#">1</a></li>
							<li class="page-item"><a class="page-link" href="#">2</a></li>
							<li class="page-item"><a class="page-link" href="#">3</a></li>
							<li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- jsGrid -->
<script src="../../plugins/jsgrid/demos/db.js"></script>
<script src="../../plugins/jsgrid/jsgrid.min.js"></script>
<!-- AdminLTE App -->
<script src="../../dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../../dist/js/demo.js"></script>
<script>

</script>