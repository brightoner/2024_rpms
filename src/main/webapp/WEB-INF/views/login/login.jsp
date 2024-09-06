<%@page import="kr.go.rastech.commons.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- <%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%> --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%-- Spring Security Filter 순위 문제로 로그인 화면을 정확히 표현해주기 위해 사용 --%>


<title></title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge;"/>
	<meta http-equiv="imagetoolbar" content="no"/>
	<link rel="shortcut icon"    href="/resources/images/favicon.ico">
	<link rel="icon"    href="/resources/images/favicon.ico">
  
  
  <script type="text/javascript">
  //뒤로가기방지
  window.history.forward();
  
    $(function() {
      if('${result}' == 'false'){
        $("#loginid").focus();
        $("#loginid").val('${loginid}');
        
        $(".loginFail").html('${message}');
        fn_showCustomAlert('${message}');
        
      }else if('${result}' == 'true'){
        location.href='${ctxt}/';
      }else{    	  
        var form = document.form;
        
        $("#loginid").focus();
      }

    });
		
    function fncLogin(){
      var form = document.form;

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
 
              var frmObj = $('<form action="${ctxt}/login/loginProcess.do" method="post"></form>');
          
           frmObj.append('<input type="hidden" name="loginid" value="' + $('#loginid').val() + '"/>');
           frmObj.append('<input type="hidden" name="password" value="' + $('#password').val() + '"/>');
			
              frmObj.append('<input type="hidden" name="returnURL" value="${param.returnURL}"/>');
              frmObj.append('<input type="hidden" name="returnUrl" value="${returnURL}"/>');
              frmObj.appendTo('body');
            frmObj.submit();
   
    }

  
    function searchPopup(param){ // ID/PW 찾기
    	
    	if(param == "paramid"){
    		location.href="${ctxt}/login/info/findId.do";	
    	}else{
    		location.href="${ctxt}/login/infoPw/findPw.do";
    		
    	}
    }
   
    

    
  </script>





<div class="content_body logIn">
	<div class="row section clear">
		<div class="line_box col2 shadow4">
		
			<form id="form" name="form" action="${ctxt}/login/loginProcess.do" method="post">
				<h2 class="boxTitle">아이디 / 비밀번호 로그인</h2>
				<div class="border_box">
					<p class="clear">
						<label class="float" for="loginid"><!-- <i class="fas fa-user" aria-hidden="true"></i> ID --></label>
						<input class="float" type="text" id="loginid" name="loginid" title="로그인 아이디" onkeydown="javascript:if(event.keyCode==13){fncLogin();}" placeholder="아이디">
					</p>
					<p class="clear">
						<label class="float" for="password"><!-- <i class="fas fa-unlock-alt" aria-hidden="true"></i>Password --></label>
						<input class="float" type="password" id="password" name="password" autocomplete="off" title="로그인  비밀번호" onkeydown="javascript:if(event.keyCode==13){fncLogin();}" placeholder="패스워드">
					</p>
					<!-- <p class="idSave clear">
	    				<span class="block float"><input type="checkbox" /></span>
	    				<label class="float font_gray font_sm" for="">아이디 저장</label>
	    			</p> -->
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
					<div class="loginFail bor_0 pa_0 ma_0"></div>
				</div>
			</form>
		</div>
	</div>
</div>



	<!-- mainContents -->