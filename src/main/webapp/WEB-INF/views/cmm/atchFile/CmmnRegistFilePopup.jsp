<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="<c:url value='/resources/js/cmmAtchFile.js'/>"></script>	<!-- 첨부파일관련 - ljk -->

<style type="text/css">

html, body {
	font-size: 14px;
	font-family: 'Noto Sans KR';
	color: #333;
}

form#atchForm {
    display: grid;
    justify-content: center;
    grid-template-rows: 330px auto 0;
}

.popTitle {
    background-color: transparent;
    font-size: 1.4em;
    font-weight: 400;
}

.popTitle {
    background-color: transparent;
    font-size: 1.4em;
    font-weight: 500;
    border-bottom: 2px solid rgba(111, 19, 22, 0.2);
    padding: 0 0 10px;
    margin: 20px 0 10px;
    color: #000;
}

table {
	border-collapse : collapse;
	width: 650px;
}

tr {
	border-collapse : collapse;
}

td {
	padding: 5px 0;
}

.chumbuDiv {
    padding: 5px 0;
}

input[type="file"] {
    width: 450px;
    font-size: 14px;
    font-family: 'Noto Sans KR', sans-serif;
    height: 32px;
    line-height: 32px;
}

#uploadFile{
	margin-top: 5px;
	margin-left : 5px;
	padding-bottom:5px;
}

.board_btns {
    text-align: center;
}

#inputNdetailDivBox span {
    max-width: 450px;
    text-overflow: ellipsis;
    display: inline-block;
    white-space: nowrap;
    line-height: 30px;
    overflow: hidden;
    white-space-collapse: preserve;
    text-wrap: nowrap;
    text-align: start !important;
    padding: initial;
    border: initial;
    overflow: hidden !important;
}

#inputNdetailDivBox span.comment {
	color: #999;
    font-size: 10px;
    width: auto;
}

