<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<%

    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
    

	String sSiteCode = "CB653";				// NICE로부터 부여받은 사이트 코드
	String sSiteSeq = "MwNcgyVOPtnS";			// NICE로부터 부여받은 사이트 패스워드
    
    String sRequestNumber = "REQ0000000001";        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
                                                    	// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
  	session.setAttribute("REQ_SEQ", sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
  	
   	String sAuthType = "M";      	// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
   	
   	String popgubun 	= "Y";		//Y : 취소버튼 있음 / N : 취소버튼 없음
	String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지
	
	String sGender = ""; 			//없으면 기본 선택 값, 0 : 여자, 1 : 남자 
	
    // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
	//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
	String sReturnUrl = "https://moneybunny.kr/member/exchange/exchPassAuthentication.do";      // 성공시 이동될 URL
    String sErrorUrl = "https://moneybunny.kr/member/regi/checkplusFail.do";          // 실패시 이동될 URL
  //  String sReturnUrl = "http://localhost:9090/member/exchange/exchPassAuthentication.do";      // 성공시 이동될 URL
  //  String sErrorUrl = "http://localhost:9090/checkplus_fail.jsp";          // 실패시 이동될 URL
   
    

    // 입력될 plain 데이타를 만든다.
    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize + 
						"6:GENDER" + sGender.getBytes().length + ":" + sGender;
    
    String sMessage = "";
    String sEncData = "";
    
    int iReturn = niceCheck.fnEncode(sSiteCode, sSiteSeq, sPlainData);
   System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
   System.out.println(iReturn);
   System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    if( iReturn == 0 )
    {
        sEncData = niceCheck.getCipherData();
    }
    else if( iReturn == -1)
    {
        sMessage = "암호화 시스템 에러입니다.";
    }    
    else if( iReturn == -2)
    {
        sMessage = "암호화 처리 오류입니다.";
    }    
    else if( iReturn == -3)
    {
        sMessage = "암호화 데이터 오류입니다.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else
    {
        sMessage = "알 수 없는 에러입니다. iReturn : " + iReturn;
    }
%>
	<script type="text/javascript">
	var html="";
	var exchPsbVal = "${exchPsbVal}"; // 환전 가능 수량
	
	$(function(){     
	
	
		
		// 프로필 첨부 이미지의 크기를 정하고 그에 맞게 이미지를 등록하도록 한다. 
	 	 $("#identityImg").change(function(event) {
		
			
			var $target = $(this);
			
		    var file = event.target.files[0];
		     
		    if(!file){
		    	fn_showCustomAlert("파일이 존재 하지 않습니다.");
		    	$("#identityImgInput").val('');
	    		$target.val('');	
		    	return false;
		    }
		    
		    var fileName = file.name;
		    var fileSize = file.size;
		    var fileMaxSize =1024 * 1024 * 10; //10 MB    
		   
		    
		    
			var ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();	//파일확장장
		    if(ext != "jpg" && ext != "jpeg"  && ext != "png"){
				
		    	fn_showCustomAlert("파일확장자를 확인해 주세요.");
				
				$target.val('');	
				$("#identityImgInput").val('');
				return false; 
			}
			
		    if(ext == "jpg" || ext != "jpeg" || ext != "png"){
			    if(fileSize > fileMaxSize){
			    	fn_showCustomAlert("사이즈는 10MB 미만으로 등록 가능합니다. "); 	// 파일 용량제한 : 10MB
					$target.val('');
					$("#identityImgInput").val('');
					return false;   
				}
		    }

		    $("#identityImgInput").val(fileName);
		  
		});
	 	  
		
	 	// 채널 메인 첨부 이미지의 크기를 정하고 그에 맞게 이미지를 등록하도록 한다. 
	 	 $("#bankBookImg").change(function(event) {
		
		
			var $target = $(this);
			
		    var file = event.target.files[0];
		     
		    if(!file){
		    	fn_showCustomAlert("파일이 존재 하지 않습니다.");
		    	$target.val('');	
		    	$("#bankBookImgInput").val('');
		    	
		    	return false;
		    }
		    
		    var fileName = file.name;
		    var fileSize = file.size;
		    var fileMaxSize =1024 * 1024 * 10; // 10 MB    
		
		    
			var ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();	//파일확장장
		    if(ext != "jpg" && ext != "jpeg" && ext != "png"){
				
		    	fn_showCustomAlert("파일확장자를 확인해 주세요.");
		    	$target.val('');	
		    	$("#bankBookImgInput").val('');
				
				return false; 
			}
			
		    if(ext == "jpg" || ext != "jpeg" || ext != "png"){
			    if(fileSize > fileMaxSize){
			    	fn_showCustomAlert("사이즈는 10MB 미만으로 등록 가능합니다. "); 	// 파일 용량제한 : 10MB
					$target.val('');	
			    	$("#bankBookImgInput").val('');
			    	
					return false;   
				}
		    }
		    $("#bankBookImgInput").val(fileName);
		});
	 	
	 	
	 	// 숫자만 입력 
	 	$("#exch_item_cnt").on("input", function () {
             var inputValue = $(this).val();
             var intValue = parseInt(inputValue);

             if (isNaN(intValue)) {
                 $(this).val("");
             } else {
                 $(this).val(intValue);
             }
        }); 
     
	 	
	 	//아이템 현황 정보
	 	fn_item_situation();
	});

	function fn_validationChk(){

		//저장 체크 
		var exch_psb_cnt = $("#exch_psb_cnt").val();
		var exch_item_cnt = $("#exch_item_cnt").val();
		var chkVal = exchPsbVal;

		if(isEmpty(exch_item_cnt) || Number(exch_item_cnt) == 0){
			fn_showCustomAlert("환전 개수를 입력해 주십시오.");
			$("#exch_item_cnt").val("");
			$("#exch_item_cnt").focus();
			return false;
		}
		
		// 아이템 갯수 숫자 정규식
		var check = /^[0-9]+$/;
		if (!check.test(exch_item_cnt)) {
			fn_showCustomAlert("정수인 숫자만 넣어주세요.");			
			$("#exch_item_cnt").val("");
			$("#exch_item_cnt").focus();
			
			return false;
		}
	
		if(Number(exch_psb_cnt) < Number(exch_item_cnt)){
			fn_showCustomAlert("환전 신청 수량은 환전 가능한 아이템 개수보다 클 수 없습니다.");			
			return false;
		}
		
		
		if(isEmpty($("#bank_nm").val())){
			fn_showCustomAlert("은행명은 필수 입력 사항입니다.");			
			return false;
		}
		
		if(isEmpty($("#account_holder").val())){
			fn_showCustomAlert("예금주는 필수 입력 사항입니다.");			
			return false;
		}

		if(isEmpty($("#account_num").val())){
			fn_showCustomAlert("계좌번호는 필수 입력 사항입니다.");			
			return false;
		}
			
	    if(isEmpty($("input[name=file_0]:eq(0)").val())){
	    	fn_showCustomAlert("신분증 이미지 업로드는 필수입니다.");			
			return false;
	    }  
	    
	    if(isEmpty($("input[name=file_1]:eq(0)").val())){
	    	fn_showCustomAlert("통장사본 이미지 업로드는 필수입니다.");			
			return false;
	    }   
	    
		
		if(!$("#information").is(":checked")){
			fn_showCustomAlert("환전 약관(이용 약관)에 동의 하셔야 환전이 가능합니다.");
			$("#information").focus();
			return false;
		}
		
	
	
		
		if(isEmpty(chkVal)){
			chkVal = '200';
		}
		
		if(isNaN(exch_item_cnt)){
			fn_showCustomAlert("입력한 값이 숫자(정수)형식이 아닙니다.");
			
			return false;
			
		}
		if(Number(exch_item_cnt) < Number(exchPsbVal)  ){
			fn_showCustomAlert("환전 가능 수량은 " + chkVal + "개 이상 환전 신청 가능합니다.");
			
			return false;
		}
		
		
		return true;
	}
	
	function fn_exchInfoSave(){
	 
	
		fn_validationChk(); // 유효성 체크
		
		
		var form = $('#exchFrm')[0];
	    var formData = new FormData(form);	
		

			$.ajax({
				url : '${ctxt}/member/exchange/saveExchBaseInfo.do',
				type:'POST',			
				data:formData,				
				enctype: "multipart/form-data",
				processData: false,    
			    contentType: false,  
				async : false,
				dataType :'json',
				success : function(result) {
					if(!isEmpty(result.exchChkVal)){
						if(result.exchChkVal == 'SUC'){
							fn_showCustomAlert("환전 신청을 완료 하였습니다.");
						}else if(result.exchChkVal == 'FAIL'){
							fn_showCustomAlert("환전 신청 수량은 환전 가능한 아이템 개수보다 클 수 없습니다."); // CASE4)
						}else if(result.exchChkVal == 'NOTNUM'){
							fn_showCustomAlert("환전 가능 수량 및 환전 신청 수량 확인이 필요합니다.<br/>다시 로그인 후 신청해 주십시오.<br/>CASE 3) NOTNUM Check" , "e");
						}else if(result.exchChkVal == 'NULL'){
							fn_showCustomAlert("환전 가능 수량 및 환전 신청 수량 확인이 필요합니다.<br/>다시 로그인 후 신청해 주십시오.<br/>CASE 2) Null Check" , "e");
						}else if(result.exchChkVal == 'NONE'){
							fn_showCustomAlert("환전 가능 수량 및 환전 신청 수량 확인이 필요합니다.<br/>다시 로그인 후 신청해 주십시오.<br/>CASE 1) NONE Check" , "e");
						}
					}
					
						
				},
				error : function() { // Ajax 전송 에러 발생시 실행
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행
					
				
					fn_item_situation();
				
					$('#exchFrm')[0].reset();
				//	location.reload();
				
				}
			});
		
		
		
	}


	function fn_delete_exch_imgFile(atch_img_id , gbn) {

		if (confirm("이미지를 삭제하시겠습니까?")) {

			var params = {};
			params.atch_img_id = atch_img_id;
			params.exch_base_id = $('#exch_base_id').val();
			params.gbn = gbn;
			
			$.ajax({
				url: "/member/exchange/deleteExchBaseInfoImgFileInf.do",
				type: 'POST',
		        dataType: 'text',
				data     :  params,
				success  : function(data) {
				 	if(data == "ok"){ location.reload();}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					alert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.');
				}
			});

		}

	}
	

	function fn_item_situation(){
		var situ_html = "";
		var params = {};

	    $.ajax({
	        url: '${ctxt}/member/exchange/readExchSituation.do',
	        data: params,
	        type: 'POST',     
	        dataType: 'json',
	        success: function(result) {
	   			
	        	if(!isEmpty(result) && !isEmpty(result.situationData)){
	        	
				
					situ_html += '<tr>';
					situ_html += ' <td>'+addComma(Number(result.situationData.total_item_cnt))+ '</td>';
					situ_html += ' <td><span class="highlight big">'+addComma(Number(result.situationData.exch_psb_cnt))+'</span></td>'; 
					situ_html += ' <td>'+addComma(Number(result.situationData.exch_get_cnt))+'</td>';	
					situ_html += ' <td>'+addComma(Number(result.situationData.exch_review_cnt))+'</td>';	
					situ_html += '</tr>';
					
					$("#exch_psb_cnt").val(result.situationData.exch_psb_cnt);
					
				    $('#item_dataList').html(situ_html);
	        	}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
				
					$("#item_dataList").html('<tr><td>0</td><td>0</td><td>0</td><td>0</td></tr>');
				}
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('나의 아이템 현황 조회에 오류가 발생하였습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
	}

	
function fn_niceIdAuth(){
	   // 유효성 체크를 먼저 실행
  if (!fn_validationChk()) {
        return; // 유효성 체크에 실패한 경우 중단
    }
	
	var strConfirm;		
	strConfirm = confirm("본인 인증 후 환전 신청 처리됩니다.\n환전 신청 하시겠습니까?");
	
	if(strConfirm){
		window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
		document.form_chk.target = "popupChk";
		document.form_chk.submit();
	}
}

</script>

<!-- 휴대폰 실명인증 -->
<form name="form_chk" method="post">
	<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
	<input type="hidden" name="EncodeData" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
</form>
<!-- 본문내용 -->
<div id="container">
   <div id="divRefreshArea">
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>환전 신청하기</h3>  
		
		<table class="table_h" >
			<caption>
			 	나의 아이템 현황
			</caption>
			<colgroup>
				<col style="width:25%">
				<col style="width:25%">
			    <col style="width:25%">
			    <col style="width:25%">
			</colgroup>
			<thead>
				<tr>
					<th scope='col' colspan="4">나의 아이템 현황</th>						
				</tr>
				<tr>
					<th scope='col'>선물 받은 아이템</th>						
					<th scope='col'>환전 가능한 아이템</th>
					<th scope='col'>환전 받은 아이템</th>
					<th scope='col'>환전 검토 중인 아이템</th>
				</tr>
			</thead>
			<tbody id="item_dataList">
			
			</tbody>  
		</table>

		<form name="exchFrm" id ="exchFrm" method="post"  action="" enctype="multipart/form-data">			
			<%-- <input type="hidden" name="exch_base_id" id = "exch_base_id" value="${data.exch_base_id}" />
			<input type="hidden" name = "atch_identity_img_id" id = "atch_identity_img_id" value = "${data.atch_identity_img_id}" />
			<input type="hidden" name = "atch_bankbook_img_id" id = "atch_bankbook_img_id" value = "${data.atch_bankbook_img_id}" /> --%>
		
			<input type="hidden" name = "exch_psb_cnt" id = "exch_psb_cnt" value = "" />
				
			<table class="table_v" >
				<caption>
				 	환전 수량 입력
				</caption>
				<colgroup>
					<col style="width:20%">
					<col style="width:80%">
				    
				</colgroup>
				<thead>
					<tr>
						<th scope='col' colspan="2">환전 수량</th>						
					</tr>
					
				</thead>
				<tbody id="item_exchInfo">
					<tr>
						<th>환전 신청할 아이템 개수 </th>
						<td>
							<input type="text" name="exch_item_cnt" id="exch_item_cnt" maxlength="10" value = ""  class="w_20"> <span> 개 </span>
							<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>환전 가능 수량 : ${exchPsbVal} 개  이상</span>			
						</td>
					</tr>
				</tbody>  
			</table>
	
				
				<table class="table_v">  
					<colgroup>
						<col width="10%">
						<col width="40%">  
						<col width="10%">
						<col width="40%">  
					</colgroup>
					<thead>
						<tr>
							<th colspan="4">환전 기본 정보 입력</th>
						</tr>
					</thead>
					<tbody>							
					<tr>
						<th>은행명 </th>
						<td colspan="3">
							<input type="text" name="bank_nm" id="bank_nm" maxlength="10" value = "" >
						</td>
					</tr>
					<tr>
						<th>예금주</th>  
						<td colspan="3">				
							<input type="text" name="account_holder" id="account_holder" maxlength="10" value = "" >
							
						</td>     
					</tr>    
					<tr>
						<th>계좌번호</th>  
						<td colspan="3">				
							<input type="text" name="account_num" id="account_num" maxlength="25" value = "" >				
						</td>     
					</tr>       		
				<%-- 
					<tr>
						<th>신분증<br>이미지</th>
					    <td>
					     <div>
				    		<c:import url="/cmm/fms/selectImageFileInfs.do" charEncoding="utf-8">
								<c:param name="atch_img_id"   value="${data.atch_identity_img_id}" />
								<c:param name="menuGbn" value="identity" />
							</c:import>
						</div>
						</td>
						<th>통장사본<br>이미지</th>
					    <td>
				    		<c:import url="/cmm/fms/selectImageFileInfs.do" charEncoding="utf-8">
								<c:param name="atch_img_id"   value="${data.atch_bankbook_img_id}" />
								<c:param name="menuGbn" value="bankbook" />
							</c:import>			
						</td>
				    </tr>	
			     --%>
				    <tr> 
						<th>신분증<br/>업로드</th>
		  
						<td colspan="3"> 
							<input id="identityImgInput"  type="text" name="file_0" title="파일첨부"  readonly class="w_60" />
							<label for="identityImg" class="btn btn-secondary" style="position: absolute;width:150px">
						 		신분증 이미지 찾기
						   		<input id="identityImg"  type="file" name="file_0" title="파일첨부" accept=".jpg,.jpeg,.gif,.png" style="opacity: 0;overflow:hidden;right: 0;top: 0;position: absolute;width: 100%;" />
						  	</label>
						</td>
							
					</tr>	
					<tr>
						<th>통장사본<br/>업로드</th>
						<td colspan="3"> 
						
						    <input id="bankBookImgInput"  type="text" name="file_1" title="파일첨부"  readonly class="w_60" />
						    <label for="bankBookImg" class="btn btn-secondary" style="position: absolute;width:150px">
							       통장사본 이미지 찾기
						        <input id="bankBookImg"  type="file" name="file_1" title="파일첨부" accept=".jpg,.jpeg,.gif,.png" style="opacity: 0;overflow:hidden;right: 0;top: 0;position: absolute;width: 100%;" />
					   		</label> 
						</td>
					</tr>
					<tr>
						<th>환전<br/> 약관</th> 
						<td colspan="3">
							<div style = "overflow: hidden;overflow-y: scroll;height: 185px;padding: 20px;border: 1px solid #cfcfcf;">
								<h2 class="title bor_b">환전 약관(sample)</h2><br>
					
								<div class="mt-4">
								  <h5>제 1 조 (개인정보의 수집 및 이용목적)</h5>
								  <ul>
									<li>회사는 회원이 선물받은 팝콘에 대한 환전인증을 신청하는 경우 회원식별, 본인확인, 회원 계좌 확인 등 환전인증의 목적으로 회원의 동의 하에 관계 법령에서 정하는 바에 따라 제2조 제1항 각호의 개인정보(이하 ‘개인정보’라고 함)를 수집합니다</li>					 
								  </ul>
								</div>
								<div class="mt-4">
								  <h5>제 2 조 (수집하는 개인정보의 항목 및 수집방법)</h5>
								  <ul>
										<li>① 회사는 팝콘 환전을 위하여 회원의 신분증(주민등록증, 외국인등록증, 운전면허증, 여권) 및 통장 사본을 수집하고 있습니다. 이에 따라 수집되는 개인정보는 다음 각호와 같습니다.</li>
										<li>가. 신분증 사본 : 회원의 얼굴이 촬영된 사진, 이름, 주민등록번호 또는 외국인등록번호, 운전면허번호 또는 여권번호, 주소 등</li>
										<li>나. 통장 사본 : 예금주의 이름, 은행명, 계좌번호 등</li>
										<li>② 회원은 원칙적으로 팝콘티비 홈페이지 또는 어플리케이션을 통하여 회사에게 개인정보를 제공하여야 하고,</li> 
										<li>예외적으로 회사의 별도 요청이 있는 경우에 한하여 이메일, 팩스, 배송, 방문제출 등의 방법으로 개인정보를 제공하여야 합니다.</li>
								  </ul>
								</div>
					
								<div class="mt-4">
									<h5>제 3 조 (개인정보의 보유 및 이용기간)</h5>
									<ul>
										<li>회사는 회원이 팝콘티비에서 탈퇴하기 전까지 개인정보를 보유 및 이용합니다. 다만, 회원이 팝콘티비에서 탈퇴한 후에도 전자상거래 등에서의</li>
										
										<li>소비자보호에 관한 법률 등 관련 법령의 규정에 의하여 보존할 필요가 있는 경우에는 일정 기간 동안 보존합니다.</li>
									</ul>
								</div>
				
								<div class="mt-4">
									<h5>제 4 조 (개인정보의 수집 동의 거부 등)</h5>
									<ul>
										<li>회원은 회사가 회원의 개인정보를 수집 및 이용하는 것을 거부할 권리가 있습니다. 다만, 회원이 이를 거부하는 경우 회사는 회원의 팝콘을 </li>
										<li>환전 신청을 거부할 수 있습니다.</li>
									</ul>
								</div>
									<div class="mt-4">
									<h5>제 5 조 (환전인증의 승인 또는 거부)</h5>
									<ul>
										<li>① 회사는 회원의 개인정보와 계정정보, 방송 화면 등을 비교 및 검토하여 그 동일성을 확인한 후 회원의 환전인증 신청을 승인 또는 거부할 수 있습니다.</li>
										<li>② 회사는 환전인증 거부시 그 사유를 통지할 수 있고, 그와 함께 추가적인 증빙자료를 제출하도록 요청할 수 있습니다.</li>
										<li>③ 회원이 제2항에 따른 거부 사유를 해소하지 못하거나 추가적인 증빙자료를 제출하지 않음으로써 회원이 환전을 하지 못하는 것에 대하여 회사는 책임을 지지 않습니다.</li>
									</ul>
								</div>
								<div class="mt-4">
									<h5>제 6 조 (회원의 의무)</h5>
									<ul>
										<li>① 회원은 환전인증과 관련하여 다음의 사항을 준수하여야 합니다.</li>
										<li>가. 회원은 타인의 개인정보를 도용하거나 허위의 개인정보를 기재해서는 안 됩니다.</li>
										<li>나. 회원은 위조 또는 변조된 증빙자료나 허위의 증빙 자료를 제출해서는 안 됩니다.</li>
										<li>다. 회원은 해킹 프로그램 또는 그 밖의 부정한 목적의 프로그램 등을 이용하여 회사의 업무에 지장을 주어서는 안 됩니다.</li>
										<li>라. 회원은 타인의 계정을 이용하여 환전인증을 신청해서는 안 됩니다.</li>
										<li>② 회사는 제1항을 위반함으로써 회원에게 발생하는 불이익 내지 손해에 대하여 아무런 책임이 없습니다</li>
										<li>③ 회사는 제1항을 위반한 회원을 상대로 서비스 이용제한 및 적법 조치를 포함하여 가능한 모든 제재를 가할 수 있습니다.</li>
									</ul>
								</div>
							 </div>	
							 
						   	 <p class="ma_t_10">
								<label for="information" class="blind">(필수)이용 약관에 동의하십니까?</label>
								<input type="checkbox" id="information" title="동의체크" name="agree" value="1" onkeypress="if(event.keyCode == 13){ fncMemberIn(); return; }" class="ma_l_15" />동의함
							 </p>
						 </td>
					</tr>						
					</tbody>
				</table>
		
		 </form> 
		       
		 <div class="flex_box">
			 <div class="buttonBox">
			     <a href="javascript:fn_niceIdAuth();" class="btnN" role="button"><span>환전 신청</span></a>
			 </div>
		 </div>
		         
	</div>

</div>
    
   
