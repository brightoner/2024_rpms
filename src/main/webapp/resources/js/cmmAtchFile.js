/**
 * 공통 첨부파일 관리시스템  
 */


var formStr = "<form id='atchFilePopupForm' name='atchFilePopupForm' method='post' enctype='multipart/form-data'>";
formStr += "<input name='file_group' type='hidden' />";		// 공통첨부파일 파일그룹키
formStr += "<input name='file_gb' type='hidden' />";				// 첨부파일 구분
formStr += "<input name='pageUrl' type='hidden' />";
formStr += "<input name='_callbacknm' type='hidden' />";
formStr += "</form>";

document.write(formStr);

/**
 * 첨부파일관리 
 * 
 *   fileKey		: 첨부파일 KEY
 *   contextName 	: 시스템 Context 명
 *   userId 		: 사용자 ID (EMPLYRKEY)
 *   savePath 		: 첨부파일 저장경로 지정
 *   pageTitle 		: 호출하는 화면 Title
 */
function popAttfileModify(file_group, file_gb, _callbacknm){

	var url = "/atchFile/CmmnRegistFilePopup.do"; 
	var frm = document.atchFilePopupForm;
	
	frm.file_group.value = file_group;
	frm.file_gb.value = file_gb;
	frm.pageUrl.value = location.href;
	frm._callbacknm.value = _callbacknm;
	 // 팝업창 호출 - 위치 조정
    var left = (screen.width / 2) - (850 / 2); // 중앙에 위치
    var top = (screen.height / 2) - (450 / 2); // 중앙에 위치
   // 팝업창 호출
    var atchPopupWindow = window.open("about:blank", "atchFilePopupForm", "width=850, height=450, scrollbars=yes, menubar=no, location=no, left=" + left + ", top=" + top);
	frm.action = url;
	frm.target = "atchFilePopupForm";
	frm.submit();
}



/**
 * (공통) 부모창 참부파일 리스트 최신화(비동기적)
 * @param fileLinkId
 * @returns
 */
