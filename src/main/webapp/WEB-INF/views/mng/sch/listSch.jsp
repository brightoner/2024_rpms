<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="kr.go.rastech.ptl.mng.menu.vo.MngMenuVo"%>

<script type="text/javascript">
//데이터 수정시에는 스케줄 작동 안함
var isInsert = false;

	
function fn_search(){
	
	if(isInsert == true){
		return;
	}
	
    $.ajax({
        url: '${ctxt}/mng/sch/selectEtlList.do',
        data : "",
        type: 'GET',
        contentType: "text/xml;charset=utf-8",
        dataType: 'text',
        success: function(rtnXml) {
        	var xmlObj = $(rtnXml).find('item');
			if(xmlObj.length > 0){
				var html="";
				var sel_opt="";
				xmlObj.each(function(){   
					html += '<tr onclick="fn_setRow(this)">';
					html += '  <td class="center" >'+$(this).find('etl_sn').text()+'</td>';
					html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('etl_id').text()+'" onchange="fn_upd(this,11)" readonly="readonly" /></td>';            
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('etl_nm').text()+'" onchange="fn_upd(this,11)" /></td>';   
			        html += '  <td><select>';
			        <c:forEach items="${etl_type}" var="type" varStatus="">
			       		html +='<option value="${type.cd}" '; if('${type.cd}' == $(this).find('etl_type').text()){ html +='selected' } html +='>${type.cd_nm}</option>';
					</c:forEach>      
					html += '  </select></td>';     
			        html += '  <td class="hide" ><input type="text" style="width:90%;" value="'+$(this).find('etl_tb').text()+'" onchange="fn_upd(this,11)" /></td>';   
			        html += '  <td class="hide" ><input type="text" style="width:90%;" value="'+$(this).find('etl_gbn').text()+'" onchange="fn_upd(this,11)" /></td>';  
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('etl_time').text()+'" onchange="fn_upd(this,11)" /></td>';
			        html += '  <td><select onchange="fn_upd(this,11)">';
			       	for(var i=1; i <= 24; i++){
		       		html +='<option value="'+i+'" '; if(i == $(this).find('etl_hh').text()){ html +='selected' } html +='>'+i+'</option>';
			       	}      
			       	html += '  </select></td>'; 
			        html += '  <td><select onchange="fn_upd(this,11)">';
			       	for(var i=0; i <= 59; i++){
		       		html +='<option value="'+i+'" '; if(i == $(this).find('etl_ss').text()){ html +='selected' } html +='>'+i+'</option>';
			       	}      
			       	html += '  </select></td>'; 
			        html += '  <td><input type="text" class="center" style="width:90%;" value="'+$(this).find('next_dt').text()+'" readonly /></td>';  
			        html += '  <td><select onchange="fn_upd(this,11)">';  
		       		html +='<option value="Y" '; if('Y' == $(this).find('use_yn').text()){ html +='selected' } html +='>예</option>';
		       		html +='<option value="N" '; if('N' == $(this).find('use_yn').text()){ html +='selected' } html +='>아니오</option>';
			       	html += '  </select></td>';    
			       	html += '  <td style="display:none;">N</td>'; 
					html += '</tr>';  
				});
				
				$('#dtlCdList').html(html);
				
				<c:if test="${if_yn == 'Y'}"> 
					parent.calcHeight();
				</c:if>

				fn_rowOn('sch_tb');
			}
			   

        },
        error: function(x, o, e) {
	            var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
	            fn_showCustomAlert(msg); 
	    }            
    }); 
}

function fn_addDtl(){

	isInsert = true;
	
	$('#dtlCdList .on').attr('class','off');
	
	var html="";
   		html += '<tr class="on" onclick="fn_setRow(this)" >';         
		html += '  <td class="center" >'+($('#dtlCdList tr').length+ 1)+'</td>';
		html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('etl_id').text()+'" onchange="fn_upd(this,11)" /></td>';            
        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('etl_nm').text()+'" onchange="fn_upd(this,11)" /></td>';   
        html += '  <td><select onchange="fn_upd(this,11)">';
        <c:forEach items="${etl_type}" var="type" varStatus="">
       		html +='<option value="${type.cd}" >${type.cd_nm}</option>';
		</c:forEach>      
		html += '  </select></td>';     
        html += '  <td class="hide" ><input type="text" style="width:90%;" value="" onchange="fn_upd(this,11)" /></td>';   
        html += '  <td class="hide" ><input type="text" style="width:90%;" value="" onchange="fn_upd(this,11)" /></td>';  
        html += '  <td><input type="text" style="width:90%;" value="" onchange="fn_upd(this,11)" /></td>';
        html += '  <td><select onchange="fn_upd(this,11)">';
       	for(var i=1; i <= 24; i++){
   		html +='<option value="'+i+'" >'+i+'</option>';
       	}      
       	html += '  </select></td>'; 
        html += '  <td><select onchange="fn_upd(this,11)">';
       	for(var i=0; i <= 59; i++){
   		html +='<option value="'+i+'" >'+i+'</option>';
       	}      
       	html += '  </select></td>'; 
        html += '  <td><input type="text" class="center" style="width:90%;" value="" readonly /></td>';  
        html += '  <td><select onchange="fn_upd(this,11)">';  
   		html +='<option value="Y" >예</option>';
   		html +='<option value="N" >아니오</option>';
       	html += '  </select></td>';    
       	html += '  <td style="display:none;">I</td>'; 
		html += '</tr>';  
 	$('#dtlCdList:last').append(html);

 	$( '#sc' ).stop().animate( { scrollTop : $('#dtlCdList > tr').length * 30 } );
 	
	<c:if test="${if_yn == 'Y'}"> 
		parent.calcHeight();
	</c:if>
}

