<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<style type="text/css">
#paging{text-align:center;}
a.paging-item,a.paging-side{margin:0 .25em;}
a.paging-item.selected{font-weight:bold;}
</style>
</head>

<script type="text/javascript">
var xmlList;
var html="";
var pagetotalCnt=0;
var datatotalCnt=0;

$(function(){
	
    var page =$('#page').val();   
	if(page == '' ){
		page=1;
		$('#page').val(1);   
	}
	
	fn_search(page);
});

	function fn_userDel(id,uid){
		if (confirm(id + " 회원탈퇴를 하시겠습니까?")) {
			$.ajax({
				url : '${ctxt}/mng/user/deleteUserMng.do',  
				data : {"ui_idno":uid},
				type : 'POST',
				cache: false,
				success : function(result) {
					fn_showCustomAlert(id+" 회원탈퇴가 처리 되었습니다.");
					fn_search(cuurPage);
				},
				error : function() { // Ajax 전송 에러 발생시 실행
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행
					
				}
			});
		}  
	}
	
	function fn_userpop(user_id){
		$('#user_id').val(user_id);
		
	    var form = document.userForm;
	    var url = "${ctxt}/mng/user/Userdetail.do";
	    
	    window.open("","userForm","width=800 height=550 scrollbars=yes menubar=no location=no") ;    
	    
	    form.action =url; 
		form.method="post";  
		form.target="userForm";
		form.submit();	
	}
	
	

	function fn_search(page){
		//현재 페이지 세팅
		cuurPage= page;
		
		/***************************************************************
		* 화면별로 따로 세팅 조회조건
		****************************************************************/
		$('#page').val(cuurPage);
	  	
		var params = $("#searchForm").serialize();

	    $.ajax({
	        url: '${ctxt}/mng/user/selectUserMng.do',
	        data: params,
	        type: 'POST',
	        dataType: 'text',
	        success: function(rtnXml) {
	        	xmlList = $(rtnXml).find('item');
	        
	        	pagetotalCnt=0;
	        	datatotalCnt=0;
	        	
	        	html="";
				if(xmlList.length > 0){
				
					//총페이지수
					pagetotalCnt = Number($(rtnXml).find('totalCnt').text());
					dataTotalCnt = Number($(rtnXml).find('totalDataCnt').text());
					
					xmlList.each(function(cnt){
				
						var obj = $(this);
						/***************************************************************
						* 화면별로 따로 세팅 테이블 데이터
						****************************************************************/
						html += '<tr class="off" style="cursor: pointer;">';
						html += ' <td class="center">'+obj.find('data_seq').text().replace('.0','') +'</td>';
						html += ' <td><strong><a href="javascript:fn_userpop(\''+ obj.find('userid').text() + '\')" >'+obj.find('userid').text() +'</a></strong></td>';
						html += ' <td class="center">'+obj.find('ui_name').text() +'</td>';
						html += ' <td  class="center">'+obj.find('ui_hp1').text() +'</td>';
						 html += ' <td>'+obj.find('insttnm_type').text() +'</td>';
						html += ' <td>'+obj.find('insttnm').text() +'</td>'; 
					//	html += ' <td>'+obj.find('ui_tele').text() +'</td>';
					//	html += ' <td>'+obj.find('ui_email').text() +'</td>';
						html += ' <td class="center">'+obj.find('ui_date_al').text() +'</td>';
						/* html += ' <td class="center"><input type="button" value="탈퇴" onclick="fn_userDel(\''+obj.find('userid').text()+'\',\''+obj.find('ui_idno').text()+'\');"  /></td>'; */
						html += ' <td style="display:none;" >'+obj.find('ui_idno').text() +'</td>';
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
					
				    $("#dataList").html(html);
				    $('#data_cnt').text(Number(dataTotalCnt));
				    
				    fn_rowOn('dataList');
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#paging').children().remove();
				    $('#data_cnt').text(0);
					$("#dataList").html('<tr><td colspan="11" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
				}
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
	}
	
</script>

<form  action="" id="userForm" name ="userForm"  method="post"  >
	<input type="hidden" id="user_id" name="user_id" value="" alt="파라미터">
</form>

<form name="loginform" id="loginform" action="del_action.jsp" method="post" >
	<input type="hidden" id="ui_idno" name="ui_idno" value="" />
</form>

	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>사용자관리</h3>  
	<div class="" style="">
		<form name="searchForm" id="searchForm">
		<div class="search_box relative mng">
		 	<input type="hidden" name="pageSize" value="10"/>
			<label for="agtype">기관명</label>
			<div class="custom-select">
				<select id="agtype_cd" name="agtype_cd"> 
					<option value="">기관유형</option>
					<c:forEach items="${AGTYPE}" var="item">
						<option value="${item.cd}">${item.cd_nm}</option>
					</c:forEach>
				</select>
			</div>
			<div class="custom-select">
				<select id="search_gbn" name="search_gbn" style="width: 100px;">
					<option value="all" <c:if test="${PTLLoginVo.searchStatus == ''}" >selected</c:if>  >전체</option>
					<option value="nm" <c:if test="${PTLLoginVo.searchStatus == 'name'}" >selected</c:if>  >사용자명</option>
					<option value="id" <c:if test="${PTLLoginVo.searchStatus == 'id'}" >selected</c:if> >사용자ID</option>
					<option value="tel" <c:if test="${PTLLoginVo.searchStatus == 'sn'}" >selected</c:if> >휴대폰번호</option>
				</select>
			</div>
			<input type="text" name="searchword" id="searchWord"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
			<a href ="javascript:void(0);" id="btnSearch" onclick="javascript:fn_search(1);" class="btn btn-primary">검색</a>
			<input type="hidden"  id="page" name="page" value="" />
			<input type="hidden"  id="board_seq" name="board_seq" value="10" />
		</div>
		</form>
		<br />
		<div style="">
			[총  : <span id="data_cnt" >0</span>건 ]
			<table id="example" class="table_h" style="margin-top:0;">        
	        	<colgroup>
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="15%" />
					<col width="15%" />   
					<col width="*" />  
					<col width="15%" />
					<%-- <col width="10%" />   --%>
					<%-- <col width="10%" />   --%>
					<%-- <col width="20%" />   --%>
	            </colgroup> 
	            <thead>       
	             <tr>
					<th>번호</th>
					<th>ID</th>  
					<th>이름</th>
					<th>휴대폰번호</th>
					<th>기관유형</th> 
					<th>소속기관명</th>
					<!-- <th>기관전화번호</th> -->
					<!-- <th>이메일</th>	 -->			
					<th>가입일</th>  
	        	  </tr>        
	       	    </thead>
		       	<tbody id="dataList">       
		        </tbody>
	     	</table>	
     	</div>
	 	<!-- 페이징 처리 -->
		<div id="paging" class="paginate"></div>
		
	</div>      
	<div class="clear"></div>
	