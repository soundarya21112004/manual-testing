<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<script type="text/javascript"
        src="js/plugins/sweetalert/sweetalert.min.js"></script>

<script type='text/javascript'
        src='js/plugins/jquery-validation/jquery.validate.js'></script>

<!-- END PLUGINS -->
<script>
    $(document).ready(function () {
        var val = document.getElementById("groupId").value;
        $.ajax({
            type: "GET",
            datatype: "json",
            url: "loadEditUserGroup/" + val,
            success: function (data) {
                $('#selectedRole').html('');
                $.each(${allRoles1}, function (i1, record1) {
                    $.each(data, function (i, record) {
                        if (record1 == record) {
                            $('input[value=' + record1 + ']').prop("checked", true);
                        }
                    });
                });
            },
            error: function () {
                alert("Something went wrong! Please try later");
            }
        });
        return true;
    });
</script>

<script>
    function selectAllcheck() {

        if ($("#CheckAll").is(":checked")) {
            $('input[id=selectedRole]').prop("checked", true);
        } else {
            $('input[id=selectedRole]').prop("checked", false);
        }
    }

    function checkSelectedUser() {

        var flag = false;
        var checkedValue = document.getElementsByName("selectedRoleGroups").length;
        for (var i = 0; i < checkedValue; i++) {
            var valu = document.getElementsByName("selectedRoleGroups")[i];
            var element = $(valu);
            if (element.is(':checked')) {
                flag = true;
            }
        }
        if (!flag) {
            swal("PLEASE SELECT ATLEAST ONE ROLE ");
            return false;
        }
        document.getElementById("validate").action = "<%=request.getContextPath()%>/updateUserGroupEdit";
        document.getElementById("validate").submit();

    }
</script>

<c:if test="${successMessage != null}">
    <script>
        swal({
            title: '',
            confirmButtonColor: '#006a4d',
            text: '${successMessage}',
            type: 'success'
        });
    </script>
</c:if>
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
<section class="content">
    <div class="container-fluid">
        <!-- SELECT2 EXAMPLE -->
        <form aaction="updateUserGroupEdit"
              id="validate" method="post" modelAttribute="mstRolesBean">
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">EDIT USER GROUP</h3>


                    <div class="card-tools">
                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                            <i class="fas fa-minus"></i>
                        </button>
                        <button type="button" class="btn btn-tool" data-card-widget="remove">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                </div>
                <!-- /.card-header -->
                <div class="card-body">

                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Group Name</label>
                                <input class="form-control required" type="text"
                                       id="newGroupName" value="${groupName}" readonly="readonly">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class=" col-sm-6">
                            <div class="form-group">
                                <label>LIST OF ROLES</label>
                                <ul>
                                    <select class="form-control" multiple id="groupId" name="groupId"
                                    <c:forEach items="${allRoles}" var="element1">
                                        <option value="${element1.roleid}">${element1.roleDetails}</option>
                                    </c:forEach>
                                    </select>


                                </ul>
                            </div>
                        </div>
                    </div>


                </div>

            </div>


            <div class="card-footer">
                <button  class="btn btn-info" onclick="$('#validate').valid(); checkSelectedUser()"
                        id="createButton">UPDATE GROUP
                </button>

            </div>


        </form>
    </div>
</section>
<!-- START PLUGINS -->
<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>

<script type="text/javascript"
        src="js/plugins/sweetalert/sweetalert.min.js"></script>

<script type='text/javascript'
        src='js/plugins/jquery-validation/jquery.validate.js'></script>

<script>
    $(document).ready(function () {
        var val = document.getElementById("groupId").value;
        $.ajax({
            type: "GET",
            datatype: "json",
            url: "loadEditUserGroup/" + val,
            success: function (data) {
                $('#selectedRole').html('');
                $.each(${allRoles1}, function (i1, record1) {
                    $.each(data, function (i, record) {
                        if (record1 == record) {
                            $('input[value=' + record1 + ']').prop("checked", true);
                        }
                    });
                });
            },
            error: function () {
                alert("Something went wrong! Please try later");
            }
        });
        return true;
    });
</script>

<script>
    function selectAllcheck() {

        if ($("#CheckAll").is(":checked")) {
            $('input[id=selectedRole]').prop("checked", true);
        } else {
            $('input[id=selectedRole]').prop("checked", false);
        }
    }

    function checkSelectedUser() {

        var flag = false;
        var checkedValue = document.getElementsByName("selectedRoleGroups").length;
        for (var i = 0; i < checkedValue; i++) {
            var valu = document.getElementsByName("selectedRoleGroups")[i];
            var element = $(valu);
            if (element.is(':checked')) {
                flag = true;
            }
        }
        if (!flag) {
            swal("PLEASE SELECT ATLEAST ONE ROLE ");
            return false;
        }
        document.getElementById("validate").action = "<%=request.getContextPath()%>/updateUserGroupEdit";
        document.getElementById("validate").submit();

    }
</script>
</head>
<c:if test="${successMessage != null}">
    <script>
        swal({
            title: '',
            confirmButtonColor: '#006a4d',
            text: '${successMessage}',
            type: 'success'
        });
    </script>
</c:if>
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