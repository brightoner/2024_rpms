<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 파일 그룹키
		GetDateTMS();
		
		
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

	    $("#file_group").val(totalTime);
	    
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

	// 파일다운로드
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
	
	// 공고 저장
	function fn_save(){
		
		// 소관부처 validation
		if($('[name=dept_org] :selected').val() == "" || $('[name=dept_org] :selected').val() == null){
			fn_showCustomAlert("소관부처를 입력 하세요.");
			$("#dept_org").val("");
			$('#dept_org').focus();
			return false;
		}
		
		// 전담기간 validation
		if($('[name=ddct_org] :selected').val() == "" || $('[name=ddct_org] :selected').val() == null){
			fn_showCustomAlert("전담기관을 입력 하세요.");
			$("#ddct_org").val("");
			$('#ddct_org').focus();
			return false;
		}
		
		// 공고번호 validation
		if($("#ann_no").val() == "" || $("#ann_no").val() == null){
			fn_showCustomAlert("공고번호를 입력 하세요.");
			$("#ann_no").val("");
			$('#ann_no').focus();
			return false;
		}
		
		// 공고일 validation
		if($("#ann_dt").val() == "" || $("#ann_dt").val() == null){
			fn_showCustomAlert("공고일을 입력 하세요.");
			$("#ann_dt").val("");
			$('#ann_dt').focus();
			return false;
		}
		
		// 접수기간 validation
		var rcptStrtdt = $("#rcpt_strtdt").val().replace(/-/g, "");  
		var rcptEnddt = $("#rcpt_enddt").val().replace(/-/g, "");  
		
		if($("#rcpt_strtdt").val() == "" || $("#rcpt_strtdt").val() == null){
			fn_showCustomAlert("접수 시작 기간을 입력 하세요.");
			$("#rcpt_strtdt").val("");
			$('#rcpt_strtdt').focus();
			return false;
		}
		
		if($("#rcpt_enddt").val() == "" || $("#rcpt_enddt").val() == null){
			fn_showCustomAlert("접수 종료 기간을 입력 하세요.");
			$("#rcpt_enddt").val("");
			$('#rcpt_enddt').focus();
			return false;
		}
		
		if(rcptStrtdt > rcptEnddt){
			fn_showCustomAlert("접수 시작 기간이 접수 종료 기간보다 큽니다.");
			$("#rcpt_strtdt").val("");
			$("#rcpt_enddt").val("");
			$('#rcpt_strtdt').focus();
			return false;
		}
		
		// 공고번호 validation
		if($("#ann_nm").val() == "" || $("#ann_nm").val() == null){
			fn_showCustomAlert("공고명 입력 하세요.");
			$("#ann_nm").val("");
			$('#ann_nm').focus();
			return false;
		}
		
		fn_showCustomConfirm("question","저장하시겠습니까?", function() {
			
			var form = document.annFrm;
			form.action = '${ctxt}/apply/ann/insertAnn.do';
			form.submit();	

		});
	}
	
	function fn_back(){
		var form = document.annList;
		form.action = '${ctxt}/apply/ann/annList.do';
		form.submit();	
	}
	
</script>
 <!-- 팝업 미리보기div 생성존  -->
    <div id = notiUpZone>
	 
	</div>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>공고현황 등록</h3>  
	<form name="annList" method="post"  action="">	
		<input type="hidden" name="searchoption1" value="${annParam.searchoption1}" />
		<input type="hidden" name="searchoption2" value="${annParam.searchoption2}" />
		<input type="hidden" name="searchoption3" value="${annParam.searchoption3}" />
		<input type="hidden" name="searchword" value="${annParam.searchword}" />
		<input type="hidden" name="page" value="${annParam.page}" />
		
	</form>
	
	<form name="annFrm" method="post"  action=""  enctype="multipart/form-data">	
		<input type="hidden" name="ann_id" id="ann_id" value="" />
		<input type="hidden" name="annDt" id="annDt" value="" />
		<input type="hidden" name="rcptStrtdt" id="rcptStrtdt" value="" />
		<input type="hidden" name="rcptEnddt" id="rcptEnddt" value="" />
		<input type="hidden" name="file_group" id="file_group" value="">
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
								<select id="dept_org" name="dept_org" class="select">
									<option value="">소관부처 선택</option> 
							        <c:forEach var="dept" items="${deptList}">
							        	<option value="${dept.org_nm}">${dept.org_nm}</option>
							        </c:forEach>
								</select>
							</div>
						</td>
						<th><font color="red" class="last-child">*</font>전담기관</th>
						<td>
							<div class="custom-select selectRow">
								 <select id="ddct_org" name="ddct_org"> 
									<option value="">전담기관 선택</option>  
							        <c:forEach var="ddct" items="${ddctList}">
							        	<option value="${ddct.org_nm}">${ddct.org_nm}</option>
							        </c:forEach>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<th><font color="red" class="last-child">*</font>공고번호</th>
						<td>
							<input type="text" name="ann_no" id="ann_no" value="" style="width:100%;ime-mode:active" maxlength="15">
						</td>
						<th><font color="red" class="last-child">*</font>공고일</th>
						<td>
							<input type="date" name="ann_dt" id="ann_dt" value="" max="9999-12-31" min="1111-01-01" style="width:100%;ime-mode:active">
						</td>
					</tr>
					<tr>
						<th><font color="red" class="last-child">*</font>접수기간</th>
						<td colspan="3">
							<input type="date" name=rcpt_strtdt id="rcpt_strtdt" value="" max="9999-12-31" min="1111-01-01" style="width:30%" /> ~
					<input type="date" name="rcpt_enddt" id="rcpt_enddt" value=""max="9999-12-31" min="1111-01-01" style="width:30%"/>
						</td>
					</tr>
					<tr>
						<th><font color="red" class="last-child">*</font>공고명</th>
						<td colspan="3">
							<input type="text" name="ann_nm" id="ann_nm" value="" style="width:100%;ime-mode:active" maxlength="200">
						</td>
					</tr>
					<tr>
						<th>사업목적</th>  
						<td colspan="3">				
							<textarea name="ann_info" id="ann_info" rows="10" style="width:100%;ime-mode:active;"></textarea> 
						</td>     
					</tr>         		
			   
					<tr> 
						<th>공고현황 첨부파일</th>
					  	<td colspan="3">
							 <a href="javascript:void(0);" onclick="fn_javascript:fn_egov_file_popup();" class="btn btn-secondary" >파일올리기</a>
							 <div id="fileContainer1" class="file-container"></div>			
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


    
   
	<!-- //right_content -->

	