<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>

<script type="text/javascript">
var tDate = "";
$(function(){
	 	 
	 
	     // 현재 날짜 객체 생성
	    var today = new Date();
	    // 날짜 정보 추출
	    var tYear = today.getFullYear();
	    var tMonth = today.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더함
	    var tDay = today.getDate();

	    tDate = tYear+"-"+(tMonth < 10 ? '0' : '') + tMonth+ "-"+tDay;
	 
	 
});


function Enter_Check(){
    // 엔터키의 코드는 13입니다.
	if(event.keyCode == 13){
		fncMemberIn();  // 실행할 이벤트
	}
}

var IS_SUBMIT = false;

// BJ 승인신청
function approval() {
	
	if ($("#bc_contents").val() == "") {
		fn_showCustomAlert("방송 컨텐츠를 입력하여 주시기 바랍니다.");
		$("#bc_contents").focus();
		return false;
	}	
	if ($("#bc_schdl_date").val() == "") {
		fn_showCustomAlert("방송 시작 예정일을 선택하여 주시기 바랍니다.");
		$("#bc_schdl_date").focus();
		return false;
	}
	
	if(tDate > $("#bc_schdl_date").val()){
		fn_showCustomAlert("방송 시작 예정일은 오늘 날짜 보다 작을 수 없습니다.");
		$("#bc_schdl_date").focus();
		return false;
	}
	
	if ($("#bc_cycle").val() == "") {
		fn_showCustomAlert("방송 주기를 입력하여 주시기 바랍니다.");
		$("#bc_cycle").focus();
		return false;
	}
	
	
	if ($("#bc_history").val() == "") {
		fn_showCustomAlert("과거 방송 이력 정보를 입력하여 주시기 바랍니다.");
		$("#bc_history").focus();
		return false;
	}
	
	IS_SUBMIT = true;
	
	if( IS_SUBMIT )	{
   	    $.ajax({
 		    url: '${ctxt}/member/inform/insertBjApprl.do',
 		    data: $("form[name=apprlForm]").serialize(),  
 		    type: 'POST',
 		    dataType: 'text',
 		    cache: false,
 		   	async: false,
 		    success: function(result) {
 				  if(result == "Y"){// 성공  					 				
  					
  					
  					 	 window.location.href = "${ctxt}/member/infoChB/memberBj.do";			
  				
 				  }else if(result =="P"){ 					  
 					 fn_showCustomAlert("승인 신청 중인 건이 존재합니다."); 					  
 				  }else if(result =="F"){ 					  
 					 fn_showCustomAlert("BJ 승인신청을 실패했습니다."); 					  
 				  }else if (result =="NOTLOGIN"){
 					 fn_showCustomAlert("로그인 상태를 확인하여 주십시오" , "e");
 				  }
 		   },
 		   error : function(){                  
 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
 		   }
 	});
	 		
	  }	else {
		  fn_showCustomAlert("처리중입니다!");
	  }	
	
}


// 이전화면으로가기
function fn_back(){
	var form = document.apprlForm;
	form.action = '${ctxt}/member/infoChB/memberBj.do';
	form.submit();	
}



</script>
<style>
	table * {
		font-size: 14px;
	}
</style>


<form name="apprlForm" method="post">
	<h3 class="page_title">BJ 승인  신청</h3>  
	
		<div class="row section">
			<div class="relative">
				<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>BJ 승인 신청 본인 인증 완료</span>
 				<table class="table_v">
					<caption>BJ 승인 신청</caption>
					<colgroup>
						<col width="15%">
						<col width="85%">
					</colgroup>
					<tbody class="report">
						<tr>
							<th>
								방송 컨텐츠
							</th>
							<td>
								<input type="text" id="bc_contents" maxlength="100" name="bc_contents" value="" title="방송컨텐츠" >
							</td>
						</tr>
						<tr>
							<th>
								방송 시작 예정일
							</th>
							<td>
								<input type="date" id="bc_schdl_date" name="bc_schdl_date" value="" title="방송시작예정일">
							</td>
						</tr>
						<tr>
							<th>
								방송 주기
							</th>
							<td>
								<input type="text" id="bc_cycle" name="bc_cycle" value="" title="방송주기" >
							</td>
						</tr>
						<tr>
							<th>
								과거 방송 이력 정보
							</th>
							<td>
								<textarea id="bc_history" name="bc_history" maxlength="400"  rows="10" style="width:100%;ime-mode:active" ></textarea>
							</td>
						</tr>
						<tr>
							<th>
								기타 정보
							</th>
							<td>
								<textarea id="bc_etc" name="bc_etc" rows="10" maxlength="400" style="width:100%;ime-mode:active" ></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				
				
				<div class="buttonBox">
					<a href="javascript: approval()" class="btnN" role="button" style="" title="BJ승인신청">BJ승인신청</a>
					<a href="javascript: fn_back()" class="btnN" role="button" style="" title="이전화면">이전화면</a>
				</div>
			
			</div>
		</div>		

	
	
</form>
