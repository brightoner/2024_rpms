<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">

//################################## sms 인증 관련 시작 ################################


var chkAction = 0;   // 인증번호 재호출 위한 flag
var timerYn = false; // 타이머 실행 flag 
var authYN = 'N';    // 인증성공여부
var setTime = 180;		// 180 초 인증
var stopNum = 0;  // 0 : 시간종료 일때 , 1:인증번호 받기를 재호출시

$(function() {
	//@@@@@@@@@@@@@@ 핸드폰 인증 str @@@@@@@@@@@@@@
	// 인증을 위한 dialog
	$("#dialog").dialog({ 
		//이벤트 발생했을때 보여주려면 autoOpen : false로 지정해줘야 한다.
		autoOpen: false, 
		//레이어팝업 넓이 
		width: 400, 
		height : 320,
		//뒷배경을 disable 시키고싶다면 true 
		modal:true, 
		//버튼종류
		buttons: [ { 
			//버튼텍스트 
			text: "확인",
			//클릭이벤트발생시 동작
			click: function() { 
		
				$( this ).dialog( "close" );
			
				// 초기화 및 영역제어
				$("#authNum").show();	
				$("#authNum").val("");
				$("#authNumChk").show();
				$("#viewTimer").html("");	
				$("#viewTimer").show();				
				$("#stopContent").hide();
				
				chkAction++;
				setTime = 180;
				fn_setInterval();
			} 
		}		
		]
	}); 
	//@@@@@@@@@@@@@@ 핸드폰 인증 end @@@@@@@@@@@@@@
});

//@@@@@@@@@@@@@@ 핸드폰 인증 str @@@@@@@@@@@@@@

//핸드폰 인증을 위한 dialog 및 초 셋팅
//다이얼로그가 닫치면(확인) 초가 시작 된다.

var tid = null;
function fn_setInterval(){
	timerYn = false;
	var authTimeout = function() {
		tid= setInterval(function() {msg_time(); }, 1000);
	}
	// 타이머 시작
	authTimeout();
}

//타이머 clear
function fn_stopTimer(val){
	if(val == 0){
		timerYn = true;
	}else{
		timerYn = false;
	}

	clearInterval(tid);		// 타이머 해제
}

//타이머 실행부
function msg_time() {	
	
	if(!timerYn){
		
		var minute =Math.floor(setTime / 60) + "분 " + (setTime % 60) + "초";	// 남은 시간 계산
		 
		var msg = "<font color='red'>" + minute + "</font> ";
		
		$("#viewTimer").html(msg);		// div 영역에 보여줌 

		if (setTime == 0) {			// 시간이 종료 되었으면..
		
			stopNum = 0;
			fn_stopTimer(stopNum);
			
			// 초기화 및 영역제어
			chkAction = 1;
			$("#authNum").hide();
			$("#authNumChk").hide();
			$("#viewTimer").hide();	
					
			$("#viewTimer").html("");	
			$("#stopContent").show();
			
		}else if(chkAction > 1){  // 초기화 : 다시 인증번호 호출시 동작		
			
			stopNum = 1;
			fn_stopTimer(stopNum);	
			
			// 초기화 및 영역제어
			setTime = 180;
			chkAction = 1;
			$("#viewTimer").html("");		

		}else{
			setTime--;					// 1초씩 감소
		}
	}
}

//dialog css 셋팅  및 오픈
function fn_dialogOpen(){
	$("#dialog").dialog("open");
	$(".ui-dialog > .ui-widget-header").css("background" , "#188688");
	$(".ui-dialog > .ui-widget-header").css("border" , "1px solid #188688");		
}

