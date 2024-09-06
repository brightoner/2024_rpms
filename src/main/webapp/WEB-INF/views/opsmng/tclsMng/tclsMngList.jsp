<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">

//dtree, 현재 선택 메뉴, 현재 선택 레벨
var m = new dTree('m'),
	curr_menu = "",
	curr_tcls_lv = "";

$(function(){

	//메뉴트리시작
	m.add(0,-1,'산업기술분류 목록',"javascript:fn_setTclsCode('','','','', 'N','sm0')","top","top");

	//메뉴트리생성
	createMenu();

	//메뉴추가
	$('#add_btn').click(function(){
		fn_addTclsCode();
	});
	//메뉴추가
	$('#del_btn').click(function(){
		fn_delMenu();
	});
	
	//메뉴정보 변경
	$('.table-c').change(function(){
		if($('#save_type').val() != 'I' ){
			$('#save_type').val('U');
		}
	});

	
});

//권한 체크 이벤트 
function fn_chk(name,val){

	$('input[name='+ name +'][value='+ val +']').click();
}

/**
 * 	메뉴 트리 생성
 */
function createMenu(){
	
	var procFn ="";

	$.ajax({
		    url : '${ctxt}/opsmng/tclsMng/readTclsMngList.do',
		    data : {},   //전송파라미터
		    contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
		    type : 'POST',
		    dataType : 'text',   //xml, html, script, json, jsonp, text
		    success : function(result) {
		           	var xmlObj = $(result);
		           	var sel_tcls_id="";
		           	xmlObj.find('item').each(function(cnt){
		           			           		
		           		var obj = new Array();
		           		var tcls_id = $(this).find('tcls_id').text();
		           		var tcls_nm = $(this).find('tcls_nm').text();
		           		var tcls_levl = $(this).find('tcls_levl').text();
		           		var tcls_prts_id = $(this).find('tcls_prts_id').text();		           	
		           		var  use_yn = $(this).find('use_yn').text();
		           		
		         
		           		var strFn = "fn_setTclsCode(";
		           			strFn += "'"+tcls_id+"',";
		           			strFn += "'"+tcls_nm+"',";
		           			strFn += "'"+tcls_levl+"',";
		           			strFn += "'"+tcls_prts_id+"',";
		           			strFn += "'"+use_yn+"',";		           		
		           			strFn += "'sm"+Number(cnt+1) +"')";
		         		m.add(cnt+1, tcls_levl , tcls_nm,  "javascript:"+strFn, tcls_id, tcls_prts_id);	
		         		
		         		if(cnt == 0){
			           		if($('#sel_tcls_id').val() != ""){
			           			sel_tcls_id = $('#sel_tcls_id').val();
			           		}else{
			           			sel_tcls_id = tcls_id;
			           		}	
		         		}
		         		if(procFn == ""){
			         		if(sel_tcls_id == tcls_id){
			         			curr_menu = tcls_id;
			         			curr_tcls_lv = tcls_levl;
			         			procFn = strFn;
			         		}
		         		}
		           	});
		           	var df = $('#dtreeDiv1').html(m.toString());
		           	
		           	
		    },
		    error : function() { // Ajax 전송 에러 발생시 실행
		           fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		    },
		    complete : function(result) { // success, error 실행 후 최종적으로 실행
		    	if(procFn != ""){
		    		eval(procFn);
		    		
		    		
		    	}
		    }
	});
	
}
/**
 * 	메뉴 클릭 이벤트
 */
function fn_setTclsCode(){
	//저장하지 않고 이동하는 경우 확인창 
	if($('#save_type').val() == 'I' || $('#save_type').val() == 'U'){
		var strConfirm = confirm("저장하지 않은 정보가 있습니다.이동하시겠습니까?");
		if(!strConfirm){
			return;
		}
	}

	var sel_id =arguments[5];

	//선택트리 색변경
	$('.dTreeNode > a').attr('style','');
	$('#'+sel_id+'').attr('style','color:#f97e15; font-weight: 600;');
	
	m.openTo(parseInt(sel_id.substring(2)), true); 
	
	//전체 체크박스 해제
	$('input[type=checkbox]').prop('checked',false);
	
	$('#save_type').val('N');  
	$('#tcls_id').val(arguments[0]);
	$('#tcls_id').prop('readonly','readonly');
	$('#tcls_nm').val(arguments[1]);
	$('#tcls_levl').val(arguments[2]);
	if(arguments[3] == '0'){
		$('#tcls_prts_id').val('');
	}else{
		$('#tcls_prts_id').val(arguments[3]);
	}

	$('input[name=use_yn][value='+arguments[4]+']').prop('checked',true);
	curr_menu = arguments[0];
	curr_tcls_lv = arguments[2];
	 
	
	
}

