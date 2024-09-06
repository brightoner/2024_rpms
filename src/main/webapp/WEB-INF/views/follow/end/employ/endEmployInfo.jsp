<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 첨부파일 로드
		getFileList($("#end_file_group_employ").val(), '12');
		
		// 채용리스트 로드
		/* var employPage =$('#employPage').val();
		if(employPage == '' ){
			employPage=1;
		} */
		var employPage=1;
		
		fn_employSearch(employPage);
		
	});
	
	
	//첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.employFileForm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	//첨부파일등록 팝업 호출
	function fn_egov_file_popup12() {
		var reqManageVo=document.employFileForm;
		var file_group = reqManageVo.end_file_group_employ.value;
		var file_gb = "12";

		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');

  	}
	
	
	// 채용인원 리스트
	function fn_employSearch(employPage){

		cuurPage= employPage;
		var proj_end_id_employ = $("#proj_end_id_employ").val();

		var params = {};
			params.page    = cuurPage;   
			params.proj_end_id = proj_end_id_employ;
			
	  	$('#employPage').val(cuurPage);
		  	
	$.ajax({
	    url: '${ctxt}/follow/end/employ/readEndEmployList.do',
	    data: params,
	    type: 'POST',
	    dataType: 'json',
	    success: function(result) {
	    	var html ='';
	    	pagetotalCnt = 0;
	    	if(!isEmpty(result)){
				if(!isEmpty(result.employList)){
					
					$("#empCnt").text(result.employTotal);
					
					$("#employDataList").children().remove();
					
					var start_num = Number(result.employTotal) - ((cuurPage -1) *5)
					pagetotalCnt =Number(result.employPageTotal);	
					
					$.each(result.employList, function(idx, item){
						html += '<tr name="listTr">';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="chkObj" title="선택하기'+idx+'" value='+item.end_employ_id+'>';		
							html += '<td class="text_c"><a href="javascript:fn_employDtl(\''+item.end_employ_id+'\');">'+((isEmpty(item.resp_nm)) ? '' : item.resp_nm) +'</a></td>';
							html += '<td class="text_c">'+((isEmpty(item.end_employ_dt)) ? '' : item.end_employ_dt)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.end_employ_gb)) ? '' : item.end_employ_gb)+'</td>';												
							html += '<td class="text_c">'+((isEmpty(item.end_employ_youth_gb)) ? '' : item.end_employ_youth_gb)+'</td>';												
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택
					//페이징처리
				    $('#employPaging').paging({
				    	
						 current:cuurPage
						,max:pagetotalCnt
						,length:5
						,onclick:function(e,page){
							cuurPage=page;
							fn_employSearch(cuurPage);
						}
					});
					
				 	$('#employDataList').html(html);
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#employPaging').children().remove();
					
					$("#employDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$("#total_cash").val(0);
				
				$('#employPaging').children().remove();
				
				$("#employDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
	    },
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    }
	});
	}
	
	
	
	// 체용인원 상세
	function fn_employDtl(seq){
		
		var end_employ_id = seq;
		
		var params = {};
		params.end_employ_id = end_employ_id;
		
		
		$.ajax({
	        url: '${ctxt}/follow/end/employ/endEmployDetail.do',
	        data: params,
	        type: 'POST', 
	        dataType: 'json',	 
			success: function(result){		
				
				$("#end_employ_cd").val(result.data.end_employ_cd);
				$("#resp_nm_employ").val(result.data.resp_nm);
				$("#end_employ_dt").val(result.data.end_employ_dt);
				$("#end_employ_info").val(result.data.end_employ_info);
				$('[name=end_employ_gb]').val(result.data.end_employ_gb);
				$('[name=end_employ_youth_gb]').val(result.data.end_employ_youth_gb);   
				
				fn_employSearch(1);
			},
			error: function(request,status,error) {
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	  	  });
		 
	  	  
	}
	
	
	// 채용인원 선택 바인딩
	// 참여연구원 팝업
	function fn_respSearch(){
		
		$("#id_gbn1").val("end_employ_cd");
		$("#id_gbn2").val("resp_nm_employ");

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/respMng/respMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}
	
	
	// 채용정보 저장
	function fn_saveEmploy(){
		
		// 이름 validation
		if($("#resp_nm_employ").val() == "" || $("#resp_nm_employ").val() == null){
			fn_showCustomAlert("채용자 찾기버튼을 이용해서 채용자를 선택해 하세요.");
			$('#resp_nm_employ').focus();
			return false;
		}
		
		// 채용일자 validation
		if($("#end_employ_dt").val() == "" || $("#end_employ_dt").val() == null){
			fn_showCustomAlert("채용일을 입력 하세요.");
			$('#end_employ_dt').focus();
			return false;
		}
		
		// 고용구분 validation
		if($('[name=end_employ_gb] :selected').val() == "" || $('[name=end_employ_gb] :selected').val() == null){
			fn_showCustomAlert("고용구분을 선택 하세요.");
			$('#end_employ_gb').focus();
			return false;
		}
		
		// 청년채용구분 validation
		if($('[name=end_employ_youth_gb] :selected').val() == "" || $('[name=end_employ_youth_gb] :selected').val() == null){
			fn_showCustomAlert("청년채용구분 선택 하세요.");
			$('#end_employ_youth_gb').focus();
			return false;
		}
		
		
		var item = serializeFormToJSON($("form[name='employFrm']"));
		var proj_end_id_employ = $("#proj_end_id_employ").val()

		var requestData = {
        		mainItem : item,
        		proj_end_id_employ : proj_end_id_employ
            };
		
	    $.ajax({
		        url: '${ctxt}/follow/end/employ/insertEndEmploy.do',
		        data: JSON.stringify(requestData),
		        contentType: 'application/json',
		        type: 'POST', 
		        dataType: 'json',	      
				success: function(result){			
					
					if(result.sMessage == "Y"){
						fn_showCustomAlert("저장이 완료되었습니다.");
						fn_employSearch(1);
						fn_clearEmploy();
					}else if(result.sMessage == "N"){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
					}else if(result.sMessage == "F"){
						fn_showCustomAlert("과제 키가 존재하지 않습니다.");
					} 
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
	  	  });
	}
	
	
	// 초기화
	function fn_clearEmploy(){
		document.getElementById('employFrm').reset();
		$("#end_employ_cd").val('');
	}
	
	// 삭제 버튼 
	function fn_delEmploy(){
	    var selectVal = [];
	    $('input[name="chkObj"]:checked').each(function() {
	    	selectVal.push($(this) ? $(this).val() : '');
	    });
	    $('input[name="end_employ_ids"]').val('');
        $('input[name="end_employ_ids"]').val(selectVal);
	    
        var sendData = $("form[name='idsForm']").serialize();
        
		if(confirm("삭제하시겠습니까?") ==true ){
	    		
	        $.ajax({
	            url: '${ctxt}/follow/end/employ/deleteEndEmploy.do',
	            type: 'POST',
	            data: sendData,
	            type: 'POST', 
	            dataType: 'json',
	            success: function(result) {
	            	if(result.result != 0){
						fn_showCustomAlert("삭제가 완료되었습니다.");
						fn_employSearch(1);
					}else if(result.result == 0){
						fn_showCustomAlert("삭제 처리가 되지 않았습니다.");
				
					}else if(result.sMessage == "F"){
						fn_showCustomAlert("채용자 키가 존재하지 않습니다.");
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
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>채용 성과 등록</h3>
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;채용 성과 등록</h4>
			<div class="btn_wrap" >			
				<a href="javascript:void(0);" onclick="javascript:fn_clearEmploy();" class="btn btn-secondary">초기화</a>
				<a href="javascript:void(0);" onclick="javascript:fn_saveEmploy();" class="btn btn-secondary">저장</a>	
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>				
		</div>		
	</div>

	
		<!--게시판-->
		<div class="employ">
			<form id="employFrm" name="employFrm" method="post"  action="">
				
		
				<table id="employTable" class="table_h" cellpadding="0" cellspacing="0" border="0" >
	
					<caption>채용정보</caption>
					<colgroup>
						<col style="width: 15%;">		
						<col style="width: 15%;">								
						<col style="width: 15%;">				
						<col style="width: 15%;">				
						<col style="width: 40%;">				
					</colgroup>
					<thead>
						<tr>
							<th scope='col'><font color="red" class="last-child">*</font>이름</th>
							<th scope='col'><font color="red" class="last-child">*</font>채용일자</th>											
							<th scope='col'><font color="red" class="last-child">*</font>고용구분</th>
							<th scope='col'><font color="red" class="last-child">*</font>청년채용구분</th>
							<th scope='col'>비고</th>
						</tr>
					</thead>
					<tbody>
						<tr>		
							<td>  
								<input type="hidden" id="end_employ_cd" name="end_employ_cd" value="">
								<input type="text" id="resp_nm_employ" name = "resp_nm_employ" value="" class="text_r form-control" style="width:55%; ime-mode:active; display: inline-block;"  readonly="readonly" />
								<a href="javascript:void(0);" onclick="javascript:fn_respSearch();" class="btn btn-secondary" style="display: inline-block;">채용자 찾기</a>	  
							</td>
							<td>
								<input type="date" id="end_employ_dt" name="end_employ_dt" value="" max="9999-12-31" min="1111-01-01">	
							</td>
							<td>
								<div class="custom-select selectRow">
									<select id="end_employ_gb" name="end_employ_gb" class="select">
										<option value="">선택하세요.</option>
									    <c:forEach var="employGbList" items="${employGbList}">
									        <option value="${employGbList.cd_nm}">${employGbList.cd_nm}</option>
									    </c:forEach>
									</select>
								</div>
							</td>
							<td>
								<div class="custom-select selectRow">
									<select id="end_employ_youth_gb" name="end_employ_youth_gb" class="select">
									    <option value="">선택하세요.</option>
									    <c:forEach var="employYouthGbList" items="${employYouthGbList}">
									        <option value="${employYouthGbList.cd_nm}">${employYouthGbList.cd_nm}</option>
									    </c:forEach>
									</select>
								</div>
							</td>
							<td>
								<textarea id="end_employ_info" name="end_employ_info" rows="3" style="width:100%;ime-mode:active;"></textarea>
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
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup12();" class="btn btn-secondary">파일 올리기</a>
						 <div id="fileContainer12" class="file-container"></div>
				    </td>
				</tr>
				</tbody>
			</table>
		</div>

		<div class="titles" style="margin-top: 40px;">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;채용 성과 정보</h4>
			<div class="btn_wrap" >
				<a href="javascript:void(0);" onclick="javascript:fn_delEmploy();" class="btn btn-secondary">삭제</a>
			</div>			
		</div>		
		
		<!--게시판-->
		<div class="employ">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>채용</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: 20%;">		
					<col style="width: 20%;">								
					<col style="width: 20%;">				
					<col style="width: 20%;">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>
						<th scope='col'>번호</th>
						<th scope='col'>이름</th>
						<th scope='col'>채용일자</th>											
						<th scope='col'>고용구분</th>
						<th scope='col'>청년채용구분</th>
					</tr>
				</thead>
				<tbody id="employDataList">
				</tbody>

			</table>
			<div id="employPaging" class="paginate"></div>
		</div>
	</div>
</div>


<!-- 채용인원 선택 관련 str -->
<form name ="orgPopForm" id="orgPopForm" method="post" action="">
	<input type="hidden" id="id_gbn1" name="id_gbn1"/>
	<input type="hidden" id="id_gbn2" name="id_gbn2"/>
	<input type="hidden" id="id_gbn3" name="id_gbn3"/>
	<input type="hidden" id="id_gbn4" name="id_gbn4"/>
	<input type="hidden" id="id_gbn5" name="id_gbn5"/>
	<input type="hidden" id="id_gbn6" name="id_gbn6"/>
	<input type="hidden" id="id_gbn7" name="id_gbn7"/>
</form>

<form name ="idsForm" id="idsForm" method="post" action="">
	<input type="hidden" id="end_employ_ids" name="end_employ_ids"/>
</form>

<form name ="employFileForm" id="employFileForm" method="post" action="">
	<input type="hidden" id = "end_file_group_employ" name="end_file_group_employ" value="${data.end_file_group}" />
</form>

<input type="hidden" id = "employPage" name="employPage" value="" />
<input type="hidden" id = "proj_end_id_employ" name="proj_end_id_employ" value="${data.proj_end_id}" />




