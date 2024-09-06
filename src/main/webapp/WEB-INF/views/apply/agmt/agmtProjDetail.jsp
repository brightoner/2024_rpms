<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<script type="text/javascript">
	
	$(function(){   
		
		// 첨부파일
		getFileList($("#file_group").val(),'2');
		getFileList($("#file_group").val(),'3');
		getFileList($("#file_group").val(),'4');
		
		
		// 총사업비 합
		sumTotal();
        document.getElementById("gov_cost").addEventListener("input", sumTotal);
        document.getElementById("cash").addEventListener("input", sumTotal);
        document.getElementById("stock").addEventListener("input", sumTotal);
        
	     // 사업비 초기 데이터 값
	     var iniGovCost = "${data.gov_cost}";
	     var govCostInput = document.getElementById("gov_cost");
	     var iniCash = "${data.cash}";
	     var cashInput = document.getElementById("cash");
	     var iniStock = "${data.stock}";
	     var stockInput = document.getElementById("stock");
	     
	     // 초기값을 포맷하고 입력 필드에 설정
	     if (iniGovCost) {
	    	 govCostInput.value = addComma(Number(iniGovCost));
	     }
	     if(iniCash){
	    	 cashInput.value = addComma(Number(iniCash));
	     }
	     if(iniStock){
	    	 stockInput.value = addComma(Number(iniStock));
	     }
	
	     // 입력 필드에 이벤트 리스너 추가
	     govCostInput.addEventListener("input", inputComma);
	     cashInput.addEventListener("input", inputComma);
	     stockInput.addEventListener("input", inputComma);
		
	});
	  
	// 첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.agmtFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}	
	
	
	 //첨부파일등록 팝업 호출
	  function fn_egov_file_popup2() {
			var agmtManageVo=document.agmtFrm;
			var file_group = agmtManageVo.file_group.value;
			var file_gb = "2";

			popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	  }
	 
	//첨부파일등록 팝업 호출
	  function fn_egov_file_popup3() {
			var agmtManageVo=document.agmtFrm;
			var file_group = agmtManageVo.file_group.value;
			var file_gb = "3";

			popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	  }
	
	//첨부파일등록 팝업 호출
	  function fn_egov_file_popup4() {
			var agmtManageVo=document.agmtFrm;
			var file_group = agmtManageVo.file_group.value;
			var file_gb = "4";

			popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	  }
	
	  //첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	  function fileContainerRefresh() {
		
		  getFileList($("#file_group").val(), '2');
		  getFileList($("#file_group").val(), '3');
		  getFileList($("#file_group").val(), '4');
	  }

	  
	// input박스 천단위 콤마
	function inputComma(event) {
        var value = event.target.value.replace(/[^0-9.]/g, '');
        
        var parts = value.split('.');
        var integerPart = parts[0];
        var decimalPart = parts.length > 1 ? '.' + parts[1] : '';

        const formattedIntegerPart = addComma(Number(integerPart));
        event.target.value = formattedIntegerPart + decimalPart;
    }
	
	// 총 사업비 계산
	function sumTotal() {
        var govCost = parseFloat(document.getElementById("gov_cost").value.replace(/,/g, '')) || 0;
        var cash = parseFloat(document.getElementById("cash").value.replace(/,/g, '')) || 0;
        var stock = parseFloat(document.getElementById("stock").value.replace(/,/g, '')) || 0;

        var total = govCost + cash + stock;

        document.getElementById("tot_cost").value = addComma(total);
    }
	

	// 과제협약 저장
	function fn_save(){
		
		// 과제유형 validation		
		if($('input[name="proj_type_cd"]').val() == "" || $('input[name="proj_type_cd"]').val() == null){
			fn_showCustomAlert("과제유형을 선택 하세요.");
			return false;
		}
		
		// 보안등급 validation		
		if($('input[name="securty_levl_cd"]').val() == "" || $('input[name="securty_levl_cd"]').val() == null){
			fn_showCustomAlert("보안등급을 선택 하세요."); 
			return false;
		}
		
		// 산업기술분류 validation
		// 소분류(1순위)
		if($("#tech_cls_cd1").val() == "" || $("#tech_cls_cd1").val() == null){
			fn_showCustomAlert("소분류(1순위)를 선택 하세요.");
			$('#tech_cls_cd1').focus();
			return false;
		}
		// 가중치(%)
		if($("#weight1").val() == "" || $("#weight1").val() == null){
			fn_showCustomAlert("가중치를 입력 하세요.");
			$('#weight1').focus();
			return false;
		}
		// 소분류(2순위)
		if($("#tech_cls_cd2").val() == "" || $("#tech_cls_cd2").val() == null){
			fn_showCustomAlert("소분류(2순위)를 선택 하세요.");
			$('#tech_cls_cd2').focus();
			return false;
		}
		// 가중치(%)
		if($("#weight2").val() == "" || $("#weight2").val() == null){
			fn_showCustomAlert("가중치를 입력 하세요.");
			$('#weight2').focus();
			return false;
		}
		// 소분류(3순위)
		if($("#tech_cls_cd3").val() == "" || $("#tech_cls_cd3").val() == null){
			fn_showCustomAlert("소분류(3순위)를 선택 하세요.");
			$('#tech_cls_cd3').focus();
			return false;
		}
		// 가중치(%)
		if($("#weight3").val() == "" || $("#weight3").val() == null){
			fn_showCustomAlert("가중치를 입력 하세요.");
			$('#weight3').focus();
			return false;
		}
		
 		// 과제명 국문 validation
		if($("#proj_nm_kor").val() == "" || $("#proj_nm_kor").val() == null){
			fn_showCustomAlert("과제명(국문)을 입력 하세요.");
			$('#proj_nm_kor').focus();
			return false;
		}
		
 		// 과제명 영문 validation
		if($("#proj_nm_eng").val() == "" || $("#proj_nm_eng").val() == null){
			fn_showCustomAlert("과제명(영문)을 입력 하세요.");
			$("#proj_nm_eng").val("");
			$('#proj_nm_eng').focus();
			return false;
		}
		
		// 주관기관 validation
		if($("#mOrg_nm").val() == "" || $("#mOrg_nm").val() == null){
			fn_showCustomAlert("주관기관을 입력 하세요.");
			$("#mOrg_nm").val("");
			return false;
		}
		
		
		// 총수행기간 validation
		var performStrtdt = $("#perform_strtdt").val().replace(/-/g, "");  
		var performEnddt = $("#perform_enddt").val().replace(/-/g, "");  
		
		if($("#perform_strtdt").val() == "" || $("#perform_strtdt").val() == null){
			fn_showCustomAlert("총수행 시작 기간을 입력 하세요.");
			$("#perform_strtdt").val("");
			$('#perform_strtdt').focus();
			return false;
		}
		
		if($("#perform_enddt").val() == "" || $("#perform_enddt").val() == null){
			fn_showCustomAlert("총수행 종료 기간을 입력 하세요.");
			$("#perform_enddt").val("");
			$('#perform_enddt').focus();
			return false;
		}
		
		if(performStrtdt > performEnddt){
			fn_showCustomAlert("총수행 시작 기간이 총수행 종료 기간보다 큽니다.");
			$("#perform_strtdt").val("");
			$("#perform_enddt").val("");
			$('#perform_strtdt').focus();
			return false;
		} 
		
		
		// 정부출연금 validation
		if($("#gov_cost").val() == "" || $("#gov_cost").val() == null){
			fn_showCustomAlert("정부출연금을 입력 하세요.");
			$("#gov_cost").val("");
			$('#gov_cost').focus();
			return false;
		}
		
		// 민간 부담금  현금 validation
		if($("#cash").val() == "" || $("#cash").val() == null){
			fn_showCustomAlert("민간 부담금 현금을 입력 하세요.");
			$("#cash").val("");
			$('#cash').focus();
			return false;
		}
		
		// 민간 부담금  현물 validation
		if($("#stock").val() == "" || $("#stock").val() == null){
			fn_showCustomAlert("민간 부담금 현물을 입력 하세요.");
			$("#stock").val("");
			$('#stock').focus();
			return false;
		}
		
     // 파라미터 셋팅        
        var mainItem = [];
        var respList = [];
        var orgList = [];
        var requestData = [];
		
    	var tot_cost_val = $("#tot_cost").val().replace(/,/g, '');
		var gov_cost_val = $("#gov_cost").val().replace(/,/g, '');
		var cash_val = $("#cash").val().replace(/,/g, '');
		var stock_val = $("#stock").val().replace(/,/g, '');
		$("#tot_cost_val").val(tot_cost_val);
		$("#gov_cost_val").val(gov_cost_val);
		$("#cash_val").val(cash_val);
		$("#stock_val").val(stock_val);
		
		var proj_id = $("#proj_id").val();
		var ann_id = $("#ann_id").val();
		
		var item = serializeFormToJSON($("form[name='agmtFrm']"));
		
		$('tr[name="respTr"]').each(function() {
        	var $td = $(this);
        	
            var resp_cd = $td.find('input[name="resp_cd"]');

            // 데이터 객체를 만들어 리스트에 추가
            var item = {
            		resp_cd: resp_cd.val()
            };
            respList.push(item);
        });
		
		$('tr[name="orgTr"]').each(function() {
        	var $td = $(this);
        	
            var part_org_cd = $td.find('input[name="part_org_cd"]');

            // 데이터 객체를 만들어 리스트에 추가
            var item = {
            		part_org_cd: part_org_cd.val()
            };
            orgList.push(item);
        });
			
		var requestData = {
			mainItem: item,
			respList : respList,
			orgList : orgList,
			proj_id:proj_id,
			ann_id:ann_id
		};	
        
        
        fn_showCustomConfirm("question","저장하시겠습니까?", function() {
			
		    $.ajax({
		        url: '${ctxt}/apply/agmt/updateAgmtProj.do',
		        data: JSON.stringify(requestData),
		        type: 'POST', 
		        dataType: 'json',	 
		        contentType: 'application/json', // Content-Type을 설정합니다.
				success: function(result){			
					
					if(result.result != 0){
						fn_showCustomAlert("수정이 완료되었습니다.");
					}else{
						fn_showCustomAlert("수정에 실패 했습니다.");
					}
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
		  	});
        });
	}
	
	// 목록으로 이동
	function fn_back(){
		var form = document.agmtListFrm;
		form.action = '${ctxt}/apply/agmt/agmtProjList.do';
		form.submit();	
	}
	

	// 산업기술분류 선택 팝업
	function fn_techSearch(seq){
		
		$("#id_gbn1").val("tech_cls_cd"+seq);
		$("#id_gbn2").val("tech_cls_nm"+seq);

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/tclsMng/tclsMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}
	
	
	// 주관기관 선택 팝업
	function fn_mOrgSearch(){
		
		$("#id_gbn1").val("lead_org_cd");
		$("#id_gbn2").val("mOrg_nm");
		$("#id_gbn3").val("mOrg_crm_no");
		$("#id_gbn4").val("mOrg_address");

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/orgMng/orgMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin) 
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}
	
	// 참여기관검색 팝업
	function fn_orgSearch(seq){
		
		$("#id_gbn1").val("part_org_cd" + seq);
		$("#id_gbn2").val("org_nm" + seq);
		$("#id_gbn3").val("org_crm_no" + seq);
		$("#id_gbn4").val("org_address" + seq);

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/orgMng/orgMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}
	
	

