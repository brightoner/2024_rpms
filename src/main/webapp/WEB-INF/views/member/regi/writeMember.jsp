<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">

//################################## sms 인증 관련 시작 ################################


$(function() {
	
});


//################################## 이메일 인증 관련 시작 ################################

$(function() {
	
});



function fn_korChk(param){
	
	var pattern_num = /[0-9]/;	// 숫자 
	var pattern_eng = /[a-zA-Z]/;	// 문자 
	var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
	var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
	
	var str=  $(param).val();
	if( pattern_kor.test(str) ){
		return true;
	}else{
		return false;	
	}
}


var IS_SUBMIT = false;

// 아이디중복확인
function idChk() {

	if ($("#user_id").val() == "") {
		fn_showCustomAlert("사용자 ID를 입력하여 주시기 바랍니다.");
		$("#user_id").focus();
		return;
	}
	
	if ($("#user_id").val().length < 3) {
		fn_showCustomAlert("사용자 ID는 3자리 이상 입력하여 주시기 바랍니다.");
		$("#user_id").focus();
		return;
	}	
	
	if ($("#user_id").val() == "") {
		fn_showCustomAlert("사용자 ID를 입력하여 주시기 바랍니다.");
		$("#user_id").focus();
		return;
	}
	
	var findStr = " ";
	if ($("#user_id").val().indexOf(findStr) != -1) {
		fn_showCustomAlert("사용자 ID에 공백이 포함되어 있습니다.");
		$("#user_id").focus();
		return;
	}
	
	if (fn_korChk($("#user_id"))){
		fn_showCustomAlert("한글은 입력할 수 없습니다.");
		$("#user_id").focus();
		
		return;
	};
	
	var params = {};
		params.loginid = $('#user_id').val();
	
		$.ajax({
		    url: '${ctxt}/member/regi/memChk.do',
		    data: params,
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {
		    	
				if(result == "Y"){// 중복 
					
					fn_showCustomAlert("사용 불가능한 ID입니다.\n다른 ID를 사용해 주세요.");
				    $("#user_id").val("");
				    $("#idChkVal").val("");
					$("#idChkChange").val("");
					
				}else if(result =="N"){ // 사용가능 		
					
					fn_showCustomAlert("사용 가능한 ID입니다.");
					$("#idChkVal").val("Y");
					$("#idChkChange").val($('#user_id').val());
				}else{	
					
					fn_showCustomAlert("아이디를 입력해 주십시오.");
				    $("#user_id").val("");
				    $("#idChkVal").val("");
				    $("#idChkChange").val("");
				}
		    },
		    error : function(){
		      fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		    }
		});
}	

function morepage(gubun, flag) {
	
	if (gubun == "agreeHtmlFre") {
		if ($(".agreeHtml").is(":hidden") && flag == "0") {
			$(".agreeHtml").slideDown("fast");
			$(".agreeHtmlFre").slideUp("fast");
		} 
		else if (flag == "1") {
			$(".agreeHtml").slideUp("fast");
			$(".agreeHtmlFre").slideDown("fast");
		}
	} 
	
}

