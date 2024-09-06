<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	 <script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 
	<%-- <script src="${ctxt}/resources/js/egovframework/com/cmm/fms/EgovMultiFile.js" type="text/javascript" ></script> --%>
	<script type="text/javascript">
	
	
	$(function(){   
		
		getFileList($("#atch_link_id").val());
		
	
	});
	  
	function fn_fileDownload(val){
		var frm = document.qnaFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?pkFileId_value="+val;
		frm.submit();
	}	
	
	function fn_delete(){
		
		if(confirm("삭제하시겠습니까?") ==true ){
			
			
			var form = document.qnaFrm;
			form.action = '${ctxt}/center/qna/deleteQna.do';
			form.submit();	
		}
	}
	
	function fn_back(){
		var form = document.qnaList;
		form.action = '${ctxt}/center/qna/qnaList.do';
		form.submit();	
	}
	

	
	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>1:1 문의하기</h3>  
	<form name="qnaList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${qna.searchopt}" />
		<input type="hidden" name="searchword" value="${qna.searchword}" />
		<input type="hidden" name="page" value="${qna.page}" />
	</form>
	<form name="qnaFrm" method="post"  action="" >			
		<input type="hidden" name="qna_id" id = "qna_id" value="${data.qna_id}" />
	</form>
	    <input type="hidden" name="atch_link_id" id="atch_link_id" value="${data.atch_link_id}">	
		<table class="table_v">  
			<colgroup>
				<col width="120px">
			</colgroup>
			<tbody>			
				<tr>
					<th>제목</th>
					<td>				
						<c:out value="${data.qna_title}"></c:out>
					</td>
				</tr>
				<tr>
					<th>문의 유형</th>
					<td>					
						${data.qna_type}
					</td>
				</tr>		
				<tr>
					<th>작성일</th>  
					<td>									
						<c:out value="${data.create_dttm}"></c:out>					
					</td>     
				</tr>
				<c:if test="${data.atch_link_id != null}">
						<tr>
							<th>첨부파일</th>
							<td  id="fileContainer">
						
							</td>
						</tr>
					</c:if>						
				<tr>
					<td colspan="2">									
						<div id="divEtcarea"><c:out value="${data.qna_contents}"></c:out></div>					
					</td>     
				</tr>
					<tr>
					<th>답변여부</th>
					<td>	
						<c:choose>
							<c:when test = "${!empty data.qna_answer && !empty data.answer_dttm}">
								답변완료
							</c:when>
							<c:otherwise>
								답변대기
							</c:otherwise>
						</c:choose>								
					</td>
				</tr>         										
			</tbody>
		</table>
		
		<c:if test = "${!empty data.qna_answer && !empty data.answer_dttm}">
		
			<br/>
			<table class="table_v">  
				<colgroup>
					<col width="120px">
					<col width="*">  
				</colgroup>
				<tbody>
					<tr class="only">
						<th>답변 내용</th>
						<td>				
							<span style="color: #888888;font-weight: bold;"><c:out value="${data.answer_dttm}"></c:out></span>
							<br/>
							<br/>
														
							<c:out value="${data.qna_answer}"></c:out>
						</td>
					</tr>					    									
				</tbody>
			</table>
		</c:if>
	   
	 <div class="buttonBox">
		
	  	<div align="right">	  		  	

			<a onclick="javascript:fn_delete();" class="btn btn-secondary">삭제 </a>
			<a onclick="javascript:fn_back();" class="btn btn-secondary">목록 </a>
		</div>
	</div>
	         
</div>


    
   
	<!-- //right_content -->

	