// 메뉴 id 중복체크 
function fn_CodeDupl_chk(param_id){
	var tcls_id = $(param_id).val();
	
	if( tcls_id == '' || tcls_id == undefined){
		
		return;
	}
	
	if($("#save_type").val() != "I"){
		
		return;
	}
	
	$.ajax({
	    url : '${ctxt}/opsmng/tclsMng/duplChkTclsMngList.do',
	    data : {"tcls_id": tcls_id},   //전송파라미터
	    contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
	    type : 'GET',
	    dataType : 'text',
	    success : function(result) {
	    	var rltObj = $(result);

           if(rltObj.find("item").length > 0 ){  // 중복된 id가 존재하면 '1' 일테니 .. 
        	    var kor_levl = ''; 
        	    var tcls_levl =  rltObj.find("item").find("tcls_levl").text();    
           		
        	    if (tcls_levl == 1 ){
					kor_levl = '하위코드 ID';          			           			
           		}else if(tcls_levl == 0){
           			kor_levl = '상위코드 ID';
           		}
        	    
        		fn_showCustomAlert(kor_levl+ "값 중에 중복된 ID가 존재합니다.");
        		$(param_id).val('');
        		$(param_id).focus();
           }
  
	    },
	    error : function() { // Ajax 전송 에러 발생시 실행
	           fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    },
	    complete : function(result) { // success, error 실행 후 최종적으로 실행
	    	
	    }
	});
	
}

/**
 * 	메뉴 저장
 */
function fn_save(){
	var colList = ["tcls_id","tcls_nm"];
	var nmList = ["코드ID","코드명"];
	if(checkVal(colList,nmList)){
		return;	
	}

	var strConfirm = confirm("저장하시겠습니까?");
	

	if(strConfirm){
		var form = document.tcls_form;
		form.action="${ctxt}/opsmng/tclsMng/saveTclsMng.do";
		form.submit();	
	}	
}

/**
 * 	메뉴 추가
 */
function fn_addTclsCode(){
	
	$("#tcls_form")[0].reset();  

	$('#save_type').val('I');
	$('#tcls_id').prop('readonly','');
	
	$('input[name=use_yn][value=Y]').prop('checked',true);

	$('#tcls_prts_id').val(curr_menu);
	
	if(curr_tcls_lv == ""){
		$('#tcls_levl').val('0');
	}else{
		$('#tcls_levl').val(Number(curr_tcls_lv)+1);
	}
}

/**
 * 메뉴 삭제
 */
function fn_delMenu(){
	if( $('#tcls_id').val() == '' && $('#tcls_prts_id').val() == '' ){
		fn_showCustomAlert("코드정보가 없습니다.",'c');
		return;
	}
	fn_showCustomConfirm("warning",$('#tcls_nm').val() +"를 삭제하시겠습니까?\n(하위코드도 같이 삭제됩니다.)", function() {
		var form = document.tcls_form;
			form.action="${ctxt}/opsmng/tclsMng/delTclsMng.do";
			form.submit();	
	});
}

</script>



<h3 class="page_title" id="title_div"><span class="adminIcon"></span>산업기술분류 관리</h3>    
<input type="hidden" id="sel_tcls_id" value="${sel_tcls_id}" />

<h2><!-- <spring:message code="menu.mng.menuMgmt" /> --></h2>
<div class="admin-system">
	<div class="part">
		<div class="text_r">
	 
	   		<a href = "javascript:void(0);" id="add_btn"  class="btn btn-secondary" >코드추가</a>
	   		<a href = "javascript:void(0);" id="del_btn"  class="btn btn-secondary" >코드삭제</a>
  		</div>
	  	<div id="dtreeDiv1"></div>
	</div>
	<div>
		<form id ="tcls_form" name="tcls_form"  action="mng_" method="post">
			<input type="hidden" name="save_type" id="save_type" value="" />
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<h5 class="title">분류 코드 정보 <span>(코드는 최대 3단계 생성)</span></h5>
		 
		<div class="table-c-wrap">
		<table class="table_v th_l bor_top" cellspacing="0" cellpadding="0" summary="코드정보"> 
			<caption>산업기술분류 관리</caption> 
			<colgroup>
	            <col width="25%" />
	            <col width="75%" />
	         </colgroup> 
			<thead>  
			</thead>  
			<tbody> 
				<tr> 
					<th>상위코드 ID</th>
					<td><input name="tcls_prts_id" id="tcls_prts_id" class="keyword" accesskey="s" type="text"  title="상위코드ID" readonly="readonly"  /></td>
				</tr> 
				<tr> 
					<th scope="col">코드레벨</th>
					<td><input name="tcls_levl" id="tcls_levl" accesskey="s" type="text" title="코드레벨" value=""  readonly="readonly" />
					</td> 
				</tr> 
	               <tr> 
					<th><font color="red">*</font> 코드ID</th>
					<td><input name="tcls_id" id="tcls_id" class="keyword" accesskey="s" type="text" onblur="fn_CodeDupl_chk(this)" title="코드ID"/></td>
				</tr>
				<tr> 
					<th><font color="red">*</font> 코드명</th>
					<td><input name="tcls_nm" id="tcls_nm" class="keyword" accesskey="s" type="text" title="코드명" /></td>
				</tr> 
				
				<tr>
	            	<th scope="row">사용여부</th>
	             	<td><div class="item">
						<input type="radio" class="i_radio" id="tcls_use_y"  name="use_yn" value="Y"  title="사용여부,사용함"  />
						<label for="tcls_use_y" title="사용함">사용함</label>
						<input type="radio" class="i_radio" id="tcls_use_n"  name="use_yn" value="N" title="사용여부,사용안함"  />
						<label for="tcls_use_n" title="사용안함">사용안함</label>
	            	</div></td>
	            </tr>
				
			</tbody> 
		</table>
		
		</div>
		</form>
		<div align="center">
			<a href = "javascript:void(0);" onclick="fn_save();" name="save_btn" class="btn btn-secondary" >저장</a>
		</div>
	</div>
</div>
	
