<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 메뉴 코드 부분 --%>
  
  <script type="text/javascript">

	$(function() {
		//트리정보
		fn_setTree();

		$('#up').on('click',function(){moveRowUp()});

		$('#down').on('click',function(){moveRowDown()});
		
		//상세코드 위로 이동
		var moveRowUp = function() {

			var element = $('#dtlCdList .rowOn');
			
			if( element.prev().html() != null  && element.prev().attr("id") != "dtlCdList"){
				element.insertBefore(element.prev());
				changNum();
			} else {
				fn_showCustomAlert("최상단입니다.")
			}
		};
		//상세코드 아래로 이동
		var moveRowDown = function() {
			
			var element = $('#dtlCdList .rowOn');
			
			if( element.next().html() != null ){
				element.insertAfter(element.next());
				changNum();
			}  else {
				fn_showCustomAlert("최하단입니다.");
			}
		};
		
		//조회버튼 클릭 이벤트
		$('#search_btn').on('click',function(){fn_search()});
		$('#search_cd').on('keyup',function(e){if(e.which  == 13){ fn_search()} });
		
		
	});	//온로드 이벤트 종료
	
	/**
	*  조회 이벤트
	*/
	function fn_search(){
		var cd_nm = $('#search_cd').val();
		
		if(cd_nm != ''){
			$('li > strong').each(function(){
				if($(this).text().indexOf(cd_nm) != -1){
					$(this).click();
					return false;
				}
			})
		}
	}
	
	/**
	* 상세코드 순번 변경
	*/
	function changNum() {
		var num = 0;
		$('#dtlCdList tr').each(function(){
			num++;
			$(this).children().eq(0).text(num);
			//순번 변경시 추가 데이터 제외하고 U타입으로 변경
			if($(this).children().eq(9).text() != 'I'){
				$(this).children().eq(9).text('U');
			}
		});
	};
	
	/**
	*	상위코드 추가
	*/
	function fn_addUpCd(){
// 		$(".admin form")[0].reset();  
		$(".admin-system form")[0].reset();  
		$('#dtlCdList').children().remove();
		$('#save_type').val('I');
		$('#cd').removeAttr('readonly');
	}
	    
	/*
	* 상위코드 수정
	*/  
	function fn_chgst(){
		if($('#save_type').val() != 'I'){
			$('#save_type').val('U');
		}
	}
	
	/**
	*	상위코드 저장
	*/
	function fn_saveUpCd(){
		if($('#save_type').val() == 'N'){
			fn_showCustomAlert('저장할 내역이 없습니다.','c');
			return;
		}
		
		var colList = ["cd","cd_nm"];
		var nmList = ["코드","코드명"];
		if(checkVal(colList,nmList)){
			return;	
		}
		
		var form = document.upCdForm;
		form.action="${ctxt}/mng/code/insertCdMng.do";
		form.submit();
	}
	
	/**
	*	상세코드 저장
	*/
	function fn_saveCd(type){
		var isVal= true;
		var msg="";
		var sel_cd ="";
		var paramObj;
		$('#dtlCdList tr').each(function(cnt){
			var trObj = $(this);
			if(trObj.find('td:eq(1) input[type=text]').val() == ''){
				isVal= false;
				msg= cnt+1+"번째 줄 코드는 필수 입력 사항입니다.";
				paramObj=trObj;
				trObj.find('td:eq(1) input[type=text]').focus();
				return false;
			}
			if(trObj.find('td:eq(2) input[type=text]').val() == ''){
				isVal= false;
				paramObj=trObj;
				msg= cnt+1+"번째 줄 코드명은 필수 입력 사항입니다.";
				trObj.find('td:eq(2) input[type=text]').focus();
				return false;
			}
			if(trObj.find('td:eq(3) input[type=text]').val() == ''){
				isVal= false;
				paramObj=trObj;
				msg= cnt+1 +"번째 줄 코드설명은 필수 입력 사항입니다.";
				trObj.find('td:eq(3) input[type=text]').focus();
				return false;
			}
		});
		
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
			$('#dtlCdList tr').each(function(){
				
				var trObj = $(this);
				//데이터 조작이 없는 경우 처리안함
				if(trObj.find('td:eq(9)').text() == 'N'){
					return true;
				}
				
				if(cnt == 0){
					sel_cd=trObj.find('td:eq(8)').text();
				}
				
				
				list[cnt] = {
						"up_cd" : trObj.find('td:eq(8)').text(),
						"cd" : trObj.find('td:eq(1) input[type=text]').val(),
						"cd_nm" : encodeURIComponent(trObj.find('td:eq(2) input[type=text]').val()),
						"cd_desc" : encodeURIComponent(trObj.find('td:eq(3) input[type=text]').val()),
						"cd_ord" : trObj.find('td:eq(0)').text(),
						"ref_val1" : encodeURIComponent(trObj.find('td:eq(4) input[type=text]').val()),
						"ref_val2" : encodeURIComponent(trObj.find('td:eq(5) input[type=text]').val()),
						"ref_val3" : encodeURIComponent(trObj.find('td:eq(6) input[type=text]').val()),
						"use_yn" : trObj.find('td:eq(7) :selected').val(),
						"save_type" : trObj.find('td:eq(9)').text()
					}
				cnt++;
				
			});

			var sendList = {"list" : list};

			$.ajax({
				url : '${ctxt}/mng/code/insertCdList.do',
				data : JSON.stringify(sendList),
				processData : false,
				//contentType: false,
				type : 'POST',
				traditional : true,
				cache: false,
				success : function(result) {

					$('#dtlCdList tr').each(function(){	
						var trObj = $(this);
						trObj.find('td:eq(9)').text('N');
					});
					
					fn_showCustomAlert("저장을 완료 하였습니다.");
					
					$('#sel_cd').val(sel_cd);
					
					setTimeout(fn_setTree, 500);
						
				},
				error : function() { // Ajax 전송 에러 발생시 실행
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행
					
				}
			});
		
		}
		
	}
	
	/*
	* 코드 중복체크
	*/
	function fn_dulChk(type,obj){
		//빈값은 체크 안함
		if($(obj).val() == ''){
			return;
		}
		//상위메뉴
		if(type == 'M'){
			if($('#save_type') != "I"){
				return;
			}
		}
		
		if($('#cd').val() == 'FORM_CD' ){
			return;
		}
		
		$.ajax({
		       url : '${ctxt}/mng/code/codeChk.do',
		       data : {"code" :$(obj).val()},   //전송파라미터
		       contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
		       type : 'GET',
		       dataType : 'text',
		       success : function(result) {
					if(result == "Y"){
						fn_showCustomAlert("코드값이 중복되었습니다.","c");
						$(obj).val('');
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
	* 로우 선택
	*/
	function fn_setRow(obj){ 
		$('#dtlCdList .rowOn').attr('class','off');
		$(obj).attr('class','rowOn');	
	}
	
	/**
	* 상세코드 추가
	*/
	function fn_addDtl(){
		if($('#save_type').val() == "I"){
			fn_showCustomAlert("상위코드 정보를 먼저 저장하세요.","c");
			return false;
		}
		
		if($('#cd').val() == ''){
			fn_showCustomAlert("상위코드 정보가 없습니다.","c");
			return false;
		}
		
		$('#dtlCdList .rowOn').attr('class','off');
		
		var html="";
	   		html += '<tr class="rowOn" onclick="fn_setRow(this)" >';         
	        html += '  <td class="text_c">'+parseInt($('#dtlCdList >tr').length +1)+'</td>';            
	        html += '  <td><input type="text" style="width:100%;"   onblur="fn_dulChk(\'D\',this)" /></td>';            
	        html += '  <td><input type="text" style="width:100%;" /></td>';   
	        html += '  <td><input type="text" style="width:100%;" /></td>';   
	        html += '  <td><input type="text" style="width:100%;" /></td>';   
	        html += '  <td><input type="text" style="width:100%;" /></td>';           
	        html += '  <td><input type="text" style="width:100%;" /></td>';           
	        html += '  <td><select><option value="Y" >사용</option><option value="N" >미사용</option></select></td>';   
	        html += '  <td style="display:none;">'+$('#cd').val() +'</td>'; 
	        html += '  <td style="display:none;">I</td>'; 
	 		html += '</tr>'; 
	 	$('#dtlCdList:last').append(html);

	 	$( '#sc' ).stop().animate( { scrollTop : $('#dtlCdList > tr').length * 30 } );
	}
	/*
	* 상세코드 삭제
	*/
	function fn_delDtl(type){
		var delRow = $('#dtlCdList .rowOn');
		if(delRow.length == 0){
			fn_showCustomAlert("선택로우가 없습니다.","c");
		}
		
		if(type != "D"){
			if($(delRow).find('td:eq(9)').text() != 'I'){
				fn_showCustomAlert('취소 대상이 아닙니다.','c');
				return false;
			}
		}
		//선택로우 삭제	
		$(delRow).remove();
		

	 	if($('#dtlCdList tr').length > 0){
	 		$('#dtlCdList .rowOn').attr('class','off');
	 		$('#dtlCdList tr:last').attr('class','rowOn');	
	 	}
	 	
	}
	
	/*
	* 코드 트리 클릭 이벤트
	*/
	function fn_setCd(){
		//저장하지 않고 이동하는 경우 확인창 
		if($('#dtlCdList tr').find('td:eq(9)').text().indexOf('I') != -1 
				|| $('#dtlCdList tr').find('td:eq(9)').text().indexOf('U') != -1 ){
			var strConfirm = confirm("저장하지 않은 정보가 있습니다.이동하시겠습니까?");
			if(!strConfirm){
				return;
			}
		}

		var col= ['cd','up_cd','cd_nm','cd_desc','ref_val1','ref_val2','ref_val3','use_yn'];
		for(var i=0; i < col.length; i++){
			$('#'+col[i]).val(arguments[i]);
		}

		$('li > strong').attr('style','cursor: pointer;');
		$(arguments[col.length]).attr('style','cursor: pointer; color:#f97e15; font-weight: 600;')
		
		$('#save_type').val('N');
		$('#dtlCdList').children().remove();
		
		$('#cd').attr('readonly','readonly');
		
		$.ajax({
			url:"${ctxt}/mng/code/readCodeList.do",
			type:'GET',
			data:{"up_cd":arguments[0]},
			contentType: "text/xml;charset=utf-8",
			dataType:"text",
			success:function(rtnXml){
				
				var xmlObj = $(rtnXml).find('item');

				var html="";
				
				xmlObj.each(function(cnt){
					
					html += '<tr onclick="fn_setRow(this)">';
					html += '  <td class="text_c">'+$(this).find('cd_ord').text()+'</td>';
					html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('cd').text()+'" onchange="fn_upd(this)" readonly="readonly" /></td>';            
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('cd_nm').text()+'" onchange="fn_upd(this)" /></td>';   
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('cd_desc').text()+'" onchange="fn_upd(this)" /></td>';   
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('ref_val1').text()+'" onchange="fn_upd(this)" /></td>';   
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('ref_val2').text()+'" onchange="fn_upd(this)" /></td>';           
			        html += '  <td><input type="text" style="width:90%;" value="'+$(this).find('ref_val3').text()+'" onchange="fn_upd(this)" /></td>';  
			        if($(this).find('use_yn').text() == 'Y'){
			        	html += '  <td><select onchange="fn_upd(this)" style="width:100%;" ><option value="Y" selected="selected" >사용</option><option value="N" >미사용</option></select></td>';
			        }else{
			        	html += '  <td><select onchange="fn_upd(this)" style="width:100%;" ><option value="Y" >사용</option><option value="N" selected="selected" >미사용</option></select></td>';
			        }
			        html += '  <td style="display:none;">'+$(this).find('up_cd').text() +'</td>'; 
			        html += '  <td style="display:none;">N</td>'; 
					html += '</tr>';
				});
				
				$('#dtlCdList').html(html);
				//트리코드 선택시 첫번째 행 선택
				$('#dtlCdList tr:first').attr('class','rowOn');	
				<c:if test="${if_yn == 'Y'}"> 
					parent.calcHeight();
				</c:if>

			},
			error:function(){
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			}
		});
	}
	
	/*
	* 코드 트리 만들기 이벤트
	*/
	function fn_setTree(){

		$('#tree').children().remove();
		
		$.ajax({
			url:"${ctxt}/mng/code/readCodeList.do",
			type:'GET',
			data:{},
			contentType: "text/xml;charset=utf-8",
			dataType:"text",
			success:function(rtnXml){
				
				var xmlObj = $(rtnXml).find('item');

				var html="";
				var bfData="";
				html += '<img src="${ctxt}/resources/images/dtree/base.gif" >코드목록</a>';
				
				xmlObj.each(function(cnt){
					
					if($(this).find('cd_gbn').text() == '1'){
						
						if(cnt != 0){
							html += '</li>';
						}
						html += '<li>';
						html += '<strong id="'+$(this).find('cd').text()+'" onclick="fn_setCd(\''+$(this).find('cd').text()+'\',\''
															+$(this).find('up_cd').text()+'\',\''
															+$(this).find('cd_nm').text()+'\',\''
															+$(this).find('cd_desc').text()+'\',\''
															+$(this).find('ref_val1').text()+'\',\''
															+$(this).find('ref_val2').text()+'\',\''
															+$(this).find('ref_val3').text()+'\',\''
															+$(this).find('use_yn').text()+'\',this)" style="cursor: pointer;" >'+$(this).find('cd_nm').text()+ '</strong>';
						bfData = $(this).find('cd').text();
						
						if(xmlObj.length == 1){
							html += '</li>';
						}
						
					}else{
						html += '<ul><li><a href="#">'+$(this).find('cd_nm').text()+'</a></li></ul>';
					}
				
				});
	
				$('#tree').html(html);

				$("#tree").treeview({
					collapsed: "closed",
					animated: "medium",
					control:"#sidetreecontrol",
					persist: "location"
				});
				//저장할 경우 저장시 선택행 	
				if($('#sel_cd').val() == '' || $('#sel_cd').val() == undefined ){
					$('#tree li:first > strong').click();
				}else{
					$('#'+$('#sel_cd').val()).click();
				}
				
			},
			error:function(){
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			}
		});

	}
	/**
	* 상세코드 데이터 수정시 
	*/
	function fn_upd(obj){
		$(obj).parent().parent().find('td:eq(9)').text('U');
	}
	
	//메뉴삭제 이벤트
	function fn_delCd(){
		var del_cd =  $('#dtlCdList .rowOn').find('td:eq(1) > input[type=text]').val();
		
		if(del_cd == undefined || del_cd == '' ||del_cd == null){
			
			fn_showCustomAlert("데이터가 존재하지 않거나 <br/> 선택된 데이터가 존재하지 않습니다." ,"c");
			
			return false;
		}
		
		if($('#dtlCdList .rowOn').find('td:eq(9)').text() == 'I'){
			fn_showCustomAlert("저장된 데이터가 아닙니다.");
			return;
		}
		
		var strConfirm = confirm("선택행 데이터를 삭제하시겠습니까?");

		if(strConfirm){
		
			$.ajax({
				url:"${ctxt}/mng/code/delCode.do",
				type:'GET',
				data:{"cd":del_cd},
				contentType: "text/xml;charset=utf-8",
				dataType:"text",
				success:function(rtnXml){
					//삭제 데이터 저장후 순번 채번
					fn_delDtl("D");
					
					changNum();
					
					fn_saveCd("D")
					
				},
				error:function(){
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				}
			});
			
		}
	}
	
</script>

<style>
.treeview, .treeview ul {
	padding: 0;
	margin: 0;
	list-style: none;
}

.treeview ul {
	background-color: white;
	margin-top: 4px;
}

.treeview .hitarea {
	background: url(/resources/images/treeview/treeview-default.gif) -64px -25px no-repeat;
	height: 16px;
	width: 16px;
	margin-left: -16px;
	float: left;
	cursor: pointer;
}
/* fix for IE6 */
* html .hitarea {
	display: inline;
	float:none;
}

.treeview li {
	margin: 0;
	padding: 3px 0pt 3px 16px;
}

.treeview a.selected {
	background-color: #eee;
}

#treecontrol { margin: 1em 0; display: none; }

.treeview .hover { color: red; cursor: pointer; }

.treeview li { background: url(/resources/images/treeview/treeview-default-line.gif) 0 0 no-repeat; }
.treeview li.collapsable, .treeview li.expandable { background-position: 0 -176px; }

.treeview .expandable-hitarea { background-position: -80px -3px; }

.treeview li.last { background-position: 0 -1766px }
.treeview li.lastCollapsable, .treeview li.lastExpandable { background-image: url(/resources/images/treeview/treeview-default.gif); }
.treeview li.lastCollapsable { background-position: 0 -111px }
.treeview li.lastExpandable { background-position: -32px -67px }

.treeview div.lastCollapsable-hitarea, .treeview div.lastExpandable-hitarea { background-position: 0; }

.treeview-red li { background-image: url(/resources/images/treeview/treeview-red-line.gif); }
.treeview-red .hitarea, .treeview-red li.lastCollapsable, .treeview-red li.lastExpandable { background-image: url(/resources/images/treeview/treeview-red.gif); }

.treeview-black li { background-image: url(/resources/images/treeview/treeview-black-line.gif); }
.treeview-black .hitarea, .treeview-black li.lastCollapsable, .treeview-black li.lastExpandable { background-image: url(/resources/images/treeview/treeview-black.gif); }

.treeview-gray li { background-image: url(/resources/images/treeview/treeview-gray-line.gif); }
.treeview-gray .hitarea, .treeview-gray li.lastCollapsable, .treeview-gray li.lastExpandable { background-image: url(/resources/images/treeview/treeview-gray.gif); }

.treeview-famfamfam li { background-image: url(/resources/images/treeview/treeview-famfamfam-line.gif); }
.treeview-famfamfam .hitarea, .treeview-famfamfam li.lastCollapsable, .treeview-famfamfam li.lastExpandable { background-image: url(/resources/images/treeview/treeview-famfamfam.gif); }

.treeview .placeholder {
	background: url(/resources/images/treeview/ajax-loader.gif) 0 0 no-repeat;
	height: 16px;
	width: 16px;
	display: block;
}

.filetree li { padding: 3px 0 2px 16px; }
.filetree span.folder, .filetree span.file { padding: 1px 0 1px 16px; display: block; }
.filetree span.folder { background: url(/resources/images/treeview/folder.gif) 0 0 no-repeat; }
.filetree li.expandable span.folder { background: url(/resources/images/treeview/folder-closed.gif) 0 0 no-repeat; }
.filetree span.file { background: url(/resources/images/treeview/file.gif) 0 0 no-repeat; }
</style>

	<!-- 메인 컨텐츠  -->
	<h3 class="page_title" >코드관리</h3>    
	<input type="hidden" id="sel_cd" name="sel_cd" value="${sel_cd}" />
	<div class="admin-system">
	<!-- 왼쪽 메뉴 -->
		<div class="part-narrow">
			<div id="sidetree" class= "treeview">
				<!-- 
				<div class="treeheader"><label for="search_cd" style="vertical-align: -webkit-baseline-middle;">코드명</label> <input type="text" id="search_cd" value="" /> 
				<input type="button" id="search_btn" name="search_btn" value="조회" /></div>
				
				<div id="sidetreecontrol"><a href="?#" class="button">전체닫기</a>&nbsp;&nbsp;<a href="?#" class="button">전체열기</a></div>
				
				<br /> 
				-->
				<ul id="tree">
				</ul>
			</div>
		</div>
		<!-- 왼쪽 컨텐츠  -->
		<div>
		
			<div class="clearfix ma_b_10 float_r">
			
				<a href = "javascript:void(0);" onclick="fn_addUpCd();"  class="btn btn-secondary" >상위코드추가</a>
				<a href = "javascript:void(0);" onclick="fn_saveUpCd();"  class="btn btn-secondary" >상위코드저장</a>
			</div>
			<div>
				<form action="" name="upCdForm" id="upCdForm" method="post">
					<input type="hidden" name="up_cd" value="PLATFORM" />
					<input type="hidden" name="cd_ord" value="0" />
					<input type="hidden" name="save_type" id="save_type" value="" />
					<table class="table_v th_l bor_top" style="margin-top: 1px;">
					    <colgroup>
					      <col width="20%" />
					      <col width="30%" />
					      <col width="20%" />
					      <col width="30%" />
					    </colgroup>
					    <tbody>
					     	<tr>
						     	<th><span class="must"></span><label for="cd">코드</label></th>
						        <td><input type="text" id="cd" name="cd" value="" title="코드" onblur="fn_dulChk('M',this)" onchange="fn_chgst()" /></td>
						        <th><span class="must"></span><label for="cd_nm">코드명</label></th>
						        <td ><input type="text" id="cd_nm" name="cd_nm" maxlength="20"  value="" title="코드명"  onchange="fn_chgst()" /></td>
					      	</tr>
					     	<tr>
						     	<th><span class="must"></span><label for="cd_desc">코드설명</label></th>
						        <td colspan="3"><input type="text" id="cd_desc" name="cd_desc" value="" title="코드설명"  onchange="fn_chgst()"  /></td>
					      	</tr>
					      	<tr>
						     	<th><span class="must"></span><label for="ref_val1">참조 칼럼1</label></th>
						        <td ><input type="text" id="ref_val1" name="ref_val1"   value="" title="참조 칼럼1"  onchange="fn_chgst()" /></td>
						        <th><span class="must"></span><label for="ref_val2">참조 칼럼2</label></th>
						        <td ><input type="text" id="ref_val2" name="ref_val2"  value="" title="참조 칼럼2"  onchange="fn_chgst()" /></td>
					      	</tr>
					      	<tr>
						     	<th><span class="must"></span><label for="ref_val3">참조 칼럼3</label></th>
						        <td><input type="text" id="ref_val3" name="ref_val3"  value="" title="참조 칼럼3"  onchange="fn_chgst()" /></td>
						        <th><span class="must"></span>사용여부</th>
						        <td style="text-align: left;" class="selectRow">
						        	<label for="use_yn" class="hidden-access">사용 여부</label>
						        	<select id="use_yn" name="use_yn" style="color: blue;"  onchange="fn_chgst()" >
						        		<option value="Y">사용</option>
						        		<option value="N">미사용</option>
						        	</select>
						        </td>
					      	</tr>
				    </tbody>
				</table>
				</form>
		
				<br /><br />
				<div style="overflow: auto;" id="sc">
	
					<div style="float: left;" >		
	      				<a href = "javascript:void(0);" onclick="fn_addDtl();"  class="btn btn-secondary" >코드추가</a>
      					<a href = "javascript:void(0);" onclick="fn_delDtl('T');"  class="btn btn-secondary" >추가취소</a>  
		      			&nbsp;
				
						
						<a href = "javascript:void(0);" id="up"   class="btn btn-secondary" >△</a>
      					<a href = "javascript:void(0);" id="down"    class="btn btn-secondary" >▽</a>  
					</div>
	
					<div style="float: right;" >
						<!-- <button class="addItemBtn" name="del_btn" onclick="fn_delCd()" >삭제</button> -->
			
						<a href = "javascript:void(0);" onclick="fn_saveCd();"  class="btn btn-secondary" >상세코드저장</a>  
	
					</div>
					<div class="clear"></div>
				
		      	<table id="example0" class="table_v bor_l"> 
		         	<colgroup>
		            	<col width="8%" />
		                <col width="18%" />
		            	<col width="18%" />
		            	<col width="18%" />
		            	<col width="10%" />
		            	<col width="10%" />
		            	<col width="10%" />
		            	<col width="10%" />
		            </colgroup> 
		            <thead>    
			         	<tr>            
			         		<th>순서</th>    
				            <th>코드</th>    
				            <th>코드명</th>            
				            <th>코드설명</th>                    
				            <th>참조값</th>        
				            <th>참조값<br/>(영문)</th>        
				            <th>참조값</th>        
				            <th>사용여부</th>        
			        	 </tr>        
		        	</thead>
		        	<tbody id="dtlCdList">       
			        </tbody>
		      	</table>
		 	   </div><!-- 오른쪽 하단 테이블 영역  -->
	    	</div><!-- 오른쪽 전체 테이블 영역  -->
		</div> <!-- 오른쪽 컨텐츠  -->
	</div> <!-- admin-system -->

