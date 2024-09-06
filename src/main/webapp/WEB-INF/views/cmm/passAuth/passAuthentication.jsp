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
	String sReturnUrl = "https://moneybunny.kr/member/regi/passAuthentication.do";      // 성공시 이동될 URL
    String sErrorUrl = "https://moneybunny.kr/member/regi/checkplusFail.do";          // 실패시 이동될 URL
   
    

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

function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

</script>


<span id="page_name" class="hide">pass 인증</span>
<div class="content_body idpw">
	<div class="row section bg age19 relative">
		<div class="row">
			<h3>실명 인증 후 이용 가능합니다.</h3>
			<p>별풍선결제, BJ승인신청, 19+ 영상을 시청하기 위해서는<br/>실명인증을 거친 후 성인이 확인되어야 이용 가능합니다.</p>
			<p class="caution"><strong>19세 미만의 청소년은 이용할 수 없습니다.</strong></p>
			
			<div class="pa_t_10">
				<form name="reqForm" method="post" style="width: fit-content;">
					<div class="idpw-form">
						<div class="ma_t_10">
							<a href="javascript:fnPopup();" class="button big" role="button" title="새 창에서 열림">휴대폰 실명인증</a>
				    	</div>
				    <%-- 	
						<!-- pass 인증이 되면 버튼 활성화 20230602-->
						<c:if test="${mbtlnum_yn eq 'Y'}">
							<div class="ma_t_10">
								<a href="javascript:fnNext();" class="button big" role="button" title="새 창에서 열림">BJ승인신청으로 이동</a>
					    	</div>				    	
						</c:if>
				    	
				    	 --%>
				    </div>
					<!-- 추가 PARAM -->
					<input type="hidden" name="page" id="page" value="" alt="pageNum" /> 
				 	<input type="hidden" name="idSecGbn" id="idSecGbn" value="idSearch" alt="idSecGbn" /> 
		
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