// 휴대폰번호 중복확인
function fn_phoneChk(){	 
	
	if ($("#mbtlnum").val() == "") {
		fn_showCustomAlert("휴대폰 번호를 입력하여 주시기 바랍니다.");
		$("#mbtlnum").focus();
		return;
	}		
 var param = {};
 	param.mbtlnum=$("#mbtlnum").val();	
		
	 $.ajax({
		    url: '${ctxt}/member/regi/authenticationPhone.do',
		    data: param,  
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {		    	 		
				  if(result == "PHONE"){// 성공  					
					  fn_showCustomAlert("중복된 휴대폰 번호가 존재합니다.");
				  } else if(result == "F"){
					  fn_showCustomAlert("인증 번호 발송 실패."); 
				  }else if(result == "SUCCESS") {
					
					//  $("#authNumVal").val(result); 
					  fn_dialogOpen();
					  
				  }else{
					  fn_showCustomAlert("인증에 실패하였습니다."); 
				  }				  
		   },
		   error : function(){                              // Ajax 전송 에러 발생시 실행
		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		   }
		});
}


// 휴대폰 인증 확인함수 
function fn_authNumChk(){

	var authNumInput = $("#authNum").val();

	if( authNumInput == '' ){	
		$("#authNum").focus();
		return;
	}
	
	 var param = {};
	     param.authNumInput= authNumInput;	
		
	 $.ajax({
		    url: '${ctxt}/member/regi/authNumCompare.do',
		    data: param,  
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {	
		    	
				  if(result == "inputNull"){// 성공  					
					  fn_showCustomAlert("[인증 실패] 인증번호 입력 값이 존재하지 않습니다.");
				  }else if(result == "numNull"){
					  fn_showCustomAlert("[인증 실패] 발송된 인증번호 값이 존재하지 않습니다.");
				  }else if(result == "FAIL"){
					  fn_showCustomAlert("[인증 실패] 인증 반환값이 존재하지 않습니다."); 
				  } else if(result == "ERROR"){
					  fn_showCustomAlert("[인증 실패] 인증 번호 검증 오류 발생. <br />관리자에게 문의 바랍니다."); 
				  }else if(result == "Y"){
					  fn_authFinalChk("Y");
				  }else if(result == "N"){
					  fn_authFinalChk("N");
				  }				  
		   },
		   error : function(){                              // Ajax 전송 에러 발생시 실행
		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.');
		   }
		});
}

function fn_authFinalChk(val){
	
	if( val == "Y" ){		 
		
		 authYN = 'Y';				
		 stopNum = 0;
		 if(tid != null){
			 fn_stopTimer(stopNum);		
		 }
		 
		 // 초기화 및 영역제어
		 setTime = 180;
	 	 chkAction = 1;
		
	 	 $("#authNum").hide();
		 $("#authNum").val("");
		 
		 $("#authNumChk").hide();
		 
		 $("#viewTimer").html("");	
		 $("#viewTimer").hide();
	
		 fn_showCustomAlert("휴대전화 인증에 성공하였습니다.");
		
		 // 휴대전화 입력 , 인증번호 받기 버튼 비활성화
		 $("#authNumGetbtn").attr("disabled", true);
		 $("#mbtlnum").attr("readonly", true);
		 
	}else{
		authYN = 'N';
		fn_showCustomAlert("휴대전화 인증에 실패하였습니다. 번호를 정확히 입력해 주십시오.(숫자 5자리)");	
	}
}
//@@@@@@@@@@@@@@ end @@@@@@@@@@@@@@

//################################## sms 인증 관련 끝 ################################



//################################## 이메일 인증 관련 시작 ################################

var chkActionE = 0;   // 인증번호 재호출 위한 flag
var timerYnE = false; // 타이머 실행 flag 
var authYNE = 'N';    // 인증성공여부
var setTimeE = 180;		// 180 초 인증
var stopNumE = 0;  // 0 : 시간종료 일때 , 1:인증번호 받기를 재호출시

$(function() {
	//@@@@@@@@@@@@@@ 이메일 인증 str @@@@@@@@@@@@@@
	// 인증을 위한 dialog
	$("#dialogE").dialog({ 
		//이벤트 발생했을때 보여주려면 autoOpen : false로 지정해줘야 한다.
		autoOpen: false, 
		//레이어팝업 넓이 
		width: 400, 
		height : 320,
		//뒷배경을 disable 시키고싶다면 true 
		modal:true, 
		//버튼종류
		buttons: [ { 
			//버튼텍스트 
			text: "확인",
			//클릭이벤트발생시 동작
			click: function() { 
		
				$( this ).dialog( "close" );
			
				// 초기화 및 영역제어
				$("#authEmail").show();	
				$("#authEmail").val("");
				$("#authEmailChk").show();
				$("#viewTimerE").html("");	
				$("#viewTimerE").show();				
				$("#stopContentE").hide();
				
				chkActionE++;
				setTimeE = 180;
				fn_setIntervalE();
			} 
		}		
		]
	}); 
	//@@@@@@@@@@@@@@ 이메일 인증 end @@@@@@@@@@@@@@
});

