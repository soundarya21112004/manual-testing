<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"


		 pageEncoding="UTF-8" %>
<html lang="en">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="/plugins/BUP.js"></script>

<%--<script type="text/javascript">
    $(function () {

        $("input[name=unique]").click(function () {
            if ($(this).val() == "Unique") {
                $("#dvPassport").show();
            } else {
                $("#dvPassport").hide();
            }
        });
    });
</script>--%>

<style>


	/* The Modal (background) */
	.modal {
		display: none; /* Hidden by default */
		position: fixed; /* Stay in place */
		z-index: 1; /* Sit on top */
		padding-top: 100px; /* Location of the box */
		left: 15%;
		top: 0;
		width: 40%; /* Full width */
		height: 100%; /* Full height */
		/* overflow: auto;  Enable scroll if needed */
		/*background-color: rgb(0,0,0); !* Fallback color *!*/
		background-color: rgba(0, 0, 0, 0.9); /* Black w/ opacity */
	}

	.modal1 {
		display: none; /* Hidden by default */
		position: fixed; /* Stay in place */
		z-index: 1; /* Sit on top */
		padding-top: 100px; /* Location of the box */
		left: 60%;
		top: 0;
		right: 0;
		width: 40%; /* Full width */
		height: 100%; /* Full height */
		/* overflow: auto; Enable scroll if needed */
		/*background-color: rgb(0,0,0); !* Fallback color *!*/
		background-color: rgba(0, 0, 0, 0.9); /* Black w/ opacity */
	}

	/* Modal Content (Image) */
	.modal-content {
		margin: auto;
		display: block;
		/*top: 10%;*/
		top: 10%;
		width: 80%;
		height: 60%;
		max-width: 700px;
	}
	/*Caption of Modal Image (Image Text) - Same Width as the Image */
	#caption {
		margin: auto;
		display: block;
		width: 80%;
		max-width: 700px;
		text-align: center;
		color: #ccc;
		padding: 10px 0;
		height: 150px;
	}

	/* Add Animation - Zoom in the Modal */
	.modal-content, #caption {
		animation-name: zoom;
		animation-duration: 0.6s;
	}

	@keyframes zoom {
		from {
			transform: scale(0)
		}
		to {
			transform: scale(1)
		}
	}

	/* The Close Button */
	.close {
		position: absolute;
		top: 15px;
		right: 35px;
		color: #f1f1f1;
		font-size: 40px;
		font-weight: bold;
		transition: 0.3s;
	}

	.close1 {
		position: absolute;
		top: 15px;
		right: 35px;
		color: #f1f1f1;
		font-size: 40px;
		font-weight: bold;
		transition: 0.3s;
	}

	.close:hover,
	.close:focus {
		color: #acacac;
		text-decoration: none;
		cursor: pointer;
	}

	.close1:hover,
	.close1:focus {
		color: #bbb;
		text-decoration: none;
		cursor: pointer;
	}

	/* POPUP for documents pdffunction */
	#popup {
		display: none;
		border: 1px black solid;
		/*width: 700px;*/
		height: 700px;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: white;
		z-index: 10;
		padding: 2em;
		position: absolute;
	}

	#popupc {
		display: none;
		border: 1px black solid;
		/* width: 700px;*/
		height: 700px;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: white;
		z-index: 10;
		padding: 2em;
		position: absolute;
	}

	.darken {
		background: rgba(0, 0, 0, 0.7);
	}

	#iframe {
		border: 0;
	}

	/*html, body, #page { height: 100%;}*/

	/* 100% Image Width on Smaller Screens */
	@media only screen and (max-width: 700px) {
		.modal-content {
			width: 100%;
		}

		* {
			box-sizing: border-box;
		}

		.img-zoom-container {
			position: relative;
		}

		.img-zoom-lens {
			position: absolute;
			border: 1px solid #d4d4d4;
			/*set the size of the lens:*/
			width: 40px;
			height: 40px;
		}

		.img-zoom-result {
			border: 1px solid #d4d4d4;
			/*set the size of the result div:*/
			width: 300px;
			height: 300px;
		}
	}
	#controls{
		position: fixed;
		left: 20%;
		width: 30px;
		height: 30px;
		top:80%;
	}
	#lftrot{
		top: 30px;
		left : 400px;
		position: absolute;
	}
	#rftrot{
		top:30px;
		position: absolute;
		left: 440px;
	}
	#overlap{
		top: 30px;
		left:480px;
		position: absolute;
	}
	#zoomplus {
		top: 30px;
		left: 0px;
		position: absolute;
	}
	#zoomminus{
		top: 30px;
		left:40px;
		position: absolute;
	}
	#zoomplus:hover, #zoomplus:focus{
		background-color: #ffffff;
		height: 35px;
		width: 35px;
	}
	#zoomminus:hover, #zoomminus:focus{
		background-color: #ffffff;
		height: 35px;
		width: 35px;
	}
	#rftrot:hover, #rftrot:focus{
		background-color: #ffffff;
		height: 35px;
		width: 35px;
	}
	#lftrot:hover, #lftrot:focus{
		background-color: #ffffff;
		height: 35px;
		width: 35px;
	}
	#overlap:hover, #overlap:focus{
		background-color: #ffffff;
		height: 35px;
		width: 35px;
	}
	#original{
		top: 30px;
		left:520px;
		position: absolute;
	}
	#original:hover, #original:focus{
		background-color: #ffffff;
		height: 35px;
		width: 35px;
	}
</style>
<script>
	function imageZoom(imgID, resultID) {var img, lens, result, cx, cy;
		img = document.getElementById(imgID);
		result = document.getElementById(resultID);
		/* Create lens: */
		lens = document.createElement("DIV");
		lens.setAttribute("class", "img-zoom-lens");
		/* Insert lens: */
		img.parentElement.insertBefore(lens, img);
		/* Calculate the ratio between result DIV and lens: */
		cx = result.offsetWidth / lens.offsetWidth;
		cy = result.offsetHeight / lens.offsetHeight;
		/* Set background properties for the result DIV */
		result.style.backgroundImage = "url('" + img.src + "')";
		result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
		/* Execute a function when someone moves the cursor over the image, or the lens: */
		lens.addEventListener("mousemove", moveLens);
		img.addEventListener("mousemove", moveLens);
		/* And also for touch screens: */
		lens.addEventListener("touchmove", moveLens);
		img.addEventListener("touchmove", moveLens);

		function moveLens(e) {
			var pos, x, y;
			/* Prevent any other actions that may occur when moving over the image */
			e.preventDefault();
			/* Get the cursor's x and y positions: */
			pos = getCursorPos(e);
			/* Calculate the position of the lens: */
			x = pos.x - (lens.offsetWidth / 2);
			y = pos.y - (lens.offsetHeight / 2);
			/* Prevent the lens from being positioned outside the image: */
			if (x > img.width - lens.offsetWidth) {
				x = img.width - lens.offsetWidth;
			}
			if (x < 0) {
				x = 0;
			}
			if (y > img.height - lens.offsetHeight) {
				y = img.height - lens.offsetHeight;
			}
			if (y < 0) {
				y = 0;
			}
			/* Set the position of the lens: */
			lens.style.left = x + "px";
			lens.style.top = y + "px";
			/* Display what the lens "sees": */
			result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
		}

		function getCursorPos(e) {
			var a, x = 0, y = 0;
			e = e || window.event;
			/* Get the x and y positions of the image: */
			a = img.getBoundingClientRect();
			/* Calculate the cursor's x and y coordinates, relative to the image: */
			x = e.pageX - a.left;
			y = e.pageY - a.top;
			/* Consider any page scrolling: */
			x = x - window.pageXOffset;
			y = y - window.pageYOffset;
			return {x: x, y: y};
		}
	}
</script>
<script>
	$(document).ready(function()
	{
		$('img').bind('contextmenu', function(e){
			return false;
		});
		$("img").attr('draggable', false);
		document.getElementById('myimage').setAttribute('draggable', false);
		document.getElementById('myimage1').setAttribute('draggable', false);
		document.getElementById('img02').setAttribute('draggable', false);
		document.getElementById('img01').setAttribute('draggable', false);



	});
