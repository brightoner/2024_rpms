<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 파일 그룹키
		GetDateTMS();
		
		// 첨부파일 로드
		getFileList($("#file_group_sales").val(), '11');
		
		// 매출 로드
		var salesPage =$('#salesPage').val();
		if(salesPage == '' ){
			salesPage=1;
		}
		
		fn_salesSearch(salesPage);
		
	});
	
	
	// input박스 천단위 콤마
	function inputComma(event) {
        var value = event.target.value.replace(/[^0-9.]/g, '');
        
        var parts = value.split('.');
        var integerPart = parts[0];
        var decimalPart = parts.length > 1 ? '.' + parts[1] : '';

        const formattedIntegerPart = addComma(Number(integerPart));
        event.target.value = formattedIntegerPart + decimalPart;
    }
	
	
	// 파일 그룹키
	function GetDateTMS() {
	    var localTime = new Date();
		var year= localTime.getFullYear();
		var month= localTime.getMonth() +1;
	    var date = localTime.getDate();
	    var hours = localTime.getHours();
	    var minutes = localTime.getMinutes();
	    var seconds = localTime.getSeconds();
	    var milliseconds = localTime.getMilliseconds();
	    var totalTime = leadingZeros(year,4)+leadingZeros(month,2)+leadingZeros(date,2)+leadingZeros(hours,2)+leadingZeros(minutes,2)+leadingZeros(seconds,2)+leadingZeros(milliseconds,3);

	    $("#file_group_sales").val(totalTime);
	    
	}

	function leadingZeros(n, digits) {
	    var zero = '';
		n = n.toString();

		if (n.length < digits) {
	      for (var i = 0; i < digits - n.length; i++)
			 zero += '0';
		}
		  return zero + n;
	}
	
	//첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.salesFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	//첨부파일등록 팝업 호출
	function fn_egov_file_popup() {
		var reqManageVo=document.salesFrm;
		var file_group = reqManageVo.file_group_sales.value;
		var file_gb = "11";

		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');

  	}
	
	//첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	function fileContainerRefresh() {
		getFileList($("#file_group_sales").val(), '11');
	}
	
	
	// 채용인원 리스트
	function fn_salesSearch(salesPage){

		cuurPage= salesPage;

		var params = {};
			params.page    = cuurPage;  
			params.searchopt    = $('[name=searchopt] :selected').val();   
			params.searchword    = $('#searchword').val(); 
			
	  	$('#salesPage').val(cuurPage);
		  	
	$.ajax({
	    url: '${ctxt}/opsmng/salesMng/readSalesMngList.do',
	    data: params,
	    type: 'POST',
	    dataType: 'json',
	    success: function(result) {
	    	var html ='';
	    	pagetotalCnt = 0;
	    	if(!isEmpty(result)){
				if(!isEmpty(result.salesList)){
					$("#salesDataList").children().remove();
					
					var start_num = Number(result.salesTotal) - ((cuurPage -1) *5)
					pagetotalCnt =Number(result.salesPageTotal);	
					
					$.each(result.salesList, function(idx, item){
						html += '<tr name="listTr">';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="salesChkObj" title="선택하기'+idx+'" value='+item.sales_id+'>';		
							html += '<td class="text_c"><a href="javascript:fn_salesDtl(\''+item.sales_id+'\');">'+((isEmpty(item.sales_item_nm)) ? '' : item.sales_item_nm) +'</a></td>';
							html += '<td class="text_c">'+((isEmpty(item.sales_revenue)) ? '' : addComma(Number(item.sales_revenue)))+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.sales_client_nm)) ? '' : item.sales_client_nm)+'</td>';												
							html += '<td class="text_c">'+((isEmpty(item.sales_trade_dt)) ? '' : item.sales_trade_dt)+'</td>';												
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택
					//페이징처리
				    $('#paging').paging({
				    	
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
					$('#paging').children().remove();
					
					$("#salesDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$('#paging').children().remove();
				
				$("#salesDataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
	    },
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    }
	});
	}
	
	
	
	// 체용인원 상세
	function fn_salesDtl(seq){
		
		var sales_id = seq;
		
		var params = {};
		params.sales_id = sales_id;
		
		
		$.ajax({
	        url: '${ctxt}/opsmng/salesMng/salesMngDetail.do',
	        data: params,
	        type: 'POST', 
	        dataType: 'json',	 
			success: function(result){		 
				
				$("#sales_id").val(result.data.sales_id);
				$("#sales_item_nm").val(result.data.sales_item_nm);
				$("#sales_revenue").val(addComma(Number(result.data.sales_revenue)));
				$("#sales_client_nm").val(result.data.sales_client_nm);
				$("#sales_trade_dt").val(result.data.sales_trade_dt);
				$("#file_group_sales").val(result.data.file_group_sales);
				fn_salesSearch(1);
				$("#subTitle").text('매출 수정');
				$("#subNm").text("( 매출품목 : "+result.data.sales_item_nm+")");
				
				getFileList($("#file_group_sales").val(), '11'); 
			},
			error: function(request,status,error) {
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	  	  });
		 
	  	  
	}
	
	
	// 논문정보 저장
	function fn_saveSales(){
		
		
		// 품목 validation
		if($("#sales_item_nm").val() == "" || $("#sales_item_nm").val() == null){
			fn_showCustomAlert("품목을 입력 하세요.");
			$('#sales_item_nm').focus();
			return false;
		}
		
		// 매출액 validation
		/* if($("#sales_revenue").val() == "" || $("#sales_revenue").val() == null){
			fn_showCustomAlert("매출액을 입력 하세요.");
			$('#sales_revenue').focus();
			return false;
		} */
		
		// 거래처 validation
		/* if($("#sales_client_nm").val() == "" || $("#sales_client_nm").val() == null){
			fn_showCustomAlert("거래처를 입력 하세요.");
			$('#sales_client_nm').focus();
			return false;
		} */
		
		// 거래일 validation
		/* if($("#sales_trade_dt").val() == "" || $("#sales_trade_dt").val() == null){
			fn_showCustomAlert("거래일을 입력 하세요.");
			$('#sales_trade_dt').focus();
			return false;
		} */
		
		// 콤마 제거
		var sales_revenue = $("#sales_revenue").val().replace(/,/g, '');
		$("#sales_revenue").val(sales_revenue);

		var item = serializeFormToJSON($("form[name='salesFrm']"));

		var requestData = {
        		mainItem : item,
            };

	    $.ajax({
		        url: '${ctxt}/opsmng/salesMng/insertSalesMng.do',
		        data: JSON.stringify(requestData),
		        contentType: 'application/json',
		        type: 'POST', 
		        dataType: 'json',	      
				success: function(result){			
					
					if(result.sMessage == "Y"){
						fn_showCustomAlert("저장이 완료되었습니다.");
						fn_salesSearch(1);
						fn_clearSales();
						$('#fileContainer11').empty();
						$("#subTitle").text('매출 신규');
					}else if(result.sMessage == "N"){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
					}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
	  	  });
	}
	
	
	// 초기화
	function fn_clearSales(){
		document.getElementById('salesFrm').reset();  
		$("#sales_id").val('');
		$("#subNm").text('');
		$("#subTitle").text('매출 신규');
		$('#fileContainer11').empty();
		GetDateTMS();
	}
	
	// 삭제 버튼 
	function fn_delSales(){
	    var salesVal = [];
	    $('input[name="salesChkObj"]:checked').each(function() {
	    	salesVal.push($(this) ? $(this).val() : '');
	    });
	    $('input[name="sales_ids"]').val('');
        $('input[name="sales_ids"]').val(salesVal);
	    
        var sendData = $("form[name='salesForm']").serialize();
	    
        fn_showCustomConfirm("warning","삭제하시겠습니까?", function() {
            	$.ajax({
					url: '${ctxt}/opsmng/salesMng/deleteSalesMng.do',
					type: 'POST',
					data: sendData,
					type: 'POST', 
					dataType: 'json',
					success: function(result) {
						if(result.result != 0){
							fn_showCustomAlert("삭제가 완료되었습니다.");
							fn_salesSearch(1);
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
         });
	}
	
</script>



 <div id="container">
	<div id="divRefreshArea">
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>매출 관리</h3>  
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;<font id="subTitle">매출 신규</font><font id="subNm" color="red" size="2px;" style="margin-left: 20px;"></font></h4>
			<div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_clearSales();" class="btn btn-secondary">초기화</a>				  				
				<a href="javascript:void(0);" onclick="javascript:fn_saveSales();" class="btn btn-secondary">저장</a>				  		
			</div>		
		</div> 
		<!--게시판-->
		<div class="sales" style="margin-bottom: 70px;">
			<form id="salesFrm" name="salesFrm" method="post"  action="">
				
				<input type="hidden" id = "file_group_sales" name="file_group_sales" value="${data.file_group_sales}" />
				
				<table id="salesTable" class="table_v" cellpadding="0" cellspacing="0" border="0" >
	
					<caption>매출정보</caption>
					<colgroup>
						<col width="20%">
						<col width="30%">  
						<col width="20%">
						<col width="30%">  		
						</colgroup>
					<thead>
					</thead>
					<tbody>
						<tr>	
							<th scope='col'><font color="red" class="last-child">*</font>매출품목</th>	
							<td>
								<input type="hidden" id="sales_id" name="sales_id" value="">
								<input type="text" id="sales_item_nm" name = "sales_item_nm" value="" maxlength="80"/>
							</td>
							<th scope='col'>매출액 (원)</th>
							<td>
								<input type="text" name="sales_revenue" id="sales_revenue" value="" maxlength="18" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event);" style="width:100%;text-align:right;ime-mode:active">
							</td>
						</tr>
						<tr>
							<th scope='col'>거래처명</th>
							<td>
								<input type="text" id="sales_client_nm" name = "sales_client_nm" value="" maxlength="15"/>
							</td>
							<th scope='col'>거래일</th>		
							<td>
								<input type="date" id="sales_trade_dt" name="sales_trade_dt" value="" max="9999-12-31" min="1111-01-01" style="width: 100%">
							</td>
						</tr>
						<tr> 
							<th>첨부파일</th>
						  	<td colspan="3">
								 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup();" class="btn btn-secondary">파일 올리기</a>
								 <div id="fileContainer11" class="file-container"></div>
						    </td>
						</tr>
					</tbody>
	
				</table>
			</form>
		</div>

		
	
			<h4 style="margin-top: 20px;margin-bottom: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;매출 정보</h4>
		
		<!-- 검색 --> 
		<div class="searchAreaFlex">
			<div class="grid_box" style="grid-template-columns:100px 200px 70px;">
				<div class="custom-select selectRow">     
					<label for="auth" class="hidden-access"></label>
					<select name="searchopt">					
						<option value="item" <c:if test="${sales.searchopt == 'item'}">selected="selected"</c:if>>매출품목</option>
						<option value="name" <c:if test="${sales.searchopt == 'name'}">selected="selected"</c:if>>거래처명</option>														
					</select>
				</div>
				<input type="text" name="searchword" id="searchword"  value="${sales.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_salesSearch(1); return false;}" />
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_salesSearch(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="" />	
			</div>	
			 <div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_delSales();" class="btn btn-secondary">삭제</a>	
			 </div>		
		
		</div>
		
		<!--게시판-->
		<div class="sales">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>매출</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: 30%;">		
					<col style="width: 20%;">								
					<col style="width: 20%;">				
					<col style="width: 30%;">				
					<col style="width: 10%;">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>
						<th scope='col'>번호</th>
						<th scope='col'>매출품목</th>
						<th scope='col'>매출액 (원)</th>											
						<th scope='col'>거래처명</th>
						<th scope='col'>거래일</th>
					</tr>
				</thead>
				<tbody id="salesDataList">
				</tbody>

			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
		</div>
	</div>
</div>


<form name ="salesForm" id="salesForm" method="post" action="">
	<input type="hidden" id="sales_ids" name="sales_ids"/>
</form>

<input type="hidden" id = "salesPage" name="salesPage" value="${sales.page}" />



