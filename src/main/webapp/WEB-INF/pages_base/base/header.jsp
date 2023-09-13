<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- <script type="text/javascript">
    $(document).ready(function () {
        $('ul li').click(function (e)
        {
            $('ul li').removeClass("active");
            $(this).addClass("active");
        });
    });
</script> -->
<ul class="x-navigation x-navigation-horizontal">

	<li class="xn-logo"><a href="#"><img src="img/brta.png"
			alt="text" style="width: 50px; height: 50px" /> MAS</a> <a href="#"
		class="x-navigation-control"></a></li>
	<li class="xn-openable"><a href="home"><span
			class="fa fa-home"></span>HOME</a></li>
	<c:if test="${headerMenu != null && headerMenu.size() > 0}">

		<c:forEach var="emp" items="${headerMenu}">

			<li class="xn-openable"><a href="#"> <span
					class="${emp.split('~')[1]}"></span><span>${emp.split('~')[0]}</span></a>


				<ul class="animated zoomIn">
					<c:if test="${subRoles != null && subRoles.size() > 0}">
						<c:forEach var="emp1" items="${subRoles}">

							<c:if test="${emp.split('~')[0] ==  emp1.split('~')[0]}">
								<c:forEach var="empcount" items="${subRolesCount}">
									<c:if test="${empcount.split('~')[0] ==  emp1.split('~')[1]}">
										<c:choose>
											<c:when test="${empcount.split('~')[1] > 1}">
												<li class="xn-openable"><a href="#"><span
														class="fa fa-angle-double-right"></span>
														${emp1.split('~')[1]} </a>
													<ul>
														<c:forEach var="emp2" items="${subMenu}">
															<c:if
																test="${emp1.split('~')[1] ==  emp2.mstRoles.subRoleName}">
																<c:if test="${emp2.mstRoles.roleDetails != null}">
																	<li><a href="${emp2.mstRoles.url}"
																		style="font-size: 11px;"><span
																			class="fa fa-angle-double-right"></span>
																			${emp2.mstRoles.roleDetails }</a></li>
																</c:if>

															</c:if>
														</c:forEach>


													</ul></li>
											</c:when>
											<c:otherwise>
												<c:forEach var="emp2" items="${subMenu}">
													<c:if
														test="${emp1.split('~')[1] ==  emp2.mstRoles.subRoleName}">
														<c:if test="${emp2.mstRoles.roleDetails != null}">
															<li><a href="${emp2.mstRoles.url}"><span
																	class="fa fa-angle-double-right"></span>
																	${emp2.mstRoles.roleDetails }</a></li>
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
				</ul></li>


		</c:forEach>
	</c:if>
	<!-- 	<li class="xn-openable"><a href="renewalDL"><span class="fa fa-check-circle-o"></span> RENEWAL DL</a></li>   -->
	<!-- SIGN OUT -->
	<li class="xn-icon-button pull-right"><a href="#"
		class="mb-control" data-box="#mb-signout"><span
			class="fa fa-sign-out"></span></a></li>
			<li class="xn-icon-button pull-right"><a href="#"><span class="fa fa-key"></span></a></li>
	<!-- END SIGN OUT -->
</ul>

<form action="logout" class="form-horizontal" method="post">
	<div class="message-box animated fadeIn" data-sound="alert"
		id="mb-signout">
		<div class="mb-container">
			<div class="mb-middle">
				<div class="mb-title">
					<span class="fa fa-sign-out"></span> Log <strong>Out</strong> ?
				</div>
				<div class="mb-content">
					<p>Are you sure you want to log out?</p>
					<p>Press No if you want to continue work. Press Yes to logout
						current user.</p>
				</div>
				<div class="mb-footer">
					<div class="pull-right">
						<a href="logout" class="btn btn-success btn-lg">Yes</a>
						<button class="btn btn-default btn-lg mb-control-close">No</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>