// 참여기관 row 추가
	
	function fn_addOrg(obj) {
		
		var orgHtml ='';
		var orgIndex =  $("#projDetailTable tr[name = 'orgTr']").length;

		var $lastRow =  $("#projDetailTable tr[name = 'orgTr']").last();
		
		orgHtml += '<tr name = "orgTr">';
		orgHtml += '<th></th>';
		orgHtml += '<td colspan = "3">';
		orgHtml += '<div class="inputs-in-a-row">'
		orgHtml += 	'<input type="hidden" name="part_org_cd" id="part_org_cd' + orgIndex + '" value="" >'
	                     +  '<input type="text" name="org_nm' + orgIndex + '" id="org_nm' + orgIndex + '" value="" style="width:25%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     +  '<input type="text" name="org_crm_no' + orgIndex + '" id="org_crm_no' + orgIndex + '" value="" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     +  '<input type="text" name="org_address' + orgIndex + '" id="org_address' + orgIndex + '" value="" style="width:45%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     +  '<a href="javascript:void(0);" onclick="javascript:fn_orgSearch(' + orgIndex + ');" class="btn btn-secondary" ><i class="fas fa-search"></i></a>'
	    				 +  '<a href="javascript:void(0);" onclick="javascript:fn_delOrg(this, \'I\');" class="btn btn-secondary" >삭제</a>'
	    				 + '</div>';
	   orgHtml += '</td>'
	   orgHtml += '</tr>'				 

		// 셀들 추가
	    $lastRow.after(orgHtml);
	}

	// 참여기관 row 삭제
	function fn_delOrg(obj , status) {
		 var row = $(obj).closest('tr');
	 	 
		 /* if(status == 'N'){
			var respVal =  row.find("input[name = 'part_org_cd']").val();
			var projVal = $("#proj_id").val();
			if(isEmpty(respVal) || isEmpty(projVal) ){
				fn_showCustomAlert("과제 키 또는 참여연구원 key 정보가 존재하지 않습니다.\n 다시 로그인 후 확인해주세요.");
				return false;
			}
			// 신규는 체크할 필요 없다 I 신규 , N 기존 
			// 기존 등록된걸 다른것으로 변경하려 할때 참여율이 잡혀있는지 확인하여 수정을 막는다.
			var rVal =  fn_respRateSearch(respVal,projVal);
			
			if(rVal == true){
				fn_showCustomAlert("해당 연구자는 과제 참여율 관리 메뉴에서 참여율이 설정되었습니다.\n변경할 수 없습니다.");
				return false;
			}
			
		} */
		
	   
		 row.remove();
	}
	
	
	// 총괄책임자 선택 팝업
	function fn_lRespSearch(){
		
		$("#id_gbn1").val("lResp_cd");
		$("#id_gbn2").val("lResp_nm");
		$("#id_gbn3").val("lResp_birth");
		$("#id_gbn4").val("lResp_dept");
		$("#id_gbn5").val("lResp_position");
		$("#id_gbn6").val("lResp_email");
		$("#id_gbn7").val("lResp_mbtlnum");

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/respMng/respMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}
	
	
	// 실무책임자 팝업
	function fn_respSearch(seq){
		
		$("#id_gbn1").val("resp_cd" + seq);
		$("#id_gbn2").val("resp_nm" + seq);
		$("#id_gbn3").val("resp_birth" + seq);
		$("#id_gbn4").val("resp_dept" + seq);
		$("#id_gbn5").val("resp_position" + seq);
		$("#id_gbn6").val("resp_email" + seq);
		$("#id_gbn7").val("resp_mbtlnum" + seq);

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/respMng/respMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}
	
	

	// 실무책임자 row 추가
	function fn_addResp(obj) {
		
		var addHtml ='';
		var respIndex =  $("#projDetailTable tr[name = 'respTr']").length;
	    var $lastRow =  $("#projDetailTable tr[name = 'respTr']").last();
		
	    addHtml += '<tr name = "respTr">';
		addHtml += '<th></th>';
    	addHtml += '<td colspan = "3">';
    	addHtml += '<div class="inputs-in-a-row">'
    	addHtml += '<input type="hidden" name="resp_cd" id="resp_cd' + respIndex + '" value="">'
	                     + '<input type="text" name="resp_nm' + respIndex + '" id="resp_nm' + respIndex + '" value="" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     + '<input type="text" name="resp_birth' + respIndex + '" id="resp_birth' + respIndex + '" value="" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     + '<input type="text" name="resp_dept' + respIndex + '" id="resp_dept' + respIndex + '" value="" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     + '<input type="text" name="resp_position' + respIndex + '" id="resp_position' + respIndex + '" value="" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     + '<input type="text" name="resp_email' + respIndex + '" id="resp_email' + respIndex + '" value="" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     + '<input type="text" name="resp_mbtlnum' + respIndex + '" id="resp_mbtlnum' + respIndex + '" value="" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
	                     + '<a href="javascript:void(0);" onclick="javascript:fn_respSearch(' + respIndex + ');" class="btn btn-secondary"><i class="fas fa-search"></i></a>'
	                     + '<a href="javascript:void(0);" onclick="javascript:fn_delResp(this, \'I\');" class="btn btn-secondary">삭제</a>'
	                     +  '</div>';
		addHtml += '</td>';
	    addHtml += '</tr>';       
	    
	    // 셀들 추가
	    $lastRow.after(addHtml);
	}
	
	// 실무책임자 row 삭제
	function fn_delResp(obj , status) {
		 var row = $(obj).closest('tr');
	 	 
		 /* if(status == 'N'){
			var respVal =  row.find("input[name = 'resp_cd']").val();
			var projVal = $("#proj_id").val();
			if(isEmpty(respVal) || isEmpty(projVal) ){
				fn_showCustomAlert("과제 키 또는 참여연구원 key 정보가 존재하지 않습니다.\n 다시 로그인 후 확인해주세요.");
				return false;
			}
			// 신규는 체크할 필요 없다 I 신규 , N 기존 
			// 기존 등록된걸 다른것으로 변경하려 할때 참여율이 잡혀있는지 확인하여 수정을 막는다.
			var rVal =  fn_respRateSearch(respVal,projVal);
			
			if(rVal == true){
				fn_showCustomAlert("해당 연구자는 과제 참여율 관리 메뉴에서 참여율이 설정되었습니다.\n변경할 수 없습니다.");
				return false;
			}
			
		} */
	   
		 row.remove();
	}
	
	