</script>

<script>
	function submitHit() {
		var sno = document.getElementById('pkID').value;
		var requestId = document.getElementById('requestId').value;
		var comment = document.getElementById('comment').value;
		if (comment != ''){
			document.getElementById('leveloneform').action = "/MVS/saveMVSL3Result?sno=" + sno + "&verifyStatusthree=hit"+"&statusCommentthree="+comment+"&requestId"+requestId;
			document.getElementById('leveloneform').submit();
		}else{
			document.getElementById('comment').style.border = "1px solid red";
		}
	}

	function submitNoHit() {
		// alert("vghfh")
		var sno = document.getElementById('pkID').value;
		var requestId = document.getElementById('requestId').value;
		var comment = document.getElementById('comment').value;
		if (comment != ''){
			document.getElementById('leveloneform').action = "/MVS/saveMVSL3Result?sno=" + sno + "&verifyStatusthree=nohit&statusCommentthree="+comment+"&requestId"+requestId;
			document.getElementById('leveloneform').submit();
		}else{
			// alert("else")
			document.getElementById('comment').style.border = "1px solid red";
		}
	}

	// function submitDecideLater() {
	// 	var id = document.getElementById('pkID').value;
	// 	var comment = document.getElementById('comment').value;
	// 	document.getElementById('leveloneform').action = "/levelthreeSearch?id=" + id + "&verifyStatus=decidelater&statusComment=" + comment;
	// 	document.getElementById('leveloneform').submit();
	// }

</script>
<style>


	.frame {
		width: 250px;
		height: 200px;
		border: 2px solid #4080bf;
		background: white;
		margin: auto;
		padding: 10px 10px;

	}

	img {
		width: 100%;
		height: 100%;

	}

	.photo {
		border-radius: 6px;
		border: 2px solid #4080bf;
		width: 200px;
		height: 200px;
		margin-top: 10px;
		margin-left: 10px;
		float: left;
		overflow: hidden;
		position: relative;
	}

	.photo1 {
		border-radius: 6px;
		border: 2px solid #4080bf;
		width: 150px;
		height: 150px;
		margin-top: 10px;
		margin-left: 10px;
		float: left;
		overflow: hidden;
		position: relative;
	}

	.photo-name {
		/*margin-top: 245px;*/
		/*padding: 0.5em;*/
		position: absolute;
		width: 100%;
		text-align: left;
		background-color: #4080bf;
	}

	.box-text {
		margin-top: 50px;
		position: absolute;
		width: 100%;
		text-align: center;
		font-weight: bold;
		font-size: 40px;
		color: black;
	}

	.post {
		border-bottom: 1px solid #adb5bd;
		color: white;
		margin-bottom: 15px;
		padding-bottom: 15px;
	}

	.color-font {
		color: #1f4380;
		padding-left: 20px;
		padding-right: 20px;
	}

	.center {
		display: block;
		margin-left: auto;
		margin-right: auto;
		width: 50%;
	}
	.values{
		float:right;
	}
</style>
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6"></div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="dashBoard">Home</a></li>
					<li class="breadcrumb-item active">Fraud Management</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="row">
	<div class="col-sm-10"></div>
	<div class="col-sm-2">
		<h6 style="text-align: center">No of Candidate : <span style="padding-left: 10px">${count}</span></h6>
	</div>
</div>

<!-- Main content -->
<section class="content">
	<div class="container-fluid">
		<div class="card card-primary">

<%--			action="/saveMVSL3Result" method="post"--%>
			<form:form id="leveloneform" modelAttribute="galleryBean" >

			<div class="row">

				<div class="col-md-6">
					<input type="hidden" id="pkID" value="${id}">
					<input type="hidden" id="requestId" value="${requestId}">
					<div>
						<div class="card card-primary" style="align-items: center; background-color: #1f4380" >
							<div class="card-header p-2">
								<h3 class="card-title">PROBE</h3>
							</div></div>

						<div class="card card-primary" style="align-items: center; ">
							<div class="col-sm-4 img-magnifier-container">
								<img id="myimage" class="example" src="${probFaceImage}" name="myimage1"
									 width="100" height="200" alt="Girl" onclick="displaymodal(this.id , this.name)">
							</div>
						&nbsp;</div>
					</div></div>

				<div class="col-md-6">

					<!-- Profile Image height: 280px;-->
					<div>
						<div class="card card-primary" style="align-items: center; background-color: #1f4380">
							<div class="card-header p-2">
								<h3 class="card-title">CANDIDATE</h3>
							</div></div>
						<div>

							<div class="card card-primary" style="align-items: center">
								<div class="col-sm-4 img-magnifier-container">
									<img align="img" width=100 height=200 id="myimage1"
										 src=" ${CanFaceImage}" name="myimage"
										 onclick="displaymodal1(this.id , this.name)">
								</div>
							&nbsp;</div></div></div></div></div>
			<div class="row">
				<div class="col-md-6">

					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title">DEMOGRAPHIC DETAILS</h3>
						</div>
						<div class="card-body box-profile">

							<div class="user-block">

								<span class="username"> </span> <span class="description"></span>
							</div>

							<div class="row mb-3">


								<div class="col-sm-10">
									<div class="row">
										<div class="col-sm-12">


											<ul class="list-group list-group-unbordered mb-3" style="line-height: initial">

												<u class="color-font"><b>FullName</b></u><br>
												<a class=" color-font"><b>First Name</b><input class="values" value="${probeDemoFields.firstName}" readonly/></a><br>
												<a class="color-font"><b>Middle Name</b><input class="values"  value="${probeDemoFields.middleName}" readonly/></a><br>
												<a class="color-font"><b>Last Name</b><input class="values"  value="${probeDemoFields.lastName}" readonly/></a><br>
												<a class="color-font"><b>Suffix</b><input class="values"   value="${probeDemoFields.suffix}" readonly/></a><br><br>
												<a class=" color-font"><b>Gender</b><input class="values" value="${probeDemoFields.gender}" readonly/></a><br>
												<u class=" color-font"><b>Date Of Birth</b></u><br>
												<a class="color-font"><b>Month Of Birth</b><input class="values" value="${probeDemoFields.monthOfBirth}" readonly/></a><br>
												<a class="color-font"><b>Day Of Birth</b><input class="values" value="${probeDemoFields.dayOfBirth}" readonly/></a><br>
												<a class=" color-font"><b>Year Of Birth</b><input class="values" value="${probeDemoFields.yearOfBirth}" readonly/></a><br>
												<a class=" color-font"><b>Place Of Birth</b><input class="values" value="${probeDemoFields.pobCountry}" readonly/></a><br>

												<u class=" color-font"><b>Permanent Address</b></u><br>
<%--												<a class=" color-font"><b>Room/Floor/Unit No/Building Name</b><input class="values" value="${probeDemoFields.presentAddressLine1}" readonly/></a><br>--%>
<%--												<a class=" color-font"><b>House/Lot/Block No</b> <input class="values" value="${probeDemoFields.presentAddressLine1}" readonly/></a><br>--%>
												<a class=" color-font"><b>Residential Address Field</b>
													<textarea class="values" style="resize: none;" cols="23" rows="5" readonly><c:out value="${probeDemoFields.presentAddressLine1}" /> </textarea>

												</a><br>
