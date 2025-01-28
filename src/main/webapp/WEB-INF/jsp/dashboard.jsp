<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--<c:if test="${setWindowName != null}">--%>
<%--	<script>--%>
<%--		window.name = '${setWindowName}';--%>
<%--		alert('HI');--%>
<%--	</script>--%>
<%--</c:if>--%>
<script>
	window.name = 'appname';
</script>
<script>
	window.onload = function() {
		checkwindow();
	}
	function checkwindow(){
		if(window.name != 'appname')
			// window.location = "/MVS/loginPage";
			window.location = "<c:url value='loginPage'/>";
	}
</script>

<div class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1 class="m-0">Dashboard</h1>
			</div>
			<!-- /.col -->
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active">Dashboard</li>
				</ol>
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container-fluid    onload="preventBack();" onpageshow="if (event.persisted) preventBack();" onunload=" "-->
</div>

<section class="content"  >
	<div class="container-fluid" modelAttribute="numberofCount">
<%--<c:choose>--%>
<%--	<c:when test="${not empty operatorLogin}">--%>
<%--		<div class="container">--%>
<%--			<div class="row mb-3">--%>
<%--				<div class="col-md-2"></div>--%>
<%--				<div class="col-md-4 ">--%>
<%--					<img class="img-adjust" style="" src="${pageContext.servletContext.contextPath}/philippines_logo.jpg">--%>
<%--				</div>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--	</c:when>--%>
<%--	<c:otherwise>--%>



		<!-- Info boxes -->
		<div class="row">
<%--			<div class="col-12 col-sm-6 col-md-3">--%>
<%--				<div class="info-box">--%>
<%--					<span class="info-box-icon bg-info elevation-1"><i--%>
<%--						class="fas fa-cog"></i></span>--%>

<%--					<div class="info-box-content">--%>
<%--						<span class="info-box-text">CPU Traffic</span> <span--%>
<%--							class="info-box-number"> 10 <small>%</small>--%>
<%--						</span>--%>
<%--					</div>--%>
<%--					<!-- /.info-box-content -->--%>
<%--				</div>--%>
				<!-- /.info-box -->
<%--			</div>--%>
			<!-- /.col -->
			<div class="col-12 col-sm-6 col-md-4">
				<div class="info-box mb-3">
					<span class="info-box-icon bg-success elevation-1"><i
						class="fas fa-thumbs-up"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">Hits</span> <span
							class="info-box-number">${numberofCount}</span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->

			<!-- fix for small devices only -->
			<div class="clearfix hidden-md-up"></div>

			<div class="col-12 col-sm-6 col-md-4">
				<div class="info-box mb-3">
					<span class="info-box-icon bg-danger elevation-1"><i
						class="fas fa-thumbs-up"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">No Hits</span> <span
							class="info-box-number">${numberofnohitCount}</span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->
			<div class="col-12 col-sm-6 col-md-4">
				<div class="info-box mb-3">
					<span class="info-box-icon bg-warning elevation-1"><i
						class="fas fa-users"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">Pending Records</span> <span
							class="info-box-number">${numberofRecords}</span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->

		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						<h5 class="card-title">Monthly Recap Report</h5>

						<div class="card-tools">
							<button type="button" class="btn btn-tool"
								data-card-widget="collapse">
								<i class="fas fa-minus"></i>
							</button>
							<div class="btn-group">
								<button type="button" class="btn btn-tool dropdown-toggle"
									data-toggle="dropdown">
									<i class="fas fa-wrench"></i>
								</button>
								<div class="dropdown-menu dropdown-menu-right" role="menu">
									<a href="#" class="dropdown-item">Action</a> <a href="#"
										class="dropdown-item">Another action</a> <a href="#"
										class="dropdown-item">Something else here</a> <a
										class="dropdown-divider"></a> <a href="#"
										class="dropdown-item">Separated link</a>
								</div>
							</div>
							<button type="button" class="btn btn-tool"
								data-card-widget="remove">
								<i class="fas fa-times"></i>
							</button>
						</div>
					</div>
					<!-- /.card-header -->
					<div class="card-body">
						<div class="row">
<%--							<div class="col-md-8">--%>
<%--								<p class="text-center">--%>
<%--									<strong>Hits 1 Jan, 2020 - 30 Jul, 2020</strong>--%>
<%--								</p>--%>

