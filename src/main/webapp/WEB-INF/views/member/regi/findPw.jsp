<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	String sReturnUrl = "https://moneybunny.kr/member/regi/passPw.do";      // 성공시 이동될 URL
    String sErrorUrl = "https://moneybunny.kr/member/regi/checkplusFail.do";          // 실패시 이동될 URL
	//String sReturnUrl = "http://localhost:9090/member/regi/passPw.do";      // 성공시 이동될 URL
   // String sErrorUrl = "http://localhost:9090/checkplus_fail.jsp";          // 실패시 이동될 URL
  

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
var IS_SUBMIT = false;

//휴대폰 실명인증 팝업
window.name ="Parent_window";

$(function() {
	
	$("input[name=mbtlnum]").on("blur", function(){
		var trans_num = $(this).val().replace(/-/gi,'');
			if(trans_num != null && trans_num != ''){
				if(trans_num.length==11 || trans_num.length==10) {
					var regExp_ctn = /^01([016789])([1-9]{1})([0-9]{2,3})([0-9]{4})$/;
					if(regExp_ctn.test(trans_num)){
						trans_num = $(this).val().replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3");
						$(this).val(trans_num);
					}else{
						fn_showCustomAlert("유효하지 않은 전화번호입니다.");
						$(this).val("");
						$(this).focus();
					}
				}else{
					fn_showCustomAlert("유효하지 않은 전화번호입니다.");
					$(this).val("");
					$(this).focus();
				}
			}
		});	
	
});

function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

//password 찾기
function fn_searchPass(){
	if ($("#mbtlnum").val() == "") {
		fn_showCustomAlert("휴대폰 실명 인증하여 주시기 바랍니다.");
		$("#mbtlnum").focus();
		return;
	}
	
} 

</script>

<c:choose>
	<c:when test="${idSecSearch == 'idSearch'}">
		<span id="page_name" class="hide">ID 찾기</span>
		<div class="content_body idpw">
			<div class="row section">
				<div class="row bg">
					<h3 class="page_title pa_0 ma_b_30">ID 찾기</h3>
					<p class="barMark ma_b_10">아이디 찾기를 위해 다음 단계를 진행해 주시기 바랍니다. </p>
					 <div class="text_c ma_t_20 pa_t_10" id="id_area" style="display: none;">
						<span id="find_id"></span>
					</div>
					<div class="ma_t_40 pa_t_10">
						<form name="reqForm" method="post" style="width: fit-content; margin: 0 auto;">
							<div class="idpw-form">
								<div>
									<a href="javascript:fnPopup();" class="button big" role="button" title="새 창에서 열림">휴대폰 실명인증</a>
						    	</div>
								
						    </div>
							<!-- 추가 PARAM -->
							<input type="hidden" name="page" id="page" value="" alt="pageNum" /> 
						 	<input type="hidden" name="idSecGbn" id="idSecGbn" value="idSearch" alt="idSecGbn" /> 
						 	<!-- 공통  필수 PARAM -->
						 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt="token"/>
						</form>
						<!-- 휴대폰 실명인증 -->
						<form name="form_chk" method="post">
							<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
							<input type="hidden" name="EncodeData" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
						</form>
					</div>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<span id="page_name" class="hide">비밀번호 찾기</span>
		<div class="content_body idpw">
			<div class="row bg">
				<div class="inner-row">
					<h3 class="page_title pa_0 ma_b_50">비밀번호 찾기</h3>
					<p class="barMark ma_b_10">비밀번호 변경을 위해 다음 단계를 진행해 주시기 바랍니다.</p>
					<p class="barMark ma_b_10">휴대폰번호 인증 후 찾기 버튼을 누르시면 <span class="font_red bold">비밀번호 재설정을 위한 페이지로 연결</span>됩니다.</p>
				
					<div class="ma_t_40">
						<form name="reqForm" method="post" style="width: fit-content; margin: 0 auto;">
							<div class="idpw-form">
								<div class="ma_t_20">
									<!-- <label for="mbtlnum" class="">휴대폰번호</label>
									<input name="mbtlnum" type="text" id="mbtlnum" value="" title="휴대폰 번호 입력" readonly="readonly" placeholder="휴대폰 실명인증을 해주세요."/> --> 
							    	<a href="javascript:fnPopup();" class="button big" role="button" title="새 창에서 열림">휴대폰 실명인증</a>
								   <!--  <div class="btn-box ma_t_20">
								    	<a href="javascript:fn_searchPass();" class="btnN" role="button" style="padding: 0 40px;" title="찾기 버튼">찾기</a>
								    </div> -->
							    </div>
						    </div>
							<!-- 추가 PARAM -->
							<input type="hidden" name="page" id="page" value="" alt="pageNum" /> 
						 	<input type="hidden" name="idSecGbn" id="idSecGbn" value="secSearch" alt="idSecGbn" />
						 	<!-- 공통  필수 PARAM -->
						 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt="token"/>
						</form>
						<!-- 휴대폰 실명인증 -->
						<form name="form_chk" method="post">
							<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
							<input type="hidden" name="EncodeData" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
						</form>
					</div>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
