<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<style type="text/css">
#paging{text-align:center;}
a.paging-item,a.paging-side{margin:0 .25em;}
a.paging-item.selected{font-weight:bold;}


.tooltip-container {
    position: relative;
    display: inline-block;  
}

.tooltip-target {
    cursor: pointer;
    padding: 5px;
    background-color: #767676;
    color: white;
    border-radius: 5px;
}

.tooltip-content {
    display: none;
    position: fixed; /* 화면 중앙에 배치 */
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #f9f9f9;
    border-radius: 5px;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2);
    z-index: 1;
    text-align: left; /* 왼쪽 정렬 */
    padding: 10px;
    white-space: pre;
    border: 1px solid #314873;
}

</style>
		


<script type="text/javascript">

var rcv_xmlList;
var rcv_html="";
var rcv_cuurPage;
var rcv_pagetotalCnt = 0;


var exch_xmlList;
var exch_html="";
var exch_cuurPage;
var exch_pagetotalCnt = 0;


var situ_html = "" 

var exchPsbVal = "${exchPsbVal}"; // 환전 가능 수량

$(function(){
	

 
	
	// 선물 받은 list
	fn_search_rcv(1);
	
	// 환전 내역 list
   fn_search_exch(1);
	
	//나의 아이템 현황
	fn_item_situation();

	// 마우스 오버 시 툴팁을 표시하는 이벤트 핸들러 추가
  	$("#exch_dataList").on("mouseenter", ".tooltip-container", function() {
        $(this).find(".tooltip-content").fadeIn();
    }).on("mouseleave", ".tooltip-container", function() {
        $(this).find(".tooltip-content").fadeOut();
    });
});  

