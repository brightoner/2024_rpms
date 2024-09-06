<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>

<script type="text/javascript">

	

function Enter_Check(){
    // 엔터키의 코드는 13입니다.
	if(event.keyCode == 13){
		fncMemberIn();  // 실행할 이벤트
	}
}


//닉네임 중복확인
function nickChk() {

	if ($("#nicknm").val() == "") {
		fn_showCustomAlert("닉네임을 입력하여 주시기 바랍니다.");
		$("#nicknm").focus();
		return;
	}
	
	if ($("#nicknm").val().length < 3) {
		fn_showCustomAlert("닉네임은  2자리 이상 입력하여 주시기 바랍니다.");
		$("#nicknm").focus();
		return;
	}	
	if ($("#nicknm").val().length >15) {
		fn_showCustomAlert("닉네임은 15자리가 넘을 수 없습니다.");
		$("#nicknm").focus();
		return;
	}	
	
	var findStr = " ";
	if ($("#nicknm").val().indexOf(findStr) != -1) {
		fn_showCustomAlert("닉네임에 공백이 포함되어 있습니다.");
		$("#nicknm").focus();
		return;
	}
	/* if (fn_korChk($("#nicknm"))){
		fn_showCustomAlert("한글은 입력할수 없습니다.");
		$("#nicknm").focus();
		return;
	}; */
	
	var params = {};
		params.nicknm = $('#nicknm').val();
	
		$.ajax({
		    url: '${ctxt}/member/regi/nickChk.do',
		    data: params,
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {
		    	
				if(result == "Y"){// 중복 
					
					fn_showCustomAlert("사용 불가능한 닉네임입니다.\n다른 닉네임을 사용해 주세요.");
				    $("#nicknm").val("");
				    $("#nickChkVal").val("");
					$("#nickChkChange").val("");
					
				}else if(result =="N"){ // 사용가능 		
					
					fn_showCustomAlert("사용 가능한 닉네임입니다.");
					$("#nickChkVal").val("Y");
					$("#nickChkChange").val($('#nicknm').val());
				}else{	
					
					fn_showCustomAlert("닉네임을 입력해 주십시오.");
				    $("#nicknm").val("");
				    $("#nickChkVal").val("");
				    $("#nickChkChange").val("");
				}
		    },
		    error : function(){                              // Ajax 전송 에러 발생시 실행
		      fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		    }
		});
}



function fncMemberIn() {
	
	if ($("#nicknm").val() == "") {
		fn_showCustomAlert("닉네임을 입력하여 주시기 바랍니다.");
		$("#nicknm").focus();
		return;
	}	 
	if ($("#nicknm").val().length < 3) {
		fn_showCustomAlert("닉네임은  2자리 이상 입력하여 주시기 바랍니다.");
		$("#nicknm").focus();
		return;
	}	
	if ($("#nicknm").val().length > 15) {
		fn_showCustomAlert("닉네임은 15자리가 넘을 수 없습니다.");
		$("#nicknm").focus();
		return;
	}	
	
	if ($("#nickChkVal").val() == "") {
		fn_showCustomAlert("닉네임 중복확인을 하여 주시기 바랍니다.");
		$("#nicknm").focus();
		return;
	}
	
	// 닉네임 중복확인 후 .. 닉네임 바꿔 저장하는걸 방지.. 
	var nickCh = $("#nickChkChange").val();
	var nicknm = $('#nicknm').val();	
	
	if(nickCh != nicknm){
		fn_showCustomAlert("닉네임 중복확인을 다시 진행해 주세요.");
		return;
	}
	
    if( confirm("수정된 내용을 적용하시겠습니까?") ){
   	    $.ajax({
 		    url: '${ctxt}/member/regi/memberNickNmEdit.do',
 		    data: $("form[name=reqForm]").serialize(),  
 		    type: 'POST',
 		    async : false, //폼 등록은 동기화가 맞아야하므로 false
 		    dataType: 'text',
 		    cache: false,
 		    success: function(result) {
 				  if(result == "Y"){// 성공  					
 					 fn_showCustomAlert("수정되었습니다.");
 				  }else if(result =="F"){ 					  
 					 fn_showCustomAlert("수정된 정보가 없습니다."); 					  
 				  }else if(result =="E"){ 					  
 					 fn_showCustomAlert("수정 중 에러가 발생하였습니다."); 					  
 				  }else if(result =="N"){ 					  
 					 fn_showCustomAlert("회원정보 수정 중 에러가 발생하였습니다."); 					  
 				  }else{
 					 fn_showCustomAlert("수정에 실패하였습니다.");
 				  }
 				  
 				  location.reload();
 				  
 		   },
 		   error : function(){ // Ajax 전송 에러 발생시 실행
 			  fn_showCustomAlert("오류가 발생했습니다. 관리자에게 문의 바랍니다.");
 		   }
 		});
	 }		 
}




$(function(){
	
	
});
	

</script>
<style>
	table * {
		font-size: 14px;
	}
</style>

<form name="reqForm" method="post">

	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>닉네임 변경</h3>  
	<div class="myPage">
		<div class="row section pa_0">
			<div class="relative pa_0">
			<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>필수 입력 사항</span>
			<table class="table_v">
				<caption>회원정보 변경 - 닉네임</caption>
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody class="report">
					
					<tr>
						<th>
							<label for="nicknm">닉네임</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>	
						</th>
						<td>
							<input name="nicknm" type="text" id="nicknm" class="w_60" value="" title="닉네임" placeholder=""  maxlength="15" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }"/>
							<input name="nickChkVal" type="hidden" id="nickChkVal" value="" />
							<input name="nickChkChange" type="hidden" id="nickChkChange" value="" />
					
							<a href ="javascript:void(0);" name="nickchk" id="nickchk"  onclick="javascript:nickChk();" class="btn btn-secondary btn-lg">닉네임 중복확인</a>
						</td>
					</tr>
					
				</tbody>
			</table>
			<div class="buttonBox">
				<a id="memberIn" href="javascript:fncMemberIn();" class="btnN" role="button" title="닉네임수정 버튼">닉네임 수정</a>
			</div> 
		</div>
	</div>		
	</div>
	
	<!-- 공통  필수 PARAM  -->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt = "token" />
	
</form>
