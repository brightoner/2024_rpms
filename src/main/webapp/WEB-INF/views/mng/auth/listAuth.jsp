<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="kr.go.rastech.ptl.mng.menu.vo.MngMenuVo"%>

<script type="text/javascript">

//dtree, 현재 선택 메뉴, 현재 선택 레벨
var m = new dTree('m'),
	curr_menu = "",
	curr_menu_nm = "";

$(function(){
	//메뉴트리시작
	m.add(0,-1,'메뉴 목록',"javascript:fn_setMenu('','','sm0')","top","top");

	//메뉴트리생성
	createMenu();
	
});

/**
 * 	메뉴 트리 생성
 */
function createMenu(){
	
	var procFn ="";

	$.ajax({
		    url : '${ctxt}/mng/menu/readMenuList.do',
		    data : {},   //전송파라미터
		    contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
		    type : 'POST',
		    dataType : 'text',   //xml, html, script, json, jsonp, text
		    success : function(result) {
		           	var xmlObj = $(result);
		           	var sel_menu_id="";
		           	xmlObj.find('item').each(function(cnt){
		           			           		
		           		var obj = new Array();
		           		var menu_id = $(this).find('menu_id').text();
		           		var menu_nm = $(this).find('menu_nm').text();
		           		var menu_levl = $(this).find('menu_levl').text();
		           		var menu_prts_id = $(this).find('menu_prts_id').text();
		           		var menu_ord = $(this).find('menu_ord').text();
		           		var menu_url_path = $(this).find('menu_url_path').text();
		           		var top_menu_yn = $(this).find('top_menu_yn').text();
		           		var left_menu_yn = $(this).find('left_menu_yn').text();
		           		var menu_use_yn = $(this).find('menu_use_yn').text();
		           		
		           		var strFn = "fn_setMenu(";
		           			strFn += "'"+menu_id+"',";
		           			strFn += "'"+menu_nm+"',";
		           			strFn += "'sm"+Number(cnt+1) +"')";
		         		m.add(cnt+1, menu_levl , menu_nm,  "javascript:"+strFn, menu_id, menu_prts_id);	
		         		
		         		if(cnt == 0){
			           		if($('#sel_menu_id').val() != ""){
			           			sel_menu_id = $('#sel_menu_id').val();
			           		}else{
			           			sel_menu_id = menu_id;
			           		}	
		         		}

		         		if(procFn == ""){
			         		if(sel_menu_id == menu_id){
			         			curr_menu = menu_id;
			         			curr_menu_nm=menu_nm;
			         			procFn = strFn;
			         		}
		         		}
		           	});
		           	var df = $('#dtreeDiv1').html('<br /><br />'+m.toString());

		    },
		    error : function() { // Ajax 전송 에러 발생시 실행
		           fn_showCustomAlert("메뉴조회에 실패하였습니다.");
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
function fn_setMenu(){
 	//저장하지 않고 이동하는 경우 확인창 
	if($('#dtlAuthList tr').find('td:eq(7)').text().indexOf('I') != -1 
			|| $('#dtlAuthList tr').find('td:eq(7)').text().indexOf('U') != -1 ){
		var strConfirm = confirm("저장하지 않은 정보가 있습니다.이동하시겠습니까?");
		if(!strConfirm){
			return;
		}
	} 
	
	var sel_id =arguments[2];
	//트리 색변경
	$('.dTreeNode > a').attr('style','');
	$('#'+sel_id+'').attr('style','color:#f97e15; font-weight: 600;');
	
	curr_menu = arguments[0];
	curr_menu_nm = arguments[1];
	
	$('#dtlAuthList').children().remove();
	
	//코드 참조값1에서 보여주고 싶은 화면만 보이기 처리

	//url권한정보 조회
	if(arguments[0] != ''){
		fn_readDtl();
	}
}
/**
 * 권한 추가
 */
function fn_add(){
	if(curr_menu == ''){
		fn_showCustomAlert("메뉴정보를 선택하세요.","c");
		return false;
	}
	
	$('#dtlAuthList .on').attr('class','off');
	
	
	$('#auth_cd option').each(function(index){
		if($(this).attr('class') != '' && $(this).attr('class') != undefined){
			if($(this).attr('class').indexOf(curr_menu) != -1){
				$(this).attr('style','')
			}else{
				$(this).attr('style','display:none;')
			}
		}else{
			$(this).attr('style','');
		}
	});
	
	
	var com_auth_cd = $('#auth_cd').html();
	
	var html="";
		html += '<tr class="on" onclick="fn_setRow(this)"  name="tr_cls">';         
	    html += '  <td class="text_c">'+parseInt($('#dtlAuthList >tr').length +1)+'</td>';            
	    html += '  <td>'+curr_menu_nm +'</td>';            
	    html += '  <td style="text-align:center" ><select name="auth_cd" style="width=100%;" >'+com_auth_cd+'</select></td>';   
	    html += '  <td><input type="text" style="width:100%;" /></td>';   
	    html += '  <td><input type="text" style="width:100%;" /></td>';   
	    html += '  <td style="display:none;" >'+curr_menu+'</td>';    
	    html += '  <td style="display:none;" ></td>';    
	    html += '  <td style="display:none;">I</td>'; 
		html += '</tr>'; 

	$('#dtlAuthList:last').append(html);
	
}

/*
* 권한 삭제
*/
function fn_del(type){
	var delRow = $('#dtlAuthList .on');
	if(delRow.length == 0){
		fn_showCustomAlert("선택로우가 없습니다.","c");
	}

	if(type != "D"){
	
		if($(delRow).find('td:eq(7)').text() != 'I'){
			fn_showCustomAlert('취소 대상이 아닙니다.','c');
			return false;
		}
	}
	
	//선택로우 삭제	
	$(delRow).remove();
	
 	if($('#dtlAuthList tr').length > 0){
 		$('#dtlAuthList .on').attr('class','off');
 		$('#dtlAuthList tr:last').attr('class','on');	
 	}
 	
}

/**
* 로우 선택
*/
function fn_setRow(obj){
	$('#dtlAuthList .on').attr('class','off');
	$(obj).attr('class','on');	
}

/**
*	권한url 저장 
*/
function fn_saveUrl(){
	var isVal= true;
	var msg="";   
	var paramObj;
	var validUrlPattern = /^\/(?:[^\/]+\/){2,}[^\/]+$/;
	$('#dtlAuthList tr').each(function(cnt){
		var trObj = $(this);

		if(trObj.find('td:eq(3) input[type=text]').val() == ''){
			isVal= false;
			paramObj=trObj;
			msg= cnt+1+"번째 줄 URL은 필수 입력 사항입니다.";
			trObj.find('td:eq(3) input[type=text]').focus();
			return false;
		}

		if(!validUrlPattern.test(trObj.find('td:eq(3) input[type=text]').val())){
			isVal= false;
			paramObj=trObj;
			msg= cnt+1+"번째 줄 URL 형식은 3개 이상 섹션으로 구성하여주세요.<br/>두번째 섹션 기준으로 메뉴가 선택됩니다.";
			trObj.find('td:eq(3) input[type=text]').focus();
			return false;
		}
		if(trObj.find('td:eq(4) input[type=text]').val() == ''){
			isVal= false;
			paramObj=trObj;
			msg= cnt+1 +"번째 줄 URL설명은 필수 입력 사항입니다.";
			trObj.find('td:eq(4) input[type=text]').focus();
			return false;
		}

	});
	
	if(!isVal){
		fn_showCustomAlert(msg);
		fn_setRow(paramObj);
		return;
	}
	
	var strConfirm = confirm("저장하시겠습니까?");

	if(strConfirm){
		
		var list = [];
		var cnt=0;
		$('#dtlAuthList tr').each(function(){
			
			var trObj = $(this);
			//데이터 조작이 없는 경우 처리안함
			if(trObj.find('td:eq(7)').text() == 'N'){
				return true;
			}
			
			list[cnt] = {
					"menu_id" : trObj.find('td:eq(5)').text(),
					"auth_gbn" : trObj.find('td:eq(2) :selected').val(),
					"url" : encodeURIComponent(trObj.find('td:eq(3) input[type=text]').val()),
					"url_desc" : trObj.find('td:eq(4) input[type=text]').val(),
					"save_type" : trObj.find('td:eq(7)').text(),
					"url_seq" : trObj.find('td:eq(6)').text()
					
				}
			cnt++;
			
		});

		var sendList = {
			 "list" : list
			,"sel_menu_id":curr_menu
		};

		$.ajax({
			url : '${ctxt}/mng/auth/saveUrlAuth.do',
			data : JSON.stringify(sendList),
			processData : false,
			//contentType: false,
			type : 'POST',
			traditional : true,
			cache: false,
			success : function(result) {

				$('#dtlAuthList tr').each(function(){	
					var trObj = $(this);
					trObj.find('td:eq(7)').text('N');
				});
				
				fn_showCustomAlert("저장을 완료 하였습니다.");

				setTimeout(fn_load(result), 1500);
					
			}, 
			error : function() { // Ajax 전송 에러 발생시 실행
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
				
			}
		});
	}	
}

function fn_load(sel_menu_id){
	location.href="${ctxt}/mng/auth/listAuth.do?sel_menu_id="+sel_menu_id;
}

/**
 * 권한url 삭제
 */
function fn_delUrl(){
	var del_id =  $('#dtlAuthList .on').find('td:eq(6)').text();

	if($('#dtlAuthList .on').find('td:eq(7)').text() == 'I'){
		fn_showCustomAlert("저장된 데이터가 아닙니다.",'c');
		return;
	}
	
	var strConfirm = confirm("선택행 데이터를 삭제하시겠습니까?");

	if(strConfirm){
	
		$.ajax({
			url:"${ctxt}/mng/auth/delUrlAuth.do",
			type:'GET',
			data:{"url_seq":del_id,
				  "sel_menu_id":curr_menu},
			contentType: "text/xml;charset=utf-8",
			dataType:"text",
			success:function(result){
				fn_del("D");
				fn_showCustomAlert("삭제처리가 완료되었습니다.");
				
				setTimeout(fn_load(result), 1500);
			},
			error:function(){
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			}
		});
		
	}
}

/*
 * 상세보기 이벤트
 */
function fn_readDtl(){
	
	var valList = new Array();
	var textList = new Array();
	
	
	$('#auth_cd option').each(function(index){
		if($(this).attr('class') != '' && $(this).attr('class') != undefined){
			if($(this).attr('class').indexOf(curr_menu) != -1){
				$(this).attr('style','');
				valList.push($(this).val());
				textList.push($(this).text());
			}else{
				$(this).attr('style','display:none;')
			}
		}else{
			valList.push($(this).val());
			textList.push($(this).text());
			$(this).attr('style','');
		}
	});
	
	$.ajax({
		url:"${ctxt}/mng/auth/readUrlAuth.do",
		type:'GET',
		data:{"menu_id":curr_menu},
		contentType: "text/xml;charset=utf-8",
		dataType:"text",
		success:function(rtnXml){
			
			var xmlObj = $(rtnXml).find('item');

			var html="";

			xmlObj.each(function(cnt){
				html += '<tr onclick="fn_setRow(this)">';
			    html += '  <td>'+Number(cnt +1)+'</td>';            
			    html += '  <td>'+$(this).find('menu_nm').text() +'</td>';     
			    html += '  <td style="text-align:center" ><select name="auth_cd" style="width=100%;" onchange="fn_upd(this)" >';   
				for(var i=0;i < valList.length; i++ ){
					if($(this).find('auth_gbn').text() == valList[i]){
						html += '<option value="'+ valList[i] + '" selected="selected" >'+textList[i]+'</option>';
					}else{
						html += '<option value="'+ valList[i] + '" >'+textList[i]+'</option>';
					}	
				}
				html += '  </select></td>';
			    html += '  <td><input type="text" style="width:95%;" value="'+$(this).find('url').text() +'" onchange="fn_upd(this)" /></td>';   
			    html += '  <td><input type="text" style="width:95%;" value="'+$(this).find('url_desc').text() +'" onchange="fn_upd(this)" /></td>';   
			    html += '  <td style="display:none;" >'+$(this).find('menu_id').text() +'</td>';    
			    html += '  <td style="display:none;" >'+$(this).find('url_seq').text() +'</td>';    
			    html += '  <td style="display:none;">N</td>'; 
				html += '</tr>';
			});
			
			$('#dtlAuthList').html(html);
			//트리코드 선택시 첫번째 행 선택
			$('#dtlAuthList tr:first').attr('class','on');	
			
		},
		error:function(){
			fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		}
	});
}

/**
* 로우 선택
*/
function fn_setRow(obj){
	$('#dtlAuthList .on').attr('class','off');
	$(obj).attr('class','on');	
}
	/**
* 상세코드 데이터 수정시 
*/
function fn_upd(obj){
	$(obj).parent().parent().find('td:eq(7)').text('U');
}
</script>

<h3 class="page_title" id="title_div"><span class="adminIcon"></span>URL관리</h3>    
<input type="hidden" id="sel_menu_id" value="${sel_menu_id}" />

<select id="auth_cd" style="display: none;" >
<c:forEach var="auth" items="${auth_cd}">
	<option value="${auth.cd}" class="${auth.ref_val1}">${auth.cd_nm}</option>
</c:forEach>
</select>

<div class="con_box admin-system">
   	
   	<div class="part-narrow">
           <div id="dtreeDiv1">
           </div>  
	</div>

	<div>
		<input type="hidden" name="save_type" id="save_type" value="" />
		<div class="clearfix ma_b_10">
			<div style="float: left;" >
			 
				<a href = "javascript:void(0);" onclick="fn_add();"  class="btn btn-secondary" >권한추가</a>
				<a href = "javascript:void(0);" onclick="fn_del('T');"  class="btn btn-secondary" >추가취소</a>
	     		
			</div>
	     	<div style="float: right;">
		
				<a href = "javascript:void(0);" onclick="fn_delUrl();"  class="btn btn-secondary" >삭제</a>
				<a href = "javascript:void(0);"  onclick="fn_saveUrl();"  class="btn btn-secondary" >저장</a>
				<!--addItemBtn 부분 ID가 중복되어 class로 교체-->
			</div>
		</div>
		<table id="example" class="table_h">        
         	<colgroup>
            	<col width="5%" />
            	<col width="15%" />
                <col width="13%" />  
            	<col width="35%" />
            	<col width="32%" />
            </colgroup> 
            <thead>    
	         	<tr>            
	         		<th>순서</th>    
	         		<th>메뉴</th>    
		            <th>권한분류</th>    
		            <th>url명</th>            
		            <th>url설명</th>                          
	        	 </tr>        
        	</thead>
        	<tbody id="dtlAuthList">       
	        </tbody>
      	</table>
	</div>
</div>