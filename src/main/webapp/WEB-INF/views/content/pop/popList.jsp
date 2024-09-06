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
        url: '${ctxt}/content/pop/readPopList.do',
        data: params,
        type: 'GET',
        //formData로 data 보낼경우 false로 세팅,그외에는 밑에 text/xml데이터로 세
        contentType: "text/xml;charset=utf-8",
        //contentType: false,
        dataType: 'text',
	    //processData: false,
        success: function(rtnXml) {
   
        	xmlList = $(rtnXml).find('item');

        	pagetotalCnt=0;
        	html="";
			if(xmlList.length > 0){
				//총페이지수
				var start_num = Number($(rtnXml).find('totalDataCnt').text()) - ((cuurPage -1) * 10)
					pagetotalCnt = Number($(rtnXml).find('totalCnt').text());
				
				xmlList.each(function(cnt){
			
					var obj = $(this);
					
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터
					****************************************************************/
					html += '<tr>';
					html += ' <td>' + (start_num - cnt) + '</td>';
				/*
					if(obj.find('pop_type').text() == 'M'){
						html += ' <td>레이어팝업</td>';
					}else{
						html += ' <td>일반팝업</td>';
					}
				*/
					html += ' <td class = "text_l"><a href="javascript:fn_popdtl('+obj.find('pop_seq').text()+');">'+obj.find('pop_title').text() +'</a></td>';
					if(obj.find('pop_target').text() == 'L'){
						html += ' <td>로그인시</td>';
					}else{
						html += ' <td>접속시</td>';
					}
					html += ' <td>'+obj.find('start_date').text() + " ~ "+obj.find('end_date').text() +'</td>';
				//	html += ' <td>'+obj.find('create_dttm_al').text() +'</td>';
				//	html += ' <td>'+obj.find('edit_date_al').text() +'</td>';
					html += '</tr>';
					
		        });				
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
				
				fn_rowOn('dataList');
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#dataList").html('<tr><td colspan="4" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
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

function fn_popdtl(seq){
	$('#pop_seq').val(seq);
	var form = document.writeForm;
	form.action = '${ctxt}/content/pop/popDetail.do';
	form.submit();	
}
function fn_write(){
	$('#pop_seq').val("");
	var form = document.writeForm;
	form.action = '${ctxt}/content/pop/popWrite.do';
	form.submit();	
}

</script>




<div id="container">
	<div id="divRefreshArea">
		<form name="writeForm" method="post" action="popup_write.jsp">
				<h3 class="page_title" id="title_div"><span class="adminIcon"></span>팝업관리</h3> 
			<div class="grid_box">   
			 	<input type="hidden" name="pageSize" value="10"/>
				<div class="custom-select selectRow">  
					<select name="searchopt" >					
						<option value="pu_subject" <c:if test="${popup.searchopt == 'pu_subject'}">selected="selected"</c:if>>제목</option>
						<option value="pu_contents" <c:if test="${popup.searchopt == 'pu_contents'}">selected="selected"</c:if>>내용</option>
					</select>
				</div>
				<input type="text" name="searchword" id="searchword"  value="${popup.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				<a href ="javascript:void(0);" id="btnSearch" onclick="javascript:fn_search(1);" class="btn btn-primary">검색 </a>

				<input type="hidden"  id="page" name="page" value="" />
				<input type="hidden"  id="pop_seq" name="pop_seq" value="" />
			</div>
		</form>
		<!--게시판-->
		<div class="admin">
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
				<caption>
				 	 팝업 -  번호, 제목, 첨부파일, 등록일, 조회수 
				</caption>
				<colgroup>
					<col style="width:10%">				
					<%-- <col style="width:10%"> --%>
					<col style="*">
					<col style="width:15%">
					<col style="width:30%">
				<%-- 	<col style="width:10%"> --%>
					<%-- <col style="width:10%"> --%>
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>
						<!-- <th scope='col'>형태</th> -->
						<th scope='col'>제목</th>
						<th scope='col'>시점</th>
						<th scope='col'>게시일</th>
					<!-- 	<th scope='col'>등록일</th> -->
						<!-- <th scope='col'>수정일</th> -->
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="right">
		  		<a href ="javascript:void(0);" onclick="javascript:fn_write();" class="btn btn-secondary">등록</a>
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  
