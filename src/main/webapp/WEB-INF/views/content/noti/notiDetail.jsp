<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	 <script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 
	
	<script type="text/javascript">
	
	
	$(function(){   
		
		fn_daumEditor("noti_contents");	  
		
		//getFileList($("#atch_link_id").val());
		
		/* $(document).on("click", "input[name='FILE_NM']", function () {
			var frm = document.notiFrm;
			frm.action = "/atchFile/CmmnFileDown.do" + "?pkFileId_value="+$(this).attr("pk_file_id");
			frm.submit();
		}); */
	});
	  
	function fn_fileDownload(val){
		var frm = document.notiFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?pkFileId_value="+val;
		frm.submit();
	}	
	
	 //첨부파일등록 팝업 호출
	  function fn_egov_file_popup() {
			var notiManageVo=document.notiFrm;
			var atch_link_id = notiManageVo.atch_link_id.value;
			var systemGb = "platForm";
			var boardGb = "noti";

			popAttfileModify(atch_link_id, systemGb, boardGb, 'fileContainerRefresh');
	  }
	  //첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	  function fileContainerRefresh() {
		
		  getFileList($("#atch_link_id").val());
	  }

	//첨부파일삭제
	  function fileDelete() {
		  var notiManageVo=document.notiFrm;

		  var contextName = "platForm";
		  var fileKey = notiManageVo.atch_link_id.value;

		  var fileCnt = $("#fileListCnt").val();
		  if(fileCnt > 0){
		 	 deleteContents(fileKey, contextName);
		  }else{
		     orgFormSubmit();
		  }
	  }

	
	
	function fn_save(){
		if($("#noti_title").val().trim() == "")
		{
			fn_showCustomAlert("공지사항 제목을 입력 하세요.");
			$("#noti_title").val("");
			$('#noti_title').focus();
			return false;
		}
		
		if(confirm("저장하시겠습니까?") ==true ){
			
			$("#noti_contents").val(Editor.getContent());
			//
			var form = document.notiFrm;
			form.action = '${ctxt}/content/noti/updateNoti.do';
			form.submit();	
		}
	}
	
	function fn_delete(){
		
		if(confirm("삭제 하시겠습니까?") ==true ){
			
			$("#noti_contents").val(Editor.getContent());
			//
			var form = document.notiFrm;
			form.action = '${ctxt}/content/noti/deleteNoti.do';
			form.submit();	
		}
	}
	function fn_back(){
		var form = document.notiList;
		form.action = '${ctxt}/content/noti/notiList.do';
		form.submit();	
	}
	

	
	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>공지사항 관리 상세</h3>  
	<form name="notiList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${noti.searchopt}" />
		<input type="hidden" name="searchword" value="${noti.searchword}" />
		<input type="hidden" name="page" value="${noti.page}" />
	</form>
	<form name="notiFrm" method="post"  action="" enctype="multipart/form-data">			
		<input type="hidden" name="noti_id" id = "noti_id" value="${data.noti_id}" />
		<input type="hidden" name="atch_link_id" id="atch_link_id" value="${data.atch_link_id}">
		
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="*">  
		</colgroup>
		<tbody>
			<tr>
				<th>중요공지</th>
				<td class='selectRow'>
					 <select name="top_status" class="select" title="조회조건 선택">
						<option value="0" <c:if test="${data.top_status == '0'}" >selected</c:if>>일반공지</option>
						<option value="1" <c:if test="${data.top_status == '1'}" >selected</c:if>>중요공지</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="noti_title" id="noti_title" value="${data.noti_title}" style="width:100%;ime-mode:active">
				</td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td colspan="2">				
					<textarea name="noti_contents" id="noti_contents" rows="10" style="width:100%;ime-mode:active;display:none;">${data.noti_contents}</textarea>
					<div id="editor_frame"></div>
				</td>     
			</tr>         		
			<%-- <tr> 
				<th>첨부파일</th>
			  	<td>
			  		<input type='button' onClick='fn_egov_file_popup();' value='파일 수정'>								
					
			  </td>
	   		</tr>	
	   		<c:if test="${data.atch_link_id != null}">
				<tr>
					<th>첨부파일목록</th>
					<td  id="fileContainer">
				
					</td>
				</tr>
			</c:if> --%>				
		</tbody>
	</table>
	 </form>       
	 <div class="flex_box">
		
	  	<div align="right">
	  		<a href="javascript:void(0);" onclick="javascript:fn_save();" class="btn btn-secondary">저장</a>
	  		<a href="javascript:void(0);" onclick="javascript:fn_delete();" class="btn btn-secondary">삭제</a>
	  		<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>

		</div>
	</div>
	         
</div>


    
   
	<!-- //right_content -->

	