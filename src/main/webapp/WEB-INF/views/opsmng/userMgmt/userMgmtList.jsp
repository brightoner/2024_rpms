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
	  	$('#page').val(cuurPage);
  	  	
    $.ajax({
        url: '${ctxt}/opsmng/userMgmt/readUserMgmtList.do',
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
				if(!isEmpty(result.userList)){
					var start_num = Number(result.userTotal) - ((cuurPage -1) *10)
					pagetotalCnt =Number(result.userPageTotal);	
					
					$.each(result.userList, function(idx, item){
						html += '<tr>'; 
							html += '<td>' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="chkObj" title="선택하기'+idx+'" value='+item.emplyrkey+'>'+'<input type="hidden" name='+item.emplyrkey+' id='+item.emplyrkey+' value='+item.emplyrkey+'>'+'</td>';						
							html += '<td class="text_c">'+((isEmpty(item.user_id)) ? '' : item.user_id) + '</td>';
							html += '<td class="text_c">'+((isEmpty(item.nicknm)) ? '' : item.nicknm) + '</td>';							
							html += '<td class="text_c">'+((isEmpty(item.orgnm)) ? '' : item.orgnm) + '</td>';
							html += '<td class="text_c">'+((isEmpty(item.deptnm)) ? '' : item.deptnm) + '</td>';
							html += '<td class="text_c">'+((isEmpty(item.posnm)) ? '' : item.posnm) + '</td>';
							html += '<td class="text_l">'+((isEmpty(item.email)) ? '' : item.email) + '</td>';					
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
					
					$("#dataList").html('<tr><td colspan="8" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#dataList").html('<tr><td colspan="8" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
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
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>사용자 목록</h3>  
			
			<div class="grid_box">
			
			
				<!-- 닉네임 검색 --> 
				<div class="custom-select selectRow">  
					<label for="auth" class="hidden-access"></label>
					<select name="searchopt">					
						<option value="id" <c:if test="${userMgmtParam.searchopt == 'id'}">selected="selected"</c:if>>ID</option>
						<option value="nickNm" <c:if test="${userMgmtParam.searchopt == 'nickNm'}">selected="selected"</c:if>>이름</option>														
					</select>
				</div>
				<input type="text" name="searchword" id="searchword"  value="${userMgmtParam.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="" />				
			</div>
		</form>
		<!--게시판-->
		<div class="admin ma_t_20"> 
					
					<div class="clear"></div>
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
				<caption>
				 	 bj승인신청 관리 -  이름, 승인여부 , 승인신청일, 승인/변려일 
				</caption>
				<colgroup>
					<col style="width:5%">		
					<col style="width:5%">	
					<col style="width:13%">
					<col style="width:13%">						 	
					<col style="width:15%">								
					<col style="width:15%">				
		 			<col style="width:8%">	
					<col style="width:*%">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>
						<th scope='col'>번호</th>												
						<th scope='col'>ID</th>
						<th scope='col'>이름</th>				
						<th scope='col'>소속기관</th>																							
						<th scope='col'>소속부서</th>
						<th scope='col'>직책</th>		
						<th scope='col'>이메일</th>				
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="right">
		  			
		  				<a href="javascript:void(0);" onclick="javascript:popup('${pageContext.request.contextPath}/login/regi/writeMember.do','writeMem',1000,900,1);" class="btn btn-secondary">회원가입</a>
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  

