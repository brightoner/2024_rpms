<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	 <script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 
	<%-- <script src="${ctxt}/resources/js/egovframework/com/cmm/fms/EgovMultiFile.js" type="text/javascript" ></script> --%>
	<script type="text/javascript">
	
	
	$(function(){   
	
	});
	  
	
	
	function fn_save(){
		if($("#faq_title").val().trim() == "")
		{
			fn_showCustomAlert("자주 묻는 질문 제목을 입력 하세요.");
			$("#faq_title").val("");
			$('#faq_title').focus();
			return false;
		}
		
		if(confirm("저장하시겠습니까?") ==true ){
			
		
			//
			var form = document.faqFrm;
			form.action = '${ctxt}/content/faq/updateFaq.do';
			form.submit();	
		}
	}
	
	function fn_delete(){
		
		if(confirm("삭제 하시겠습니까?") ==true ){
			
			$("#faq_contents").val(Editor.getContent());
			//
			var form = document.faqFrm;
			form.action = '${ctxt}/content/faq/deleteFaq.do';
			form.submit();	
		}
	}
	function fn_back(){
		var form = document.faqList;
		form.action = '${ctxt}/content/faq/faqList.do';
		form.submit();	
	}
	

	
	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>자주 묻는 질문 관리</h3>  
	<form name="faqList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${faq.searchopt}" />
		<input type="hidden" name="searchword" value="${faq.searchword}" />
		<input type="hidden" name="page" value="${faq.page}" />
	</form>
	<form name="faqFrm" method="post"  action="" enctype="multipart/form-data">			
		<input type="hidden" name="faq_id" id = "faq_id" value="${data.faq_id}" />
		
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="*">  
		</colgroup>
		<tbody>			
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="faq_title" id="faq_title" value="${data.faq_title}" style="width:100%;ime-mode:active">
				</td>
			</tr>	
			<tr>
				<th>표시순번</th>
				<td>
					<input type="text" name="faq_order" id="faq_order" value="${data.faq_order}" style="width:20%;ime-mode:active">
				</td>
			</tr>		
			<tr>
				<th>내용</th>  
				<td>				
					<textarea name="faq_contents" id="faq_contents" rows="10" style="width:100%;ime-mode:active;">${data.faq_contents}</textarea>
				
				</td>     
			</tr>         		
						
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

	