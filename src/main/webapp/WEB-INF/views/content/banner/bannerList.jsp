<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<style type="text/css">
#paging{text-align:center;}
a.paging-item,a.paging-side{margin:0 .25em;}
a.paging-item.selected{font-weight:bold;}
</style>
		


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
        url: '${ctxt}/content/banner/readBannerList.do',
        data: params,
        type: 'POST',
        //formData로 data 보낼경우 false로 세팅,그외에는 밑에 text/xml데이터로 세
      //  contentType: "text/xml;charset=utf-8",
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
					
					var banner_seq = obj.find('banner_seq').text();
					var banner_title = obj.find('banner_title').text(); 
					var atch_img_id  = obj.find('atch_img_id').text();
					var img_yn  = obj.find('img_yn').text();
					var create_dttm_al  = obj.find('create_dttm_al').text();
					var edit_date_al  = obj.find('edit_date_al').text();
					var use_yn  = obj.find('use_yn').text();
					
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터
					****************************************************************/
					html += '<tr>';
					html += ' <td>' + (start_num - cnt) + '</td>';
					
					html += ' <td class="text_l">';
					if(img_yn == 'Y'){
						html += '<img src="${ctxt}/cmm/fms/getImage.do?atch_img_id='+atch_img_id+'&fileSn=0" alt="'+banner_title+'로고 이미지" title="'+banner_title+'" style="width:100px; height:auto; border:1px solid #ddd; vertical-align:middle;">';
					}
					html += '</td>';
					
					html += ' <td class="text_l"><a href="javascript:fn_bannerdtl('+banner_seq+');">'+banner_title +'</a></td>';
					html += ' <td>'+create_dttm_al+'</td>';
					html += ' <td>'+edit_date_al+'</td>';
					html += ' <td>'+use_yn+'</td>';
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

function fn_bannerdtl(seq){
	$('#banner_seq').val(seq);
	var form = document.writeForm;
	form.action = '${ctxt}/content/banner/bannerDetail.do';
	form.submit();	
}
function fn_write(){
	$('#banner_seq').val("");
	var form = document.writeForm;
	form.action = '${ctxt}/content/banner/bannerWrite.do';
	form.submit();	
}

</script>




<div id="container">
	<div id="divRefreshArea">
		<form name="writeForm" method="post" action="">
			<h3 class="page_title" id="title_div"><span class="adminIcon"></span>배너 관리</h3>  
			<div class="grid_box">   
			 	<input type="hidden" name="pageSize" value="10"/>
				<div class="custom-select selectRow">  
					<select name="searchopt" >					
						<option value="pu_subject" <c:if test="${banner.searchopt == 'pu_subject'}">selected="selected"</c:if>>제목</option>
					<%-- 	<option value="pu_contents" <c:if test="${banner.searchopt == 'pu_contents'}">selected="selected"</c:if>>내용</option> --%>
					</select>
				</div>
				<input type="text" name="searchword" id="searchword"  value="${banner.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				<a href ="javascript:void(0);" id="btnSearch" onclick="javascript:fn_search(1);" class="btn btn-primary">검색</a>
				<input type="hidden"  id="page" name="page" value="" />
				<input type="hidden"  id="banner_seq" name="banner_seq" value="" />
			</div>
		</form>
		<!--게시판-->
		<div class="admin">
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable">
				<caption>
				 	 배너 -  번호, 배너이미지, 제목 
				</caption>
				<colgroup>
					<col style="width:10%">				
					<col style="width:10%">
					<col style="*">			
					<col style="width:15%">
					<col style="width:15%">	
					<col style="width:10%">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>
						<th scope='col'>배너이미지</th>
						<th scope='col'>제목</th>			
						<th scope='col'>등록일</th>
						<th scope='col'>수정일</th>
						<th scope='col'>사용여부</th>										
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
			<!-- 페이징 처리 -->
			<div id="paging" class="paginate"></div>
			<!-- 버튼 -->
			<div align="right">
		  		<a href ="javascript:void(0);" onclick="javascript:fn_write();" class="btn btn-primary">등록</a>
			</div>
			<!--//버튼 -->
		</div>
	</div>
</div>  
