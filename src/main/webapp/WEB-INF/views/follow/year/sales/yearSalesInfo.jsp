<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 매출관리 로드
		/* var mngPage =$('#mngPage').val();
		if(mngPage == '' ){
			mngPage=1;
		} */
		var mngPage =1;
		fn_mngSearch(mngPage);
		
		

		// 사후관리 매출 로드
		/* var salesPage =$('#salesPage').val();
		if(salesPage == '' ){
			salesPage=1;
		} */
		 var salesPage =1;
		fn_salesSearch(salesPage);
		
	});
	
	
	//첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.salesFileForm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	
	//############## 매출관리 str #################
	// 매출관리 리스트
	function fn_mngSearch(mngPage){

		var cuurPage= mngPage;
		var proj_year_id_sales = $("#proj_year_id_sales").val();

		var params = {};
			params.page    = cuurPage;   
			params.searchopt    = $('[name=searchopt] :selected').val();   
			params.searchword    = $('#searchword').val()
			params.proj_year_id = proj_year_id_sales;
			
	  	$('#mngPage').val(cuurPage);
		  	
		$.ajax({
		    url: '${ctxt}/follow/year/sales/readSalesMngList.do',
		    data: params,
		    type: 'POST',
		    dataType: 'json',
		    success: function(result) {
		    	var html ='';
		    	pagetotalCnt = 0;
		    	if(!isEmpty(result)){
					if(!isEmpty(result.mngList)){
						$("#mngDataList").children().remove();
						
						var start_num = Number(result.mngTotal) - ((cuurPage -1) *5)
						pagetotalCnt =Number(result.mngPageTotal);	
						
						$.each(result.mngList, function(idx, item){
							
							html += '<tr name="listTr">';
								html += '<td class="text_c">' + (start_num - idx) + '</td>';
								html += '<td class="text_c">'+'<input type="checkbox" name="mngChkObj" title="선택하기'+idx+'" value='+item.sales_id+'>';		
								html += '<td class="text_c">'+((isEmpty(item.sales_item_nm)) ? '' : item.sales_item_nm)+'</td>';												
								html += '<td class="text_c">'+((isEmpty(item.sales_revenue)) ? '' : addComma(Number(item.sales_revenue)))+'</td>';
								html += '<td class="text_c">'+((isEmpty(item.sales_client_nm)) ? '' : item.sales_client_nm)+'</td>';												
								html += '<td class="text_c">'+((isEmpty(item.sales_trade_dt)) ? '' : item.sales_trade_dt)+'</td>';												
							html += '</tr>';						
						});
						
						//트리코드 선택시 첫번째 행 선택
						//페이징처리
					    $('#mngPaging').paging({
					    	
							 current:cuurPage
							,max:pagetotalCnt
							,length:5
							,onclick:function(e,page){ 
								cuurPage=page;
								fn_mngSearch(cuurPage);
							}
						});
						
					 	$('#mngDataList').html(html);
					}else{
						/***************************************************************
						* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
						****************************************************************/
						$('#mngPaging').children().remove();
						
						$("#mngDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
					}
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					
					$('#mngPaging').children().remove();
					
					$("#mngDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
				}
		    },
		    error : function(){                              // Ajax 전송 에러 발생시 실행
		    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		    }
		});
	}
	
	
	function fn_mngSave(){
		
		 var mngVal = [];
		    $('input[name="mngChkObj"]:checked').each(function() {
		    	mngVal.push($(this) ? $(this).val() : '');
		    });
		    $('input[name="mng_ids"]').val('');
	        $('input[name="mng_ids"]').val(mngVal);
		    
	        var sendData = $("form[name='mngForm']").serialize();
	        
			if(confirm("저장하시겠습니까?") ==true ){
		    		
		        $.ajax({
		            url: '${ctxt}/follow/year/sales/insertYearSales.do',
		            type: 'POST',
		            data: sendData,
		            type: 'POST', 
		            dataType: 'json',
		            success: function(result) {
		            	if(result.result != 0){
		            		fn_showCustomAlert("저장이 완료되었습니다.");
							fn_mngSearch(1);
							fn_salesSearch(1);
						}else if(result.result == 0){
							fn_showCustomAlert("저장이 처리되지 않았습니다.");
					
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
	
	//############## 매출관리 end #################
	
	
	//############## 연차 사후관리 매출 str #################
	
	// 매출관리 리스트
	function fn_salesSearch(salesPage){

		var cuurPage= salesPage;
		var proj_year_id_sales = $("#proj_year_id_sales").val();

		var params = {};
			params.page    = cuurPage;   
			params.proj_year_id = proj_year_id_sales;
			
	  	$('#salesPage').val(cuurPage);
		  	
		$.ajax({
		    url: '${ctxt}/follow/year/sales/readYearSalesgList.do',
		    data: params,
		    type: 'POST',
		    dataType: 'json',
		    success: function(result) {
		    	var html ='';
		    	pagetotalCnt = 0;
		    	if(!isEmpty(result)){
					if(!isEmpty(result.salesList)){
						
						$("#slsCnt").text(result.salesTotal);
						
						$("#salesDataList").children().remove();
						
						var start_num = Number(result.salesTotal) - ((cuurPage -1) *5)
						pagetotalCnt =Number(result.salesPageTotal);	
						
						$.each(result.salesList, function(idx, item){
							html += '<tr name="saleslistTr">';
								html += '<td class="text_c">'+ (start_num - idx) + '</td>';
								html += '<td class="text_c">';
								html += '<input type="checkbox" name="salesChkObj" title="선택하기'+idx+'" value="'+item.year_sales_id+'">';
								html += '<input type = "hidden"  name = "stauts" value = "N" >';
								html += '<input type = "hidden"  name = "year_sales_id" value = "'+item.year_sales_id+'" >';
								html += '</td>';
								html += '<td class="text_c">'+((isEmpty(item.sales_item_nm)) ? '' : item.sales_item_nm)+'</td>';
								html += '<td class="text_c">'+((isEmpty(item.sales_revenue)) ? '' : addComma(Number(item.sales_revenue)))+'</td>';
								html += '<td class="text_c">'+((isEmpty(item.sales_client_nm)) ? '' : item.sales_client_nm)+'</td>';												
								html += '<td class="text_c">'+((isEmpty(item.sales_trade_dt)) ? '' : item.sales_trade_dt)+'</td>';												
								html += '<td class="text_c">'+'<input type = "text"  name = "sales_contribute" value="'+item.sales_contribute+'" maxlength="3"  oninput = "javascript:fn_changeStatus(this);" style="text-align:right;">'+'</td>';
								
							    html += '<td class="text_l">';
							    if (!isEmpty(item.file_ids) && !isEmpty(item.orgn_file_nms)) {
							    	var fileIds = item.file_ids ? item.file_ids.split(',') : [];
							        var fileNames = item.orgn_file_nms ? item.orgn_file_nms.split(',') : [];
							        for (var i = 0; i < fileIds.length; i++) {
							            html += '<a href="javascript:fn_fileDownload(\''+fileIds[i]+'\');">'+((isEmpty(fileNames[i])) ? '' : fileNames[i]) +'</a><br>';
							        } 
								html += '</td>';
							    }
							});
							html += '</tr>';						

						//트리코드 선택시 첫번째 행 선택
						//페이징처리
					    $('#salespaging').paging({
					    	
							 current:cuurPage
							,max:pagetotalCnt
							,length:5
							,onclick:function(e,page){ 
								cuurPage=page;
								fn_salesSearch(cuurPage);
							}
						});
						
					 	$('#salesDataList').html(html);

					}else{
						/***************************************************************
						* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
						****************************************************************/
						$('#salespaging').children().remove();
						
						$("#salesDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
					}
		   }else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$('#salespaging').children().remove();
				
				$("#salespaging").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
	    },
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    }
	});
	}
	
	
	// 기여율 update전 상태변경
	function fn_changeStatus(val){
		
		$(val).closest('tr').find('input[name="stauts"]').val('Y');
	}
	
	
	// 수정
	function fn_salesSave(){
		var salesList = [];			
		
        $('tr[name="saleslistTr"]').each(function() {
        	 if ($(this).find('input[name="stauts"]').val() === 'Y') {
	        	
        		var year_sales_id =  $(this).closest('tr').find('input[name="year_sales_id"]').val();
	            var sales_contribute = $(this).closest('tr').find('input[name="sales_contribute"]').val();

	            var item = {
	            		year_sales_id: year_sales_id,
	            		sales_contribute: sales_contribute
	            };
	            salesList.push(item);
        	}
        });
        
        var requestData = {
   			 salesList: salesList,
            };
        
		if(confirm("저장하시겠습니까?") ==true ){
			
		    $.ajax({
		        url: '${ctxt}/follow/year/sales/updateYearSales.do',
		        data: JSON.stringify(requestData),
		        type: 'POST', 
		        contentType: 'application/json', // Content-Type을 설정합니다.
		        dataType: 'json',
		        cache: false, 	
			    async : false,
				success: function(result){			
					
					if(result.result != 0){
						fn_showCustomAlert("수정이 완료되었습니다.");
						fn_salesSearch(1);
					}else{
						fn_showCustomAlert("수정에 실패 했습니다.");
					}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
		  	  });
		}
	}
	
	
	
	// 삭제
	function fn_salesDel(){
		
	    var salesVal = [];
	    $('input[name="salesChkObj"]:checked').each(function() {
	    	salesVal.push($(this) ? $(this).val() : '');
	    });
	    $('input[name="year_sales_ids"]').val('');
        $('input[name="year_sales_ids"]').val(salesVal);
	    
        var sendData = $("form[name='salesForm']").serialize();
        
		if(confirm("삭제하시겠습니까?") ==true ){
	    		
	        $.ajax({
	            url: '${ctxt}/follow/year/sales/deleteYearSales.do',
	            type: 'POST',
	            data: sendData,
	            type: 'POST', 
	            dataType: 'json',
	            success: function(result) {
	            	if(result.result != 0){
						fn_showCustomAlert("삭제가 완료되었습니다.");
						fn_salesSearch(1);
						fn_mngSearch(1);
					}else if(result.result == 0){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
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
	
	//############## 연차 사후관리 매출 end #################
	
</script>



 <div id="container">
	<div id="divRefreshArea">
		
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>매출 성과 등록</h3>
		
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;매출관리 통합 정보<font color="blue" size="2px;" style="margin-left: 50px;"> ※운영관리 > 매출관리 메뉴의 매출정보 입니다.</font></h4>
			
		</div>
		<!-- 검색 --> 
		
		<div class="searchAreaFlex" style="margin-top: 15px;">  
			<div class="grid_box" style="grid-template-columns:100px 200px 70px;">
				<div class="custom-select selectRow">     
					<label for="auth" class="hidden-access"></label>
					<select name="searchopt">					
						<option value="item" <c:if test="${mng.searchopt == 'item'}">selected="selected"</c:if>>매출품목</option>
						<option value="name" <c:if test="${mng.searchopt == 'name'}">selected="selected"</c:if>>거래처명</option>												
					</select>
				</div>
				<input type="text" name="searchword" id="searchword"  value="${mng.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_mngSearch(1); return false;}" />
				<a href="javascript:void(0);" id="mngSearch" class="btn btn-primary" onclick="fn_mngSearch(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="mngPage" name="mngPage" value="" />	
			</div>	
			 <div class="btn_wrap" >			
			  		<a href="javascript:void(0);" style = "width:100px;" onclick="javascript:fn_mngSave();" class="btn btn-secondary">매출성과 저장</a>	  
			  		<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>	
			 </div>		
		
		</div>
		
		<!--매출관리 -->
		<div class="mng">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>매출</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: *;">		
					<col style="width: 20%;">								
					<col style="width: 20%;">												
					<col style="width: 10%;">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>
						<th scope='col'>선택</th>
						<th scope='col'>매출품목</th>
						<th scope='col'>매출액 (원)</th>											
						<th scope='col'>거래처명</th>
						<th scope='col'>거래일</th>
					</tr>
				</thead>
				<tbody id="mngDataList">
				</tbody>

			</table>
			<!-- 페이징 처리 -->
			<div id="mngPaging" class="paginate"></div>
		</div>
		
		
		
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;매출 성과 정보</h4>
		</div>
		<div class="btn_wrap" >			
		  		<a href="javascript:void(0);" onclick="javascript:fn_salesSave();" class="btn btn-secondary">저장</a>	  
		  		<a href="javascript:void(0);" onclick="javascript:fn_salesDel();" class="btn btn-secondary">삭제</a>	  
			 </div>	
			 
		<!--연차사후관리 매출-->
		<div class="sales">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>매출</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: *;">		
					<col style="width: 10%;">								
					<col style="width: 15%;">				
					<col style="width: 10%;">				
					<col style="width: 10%;">				
					<col style="width: 20%;">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>
						<th scope='col'>선택</th>
						<th scope='col'>매출품목</th>
						<th scope='col'>매출액 (원)</th>											
						<th scope='col'>거래처명</th>
						<th scope='col'>거래일</th>
						<th scope='col'>기여율 (%)</th>
						<th scope='col'>첨부파일</th>
					</tr>
				</thead>
				<tbody id="salesDataList">
				</tbody>

			</table>
			<!-- 페이징 처리 -->
<!-- 			<div id="salespaging" class="paginate"></div> -->
		</div>
	</div>
</div>


<form name ="mngForm" id="mngForm" method="post" action="">
	<input type="hidden" id="mng_ids" name="mng_ids"/>
	<input type="hidden" id = "proj_year_id_mng" name="proj_year_id_mng" value="${data.proj_year_id}" />
</form>

<form name ="mngFileForm" id="mngFileForm" method="post" action="">
	<input type="hidden" id = "file_group_sales" name="file_group_sales" value="${data.file_group_sales}" />
</form>

<input type="hidden" id = "salesPage" name="salesPage" value="" />
<input type="hidden" id = "proj_year_id_sales" name="proj_year_id_sales" value="${data.proj_year_id}" />

<form name ="salesForm" id="salesForm" method="post" action="">
	<input type="hidden" id="year_sales_ids" name="year_sales_ids"/>
</form>

