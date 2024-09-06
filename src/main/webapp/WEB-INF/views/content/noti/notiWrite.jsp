<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	<script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 

<%-- 	<script src="${ctxt}/resources/js/egovframework/com/cmm/fms/EgovMultiFile.js" type="text/javascript" ></script> --%>


	<script type="text/javascript">
	
	
	$(function(){
		
		GetDateTMS();
		
		fn_daumEditor("noti_contents");
		
	});
	
	

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

	    $("#atch_link_id").val(totalTime);
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


	//첨부파일등록 팝업 호출
	function fn_egov_file_popup() {
		var notiManageVo=document.notiFrm;
		var atch_link_id = notiManageVo.atch_link_id.value;
		var systemGb = "platForm";
		var boardGb = "noti";

		popAttfileModify(atch_link_id, systemGb, boardGb, 'fileContainerRefresh');

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
			form.action = '${ctxt}/content/noti/insertNoti.do';
			form.submit();	
		}
	}
	
	function fn_back(){
		var form = document.notiList;
		form.action = '${ctxt}/content/noti/notiList.do';
		form.submit();	
	}
	
</script>
 <!-- 팝업 미리보기div 생성존  -->
    <div id = notiUpZone>
	 
	</div>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>공지사항 관리 등록</h3>  
	<form name="notiList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${noti.searchopt}" />
		<input type="hidden" name="searchword" value="${noti.searchword}" />
		<input type="hidden" name="page" value="${noti.page}" />
		
	</form>
	<form name="notiFrm" method="post"  action=""  enctype="multipart/form-data">	

		<input type="hidden" name="noti_id" id = "noti_id" value="" />
		<input type="hidden" name="atch_link_id" id="atch_link_id" value="">
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="*">  
		</colgroup>
		<tbody>
			<tr>
				<th>중요공지</th>
				<td>
					 <select name="top_status" class="select" title="조회조건 선택">
						<option value="0">일반공지</option>
						<option value="1">중요공지</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="noti_title" id="noti_title" value="" style="width:100%;ime-mode:active">
				</td>
			</tr>
			
			<tr>
				<th>내용</th>  
				<td>				
					<textarea name="noti_contents" id="noti_contents" rows="10" style="width:100%;ime-mode:active;display:none;"></textarea>
					<div id="editor_frame"></div>
				</td>     
			</tr>         		
	   
			<tr> 
				<th>첨부파일</th>
			  	<td>
					 <input type='button' onClick='fn_egov_file_popup();' value='파일 올리기'>		
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

	