<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.cashArea{
	background-color : #fff4e4 !important;
	}
</style>

<script type="text/javascript"> 

/*  최상의 항목의 코드 단위는 100000 으로 정의한다  10만 단위 */

 

var proj_year_id_budget_exec = '${data.proj_year_id}';

$(function(){ 

	fn_searchAcctSubRowExec();

	
}); 
 



function fn_searchAcctSubRowExec(){
	$("#bgExecList").children().remove();
		var html = '';
		var params = {};
		params.proj_year_id = proj_year_id_budget_exec;
  	  	
    $.ajax({
        url: '${ctxt}/execute/bgExec/readAcctSubjRowList.do',
        data: params,
        type: 'POST', 
     //   async : false, 
		dataType:"json",
        success: function(result) { 
        	if(!isEmpty(result)){
				if(!isEmpty(result.rowAcctSubjList)){
					  
					$.each(result.rowAcctSubjList, function(idx, item){
					
						html+= '<tr>';										
						html+= '<td class="text_l">';
										html+= '<input type ="hidden" name ="budget_id" value = "'+item.budget_id+ '" />';
										html+= '<input type ="hidden" name ="subj_levl" value = "'+item.subj_levl+ '" />';
										html+= '<input type ="hidden" name ="subj_prts_id" value = "'+item.subj_prts_id+ '" />';
										html+= '<input type ="hidden" name ="subj_id" value = "'+item.subj_id+ '" />';										
									if(item.subj_levl == 0){
										html+='<span>' +item.subj_no +'&nbsp;&nbsp;&nbsp;'+ item.subj_nm +'</span>';
									}else if(item.subj_levl == 1){
										html+='<span style="margin-left:20px;">' +item.subj_no +'&nbsp;&nbsp;&nbsp;'+ item.subj_nm +'</span>';
									}else if(item.subj_levl == 2){
										html+='<span style="margin-left:40px;">' +item.subj_no +'&nbsp;&nbsp;&nbsp;'+ item.subj_nm +'</span>';
									}else if(item.subj_levl == 3){
										html+='<span style="margin-left:60px;">' +item.subj_no +'&nbsp;&nbsp;&nbsp;'+ item.subj_nm +'</span>';
									}else{
										html+='<span style="margin-left:80px;">' +item.subj_no +'&nbsp;&nbsp;&nbsp;'+ item.subj_nm +'</span>';
									}
						html+='</td>';					
						html+= '<td class="text_r cashArea">'; 
						
							html += "<input type='text' class='text_r form-control' name='cash_sum_data'  value='" + addComma(item.cash_sum_data) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_execSumRow(this);' disabled />";														
						html+= '</td>';												
						html+= '<td class="text_r">';
							
							html += "<input type='text' class='text_r form-control' name='stock_sum_data'  value='" + addComma(item.stock_sum_data) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_execSumRow(this);' disabled />";	
	
						html+= '</td>';			
						html+= '<td class="text_r cashArea" >';
						if(item.subj_levl == 0){
							html += "<input type='text' class='text_r form-control' name='exec_account'  value='" + addComma(item.exec_account) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_execSumRow(this);' disabled />";	
						}else{
							html += "<input type='text' class='text_r' name='exec_account'  value='" + addComma(item.exec_account) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_execSumRow(this);' />";
						}
					
					
						html+= '</td>';				
						html+= '<td class="text_r">';
						if(item.subj_levl == 0){
							html += "<input type='text' class='text_r form-control' name='stock_exec_account'  value='" + addComma(item.stock_exec_account) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_execSumRow(this);' disabled />";	
						}else{
							html += "<input type='text' class='text_r' name='stock_exec_account'  value='" + addComma(item.stock_exec_account) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_execSumRow(this);' />";
						}
					
					
						html+= '</td>';				
						html+= '<td class="text_r cashArea" attr = "cash_remain_account">' + addComma(item.cash_remain_account) +'</td>';			
						html+= '<td class="text_r" attr = "stock_remain_account">' + addComma(item.stock_remain_account) +'</td>';			
						html+= '<td class="cashArea" attr = "cashRatio"></td>';			
						html+= '<td  attr = "stockRatio"></td>';			
						html+= '<td>';
							if(item.subj_levl > 0){
								html+= '<input type ="text" name ="exec_etc" value = "'+ ((isEmpty(item.exec_etc)) ? '' : item.exec_etc) + '" />';  
							}
						html+= '</td>';	
						html+= '</tr>'; 
					});
					html+= '<tr id ="execSumRow" style="background: blanchedalmond;" >';
						html+= '<td>합&nbsp;&nbsp;&nbsp;&nbsp;계</td>';			
						html+= '<td id = "cash_sum" class="text_r"></td>';				
						html+= '<td id = "stock_sum" class="text_r"></td>';				
						html+= '<td id = "exec_cach_sum" class="text_r"></td>';
						html+= '<td id = "exec_stock_sum" class="text_r"></td>';
						html+= '<td id = "cash_remain_sum" class="text_r"></td>';
						html+= '<td id = "stock_remain_sum" class="text_r"></td>';
						html+= '<td id = "exec_cash_ratio_sum" ></td>';
						html+= '<td id = "exec_stock_ratio_sum" ></td>';
						html+= '<td></td>';
					html+= '</tr>';
					
					$('#bgExecList').html(html);
					
					// 입력할때 천단위 콤마
					$('input[name="exec_account"]' , 'input[name="stock_exec_account"]').on('input', function(event) {
				        //  숫자와 쉼표만 남기고 모두 제거
				        var value = $(this).val().replace(/[^\d,]/g, '');	        
				        // 쉼표(,) 제거
				        value = value.replace(/,/g, '');	        
				        // 천 단위마다 쉼표 추가
				        var formatVal = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');	        
				        
				        $(this).val(formatVal);
		  		    });
					
			
					var total_cash = 0;					
					var total_stock = 0;
			
					var total_cash_exec_account = 0;
					var total_stock_exec_account = 0;
				
					var total_cash_remain_account = 0;
					var total_stock_remain_account = 0;
				
					var sum_total = 0;
				    $('#bgExecList tr').each(function() {
				         var $tr = $(this);
				         
				        if( $tr.attr("id") != 'execSumRow' ){
				        	var subj_levl = $tr.find('input[name="subj_levl"]');
				        	
				     
				            
				        	var cash_sum_data_input = $tr.find('input[name="cash_sum_data"]');
				        	var stock_sum_data_input = $tr.find('input[name="stock_sum_data"]');
				        	
				            var cash_exec_account_input = $tr.find('input[name="exec_account"]');
				            var stock_exec_account_input = $tr.find('input[name="stock_exec_account"]');
				        
				            var cash_remain_td = $tr.find('td[attr="cash_remain_account"]');
				            var stock_remain_td = $tr.find('td[attr="stock_remain_account"]');
			         		
				            if(subj_levl.val() == 0){
				            	
					            if(!isNaN(parseInt(uncomma(cash_sum_data_input.val()))))  {
					            	total_cash += parseInt(uncomma(cash_sum_data_input.val()));	
				         		}				         		
				         	
				         		if(!isNaN(parseInt(uncomma(stock_sum_data_input.val()))))  {
				         			total_stock += parseInt(uncomma(stock_sum_data_input.val()));	
				         		}			
				         		
				         		if(!isNaN(parseInt(uncomma(cash_exec_account_input.val())))){
				         			total_cash_exec_account += parseInt(uncomma(cash_exec_account_input.val()));	
				         		}
				         		
				         		if(!isNaN(parseInt(uncomma(stock_exec_account_input.val())))){
				         			total_stock_exec_account += parseInt(uncomma(stock_exec_account_input.val()));	
				         		}
				         	
				         		if(!isNaN(parseInt(uncomma(cash_remain_td.text())))){
				         			total_cash_remain_account += parseInt(uncomma(cash_remain_td.text()));	
				         		}
				         		
				         		if(!isNaN(parseInt(uncomma(stock_remain_td.text())))){
				         			total_stock_remain_account += parseInt(uncomma(stock_remain_td.text()));	
				         		}
				            }
				            
				            fn_execSumRow(cash_sum_data_input);
				            fn_execSumRow(stock_sum_data_input);
				            fn_execSumRow(cash_exec_account_input);
				            fn_execSumRow(stock_exec_account_input);
				         
				            
				        }
				  
				    });
				    
				    $("#cash_sum").text(addComma(total_cash));				
				    $("#stock_sum").text(addComma(total_stock));
				    $("#exec_cach_sum").text(addComma(total_cash_exec_account));
				    $("#exec_stock_sum").text(addComma(total_stock_exec_account));
				    $("#cash_remain_sum").text(addComma(total_cash_remain_account));
				    $("#stock_remain_sum").text(addComma(total_stock_remain_account));
				
				    
				    fn_execSumRow(); // object 가 없어도 무방하다.  
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


function fn_execSumRow(obj) {
	 // 선택된 row의 기본정보 str
	  var $row = $(obj).closest('tr');
	  var rowCash = $row.find('input[name="cash_sum_data"]');   
	  var rowStock = $row.find('input[name="stock_sum_data"]');   

	  var rowExecCash = $row.find('input[name="exec_account"]');   
	  var rowExecStock = $row.find('input[name="stock_exec_account"]');   
	  
	  
	  
	  var rowsubj_id = $row.find('input[name="subj_id"]');
	  var rowsubj_prts_id = $row.find('input[name="subj_prts_id"]');
	 
      var rowCash_remain_td = $row.find('td[attr="cash_remain_account"]');
      var rowStock_remain_td = $row.find('td[attr="stock_remain_account"]');
   // 선택된 row의 기본정보 end 
   
	// 1. row의 잔액 구하기 str

	  var cashVal = 0;	
	  var stockVal = 0;
	  var execCashVal = 0;
	  var execStockVal = 0;
	    
	  	// row 의 잔액를 구성한다. STR
		if(!isNaN(parseInt(uncomma(rowCash.val())))){
			cashVal =  parseInt(uncomma(rowCash.val()));	
		} 
		if(!isNaN(parseInt(uncomma(rowStock.val())))){
			stockVal =  parseInt(uncomma(rowStock.val()));	
		} 
		if(!isNaN(parseInt(uncomma(rowExecCash.val())))){
			execCashVal =  parseInt(uncomma(rowExecCash.val()));	
		} 
		if(!isNaN(parseInt(uncomma(rowExecStock.val())))){
			execStockVal =  parseInt(uncomma(rowExecStock.val()));	
		} 
	
	
		
		rowCash_remain_td.text(addComma(cashVal - execCashVal));
		rowStock_remain_td.text(addComma(stockVal - execStockVal));
		
		// row 의 잔액를 구성한다. END
  
	// 2. 하위 항목을 입력하면 상위 항목에 SUM값을 표현해준다 STR
		
	   var sumColCash = 0;	
	   var sumColStock = 0;
	   var sumColExecCash = 0;
	   var sumColExecStock = 0;
	   // 하위 값 모두 더하기
	   $('#bgExecList tr').each(function() {
		    
		  var $tr = $(this);

          var subj_id = $tr.find('input[name="subj_id"]');
          var subj_prts_id = $tr.find('input[name="subj_prts_id"]');
       
          var cash_sum_data_input = $tr.find('input[name="cash_sum_data"]');
      	  var stock_sum_data_input = $tr.find('input[name="stock_sum_data"]');
      	
          var cash_exec_account_input = $tr.find('input[name="exec_account"]');
          var stock_exec_account_input = $tr.find('input[name="stock_exec_account"]');
			     
			if(rowsubj_prts_id.val() != subj_id.val() && rowsubj_prts_id.val() == subj_prts_id.val()  ){
				if($(obj).attr('name') == 'cash_sum_data'){
				  	   if(!isNaN(parseInt(uncomma(cash_sum_data_input.val())))){
				  		 sumColCash += parseInt(uncomma(cash_sum_data_input.val()));	               
				         }
				}else if($(obj).attr('name') == 'stock_sum_data'){
					   if(!isNaN(parseInt(uncomma(stock_sum_data_input.val())))){
						   sumColStock += parseInt(uncomma(stock_sum_data_input.val()));	               
				       	}
				}else if($(obj).attr('name') == 'exec_account'){
					   if(!isNaN(parseInt(uncomma(cash_exec_account_input.val())))){
						   sumColExecCash += parseInt(uncomma(cash_exec_account_input.val()));	               
				       }
				}else if($(obj).attr('name') == 'stock_exec_account'){
					   if(!isNaN(parseInt(uncomma(stock_exec_account_input.val())))){
						   sumColExecStock += parseInt(uncomma(stock_exec_account_input.val()));	               
				       }
				}              
		 	} 
	         
	  }); 
	    // 더한 하위값 상위에 셋팅
	   $('#bgExecList tr').each(function() {
		     
			  var $tr = $(this);

	          var subj_id = $tr.find('input[name="subj_id"]');
	          var subj_prts_id = $tr.find('input[name="subj_prts_id"]');
	            
	          var cash_sum_data_input = $tr.find('input[name="cash_sum_data"]');
	      	  var stock_sum_data_input = $tr.find('input[name="stock_sum_data"]');
	      	
	          var cash_exec_account_input = $tr.find('input[name="exec_account"]');
	          var stock_exec_account_input = $tr.find('input[name="stock_exec_account"]');
        	 
	          if(rowsubj_prts_id.val() == subj_id.val() ){  
        		  if($(obj).attr('name') == 'cash_sum_data'){
        			  cash_sum_data_input.val(0);
        			  cash_sum_data_input.val(addComma(sumColCash));
	        	  }else if($(obj).attr('name') == 'stock_sum_data'){
	        		  stock_sum_data_input.val(0);
	        		  stock_sum_data_input.val(addComma(sumColStock));
	        	  }else if($(obj).attr('name') == 'exec_account'){
	        		  cash_exec_account_input.val(0); 
	        		  cash_exec_account_input.val(addComma(sumColExecCash)); 
	        	  }else if($(obj).attr('name') == 'stock_exec_account'){
	        		  stock_exec_account_input.val(0); 
	        		  stock_exec_account_input.val(addComma(sumColExecStock)); 
	        	  }
        		

        	  } 
            
		  }); 
	  
	 // 하위 항목을 입력하면 상위 항목에 SUM값을 표현해준다 END
	 
	//3. 최 하단 row 합계 및 집행률 구하기
	var sum_total_cash = 0;
	var sum_total_stock = 0;

	var sum_total_exec_cash = 0;
	var sum_total_exec_stock = 0;

	
	
	
	
    $('#bgExecList tr').each(function() {
         var $tr = $(this);
         
        if( $tr.attr("id") != 'execSumRow' ){
        	
          var subj_levl = $tr.find('input[name="subj_levl"]');
        
          var cash_sum_data_input = $tr.find('input[name="cash_sum_data"]');
      	  var stock_sum_data_input = $tr.find('input[name="stock_sum_data"]');
      	
          var cash_exec_account_input = $tr.find('input[name="exec_account"]');
          var stock_exec_account_input = $tr.find('input[name="stock_exec_account"]');
         
      
          
    		if(subj_levl.val() == 0){
         
	      		if(!isNaN(parseInt(uncomma(cash_sum_data_input.val())))){
	      			sum_total_cash += parseInt(uncomma(cash_sum_data_input.val()));	
	      		}
	      		
	      		if(!isNaN(parseInt(uncomma(stock_sum_data_input.val())))){
	      			sum_total_stock += parseInt(uncomma(stock_sum_data_input.val()));	
	      		}
	      		
	      		if(!isNaN(parseInt(uncomma(cash_exec_account_input.val())))){
	      			sum_total_exec_cash += parseInt(uncomma(cash_exec_account_input.val()));	
	      		}
	      		
	      		if(!isNaN(parseInt(uncomma(stock_exec_account_input.val())))){
	      			sum_total_exec_stock += parseInt(uncomma(stock_exec_account_input.val()));	
	      		}
	      		
    		}
        }
 
      
    });
    
    $("#cash_sum").text(addComma(sum_total_cash));				
    $("#stock_sum").text(addComma(sum_total_stock));
    $("#exec_cach_sum").text(addComma(sum_total_exec_cash));
    $("#exec_stock_sum").text(addComma(sum_total_exec_stock));
   
    
    // 합계 row 최하단  현금 ,현물 집행율
	var sumRowCashPercent=0 , sumRowStockPercent = 0;
    var sumRowCashNumerator=0 , sumRowStockNumerator = 0;
    var sumRowCashDemnominator = 1 , sumRowStockDemnominator= 1; 
    
    if( (sum_total_cash) <1 ){ 	
    	sumRowCashDemnominator = 1; 	
    }else{	
    	sumRowCashDemnominator = sum_total_cash;	
    }
    
    if( (sum_total_stock) <1 ){ 
    	sumRowStockDemnominator = 1; 	
    }else{	
    	sumRowStockDemnominator = sum_total_stock;	
    }
    
    sumRowCashNumerator = sum_total_exec_cash;
    sumRowStockNumerator = sum_total_exec_stock;
    
    if(sum_total_exec_cash == 0 && sumRowCashDemnominator == 1 ){
    	sumRowCashPercent = 0;
	}else{
		sumRowCashPercent = ( sumRowCashNumerator / sumRowCashDemnominator) * 100;
	}

    if(sum_total_exec_stock == 0 && sumRowStockDemnominator == 1 ){
    	sumRowStockPercent = 0;
	}else{
		sumRowStockPercent = ( sumRowStockNumerator / sumRowStockDemnominator) * 100;
	}
    
	var sumRowCashPercentRound = sumRowCashPercent.toFixed(2); 
	var sumRowStockPercentRound = sumRowStockPercent.toFixed(2); 

   	 $("#exec_cash_ratio_sum").text(sumRowCashPercentRound);
   	 $("#exec_stock_ratio_sum").text(sumRowStockPercentRound);
    
   
    
  //3. 각 하단 합계 및 집행률  구하기 END 
 
      
    
    
   
	
	var sum_total_remain_cash = 0;
	var sum_total_remain_stock = 0;
	
	// 잔액의 총 합계 구하기 str
	 $('#bgExecList tr').each(function() { 
         var $tr = $(this);
        
        if( $tr.attr("id") != 'execSumRow' ){
	     	  var subj_levl = $tr.find('input[name="subj_levl"]');
	     	
	     	  var cash_sum_data_input = $tr.find('input[name="cash_sum_data"]');
          	  var stock_sum_data_input = $tr.find('input[name="stock_sum_data"]');
        	
              var cash_exec_account_input = $tr.find('input[name="exec_account"]');
              var stock_exec_account_input = $tr.find('input[name="stock_exec_account"]');
        
              var cash_remain_td = $tr.find('td[attr="cash_remain_account"]');
              var stock_remain_td = $tr.find('td[attr="stock_remain_account"]');
	          
	          var cash=0;
	          var stock=0;	            
	          var exec_cash =0;
	          var exec_stock =0;
	         
	          if(subj_levl.val() != 0){
	      	 	if(!isNaN(parseInt(uncomma(cash_remain_td.text())))){
	      	 		sum_total_remain_cash += parseInt(uncomma(cash_remain_td.text()));	
	      		}	
	      	 	if(!isNaN(parseInt(uncomma(stock_remain_td.text())))){
	      	 		sum_total_remain_stock += parseInt(uncomma(stock_remain_td.text()));	
	      		}	
	          }	
	          if(subj_levl.val() == 0){
		          if(!isNaN(parseInt(uncomma(cash_sum_data_input.val())))){
		        	  cash =  parseInt(uncomma(cash_sum_data_input.val()));	
		      		}
		          if(!isNaN(parseInt(uncomma(stock_sum_data_input.val())))){
		        	  stock =  parseInt(uncomma(stock_sum_data_input.val()));	
		      		}
		          if(!isNaN(parseInt(uncomma(cash_exec_account_input.val())))){
		        	  exec_cash =  parseInt(uncomma(cash_exec_account_input.val()));	
		      		}
		          if(!isNaN(parseInt(uncomma(stock_exec_account_input.val())))){
		        	  exec_stock =  parseInt(uncomma(stock_exec_account_input.val()));	
		      		}
		      		
		      		
		      	
		          cash_remain_td.text(addComma(cash- exec_cash));
		          stock_remain_td.text(addComma(stock- exec_stock));
	          }
        } 
         
       
    });
	
	$("#cash_remain_sum").text(addComma(sum_total_remain_cash));
	$("#stock_remain_sum").text(addComma(sum_total_remain_stock));

	  // end
	 
	  // 비목별 row 현금 ,현물 집행윯 셋팅
	 $('#bgExecList tr').each(function() {
	        var $tr = $(this); 
	        
        	var cash_sum_data_input = $tr.find('input[name="cash_sum_data"]');
        	var stock_sum_data_input = $tr.find('input[name="stock_sum_data"]');
        	
            var cash_exec_account_input = $tr.find('input[name="exec_account"]');
            var stock_exec_account_input = $tr.find('input[name="stock_exec_account"]');
        
            
            var cashRatio =  $tr.find('td[attr="cashRatio"]');
            var stockRatio =  $tr.find('td[attr="stockRatio"]');
	   	    
			  
	        var row_cash = 0;	     	
	   	 	var row_stock = 0;
	   	  	var row_cash_exec_account = 0;	   	    	   	  	
	   		var row_stock_exec_account = 0;
	   		
	   		var cashPercent =0 , stockPercent =0 ;
			var cashDemnominator = 1 , stockDemnominator = 1;
			var cashNumerator = 1 , stockNumerator = 1;
	   		
			if(!isNaN(parseInt(uncomma(cash_sum_data_input.val()))))  {
	            row_cash += parseInt(uncomma(cash_sum_data_input.val()));	
      		}				         		
      	
      		if(!isNaN(parseInt(uncomma(stock_sum_data_input.val()))))  {
      			row_stock += parseInt(uncomma(stock_sum_data_input.val()));	
      		}			
      		
      		if(!isNaN(parseInt(uncomma(cash_exec_account_input.val())))){
      			row_cash_exec_account += parseInt(uncomma(cash_exec_account_input.val()));	
      		}
      		
      		if(!isNaN(parseInt(uncomma(stock_exec_account_input.val())))){
      			row_stock_exec_account += parseInt(uncomma(stock_exec_account_input.val()));	
      		}
      	

			if((row_cash) < 1){
				cashDemnominator = 1;
			}else{
				cashDemnominator = row_cash;
			}
			
			if((row_stock) < 1){
				stockDemnominator = 1;
			}else{
				stockDemnominator = row_stock;
			}
			
			cashNumerator = row_cash_exec_account;
			stockNumerator = row_stock_exec_account;
			
			
			if(cashNumerator == 0 && cashDemnominator == 1 ){
				cashPercent = 0;
			}else{
				cashPercent = ( cashNumerator / cashDemnominator) * 100;
			}
			
			if(stockNumerator == 0 && stockDemnominator == 1 ){
				stockPercent = 0;
			}else{
				stockPercent = ( stockNumerator / stockDemnominator) * 100;
			}
		
			var cashPercentRound = cashPercent.toFixed(2);  // 소수점 둘째 자리까지 반올림
			var stockPercentRound = stockPercent.toFixed(2);  // 소수점 둘째 자리까지 반올림
		
			cashRatio.text(cashPercentRound);      
			stockRatio.text(stockPercentRound);      
     });   
	  
}

function fn_projBudgetExecSave(){ 

	var params = [];
	
    $('#bgExecList tr').each(function() {
         var $tr = $(this);
      
         var subj_levl = $tr.find('input[name="subj_levl"]');
         
         var p_budget_id = $tr.find('input[name="budget_id"]');
         var p_subj_id = $tr.find('input[name="subj_id"]');       
        
         var p_cash_exec_account = $tr.find('input[name="exec_account"]');
         var p_stock_exec_account = $tr.find('input[name="stock_exec_account"]');
         var p_exec_etc = $tr.find('input[name="exec_etc"]');
         
  		
         if( subj_levl.val() > 0){ 

	         p_budget_id =  p_budget_id.val() ;
	         p_subj_id =   p_subj_id.val();

	         p_cash_exec_account =   uncomma(p_cash_exec_account.val());
	         p_stock_exec_account =   uncomma(p_stock_exec_account.val());
	         p_exec_etc =   p_exec_etc.val();
	     
	
	         // 데이터 객체를 만들어 리스트에 추가합니다
	         var item = {
	       		 budget_id:p_budget_id ,
	       		 subj_id: p_subj_id,	       	
	       		 exec_account: p_cash_exec_account,
	       		 stock_exec_account: p_stock_exec_account,
	       		 exec_etc: p_exec_etc,           
	       	       
	         };
	         
	         params.push(item);
         }
    });
	 	
    var requestData = {
        itemList: params
        
    };

   $.ajax({
        url: '${ctxt}/execute/bgExec/updateProjBudgetExec.do',
        data: JSON.stringify(requestData),
        type: 'POST', 
	    cache: false, 	
	    async : false,
	    contentType: 'application/json', // Content-Type을 설정합니다.
	   	dataType : 'json',    
		success: function(result){			
			if(result.sMessage == 'Y'){
				fn_showCustomAlert("저장이 완료 되었습니다.");
				fn_searchAcctSubRowExec();
				
			}else {
				fn_showCustomAlert("로그인 시간이 만료 되었습니다. 다시 로그인 해주세요.");
			}
		
		
		},
		error: function(request,status,error) {
			fn_showCustomAlert("에러가 발생하였습니다.");
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}



</script>
 

<div id="container">
	<div id="divRefreshArea">

		<h3 class="page_title" id="title_div">
			<span class="adminIcon"></span>예산 집행
		</h3>

		<div style ="float: left;">
			 
		
			 
			<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_wideContent();" >화면 확대/축소</a>
			<a href="javascript:void(0);" class="btn btn-secondary" onclick="javascript:fn_projBudgetExecSave();">저장</a>
			<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
		</div>
		
		<div style ="float: right;margin-top: 10px;">
			(단위 : 원)
		</div>
		<div class="clear"></div>
		<!--게시판-->
		<div class="budget">


			<table class="table_h" cellpadding="0" cellspacing="0" border="0"  id ="execTable">

				<caption>예산집행</caption>
				<colgroup>
					<col style="width: *;">															
					<col style="width: 8%;">
					<col style="width: 8%;">
					<col style="width: 8%;">					
					<col style="width: 8%;">
					<col style="width: 8%;">
					<col style="width: 8%;">
					<col style="width: 8%;">
					<col style="width: 8%;">
					<col style="width: 20%;">

				</colgroup>
				<thead>
					<tr>
						<!-- <th scope='col' rowspan="2">비목</th>
						<th scope='col' colspan="2">예산</th>
						<th scope='col' rowspan="2">집행금액(현금)</th>
						<th scope='col' rowspan="2">집행금액(현물)</th>					
						<th scope='col' rowspan="2">잔액(현금)</th>
						<th scope='col' rowspan="2">잔액(현물)</th>
						<th scope='col' rowspan="2">집행률(현금)</th>
						<th scope='col' rowspan="2">집행률(현물)</th>
						<th scope='col' rowspan="2">비고</th> -->
						<th scope='col' rowspan="2">비목</th>
						<th scope='col' colspan="2">예산(계속비 포함)</th>
						<th scope='col' colspan="2">집행</th>											
						<th scope='col' colspan="2">잔액</th>						
						<th scope='col' colspan="2">집행률(%)</th>
						
						<th scope='col' rowspan="2">비고</th>
					</tr>
					<tr>
																					
						<th scope='col' >현금</th>					
						<th scope='col'>현물</th>		
						<th scope='col' >현금</th>					
						<th scope='col'>현물</th>	
						<th scope='col' >현금</th>					
						<th scope='col'>현물</th>	
						<th scope='col' >현금</th>					
						<th scope='col'>현물</th>	
						
					</tr>
				</thead>
				<tbody id="bgExecList">
				</tbody>

			</table>

		</div>
	</div>
</div>

