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
        <form method="post" action="/commonReportsController" >

            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">REPORTS</h3>

                        </div><br>


                        <!-- /.card-header -->
                        <div modelAttribute="allUnverifiedReports">
                            <div class="card-body">

                                <table class="table table-bordered table-hover" style="width: 100%;">
                                    <tr>
                                        <td>Number of cases to be verified </td><td>${allUnverifiedReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of cases verified </td><td>${allverifiedReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of cases deadlocked</td><td>${deadlockedReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of deadlocked cases verified by Supervisor</td><td>${verifiedDeadlockedReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of HIT Cases </td><td>${hitcasesReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of HIT Cases sent to Fraud  </td><td>${hitCasesFraudReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of No Hit Decisions  </td><td>${nohitDecisionsReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of Hit Decisions  </td><td>${hitDecisionsReports}</td>
                                    </tr>

                                    <tr>
                                        <td>Number of verifications with demographic hits  </td><td>${demographicReports}</td>
                                    </tr>
                                    <tr>
                                        <td>Number of verifications with biometric hits     </td><td>${biometricReports}</td>
                                    </tr>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>Number of verification cases and their proof--%>
                                    <%--                                            of identity submittals (e.g. Birth Certificates,--%>
                                    <%--                                            MD, Barangay certificates and the like)        </td><td>${allUnverifiedReports}</td>--%>
                                    <%--                                    </tr>--%>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>Number of cases with biometric exemptions     </td><td>${allUnverifiedReports}</td>--%>
                                    <%--                                    </tr>--%>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>Number of cases with forced capture   </td><td>${allUnverifiedReports}</td>--%>
                                    <%--                                    </tr>--%>
                                </table>


                            </div>





                        </div>
                    </div>
                </div>

            </div>
        </form></div>
    <!-- /.container-fluid -->
</section>
</html>