<%--												<a class=" color-font"><b>Subdivision</b> <input class="values" value="${probeDemoFields.subDivision}" readonly/></a><br>--%>
												<a class=" color-font"><b>Barangay / Purok</b> <input class="values" value="${probeDemoFields.presentBarangay}" readonly/></a><br>
												<a class=" color-font"><b>City / Municipality</b><input class="values" value="${probeDemoFields.presentCity}" readonly/></a><br>
												<a class="color-font"><b>Province</b><input class="values" value="${probeDemoFields.presentProvince}" readonly/></a><br>
												<a class=" color-font"><b>Country</b><input class="values" value="${probeDemoFields.presentCountry}" readonly/></a>

											</ul>
										</div>

									</div>

								</div>

							</div>

						</div>

					</div>

				</div>
				<div class="col-md-6">

					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title">DEMOGRAPHIC DETAILS</h3>
						</div>
						<div class="card-body box-profile">


							<div class="user-block">

								<span class="username"> </span> <span class="description"></span>
							</div>

							<div class="row mb-3">


								<div class="col-sm-10">
									<div class="row">
										<div class="col-sm-12">

											<ul class="list-group list-group-unbordered mb-3" style="line-height: initial">
												<u class="color-font"><b>FullName</b></u><br>
												<a class=" color-font"><b>First Name</b><input class="values" value="${CanDemoFields.firstName}" readonly/></a><br>
												<a class="color-font"><b>Middle Name</b><input class="values"  value="${CanDemoFields.middleName}" readonly/></a><br>
												<a class="color-font"><b>Last Name</b><input class="values" value="${CanDemoFields.lastName}" readonly/></a><br>
												<a class="color-font"><b>Suffix</b><input class="values"  value="${CanDemoFields.suffix}" readonly/></a><br><br>
												<a class=" color-font"><b>Gender</b><input class="values" value="${CanDemoFields.gender}" readonly/></a><br>
												<u class=" color-font"><b>Date Of Birth</b></u><br>
												<a class="color-font"><b>Month Of Birth</b><input class="values" value="${CanDemoFields.monthOfBirth}" readonly/></a><br>
												<a class="color-font"><b>Day Of Birth</b><input class="values" value="${CanDemoFields.dayOfBirth}" readonly/></a><br>
												<a class=" color-font"><b>Year Of Birth</b><input class="values" value="${CanDemoFields.yearOfBirth}" readonly/></a><br>
												<a class=" color-font"><b>Place Of Birth</b><input class="values" value="${CanDemoFields.pobCountry}" readonly/></a><br>

												<u class=" color-font"><b>Permanent Address</b></u><br>
<%--												<a class=" color-font"><b>Room/Floor/Unit No/Building Name</b><input class="values" value="${CanDemoFields.presentAddressLine1}" readonly/></a><br>--%>
<%--												<a class=" color-font"><b>House/Lot/Block No</b> <input class="values" value="${CanDemoFields.presentAddressLine1}" readonly/></a><br>--%>
												<a class=" color-font"><b>Residential Address Field</b>
													<textarea class="values" style="resize: none;" cols="23" rows="5" readonly><c:out value="${CanDemoFields.presentAddressLine1}" /> </textarea>

												</a><br>
<%--												<a class=" color-font"><b>Subdivision</b> <input class="values" value="${CanDemoFields.subDivision}" readonly/></a><br>--%>
												<a class=" color-font"><b>Barangay / Purok</b> <input class="values" value="${CanDemoFields.presentBarangay}" readonly/></a><br>
												<a class=" color-font"><b>City / Municipality</b><input class="values" value="${CanDemoFields.presentCity}" readonly/></a><br>
												<a class="color-font"><b>Province</b><input class="values" value="${CanDemoFields.presentProvince}" readonly/></a><br>
												<a class=" color-font"><b>Country</b><input class="values" value="${CanDemoFields.presentCountry}" readonly/></a>


											</ul>



										</div>

									</div>

								</div>

							</div>

						</div>

					</div>

				</div></div>


				<%--			<div class="row">--%>
				<%--				<div class="col-sm-6">--%>


				<%--					<div class="card card-primary">--%>
				<%--						<div class="card-header p-2">--%>
				<%--							<h3 class="card-title">SUPPORTING DOCUMENTS</h3>--%>
				<%--						</div>--%>

				<%--						<div class="card-body box-profile">--%>

				<%--							<div class="post">--%>
				<%--								<div class="user-block">--%>

				<%--									<span class="username"> </span> <span class="description"></span>--%>
				<%--								</div>--%>
				<%--								<li class="list-group-item color-font"><b>UMID ID matched with external database</b>--%>
				<%--								<li class="list-group-item color-font"><b>Passport matched with external database</b>--%>
				<%--							</div></div></div></div>--%>



				<%--				<div class="col-sm-6">--%>
				<%--					<div class="card card-primary">--%>
				<%--						<div class="card-header p-2">--%>
				<%--							<h3 class="card-title">SUPPORTING DOCUMENTS</h3>--%>
				<%--						</div>--%>

				<%--						<div class="card-body box-profile">--%>

				<%--							<div class="post">--%>
				<%--								<div class="user-block">--%>

				<%--									<span class="username"> </span> <span class="description"></span>--%>
				<%--								</div>--%>
				<%--								<li class="list-group-item color-font"><b>Passport matched with external database</b>--%>
				<%--								<li class="list-group-item color-font"><b>UMID ID matched with external database</b>--%>
				<%--							</div></div></div></div></div>--%>

				<%--							<div class="row">--%>
				<%--				<!-- /.col -->--%>
				<%--				<div class="col-md-6">--%>
				<%--					<div class="card card-primary">--%>
				<%--						<div class="card-header p-2">--%>
				<%--							<h3 class="card-title">SUPPORTING DOCUMENTS FOR BIOMETRIC EXCEPTIONS</h3>--%>
				<%--						</div>--%>
				<%--						<li class="list-group-item color-font"><b>pic.jpeg</b>--%>
				<%--						<li class="list-group-item color-font"><b>Operator Comments</b>--%>
				<%--					</div>--%>
				<%--				</div>--%>

				<%--				<div class="col-md-6">--%>
				<%--					<div class="card card-primary">--%>
				<%--						<div class="card-header p-2">--%>
				<%--							<h3 class="card-title">SUPPORTING DOCUMENTS FOR BIOMETRIC EXCEPTIONS</h3>--%>
				<%--						</div>--%>
				<%--						<!-- /.card-header -->--%>
				<%--						<li class="list-group-item color-font"><b>pic.jpeg</b></li>--%>
				<%--						<li class="list-group-item color-font"><b>Operator Comments</b>--%>
				<%--						</li>--%>
				<%--					</div></div></div>--%>

			<div class="row mb-12">
				<div class="col-md-6">
<%--					<c:if test="${not empty reportPDFPOI || reportPDFPOA || reportPDFPOE}">--%>

					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title">PROBE DOCUMENTS</h3>
						</div>
					</div>
						<%--<div class="card card-primary">
                            <div class="card-header p-2">
                                <h3 class="card-title">PROOF OF IDENTITY</h3>
                            </div>
                        </div>--%>
					<div class="card-body">
						<c:if test="${not empty reportPDFPOI}">
							<div id="page">
								<a href="" id="probepoi"> Proof of Identity</a>
								<div id="popup">
									<i id="closepdf" class="fa fa-times"></i>Close
									<iframe id="iframe" width="550" height="600" frameborder="1"></iframe>
								</div>
							</div></c:if>
						<c:if test="${not empty reportPDFPOA}">
							<div id="page">
								<a href="" id="probepoa"> Proof of Address</a>
								<div id="popup">
									<i id="closepdf" class="fa fa-times"></i>Close
									<iframe id="iframe" width="550" height="600"></iframe>
								</div>
							</div></c:if>
						<c:if test="${not empty reportPDFPOE}">
							<div id="page">
								<a href="" id="probepoe"> Proof of Exception</a>
								<div id="popup">
									<i id="closepdf" class="fa fa-times"></i>Close
									<iframe id="iframe" width="550" height="600"></iframe>
								</div>
							</div>

						</c:if></div></div>

				<div class="col-md-6">
<%--					<c:if test="${not empty reportPDFPOICan || reportPDFPOACan || reportPDFPOECan}">--%>

					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title">CANDIDATE DOCUMENTS</h3>
						</div>
					</div>
					<div class="card-body">
						<c:if test="${not empty reportPDFPOICan}">
							<div id="pagec">
								<a href="" id="candipoi"> Proof of Identity</a>
								<div id="popupc">
									<i id="closepdf1" class="fa fa-times"></i>Close
									<iframe id="iframec" width="550" height="600" frameborder="1"></iframe>
								</div>
							</div></c:if>
						<c:if test="${not empty reportPDFPOACan}">
							<div id="pagec">
								<a href="" id="candipoa"> Proof of Address</a>
								<div id="popupc">
									<i id="closepdf1" class="fa fa-times"></i>Close
									<iframe id="iframec" width="550" height="600" frameborder="1"></iframe>
								</div>
							</div></c:if>

						<c:if test="${not empty reportPDFPOECan}">
							<div id="pagec">
								<a href="" id="candipoe"> Proof of Exception</a>
								<div id="popupc">
									<i id="closepdf1" class="fa fa-times">Close</i>
									<iframe id="iframec" width="500" height="600" frameborder="1"></iframe>
								</div>
							</div>
						</c:if>
					</div>
