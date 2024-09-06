<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="kr.go.rastech.ptl.mng.menu.vo.MngMenuVo"%>

<link href="${ctxt}/resources/css/tree/jquery.treeview.css"
	rel="stylesheet" type="text/css">
<link href="${ctxt}/resources/css/tree/code.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	//dtree, 현재 선택 메뉴, 현재 선택 레벨

	$(function() {
		fn_readDtl();

		$('#test tr td').hover(function() {
			$(this).css('background', 'blue');
			//$(this).attr('style','background:#f0f;');
		}, function() {
			$(this).css('background', 'red');
			//$(this).attr('style','background:red;');
		});

	});

	/**
	 * 권한 추가
	 */
	function fn_add() {

		$('#dtlLangList .on').attr('class', 'off');

		var tag_type_cd = $('#tag_type').html();

		var html = "";
		html += '<tr class="on" onclick="fn_setRow(this)"  name="tr_cls">';
		html += '  <td class="center" >' + parseInt($('#dtlLangList >tr').length + 1) + '</td>';
		html += '  <td><input type="text" value="" ></td>';
		html += '  <td style="text-align:center" ><select name="auth_cd" style="width=100%;" >'
				+ tag_type_cd + '</select></td>';
		html += '  <td><input type="text" style="width:100%;" /></td>';
		html += '  <td><input type="text" style="width:100%;" /></td>';
		html += '  <td><input type="text" style="width:100%;" /></td>';
		html += '  <td style="display:none;">I</td>';
		html += '</tr>';

		$('#dtlLangList:last').append(html);
		
		<c:if test="${if_yn == 'Y'}"> 
			parent.calcHeight();
		</c:if>

		$('html, body').stop().animate( { scrollTop : $('#dtlLangList .on').offset().top },3000 );
		
	}

	/*
	 * 권한 삭제
	 */
	function fn_del(type) {
		var delRow = $('#dtlLangList .on');
		if (delRow.length == 0) {
			fn_showCustomAlert("선택로우가 없습니다.", "c");
		}

		if (type != "D") {

			if ($(delRow).find('td:eq(6)').text() != 'I') {
				fn_showCustomAlert('취소 대상이 아닙니다.', 'c');
				return false;
			}
		}

		//선택로우 삭제	
		$(delRow).remove();

		if ($('#dtlLangList tr').length > 0) {
			$('#dtlLangList .on').attr('class', 'off');
			$('#dtlLangList tr:last').attr('class', 'on');
		}

	}

	/**
	 * 로우 선택
	 */
	function fn_setRow(obj) {
		$('#dtlLangList .on').attr('class', 'off');
		$(obj).attr('class', 'on');
	}

	/**
	 *	권한url 저장
	 */
	function fn_saveUrl() {
		var isVal = true;
		var msg = "";
		var paramObj;
		$('#dtlLangList tr').each(function(cnt) {
			var trObj = $(this);

			if (trObj.find('td:eq(3) input[type=text]').val() == '') {
				isVal = false;
				paramObj = trObj;
				msg = cnt + 1 + "번째 줄 URL은 필수 입력 사항입니다.";
				trObj.find('td:eq(3) input[type=text]').focus();
				return false;
			}
			if (trObj.find('td:eq(4) input[type=text]').val() == '') {
				isVal = false;
				paramObj = trObj;
				msg = cnt + 1 + "번째 줄 URL설명은 필수 입력 사항입니다.";
				trObj.find('td:eq(4) input[type=text]').focus();
				return false;
			}

		});

		if (!isVal) {
			fn_showCustomAlert(msg);
			fn_setRow(paramObj);
			return;
		}

		var strConfirm = confirm("저장하시겠습니까?");

		if (strConfirm) {

			var list = [];
			var cnt = 0;
			$('#dtlLangList tr').each(
					function() {

						var trObj = $(this);
						//데이터 조작이 없는 경우 처리안함
						if (trObj.find('td:eq(6)').text() == 'N') {
							return true;
						}
						list[cnt] = {
							"seq" : trObj.find('td:eq(0)').text(),
							"page_id" : trObj.find('td:eq(1) input[type=text]')
									.val(),
							"tag_gbn" : trObj.find('td:eq(2) :selected').val(),
							"tag" : trObj.find('td:eq(3) input[type=text]')
									.val(),
							"tag_val_ko" : trObj.find(
									'td:eq(4) input[type=text]').val(),
							"tag_val_eng" : trObj.find(
									'td:eq(5) input[type=text]').val(),
							"save_type" : trObj.find('td:eq(6)').text()

						}
						cnt++;

					});

			var sendList = {
				"list" : list
			};

			$.ajax({
				url : '${ctxt}/mng/lang/saveLang.do',
				data : JSON.stringify(sendList),
				processData : false,
				//contentType: false,
				type : 'POST',
				traditional : true,
				cache : false,
				success : function(result) {

					$('#dtlLangList tr').each(function() {
						var trObj = $(this);
						trObj.find('td:eq(7)').text('N');
					});

					fn_showCustomAlert("저장을 완료 하였습니다.");
					
					location.reload();

				},
				error : function() { // Ajax 전송 에러 발생시 실행
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.', 'e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행

				}
			});
		}
	}

	function fn_load(sel_menu_id) {
		location.href = "${ctxt}/mng/lang/listLang.do?sel_menu_id="
				+ sel_menu_id;
	}

	/**
	 * 권한url 삭제
	 */
	function fn_delUrl() {
		var del_id = $('#dtlLangList .on').find('td:eq(0)').text();

		if ($('#dtlLangList .on').find('td:eq(6)').text() == 'I') {
			fn_showCustomAlert("저장된 데이터가 아닙니다.", 'c');
			return;
		}

		var strConfirm = confirm("선택행 데이터를 삭제하시겠습니까?");

		if (strConfirm) {

			$.ajax({
				url : "${ctxt}/mng/auth/delUrlAuth.do",
				type : 'GET',
				data : {
					"url_seq" : del_id
				},
				contentType : "text/xml;charset=utf-8",
				dataType : "text",
				success : function(result) {
					fn_del("D");
					fn_showCustomAlert("삭제처리가 완료되었습니다.");

					setTimeout(fn_load(result), 1500);
				},
				error : function() {
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.', 'e');
				}
			});

		}
	}

	/*
	 * 상세보기 이벤트
	 */
	function fn_readDtl() {

		var valList = new Array();
		var textList = new Array();

		$('#tag_type option').each(
				function(index) {
					if ($(this).attr('class') != ''
							&& $(this).attr('class') != undefined) {
						/* if($(this).attr('class').indexOf(curr_menu) != -1){
							$(this).attr('style','');
							valList.push($(this).val());
							textList.push($(this).text());
						 }else{*/
						$(this).attr('style', 'display:none;')
						//}
					} else {
						valList.push($(this).val());
						textList.push($(this).text());
						$(this).attr('style', '');
					}
				});

		$.ajax({
					url : "${ctxt}/mng/lang/selectLang.do",
					type : 'GET',
					data : {},
					contentType : "text/xml;charset=utf-8",
					dataType : "text",
					success : function(rtnXml) {

						var xmlObj = $(rtnXml).find('item');

						var html = "";

						xmlObj
								.each(function(cnt) {
									html += '<tr onclick="fn_setRow(this)">';
									html += '  <td class="center" >'
											+ $(this).find('seq').text()
											+ '</td>';
									html += '  <td class="center" >'
											+ $(this).find('page_id').text()
											+ '</td>';
									html += '  <td style="text-align:center" ><select style="width=100%;" onchange="fn_upd(this)" >';
									for (var i = 0; i < valList.length; i++) {
										if ($(this).find('tag_gbn').text() == valList[i]) {
											html += '<option value="'+ valList[i] + '" selected="selected" >'
													+ textList[i] + '</option>';
										} else {
											html += '<option value="'+ valList[i] + '" >'
													+ textList[i] + '</option>';
										}
									}
									html += '  </select></td>';
									html += '  <td><input type="text" style="width:90%;" value="'
											+ $(this).find('tag').text()
											+ '" onchange="fn_upd(this)" /></td>';
									html += '  <td><input type="text" style="width:90%;" value="'
											+ $(this).find('tag_val_ko').text()
											+ '" onchange="fn_upd(this)" /></td>';
									html += '  <td><input type="text" style="width:90%;" value="'
											+ $(this).find('tag_val_eng')
													.text()
											+ '" onchange="fn_upd(this)" /></td>';
									html += '  <td style="display:none;">N</td>';
									html += '</tr>';
								});

						$('#dtlLangList').html(html);
						//트리코드 선택시 첫번째 행 선택
						$('#dtlLangList tr:first').attr('class', 'on');
						
						<c:if test="${if_yn == 'Y'}"> 
							parent.calcHeight();
						</c:if>

					},
					error : function() {
						fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.', 'e');
					}
				});
	}

	/**
	 * 로우 선택
	 */
	function fn_setRow(obj) {
		$('#dtlLangList .on').attr('class', 'off');
		$(obj).attr('class', 'on');
	}
	/**
	 * 상세코드 데이터 수정시 
	 */
	function fn_upd(obj) {
		$(obj).parent().parent().find('td:eq(6)').text('U');
	}
