<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 첨부파일 로드
		getFileList($("#year_file_group_etc").val(), '10');
		
		// 채용리스트 로드
		/* var etcPage =$('#etcPage').val();
		if(etcPage == '' ){
			etcPage=1;
		} */
		var etcPage = 1; 
		
		
		fn_etcSearch(etcPage);
		
	});
	
	
	//첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.etcFileForm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	//첨부파일등록 팝업 호출
	function fn_egov_file_popup10() {
		var reqManageVo=document.etcFileForm;
		var file_group = reqManageVo.year_file_group_etc.value;
		var file_gb = "10";

		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');

  	}
	
	//첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	function fileContainerRefresh() {
	
		getFileList($("#year_file_group_etc").val(), '10');
		getFileList($("#year_file_group_article").val(), '9');
		getFileList($("#year_file_group_employ").val(), '7');
		getFileList($("#year_file_group_ip").val(), '8');
	}
	
	
	
	
	// 지적재산권 리스트
	function fn_etcSearch(etcPage){

		cuurPage= etcPage;
		var proj_year_id_etc = $("#proj_year_id_etc").val();

		var params = {};
			params.page    = cuurPage;   
			params.proj_year_id = proj_year_id_etc;
			
	  	$('#etcPage').val(cuurPage);
		  	
	$.ajax({
	    url: '${ctxt}/follow/year/etc/readYearEtcList.do',
	    data: params,
	    type: 'POST',
	    dataType: 'json',
	    success: function(result) {
	    	var html ='';
	    	pagetotalCnt = 0;
	    	if(!isEmpty(result)){
				if(!isEmpty(result.etcList)){
					
					$("#etcCnt").text(result.etcTotal);
					
					$("#etcDataList").children().remove();
					
					var start_num = Number(result.etcTotal) - ((cuurPage -1) *5)
					pagetotalCnt =Number(result.etcPageTotal);	
					
					$.each(result.etcList, function(idx, item){
						html += '<tr name="listTr">';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="etcChkObj" title="선택하기'+idx+'" value='+item.year_etc_id+'>';		
							html += '<td class="text_c"><a href="javascript:fn_etcDtl(\''+item.year_etc_id+'\');">'+((isEmpty(item.year_etc_nm)) ? '' : item.year_etc_nm) +'</a></td>';
							html += '<td class="text_c">'+((isEmpty(item.year_etc_gb)) ? '' : item.year_etc_gb)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.year_etc_cn)) ? '' : item.year_etc_cn)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.year_etc_info)) ? '' : item.year_etc_info)+'</td>';												
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택
					//페이징처리
				    $('#etcPaging').paging({
				    	
						 current:cuurPage
						,max:pagetotalCnt
						,length:5
						,onclick:function(e,page){
							cuurPage=page;
							fn_etcSearch(cuurPage);
						}
					});
					
				 	$('#etcDataList').html(html);
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#etcPaging').children().remove();
					
					$("#etcDataList").html('<tr><td colspan="4" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$('#etcPaging').children().remove();
				
				$("#etcDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
	    },
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    }
	});
	}
	
	
	
	// 지적재산권 상세
	function fn_etcDtl(seq){
		
		var year_etc_id = seq;
		
		var params = {};
		params.year_etc_id = year_etc_id;
		
		
		$.ajax({
	        url: '${ctxt}/follow/year/etc/yearEtcDetail.do',
	        data: params,
	        type: 'POST', 
	        dataType: 'json',	 
			success: function(result){		
				
				$("#year_etc_id").val(result.data.year_etc_id);
				$("#year_etc_nm").val(result.data.year_etc_nm);
				$("#year_etc_cn").val(result.data.year_etc_cn);
				$("#year_etc_gb").val(result.data.year_etc_gb);
				$("#year_etc_info").val(result.data.year_etc_info);
				
				fn_etcSearch(1);
			},
			error: function(request,status,error) {
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	  	  });
		 
	  	  
	}
	
	
	// 기타 저장
	function fn_saveEtc(){
		
		// 성과명칭 validation
		if($("#year_etc_nm").val() == "" || $("#year_etc_nm").val() == null){
			fn_showCustomAlert("성과명칭을 입력 하세요.");
			$("#year_etc_nm").val("");
			$('#year_etc_nm').focus();
			return false;
		}
		
		// 구분 validation
		if($("#year_etc_gb").val() == "" || $("#year_etc_gb").val() == null){
			fn_showCustomAlert("구분을 입력 하세요.");
			$("#year_etc_gb").val("");
			$('#year_etc_gb').focus();
			return false;
		}
		
		// 성과내용 validation
		if($("#year_etc_cn").val() == "" || $("#year_etc_cn").val() == null){
			fn_showCustomAlert("성과내용을 입력 하세요.");
			$("#year_etc_cn").val("");
			$('#year_etc_cn').focus();
			return false;
		}
		
		
		var item = serializeFormToJSON($("form[name='etcFrm']"));
		var proj_year_id_etc = $("#proj_year_id_etc").val();

		var requestData = {
        		mainItem : item,
        		proj_year_id_etc : proj_year_id_etc
            };
		
		
	    $.ajax({
		        url: '${ctxt}/follow/year/etc/insertYearEtc.do',
		        data: JSON.stringify(requestData),
		        contentType: 'application/json',
		        type: 'POST', 
		        dataType: 'json',	      
				success: function(result){			
					
					if(result.sMessage == "Y"){
						fn_showCustomAlert("저장이 완료되었습니다.");
						fn_etcSearch(1);
						fn_clearEtc();
						
					}else if(result.sMessage == "N"){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
					}else if(result.sMessage == "F"){
						fn_showCustomAlert("과제키가 존재하지 않습니다.");
					} 
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
	  	  });
	}
	
	
	// 초기화
	function fn_clearEtc(){
		$("#etcFrm").find('input[type="text"]').val(''); 
		$("#etcFrm").find('textarea').val(''); 
		$("#year_etc_id").val(''); 
	}
	
	
	// 삭제 버튼 
	function fn_delEtc(){
	    var etcVal = [];
	    $('input[name="etcChkObj"]:checked').each(function() {
	    	etcVal.push($(this) ? $(this).val() : '');
	    });
	    $('input[name="year_etc_ids"]').val('');
        $('input[name="year_etc_ids"]').val(etcVal);
	    
        var sendData = $("form[name='etcsForm']").serialize();
        
		if(confirm("삭제하시겠습니까?") ==true ){
	    		
	        $.ajax({
	            url: '${ctxt}/follow/year/etc/deleteYearEtc.do',
	            type: 'POST',
	            data: sendData,
	            type: 'POST', 
	            dataType: 'json',
	            success: function(result) {
	            	if(result.result != 0){
						fn_showCustomAlert("삭제가 완료되었습니다.");
						fn_etcSearch(1);
					}else if(result.result == 0){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
					}else if(result.sMessage == "F"){
						fn_showCustomAlert("지적재산권 키가 존재하지 않습니다.");
					} 
	            },
	            error: function(error) {
	                // 실패 시 처리할 코드
	                console.log('삭제 실패', error);
	            }
	        });
	    }
	}
	
</script>



 <div id="container">
	<div id="divRefreshArea">
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>기타 성과 등록</h3>
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;기타 성과 등록</h4>
			<div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_clearEtc();" class="btn btn-secondary">초기화</a>				  				
				<a href="javascript:void(0);" onclick="javascript:fn_saveEtc();" class="btn btn-secondary">저장</a>
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>						  		
			</div>		
		</div>
		
		<!--게시판-->
		<div class="etc">
			<form id="etcFrm" name="etcFrm" method="post"  action="">
		
				<table id="etcTable" class="table_h" cellpadding="0" cellspacing="0" border="0" >
	
					<caption>지적재산권정보</caption>
					<colgroup>
						<col style="width: 20%;">		
						<col style="width: 20%;">				
						<col style="width: 30%;">								
						<col style="width: 30%;">				
					</colgroup>
					<thead>
						<tr>
							<th scope='col'><font color="red" class="last-child">*</font>성과명칭</th>
							<th scope='col'><font color="red" class="last-child">*</font>구분</th>
							<th scope='col'><font color="red" class="last-child">*</font>성과내용</th>											
							<th scope='col'>비고</th>
						</tr>
					</thead>
					<tbody>
						<tr>		
							<td>
								<input type="hidden" id="year_etc_id" name="year_etc_id" value=""/>
								<input type="text" id="year_etc_nm" name = "year_etc_nm" value=""/>
							</td>
							<td>
								<input type="text" id="year_etc_gb" name = "year_etc_gb" value=""/>
							</td>
							<td>
								<textarea id="year_etc_cn" name="year_etc_cn" rows="3" style="width:100%;ime-mode:active;"></textarea>
							</td>
							<td>
								<textarea id="year_etc_info" name="year_etc_info" rows="3" style="width:100%;ime-mode:active;"></textarea>
							</td>
						</tr>
					</tbody>
	
				</table>
			</form>
		</div>


		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;첨부파일 등록</h4>
		</div>
		<div class="attch">
			<table class="table_v">  
				<colgroup>
					<col width="20%">
					<col width="20%">
					<col width="30%">  
					<col width="30%">    
				</colgroup>
				<tbody>
					<tr> 
					<th>기타 첨부파일</th>
				  	<td colspan="3">
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup10();" class="btn btn-secondary">파일 올리기</a>
						 <div id="fileContainer10" class="file-container"></div>
				    </td>
				</tr>
				</tbody>
			</table>
		</div>


		<div class="titles" style="margin-top: 40px;">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;기타 성과 정보</h4>
			<div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_delEtc();" class="btn btn-secondary">삭제</a>				  		
			</div>		
		</div>
		<!--게시판-->
		<div class="etc">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>지적재산권</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: *;">		
					<col style="width: 30%;">				
<%-- 					<col style="width: 25%;">								 --%>
<%-- 					<col style="width: 30%;">				 --%>
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>
						<th scope='col'>번호</th>
						<th scope='col'>성과명칭</th>
						<th scope='col'>구분</th>
<!-- 						<th scope='col'>성과내용</th>											 -->
<!-- 						<th scope='col'>비고</th> -->
					</tr>
				</thead>
				<tbody id="etcDataList">
				</tbody>

			</table>
				<div id="etcPaging" class="paginate"></div>
		</div>
	</div>
</div>



<form name ="etcsForm" id="etcsForm" method="post" action="">
	<input type="hidden" id="year_etc_ids" name="year_etc_ids"/>
</form>

<form name ="etcFileForm" id="etcFileForm" method="post" action="">
	<input type="hidden" id = "year_file_group_etc" name="year_file_group_etc" value="${data.year_file_group}" />
</form>

<input type="hidden" id = "etcPage" name="etcPage" value="" />
<input type="hidden" id = "proj_year_id_etc" name="proj_year_id_etc" value="${data.proj_year_id}" />




