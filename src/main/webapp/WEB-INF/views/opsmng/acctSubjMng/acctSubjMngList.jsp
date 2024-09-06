<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">

//dtree, 현재 선택 계정과목, 현재 선택 레벨
var m = new dTree('m'),
	curr_menu = "",
	curr_subj_lv = "";

$(function(){

	//계정과목트리시작
	m.add(0,-1,'계정과목 목록',"javascript:fn_setAcctSubjCode('','','','','', 'N','sm0')","top","top");

	//계정과목트리생성
	createAcctSubj();

	//계정과목추가
	$('#add_btn').click(function(){
		fn_addAcctSubjCode();
	});
	//계정과목추가
	$('#del_btn').click(function(){
		fn_delAcctSubj();
	});
	
	//계정과목정보 변경
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
 * 	계정과목 트리 생성
 */
function createAcctSubj(){
	
	var procFn ="";

	$.ajax({
		    url : '${ctxt}/opsmng/acctSubjMng/readAcctSubjMngList.do',
		    data : {},   //전송파라미터
		    contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
		    type : 'POST',
		    dataType : 'text',   //xml, html, script, json, jsonp, text
		    success : function(result) {
		           	var xmlObj = $(result);
		           	var sel_subj_id="";
		           	xmlObj.find('item').each(function(cnt){
		           			           		
		           		var obj = new Array();
		           		var subj_id = $(this).find('subj_id').text();
		           		var subj_nm = $(this).find('subj_nm').text();
		           		var subj_levl = $(this).find('subj_levl').text();
		           		var subj_no = $(this).find('subj_no').text();
		           		var subj_prts_id = $(this).find('subj_prts_id').text();		           	
		           		var  use_yn = $(this).find('use_yn').text();
		           		
		         
		           		var strFn = "fn_setAcctSubjCode(";
		           			strFn += "'"+subj_id+"',";
		           			strFn += "'"+subj_nm+"',";
		           			strFn += "'"+subj_levl+"',";		           		
		           			strFn += "'"+subj_prts_id+"',";
		           			strFn += "'"+use_yn+"',";	
		           			strFn += "'"+subj_no+"',";
		           			strFn += "'sm"+Number(cnt+1) +"')";
		         		m.add(cnt+1, subj_levl , subj_no +'  '+subj_nm ,  "javascript:"+strFn, subj_id, subj_prts_id);	
		         		
		         		if(cnt == 0){
			           		if($('#sel_subj_id').val() != ""){
			           			sel_subj_id = $('#sel_subj_id').val();
			           		}else{
			           			sel_subj_id = subj_id;
			           		}	
		         		}
		         		if(procFn == ""){
			         		if(sel_subj_id == subj_id){
			         			curr_menu = subj_id;
			         			curr_subj_lv = subj_levl;
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
 * 	계정과목 클릭 이벤트
 */
function fn_setAcctSubjCode(){
	//저장하지 않고 이동하는 경우 확인창 
	if($('#save_type').val() == 'I' || $('#save_type').val() == 'U'){
		var strConfirm = confirm("저장하지 않은 정보가 있습니다.이동하시겠습니까?");
		if(!strConfirm){
			return;
		}
	}

	var sel_id =arguments[6];

	//선택트리 색변경
	$('.dTreeNode > a').attr('style','');
	$('#'+sel_id+'').attr('style','color:#f97e15; font-weight: 600;');
	
	m.openTo(parseInt(sel_id.substring(2)), true); 
	
	//전체 체크박스 해제
	$('input[type=checkbox]').prop('checked',false);
	
	$('#save_type').val('N');  
	$('#subj_id').val(arguments[0]);
	$('#subj_id').prop('readonly','readonly');
	$('#subj_nm').val(arguments[1]);
	$('#subj_levl').val(arguments[2]);
	
	if(arguments[3] == '0'){
		$('#subj_prts_id').val('');
	}else{
		$('#subj_prts_id').val(arguments[3]);
	}

	$('input[name=use_yn][value='+arguments[4]+']').prop('checked',true);
	$('#subj_no').val(arguments[5]);
	curr_menu = arguments[0];
	curr_subj_lv = arguments[2];
	 
	
	
}

// 계정과목 id 중복체크 
function fn_CodeDupl_chk(param_id){
	var subj_id = $(param_id).val();
	
	if( subj_id == '' || subj_id == undefined){
		
		return;
	}
	
	if($("#save_type").val() != "I"){
		
		return;
	}
	
	$.ajax({
	    url : '${ctxt}/opsmng/acctSubjMng/duplChkAcctSubjMngList.do',
	    data : {"subj_id": subj_id},   //전송파라미터
	    contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
	    type : 'GET',
	    dataType : 'text',
	    success : function(result) {
	    	var rltObj = $(result);

           if(rltObj.find("item").length > 0 ){  // 중복된 id가 존재하면 '1' 일테니 .. 
        	    var kor_levl = ''; 
        	    var subj_levl =  rltObj.find("item").find("subj_levl").text();    
           		
        	    if (subj_levl == 1 ){
					kor_levl = '하위코드 ID';          			           			
           		}else if(subj_levl == 0){
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
 * 	계정과목 저장
 */
function fn_save(){
	var colList = ["subj_id","subj_nm", "subj_no"];
	var nmList = ["코드ID","코드명","코드순번"];
	if(checkVal(colList,nmList)){
		return;	
	}

	var strConfirm = confirm("저장하시겠습니까?");
	

	if(strConfirm){
		var form = document.subj_form;
		form.action="${ctxt}/opsmng/acctSubjMng/saveAcctSubjMng.do";
		form.submit();	
	}	
}

/**
 * 	계정과목 추가
 */
function fn_addAcctSubjCode(){
	
	$("#subj_form")[0].reset();  

	$('#save_type').val('I');
	$('#subj_id').prop('readonly','');
	
	$('input[name=use_yn][value=Y]').prop('checked',true);

	$('#subj_prts_id').val(curr_menu);
	
	if(curr_subj_lv == ""){
		$('#subj_levl').val('0');
	}else{
		$('#subj_levl').val(Number(curr_subj_lv)+1);
	}
}

/**
 * 계정과목 삭제
 */
function fn_delAcctSubj(){
	if( $('#subj_id').val() == '' && $('#subj_prts_id').val() == '' ){
		fn_showCustomAlert("코드정보가 없습니다.",'c');
		return;
	}
	
	fn_showCustomConfirm("warning",$('#subj_nm').val()+"를 삭제하시겠습니까?\n(하위코드도 같이 삭제됩니다.)", function() {
		var form = document.subj_form;
		form.action="${ctxt}/opsmng/acctSubjMng/delAcctSubjMng.do";
		form.submit();	
	});
}

</script>



<h3 class="page_title" id="title_div"><span class="adminIcon"></span>계정과목 관리</h3>    
<input type="hidden" id="sel_subj_id" value="${sel_subj_id}" />

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
		<form id ="subj_form" name="subj_form"  action="mng_" method="post">
			<input type="hidden" name="save_type" id="save_type" value="" />
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<h5 class="title">계정과목 코드 정보 <span></span></h5>
		 
		<div class="table-c-wrap">
		<table class="table_v th_l bor_top" cellspacing="0" cellpadding="0" summary="코드정보"> 
			<caption>계정과목 관리</caption> 
			<colgroup>
	            <col width="25%" />
	            <col width="75%" />
	         </colgroup> 
			<thead>  
			</thead>  
			<tbody> 
				<tr> 
					<th>상위코드 ID</th>
					<td><input name="subj_prts_id" id="subj_prts_id" class="keyword" accesskey="s" type="text"  title="상위코드ID" readonly="readonly"  /></td>
				</tr> 
				<tr> 
					<th scope="col">코드레벨</th>
					<td><input name="subj_levl" id="subj_levl" accesskey="s" type="text" title="코드레벨" value=""  readonly="readonly" />
					</td> 
				</tr> 
	               <tr> 
					<th><font color="red">*</font> 코드ID</th>
					<td><input name="subj_id" id="subj_id" class="keyword" accesskey="s" type="text" onblur="fn_CodeDupl_chk(this)" title="코드ID"/></td>
				</tr>
				<tr> 
					<th><font color="red">*</font> 코드명</th>
					<td><input name="subj_nm" id="subj_nm" class="keyword" accesskey="s" type="text" title="코드명" /></td>
				</tr> 
				<tr> 
					<th><font color="red">*</font> 코드 No</th>
					<td><input name="subj_no" id="subj_no" class="keyword" accesskey="s" type="text" title="코드명" /></td>
				</tr> 
				<tr>
	            	<th scope="row">사용여부</th>
	             	<td><div class="item">
						<input type="radio" class="i_radio" id="subj_use_y"  name="use_yn" value="Y"  title="사용여부,사용함"  />
						<label for="subj_use_y" title="사용함">사용함</label>
						<input type="radio" class="i_radio" id="subj_use_n"  name="use_yn" value="N" title="사용여부,사용안함"  />
						<label for="subj_use_n" title="사용안함">사용안함</label>
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
	
