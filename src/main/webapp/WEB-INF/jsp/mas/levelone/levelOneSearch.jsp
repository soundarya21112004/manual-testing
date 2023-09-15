<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<%--<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">--%>

<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>


<script>
    $(document).ready(function() {
        $('#caseAssign').DataTable({
            // "buttons": true,
            "searching": false,
            "bSort": false,
            "paging": true,
            "lengthMenu": [ 20, 30, 40 ,50 ],
            "pagingType": "simple",
            "language": {
                "info": "Showing page _PAGE_ of _PAGES_"
            },
            "dom": "<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6 '<'float-right'i>>>" +
                    "<'row'<'col-sm-12'tr>>" +
                "<'row'<'col-sm-12 col-md-5'<'float-left'p>><'col-sm-12 col-md-7'f>>"
        });
    } );
</script>

<script>
    function submitCase() {

            document.getElementById('submitcase').action = "/refreshNewCase";
            document.getElementById('submitcase').submit();

    }
    function alertSubmit(){
        swal.fire({
            title: "Submit all cases?",
            // text: "Once deleted, you will not be able to recover this imaginary file!",
            icon: "question",
            showCancelButton: true,
            // dangerMode: true,
        })
            .then((result) => {
                if (result.isConfirmed) {
                    submitCase();
                } else {
                    swal.fire({icon: "warning", title: "cancelled"});
                }
            });
    }
</script>
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
            <div class="col-sm-6"></div>
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="dashBoard">Home</a></li>
                    <li class="breadcrumb-item active">Operators Search</li>
                </ol>
            </div>
        </div>
    </div>
</section>
<script>
    window.onload = function () {
        checkwindow();
    }
    function checkwindow() {
        if (window.name != 'appname')
            window.location = "/loginPage";
    }
</script>
<c:if test="${successMessage != null}">
<script>
    Swal.fire({
        icon: "success",
        title: 'Success!',
        text: '${successMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${faliureMessage != null}">
<script>
    Swal.fire({
        icon: "error",
        title: 'Faliure!',
        text: '${faliureMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${errorMessage != null}">
<script>

    Swal.fire({
        icon: "warning",
        title: 'Processing!',
        text: '${errorMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>

<section class="content">
    <div class="container-fluid">
        <form id="submitcase" method="get" action="/levelOneSearch">
            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">List Of Subject</h3>
                            <span class="float-right" >No of Candidates : ${galleryList.size()}</span>
                        </div>
<%--                                    <br>--%>
                        <div modelAttribute="galleryList">
                            <div class="card-body">
                                <table id="caseAssign" class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>Sno</th>
                                            <th>Registration Id</th>
                                            <th>Candidate Reference Id</th>
                                            <th>Reason</th>
                                            <th>Created Date</th>
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
                                                <td>
                                                    <fmt:formatDate value="${emp.createdDate}" type="date"
                                                                    pattern="dd-MMM-yyyy"/></td>
                                                <td>
                                                    <c:out value="${galleryList.size()}"></c:out>
                                                   
                                                    <c:choose>
                                                        <c:when test="${emp.op1userId != userid && emp.op2userId != userid}">
                                                            <a href="<c:url value="leveloneSearchByName">
                                                         <c:param name="id" value="${emp.sno}"></c:param>
                                                         <c:param name="probe" value="${emp.regId}"></c:param>
                                                         <c:param name="candidate" value="${emp.matchedRefId}"></c:param>
                                                        <c:param name="requestId" value="${emp.reqid}"></c:param>
                                                        <c:param name="caseListNo" value="${counter.count} of ${galleryList.size()}"></c:param>
                                                          </c:url>">
                                                                <i class="nav-icon fas fa-edit" aria-hidden="true"></i>
                                                            </a>

                                                        </c:when>
                                                        <c:when test="${emp.op2verifyStatus == 'nohit'}">
                                                            <button type="submit" class="btn btn-success" >No Hit</button>
                                                        </c:when>
                                                        <c:when test="${emp.op2verifyStatus == 'hit'}">
                                                            <button type="submit" class="btn btn-danger" >Hit</button>
                                                        </c:when>
                                                        <c:when test="${emp.op1verifyStatus == 'nohit'}">
                                                            <button type="submit" class="btn btn-success" >No Hit</button>
                                                        </c:when>
                                                        <c:when test="${emp.op1verifyStatus == 'hit'}">
                                                            <button type="submit" class="btn btn-danger" >Hit</button>
                                                        </c:when>
                                                    </c:choose>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>
                                <div class="col-md-12">
                                    <button type="button" class="btn btn-primary float-right" onclick="alertSubmit()">SUBMIT</button>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