/*
* 상세코드 삭제
*/
function fn_delDtl(type){
	var delRow = $('#dtlCdList .on');
	if(delRow.length == 0){
		fn_showCustomAlert("선택로우가 없습니다.","c");
	}
	
	if(type != "D"){
		if($(delRow).find('td:eq(11)').text() != 'I'){
			fn_showCustomAlert('취소 대상이 아닙니다.','c');
			return false;
		}
	}
	//선택로우 삭제	
	$(delRow).remove();
	

 	if($('#dtlCdList tr').length > 0){
 		$('#dtlCdList .on').attr('class','off');
 		$('#dtlCdList tr:last').attr('class','on');	
 	}
 	
 	isInsert = false;
	$('#dtlCdList tr').each(function(){
		var trObj = $(this);
		//데이터 조작이 없는 경우 처리안함
		if(trObj.find('td:eq(11)').text() == 'I' || trObj.find('td:eq(11)').text() == 'U'){
			isInsert = true;
			return false;
		}
	});
 	
 	
}

/**
*	상세코드 저장
*/
function fn_saveSch(type){
	var sel_cd ="";
	var paramObj;   
	
	var param_idx = [1,2,6];
	var param_nm = ["ID","스켸줄명","시간"]
	
	var isVal = fn_valchk('dtlCdList',param_idx,param_nm);

	if(!isVal){
		alert(msg);
		fn_setRow(paramObj);
		return;
	}
	var strConfirm;
	
	if(type == 'D'){
		strConfirm=true;
	}else{
		strConfirm = confirm("저장하시겠습니까?");
	}

	if(strConfirm){
		
		var list = [];
		var cnt=0;
		$('#dtlCdList tr').each(function(){
			
			var trObj = $(this);
			//데이터 조작이 없는 경우 처리안함
			if(trObj.find('td:eq(11)').text() == 'N'){
				return true;
			}
			
			if(cnt == 0){   
				sel_cd=trObj.find('td:eq(8)').text();
			}
			   
			list[cnt] = {
					"etl_sn" : trObj.find('td:eq(0)').text(),
					"etl_id" : trObj.find('td:eq(1) input[type=text]').val(),
					"etl_nm" : encodeURIComponent(trObj.find('td:eq(2) input[type=text]').val()),
					"etl_type" : trObj.find('td:eq(3) :selected').val(),
					"etl_tb" : trObj.find('td:eq(4) input[type=text]').val(),
					"etl_gbn" : trObj.find('td:eq(5) input[type=text]').val(),
					"etl_time" : trObj.find('td:eq(6) input[type=text]').val(),
					"etl_hh" : trObj.find('td:eq(7) :selected').val(),
					"etl_ss" : trObj.find('td:eq(8) :selected').val(),
					"use_yn" : trObj.find('td:eq(10) :selected').val(),
					"save_type" : trObj.find('td:eq(11)').text()
				}
			cnt++;
			
		});

		var sendList = {"list" : list};

		$.ajax({
			url : ctxt+'/mng/sch/saveEtlList.do',
			data : JSON.stringify(sendList),
			processData : false,
			//contentType: false,
			type : 'POST',
			traditional : true,
			cache: false,
			success : function(result) {

				$('#dtlCdList tr').each(function(){	
					var trObj = $(this);
					trObj.find('td:eq(11)').text('N');
				});
				
				fn_showCustomAlert("저장을 완료 하였습니다.");
				
				$('#sel_cd').val(sel_cd);
				
				fn_search();
				
				isInsert = false;
					
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
				
			}
		});
	
	}
	
}


function fn_init(){
	if(confirm("현재시간을 기준으로  초기화하시겠습니까?")){
	    $.ajax({
	        url: '${ctxt}/mng/sch/init.do',
	        async: false,
	        cache: false,    //cache가 있으면 새로운 내용이 업데이트 되지 않는다.
	        type: 'POST',
	        success: function(rtnXml) {
	        	fn_showCustomAlert("정상 처리되었습니다.");
	        	fn_search();
	        }
	    });
	}
}


$(function(){
	fn_search();
	setInterval(function() {fn_search()}, 18000);
});

</script>
<script src="${ctxt}/resources/js/common/write.js" ></script>

	<h3 class="page_title" >스켸줄러 관리</h3>    
		<button class="addItemBtn" name="add_btn" onclick="fn_addDtl()" >추가</button>   
		<button class="addItemBtn" name="cal_btn" onclick="fn_delDtl('T')" >취소</button>  
		<div class="float_r">
			<button class="addItemBtn" name="save_btn" onclick="fn_saveSch()" >저장</button>
		</div>
		<form action="" name="etl_form">
	     	<table id="sch_tb" class="table_h"> 
	        	<colgroup>
		           	<col width="5%" />
		            <col width="10%" />
		           	<col width="20%" />   
		           	<col width="9%" />
		           	<col class="hide" width="9%" />
		           	<col class="hide"  width="9%" />
		           	<col width="9%" />
		           	<col width="10%" />
		           	<col width="10%" />
		           	<col width="*" />
		           	<col width="9%" />
		           </colgroup> 
		           <thead>    
		         	<tr>            
		         		<th>순번</th>    
			            <th>ID</th>    
			            <th>ETL명</th>            
			            <th>타입</th>                    
			            <th class="hide" >타겟테이블</th>    
			            <th class="hide" >구분</th>     
			            <th>시간간격</th>           
			            <th>시작시간</th>        
			            <th>시작분</th>      
			            <th>다음실행</th>        
			            <th>사용여부</th>        
		        	 </tr>        
		       	</thead>
		       	<tbody id="dtlCdList">       
		        </tbody>
	     	</table>		
     	</form>
		<div class="float_r">
			<button class="button"  onclick="fn_init()" >전체 초기화</button>
		</div>