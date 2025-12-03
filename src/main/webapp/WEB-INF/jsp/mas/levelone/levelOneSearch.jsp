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
        const table = $('#caseAssign').DataTable({
            // "buttons": true,
            "searching": true,
            "bSort": false,
            "paging": true,
            "lengthMenu": [20, 30, 40, 50],
            "pagingType": "simple",
            'language': {
                "info": "Showing page _PAGE_ of _PAGES_",
                'paginate': {
                    'previous': '<span>BACK</span>',
                    'next': '<span>NEXT</span>'
                }
            },
            // "language": {
            //     "info": "Showing page _PAGE_ of _PAGES_",
            //     'previous': '<h5>Back</h5>',
            //     'next': '<h5>Next</h5>'
        // },

            "dom":  "<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6 '<'float-right'i>>>" +
                    "<'row'<'col-sm-12'tr>>" +
                "<'row'<'col-sm-12 col-md-5'<'float-left'p>>>"
        });


        // It is used to change color when hover table
        // table.on('click', 'tbody tr', function (e) {
        //     e.currentTarget.classList.toggle('selected');
        // });

    });

   // function removebackground(){
   //      function removee(element, errorClass, validClass) {
   //          $(element).addClass('remove');
   //      }
   //  }

</script>
<style>
    /*.swal2-success-line-tip, .swal2-success-line-long {*/
    /*    font-size: 16px !important;*/
    /*}*/
    /*.swal2-x-mark-line-right, .swal2-x-mark-line-left {*/
    /*    font-size: 16px !important;*/
    /*}*/
    /*.icon-size{*/
    /*    font-size: 16px !important;*/
    /*}*/
    /*.remove{*/
    /*    background: none !important;*/
    /*}*/

    .selected{
        background-color: #efef54;
    }

    /*table#caseAssign   tbody tr:hover{*/
    /*    background:none !important;*/
    /*}*/
</style>

<script>


    function submitCase() {

            // document.getElementById('submitcase').action = "/MVS/refreshNewCase";
            document.getElementById('submitcase').action = "<c:url value='refreshNewCase'/>";
            document.getElementById('submitcase').submit();

    }
    function alertSubmit(){
        swal.fire({
            title: "Do you want to submit all the cases?",
            // text: "Once deleted, you will not be able to recover this imaginary file!",
            // icon: "question",
            showCancelButton: true,
            // dangerMode: true,
        })
            .then((result) => {
                if (result.isConfirmed) {
                    submitCase();
                } else {
                    // swal.fire({icon: "warning", title: "cancelled"});
                    swal.fire({title: "cancelled"});
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
        // icon: "success",
        title: 'Success!',
        text: '${successMessage}',
        confirmButtonText: 'OK',
        customClass: {
            icon: "icon-size"
        }
    });
</script>
</c:if>
<c:if test="${failureMessage != null}">
<script>
    Swal.fire({
        // icon: "error",
        title: 'failure!',
        text: '${failureMessage}',
        confirmButtonText: 'OK',
        customClass: {
            icon: "icon-size"
        }
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
                                <c:set var="processedCount" value= "0" ></c:set>
                                <table id="caseAssign" class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Sno</th>
                                            <th>Registration Id</th>
                                            <th>Candidate Reference Id</th>
                                            <th>Reason</th>
                                            <th>Registration Type</th>
                                            <th>Created Date</th>
                                            <th>Action</th>
                                         </tr>
                                    </thead>
                                    <tbody >
                                         <c:forEach var="emp" items="${galleryList}" varStatus="counter">
                                            <tr>
                                                <td>${counter.count}</td>

                                                <td>${emp.regId}</td>
                                                <td>${emp.matchedRefId} </td>
                                                <td>Biometric Potential Match</td>
                                                <td>${emp.regType eq 'Update' ? 'Update' : 'New'}</td>
                                                <td>
                                                    <fmt:formatDate value="${emp.createdDate}" type="date"
                                                                    pattern="dd-MMM-yyyy"/></td>
                                                <td style="align-items: center; justify-content: center;">

                                                    <c:choose>
                                                       <c:when test="${emp.op1userId != userid && emp.op2userId != userid && (emp.op1userId == null || emp.op2userId == null)}">
                                                                   <a href="<c:url value="leveloneSearchByName">
                                                               <c:param name="id" value="${emp.sno}"></c:param>
                                                               <c:param name="probe" value="${emp.regId}"></c:param>
                                                               <c:param name="candidate" value="${emp.matchedRefId}"></c:param>
                                                              <c:param name="requestId" value="${emp.reqid}"></c:param>
                                                              <c:param name="caseListNo" value="${counter.count} of ${galleryList.size()}"></c:param>
                                                              <c:param name="regType" value="${emp.regType}"></c:param>
                                                              <c:param name="candidateRegType" value="${emp.candidateRegType}"></c:param>
                                                                </c:url>">
                                                                      <i class="nav-icon fas fa-edit fa-2x" aria-hidden="true"></i>
                                                                  </a>
                                                           <%--

                                                        <a href="javascript:void(0);" onclick="handleLevelOneSearchByName('${emp.sno}', '${emp.regId}', '${emp.matchedRefId}', '${emp.reqid}', '${counter.count}', '${galleryList.size()}');">
                                                            <i class="nav-icon fas fa-edit fa-2x" aria-hidden="true"></i>
                                                        </a>
--%>

                                                        </c:when>
                                                        <c:when test="${emp.op2verifyStatus == 'nohit'}">
                                                           <c:set var="processedCount" value= "${processedCount + 1}" ></c:set>
                                                            <button type="button" class="btn btn-success"  style="width: 85px;">No Hit</button>
                                                        </c:when>
                                                        <c:when test="${emp.op2verifyStatus == 'hit'}">
                                                            <c:set var="processedCount" value= "${processedCount + 1}" ></c:set>
                                                            <button type="button" class="btn btn-danger" style="width: 85px;">Hit</button>
                                                        </c:when>
                                                        <c:when test="${emp.op1verifyStatus == 'nohit'}">
                                                            <c:set var="processedCount" value= "${processedCount + 1}" ></c:set>
                                                            <button type="button" class="btn btn-success"  style="width: 85px;">No Hit</button>
                                                        </c:when>
                                                        <c:when test="${emp.op1verifyStatus == 'hit'}">
                                                            <c:set var="processedCount" value= "${processedCount + 1}" ></c:set>
                                                            <button type="button" class="btn btn-danger"  style="width: 85px;">Hit</button>
                                                        </c:when>
                                                    </c:choose>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>
                                <div class="col-md-12">
                                    <c:choose>

                                        <c:when test="${processedCount == galleryList.size() && galleryList.size() != 0 }">
                                            <button type="button" class="btn btn-primary float-right" onclick="alertSubmit()">SUBMIT</button>
                                        </c:when>
                                        <c:when test="${galleryList.size() == 0 }"></c:when>
                                        <c:otherwise>
                                            <button type="button" class="btn btn-primary float-right" onclick="alertSubmit()" disabled>SUBMIT</button>
                                        </c:otherwise>
                                    </c:choose>
<%--                                    <button type="button" class="btn btn-primary float-right" onclick="alertSubmit()">SUBMIT</button>--%>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
</html>
