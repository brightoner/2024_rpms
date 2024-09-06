<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<style type="text/css">
	.outInfo_1{
		margin-left: 10px;
	}
	.outInfo_2{
		float: right;
		margin-right: 10px;
	}
</style>

<script type="text/javascript">

 
// 탈퇴 클릭시 나이스 본인인증 팝업창
function fn_goMemOut(){
	var strConfirm;		
	strConfirm = confirm("탈퇴 하시겠습니까? 탈퇴 후 로그인은 불가합니다.");
		
	if(strConfirm){
		 $.ajax({
	    	 url: '${pageContext.request.contextPath}/member/inform/passChkBeforeMemOut.do',
			     data: '',  
			     type: 'POST',
			   	 dataType: 'json',
		     cache: false,
		     success: function(result) {
				  if(result.message=='Y'){ //성공
					  fn_showCustomAlert("탈퇴 처리되었습니다.");
					  var form = $('<form action="${pageContext.request.contextPath}/login/logout.do" method="post"></form>');
					  form.append('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></form>');
					  form.appendTo('body');
					  
					  setTimeout(function run() {
						  form.submit();
						}, 3000);
					  
				  }else if(result.message=='N'){
					  fn_showCustomAlert("비밀번호가 동일하지 않아 탈퇴 처리 할 수 없습니다.");
				  }else if(result.message=='F'){
					  fn_showCustomAlert("탈퇴 처리가 실패하였습니다.");
				  }
		   },
		   error : function(){                              // Ajax 전송 에러 발생시 실행
	 		     console.log('오류가 발생했습니다. 관리자에게 문의 바랍니다.');
	 		   }
	    });
	}
}




</script>

<body>
	<div class="content_body idpw">
		<div class="row section bg">
			<div class="inner-row">
				<h3 class="page_title pa_0 ma_b_50">회원 탈퇴</h3>
					<p class="barMark ma_b_10 font_red bold">회원 탈퇴 시 개인정보 및 모든 정보가 초기화되어 복구 불가합니다.</p>
										
				<div class="ma_t_20 ma_b_40">
					<form name="reqForm" method="post" style="width: fit-content; margin: 0 auto;">
						<div class="idpw-form">
							<div class="buttonBox">
								<a href="javascript:fn_goMemOut();"class='btnN' role="button"><span>회원탈퇴</span></a>
							</div>
					    </div>
					    
					    <!-- 회원탈퇴 메시지 팝업 영역 -->
						<div id="popOutMessageZone">
						
						</div>
						<!-- 공통  필수 PARAM -->
					 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt="token"/>
					</form>
					<!-- 휴대폰 실명인증 -->
					<form name="form_chk" method="post">
					
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>