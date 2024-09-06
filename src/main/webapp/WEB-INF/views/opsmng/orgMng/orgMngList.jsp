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

});

/*
* 페이징 처리 2 : 페이지 num를 조회조건으로 넘겨서 조회, 페이지 이동시 ajax 재호출
*/
function fn_search(page){
	//현재 페이지 세팅
		cuurPage= page;
	
		var params = {};
			params.page    = cuurPage;   
			params.searchopt    = $('[name=searchopt] :selected').val();   
			params.searchword    = $('#searchword').val();   
			params.searchopt2    = $('[name=searchopt2] :selected').val();   
			params.searchClass    = $('[name=searchClass] :selected').val();   
		
	  	$('#page').val(cuurPage);
  	  	
    $.ajax({
        url: '${ctxt}/opsmng/orgMng/readOrgMngList.do',
        data: params,
        type: 'POST',
        //formData로 data 보낼경우 false로 세팅,그외에는 밑에 text/xml데이터로 세
      //  contentType: "text/xml;charset=utf-8",
        //contentType: false,
        dataType: 'json',
	    //processData: false,
        success: function(result) {
        	html ='';
        	pagetotalCnt = 0;
        	if(!isEmpty(result)){
				if(!isEmpty(result.orgMngList)){
					var start_num = Number(result.orgMngTotal) - ((cuurPage -1) *10)
					pagetotalCnt =Number(result.orgMngPageTotal);	
					
					$.each(result.orgMngList, function(idx, item){
						html += '<tr>';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+((isEmpty(item.org_class_nm)) ? '' : item.org_class_nm)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.org_gb_nm)) ? '' : item.org_gb_nm)+'</td>';
							html += '<td class="text_c"><a href="javascript:fn_orgMngDtl(\''+item.org_id+'\');">'+ ((isEmpty(item.org_nm)) ? '' : item.org_nm)+'</a></td>';						
							html += '<td class="text_c">'+((isEmpty(item.org_crm_no)) ? '' : item.org_crm_no) + '</td>';
							html += '<td class="text_l">'+((isEmpty(item.org_address)) ? '' : item.org_address) + '</td>';														
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
					
					$("#dataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#dataList").html('<tr><td colspan="6" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        }
    });
}


function onClickEnter(){
	
	if(event.keyCode == 13){
		fn_search(1);
		
		return;
	}
}

function fn_orgMngDtl(seq){
	$('#org_id').val(seq);
	var form = document.writeForm;
	form.action = '${ctxt}/opsmng/orgMng/orgMngDetail.do';
	form.submit();	
}

function fn_write(){
	$('#org_id').val("");
	var form = document.writeForm;
	form.action = '${ctxt}/opsmng/orgMng/orgMngWrite.do';
	form.submit();	
}

</script>


<div id="container">
	<div id="divRefreshArea">
		<form name="writeForm" method="post" action="">
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span> 기관관리</h3>  
			<div class="grid_box" style="grid-template-columns: 100px 100px 100px 150px 50px; "> 
			
				<!-- 승인여부 -->
				
				<div class="custom-select selectRow">
					<label for="searchopt2" class="hidden-access"></label>
					<select name="searchopt2" id = "searchopt2">
					   <option value="" <c:if test="${orgMng.searchopt2 == ''}">selected="selected"</c:if>>기관구분</option>						
						<option value="1" <c:if test="${orgMng.searchopt2 == '1'}">selected="selected"</c:if>>소관부처</option>								
						<option value="2" <c:if test="${orgMng.searchopt2 == '2'}">selected="selected"</c:if>>전담기관</option>								
						<option value="3" <c:if test="${orgMng.searchopt2 == '3'}">selected="selected"</c:if>>일반기관</option>								
					</select>
				</div>
				<div class="custom-select selectRow">
					<label for="searchClass" class="hidden-access"></label>
				
						<select name="searchClass" id = "searchClass" class="select" title="조회조건 선택">
						   	<option value="" <c:if test="${orgMng.searchClass == ''}">selected="selected"</c:if>>기관분류</option>	
								<c:forEach items="${org_class_code}" var="gbn">
										
											<option value="${gbn.cd}" <c:if test="${orgMng.searchClass eq gbn.cd}">selected</c:if>>${gbn.cd_nm}</option>
									
								</c:forEach>
							</select>
				</div>
				<!-- 닉네임 검색 -->
				<div class="custom-select selectRow">  
					<label for="auth" class="hidden-access"></label>
					
					<select name="searchopt">					
						<option value="org_nm" <c:if test="${orgMng.searchopt == 'org_nm'}">selected="selected"</c:if>>기관명</option>								
					</select>
				
				</div>
				<input type="text" name="searchword" id="searchword"  value="${orgMng.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="${orgMng.page}" />
				<input type="hidden"  id="org_id" name="org_id" value="" />
			</div>
		</form>
		<!--게시판-->
		<div class="admin">
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
				<caption>
				  기관관리
				</caption>
				<colgroup>
					<col style="width:10%">
					<col style="width:8%">								
					<col style="width:5%">								
					<col style="width:20%">
					<col style="width:15%">				
					<col style="*%">								
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>		
						<th scope='col'>기관구분</th>				
						<th scope='col'>기관분류</th>
						<th scope='col'>기관명</th>			
						<th scope='col'>사업자 등록번호</th>
						<th scope='col'>주소</th>
				
					</tr>
				</thead> 
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="right">
		  		<a href="javascript:void(0);" onclick="javascript:fn_write();" class="btn btn-secondary">등록</a>
		  	
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  
