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
		} */
	
		//현재 페이지 세팅
		
		var form = document.respMngFrm;
		form.action = '${ctxt}/opsmng/respMng/insertRespMng.do';
		form.submit();	
	}
	
	
	function fn_back(){
		var form = document.respMngList;
		form.action = '${ctxt}/opsmng/respMng/respMngList.do';
		form.submit();	
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
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>연구자 관리 등록</h3>  
	<form name="respMngList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${respMng.searchopt}" />
		<input type="hidden" name="searchword" value="${respMng.searchword}" />
		<input type="hidden" name="page" value="${respMng.page}" />
	</form>
	<form name="respMngFrm" method="post"  action="">			
		<input type="hidden" name="resp_id" id = "resp_id" value="${data.resp_id}" />
		
		<!-- bj신청시 작성 내용 -->
		<table class="table_v">  
			<colgroup>
				<col width="25%">
				<col width="25%">  
				<col width="25%">
				<col width="25%">  
			</colgroup>
			<tbody> 
				<tr>
					<th><font color="red" class="last-child">*</font>연구자명</th>
					<td>
					 	<input type="text" name="resp_nm" id="resp_nm" maxlength="10" >
					</td>
					<th>생년월일</th>
					<td>					 
					 	<input type="date" name="resp_birth" id="resp_birth"  max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th>입사일</th>
					<td>					 
			 		 	<input type="date" name="resp_join_dt" id="resp_join_dt"    max="9999-12-31" min="1111-01-01">
					</td>
					<th>퇴사일</th>
					<td>					 
			 		 	<input type="date" name="resp_quit_dt" id="resp_quit_dt"   max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="resp_sex" id="resp_sex1"   value = "1"  checked> <label for="resp_sex1">남자</label>
						<input type="radio" name="resp_sex" id="resp_sex2"   value = "2"  ><label for="resp_sex2">여자</label>
					</td>	
					<th>휴대전화</th>
					<td>
					 	<input type="text" name="resp_mbtlnum" id="resp_mbtlnum" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"  >
					</td>
					
				</tr>
				<tr>
					<th>전화</th>
					<td>					 
		 		 		<input type="text" name="resp_phone" id="resp_phone" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" >
					</td>
					<th>FAX</th>
					<td>
					 	<input type="text" name="resp_fax" id="resp_fax" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"  >
					</td>
					
				</tr>	
				<tr>
					<th>이메일</th>
					<td>
									
							<input id="email1" name="email1" title="이메일아이디" placeholder="이메일 ID" type="text"  value="" maxlength="30" autocomplete="off" />
					</td>
					<td class="grid-input-0">
							<label for="email2" class="blind">@</label>
							<input id="email2" name="email2" title="이메일주소" type="text"  value="" maxlength="30" autocomplete="off" class="ma_l_3 txt" />
							
					
					</td>
					<td>		
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
				<tr>
					<th>소속구분</th>
					<td>
						<div class="custom-select selectRow">
							 <select name="resp_affil" class="select" title="조회조건 선택">
								<c:forEach items="${affil_code}" var="gbn">
										
											<option value="${gbn.cd}" >${gbn.cd_nm}</option>
									
								</c:forEach>
							</select>
						</div>
					</td>	
					<th>소속기관</th>
					<td>
						<input type="text" name="resp_org" id="resp_org" maxlength="50" >
					</td>					
				</tr>
				<tr>
					<th>소속부서</th>
					<td>
						<input type="text" name="resp_dept" id="resp_dept" maxlength="100" >
					</td>
					<th>직책</th>
					<td>
						<input type="text" name="resp_position" id="resp_position" maxlength="20" >
					</td>					
				</tr>  
					<tr>
					<th>학위구분</th>
					<td>
						<div class="custom-select selectRow">
					 	<select name="resp_degree" class="select" title="조회조건 선택">
							<c:forEach items="${degree_code}" var="gbn">
									
										<option value="${gbn.cd}" >${gbn.cd_nm}</option>
								
							</c:forEach>
						</select>
						</div>
					</td> 
					<th>과학기술등록번호</th>
					<td>					 
		 		 		<input type="text" name="resp_srch_no" id="resp_srch_no"  value = "" >
					</td>		
				</tr> 
				<tr>
					<th>전문가 여부</th>
					<td>
					
					  <input type="radio" id ="resp_expert_gbn1" name="resp_expert_gbn" value="Y"  ><label for="resp_expert_gbn1">예</label> 
						<input type="radio" id="resp_expert_gbn2" name="resp_expert_gbn" value ="N" checked><label for="resp_expert_gbn2">아니오</label>
					</td>
					<th>전공 분야</th>
					<td>
						
						<div class="custom-select selectRow">
					 	<select name="resp_major" class="select" title="조회조건 선택">
					 		<option value="" >없음</option>
							<c:forEach items="${major_code}" var="gbn">									
								<option value="${gbn.cd}" >${gbn.cd_nm}</option>								
							</c:forEach>				
						</select>
						</div>
					</td>					
				</tr>   
				<tr>
				<th>전공명</th>
						<td >
						<input type="text" name="resp_major_nm" id="resp_major_nm" maxlength="20" value = "">
					</td>	
					<th>최종출신학교</th>
					<td>
						<input type="text" name="resp_school" id="resp_school" maxlength="40" value = "">
					</td>									
				</tr> 
				<tr>
					<th>전문분야</th>
					<td >
						<input type="hidden" name="resp_special1" id="resp_special1" maxlength="20" value = "">
						<input type="text" name="resp_special1_nm" id="resp_special1_nm" maxlength="20" style="width: 70%" value = "" readonly="readonly">
						<a href="javascript:void(0);" onclick="javascript:fn_techSearch(1);" class="btn btn-secondary">  <i class="fas fa-search"></i></a>
					</td>
  
					
					<td >
						<input type="hidden" name="resp_special2" id="resp_special2" maxlength="20"  value = "">
						<input type="text" name="resp_special2_nm" id="resp_special2_nm" maxlength="20"  style="width: 70%"value = "" readonly="readonly">
						<a href="javascript:void(0);" onclick="javascript:fn_techSearch(2);" class="btn btn-secondary"> <i class="fas fa-search"></i></a>
					</td>					
					<td >
						<input type="hidden" name="resp_special3" id="resp_special3" maxlength="20"  value = "">
						<input type="text" name="resp_special3_nm" id="resp_special3_nm" maxlength="20"  style="width: 70%"value = "" readonly="readonly"">
						<a href="javascript:void(0);" onclick="javascript:fn_techSearch(3);" class="btn btn-secondary"> <i class="fas fa-search"></i></a>
					</td>					
				</tr>  
				
				<tr>
						<th>비고</th>
				<td colspan="3">
						<textarea name="resp_etc" id="resp_etc" rows="5"  style="width: 100%;" ></textarea>
					</td>
			  				
				</tr> 
			</tbody>
		</table>
		
	
	 </form>       
	 <div class="flex_box">
		
	  	<div align="right">
	  		
	  		<a href="javascript:void(0);" onclick="javascript:fn_save();" class="btn btn-secondary">저장</a>
	  		
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

	