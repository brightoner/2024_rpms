<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 파일 그룹키
		
// 		GetDateTMS();
		
// 		getFileList($("#year_eval_file_group").val(), '');
		
		
	});
	
	// 파일 그룹키
	/* function GetDateTMS() {
	    var localTime = new Date();
		var year= localTime.getFullYear();
		var month= localTime.getMonth() +1;
	    var date = localTime.getDate();
	    var hours = localTime.getHours();
	    var minutes = localTime.getMinutes();
	    var seconds = localTime.getSeconds();
	    var milliseconds = localTime.getMilliseconds();
	    var totalTime = leadingZeros(year,4)+leadingZeros(month,2)+leadingZeros(date,2)+leadingZeros(hours,2)+leadingZeros(minutes,2)+leadingZeros(seconds,2)+leadingZeros(milliseconds,3);

	    // 신규저장일경우만 file_group키를 셋팅
	    var eval_file_group = '${data.year_eval_file_group}';
	    if(eval_file_group == null || eval_file_group == ''){
	    	$("#year_eval_file_group").val(totalTime);
	    }
	    
	} */

	/* function leadingZeros(n, digits) {
	    var zero = '';
		n = n.toString();

		if (n.length < digits) {
	      for (var i = 0; i < digits - n.length; i++)
			 zero += '0';
		}
		  return zero + n;
	} */
	
	
	// 첨부파일 다운로드
	/* function fn_fileDownload(val){
		var frm = document.evalFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	} */	

	//첨부파일등록 팝업 호출
	/* function fn_egov_file_popup() {
		var reqManageVo=document.evalFrm;
		var file_group = reqManageVo.year_eval_file_group.value;
		var file_gb = "";

		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');

  	} */
	
	//첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	/* function fileContainerRefresh() {
	
		getFileList($("#year_eval_file_group").val(), '');
	} */
	
	
	function fn_back(){
		var form = document.listFrm;
		form.action = '${ctxt}/follow/year/planInfo/yearFollowList.do';
		form.submit();	
	}
	
	
	
	
