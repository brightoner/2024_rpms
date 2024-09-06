<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<script type="text/javascript">
	
	$(function(){   
		
		
		getFileList($("#file_group").val(), '2');
		getFileList($("#file_group").val(), '3');
		
	});
	  
	// 첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.selFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}	
	
	 //첨연구신청과제 첨부파일등록 팝업 호출
	  function fn_egov_file_popup2() {
			var selManageVo=document.selFrm;
			var file_group = selManageVo.file_group.value;
			var file_gb = "2";

			popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	  }
	 
	 // 산정결과 첨부파일등록 팝업 호출
	  function fn_egov_file_popup3() {
			var selManageVo=document.selFrm;
			var file_group = selManageVo.file_group.value;
			var file_gb = "3";

			popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	  }
	 
	  //첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	  function fileContainerRefresh() {
		  getFileList($("#file_group").val(), '2');
		  getFileList($("#file_group").val(), '3');
	  }

	// 선정결과 처리 저장
	function fn_save(){
		
		fn_showCustomConfirm("question","저장하시겠습니까?", function() {
			
			var sendData = $("form[name='selFrm']").serialize();
			
		    $.ajax({
		        url: '${ctxt}/apply/sel/updateSelProj.do',
		        data: sendData,
		        type: 'POST', 
		        dataType: 'json',	 
					success: function(result){			
						if(result.result != 0){
							fn_showCustomAlert("저장 되었습니다..");
						}else{
							fn_showCustomAlert("저장에 실패 했습니다.");
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
		var form = document.selListFrm;
		form.action = '${ctxt}/apply/sel/selProjList.do';
		form.submit();	
	}
	

	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>선정결과 처리 상세</h3>  
	<form name="selListFrm" method="post"  action="">	
		<input type="hidden" name="searchoption1" value="${selParam.searchoption1}" />
		<input type="hidden" name="searchoption2" value="${selParam.searchoption2}" />
		<input type="hidden" name="searchoption3" value="${selParam.searchoption3}" />
		<input type="hidden" name="searchoption4" value="${selParam.searchoption4}" />
		<input type="hidden" name="searchoption5" value="${selParam.searchoption5}" />	
		<input type="hidden" name="searchword1" value="${selParam.searchword1}" />
		<input type="hidden" name="searchword2" value="${selParam.searchword2}" />
		<input type="hidden" name="page" value="${selParam.page}" />
	</form>
	
	<form name="annFrm" method="post"  action=""  enctype="multipart/form-data">
	<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;공고현황 정보</h4>	
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
	 </form>      
	
	 <form name="selFrm" method="post"  action=""  enctype="multipart/form-data">
	 	<input type="hidden" name="ann_id" id="ann_id" value="${data.ann_id}" />
		<input type="hidden" name="proj_id" id="proj_id" value="${data.proj_id}" />
		<input type="hidden" name="file_group" id="file_group" value="${data.file_group}">
	<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;선정결과 처리 정보</h4>	
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
					<th>신청(접수)일</th>
					<td> 
						<c:out value="${data.req_dt}"></c:out>
					</td>
				</tr>
				<%-- <tr>
					<th rowspan="2">과제명</th>
					<td colspan="3">
						<c:out value="${data.proj_nm_kor}"></c:out>
					</td>
				</tr>
				<tr>
					<td>
						<c:out value="${data.proj_nm_eng}"></c:out>
					</td>
				</tr> --%>
				<tr>
					<th>과제명</th>
					<td colspan="3">
						<table style="border:0px solid rgb(235 221 216); margin:0px;"> 
							<tr>
								<th style="width: 100px;">국문</th>
								<td style="width: 600px;">
									<c:out value="${data.proj_nm_kor}"></c:out>
								</td>
							</tr>
							<tr>
								<th  style="width: 100px;">영문</th>
								<td  style="width: 600px;">
									<c:out value="${data.proj_nm_eng}"></c:out>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>선정결과</th>
					<td class="selectRow2 last-child"> 
						<div class="custom-select selectRow">
							<select id="sel_gb" name="sel_gb" class="select last-child">
							    <option value="0" ${data.sel_gb == '0' ? 'selected' : ''}>선택</option>  
							    <option value="1" ${data.sel_gb == '1' ? 'selected' : ''}>선정</option>  
							    <option value="2" ${data.sel_gb == '2' ? 'selected' : ''}>탈락</option>  
							</select>
						</div>
					</td>
					<th><font color="red" class="last-child">*</font>선정일자</th>
					<td>
						 <input type="date" name="sel_dt" id="sel_dt"  value ="${data.sel_dt}" max="9999-12-31" min="1111-01-01">		 
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>선정평가 의견</th>
					<td colspan="3">
						 <textarea name="sel_info" id="sel_info" rows="10" style="width:100%;ime-mode:active;">${data.sel_info}</textarea> 
					</td>
				</tr>
	 			<tr> 
					<th>연구과제신청 첨부파일</th> 
				  	<td colspan="3">
<!-- 						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup2();" class="btn btn-secondary">파일 올리기</a> -->
<!-- 						 <div id="fileContainer2" class="file-container"></div>		 -->
						 <div id="fileContainer2"></div>		
				    </td>
				</tr>
				<tr> 
					<th>선정결과 첨부파일</th> 
				  	<td colspan="3">
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup3();" class="btn btn-secondary">파일 올리기</a>
						 <div id="fileContainer3" class="file-container"></div>		
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
<form name ="orgPopForm" id="orgPopForm" method="post" action="">
	<input type="hidden" id="id_gbn1" name="id_gbn1"/>
	<input type="hidden" id="id_gbn2" name="id_gbn2"/>
	<input type="hidden" id="id_gbn3" name="id_gbn3"/>
	<input type="hidden" id="id_gbn4" name="id_gbn4"/>
	<input type="hidden" id="id_gbn5" name="id_gbn5"/>
	<input type="hidden" id="id_gbn6" name="id_gbn6"/>
	<input type="hidden" id="id_gbn7" name="id_gbn7"/>
</form>

    
   
	<!-- //right_content -->

	