<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:if test="${userdetails.userid == null}">
	<div class="page-container page-navigation-top">
		<div class="page-content">
			<tiles:insertAttribute name="headerSession" />

		</div>
		<tiles:insertAttribute name="footer" />
	</div>
</c:if>
<c:if test="${userdetails.userid != null}">
	<div class="page-container page-navigation-top">
		<!-- PAGE CONTENT -->
		<div class="page-content">
			<tiles:insertAttribute name="header" />
			<div class="page-container page-navigation-top">
				<tiles:insertAttribute name="body" />
			</div>
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</c:if>

<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>User Group Creation </h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active">User Group Creation</li>
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
		<form action="createRoleForGroup"
			  id="validate" method="post" modelAttribute="mstRolesBean">
			<div class="card card-primary">
				<div class="card-header">
					<h3 class="card-title">Add User Group</h3>


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
						<div class="col-md-6">
							<div class="form-group">
								<label>Group Name</label>
								<input class="form-control required" required type="text"
									   id="newGroupName" name="mstRoleGroup.groupName"
									   placeholder="Enter Group Name">
							</div>
						</div>
					</div>
					<div class="row">
						<div class=" col-sm-6">
							<div class="form-group">
								<label>Select Role</label>
								<ul>

									<select class="form-control" multiple required name="selectedRoleGroups" id="selectedRoleGroups"
	                               <c:forEach items="${allRoles}" var="element1">
			                      <option value="${element1.roleid}">${element1.roleDetails}</option>
									</c:forEach>
									</select>
								</ul>
							</div>
						</div>
					</div>


				</div>

			</div>


			<div class="card-footer">
				<button type="submit" class="btn btn-info">Submit</button>
				<button type="reset" class="btn btn-default float-right">Cancel</button>
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
	function selectAllcheck() {

		if ($("#CheckAll").is(":checked")) {
			$('input[id=selectedRoleGroups]').prop("checked", true);
		} else {
			$('input[id=selectedRoleGroups]').prop("checked", false);
		}
	}
	function autocaps(cObj) {

		if (cObj.value.indexOf("  ") >= 0) {
			alert("No more than one space is allowed.");
			cObj.value = '';
		}
		cObj.value = cObj.value.charAt(0).toUpperCase() + cObj.value.slice(1);

		var badWords = [ 'Murder', 'Kill', 'Death' ];
		$(':input').on('keyup', function() {
			var value = $(this).val();
			$.each(badWords, function(idx, word) {

				value = value.replace(word, '');

			});
			$(this).val(value);
		});
	}

	function checkSelectedUser() {

		var newGroupName = document.getElementById("newGroupName").value;
		if (newGroupName !== "") {
			var len = newGroupName.trim().length;
			if (len < 1) {
				document.getElementById("newGroupName").value = "";
				$('#newGroupName').focus();
				// swal("ENTER VALID GROUP NAME.");
				swal("ENTER VALID GROUP NAME.");
				return false;
			}
		} else {
			// swal("ENTER GROUP NAME.");
			swal("ENTER GROUP NAME.");
			return false;
		}

		if (newGroupName !== "") {
			var verify;

			$.ajax({
				type : "POST",
				datatype : "json",
				//data: JSON.stringify(variables),
				url : "checkGroupName/"+ newGroupName,
				contentType: 'application/json',
				async: false,
				success : function(data) {
					verify = data;
				},
				error: function () {
					alert("Something went wrong! Please try later");
				}
			});
			if (verify) {
				document.getElementById("newGroupName").value = newGroupName;
				$("#newGroupName").focus();
				$("#newGroupName").css({
					'color': 'red'
				});
				swal("GROUP NAME ALREADY EXISTS.")
				return false;
			}
		} else {
			$("#newGroupName").focus();
			swal("PLEASE ENTER THE GROUP NAME")
			return false;
		}


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

		document.getElementById("validate").action = "<%=request.getContextPath()%>/createRoleForGroup";
		document.getElementById("validate").submit();

	}