</script>

<div id="container">
	<div id="divRefreshArea">
		<h3 class="page_title" ><spring:message code="menu.mng.lang" text="default text" /></h3>    
		
		<input type="hidden" id="sel_menu_id" value="${sel_menu_id}" /> <select
			id="tag_type" style="display: none;">
			<c:forEach var="tag" items="${tag_type}">
				<option value="${tag.cd}" class="${tag.ref_val1}">${tag.cd_nm}</option>
			</c:forEach>
		</select>
		<br />
		<div class="con_box">
			<input type="hidden" name="save_type" id="save_type" value="" />
			<div style="float: left;">
				<button class="addItemBtn" name="save_btn" onclick="fn_add()">다국어추가</button>
				<button class="addItemBtn" name="func_btn" onclick="fn_del('T')">다국어취소</button>
			</div>
			<div style="float: right;">
				<button class="addItemBtn" name="del_btn" onclick="fn_delUrl()">삭제</button>
				<button class="addItemBtn" name="save_btn" onclick="fn_saveUrl()">저장</button>
			</div>

			<div class="clear"></div>
			<br />

			<table id="example">
				<colgroup>
					<col width="5%" />
					<col width="10%" />
					<col width="10%" />
					<col width="20%" />
					<col width="25%" />
					<col width="25%" />
				</colgroup>
				<thead>
					<tr>
						<th>순서</th>
						<th>페이지ID</th>
						<th>태그타입</th>
						<th>위치명</th>
						<th>ko</th>
						<th>eng</th>
					</tr>
				</thead>
				<tbody id="dtlLangList">
				</tbody>
			</table>
			<br />

			<div class="clear"></div>
		</div>

	</div>
</div>

