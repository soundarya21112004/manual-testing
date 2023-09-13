<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul class="x-navigation x-navigation-horizontal">

	<li class="xn-logo"><a href="#"><img src="img/brta.png"
			alt="text" style="width: 50px; height: 50px" /> MAS</a> <a href="#"
		class="x-navigation-control"></a></li>
	<li class="xn-openable"><a href="register"><span
			class="fa fa-edit"></span> REGISTER</a></li>
	<li class="xn-openable"><a href="downloadLLRDetails"><span
			class="fa fa-download"></span>DOWNLOAD</a></li>
	<!-- <li class="xn-openable"><a href="resubmissionsearch"><span
			class="fa fa-edit"></span>RE-SUBMISSION</a></li> -->
	<li class="xn-icon-button pull-right"><a href="#"
		class="mb-control" data-box="#mb-signout"><span
			class="fa fa-sign-out"></span></a></li>
	<!-- END SIGN OUT -->
</ul>

<form action="login" class="form-horizontal" method="post">
	<div class="message-box animated fadeIn" data-sound="alert"
		id="mb-signout">
		<div class="mb-container">
			<div class="mb-middle">
				<div class="mb-title">
					<span class="fa fa-sign-out"></span> EXIT <strong></strong> ?
				</div>
				<div class="mb-content">
					<p>Are you sure you want to go to home page?</p>

				</div>
				<div class="mb-footer">
					<div class="pull-right">
						<a href="logout" class="btn btn-success btn-lg">Yes</a>
						<button class="btn btn-default btn-lg mb-control-close">No</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>