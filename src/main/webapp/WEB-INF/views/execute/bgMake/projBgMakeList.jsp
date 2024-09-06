<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"> 

/*  최상의 항목의 코드 단위는 100000 으로 정의한다  10만 단위 */

 

var proj_year_id_budget = '${data.proj_year_id}';

$(function(){ 

	fn_searchAcctSubRow();

	  
}); 
 

function fn_acctSubjectListSearch(){

		var params = {};
		params.proj_year_id = proj_year_id_budget;
  	  	
    $.ajax({
        url: '${ctxt}/execute/bgMake/readProjAccountSubjectList.do',
        data: params,
        type: 'POST', 
       // async : false, 
		dataType:"json",
        success: function(result) { 

	      	var layerHtml = "";
	    
	      	//드래그 가능한 div
	      	layerHtml +=`<div class="draggable-div" style="width: 500px; inset: 100px auto auto 750px; height: auto; z-index:999;">			
	      		<div class="form-group">
	       		<label for="inputText" class="form-label"><strong>계정과목</strong></label>
	       		<div class="" style = "display: grid;  grid-template-columns: 35px 70px;justify-content: center;margin-bottom: 10px;">				
	       		<input type ="checkbox" id="checkJstree" /><label for="checkJstree" >전체선택</label>	
	       				</div>
	       				<div id="account_subject"></div>
	           		
	           	
	           	<div class="ma_t_30">						
	           	    <a href="javascript:fn_projAcctSubjSave();" class="float_n btn btn-primary">생성</a>
	           	    <a href="javascript:closeBgMakePop();" class="btn btn-secondary">닫기</a>
	       		</div>
	       	</div>
	       </div>`;
	      	
	      	$("#bdMakeLayer").html(layerHtml);
	      	$(".draggable-div").draggable();		
	      	    
	   		var treeData = buildTree(result.acctSubjectlist);
	   		
	   		
	  	    $('#account_subject').jstree({
	  	        'core': {
	  	            'data': treeData
	  	        },'checkbox' : {
	  	          'three_state': false
	  	      },
	  	        'plugins': ["checkbox"]
	  	    }).on('ready.jstree', function() {
	  	    	
	  	    	result.bgMakeList.forEach(function(item) {
                    var node = $('#account_subject').jstree('get_node',item.subj_id);
                    if (node) {
                        $('#account_subject').jstree('check_node', node);
                    }
                });   
	  	    });
	  	    
	  		 // 하위 체크박스 클릭 감지
	        $('#account_subject').on('click', '.jstree-anchor', function() {
	            var node = $(this).closest('.jstree-node');
	            var tree = $('#account_subject').jstree(true);
	            var node_id = node.attr('id');
	
	            if (tree.is_checked(node_id)) {
	            	   // 체크된 경우, 부모 노드 체크 상태 업데이트
                    updateParentNodeState(tree, node_id);
                } else {
                    // 체크 해제된 경우, 모든 자식 노드 체크 상태 업데이트
                    tree.get_node(node_id).children.forEach(function(childId) {
                        tree.uncheck_node(childId);
                    });
                }
	        });  	    
  	 
	        // 전체선택 체크박스 클릭 이벤트
            $('#checkJstree').on('change', function() {
                var tree = $('#account_subject').jstree(true);
                if (this.checked) {
                    tree.check_all();
                } else {
                    tree.uncheck_all();
                }
            });
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        },
        complete : function (){
        	
        }
    }); 
}

//json 결과 데이터를  jstree에 맞게 재구성 
function buildTree(data) {
 const tree = [];
 const lookup = {};
 


 data.forEach(item => {
     lookup[item.subj_id] = { id: item.subj_id, text: item.subj_nm, data: { value: item.subj_id }, children: [] ,  state: { opened: true } };
 });

 data.forEach(item => {
     if (item.subj_prts_id) {
         lookup[item.subj_prts_id].children.push(lookup[item.subj_id]);
     } else {
         tree.push(lookup[item.subj_id]);
     }
 });

 return tree;
}

