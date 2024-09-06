<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<script type="text/javascript">
var IS_SUBMIT = false;

function fncMemberPW() {
	if ($("#rpassword").val() == "") {
		fn_showCustomAlert("기존 비밀번호를 입력하여 주시기 바랍니다.");
		$("#rpassword").focus();
		return;
	}	
	
	if ($("#password").val() == "") {
		fn_showCustomAlert("비밀번호를 입력하여 주시기 바랍니다.");
		$("#password").focus();
		return;
	}	
	
	var check = codeCheck($("#password").val());   //비밀번호 유효성체크
	if (!check) {
		$("#password").focus();
		return;
	}
	if ($("#password2").val() == "") {
		fn_showCustomAlert("비밀번호 확인을 입력하여 주시기 바랍니다.");
		$("#password2").focus();
		return;
	}	
	if ($("#password").val() != $("#password2").val()) {
		fn_showCustomAlert("입력하신 비밀번호가 서로 다릅니다.");
		$("#password2").focus();
		return;
	}	

	if(!IS_SUBMIT){
				  
        $.ajax({
        	 url: '${ctxt}/member/regi/pwModiPtlEmplyr.do',
  		     data: $("form[name=reqForm]").serialize(),  
  		     type: 'POST',
  		   	 dataType: 'text',
		     cache: false,
		     success: function(result) {
				  if(result=='Y'){ //성공
					  fn_showCustomAlert("비밀번호가 변경되었습니다.");
					  $("#rpassword").val("");  
				  	  $("#password").val("");
					  $("#password2").val("");
					 /*  
					  var form = $('<form action="${ctxt}/index/index.do" method="post"></form>');
					  form.append('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></form>');
					  form.appendTo('body');
					  form.submit(); 
					  */
				  }else if(result=='N'){
					  fn_showCustomAlert("기존 비밀번호가 동일하지 않아 변경할 수 없습니다.");
				  }else if(result=='F'){
					  fn_showCustomAlert("비밀번호 찾기에 실패하였습니다.");
				  }
		   },
		   error : function(){                              // Ajax 전송 에러 발생시 실행
	 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	 		   }
        });
        
	}
}


var codeCheck = function(acCode) {//비밀번호 구성 조건(영문,숫자,특수문자 하나 이상 포함하여 9자리 이상으로 입력)
	var pattern1 = /[a-zA-Z]{1,}/g ;
	var pattern2 = /[^0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힝]{1,}/g ;
	var pattern3 = /[0-9]{1,}/g ;
	var pattern4 = /[ㄱ-ㅎㅏ-ㅣ가-힝]{1,}/g ;
	var acCodeHanName ="비밀번호";
	var returnVal = false; 
	
	if (!pattern1.test(acCode)) {
		//fn_showCustomAlert(acCodeHanName + "는 영문을 포함하여 입력하셔야 합니다.");
		fn_showCustomAlert(acCodeHanName + "는 영문, 숫자, 특수문자 하나 이상을 포함하여 9자리 이상으로 입력하셔야 합니다.");
	}
	else if (!pattern2.test(acCode)) {
		//fn_showCustomAlert(acCodeHanName + "는 특수문자를 포함하여 입력하셔야 합니다");
		fn_showCustomAlert(acCodeHanName + "는 영문, 숫자, 특수문자 하나 이상을 포함하여 9자리 이상으로 입력하셔야 합니다.");
	}
	else if (!pattern3.test(acCode)) {
		//fn_showCustomAlert(acCodeHanName + "는 숫자를 포함하여 입력하셔야 합니다.");
		fn_showCustomAlert(acCodeHanName + "는 영문, 숫자, 특수문자 하나 이상을 포함하여 9자리 이상으로 입력하셔야 합니다.");
	}
	else if (pattern4.test(acCode)) {
		//fn_showCustomAlert(acCodeHanName + "는 한글을 쓸 수 없습니다.");
		fn_showCustomAlert(acCodeHanName + "는 영문, 숫자, 특수문자 하나 이상을 포함하여 9자리 이상으로 입력하셔야 합니다.");
	}
	else if (acCode.length < 9) {
		//fn_showCustomAlert(acCodeHanName + "는 9자리 이상으로 입력하셔야 합니다.");
		fn_showCustomAlert(acCodeHanName + "는 영문, 숫자, 특수문자 하나 이상을 포함하여 9자리 이상으로 입력하셔야 합니다.");
	}
	else {
		returnVal = true;
	}
	
	return returnVal;
};
</script>

<form name="reqForm" method="post">

	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>비밀번호 변경</h3>  
	<div>
		<div class="row section pa_0">
					<ul class="list_full pa_l_0 ma_l_3">
						<li class="barMark">정보보호를 위하여 3개월에 한 번씩 비밀번호를 변경하시기 바랍니다.</li>
					</ul>
					<div id="divRefreshArea" class="ma_t_20 pa_0">
						<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>필수 입력 사항</span>
						<table class="table_v">
							<!-- <table class="view_table"> -->
							<caption>회원정보 수정(정보수정)- 기존, 변경, 재확인</caption>
							<colgroup>
								<col width="15%">
								<col width="50%">
							</colgroup>
								<tbody>
								<tr>
									<th>
										<label for="">기존 비밀번호</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
									</th>
									<td>
										<input name="rpassword" type="password" id="rpassword" class="w_40" title="기존 비밀번호 입력" value="" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberPW(); return; }"/>
									</td>
								</tr>
								<tr>
									<th>
										<label for="">변경 비밀번호</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
									</th>
									<td>
										<input name="password" type="password" id="password"  class="w_40"  title="변경 비밀번호 입력"  value="" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberPW(); return; }"/> 
										<span class="font_xsm starMark ma_t_3 font_blue block"><span class="ir_so">중요 표시</span>영문, 숫자, 특수문자 포함 9자리 이상</span>
									</td>
								</tr>
								<tr>
									<th>
										<label for="">변경 비밀번호 확인</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
									</th>
									<td>
										<input name="password2" type="password" id="password2"  class="w_40"  title="변경 비밀번호 확인 입력"  value="" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberPW(); return; }"/>
									</td>
								</tr>
								</tbody>
						</table>
					</div>
					<div class="buttonBox">
						<a href="javascript:fncMemberPW();" class='btnN' role="button" title="비밀번호 변경 버튼"><span>비밀번호변경</span></a>
					</div>
				</div>
			</div>
 	
 	<!-- 공통  필수 PARAM -->
 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt="token"/>
</form>
