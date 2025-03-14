<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
<%--    <a href="" class="brand-link">--%>
        <!--   <img src="dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8"> -->
        <span class="brand-link brand-text font-weight-light"><b>Manual
				Verification System</b></span>
<%--    </a>--%>

    <!-- Sidebar -->
    <div class="sidebar" >
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
<%--            <div class="image">--%>
<%--                <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2"--%>
<%--                     alt="User Image">--%>
<%--            </div>--%>
            <div class="info">
                <a href="#" class="d-block">Welcome ${userdetails.firstnameEn} !</a>
                <a href="logout1" class="d-block">Logout</a>
            </div>
        </div>


        <nav class="mt-2" >
            <ul class="nav nav-pills nav-sidebar flex-column"
                data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->


                <c:if test="${headerMenu != null && headerMenu.size() > 0}">

                    <c:forEach var="emp" items="${headerMenu}">

                        <li class="nav-item">
                            <a href="#" class="nav-link"> <i
                                    class="far fa-circle nav-icon"></i>
                                <p>${emp.split('~')[0]} <i class="right fas fa-angle-left"></i></p></a>
                            <ul class="nav nav-treeview">
                                <c:if test="${subRoles != null && subRoles.size() > 0}">
                                    <c:forEach var="emp1" items="${subRoles}">

                                        <c:if test="${emp.split('~')[0] ==  emp1.split('~')[0]}">
                                            <c:forEach var="empcount" items="${subRolesCount}">
                                                <c:if test="${empcount.split('~')[0] ==  emp1.split('~')[1]}">
                                                    <c:choose>
                                                        <c:when test="${empcount.split('~')[1] > 1}">
                                                            <li class="xn-openable" style="list-style: none">

                                                                    <c:forEach var="emp2" items="${subMenu}">
                                                                        <c:if test="${emp1.split('~')[1] ==  emp2.mstRoles.subRoleName}">
                                                                            <c:if test="${emp2.mstRoles.roleDetails != null}">
                                                                                <select name="category">
                                                                                    <c:forEach items="${listCategory}" var="category">
                                                                                        <option value="${category.id}"
                                                                                                <c:if test="${category.id eq selectedCatId}">selected="selected"</c:if>>
                                                                                                ${category.name}
                                                                                        </option>

                                                                                    </c:forEach>
                                                                                    <option>test</option>
                                                                                </select>
                                                                                <li class="nav-item"><a
                                                                                        href="${emp2.mstRoles.url}"
                                                                                        class="nav-link"><i
                                                                                        class="fa fa-angle-double-right"></i>
                                                                                    <p>${emp2.mstRoles.roleDetails }</p>
                                                                                </a></li>

                                                                            </c:if>

                                                                        </c:if>
                                                                    </c:forEach>

                                                            </li>

                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="emp2" items="${subMenu}">
                                                                <c:if
                                                                        test="${emp1.split('~')[1] ==  emp2.mstRoles.subRoleName}">
                                                                    <c:if test="${emp2.mstRoles.roleDetails != null}">
                                                                        <li class="nav-item"><a
                                                                                href="${emp2.mstRoles.url}"
                                                                                class="nav-link"><i
                                                                                class="fa fa-angle-double-right"></i>
                                                                            <p>
                                                                                    ${emp2.mstRoles.roleDetails }</p>
                                                                        </a></li>



                                                                    </c:if>

                                                                </c:if>

                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:forEach>

                                        </c:if>


                                    </c:forEach>
                                </c:if>
<%--               start                 <li class="nav-item"><a--%>
<%--                                        href="userApproval"--%>
<%--                                        class="nav-link"><i--%>
<%--                                        class="fa fa-angle-double-right"></i>--%>
<%--                                    <p>APPROVAL</p>--%>
<%--                                </a></li>--%>
<%--                                <li class="nav-item"><a--%>
<%--                                        href="changePasswordDetails"--%>
<%--                                        class="nav-link"><i--%>
<%--                                        class="fa fa-angle-double-right"></i>--%>
<%--                                    <p>CHANGE PASSWORD</p>--%>
<%--                                </a></li>--%>
<%--                                <li class="nav-item"><a--%>
<%--                                        href="commonReportsController"--%>
<%--                                        class="nav-link"><i--%>
<%--                                        class="fa fa-angle-double-right"></i>--%>
<%--                                    <p>REPORTS</p>--%>
<%--                                </a></li>--%>
<%--                                <li class="nav-item"><a--%>
<%--                                        href="operatorController"--%>
<%--                                        class="nav-link"><i--%>
<%--                                        class="fa fa-angle-double-right"></i>--%>
<%--                                    <p>REPORTS per Role</p>--%>
<%--                 end               </a></li>--%>

<%--             ------   up      ---          <li class="nav-item">--%>
<%--                                    <a href="#" class="nav-link"> <i--%>
<%--                                            class="fa fa-angle-double-right"></i>--%>
<%--                                        <p>REPORTS <i class="right fas fa-angle-left"></i></p></a>--%>
<%--                                    <ul class="nav nav-treeview">--%>

<%--                                <li class="nav-item"><a--%>
<%--                                        href="allReportsController"--%>
<%--                                        class="nav-link"><i--%>
<%--                                        class="fa fa-angle-double-right"></i>--%>
<%--                                    <p>All Reports</p>--%>
<%--                                </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="verifiedReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p>Verified Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="deadlockReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p>Deadlock Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="supervisorReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p>Supervisor verified Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="hitcasesReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p>Hit Cases Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="fraudReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p>Hit Cases sent to Fraud Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="noHitDecisionsReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p>No Hit Decisions Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="hitDecisionsReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p> Hit Decisions Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="demographicReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p> Demographic Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                        <li class="nav-item"><a--%>
<%--                                                href="biometricReportsController"--%>
<%--                                                class="nav-link"><i--%>
<%--                                                class="fa fa-angle-double-right"></i>--%>
<%--                                            <p> Biographic Reports</p>--%>
<%--                                        </a></li>--%>

<%--                                    </ul>--%>
<%--                                </li>--%>
                            </ul>
                        </li>



                    </c:forEach>
                </c:if>


            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>
