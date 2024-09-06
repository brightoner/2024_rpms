<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">

 $(function() {
	
	 
	 console.log('${result}');   
	 console.log('${message}');
	
     if('${result}' == 'false'){
       $("#loginid").focus();
       //$("#loginid").val('${loginid}');
       if(!isEmpty('${message}')){
	       $(".loginFail").html('${message}');
	       fn_showCustomAlert('${message}');
       }
     }else if('${result}' == 'true'){
       location.href='${ctxt}/';
     }else{    	  

       $("#loginid").focus();
     }
     
     
 });
 
 

	function fncLogin(){
	     var form = document.mainLoginform;
	
	     if ($("#loginid").val() == '') {
	       fn_showCustomAlert('아이디를 입력하세요.','c');
	       $("#loginid").focus();
	       return;
	     }
	
	     if($("#password").val() == ""){
	        fn_showCustomAlert("비밀번호를 입력하세요.",'c');
	        $("#password").focus();
	        return;
	     }
	
	    form.submit();      		
	  
	}
 


 
 function searchPopup(param){ // ID/PW 찾기
	
 	if(param == "paramid"){
 		location.href="${ctxt}/login/info/findId.do";	
 	}else{
 		location.href="${ctxt}/login/infoPw/findPw.do";
 		
 	}
 }

 
 
 </script>
 


<!--로그인, 채팅 영역 -->

	<!-- 로그인 -->
	<div class="card card-main mb-4">
	<!-- 
		<div class="card-header">로그인</div> -->
		<div class="card-body">
			<div class="row">
				<div class="col-sm-6">
					<form id="mainLoginform" name="mainLoginform" action="${ctxt}/login/loginProcess.do" method="post">
					   <c:choose>
		                	<c:when test="${userVo eq null}">
		                		<div class="logIn">	
									<div class="login_header">
										<h2 class="title">로그인</h2>
										<p>(주)라스테크 연구과제관리시스템에 오신 것을 환영합니다.</p>
									</div>
									<div class="border_box">	
		                				<p class="clear relative">
											<label class="float" for="loginid"></label>
											<input class="float" type="text" id="loginid" name="loginid" value="ras_admin" title="로그인 아이디" onkeydown="javascript:if(event.keyCode==13){fncLogin();}" placeholder="아이디">
											<i class="fas fa-user absolute"></i>
										</p>
										<p class="clear relative">
											<label class="float" for="password"></label>
											<input class="float" type="password" id="password" name="password" autocomplete="off"  value="dkzkdlqm1!" title="로그인  비밀번호" onkeydown="javascript:if(event.keyCode==13){fncLogin();}" placeholder="패스워드">
											<i class="fas fa-unlock-alt absolute"></i>
											
										</p>	
										<!-- v2 (체크박스) -->
										<div class="g-recaptcha ma_b_0" data-sitekey="6LdT03QnAAAAAGHpt4qah7Lhct8kSx97d4q8MEKu" style="transform:scale(0.832); transform-origin:0 0;"></div>	
									
									     
										<div class="text_c">
											<button type="button" class="button btn2" onclick="fncLogin()" title="로그인 버튼">로그인</button>
											<%-- <a href="${ctxt}/resources/images/log_1.png" target="_blank"><button type="button"  class="button btn2">공인인증서 로그인</button></a> --%>
										</div>
										<div class="loginFail bor_0 pa_0 ma_0"></div>
									</div>
								</div>
		                	</c:when>
			                	<c:otherwise>
			                		
			                		<!-- 로그인 후 채워질  영역  -->
			                		<div class="box-img-cover">
				                		<img src="${ctxt}/resources/images/main/cover_main_rpms.png" alt="" />
				                	</div>
							
			                	</c:otherwise>
		                	</c:choose>
						
					</form>			
				</div>
			</div>
		</div>
	</div>



	<!-- 탭 -->

	<!-- 실시간 채팅 --> 
	<!-- 20240614 -->
		<%-- <div class="card2 borderless">
			<div class="chat-body">
				 	 <%@include file="./cmmChatRoom.jsp"%> 
			</div>
		</div> --%>
	

<script type="text/javascript">
 

 </script>