//@@@@@@@@@@@@@@ 이메일 인증 str @@@@@@@@@@@@@@

//핸드폰 인증을 위한 dialog 및 초 셋팅
//다이얼로그가 닫치면(확인) 초가 시작 된다.

var tidE = null;
function fn_setIntervalE(){
	timerYnE = false;
	var authTimeoutE = function() {
		tidE= setInterval(function() {msg_timeE(); }, 1000);
	}
	// 타이머 시작
	authTimeoutE();
}

//타이머 clear
function fn_stopTimerE(val){
	if(val == 0){
		timerYnE = true;
	}else{
		timerYnE = false;
	}

	clearInterval(tidE);		// 타이머 해제
}

//타이머 실행부
function msg_timeE() {	
	
	if(!timerYnE){
		
		var minute =Math.floor(setTimeE / 60) + "분 " + (setTimeE % 60) + "초";	// 남은 시간 계산
		 
		var msg = "<font color='red'>" + minute + "</font> ";
		
		$("#viewTimerE").html(msg);		// div 영역에 보여줌 

		if (setTimeE == 0) {			// 시간이 종료 되었으면..
		
			stopNumE = 0;
			fn_stopTimerE(stopNumE);
			
			// 초기화 및 영역제어
			chkActionE = 1;
			$("#authEmail").hide();
			$("#authEmailChk").hide();
			$("#viewTimerE").hide();	
					
			$("#viewTimerE").html("");	
			$("#stopContentE").show();
			
		}else if(chkActionE > 1){  // 초기화 : 다시 인증번호 호출시 동작		
			
			stopNumE = 1;
			fn_stopTimerE(stopNumE);	
			
			// 초기화 및 영역제어
			setTimeE = 180;
			chkActionE = 1;
			$("#viewTimerE").html("");		

		}else{
			setTimeE--;					// 1초씩 감소
		}
	}
}

//dialog css 셋팅  및 오픈
function fn_dialogOpenE(){
	$("#dialogE").dialog("open");
	$(".ui-dialogE > .ui-widget-header").css("background" , "#188688");
	$(".ui-dialogE > .ui-widget-header").css("border" , "1px solid #188688");		
}



//이메일 주소 중복확인
function fn_emailChk(){	 
	
	//이메일 정규식
	var regEmail1 = /([a-zA-Z0-9._%+-])$/; 
	var regEmail2 = /([a-zA-Z0-9.-]+\.[a-zA-Z]{2,})$/; 
	
	if(!regEmail1.test($('#email1').val())){
		fn_showCustomAlert("주소부분을 정규식에 맞게 작성해 주세요.");
		$("#email1").focus();
		return;
	}
	if(!regEmail2.test($('#email2').val())){
		fn_showCustomAlert("도메인을 정규식에 맞게 작성해 주세요.");
		$("#email2").focus();
		return;
	}
	
	
	if ($("#email1").val() == "" || $("#email2").val() == "" ) {
		fn_showCustomAlert("이메일 주소를 정확히 입력하여 주시기 바랍니다.");
		$("#email1").focus();
		return;
	}		
 var param = {};

 	var email1 = $("#email1").val(); 
 	var email2 = $("#email2").val();
 	var email = email1+"@"+email2;
 	param.email=email;
		
	 $.ajax({
		    url: '${ctxt}/member/regi/authenticationEmail.do',
		    data: param,  
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {		    	 		
				  if(result == "EMAIL"){// 성공  					
					  fn_showCustomAlert("중복된 이메일 주소가 존재합니다.");
				  } else if(result == "F"){
					  fn_showCustomAlert("인증 번호 발송 실패."); 
				  }else if(result == "SUCCESS") {
					  fn_dialogOpenE();
				  }else{
					  fn_showCustomAlert("인증에 실패하였습니다."); 
				  }				  
		   },
		   error : function(){                              // Ajax 전송 에러 발생시 실행
		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		   }
		});
}

