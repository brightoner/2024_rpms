<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>




<script type="text/javascript">
var xmlList;
var html="";
var cuurPage;
var uploadPath = "";
var pagetotalCnt = 0;

var popCuurPage;
var poppopPagetotalCnt = 0;
$(function(){
	
	// 파일 그룹키
	GetDateTMS();
	
	
	
    var page =$('#page').val();
	if(page == '' ){
		page=1;
	}

	// 년도 selectbox 처리
	var select = document.getElementById('searchoption3');
    var currentYear = new Date().getFullYear();
    var startYear = 2020;	// 시작년도 설정

    for (var year = currentYear; year >= startYear; year--) {
        var option = document.createElement('option');

        if("${followParam.searchoption3}" == year ){
        	option.selected = 	'selected';
        }
        option.value = year;
        option.textContent = year;
        select.appendChild(option); 
    }
    
    fn_search(page);
    
    
	// 체크박스가 하나만 선택되게 처리  
	$(document).on('change', 'input[name="targetChkObj"]', function() {
	    console.log('Checkbox changed');
	    $('input[name="targetChkObj"]').not(this).prop('checked', false);
	});
    
});

//파일 그룹키
function GetDateTMS() {
    var localTime = new Date();
	var year= localTime.getFullYear();
	var month= localTime.getMonth() +1;
    var date = localTime.getDate();
    var hours = localTime.getHours();
    var minutes = localTime.getMinutes();
    var seconds = localTime.getSeconds();
    var milliseconds = localTime.getMilliseconds();
    var totalTime = leadingZeros(year,4)+leadingZeros(month,2)+leadingZeros(date,2)+leadingZeros(hours,2)+leadingZeros(minutes,2)+leadingZeros(seconds,2)+leadingZeros(milliseconds,3);

    $("#end_file_group").val(totalTime);
    
}

function leadingZeros(n, digits) {
    var zero = '';
	n = n.toString();

	if (n.length < digits) {
      for (var i = 0; i < digits - n.length; i++)
		 zero += '0';
	}
	  return zero + n;
}



