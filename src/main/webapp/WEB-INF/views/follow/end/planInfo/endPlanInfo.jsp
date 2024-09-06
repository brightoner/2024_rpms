<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		getFileList($("#file_group").val(),'2');
		getFileList($("#file_group").val(),'3');
		getFileList($("#file_group").val(),'4');
		
		
	});
	
	
	
	// 첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.endFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	
	//첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	function fileContainerRefresh() {
	
		getFileList($("#file_group").val(), '2');
		getFileList($("#file_group").val(), '3');
		getFileList($("#file_group").val(), '4');
	}
	
	// 목록으로 이동
	function fn_back(){
		var form = document.listFrm;
		form.action = '${ctxt}/follow/end/planInfo/endFollowList.do';
		form.submit();	
	}
	
	
	
	
</script>
 <!-- 팝업 미리보기div 생성존  -->
    <div id = notiUpZone>
	 
	</div>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>종료과제 기본 정보  (<c:out value="${data.proj_end_gb}"></c:out>년차)</h3>  
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
	
	<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;종료과제 정보</h4>
	</div>
	
	 <form name="endFrm" method="post"  action=""  enctype="multipart/form-data">
		<input type="hidden" name="proj_id" id="proj_id" value="${data.proj_id}" />
		<input type="hidden" name="proj_end_id" id="proj_end_id" value="${data.proj_end_id}" />
		<input type="hidden" name="file_group" id="file_group" value="${data.file_group}">
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
					</td>
					<th>협약일</th>
					<td>
						 <c:out value="${data.agmt_dt}"></c:out> 
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
				<tr>
					<th>주관기관</th>
					<td colspan="3">
						 <input type="hidden" name="lead_org_cd" id="lead_org_cd" value="${data.lead_org_cd}"> 
						 <div style="width:25%; display: inline-block;">
							 <c:out value="${data.org_nm}"></c:out> 
						 </div>
					  	 <div style="width:10%; display: inline-block;">
						 	<c:out value="${data.org_crm_no}"></c:out>
						 </div>
						 <div style="width:45%; display: inline-block;">
						 	<c:out value="${data.org_address}"></c:out> 
						 </div>
					</td>
				</tr> 
				<tr>
					<th>총괄책임자</th>
					<td colspan="3">
						 <input type="hidden" name="lResp_cd" id="lResp_cd" value="${lRespMap.resp_cd}">
						 <div style="width:10%; display: inline-block;">
							 <c:out value="${lRespMap.resp_nm}"></c:out> 
						 </div>
					  	 <div style="width:10%; display: inline-block;">
						 	<c:out value="${lRespMap.resp_birth}"></c:out>
						 </div>
						 <div style="width:15%; display: inline-block;">
						 	<c:out value="${lRespMap.resp_dept}"></c:out> 
						 </div>
						 <div style="width:10%; display: inline-block;">
						 	<c:out value="${lRespMap.resp_position}"></c:out> 
						 </div>
						 <div style="width:15%; display: inline-block;">
						 	<c:out value="${lRespMap.resp_email}"></c:out> 
						 </div>
						 <div style="width:15%; display: inline-block;">
						 	<c:out value="${lRespMap.resp_mbtlnum}"></c:out> 
						 </div>
					</td>
				</tr>
				<c:choose>
			<c:when test="${respList == null || fn:length(respList) == 0}">
				<tr>
					<th>실무책임자</th>
					<td colspan="3">
						 <input type="hidden" name="resp_cd[]" id="resp_cd0" value="${respList.resp_cd}">
						  <div style="width:10%; display: inline-block;">
							 <c:out value="${respList.resp_nm}"></c:out> 
						 </div>
					  	 <div style="width:10%; display: inline-block;">
						 	<c:out value="${respList.resp_birth}"></c:out>
						 </div>
						 <div style="width:15%; display: inline-block;">
						 	<c:out value="${respList.resp_dept}"></c:out> 
						 </div>
						 <div style="width:10%; display: inline-block;">
						 	<c:out value="${respList.resp_position}"></c:out> 
						 </div>
						 <div style="width:15%; display: inline-block;">
						 	<c:out value="${respList.resp_email}"></c:out> 
						 </div>
						 <div style="width:15%; display: inline-block;">
						 	<c:out value="${respList.resp_mbtlnum}"></c:out> 
						 </div>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${respList}" var="respList"  begin="0" end="${respList.size()}" step="1" varStatus="stts">
					<tr> 
					 	<c:choose>
						 	<c:when test="${stts.index ==0}">
								<th>실무책임자</th>
						 	</c:when>
						 	<c:otherwise>
								<th></th> 
						 	</c:otherwise>
					  </c:choose> 
						<td colspan="3">
							 <input type="hidden" name="resp_cd[]" id="resp_cd${stts.index}" value="${respList.resp_cd}" >
							 <div style="width:10%; display: inline-block;">
							 <c:out value="${respList.resp_nm}"></c:out> 
							 </div>
						  	 <div style="width:10%; display: inline-block;">
							 	<c:out value="${respList.resp_birth}"></c:out>
							 </div>
							 <div style="width:15%; display: inline-block;">
							 	<c:out value="${respList.resp_dept}"></c:out> 
							 </div>
							 <div style="width:10%; display: inline-block;">
							 	<c:out value="${respList.resp_position}"></c:out> 
							 </div>
							 <div style="width:15%; display: inline-block;">
							 	<c:out value="${respList.resp_email}"></c:out> 
							 </div>
							 <div style="width:15%; display: inline-block;">
							 	<c:out value="${respList.resp_mbtlnum}"></c:out> 
							 </div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<tr>
					<th>총수행기간</th>
					<td>
						 <c:out value="${data.perform_strtdt}"></c:out> ~ <c:out value="${data.perform_enddt}"></c:out> 
					</td>
				</tr>
				<tr>
					<th rowspan="3">단계별 기간</th>
					<td colspan="3">
						<font style="margin-left: 20px;">1단계 : </font>
						<c:out value="${data.step1_strtdt}"></c:out> ~ <c:out value="${data.step1_enddt}"></c:out> 
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<font style="margin-left: 20px;">2단계 : </font>
						<c:out value="${data.step2_strtdt}"></c:out> ~ <c:out value="${data.step2_enddt}"></c:out> 
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<font style="margin-left: 20px;">3단계 : </font>
						<c:out value="${data.step3_strtdt}"></c:out> ~ <c:out value="${data.step3_strtdt}"></c:out> 
					</td>
				</tr>
				<tr>
					<th>총사업비(원)</th>
					<td colspan="3">
						<div style="width:20%; margin-right:50px; display: inline-block;">
							<fmt:formatNumber value="${data.tot_cost}" pattern="#,###" />
						</div>
						<div style="width:20%; margin-right:50px; display: inline-block;">
							<font>정부출연금(원)  : </font><fmt:formatNumber value="${data.gov_cost}" pattern="#,###" />
						</div>
						<div style="width:20%; margin-right:50px; display: inline-block;">
							<font>민간 부담금 현금(원)  : </font><fmt:formatNumber value="${data.cash}" pattern="#,###" />
						</div>
						<div style="width:20%; margin-right:50px; display: inline-block;">
							<font>민간 부담금 현물(원)  : </font><fmt:formatNumber value="${data.stock}" pattern="#,###" />
						</div>
					</td>
				</tr>
				<c:choose>
			<c:when test="${partList == null || fn:length(partList) == 0}">
				<tr>
					<th>참여기관</th>
					<td colspan="3">
						 <input type="hidden" name="part_org_cd[]" id="part_org_cd0" value="${partList.part_org_cd}">
						 <div style="width:25%; display: inline-block;">
						 	<c:out value="${partList.org_nm}"></c:out>
						 </div>
						 <div style="width:10%; display: inline-block;">
						 	<c:out value="${partList.org_crm_no}"></c:out>
						 </div>
						 <div style="width:45%; display: inline-block;">
						 	<c:out value="${partList.org_address}"></c:out>
						 </div>
					</td>	 
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${partList}" var="partList" begin="0" end="${partList.size()}" step="1" varStatus="status">
					<tr>