//부모 노드 상태 업데이트 함수
function updateParentNodeState(tree, node_id) {
    var node = tree.get_node(node_id);
    if (!node) return;

    var parent = tree.get_node(node.parent);
    if (!parent || parent.id === '#') return;

    tree.check_node(parent);
    updateParentNodeState(tree, parent.id); // 재귀적으로 상위 노드 상태 업데이트
}
//
function closeBgMakePop(){	     
	 var element = document.getElementById('bdMakeLayer');
	 element.innerText = '';
}	

function fn_projAcctSubjSave(){ 

	 var params = [];
	
  
	 $('#account_subject').jstree('get_checked', true).forEach(function(node) {
       if (node.data && node.data.value) {
    	   var item = {
          		 subj_id:node.data.value
          		 ,proj_year_id:proj_year_id_budget
           };
           params.push(item);
       }
   });
	
    var requestData = {
        itemList: params
        
    };
    
    fn_showCustomConfirm("question","계정과목을 생성 하시겠습니까?", function() {
	   $.ajax({
	       url: '${ctxt}/execute/bgMake/insertProjAcctSubj.do',
	       data: JSON.stringify(requestData),
	       type: 'POST', 
		   	cache: false, 	
		   	 async : false,
			contentType: 'application/json', // Content-Type을 설정합니다.
		   	dataType : 'json',    
			success: function(result){			
				if(result.sMessage == 'Y'){
					fn_showCustomAlert("저장이 완료 되었습니다.");
					setTimeout(function() { location.reload(); }, 2000);
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



function fn_searchAcctSubRow(){
	$("#bgMakeList").children().remove();
		var html = '';
		var params = {};
		params.proj_year_id = proj_year_id_budget;
  	  	
    $.ajax({
        url: '${ctxt}/execute/bgMake/readAcctSubjRowList.do',
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
										html+= '<input type ="hidden" name ="delGbn" value = "N" />';
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
						html+= '<td class="text_r">'; 
						
						html += '<div class="btnDept">';	
							if(item.subj_levl > 0){
				        		html += '<a href="javascript:void(0);"  class="btn btn-dept" onclick="javascript:fn_acctSubjDel(this);">삭제</a>';
							}
			        	 html += '</div>';
						
						html+= '</td>';		
						html+= '<td class="text_r">'; 
							if(item.subj_levl == 0){
								html += "<input type='text' class='text_r form-control' name='cash'  value='" + addComma(item.cash) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_sumRow(this);'   disabled />";	
							}else{
								html += "<input type='text' class='text_r' name='cash'  value='" + addComma(item.cash) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_sumRow(this);'  />";
							}
							
						html+= '</td>';			
						html+= '<td class="text_r">';
							if(item.subj_levl == 0){
								html += "<input type='text'  class='text_r form-control' name='stock' value='" + addComma(item.stock) + "' oninput='this.value = this.value.replace(/[^0-9.]/g, \"\");' onchange = 'fn_sumRow(this);' disabled />";	
							}else{
								html += "<input type='text'  class='text_r' name='stock' value='" + addComma(item.stock) + "' oninput='this.value = this.value.replace(/[^0-9.]/g, \"\");' onchange = 'fn_sumRow(this);'/>";
							}
						 
						html+= '</td>';						
						html+= '<td class="text_r">';
							if(item.subj_levl == 0){
								html += "<input type='text' class='text_r form-control' name='ongo_cash'  value='" + addComma(item.ongo_cash) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_sumRow(this);' disabled />";	
							}else{
								html += "<input type='text' class='text_r' name='ongo_cash'  value='" + addComma(item.ongo_cash) + "'  oninput='this.value = this.value.replace(/[^0-9.]/g,\"\");' onchange = 'fn_sumRow(this);' />";
							}
						
						
						html+= '</td>';			
						html+= '<td class="text_r">';
							if(item.subj_levl == 0){
								html += "<input type='text'  class='text_r form-control' name='ongo_stock' value='" + addComma(item.ongo_stock) + "' oninput='this.value = this.value.replace(/[^0-9.]/g, \"\");' onchange = 'fn_sumRow(this);' disabled />";	
							}else{
								html += "<input type='text'  class='text_r' name='ongo_stock' value='" + addComma(item.ongo_stock) + "' oninput='this.value = this.value.replace(/[^0-9.]/g, \"\");' onchange = 'fn_sumRow(this);' />";
							}
						
						html+= '</td>';						
						html+= '<td class="text_r" attr = "rowTotal">' + addComma(item.sum_data) +'</td>';			
						html+= '<td  attr = "rowRatio"></td>';			
						html+= '<td>';
							if(item.subj_levl > 0){
								html+= '<input type ="text" name ="etc" value = "'+ ((isEmpty(item.etc)) ? '' : item.etc) + '" />';  
							}
						html+= '</td>';	
						html+= '</tr>'; 
					});
					html+= '<tr id ="sumRow" style="background: blanchedalmond;" >';
						html+= '<td>합&nbsp;&nbsp;&nbsp;&nbsp;계</td>';
						html+= '<td></td>';
						html+= '<td id = "cashSum" class="text_r"></td>';
						html+= '<td id = "stockSum" class="text_r"></td>';
						html+= '<td id = "ongo_cashSum" class="text_r"></td>';
						html+= '<td id = "ongo_stockSum" class="text_r"></td>';
						html+= '<td id = "totalSum" class="text_r"></td>';
						html+= '<td id = "ratioSum" ></td>';
						html+= '<td></td>';
					html+= '</tr>';
					
					$('#bgMakeList').html(html);
					
					// 입력할때 천단위 콤마
					$('input[name="cash"],input[name="stock"]').on('input', function(event) {
				        //  숫자와 쉼표만 남기고 모두 제거
				        var value = $(this).val().replace(/[^\d,]/g, '');	        
				        // 쉼표(,) 제거
				        value = value.replace(/,/g, '');	        
				        // 천 단위마다 쉼표 추가
				        var formatVal = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');	        
				        
				        $(this).val(formatVal);
		  		    });
					
			
					var sum_cash = 0;
					var sum_stock = 0;
					var sum_ongo_cash = 0;
					var sum_ongo_stock = 0;
					var sum_total = 0;
				    $('#bgMakeList tr').each(function() {
				         var $tr = $(this);
				         
				        if( $tr.attr("id") != 'sumRow' ){
				        	var subj_levl = $tr.find('input[name="subj_levl"]');
				        	
				        	var cashInput = $tr.find('input[name="cash"]');
				            var stockInput = $tr.find('input[name="stock"]');
				        	var ongo_cashInput = $tr.find('input[name="ongo_cash"]');
				            var ongo_stockInput = $tr.find('input[name="ongo_stock"]');
				            var totalInput = $tr.find('td[attr="rowTotal"]');
			         		
				            if(subj_levl.val() == 0){
					            if(!isNaN(parseInt(uncomma(cashInput.val()))))  {
				         			sum_cash += parseInt(uncomma(cashInput.val()));	
				         		}				         		
				         		if(!isNaN(parseInt(uncomma(stockInput.val())))){
				         			sum_stock += parseInt(uncomma(stockInput.val()));	
				         		}
				         		if(!isNaN(parseInt(uncomma(ongo_cashInput.val()))))  {
				         			sum_ongo_cash += parseInt(uncomma(ongo_cashInput.val()));	
				         		}				         		
				         		if(!isNaN(parseInt(uncomma(stockInput.val())))){
				         			sum_ongo_stock += parseInt(uncomma(ongo_stockInput.val()));	
				         		}
				         		if(!isNaN(parseInt(uncomma(totalInput.text())))){
				         			sum_total += parseInt(uncomma(totalInput.text()));	
				         		}	
				            }
				            fn_sumRow(cashInput);
				            fn_sumRow(stockInput);
				            fn_sumRow(ongo_cashInput);
				            fn_sumRow(ongo_stockInput);
				        }
				  
				    });
				    
				    $("#cashSum").text(addComma(sum_cash));
				    $("#stockSum").text(addComma(sum_stock));
				    $("#ongo_cashSum").text(addComma(sum_ongo_cash));
				    $("#ongo_stockSum").text(addComma(sum_ongo_stock));
				    $("#totalSum").text(addComma(sum_total));
				    
				    fn_sumRow(); // object 가 없어도 무방하다.  
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


function fn_acctSubjDel(obj){
	  var $row = $(obj).closest('tr');
	  var rowCash = $row.find('input[name="cash"]');   
	  var rowStock = $row.find('input[name="stock"]');
	  var rowOngoCash = $row.find('input[name="ongo_cash"]');   
	  var rowOngoStock = $row.find('input[name="ongo_stock"]');
	  var rowDelGbn = $row.find('input[name="delGbn"]');

	  // 혹시 모르니 값 버리지 말고 실시간 반영 하지 말고 지우자
	  if(rowCash.val() > 0 || rowStock.val()  > 0 || rowOngoCash.val()  > 0|| rowOngoStock.val()  > 0){
		 if(confirm("입력값이 존재합니다. 삭제하시겠습니까?") == true){
		//	 rowCash.val(0);
		//	 rowStock.val(0);
		//	 rowOngoCash.val(0);
		//	 rowOngoStock.val(0);
			 rowDelGbn.val("Y");
			 $row.css("display" , "none");
			  
		//	 fn_sumRow(rowCash);
		//	 fn_sumRow(rowStock);
		//	 fn_sumRow(rowOngoCash);
		//	 fn_sumRow(rowOngoStock);
			 
		 }
	  }else{
		//  rowCash.val(0);
		//  rowStock.val(0);
		//  rowOngoCash.val(0);
		//  rowOngoStock.val(0);
		  rowDelGbn.val("Y");
		  $row.css("display" , "none");
		  
		//  fn_sumRow(rowCash);
		//  fn_sumRow(rowStock);
		//  fn_sumRow(rowOngoCash);
		//  fn_sumRow(rowOngoStock);  
	  }
	  

	  
	  
	
}

function fn_sumRow(obj) {
	
	  var $row = $(obj).closest('tr');
	  var rowCash = $row.find('input[name="cash"]');   
	  var rowStock = $row.find('input[name="stock"]');
	  var rowOngoCash = $row.find('input[name="ongo_cash"]');   
	  var rowOngoStock = $row.find('input[name="ongo_stock"]');
	  var rowsubj_id = $row.find('input[name="subj_id"]');
	  var rowsubj_prts_id = $row.find('input[name="subj_prts_id"]');
	  var rowTotal = $row.find('td[attr="rowTotal"]');
	
	// 1. row의 계 구하기 str
	    

	  var cashVal = 0;
	  var stockVal = 0;
	  var ongo_cashVal = 0;
	  var ongo_stockVal = 0;
	    
	  	// 각row 의 계를 구성한다. STR
		if(!isNaN(parseInt(uncomma(rowCash.val())))){
			cashVal =  parseInt(uncomma(rowCash.val()));	
		} 
	
		if(!isNaN(parseInt(uncomma(rowStock.val())))){
			stockVal =  parseInt(uncomma(rowStock.val()));	
		}
		if(!isNaN(parseInt(uncomma(rowOngoCash.val())))){
			ongo_cashVal =  parseInt(uncomma(rowOngoCash.val()));	
		}
	
		if(!isNaN(parseInt(uncomma(rowOngoStock.val())))){
			ongo_stockVal =  parseInt(uncomma(rowOngoStock.val()));	
		}
		 
		rowTotal.text(addComma(cashVal + stockVal+ongo_cashVal+ongo_stockVal));
	// 각row 의 계를 구성한다. END
  
	// 2. 하위 항목을 입력하면 상위 항목에 SUM값을 표현해준다 STR
		
	   var sumColCash = 0;
	   var sumColStock = 0;
	   var sumColOngoCash = 0;
	   var sumColOngoStock = 0;
	   // 하위 값 모두 더하기
	   $('#bgMakeList tr').each(function() {
		    
		  var $tr = $(this);

          var subj_id = $tr.find('input[name="subj_id"]');
          var subj_prts_id = $tr.find('input[name="subj_prts_id"]');
            
          var cashInput = $tr.find('input[name="cash"]');         
          var stockInput = $tr.find('input[name="stock"]');
          var ongo_cashInput = $tr.find('input[name="ongo_cash"]');
          var ongo_stockInput = $tr.find('input[name="ongo_stock"]');
    	  
   
			     
			if(rowsubj_prts_id.val() != subj_id.val() && rowsubj_prts_id.val() == subj_prts_id.val()  ){
				if($(obj).attr('name') == 'cash'){
				  	   if(!isNaN(parseInt(uncomma(cashInput.val())))){
				          	sumColCash += parseInt(uncomma(cashInput.val()));	               
				         }
				} else if($(obj).attr('name') == 'stock'){
					   if(!isNaN(parseInt(uncomma(stockInput.val())))){
						   sumColStock += parseInt(uncomma(stockInput.val()));	               
				       }
				} else if($(obj).attr('name') == 'ongo_cash'){
					   if(!isNaN(parseInt(uncomma(ongo_cashInput.val())))){
						   sumColOngoCash += parseInt(uncomma(ongo_cashInput.val()));	               
				       	}
				}else if($(obj).attr('name') == 'ongo_stock'){
					   if(!isNaN(parseInt(uncomma(ongo_stockInput.val())))){
						   sumColOngoStock += parseInt(uncomma(ongo_stockInput.val()));	               
				       }
				}              
		 	} 
	         
	  }); 
	    // 더한 하위값 상위에 셋팅
	   $('#bgMakeList tr').each(function() {
		     
			  var $tr = $(this);

	          var subj_id = $tr.find('input[name="subj_id"]');
	          var subj_prts_id = $tr.find('input[name="subj_prts_id"]');
	            
	          var cashInput = $tr.find('input[name="cash"]');         
	          var stockInput = $tr.find('input[name="stock"]');
	          var ongo_cashInput = $tr.find('input[name="ongo_cash"]');
	          var ongo_stockInput = $tr.find('input[name="ongo_stock"]');	    	
       
        	 
	          if(rowsubj_prts_id.val() == subj_id.val() ){  
        		  if($(obj).attr('name') == 'cash'){
	        	      cashInput.val(0);
	        		  cashInput.val(addComma(sumColCash));
	        	  }else if($(obj).attr('name') == 'stock'){
	        		  stockInput.val(0);
	        		  stockInput.val(addComma(sumColStock));
	        	  }else if($(obj).attr('name') == 'ongo_cash'){
	        		  ongo_cashInput.val(0);
	        		  ongo_cashInput.val(addComma(sumColOngoCash));
	        	  }else if($(obj).attr('name') == 'ongo_stock'){
	        		  ongo_stockInput.val(0); 
	        		  ongo_stockInput.val(addComma(sumColOngoStock)); 
	        	  }
        		

        	  } 
            
		  }); 
	  
	 // 하위 항목을 입력하면 상위 항목에 SUM값을 표현해준다 END
	 
	//3. 각 하단 합계 구하기
	var sum_cash = 0;
	var sum_stock = 0;
	var sum_ongo_cash = 0;
	var sum_ongo_stock = 0;
	var sum_total = 0;
	
	
    $('#bgMakeList tr').each(function() {
         var $tr = $(this);
         
        if( $tr.attr("id") != 'sumRow' ){
        	
          var subj_levl = $tr.find('input[name="subj_levl"]');

          var cashInput = $tr.find('input[name="cash"]');
          var stockInput = $tr.find('input[name="stock"]');
          var ongo_cashInput = $tr.find('input[name="ongo_cash"]');
          var ongo_stockInput = $tr.find('input[name="ongo_stock"]');
    		if(subj_levl.val() == 0){
         
	      		if(!isNaN(parseInt(uncomma(cashInput.val())))){
	      			sum_cash += parseInt(uncomma(cashInput.val()));	
	      		}
	      		
	      		if(!isNaN(parseInt(uncomma(stockInput.val())))){
	      			sum_stock += parseInt(uncomma(stockInput.val()));	
	      		}	
	      		if(!isNaN(parseInt(uncomma(ongo_cashInput.val())))){
	      			sum_ongo_cash += parseInt(uncomma(ongo_cashInput.val()));	
	      		}
	      		
	      		if(!isNaN(parseInt(uncomma(ongo_stockInput.val())))){
	      			sum_ongo_stock += parseInt(uncomma(ongo_stockInput.val()));	
	      		}
    		}
        }
         
      
    });
    
    $("#cashSum").text(addComma(sum_cash));
    $("#stockSum").text(addComma(sum_stock));
    $("#ongo_cashSum").text(addComma(sum_ongo_cash));
    $("#ongo_stockSum").text(addComma(sum_ongo_stock));
  //3. 각 하단 합계 구하기 END 
 
      
    
    
   
	
	
	 //end 
	// 계의 총 합계 구하기 str
	 $('#bgMakeList tr').each(function() { 
         var $tr = $(this);
        
        if( $tr.attr("id") != 'sumRow' ){
	     	  var subj_levl = $tr.find('input[name="subj_levl"]');
	     	  var cashInput = $tr.find('input[name="cash"]');
	          var stockInput = $tr.find('input[name="stock"]');
	          var ongo_cashInput = $tr.find('input[name="ongo_cash"]');
	          var ongo_stockInput = $tr.find('input[name="ongo_stock"]');
	          var rowTotal = $tr.find('td[attr="rowTotal"]');
	          
	          var cash=0;
	          var stock=0;
	          var ongo_cash=0;  
	          var ongo_stock =0;
	         
	          if(subj_levl.val() != 0){
	      	 	if(!isNaN(parseInt(uncomma(rowTotal.text())))){
	      			sum_total += parseInt(uncomma(rowTotal.text()));	
	      		}	
	          }	
	          if(subj_levl.val() == 0){
		          if(!isNaN(parseInt(uncomma(cashInput.val())))){
		        	  cash =  parseInt(uncomma(cashInput.val()));	
		      		}
		      		
		      		if(!isNaN(parseInt(uncomma(stockInput.val())))){
		      			stock=  parseInt(uncomma(stockInput.val()));	
		      		}	
		      		if(!isNaN(parseInt(uncomma(ongo_cashInput.val())))){
		      			ongo_cash = parseInt(uncomma(ongo_cashInput.val()));	
		      		}
		      		
		      		if(!isNaN(parseInt(uncomma(ongo_stockInput.val())))){
		      			ongo_stock = parseInt(uncomma(ongo_stockInput.val()));	
		      		}
		      		rowTotal.text(addComma(cash + stock+ ongo_cash+ongo_stock));
	          }
        } 
         
       
    });
	  $("#totalSum").text(addComma(sum_total));

	  // end
	 
	 $('#bgMakeList tr').each(function() {
         var $tr = $(this); 
         
        if( $tr.attr("id") != 'sumRow' ){
  
         var rowTotalInput = $tr.find('td[attr="rowTotal"]');
         var rowRatioInput = $tr.find('td[attr="rowRatio"]');
         var totalSum = parseInt(uncomma($("#totalSum").text()));
 
      	 	if(!isNaN(parseInt(uncomma(rowTotalInput.text())))){
      	 		if(totalSum < 1){
      	 			totalSum = 1;
      	 		}
      	 		var percent = (parseInt(uncomma(rowTotalInput.text())) / totalSum) * 100;
      	 		var percentRound = percent.toFixed(1);  // 소수점 첫째 자리까지 반올림
      	 		rowRatioInput.text(percentRound);
      	 	
      		} 
      	 	
      		
        }
         
      
     });  
	  
}

function fn_projBudgetSave(){ 

	var params = [];
	
    $('#bgMakeList tr').each(function() {
         var $tr = $(this);
      
         var subj_levl = $tr.find('input[name="subj_levl"]');
         
         var p_budget_id = $tr.find('input[name="budget_id"]');
         var p_subj_id = $tr.find('input[name="subj_id"]');
         var p_cash = $tr.find('input[name="cash"]');
         var p_stock = $tr.find('input[name="stock"]');
         var p_ongocash = $tr.find('input[name="ongo_cash"]');
         var p_ongostock = $tr.find('input[name="ongo_stock"]');
         var p_etc = $tr.find('input[name="etc"]');
         var p_delGbn = $tr.find('input[name="delGbn"]');
  		
         if( subj_levl.val() > 0){ 

	         p_budget_id =  p_budget_id.val() ;
	         p_subj_id =   p_subj_id.val();
	         p_cash =   uncomma(p_cash.val());
	         p_stock =   uncomma(p_stock.val());
	         p_ongocash =   uncomma(p_ongocash.val());
	         p_ongostock =   uncomma(p_ongostock.val());
	         p_etc =   p_etc.val();
	         p_delGbn =  p_delGbn.val();
	
	         // 데이터 객체를 만들어 리스트에 추가합니다
	         var item = {
	       		 budget_id:p_budget_id ,
	       		 subj_id: p_subj_id,
	       		 cash: p_cash,
	       		 stock: p_stock,
	       		 ongo_cash: p_ongocash,
	       		ongo_stock: p_ongostock,
	       		 etc: p_etc,           
	       		 delgbn: p_delGbn,           
	         };
	         
	         params.push(item);
         }
    });
	 	
    var requestData = {
        itemList: params
        
    };

   $.ajax({
        url: '${ctxt}/execute/bgMake/updateProjBudget.do',
        data: JSON.stringify(requestData),
        type: 'POST', 
	    cache: false, 	
	    async : false,
	    contentType: 'application/json', // Content-Type을 설정합니다.
	   	dataType : 'json',    
		success: function(result){			
			if(result.sMessage == 'Y'){
				fn_showCustomAlert("저장이 완료 되었습니다.");
				fn_searchAcctSubRow();
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
			<span class="adminIcon"></span>예산 편성
		</h3>

		<div style ="float: left;">
			<a href="javascript:void(0);" onclick="javascript:fn_acctSubjectListSearch();" 	class="btn btn-secondary">계정과목생성</a> 				
			<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_wideContent();" >화면 확대/축소</a>
			 <a href="javascript:void(0);" class="btn btn-secondary" onclick="javascript:fn_projBudgetSave();">저장</a>
			<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
		</div>
		
		<div style ="float: right;margin-top: 10px;">
			(단위 : 원)
		</div>
		<div class="clear"></div>
		<!--게시판-->
		<div class="budget">


			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>예산 편성</caption>
				<colgroup>
					<col style="width: *;">		
					<col style="width: 5%;">								
					<col style="width: 9%;">
					<col style="width: 9%;">
					<col style="width: 9%;">
					<col style="width: 9%;">
					<col style="width: 9%;">
					<col style="width: 9%;">
					<col style="width: 15%;">

				</colgroup>
				<thead>

					<tr>
						<th scope='col'>비목</th>
						<th scope='col'></th>											
						<th scope='col'>현금</th>
						<th scope='col'>현물</th>
						<th scope='col'>현금(계속비)</th>
						<th scope='col'>현물(계속비)</th>
						<th scope='col'>계</th>
						<th scope='col'>구성비(%)</th>
						<th scope='col'>비고</th>
					</tr>
				</thead>
				<tbody id="bgMakeList">
				</tbody>

			</table>

		</div>
	</div>
</div>