<%--					</c:if>--%>
				</div>
			</div>




			<div class="row">
				<!-- /.col -->
				<div class="col-md-6">
					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title">BIOMETRIC IMAGES</h3>
						</div>
						<!-- /.card-header -->
						<div class="card-body">
							<div class="tab-content" style="margin-left: 15%">
								<div class="active tab-pane" id="activity">
										<%--									<div class="post" value="Left Iris">--%>
										<%--										<c:forEach items="${irisProbScore}" var="irselement" varStatus="status">--%>

										<%--											<div class='photo'>--%>
										<%--												<p class='photo-name'>--%>
										<%--														&lt;%&ndash;														${irselement.url} -&ndash;%&gt;--%>
										<%--														${irselement.score}</p>--%>

										<%--												<img class="img-fluid mb-3" style="margin-top: 20%"--%>
										<%--													 id="piris-${status.count}" name="ciris-${status.count}"--%>
										<%--													 src="${irselement.probeIrisImage}" alt="Photo"--%>
										<%--													 onclick="displaymodal(this.id , this.name)">--%>

										<%--											</div>	</c:forEach>--%>
										<%--									</div>--%>
									<div class="post">


										<c:forEach items="${irisProbScore}" var="irselement" varStatus="status">

											<div class='photo'>
												<p class='photo-name'> ${irselement.url} - ${irselement.score}</p>

												<img class="img-fluid mb-3" style="margin-top: 20%"
													 id="piris-${status.count}" name="ciris-${status.count}"
													 src="${irselement.probeIrisImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)"></div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title">BIOMETRIC IMAGES</h3>
						</div>
						<!-- /.card-header -->
						<div class="card-body">
							<div class="tab-content" style="margin-left: 15%">
								<div class="active tab-pane" id="activity">
									<div class="post" value="Left Iris">

										<c:forEach items="${irisCanScore}" var="irselement1" varStatus="status">

											<div class='photo'>

												<p class='photo-name'> ${irselement1.url} - ${irselement1.score}</p>

												<img class="img-fluid mb-3" style="margin-top:20%"
													 id="ciris-${status.count}" name="piris-${status.count}"
													 src="${irselement1.probeIrisImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">
											</div>

										</c:forEach>


									</div>

								</div>

							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->
				</div>

			</div>

			<div class="row">
				<!-- /.col -->

				<div class="col-md-6">
					<div class="card card-primary">

						<div class="card-body">
							<div class="tab-content" style="margin-left: 50px">
								<div class="active tab-pane" id="activity">
									<div class="post">
										<div class="row mb-4">


												<%--                                                <c:forEach items="${leftfingerScore}" var="element" varStatus="status">--%>

												<%--											<div class='photo1' >--%>

												<%--												<p class='photo-name'>--%>
												<%--														${leftfingerProb[0].url}---%>
												<%--														${leftfingerProb[0].score}--%>
												<%--												</p>--%>


												<%--												<img class="center" id="pl1" name="cl1"--%>
												<%--													 src="${leftfingerProb[0].fingerImage}" alt="Photo"--%>
												<%--													 onclick="displaymodal(this.id , this.name)">--%>

												<%--											</div>--%>

												<%--                                                </c:forEach>--%>
											<div class='photo1'>

												<p class='photo-name'> ${leftfingerProb[0].url}
													- ${leftfingerProb[0].score} </p>

												<img class="center" id="pl1" name="cl1"
													 src="${leftfingerProb[0].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerProb[3].url}
													- ${leftfingerProb[3].score}</p>
												<img class="center" id="pl2" name="cl2"
													 src="${leftfingerProb[3].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerProb[2].url}
													- ${leftfingerProb[2].score}</p>
												<img class="center" id="pl3" name="cl3"
													 src="${leftfingerProb[2].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerProb[4].url}
													- ${leftfingerProb[4].score}</p>
												<img class="center" id="pl4" name="cl4"
													 src="${leftfingerProb[4].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerProb[1].url}
													- ${leftfingerProb[1].score}</p>
												<img class="center" id="pl5" name="cl5"
													 src="${leftfingerProb[1].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">
											</div>
										</div>


									</div>
								</div>

							</div>

						</div>

					</div>

				</div>

				<div class="col-md-6">
					<div class="card card-primary">

						<div class="card-body">
							<div class="tab-content" style="margin-left: 50px">
								<div class="active tab-pane" id="activity">
									<div class="post">
										<div class="user-block">

											<span class="username"> </span> <span class="description"></span>
										</div>


										<div class="row mb-3">



												<%--											<div class='photo1'>--%>
												<%--												<p class='photo-name'>--%>
												<%--														&lt;%&ndash;                                                        ${leftfingerCan[1].url}- &ndash;%&gt;--%>
												<%--														${leftfingerCan[1].score}</p>--%>
												<%--												<img class="center" id="cl5" name="pl5"--%>
												<%--													 src="${leftfingerCan[1].fingerImage}" alt="Photo"--%>
												<%--													 onclick="displaymodal1(this.id , this.name)">--%>
												<%--											</div>--%>
											<div class='photo1'>

												<p class='photo-name'> ${leftfingerCan[0].url}
													- ${leftfingerCan[0].score} </p>

												<img class="center" id="cl1" name="pl1"
													 src="${leftfingerCan[0].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id,this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerCan[3].url}
													- ${leftfingerCan[3].score}</p>
												<img class="center" id="cl2" name="pl2"
													 src="${leftfingerCan[3].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerCan[2].url}
													- ${leftfingerCan[2].score}</p>
												<img class="center" id="cl3" name="pl3"
													 src="${leftfingerCan[2].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerCan[4].url}
													- ${leftfingerCan[4].score}</p>
												<img class="center" id="cl4" name="pl4"
													 src="${leftfingerCan[4].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${leftfingerCan[1].url}
													- ${leftfingerCan[1].score}</p>
												<img class="center" id="cl5" name="pl5"
													 src="${leftfingerCan[1].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">
											</div>

										</div>


									</div>

								</div>

							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->
				</div>

			</div>


			<div class="row">
				<!-- /.col -->

				<div class="col-md-6">
					<div class="card card-primary">

						<div class="card-body">
							<div class="tab-content" style="margin-left: 50px">
								<div class="active tab-pane" id="activity">
									<div class="post">
										<div class="user-block">

											<span class="username"> </span> <span class="description"></span>
										</div>

										<div class="row mb-3">



												<%--											<div class='photo1'>--%>
												<%--												<p class='photo-name'>--%>
												<%--														&lt;%&ndash;                                                        ${rightfingerProb[1].url}- &ndash;%&gt;--%>
												<%--														${rightfingerProb[1].score}</p>--%>
												<%--												<img class="center" id="pr5" name="cr5"--%>
												<%--													 src="${rightfingerProb[1].fingerImage}" alt="Photo"--%>
												<%--													 onclick="displaymodal(this.id , this.name)">--%>
												<%--											</div>--%>
											<div class='photo1'>

												<p class='photo-name'> ${rightfingerProb[0].url}
													- ${rightfingerProb[0].score} </p>

												<img class="center" id="pr1" name="cr1"
													 src="${rightfingerProb[0].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id,  this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerProb[3].url}
													- ${rightfingerProb[3].score}</p>
												<img class="center" id="pr2" name="cr2"
													 src="${rightfingerProb[3].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerProb[2].url}
													- ${rightfingerProb[2].score}</p>
												<img class="center" id="pr3" name="cr3"
													 src="${rightfingerProb[2].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerProb[4].url}
													- ${rightfingerProb[4].score}</p>
												<img class="center" id="pr4" name="cr4"
													 src="${rightfingerProb[4].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id, this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerProb[1].url}
													- ${rightfingerProb[1].score}</p>
												<img class="center" id="pr5" name="cr5"
													 src="${rightfingerProb[1].fingerImage}" alt="Photo"
													 onclick="displaymodal(this.id , this.name)">
											</div>

										</div>
									</div></div></div></div>
					</div></div>


				<div class="col-md-6">
					<div class="card card-primary">

						<div class="card-body">
							<div class="tab-content" style="margin-left: 50px">
								<div class="active tab-pane" id="activity">
									<div class="post">
										<div class="user-block">

											<span class="username"> </span> <span class="description"></span>
										</div>

										<div class="row mb-3">

											<div class='photo1'>
												<p class='photo-name'> ${rightfingerCan[0].url}
													- ${rightfingerCan[0].score}</p>


												<img class="center" id="cr1" name="pr1"
													 src="${rightfingerCan[0].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerCan[3].url}
													- ${rightfingerCan[3].score}</p>
												<img class="center" id="cr2" name="pr2"
													 src="${rightfingerCan[3].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'>
												<p class='photo-name'> ${rightfingerCan[2].url}
													- ${rightfingerCan[2].score}</p>
												</p>
												<img class="center" id="cr3" name="pr3"
													 src="${rightfingerCan[2].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">

											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerCan[4].url}
													- ${rightfingerCan[4].score}</p>
												<img class="center" id="cr4" name="pr4"
													 src="${rightfingerCan[4].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name)">
											</div>
											<div class='photo1'>
												<p class='photo-name'> ${rightfingerCan[1].url}
													- ${rightfingerCan[1].score}</p>
												<img class="center" id="cr5" name="pr5"
													 src="${rightfingerCan[1].fingerImage}" alt="Photo"
													 onclick="displaymodal1(this.id , this.name )">
											</div>

										</div>


									</div>

								</div>

							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.card-body -->
					</div>
					<!-- /.card -->
				</div>

			</div>


				<%--			<div class="row">--%>

				<%--				<div class="col-md-12">--%>

				<%--					<div class="card card-primary">--%>
				<%--						<div class="card-header p-2">--%>
				<%--							<h3 class="card-title">ABIS REMARKS(if Applicable)</h3>--%>
				<%--						</div>--%>
				<%--						<input type="text" value="(data drawn from ABIS MA)" style="padding: 0 7em 2em 0;" readonly/></div>--%>
				<%--				</div></div>--%>
	<div class="row">

		<div class="col-md-12">
			<div class="card card-primary" style="text-align: center">
				<div class="card-header p-2">
					<h3 class="card-title1">PROBE AND CANDIDATE BIOMETRIC MATCHING SCORE  </h3>
				</div>
				<div class="card-body box-profile">


					<div class="user-block">

						<span class="username"> </span> <span class="description"></span>
					</div>

					<div class="row">


						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<ul class="list-group list-group-unbordered mb-3" style="line-height: initial">

											<%--                                                                    <u class="color-font" style="padding-left: 400px;"><b>Full Name</b></u><br>--%>
										<a class="color-fonts"><b>FACE</b><input class="values"  value="${fid}" readonly/></a><br>
										<a class="color-fonts"><b>IRIS</b><input class="values"   value="${iir}" readonly/></a><br>
										<a class=" color-fonts"><b>FINGER</b><input class="values" value="${fir}" readonly/></a><br>
											<%--                                                                    <a class="color-fonts"><b>Suffix</b><input class="values" value="${uiFields.suffix}" readonly/></a><br>--%>
											<%--                                                                    <a class="color-fonts"><b>Gender</b><input class="values" value="${uiFields.gender}" readonly/></a><br>--%>
											<%--                                                                    <u class="color-font" style="padding-left: 400px;"><b>Date Of Birth</b></u><br>--%>
											<%--                                                                    <a class="color-fonts"><b>Month Of Birth</b><input class="values"  value="${uiFields.monthOfBirth}" readonly/></a><br>--%>
											<%--                                                                    <a class="color-fonts"><b>Day Of Birth</b><input class="values"   value="${uiFields.dayOfBirth}" readonly/></a><br>--%>
											<%--                                                                    <a class=" color-fonts"><b>Year Of Birth</b><input class="values" value="${uiFields.yearOfBirth}" readonly/></a>--%>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">

		<div class="col-md-12">

			<div class="card card-primary">
				<div class="card-header p-2">
					<h3 class="card-title"><b>ABIS Operator 1 Remarks</b></h3>
					<div class="col-md-8 float-right">${commentABIS}</div>
				</div>
			</div>
		</div></div>
	<div class="row">

		<div class="col-md-12">

			<div class="card card-primary">
				<div class="card-header p-2">
					<h3 class="card-title"><b>ABIS Operator 2 Remarks</b></h3>
					<div class="col-md-8 float-right">${comment1ABIS}</div>
				</div>
			</div>
		</div></div>
	<div class="row">

		<div class="col-md-12">


			<div class="card card-primary">
				<div class="card-header p-2">
					<h3 class="card-title"><b>Manual Verification Operator 1 Decision</b></h3>
					<div class="col-md-8 float-right">${op1verifyStatus}</div>
				</div>
				<div class="card-header p-2">
					<h3 class="card-title"><b>Manual Verification Operator 1 Remarks</b></h3><br>
					<input type="text" style="width:100%; padding: 0 7em 2em 0;" readonly value="${op1Comment}">
				</div>
					<%--						<input type="text" value="(data drawn from MVS)" style="padding: 0 7em 2em 0;" readonly/>--%>
			</div>
		</div></div>



	<div class="row">

		<div class="col-md-12">

			<div class="card card-primary">
				<div class="card-header p-2">
					<h3 class="card-title"><b>Manual Verification Operator 2 Decision</b></h3>
					<div class="col-md-8 float-right">${op2verifyStatus}</div>
				</div>
				<div class="card-header p-2">
					<h3 class="card-title"><b>Manual Verification Operator 2 Remarks</b></h3><br>
					<input type="text" style="width:100%; padding: 0 7em 2em 0;" readonly value="${op2Comment}">
				</div>
					<%--						<input type="text" value="(data drawn from MVS)" style="padding: 0 7em 2em 0;" readonly/>--%>
			</div>
		</div></div>


	<c:choose>
		<c:when test="${empty supervisorVerifyStatus}">


		</c:when>
		<c:otherwise>
			<div class="row">

				<div class="col-md-12">

					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title"><b>Manual Verification Supervisor Decision</b></h3>
							<div class="col-md-8 float-right">${supervisorVerifyStatus}</div>
						</div>
						<div class="card-header p-2">
							<h3 class="card-title"><b>Manual Verification Supervisor Remarks</b></h3><br>
							<input type="text" style="width:100%; padding: 0 7em 2em 0;" readonly value="${supervisorComment}">
						</div>
							<%--						<input type="text" value="(data drawn from MVS)" style="padding: 0 7em 2em 0;" readonly/>--%>
					</div>
				</div></div>
		</c:otherwise>
	</c:choose>


