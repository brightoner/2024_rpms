<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">

</script>
<!-- 상단메뉴 -->
	<c:if test="${userVo ne null}">
		<input type="hidden" id="header_log" value="Y" />
	</c:if>
	<div class="header-wrap">
		<h1 id="logo"><a href="/"><img src="${ctxt}/resources/images/common/logo_rastech.svg"  style="width: 200px;height: 50px;" alt="로고" class="" /></a></h1>
		<nav id="gnb" class="gnb gnb1">
			<ul class="menu">
			
				<c:forEach var="menuVo" items="${sys_topMenu}" varStatus="stat">
					<c:if test="${menuVo.menu_id ne 'LOGIN' &&  menuVo.menu_id ne 'POLICY'}">
						<li>
							<%--  <button type="button" class="reset"><c:out value="${menuVo.menuNm}" /></button>
							<div class="sub-menu-box">
								<c:if test="${not empty menuVo.subList}">
								<ul class="menu-02">
										<c:forEach var="subMenuVo" items="${menuVo.subList}">
											<li>
												<a href="${ctxt}<c:out value='${subMenuVo.url}'/>" <c:if test="${subMenuVo.selYn eq true}"></c:if> title="${subMenuVo.menuNm} 메뉴 이동"> 
								 					<span><c:out value="${subMenuVo.menuNm}" /></span>
												</a>
											</li>
											<input type="hidden" name="selYn" value="${subMenuVo.selYn}" />
										</c:forEach>
									</ul>
								</c:if>
							</div>  --%>		
							<span class="mainMenuButton">				
							 	<a href="${ctxt}<c:out value='${menuVo.url}'/>" <c:if test="${menuVo.selYn eq true}"></c:if> title="${menuVo.menuNm} 메뉴 이동" class=font_Dgray> 
			 						<c:out value="${menuVo.menuNm}" /> 
								</a> 
							</span>
						</li>	
					</c:if>
				</c:forEach>
			</ul>
		</nav>
		<!-- 상단 로그인 부분 -->
       <ul class="right-menu">
			<li class="user relative">
             <!-- 상단 로그인 페이지 이동 및 정보 이동 주석 이 사이트에서는 사용안함 -->
             
                <c:choose>
                	<c:when test="${userVo eq null}">
                		<a href="${ctxt}/index/index.do" id="top_a1" title="로그인 페이지로 이동">LOGIN</a>
                	</c:when>
                	<c:otherwise>
             			    <a class="user-info" href="javascript:popOpen('logininfo');" role="button" title="마이메뉴 보기 버튼"><c:out value="${userVo.nicknm}"/>님</a>
                	</c:otherwise>
                </c:choose> 
                
         	    <div class="loginfo" id="logininfo" style="display: none;"><strong><c:out value="${userVo.nicknm}"/>님</strong>
		          	<c:if test = "${fn:length(sys_popMenu) ne 0  }" >
						<c:forEach var="popMenuVo" items="${sys_popMenu}" varStatus="status">
							<div><p><a  class ="popMenu" href="${ctxt}<c:out value='${popMenuVo.url}'/>"> <c:out value="${popMenuVo.menuNm}"/></a></p></div>
						</c:forEach>
					
					</c:if>											       
			        <div><p><a class="userTooltip" href="${ctxt}/login/logout.do" id="top_a3" title="로그아웃 하기" onblur="javascript:logoutTabOff();"><span>LOGOUT</span></a></p></div>
			        <span class="arrw"></span>
			        <p class="text_c">
			            <a href="javascript:void(0);" onclick="popClose('logininfo');" role="button" class="button btn-lg w-100 popMenuClose" title="닫기 버튼">close</a>
			        </p>
		    	</div>
                
			</li>		
		<!-- 	<li class="sitemap-list"><a href="#" class="sitemap-btn" data-toggle="modal" data-target="#siteMap" title="사이트맵 열기"></a></li> -->
			<!-- <li class="mo-menu-list"><a href="#" class="mo-menu-btn" title="모바일 메뉴 열기"></a></li> -->
		</ul>
	</div>
	<div class="progress-container" >	
		<div class="progress-bar" id="myBar" ></div>
	</div>

	<div class="mo-menu">
		<div class="h-box">
			<ul class="user-list">
			 <!-- 상단 로그인 페이지 이동 및 정보 이동 주석 이 사이트에서는 사용안함 -->
			<%-- 
				<c:choose>
					<c:when test="${userVo eq null}">
						<li><a href="${ctxt}/login/user/login.do" class="user" title="로그인 페이지로 이동">로그인</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${ctxt}/member/infoChI/infoMember.do" class="user" title="회원정보로 이동"><c:out value="${userVo.nicknm}"/>님</a></li>
				    	<li><a href="javascript:logout();"  onclick="location.href='${ctxt}/login/logout.do'"  id="top_a"  class="button" title="로그아웃 하기">LogOut</a></li>		         
					</c:otherwise>		
				</c:choose>
               --%>
            </ul>        
            <button class="mo-menu-close"></button>
		</div>
		<div class="menu-box">
			<ul class="menu">
				<li>
					<c:forEach var="menuVo" items="${sys_topMenu}" varStatus="stat" >						
						<c:if test="${menuVo.menu_id ne 'LOGIN' &&  menuVo.menu_id ne 'POLICY'}">
								<li>
										<c:choose>
											<c:when test="${menuVo.child_yn == 'Y'}">
												<a id="momenu_${stat.index}"> 
							 						<c:out value="${menuVo.menuNm}" />
												</a> 
											</c:when>
											<c:otherwise>
												<a href="${ctxt}<c:out value='${menuVo.url}'/>" <c:if test="${menuVo.selYn eq true}"></c:if> title="${menuVo.menuNm} 메뉴 이동" > 
							 						<c:out value="${menuVo.menuNm}" />
												</a> 
											</c:otherwise>
										</c:choose>
											 <c:if test="${not empty menuVo.subList}">
												<ul class="menu-02">
													<c:forEach var="subMenuVo" items="${menuVo.subList}"> 
															<li>
																<a href="${ctxt}<c:out value='${subMenuVo.url}'/>" <c:if test="${subMenuVo.selYn eq true}"></c:if> > 
												 					<span><c:out value="${subMenuVo.menuNm}" /></span>
																</a>
																	
															</li>
															<input type="hidden" name="selYn" value="${subMenuVo.selYn}" />
														
													</c:forEach>
												</ul>
											</c:if>  
										<%-- <ul class="menu-02">
													<li>
														<a href="${ctxt}/center/noti/notiList.do"  > 
										 					<span>공지사항</span>
														</a>
															
													</li>
													<input type="hidden" name="selYn" value="${subMenuVo.selYn}" />
											</ul> --%>
								</li>			
						</c:if>	
					</c:forEach>
		        </li>
		    </ul>
		</div>
	</div>
	<form id="subForm" name="subForm" action="${ctxt}/" method="post">
	</form>
	
  
