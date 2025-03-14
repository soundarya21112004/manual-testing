<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<script>
    function validate() {

        $('#quickForm').validate({
            rules: {
                rid: {
                    required: true
                }
            },
            messages: {
                rid: {
                    required: "Please provide FileName"
                }
            },
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
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
            <div class="col-sm-6">
                <h1>User Creation </h1>
            </div>
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="dashBoard">Home</a></li>
                    <li class="breadcrumb-item active">User Creation</li>
                </ol>
            </div>
        </div>
    </div>
    <!-- /.container-fluid -->
</section>

<c:if test="${successMessage != null}">
<script>
    Swal.fire({
        title: 'Success!',
        text: '${successMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${failureMessage != null}">
<script>
    Swal.fire({
        title: 'failure!',
        text: '${failureMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<%--if(request.getAttribute("error")!=null){--%>
<%--<c:if test="${error != null}">--%>

<c:if test="${failureMessage != null}">

<script>

    Swal.fire({
        title: 'Error!',
        text: '${failureMessage}',
        confirmButtonText: 'OK'
    });
</script></c:if>


<c:if test="${errorMessage != null}">
<script>
    swal({
        title: 'Sorry!',
        confirmButtonColor: '#006a4d',
        text: '${errorMessage}',
        type: 'error'
    });
</script>
</c:if>

<script>
    function submitForm() {
        var mail = document.getElementById('email').value;
        $.ajax({
            type: "GET",
            datatype: "json",
            url: "exitsemail?email=" + mail,
            success: function (result) {
                if (result == true) {
                    Swal.fire("Email already exist");
                    document.getElementById("email").value = "";
                    return false;
                }
            }
        })
    }
</script>

<!-- Main content -->
<section class="content">
    <div class="container-fluid">
        <!-- SELECT2 EXAMPLE    action="createUser" method="POST" -->
        <form id="quickForm"  modelAttribute="mstRolegfsBean" action="createUser" method="POST" >
<%--        <form id="quickForm"  modelAttribute="mstRolegfsBean" id="usercreate">--%>
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">Add User</h3>


                    <div class="card-tools">
                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                            <i class="fas fa-minus"></i>
                        </button>
                        <button type="button" class="btn btn-tool" data-card-widget="remove">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row">
                        <input type="hidden" name="mstRoleGroup.groupId"
                               value="${groupId}"/> <input type="hidden"
                                                           name="mstRoleGroup.groupName" value="${groupName}"/>
                        <div class="col-md-6" style="height: 205px">
                            <div class="form-group" style="height: 102px; margin-bottom: 5px;">
                                <label>First Name</label>
                                <input class="form-control" type="text" name="userdetails.firstnameEn" required id="firstname"
                                       onkeypress="return onlyAlphabets(event);" autocomplete="nope"
                                       onkeyup=" autoCaps(this); spaceNotAllowed(this); "
                                       placeholder="Enter First Name">

                            </div>

                            <div class="form-group" style="height: 102px; margin-bottom: 5px;">
                                <label>Last Name</label>
                                <input class="form-control" required type="text" placeholder="Enter Last Name"
                                       name="userdetails.lastnameEn" id="lastname"
                                       onkeypress="return onlyAlphabets(event);" autocomplete="nope"
                                       onkeyup=" autoCaps(this); spaceNotAllowed(this); " >
                            </div>

                        </div>

                        <div class="col-md-6" style="height: 205px">
                            <div class="form-group" style="height: 102px; margin-bottom: 5px;">
                                <label>Middle Name</label>
                                <input type="text" class="form-control" required placeholder="Enter Middle Name"
                                       name="userdetails.middleName" id="middlename"
                                       onkeypress="return onlyAlphabets(event);" autocomplete="nope"
                                       onkeyup=" autoCaps(this); spaceNotAllowed(this); ">
                            </div>

                            <div class="form-group" style="height: 102px; margin-bottom: 5px;">
                                <label>Organization</label>
                                <input type="text" class="form-control required" required
                                       name="userdetails.organisation" id="organization"
                                       onkeypress="return onlyAlphabetsWs(event);" placeholder="Enter Organization"
                                       autocomplete="off"
                                       onkeyup=" AllowSingleSpaceNotInFirstAndLast(this); autoCaps(this);"/>
                            </div>

                        </div>

                        <div class="col-md-6" style="height: 205px">
                            <div class="form-group" style="height: 102px; margin-bottom: 5px;">
                                <label>Email</label>
                                <input type="email" class="form-control required" pattern="([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\.[A-Za-z0-9]+)+)" required
                                       name="userdetails.email" id="email" autocomplete="nope" onchange="submitForm();"
                                       onkeyup="return spaceNotAllowed(this);" placeholder="Enter email">
                            </div>
<%--                            onkeypress="return onlyAlphabets(event);" autocomplete="off"--%>
<%--                            onkeyup=" spaceNotAllowed(this); autoCaps(this);"--%>
							<div class="form-group" style="height: 102px; margin-bottom: 5px;">
								<label>Designation</label>
								<select type="text" class="form-control required" required name="userdetails.designation"
									   id="designation"

									   placeholder=" Enter Designation ">
<%--                                    <option value="default">---choose an option---</option>--%>
                                    <option value="OPERATOR">OPERATOR</option>
                                    <option value="SUPERVISOR">SUPERVISOR</option>

                                    <option value="FRAUD">FRAUD MANAGEMENT</option>

                                    <option value="ADMIN">ADMIN</option>

                                </select>
							</div>
                        </div>
                        <div class="col-md-6" style="height: 205px">
							<div class="form-group" style="height: 102px; margin-bottom: 5px;">
								<label>Mobile No</label>
<%--                                onchange="onlyNum('contactnumber'); phoneValidate();"--%>
								<input type="text" class="form-control required"  minlength="10" maxlength="10" pattern="(?!0+$)(?!1+$)(?!2+$)(?!3+$)(?!4+$)(?!5+$)(?!6+$)(?!7+$)(?!8+$)(?!9+$)\d{10}$" required name="userdetails.contactnumber"
									   id="contactnumber"
									    autocomplete="nope"

									   onkeypress="return isNumberKey(event);" placeholder=" Enter Mobile No">
							</div>
                            <div class="form-group" style="height: 102px; margin-bottom: 5px;">
                                <label>Group</label>

                                    <select  class="form-control" required name="groupId"  id="groupName"
                                    <c:forEach items="${loadRoleGroup}" var="element1">
                                        <option value="${element1.groupId}">${element1.groupName}</option>
                                    </c:forEach>
                                    </select>

                            </div>

                        </div>

                    </div>


                </div>

            </div>


            <div class="card-footer">
                <button type="submit" onclick="checkPhone();" id="validate"  class="btn btn-info">Submit</button>
                <button type="reset" class="btn btn-default float-right">Cancel</button>
            </div>


        </form>
    </div>
</section>
<%--onclick="validateOnsubmit();" id="validate"checkEmail(); validateOnsubmit();--%>



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
<!-- AdminLTE for demo purposes    -->
<%--    <%=request.getContextPath()%>--%>
<script src="../../dist/js/demo.js"></script>
<script language="javascript">

    function checkEmail() {
        var email = document.getElementById('email');
        var filter = /^[a-z0-9._%+-]+@[a-z0-9-]+\.[a-z]{3,3}$/;

        if (!filter.test(email.value)) {
            alert('Please provide a valid email address');
            email.focus;
            return false;
        }
    }
    function checkPhone()
    {
        var phone = document.getElementById('contactnumber');
        var phoneno = /^\d{10}$/;
        if(phone.value.match(phoneno))
        {
            return true;
        }
        else
        {
            // alert("message");
            return false;
        }
    }
</script>
<script type="text/javascript">
    function validateOnsubmit() {
        alert("validate on submit")
        if ($('#validate').valid()) {
            alert("validate ");
            swal({
                title: 'Are you sure?',
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#006a4d",
                confirmButtonText: "Yes, Confirm!",
                closeOnConfirm: false,
                showLoaderOnConfirm: true
            }, function () {
                setTimeout(function () {

                    document.getElementById("validate").action = "<%=request.getContextPath()%>/createUser";
                    document.getElementById("validate").submit();
                }, 1000);
            });
            return false;
        }
        alert("validate submit");
    }

    function isNumber(evt) {
        var iKeyCode = (evt.which) ? evt.which : evt.keyCode
        if (iKeyCode != 46 && iKeyCode > 31 && (iKeyCode < 48 || iKeyCode > 57))
            return false;

        return true;
    }

    function isNumberKey(evt){
        var charCode = (evt.which) ? evt.which : event.keyCode;
        return !(charCode > 31 && (charCode < 48 || charCode > 57));
    }

    function phoneValidate() {
        var phone = document.getElementById("contactnumber").value;
        $.ajax({
            type: "GET",
            datatype: "json",
            url: "contactnum/" + phone,
            success: function (result) {
                if (result == phone) {
                    swal("Mobile number already exist");
                    document.getElementById("contactnumber").value = "";
                    return false;
                }
            }
        })
    }
//  || (charCode == 32)
    function onlyAlphabets(e) {
        try {
            if (window.event) {
                var charCode = window.event.keyCode;
            } else if (e) {
                var charCode = e.which;
            } else {
                return true;
            }

            if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || (e.keyCode == 8) || (e.keyCode == 9)) {
                return true;
            } else {
                return false;
            }
        } catch (err) {
            alert(err.Description);
        }
    }

    function onlyAlphabetsWs(e) {
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
        $th.val($th.val().toLowerCase().replace(/\b[a-z]/g, function (letter) {
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
            var mob = $this.val();
            var ex = /\D/g;
            if (!mob.match(ex)) {
                document.getElementById("contactnumber").value = mob;
            } else {
                document.getElementById("contactnumber").value = "";
            }
        }
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
        $('#datemask').inputmask('dd/mm/yyyy', {'placeholder': 'dd/mm/yyyy'})
        //Datemask2 mm/dd/yyyy
        $('#datemask2').inputmask('mm/dd/yyyy', {'placeholder': 'mm/dd/yyyy'})
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
                ranges: {
                    'Today': [moment(), moment()],
                    'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                    'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                    'This Month': [moment().startOf('month'), moment().endOf('month')],
                    'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                startDate: moment().subtract(29, 'days'),
                endDate: moment()
            },
            function (start, end) {
                $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
            }
        )

        //Timepicker
        $('#timepicker').datetimepicker({
            format: 'LT'
        })

        //Bootstrap Duallistbox
        $('.duallistbox').bootstrapDualListbox()

        //Colorpicker
        $('.my-colorpicker1').colorpicker()
        //color picker with addon
        $('.my-colorpicker2').colorpicker()

        $('.my-colorpicker2').on('colorpickerChange', function (event) {
            $('.my-colorpicker2 .fa-square').css('color', event.color.toString());
        })

        $("input[data-bootstrap-switch]").each(function () {
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

    myDropzone.on("addedfile", function (file) {
        // Hookup the start button
        file.previewElement.querySelector(".start").onclick = function () {
            myDropzone.enqueueFile(file)
        }
    })

    // Update the total progress bar
    myDropzone.on("totaluploadprogress", function (progress) {
        document.querySelector("#total-progress .progress-bar").style.width = progress + "%"
    })

    myDropzone.on("sending", function (file) {
        // Show the total progress bar when upload starts
        document.querySelector("#total-progress").style.opacity = "1"
        // And disable the start button
        file.previewElement.querySelector(".start").setAttribute("disabled", "disabled")
    })

    // Hide the total progress bar when nothing's uploading anymore
    myDropzone.on("queuecomplete", function (progress) {
        document.querySelector("#total-progress").style.opacity = "0"
    })

    // Setup the buttons for all transfers
    // The "add files" button doesn't need to be setup because the config
    // `clickable` has already been specified.
    document.querySelector("#actions .start").onclick = function () {
        myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED))
    }
    document.querySelector("#actions .cancel").onclick = function () {
        myDropzone.removeAllFiles(true)
    }
    // DropzoneJS Demo Code End
</script>
</html>