<%--	<div class="row">--%>

<%--		<div class="col-md-12">--%>

<%--			<div class="card card-primary">--%>
<%--				<div class="card-header p-2">--%>
<%--					<h3 class="card-title"><b>Manual Verification Supervisor Decision</b></h3>--%>
<%--					<div class="col-md-8 float-right">${supervisorVerifyStatus}</div>--%>
<%--				</div>--%>
<%--				<div class="card-header p-2">--%>
<%--					<h3 class="card-title"><b>Manual Verification Supervisor Remarks</b></h3><br>--%>
<%--					<input type="text" style="width:100%; padding: 0 7em 2em 0;" readonly value="${supervisorComment}">--%>
<%--				</div>--%>
<%--					&lt;%&ndash;						<input type="text" value="(data drawn from MVS)" style="padding: 0 7em 2em 0;" readonly/>&ndash;%&gt;--%>
<%--			</div>--%>
<%--		</div></div>--%>

<%--	<div class="row">--%>

<%--		<div class="col-md-12">--%>

<%--			<div class="card card-primary">--%>
<%--				<div class="card-header p-2">--%>
<%--					<h3 class="card-title">Manual Verification Supervisor Remarks/Decision</h3>--%>
<%--				</div>--%>
<%--				<input type="text" style="padding: 0 7em 2em 0;" id="comment" />--%>
<%--			</div>--%>
<%--		</div></div>--%>


	<div class="col-md-12">
		<div class="card card-primary">
			<div class="card-header p-2">
				<h3 class="card-title" style="padding-left: 560px">REGISTRATION DETAILS</h3>
			</div></div></div>






			<div class="row">
				<!-- /.col -->

				<div class="col-md-6">
					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title" style="padding-left: 290px">PROBE</h3>
						</div><br>

							<%--						<a class=" color-font"><b>Registration Center/Code</b> <input class="values" style="width:260px" value="${probeDemoFields.registrationType}" readonly/></a><br>--%>
						<a class=" color-font"><b>Registration Officer</b><input class="values" style="width:260px" value="${probeDemoFields.officer}" readonly/></a><br>
						<a class=" color-font"><b>Date Of Registration</b> <input class="values" style="width:260px" value="${probeDemoFields.creationdate}" readonly/></a><br>
						<a class=" color-font"><b>Transaction Number</b> <input class="values" style="width:260px" value="${probeDemoFields.registrationId}" readonly/></a><br>
							<%--						<a class=" color-font"><b>BIO Ref ID</b> <input class="values" style="width:260px" value="${probeDemoFields.registrationType}" readonly/></a><br>--%>
						<a class=" color-font"><b>Time Of Registration</b> <input class="values" style="width:260px" value="${probeDemoFields.creationtime}" readonly/></a><br>
					</div>

				</div>

				<div class="col-md-6">
					<div class="card card-primary">
						<div class="card-header p-2">
							<h3 class="card-title" style="padding-left: 290px">CANDIDATE</h3>
						</div><br>
							<%--						<a class=" color-font"><b>Registration Center/Code</b> <input class="values" style="width:260px" value="${CanDemoFields.registrationType}" readonly/></a><br>--%>
						<a class=" color-font"><b>Registration Officer</b><input class="values" style="width:260px" value="${CanDemoFields.officer}" readonly/></a><br>
						<a class=" color-font"><b>Date Of Registration</b> <input class="values" style="width:260px" value="${CanDemoFields.creationdate}" readonly/></a><br>
						<a class=" color-font"><b>Transaction Number</b> <input class="values" style="width:260px" value="${CanDemoFields.registrationId}" readonly/></a><br>
							<%--						<a class=" color-font"><b>BIO Ref ID</b> <input class="values" style="width:260px" value="${CanDemoFields.registrationType}" readonly/></a><br>--%>
						<a class=" color-font"><b>Time Of Registration</b> <input class="values" style="width:260px" value="${CanDemoFields.creationtime}" readonly/></a><br>
					</div>

				</div>

					<%--        <!-- Justification by Level1 operator -->--%>
					<%--                <div class="row mb-6">--%>
					<%--                    <div class="col-md-6">--%>
					<%--                        <div class="card card-primary">--%>
					<%--                            <div class="card-header p-2">--%>
					<%--                                <h3 class="card-title">JUSTIFICATION BY LEVEL ONE</h3>--%>
					<%--                            </div>--%>
					<%--                        </div>--%>
					<%--                            &lt;%&ndash; <textarea class="form-control" type="text" id="commentLevel1" readonly="true" name="commentLevel1" style="resize: none">${statusComment}</textarea>&ndash;%&gt;--%>
					<%--                        <div class="col-sm-12">${statusComment}</div>--%>
					<%--                    </div>--%>

					<%--                    <div class="col-md-6">--%>
					<%--                        <div class="card card-primary">--%>
					<%--                            <div class="card-header p-2">--%>
					<%--                                <h3 class="card-title">DECISION BY LEVEL ONE</h3>--%>
					<%--                            </div>--%>
					<%--                        </div>--%>
					<%--                        <div class="col-sm-12">${verifyStatus}</div>--%>
					<%--                    </div>--%>
					<%--                </div>--%>
					<%--                <div class="row mb-6">--%>
					<%--                    <div class="col-md-12">--%>
					<%--                        <div class="card card-primary">--%>
					<%--                            <div class="card-header p-2" id="dvPassport">--%>
					<%--                                <h3 class="card-title">JUSTIFICATION</h3></div></div></div>--%>
					<%--                    <div class="col-md-4">--%>
					<%--                            <textarea class="form-control" type="text" id="comment" name="comment" style="resize: none"--%>
					<%--                                      onkeypress="return onlyAlphabets(event);" autocomplete="nope"--%>
					<%--                                      onkeyup="AllowSingleSpaceNotInFirstAndLast(this);autoCaps(this);">--%>
					<%--                            </textarea></div>--%>
					<%--                </div>--%>

					<%--                <div class="card-footer">--%>

					<%--                    <button type="submit" class="btn btn-info" onclick="return submitHit()">Hit</button>--%>
					<%--                    <button type="submit" class="btn btn-default" onclick="return submitNoHit()">No Hit</button>--%>
					<%--                    <button type="submit" class="btn btn-default" onclick="return submitDecideLater()">Undecidable</button>--%>
					<%--                </div>--%>

				</form:form>