// 아이템 받은 내역 list
function fn_search_rcv(rcv_page){
	//현재 페이지 세팅
	rcv_html=""; 
	rcv_cuurPage= rcv_page;

	
	if(!isEmpty( $('#rcv_startDate').val()) && isEmpty( $('#rcv_endDate').val())  ){
		fn_showCustomAlert("조회 종료 날짜를 입력해 주십시오.");
		
		return false;
	}
	
	if(isEmpty( $('#rcv_startDate').val()) && !isEmpty( $('#rcv_endDate').val())  ){
		fn_showCustomAlert("조회 시작 날짜를 입력해 주십시오.");
		return false;
	}
	
	if(  $('#rcv_startDate').val() >   $('#rcv_endDate').val() ){
		fn_showCustomAlert("조회 시작 날짜는 종료 날짜보다 클 수 없습니다.");
		
		return false;
	}
	
	var params = {};
		params.page    = rcv_cuurPage;   
		params.startDate = $('#rcv_startDate').val(); 
		params.endDate = $('#rcv_endDate').val(); 
	

    $.ajax({
        url: '${ctxt}/member/exchange/readExchBaseRcvInfoList.do',
        data: params,
        type: 'POST',     
        dataType: 'json',
        success: function(result) {
   			
        	if(!isEmpty(result) && !isEmpty(result.exchBaseRcvList)){
        		var start_num = Number(result.exchBaseRcvListTotal) - ((rcv_cuurPage -1) * 10)
				rcv_pagetotalCnt =Number(result.exchBaseRcvListPageTotal);	
        		
				$.each(result.exchBaseRcvList, function(idx, item){
					rcv_html += '<tr>';
					rcv_html += ' <td>' + (start_num - idx) + '</td>';				
					rcv_html += ' <td>'+item.item_use_his+'</td>';
					rcv_html += ' <td>'+item.to_user_nicknm+'</td>';
					rcv_html += ' <td>'+item.create_dttm+'</td>';			
					rcv_html += '</tr>';
					
				});
				
				$('#rcv_paging').paging({
			    	
					 current:rcv_cuurPage
					,max:rcv_pagetotalCnt
					,length:pageLen
					,onclick:function(e,rcv_page){
						rcv_cuurPage=rcv_page;
						fn_search_rcv(rcv_cuurPage);
					}
				});
				
			    $('#rcv_dataList').html(rcv_html);
        	}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#rcv_paging').children().remove();
				
				$("#rcv_dataList").html('<tr><td colspan="54" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        },
        complete : function(){
        
        }
    });
}

function fn_item_situation(){
	situ_html = "";
	var params = {};

    $.ajax({
        url: '${ctxt}/member/exchange/readExchSituation.do',
        data: params,
        type: 'POST',     
        dataType: 'json',
        success: function(result) {
   			
        	if(!isEmpty(result) && !isEmpty(result.situationData)){
        				
				situ_html += '<tr>';
				situ_html += ' <td>'+addComma(Number(result.situationData.total_item_cnt))+ '</td>';
				situ_html += ' <td><span class="highlight big">'+addComma(Number(result.situationData.exch_psb_cnt))+'</span></td>';
				situ_html += ' <td>'+addComma(Number(result.situationData.exch_get_cnt))+'</td>';					
				situ_html += ' <td>'+addComma(Number(result.situationData.exch_review_cnt))+'</td>';	
				situ_html += '</tr>';
					
				$("#exch_psb_cnt").val(result.situationData.exch_psb_cnt);
				
			    $('#item_dataList').html(situ_html);
        	}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
			
				$("#item_dataList").html('<tr><td>0</td><td>0</td><td>0</td><td>0</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        }
    });
}


//환전신청 내역 list
function fn_search_exch(exch_page){
	//현재 페이지 세팅
	exch_html=""; 
	exch_cuurPage= exch_page;

	
	if(!isEmpty( $('#exch_startDate').val()) && isEmpty( $('#exch_endDate').val())  ){
		fn_showCustomAlert("조회 종료 날짜를 입력해 주십시오.");
		
		return false;
	}
	
	if(isEmpty( $('#exch_startDate').val()) && !isEmpty( $('#exch_endDate').val())  ){
		fn_showCustomAlert("조회 시작 날짜를 입력해 주십시오.");
		return false;
	}
	
	if(  $('#exch_startDate').val() >   $('#exch_endDate').val() ){
		fn_showCustomAlert("조회 시작 날짜는 종료 날짜보다 클 수 없습니다.");
		
		return false;
	}
	  
	var params = {};
		params.page    = exch_cuurPage;   
	
		params.startDate = $('#exch_startDate').val(); 
		params.endDate = $('#exch_endDate').val(); 
	
	 $.ajax({
	     url: '${ctxt}/member/exchange/readExchInfoList.do',  
	     data: params,
	     type: 'POST',     
	     dataType: 'json',
	     success: function(result) {
				
	     	if(!isEmpty(result) && !isEmpty(result.exchInfoList)){
	     		var start_num = Number(result.exchInfoListTotal) - ((exch_cuurPage -1) * 10)
					exch_pagetotalCnt =Number(result.exchInfoListPageTotal);	
	     		
					$.each(result.exchInfoList, function(idx, item){
						exch_html += '<tr>';
						exch_html += ' <td>' + (start_num - idx) + '</td>';
						exch_html += ' <td>'+item.exch_num+'</td>';
						exch_html += ' <td>'+addComma(Number(item.exch_item_cnt))+'</td>';
						exch_html += ' <td>'+addComma(Number(item.exch_item_money))+'</td>';
						exch_html += ' <td>';						
							if(item.exch_apprl_gbn == '환전 취소'){		
								exch_html += '<div class="tooltip-container">';
									exch_html += '<div class="tooltip-target">';
										exch_html += item.exch_apprl_gbn;
									exch_html += '</div>';								
									exch_html += '<div class="tooltip-content">';
										exch_html += ((isEmpty(item.exch_cancle_comment)) ? '' : item.exch_cancle_comment);
									exch_html += '</div>';
								exch_html += '</div>';
							}else{
								exch_html += item.exch_apprl_gbn;
							}
							
						exch_html += ' </td>';
			//			exch_html += ' <td>'+((isEmpty(item.exch_cancle_comment)) ? '' : item.exch_cancle_comment)+'</td>';
						exch_html += ' <td>'+item.create_dttm+'</td>';			
						exch_html += '</tr>';					
					});
					
					$('#exch_paging').paging({
				    	
						 current:exch_cuurPage
						,max:exch_pagetotalCnt
						,length:pageLen
						,onclick:function(e,exch_page){
							exch_cuurPage=exch_page;
							fn_search_exch(exch_cuurPage);
						}
					});
					
				    $('#exch_dataList').html(exch_html);
	     	}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#exch_paging').children().remove();
					
					$("#exch_dataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
				}
	     },
	     error : function(){                              // Ajax 전송 에러 발생시 실행
	     	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	     },
	     complete : function(){
	     
	     }
	 });
 
}

function onClickEnter(){
	
	if(event.keyCode == 13){
		fn_search(1);
		
		return;
	}
}


function fn_exchBaseinfo(){
	var exch_psb_cnt =  $("#exch_psb_cnt").val();
	var chkVal = exchPsbVal;
	
	if(isEmpty(chkVal)){
		chkVal = '200';
	}

	if(Number(exch_psb_cnt) < Number(exchPsbVal)  ){
		fn_showCustomAlert("환전 가능 수량이 부족합니다.<br/>" + chkVal + "개 이상 환전 신청 가능합니다.");
		
		return false;
	}
	
	var form = document.exchForm;
	form.action = '${ctxt}/member/exchange/exchBaseInfoView.do';
	form.submit();	
}
</script>


<div id="container">
   <div id="divRefreshArea">
    		<div class="tabBox pa_t0">
    			<h3 class="page_title">환전 신청하기</h3>  
    			 <form name="exchForm" method="post" action="">
    			 </form>
	            <div class="tab clear ma_b_30 tab-trade">
	               <button class="tablinks" type="button" onclick="openTab(event, 'name_1')" id="defaultOpen">선물 받은 아이템</button>
	               <button class="tablinks active" type="button" onclick="openTab(event, 'name')" title="선택됨">환전 신청 내역</button>
	            </div>
	            <div>
		             <!-- 탭 1 -->
		               <article id="name_1" class="tabcontent clear" style="display: none;">
				        
							
							<div class="grid_box">   
								
								<!-- 조회기간 -->
								<label>조회기간 : </label>
								<input type="date" name="rcv_startDate" id="rcv_startDate"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search_rcv(1); return false;}" />
								~ <input type="date" name="rcv_endDate" id="rcv_endDate"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search_rcv(1); return false;}" />							
								<a href ="javascript:void(0);" id="btnSearch_rcv" onclick="javascript:fn_search_rcv(1);" class="btn btn-primary">검색</a>		

							</div>
					
						<!--게시판-->
						<div class="admin">
							<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
								<caption>
								 	 아이템 받은 내역 -  날짜, 선물받은 개수 , 선물한 유저 
								</caption>
								<colgroup>
									<col style="width:10%">
									<col style="width:30%">
									<col style="width:30%">
									<col style="width:30%">				
								</colgroup>
								<thead>
									<tr>
										<th scope='col' colspan="4">선물 받은 아이템</th>					
									</tr>
									<tr>
										<th scope='col'>번호</th>
										<th scope='col'>선물받은 개수</th>
										<th scope='col'>선물한 유저</th>									
										<th scope='col'>선물 받은 일시</th>
									</tr>
								</thead>
								<tbody id="rcv_dataList">
								</tbody>
							</table>
							<!-- 페이징 처리 -->
							<div id="rcv_paging" class="paginate"></div>
						</div>
						
					 </article>
					 
					 
					  <!-- 탭 2 -->
		               <article id="name" class="tabcontent clear" style="display: none;">
				     
						
						<div class="grid_box">   
							
							<!-- 조회기간 -->
							<label>조회기간 : </label>
							<input type="date" name="exch_startDate" id="exch_startDate"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search_exch(1); return false;}" />
							~ <input type="date" name="exch_endDate" id="exch_endDate"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search_exch(1); return false;}" />							
							<a href ="javascript:void(0);" id="btnSearch_rcv" onclick="javascript:fn_search_exch(1);" class="btn btn-primary">검색</a>		

						</div>
						
						<!--게시판-->
						<div class="admin">
							<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
								<caption>
								 	 환전 신청 내역 
								</caption>
								<colgroup>
									<col style="width:10%">
									<col style="width:20%">
									<col style="width:15%">
									<col style="width:15%">
									<col style="width:20%">
							<%-- 		<col style="width:20%"> --%>
									<col style="width:20%">				
								</colgroup>
								<thead>
									<tr>
										<th scope='col' colspan="5"> 환전 신청 내역 </th>					
									</tr>
									<tr>
										<th scope='col'>번호</th>
										<th scope='col'>환전 No.</th>
										<th scope='col'>환전 신청 개수 </th>
										<th scope='col'>환전 신청 금액 </th>
										<th scope='col'>환전 상태</th>			
						<!-- 				<th scope='col'>취소 사유</th>				 -->					
										<th scope='col'>환전 신청일</th>
									</tr>
								</thead>
								<tbody id="exch_dataList">
								</tbody>
							</table>
							<!-- 페이징 처리 -->
							<div id="exch_paging" class="paginate"></div>
						</div>
						
					 </article>
			 
				</div>
		    </div>
		    
			<div class="admin">
				<table class="table_h" cellpadding="0" cellspacing="0" border="0">
					<caption>
					 	나의 아이템 현황
					</caption>
					<colgroup>
						<col style="width:25%">
						<col style="width:25%">
					    <col style="width:25%">
					    <col style="width:25%">
					</colgroup>
					<thead>
						<tr>
							<th scope='col' colspan="4">나의 아이템 현황</th>						
						</tr>
						<tr>
							<th scope='col'>선물 받은 아이템</th>						
							<th scope='col'>환전 가능한 아이템</th>
							<th scope='col'>환전 받은 아이템</th>
							<th scope='col'>환전 검토 중인 아이템</th>
						</tr>
					</thead>
					<tbody id="item_dataList">
					
					</tbody>  
				</table>
		
			</div>
			
			<div class="buttonBox">
					<a href="javascript:fn_exchBaseinfo();" class="btnN" role="button"><span>환전 신청하기</span></a>
			</div>
  	 </div>  
</div>

<input type="hidden" name = "exch_psb_cnt" id = "exch_psb_cnt" value = "" />

<script type="text/javascript">

//Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>
