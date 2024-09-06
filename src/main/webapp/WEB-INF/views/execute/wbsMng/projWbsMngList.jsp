<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
 <style>
		 .wbsThLine{
		    padding : 10px 2px !important;
	 		border: 1px solid rgba(0, 0, 0, 0.1) !important;
	 		background: #f7ff05 !important;
	    	font-size: 1rem !important;
	    	min-width: 22px !important;
		}
		.wbsline{
 			border-top: 1px solid rgba(0, 0, 0, 0.1) !important;
 			border-bottom: 1px solid rgba(0, 0, 0, 0.1) !important;
 			border-left: 1px solid rgba(0, 0, 0, 0.1) !important;
 			border-right: 1px solid rgba(0, 0, 0, 0.1) !important;
		}
		.col-lg-9 {
		    width: 79%;
		}
        .admin {
		    overflow-x: auto;
		    margin-bottom: 80px;
		    max-height: 600px;
		    width : 1550px;
		}
        ::-webkit-scrollbar {
			height: 6px;
		}
		#divRefreshArea .grid_box {
			margin-bottom: 24px;
		}
		table.table_h.wbsTable, table.table_h.wbsTable th {
			padding: 0;
		}
		
		.wbsTable {
			border-collapse: separate;
			border-spacing: 0;
			padding: 0;
		}
		.wbsThead {
			display: table-header-group;
		    height: 100px;
		    position: sticky;
		    top: 0;
		    z-index: 11;
		}
		thead > tr:first-child > th {
			position: sticky;
			top: 0;
			border-top: 1px solid rgb(235 221 216) !important;
		}
		
		thead > tr:nth-child(2) > th {
			position: sticky;
			top: 50px;
			border-bottom: 1px solid rgb(235 221 216) !important;
		}
		.btn-dept {
		    font-size: 12px;
		    min-height: 28px;
		}


        #dataList td {
            position: relative;
/*             padding: 0 6px; */          
            padding: 0px;          
            height: 50px; /* Adjust height as needed */
        }
        #dataList tr td:nth-child(n-5) {
		    background-color: #f9f9f9;
		}
        
        th {
  			border: 1px solid #d9e0e4;
  			position: sticky;
    		top: 0;
        }
 
        .clickable-area {
            position: absolute;
            width: 100%;
            height: 50%;
            background-color: white;
            cursor: pointer;
            font-size: 13px;
            z-index: 1; /* Ensure clickable area is above cell content */
            
        }

        .clickable-area.top {
            top: 0;
            border-bottom: 1px solid #e3e3e3;
            margin-bottom: 5px; /* Add margin between top and bottom areas */
        }

        .clickable-area.bottom { 
            bottom: 0;
            border-bottom: 1px solid #e3e3e3;
            border-top: 1px solid #ffffff;
            margin-top: 5px; /* Add margin between top and bottom areas */
        }
        tr:last-child .clickable-area.bottom {
         	border-bottom: 0;
         }

        .clickable-area.top.highlighted { 
            background-color: #FF9800;
        }

        .clickable-area.bottom.highlighted {
            background-color: #00adff;
        }
        
         
        .wbsInput_item{
            height: 25px !important;            

        }
        .wbsInput{
            height: 25px !important;
         /*    width:90% !important; */

        }
         .btnResp{
        	display: grid;
        	grid-template-columns:150px 37px; 
        	justify-content: center;
        }
        .btnDeptAdd{
        	display: grid;
        	grid-template-columns:42px 42px; 
        	justify-content: center;
        }
        
        .btnDept{
        	display: grid;
        	grid-template-columns:50px; 
        	justify-content: center;
        }
    </style> 
	    

<script type="text/javascript"> 

/*  최상의 항목의 코드 단위는 100000 으로 정의한다  10만 단위 */


var addScheRowSet = '';
var proj_year_id = '${data.proj_year_id}';

$(function(){ 

	fn_calClickEvent();
	
	fn_searchCal();
	
	fn_headerColspan();
	
	fn_searchItem();
	fn_searchItemProgressRate();
	fn_searchSchedule();
	

}); 

// 일정 헤더를 정리한다
function fn_headerColspan(){
	 var $headers = $('.first th'); // 첫 번째 행의 모든 th 요소를 선택

	    var prevText = $headers.first().text(); // 첫 번째 요소의 텍스트
	    var colspanCount = 1; // colspan 값을 설정할 카운트 변수

	    for (var i = 1; i < $headers.length; i++) {
	        var currentText = $headers.eq(i).text();

	        if (currentText === prevText) {
	            colspanCount++;
	            $headers.eq(i).remove(); // 이전과 같은 경우 현재 요소 제거
	        } else {
	            if (colspanCount > 1) {
	                $headers.eq(i - colspanCount).attr('colspan', colspanCount); // 이전 그룹에 colspan 설정
	            }
	            prevText = currentText;
	            colspanCount = 1; // 초기화
	        }
	    }

	    // 마지막 그룹에 대한 처리
	    if (colspanCount > 1) {
	        $headers.eq($headers.length - colspanCount).attr('colspan', colspanCount); // 마지막 그룹에 colspan 설정
	    }
}

function fn_chgst(obj){


	var cloTd = $(obj).closest('td'); 
	var cloTr = cloTd.closest('tr');
	
	var itemState = cloTr.find('input[attr="item_state"]');

    // item_state의 value 가져오기
    itemState.val();

    if(itemState.val() != 'I'){
    	itemState.val("U");
	}
/* 	if($('#save_type').val() != 'I'){
		$('#save_type').val('U');
	} */
}


