<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 

<!-- 네이버 로그인 연동 -->	
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>	
 
<!-- 구글 recaptcha -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script> 


<script type="text/javascript">
  
 
 // 네이버 로그인 생성자 
 var naver_id_login = new naver_id_login("r5bYnFYLTEFIfuYg_g2Y", "https://moneybunny.kr/member/api/callback.do");
 var state = naver_id_login.getUniqState();

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
     
  
    if(!isEmpty('${userVo.loginid}') ){
    	// ajax로 포인트, 아이템 부를때 사용 (commonPlatform.js)
    	 fn_totalItemPoint();
    	 
    	 
    	 // commonPlatform.js
    	 // 로그인폼의 프로필 이미지를 저장된 이미지로 바꿔준다. 3번째 인자값은 없음
    	 fn_chProfilImg('loginProfil', "mych");
     }
     
	
   //  네이버 버튼 init  and 커스텀 위한 이벤트
    $(document).on("click", "#naverIdLogin_loginButton", function(){
    	   
    
    	   
    	 naver_id_login.setButton("white", 3,40);
    	 naver_id_login.setDomain("https://moneybunny.kr/");
    	 naver_id_login.setState(state);
    	 naver_id_login.setPopup();
    	 naver_id_login.init_naver_id_login();
    	
        var naverLogin = document.getElementById("naver_id_login").firstChild;
        naverLogin.click();
    });
     
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
	     var recaptchaResponse = grecaptcha.getResponse(); // reCAPTCHA 응답 값 가져오기
	  
	     // reCAPTCHA 안쓸때 풀고 reCAPTCHA 주석
	    form.submit();      		
	     
	     // reCAPTCHA 응답 값이 비어있는 경우, 즉 사용자가 로봇일 가능성이 높은 경우 처리
// 	    if (recaptchaResponse === "") {
	    
// 	         fn_showCustomAlert("reCAPTCHA 체크가 누락되었습니다.");
// 	     } else {
	        
// 	         var params = {};
// 				params.vertify    = recaptchaResponse;   
// 	       	 $.ajax({
// 	               url: '${ctxt}/verify/recaptcha/verifyRecaptcha.do',
// 	               type : 'POST',
// 	               dataType: 'json',               
// 	               data : params,
// 	               success : function(result){
// 	                   if(!isEmpty(result.returnData.success)){
// 	                	   if(result.returnData.success == true){
// 	                		   form.submit();      		   
// 	                	   }else{
// 	                		   fn_showCustomAlert("reCAPTCHA 인증이 실패하였습니다.");
// 	                	   }
// 	                   }
// 	               },
// 	               error: function(e){	            	   
// 	                   console.log("reCAPTCHA fail");
// 	               }
//              });// end ajax	    	
// 	     }
	}
 


 
 function searchPopup(param){ // ID/PW 찾기
	
 	if(param == "paramid"){
 		location.href="${ctxt}/login/info/findId.do";	
 	}else{
 		location.href="${ctxt}/login/infoPw/findPw.do";
 		
 	}
 }

 
 
 
