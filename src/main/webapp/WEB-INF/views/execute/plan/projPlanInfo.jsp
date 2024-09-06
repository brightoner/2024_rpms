<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<script type="text/javascript">

	$(function(){   
		
		// 첨부파일
		getFileList($("#file_group").val(),'4');
		getFileList($("#year_file_group").val(),'5');
		
		// 총사업비 합
		sumTotal();
        document.getElementById("gov_cost").addEventListener("input", sumTotal);
        document.getElementById("cash").addEventListener("input", sumTotal);
        document.getElementById("stock").addEventListener("input", sumTotal);
		
	});
	
	
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
	
	  
	// 첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.planFrm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}	
	
	
	//첨부파일등록 팝업 호출
	function fn_egov_file_popup() {
		var planManageVo=document.planFrm;
		var file_group = planManageVo.file_group.value;
		var file_gb = "4";
		
		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	}
	
	//첨부파일등록 팝업 호출
	function fn_egov_file_popup1() {
		var planManageVo=document.planFrm;
		var file_group = planManageVo.year_file_group.value;
		var file_gb = "5";
		
		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');
	}
	 
	
	//첨부파일 목록 비동기적으로 최신화(자식창에서 변경되면 부모창에도 변경)
	function fileContainerRefresh() {
	
		getFileList($("#file_group").val(), '4');
		getFileList($("#year_file_group").val(), '5');
	}

	 
	// 과제협약 저장
	function fn_save(){
        
        var mainItem = [];
        var respList = [];
        var requestData = [];
        
        var item = serializeFormToJSON($("form[name='planFrm']"));

		var proj_year_id = $("#proj_year_id").val();
		
        $('tr[name="respTr"]').each(function() {
        	var $td = $(this);
        	
            var year_resp_cd = $td.find('input[name="resp_cd"]');
            var year_part_rate = $td.find('input[name="part_rate"]');
            var year_resp_gb = $td.find('select[name="resp_gb"] option:selected');
            var year_part_strtdt = $td.find('input[name="part_strtdt"]');
            var year_part_enddt = $td.find('input[name="part_enddt"]');

            // 데이터 객체를 만들어 리스트에 추가
            var item = {
            		year_resp_cd: year_resp_cd.val(),
            		year_part_rate: isEmpty(year_part_rate.val()) ?  '0' :year_part_rate.val(), 
            		year_resp_gb: year_resp_gb.val(),
            		year_part_strtdt: year_part_strtdt.val(),
            		year_part_enddt: year_part_enddt.val()
            };
            respList.push(item);
        });
    
        var requestData = {
        		mainItem: item,
        		respList : respList,
        		proj_year_id:proj_year_id
            };
        
		if(confirm("저장하시겠습니까?") ==true ){
			
		    $.ajax({
		        url: '${ctxt}/execute/plan/updateProjPlan.do',
		        data: JSON.stringify(requestData),
		        type: 'POST', 
		        contentType: 'application/json', // Content-Type을 설정합니다.
		        dataType: 'json',
		        cache: false, 	
			    async : false,
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
		}
	}
	
	// 삭제
	function fn_delete(){
		var projVal = $("#proj_year_id").val();
		if( isEmpty(projVal) ){
			fn_showCustomAlert("과제 키 또는 참여연구원 key 정보가 존재하지 않습니다.\n 다시 로그인 후 확인해주세요.");
			return false;
		}
	
		var rVal =  fn_respRateSearch('',projVal);
		
		if(rVal == true){
			fn_showCustomAlert("참여 연구자중 과제 참여율 관리 메뉴에서 참여율이 설정된 연구자가 존재합니다.\n삭제할 수 없습니다.");
			return false;
		}
		
		
		
		if(confirm("삭제 하시겠습니까?") ==true ){
			var form = document.planFrm;
			form.action = '${ctxt}/execute/plan/deleteProjPlan.do'; 
			form.submit();
		}
	}
	
	// 목록으로 이동
	function fn_back(){
		var form = document.planFrm;
		form.action = '${ctxt}/execute/plan/projPlanList.do';
		form.submit();	
	}
	
 
	function fn_wbsCreate (){
		fn_showCustomConfirm("question","WBS를 생성 하시겠습니까?", function() {
				var sDt = $("#wbs_str_dt").val();
				var eDt = $("#wbs_end_dt").val();
		
				var sDtYM = sDt.substring(0, 7);  // yyyy-mm
				var eDtYM = eDt.substring(0, 7);  
				
				if(isEmpty(sDt) ||  isEmpty(eDt)){
					fn_showCustomAlert("입력값이 존재하는지 확인 해주십시오.");
					return false;
				}
				
				if(sDt >= eDt){
					fn_showCustomAlert("시작일은 종료일이 보다 크거나 같을 수 없습니다.");
					return false;
				}
				
				if(sDtYM >= eDtYM){
					fn_showCustomAlert("동일한 년도와 월이 설정 되었습니다. 확인 바랍니다.");
					return false;
				}
				
				var params = {};
				 	params.proj_year_id ='${data.proj_year_id}';
					params.wbs_str_dt = $("#wbs_str_dt").val();
					params.wbs_end_dt = $("#wbs_end_dt").val();
				
				$.ajax({
				    url: '${ctxt}/execute/wbsMng/insertWbsBaseInfo.do',
				    data: params,
				    type: 'POST',
				    async : false,
				    dataType: 'json',
				    //processData: false,
				    success: function(result) {
					     if(result.sMessege == "Y"){
					    	 fn_showCustomAlert("WBS가 생성되었습니다.");
					    	 setTimeout(function() { location.reload(); }, 2000);
					     }else {
					    	 fn_showCustomAlert("다시 로그인이 필요합니다.");
					     }
				    	},
					    error : function(){                              // Ajax 전송 에러 발생시 실행
					    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
					    },
					    complete : function (){
					    	
					    }
					});
		});
	}
	
	function fn_regWbsLayer(){

		var layerHtml = "";
		
	 		 
		//드래그 가능한 div
		layerHtml +=`<div class="draggable-div" style="width: 400px; inset: 224px auto auto 804px; height: 250px;">			
			<div class="form-group">
	 		<label for="inputText" class="form-label"><strong>WBS 생성</strong></label>
	 		
	 		<div style= "display: grid; grid-template-columns: auto 50px auto; justify-content: center;align-items: center;text-align: center;">
	     	
	     		<input type="date" class="form-control" id="wbs_str_dt" name="wbs_str_dt" value="${data.cur_strtdt}"   max="9999-12-31" min="1111-01-01"> 
	     		<label>~</label>
	     		<input type="date" class="form-control" id="wbs_end_dt" name="wbs_end_dt" value="${data.cur_enddt}"  max="9999-12-31" min="1111-01-01"> 
	     	</div>
	     	<div class="ma_t_30">						
	     	    <a href="javascript:fn_wbsCreate();" class="float_n btn btn-primary">생성</a>
	     	    <a href="javascript:closeWbsPop();" class="btn btn-secondary">닫기</a>
	 		</div>
	 	</div>
	 </div>`;
		
		$("#regWbsLayer").html(layerHtml);
		$(".draggable-div").draggable();		
	  
	}

	//아이템 선물하기 팝업 닫기 
	function closeWbsPop(){	     
		 var element = document.getElementById('regWbsLayer');
		 element.innerText = '';
	}	
	
	function fn_respRateSearch(respVal , projVal){
		
		var ruturnVal = false;	
		var params = {};
			params.year_resp_cd    = respVal;   
			params.proj_year_id    = projVal;   
				
				
		    $.ajax({
		        url: '${ctxt}/execute/rpMng/chkProjRpRate.do',
		        data: params,
		        type: 'POST',
		        async : false,
		        dataType: 'json',
		        success: function(result) {
		        	if(!isEmpty(result) && !isEmpty(result.rateInfo)){
		        		
		        		if(result.rateInfo.sum_rate > 0){
		        			ruturnVal = true;	
		        		}
		        	}else{
		        		ruturnVal = false;
		        	}
		        	
		        },
		        error : function(){                              // Ajax 전송 에러 발생시 실행
		        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		        	ruturnVal = false;
		        }
		    });
		    
		    return ruturnVal;
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
	
	
	
	// 참여연구원 팝업
	function fn_respSearch(seq , status){
		
		if(status == 'N'){
			var respVal = $("#resp_cd" + seq).val();
			var projVal = $("#proj_year_id").val();
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
			
		}
		
		$("#id_gbn1").val("resp_cd" + seq);
		$("#id_gbn2").val("resp_nm" + seq);

		var popUrl = "<c:out value='${pageContext.request.contextPath}'/>/opsmng/respMng/respMngPopList.do";	//팝업창에 출력될 페이지 URL
		var popOption = "width=1300, height=900, resizable=yes, scrollbars=yes, status=no;"; //팝업창 옵션(optoin)
		popOpen = window.open(popUrl, 'selectPopup', popOption);
		$('#orgPopForm').attr("action",popUrl);
		$('#orgPopForm').attr("target","selectPopup");
		$('#orgPopForm').attr("method","post");
		$('#orgPopForm').submit();
	}

	
	
 
	function fn_addResp(obj) {
		var addHtml ='';
		
		var respIndex =  $("#projDetailTable tr[name = 'respTr']").length;
		
	    var $lastRow =  $("#projDetailTable tr[name = 'respTr']").last();
	 	    
	    addHtml += '<tr name = "respTr">';
    		addHtml += '<th></th>';
	    	addHtml += '<td colspan = "3">';
			    addHtml += '<div class="inputs-in-a-row">'
			    	             + '<input type="hidden" name="resp_cd" id="resp_cd' + respIndex + '" value="">'
			    	             + '<input type="hidden" name="rowState" value="I">'
			                     + '<input type="text" name="resp_nm" id="resp_nm' + respIndex + '" value="" style="width:10%;left-margin:4px;background-color:#e9ecef;ime-mode:active" readonly="readonly">'
			                     + '<div class="custom-select selectRow" left-margin:4px;>'
			                     + '    <select name="resp_gb" id="resp_gb' + respIndex + '" class="select">'
			                     + '        <option value="">구분</option>' 
			                     + '        <option value="L">연구책임자</option>'
			                     + '        <option value="R">실무담당자</option>'
			                     + '        <option value="E">기타</option>' 
			                     + '    </select>'
			                     + '</div>'
			                     + '<input type="text" name="part_rate" id="part_rate' + respIndex + '" value="" maxlength="6" style="width:10%;margin-left: 4px;text-align:right;ime-mode:active" placeholder="참여율(%)" oninput="this.value = this.value.replace(/(?!\\d*\\.?\\d{0,2}$)[^\\d.]/g, \'\').replace(/^(\\d*\\.\\d{2}).*$/, \'$1\'); if (parseInt(this.value) > 100) this.value = \'0\'">'
			                     + '<input type="date" name="part_strtdt" id="part_strtdt' + respIndex + '" value=""  max="9999-12-31" min="1111-01-01" style="left-margin:4px;">'
			                     + ' ~ '
			                     + '<input type="date" name="part_enddt" id="part_enddt' + respIndex + '" value=""  max="9999-12-31" min="1111-01-01" style="left-margin:4px;">'
			                     + '<a href="javascript:void(0);" onclick="javascript:fn_respSearch(' + respIndex + ', \'I\');" class="btn btn-secondary" style="margin-left: 4px;"><i class="fas fa-search"></i></a>'
			                     + '<a href="javascript:void(0);" onclick="javascript:fn_delResp(this , \'I\');" class="btn btn-secondary" style="margin-left: 4px;">삭제</a>'
			                     + '<div>';
	        addHtml += '</td>'
        addHtml += '</tr>'
	    // 셀들 추가
     
	    $lastRow.after(addHtml);

	}
	
	// 참여연구원 row 삭제
	function fn_delResp(obj , status) {
		 var row = $(obj).closest('tr');
		 	 
		 if(status == 'N'){
			var respVal =  row.find("input[name = 'resp_cd']").val();
			var projVal = $("#proj_year_id").val();
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
			
		}
		
	   
		 row.remove();
	}
	
</script>
<!-- 본문내용 -->
<!-- 본문내용 -->
 
<div id="right_content">   
	 <h3 class="page_title" id="title_div"><span class="adminIcon"></span>연차과제 상세</h3>  
	<div class="titles">
	<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;과제 협약 정보</h4>
			<div class="btn_wrap" >			
				<a href="javascript:void(0);" onclick="javascript:fn_regWbsLayer();" class="btn btn-secondary">WBS 생성</a> 			
				<a href="javascript:void(0);" onclick="javascript:fn_acctSubjectListSearch();" class="btn btn-secondary">예산 편성</a>
				<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_wideContent();" >화면 확대/축소</a>						
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>
			</div>		
	</div>
	
	
	
	<form name="annFrm" method="post"  action=""  enctype="multipart/form-data">	
		<table class="table_v" >  
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
				<!-- 주관기관 -->
				<tr>
					<th>주관기관</th>
					<td colspan="3">
<%-- 						 <input type="hidden" name="lead_org_cd" id="lead_org_cd" value="${data.lead_org_cd}">  --%>
						<div style="width:25%; display: inline-block;">
							<c:out value="${data.org_nm}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${data.org_crm_no}"></c:out>
						</div>
						<div style="width:45%; display: inline-block;">
							<c:out value="${data.org_address}"></c:out>
						</div>
					</td>
				</tr> 
				
				<!-- 참여기관 -->
		<c:choose>
			<c:when test="${partList == null || fn:length(partList) == 0}">
				<tr>
					<th>참여기관</th>
					<td colspan="3">
						<div style="width:25%; display: inline-block;">
							<c:out value="${partList.org_nm}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${partList.org_crm_no}"></c:out>
						</div>
						<div style="width:45%; display: inline-block;">
							<c:out value="${partList.org_address}"></c:out>
						</div>
					</td>	 
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${partList}" var="partList" begin="0" end="${partList.size()}" step="1" varStatus="status">
					<tr>
						<c:choose>
							 <c:when test="${status.index ==0}">
								<th>참여기관</th> 
							 </c:when>
							 <c:otherwise> 
								<th></th> 
							 </c:otherwise>
						</c:choose>   
						<td colspan="3">
							<div style="width:25%; display: inline-block;">
								<c:out value="${partList.org_nm}"></c:out>
							</div>
							<div style="width:10%; display: inline-block;">
								<c:out value="${partList.org_crm_no}"></c:out>
							</div>
							<div style="width:45%; display: inline-block;">
								<c:out value="${partList.org_address}"></c:out>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<!-- 총괄책임자 -->
				<tr>
					<th>총괄책임자</th>
					<td colspan="3">
						<div style="width:10%; display: inline-block;">
							<c:out value="${lRespMap.resp_nm}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${lRespMap.resp_birth}"></c:out>
						</div>
						<div style="width:15%; display: inline-block;">
							<c:out value="${lRespMap.resp_dept}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${lRespMap.resp_position}"></c:out>
						</div>
						<div style="width:15%; display: inline-block;">
							<c:out value="${lRespMap.resp_email}"></c:out>
						</div>
						<div style="width:15%; display: inline-block;">					
							<c:out value="${fn:substring (lRespMap.resp_mbtlnum , 0 ,3)}"></c:out>
							&nbsp;<c:out value="${fn:substring (lRespMap.resp_mbtlnum , 3 ,7)}"></c:out>
							&nbsp;<c:out value="${fn:substring (lRespMap.resp_mbtlnum , 7 ,11)}"></c:out>
							
						</div>
					</td>
				</tr>
				
				<!-- 실무책임자 -->
				<c:choose>
			<c:when test="${respList == null || fn:length(respList) == 0}">
				<tr>
					<th>실무책임자</th>
					<td colspan="3">
						<div style="width:10%; display: inline-block;">
							<c:out value="${respList.resp_nm}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${respList.resp_birth}"></c:out>
						</div>
						<div style="width:15%; display: inline-block;">
							<c:out value="${respList.resp_dept}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${respList.resp_position}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">
							<c:out value="${respList.resp_email}"></c:out>
						</div>
						<div style="width:10%; display: inline-block;">	
							${respList.resp_mbtlnum}												
						</div>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${respList}" var="respList"  begin="0" end="${respList.size()}" step="1" varStatus="stts">
					<tr> 
					   <c:choose>
						 <c:when test="${stts.index ==0}">
							<th>실무책임자</th>
						 </c:when>
						 <c:otherwise>
							<th></th> 
						 </c:otherwise>
					  </c:choose> 
						<td colspan="3">
							<div style="width:10%; display: inline-block;">
								<c:out value="${respList.resp_nm}"></c:out>
							</div>
							<div style="width:10%; display: inline-block;">
								<c:out value="${respList.resp_birth}"></c:out>
							</div>
							<div style="width:15%; display: inline-block;">
								<c:out value="${respList.resp_dept}"></c:out>
							</div>
							<div style="width:10%; display: inline-block;">
								<c:out value="${respList.resp_position}"></c:out>
							</div>
							<div style="width:15%; display: inline-block;">
								<c:out value="${respList.resp_email}"></c:out>
							</div>
							<div style="width:15%; display: inline-block;">
								<c:out value="${fn:substring (respList.resp_mbtlnum , 0 ,3)}"></c:out>
								&nbsp;<c:out value="${fn:substring (respList.resp_mbtlnum , 3 ,7)}"></c:out>
								&nbsp;<c:out value="${fn:substring (respList.resp_mbtlnum , 7 ,11)}"></c:out>
							</div> 
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				
				
			</tbody>
		</table>  
	 </form>      
	
	 <form name="planFrm" method="post"  action=""  enctype="multipart/form-data">
	 	<input type="hidden" name="ann_id" id="ann_id" value="${data.ann_id}" />
		<input type="hidden" name="proj_id" id="proj_id" value="${data.proj_id}" />
		<input type="hidden" name="proj_year_id" id="proj_year_id" value="${data.proj_year_id}" />
		<input type="hidden" name="file_group" id="file_group" value="${data.file_group}">
		<input type="hidden" name="year_file_group" id="year_file_group" value="${data.year_file_group}">
		<input type="hidden" name="part_org_cds" id="part_org_cds" value="">
		<input type="hidden" name="resp_cds" id="resp_cds" value="">
		<input type="hidden" name="part_rates" id="part_rates" value="">
		<input type="hidden" name="resp_gbs" id="resp_gbs" value="">
		<input type="hidden" name="part_strtdts" id="part_strtdts" value="">
		<input type="hidden" name="part_enddts" id="part_enddts" value="">

		<input type="hidden" name="page" value="${plan.page}" />
		<input type="hidden" name="dept_org" value="${plan.dept_org}" />
		<input type="hidden" name="ddct_org" value="${plan.ddct_org}" />
		<input type="hidden" name="year" value="${plan.year}" />
		<input type="hidden" name="searchword" value="${plan.searchword}" />
	
			<div class="titles"> 
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;연차과제 정보</h4>
			</div>		
		</div>
	
	 	<table class="table_v" id = "projDetailTable">  
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
						 <c:out value="${data.proj_recpt_no}"></c:out>
					</td>
				</tr>
				<tr>
					<th><font color="red">*</font>수행단계, 연차</th> 
					<td>
						<div class="form-row">
							<div class="custom-select selectRow">
								<select id="proj_step_gb" name="proj_step_gb" class="select">
									<option value="">단계선택</option> 
									<option value="1" <c:if test="${data.proj_step_gb eq '1'}">selected</c:if>>1 단계</option> 
									<option value="2" <c:if test="${data.proj_step_gb eq '2'}">selected</c:if>>2 단계</option> 
									<option value="3" <c:if test="${data.proj_step_gb eq '3'}">selected</c:if>>3 단계</option> 
								</select>
							</div>
							<div class="custom-select selectRow">
								<select id="proj_year_gb" name="proj_year_gb" class="select">
									<option value="">연차선택</option> 
									<option value="1" <c:if test="${data.proj_year_gb eq '1'}">selected</c:if>>1 연차</option> 
									<option value="2" <c:if test="${data.proj_year_gb eq '2'}">selected</c:if>>2 연차</option> 
									<option value="3" <c:if test="${data.proj_year_gb eq '3'}">selected</c:if>>3 연차</option> 
									<option value="4" <c:if test="${data.proj_year_gb eq '4'}">selected</c:if>>4 연차</option> 
									<option value="5" <c:if test="${data.proj_year_gb eq '5'}">selected</c:if>>5 연차</option> 
									<option value="6" <c:if test="${data.proj_year_gb eq '6'}">selected</c:if>>6 연차</option> 
									<option value="7" <c:if test="${data.proj_year_gb eq '7'}">selected</c:if>>7 연차</option> 
									<option value="8" <c:if test="${data.proj_year_gb eq '8'}">selected</c:if>>8 연차</option> 
									<option value="9" <c:if test="${data.proj_year_gb eq '9'}">selected</c:if>>9 연차</option> 
								</select>
							</div>
						</div>
					</td>
					<th><font color="red" class="last-child">*</font>수행기간</th>
					<td>
						 <input type="date" name="cur_strtdt" id="cur_strtdt" value="${data.cur_strtdt}" max="9999-12-31" min="1111-01-01"> ~
						 <input type="date" name="cur_enddt" id="cur_enddt" value ="${data.cur_enddt}" max="9999-12-31" min="1111-01-01">
					</td>
				</tr>
				<tr>
					<th><font color="red" class="last-child">*</font>당해년도 사업비(원)</th>
					<td colspan="3">
						<input type="text" name="tot_cost" id="tot_cost" value="${data.tot_cost}" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, '');" style="width:15%;text-align:right;background-color:#e9ecef;ime-mode:active" readonly="readonly">
						<font style="margin-left: 20px;">정부 출연금(원) : </font><input type="text" name="gov_cost" id="gov_cost" value="${data.gov_cost}" maxlength="18" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">민간 부담금 현금(원) : </font><input type="text" name="cash" id="cash" value="${data.cash}" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
						<font style="margin-left: 20px;">민간 부담금 현물(원) : </font><input type="text" name="stock" id="stock" value="${data.stock}" maxlength="13" oninput="this.value = this.value.replace(/[^0-9.]/g, ''); inputComma(event)" style="width:15%;text-align:right;ime-mode:active">
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
		<c:choose>
			<c:when test="${resYearpList == null || fn:length(resYearpList) == 0}">
				<tr name="respTr">
					<th><font color="red" class="last-child">*</font>참여연구원</th>
					<td colspan="3">
						<div class="inputs-in-a-row">
							 <input type="hidden" name="resp_cd" id="resp_cd0" value="${resYearpList.year_resp_cd}">
							 <input type="text" name="resp_nm" id="resp_nm0" value="${resYearpList.resp_nm}" style="width:10%;background-color:#e9ecef;ime-mode:active" readonly="readonly">
							 <div class="custom-select selectRow">
								<select id="resp_gb0" name="resp_gb" class="select">
									<option value="">구분</option> 
									<option value="L">연구책임자</option> 
									<option value="R">실무담당자</option> 
									<option value="E">기타</option> 
								</select>
							</div>
								 <input type="text" name="part_rate" id="part_rate${stts.index}" value="${resYearpList.year_part_rate}" maxlength="6" oninput="this.value = this.value.replace(/(?!\d*\.?\d{0,2}$)[^\d.]/g, '').replace(/^(\d*\.\d{2}).*$/, '$1'); if (parseInt(this.value) > 100) this.value = '0';" style="width:10%;text-align:right;ime-mode:active"  placeholder="참여율(%)">
							 <input type="date" name="part_strtdt" id="part_strtdt0" value="${resYearpList.year_part_strtdt}" max="9999-12-31" min="1111-01-01">  
							 ~ <input type="date" name="part_enddt" id="part_enddt0" value="${resYearpList.year_part_enddt}" max="9999-12-31" min="1111-01-01">  
							 <a href="javascript:void(0);" onclick="javascript:fn_respSearch('0', 'I');" class="btn btn-secondary"><i class="fas fa-search"></i></a>	 
							 <a href="javascript:void(0);" onclick="javascript:fn_addResp(this);" class="btn btn-secondary">추가</a>
						</div>		 
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${resYearpList}" var="resYearpList"  begin="0" end="${resYearpList.size()}" step="1" varStatus="stts">
					<tr name="respTr"> 
					   <c:choose>
						 <c:when test="${stts.index ==0}">
							<th><font color="red" class="last-child">*</font>참여연구원</th>
						 </c:when> 
						 <c:otherwise>
							<th></th> 
						 </c:otherwise>
					  </c:choose> 
						<td colspan="3">
							<div class="inputs-in-a-row">
								 <input type="hidden" name="resp_cd" id="resp_cd${stts.index}" value="${resYearpList.year_resp_cd}" >
								 <input type="hidden" name="rowState" value="N">
								 <input type="text" name="resp_nm" id="resp_nm${stts.index}" value="${resYearpList.resp_nm}" style="width:10%;background-color:#e9ecef;text-align:right;ime-mode:active" readonly="readonly">
								<div class="custom-select selectRow">
									<select id="resp_gb0" name="resp_gb" class="select">
										<option value="">구분</option> 
										<option value="L" <c:if test="${resYearpList.res_gb eq 'L'}">selected</c:if>>연구책임자</option> 
										<option value="R" <c:if test="${resYearpList.res_gb eq 'R'}">selected</c:if>>실무담당자</option> 
										<option value="E" <c:if test="${resYearpList.res_gb eq 'E'}">selected</c:if>>기타</option> 
									</select>
								</div>
								 <input type="text" name="part_rate" id="part_rate${stts.index}" value="${resYearpList.year_part_rate}" maxlength="6" oninput="this.value = this.value.replace(/(?!\d*\.?\d{0,2}$)[^\d.]/g, '').replace(/^(\d*\.\d{2}).*$/, '$1'); if (parseInt(this.value) > 100) this.value = '0'" style="width:10%;text-align:right;ime-mode:active"  placeholder="참여율(%)">
								 <input type="date" name="part_strtdt" id="part_strtdt${stts.index}" value="${resYearpList.year_part_strtdt}" max="9999-12-31" min="1111-01-01"> 
							 	 ~ <input type="date" name="part_enddt" id="part_enddt${stts.index}" value="${resYearpList.year_part_enddt}" max="9999-12-31" min="1111-01-01">
								 <a href="javascript:void(0);" onclick="javascript:fn_respSearch('${stts.index}' , 'N');" class="btn btn-secondary"><i class="fas fa-search"></i></a> 
								<c:if test="${stts.index eq 0}">
										<a href="javascript:void(0);" onclick="javascript:fn_addResp(this);" class="btn btn-secondary">추가</a>		 
								</c:if>
								<c:if test="${stts.index ne 0}">   
										<a href="javascript:void(0);" onclick="javascript:fn_delResp(this , 'N');" class="btn btn-secondary">삭제</a>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				<tr>
					<th>특기사항</th>
					<td colspan="3">
						<textarea name="year_info" id="year_info" rows="10" style="width:100%;ime-mode:active;">${data.year_info}</textarea>
					</td>
				</tr>
	 			<tr> 
					<th>협약처리 첨부파일<br><font size="1">(수정사업계획서 첨부파일)</font></th> 
				  	<td colspan="3">
<!-- 						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup();" class="btn btn-secondary">파일 올리기</a> -->
<!-- 						 <div id="fileContainer4" class="file-container"></div>	 -->
							<div id="fileContainer4"></div>	
				    </td>
				</tr>
	 			<tr> 
					<th>연차과제 첨부파일</th> 
				  	<td colspan="3">
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup1();" class="btn btn-secondary">파일 올리기</a>
						 <div id="fileContainer5" class="file-container"></div>	
				    </td>
				</tr>
				
			</tbody>
		</table>
		
	 
	 </form>
	  
	 <div class="flex_box">
		
	  	<div align="right">
	  			<a href="javascript:void(0);" onclick="javascript:fn_save();" class="btn btn-secondary">저장</a>
	  			<a href="javascript:void(0);" onclick="javascript:fn_delete();" class="btn btn-secondary">삭제</a>
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

	<div id="regWbsLayer">
	</div>