//wbs 항목을 조회
function fn_searchItem(){

		var params = {};
		params.proj_year_id = proj_year_id;
  	  	
    $.ajax({
        url: '${ctxt}/execute/wbsMng/readProjWbsMngList.do',
        data: params,
        type: 'POST', 
        async : false, 
		dataType:"json",
        success: function(result) { 
        	$('#dataList').children().remove();
        	var html="";
        
        	if(!isEmpty(result)){
				if(!isEmpty(result.wbsList)){ 
					
					
					$.each(result.wbsList, function(idx, item){
						var deptLevl = 1 ;
						var pressTr = 0;
				        if(item.item_levl == 0){
				        	deptLevl  = 1; 
				        	
				        }else{
				        	deptLevl  = item.item_levl+1;
				        	
				        	if(item.item_levl == 1){
				        		pressTr =  40;
			 	        	}else if(item.item_levl == 2){
				        		pressTr =  80;
				        	}else if(item.item_levl == 3){
				        		pressTr =  120;
				        	}else  if(item.item_levl == 4){
				        		pressTr =  160;
				        	}
				        }
				        
						html += '<tr onclick="fn_setRow(this)">';	   
						    html += '<td style="text-align: left;display:none;" >';
						    	html +='<input type="hidden" value="'+item.item_prts_id+'" attr = "item_prts_id" class="wbsInput" />';
						    	html +='<input type="hidden" value = "N"  attr = "item_state" class="wbsInput" />';
						    	html += '<input type="hidden" value="'+item.item_id+'" attr = "item_id"  class="wbsInput" />';
						    	html += '<input type="text" value="'+item.item_levl+'" attr = "item_levl"  class="wbsInput" />';
						    html += '</td>';   												    
						    html += '<td style="text-align: left;padding-left: '+pressTr +'px; min-width:300px;  position: sticky; background-color: #f9f9f9; left: 0; top: 1px; z-index: 10;">';
						    	html += '<input type="text" value="'+item.item_nm+'" attr = "item_nm" oninput="javascript:fn_chgst(this);"  class="wbsInput_item"/>';
						    html +='</td>';   						
				        	html += '<td style="min-width:200px; position: sticky; background-color: #f9f9f9; left: 300px; top: 1px; z-index: 10;">';
				        		html += '<div class="btnResp">';
				        			html += '<input type="text" value="'+item.item_resp_nm+'" attr = "item_resp_nm" readonly="readonly" class="wbsInput" />';
				        			html += '<input type="hidden" value="'+item.item_resp+'" attr = "item_resp"  class="wbsInput" />';
				        		html += '	<a href="javascript:void(0);" class="btn btn-dept" onclick="javascript:fn_respPop(' +item.item_id+ ');"><i class="fas fa-search last-child"></i></a>';
				        		html += '</div>'
				        		
				        	html+= '</td>';
				        	
							
						
					        html += '<td style="min-width:100px; position: sticky; background-color: #f9f9f9; left: 500px; top: 1px; z-index: 10;">';
					        
					        if(item.item_levl < 2){ // 2 아래만 추가 버튼을 생성
					        	
					        	html += '<div class="btnDeptAdd">';
					    		html += '<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_downDeptRow('+(deptLevl)+' , '+parseInt(item.item_id)+');">추가</a>';
					        }else{
					        	html += '<div class="btnDept">';
					        }
			
						        
					        	html += '<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_delDeptRow('+parseInt(item.item_id)+' , '+parseInt(item.item_prts_id)+');">삭제</a>';
				        	 html += '</div>';
					        html += '</td>';
					      
					        html += '<td style="text-align: center;  min-width:80px; position: sticky; background-color: #f9f9f9; left: 600px; top: 1px; z-index: 10; border-right: 1px solid #eee;">';
				        		html += '<span attr = "proRate" >  </span>';		    				        	 
			       			 html += '</td>';
			       			 
			        		html += '<td style="text-align: left; min-width:50px; position: sticky; background-color: #f9f9f9; left: 680px; top: 1px; z-index: 10; border-right: 1px solid #eee;">';
				        		html += '<div class="clickable-area top" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">계획</div>';
				        		html += '<div class="clickable-area bottom" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">실적</div>';					    				        	 
					        html += '</td>';
					        
						  html +=  fn_calRowAdd();
												
						html += '</tr>';				
											
					});
		
				 	$('#dataList').html(html);

				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/

				}
			}else{

			 
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        },
        complete : function (){
        	
        }
    }); 
}



//wbs 항목별 진척율 조회
function fn_searchItemProgressRate(){

		var params = {};
		params.proj_year_id = proj_year_id;
	  	
  $.ajax({
      url: '${ctxt}/execute/wbsMng/readWbsProRateList.do',
      data: params,
      type: 'POST', 
      async : false, 
		dataType:"json",
      success: function(result) { 
      	var html="";
      
      	if(!isEmpty(result)){
				if(!isEmpty(result.wbsProRateList)){ 
					
			   	    var $rows = $('#dataList tr'); // 모든 행을 한번만 선택
			   	    
					$.each(result.wbsProRateList, function(idx, rate){
						 $rows.each(function() {
							   var $tr = $(this);
					           var itemId = $tr.find('input[attr="item_id"]');
					           var itemProRate = $tr.find('span[attr="proRate"]');
					           
					           if(rate.item_id ==itemId.val()){
					        	   itemProRate.text(rate.item_ratio);
					           } 
					           
						 });
											
					});

				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
				}
			}else{

			}
      },
      error : function(){                              // Ajax 전송 에러 발생시 실행
      	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
      },
      complete : function (){
      	
      }
  }); 
}


// wbs 일정을 조회
function fn_searchSchedule(){
	
	var params = {};
	params.proj_year_id = proj_year_id;
	
$.ajax({
    url: '${ctxt}/execute/wbsMng/readWbsScheduleList.do',
    data: params,
    type: 'POST',
   async : false, 
	dataType:"json",
    success: function(result) {
    	var html="";
    
    	if(!isEmpty(result)){
			if(!isEmpty(result.wbsScheduleList)){ 
				   var $rows = $('#dataList tr'); // 모든 행을 한번만 선택
				
				$.each(result.wbsScheduleList, function(idx, sItem){
						
					 $rows.each(function() {
				         var $tr = $(this);
				         var itemId = $tr.find('input[attr="item_id"]');
				         	if(itemId.val() == sItem.item_id ){
				             $tr.find('td').each(function() {
				            	 var $td = $(this);
				            	
				                 var planVal = $td.find('input[bindattr="plan"]');
				                 var planKeyVal = $td.find('input[bindattr="planKey"]');
				                 var planItemIdVal = $td.find('input[bindattr="planItemId"]');
				                 var planYmVal = $td.find('input[bindattr="planYm"]');
				                 var planWeekVal = $td.find('input[bindattr="planWeek"]');
				                
				                 var perforVal = $td.find('input[bindattr="perfor"]');
				                 var perforKeyVal = $td.find('input[bindattr="perforKey"]');
				                 var perforItemIdVal = $td.find('input[bindattr="perforItemId"]');
				                 var perforYmVal = $td.find('input[bindattr="perforYm"]');
				                 var perforWeekVal = $td.find('input[bindattr="perforWeek"]');
				               
				           
				                 if(planYmVal.val() == sItem.wbs_sch_ym &&  planWeekVal.val() == sItem.wbs_sch_week  ){
				                	 if( sItem.wbs_sch_gbn == 'plan'){
				                		 planVal.val('plan');
				                		 planKeyVal.val(sItem.wbs_sch_id);					                	
				                		  planItemIdVal.val(sItem.item_id);
				                		  
					                	 // $td.find('div[class="clickable-area top"]').attr('data-programmatic-click', 'true').click(); // 셀 색깔
				                		  $td.find('div[class="clickable-area top"]').addClass('highlighted').css('background-color', '#FF9800');
				                	 }
				                			            	 			                 
				                 }
				                 
				                 if(  perforYmVal.val() == sItem.wbs_sch_ym &&  perforWeekVal.val() == sItem.wbs_sch_week  ){
				                	 if( sItem.wbs_sch_gbn == 'perfor'){
				                		 perforVal.val('perfor');
				                		 perforKeyVal.val(sItem.wbs_sch_id);
				                		 perforItemIdVal.val(sItem.item_id);
				                		
					                 	// $td.find('div[class="clickable-area bottom"]').attr('data-programmatic-click', 'true').click();   	//셀 색깔	 
				                		 $td.find('div[class="clickable-area bottom"]').addClass('highlighted').css('background-color', '#00adff');
				                	 }
				                			            	 			                 
				                 } 
				             });
				         	}
				        
				     });
				});
				
			
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
	
			
			}
		}else{

		}
    },
    error : function(){                              // Ajax 전송 에러 발생시 실행
    	console.log('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
    },
    complete : function (){
    	
    }
});
}
// wbs 헤더 정보를 구성한다. th  colgroup  일정해더
function fn_searchCal(){
	

	var params = {};
	 	params.proj_year_id = proj_year_id;
	  	
$.ajax({
    url: '${ctxt}/execute/wbsMng/readWbsCalendarList.do',
    data: params,
    type: 'POST',
    async : false,
    dataType: 'json',
    //processData: false,
    success: function(result) {
	    	var topTh ='';
	    	var bottomTh ='';	
	    	var colGroup ='';	  
	    	if(!isEmpty(result)){
				if(!isEmpty(result.wbsCalendarList)){
				
					$.each(result.wbsCalendarList, function(idx, item){
						
						topTh += '<th scope="col" class="wbsThLine">' +item.ym+ '</th>';
																		
						bottomTh +=	'<th scope="col" class="wbsThLine">' +item.week+ '</th>';
						colGroup +=	'<col style="width: ' +3 + 'px;">';		
					
					
						addScheRowSet +=	 `<td style="text-align: left;" class= "wbsline">											
					    		  				<div class="clickable-area top"  >
					    		  					<input type="hidden" value ="" attr ="plan`+idx+`"  bindattr = "plan"  >
					    		  					<input type="hidden" value ="" attr ="planKey`+idx+`"  bindattr = "planKey" >
					    		  					<input type ="hidden" value = "" attr ="planItemId`+idx+`" bindattr = "planItemId" /> 
													<input type ="hidden" value = `+ item.ym +` attr ="planYm`+idx+`" bindattr = "planYm" /> 
													<input type ="hidden" value = `+ item.week +` attr ="planWeek`+idx+`"  bindattr = "planWeek"//>
				    		  					</div>
					             		  		<div class="clickable-area bottom" >
					             		  			<input type="hidden" value = "" attr = "perfor`+idx+`" bindattr = "perfor"  >
					             		  			<input type="hidden" value = "" attr = "perforKey`+idx+`" bindattr = "perforKey" >
													<input type ="hidden" value = "" attr ="perforItemId`+idx+`" bindattr = "perforItemId" /> 
													<input type ="hidden" value = `+ item.ym +` attr ="perforYm`+idx+ `" bindattr = "perforYm" /> 
													<input type ="hidden" value = `+ item.week +` attr ="perforWeek`+idx+`" bindattr = "perforWeek"/ />
					             		  		</div>
					             		 	 </td>`;
					             		 	 
						
					});
				
					
							
					
						
					$('#colG').after(colGroup);
				 	$('#topTh').after(topTh);
				 	$('#bottomTh').after(bottomTh);
				 	
				}	
			}
    	},
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    },
	    complete : function (){
	    	
	    }
	});
}
 

/** 
* 로우 선택 
*/
function fn_setRow(obj){ 
	 
	$('#dataList .highlight-td').attr('class','off');
	

    // 클릭된 tr의 1ㅡ2ㅡ3ㅡ4 td에 highlight 클래스 추가
    $(obj).find('td').slice(0, 4).attr('class','highlight-td');	
}

// 일정 td 추가 
function fn_calRowAdd(){
	
		return addScheRowSet;
}

// 상위 항목을 개설한다.
function fn_topDeptRow(){
	 //<td><select  class=""><option value="Y"  >홍길동</option><option value="N" >김영희</option></select></td>`;	   
	 var deptLevl = 1;
	
	 var itemId = 100000; // 초기값  최초 생성시  상위 항목의 키값
	
	 var itemList = $('#dataList tr').toArray(); // tr  배열로 변환
	
	 var  maxValItemId = -Infinity;
     itemList.map(function(tr) {
	   	    var $tr = $(tr);
	        var itemPrtsId = $tr.find('input[attr="item_prts_id"]').val();
	   	    var itemIdChk = $tr.find('input[attr="item_id"]');
		   	
	   	    if (itemPrtsId === "") {
		   	    // input 요소의 값 추출
		   	    var chkVal = parseInt(itemIdChk.val());
		   	    
		   	    if(!isNaN(chkVal)){
		   	    	if(chkVal > maxValItemId){
		   	    		maxValItemId  =  chkVal;
		   	    		
		   	    		itemId= maxValItemId+ 100000;  // max  가 100000 이면 다음 증가값은 200000
		   	    		
		   	    	//	return true;  // 멈춤
		   	    	}
		   	    }
		   	}

   	});

	
   	
	var addrow = `<tr onclick="javascript:fn_setRow(this);">`;										   
		addrow +=	`<td style="text-align: left;display:none;">
				    	<input type="hidden" value = ""  attr = "item_prts_id" />
				        <input type="hidden" value = "I"  attr = "item_state"/>
			        	<input type="hidden" value = "`+itemId+`"  attr = "item_id" class="wbsInput" />
			        		<input type="text" value = "0"  attr = "item_levl" class="wbsInput" />
				    </td>  										   													
				    <td style="text-align: left;padding-left: '+pressTr +'px; min-width:300px;  position: sticky; background-color: #f9f9f9; left: 0; top: 1px; z-index: 10;"><input type="text" value = ""  attr = "item_nm" class="wbsInput_item" /></td>   					    
				    <td style="min-width:200px; position: sticky; background-color: #f9f9f9; left: 300px; top: 1px; z-index: 10;">
				    	<div class="btnResp">
				    		<input type="hidden" value = ""  attr = "item_resp" class="wbsInput" />
				    			<input type="text" value="" attr = "item_resp_nm" readonly="readonly" class="wbsInput" />
				    		<a href="javascript:void(0);" class="btn btn-dept" onclick="javascript:fn_respPop(`+itemId+` );"><i class="fas fa-search last-child"></i></a>
				    	</div>
				    </td>			       
				    <td style="min-width:100px; position: sticky; background-color: #f9f9f9; left: 500px; top: 1px; z-index: 10;">
				    <div class="btnDeptAdd">
				    	<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_downDeptRow(`+deptLevl+` , ` +itemId+ `);">추가</a>
				    	<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_delDeptRow( `+itemId+`  , '');">삭제</a>
				    </div>	
				    </td>
				    <td style="text-align: left; min-width:80px; position: sticky; background-color: #f9f9f9; left: 600px; top: 1px; z-index: 10; border-right: 1px solid #eee;">
					<span attr = "proRate" >  </span>	    			    				        	 
		         	</td>
					<td style="text-align: left; min-width:50px; position: sticky; background-color: #f9f9f9; left: 680px; top: 1px; z-index: 10; border-right: 1px solid #eee;">
	        		<div class="clickable-area top" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">계획</div>
	        		<div class="clickable-area bottom" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">실적</div>					    				        	 
		         	</td>`;
		        
			       
        addrow +=	fn_calRowAdd(); // 일정 td 추가 
		                
        addrow += `</tr>`;			
		
        $("#dataList").append(addrow);
		 
        fn_setRow($("#dataList tr:last-child")[0]);  // 추가한거 선택해주려고 ...
        
        fn_sortTable();
  
} 
  
// 하위 항목 생성  
function fn_downDeptRow(depth , prtsId  ){
	var idPlus = 0;  // 아이디 증가값
	var itemId = 0;
	
	var deptLevl = parseInt(depth); 
    var itemPrtsId = parseInt(prtsId);

    var pressTr =  10;  //들여쓰기값
   
    if(depth == 1){
    	idPlus = 1000;
    	pressTr =  40;
    }else if(depth == 2){
    	idPlus = 100;
    	pressTr =  80;
    }else if(depth == 3){
    	idPlus = 10;
    	pressTr =  120;
    }else if(depth == 4){
    	idPlus = 1;
    	pressTr =  160;
    } 
      
  	
   	itemId =itemPrtsId +idPlus;
   	
    var itemList = $('#dataList tr').toArray(); // tr  배열로 변환
    
   	itemList.some(function(tr) {
   	    var $tr = $(tr);

   	    var itemIdChk = $tr.find('input[attr="item_id"]');
   	    
   	    // input 요소의 값 추출
   	    var chkVal = parseInt(itemIdChk.val());
   	    
   	    if (chkVal === itemId) {
   	        itemId+=idPlus; // itemId 증가
  
   	    }
   	});

	var addrow = `<tr  onclick="javascript:fn_setRow(this);">`;										   
		addrow +=	`<td style="text-align: left;display:none;">
				    	<input type="hidden" value = "`+itemPrtsId+`"  attr = "item_prts_id" />
				        <input type="hidden" value = "I"  attr = "item_state"/>
		        		<input type="hidden" value = "`+itemId+`"  attr = "item_id" class="wbsInput"  />
		        		<input type="text" value = ` + deptLevl+ `  attr = "item_levl" class="wbsInput" />
				    </td>  										    			
				    <td style="text-align: left;padding-left: `+pressTr +`px; min-width:300px;  position: sticky; background-color: #f9f9f9; left: 0; top: 1px; z-index: 10;"><input type="text" value = ""  attr = "item_nm" style="text-align: left;"  class="wbsInput_item" /></td>   					    																																		    													   
				    <td style="min-width:200px; position: sticky; background-color: #f9f9f9; left: 300px; top: 1px; z-index: 10;">
					    <div class="btnResp">
					    	<input type="hidden" value = ""  attr = "item_resp" class="wbsInput" />
					    	<input type="text" value = ""  attr = "item_resp_nm" readonly="readonly" class="wbsInput" />
				    		<a href="javascript:void(0);" class="btn btn-dept" onclick="javascript:fn_respPop(`+itemId+`);"><i class="fas fa-search last-child"></i></a>
					    </div>
				    </td>   																																		    													    
				    <td style="min-width:100px; position: sticky; background-color: #f9f9f9; left: 500px; top: 1px; z-index: 10;">`;
				
			    if(depth == 1){
	    			addrow += `<div class="btnDeptAdd">
					    	<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_downDeptRow(` +(deptLevl +1)+` , ` +itemId+ `);">추가</a>`;
			    }else{
			    	addrow += `<div class="btnDept">`;
			    }			
			    addrow += `<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_delDeptRow( `+itemId+`   , `+itemPrtsId+`);">삭제</a>`;
    	addrow +=	`</td>`;
    	addrow += `<td style="text-align: center; min-width:80px; position: sticky; background-color: #f9f9f9; left: 600px; top: 1px; z-index: 10; border-right: 1px solid #eee;">
    					<span attr = "proRate" >  </span>
	  				  </td>`;
    	addrow += `<td style="text-align: left;min-width:50px; position: sticky; background-color: #f9f9f9; left: 680px; top: 1px; z-index: 10; border-right: 1px solid #eee;">
					<div class="clickable-area top" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">계획</div>
					<div class="clickable-area bottom" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">실적</div>					    				        	 
			    </td>`;
    	
		addrow +=	fn_calRowAdd(); // 일정 td 추가 
		       
         
        addrow += `</tr>`;			
		
       
       	$("#dataList").append(addrow);	
        
      
       	fn_sortTable();

}


//항목 삭제
//item_id 자신의 id
//item_prts_id 자신의 부모 id 
function fn_delDeptRow(item_id, item_prts_id) {

	fn_showCustomConfirm("warning","하위 항목이 존재하면 같이 삭제 됩니다.\n삭제 하시겠습니까?(저장 후 최종 반영)", function() {
		
			var itemList = $('#dataList tr').toArray(); // tr 요소 배열로 변환
			   
		    // 계층적으로 하위 요소를 찾아서 삭제
		      // 재귀적으로 subItems을 돌면서 호출된다 다끝나면 subItems 의 루프가 일반처럼 실행되며 deleteSubItems 하위의 작업이 이루어진다. 
		    function deleteSubItems(parentId) { 
		    	
		        // parentId와 일치하는 item_prts_id를 가진 하위 요소들 찾기
		        var subItems = itemList.filter(function(tr) {
		            var itemPrtsId = $(tr).find('input[attr="item_prts_id"]').val();
		            return itemPrtsId == parentId;
		        });
		        
		      
		        subItems.forEach(function(tr) {
		        	
		            var $tr = $(tr);
		            var itemId = $tr.find('input[attr="item_id"]').val();
		            var itemState = $tr.find('input[attr="item_state"]');
		            
		            // 재귀적으로 하위 요소 삭제
		         
		            deleteSubItems(itemId);
		            
		            
		            // 현재 요소의 상태를 D로 설정 (삭제)
		            itemState.val('D');
		            $tr.find('td').css('display', 'none'); // 모든 요소 숨기기
		        });
		    }
		    
		    // 최상위 tr 요소 찾기
		    var topLevelTr = itemList.find(function(tr) {
		        var itemId = $(tr).find('input[attr="item_id"]').val();
		        return itemId == item_id;
		    });
		    
		    if (topLevelTr) {
		        // 최상위 tr 요소를 삭제하기 전에 하위 요소들을 모두 삭제
		        var topLevelItemId = $(topLevelTr).find('input[attr="item_id"]').val();
		        deleteSubItems(topLevelItemId);
		        
		        // 최상위 요소의 상태를 D로 설정 (삭제)
		        var topLevelItemState = $(topLevelTr).find('input[attr="item_state"]');
		        topLevelItemState.val('D');
		        $(topLevelTr).find('td').css('display', 'none'); // 모든 요소 숨기기
		    }

	});
}


// 테이블을 정렬한다 itemid  순으로
function fn_sortTable(){
	  
   	// append 후 tr 을 item_id 순으로 정렬한다.str 
  
   	var rows = $('#dataList tr').toArray();
   	
   	rows.sort(function(a, b) {
   	    var itemIdA = parseInt($(a).find('input[attr="item_id"]').val()); // 정수로 변환
   	    var itemIdB = parseInt($(b).find('input[attr="item_id"]').val()); // 정수로 변환
   	    
   	    return itemIdA - itemIdB; // 오름차순 정렬
   	});

   	// 정렬된 tr 요소를 tbody에 다시 추가
   	var tbody = $('#dataList');
   	$.each(rows, function(index, row) {
   	    tbody.append(row);
   	});
	
  // append 후 tr 을 item_id 순으로 정렬한다.end
  
}

// 저장
function fn_wbsSave(){
	fn_showCustomConfirm("question","저장하시겠습니까?", function() {
			 var params = [];
			 var planList = [];
			 var perforList = [];
		     $('#dataList tr').each(function() {
		         var $tr = $(this);
		         var itemStateInput = $tr.find('input[attr="item_state"]');
		         
		         if (itemStateInput.val() === "I" || itemStateInput.val() === "D"|| itemStateInput.val() === "U") {
		             var itemParams = {};
		             var calParams = {};
		             $tr.find('input').each(function() {
		                 var calAttrName = $(this).attr('bindattr');
		                 var attrName = $(this).attr('attr');
		                 var attrValue = $(this).val();
		            	 if(isEmpty(calAttrName)){  //bindattr 속성 없는거만
		 
		            		itemParams[attrName] = attrValue; 
		            	 }
		            	
		             });
		             params.push(itemParams); 
		           
		             $tr.find('td').each(function() {
		                 var $td = $(this);
		
		               
		                 var planInput = $td.find('input[bindattr="plan"]');
		                 if (planInput.val() === "plan") {
		                     var planInput = $td.find('input[bindattr="plan"]');
		                     var planKeyInput = $td.find('input[bindattr="planKey"]');
		                     var planItemIdInput = $td.find('input[bindattr="planItemId"]');
		                     var planYmInput = $td.find('input[bindattr="planYm"]');
		                     var planWeekInput = $td.find('input[bindattr="planWeek"]');
		
		                     // 데이터 객체를 만들어 리스트에 추가합니다
		                     var item = {
		                   		 wbs_sch_id: planKeyInput.val(),
		                   		 wbs_sch_gbn: planInput.val(),
		                   		 item_id: planItemIdInput.val(),
		                         wbs_sch_ym: planYmInput.val(),
		                         wbs_sch_week: planWeekInput.val()
		                     };
		                     planList.push(item);
		                 }
		                 
		            
		                 var perforInput = $td.find('input[bindattr="perfor"]');
		                 if (perforInput.val() === "perfor") {
		                	 
		                     var perforInput = $td.find('input[bindattr="perfor"]');
		                     var perforKeyInput = $td.find('input[bindattr="perforKey"]');
		                     var perforItemIdInput = $td.find('input[bindattr="perforItemId"]');
		                     var perforYmInput = $td.find('input[bindattr="perforYm"]');
		                     var perforWeekInput = $td.find('input[bindattr="perforWeek"]');
		
		                     // 데이터 객체를 만들어 리스트에 추가합니다
		                     var item = {
		                    		 wbs_sch_id: perforKeyInput.val(),
		                    		 wbs_sch_gbn: perforInput.val(),
		                    		 item_id: perforItemIdInput.val(),
		                    		 wbs_sch_ym: perforYmInput.val(),
		                    		 wbs_sch_week: perforWeekInput.val()
		                     };
		                     perforList.push(item);
		                 }
		             });
		         }
		     });
		     
		 	
		 	
		     var requestData = {
		         itemList: params,
		         planList: planList,
		         perforList: perforList,
		         proj_year_id:proj_year_id
		     };
		
		    $.ajax({
		        url: '${ctxt}/execute/wbsMng/insertWbsItem.do',
		        data: JSON.stringify(requestData),
		        type: 'POST', 
		    	cache: false, 	
		    	 async : false,
			    contentType: 'application/json', // Content-Type을 설정합니다.
		    	dataType : 'json',    
				success: function(result){			
					
				
					if(result.sMessage == 'Y'){
						fn_showCustomAlert("저장이 완료 되었습니다.");
						
						//setTimeout(function() { location.reload(); }, 3000);
						//모두 클리어
							$('#dataList').children().remove();
							$('#colG').nextAll('col').remove();
						 	$('#topTh').nextAll('th').remove();
						 	$('#bottomTh').nextAll('th').remove();
						 	addScheRowSet ='';
							console.log("2222222222");
							fn_searchCal();
							
							fn_headerColspan();
							
							fn_searchItem();
							fn_searchItemProgressRate();
							fn_searchSchedule();
							console.log("555555");
							
					}else {
						fn_showCustomAlert("로그인 시간이 만료 되었습니다. 다시 로그인 해주세요.");
					}
		
					
				},
				error: function(request,status,error) {
					fn_showCustomAlert("에러가 발생하였습니다.");
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
		  	  });
	});
}

// 일정 영역의 이벤트를 제어
function fn_calClickEvent() {
    var isDragging = false;
    var isTop = false;

    $(document).on('mousedown', '.clickable-area.top, .clickable-area.bottom', function(e) {
        isDragging = true;
        var $this = $(this);
        isTop = $this.hasClass('top');

        applyHighlight($this, isTop);
    });

    $(document).on('mouseenter', '.clickable-area.top, .clickable-area.bottom', function(e) {
        if (isDragging) {
            var $this = $(this);
            if (isTop === $this.hasClass('top')) {
                applyHighlight($this, isTop);
            }
        }
    });

    $(document).on('mouseup', function(e) {
        isDragging = false;
    });

    function applyHighlight($this, isTop) {
        var $tr = $this.closest('tr');
        var itemId = $tr.find('input[attr="item_id"]').val();
        var itemState = $tr.find('input[attr="item_state"]').val();
        var item_levl = $tr.find('input[attr="item_levl"]').val();

        if (itemState != "I") {
            $tr.find('input[attr="item_state"]').val("U");
        }

        if ($this.hasClass('highlighted')) {
            $this.removeClass('highlighted').css('background-color', 'white');
            
            if (isTop) {
                $this.find('input[bindattr="plan"]').val("");
                $this.find('input[bindattr="planItemId"]').val("");
            } else {
                $this.find('input[bindattr="perfor"]').val("");
                $this.find('input[bindattr="perforItemId"]').val("");
            }
        } else { 
            $this.addClass('highlighted').css('background-color', isTop ? '#FF9800' : '#00adff');
            if (isTop) {
                $this.find('input[bindattr="plan"]').val("plan");
                $this.find('input[bindattr="planItemId"]').val(itemId);
            } else {
                $this.find('input[bindattr="perfor"]').val("perfor");
                $this.find('input[bindattr="perforItemId"]').val(itemId);
            }
        }
    }
}



function fn_respPop( itemId){
	$("#wbsRespLayer").children().remove();
	var layerHtml = "";
	var itemList = $('#dataList tr').toArray(); // tr  배열로 변환
	  var itemRespList = "";
	itemList.some(function(tr) {
	    var $tr = $(tr);

	    var itemIdChk = $tr.find('input[attr="item_id"]');
	    
	    // input 요소의 값 추출
	    var chkVal = parseInt(itemIdChk.val());
	  
	    if (chkVal === itemId) {
	    	
	    	  var itemRespId = $tr.find('input[attr="item_resp"]');
	    	  var itemRespNm = $tr.find('input[attr="item_resp_nm"]');
	    	  var itemState = $tr.find('input[attr="item_state"]');
	    	
	    	  itemRespList = itemRespId.val();
	    	  itemRespList = itemRespList.split(',');
	    	  
			  return true;
	    }
	});
	//드래그 가능한 div
	layerHtml +=`<div class="draggable-div" style="width: 250px; inset: 224px auto auto 804px; height: auto;z-index:99;">				
						<div class= "form-group">
							<label for="inputText" class="form-label"><strong>담당자 확인</strong></label>
					
							<c:forEach items="${respWbsList}" var="resp" varStatus ="varS">
								<div class="" style = "display: grid;  grid-template-columns: 50px 100px;justify-content: center;margin-bottom: 10px;">								
									  <input type="checkbox" id="${resp.year_resp_cd}" name="year_resp_cd" value="${resp.year_resp_cd}" respAttr = "${resp.resp_nm}"  > <label for="${resp.year_resp_cd}" >${resp.resp_nm}</label>		  
								</div>
							</c:forEach> 
						
							<div class="ma_t_30">						

				    		<a href="javascript:fn_choiceResp(`+itemId+`);" class="btn btn-secondary">선택</a>
					    		<a href="javascript:closeRespPop();" class="btn btn-secondary">닫기</a>
							</div>
						</div>
				 </div>`;

	$("#wbsRespLayer").html(layerHtml);
		 
	$(".draggable-div").draggable();
	
	
   
       $("input[name='year_resp_cd']").each(function(e){
    	   var checkedVal =  $(this);
	       $.each(itemRespList, function(index, value) {
	       		if(value == checkedVal.val()){
	       			checkedVal.prop('checked', true);
	       		}
	       })
       });
   

  
}


function fn_choiceResp (itemId){
	var itemList = $('#dataList tr').toArray(); // tr  배열로 변환
	var checkArrId = [];
	var checkArrNm = [];
	
	itemList.some(function(tr) {
	    var $tr = $(tr);

	    var itemIdChk = $tr.find('input[attr="item_id"]');
	    
	    // input 요소의 값 추출
	    var chkVal = parseInt(itemIdChk.val());
	    
	    if (chkVal === itemId	) {
	    	
	    	  var itemRespId = $tr.find('input[attr="item_resp"]');
	    	  var itemRespNm = $tr.find('input[attr="item_resp_nm"]');
	    	  var itemState = $tr.find('input[attr="item_state"]');
	    	
	    	  var len = $("input[name='year_resp_cd']:checked").length;
	    	  if(len > 0){ //개수를 체크하고 2개부터는 each함수를 통해 각각 가져온다.
	    	      $("input[name='year_resp_cd']:checked").each(function(e){
	    	    	  var value = $(this).val();
	    	    	  var value2= $(this).attr("respAttr");
	    	    	  checkArrId.push(value);     
	    	    	  checkArrNm.push(value2);     
	    	      })
	    	  }
	    	 if(itemState.val() != 'I'){
	    		 itemState.val("U");
	    	 }
	    	  itemRespId.val(checkArrId);
	    	  itemRespNm.val(checkArrNm);
	    	  
			  return true;
	    }
	});
}

//아이템 선물하기 팝업 닫기 
function closeRespPop(){	     
	 var element = document.getElementById('wbsRespLayer');
	 element.innerText = '';
}


//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////



var addScheRowSetExcel = '';
var addScheRowSet2Excel = '';


//wbs 항목을 조회
function fn_searchItemExcel(){

		var params = {};
		params.proj_year_id = proj_year_id;
  	  	
    $.ajax({
        url: '${ctxt}/execute/wbsMng/readProjWbsMngList.do',
        data: params,
        type: 'POST', 
        async : false, 
		dataType:"json",
        success: function(result) { 
        	$('#excelDataList').children().remove();
        	var html="";
        
        	if(!isEmpty(result)){
				if(!isEmpty(result.wbsList)){ 
					
					
					$.each(result.wbsList, function(idx, item){
						var deptLevl = 1 ;
						var pressTr = 0;
				        if(item.item_levl == 0){
				        	deptLevl  = 1; 
				        	
				        }else{
				        	deptLevl  = item.item_levl+1;
				        	
				        	if(item.item_levl == 1){
				        		pressTr =  40;
			 	        	}else if(item.item_levl == 2){
				        		pressTr =  80;
				        	}else if(item.item_levl == 3){
				        		pressTr =  120;
				        	}else  if(item.item_levl == 4){
				        		pressTr =  160;
				        	}
				        }
				        
						html += '<tr  onclick="fn_setRow(this)">';	   
						    html += '<td rowspan = "2" style="text-align: left;display:none;" >';
						    	html +='<input type="hidden" value="'+item.item_prts_id+'" attr = "item_prts_id" class="wbsInput" />';
						    	html +='<input type="hidden" value = "N"  attr = "item_state" class="wbsInput" />';
						    	html += '<input type="hidden" value="'+item.item_id+'" attr = "item_id"  class="wbsInput" />';
						    html += '</td>';   												    
						    html += '<td rowspan = "2" style="text-align: left;padding-left: '+pressTr +'px; min-width:300px;  position: sticky; background-color: #f9f9f9; left: 0; top: 1px; z-index: 10;">';
						    	html += ''+item.item_nm+'';
						    html +='</td>';   						
				        	html += '<td rowspan = "2" style="min-width:200px; position: sticky; background-color: #f9f9f9; left: 300px; top: 1px; z-index: 10;">';
				        		html += '<div class="btnResp">';
				        			html += ''+item.item_resp_nm+'';
				        			html += '<input type="hidden" value="'+item.item_resp+'" attr = "item_resp"  class="wbsInput" />';
				        		html += '	<a href="javascript:void(0);" class="btn btn-dept" onclick="javascript:fn_respPop(' +item.item_id+ ');"><i class="fas fa-search last-child"></i></a>';
				        		html += '</div>'
				        		
				        	html+= '</td>';
				        	html += '<td rowspan = "2" style="text-align: center; min-width:50px; position: sticky; background-color: #f9f9f9; left: 600px; top: 1px; z-index: 10; border-right: 1px solid #eee;">';
			        		html += '<span attr = "proRate" >  </span>';		    				        	 
				       		 html += '</td>';
			        		html += '<td rowspan = "2" style="text-align: left; min-width:50px; position: sticky; background-color: #f9f9f9; left: 650px; top: 1px; z-index: 10; border-right: 1px solid #eee;">';
				        		html += '<div class="clickable-area top" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">계획</div>';
				        		html += '<div class="clickable-area bottom" style =  "background-color: #f9f9f9; line-height: 25px; text-align: center;">실적</div>';					    				        	 
					        html += '</td>';
					        
					        
						  html +=  fn_calRowAddExcel();
												
						html += '</tr>'
							html += '<tr>'
	  		    
				        
					  html +=  fn_calRowAdd2Excel();
											
					html += '</tr>'
						;				
											
					});
					
				 
					
				 	$('#excelDataList').html(html);
				 	 
				 
				 	
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
				
					
				
				}
			}else{
			
				
			 
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        
        	$("#loading-spinner").hide();
        },
        complete : function (){
        	
        }
    }); 
}



//wbs 항목별 진척율 조회
function fn_searchItemProgressRateExcel(){

		var params = {};
		params.proj_year_id = proj_year_id;
	  	
$.ajax({
    url: '${ctxt}/execute/wbsMng/readWbsProRateList.do',
    data: params,
    type: 'POST', 
    async : false, 
		dataType:"json",
    success: function(result) { 
    	var html="";
    
    	if(!isEmpty(result)){
				if(!isEmpty(result.wbsProRateList)){ 
					
			   	    var $rows = $('#excelDataList tr'); // 모든 행을 한번만 선택
			   	    
					$.each(result.wbsProRateList, function(idx, rate){
						 $rows.each(function() {
							   var $tr = $(this);
					           var itemId = $tr.find('input[attr="item_id"]');
					           var itemProRate = $tr.find('span[attr="proRate"]');
					           
					           if(rate.item_id ==itemId.val()){
					        	   itemProRate.text(rate.item_ratio);
					           } 
					           
						 });
											
					});

				
				 	 
				 
				 	
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/

				}
			}else{

			}
    },
    error : function(){                              // Ajax 전송 에러 발생시 실행
    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
    	$("#loading-spinner").hide();
    },
    complete : function (){
    	
    }
}); 
}

// wbs 일정을 조회
function fn_searchScheduleExcel(){
	
	var params = {};
	params.proj_year_id = proj_year_id;
	
$.ajax({
    url: '${ctxt}/execute/wbsMng/readWbsScheduleList.do',
    data: params,
    type: 'POST',
   async : false, 
	dataType:"json",
    success: function(result) {
    	var html="";
    
    	if(!isEmpty(result)){
			if(!isEmpty(result.wbsScheduleList)){ 
				   var $rows = $('#excelDataList tr'); // 모든 행을 한번만 선택
				
				$.each(result.wbsScheduleList, function(idx, sItem){
						
					 $rows.each(function() {
				         var $tr = $(this);

				         var $previousTr =$tr.prev('tr');  // 바로 이전 형제 tr 요소

				         var itemId = $tr.find('input[attr="item_id"]');
				         var previtemId = $previousTr.find('input[attr="item_id"]');
				         	if(itemId.val() == sItem.item_id ){
				             $tr.find('td').each(function() {
				            	 var $td = $(this);
				            	
				                 var planVal = $td.find('input[bindattr="plan"]');
				                 var planKeyVal = $td.find('input[bindattr="planKey"]');
				                 var planItemIdVal = $td.find('input[bindattr="planItemId"]');
				                 var planYmVal = $td.find('input[bindattr="planYm"]');
				                 var planWeekVal = $td.find('input[bindattr="planWeek"]');
				                
				                 var perforVal = $td.find('input[bindattr="perfor"]');
				                 var perforKeyVal = $td.find('input[bindattr="perforKey"]');
				                 var perforItemIdVal = $td.find('input[bindattr="perforItemId"]');
				                 var perforYmVal = $td.find('input[bindattr="perforYm"]');
				                 var perforWeekVal = $td.find('input[bindattr="perforWeek"]');
				               
				           
				                 if(planYmVal.val() == sItem.wbs_sch_ym &&  planWeekVal.val() == sItem.wbs_sch_week  ){
				                	 if( sItem.wbs_sch_gbn == 'plan'){
				                	//	 planVal.val('plan');
				                	//	 planKeyVal.val(sItem.wbs_sch_id);					                	
				                		//  planItemIdVal.val(sItem.item_id);
				                		  
					                	 // $td.find('div[class="clickable-area top"]').attr('data-programmatic-click', 'true').click(); // 셀 색깔
				                		  //$td.hasClass('clickable-area top').addClass('highlighted').css('background-color', '#FF9800');
				                		  if ($td.hasClass('clickable-area') && $td.hasClass('top')) {
				                		        // 'highlighted' 클래스를 추가하고 배경색을 변경합니다.
				                		       $td.addClass('highlighted').css('background-color', '#FF9800');
				                		    }
				                	 }
				                			            	 			                 
				                 }
				                 
				                 if(  perforYmVal.val() == sItem.wbs_sch_ym &&  perforWeekVal.val() == sItem.wbs_sch_week  ){
				                	 if( sItem.wbs_sch_gbn == 'perfor'){
				                		// perforVal.val('perfor');
				                		// perforKeyVal.val(sItem.wbs_sch_id);
				                		// perforItemIdVal.val(sItem.item_id);
				                		
					                 	// $td.find('div[class="clickable-area bottom"]').attr('data-programmatic-click', 'true').click();   	//셀 색깔	 
				                		//  $td.hasClass('clickable-area bottom').addClass('highlighted').css('background-color', '#00adff');
				                		  if ($td.hasClass('clickable-area') && $td.hasClass('bottom')) {
				                		        // 'highlighted' 클래스를 추가하고 배경색을 변경합니다.
				                		        $td.addClass('highlighted').css('background-color', '#00adff');
				                		    }
				                	 }
				                			            	 			                 
				                 } 
				             });
				         	}
				         	
				        	if(previtemId.val() == sItem.item_id ){
				        		$tr.find('td').each(function() {
					            	 var $td = $(this);
					            	
					                 var planVal = $td.find('input[bindattr="plan"]');
					                 var planKeyVal = $td.find('input[bindattr="planKey"]');
					                 var planItemIdVal = $td.find('input[bindattr="planItemId"]');
					                 var planYmVal = $td.find('input[bindattr="planYm"]');
					                 var planWeekVal = $td.find('input[bindattr="planWeek"]');
					                
					                 var perforVal = $td.find('input[bindattr="perfor"]');
					                 var perforKeyVal = $td.find('input[bindattr="perforKey"]');
					                 var perforItemIdVal = $td.find('input[bindattr="perforItemId"]');
					                 var perforYmVal = $td.find('input[bindattr="perforYm"]');
					                 var perforWeekVal = $td.find('input[bindattr="perforWeek"]');
					               
					           
					                 if(planYmVal.val() == sItem.wbs_sch_ym &&  planWeekVal.val() == sItem.wbs_sch_week  ){
					                	 if( sItem.wbs_sch_gbn == 'plan'){
					                	//	 planVal.val('plan');
					                	//	 planKeyVal.val(sItem.wbs_sch_id);					                	
					                		//  planItemIdVal.val(sItem.item_id);
					                		  
						                	 // $td.find('div[class="clickable-area top"]').attr('data-programmatic-click', 'true').click(); // 셀 색깔
					                		  //$td.hasClass('clickable-area top').addClass('highlighted').css('background-color', '#FF9800');
					                		  if ($td.hasClass('clickable-area') && $td.hasClass('top')) {
					                		        // 'highlighted' 클래스를 추가하고 배경색을 변경합니다.
					                		       $td.addClass('highlighted').css('background-color', '#FF9800');
					                		    }
					                	 }
					                			            	 			                 
					                 }
					                 
					                 if(  perforYmVal.val() == sItem.wbs_sch_ym &&  perforWeekVal.val() == sItem.wbs_sch_week  ){
					                	 if( sItem.wbs_sch_gbn == 'perfor'){
					                		// perforVal.val('perfor');
					                		// perforKeyVal.val(sItem.wbs_sch_id);
					                		// perforItemIdVal.val(sItem.item_id);
					                		
						                 	// $td.find('div[class="clickable-area bottom"]').attr('data-programmatic-click', 'true').click();   	//셀 색깔	 
					                		//  $td.hasClass('clickable-area bottom').addClass('highlighted').css('background-color', '#00adff');
					                		  if ($td.hasClass('clickable-area') && $td.hasClass('bottom')) {
					                		        // 'highlighted' 클래스를 추가하고 배경색을 변경합니다.
					                		        $td.addClass('highlighted').css('background-color', '#00adff');
					                		    }
					                	 }
					                			            	 			                 
					                 } 
					             });
					         	}
				        
				     });
				});
				
			 
			
			
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
			
				
			
			}
		}else{
		
			
		
		}
    },
    error : function(){                              // Ajax 전송 에러 발생시 실행
    	console.log('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
    	$("#loading-spinner").hide();
    },
    complete : function (){
    	
    }
});
}
// wbs 헤더 정보를 구성한다. th  colgroup  일정해더
function fn_searchCalExcel(){
	

	var params = {};
	 	params.proj_year_id = proj_year_id;
	  	
$.ajax({
    url: '${ctxt}/execute/wbsMng/readWbsCalendarList.do',
    data: params,
    type: 'POST',
    async : false,
    dataType: 'json',
    //processData: false,
     beforeSend: function () {
    		$("#loading-spinner").show();
    },
    success: function(result) {
	    	var topThExcel ='';
	    	var bottomThExcel ='';	
	    	var colGroup ='';	  
	    	if(!isEmpty(result)){
				if(!isEmpty(result.wbsCalendarList)){
				
					$.each(result.wbsCalendarList, function(idx, item){
						
						topThExcel += '<th scope="col" class="wbsThLine">' +item.ym+ '</th>';
																		
						bottomThExcel +=	'<th scope="col" class="wbsThLine">' +item.week+ '</th>';
						colGroup +=	'<col style="width: ' +3 + 'px;">';		
					
					
						addScheRowSetExcel +=	 `<td style="text-align: left;" class= "wbsline clickable-area top">											
					    		  				
					    		  					<input type="hidden" value ="" attr ="plan`+idx+`"  bindattr = "plan"  >
					    		  					<input type="hidden" value ="" attr ="planKey`+idx+`"  bindattr = "planKey" >
					    		  					<input type ="hidden" value = "" attr ="planItemId`+idx+`" bindattr = "planItemId" /> 
													<input type ="hidden" value = `+ item.ym +` attr ="planYm`+idx+`" bindattr = "planYm" /> 
													<input type ="hidden" value = `+ item.week +` attr ="planWeek`+idx+`"  bindattr = "planWeek"//>
											 </td> `;
						addScheRowSet2Excel +=	 `<td  style="text-align: left;" class= "wbsline clickable-area bottom">
             		  		
         		  			<input type="hidden" value = "" attr = "perfor`+idx+`" bindattr = "perfor"  >
         		  			<input type="hidden" value = "" attr = "perforKey`+idx+`" bindattr = "perforKey" >
							<input type ="hidden" value = "" attr ="perforItemId`+idx+`" bindattr = "perforItemId" /> 
							<input type ="hidden" value = `+ item.ym +` attr ="perforYm`+idx+ `" bindattr = "perforYm" /> 
							<input type ="hidden" value = `+ item.week +` attr ="perforWeek`+idx+`" bindattr = "perforWeek"/ />
         		  
         			 	 </td>`;    	 
					             		 	 
						
					});
	
					$('#colGExcel').after(colGroup);
				 	$('#topThExcel').after(topThExcel);
				 	$('#bottomThExcel').after(bottomThExcel);
				 	
				}	
			}
    	},
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    	$("#loading-spinner").hide();
	    },
	    complete : function (){
	  
	    }
	});
}
 


// 일정 td 추가 
function fn_calRowAddExcel(){
	
		return addScheRowSetExcel;
}
function fn_calRowAdd2Excel(){
	
	return addScheRowSet2Excel;
}

function fn_printExcel(){
	
	fn_searchCalExcel();
	
	fn_headerColspan();
	
	fn_searchItemExcel();
	
	fn_searchItemProgressRateExcel();
	
	fn_searchScheduleExcel();
	
	setTimeout(function() { 
	// 원본 table html은 안건드려야한다.
	var tableCopy = $("#excelWbsDiv").clone();


	//tableCopy.find("th[style*='display: none'], td[style*='display: none']").remove();
	tableCopy.find("th[style*='display: none'], td[style*='display: none']").each(function() {
	    $(this).css("display", "block");
	});
	tableCopy.find("input[type*='hidden']").remove();
	tableCopy.find("input[type*='text']").remove();
	tableCopy.find("th, td, tr").removeAttr("onclick");
	tableCopy.find("a").removeAttr("onclick").removeAttr("href");
	
	
	var url = "${ctxt}/execute/wbsMng/excelDown.do";

	$("#printTitle").val("WBS"); // 현재 페이지 타이틀 정보
	$("#printHead").val("22"); // 현재 페이지 테이블 제목
	$("#printBody").val(tableCopy.html()); // 현재 페이지 테이블 내용
	
	$('#excelDataList').children().remove();
	$('#colGExcel').nextAll('col').remove();
 	$('#topThExcel').nextAll('th').remove();
 	$('#bottomThExcel').nextAll('th').remove();
 	addScheRowSetExcel = '';
 	addScheRowSet2Excel = '';
 	
	document.outputFrm.action = url;
	document.outputFrm.submit();
	
	$("#loading-spinner").hide();
	},2000);
	
			  
}

</script>
<form id="outputFrm" name="outputFrm" method="post">
	<input type="hidden" id="printTitle" name="printTitle">
	<input type="hidden" id="printHead" name="printHead">
	<input type="hidden" id="printBody" name="printBody">
	<input type="hidden" name="searchCondition">
</form>

<div id="container">
	<div id="divRefreshArea">
			
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>WBS</h3>
	  
			<div align="left">
		  		
					<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_topDeptRow();" >WBS 최상위 항목 추가</a>							
					<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_wideContent();" >화면 확대/축소</a>
					<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_printExcel();" >Excel</a>
					<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_wbsSave();" >저장</a>
					<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
			</div>
		
		
		<!--게시판--> 
		<div class="admin">

			  
			<table class="table_h wbsTable" cellpadding="0" cellspacing="0" border="0"  >

				<caption>
				  wbs
				</caption>
				<colgroup>
					<col style="width:13%;">				
					<col style="width:85px;">								
					<col style="width:4%;" >
					<col style="width:2%;" >
					<col style="width:2%;" id="colG">
												
				</colgroup> 
				<thead class="wbsThead">
					<tr>
						<th colspan="5" id ="topTh" style="position: sticky; min-width: 650px; top: 0; left: 0; z-index: 11;">항목</th>
					</tr>
					<tr>				
						<th scope='col' style="min-width:300px; position: sticky; left: 0; top: 50px; z-index: 11;">명</th>								
						<th scope='col' style="min-width:200px; position: sticky; left: 300px; top: 50px; z-index: 11;">담당</th>												
						<th scope='col' style="min-width:100px; position: sticky; left: 500px; top: 50px; z-index: 11;"></th>
						<th scope='col' style="min-width:80px; position: sticky; left: 600px; top: 50px; z-index: 11;">진척률(%)</th>
						<th scope='col' style="min-width:50px; position: sticky; left: 680px; top: 50px; z-index: 11;" id ="bottomTh">구분</th>		
					<tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
			
		</div>
	</div>
</div>  
	
<div id="wbsRespLayer">
	</div>
	
	

<div id="excelWbsDiv" style="display: none;">

			  
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="excelWbsTable">

				
				<colgroup>
					<col style="width:1%;">				
					<col style="width:13%;">				
					<col style="width:13%;">																					
					<col style="width:4%;">																					
					<col style="width:5%;" id="colGExcel">
												
				</colgroup> 
				<thead >
					<tr>
						<th colspan="5" id ="topThExcel" style="position: sticky; min-width: 650px; top: 0; left: 0; z-index: 11;">항목</th>
					</tr>
					<tr>				
						<th scope='col' style="min-width:0px; position: sticky; left: 0; top: 50px; z-index: 11;"></th>								
						<th scope='col' style="min-width:300px; position: sticky; left: 0; top: 50px; z-index: 11;">명</th>								
						<th scope='col' style="min-width:200px; position: sticky; left: 300px; top: 50px; z-index: 11;">담당</th>												
				     	<th scope='col' style="min-width:50px; position: sticky; left: 500px; top: 50px; z-index: 11;">진척률(%)</th>
						<th scope='col' style="min-width:50px; position: sticky; left: 550px; top: 50px; z-index: 11;" id ="bottomThExcel">구분</th>		
					</tr>
				</thead>
				<tbody id="excelDataList">
				</tbody>
			</table>
	
</div>  
	