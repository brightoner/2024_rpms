<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
	<meta charset="utf-8">
	<title>유사도검색</title>
<script type="text/javascript">
var cuurPage;

	$(function(){

	    var page =$('#page').val();
		if(page == '' ){
			page=1;
			$('#page').val(1);
		}
	  
		fn_search(1);
		
	});

	/* 검색  */
	function fn_search(page){
		
		cuurPage = page;
		$('#page').val(cuurPage);
		$('#dataList').children().remove();
		$('.loading').show();
		$.ajax({
			url : "${ctxt}/mng/user/selectAcessUser.do",
			type : 'get',
			data : $("form[name=periodForm]").serialize(),
			contentType : "text/xml;charset=utf-8",
			dataType : "text",
			success : function(rtnXml) {
				pagetotalCnt=0;
				var xmlObj = $(rtnXml).find('item');
				var html = "";
				if(xmlObj.length > 0){
					var start_num = ((cuurPage -1) * 20) +1
					pagetotalCnt = Number($(rtnXml).find('totalCnt').text());
					$('#data_cnt').text(Number($(rtnXml).find('totalDataCnt').text()));
					
					xmlObj.each(function(cnt) {
								html += '<tr>';
								html += ' <td class="center no">' + (start_num + cnt) + '</td>';
								html += ' <td class="center" >' + $(this).find('emplyrkey').text() +'</td>';
								html += ' <td class="center" >' + $(this).find('loginid').text() + '</td>';
								html += ' <td class="center" >' + $(this).find('nicknm').text() + '</td>';
								html += ' <td class="center" >' + $(this).find('login_ip').text() + '</td>';
								html += ' <td class="center" >' + $(this).find('login_date_al').text() + '</td>';
								html += '</tr>';
								});
						$('#dataList').html(html);
						//트리코드 선택시 첫번째 행 선택
						$('#dataList tr:first').attr('class', 'on');
						
						//페이징처리
					    $('#paging').paging({
							 current:cuurPage,max:pagetotalCnt,length:pageLen
							 ,onclick:function(e,page){cuurPage=page; fn_search(cuurPage);}
						});
						
						
						fn_rowOn('example');
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$("#dataList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
					
					$('#data_cnt').text(0);
				}

			},
			error : function() {
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.', 'e');
			},
			complete: function(data,status){
				$('.loading').hide();
			}
		});
	}
	
	function fn_download(){
		periodForm.action="${ctxt}/mng/user/downloadAcessUser.do"
		periodForm.submit();
	}
	
</script>
</head>

<!-- 샘플 영역 -->
<div class="test">
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>사용자접속이력</h3>  
	<!-- 검색 박스 영역 -->
		<form id="periodForm" name="periodForm" method="post" style="text-align:center;">
		<input type="hidden" id="page" name="page" value="" /> 
		<div class="search_box relative mng">
			<div class="flex_box">
				<div></div>
				<div class="picker_box clear relative">
					<p class="relative">
						<span class="btn_datepicker picker_l"></span>  
						<input class="datepicker picker_l" type="text" name="sdate" id="sdate" value="${sdate}" maxlength="10" />
					</p>
					<span class="absolute_50 line_h_30 ma_l_2 p_color">~</span>
					<p class="relative">
						<span class="btn_datepicker picker_l"></span>
						<input class="datepicker picker_l" type="text" name="edate" id="edate" value="${edate}" maxlength="10" />
					</p>
				</div>
				<div class="text_l relative">
			    	<button id="btnSearch" type="button" class="btnSearch" onclick="fn_search(1);">검색</button>
				</div>
			</div>
		</div>
		</form>
	 <!-- 검색 필터 영역 -->
	 <!-- 데이터 영역 -->   
	 	<br />
	 	<div class="data_cnt_line">
			[총  : <span id="data_cnt" >0</span>건] 
		</div>
		<table id="example" class="table_h">        
        	<colgroup>
           	   <col width="5%" />
           	   <col width="15%" />
               <col width="20%" />
               <col width="20%" />
               <col width="20%" />
               <col width="20%" />
            </colgroup> 
            <thead>    
             <tr>            
         		<th>No</th>    
         		<th>사용자키</th>
         		<th>사용자ID</th>
         		<th>사용자명</th>
         		<th>접속IP</th>
	            <th>접속일시</th>    
        	  </tr>        
       	    </thead>
	       	<tbody id="dataList">       
	        </tbody>
     	</table>	
	 	<!-- 페이징 처리 -->
		<div id="paging" class="paginate"></div>
		<div align="right">
			<input type="button" onclick="fn_download()" name="save_btn" value="다운로드" />
		</div>
</div>