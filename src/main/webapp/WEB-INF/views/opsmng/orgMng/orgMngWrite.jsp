<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	
	<script type="text/javascript">
	$(function(){
		
		$("select[name=selEmailOpt]").change(function() {
			if ($(this).val() == "direct") {
				$("#org_charge_email2").attr("readonly", false);
				$("#org_charge_email2").css("background-color", "#FFFFFF");
				$("#org_charge_email2").val("");
				$("#org_charge_email2").focus();
			} else {
				$("#org_charge_email2").val($(this).val());
				$("#org_charge_email2").attr("readonly", true);
				$("#org_charge_email2").css("background-color", "#F1F1F1");
			}
		});
	});
	
	function fn_save(){
		if($("#org_nm").val().trim() == ""){
			fn_showCustomAlert("기관명을 입력해 주십시오");
			$("#org_nm").val("");
			$('#org_nm').focus();
			return false;
		}
	/* 	if($("#org_address").val().trim() == ""){
			fn_showCustomAlert("기관주소를 입력해 주십시오.");
			$("#org_nm").val("");
			$('#org_nm').focus();
			return false;
		} */
		
		//현재 페이지 세팅
		
		var form = document.orgMngFrm;
		form.action = '${ctxt}/opsmng/orgMng/insertOrgMng.do';
		form.submit();	
	}
	
	
	function fn_back(){
		var form = document.orgMngList;
		form.action = '${ctxt}/opsmng/orgMng/orgMngList.do';
		form.submit();	
	}
	
</script>

<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>기관관리 등록</h3>  
	<form name="orgMngList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${orgMng.searchopt}" />
		<input type="hidden" name="searchopt2" value="${orgMng.searchopt2}" />
		<input type="hidden" name="searchword" value="${orgMng.searchword}" />
		<input type="hidden" name="page" value="${orgMng.page}" />
	</form>
	<form name="orgMngFrm" method="post"  action="">			
		<input type="hidden" name="org_id" id = "org_id" value="${data.org_id}" />
		
		<!-- bj신청시 작성 내용 -->
		<table class="table_v">  
			<colgroup>
				<col width="20%">
				<col width="*">  
				<col width="20%">
				<col width="*">   
			</colgroup>
			<tbody> 
				<tr>
					<th><font color="red" class="last-child">*</font>기관구분</th>
					<td>
						<div class="custom-select selectRow">
						 	<select name="org_gb" id = "org_gb" class="select" title="조회조건 선택">
								<option value="1" >소관부처</option>
								<option value="2" >전담기관</option>
								<option value="3" >일반기관</option>
							</select>
						</div>
					</td>
					<th>기관분류</th>
					<td>
						<div class="custom-select selectRow">
						 	<select name="org_class" class="select" title="조회조건 선택">
									<c:forEach items="${org_class_code}" var="gbn">
										
											<option value="${gbn.cd}" >${gbn.cd_nm}</option>
									
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>기관명</th>
					<td>
						<input type="text" name="org_nm" id="org_nm" maxlength="100" value="" >
					</td>
					<th>사업자 번호</th>  
					<td>				
						<input type="text" name="org_crm_no" id="org_crm_no" maxlength="40" value="" >
					</td>
				</tr>	
				<tr>
					<th>기관 주소</th>  
					<td colspan="3">				
						<input type="text" name="org_address" id="org_address" maxlength="100" value="" >
					</td>     
				</tr>     			
				<tr>
					<th>실무자 명</th>  
					<td colspan="3">				
						<input type="text" name="org_charge_nm" id="org_charge_nm" maxlength="20" value="" >
					</td>     
				</tr>
				<tr>
					<th>실무자 전화번호</th>  
					<td >				
						<input type="text" name="org_charge_phone" placeholder="'-'을 제외하고 숫자만 입력" id="org_charge_phone" maxlength="12"  value="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" >
					</td>  
					<th>실무자 휴대전화</th>  
					<td >				
						<input type="text" name="org_charge_mbtlnum"  placeholder="'-'을 제외하고 숫자만 입력" id="org_charge_mbtlnum" maxlength="12" value="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
					</td>    
				</tr>  
				<tr>
					<th>실무자 이메일</th>
					<td>									
						<input id="org_charge_email1" name="org_charge_email1" title="이메일아이디" placeholder="이메일 ID" type="text"  maxlength="30" autocomplete="off"  value = "" />
					</td>
						
					<td class="grid-input-0">
							<label for="org_charge_email2" class="blind">@</label>
							<input id="org_charge_email2" name="org_charge_email2" title="이메일주소" type="text"   maxlength="30" autocomplete="off" class="ma_l_3 txt"   value = "" />
					
					</td>
					<td>		
							<div class="selectRow in_block">
								<select id="org_charge_email3" name="selEmailOpt" title="주 사용 전자우편(E-mail)">
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


    
   
	<!-- //right_content -->

	