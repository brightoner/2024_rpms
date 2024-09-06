<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver"%>

<script src="${ctxt}/resources/js/jquery.cookie.js"></script>
<script type="text/javaScript">

</script>
 <!-- Responsive navbar-->
 <!-- 원본 메뉴  -->
       <%--  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="#!">사이트타이틀</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                  <!--   <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="#">투자포인트</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">별풍선 포인트</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">현물 모의 투자</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">선물 모의 투자</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">모의 투자 튜토리얼</a></li>
                    </ul> -->
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
						<c:forEach var="menuVo" items="${sys_topMenu}" varStatus="stat">
							<c:if test="${locale != 'en'}" >   
								<c:if test="${menuVo.menu_id ne 'SEARCH' &&  menuVo.menu_id ne 'LOGIN' &&  menuVo.menu_id ne 'POLICY' &&  menuVo.menu_id ne 'MEMBER'}">
									<li class="nav-item">										
										<a class="nav-link"  href="${ctxt}<c:out value='${menuVo.url}'/>" title="${menuVo.menuNm} 메뉴 이동"> <span><c:out value="${menuVo.menuNm}" /></span> </a>
									</li>								
								</c:if>
							</c:if>
						</c:forEach>
					</ul>
                </div>
            </div> 
        </nav> --%>
        <!-- Page header with logo and tagline-->
        <header id= "header" class="py-5 bg-light border-bottom mb-4">
        	<div class="gnb-wrap">
				<%@include file="./subPlatFormMenu.jsp"%>
				<div class="menu-back"></div>   
			</div>
            <div class="container">
            <!--     <div class="text-center my-5">
                    <h1 class="fw-bolder">이미지</h1>
                </div> -->
            </div>
        </header>
