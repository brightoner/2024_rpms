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
        url: '${ctxt}/opsmng/tclsMng/readTclsMngPopList.do',
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
				if(!isEmpty(result.tclsMngList)){
					var start_num = Number(result.tclsMngTotal) - ((cuurPage -1) *10)
					pagetotalCnt =Number(result.tclsMngPageTotal);	
					
					$.each(result.tclsMngList, function(idx, item){
						html += '<tr>';
							html += '<td class="text_c" ><input type="checkbox" class="chkSel" name ="chk"></td>';
							html += '<td class="text_c" >' + (start_num - idx) + '</td>';	
							html += '<td class="text_c" >'+((isEmpty(item.main_cate_id)) ? '' : item.main_cate_id) + '</td>';
							html += '<td class="text_c" >'+((isEmpty(item.main_cate_nm)) ? '' : item.main_cate_nm) + '</td>';	
							html += '<td class="text_c" >'+((isEmpty(item.mid_cate_id)) ? '' : item.mid_cate_id) + '</td>';
							html += '<td class="text_c" >'+((isEmpty(item.mid_cate_nm)) ? '' : item.mid_cate_nm) + '</td>';																																		
							html += '<td class="text_c" ><input type="hidden" id = "sub_cate_id'+idx+'" value="'+((isEmpty(item.sub_cate_id)) ? '' : item.sub_cate_id)+'">'+((isEmpty(item.sub_cate_id)) ? '' : item.sub_cate_id) + '</td>';
							html += '<td class="text_c" ><input type="hidden" id = "sub_cate_nm'+idx+'" value="'+((isEmpty(item.sub_cate_nm)) ? '' : item.sub_cate_nm)+'">'+((isEmpty(item.sub_cate_nm)) ? '' : item.sub_cate_nm) + '</td>';														
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

function fn_seltcls(){
	
	
	var check = $("input[name='chk']:checked").length;

	if (check > 1) {
		fn_showCustomAlert('목록 한개를 선택하세요.');
		
		return false;
	}
	
	$( "input[name='chk']:checked" ).each (function (){
		
		var row = $(this).closest('tr');
		 
		var id_gbn1 = '${tclsMng.id_gbn1}';
		var id_gbn2 = '${tclsMng.id_gbn2}';
		
	    $('#'+id_gbn1 ,opener.document).val(row.find('input[id^="sub_cate_id"]').val());
	    $('#'+id_gbn2 ,opener.document).val(row.find('input[id^="sub_cate_nm"]').val());
	    
	    self.close();	  
	  });
	

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
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>산업기술분류</h3>  
			<div class="grid_box" style="grid-template-columns: 100px 250px 50px; "> 
			
				
			
				<div class="custom-select selectRow">  
					<label for="auth" class="hidden-access"></label>
					
					<select name="searchopt">					
						<option value="cate_nm" <c:if test="${tclsMng.searchopt == 'cate_nm'}">selected="selected"</c:if>>분류명</option>
						<option value="cate_id" <c:if test="${tclsMng.searchopt == 'cate_id'}">selected="selected"</c:if>>코드</option>								
														
					</select>
				
				</div>
				<input type="text" name="searchword" id="searchword"  value="${tclsMng.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="" />
				<input type="hidden"  id="tcls_id" name="tcls_id" value="" />
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
					<col style="width:8%">
					<col style="width:10%">								
					<col style="width:15%">
					<col style="width:10%">				
					<col style="width:17%">				
					<col style="width:10%">				
					<col style="*%">								
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>		
						<th scope='col'>번호</th>		
						<th scope='col'>대분류코드</th>			
						<th scope='col'>대분류명</th>				
						<th scope='col'>중분류코드</th>
						<th scope='col'>중분류명</th>				
						<th scope='col'>소분류코드</th>
						<th scope='col'>소분류명</th>				
						
				
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="center">
		  		<a href="javascript:void(0);" onclick="javascript:fn_seltcls();" class="btn btn-primary">산업기술분류 선택 </a>		  		
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  
