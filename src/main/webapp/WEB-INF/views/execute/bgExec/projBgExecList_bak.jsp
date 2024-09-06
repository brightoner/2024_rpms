<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"> 

var cuurPage;
var pagetotalCnt = 0;
/*  최상의 항목의 코드 단위는 100000 으로 정의한다  10만 단위 */

 

var proj_year_id_bg_exec = '${data.proj_year_id}';

$(function(){ 
	   var bgExecPage =$('#bgExecPage').val();
		if(bgExecPage == '' ){
			bgExecPage=1;
		}

		
	fn_bgExecSearch(bgExecPage);

	  
}); 
 

function fn_bgExecSearch(bgExecPage){

	cuurPage= bgExecPage;

	var params = {};
		params.page    = cuurPage;   
		params.proj_year_id = proj_year_id_budget;
		
  	$('#bgExecPage').val(cuurPage);
	  	
$.ajax({
    url: '${ctxt}/execute/bgExec/readProjBgExecList.do',
    data: params,
    type: 'POST',
    dataType: 'json',
    success: function(result) {
    	var html ='';
    	pagetotalCnt = 0;
    	if(!isEmpty(result)){
    		  if(!isEmpty(result.cashData)){
    			//  총현금
	   			  if(!isNaN(parseInt(uncomma(result.cashData.sum_cash))))  {
	   					$("#total_cash").val(addComma(result.cashData.sum_cash));
	   			  }else{
	   					$("#total_cash").val(0);
	   			  }
    		  }else{
    				$("#total_cash").val(0);
    		  }
    		// 이력 데이터 
			if(!isEmpty(result.bgExecList)){
				$("#bgExecDataList").children().remove();
				
				var start_num = Number(result.bgExecTotal) - ((cuurPage -1) *5)
				pagetotalCnt =Number(result.bgExecPageTotal);	
				
				$.each(result.bgExecList, function(idx, item){
					html += '<tr>';
						html += '<td class="text_c">' + (start_num - idx) + '</td>';
						html += '<td class="text_c">'+((isEmpty(item.total_cash)) ? '' : addComma(item.total_cash))+'</td>';
						html += '<td class="text_c">'+((isEmpty(item.exec_account)) ? '' : addComma(item.exec_account))+'</td>';
						html += '<td class="text_c">'+((isEmpty(item.remain_account)) ? '' : addComma(item.remain_account))+'</td>';												
						html += '<td class="text_l">'+((isEmpty(item.etc)) ? '' : item.etc)+'</td>';												
						html += '<td class="text_c">'+((isEmpty(item.create_dttm)) ? '' : item.create_dttm) + '</td>';
						html += '<td class="text_c">'+((isEmpty(item.create_id)) ? '' : item.create_id) + '</td>';														
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
						fn_bgExecSearch(cuurPage);
					}
				});
				
			 	$('#bgExecDataList').html(html);
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#bgExecDataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
			}
		}else{
			/***************************************************************
			* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
			****************************************************************/
			
			$("#total_cash").val(0);
			
			$('#paging').children().remove();
			
			$("#bgExecDataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
		}
    },
    error : function(){                              // Ajax 전송 에러 발생시 실행
    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
    }
});
}