//********** 2023 ljk - kakao 소셜 로그인 ******************
	//SDK 초기화
	Kakao.init('55b9dabf03a8e9634d46bd14294f2a32'); // 사용하려는 앱의 JavaScript 키 입력

	// 카카오 로그인 
	  function fn_kakaoLogin(){
	
			Kakao.Auth.login({
		      success: function (response) {
		        Kakao.API.request({
		          url: '/v2/user/me',
		          success: function (response) {
		        	  console.log("nickname : " +response.kakao_account.profile.nickname);
		        	  var userId = "K_"+response.id;
		        	  
		        	  var params = {};
		        	  params.userId = userId;
		        	  
		        	  // 카카오로그인 or 카카오회원가입 처리 부분
		        	 $.ajax({
		  				url : '${ctxt}/member/regi/idDuplChkBySocial.do',
		  				data : params,   //전송파라미터
		  				type : 'POST',
		  				dataType: 'text',
		  	   			cache: false,
		  				success : function(result) {
		  					 if(result == "Y"){			// 신규회원이면  					
		  						 //fn_showCustomAlert("회원 가입이 완료되었습니다. 다시 소셜 로그인을 클릭해 주세요.");
			  					 var frmObj = $('<form action="${ctxt}/member/regi/writeMemberBySocial.do" method="post"></form>');
		  						 frmObj.append('<input type="hidden" name="user_id" value="' + userId + '"/>');
		  						 frmObj.appendTo('body');
		  						 frmObj.submit();
		  					  }else if(result =="N"){	 // 이미 가입되어있으면 로그인 처리
		  						 var frmObj = $('<form action="${ctxt}/login/loginProcess.do" method="post"></form>');
		  						 frmObj.append('<input type="hidden" name="loginid" value="' + userId + '"/>');
		  						 frmObj.append('<input type="hidden" name="returnURL" value="${param.returnURL}"/>');
		  						 frmObj.append('<input type="hidden" name="returnUrl" value="${returnURL}"/>');
		  						 frmObj.appendTo('body');
		  						 frmObj.submit();
		  					  }else if(result =="F"){ 					  
		  						 fn_showCustomAlert("회원가입이 실패하였습니다."); 					  
		  					  }
		  				},
		  				error : function() { // Ajax 전송 에러 발생시 실행
		  					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		  				}
		  			});
		        	  
		        	  
		          },
		          fail: function (error) {
		            console.log(error);
		          },
		        })
		      },
		      fail: function (error) {
		        console.log(error)
		      },
		    })
		  }

	// 네이버 소셜 로그인 함수
	function fn_naverLogin(paramVal) {
			  
			  var userId = 'N_'+paramVal.split('@')[0];  // 이메일
			  
			  var params = {};
			  params.userId = userId;
			  
			  // 카카오로그인 or 카카오회원가입 처리 부분
			 $.ajax({
					url : '${ctxt}/member/regi/idDuplChkBySocial.do',
					data : params,   //전송파라미터
					type : 'POST',
					dataType: 'text',
		 			
					success : function(result) {
						 if(result == "Y"){			// 신규회원이면  					
							 //fn_showCustomAlert("회원 가입이 완료되었습니다. 다시 소셜 로그인을 클릭해 주세요.");
							 var frmObj = $('<form action="${ctxt}/member/regi/writeMemberBySocial.do" method="post"></form>');
							 frmObj.append('<input type="hidden" name="user_id" value="' + userId + '"/>');
							 frmObj.appendTo('body');
							 frmObj.submit();
						  }else if(result =="N"){	 // 이미 가입되어있으면 로그인 처리
							 var frmObj = $('<form action="${ctxt}/login/loginProcess.do" method="post"></form>');
							 frmObj.append('<input type="hidden" name="loginid" value="' + userId + '"/>');
							 frmObj.append('<input type="hidden" name="returnURL" value="${param.returnURL}"/>');
							 frmObj.append('<input type="hidden" name="returnUrl" value="${returnURL}"/>');
							 frmObj.appendTo('body');
							 frmObj.submit();
						  }else if(result =="F"){ 					  
							 fn_showCustomAlert("회원가입이 실패하였습니다."); 					  
						  }
					},
					error : function() { // Ajax 전송 에러 발생시 실행
						fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
					}
				});
			  
        				
			  
}
		  
  // 자식 창에서 호출될 콜백 함수
  function passEmailToParent(email) {
    // 넘겨받은 이메일 값을 사용하여 원하는 동작을 수행합니다.
    // 예: 이메일 정보를 사용하여 부모 창 함수 호출
    fn_naverLogin(email);
  }

//********** 2023 ljk - naver 소셜 로그인 끝******************


 
 </script>
 


