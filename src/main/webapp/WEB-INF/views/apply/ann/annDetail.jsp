<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<script type="text/javascript">
	
	$(function(){   
		 
		getFileList($("#file_group").val(), '1');
		
	});
	   
	// 첨부파일 다운로드
	function fn_fileDownload(val){
		
		var frm = document.annFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}	
	
	 //첨부파일등록 팝업 호출
	  function fn_egov_file_popup() {
			var annManageVo=document.annFrm;
			var file_group = annManageVo.file_group.value;
			var file_gb = "1";

			popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	  }
	  //첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	 function fileContainerRefresh() {
		
		  getFileList($("#file_group").val(), '1');
	  }

	// 공고정보 저장
	function fn_save(){
		
		fn_showCustomConfirm("question","저장하시겠습니까?", function() {
           	var sendData = $("form[name='annFrm']").serialize();
	
			$.ajax({
				url: '${ctxt}/apply/ann/updateAnn.do',
				data: sendData,
				type: 'POST', 
				dataType: 'json',	 
			   
						success: function(result){			
							
							if(result.result != 0){
								fn_showCustomAlert("수정이 완료되었습니다.");
							}else{
								fn_showCustomAlert("수정에 실패 했습니다.");
							}
						},
						error: function(request,status,error) {
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
			  });
		});
	}
	
	// 공고 삭제
	function fn_delete(){
		
		fn_showCustomConfirm("warning","삭제하시겠습니까?", function() {
			
			var params = {};
			params.ann_id    =  $("#ann_id").val();
			
			$.ajax({
				url: '${ctxt}/apply/ann/chkAnnId.do',
				data: params,
				type: 'POST', 
				dataType: 'json',	 
				success: function(result){			
			
					if(result.result != 0){
						fn_showCustomAlert("연구과제 신청이 존재하여 삭제가 불가합니다.");
						return false;
					}else{
						var form = document.annFrm;
						form.action = '${ctxt}/apply/ann/deleteAnn.do'; 
						form.submit();
					}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			  });

		});
		
	}
	
	// 목록
	function fn_back(){
		var form = document.annList;
		form.action = '${ctxt}/apply/ann/annList.do';
		form.submit();	
	}
	

	
	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>공고현황 상세</h3>  
	<form name="annList" method="post"  action="">	
		<input type="hidden" name="searchoption1" value="${annParam.searchoption1}" />
		<input type="hidden" name="searchoption2" value="${annParam.searchoption2}" />
		<input type="hidden" name="searchoption3" value="${annParam.searchoption3}" />
		<input type="hidden" name="searchword" value="${annParam.searchword}" />
		<input type="hidden" name="page" value="${annParam.page}" />
		
	</form>
	<form name="annFrm" method="post"  action=""  enctype="multipart/form-data">	
		<input type="hidden" name="ann_id" id="ann_id" value="${data.ann_id}" />
		<input type="hidden" name="file_group" id="file_group" value="${data.file_group}">
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="30%">  
			<col width="20%">
			<col width="30%">  
		</colgroup>
		<tbody>
			<tr>
				<th><font color="red" class="last-child">*</font>소관부처</th>
				<td>
					<div class="custom-select selectRow">
						 <select id=dept_org name="dept_org">
				            <option value="">소관부처 select</option>
				            <c:forEach var="dept" items="${deptList}">
				                <option value="${dept.org_nm}" <c:if test="${dept.org_nm == data.dept_org}">selected</c:if>>${dept.org_nm}</option>
				            </c:forEach>
				        </select>
				     </div>
				</td>
				<th><font color="red" class="last-child">*</font>전담기관</th>
				<td>
					<div class="custom-select selectRow">
						<select id="ddct_org" name="ddct_org">
				            <option value="">전담기관 select</option>
				            <c:forEach var="ddct" items="${ddctList}">
				                <option value="${ddct.org_nm}" <c:if test="${ddct.org_nm == data.ddct_org}">selected</c:if>>${ddct.org_nm}</option>
				            </c:forEach>
				        </select>
				     </div>
				</td>
			</tr>
			<tr>
				<th><font color="red" class="last-child">*</font>공고번호</th>
				<td>
					<input type="text" name="ann_no" id="ann_no" value="${data.ann_no}" style="width:100%;ime-mode:active">
				</td>
				<th><font color="red" class="last-child">*</font>공고일</th>
				<td>
					<input type="date" name="ann_dt" id="ann_dt" value="${data.ann_dt}" max="9999-12-31" min="1111-01-01" style="width:100%;ime-mode:active">
				</td>
			</tr>
			<tr>
				<th><font color="red" class="last-child">*</font>접수기간</th>
				<td colspan="3">
					<input type="date" name=rcpt_strtdt id="rcpt_strtdt" value="${data.rcpt_strtdt}" max="9999-12-31" min="1111-01-01" style="width:30%" /> ~
					<input type="date" name="rcpt_enddt" id="rcpt_enddt" value="${data.rcpt_enddt}"max="9999-12-31" min="1111-01-01" style="width:30%"/>
				</td>
			</tr>
			<%-- <tr>
				<th>등록일</th>
				<td>
					<c:out value="${data.create_dttm}"></c:out>
				</td>
				<th>등록자</th>
				<td>
					<c:out value="${data.username}"></c:out>
				</td>
			</tr> --%>
			<tr>
				<th><font color="red" class="last-child">*</font>공고명</th>
				<td colspan="3">
					<input type="text" name="ann_nm" id="ann_nm" value="${data.ann_nm}" style="width:100%;ime-mode:active">
				</td>
			</tr>
			<tr>
				<th>사업목적</th>  
				<td colspan="3">				
					<textarea name="ann_info" id="ann_info" rows="10" style="width:100%;ime-mode:active;">${data.ann_info}</textarea> 
				</td>     
			</tr>         		
	   
			<tr> 
				<th>공고현황 첨부파일</th>
			  	<td colspan="3">
			    	<a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup();" class="btn btn-secondary">파일 올리기</a>
			    	<div id="fileContainer1" class="file-container"></div>	
			    </td>
			</tr>
			
		</tbody>
</table>
	 </form>       
	 <div class="flex_box">
		
	  	<div align="right">
	  			<a href="javascript:void(0);" onclick="javascript:fn_save();" class="btn btn-secondary">수정</a>
	  			<a href="javascript:void(0);" onclick="javascript:fn_delete();" class="btn btn-secondary">삭제</a>
	  			<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
		</div>
	</div>
	         
</div>


    
   
	<!-- //right_content -->

	