function fn_saveBgExec(){
	var totalCash = uncomma($("#total_cash").val());
	var execAmt = uncomma($("#exec_account").val());
	
	
	if(parseInt(totalCash) < 1){
		fn_showCustomAlert("총현금이 0이 맞는지 확인해 주십시오.");
		$("#remain_account").val(0);
		
		return false;
	}
	
	if(parseInt(totalCash) < parseInt(execAmt) ){
		fn_showCustomAlert("집행 금액이 총 현금 보다 큽니다.");
		$("#remain_account").val(0);
		
		return false;
	}
	
	var total_cash = uncomma($("#total_cash").val());
	var exec_account = uncomma($("#exec_account").val());
	var remain_account = uncomma($("#remain_account").val());
	
	$("#total_cash").val(total_cash);
	$("#exec_account").val(exec_account);
	$("#remain_account").val(remain_account);
	//현재 페이지 세팅
	
	
	var sendData = $("form[name='budgetExecFrm']").serialize();
	
    $.ajax({
	        url: '${ctxt}/execute/bgExec/insertProjBgExec.do',
	        data: sendData,
	        type: 'POST', 
	        dataType: 'json',	      
			success: function(result){			
				
				if(result.sMessage == "Y"){
					fn_showCustomAlert("저장이 완료되었습니다.");
					fn_bgExecSearch(1);
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


function fn_bgExecSet(obj){
	var totalCash = uncomma($("#total_cash").val());
	var execAmt = uncomma($(obj).val());
	
	
	if(parseInt(totalCash) < 1){
		fn_showCustomAlert("총현금이 0이 맞는지 확인해 주십시오.");
		$("#remain_account").val(0);
		
		return false;
	}
	
	if(parseInt(totalCash) < parseInt(execAmt) ){
		fn_showCustomAlert("집행 금액이 총 현금 보다 큽니다.");
		$("#remain_account").val(0);
		
		return false;
	}
	
	
	$("#remain_account").val(addComma(parseInt(totalCash) -parseInt(execAmt)));

	
}

</script>
 
<input type="hidden" id = "bgExecPage" name="bgExecPage" value="${bgExec.page}" />
<div id="container">
	<div id="divRefreshArea">
		<h3 class="page_title" id="title_div"> <span class="adminIcon"></span>
			예산 집행 입력
		</h3>
			<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>예산 집행 저장 이전에 예산 편성 데이터를 처리해 주십시오.</span>

	
		<!--게시판-->
		<div class="budgetExec">
			<form name="budgetExecFrm" method="post"  action="">
				<input type="hidden" id = "proj_year_id_bg_exec" name="proj_year_id_bg_exec" value="${data.proj_year_id}" />
		
				<table class="table_h" cellpadding="0" cellspacing="0" border="0" >
	
					<caption>예산집행</caption>
					<colgroup>
						
						<col style="width: 20%;">		
						<col style="width: 20%;">								
						<col style="width: 20%;">				
						<col style="width: *;">				
					</colgroup>
					<thead>
	
						<tr>
							<th scope='col'>총 현금</th>
							<th scope='col'>집행 금액</th>											
							<th scope='col'>잔액</th>
							<th scope='col'>비고</th>
			
						</tr>
					</thead>
					<tbody>
						<tr>				
							<td><input type="text" id="total_cash" name = "total_cash" class="text_r form-control"   readonly="readonly" /></td>
							<td><input type="text" id="exec_account" name = "exec_account" maxlength="12" class="text_r" value = "0"  oninput = "this.value = this.value.replace(/[^0-9.]/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" onchange = "javascript:fn_bgExecSet(this);"/></td>
							<td><input type="text" id="remain_account" name ="remain_account"  class="text_r form-control"  readonly="readonly" /></td>
							<td><input type="text" id="etc" name = "etc"  class="text_l" maxlength="100" /></td>
						</tr>
					</tbody>
	
				</table>
			</form>
			 <div class="flex_box">
					
				  	<div align="right">
				  		
				  		<a href="javascript:void(0);" onclick="javascript:fn_saveBgExec();" class="btn btn-secondary">저장</a>				  		
					</div>
				</div>
	         
		</div>


		<h3 class="page_title" id="title_div">
			<span class="adminIcon"></span>예산 집행 저장 이력
		</h3>
		<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>예산 집행 저장 데이터를 이력으로 확인할 수 있습니다.</span>
	
		<!--게시판-->
		<div class="budgetExec">


			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>예산집행</caption>
				<colgroup>
					
					<col style="width: 5%;">		
					<col style="width: 10%;">								
					<col style="width: 10%;">
					<col style="width: 10%;">
					<col style="width: *;">
					<col style="width: 15%;">
					<col style="width: 15%;">
				</colgroup>
				<thead>

					<tr>
						<th scope='col'>순번</th>
						<th scope='col'>총 현금</th>											
						<th scope='col'>집행 금액</th>
						<th scope='col'>잔액</th>						
						<th scope='col'>비고</th>
						<th scope='col'>생성일</th>
						<th scope='col'>생성자</th>
					</tr>
				</thead>
				<tbody id="bgExecDataList">
				</tbody>

			</table>
				<div id="paging" class="paginate"></div>
		</div>
	</div>
</div>