/*
* 페이징 처리 2 : 페이지 num를 조회조건으로 넘겨서 조회, 페이지 이동시 ajax 재호출
*/
function fn_search(page){
	//현재 페이지 세팅
		cuurPage= page;
	
		var params = {};
			params.page    		= cuurPage;   
			
			params.searchoption1 = $('[name="searchoption1"] :selected').val();    
			params.searchoption2 = $('[name="searchoption2"] :selected').val(); 
			params.searchoption3 = $('[name="searchoption3"] :selected').val();
			
			params.searchword    = $('#searchword').val();   
			
	  	$('#page').val(cuurPage);
  	  	
    $.ajax({
        url: '${ctxt}/follow/end/planInfo/readEndFollowList.do',
        data: params,
        type: 'POST',
        dataType: 'json',
        success: function(result) {
        	html ='';
        	pagetotalCnt = 0;
        	if(!isEmpty(result)){
				if(!isEmpty(result.planList)){
					var start_num = Number(result.planTotal) - ((cuurPage -1) *10)
					pagetotalCnt =Number(result.planPageTotal);	
					
					$.each(result.planList, function(idx, item){
						html += '<tr>';
							html += '<td>' + (start_num - idx) + '</td>';
							html += '<td>'+((isEmpty(item.dept_org)) ? '' : item.dept_org) +'</td>';
							html += '<td>'+((isEmpty(item.ddct_org)) ? '' : item.ddct_org) +'</td>';
							html += '<td class="text_l"><a href="javascript:fn_endFollowDtl(\''+item.proj_end_id+'\');">'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</a></td>';
							html += '<td>'+((isEmpty(item.proj_end_gb)) ? '' : item.proj_end_gb + ' 년차') +'</td>';
							html += '<td>'+((isEmpty(item.perform_strtdt)) ? '' : item.perform_strtdt+' ~ '+item.perform_enddt) +'</td>';
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택 
					//페이징처리
				    $('#paging').paging({
				    	
						 current:cuurPage
						,max:pagetotalCnt
						,length:pageLen
						,onclick:function(e,page){
							cuurPage=page;
							fn_search(cuurPage);
						}
					});
					
				 	$('#dataList').html(html);
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#paging').children().remove();
					
					$("#dataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#dataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        }
    });
}




function showCustomPopup() {
    Swal.fire({
        title: '종료과제 연차별 성과등록 대상 목록',
        width : 1600,
        html: `
		<div class="divPopArea">
        	<form name="writeForm" method="post" action="">
			
        	<div class="searchAreaFlex">
				<div class="grid_box">
				
					
					<div class="custom-select selectRow">
						<label for="dept_org_pop" class="hidden-access"></label>
						<select id="dept_org_pop" name="dept_org_pop"> 
							<option value="">소관부처</option> 
					        <c:forEach var="dept" items="${deptList}">
					        	<option value="${dept.org_nm}">${dept.org_nm}</option>
					        </c:forEach>
						</select>
					</div>
					
				
					<div class="custom-select selectRow">
						<label for="ddct_org_pop" class="hidden-access"></label>
						<select id="ddct_org_pop" name="ddct_org_pop"> 
							<option value="">전담기관</option> 
					        <c:forEach var="ddct" items="${ddctList}">
					        	<option value="${ddct.org_nm}">${ddct.org_nm}</option>
					        </c:forEach>
						</select>
					</div>
					  
					
					협약과제명 : <input type="text" name="searchword_pop" id="searchword_pop"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_popSearch(1); return false;}" />
					
					<a href="javascript:void(0);" class="btn btn-primary" onclick="javascript:fn_popSearch(1);" >검색</a>
					
				</div>
				<div class="btn_wrap">	
					
					<div class="custom-select selectRow" style="width : 80px;">
							<select id="endProjYearSel" name="endProjYearSel"> 
								<option value="1">1년 차</option> 
								<option value="2">2년 차</option> 
								<option value="3">3년 차</option> 
								<option value="4">4년 차</option> 
								<option value="5">5년 차</option> 
								<option value="6">6년 차</option> 
								<option value="7">7년 차</option> 
					        
							</select>
					</div>	
				 <a href="javascript:void(0);" class="btn btn-secondary"  style="width : 150px;" onclick="javascript:fn_endCreate();">종료과제 생성</a>					
				</div>	
			</div>
			
		</form>
		
		 
	
		<div class="admin ma_t_20"> 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" > 
				<caption>
				 	 연차과제 -  번호, 소관기관 , 전담기관, 공고명, 신청과제명, 협약일
				</caption>
				<colgroup>
					<col style="width:5%">	
					<col style="width:5%">	
					<col style="width:12%">
					<col style="width:12%">						 	
					<col style="width:*">				
					<col style="width:18%">		
					<col style="width:8%">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>												
						<th scope='col'>선택</th>												
						<th scope='col'>소관기관</th>
						<th scope='col'>전담기관</th>
						<th scope='col'>협약과제명</th>																							
						<th scope='col'>수행기간</th>
						<th scope='col'>(현)종료연차</th>																							
					</tr>
				</thead>
				<tbody id="popDataList">
				</tbody>
			</table>
		
			<div id="popPaging" class="paginate"></div>
		
		</div>
		</div> 
        `,
        showCancelButton: true,
        showConfirmButton: false,
        /* confirmButtonText: '', */
        cancelButtonText: '닫기',
        preConfirm: () => {
        
        }
    }).then((result) => {
        if (result.isConfirmed) {
        
        }
    });
    
    
	fn_popSearch(1);   // 데이터 호출 
	$(".swal2-popup").draggable();	
	 
}  


// 종료과제생성
function fn_endCreate(){
	
	var proj_id_target =  $('input[name="targetChkObj"]:checked').val();		
	var proj_end_gb = $('select[name="endProjYearSel"]').val();
	var end_file_group = $("#end_file_group").val();
	
	var requestData = {
			proj_id_target : proj_id_target,
			proj_end_gb : proj_end_gb,
			end_file_group : end_file_group
        };
	
	// 종료과제 존재 유무 확인
	$.ajax({
        url: '${ctxt}/follow/year/employ/chkEndPlan.do',
        data: JSON.stringify(requestData),
        contentType: 'application/json',
        type: 'POST', 
        dataType: 'json',	      
		success: function(result){			
			
			if(result.result != 0){
				fn_showCustomAlert("종료과제의 연차가 이미 존재합니다.");
				return false;
			}else{
				create();
			}
		},
		error: function(request,status,error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
  });
	
	
}


function create(){
	
	
	var proj_id_target =  $('input[name="targetChkObj"]:checked').val();		
	var proj_end_gb = $('select[name="endProjYearSel"]').val();
	var end_file_group = $("#end_file_group").val();
	
	var requestData = {
			proj_id_target : proj_id_target,
			proj_end_gb : proj_end_gb,
			end_file_group : end_file_group
        };
	
	$.ajax({
        url: '${ctxt}/follow/year/employ/insertEndPlan.do',
        data: JSON.stringify(requestData),
        contentType: 'application/json',
        type: 'POST', 
        dataType: 'json',	      
		success: function(result){			
			
			if(result.sMessage == "Y"){
				fn_showCustomAlert("저장이 완료되었습니다.");
				fn_search(1)
			}else if(result.sMessage == "N"){
				fn_showCustomAlert("저장에 실패했습니다.");
		
			}else if(result.sMessage == "F"){
				fn_showCustomAlert("과제키가 존재하지 않습니다.");
			} 
		},
		error: function(request,status,error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
  });
}


//협약 연차 종료 과제  팝업 search 
//'${ctxt}/execute/reg/readProjRegList.do  기존꺼 가져 왔으니 업무에 맞게  다른것으로 설정
// 서버에서 받는 검색어 및 기타 검색 옵션은  모두  ex) searchword 처럼   변경   
function fn_popSearch(page){
	//현재 페이지 세팅
		popCuurPage= page;
	
		var params = {};
			params.page    = popCuurPage;   
		
			params.searchword    = $('#searchword_pop').val();     // 종료 과제명 
			params.dept_org = $('[name=dept_org_pop] :selected').val();     // 소관부처 
			params.ddct_org = $('[name=ddct_org_pop] :selected').val();  // 전담기관 

  	  	
    $.ajax({
        url: '${ctxt}/follow/end/planInfo/selectEndTargetList.do', 
        data: params,
        type: 'POST',
        dataType: 'json',
        success: function(result) {
        	var html ='';
        	popPagetotalCnt = 0;
        	if(!isEmpty(result)){
				if(!isEmpty(result.targetList)){
					var start_num = Number(result.targetTotal) - ((popCuurPage -1) *10)
					popPagetotalCnt =Number(result.targetPageTotal);	
					
					$.each(result.targetList, function(idx, item){
						html += '<tr>';
							html += '<td>' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="targetChkObj" title="선택하기" value='+item.proj_id+'>';		
							html += '<td>'+((isEmpty(item.dept_org)) ? '' : item.dept_org) +'</td>'; 
							html += '<td>'+((isEmpty(item.ddct_org)) ? '' : item.ddct_org) +'</td>';
// 							html += '<td class="text_l"><a href="javascript:fn_regDtl(\''+item.proj_id+'\');">'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</a></td>';
							html += '<td>'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</td>';
							html += '<td>'+((isEmpty(item.perform_strtdt)) ? '' : item.perform_strtdt+' ~ '+item.perform_enddt) +'</td>';	
							html += '<td>'+((isEmpty(item.max_proj_end_gb)) ? '' : item.max_proj_end_gb+' 년차') +'</td>';	
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택 
					//페이징처리
				    $('#popPaging').paging({
				    	
						 current:popCuurPage
						,max:popPagetotalCnt
						,length:pageLen
						,onclick:function(e,page){
							popCuurPage=page;
							fn_popSearch(popCuurPage);
						}
					});
					
				 	$('#popDataList').html(html);
				 	
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#popPaging').children().remove();
					
					$("#popDataList").html('<tr><td colspan="5" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{ 
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#popPaging').children().remove();
				
				$("#popDataList").html('<tr><td colspan="5" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        }
    });
}


// 연차과제 상세 상세 
function fn_endFollowDtl(endSeq){
	
// 	$(input[name='proj_end_id']).val(endSeq);
	$('input[name="proj_end_id"]').val(endSeq);
	var form = document.writeForm;
	form.action = '${ctxt}/follow/end/planInfo/endFollowDetail.do';
	form.submit();
	
}


function onClickEnter(){
	
	if(event.keyCode == 13){
		fn_search(1);
		
		return;
	}
}

</script>



<div id="container">
	<div id="divRefreshArea">
		<form name="writeForm" method="post" action="">
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>종료과제 수행 관리</h3>  
			<div class="searchAreaFlex">
				<div class="grid_box" >
				
					<!-- 소관부처 검색 -->
					<div class="custom-select selectRow">
						<label for="searchoption1" class="hidden-access"></label>
					<select id="searchoption1" name="searchoption1"> 
							<option value="">소관부처</option> 
					        <c:forEach var="dept" items="${deptList}">
					        	<option value="${dept.org_nm}" <c:if test="${followParam.searchoption1 == dept.org_nm}">selected="selected"</c:if>>${dept.org_nm}</option>
					        </c:forEach>
						</select>
					</div>
					
					<!-- 전담기관 검색 -->
					<div class="custom-select selectRow">
						<label for="searchoption2" class="hidden-access"></label>
					<select id="searchoption2" name="searchoption2"> 
							<option value="">전담기관</option> 
					        <c:forEach var="ddct" items="${ddctList}">
					        	<option value="${ddct.org_nm}" <c:if test="${followParam.searchoption2 == ddct.org_nm}">selected="selected"</c:if>>${ddct.org_nm}</option>
					        </c:forEach>
						</select>
					</div>
					 
					<!-- 연차과제 년도 검색 -->
					<div class="custom-select selectRow">
						<label for="searchoption3" class="hidden-access"></label>
						<select id="searchoption3" name="searchoption3">
							<option value="">과제시작년도</option>
						</select>
					</div>
					
					<!-- 신청과제명 검색 -->
					종료과제명 : <input type="text" name="searchword" id="searchword"  value="${followParam.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
					
					<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
				 	<input type="hidden" name="pageSize" value="10"/>
					<input type="hidden"  id="page" name="page" value="${followParam.page}" />					
					<input type="hidden"  id="proj_id" name="proj_id" value="" />				
					<input type="hidden"  id="proj_end_id" name="proj_end_id" value="" />				 
				</div>
				<div class="btn_wrap">	
					 <a href="javascript:void(0);" class="btn btn-secondary"  style="width : 150px;" onclick="javascript:showCustomPopup(1);">종료과제 생성 팝업</a>					
				</div>		
			</div>
		</form>
		<!--게시판-->
		<div class="admin ma_t_20"> 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable"> 
				<caption>
				 	  사후관리 -  번호, 소관기관 , 전담기관, 신청과제명, 단계구분, 종료연차구분, 수행기간
				</caption>
				<colgroup>
					<col style="width:5%">	
					<col style="width:12%">
					<col style="width:12%">						 	  
					<col style="width:*%">								
					<col style="width:8%">				
					<col style="width:20%">		
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>												
						<th scope='col'>소관기관</th>
						<th scope='col'>전담기관</th>
						<th scope='col'>종료과제명</th>																							
						<th scope='col'>종료연차구분</th>
						<th scope='col'>수행기간</th>
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="right">
<!-- 		  		<a href ="javascript:void(0);" onclick="javascript:fn_write();" class="btn btn-primary">등록</a> -->
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  

<input type="hidden" name=end_file_group id="end_file_group" value="">

 	<!-- 포인트 지급 div 생성존  -->
	<div id="sendAdPointZone">
	</div>
	
