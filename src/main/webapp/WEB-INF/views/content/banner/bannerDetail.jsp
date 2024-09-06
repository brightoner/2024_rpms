<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	 <script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 
	<%-- <script src="${ctxt}/resources/js/egovframework/com/cmm/fms/EgovMultiFile.js" type="text/javascript" ></script> --%>
	<script type="text/javascript">
	
	
	$(function(){   
		
		fn_daumEditor("banner_contents");	
		
		//배너 첨부 이미지의 크기를 정하고 그에 맞게 이미지를 등록하도록 한다. 
	 	$("#egovComFileUploader").change(function(event) {
	 		var $dataWidth = $(this).attr('data-width');
			var $dataHeight = $(this).attr('data-height');
			var $target = $(this);
			
		    var file = event.target.files[0];
		    var reader = new FileReader(); 

		    reader.onload = function(e) {
		 		
		 		var img = new Image();
			 	img.src  = e.target.result;
			 	
			 	img.onload = function(){
			 		
			 		var imgWidth = this.width;
			 		var imgHeight= this.height;			 		
			 		$('#imgPreView').attr('src', e.target.result);
		    		
			 		if(imgWidth != $dataWidth || imgHeight != $dataHeight){                  //가로 세로 사이즈 비교 후 반환
		                fn_showCustomAlert('지정된 크기와 맞지 않습니다.(너비: '+imgWidth + ' 높이: '+ imgHeight +') <br>업로드된 이미지(파일)는  삭제됩니다.'  );
						      
			      		//올린 이미지 삭제 처리
		              	$target.val('');		              	
		                //$('#preview').removeAttr("src"); //위에서 생성한 임시 img 태그 삭제
		                //일단 리턴하지말고 미리보기는 보여주자
		                //return; 
		            }
			 	}
		    } 
		    reader.readAsDataURL(file);
		});
	 	 
	});
	  
	
	
	function fn_save(){
		if($("#banner_title").val().trim() == "")
		{
			fn_showCustomAlert("배너 제목을 입력 하세요.");
			$("#banner_title").val("");
			$('#banner_title').focus();
			return false;
		}
		
		if(confirm("저장하시겠습니까?") ==true ){
			
			$("#banner_contents").val(Editor.getContent());
			//
			var form = document.bannerFrm;
			form.action = '${ctxt}/content/banner/saveBanner.do';
			form.submit();	
		}
	}
	
	function fn_delete(){
		
		if(confirm("삭제 하시겠습니까?") ==true ){
			
			$("#banner_contents").val(Editor.getContent());
			//
			var form = document.bannerFrm;
			form.action = '${ctxt}/content/banner/deleteBanner.do';
			form.submit();	
		}
	}
	function fn_back(){
		var form = document.bannerList;
		form.action = '${ctxt}/content/banner/bannerList.do';
		form.submit();	
	}
	

	function fn_delete_imgFile(atch_img_id) {

		if (confirm("이미지를 삭제하시겠습니까?")) {

			var params = {};
			params.atch_img_id = $('#atch_img_id').val();
			params.banner_seq = $('#banner_seq').val();
			$.ajax({
				url: "/content/banner/deleteFileInf.do",
				type: 'POST',
		        dataType: 'text',
				data     :  params,
				success  : function(data) {
				 	if(data == "ok"){ location.reload();}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					alert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.');
				}
			});

		}

	}
	
</script>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>배너 관리</h3>  
	<form name="bannerList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${banner.searchopt}" />
		<input type="hidden" name="searchword" value="${banner.searchword}" />
		<input type="hidden" name="page" value="${banner.page}" />
	</form>
	<form name="bannerFrm" method="post"  action="" enctype="multipart/form-data">	
		<input type="hidden" name="save_type" id="save_type" value="U" />
		<input type="hidden" name="banner_seq" id = "banner_seq" value="${data.banner_seq}" />
		<input type="hidden" name="atch_img_id" id = "atch_img_id" value="${data.atch_img_id}" />
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="*">  
		</colgroup>
		<tbody>
		
		<tr>
			<th>배너 제목</th>
			<td>
				<input type="text" name="banner_title" id="banner_title" value="${data.banner_title}" style="width:100%;ime-mode:active">
			</td>
		</tr>
		<tr>
			<th>사용여부</th>
				<td class="selectRow">
					 <select name="use_yn" class="select" title="조회조건 선택">
						<option value="Y" <c:if test="${data.use_yn == 'Y'}" >selected</c:if>>사용</option>
						<option value="N" <c:if test="${data.use_yn == 'N'}" >selected</c:if>>미사용</option>
					</select>
				</td>
			</tr>
		<tr>
			<th>내용</th>  
			<td>				
				<textarea name="banner_contents" id="banner_contents" rows="10" style="width:100%;ime-mode:active;display:none;">${data.banner_contents}</textarea>
				<div id="editor_frame"></div>
			</td>     
		</tr>         		
		<tr>
			<th>저장된 이미지 보기</th>
		    <td>
	    		<c:import url="/cmm/fms/selectImageFileInfs.do" charEncoding="utf-8">
					<c:param name="atch_img_id" value="${data.atch_img_id}" />
					<c:param name="menuGbn" value="banner" />
				</c:import>
			
			</td>
	    </tr>	
    	<tr>
			<th>이미지 미리보기</th>
		    <td>	    		
				<img src=""  id="imgPreView" >
			</td>
	    </tr>	
	    <tr> 
			<th>배너 이미지</th>
		  	<td>
				<table class="borderless">
					<tr class="borderless">
						<td>
						  배너 이미지는  너비 : 600px  높이 : 400px 이미지만 등록 가능합니다.  
						</td>
					</tr>
					<tr>
						<td>
						    <input id="egovComFileUploader"  type="file" name="file_0" title="파일첨부"  data-width="600" data-height="400" />
						</td>
					</tr>
		    			 <!--<tr>
				        <td style="border-bottom:0px;">
				        	<div id="egovComFileList"></div>
				        </td>
					</tr> -->
				</table>
		<!--  첨부파일 업로드 가능화일 설정 Start..-->
		<script type="text/javascript">
			/*  	var maxFileNum = document.getElementById('posblAtchFileNumber').value; // 첨부파일 갯수 		
				
				if (maxFileNum == null || maxFileNum == "") {
					maxFileNum = 1;
				}		
			 	var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
				multi_selector.addElement(document.getElementById('egovComFileUploader'));   */
		</script>
		<!-- 첨부파일 업로드 가능화일 설정 End.-->
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

	