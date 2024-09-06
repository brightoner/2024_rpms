<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	
	<script type="text/javascript">
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
	});
	
	
	function fn_save(){
		if ($("#resp_nm").val() == "") {
			fn_showCustomAlert("연구자 명을 입력해 주십시오.");
			$("#resp_nm").focus();
			return false;
		}	
		/* 
		if ($("#resp_birth").val() == "") {
			fn_showCustomAlert("생년월일을 입력해 주십시오.");
			$("#resp_birth").focus();
			return false;
		}	
		
		if ($("#resp_org").val() == "") {
			fn_showCustomAlert("소속기관을 입력해 주십시오.");
			$("#resp_org").focus();
			return false;
		}
		if ($("#resp_dept").val() == "") {
			fn_showCustomAlert("소속부서를 입력해 주십시오.");
			$("#resp_dept").focus();
			return false;
		}	
		if ($("#email1").val() == "") {
			fn_showCustomAlert("이메일 주소를 입력해 주십시오.");
			$("#email1").focus();
			return false;
		}	
		if ($("#email2").val() == "") {
			fn_showCustomAlert("이메일 주소를 입력해 주십시오.");
			$("#email1").focus();
			return false;
		}	

		//이메일 정규식
		var regEmail1 = /([a-zA-Z0-9._%+-])$/; 
		var regEmail2 = /([a-zA-Z0-9.-]+\.[a-zA-Z]{2,})$/; 
		
		if(!regEmail1.test($('#email1').val())){
			fn_showCustomAlert("주소부분을 정규식에 맞게 작성해 주세요.");
			$("#email1").focus();
			return false;
		}
		if(!regEmail2.test($('#email2').val())){
			fn_showCustomAlert("도메인을 정규식에 맞게 작성해 주세요.");
			$("#email2").focus();
			return false;
		}
	 */
		
		//현재 페이지 세팅
		
		
		var sendData = $("form[name='respMngFrm']").serialize();
		
	    $.ajax({
	        url: '${ctxt}/opsmng/respExpert/updateRespMng.do',
	        data: sendData,
	        type: 'POST', 
	        dataType: 'json',	 
	       
					success: function(result){			
						
						if(result.result != 0){
							fn_showCustomAlert("수정이 완료되었습니다.");
					
						}
					},
					error: function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
	  	  });
	}
	
	
	function fn_back(){
		var form = document.respMngList;
		form.action = '${ctxt}/opsmng/respExpert/respExpertList.do';
		form.submit();	
	}
	
	function fn_delete(){
		if(confirm("삭제하시겠습니까?") ==true ){
		var form = document.respMngFrm;
		form.action = '${ctxt}/opsmng/respExpert/deleteRespMng.do';
		form.submit();	
		}
	}
	

	// 산업기술분류 선택 팝업
function fn_techSearch(seq){
	
	$("#id_gbn1").val("resp_special"+seq);
	$("#id_gbn2").val("resp_special"+seq+"_nm");

	var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/tclsMng/tclsMngPopList.do";	//팝업창에 출력될 페이지 URL
	var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
	popOpen = window.open(popUrl, 'selectPopup', popOption);
	$('#callPopForm').attr("action",popUrl);
	$('#callPopForm').attr("target","selectPopup");
	$('#callPopForm').attr("method","post");
	$('#callPopForm').submit();
}

</script>

