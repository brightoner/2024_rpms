<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">

var codeCheck = function(acCode) {//비밀번호 구성 조건(영문,숫자,특수문자 하나 이상 포함하여 9자리 이상으로 입력)
	var pattern1 = /[a-zA-Z]{1,}/g ;
	var pattern2 = /[^0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힝]{1,}/g ;
	var pattern3 = /[0-9]{1,}/g ;
	var pattern4 = /[ㄱ-ㅎㅏ-ㅣ가-힝]{1,}/g ;
	var acCodeHanName ="비밀번호";
	var returnVal = false; 
	
	if (!pattern1.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 영문을 포함하여 입력하셔야 합니다.");
	}
	else if (!pattern2.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 특수문자를 포함하여 입력하셔야 합니다");
	}
	else if (!pattern3.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 숫자를 포함하여 입력하셔야 합니다.");
	}
	else if (pattern4.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 한글을 쓸 수 없습니다.");
	}
	else if (acCode.length < 9) {
		fn_showCustomAlert(acCodeHanName + "는 9자리 이상으로 입력하셔야 합니다.");
	}
	else {
		returnVal = true;
	}
	
	return returnVal;

};

function fn_changePass(){
	if ($("#password").val() == "") {
		fn_showCustomAlert("비밀번호를 입력하여 주시기 바랍니다.");
		$("#password").focus();
		return;
	}
	if ($("#passwordChk").val() == "") {
		fn_showCustomAlert("비밀번호를 입력하여 주시기 바랍니다.");
		$("#passwordChk").focus();
		return;
	}
	if($("#password").val() != $("#passwordChk").val()){
		fn_showCustomAlert("입력한 비밀번호가 서로 다릅니다. 다시 입력해 주시기 바랍니다.");
		$("#passwordChk").focus();
		return;
	}
	
	var check = codeCheck($("#password").val());   //비밀번호 유효성체크
	if (!check) {
		$("#password").focus();
		return;
	}
	
	
	var url = '${ctxt}' + "/member/regi/changePass.do";
	
	$("form[name=reqForm]").attr("action", url).submit();
} 

</script>
		<span id="page_name" class="hide">PW 변경</span>
		<div class="content_body idpw">
			<div class="row section">
				<div class="row bg">
					<p class="barMark ma_b_10">비밀번호 재설정을 위해 다음 단계를 진행해 주시기 바랍니다.</p>
					<p class="barMark">비밀번호는 <span class="font_red"><strong>영문, 숫자, 특수문자를 각 한 문자 이상 포함하여 9자리 이상</strong></span>으로 입력하세요.</p>  
				
					<div class="ma_t_40">
						<form name="reqForm" method="post" style="width: fit-content; margin: 0 auto;">
							<div class="idpw-form change">
								<div class="ma_t_10">
									<input type="hidden" id="emplyrkey" name="emplyrkey" value="${emplyrkey}"/> 
									<label for="password">변경할 비밀번호</label>
									<input name="password" type="password" id="password" title="비밀번호 입력" />
								</div>
								<div class="ma_t_10">
									<label for="passwordChk">비밀번호 확인</label>
									<input name="passwordChk" type="password" id="passwordChk" title="비밀번호 입력" /> 
						    	</div>
						    	<div class="btn-box ma_t_20">
						    		<a href="javascript:fn_changePass();" class="btnN" role="button" style="padding: 0 40px;">확인</a>
						    	</div>
						    </div>
							<!-- 추가 PARAM -->
							<input type="hidden" name="page" id="page" value="" alt="pageNum" /> 
						 	<input type="hidden" name="idSecGbn" id="idSecGbn" value="secSearch" alt="idSecGbn" />
						 	<!-- 공통  필수 PARAM -->
						 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt="token"/>
						</form>
					</div>
				</div>
			</div>
		</div>
