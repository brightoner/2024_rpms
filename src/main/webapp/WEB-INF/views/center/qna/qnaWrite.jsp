<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	<script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 

<%-- 	<script src="${ctxt}/resources/js/egovframework/com/cmm/fms/EgovMultiFile.js" type="text/javascript" ></script> --%>

	<script type="text/javascript">
	
	
	$(function(){
		
		GetDateTMS();
		
		//fn_daumEditor("qna_contents");
		 
		
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
		var qnaManageVo=document.qnaFrm;
		var atch_link_id = qnaManageVo.atch_link_id.value;
		var systemGb = "platForm";
		var boardGb = "qna";

		popAttfileModify(atch_link_id, systemGb, boardGb, 'fileContainerRefresh');

  	}
	
	
	function fn_save(){
		if($("#qna_title").val().trim() == "") {
			fn_showCustomAlert("1:1 문의 제목을 입력 하세요.");
			$("#qna_title").val("");
			$('#qna_title').focus();
			return false;
		}
		if($("#qna_contents").val().trim() == "") {
			fn_showCustomAlert("1:1 문의 내용을 입력 하세요.");
			$("#qna_contents").val("");
			$('#qna_contents').focus();
			return false;
		}
		
		if(confirm("저장하시겠습니까?") ==true ){
			
			
			//
			var form = document.qnaFrm;
			form.action = '${ctxt}/center/qna/insertQna.do';
			form.submit();	
		}
	}
	
	function fn_back(){
		var form = document.qnaList;
		form.action = '${ctxt}/center/qna/qnaList.do';
		form.submit();	
	}
	
</script>
 <!-- 팝업 미리보기div 생성존  -->
    <div id = qnaUpZone>
	 
	</div>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>1:1 문의하기</h3>  
	<form name="qnaList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${qna.searchopt}" />
		<input type="hidden" name="searchword" value="${qna.searchword}" />
		<input type="hidden" name="page" value="${qna.page}" />
	</form>
	<form name="qnaFrm" method="post"  action=""  enctype="multipart/form-data">	

		<input type="hidden" name="qna_id" id = "qna_id" value="" />
		<input type="hidden" name="atch_link_id" id="atch_link_id" value="">
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="*">  
		</colgroup>
		<tbody>
			<tr>
				<th>문의 유형</th>
				<td class="selectRow">
					
					 <select name="qna_type" class="select" title="조회조건 선택">
						<!-- <option value="0" >회원가입/탈퇴</option>
						<option value="1" >유저정보</option>
						<option value="2" >BJ권한 신청</option>
						<option value="3" >결제/충전</option>
						<option value="4" >기타</option> -->		
							<c:forEach items="${qna_code}" var="item" varStatus="idx">
								<option value="${item.cd}">${item.cd_nm}</option>	
							</c:forEach>			
					</select>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="qna_title" id="qna_title" value="" style="width:100%;ime-mode:active">
				</td>
			</tr>
			
			<tr>
				<th>내용</th>  
				<td>				
					<textarea name="qna_contents" id="qna_contents" rows="10" style="width:100%;ime-mode:active"></textarea>
					
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
	  		
	 
			<a onclick="javascript:fn_save();" class="btn btn-secondary">저장 </a>
			<a onclick="javascript:fn_back();" class="btn btn-secondary">목록 </a>
		
		</div>
	</div>
	         
</div>


    
   
	<!-- //right_content -->

	