</script>
<!-- 본문내용 -->
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>과제협약 처리 상세</h3>  
	<form name="agmtListFrm" method="post"  action="">	
		<input type="hidden" name="searchoption1" value="${agmtParam.searchoption1}" />
		<input type="hidden" name="searchoption2" value="${agmtParam.searchoption2}" />
		<input type="hidden" name="searchoption3" value="${agmtParam.searchoption3}" />
		<input type="hidden" name="searchoption4" value="${agmtParam.searchoption4}" />

		<input type="hidden" name="searchword1" value="${agmtParam.searchword1}" />
		<input type="hidden" name="searchword2" value="${agmtParam.searchword2}" />
		<input type="hidden" name="page" value="${agmtParam.page}" />
	</form>
	
	
		<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;공고현황 정보</h4>
		<table class="table_v">  
			<colgroup>
				<col width="20%">
				<col width="30%">  
				<col width="20%">
				<col width="30%">  
			</colgroup>
			<tbody>
				<tr>
					<th>소관부처</th>
					<td>
						 <c:out value="${data.dept_org}"></c:out>  
					</td>
					<th>전담기관</th>
					<td>
						 <c:out value="${data.ddct_org}"></c:out> 
					</td>
				</tr>
				<tr>
					<th>공고번호</th>
					<td>
						<c:out value="${data.ann_no}"></c:out>
					</td>
					<th>공고일</th>
					<td>
						<c:out value="${data.ann_dt}"></c:out>
					</td>
				</tr>
				<tr>
					<th>공고명</th>
					<td colspan="3">
						<c:out value="${data.ann_nm}"></c:out>
					</td>
				</tr>
			</tbody>
		</table>
	
	
	 <form name="agmtFrm" method="post"  action=""  enctype="multipart/form-data">
	 	<input type="hidden" name="ann_id" id="ann_id" value="${data.ann_id}" />
		<input type="hidden" name="proj_id" id="proj_id" value="${data.proj_id}" />
		<input type="hidden" name="file_group" id="file_group" value="${data.file_group}">
		<input type="hidden" name="part_org_cds" id="part_org_cds" value="">
		<input type="hidden" name="resp_cds" id="resp_cds" value=""> 
		<input type="hidden" id="tot_cost_val" name="tot_cost_val" value="">
		<input type="hidden" id="gov_cost_val" name="gov_cost_val" value="">
		<input type="hidden" id="cash_val" name="cash_val" value="">
		<input type="hidden" id="stock_val" name="stock_val" value="">
		
		<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;과제협약 처리 정보</h4>
	 	<table class="table_v" id="projDetailTable">  
			<colgroup>
				<col width="20%">
				<col width="30%">  
				<col width="20%">
				<col width="30%">
			</colgroup>
			<tbody>
				<tr>
					<th>과제(접수)번호</th>
					<td>
						 <input type="text" name="proj_recpt_no" id="proj_recpt_no" value="${data.proj_recpt_no}" style="width:100%;ime-mode:active">
					</td>
					<th>신청(접수)일</th>
					<td>
						 <c:out value="${data.req_dt}"></c:out> 
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>과제유형</th>
					<td colspan="3">
						 <c:forEach var="type" items="${projTyList}">
					        <input type="radio" id="proj_type_cd" name="proj_type_cd" value="${type.cd}" <c:if test="${data.proj_type_cd eq type.cd}">checked</c:if>>
					        <label for="${type.cd_nm}">${type.cd_nm}</label> 
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>보안등급</th>
					<td colspan="3">
						<c:forEach var="secu" items="${secuList}">
					        <input type="radio" id="securty_levl_cd" name="securty_levl_cd" value="${secu.cd}" <c:if test="${data.securty_levl_cd eq secu.cd}">checked</c:if>>
					        <label for="${secu.cd_nm}">${secu.cd_nm}</label>
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>R&#38;D여부</th>
					<td>
						<div class="custom-select selectRow">
							<select id="rnd_gb" name="rnd_gb" class="select">
							    <c:forEach var="rnd" items="${rndList}">
							        <option value="${rnd.cd_nm}" <c:if test="${data.rnd_gb eq rnd.cd_nm}">selected</c:if>>${rnd.cd_nm}</option>
							    </c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>산업기술분류</th>
					<td colspan="3">
						<table style="border:0px solid rgb(235 221 216); margin:0px;">
							<colgroup>
								<col width="25%">
								<col width="8%">  
								<col width="25%">
								<col width="8%">   
								<col width="25%">  
								<col width="8%"> 
							</colgroup>
							<tbody>
								<tr>
									<th>소분류(1순위)</th>
									<th>가중치(%)</th>
									<th>소분류(2순위)</th>
									<th>가중치(%)</th>
									<th>소분류(3순위)</th>
									<th>가중치(%)</th>
								</tr>
								<tr>
									<td>
										<input type="hidden" name="tech_cls_cd1" id="tech_cls_cd1" value="${data.tech_cls_cd1}">
										<input type="text" name="tech_cls_nm1" id="tech_cls_nm1" value="${data.tech_cls_nm1}" style="width:75%;background-color:#e9ecef; ime-mode:active" readonly="readonly"> 
										<a href="javascript:void(0);" onclick="javascript:fn_techSearch('1');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
									</td>
									<td>
										<input type="text" name="weight1" id="weight1" value="${data.weight1}" maxlength="3" oninput="this.value = this.value.replace(/[^\d.]/g, ''); if (parseInt(this.value) > 100) this.value = '';" style="width:100%;text-align:right; ime-mode:active">
									</td>
									<td>
										<input type="hidden" name="tech_cls_cd2" id="tech_cls_cd2" value="${data.tech_cls_cd2}">
										<input type="text" name="tech_cls_nm2" id="tech_cls_nm2" value="${data.tech_cls_nm2}" style="width:75%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
										<a href="javascript:void(0);" onclick="javascript:fn_techSearch('2');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
									</td>
									<td>
										<input type="text" name="weight2" id="weight2" value="${data.weight2}" maxlength="3" oninput="this.value = this.value.replace(/[^\d.]/g, ''); if (parseInt(this.value) > 100) this.value = '';" style="width:100%;text-align:right;ime-mode:active">
									</td> 
									<td>
										<input type="hidden" name="tech_cls_cd3" id="tech_cls_cd3" value="${data.tech_cls_cd3}">
										<input type="text" name="tech_cls_nm3" id="tech_cls_nm3" value="${data.tech_cls_nm3}" style="width:75%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
										<a href="javascript:void(0);" onclick="javascript:fn_techSearch('3');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
									</td>
									<td>
										<input type="text" name="weight3" id="weight3" value="${data.weight3}" maxlength="3" oninput="this.value = this.value.replace(/[^\d.]/g, ''); if (parseInt(this.value) > 100) this.value = '';" style="width:100%;text-align:right;ime-mode:active">
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>과제명</th>
					<td colspan="3">
						<table style="border:0px solid rgb(235 221 216); margin:0px;"> 
							<tr>
								<th>국문</th>
								<td>
									<input type="text" name="proj_nm_kor" id="proj_nm_kor" value="${data.proj_nm_kor}" style="width:100%;ime-mode:active">
								</td>
							</tr>
							<tr>
								<th>영문</th>
								<td>
									<input type="text" name="proj_nm_eng" id="proj_nm_eng" value="${data.proj_nm_eng}" style="width:100%;ime-mode:active">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>주관기관</th>
					<td colspan="3">
						<div class="inputs-in-a-row">
							 <input type="hidden" name="lead_org_cd" id="lead_org_cd" value="${data.lead_org_cd}"> 
							 <input type="text" name="mOrg_nm" id="mOrg_nm" value="${data.org_nm}" style="width:25%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
							 <input type="text" name="mOrg_crm_no" id="mOrg_crm_no" value="${data.org_crm_no}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="mOrg_address" id="mOrg_address" value="${data.org_address}" style="width:45%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <a href="javascript:void(0);" onclick="javascript:fn_mOrgSearch();" class="btn btn-secondary"><i class="fas fa-search"></i></a>
						</div>
					</td>
				</tr> 
				<tr>
					<th><font color="red" class="last-child">*</font>총괄책임자</th>
					<td colspan="3">
						<div class="inputs-in-a-row">
							 <input type="hidden" name="lResp_cd" id="lResp_cd" value="${lRespMap.resp_cd}">
							 <input type="text" name="lResp_nm" id="lResp_nm" value="${lRespMap.resp_nm}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
							 <input type="text" name="lResp_birth" id="lResp_birth" value="${lRespMap.resp_birth}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="lResp_dept" id="lResp_dept" value="${lRespMap.resp_dept}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="lResp_position" id="lResp_position" value="${lRespMap.resp_position}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
							 <input type="text" name="lResp_email" id="lResp_email" value="${lRespMap.resp_email}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="lResp_mbtlnum" id="lResp_mbtlnum" value="${lRespMap.resp_mbtlnum}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly">  
							 <a href="javascript:void(0);" onclick="javascript:fn_lRespSearch();" class="btn btn-secondary"><i class="fas fa-search"></i></a>
						</div>		 
					</td>
				</tr>
				<c:choose>
			<c:when test="${respList == null || fn:length(respList) == 0}">
				<tr name="respTr">
					<th><font color="red" class="last-child">*</font>실무책임자</th>
					<td colspan="3">
						<div class="inputs-in-a-row">
							 <input type="hidden" name="resp_cd" id="resp_cd0" value="${respList.resp_cd}">
							 <input type="text" name="resp_nm" id="resp_nm0" value="${respList.resp_nm}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
							 <input type="text" name="resp_birth" id="resp_birth0" value="${respList.resp_birth}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="resp_dept" id="resp_dept0" value="${respList.resp_dept}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="resp_position" id="resp_position0" value="${respList.resp_position}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="resp_email" id="resp_email0" value="${respList.resp_email}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="resp_mbtlnum" id="resp_mbtlnum0" value="${respList.resp_mbtlnum}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly">  
							 <a href="javascript:void(0);" onclick="javascript:fn_respSearch('0');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
							 <a href="javascript:void(0);" onclick="javascript:fn_addResp(this);" class="btn btn-secondary">추가</a>
						</div>		 
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${respList}" var="respList"  begin="0" end="${respList.size()}" step="1" varStatus="stts">
					<tr name="respTr"> 
					  <c:choose>
						 <c:when test="${stts.index ==0}">
							<th><font color="red" class="last-child">*</font>실무책임자</th>
						 </c:when>
						 <c:otherwise>
							<th></th> 
						 </c:otherwise>
					  </c:choose> 
							<td colspan="3">
								<div class="inputs-in-a-row">
									 <input type="hidden" name="resp_cd" id="resp_cd${stts.index}" value="${respList.resp_cd}" >
									 <input type="text" name="resp_nm" id="resp_nm${stts.index}" value="${respList.resp_nm}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
									 <input type="text" name="resp_birth" id="resp_birth${stts.index}" value="${respList.resp_birth}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
									 <input type="text" name="resp_dept" id="resp_dept${stts.index}" value="${respList.resp_dept}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
									 <input type="text" name="resp_position" id="resp_position${stts.index}" value="${respList.resp_position}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
									 <input type="text" name="resp_email" id="resp_email${stts.index}" value="${respList.resp_email}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
									 <input type="text" name="resp_mbtlnum" id="resp_mbtlnum${stts.index}" value="${respList.resp_mbtlnum}" style="width:15%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
									 <a href="javascript:void(0);" onclick="javascript:fn_respSearch('${stts.index}');" class="btn btn-secondary"><i class="fas fa-search"></i></a>
								<c:if test="${stts.index eq 0}">
										<a href="javascript:void(0);" onclick="javascript:fn_addResp(this);" class="btn btn-secondary">추가</a>			 
								</c:if>
								<c:if test="${stts.index ne 0}">   
										<a href="javascript:void(0);" onclick="javascript:fn_delResp(this, 'N');" class="btn btn-secondary">삭제</a>		 
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<tr>
					<th><font color="red" class="last-child">*</font>총수행기간</th>
					<td>
						 <input type="date" name="perform_strtdt" id="perform_strtdt" value="${data.perform_strtdt}" max="9999-12-31" min="1111-01-01"> ~
						 <input type="date" name="perform_enddt" id="perform_enddt" value ="${data.perform_enddt}" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th rowspan="3">단계별 기간</th>
					<td colspan="3">
						<font style="margin-left: 20px;">1단계 : </font>
						<input type="date" name="step1_strtdt" id="step1_strtdt" value="${data.step1_strtdt}" max="9999-12-31" min="1111-01-01"> ~
						<input type="date" name="step1_enddt" id="step1_enddt" value ="${data.step1_enddt}" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<font style="margin-left: 20px;">2단계 : </font>
						<input type="date" name="step2_strtdt" id="step2_strtdt" value="${data.step2_strtdt}" max="9999-12-31" min="1111-01-01"> ~
						<input type="date" name="step2_enddt" id="step2_enddt" value ="${data.step2_enddt}" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<font style="margin-left: 20px;">3단계 : </font>
						<input type="date" name="step3_strtdt" id="step3_strtdt" value="${data.step3_strtdt}" max="9999-12-31" min="1111-01-01"> ~
						<input type="date" name="step3_enddt" id="step3_enddt" value ="${data.step3_strtdt}" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>총사업비(원)</th>
					<td colspan="3">
						<input type="text" name="tot_cost" id="tot_cost" value="${data.tot_cost}" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, '');" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">정부 출연금(원) : </font><input type="text" name="gov_cost" id="gov_cost" value="${data.gov_cost}" maxlength="18" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">민간 부담금 현금(원) : </font><input type="text" name="cash" id="cash" value="${data.cash}" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">민간 부담금 현물(원) : </font><input type="text" name="stock" id="stock" value="${data.stock}" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
					</td>
				</tr>
				<c:choose>
			<c:when test="${partList == null || fn:length(partList) == 0}">
				<tr name="orgTr">
					<th>참여기관</th>
					<td colspan="3">
						<div class="inputs-in-a-row">
							 <input type="hidden" name="part_org_cd" id="part_org_cd0" value="${partList.part_org_cd}">
							 <input type="text" name="org_nm" id="org_nm0" value="${partList.org_nm}" style="width:25%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
							 <input type="text" name="org_crm_no" id="org_crm_no0" value="${partList.org_crm_no}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <input type="text" name="org_address" id="org_address0" value="${partList.org_address}" style="width:45%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
							 <a href="javascript:void(0);" onclick="javascript:fn_orgSearch('0');" class="btn btn-secondary"><i class="fas fa-search"></i></a>	 
							 <a href="javascript:void(0);" onclick="javascript:fn_addOrg(this);" class="btn btn-secondary">추가</a>
						</div>
					</td> 
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${partList}" var="partList" begin="0" end="${partList.size()}" step="1" varStatus="status">
					<tr name="orgTr">
						<c:choose>
							 <c:when test="${status.index ==0}">
								<th>참여기관</th> 
							 </c:when>
							 <c:otherwise> 
								<th></th> 
							 </c:otherwise>
						</c:choose>   
						<td colspan="3">
							<div class="inputs-in-a-row">
								 <input type="hidden" name="part_org_cd" id="part_org_cd${status.index}" value="${partList.part_org_cd}">
								 <input type="text" name="org_nm" id="org_nm${status.index}" value="${partList.org_nm}" style="width:25%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
								 <input type="text" name="org_crm_no" id="org_crm_no${status.index}" value="${partList.org_crm_no}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
								 <input type="text" name="org_address" id="org_address${status.index}" value="${partList.org_address}" style="width:45%;background-color:#e9ecef;ime-mode:active" readonly="readonly"> 
								 <a href="javascript:void(0);" onclick="javascript:fn_orgSearch('${status.index}');" class="btn btn-secondary"><i class="fas fa-search"></i></a> 
								<c:if test="${status.index eq 0}">
								 <a href="javascript:void(0);" onclick="javascript:fn_addOrg(this);" class="btn btn-secondary">추가</a>
								</c:if>
								<c:if test="${status.index ne 0}">
								 <a href="javascript:void(0);" onclick="javascript:fn_delOrg(this, 'N');" class="btn btn-secondary">삭제</a>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<tr>
					<th><font color="red" class="last-child">*</font>협약처리</th>
					<td>
						<div class="custom-select selectRow">
							 <select id="agmt_gb" name="agmt_gb" class="select"> 
							    <option value="0" ${data.agmt_gb == '0' ? 'selected' : ''}>미협약</option>  
							    <option value="1" ${data.agmt_gb == '1' ? 'selected' : ''}>협약완료</option>  
							</select>
						</div>

					</td>
					<th><font color="red" class="last-child">*</font>협약일자</th>
					<td>
						 <input type="date" name="agmt_dt" id="agmt_dt"  value ="${data.agmt_dt}" max="9999-12-31" min="1111-01-01">	
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>특기사항</th>
					<td colspan="3">
						<textarea name="agmt_info" id="agmt_info" rows="10" style="width:100%;ime-mode:active;">${data.agmt_info}</textarea> 
					</td>
				</tr>
				<tr> 
					<th>연구과제신청 첨부파일</th> 
				  	<td colspan="3">