<!-- 						<th>참여기관</th> -->
						<c:choose>
							 <c:when test="${status.index ==0}">
								<th>참여기관</th> 
							 </c:when>
							 <c:otherwise> 
								<th></th> 
							 </c:otherwise>
						</c:choose> 
						<td colspan="3">
							 <input type="hidden" name="part_org_cd[]" id="part_org_cd${status.index}" value="${partList.part_org_cd}">
							 <div style="width:25%; display: inline-block;">
							 	<c:out value="${partList.org_nm}"></c:out>
							 </div>
							 <div style="width:10%; display: inline-block;">
							 	<c:out value="${partList.org_crm_no}"></c:out>
							 </div>
							 <div style="width:45%; display: inline-block;">
							 	<c:out value="${partList.org_address}"></c:out> 
							 </div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<tr>
					<th>협약처리</th>
					<td>
						<c:out value="${data.agmt_gb == '0' ? '미협약' : '협약완료'}"></c:out> 

					</td>
					<th>협약일자</th>
					<td>
						 <c:out value="${data.agmt_dt}"></c:out>
					</td>
				</tr>
				<tr>
					<th>특기사항</th>
					<td colspan="3">
						<textarea name="agmt_info" id="agmt_info" rows="10" style="width:100%;ime-mode:active;" readonly="readonly">${data.agmt_info}</textarea> 
					</td>
				</tr>
				<tr> 
					<th>연구과제신청 첨부파일</th> 
				  	<td colspan="3">
						 <div id="fileContainer2"></div>		
				    </td>
				</tr>
				<tr> 
					<th>선정결과 첨부파일</th> 
				  	<td colspan="3">
						 <div id="fileContainer3"></div>	
				    </td>
				</tr>
	 			<tr> 
					<th>협약처리 첨부파일<br><font size="1">(수정사업계획서 첨부파일)</font></th> 
				  	<td colspan="3">
						 <div id="fileContainer4"></div>	
				    </td>
				</tr>
			</tbody>
		</table>
	 
	 </form>
	  
	
	         
</div>

   
	<!-- //right_content -->

	