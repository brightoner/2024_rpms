var ctxt = ""
var msg="";

/**
 * 테이블 리 필수값 체크 
 * param[0] : 체크colum list
 * param[1] : 체크colum list
 * return 
 */
function fn_valchk(tbody_id, param_idx, param_nm){
	
	var isVal = true;
	
	$('#'+tbody_id+' tr').each(function(cnt){
		var trObj = $(this);
		for(var i=0; i <param_idx.length;i++){
			if(trObj.find('td:eq('+param_idx[i]+') input[type=text]').val() == ''){
				isVal= false;
				msg= cnt+1+"번째 줄 ["+param_nm[i]+"]는 필수 입력 사항입니다.";
				paramObj=trObj;
				trObj.find('td:eq('+param_idx[i]+') input[type=text]').focus();
				return false;
			}
		}
	});
	return isVal;
}

/**
* 로우 선택
*/
function fn_setRow(obj,tb_nm){
/*	var nm = "";
	if(tb_nm == undefined || tb_nm == ''){
		nm = "dtlCdList";
	}else{
		nm = tb_nm;
	}
	$('.on').attr('class','');
	$(obj).attr('class','on');	*/
}

/**
 *	상세코드 저장
 */
function fn_saveForm(type){
	var sel_cd ="";
	var paramObj;
	
	var param_idx = [1,2];
	var param_nm = ["elmnt_id","elmnt_명"]
	
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
			if(trObj.find('td:eq(7)').text() == 'N'){
				return true;
			}
			
/*			if(cnt == 0){   
				sel_cd=trObj.find('td:eq(8)').text();
			}*/
			
			list[cnt] = {
					"elmnt_id" : trObj.find('td:eq(1) input[type=text]').val(),
					"elmnt_nm" : encodeURIComponent(trObj.find('td:eq(2) input[type=text]').val()),
					"multi_yn" : trObj.find('td:eq(3) :selected').val(),	
					"data_type" : trObj.find('td:eq(4) :selected').val(),
					"cd" : trObj.find('td:eq(5) :selected').val(),
					"lbl_kor" : encodeURIComponent(trObj.find('td:eq(6) input[type=text]').val()),
					"lbl_eng" : trObj.find('td:eq(7) input[type=text]').val(),
					"url" : trObj.find('td:eq(8) input[type=text]').val(),
					"save_type" : trObj.find('td:eq(9)').text()
			}
			cnt++;

		});  
		
		var sendList = {"list" : list};
		
		$.ajax({
			url : ctxt+'/mng/form/saveFormList.do',
			data : JSON.stringify(sendList),  
			processData : false,    
			//contentType: false,
			type : 'POST',
			traditional : true,
			cache: false,
			success : function(result) {
				
				/*$('#dtlCdList tr').each(function(){	
					var trObj = $(this);
					trObj.find('td:eq(8)').text('N');
				});*/
				
				fn_search();
				
				fn_showCustomAlert("저장을 완료 하였습니다.");
				
				$('#sel_cd').val(sel_cd);
				
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
				
			}
		});
		
	}
	
}

/**
 *	상세코드 저장
 */
function fn_saveWrkDtlForm(type){
	var sel_cd ="";
	var paramObj;
	
	var param_idx = [3];
	var param_nm = ["순서"]
	
	var isVal = fn_valchk('dtlFrmDtlList',param_idx,param_nm);   
	
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
		$('#dtlFrmDtlList tr').each(function(){
			
			var trObj = $(this);
			list[cnt] = {
					"frm_id" : trObj.find('td:eq(0)').text(),
					"frm_seq" : trObj.find('td:eq(1)').text(),
					"frm_elmnt_id" : trObj.find('td:eq(2) :selected').val(),
					"frm_seq" : trObj.find('td:eq(3) input[type=text]').val(),  
					"frm_col" : trObj.find('td:eq(4) :selected').val(),  
					"use_yn" : trObj.find('td:eq(5) :selected').val(),
					"lst_yn" : trObj.find('td:eq(6) :selected').val(),
					"srch_yn" : trObj.find('td:eq(7) :selected').val(),      
					"save_type" : trObj.find('td:eq(8)').text()
			}
			cnt++;    
			
		});  
		  
		var sendList = {"list" : list};
		
		$.ajax({
			url : ctxt+'/mng/form/saveWrDtlFormList.do',
			data : JSON.stringify(sendList),  
			processData : false,    
			//contentType: false,
			type : 'POST',
			traditional : true,
			cache: false,
			success : function(result) {
				
				$('#dtlFrmDtlList tr').each(function(){	
					var trObj = $(this);
					trObj.find('td:eq(8)').text('N');
				});
				alert("저장을 완료 하였습니다.");
				$('#sel_cd').val(sel_cd);
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
				
			}
		});
		
	}
	
}

/**
 *	상세코드 저장
 */
function fn_saveWrkForm(type){
	var sel_cd ="";
	var paramObj;
	
	var param_idx = [3];
	var param_nm = ["frm_id"]
	
	var isVal = fn_valchk('dtlFrmList',param_idx,param_nm);
	
	if(!isVal){
		fn_showCustomAlert(msg);
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
		$('#dtlFrmList tr').each(function(){
			
			var trObj = $(this);
			//데이터 조작이 없는 경우 처리안함
			if(trObj.find('td:eq(5)').text() == 'N'){
				return true;
			}
			
			list[cnt] = {
					"frm_id" : trObj.find('td:eq(1) input[type=text]').val(),
					"frm_gbn" : trObj.find('td:eq(2) :selected').val(),
					"frm_etc" : encodeURIComponent(trObj.find('td:eq(3) input[type=text]').val()),
					"use_yn" : trObj.find('td:eq(4) :selected').val(),
					"save_type" : trObj.find('td:eq(5)').text()
			}
			cnt++;
			
		});  
		
		var sendList = {"list" : list};
		
		$.ajax({
			url : ctxt+'/mng/form/saveWrkFormList.do',
			data : JSON.stringify(sendList),  
			processData : false,    
			//contentType: false,
			type : 'POST',
			traditional : true,
			cache: false,
			success : function(result) {
				
				$('#dtlFrmList tr').each(function(){	
					var trObj = $(this);
					trObj.find('td:eq(5)').text('N');
				});
				
				fn_showCustomAlert("저장을 완료 하였습니다.");
				
				$('#sel_cd').val(sel_cd);
				
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
				
			}
		});
		
	}
	
}

/**
* 상세코드 데이터 수정시   
*/
function fn_upd(obj, idx){
	if($(obj).parent().parent().find('td:eq('+idx+')').text() != 'I'){
		$(obj).parent().parent().find('td:eq('+idx+')').text('U');
	}
}