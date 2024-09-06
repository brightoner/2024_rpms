<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<script type="text/javascript">
	
	$(function(){   
		
		// 파일 그룹키
		GetDateTMS();
		
		// 총사업비 합
		sumTotal();
        document.getElementById("gov_cost").addEventListener("input", sumTotal);
        document.getElementById("cash").addEventListener("input", sumTotal);
        document.getElementById("stock").addEventListener("input", sumTotal);
        
	});
	
	// 파일 그룹키
	function GetDateTMS() {
	    var localTime = new Date();
		var year= localTime.getFullYear();
		var month= localTime.getMonth() +1;
	    var date = localTime.getDate();
	    var hours = localTime.getHours();
	    var minutes = localTime.getMinutes();
	    var seconds = localTime.getSeconds();
	    var milliseconds = localTime.getMilliseconds();
	    var totalTime = leadingZeros(year,4)+leadingZeros(month,2)+leadingZeros(date,2)+leadingZeros(hours,2)+leadingZeros(minutes,2)+leadingZeros(seconds,2)+leadingZeros(milliseconds,3);

	    $("#year_file_group").val(totalTime);
	    
	}

	function leadingZeros(n, digits) {
	    var zero = '';
		n = n.toString();

		if (n.length < digits) {
	      for (var i = 0; i < digits - n.length; i++)
			 zero += '0';
		}
		  return zero + n;
	}
	  
	  
	// input박스 천단위 콤마
	function inputComma(event) {
        var value = event.target.value.replace(/[^0-9.]/g, '');
        
        var parts = value.split('.');
        var integerPart = parts[0];
        var decimalPart = parts.length > 1 ? '.' + parts[1] : '';

        const formattedIntegerPart = addComma(Number(integerPart));
        event.target.value = formattedIntegerPart + decimalPart;
    }
	
	// 총 사업비 계산
	function sumTotal() {
        var govCost = parseFloat(document.getElementById("gov_cost").value.replace(/,/g, '')) || 0;
        var cash = parseFloat(document.getElementById("cash").value.replace(/,/g, '')) || 0;
        var stock = parseFloat(document.getElementById("stock").value.replace(/,/g, '')) || 0;

        var total = govCost + cash + stock;

        document.getElementById("tot_cost").value = addComma(total);
    }

	
	// 연차과제 저장
	function fn_reg(){
		
		// 단계구분 validateion
		if($('[name=proj_step_gb] :selected').val() == "" ||$('[name=proj_step_gb] :selected').val() == null){
			fn_showCustomAlert("단계구분을 선택 하세요.");
			$("#proj_step_gb").val("");
			$('#proj_step_gb').focus();
			return false;
		}
		
		// 연차구분 validateion
		if($('[name=proj_year_gb] :selected').val() == "" ||$('[name=proj_year_gb] :selected').val() == null){
			fn_showCustomAlert("연차구분을 선택 하세요.");
			$("#proj_year_gb").val("");
			$('#proj_year_gb').focus();
			return false;
		}
		
		// 수행기간 validation
		var curStrtdt = $("#cur_strtdt").val().replace(/-/g, "");  
		var curEnddt = $("#cur_enddt").val().replace(/-/g, "");  
		
		if($("#cur_strtdt").val() == "" || $("#cur_strtdt").val() == null){
			fn_showCustomAlert("수행 시작 기간을 입력 하세요.");
			$("#cur_strtdt").val("");
			$('#cur_strtdt').focus();
			return false;
		}
		
		if($("#cur_enddt").val() == "" || $("#cur_enddt").val() == null){
			fn_showCustomAlert("수행 종료 기간을 입력 하세요.");
			$("#cur_enddt").val("");
			$('#cur_enddt').focus();
			return false;
		}
		
		if(curStrtdt > curEnddt){
			fn_showCustomAlert("수행 시작 기간이 수행 종료 기간보다 큽니다.");
			$("#cur_strtdt").val("");
			$("#cur_enddt").val("");
			$('#cur_strtdt').focus();
			return false;
		} 

		// 정부출연금 validation
		if($("#gov_cost").val() == "" || $("#gov_cost").val() == null){
			fn_showCustomAlert("정부출연금 입력 하세요.");
			$("#gov_cost").val("");
			$('#gov_cost').focus();
			return false;
		}
		
		// 민간 부담금 현금 validation
		if($("#cash").val() == "" || $("#cash").val() == null){
			fn_showCustomAlert("민간 부담금 현금을 입력 하세요.");
			$("#cash").val("");
			$('#cash').focus();
			return false;
		}
		
		// 민간 부담금 현물 validation
		if($("#stock").val() == "" || $("#stock").val() == null){
			fn_showCustomAlert("민간 부담금 현물을 입력 하세요.");
			$("#stock").val("");
			$('#stock').focus();
			return false;
		}
		
		fn_showCustomConfirm("question","연차과제를 생성 하시겠습니까?", function() {
			
			var params = {};
			params.proj_id    =  $("#proj_id").val();
			params.proj_year_gb    =  $("#proj_year_gb").val();
			params.proj_step_gb    =  $("#proj_step_gb").val();
			params.cur_strtdt    =  $("#cur_strtdt").val();
			params.cur_enddt    =  $("#cur_enddt").val();
			
			$.ajax({
		        url: '${ctxt}/execute/reg/chkYear.do',
		        data: params,
		        type: 'POST', 
		        dataType: 'json',	 
				success: function(result){			
					
					if(result.result != 0){
						fn_showCustomAlert("연차과제가 이미 존재합니다. 수행단계와 연차를 다시 확인해 주세요.");
						return false;
					}else{
						var form = document.regFrm;
						form.action = '${ctxt}/execute/reg/insertProjPlan.do';
						form.submit();
					}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
		  	  });
			
		});
		
	}
	
	// 목록으로 이동
	function fn_back(){
		var form = document.regFrm;
		form.action = '${ctxt}/execute/reg/projRegList.do';
		form.submit();	
	}
	
	
	
</script>
<!-- 본문내용 -->
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>협약과제 상세</h3>  
	<form name="updBusList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${noti.searchopt}" />
		<input type="hidden" name="searchword" value="${noti.searchword}" />
		<input type="hidden" name="page" value="${noti.page}" />
	</form>
	
	 <form name="regFrm" method="post"  action=""  enctype="multipart/form-data">
		<input type="hidden" name="proj_id" id="proj_id" value="${data.proj_id}" />
		<input type="hidden" name="proj_year_id" id="proj_year_id" value="${data.proj_year_id}" />
		<input type="hidden" name="year_file_group" id="year_file_group" value="">
		<input type="hidden" name="part_org_cds" id="part_org_cds" value="">
		<input type="hidden" name="resp_cds" id="resp_cds" value="">
		
	 	<table class="table_v">  
			<colgroup>
				<col width="20%">
				<col width="30%">  
				<col width="20%">
				<col width="30%">
			</colgroup>
			<tbody>
				<tr>
					<th>소관부처</th> 
					<td>  
						 <c:out value="${data.dept_org}"></c:out>  
					</td>
					<th>전담기관</th>
					<td>
						 <c:out value="${data.ddct_org}"></c:out> 
					</td>
				</tr>
				<tr>
					<th>과제(접수)번호</th>
					<td>
						 <c:out value="${data.proj_recpt_no}"></c:out>
						 <input type="hidden" name="proj_recpt_no" id="proj_recpt_no" value="${data.proj_recpt_no}">
					</td>
				</tr>
				<tr>
					<th><font color="red">*</font>수행단계, 연차</th> 
					<td>
						<div class="form-row">
							<div class="custom-select selectRow" style= "width: 100px;">
								<select id="proj_step_gb" name="proj_step_gb" class="select">
									<option value="">단계선택</option> 
									<option value="1">1 단계</option> 
									<option value="2">2 단계</option> 
									<option value="3">3 단계</option> 
								</select>
							</div>
							<div class="custom-select selectRow" style= "width: 100px;">
								<select id="proj_year_gb" name="proj_year_gb" class="select">
									<option value="">연차선택</option> 
									<option value="1">1 연차</option> 
									<option value="2">2 연차</option> 
									<option value="3">3 연차</option> 
									<option value="4">4 연차</option> 
									<option value="5">5 연차</option> 
									<option value="6">6 연차</option> 
									<option value="7">7 연차</option> 
									<option value="8">8 연차</option> 
									<option value="9">9 연차</option> 
								</select>
							</div>
						</div>
					</td>
					<th>수행 기간</th>
					<td>
						 <input type="date" name="cur_strtdt" id="cur_strtdt" value="" max="9999-12-31" min="1111-01-01"> ~
						 <input type="date" name="cur_enddt" id="cur_enddt" value ="" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>당해년도 사업비(원)</th>
					<td colspan="3">
						<input type="text" name="tot_cost" id="tot_cost" value="" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, '');" style="width:15%;text-align:right;background-color:#e9ecef;ime-mode:active" readonly="readonly">
						<font style="margin-left: 20px;">정부 출연금(원) : </font><input type="text" name="gov_cost" id="gov_cost" value="" maxlength="18" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">민간 부담금 현금(원) : </font><input type="text" name="cash" id="cash" value="" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">민간 부담금 현물(원) : </font><input type="text" name="stock" id="stock" value="" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
					</td>
				</tr>
				<tr>
					<th>과제유형</th>
					<td colspan="3">
						 <c:forEach var="type" items="${projTyList}">
					        <input type="radio" id="proj_type_cd" name="proj_type_cd" value="${type.cd}" <c:if test="${data.proj_type_cd eq type.cd}">checked</c:if>>
					        <label for="${type.cd_nm}">${type.cd_nm}</label>
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th>보안등급</th>
					<td colspan="3">
						<c:forEach var="secu" items="${secuList}">
					        <input type="radio" id="securty_levl_cd" name="securty_levl_cd" value="${secu.cd}" <c:if test="${data.securty_levl_cd eq secu.cd}">checked</c:if>>
					        <label for="${secu.cd_nm}">${secu.cd_nm}</label>
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th>R&#38;D여부</th>
					<td>
						<div class="custom-select selectRow">
							<select id="rnd_gb" name="rnd_gb" class="select">
						        <c:forEach var="rnd" items="${rndList}">
						        	<option value="${rnd.cd_nm}">${rnd.cd_nm}</option>
						        </c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>산업기술분류</th>
					<td colspan="3">
						<table style="border:0px solid rgb(235 221 216); margin:0px;">
							<colgroup>
								<col width="25%">
								<col width="8%">  
								<col width="25%">
								<col width="8%">   
								<col width="25%">  
								<col width="8%"> 
							</colgroup>
							<tbody>
								<tr>
									<th>소분류(1순위)</th>
									<th>가중치(%)</th>
									<th>소분류(2순위)</th>
									<th>가중치(%)</th>
									<th>소분류(3순위)</th>
									<th>가중치(%)</th>
								</tr>
								<tr> 
									<td>
										<input type="hidden" name="tech_cls_cd1" id="tech_cls_cd1" value="${data.tech_cls_cd1}">
										<input type="text" name="tech_cls_nm1" id="tech_cls_nm1" value="${data.tech_cls_nm1}" style="width:75%;background-color:#e9ecef; ime-mode:active" readonly="readonly"> 
										<a href="javascript:void(0);" onclick="javascript:fn_techSearch('1');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
									</td>
									<td>
										<input type="text" name="weight1" id="weight1" value="${data.weight1}" maxlength="3" oninput="this.value = this.value.replace(/[^\d.]/g, ''); if (parseInt(this.value) > 100) this.value = '';" style="width:100%;text-align:right; ime-mode:active">
									</td>
									<td>
										<input type="hidden" name="tech_cls_cd2" id="tech_cls_cd2" value="${data.tech_cls_cd2}">
										<input type="text" name="tech_cls_nm2" id="tech_cls_nm2" value="${data.tech_cls_nm2}" style="width:75%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
										<a href="javascript:void(0);" onclick="javascript:fn_techSearch('2');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
									</td>
									<td>
										<input type="text" name="weight2" id="weight2" value="${data.weight2}" maxlength="3" oninput="this.value = this.value.replace(/[^\d.]/g, ''); if (parseInt(this.value) > 100) this.value = '';" style="width:100%;text-align:right;ime-mode:active">
									</td> 
									<td>
										<input type="hidden" name="tech_cls_cd3" id="tech_cls_cd3" value="${data.tech_cls_cd3}">
										<input type="text" name="tech_cls_nm3" id="tech_cls_nm3" value="${data.tech_cls_nm3}" style="width:75%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
										<a href="javascript:void(0);" onclick="javascript:fn_techSearch('3');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
									</td>
									<td>
										<input type="text" name="weight3" id="weight3" value="${data.weight3}" maxlength="3" oninput="this.value = this.value.replace(/[^\d.]/g, ''); if (parseInt(this.value) > 100) this.value = '';" style="width:100%;text-align:right;ime-mode:active">
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<th>과제명</th>
					<td colspan="3">
						<table style="border:0px solid rgb(235 221 216); margin:0px;"> 
							<tr>
								<th>국문</th>
								<td>
									<input type="text" name="proj_nm_kor" id="proj_nm_kor" value="${data.proj_nm_kor}" style="width:100%;ime-mode:active">
								</td>
							</tr>
							<tr>
								<th>영문</th>
								<td>
									<input type="text" name="proj_nm_eng" id="proj_nm_eng" value="${data.proj_nm_eng}" style="width:100%;ime-mode:active">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	 
	 </form>
	  
	 <div class="flex_box">
		
	  	<div align="right">
	  			<a href="javascript:void(0);" onclick="javascript:fn_reg();" class="btn btn-secondary">저장</a>
	  			<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
		</div>
	</div>
	         
</div>


