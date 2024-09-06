<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<script type="text/javascript">
	var cuurProjPlanPage;
	var pageProjPlantotalCnt = 0;
	
	$(function(){   
		
		
		getFileList($("#file_group").val(),'2');
		getFileList($("#file_group").val(),'3');
		getFileList($("#file_group").val(),'4');
		
		fn_projPlanListSearch(1);
	});
	  
	// 첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.regFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}	
	
	
	//첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	function fileContainerRefresh() {
	
		getFileList($("#file_group").val(), '2');
		getFileList($("#file_group").val(), '3');
		getFileList($("#file_group").val(), '4');
	}


	// 연차과제 저장
	function fn_reg(){
		var form = document.regFrm;
		form.action = '${ctxt}/execute/reg/projPlanWrite.do';
		form.submit();	
	}
	
	// 목록으로 이동
	function fn_back(){
		var form = document.regFrm;
		form.action = '${ctxt}/execute/reg/projRegList.do';
		form.submit();	
	}
	
	
	function fn_projPlanListSearch(page){
		
		$("#projDataList").children().remove();
		//현재 페이지 세팅
		cuurProjPlanPage= page;
	
		var params = {};
			params.page  = cuurProjPlanPage;   
			params.proj_id = $("#proj_id").val();
			
	  	$('#projPlanpaging').val(cuurProjPlanPage);
	  	  	
	    $.ajax({
	        url: '${ctxt}/execute/plan/readProjPlanList.do',
	        data: params,
	        type: 'POST',
	        dataType: 'json',
	        success: function(result) {
	        	var pHtml ='';
	        	pageProjPlantotalCnt = 0;
	        	if(!isEmpty(result)){
					if(!isEmpty(result.planList)){
						var start_num = Number(result.planTotal) - ((cuurProjPlanPage -1) *10)
						pageProjPlantotalCnt =Number(result.planPageTotal);	
						
						$.each(result.planList, function(idx, item){
							pHtml += '<tr >';
								pHtml += '<td>' + (start_num - idx) + '</td>';
								pHtml += '<td>'+((isEmpty(item.dept_org)) ? '' : item.dept_org) +'</td>';
								pHtml += '<td>'+((isEmpty(item.ddct_org)) ? '' : item.ddct_org) +'</td>';
							//	pHtml += '<td class="text_l"><a href="javascript:fn_planDtl(\''+item.proj_year_id+'\');">'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</a></td>';
								pHtml += '<td class="text_l">'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</td>';
								pHtml += '<td>'+((isEmpty(item.proj_step_gb)) ? '' : item.proj_step_gb + '단계') +'</td>';
								pHtml += '<td>'+((isEmpty(item.proj_year_gb)) ? '' : item.proj_year_gb + '년차') +'</td>';
								pHtml += '<td>'+((isEmpty(item.cur_strtdt)) ? '' : item.cur_strtdt + ' ~ ' + item.cur_enddt) +'</td>';	
							pHtml += '</tr>';						
						});
						
						//트리코드 선택시 첫번째 행 선택 
						//페이징처리
					    $('#projPlanpaging').paging({
					    	
							 current:cuurProjPlanPage
							,max:pageProjPlantotalCnt
							,length:pageLen
							,onclick:function(e,page){
								cuurProjPlanPage=page;
								fn_projPlanListSearch(cuurProjPlanPage ,val);
							}
						});
						
					 	$('#projDataList').html(pHtml);
					}else{
						/***************************************************************
						* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
						****************************************************************/
						$('#projPlanpaging').children().remove();
						
						$("#projDataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
					}
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#projPlanpaging').children().remove();
					
					$("#projDataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
				}
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
	}
	
</script>
<!-- 본문내용 -->
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>협약과제 상세</h3>  
	<div class="titles">
			<span></span>
			<div class="btn_wrap" >			
				<a href="javascript:void(0);" onclick="javascript:fn_reg();" class="btn btn-secondary">연차과제생성</a>
	  			<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
			</div>		
	</div>
	

	 <form name="regFrm" method="post"  action=""  enctype="multipart/form-data">
		<input type="hidden" name="proj_id" id="proj_id" value="${data.proj_id}" />
		<input type="hidden" name="file_group" id="file_group" value="${data.file_group}">
		<input type="hidden" name="part_org_cds" id="part_org_cds" value="">
		<input type="hidden" name="resp_cds" id="resp_cds" value="">
		
		<input type="hidden" name="page" value="${regParam.page}" />
		<input type="hidden" name="dept_org" value="${regParam.dept_org}" />
		<input type="hidden" name="ddct_org" value="${regParam.ddct_org}" />		
		<input type="hidden" name="searchword" value="${regParam.searchword}" />
		
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
	 
	 
	 		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;연차과제 정보</h4>
			<div class="btn_wrap">			
										  
			</div>		
		</div>
		<div class="admin ma_t_20"> 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable"> 
				<caption>
				 	  과제수행관리 -  번호, 소관기관 , 전담기관, 신청과제명, 단계구분, 연차구분, 수행기간
				</caption>
				<colgroup>
					<col style="width:5%">	
					<col style="width:12%">
					<col style="width:12%">						 	
					<col style="width:40%">								
					<col style="width:8%">				
					<col style="width:8%">		
					<col style="width:15%">		
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>												
						<th scope='col'>소관기관</th>
						<th scope='col'>전담기관</th>
						<th scope='col'>연차과제명</th>																							
						<th scope='col'>단계구분</th>
						<th scope='col'>연차구분</th>
						<th scope='col'>수행기간</th>
					</tr>
				</thead>
				<tbody id="projDataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="projPlanpaging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="right">
<!-- 		  		<a href ="javascript:void(0);" onclick="javascript:fn_write();" class="btn btn-primary">등록</a> -->
			</div>
			<!--//버튼 -->
		</div>
</div>

   

	