//이메일 인증 확인함수 
function fn_authEmailChk(){

	var authEmailInput = $("#authEmail").val();

	if( authEmailInput == '' ){	
		$("#authEmail").focus();
		return;
	}
	
	 var param = {};
	     param.authEmailInput= authEmailInput;	
		
	 $.ajax({
		    url: '${ctxt}/member/regi/authEmailCompare.do',
		    data: param,  
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {	
		    	
				  if(result == "inputNull"){// 성공  					
					  fn_showCustomAlert("[인증 실패] 인증번호 입력 값이 존재하지 않습니다.");
				  }else if(result == "emailNull"){
					  fn_showCustomAlert("[인증 실패] 발송된 인증번호 값이 존재하지 않습니다.");
				  }else if(result == "FAIL"){
					  fn_showCustomAlert("[인증 실패] 인증 반환값이 존재하지 않습니다."); 
				  } else if(result == "ERROR"){
					  fn_showCustomAlert("[인증 실패] 인증 번호 검증 오류 발생. <br /> 관리자에게 문의 바랍니다."); 
				  }else if(result == "Y"){
					  fn_authFinalChkE("Y");
				  }else if(result == "N"){
					  fn_authFinalChkE("N");
				  }				  
		   },
		   error : function(){                              // Ajax 전송 에러 발생시 실행
		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.');
		   }
		});
}
function fn_authFinalChkE(val){
	
	if( val == "Y" ){		 
		
		 authYNE = 'Y';				
		 stopNumE = 0;
		 if(tidE != null){
			 fn_stopTimerE(stopNumE);		
		 }
		 
		 // 초기화 및 영역제어
		 setTimeE = 180;
	 	 chkActionE = 1;
		
	 	 $("#authEmail").hide();
		 $("#authEmail").val("");
		 
		 $("#authEmailChk").hide();
		 
		 $("#viewTimerE").html("");	
		 $("#viewTimerE").hide();
	
		 fn_showCustomAlert("이메일 인증에 성공하였습니다.");
		
		 // 이메일 입력 , 인증번호 받기 버튼 비활성화
		 $("#authEmailGetbtn").attr("disabled", true);
		 $("#email").attr("readonly", true);
		 
	}else{
		authYNE = 'N';
		fn_showCustomAlert("이메일 인증에 실패하였습니다. 번호를 정확히 입력해 주십시오.(숫자 5자리)");	
	}
}
//################################## 이메일 인증 관련 끝 ################################





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
	checkVal[9] =  "휴대폰 실명인증은 필수 사항입니다.";
	checkVal[11] = "이메일 주소를 입력하여 주시기 바랍니다.";
	checkVal[12] = "개인정보 수집(이용)에 동의하셔야 회원가입이 가능합니다.";
	checkVal[13] = "처리 중입니다!";
	
	
