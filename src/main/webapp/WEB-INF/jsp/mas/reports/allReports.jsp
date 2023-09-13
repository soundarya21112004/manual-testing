<script src="plugins/sweetalert2/sweetalert2.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js">--%>
<%--</script>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.6/jspdf.plugin.autotable.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<%--<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">--%>

<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

<script>
    $(document).ready(function() {
        $('#print-section').DataTable({
            "bSort": false,
            "paging": true
        });
    } );
</script>
<script>function onClick() {



    var doc = new jsPDF('p', 'pt', 'letter');
    var columns = ['Sno', 'Registration id', 'Candidate Reference id'];

    var body = [];

    var count=1;
     <c:forEach var="emp" items="${reports}" varStatus="counter">
    var temp = [count++ , BigInt(${emp.regId}),BigInt(${emp.matchedRefId})];
    body.push(temp);

                                    </c:forEach>
    // console.log("seee"+temp)
    doc.autoTable( {
        columns,body, startY: 50,
        theme: 'grid',
        headStyles:{
            valign: 'middle',
            halign : 'center'
        },
        columnStyles: {
            0: {
                cellWidth: 50,
                halign : "center",
                valign: 'middle'
            },
            1: {
                cellWidth: 250,
                halign : "center",
                valign: 'middle'

            },
            2: {
                cellWidth: 250,
                halign : "center",
                valign: 'middle'

            }
        },
        styles: {
            minCellHeight: 30,
            // cellPadding: 0.5,
            fontSize: 12
        }
    })
    doc.save('Reports.pdf')
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
                    <li class="breadcrumb-item active">Reports</li>
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
        <form method="post" action="/allReportsController" >

            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">List of  un-verified Reports</h3>

                        </div><br>
<%--                        <div class="card-header">--%>
<%--                            &lt;%&ndash;                        <h3 class="card-title">Operators</h3><br>&ndash;%&gt;--%>
<%--                            &lt;%&ndash;                        <select name="Operators">&ndash;%&gt;--%>
<%--                            &lt;%&ndash;                            <a href >Operator 1</a><br>&ndash;%&gt;--%>
<%--                            &lt;%&ndash;                            <a href >Operator 2</a>&ndash;%&gt;--%>
<%--                            &lt;%&ndash;                        </select>&ndash;%&gt;--%>
<%--                        </div>--%>

                        <!-- /.card-header -->
                        <div modelAttribute="reports">
                            <button type="button" class="btn btn-default" style="margin-left: 20px" onclick="onClick()">download pdf</button>

                            <div class="card-body" >
<%--                                <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.3/jspdf.min.js"></script>--%>
                                <table id="print-section" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>Sno</th>
                                        <th>Registration Id</th>
                                        <th>Candidate Reference Id</th>







                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="emp" items="${reports}" varStatus="counter">

                                        <tr>

                                            <td>${counter.count}</td>
                                            <td>${emp.regId}</td>
                                            <td>${emp.matchedRefId} </td>





                                        </tr>
                                    </c:forEach>


                                    </tbody>
                                </table>
                            </div>




                            <!-- /.card-body -->
<%--                            <div class="card-footer clearfix">--%>
<%--                                <ul class="pagination pagination-sm m-0 float-right">--%>
<%--                                    <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>--%>
<%--                                    <li class="page-item"><a class="page-link" href="#">1</a></li>--%>
<%--                                    <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--                                    <li class="page-item"><a class="page-link" href="#">3</a></li>--%>
<%--                                    <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>--%>
<%--                                </ul>--%>
<%--                            </div>--%>
                        </div>
                    </div>
                </div>

            </div>
        </form></div>
    <!-- /.container-fluid -->
</section>
