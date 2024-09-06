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
        url: '${ctxt}/opsmng/respMng/readRespMngList.do',
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
				if(!isEmpty(result.respMngList)){
					var start_num = Number(result.respMngTotal) - ((cuurPage -1) *10)
					pagetotalCnt =Number(result.respMngPageTotal);	
					
					$.each(result.respMngList, function(idx, item){
						html += '<tr>';
							html += '<td class="text_c" ><input type="hidden" id = "resp_id'+idx+'" value="'+item.resp_id+'"><input type="checkbox" class="chkSel" name ="chk"></td>';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c"><input type="hidden" id = "resp_nm'+idx+'" value="'+((isEmpty(item.resp_nm)) ? '' : item.resp_nm)+'"><a href="javascript:fn_respMngDtl(\''+item.resp_id+'\');">'+ ((isEmpty(item.resp_nm)) ? '' : item.resp_nm)+'</a></td>';																				
							html += '<td class="text_c"><input type="hidden" id = "resp_org'+idx+'" value="'+((isEmpty(item.resp_org)) ? '' : item.resp_org)+'">'+((isEmpty(item.resp_org)) ? '' : item.resp_org) + '</td>';
							html += '<td class="text_c"><input type="hidden" id = "resp_dept'+idx+'" value="'+((isEmpty(item.resp_dept)) ? '' : item.resp_dept)+'">'+((isEmpty(item.resp_dept)) ? '' : item.resp_dept) + '</td>';
							html += '<td class="text_c"><input type="hidden" id = "resp_position'+idx+'" value="'+((isEmpty(item.resp_position)) ? '' : item.resp_position)+'">'+((isEmpty(item.resp_position)) ? '' : item.resp_position) + '</td>';
							html += '<td class="text_c"><input type="hidden" id = "resp_birth'+idx+'" value="'+((isEmpty(item.resp_birth)) ? '' : item.resp_birth)+'">'+((isEmpty(item.resp_birth)) ? '' : item.resp_birth)+'</td>';
							html += '<td class="text_l"><input type="hidden" id = "resp_mbtlnum'+idx+'" value="'+((isEmpty(item.resp_mbtlnum)) ? '' : item.resp_mbtlnum)+'">'+((isEmpty(item.resp_mbtlnum)) ? '' : item.resp_mbtlnum) + '</td>';
							html += '<td class="text_l"><input type="hidden" id = "resp_email'+idx+'" value="'+((isEmpty(item.resp_email)) ? '' : item.resp_email)+'">'+((isEmpty(item.resp_email)) ? '' : item.resp_email) + '</td>';																																			
																					
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
				 	
				 	 // 체크 박스 하나만 선택
					  $('.chkSel').change(function() {					 		  
				        $('.chkSel').prop('checked', false);
				        $(this).prop('checked', true);
				    });
				 	 
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


function onClickEnter(){
	
	if(event.keyCode == 13){
		fn_search(1);
		
		return;
	}
}


function fn_selResp(){
	
	
	var check = $("input[name='chk']:checked").length;

	if (check > 1) {
		fn_showCustomAlert('목록 한개를 선택하세요.');
		
		return false;
	}
	
	$( "input[name='chk']:checked" ).each (function (){
		
			var row = $(this).closest('tr');
			 
			var id_gbn1 = '${respMng.id_gbn1}';
			var id_gbn2 = '${respMng.id_gbn2}';
			var id_gbn3 = '${respMng.id_gbn3}';
			var id_gbn4 = '${respMng.id_gbn4}';
			var id_gbn5 = '${respMng.id_gbn5}';
			var id_gbn6 = '${respMng.id_gbn6}';
			var id_gbn7 = '${respMng.id_gbn7}';
		
			
		   $('#'+id_gbn1 ,opener.document).val( row.find('input[id^="resp_id"]').val());
		   $('#'+id_gbn2 ,opener.document).val( row.find('input[id^="resp_nm"]').val());
		   $('#'+id_gbn3 ,opener.document).val(	row.find('input[id^="resp_birth"]').val());
		   $('#'+id_gbn4 ,opener.document).val(	row.find('input[id^="resp_dept"]').val());
		   $('#'+id_gbn5 ,opener.document).val(	row.find('input[id^="resp_position"]').val());
		   $('#'+id_gbn6 ,opener.document).val(	row.find('input[id^="resp_email"]').val());
		   $('#'+id_gbn7 ,opener.document).val(	row.find('input[id^="resp_mbtlnum"]').val());

		   self.close();	  
	  });
	

} 

</script>


<div id="container">
	<div id="divRefreshArea">
		<form name="writeForm" method="post" action="">
			<h3 class="page_title">연구자 목록</h3>  
			<div class="grid_box" style="grid-template-columns: 100px 150px 50px; "> 
			
				
				<!-- 닉네임 검색 -->
				<div class="custom-select selectRow">  
					<label for="auth" class="hidden-access"></label>
					
					<select name="searchopt">					
						<option value="resp_nm" <c:if test="${respMng.searchopt == 'resp_nm'}">selected="selected"</c:if>>연구자명</option>
						<option value="resp_org" <c:if test="${respMng.searchopt == 'resp_org'}">selected="selected"</c:if>>소속기관</option>								
						<option value="resp_dept" <c:if test="${respMng.searchopt == 'resp_dept'}">selected="selected"</c:if>>소속부서</option>								
						<option value="resp_position" <c:if test="${respMng.searchopt == 'resp_position'}">selected="selected"</c:if>>직책</option>								
					</select>
				
				</div>
				<input type="text" name="searchword" id="searchword"  value="${respMng.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="" />
				<input type="hidden"  id="resp_id" name="resp_id" value="" />
			</div>
		</form>
		<!--게시판-->
		<div class="admin">
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
				<caption>
				  기관관리
				</caption>
				<colgroup>
					<col style="width:5%">
					<col style="width:5%">
					<col style="width:13%">								
					<col style="width:15%">
					<col style="width:10%">				
					<col style="width:10%">				
					<col style="width:10%">	
					<col style="width:10%">					
					<col style="*%">								
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>		
						<th scope='col'>번호</th>		
						<th scope='col'>연구자 명</th>								
						<th scope='col'>소속기관</th>
						<th scope='col'>소속부서</th>
						<th scope='col'>직책</th>
						<th scope='col'>생년월일</th>
						<th scope='col'>휴대전화</th>
						<th scope='col'>이메일</th>
					
				
					</tr>
				</thead> 
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="center">
		  		<a href="javascript:void(0);" onclick="javascript:fn_selResp();" class="btn btn-primary">연구자 선택 </a>		  		
		
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  
