
<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<%--<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">--%>

<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

<script>
	$(document).ready(function() {
		$('#example').DataTable({
			"bSort": false,
			"paging": true
		});
	} );
</script>
<script>
	function validate() {

		$('#quickForm').validate({
			rules : {
				rid : {
					required : true
				}
			},
			messages : {
				rid : {
					required : "Please provide FileName"
				}
			},
			errorElement : 'span',
			errorPlacement : function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight : function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight : function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
		if ($('#quickForm').validate()) {
			var filename = document.getElementById("filename").value;
			if (filename.length != "" && filename.length != 29) {
				Swal.fire("Invalid File Name");
				return false;
			}

		}

	}
</script>
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6"></div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="dashBoard">Home</a></li>
					<li class="breadcrumb-item active">Supervisor Search</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>

<c:if test="${successMessage != null}">
<script>
	Swal.fire({
		title : 'Success!',
		text : '${successMessage}',
		confirmButtonText : 'OK'
	});
</script>
</c:if>
<c:if test="${faliureMessage != null}">
<script>
	Swal.fire({
		title : 'Faliure!',
		text : '${faliureMessage}',
		confirmButtonText : 'OK'
	});
</script>
</c:if>
<section class="content">
	<div class="container-fluid">

		<!-- /.row -->
		<div class="row">
			<div class="col-md-12">
				<div class="card card-primary">
					<div class="card-header">
						<h3 class="card-title">List Of Subject</h3>
					</div>
					<!-- /.card-header -->
					<form:form modelAttribute="galleryList">
						<div class="card-body">
							<table id="example" class="table table-bordered table-hover">
								<thead>
								<tr>
									<th>Sno </th>
									<th>Registration Id</th>
									<th>Candidate Reference Id</th>
									<th>Reason</th>
									<th>Verified By Operator</th>
									<th>Verified By Operator</th>
									<th>Verified Date</th>
									<th>Process</th>


								</tr>
								</thead>
								<tbody>
								<c:forEach var="emp" items="${galleryList}" varStatus="counter">

									<tr>

										<td>${counter.count}</td>
										<td>${emp.regId}</td>
										<td>${emp.matchedRefId} </td>
										<td>Biometric Potential Match</td>
										<td>${emp.op1UpdBy}</td>
										<td>${emp.op2UpdBy} </td>
										<td>
											<fmt:formatDate value="${emp.op2UpdatedDate}" type="date" pattern="dd-MMM-yyyy"/></td>


										<td> <a href="<c:url value="leveltwoSearchByName">
<c:param name="id" value="${emp.sno}"></c:param>
<c:param name="probe" value="${emp.regId}"></c:param>
<c:param name="candidate" value="${emp.matchedRefId}"></c:param>
<c:param name="requestId" value="${emp.reqid}"></c:param>
<c:param name="op1Comment" value="${emp.op1Comment}"></c:param>
<c:param name="op2Comment" value="${emp.op2Comment}"></c:param>

<c:param name="op1verifyStatus" value="${emp.op1verifyStatus}"></c:param>
<c:param name="op2verifyStatus" value="${emp.op2verifyStatus}"></c:param>

			</c:url>">  <i class="nav-icon fas fa-edit" aria-hidden="true"></i></a></td>

										<!-- <i class="fa fa-times" aria-hidden="true"></i><i class="fa fa-plus" aria-hidden="true"></i> -->


									</tr>
								</c:forEach>


								</tbody>
							</table>
						</div>
					</form:form>
					<!-- /.card-body -->
<%--					<div class="card-footer clearfix">--%>
<%--						<ul class="pagination pagination-sm m-0 float-right">--%>
<%--							<li class="page-item"><a class="page-link" href="#">&laquo;</a></li>--%>
<%--							<li class="page-item"><a class="page-link" href="#">1</a></li>--%>
<%--							<li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--							<li class="page-item"><a class="page-link" href="#">3</a></li>--%>
<%--							<li class="page-item"><a class="page-link" href="#">&raquo;</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
				</div>
			</div>
		</div>

	</div>
	<!-- /.container-fluid -->
</section>

