<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        title: 'Success!',
        text: '${successMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${faliureMessage != null}">
<script>
    Swal.fire({
        title: 'Faliure!',
        text: '${faliureMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<c:if test="${errorMessage != null}">
<script>

    Swal.fire({
        title: 'Processing!',
        text: '${errorMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>

<section class="content">
    <div class="container-fluid">
        <form method="post" action="/levelOneSearch">
            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">List Of Subject</h3>
                        </div>
                                    <br>
                        <div modelAttribute="galleryList">
                            <div class="card-body">
                                <table class="table table-bordered table-hover">
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
                                                    <a href="<c:url value="leveloneSearchByName">
                                                    <c:param name="id" value="${emp.sno}"></c:param>
                                                    <c:param name="probe" value="${emp.regId}"></c:param>
                                                    <c:param name="candidate" value="${emp.matchedRefId}"></c:param>
                                                    <c:param name="requestId" value="${emp.reqid}"></c:param>
                                                    </c:url>">
                                                    <i class="nav-icon fas fa-edit" aria-hidden="true"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