.btn {
  display: inline-block;
  font-weight: 500;
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
  line-height: 1.5;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  border: 1px solid transparent;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  border-radius: 0.25rem;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.btn-line {
    color: #314873;
    border: 1px solid #D6D8E2;
    background: #fff;
    -webkit-transition: all 0.2s;
    -moz-transition: all 0.2s;
    -ms-transition: all 0.2s;
    transition: all 0.2s;
    cursor: pointer;
    font-weight: 400;
    vertical-align: top;
    line-height: 1.5;
    font-size: 13px;
    border-radius: 0;
    margin-left: 2px;
}

.btn-line:hover, .btn-line:focus {
    background: #f1edf5;
    -webkit-transition: all 0.2s;
    -moz-transition: all 0.2s;
    -ms-transition: all 0.2s;
    transition: all 0.2s;
}

.btn-primary {
 color: var(--bs-primary);
    background-color: #fff;
    border-color: var(--bs-primary);
    margin-left: 5px;
    min-width: 60px;
    padding: 0.56rem 1rem;
}
   
.btn-primary:hover{
  color: #fff;
/*   background-color: #ff9813; */
  background-color: #233f87;
}

.float_r {
	float: right; 
}

.pa_0 {
	padding: 0;
}

 .file-input-wrapper {
            position: relative;
            display: inline-block;
        }

        .file-input {
            display: none;
        }

        .file-input-label {
            display: inline-block;
            background-color: #314873;
            color: white;
            padding: 10px 20px;
            font-size: 12px;
            cursor: pointer;
            border-radius: 5px;
        }

        .file-input-label:hover {
            background-color: #45a049;
        }

        .file-input-label:active {
            background-color: #3e8e41;
        }

        .file-name {
            margin-left: 10px;
            font-size: 12px;
        }
</style>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
 
});
	var curFileCount = 1;							// 현재 추가할 파일 수
	var lastCurFileCount = 0;						// 마지막 추가행

	// 화면 시작부터 첨부파일 등록 칸 생성
	window.addEventListener('load', (event) => {
		console.log(event);
		addFileInput() ;
		
		 
	});

	// 닫기버튼 클릭시 비동기적으로 파일 리스트 최신화
	window.addEventListener('beforeunload', (event) => {
		<c:if test="${!empty _callbacknm}">
		opener.${_callbacknm}();
		</c:if>
	});

	  

	// 첨부파일 추가시 행 추가
	function addFileInput(){

		// 첨부파일갯수제한 : 10개
		var existFileCount = ${atchFileInfo.size()};		// 존재하는 파일 개수
		var newFileCount = $(".chumbuDiv").length;			// 추가하는 파일 개수
		var fileCountSum = existFileCount + newFileCount;	// 존재하는 파일 개수 + 추가하는 파일 개수
		if(fileCountSum > 10){
			alert("첨부파일은 10개까지 등록할 수 있습니다.");
			return;
		}

		// 행추가
		var addChumbu = $(".uploadFile").val();
		if(addChumbu != null || addChumbu != ""){
			var newfile = "<div class='chumbuDiv'  id='chumbuDiv" + (curFileCount) + "'>" ;
			newfile += "<input type='file' name='uploadFile' class='file-input' id='fileInput"+(curFileCount)+"' onchange='chumbu();' accept='.pdf,.hwp,.hwpx,.doc,.docx,.xlsx,.xls,.jpg,.jpeg,.gif,.png,.zip'>" ;
			newfile += "<label for='fileInput"+(curFileCount)+"' class='file-input-label'>파일 선택</label>" ;
			newfile += "<label for='fileInput"+(curFileCount)+"' class='file-name' id='fileName'>파일을 선택하세요...</label>" ;
			newfile += "<input type='button' class='btn btn-primary float_r' name='delFile' onclick=\"del('" + ( curFileCount ) + "');return false;\" value='삭제' />" ;
			newfile += "</div>";
			$(".filupp").append(newfile);
			lastCurFileCount = curFileCount;	// 마지막 파일등록 카운트
			++curFileCount;						// 행 추가시 현재파일 카운트 증가
		}
		  $('.file-input').on('change', function() {
		        var fileName = this.files.length > 0 ? this.files[0].name : '파일을 선택하세요...';
		        $(this).next('.file-input-label').next('.file-name').text(fileName);
		    });

	}

	// 첨부파일을 등록할 경우 파일 입력행 추가
	function chumbu(){
		addFileInput();
	}


	function del(curFileCount){
		if(curFileCount == lastCurFileCount){
			$("#chumbuDiv").val('');
		}else{
			$("#chumbuDiv" + curFileCount).remove();
		}
	}

	// 저장 버튼
	function fn_save(){

		// formData 관련
		var formData = new FormData();

		// fileGroup, fileGb 담기
		var fileGroup = $("#fileGroup").val();
		var fileGb = $("#fileGb").val();
		formData.append("fileGroup", fileGroup);
		formData.append("fileGb", fileGb);

		// 첨부파일 formData에 담기
		var file_len = $(".chumbuDiv").length;	// 신규 첨부 파일 개수
		for( var i = 0; i < file_len; i++ ){
			var files = $("input[name=uploadFile]")[i].files;
			var filename = $("input[name=uploadFile]:eq(" + i + ")").val();		// 파일이름
			if(!(filename == null || filename == "" || filename == undefined)){	// 첨부파일 이름이 있으면 (default 파일 추가 행은 이름이 없다.)
				//첨부파일 확장자 제한
				var ext = filename.substring(filename.lastIndexOf(".")+1,filename.length).toLowerCase();	//파일확장장
				if(ext != "pdf" && ext != "hwp" && ext != "hwpx" && ext != "doc" && ext != "docx" && ext != "xlsx" && ext != "xls" && ext != "jpg" && ext != "jpeg" && ext != "gif" && ext != "png" && ext != "zip"){
					alert("파일확장자를 확인해 주세요.");
	 				$("input[name=uploadFile]:eq(" + i + ")").val("");
					return false;
				}

				// 첨부파일 용량제한
			
				/* var fileSize = files[0].size;
				var maxSize = 2 * 1024 * 1024 * 10;// 20MB     
				if(fileSize > maxSize){       
					alert("첨부파일 사이즈는 20MB 이내로 등록 가능합니다. "); 	// 파일 용량제한 : 20MB
					$("input[name=uploadFile]:eq(" + i + ")").val("");     
					return false;   
				} */
				

				//첨부파일 저장
				formData.append("uploadFile", files[0]);	// 루프가 돌면서 초기화된 파일정보를 넣어 주어야한다.
			}
		}

		$.ajax({
			type: "POST",
			enctype: "multipart/form-data",
			url: "/atchFile/CmmnFileInsert.do",
			data : formData,
			async : false,
			processData: false,
			contentType: false,
			success: function (data) {
				location.reload();
				alert("파일업로드 성공");
				//window.close();
			},
			error: function (e) {
				alert("서버오류로 지연되고 있습니다. 잠시 후 다시 시도해 주시기 바랍니다.");
				return false;
			}
		});
	}

	// 닫기 버튼
	function fn_close(){
		window.close();

	}

	$(document).ready(function () {

		 //불러온 파일 삭제(입력되어있던 첨부파일 삭제)
		 $("button[name='delete']").on("click", function(){

			 if (confirm("삭제하시겠습니까?")) {
				 var index = $(this).attr("id").substring(6);
				 var fileGroup_value = $("#fileGroup").val();
				 var fileId_value = $("#fileId"+index).val();

				//파일 삭제 클릭시 첨부파일에서 삭제하는 ajax
				$.ajax({
					url : '/atchFile/CmmnFileDelete.do',
					data : {fileGroup:fileGroup_value, fileId:fileId_value},   //전송파라미터
					type : 'POST',
					dataType : 'json',
					success : function() {
						location.reload();
						alert("파일이 삭제되었습니다.");
					},
					error : function() { // Ajax 전송 에러 발생시 실행
						alert('오류가 발생했습니다.\n관리자에게 문의 바랍니다.','e');
					}
				});
				$(this).parent().remove();
			}
		 });
	});

	// 원문파일 다운로드
	function fn_atchFileDownload(){
		var form=document.atchForm;
		var index = event.currentTarget.id.substring(8);
		var fileId_value = $("#fileId"+index).val();
		form.action = "${ctxt}/atchFile/CmmnFileDown.do" + "?fileId_value="+fileId_value;
	 	form.submit();
	}


