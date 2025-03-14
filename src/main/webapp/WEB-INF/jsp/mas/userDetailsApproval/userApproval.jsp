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
                    <li class="breadcrumb-item active">user approval Search</li>
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
        title: 'error!',
        text: '${failureMessage}',
        confirmButtonText: 'OK'
    });
</script></c:if>

<%--}--%>

<section class="content">
    <div class="container-fluid">
        <!-- /.row -->
        <form method="post" action="/userApproval" >

            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">List Of Subject</h3>

                        </div><br>
                        <div class="card-header">
                            <%--                        <h3 class="card-title">Operators</h3><br>--%>
                            <%--                        <select name="Operators">--%>
                            <%--                            <a href >Operator 1</a><br>--%>
                            <%--                            <a href >Operator 2</a>--%>
                            <%--                        </select>--%>
                        </div>

                        <!-- /.card-header -->
                        <div modelAttribute="userDetails">
                            <div class="card-body">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>Sno</th>
                                        <th>User Id</th>
                                        <th>user Name</th>
                                        <th>Email</th>
                                        <th>Designation</th>

                                        <th>Approval</th>



                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="emp" items="${userDetails}" varStatus="counter">

                                        <tr>

                                            <td>${counter.count}</td>
                                            <td>${emp.userid}</td>
                                            <td>${emp.firstnameEn} </td>
                                            <td>${emp.email} </td>
                                            <td>${emp.designation} </td>

                                            <td><a href="<c:url value="userApproveDecline">
                                           <c:param name="status" value="approved"> </c:param>
                                            <c:param name="userid" value="${emp.userid}"> </c:param>
                                           </c:url>"> <button type="button" class="btn btn-info">APPROVE</button></a>
                                                <a href="<c:url value="userApproveDecline">
                                                <c:param name="status" value="declined"></c:param>
                                                 <c:param name="userid" value="${emp.userid}"> </c:param>
                                           </c:url>"> <button type="button" class="btn btn-info">DECLINE</button></a>
                                            </td>



<%--                                            <td><a href="<c:url value="leveloneSearchByName">--%>
<%--<c:param name="id" value="${emp.sno}"></c:param>--%>
<%--<c:param name="probe" value="${emp.regId}"></c:param>--%>
<%--<c:param name="candidate" value="${emp.matchedRefId}"></c:param>--%>
<%--<c:param name="matchingScore" value="${emp.matchingScore}"></c:param>--%>

<%--										</c:url>">--%>

<%--                                                <i class="nav-icon fas fa-edit" aria-hidden="true"></i>--%>
<%--                                            </a></td>--%>

                                            <!-- <i class="fa fa-times" aria-hidden="true"></i><i class="fa fa-plus" aria-hidden="true"></i> -->


                                        </tr>
                                    </c:forEach>


                                    </tbody>
                                </table>
                            </div>




                            <!-- /.card-body -->
                            <div class="card-footer clearfix">
                                <ul class="pagination pagination-sm m-0 float-right">
                                    <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
                                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </form></div>
    <!-- /.container-fluid -->
</section>
</html>