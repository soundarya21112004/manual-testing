<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.3/jspdf.min.js"></script>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.6/jspdf.plugin.autotable.min.js"></script>
<script>function onClick() {
    console.log('hi');
    // var pdf = new jsPDF('p', 'mm',[400,300]);

    // pdf.canvas.height = 72 * 11;
    // pdf.canvas.width = 700;
    // margins = {
    //     top: 80,
    //     bottom: 60,
    //     left: 40,
    //     width: 700  ,margins.left,margins.top
    // };

    // pdf.fromHTML(document.getElementById("print-section"));
    //
    // pdf.save('test.pdf');
    var doc = new jsPDF('p', 'pt', 'letter');

    // var y = 20;
    // doc.setLineWidth(2);
    // doc.text(200, y = y + 30, "TOTAL MARKS OF STUDENTS");
    doc.autoTable({
        html: '#print-section',
        startY: 70,
        theme: 'grid',

        columnStyles: {
            0: {
                cellWidth: 450,
                halign : "left",
                valign: 'middle'
            },
            1: {
                cellWidth: 80,
                halign : "center",
                valign: 'middle'
            }
        },
        styles: {
            minCellHeight: 40,
            // cellPadding: 0.5,
            fontSize: 12

        }
    })
    doc.save('Reports.pdf')
};


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
<script>
    function hidefuncton() {
var hide= true;
return hide;
    }

    function showfuncton() {
        var hide= false;
        return hide;
    }
</script>
<%--}--%>

<section class="content">
    <div class="container-fluid">
        <!-- /.row -->
        <form method="post" action="/operatorReportsController" >

            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">REPORTS</h3>

                        </div><br>


                        <!-- /.card-header -->
                        <div modelAttribute="unverifiedOperator">
                            <button type="button" class="btn btn-default" style="margin-left: 20px" onclick="onClick()">download pdf</button>

                            <div class="card-body"  >
                                <select  class="col-md-4 form-control"  onchange="location = this.value;">
                                    <option selected="selected">select role</option>
                                    <option  value="operatorReportsController">Operator</option>
                                    <option  value="supervisorReportsController"> Supervisor</option>
                                </select>




<%--                                hidden="operatorReportsController"--%>
                                <table class="table table-bordered table-hover"  id="print-section" style="width: 100%;">
                                    <tr >

                                        <td>Number of cases to be verified </td>    <td><c:choose>
                                            <c:when test="${operatorDetail == true}">
                                                <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="1"></c:param>
										</c:url>">${unverified}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="1"></c:param>
										</c:url>">${unverified}</a>
                                            </c:otherwise>
                                        </c:choose>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Number of cases verified </td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                        <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="2"></c:param>
										</c:url>">${verified}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="2"></c:param>
										</c:url>">${verified}</a>
                                        </c:otherwise>
                                    </c:choose></td>
                                    </tr>
                                    <tr>
                                        <td>Number of cases deadlocked</td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                            <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="3"></c:param>
										</c:url>">${deadlocked}</a>
                                        </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="3"></c:param>
										</c:url>">${deadlocked}</a>
                                            </c:otherwise>
                                        </c:choose></td>
                                    </tr>
                                    <tr <c:if test="${hide == true}">hidden</c:if>>
                                        <td>Number of deadlocked cases verified by Supervisor</td><td><a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="4"></c:param>
										</c:url>">${verifiedDeadlocked}</a></td>
                                    </tr>
                                    <tr>
                                        <td>Number of HIT Cases </td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                            <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="5"></c:param>
										</c:url>">${hitcases}</a>
                                        </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="5"></c:param>
										</c:url>">${hitcases}</a>
                                            </c:otherwise>
                                        </c:choose></td>
                                    </tr>
                                    <tr <c:if test="${hide == true}">hidden</c:if> >
                                        <td>Number of HIT Cases sent to Fraud  </td><td><a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="6"></c:param>
										</c:url>">${hitCasesFraud}</a></td>
                                    </tr>
                                    <tr>
                                        <td>Number of No Hit Decisions  </td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                            <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="7"></c:param>
										</c:url>">${nohitDecisions}</a>
                                        </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="7"></c:param>
										</c:url>">${nohitDecisions}</a>
                                            </c:otherwise>
                                        </c:choose></td>
                                    </tr>
                                    <tr>
                                        <td>Number of Hit Decisions  </td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                            <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="8"></c:param>
										</c:url>">${hitDecisions}</a>
                                        </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="8"></c:param>
										</c:url>">${hitDecisions}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    </tr>

                                    <tr>
                                        <td>Number of verifications with demographic hits  </td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                            <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="9"></c:param>
										</c:url>">${demographic}</a>
                                        </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="9"></c:param>
										</c:url>">${demographic}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    </tr>
                                    <tr>
                                        <td>Number of verifications with biometric hits     </td><td>
                                        <c:choose>
                                        <c:when test="${operatorDetail == true}">
                                            <a href="<c:url value="operatorReportsDetails">
<c:param name="reports" value="10"></c:param>
										</c:url>">${biometric}</a> </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value="supervisorReportsDetails">
<c:param name="reports" value="10"></c:param>
										</c:url>">${biometric}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
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