</script>

<title>첨부파일 업로드</title> 
</head>
<body>
	<form id="atchForm"	name="atchForm" method="post" enctype="multipart/form-data" class="apply user_mode" accept-charset="UTF-8">
			<div class="box-blue-line clear relative">

			<!-- title -->
			<h3 class="popTitle" id="title_div"><span class="adminIcon"></span>첨부파일</h3>
			<!-- 입력 및 상세영역 -->
					<div id="inputNdetailDivBox" class="inputNdetail narrow">
						<table>
							<c:forEach items="${atchFileInfo}" var="atchFileInfo" begin="0" end="${atchFileInfo.size()}" step="1" varStatus="status">
								<tr>
									<td>
										<c:choose>
											<c:when test="${atchFileInfo eq null || atchFileInfo eq ''}">
												<span class="filupp-file-name js-value">등록된 파일이 없습니다.</span>
											</c:when>
											<c:otherwise>
												<span>${atchFileInfo.orgn_file_nm}</span>
												<input type="hidden" class="FILE_NM" name="FILE_NM" id="FILE_NM${status.index}" value="${atchFileInfo.orgn_file_nm}" readonly="readonly" style="border:none;">&nbsp;
												<span class="comment">[<fmt:formatNumber value="${atchFileInfo.file_size}" pattern="#,###"/>&nbsp;byte]</span>
												<input type="hidden" id="fileGroup" name="fileGroup" value="${atchFileInfo.file_group}">
												<input type="hidden" id="subFileId${status.index}" name="subFileId" value="${atchFileInfo.sub_file_id}">
												<input type="hidden" id="fileId${status.index}" name="fileId" value="${atchFileInfo.file_id}">
											</c:otherwise>
										</c:choose>
										<div style="float: right;">
											<button type="button" id="fileDown${status.index}" class="btn btn-primary" name="fileDown" onclick="fn_atchFileDownload();">다운</button>	<!-- 파일다운로드 -->
											<button type="button" id="delete${status.index}" class="btn btn-primary" name="delete">삭제</button> 									<!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="pa_0">
									<label for="attFile_custom-file-upload" class="filupp">
										<!-- 파일추가행이 들어가 자리 -->
 									</label>
								</td>
							</tr>
						</table>
					</div>
				
			</div>
			<!-- 입력 및 상세영역 END -->

			<!-- 저장, 닫기 버튼 -->
			<div class="board_btns">
				<button type="button" class="btn btn-primary" onclick="javascript:fn_save();" ><span></span>저장</button>
				<button type="button" class="btn btn-primary" onclick="javascript:fn_close();" ><span></span>닫기</button><br><br>
			</div>
			<!-- 저장, 닫기 버튼 END -->
	


		<input type="hidden" id="fileGroup" name="fileGroup" value="${fileGroup}">
		<input type="hidden" id="fileGb" name="fileGb" value="${fileGb}">  
	</form>
</body>

</html>