<%--				<div class="card-footer">--%>

<%--					<button type="button" class="btn btn-default" onclick="return submitNoHit()">No Hit</button>--%>
<%--					<button type="button" class="btn btn-info" onclick="return submitHit()">Hit</button>--%>

<%--				</div>--%>




			</div>
		</div>

		<!-- The Modal -->
		<div id="myModal" class="modal" style="height: 100%;">

			<!-- The Close Button -->
			<span class="close">&times;</span>

			<!-- Modal Content (The Image) -->
			<img class="modal-content" id="img01">

			<!-- Modal Caption (Image Text) -->

			<div id="caption1"></div>
			<div id="controls">
				<img src="${pageContext.servletContext.contextPath}/overlap1.png" alt="overlap" id="overlap">
				<img src="${pageContext.servletContext.contextPath}/leftRotate.png" alt="left-rotate" id="lftrot">
				<img src="${pageContext.servletContext.contextPath}/rightRotate.png" alt="rigth-rotate" id="rftrot">
				<img src="${pageContext.servletContext.contextPath}/original.png" alt="Original" id="original" disabled>
				<!--<img src="../../../../balckimage%20(1).png" alt="zoom-plus" id="zoomplus">
                <img src="../../../../balckimage%20(2).png" alt="Zoom-minus" id="zoomminus">    -->
			</div>
		</div>

		<div id="myModal1" class="modal1">

			<!-- The Close Button -->
			<span class="close1">&times;</span>

			<!-- Modal Content (The Image) -->
			<img class="modal-content" id="img02" >

			<!-- Modal Caption (Image Text) -->
			<div id="caption2"></div>
		</div>
		<!-- /.container-fluid -->
</section>
<div id="myresult" class="img-zoom-result"></div>

<script>
	imageZoom("myimage", "myimage");
	imageZoom("myimage1", "myresult");
</script>