// 회원가입
function fncMemberIn() {

	var checkVal = new Array();
	checkVal[0] = "사용자 ID를 입력하여 주시기 바랍니다.";
	checkVal[1] = "사용자 ID 중복확인을 하여 주시기 바랍니다.";
	checkVal[2] = "사용자 ID는 3자리 이상 12자리 이하로 입력하여 주시기 바랍니다.";
	checkVal[3] = "이름을 입력하여 주시기 바랍니다.";
	checkVal[4] = "비밀번호를 입력하여 주시기 바랍니다.";
	checkVal[5] = "비밀번호 확인을 입력하여 주시기 바랍니다.";
	checkVal[6] = "비밀번호와 비밀번호 확인이 다릅니다.";
	checkVal[7] = "생년월일을 입력하여 주시기 바랍니다.";
 	checkVal[8] = "생년월일을 8자리로 입력해 주십시오. 예)19800101";
	checkVal[9] = "휴대폰 실명인증은 필수 사항입니다.";
	checkVal[10] = "소속사항을 확인 주시기 바랍니다. *필수 입력 사항";
	checkVal[11] = "이메일 주소를 입력하여 주시기 바랍니다.";
	checkVal[12] = "개인정보 수집(이용)에 동의하셔야 회원가입이 가능합니다.";
	checkVal[13] = "처리중입니다!";
	checkVal[14] = "주소를 입력하여 주시기 바랍니다.";
	checkVal[15] = "상세주소를 입력하여 주시기 바랍니다.";
	checkVal[16] = "사용자 ID에 공백이 포함되어 있습니다.";
	checkVal[17] = "사용자 이름에 공백이 포함되어 있습니다.";
	checkVal[18] = "직업을 선택하여 주시기 바랍니다.";
	checkVal[19] = "이름을 입력하여 주시기 바랍니다.";
	checkVal[20] = "이름 중복확인을 하여 주시기 바랍니다.";
 	
	if ($("#user_id").val() == "") {
		fn_showCustomAlert(checkVal[0]);
		$("#user_id").focus();
		return;
	}	
	
	if (fn_korChk($("#user_id"))){
		fn_showCustomAlert("한글은 입력할 수 없습니다.");
		$("#user_id").focus();
		
		return;
	};
	
	if ($("#idChkVal").val() == "") {
		fn_showCustomAlert(checkVal[1]);
		$("#idchk").focus();
		return;
	}
	
	var findStr = " ";
	if ($("#user_id").val().indexOf(findStr) != -1) {
		fn_showCustomAlert(checkVal[16]);
		$("#user_id").focus();
		return;
	}
	
	if ($("#user_id").val().length < 3 || $("#user_id").val().length > 12) {
		fn_showCustomAlert(checkVal[2]);
		$("#user_id").focus();
		return;
	}	

	
	if ($("#pass").val() == "") {
		fn_showCustomAlert(checkVal[4]);
		$("#pass").focus();
		return;
	}	
	
	var check = codeCheck($("#pass").val());   //비밀번호 유효성체크
	if (!check) {
		$("#pass").focus();
		return;
	}
	
	if ($("#pass2").val() == "") {
		fn_showCustomAlert(checkVal[5]);
		$("#pass2").focus();
		return;
	}	
	if ($("#pass").val() != $("#pass2").val()) {
		fn_showCustomAlert(checkVal[6]);
		$("#pass2").focus();
		return;
	}	

	if ($("#nicknm").val() == "") {
		fn_showCustomAlert(checkVal[19]);
		$("#nicknm").focus();
		return;
	}	 
	
	if ($("#nickChkVal").val() == "") {
		fn_showCustomAlert(checkVal[20]);
		$("#nicknm").focus();
		return;
	}


	if ($("#mbtlnum").val() == "") {
		fn_showCustomAlert(checkVal[9]);
		$("#mbtlnum").focus();
		return;
	}	
	
	if ($("#orgnm").val() == "" || $("#deptnm").val() == ""||$("#posnm").val() == "") {
		fn_showCustomAlert(checkVal[10]);
		if($("#orgnm").val() == "" ){
			$("#orgnm").focus();	
		}else if($("#deptnm").val() == ""){
			$("#deptnm").focus();
		}else{
			$("#posnm").focus();
		}
		
		return;
	}	
	
	if ($("#email1").val() == "") {
		fn_showCustomAlert(checkVal[11]);
		$("#email1").focus();
		return;
	}
	if ($("#email2").val() == "") {
		fn_showCustomAlert(checkVal[11]);
		$("#email2").focus();
		return;
	}	
	
	 
	
	IS_SUBMIT = true;
    
	if( IS_SUBMIT )	{
   	    $.ajax({
 		    url: '${ctxt}/member/regi/addPtlUserReg.do',
 		    data: $("form[name=reqForm]").serialize(),  
 		    type: 'POST',
 		    dataType: 'text',
 		    cache: false,
 		   	async: false,
 		    success: function(result) {
 				  if(result == "Y"){				// 성공  					
 					 fn_showCustomAlert("회원가입이 완료되었습니다. ");
  				 	opener.location.reload();
 				  }else if(result =="ID"){	 					
 					 fn_showCustomAlert("회원 ID가 중복되었습니다. 다른 회원 ID로 가입하시기 바랍니다."); 	
 				  }else if(result =="F"){ 					  
 					 fn_showCustomAlert("회원가입에 실패하였습니다."); 					  
 				  }
 		   },
 		   error : function(){                              // Ajax 전송 에러 발생시 실행
 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
 		   }
 		});
	 		
	  }	else {
		  fn_showCustomAlert(checkVal[13]);
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
		fn_showCustomAlert(acCodeHanName + "는 영문을 포함하여 입력하셔야 합니다.");
	}
	else if (!pattern2.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 특수문자를 포함하여 입력하셔야 합니다.");
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

$(function(){
	
	$("select[name=selEmailOpt]").change(function() {
		if ($(this).val() == "direct") {
			$("#email2").attr("readonly", false);
			$("#email2").css("background-color", "#FFFFFF");
			$("#email2").val("");
			$("#email2").focus();
		} else {
			$("#email2").val($(this).val());
			$("#email2").attr("readonly", true);
			$("#email2").css("background-color", "#F1F1F1");
		}
	});
	
	$("input[name=mbtlnum]").on("blur", function(){
		var trans_num = $(this).val().replace(/-/gi,'');
			if(trans_num != null && trans_num != ''){
				if(trans_num.length==11 || trans_num.length==10) {
					var regExp_ctn = /^01([016789])([1-9]{1})([0-9]{2,3})([0-9]{4})$/;
			
					if(trans_num.length==10){
						var regex =/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/;
						if(!regex.test($(this).val())){
							fn_showCustomAlert("유효하지 않은 전화번호입니다.");
							$(this).val("");
							$(this).focus();
						}
					}else{
					
						if(regExp_ctn.test(trans_num)){
							trans_num = $(this).val().replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3");
							$(this).val(trans_num);
						}else{
							fn_showCustomAlert("유효하지 않은 전화번호입니다.");
							$(this).val("");
							$(this).focus();
						}
					}
				}else{
					fn_showCustomAlert("유효하지 않은 전화번호입니다.");
					$(this).val("");
					$(this).focus();
				}
			}
		});	
});

</script>
<style>
	table * {
		font-size: 14px;
	}
</style>
<form name="reqForm" method="post">
<span id="page_name" class="hide">회원가입</span>
	<div class="content_body writeM">
		<div class="row pa_0">			<!-- table영역 줄일때 width 축소!!!!!!!!! -->
			<div class="content_list table">	
				<div id="divRefreshArea" class="relative">
					
					<h3 class="page_title pa_0 ma_b_30 text_c" id="title_div"><span class="adminIcon"></span>회원가입</h3>
					<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>필수 입력 사항</span>	
					

					<table class="table_v table_n">
						<caption>회원가입</caption>
						<tbody class="report">
							<tr>
								<th>
									<label for="user_id">아이디</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th>
							</tr>
							<tr>
								<td class="grid-input-2">
									<input name="user_id"  type="text" id="user_id" value="" title="로그인 아이디" placeholder="3 ~ 12자리를 입력하여 주세요." autocomplete="off" maxlength="12" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" />
									<input name="idChkVal" type="hidden" id="idChkVal" value="" /> 
									<input name="idChkChange" type="hidden" id="idChkChange" value="" />
									<a href ="javascript:void(0);" name="idchk" id="idchk"  onclick="javascript:idChk();" class="btn btn-secondary">ID 중복확인</a>
								</td>
							</tr>
							<tr>
								<th>
									<label for="nicknm">이름</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>	
								</th>
							</tr>
							<tr>
								<td class="grid-input-2">
									<input name="nicknm" type="text" id="nicknm" maxlength="15" value="" title="이름"  autocomplete="off" placeholder="" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/>
								
								</td>
							</tr> 
							
							<tr>
								<th>
									<label for="password">비밀번호</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th>
							</tr>
							<tr>
								<td class="grid-input-1">
									<div>
<!-- 									<input name="password" type="password" id="password" autocomplete="off" value=""  title="비밀번호" maxlength="20"  onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/>  -->
									<input name="password" type="password" id="pass" value=""  autocomplete="new-password" maxlength="50" title="비밀번호"/>
									<p class="font_xsm starMark ma_t_3 font_blue"><span class="ir_so">중요 표시</span>영문, 숫자, 특수문자를 각 한 문자 이상 포함하여 9자리 이상으로 입력하세요.</p> 
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="password2">비밀번호 확인</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th>
							</tr>
							<tr>
								<td class="grid-input-1">
<!-- 									<input name="password2" type="password" id="password2" autocomplete="off" value="" title="비밀번호 확인"  maxlength="20"  onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/> -->
									<input name="password2" type="password" id="pass2" value="" maxlength="50" autocomplete="new-password" title="비밀번호확인"/>
								</td>
							</tr>
							<tr>
								<th>
									<label for="mbtlnum">휴대전화</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th> 
							</tr>
							<tr class="grid-input-1">
								<td class="grid-input-2">
									<input name="mbtlnum" placeholder="'-'을 제외하고 숫자만 입력" type="text" id="mbtlnum"  title="휴대전화번호" value="" autocomplete="off"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
								
								</td> 
																						
							
							</tr> 
							<tr>
								<th>
									<label for="email">소속사항</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th>
							</tr>
							<tr class="grid-input-1">
								<td class="grid-input-7">
										<!--소속  -->
									<label for="orgnm" class="blind">소속기관 :</label>
									<input id="orgnm" name="orgnm" title="소속기관"  type="text"  value="" maxlength="15" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" />
									<label for="deptnm" class="blind">소속부서 :</label>
									<input id="deptnm" name="deptnm" title="소속부서" type="text"  value="" maxlength="15" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" class="ma_l_3"/>
									<label for="posnm" class="blind">직책 :</label>
										<input id="posnm" name="posnm" title="직책" type="text"  value="" maxlength="15" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" class="ma_l_3"/>
																			
								</td>
								
							</tr>				
							<tr>
								<th>
									<label for="email">이메일</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th>
							</tr>
							<tr class="grid-input-1">
								<td class="grid-input-5">
<!-- 									<input name="email" type="text" id="email"  title="이메일 주소" value="" style="width:calc(100% - 120px);" /> -->
									<input id="email1" name="email1" title="이메일아이디" placeholder="이메일 ID" type="text"  value="" maxlength="30" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" />
									<label for="email2" class="blind">@</label>
									<input id="email2" name="email2" title="이메일주소" type="text"  value="" maxlength="30" autocomplete="off" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" class="ma_l_3"/>
									
									<div class="selectRow in_block">
										<select id="email3" name="selEmailOpt" title="주 사용 전자우편(E-mail)">
											<option value="">선택해 주세요.</option>
											<option value="direct" selected="selected">직접 입력</option>
											<option value="rastech.co.kr">rastech.co.kr</option>
											<option value="gmail.com">gmail.com</option>
											<option value="naver.com">naver.com</option>
											<option value="hanmail.net">hanmail.net</option>
											<option value="daum.net">daum.net</option>
											<option value="kakao.com">kakao.com</option>
											<option value="nate.com">nate.com</option>
										</select>
									</div>
																										
								</td>
								
							</tr>							
							
					
							
						</tbody>
					</table>
<!-- 테이블 스타일 2(신규) END -->
				
				</div>
			</div>
			<div class="buttonBox ma_b_50">
				<a href="javascript: fncMemberIn();" class="btn btn-secondary" role="button" title="회원가입 버튼">회원가입</a>
			</div>
		</div>
	
	</div>
		

<!-- 공통  필수 PARAM  -->
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt = "token" />
	
</form>

 <a class="initialism fadeandscale_open btn btn-success" name="fadeandscale" style="display: none;">Fade &amp; scale</a>
  <div id="fadeandscale" class="alertBox animated fadeInDown">
     <div>
	 <i></i>		
	 <div id="msg_content" class="float"></div>
	 <button class="fadeandscale_close" title="닫기 버튼"><i class="fa fa-times blue-cross" aria-hidden="true"></i></button>
  </div>
  </div>