</script>
<script>
	$(function () {
		//Initialize Select2 Elements
		$('.select2').select2()

		//Initialize Select2 Elements
		$('.select2bs4').select2({
			theme: 'bootstrap4'
		})

		//Datemask dd/mm/yyyy
		$('#datemask').inputmask('dd/mm/yyyy', { 'placeholder': 'dd/mm/yyyy' })
		//Datemask2 mm/dd/yyyy
		$('#datemask2').inputmask('mm/dd/yyyy', { 'placeholder': 'mm/dd/yyyy' })
		//Money Euro
		$('[data-mask]').inputmask()

		//Date range picker
		$('#reservationdate').datetimepicker({
			format: 'L'
		});
		//Date range picker
		$('#reservation').daterangepicker()
		//Date range picker with time picker
		$('#reservationtime').daterangepicker({
			timePicker: true,
			timePickerIncrement: 30,
			locale: {
				format: 'MM/DD/YYYY hh:mm A'
			}
		})
		//Date range as a button
		$('#daterange-btn').daterangepicker(
				{
					ranges   : {
						'Today'       : [moment(), moment()],
						'Yesterday'   : [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
						'Last 7 Days' : [moment().subtract(6, 'days'), moment()],
						'Last 30 Days': [moment().subtract(29, 'days'), moment()],
						'This Month'  : [moment().startOf('month'), moment().endOf('month')],
						'Last Month'  : [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
					},
					startDate: moment().subtract(29, 'days'),
					endDate  : moment()
				},
				function (start, end) {
					$('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
				}
		)

		//Timepicker
		$('#timepicker').datetimepicker({
			format: 'LT'
		})
		$('.duallistbox').bootstrapDualListbox()

		//Colorpicker
		$('.my-colorpicker1').colorpicker()
		//color picker with addon
		$('.my-colorpicker2').colorpicker()

		$('.my-colorpicker2').on('colorpickerChange', function(event) {
			$('.my-colorpicker2 .fa-square').css('color', event.color.toString());
		})

		$("input[data-bootstrap-switch]").each(function(){
			$(this).bootstrapSwitch('state', $(this).prop('checked'));
		})

	})
	// BS-Stepper Init
	document.addEventListener('DOMContentLoaded', function () {
		window.stepper = new Stepper(document.querySelector('.bs-stepper'))
	})

	// DropzoneJS Demo Code Start
	Dropzone.autoDiscover = false

	// Get the template HTML and remove it from the doumenthe template HTML and remove it from the doument
	var previewNode = document.querySelector("#template")
	previewNode.id = ""
	var previewTemplate = previewNode.parentNode.innerHTML
	previewNode.parentNode.removeChild(previewNode)

	var myDropzone = new Dropzone(document.body, { // Make the whole body a dropzone
		url: "/target-url", // Set the url
		thumbnailWidth: 80,
		thumbnailHeight: 80,
		parallelUploads: 20,
		previewTemplate: previewTemplate,
		autoQueue: false, // Make sure the files aren't queued until manually added
		previewsContainer: "#previews", // Define the container to display the previews
		clickable: ".fileinput-button" // Define the element that should be used as click trigger to select files.
	})

	myDropzone.on("addedfile", function(file) {
		// Hookup the start button
		file.previewElement.querySelector(".start").onclick = function() { myDropzone.enqueueFile(file) }
	})

	// Update the total progress bar
	myDropzone.on("totaluploadprogress", function(progress) {
		document.querySelector("#total-progress .progress-bar").style.width = progress + "%"
	})

	myDropzone.on("sending", function(file) {
		// Show the total progress bar when upload starts
		document.querySelector("#total-progress").style.opacity = "1"
		// And disable the start button
		file.previewElement.querySelector(".start").setAttribute("disabled", "disabled")
	})

	// Hide the total progress bar when nothing's uploading anymore
	myDropzone.on("queuecomplete", function(progress) {
		document.querySelector("#total-progress").style.opacity = "0"
	})

	// Setup the buttons for all transfers
	// The "add files" button doesn't need to be setup because the config
	// `clickable` has already been specified.
	document.querySelector("#actions .start").onclick = function() {
		myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED))
	}
	document.querySelector("#actions .cancel").onclick = function() {
		myDropzone.removeAllFiles(true)
	}

	// DropzoneJS Demo Code End
</script>