</script>
 <!-- 팝업 미리보기div 생성존  -->
    <div id = notiUpZone>
	 
	</div>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>연차과제 기본 정보</h3>  
	<form name="listFrm" method="post"  action="">	
		<input type="hidden" name="searchoption1" value="${followParam.searchoption1}" />
		<input type="hidden" name="searchoption2" value="${followParam.searchoption2}" />
		<input type="hidden" name="searchoption3" value="${followParam.searchoption3}" />
		<input type="hidden" name="searchword" value="${followParam.searchword}" />
		<input type="hidden" name="page" value="${followParam.page}" />
	</form>
	
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;공고 정보</h4>
			<div class="btn_wrap" >			
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>		
			</div>		
	</div>
	
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
					<th>공고번호</th>
					<td>
						<c:out value="${data.ann_no}"></c:out>
					</td>
					<th>공고일</th>
					<td>
						<c:out value="${data.ann_dt}"></c:out>
					</td>
				</tr>
				<tr>
					<th>공고명</th>
					<td colspan="3">
						<c:out value="${data.ann_nm}"></c:out>
					</td>
				</tr>
			</tbody>
		</table>
	 
	
	
	<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;연차과제 정보</h4>
	<form name="reqFrm" method="post"  action=""  enctype="multipart/form-data">
		
	 	<table class="table_v">  
			<colgroup>
				<col width="20%">
				<col width="30%">  
				<col width="20%">
				<col width="30%">
			</colgroup>
			<tbody>
				<tr>
					<th>과제(접수)번호</th>
					<td>
						 <c:out value="${data.proj_recpt_no}"></c:out>
					</td>
				</tr>
				<tr>
					<th>수행단계, 연차</th> 
					<td>
						<c:out value="${data.proj_step_gb}"></c:out><font style="margin-right: 20px;"> 단계</font>
						<c:out value="${data.proj_year_gb}"></c:out><font> 연차</font>
						 
					</td>
					
					<th>수행기간</th>
					<td>
						 <c:out value="${data.cur_strtdt}"></c:out>
						 ~ <c:out value="${data.cur_enddt}"></c:out>
					</td>
				</tr>
				<tr>
					<th>당해년도 사업비(원)</th>
					<td colspan="3">
						<c:out value="${data.tot_cost}"></c:out>
						<font style="margin-left: 50px;">정부 출연금(원) : </font><c:out value="${data.gov_cost}"></c:out>
						<font style="margin-left: 50px;">민간 부담금 현금(원) : </font><c:out value="${data.cash}"></c:out>
						<font style="margin-left: 50px;">민간 부담금 현물(원) : </font><c:out value="${data.stock}"></c:out> 
					</td>
				</tr>
				<tr>
					<th>과제유형</th>
					<td colspan="3">
						 <c:forEach var="type" items="${projTyList}">
					        <input type="radio" id="proj_type_cd" name="proj_type_cd" value="${type.cd}" <c:if test="${data.proj_type_cd eq type.cd}">checked</c:if> disabled="disabled">
					        <label for="${type.cd_nm}">${type.cd_nm}</label>
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th>보안등급</th>
					<td colspan="3">
						<c:forEach var="secu" items="${secuList}">
					        <input type="radio" id="securty_levl_cd" name="securty_levl_cd" value="${secu.cd}" <c:if test="${data.securty_levl_cd eq secu.cd}">checked</c:if> disabled="disabled">
					        <label for="${secu.cd_nm}">${secu.cd_nm}</label>
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th>R&#38;D여부</th>
					<td>
						<c:out value="${data.rnd_gb}"></c:out>
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
									<td style="text-align: center;">
										<c:out value="${data.tech_cls_nm1}"></c:out> 
									</td>
									<td style="text-align: center;">
										<c:out value="${data.weight1}"></c:out>
									</td>
									<td style="text-align: center;"> 
										<c:out value="${data.tech_cls_nm2}"></c:out> 
									</td>
									<td style="text-align: center;">
										<c:out value="${data.weight2}"></c:out>
									</td> 
									<td style="text-align: center;">
										<c:out value="${data.tech_cls_nm3}"></c:out>  
									</td>
									<td style="text-align: center;">
										<c:out value="${data.weight3}"></c:out>
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
									<c:out value="${data.proj_nm_kor}"></c:out>
								</td>
							</tr>
							<tr>
								<th>영문</th>
								<td>
									<c:out value="${data.proj_nm_eng}"></c:out> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			<c:forEach items="${resYearpList}" var="resYearpList"  begin="0" end="${resYearpList.size()}" step="1" varStatus="stts">
				<tr> 
				   <c:choose>
					 <c:when test="${stts.index ==0}">
						<th>참여연구원</th>
					 </c:when> 
					 <c:otherwise>
						<th></th> 
					 </c:otherwise>
				  </c:choose> 
					<td colspan="3">
						<div class="inputs-in-a-row">
							<font style="width:100px;"><c:out value="${resYearpList.resp_nm}"></c:out></font>
							<c:choose>
								<c:when test="${resYearpList.res_gb eq 'L'}">
									<font style="width:100px; margin-left: 50px;">연구책임자</font>
								</c:when>
								<c:when test="${resYearpList.res_gb eq 'R'}">
									<font style="width:100px; margin-left: 50px;">실무담당자</font>
								</c:when>
								<c:otherwise>
									<font style="width:100px; margin-left: 50px;">기타</font>
								</c:otherwise>
							</c:choose>
							<font style="width:100px; margin-left: 50px;"><c:out value="${resYearpList.year_part_rate}"></c:out> %</font>
							<font style="margin-left: 50px;">
								<c:out value="${resYearpList.year_part_strtdt}"></c:out> 
								~ <c:out value="${resYearpList.year_part_enddt}"></c:out>
							</font>
						</div>
					</td>
				</tr>
			</c:forEach>
				<tr>
					<th>특기사항</th>
					<td colspan="3">
						<textarea name="year_info" id="year_info" rows="10" style="width:100%;ime-mode:active;" disabled="disabled">${data.year_info}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	 
	 </form>
	  
	
	         
</div>

   
	<!-- //right_content -->

	