<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>외부 연구자 DB 상세</h3>  
	<form name="respMngList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${respExpert.searchopt}" />
	
		<input type="hidden" name="searchword" value="${respExpert.searchword}" />
		<input type="hidden" name="page" value="${respExpert.page}" />
	</form>
	<form name="respMngFrm" method="post"  action="">			
		<input type="hidden" name="resp_id" id = "resp_id" value="${data.resp_id}" />
		
		<!-- bj신청시 작성 내용 -->
		<table class="table_v">  
			<colgroup>
				<col width="20%">
				<col width="*">  
			</colgroup>
			<tbody>
				<tr>
					<th><font color="red" class="last-child">*</font>연구자명</th>
					<td>
					 	${data.resp_nm}
					</td>
					<th>생년월일</th>
					<td>					 
			 		 	<input type="date" name="resp_birth" id="resp_birth"   value = "${data.resp_birth}" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="resp_sex" id="resp_sex1"   value = "1"  <c:if test="${data.resp_sex eq '1'}">checked</c:if>> <label for="resp_sex1">남자</label>
						<input type="radio" name="resp_sex" id="resp_sex2"   value = "2" <c:if test="${data.resp_sex eq '2'}">checked</c:if>><label for="resp_sex2">여자</label>
					</td>	
					<th>휴대전화</th>
					<td>
						 ${data.resp_mbtlnum}
					</td>
					
				</tr>
				<tr>
					<th>전화</th>
					<td>					 
		 		 		${data.resp_phone}
					</td>
					<th>FAX</th>
					<td>
					 	${data.resp_fax}
					</td>
					
				</tr>	
				<tr>
					<th>이메일</th>
					<td colspan="3">
						 	${data.resp_email}
					</td>
					
				<tr>			
				<tr>
					<th>소속구분</th>
					<td>
						<div class="custom-select selectRow">
							 <select name="resp_affil" class="select" title="조회조건 선택">
								<c:forEach items="${affil_code}" var="gbn">
										
											<option value="${gbn.cd}" <c:if test="${data.resp_affil eq gbn.cd}">selected</c:if>>${gbn.cd_nm}</option>
									
								</c:forEach>
							</select>
						</div>
					</td>	
					<th>소속기관</th>
					<td>
				
						${data.resp_org}
					</td>					
				</tr>
				<tr>
					<th>소속부서</th>
					<td>
						${data.resp_dept}
					</td>
					<th>직책</th>
					<td>
						${data.resp_position}
					</td>					
				</tr>   
				<tr>
					<th>학위구분</th>
					<td>
						<div class="custom-select selectRow">
					 	<select name="resp_degree" class="select" title="조회조건 선택">
							<c:forEach items="${degree_code}" var="gbn">
									
										<option value="${gbn.cd}" <c:if test="${data.resp_degree eq gbn.cd}">selected</c:if>>${gbn.cd_nm}</option>
								
							</c:forEach>
						</select>
						</div>
					</td> 
					<th>국가연구자번호</th>
					<td>					 
		 		 		${data.resp_srch_no}
					</td>		
				</tr> 
				<tr>
					<th>외부 전문가 여부</th>
					<td>
					
					  <input type="radio" id ="resp_expert_gbn1" name="resp_expert_gbn" value="Y" <c:if test="${data.resp_expert_gbn eq 'Y'}">checked</c:if> ><label for="resp_expert_gbn1">예</label> 
						<input type="radio" id="resp_expert_gbn2" name="resp_expert_gbn" value ="N" <c:if test="${data.resp_expert_gbn eq 'N'}">checked</c:if> ><label for="resp_expert_gbn2">아니오</label>
					</td>
					<th>전공 분야</th>
					<td>
						
						<div class="custom-select selectRow">
					 	<select name="resp_major" class="select" title="조회조건 선택">
					 			<option value="" <c:if test="${data.resp_major eq ''}">selected</c:if>>없음</option>
							<c:forEach items="${major_code}" var="gbn">								
									<option value="${gbn.cd}" <c:if test="${data.resp_major eq gbn.cd}">selected</c:if>>${gbn.cd_nm}</option>								
							</c:forEach>
						</select>
						</div>
					</td>					
				</tr>   
				<tr>
					<th>전공명</th>
					<td>
						${data.resp_major_nm}
					</td>
					<th>최종출신학교</th>
					<td >
						${data.resp_school}
					</td>									
				</tr> 
				<tr>
					<th>전문분야</th>
					<td>
						${data.resp_special1_nm}
					</td>
					<td>
						${data.resp_special2_nm}
					</td>					
					<td>
						${data.resp_special3_nm}
					</td>					
				</tr> 
				<tr>
					<th>비고</th>
					<td colspan="3">
						${data.resp_etc}
					</td>			  				
				</tr> 
			</tbody>
		</table>
		
	
	 </form>       
	 <div class="flex_box">
		
	 	<div align="right">
	  		
	  		
	  		<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
	  		
		</div> 
	</div>
	         
</div>


    
<!-- 주기관 선택 관련 str -->
<form name ="callPopForm" id="callPopForm" method="post" action="">
	<input type="hidden" id="id_gbn1" name="id_gbn1"/>
	<input type="hidden" id="id_gbn2" name="id_gbn2"/>
	<input type="hidden" id="id_gbn3" name="id_gbn3"/>
	<input type="hidden" id="id_gbn4" name="id_gbn4"/>
	<input type="hidden" id="id_gbn5" name="id_gbn5"/>
	<input type="hidden" id="id_gbn6" name="id_gbn6"/>
	<input type="hidden" id="id_gbn7" name="id_gbn7"/>
</form>

   
	<!-- //right_content -->

	