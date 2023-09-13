<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 13/07/2022
  Time: 4:51 PM
  To change this template use File | Settings | File Templates.
--%>

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
        $('#Table').DataTable({
            "bSort": false,
            "paging": true
        });
    } );
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
<c:if test="${faliureMessage != null}">
<script>
    Swal.fire({
        title: 'Faliure!',
        text: '${faliureMessage}',
        confirmButtonText: 'OK'
    });
</script>
</c:if>
<%--if(request.getAttribute("error")!=null){--%>
<%--<c:if test="${error != null}">--%>

<c:if test="${faliureMessage != null}">

<script>

    Swal.fire({
        title: 'error!',
        text: '${faliureMessage}',
        confirmButtonText: 'OK'
    });
</script></c:if>

<%--}--%>

<section class="content">
    <div class="container-fluid">
        <!-- /.row -->
        <form method="post" action="/commonReportsController" >

            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">REPORTS</h3>

                        </div><br>


                        <!-- /.card-header -->
                        <div modelAttribute="dupReports">
                            <div class="card-body">

                                <table id="Table" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>Sno </th>
                                        <th>Registration Id</th>

                                        <th>Number of Duplicates</th>


                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="dup" items="${dupReports}" varStatus="counter">
                                        <tr>
                                            <td>${counter.count}</td>
                                            <td>${dup.regId}</td>
                                            <td>${dup.count}</td>
                                            <!-- <i class="fa fa-times" aria-hidden="true"></i><i class="fa fa-plus" aria-hidden="true"></i> -->
                                        </tr>
                                    </c:forEach>


                                    </tbody>
                                </table>


                            </div>





                        </div>
                    </div>
                </div>

            </div>
        </form></div>
    <!-- /.container-fluid -->
</section>

