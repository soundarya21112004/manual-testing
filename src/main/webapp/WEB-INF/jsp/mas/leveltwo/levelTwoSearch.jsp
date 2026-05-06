<script type="text/javascript" src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>


<script>
    document.addEventListener("DOMContentLoaded", function () {
        let startDateInput = document.getElementById('startDate');
        let endDateInput = document.getElementById('endDate');
        const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

        // Set initial min and max attributes for Start and End Date
        startDateInput.setAttribute('max', today);
        startDateInput.setAttribute('min', '2020-01-01');
        endDateInput.setAttribute('max', today);
        endDateInput.setAttribute('min', '2020-01-01');

        // Update min attribute of End Date when Start Date changes
        startDateInput.addEventListener('change', function () {
            if (new Date(startDateInput.value) > new Date(today)) {
                Swal.fire('Invalid Date', 'The Start Date cannot be in the future.');
                startDateInput.value = ""; // Reset the value
                endDateInput.setAttribute('min', ''); // Reset min for End Date
                endDateInput.value = "";
                endDateInput.disabled = true;
            }
        });

        // Validate End Date
        endDateInput.addEventListener('change', function () {
            if (new Date(endDateValue.value) > new Date(today)) {
                Swal.fire('Invalid Date', 'The End Date cannot be in the future.');
                endDateInput.value = ""; // Reset the value
            }
        });
    });

    function validateDates() {
        let startDate = document.getElementById('startDate');
        let endDate = document.getElementById('endDate');
        let submitButton = document.getElementById('submitButton');

        // Enable the End Date input only if the Start Date is selected
        if (startDate.value) {
            endDate.disabled = false;
        } else {
            endDate.disabled = true;
            endDate.value = ""; // Clear the end date if Start Date is removed
            submitButton.disabled = true; // Disable Submit button as well
        }
    }

    function checkSubmitButton() {
        const startDate = document.getElementById('startDate');
        const endDate = document.getElementById('endDate');
        const submitButton = document.getElementById('submitButton');

        // Enable the Submit button only if both dates are selected
        if (startDate.value && endDate.value) {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        }
    }

    $(document).ready(function () {
// Get the index of the current row and total number of rows
        var rowIndex = 0; // Zero-based index
        var totalRows;
        var table = $('#example').DataTable({
            "searching": true,
            "bSort": false,
            "paging": true,
            "server-side": true,
            // "dom": '<"top"f>rt<"bottom"lp><"clear">',
            "ajax": {
                "url": "<c:url value='loadLevelTwoData'/>",
                "type": "GET",
                "dataSrc": function (json) {
                    totalRows = json.data.length;
                    return json.data; // Return the data array
                }
            },
            "columns": [
                {"data": "sno"},
                {"data": "regId"},
                {"data": "matchedRefId"},
                {"data": "reason", "defaultContent": "Biometric Potential Match"},
                {
                    "data": "regType",
                    "render": function (data, type, row) {
                        return (data && data.toLowerCase() === "update") ? "Update" : "New";
                    }
                },
                {"data": "op1UpdBy"},
                {"data": "op2UpdBy"},
                {"data": "supervisorUpdBy"},
                {
                    "data": null,
                    "render": function (data, type, row) {
                        const date = row.supervisorUpdatedDate || row.op2UpdatedDate || '';
                        return date ? moment(date).format('DD-MM-YYYY') : '';
                    }
                },
                {
                    "data": null,
                    "render": function (data, type, row) {
                        const date = row.createdDate;
                        return date ? moment(date).format('DD-MM-YYYY') : '';
                    }
                },
                {

                    "data": null,
                    "render": function (data, type, row) {
                        // Base URL
                        var baseUrl = "<c:url value='leveltwoSearchByName'/>";
                        // Construct URL with encoded parameters
                        var url = baseUrl +
                            '?id=' + encodeURIComponent(row.sno || '') +
                            '&probe=' + encodeURIComponent(row.regId || '') +
                            '&candidate=' + encodeURIComponent(row.matchedRefId || '') +
                            '&requestId=' + encodeURIComponent(row.reqid || '') +
                            '&op1Comment=' + encodeURIComponent(row.op1Comment || '') +
                            '&op1verifyStatus=' + encodeURIComponent(row.op1verifyStatus || '') +
                            '&op2Comment=' + encodeURIComponent(row.op2Comment || '') +
                            '&op2verifyStatus=' + encodeURIComponent(row.op2verifyStatus || '') +
                            '&supervisorComment=' + encodeURIComponent(row.supervisorComment || '') +
                            '&supervisorVerifyStatus=' + encodeURIComponent(row.supervisorVerifyStatus || '') +
                            '&caseListNo=' + encodeURIComponent(++rowIndex + ' of ' + totalRows) +
                            '&typeOfView=' + encodeURIComponent('listView');
                        // '&caseListNo=' + encodeURIComponent(row(this).index()+' of '+table.rows().data().length || '');

                        return '<a href="' + url + '">' +
                            '<i class="nav-icon fas fa-edit" aria-hidden="true"></i>' +
                            '</a>';
                    }
                }

            ]

        });

        // Function to enable Load More button when "Next" is disabled
        function updateLoadMoreButtonState() {
            const loadMoreButton = document.getElementById('loadMoreButton');
            const pageInfo = table.page.info();
            const isLastPage = pageInfo.page === pageInfo.pages - 1; // Current page equals the last page

            // Enable the Load More button if on the last page
            loadMoreButton.disabled = !isLastPage;
        }

        table.on('page', function () {
            updateLoadMoreButtonState();
        });

        // Set initial state for Load More button
        updateLoadMoreButtonState();

    });

    let offSet = 0;

    function getValueByDate() {

        let startDateInput = document.getElementById('startDate');
        let endDateInput = document.getElementById('endDate');

        if (new Date(endDateInput.value) < new Date(startDateInput.value)) {
            Swal.fire('Invalid Date', 'The End Date cannot be earlier than the Start Date.');
            endDateInput.value = ""; // Reset the value
        } else {
            var table = $('#example').DataTable();

            // Get the form values
            const startDate = document.getElementById('startDate').value;
            const endDate = document.getElementById('endDate').value;
            const operator1 = document.getElementById('mySelectone').value;
            const operator2 = document.getElementById('secondSelect').value;
            const selecedRadio = document.getElementsByName('dateType');
            let dateType = '';
            for (const radio of selecedRadio) {
                if (radio.checked) {
                    dateType = radio.value;
                    break;
                }
            }
            // Make an AJAX call to send data to the backend
            $.ajax({
                url: "<c:url value='loadDataTwo'/>",  // Backend endpoint (avoid JSP-specific tags)
                type: "GET",  // Use GET or POST depending on your backend setup
                data: {
                    startDate: startDate,
                    endDate: endDate,
                    operator1: operator1,
                    operator2: operator2,
                    dateType: dateType,
                    offSet: 0,
                },
                success: function (response) {
                    // Handle successful response (e.g., populate table with data)
                    if (response && response.data) {
                        table.clear();// Clear any existing rows
                        table.rows.add(response.data);// Add new data to the table;
                        if (response.data.length > 0) {
                            Swal.fire('Data Fetched', 'Data Fetched Successfully.');
                        } else {
                            Swal.fire('No Data', 'No data available for the selected criteria.');
                        }
                        table.draw(false);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    Swal.fire('Error!', 'Failed to load data.');
                }
            });
        }
        offSet = 0;
    }

    // Initialize the DataTable outside of the AJAX call


    function getValueByDateLoadMore() {
        // Initialize the DataTable outside of the AJAX call
        var table = $('#example').DataTable();

        // Get the form values
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
        const operator1 = document.getElementById('mySelectone').value;
        const operator2 = document.getElementById('secondSelect').value;
        const selecedRadio = document.getElementsByName('dateType');
        let dateType = '';
        for (const radio of selecedRadio) {
            if (radio.checked) {
                dateType = radio.value;
                break;
            }
        }
        // Make an AJAX call to send data to the backend
        $.ajax({
            url: "<c:url value='loadDataTwo'/>",  // Backend endpoint (avoid JSP-specific tags)
            type: "GET",  // Use GET or POST depending on your backend setup
            data: {
                startDate: startDate,
                endDate: endDate,
                operator1: operator1,
                operator2: operator2,
                dateType: dateType,
                offSet: ++offSet,
            },
            success: function (response) {
                // Handle successful response (e.g., populate table with data)
                if (response && response.data) {
                    table.rows.add(response.data);
                    table.draw(false);
                    if (response.data.length == 0) {
                        Swal.fire('No Data', 'No More Data.');
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                Swal.fire('Error!', 'Failed to load data.');
            }
        });
    }


</script>

<script>
    function validate() {
        $('#quickForm').validate({
            rules: {
                rid: {required: true}
            },
            messages: {
                rid: {required: "Please provide FileName"}
            },
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element) {
                $(element).removeClass('is-invalid');
            }
        });
        if ($('#quickForm').valid()) {
            var filename = document.getElementById("filename").value;
            if (filename.length && filename.length !== 29) {
                Swal.fire("Invalid File Name");
                return false;
            }
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
        let startDateInput = document.getElementById('startDate');
        let endDateInput = document.getElementById('endDate');
        let today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

        // Set initial min and max attributes for Start and End Date
        startDateInput.setAttribute('max', today);
        startDateInput.setAttribute('min', '2020-01-01');
        endDateInput.setAttribute('max', today);
        endDateInput.setAttribute('min', '2020-01-01');

        // Update min attribute of End Date when Start Date changes
        startDateInput.addEventListener('change', function () {
            if (new Date(startDateInput.value) > new Date(today)) {
                Swal.fire('Invalid Date', 'The Start Date cannot be in the future.');
                startDateInput.value = ""; // Reset the value
                endDateInput.setAttribute('min', ''); // Reset min for End Date
            }
        });

        // Validate End Date
        endDateInput.addEventListener('change', function () {
            if (new Date(endDateInput.value) > new Date(today)) {
                Swal.fire('Invalid Date', 'The End Date cannot be in the future.');
                endDateInput.value = ""; // Reset the value
            }
        });
    });
    g


    function resetValue() {
        document.getElementById("myForm").reset();

        // Reset dropdowns to their default selected option
        document.getElementById("mySelectone").selectedIndex = 0;
        document.getElementById("secondSelect").selectedIndex = 0;

        // Disable the Submit button
        document.getElementById("submitButton").disabled = true;

        // Disable the End Date field
        document.getElementById("endDate").disabled = true;
    }

    const dropdown1 = document.getElementById("mySelectone");
    const dropdown2 = document.getElementById("secondSelect");


    dropdown1.addEventListener('change', function () {
        const selectedValue = dropdown1.value;
        disableSelectedOption(selectedValue);
    });

    dropdown2.addEventListener('change', function () {
        const selectedValue = dropdown2.value;
        disableSelectedOption1(selectedValue);
    });

    function disableSelectedOption(value) {

        for (let option of dropdown2.options) {
            if (option.value === value) {
                option.disabled = true;
            }
        }
    }

    function disableSelectedOption1(value) {

        for (let option of dropdown1.options) {
            if (option.value === value) {
                option.disabled = true;
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
        title: 'Success!',
        text: '${successMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${failureMessage != null}">
<script>
    Swal.fire({
        title: 'Failure!',
        text: '${failureMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>


<section class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card card-primary">
                    <div class="card-header">

                        <h3 class="card-title">Supervisor Search</h3>
                    </div>

                    <form id="myForm" style="padding: 1rem !important;">
                        <div class="row">

                            <div class="col-12 pb-2">
                                <lable style="font-weight: bold">Filter data based on :</lable> &nbsp;&nbsp;
                                <input type="radio" value="verifiedDate" id="radio1" name="dateType" checked> <label
                                    for="radio1" style="font-weight: initial">Verified Date</label></input> &nbsp;
                                &nbsp;
                                <input type="radio" value="createdDate" id="radio2" name="dateType"> <label for="radio2"
                                                                                                            style="font-weight: initial">Created
                                Date</label></input>
                            </div>

                            <div class="col-3 pb-2">
                                <p>Start Date</p>
                                <input type="date" onchange="validateDates()" name="startDate" class="form-control"
                                       id="startDate">
                            </div>
                            <div class="col-3 pb-2">
                                <p>End Date</p>
                                <input type="date" name="endDate" class="form-control" id="endDate" disabled
                                       onchange="checkSubmitButton()">
                            </div>
                            <div class="col-3 form-group pb-2">
                                <p>Operator1</p>
                                <div class="select-with-clear">
                                    <select class="form-select form-control" id="mySelectone">
                                        <option value="" disabled selected>Select Operator1</option>
                                        <c:forEach var="operator" items="${operators}">
                                            <option value="${operator}">${operator}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-3 form-group pb-2">
                                <p>Operator2</p>
                                <div class="select-with-clear">
                                    <select class="form-select form-control" id="secondSelect">
                                        <option value="" disabled selected>Select Operator2</option>
                                        <c:forEach var="operator" items="${operators}">
                                            <option value="${operator}">${operator}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="col-6 mb-2">
                                <button type="reset" class="btn btn-danger float-left" id="resetButton"
                                        onclick="resetValue()">Reset
                                </button>
                            </div>
                            <div class="col-6 mb-2">
                                <button class="btn btn-success float-right" type="button" onclick="getValueByDate()"
                                        id="submitButton" disabled>Submit
                                </button>
                            </div>
                        </div>
                    </form>


                </div>
            </div>
        </div>
    </div>

</section>


<section class="content">
    <div class="container-fluid">

        <!-- /.row -->
        <div class="row">
            <div class="col-md-12" style="top: 0">
                <div class="card card-primary">
                    <div class="card-header">
                        <h3 class="card-title">List Of Subject</h3>
                    </div>

                    <form:form modelAttribute="galleryList">
                        <div class="card-body">
                            <table id="example" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Sno</th>
                                    <th>Registration Id</th>
                                    <th>Candidate Reference Id</th>
                                    <th>Reason</th>
                                    <th>Registration Type</th>
                                    <th>Verified By Operator 1</th>
                                    <th>Verified By Operator 2</th>
                                    <th>Verified By Supervisor</th>
                                    <th>Verified Date</th>
                                    <th>Created Date</th>
                                    <th>Process</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                        <div class="text-right mb-3" style="margin-right: 20px;">
                            <button class="btn btn-primary align-content-center" type="button" id="loadMoreButton"
                                    onclick="getValueByDateLoadMore()">
                                Load More
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</section>

</html>