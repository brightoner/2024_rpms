<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
 <script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 
<%-- <script src="${ctxt}/resources/js/egovframework/com/cmm/fms/EgovMultiFile.js" type="text/javascript" ></script> --%>
<style>
#divEtcarea img {
	display: block;
}

</style>

	<script type="text/javascript">
	
	
	$(function(){   
		
	//	getFileList('${data.atch_link_id}');
		
	
	});
	  
	function fn_fileDownload(val){
		var frm = document.notiList;
		frm.action = "/atchFile/CmmnFileDown.do" + "?pkFileId_value="+val;
		frm.submit();
	}	
	
	
	function fn_back(){
		var form = document.notiList;
		form.action = '${ctxt}/center/noti/notiList.do';
		form.submit();	
	}
	

	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>공지사항</h3>  
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
			<col width="120px;">
		</colgroup>
		<tbody>			
			<tr>
				<th>제목</th>
				<td>
					<c:out value="${data.noti_title}"></c:out> 
				</td>
			</tr>
			<tr>
				<th>등록일</th>
				<td>
					<c:out value="${data.create_dttm}"></c:out>
				</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>
					<c:out value="${data.rdcnt}"></c:out>
				</td>
			</tr>						
			<tr>
				<!-- <th>내용</th>   -->
				<td align="left" colspan="2">
					<div id='divEtcarea' style="width: 100%;">${data.noti_contents} </div>				
					
				</td>     
			</tr>         		
			<!-- <tr>
				<th scope="row">첨부파일</th>
					<td  id="fileContainer">
				
					</td>
				</tr> -->
		</tbody>
	</table>
	 </form>       
	 <div class="flex_box">
		
	  	<div align="right">

				<a onclick="javascript:fn_back();" class="btn btn-secondary">목록 </a>
		</div>
	</div>
	         
</div>


 <form name ="fForm">
	<input type="hidden" id="param_atchFileId" name="param_atchFileId" value="" alt="fParameter" />
	<input type="hidden" id="param_pageGbn" name="param_pageGbn" value="" alt="fParameter" />
	<input type="hidden" id="param_context" name="param_context" value="" alt="fParameter" />
	<input type="hidden" id="param_title" name="param_title" value="${data.noti_title}" alt="fParameter" />
</form>
    
   
	<!-- //right_content -->

	