function getFileList(fileGroup, fileGb) {
	
	$.ajax({
		url: '/atchFile/CmmnFileList.do',
		type: 'POST',
		data: {
			'fileGroup' : fileGroup
			,'fileGb' : fileGb
		},
		beforeSend: function () {
			if(fileGb == 1){					// 공고
				$('#fileContainer1').empty();
			}else if(fileGb == 2){				// 연구과제신청
				$('#fileContainer2').empty();
			}else if(fileGb == 3){				// 선정결과처리
				$('#fileContainer3').empty();
			}else if(fileGb == 4){				// 과제협약(수정사업계획서)
				$('#fileContainer4').empty();
			}else if(fileGb == 5){				// 연차과제
				$('#fileContainer5').empty();
			}else if(fileGb == 6){				// 연차평가
				$('#fileContainer6').empty();
			}else if(fileGb == 7){				// 연차사후_채용
				$('#fileContainer7').empty();
			}else if(fileGb == 8){				// 연차사후_지적재산권
				$('#fileContainer8').empty();	
			}else if(fileGb == 9){				// 연차사후_논문
				$('#fileContainer9').empty();
			}else if(fileGb == 10){				// 연차사후_기타
				$('#fileContainer10').empty();
			}else if(fileGb == 11){				// 매출
				$('#fileContainer11').empty();
			}else if(fileGb == 12){				// 종료사후_채용
				$('#fileContainer12').empty();
			}else if(fileGb == 13){				// 종료사후_지적재산권
				$('#fileContainer13').empty();	
			}else if(fileGb == 14){				// 종료사후_종료사후_논문
				$('#fileContainer14').empty();	
			}else if(fileGb == 15){				// 종료사후_기타
				$('#fileContainer15').empty();		
			}else{
				$('#fileContainer').empty();
			}
		},			
		success: function(data) {
			
			var fileLst = data.atchFileList;
			
			var fileItem = '';
			fileLst.forEach((item, idx, arr) => {
				console.log(item);
				fileItem = '<div>';
				fileItem += '<i class="fa fa-link"></i>&nbsp;';
				fileItem += 	'<a href="javascript:void(0);" style="vertical-align:middle;" onclick = "fn_fileDownload(\''+item.file_id+'\');" title="파일다운로드">'+item.orgn_file_nm+'</a>' ;				
				fileItem += '</div>';
				if(fileGb == 1){				// 공고
					$('#fileContainer1').append(fileItem);
				}else if(fileGb == 2){			// 연구과제신청
					$('#fileContainer2').append(fileItem);
				}else if(fileGb == 3){			// 선정결과처리
					$('#fileContainer3').append(fileItem);
				}else if(fileGb == 4){			// 과제협약(수정사업계획서)
					$('#fileContainer4').append(fileItem);
				}else if(fileGb == 5){			// 연차과제
					$('#fileContainer5').append(fileItem);
				}else if(fileGb == 6){			// 연차평가
					$('#fileContainer6').append(fileItem);
				}else if(fileGb == 7){			// 연차사후_채용
					$('#fileContainer7').append(fileItem);	
				}else if(fileGb == 8){			// 연차사후_지적재산권
					$('#fileContainer8').append(fileItem);
				}else if(fileGb == 9){			// 연차사후_논문
					$('#fileContainer9').append(fileItem);
				}else if(fileGb == 10){			// 연차사후_기타
					$('#fileContainer10').append(fileItem);
				}else if(fileGb == 11){			// 매출
					$('#fileContainer11').append(fileItem);
				}else if(fileGb == 12){			// 종료사후_채용
					$('#fileContainer12').append(fileItem);
				}else if(fileGb == 13){			// 종료사후_지적재산권
					$('#fileContainer13').append(fileItem);
				}else if(fileGb == 14){			// 종료사후_논문
					$('#fileContainer14').append(fileItem);
				}else if(fileGb == 15){			// 종료사후_기타
					$('#fileContainer15').append(fileItem);
				}else{
					$('#fileContainer').append(fileItem);
				}
					
			});
		},
		error: function(request,status,error) {
			alert("서버와의 통신에 실패 했습니다. 재 접속 후 다시 시도하여 주시기 바랍니다.");
			console.log(
				"code:" + request.status + "\n" + 
				"message:" + request.responseText + "\n" + 
				"error:" + error
			);
		}
	});
}



/**
 * (공통) 부모창 참부파일 리스트 최신화(비동기적)
 * @param fileLinkId
 * @returns
 */
function getFileVod(fileLinkId) {
	
	$.ajax({
		url: '/atchFile/CmmnFileList.do',
		type: 'POST',
		data: {
			'fileLinkId' : fileLinkId
		},
		beforeSend: function () {
			$('#fileContainer').empty();
		},			
		success: function(data) {
			var fileLst = data.atchFileList;
			
			var fileItem = '';
			fileLst.forEach((item, idx, arr) => {
				console.log(item);
				fileItem = '<div>';
				fileItem += '<i class="fa fa-link"></i>&nbsp;';
				fileItem += 	item.orgn_file_nm ;			
				fileItem += '</div>';
				
			//	fileItem += '<input type="text" class="FILE_NM" name="FILE_NM" id="FILE_NM'+idx+'" value="'+item.orgn_file_nm+'" pk_file_id="'+item.pk_file_id+'" readonly="readonly" style="border:none; outline: none;">&nbsp;['+item.file_size+'&nbsp;byte]';
			//	fileItem+= 	'</label><br/>';
				$('#fileContainer').append(fileItem)
				
			});
		},
		error: function(request,status,error) {
			alert("서버와의 통신에 실패 했습니다. 재 접속 후 다시 시도하여 주시기 바랍니다.");
			console.log(
				"code:" + request.status + "\n" + 
				"message:" + request.responseText + "\n" + 
				"error:" + error
			);
		}
	});
}