<%--								<div class="chart">--%>
<%--									<!-- Sales Chart Canvas -->--%>
<%--									<canvas id="salesChart" height="180" style="height: 180px;"></canvas>--%>
<%--								</div>--%>
<%--								<!-- /.chart-responsive -->--%>
<%--							</div>--%>
							<!-- /.col -->
	<div class="col-md-1"></div>
							<div class="col-md-10">
								<p class="text-center">
									<strong>Goal Completion</strong>
								</p>

								<div class="progress-group">
									Total Hits Till Date<span class="float-right"><b>${numberofCount}</b>/${totalRecords}</span>
									<div class="progress progress-sm">
										<div class="progress-bar bg-primary" style="width: ${(numberofCount / totalRecords)*100}%"></div>
									</div>
								</div>
								<!-- /.progress-group -->

								<div class="progress-group">
									Total Hits This Month<span class="float-right"><b>${hitThisMonthcount}</b>/${totalRecords}</span>
									<div class="progress progress-sm">
										<div class="progress-bar bg-danger" style="width: <fmt:formatNumber value="${(hitThisMonthcount / totalRecords)*100}" minFractionDigits="0" maxFractionDigits="0"/>%"></div>
									</div>
								</div>

								<!-- /.progress-group -->
								<div class="progress-group">
									<span class="progress-text">Total Records Verified Till Date</span> <span
										class="float-right"><b>${numberofVerifiedRecords}</b>/${totalRecords}</span>
									<div class="progress progress-sm">
										<div class="progress-bar bg-success" style="width: ${(numberofVerifiedRecords / totalRecords)*100}%"></div>
									</div>
								</div>

								<!-- /.progress-group -->
<%--								<div class="progress-group">--%>
<%--									Avg. Record Received Per Month <span class="float-right"><b>250</b>/500</span>--%>
<%--									<div class="progress progress-sm">--%>
<%--										<div class="progress-bar bg-warning" style="width: 50%"></div>--%>
<%--									</div>--%>
<%--								</div>--%>
								<!-- /.progress-group -->
							</div>
							<!-- /.col -->
						</div>
						<!-- /.row -->
					</div>
					<!-- ./card-body -->
					<div class="card-footer">
						<div class="row">
							<div class="col-sm-4 col-6">

								<div class="description-block border-right">

									<span class="description-percentage text-success"><i
										class="fas "></i> <fmt:formatNumber value="${(numberofCount / totalRecords)*100}" minFractionDigits="0" maxFractionDigits="0"/>%</span>
									<h5 class="description-header">${numberofCount}</h5>
									<span class="description-text">TOTAL HITS TILL DATE</span>
								</div>
								<!-- /.description-block fa-caret-up-->
							</div>
							<!-- /.col -->
							<div class="col-sm-4 col-6">
								<div class="description-block border-right">


									<span class="description-percentage text-warning"><i
										class="fas "></i><fmt:formatNumber value="${(hitThisMonthcount / totalRecords)*100}" minFractionDigits="0" maxFractionDigits="0"/>%</span>
									<h5 class="description-header">${hitThisMonthcount}</h5>
									<span class="description-text">TOTAL HITS THIS MONTH</span>
								</div>
								<!-- /.description-block fa-caret-left-->
							</div>
							<!-- /.col -->
							<div class="col-sm-4 col-6">
								<div class="description-block border-right">

									<span class="description-percentage text-success"><i
										class="fas "></i> <fmt:formatNumber value="${(numberofVerifiedRecords / totalRecords)*100}" minFractionDigits="0" maxFractionDigits="0"/>%</span>
									<h5 class="description-header">${numberofVerifiedRecords}</h5>
									<span class="description-text">Total Records Verified Till Date</span>
								</div>
								<!-- /.description-block fa-caret-up-->
							</div>
							<!-- /.col -->
<%--							<div class="col-sm-3 col-6">--%>
<%--								<div class="description-block">--%>
<%--									<span class="description-percentage text-danger"><i--%>
<%--										class="fas "></i> 18%</span>--%>
<%--									<h5 class="description-header">1200</h5>--%>
<%--									<span class="description-text">Average Records Received Per Month</span>--%>
<%--								</div>--%>
<%--								<!-- /.description-block fa-caret-down"-->--%>
<%--							</div>--%>
						</div>
						<!-- /.row -->
					</div>
					<!-- /.card-footer -->
				</div>
				<!-- /.card -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->

	</div>
	<!--/. container-fluid -->

<%--	</c:otherwise>--%>
<%--	</c:choose>--%>
</section>

