<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<script type="text/javascript">



function Enter_Check(){
    // 엔터키의 코드는 13입니다.
	if(event.keyCode == 13){
		fncMemberIn();  // 실행할 이벤트
	}
}

function approval() {
	
	var form = document.apprlForm;
	form.action = '${ctxt}/member/inform/approvalBj.do'; 
	form.submit();
}


</script>
<style>
	table * {
		font-size: 14px;
	}
</style>


<form name="apprlForm" method="post">

<h3 class="page_title" id="title_div"><span class="adminIcon"></span>BJ 승인신청</h3> 
	<div class="myPage">
		<div class="row section pa_0">
			<div class="relative pa_0">
			
				<!-- BJ 승인여부 -->
				<table class="table_v">
					<caption>BJ 승인신청</caption>
					<colgroup>
						<col width="15%">
						<col width="85%">
					</colgroup>
					<tbody class="report">
						<tr class="only">
							<th>
								<label for="nicknm">BJ 승인여부</label>
							</th>
							<td>
								<c:choose> 
									<c:when test="${resultMap.BJ_AUTH eq 'Y'}">
										<p>BJ 권한이 승인되었습니다.</p>										
									</c:when> 
									<c:when test="${resultMap.BJ_AUTH eq 'N'}">
										BJ 승인 신청이 반려되었습니다.
										<a id="approval" href="javascript:approval();" class="btnN ma_l_20" role="button" title="BJ승인신청 버튼">BJ승인신청</a>
										<br>
										<p>처리내용 : ${resultMap.CONFIRM_COMMENT}</p>
									</c:when>
									<c:when test="${resultMap.BJ_AUTH eq 'F'}">
										<font color="red">BJ 승인 신청이 취소되었습니다. </font>
										<br>1:1게시판을 통해 관리자에게 문의해주세요.
										<br>
										<p>처리내용 : ${resultMap.CONFIRM_COMMENT}</p>
									</c:when> 
									<c:when test="${resultMap.BJ_AUTH eq 'P'}">
										<p>BJ 승인 신청이 진행중입니다.</p>
									</c:when> 
									<c:otherwise>
										BJ 권한이 없습니다.
											<a id="approval" href="javascript:approval();" class="btnN ma_l_20" role="button" title="BJ승인신청 버튼">BJ승인신청</a>
									</c:otherwise> 
								</c:choose> 
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<!-- BJ 승인방법 -->
				<table class="table_v">
					<colgroup>
						<col width="15%">
						<col width="85%">
					</colgroup>
					<tbody class="report">
						<tr class="only">
							<th>
								<label for="nicknm">BJ 승인방법</label>
							</th>
							<td>
								<p>
								1.개인정보 수정 메뉴에서 실명인증<Br>
								2.실명인증 완료 후 ‘BJ승인신청‘ 메뉴에서 BJ승인 신청<br>
								3.관리자 검토 후 BJ승인 또는 반려
								4.승인이 완료 되면 사이트에 다시 로그인 후 승인 상태를 확인해 주세요.
								</p>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<!-- 방송 설정 방법 -->
				<table class="table_v">
					<colgroup>
						<col width="15%">
						<col width="85%">
					</colgroup>
					<tbody class="report">
						<tr class="only">
							<th>
								<label for="nicknm">방송 설정 방법</label>
							</th>
							<td>
								<p>
								1.방송설정 > 방송설정 메뉴 이동<br>
								2.방송제목, 참여인원등 방송 정보 입력<br>
								3.방송설정안의 player에서 모니터링 및 채팅관리<br>
								</p>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<!-- OBS 설정 방법 -->
				<table class="table_v">
					<colgroup>
						<col width="15%">
						<col width="85%">
					</colgroup>
					<tbody class="report">
						<tr class="only">
							<th>
								<label for="nicknm">OBS 설정 방법</label>
							</th>
							<td>
								<p>
								1.OBS 실행<br>
								2.스트림키, 서버URL 입력<br>
								3.방송시작 / 방송 종료
								</p>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>		
	</div>
	
	
</form>
