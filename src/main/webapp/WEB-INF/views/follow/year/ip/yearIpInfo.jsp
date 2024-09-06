<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 첨부파일 로드
		getFileList($("#year_file_group_ip").val(), '8');
		
		// 채용리스트 로드
		/* var ipPage =$('#ipPage').val();
		if(ipPage == '' ){
			ipPage=1;
		} */
		var ipPage = 1;
		
		
		fn_ipSearch(ipPage);
		
	});
	
	
	//첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.ipFileForm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	//첨부파일등록 팝업 호출
	function fn_egov_file_popup8() {
		var reqManageVo=document.ipFileForm;
		var file_group = reqManageVo.year_file_group_ip.value;
		var file_gb = "8";

		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');

  	}
	
	
	// 지적재산권 리스트
	function fn_ipSearch(ipPage){

		cuurPage= ipPage;
		var proj_year_id_ip = $("#proj_year_id_ip").val();

		var params = {};
			params.page    = cuurPage;   
			params.proj_year_id = proj_year_id_ip;
			
	  	$('#ipPage').val(cuurPage);
		  	
	$.ajax({
	    url: '${ctxt}/follow/year/ip/readYearIpList.do',
	    data: params,
	    type: 'POST',
	    dataType: 'json',
	    success: function(result) {
	    	var html ='';
	    	pagetotalCnt = 0;
	    	if(!isEmpty(result)){
				if(!isEmpty(result.ipList)){
					
					$("#ipCnt").text(result.ipTotal);
					
					$("#ipDataList").children().remove();
					
					var start_num = Number(result.ipTotal) - ((cuurPage -1) *5)
					pagetotalCnt =Number(result.ipPageTotal);	
					
					$.each(result.ipList, function(idx, item){
						html += '<tr name="listTr">';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="ipChkObj" title="선택하기'+idx+'" value='+item.year_ip_id+'>';		
							html += '<td class="text_c"><a href="javascript:fn_ipDtl(\''+item.year_ip_id+'\');">'+((isEmpty(item.year_ip_nm)) ? '' : item.year_ip_nm) +'</a></td>';
							html += '<td class="text_c">'+((isEmpty(item.year_ip_dt)) ? '' : item.year_ip_dt)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.year_ip_no)) ? '' : item.year_ip_no)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.year_ip_gb)) ? '' : item.year_ip_gb)+'</td>';												
							html += '<td class="text_c">'+((isEmpty(item.year_ip_domestic_gb)) ? '' : item.year_ip_domestic_gb)+'</td>';												
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택
					//페이징처리
				    $('#ipPaging').paging({
				    	
						 current:cuurPage
						,max:pagetotalCnt
						,length:5
						,onclick:function(e,page){
							cuurPage=page;
							fn_ipSearch(cuurPage);
						}
					});
					
				 	$('#ipDataList').html(html);
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#ipPaging').children().remove();
					
					$("#ipDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$('#ipPaging').children().remove();
				
				$("#ipDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
	    },
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    }
	});
	}
	
	
	
	// 지적재산권 상세
	function fn_ipDtl(seq){
		
		var year_ip_id = seq;
		
		var params = {};
		params.year_ip_id = year_ip_id;
		
		
		$.ajax({
	        url: '${ctxt}/follow/year/ip/yearIpDetail.do',
	        data: params,
	        type: 'POST', 
	        dataType: 'json',	 
			success: function(result){		
				
				$("#year_ip_id").val(result.data.year_ip_id);
				$("#year_ip_no").val(result.data.year_ip_no);
				$("#year_ip_nm").val(result.data.year_ip_nm);
				$("#year_ip_dt").val(result.data.year_ip_dt);
				$('[name=year_ip_gb]').val(result.data.year_ip_gb);
				$('[name=year_ip_domestic_gb]').val(result.data.year_ip_domestic_gb);   
				
				fn_ipSearch(1);
			},
			error: function(request,status,error) {
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	  	  });
		 
	  	  
	}
	
	
	// 지적재산권정보 저장
	function fn_saveIp(){
		
		// 지적재산권 이름 validation
		if($("#year_ip_nm").val() == "" || $("#year_ip_nm").val() == null){
			fn_showCustomAlert("지적재산권 이름을 입력 하세요.");
			$("#year_ip_nm").val("");
			$('#year_ip_nm').focus();
			return false;
		}
		
		// 지적재산권 일자 validation
		if($("#year_ip_dt").val() == "" || $("#year_ip_dt").val() == null){
			fn_showCustomAlert("지적재산권 등록 일자를 입력 하세요.");
			$("#year_ip_dt").val("");
			$('#year_ip_dt').focus();
			return false;
		}
		
		// 지적재산권 번호 validation
		if($("#year_ip_no").val() == "" || $("#year_ip_no").val() == null){
			fn_showCustomAlert("지적재산권 번호를 입력 하세요.");
			$("#year_ip_no").val("");
			$('#year_ip_no').focus();
			return false;
		}
		
		// 출원/등록구분 validation
		if($('[name=year_ip_gb] :selected').val() == "" || $('[name=year_ip_gb] :selected').val() == null){
			fn_showCustomAlert("출원/등록여부를 선택 하세요.");
			$("#year_ip_gb").val("");
			$('#year_ip_gb').focus();
			return false;
		}
		
		// 국내외구분 validation
		if($('[name=year_ip_domestic_gb] :selected').val() == "" || $('[name=year_ip_domestic_gb] :selected').val() == null){
			fn_showCustomAlert("국내외구분을 선택 하세요.");
			$("#year_ip_domestic_gb").val("");
			$('#year_ip_domestic_gb').focus();
			return false;
		}
		
		var item = serializeFormToJSON($("form[name='ipFrm']"));
		var proj_year_id_ip = $("#proj_year_id_ip").val();

		var requestData = {
				mainItem : item,
        		proj_year_id_ip : proj_year_id_ip
            };
		
	    $.ajax({
		        url: '${ctxt}/follow/year/ip/insertYearIp.do',
		        data: JSON.stringify(requestData),
		        contentType: 'application/json',
		        type: 'POST', 
		        dataType: 'json',	      
				success: function(result){			
					
					if(result.sMessage == "Y"){
						fn_showCustomAlert("저장이 완료되었습니다.");
						fn_ipSearch(1);
						fn_clearIp();
						
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
	function fn_clearIp(){
		$("#ipFrm").find('input[type="text"]').val(''); 
		$("#year_ip_id").val(''); 
		$("#ipFrm").find('input[type="date"]').val(''); 
		$("#ipFrm").find('select').val('');
	}
	
	
	// 삭제 버튼 
	function fn_delIp(){
	    var ipVal = [];
	    $('input[name="ipChkObj"]:checked').each(function() {
	    	ipVal.push($(this) ? $(this).val() : '');
	    });
	    $('input[name="year_ip_ids"]').val('');
        $('input[name="year_ip_ids"]').val(ipVal);
	    
        var sendData = $("form[name='ipsForm']").serialize();
        
		if(confirm("삭제하시겠습니까?") ==true ){
	    		
	        $.ajax({
	            url: '${ctxt}/follow/year/ip/deleteYearIp.do',
	            type: 'POST',
	            data: sendData,
	            type: 'POST', 
	            dataType: 'json',
	            success: function(result) {
	            	if(result.result != 0){
						fn_showCustomAlert("삭제가 완료되었습니다.");
						fn_ipSearch(1);
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
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>지적재산권 성과 등록</h3>
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;지적재산권 성과 등록</h4>
			<div class="btn_wrap" >			
				<a href="javascript:void(0);" onclick="javascript:fn_clearIp();" class="btn btn-secondary">초기화</a>		
				<a href="javascript:void(0);" onclick="javascript:fn_saveIp();" class="btn btn-secondary">저장</a>
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>						
			</div>		
		</div>
		<!--게시판-->
		<div class="ip">
			<form id="ipFrm" name="ipFrm" method="post"  action="">
		
				<table id="ipTable" class="table_h" cellpadding="0" cellspacing="0" border="0" >
	
					<caption>지적재산권정보</caption>
					<colgroup>
						<col style="width: 20%;">		
						<col style="width: 20%;">								
						<col style="width: 20%;">				
						<col style="width: 20%;">				
						<col style="width: 20%;">				
					</colgroup>
					<thead>
						<tr>
							<th scope='col'><font color="red" class="last-child">*</font>지적재산권 이름</th>
							<th scope='col'><font color="red" class="last-child">*</font>지적재산권 등록 일자</th>											
							<th scope='col'><font color="red" class="last-child">*</font>지적재산권 번호</th>
							<th scope='col'><font color="red" class="last-child">*</font>출원/등록구분</th>
							<th scope='col'><font color="red" class="last-child">*</font>국내외구분</th>
						</tr>
					</thead>
					<tbody>
						<tr>		
							<td>
								<input type="hidden" id="year_ip_id" name = "year_ip_id" value=""/>
								<input type="text" id="year_ip_nm" name = "year_ip_nm" value=""/>
							</td>
							<td>
								<input type="date" id="year_ip_dt" name="year_ip_dt" value="" max="9999-12-31" min="1111-01-01">	
							</td>
							<td>
								<input type="text" id="year_ip_no" name = "year_ip_no" value=""/>
							</td>
							<td>
								<div class="custom-select selectRow">
									<select id="year_ip_gb" name="year_ip_gb" class="select">
									    <option value="">선택하세요.</option>
									    <c:forEach var="ipGbList" items="${ipGbList}">
									        <option value="${ipGbList.cd_nm}">${ipGbList.cd_nm}</option>
									    </c:forEach>
									</select>
								</div>
							</td>
							<td>
								<div class="custom-select selectRow">
									<select id="year_ip_domestic_gb" name="year_ip_domestic_gb" class="select">
									    <option value="">선택하세요.</option>
									    <c:forEach var="ipDmstGbList" items="${ipDmstGbList}">
									        <option value="${ipDmstGbList.cd_nm}">${ipDmstGbList.cd_nm}</option>
									    </c:forEach>
									</select>
								</div>
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
					<col width="30%">  
					<col width="20%">
					<col width="30%">    
				</colgroup>
				<tbody>
					<tr> 
					<th>첨부파일</th>
				  	<td colspan="3">
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup8();" class="btn btn-secondary">파일 올리기</a>
						 <div id="fileContainer8" class="file-container"></div>
				    </td>
				</tr>
				</tbody>
			</table>
		</div>


		<div class="titles" style="margin-top: 40px;">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;지적재산권 성과 정보</h4>
			<div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_delIp();" class="btn btn-secondary">삭제</a>						  		
			</div>		
		</div>
		
		
		<!--게시판-->
		<div class="ip">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>지적재산권</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: 15%;">		
					<col style="width: 15%;">								
					<col style="width: 20%;">				
					<col style="width: 20%;">				
					<col style="width: 20%;">	
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>
						<th scope='col'>번호</th>
						<th scope='col'>지적재산권 이름</th>
						<th scope='col'>지적재산권 등록 일자</th>											
						<th scope='col'>지적재산권 번호</th>
						<th scope='col'>출원/등록구분</th>
						<th scope='col'>국내외구분</th>
					</tr>
				</thead>
				<tbody id="ipDataList">
				</tbody>

			</table>
				<div id="ipPaging" class="paginate"></div>
		</div>
	</div>
</div>



<form name ="ipsForm" id="ipsForm" method="post" action="">
	<input type="hidden" id="year_ip_ids" name="year_ip_ids"/>
</form>

<form name ="ipFileForm" id="ipFileForm" method="post" action="">
	<input type="hidden" id = "year_file_group_ip" name="year_file_group_ip" value="${data.year_file_group}" />
</form>

<input type="hidden" id = "ipPage" name="ipPage" value="" />
<input type="hidden" id = "proj_year_id_ip" name="proj_year_id_ip" value="${data.proj_year_id}" />