// ###### 휴대폰 sms인증관련	validation 시작 ########
   	if(authYN != 'Y'){
		fn_showCustomAlert("휴대전화 인증이 완료 되지 않았습니다.");
		return;
	} 	
 // ###### 휴대폰 sms인증관련 validation 끝 ########	


	if ($("#mbtlnum").val() == "") {
		fn_showCustomAlert(checkVal[9]);
		$("#mbtlnum").focus();
		return;
	}	
	
	// ###### 이메일 인증관련	validation 시작 ########
   	if(authYNE != 'Y'){
		fn_showCustomAlert("이메일 인증이 완료되지 않았습니다.");
		return;
	} 	
 // ###### 이메일 인증관련 validation 끝 ########	
	
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
	
	
	
	
	
	
	if(!$("#information").is(":checked")){
		fn_showCustomAlert(checkVal[12]);
		$("#information").focus();
		return;
	}
	 

	IS_SUBMIT = true;
    
	if( IS_SUBMIT )	{
   	    $.ajax({
 		    url: '${ctxt}/member/regi/addPtlUserRegByKakao.do',
 		    data: $("form[name=reqForm]").serialize(),  
 		    type: 'POST',
 		    dataType: 'text',
 		    cache: false,
 		   	async: false,
 		    success: function(result) {
 				  if(result == "Y"){				// 성공  					
 					fn_showCustomAlert("회원가입이 완료되었습니다. 다시 소셜 로그인을 클릭해 주세요.");
 					setTimeout(function() {
 						location.href = "${ctxt}/login/user/login.do";
 			        }, 3000);
 				  }else if(result =="PHONE"){ 		// 사용가능 		 					
 					 fn_showCustomAlert("이미 가입하신 휴대전화번호입니다."); 					   					  
 				  }else if(result =="EMAIL"){	 					
 	 					 fn_showCustomAlert("이미 가입하신 이메일주소입니다.");
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
<input type="hidden" id="user_id" name="user_id" value="${ptlUserRegVo.user_id}">

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
									<label for="mbtlnum">휴대전화</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
								</th>
							</tr>
							<tr class="grid-input-1">
								<td class="grid-input-2">
									<input name="mbtlnum" type="text" id="mbtlnum"  title="휴대전화번호" value="" />
									<!--  핸드폰 sms인증  str -->
									<a href ="javascript:void(0);" name="authNumGetbtn" id="authNumGetbtn"  onclick="javascript:fn_phoneChk();" class="btn btn-secondary">인증번호 받기 </a>								
								</td>
								<td class="grid-input-2">
									<input type="text" name="authNum" id="authNum"  title="인증번호 입력값" style="display: none;width: 150px;" placeholder="인증번호 5자리 입력"/>									
									<a href ="javascript:void(0);" name="authNumChk" id="authNumChk"  onclick="javascript:fn_authNumChk();"  style="display: none;" class="btn btn-secondary">확인</a>									
									<div id="viewTimer" style = "display: inline;"></div>
									<div id="stopContent" style="display: none;"><span style="border-bottom:0px dotted red; color:red;">[시간초과] 재인증 바랍니다.</span></div>
									<!--  핸드폰 sms인증  end  -->
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
									<input id="email1" name="email1" title="이메일아이디" placeholder="이메일 ID" type="text"  value="" maxlength="30"   onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" />
									<label for="" class="blind">@</label>
									<input id="email2" name="email2" title="이메일주소" type="text"  value="" maxlength="30"  onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" class="ma_l_3"/>
									
									<div class="selectRow in_block">
										<select id="email3" name="selEmailOpt" title="주 사용 전자우편(E-mail)">
											<option value="">선택해 주세요.</option>
											<option value="direct" selected="selected">직접 입력</option>
											<option value="gmail.com">gmail.com</option>
											<option value="naver.com">naver.com</option>
											<option value="hanmail.net">hanmail.net</option>
											<option value="daum.net">daum.net</option>
											<option value="kakao.com">kakao.com</option>
											<option value="nate.com">nate.com</option>
										</select>
									</div>
									<!--  이메일 인증  str -->	
									<a href ="javascript:void(0);" name="authEmailGetbtn" id="authEmailGetbtn"  onclick="javascript:fn_emailChk();"  class="btn btn-secondary">인증번호 받기</a>
								</td>
								<td class="grid-input-2 ma_b_20">									
									<input type="text" name="authEmail" id="authEmail"  title="인증번호 입력값" style="display: none;width: 150px;margin-top: 10px;" placeholder="인증번호 5자리 입력"/>									
									<a href ="javascript:void(0);" name="authEmailChk" id="authEmailChk"  onclick="javascript:fn_authEmailChk();"  style="display: none; margin-top: 10px;"  class="btn btn-secondary">확인</a>								
									<div id="viewTimerE" style = "display: inline;"></div>
									<div id="stopContentE" style="display: none;"><span style="border-bottom:0px dotted red; color:red;">[시간초과] 재인증 바랍니다.</span></div>
									<!--  이메일 인증  end  -->
								</td>
							</tr>
							<tr>	
								<th>
									<label for="nicknm">문자 메시지 수신 동의</label>
								</th>
							</tr>
							<tr>
								<td>
									<input name="smsAgree" id="smsAgree1" type="radio" title="SMS 수신"  value="1" checked onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/><label for="smsAgree1" class="ma_r_10">수신</label>
									<input name="smsAgree" id="smsAgree2" type="radio" title="SMS 수신안함" value="0" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/><label for="smsAgree2">수신 안함</label>
									<input name="isSmsCheck01" id="isSmsCheck01" type="hidden" value="1" />	
									<p class="font_xsm starMark ma_t_3 font_blue ma_b_20"><span class="ir_so">중요 표시</span>공지사항이나 새소식 알림을 받을 수 있습니다.</p> 
								</td>
							</tr>
							<tr>
								<th>
									<label for="nicknm">이메일 수신 동의</label>
								</th>
							</tr>
							<tr>
								<td>
									<input name="emailAgree" id="emailAgree1" type="radio" title="E-mail 수신"  value="1" checked onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/><label for="emailAgree1" class="ma_r_10">수신</label>
									<input name="emailAgree" id="emailAgree2" type="radio" title="E-mail 수신안함" value="0" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/><label for="emailAgree2">수신 안함</label>
									<input name="isEmailCheck01" id="isEmailCheck01" type="hidden" value="1" />	
									<p class="font_xsm starMark ma_t_3 font_blue ma_b_20"><span class="ir_so">중요 표시</span>공지사항이나 새소식 알림을 받을 수 있습니다.</p> 
								</td>
							</tr>
							<tr>
								<th>이용약관<span class="starMark"><span class="ir_so">필수 입력 사항</span></span></th>
							</tr>
							<tr>
								<td>
									<p title="개인정보 수집(이용) 및 동의 안내">개인정보 수집 및 이용에 관한 안내</p>
									<textarea id="infotxt" name="infotxt" readonly title="개인정보 수집(이용) 및 동의 안내" class="w_100 font_xsm">
[개인정보의 수집•이용목적]
가. 홈페이지 회원에게 의과학 지식정보 서비스 제공
나. 개인식별,민원처리,고지사항 전달

[수집하는 개인정보의 항목]
가. 필수항목 : 아이디, 이름, 성별, 휴대폰번호, 생년월일, 주소, 전자우편
나. 선택항목 : 근무지 주소, 근무지 전화번호, 근무처, 부서명, 학교명

[개인정보의 보유 및 이용 기간]
회원 탈퇴 시까지

개인정보의 수집을 거부할 수 있으며, 거부할 경우 서비스 이용이 제한됩니다.													
										
									</textarea>
									<p>
										<label for="information" class="blind">(필수) 개인정보 수집•이용에 동의하십니까?</label>
										<input type="checkbox" id="information" title="동의체크" name="agree" value="1" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" class="ma_l_15 ma_t_-3" />동의함
									</p>
								</td>
							</tr>
							
						</tbody>
					</table>
				
				</div>
			</div>
			<div class="buttonBox">
				<a href="javascript: fncMemberIn();" class="btnN" role="button" title="회원가입 버튼">회원가입</a>
			</div>
		</div>
	
	</div>
		
	
</form>



<!--############## 핸드폰 인증 str ##############  -->
<div id = "dialog"  title="휴대전화 인증하기" aria-hidden="true" > 
	<p>입력하신 휴대전화번호로<br>
	인증번호가 발송되었습니다.<br><br>
	화면에 인증번호 5자리를 입력하고<br>
	확인 버튼을 클릭해 주십시오.<br><br></p>
	<span>(예시) 12345 (숫자 5자리)</span>
</div>	
<!--############## 핸드폰 인증 end ##############  -->



<!--############## 이메일 인증 str ##############  -->
<div id = "dialogE"  title="이메일 인증하기" aria-hidden="true" > 
	<p>입력하신 이메일 주소로<br>
	인증번호가 발송되었습니다.<br><br>
	화면에 인증번호 5자리를 입력하고<br>
	확인 버튼을 클릭해 주십시오.<br><br></p>
	<span>(예시) 12345 (숫자 5자리)</span> 
</div>	
<!--############## 이메일 인증 end ##############  -->