<!-- 						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup2();" class="btn btn-secondary">파일 올리기</a> -->
<!-- 						 <div id="fileContainer2" class="file-container"></div>		 -->
						 <div id="fileContainer2"></div>		
				    </td>
				</tr>
				<tr> 
					<th>선정결과 첨부파일</th> 
				  	<td colspan="3">
<!-- 						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup3();" class="btn btn-secondary">파일 올리기</a> -->
<!-- 						 <div id="fileContainer3" class="file-container"></div>	 -->
						 <div id="fileContainer3"></div>	
				    </td>
				</tr>
	 			<tr> 
					<th>협약처리 첨부파일<br><font size="1">(수정사업계획서 첨부파일)</font></th> 
				  	<td colspan="3">
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup4();" class="btn btn-secondary">파일 올리기</a>	
						 <div id="fileContainer4" class="file-container"></div>
				    </td>
				</tr>
			</tbody>
		</table>
	 
	 </form>
	  
	 <div class="flex_box">
		
	  	<div align="right">
	  			<a href="javascript:void(0);" onclick="javascript:fn_save();" class="btn btn-secondary">저장</a>	  			
	  			<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
		</div>
	</div>
	         
</div>


<!-- 주기관 선택 관련 str -->
<form name ="orgPopForm" id="orgPopForm" method="post" action="">
	<input type="hidden" id="id_gbn1" name="id_gbn1"/>
	<input type="hidden" id="id_gbn2" name="id_gbn2"/>
	<input type="hidden" id="id_gbn3" name="id_gbn3"/>
	<input type="hidden" id="id_gbn4" name="id_gbn4"/>
	<input type="hidden" id="id_gbn5" name="id_gbn5"/>
	<input type="hidden" id="id_gbn6" name="id_gbn6"/>
	<input type="hidden" id="id_gbn7" name="id_gbn7"/>
</form>

    
   
	<!-- //right_content -->

	