<script>
	function base64PDFToBlobUrl(base64){
		console.log('base64 ')
		const binStr = atob( base64 );
		const len = binStr.length;
		const arr = new Uint8Array(len);
		for (let i = 0; i < len; i++) {
			arr[ i ] = binStr.charCodeAt( i );
		}
		const blob =  new Blob( [ arr ], { type: 'application/pdf' });
		const url = URL.createObjectURL( blob );
		// console.log('url sanitized : '+ this.sanitizer.bypassSecurityTrustResourceUrl(url+'#toolbar=0'))
		// return  this.sanitizer.bypassSecurityTrustResourceUrl(url+'#toolbar=0');
		return url; // return only this url
	}


	/* 1popup documents pdffunction  */
	//obj = document.getElementById(), then set obj.onclick
	$("#probepoa").click(function(e){
		e.preventDefault();
		var isInit = true; // indicates if the popup already been initialized.
		var isClosed = false; // indicates the state of the popup
		document.getElementById("popup").style.display = "block";
		document.getElementById('iframe').src = base64PDFToBlobUrl("${reportPDFPOA}")+"#toolbar=0";
		document.getElementById('page').className = "darken";
		document.getElementById('closepdf').onclick = function () {
			if (isInit) {
				isInit = false;
				return;
			}
			if (isClosed) {
				return;
			} //if the popup is closed, do nothing.
			document.getElementById("popup").style.display = "none";
			document.getElementById('page').className = "";
			isClosed = true;
		}
		return false;
	});
	// });
	//  document.getElementById("probepoi").onclick = function (e) {
	$("#probepoi").click(function(e){
		e.preventDefault();
		var isInit = true; // indicates if the popup already been initialized.
		var isClosed = false; // indicates the state of the popup
		document.getElementById("popup").style.display = "block";
		document.getElementById('iframe').src = base64PDFToBlobUrl("${reportPDFPOI}")+"#toolbar=0";
		document.getElementById('page').className = "darken";
		document.getElementById('closepdf').onclick = function () {
			if (isInit) {
				isInit = false;
				return;
			}
			if (isClosed) {
				return;
			} //if the popup is closed, do nothing.
			document.getElementById("popup").style.display = "none";
			document.getElementById('page').className = "";
			isClosed = true;
		}
		return false;
	});

	<%--document.getElementById("probepoa").onclick = function (e) {--%>
	<%--     e.preventDefault();--%>
	<%--     var isInit = true; // indicates if the popup already been initialized.--%>
	<%--     var isClosed = false; // indicates the state of the popup--%>
	<%--     document.getElementById("popup").style.display = "block";--%>
	<%--     document.getElementById('iframe').src = "${reportPDFPOA}#toolbar=0";--%>
	<%--     document.getElementById('page').className = "darken";--%>
	<%--     document.getElementById('closepdf').onclick = function () {--%>
	<%--         if (isInit) {--%>
	<%--             isInit = false;--%>
	<%--             return;--%>
	<%--         }--%>
	<%--         if (isClosed) {--%>
	<%--             return;--%>
	<%--         } //if the popup is closed, do nothing.--%>
	<%--         document.getElementById("popup").style.display = "none";--%>
	<%--         document.getElementById('page').className = "";--%>
	<%--         isClosed = true;--%>
	<%--     }--%>
	<%--     return false;--%>
	<%-- }--%>



	$("#probepoe").click(function(e){
		e.preventDefault();
		var isInit = true; // indicates if the popup already been initialized.
		var isClosed = false; // indicates the state of the popup
		document.getElementById("popup").style.display = "block";
		document.getElementById('iframe').src = "data:image/jpeg;base64,${reportPDFPOE}#toolbar=0";
		document.getElementById('page').className = "darken";
		document.getElementById('closepdf').onclick = function () {
			if (isInit) {
				isInit = false;
				return;
			}
			if (isClosed) {
				return;
			} //if the popup is closed, do nothing.
			document.getElementById("popup").style.display = "none";
			document.getElementById('page').className = "";
			isClosed = true;
		}
		return false;
	});
	$("#candipoi").click(function(e){
		e.preventDefault();
		var isInit = true; // indicates if the popup already been initialized.
		var isClosed = false; // indicates the state of the popup
		document.getElementById("popupc").style.display = "block";
		document.getElementById('iframec').src = base64PDFToBlobUrl("${reportPDFPOICan}")+"#toolbar=0";
		document.getElementById('pagec').className = "darken";
		document.getElementById('closepdf1').onclick = function () {
			if (isInit) {
				isInit = false;
				return;
			}
			if (isClosed) {
				return;
			} //if the popup is closed, do nothing.
			document.getElementById("popupc").style.display = "none";
			document.getElementById('pagec').className = "";
			isClosed = true;
		}
		return false;
	});

	$("#candipoa").click(function(e){
		e.preventDefault();
		var isInit = true; // indicates if the popup already been initialized.
		var isClosed = false; // indicates the state of the popup
		document.getElementById("popupc").style.display = "block";
		document.getElementById('iframec').src = base64PDFToBlobUrl("${reportPDFPOACan}")+"#toolbar=0";
		document.getElementById('pagec').className = "darken";
		document.getElementById('closepdf1').onclick = function () {
			if (isInit) {
				isInit = false;
				return;
			}
			if (isClosed) {
				return;
			} //if the popup is closed, do nothing.
			document.getElementById("popupc").style.display = "none";
			document.getElementById('pagec').className = "";
			isClosed = true;
		}
		return false;
	});
	$("#candipoe").click(function(e){
		e.preventDefault();
		var isInit = true; // indicates if the popup already been initialized.
		var isClosed = false; // indicates the state of the popup
		document.getElementById("popupc").style.display = "block";
		document.getElementById('iframec').src = "data:image/jpeg;base64,${reportPDFPOECan}#toolbar=0";
		document.getElementById('pagec').className = "darken";
		document.getElementById('closepdf1').onclick = function () {
			if (isInit) {
				isInit = false;
				return;
			}
			if (isClosed) {
				return;
			} //if the popup is closed, do nothing.
			document.getElementById("popupc").style.display = "none";
			document.getElementById('pagec').className = "";
			isClosed = true;
		}
		return false;
	});
	// Get the modal

	function displaymodal(id,nme) {
		var modal = document.getElementById("myModal");
		var modal1 = document.getElementById("myModal1");
		// Get the image and insert it inside the modal - use its "alt" text as a caption
		var img = document.getElementById(id);
		var modalImg = document.getElementById("img01");
		var captionText = document.getElementById("caption1");
		var img1 = document.getElementById(nme);
		var modalImg1 = document.getElementById("img02");
		var captionText1 = document.getElementById("caption2");

		//img.onclick = function () { single click to open model popup
		modal.style.display = "block";
		modalImg.src = img.src;
		modalImg.onmouseover = imageZoom("img01", "myresult");
		captionText.innerHTML = this.alt;
		modal1.style.display = "block";
		modalImg1.src = img1.src;
		modalImg1.onmouseover = imageZoom("img02", "myresult");
		captionText1.innerHTML = this.alt;
		//}
		// Get the <span> element that closes the modal
		modalImg1.style.display = "block";
		modalImg1.onmousedown = function (event) {
			modalImg1.style.position = 'absolute';
			modalImg1.style.zIndex = 1;
			//Negative="img1";
			document.body.append(modalImg1);
			modalImg1.style.width = 32 + '%';
			modalImg1.style.height = 52.5  + '%';
			function moveAt(pageX, pageY) {
				modalImg1.style.left = pageX - modalImg1.offsetWidth / 2 + 'px';
				modalImg1.style.top = pageY - modalImg1.offsetHeight / 2 + 'px';
			}

			moveAt(event.pageX, event.pageY);

			function onMouseMove(event) {
				moveAt(event.pageX, event.pageY);
			}

			document.addEventListener('mousemove', onMouseMove);
			modalImg1.onmouseup = function () {
				document.removeEventListener('mousemove', onMouseMove);
				modalImg1.onmouseup = null;
			};
		};
		modalImg1.ondragstart = function () {
			return false;
		};

		var span = document.getElementsByClassName("close")[0];
		// When the user clicks on <span> (x), close the modal
		span.onclick = function () {
			modal.style.display = "none";
			modal1.style.display = "none";
			modalImg1.style.display = "none";
			document.getElementById("img01").style.transform = "rotate(0deg)";
			document.getElementById("img02").style.transform = "rotate(0deg)";
			modal1.append(modalImg1);
			modalImg1.style.top = 21.4 + '%';
			modalImg1.style.left = 10 + '%';
			modalImg1.style.filter = "opacity(100%)";
			modalImg.style.filter = "opacity(100%)";
		}
		var span1 = document.getElementsByClassName("close1")[0];
		// When the user clicks on <span> (x), close the modal
		span1.onclick = function () {
			modal.style.display = "none";
			modal1.style.display = "none";
			modalImg1.style.display = "none";
			document.getElementById("img01").style.transform = "rotate(0deg)";
			document.getElementById("img02").style.transform = "rotate(0deg)";
			modal1.append(modalImg1);
			modalImg1.style.top = 21.4 + '%';
			modalImg1.style.left = 10 + '%';
			modalImg1.style.filter = "opacity(100%)";
			modalImg.style.filter = "opacity(100%)";
		}
		var angle = 0;
		var rightRotate=document.getElementById("rftrot");
		rightRotate.onclick = function() {
			angle = angle + 5;
			if (angle > 360) {
				angle = 5;
			}
			document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
			document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
		}
		var leftRotate = document.getElementById("lftrot");
		leftRotate.onclick = function() {
			angle = angle - 5;
			if (angle < 0) {
				anlge = -5;
			}
			document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
			document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
		}
		var overlap1= document.getElementById("overlap");
		overlap1.onclick = function() {

			document.getElementById("img02").style.filter = "contrast(200%)";
			document.getElementById("img02").style.filter = "opacity(50%)";
			document.getElementById("img01").style.filter = "invert(200%)";
			modalImg1.style.position = "absolute";
			modalImg.style.position = "absolute";
			modal.appendChild(modalImg);
			modal.appendChild(modalImg1);
			modalImg.style.top = 21.9 + '%';
			modalImg.style.left = 10 + '%';
			modalImg1.style.top = 21.9 + '%';
			modalImg1.style.left = 10 + '%';
		}
		var original1 = document.getElementById("original");
		original1.onclick = function() {
			modal1.append(modalImg1);
			modal.append(modalImg);
			modalImg1.style.top = 21.4 + '%';
			modalImg1.style.left = 10 + '%';
			modalImg1.style.width = 80 + '%';
			modalImg1.style.height = 50 + '%';
			modalImg.style.width = 80 + '%';
			modalImg.style.height = 50 + '%';
			modalImg1.style.filter = "opacity(100%)";
			modalImg.style.filter = "opacity(100%)";
		}
	}

	function displaymodal1(id,nme) {
		var modal1 = document.getElementById("myModal1");
		var modal2 = document.getElementById("myModal");
		// Get the image and insert it inside the modal - use its "alt" text as a caption
		var img1 = document.getElementById(id);
		var modalImg1 = document.getElementById("img02");
		var captionText1 = document.getElementById("caption2");
		var img2 = document.getElementById(nme);
		var modalImg2 = document.getElementById("img01");
		var captionText2 = document.getElementById("caption1");
		//img1.onclick = function () {
		modal1.style.display = "block";
		modalImg1.src = img1.src;
		modalImg1.onmouseover = imageZoom("img02", "myresult"); //checkAngular
		captionText1.innerHTML = this.alt;
		modal2.style.display = "block";
		modalImg2.src = img2.src;
		modalImg2.onmouseover = imageZoom("img01", "myresult"); //checkAngular
		captionText2.innerHTML = this.alt;
		//}
		modalImg1.style.top = window.center;
		modalImg1.style.left = window.center;
		modalImg1.style.display = "block";
		modalImg1.onmousedown = function (event) {
			modalImg1.style.position = 'absolute';
			modalImg1.style.zIndex = 1;
			//Negative="img1";
			document.body.append(modalImg1);
			modalImg1.style.width = 32 + '%';
			modalImg1.style.height = 52.5  + '%';

			function moveAt(pageX, pageY) {
				modalImg1.style.left = pageX - modalImg1.offsetWidth / 2 + 'px';
				modalImg1.style.top = pageY - modalImg1.offsetHeight / 2 + 'px';
			}

			moveAt(event.pageX, event.pageY);

			function onMouseMove(event) {
				moveAt(event.pageX, event.pageY);
			}

			document.addEventListener('mousemove', onMouseMove);
			modalImg1.onmouseup = function () {
				document.removeEventListener('mousemove', onMouseMove);
				modalImg1.onmouseup = null;
			};
		};
		modalImg1.ondragstart = function () {
			return false;
		};
		// Get the <span> element that closes the modal
		var span1 = document.getElementsByClassName("close1")[0];

		// When the user clicks on <span> (x), close the modal
		span1.onclick = function () {
			modal2.style.display = "none";
			modal1.style.display = "none";
			modalImg1.style.display = "none";
			document.getElementById("img01").style.transform = "rotate(0deg)";
			document.getElementById("img02").style.transform = "rotate(0deg)";
			modal1.append(modalImg1);
			modalImg1.style.top = 21.4 + '%';
			modalImg1.style.left = 10 + '%';
			modalImg1.style.filter = "opacity(100%)";
			modalImg2.style.filter = "opacity(100%)";
			modalImg1.style.width = 80 +'%';
			modalImg1.style.height = 52.4 + '%';
			// modalImg2.style.width = 80 +'%';
			// modalImg2.style.height = 60 + '%';
		}
		var span = document.getElementsByClassName("close")[0];


		// When the user clicks on <span> (x), close the modal
		span.onclick = function () {
			modal2.style.display = "none";
			modal1.style.display = "none";
			modalImg1.style.display = "none";
			document.getElementById("img01").style.transform = "rotate(0deg)";
			document.getElementById("img02").style.transform = "rotate(0deg)";
			modal1.append(modalImg1);
			modalImg1.style.top = 21.4 + '%';
			modalImg1.style.left = 10 + '%';
			modalImg1.style.filter = "opacity(100%)";
			modalImg2.style.filter = "opacity(100%)";
			modalImg1.style.width = 80 +'%';
			modalImg1.style.height = 52.4 + '%';
			// modalImg2.style.width = 80 +'%';
			// modalImg2.style.height = 60 + '%';
		}

		var angle = 0;
		var rightRotate=document.getElementById("rftrot");
		rightRotate.onclick = function() {
			angle = angle + 5;
			if (angle > 360) {
				angle = 5;
			}
			document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
			document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
		}
		var leftRotate = document.getElementById("lftrot");
		leftRotate.onclick = function() {
			angle = angle - 5;
			if (angle < 0) {
				anlge = -5;
			}
			document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
			document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
		}
		var overlap1= document.getElementById("overlap");
		overlap1.onclick = function() {
			// modalImg1.style.width = 80 + '%';
			// modalImg1.style.height = 52.4 + '%';
			document.getElementById("img02").style.filter = "contrast(200%)";
			document.getElementById("img02").style.filter = "opacity(50%)";
			document.getElementById("img01").style.filter = "invert(200%)";
			modalImg1.style.position = "absolute";
			modalImg2.style.position = "absolute";
			modal2.appendChild(modalImg2);
			modal2.appendChild(modalImg1);
			modalImg2.style.top = 21.9 + '%';
			modalImg2.style.left = 10 + '%';
			modalImg1.style.top = 21.9 + '%';
			modalImg1.style.left = 10 + '%';
			// modalImg1.style.width = 80 +'%';
			// modalImg1.style.height = 52.1 + '%';
			// modalImg2.style.width = 80 +'%';
			// modalImg2.style.height = 60 + '%';
			/*var zoomplus1= document.getElementById("zoomplus");
            zoomplus1.onclick = function(){
                var curwidth1= modalImg1.clientWidth ;
                var curheight1=modalImg1.clientHeight;
                var curwidth2=modalImg2.clientWidth;
                var curheight2=modalImg2.clientHeight;
                if(curwidth1 > 600 ){
                    alert("U reached a limit");
                }
                else{
                    modalImg1.style.width=(curwidth1 + 10) +'px';
                    modalImg1.style.height= ( curheight1 + 10) +'px';
                    modalImg2.style.width=(curwidth2 + 10) +'px';
                    modalImg2.style.height= ( curheight2 + 10) +'px';
                }
            }*/
		}
		var original1 = document.getElementById("original");
		original1.onclick = function() {
			modal1.append(modalImg1);
			modal2.append(modalImg2);
			modalImg1.style.top = 21.4 + '%';
			modalImg1.style.left = 10 + '%';
			modalImg1.style.width = 80 + '%';
			modalImg1.style.height = 52.4 + '%';
			modalImg2.style.width = 80 + '%';
			modalImg2.style.height = 52.4 + '%';
			modalImg1.style.filter = "opacity(100%)";
			modalImg2.style.filter = "opacity(100%)";
		}
	}
</script>
<!-- /.content -->