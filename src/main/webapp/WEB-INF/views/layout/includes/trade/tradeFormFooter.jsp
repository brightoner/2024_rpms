<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="kr.go.rastech.base.controller.BaseController"%>
<!-- Footer -->


   <footer class="footer bg-dark" id ="footer">
   	 <div class="container">
       		
	       <div class="footer-wrap">
			 <div class="foot-logo"><img src="${ctxt}/resources/images/common/logo.png" alt="로고"/></div>
			 <div class="foot-con">
				 <ul class="foot-menu">
				 	 <li><a href="${ctxt}/policy/company.do" title="회사소개">회사소개</a></li>
					 <li><a href="${ctxt}/policy/usePolicy.do" title="이용안내">이용약관</a></li> 
					 <li><a href="${ctxt}/policy/privacy.do">개인정보처리방침</a></li>
					 <li><a href="${ctxt}/policy/copy.do" title="저작권 정책">저작권정책</a></li>
					  <li><a href="${ctxt}/policy/youthPolicy.do" title="저작권 정책">청소년 보호정책</a></li>
				 </ul>
				 <p class="address">(34037) 대전광역시 유성구 갑천로 361-39 (주)OOOOO</p>
				 <ul class="foot-info-list">
					 <li><span>TEL:&nbsp;</span><p>042-000-0000</p></li>
					 <li><span>FAX:&nbsp;</span><p>042-000-0000</p></li>
					 <li><span>E-MAIL:&nbsp;</span><p>0000@0000.kr</p></li>
				 </ul>
				 <p class="copyright">Copyright 2023 by 회사명. All Rights Reserved.</p>
				<div class="wa-mark">
				</div>
			 </div>
			

       </div>
       
             
       <a class="initialism fadeandscale_open btn btn-success" name="fadeandscale" style="display: none;">Fade &amp; scale</a>
	   <div id="fadeandscale" class="alertBox animated fadeInDown">
	      <div>
			 <i></i>		
			 <div id="msg_content" class="float"></div>
			 <button class="fadeandscale_close" title="닫기 버튼"><i class="fa fa-times blue-cross" aria-hidden="true"></i></button>
		  </div>
	   </div>
   </footer>
   <!-- Bootstrap core JS-->
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
   <!-- Core theme JS-->
   <script src="${ctxt}/resources/js/new/scripts.js"></script>
   
   
  <!-- Footer-->