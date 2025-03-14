<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
				<h1>Edit User </h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active">Edit User  Group</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>

<section class="content">
	<div class="container-fluid">
		<!-- SELECT2 EXAMPLE -->
		<form action="searchUserDetailsForEdit"
			  method="post" modelAttribute="mstRoleGroup">
			<div class="card card-primary">
				<div class="card-header">
					<h3 class="card-title">EDIT  GROUP </h3>


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

					</div>
					<div class="row">
						<div class=" col-sm-6">
							<div class="form-group">
								<label class="col-md-3 control-label">GROUP NAME </label>
								<div class="col-md-9">
									<select class="form-control"  id="groupName" name="groupName"
									<c:forEach items="${loadRoleGroup}" var="element1">
										<option value="${element1.groupName}">${element1.groupName}</option>
									</c:forEach>
									</select>
									<%--	<select path="mstRoleGroup.groupId" id="groupName"
                                                     class="form-control select required">
                                            <option value="" label="SELECT GROUP NAME" />
                                            <options items="${loadRoleGroup}" itemLabel="groupName"
                                                          itemValue="groupId" />
                                        </select>--%>

								</div>
							</div>
						</div>
					</div>


				</div>

			</div>


			<div class="card-footer">
				<button	onclick="return validateOnSubmit();" id="submit" class="btn btn-info">SEARCH</button>
				<button type="submit" class="btn btn-default float-right">Cancel</button>
			</div>


		</form>
	</div>
</section>

<script>
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
</html>