<!--로그인, 채팅 영역 -->

	<!-- 로그인 -->
	<div class="card mb-4">
	<!-- 
		<div class="card-header">로그인</div> -->
		<div class="card-body">
			<div class="row">
				<div class="col-sm-6">
				<form id="mainLoginform" name="mainLoginform" action="${ctxt}/login/loginProcess.do" method="post">
						<div class="logIn">	
							<div class="border_box">
								
								   <c:choose>
					                	<c:when test="${userVo eq null}">
					                			
					                				<p class="clear relative">
														<label class="float" for="loginid"></label>
														<input class="float" type="text" id="loginid" name="loginid" value="" title="로그인 아이디" onkeydown="javascript:if(event.keyCode==13){fncLogin();}" placeholder="아이디">
														<i class="fas fa-user absolute"></i>
													</p>
													<p class="clear relative">
														<label class="float" for="password"></label>
														<input class="float" type="password" id="password" name="password" autocomplete="off"  value="" title="로그인  비밀번호" onkeydown="javascript:if(event.keyCode==13){fncLogin();}" placeholder="패스워드">
														<i class="fas fa-unlock-alt absolute"></i>
														
													</p>	
													<!-- v2 (체크박스) -->
													<div class="g-recaptcha ma_b_0" data-sitekey="6LdT03QnAAAAAGHpt4qah7Lhct8kSx97d4q8MEKu" style="transform:scale(0.832); transform-origin:0 0;"></div>	
												
												     
													<div class="text_c">
														<button type="button" class="button btn2" onclick="fncLogin()" title="로그인 버튼">로그인</button>
														<%-- <a href="${ctxt}/resources/images/log_1.png" target="_blank"><button type="button"  class="button btn2">공인인증서 로그인</button></a> --%>
													</div>
																						
												<p class="text_c row20">
													<a href="${ctxt}/login/regi/writeMember.do" role="button" class="font_Dgray" title="회원가입">회원가입</a>
													<span class="barVertical"></span>
													<a href="javascript:void(0);" onclick="searchPopup('paramid');" role="button" class="font_Dgray" title="아이디 찾기">아이디 찾기</a>
													<span class="barVertical"></span>
													<a href="javascript:void(0);" onclick="searchPopup('paramsec');" role="button" class="font_Dgray" title="비밀번호 찾기">비밀번호 찾기</a>
												</p>
 												<p class="social_box">
	 												<a id="naverIdLogin_loginButton" href= "javascript:void(0);">
														<img src='${ctxt}/resources/images/main/ic_naver.png' class="social">네이버 로그인
													</a> 
											 		 <div id="naver_id_login" style="display:none;"></div>
 												</p>
												
												<p class="social_box ma_b_0">
													<a id="kakao-login-btn" href="javascript:fn_kakaoLogin()">
														<img src='${ctxt}/resources/images/main/ic_kakao.png' class="social">카카오 로그인
													</a>
												</p>												
												<div class="loginFail bor_0 pa_0 ma_0"></div>
					                	</c:when>
					                	<c:otherwise>
					                	<p class="profile" id ="loginProfil"></p>
					                	<p class="text_c">
					                		
										    <a class="user-info" href="javascript:popOpen('logininfo');" role="button" title="마이메뉴 보기 버튼"><c:out value="${userVo.nicknm}"/>님</a>

										    
										</p>
										        <div class="loginfo" id="logininfo" style="display: none;"><strong><c:out value="${userVo.nicknm}"/>님</strong>
										          	<c:if test = "${fn:length(sys_popMenu) ne 0  }" >
			            								<c:forEach var="popMenuVo" items="${sys_popMenu}" varStatus="status">
			            									<div><p><a href="${ctxt}<c:out value='${popMenuVo.url}'/>"> <c:out value="${popMenuVo.menuNm}"/></a></p></div>
			            								</c:forEach>
		            								
		            								</c:if>											       
												        <%-- <div><p><a class="userTooltip" href="${ctxt}/login/logout.do" id="top_a3" title="로그아웃 하기" onblur="javascript:logoutTabOff();"><span>LOGOUT</span></a></p></div> --%>
												        <span class="arrw"></span>
												        <p class="text_c">
												            <a href="javascript:void(0);" onclick="popClose('logininfo');" role="button" class="button btn-lg w-100" title="닫기 버튼">close</a>
												        </p>
										    	</div>

									    	
									    	<%-- <a href="${ctxt}/member/infoChI/infoMember.do" id="top_a4">My ncmiklib</a> --%>									    	
									    	
									    
											<div id="totalCntDiv">
												<p class="text_c">사용가능 별풍선 : <span id="itemId">0</span> </p>
<!-- 												<p class="text_c">선물받은 별풍선: <span id="itemRsvId">0</span> </p> -->
												<p class="text_c">포인트 : <span id="pointId">0</span> </p>
											</div>
											<p class="text_c">
											<a href="${ctxt}/item/charge/itemCharge.do" class="button">별풍선 충전</a>
											<a href="${ctxt}/point/charge/pointCharge.do" class="button">포인트 충전</a>
											<a href="${ctxt}/market/buy/marketBuy.do" class="button">마켓 방문</a>
											</p>
											
									
					                	</c:otherwise>
				                	</c:choose>
							</div>
						</div>
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
