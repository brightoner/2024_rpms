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
$(function(){
	
    var page =$('#page').val();
	if(page == '' ){
		page=1;
	}

	
	fn_search(page);

	// 년도 selectbox 처리
	var select = document.getElementById('proj_year');
    var currentYear = new Date().getFullYear();
    var startYear = 2020;	// 시작년도 설정

    for (var year = currentYear; year >= startYear; year--) {
        var option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        select.appendChild(option);
    }
});

/*
* 페이징 처리 2 : 페이지 num를 조회조건으로 넘겨서 조회, 페이지 이동시 ajax 재호출
*/
function fn_search(page){
	//현재 페이지 세팅
		cuurPage= page;
	
		var params = {};
			params.page    		= cuurPage;   
			params.searchopt	= $('[name=searchopt] :selected').val();   
			params.searchword   = $('#searchword').val();   
		
			
			params.dept_org 	= $('[name=dept_org] :selected').val();    
			params.ddct_org 	= $('[name=ddct_org] :selected').val(); 
			params.proj_year 	= $('[name=year] :selected').val();
	
			
	  	$('#page').val(cuurPage);
  	  	
    $.ajax({
        url: '${ctxt}/execute/plan/readProjPlanList.do',
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
							html += '<td class="text_l"><a href="javascript:fn_planDtl(\''+item.proj_year_id+'\');">'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</a></td>';
							html += '<td>'+((isEmpty(item.proj_step_gb)) ? '' : item.proj_step_gb + '단계') +'</td>';
							html += '<td>'+((isEmpty(item.proj_year_gb)) ? '' : item.proj_year_gb + '년차') +'</td>';
							html += '<td>'+((isEmpty(item.cur_strtdt)) ? '' : item.cur_strtdt + ' ~ ' + item.cur_enddt) +'</td>';	
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


// 연차과제 상세 상세 
function fn_planDtl(seq){
	
	$('#proj_year_id').val(seq);
	var form = document.writeForm;
	form.action = '${ctxt}/execute/plan/projPlanDetail.do';
	form.submit();
	
}


function onClickEnter(){
	
	if(event.keyCode == 13){
		fn_search(1);
		
		return;
	}
}

</script>
<style>
  .popHidden {
    display: none;
  }
  
  #mailTempPopup {
    position: absolute;
    background-color: white;
    border: 1px solid #ccc;
    padding: 10px;
    z-index: 9999;
  }
</style>
   
 
<div id="container">
	<div id="divRefreshArea">
		<form name="writeForm" method="post" action="">
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>과제 수행 관리</h3>  
			
			<div class="grid_box">
			
				<!-- 소관부처 검색 -->
				<div class="custom-select selectRow">
					<label for="dept_org" class="hidden-access"></label>
					<select id="dept_org" name="dept_org"> 
						<option value="">소관부처</option> 
				        <c:forEach var="dept" items="${deptList}">
				        	<option value="${dept.org_nm}">${dept.org_nm}</option>
				        </c:forEach>
					</select>
				</div>
				
				<!-- 전담기관 검색 -->
				<div class="custom-select selectRow">
					<label for="ddct_org" class="hidden-access"></label>
					<select id="ddct_org" name="ddct_org"> 
						<option value="">전담기관</option> 
				        <c:forEach var="ddct" items="${ddctList}">
				        	<option value="${ddct.org_nm}">${ddct.org_nm}</option>
				        </c:forEach>
					</select>
				</div>
				 
				 <!-- 연차과제 년도 검색 -->
				<div class="custom-select selectRow">
					<label for="proj_year" class="hidden-access"></label>
					<select id="proj_year" name="year">
						<option value="">과제년도</option>
					</select>
				</div>
				
				<!-- 연차과제명 검색 -->
				연차과제명 : <input type="text" name="searchword" id="searchword"  value="${dataParam.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="${dataParam.page}" />				
				<input type="hidden"  id="proj_id" name="proj_id" value="" />				
				<input type="hidden"  id="proj_year_id" name="proj_year_id" value="" />				 
			</div>
		</form>
		<!--게시판-->
		<div class="admin ma_t_20"> 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable"> 
				<caption>
				 	  과제수행관리 -  번호, 소관기관 , 전담기관, 신청과제명, 단계구분, 연차구분, 수행기간
				</caption>
				<colgroup>
					<col style="width:5%">	
					<col style="width:12%">
					<col style="width:12%">						 	
					<col style="width:40%">								
					<col style="width:8%">				
					<col style="width:8%">		
					<col style="width:15%">		
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>												
						<th scope='col'>소관기관</th>
						<th scope='col'>전담기관</th>
						<th scope='col'>연차과제명</th>																							
						<th scope='col'>단계구분</th>
						<th scope='col'>연차구분</th>
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


 	<!-- 포인트 지급 div 생성존  -->
	<div id="sendAdPointZone">
	</div>