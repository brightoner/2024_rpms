<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 

<script type="text/javascript">

//################################## sms 인증 관련 시작 ################################

$(function() {
	
});



var IS_SUBMIT = false;

function Enter_Check(){
    // 엔터키의 코드는 13입니다.
	if(event.keyCode == 13){
		fncMemberIn();  // 실행할 이벤트
	}
}

// 광고성정보 수신여부 수정
function fncMemberAgreeIn() {
	if( confirm("수정된 내용을 적용하시겠습니까?") ){
		$.ajax({
		    url: '${ctxt}/member/regi/infoMemberAgreeEdit.do',
		    data: $("form[name=agreeForm]").serialize(),
		    type: 'POST',
		    dataType: 'text',
		    cache: false,
		    success: function(result) {
		    	if(result=='Y'){ //성공
					  fn_showCustomAlert("수정되었습니다.");
				  }else{
					  fn_showCustomAlert("수정에 실패하였습니다.");
				  }
		    },
		    error : function(){
		      fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		    }
		});
	}
}
 


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
							fn_showCustomAlert("유효하지 않은 전화번호 입니다.");
							$(this).val("");
							$(this).focus();
						}
					}else{
					
						if(regExp_ctn.test(trans_num)){
							trans_num = $(this).val().replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3");
							$(this).val(trans_num);
						}else{
							fn_showCustomAlert("유효하지 않은 전화번호 입니다.");
							$(this).val("");
							$(this).focus();
						}
					}
				}else{
					fn_showCustomAlert("유효하지 않은 전화번호 입니다.");
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


<!-- 기본정보와 광고설정보 수정  form -->
<form name="agreeForm" method="post">
<h3 class="page_title" id="title_div"><span class="adminIcon"></span>개인정보 변경</h3>
	<div class="myPage">
		<div class="row section pa_0">
			<div class="relative pa_0">
				<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>수신 안함의 경우. 광고가 아닌 공지 및 안내는 발송됩니다.</span>
			<table class="table_v">
				<caption>회원정보 변경 - 광고성정보 수정</caption>
				<colgroup>
					<col width="20%">
				</colgroup>
				<tbody class="report">
					<tr>
						<th>
							<label for="user_id">아이디</label>
						</th>
						<td class="relative">
							<p><c:out value="${ptlUserRegVo.user_id}"></c:out></p>
						</td>
					</tr>
					<tr>
						<th>
							<label for="nicknm">이릅</label>
						</th>
						<td class="padding_l">
							<p><c:out value="${ptlUserRegVo.nicknm}"></c:out></p>
						</td>
					</tr>
					<tr>
						<th>
							<label for="mbtlnum">휴대전화</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
						</th>
						<td>
							<input name="mbtlnum" type="text" id="mbtlnum" value="${ptlUserRegVo.mbtlnum}" title="휴대전화 입력"   maxlength="13" class="w_30" />
						</td>
					</tr>
					<tr>
							<th>
								<label for="email">소속사항</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
							</th>
						
							
								<td class="grid-input-8">
										<!--소속  -->
									<label for="orgnm" class="blind">소속기관 :</label>
									<input id="orgnm" name="orgnm" title="소속기관"  type="text"  value="${ptlUserRegVo.orgnm}" maxlength="15" autocomplete="off" />
									<label for="deptnm" class="blind">소속부서 :</label>
									<input id="deptnm" name="deptnm" title="소속부서" type="text" value="${ptlUserRegVo.deptnm}" maxlength="15" autocomplete="off"  class="ma_l_3"/>
									<label for="posnm" class="blind">직책 :</label>
									<input id="posnm" name="posnm" title="직책" type="text"  value="${ptlUserRegVo.posnm}" maxlength="15" autocomplete="off" class="ma_l_3"/>
																			
								</td>
						
					<tr>
						<th>
							<label for="email">이메일</label><span class="starMark"><span class="ir_so">필수 입력 사항</span></span>
						</th>
						<td>
							<input type="text" id="email1" name="email1" title="이메일아이디" value="${fn:split(ptlUserRegVo.email,'@')[0]}"  maxlength="30"   class="w_20"/>
							<label for="email2" class="blind">@</label>
							<input type="text" id="email2" name="email2" title="이메일주소" value="${fn:split(ptlUserRegVo.email,'@')[1]}"  maxlength="30"  class="ma_l_3 w_25"/>
							
							<div class="selectRow in_block w_25">
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
					<tr>
						<th>
							<label for="">가입일</label>
						</th>
						<td>		
							<p><c:out value="${ptlUserRegVo.reg_date}"></c:out></p>		
						</td>
					</tr>	
				</tbody>
			</table>
			<div class="buttonBox">
				<a id="memberIn" href="javascript:fncMemberAgreeIn();" class="btnN" role="button" title="개인정보수정 버튼">개인정보수정</a>
			</div> 
		</div>
	</div>		
	</div>

</form>
  
  