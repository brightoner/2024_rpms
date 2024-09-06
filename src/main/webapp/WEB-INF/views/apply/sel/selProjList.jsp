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


	// 년도 selectbox 처리
	var select = document.getElementById('searchoption3');
    var currentYear = new Date().getFullYear();
    var startYear = 2020;

    for (var year = currentYear; year >= startYear; year--) {
        var option = document.createElement('option');
        
        if("${selParam.searchoption3}" == year ){
        	option.selected = 	'selected';
        }
        option.value = year;
        option.textContent = year;
        select.appendChild(option);
    }
    
	
	fn_search(page);

});

/*
* 페이징 처리 2 : 페이지 num를 조회조건으로 넘겨서 조회, 페이지 이동시 ajax 재호출
*/
function fn_search(page){
	//현재 페이지 세팅
		cuurPage= page;
	
		var params = {};
			params.page    = cuurPage;   				
			
			params.searchoption1 = $('select[name=searchoption1] :selected').val();    
			params.searchoption2 = $('select[name=searchoption2] :selected').val(); 
			params.searchoption3 = $('select[name=searchoption3] :selected').val();
			params.searchoption4 = $('select[name=searchoption4] :selected').val();
			params.searchoption5 = $('select[name=searchoption5] :selected').val();
			
			params.searchword1 = $('#searchword1').val();
			params.searchword2 = $('#searchword2').val();
	  	$('#page').val(cuurPage);
  	  	
    $.ajax({
        url: '${ctxt}/apply/sel/readSelProjList.do',
        data: params,
        type: 'POST',
        dataType: 'json',
        success: function(result) {
        	html ='';
        	pagetotalCnt = 0;
        	if(!isEmpty(result)){
				if(!isEmpty(result.selList)){
					var start_num = Number(result.selTotal) - ((cuurPage -1) *10)
					pagetotalCnt =Number(result.selPageTotal);	
					
					$.each(result.selList, function(idx, item){
						html += '<tr>';
							html += '<td>' + (start_num - idx) + '</td>';
							html += '<td>'+((isEmpty(item.dept_org)) ? '' : item.dept_org) +'</td>';
							html += '<td>'+((isEmpty(item.ddct_org)) ? '' : item.ddct_org) +'</td>';
							html += '<td class="text_l"><a href="javascript:fn_selDtl(\''+item.ann_id+'\', \''+item.proj_id+'\');">'+((isEmpty(item.ann_nm)) ? '' : item.ann_nm) +'</a></td>';
							html += '<td class="text_l"><a href="javascript:fn_selDtl(\''+item.ann_id+'\', \''+item.proj_id+'\');">'+((isEmpty(item.proj_nm_kor)) ? '' : item.proj_nm_kor) +'</a></td>';
							html += '<td>'+((isEmpty(item.sel_dt)) ? '' : item.sel_dt) +'</td>';	
							html += '<td>'+((isEmpty(item.sel_gb_nm)) ? '' : item.sel_gb_nm) +'</td>';
							html += '<td>'+((isEmpty(item.agmt_gb_nm)) ? '' : item.agmt_gb_nm) +'</td>';
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
					
					$("#dataList").html('<tr><td colspan="10" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#dataList").html('<tr><td colspan="10" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        }
    });
}


// 공고현황 상세 
function fn_selDtl(annSeq, projSeq){
	
	$('#ann_id').val(annSeq);
	$('#proj_id').val(projSeq);
	var form = document.writeForm;
	form.action = '${ctxt}/apply/sel/selProjDetail.do';
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
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>선정결과 처리</h3>  
			
			<div class="grid_box">
			
				<!-- 소관부처 검색 -->
				<div class="custom-select selectRow">
					<label for="searchoption1" class="hidden-access"></label>
					<select id="searchoption1" name="searchoption1"> 
						<option value="">소관부처</option> 
				        <c:forEach var="dept" items="${deptList}">
				        	<option value="${dept.org_nm}" <c:if test="${selParam.searchoption1 == dept.org_nm}">selected="selected"</c:if>>${dept.org_nm}</option>
				        </c:forEach>
					</select>
				</div>
				
				<!-- 전담기관 검색 -->
				<div class="custom-select selectRow">
					<label for="searchoption2" class="hidden-access"></label>
					<select id="searchoption2" name="searchoption2"> 
						<option value="">전담기관</option> 
				        <c:forEach var="ddct" items="${ddctList}">
				        	<option value="${ddct.org_nm}" <c:if test="${selParam.searchoption2 == ddct.org_nm}">selected="selected"</c:if>>${ddct.org_nm}</option>
				        </c:forEach>
					</select>
				</div>
				 
				 <!-- 공고년도검색 -->
				<div class="custom-select selectRow">
					<label for="searchoption3" class="hidden-access"></label>
					<select id="searchoption3" name="searchoption3">
						<option value="">선정년도</option>
					</select>
				</div>
				
				<!-- 선정여부 -->
				<div class="custom-select selectRow">
					<label for="searchoption4" class="hidden-access"></label>
					<select id="searchoption4" name="searchoption4">
						<option value="" <c:if test="${selParam.searchoption4 == ''}">selected="selected"</c:if>>선정여부</option>
						<option value="1" <c:if test="${selParam.searchoption4 == '1'}">selected="selected"</c:if>>선정</option>
						<option value="2" <c:if test="${selParam.searchoption4 == '2'}">selected="selected"</c:if>>탈락</option>
					</select>
				</div>
				
				<!-- 협약여부 -->
				<div class="custom-select selectRow">
					<label for="searchoption5" class="hidden-access"></label>
					<select id="searchoption5" name="searchoption5">
						<option value="" <c:if test="${selParam.searchoption5 == ''}">selected="selected"</c:if>>협약여부</option>
						<option value="0" <c:if test="${selParam.searchoption5 == '0'}">selected="selected"</c:if>>미협약</option>
						<option value="1" <c:if test="${selParam.searchoption5 == '1'}">selected="selected"</c:if>>협약완료</option>
					</select>
				</div>
				
				<!-- 공고명 검색--> 
				공고명 : <input type="text" name="searchword1" id="searchword1"  value="${selParam.searchword1}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				
				<!-- 신청과제명 검색 -->
				신청과제명 : <input type="text" name="searchword2" id="searchword2"  value="${selParam.searchword2}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="${selParam.page}" />				
				<input type="hidden"  id="ann_id" name="ann_id" value="" />				
				<input type="hidden"  id="proj_id" name="proj_id" value="" />				
			</div>
		</form>
		<!--게시판-->
		<div class="admin ma_t_20"> 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable"> 
				<caption>
				 	 연구과제 신청 -  번호, 소관기관 , 전담기관, 공고명, 신청과제명, 접수일, 선정여부, 협약여부
				</caption>
				<colgroup>
					<col style="width:5%">	
					<col style="width:11%">
					<col style="width:12%">						 	
					<col style="width:23%">								
					<col style="width:*">				
					<col style="width:10%">		
					<col style="width:6%">		
					<col style="width:6%">		
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>												
						<th scope='col'>소관기관</th>
						<th scope='col'>전담기관</th>
						<th scope='col'>공고명</th>
						<th scope='col'>신청과제명</th>																							
						<th scope='col'>선정일</th>
						<th scope='col'>선정여부</th>
						<th scope='col'>협약여부</th>
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