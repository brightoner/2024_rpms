<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>

<script type="text/javascript">
var cuurPage;
var lastPage;
var pagetotalCnt = 0;
$(function(){
	
	// 캘린더 - 등록일
 	$(function() {
		$( "#str_dt" ).datepicker({
		showOn: "button",
		buttonImage: "${ctxt}/resources/img/common/ico_dal.png",
		buttonImageOnly: true,
		changeYear: true,
		changeMonth: true,
		buttonText: "Select date",
		dateFormat: 'yy-mm-dd'
		});
	}); 

	// 캘린더 - 졸업 예정일자
	$(function() {
		$( "#end_dt" ).datepicker({
		showOn: "button",
		buttonImage: "${ctxt}/resources/img/common/ico_dal.png",
		buttonImageOnly: true,
		changeYear: true,
		changeMonth: true,
		buttonText: "Select date",
		dateFormat: 'yy-mm-dd'
		});
	}); 
	
    var page =$('#page').val();
	if(page == '' ){
		page=1;
	}

	fn_search(page);
	
});

function fn_save(){
		if(confirm("저장하시겠습니까?")){
		 	var formData = $("#authForm").serialize();
			$.ajax({
				url : '${ctxt}/mng/user/insertAuth.do',
				data : formData,
				type : 'POST',
				cache: false,
				success : function(result) {

					fn_showCustomAlert("저장을 완료하였습니다.");
					
				},
				error : function() { // Ajax 전송 에러 발생시 실행
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				}
//	 			,
//	 			complete : function(result) { //  success, error 실행 후 최종적으로 실행
//	 				fn_showCustomAlert(resultText);
//	 			}
			});
		}
}


function fn_keySearch(){
    
	if(event.keyCode == 13){
		fn_search(1);
	}
}


/*
* 페이징 처리 2 : 페이지 num를 조회조건으로 넘겨서 조회, 페이지 이동시 ajax 재호출
*/
function fn_search(page){
	$('#userList .on').attr('class','off');
	$('#sel_user').text("");
	$('input:checkbox[name="auth"]').prop("checked",false);
	//현재 페이지 세팅
	cuurPage= page;
	
	/***************************************************************
	* 화면별로 따로 세팅 조회조건
	****************************************************************/
	$('#page').val(cuurPage);
  	
	var params = {};
	$('#searchStatus').val($('#status option:selected').val());
	$('#usrAuth').val($('#auth option:selected').val());

	params.searchStatus = $('#status option:selected').val();
	params.searchText = $('#search_input').val();
	params.usrAuth = $('#auth option:selected').val();
    params.page    = cuurPage;     

    $.ajax({
        url: '${ctxt}/mng/user/selectUserList.do',
        data: params,
        type: 'POST',
        dataType: 'text',
        success: function(rtnXml) {
        	xmlList = $(rtnXml).find('item');
        	pagetotalCnt=0;
        	html="";
			if(xmlList.length > 0){
			
				//총페이지수
				pagetotalCnt = Number($(rtnXml).find('totalCnt').text());
				lastPage= pagetotalCnt;
				xmlList.each(function(cnt){
			
					var obj = $(this);
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터
					****************************************************************/
					html += '<tr onclick="fn_selectAuth(\''+obj.find('loginid').text()+'\',this);" class="off" style="cursor: pointer;">';
					html += ' <td class="text_c">'+obj.find('emplyrkey').text() +'</td>';
					html += ' <td class="text_c" >'+obj.find('loginid').text() +'</td>';
					html += ' <td class="text_c">'+obj.find('nicknm').text() +'</td>';
					/* html += ' <td class="text_c"><input type="button" class="line_h_10 h_25" value="사용자변경" onclick="fn_chgUser(\''+obj.find('loginid').text()+'\',this);"  /></td>'; */
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
				
			    $("#userList").html(html);
			    
				<c:if test="${if_yn == 'Y'}"> 
					parent.calcHeight();
				</c:if>
			    
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				$('#paging').children().remove();
				
				$("#userList").html('<tr><td colspan="7" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
        },
        error : function(){                              // Ajax 전송 에러 발생시 실행
        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
        }
    });
}

function fn_chgUser(user){
	
	var form = document.chgForm;
	form.loginid.value=user;
	form.method="POST";
	form.action="${ctxt}/mng/user/chgUser.do";
	form.submit();
	
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



function fn_selectAuth(param_sn,obj){
	
	$('#userList .rowOn').attr('class','off'); 
	$('#sel_user').text("선택 사용자 : "+param_sn);
	$(obj).attr('class','rowOn');	
	$('#authForm').find('#user_id').val(param_sn);
	
	 $.ajax({
	        url: '${ctxt}/mng/user/selectUserAuth.do',
	        data: {"loginid":param_sn},
	        type: 'POST',
	        dataType: 'text',
	        success: function(rtnXml) {
				$('input:checkbox[name="auth"]').prop("checked",false);
	        	
	        	xmlList = $(rtnXml).find('item');
	        	
	        	if(xmlList.length > 0){
	        		xmlList.each(function(cnt){
	        			
						var obj = $(this);
						
						var user_auth_cd = obj.find('user_auth_cd').text();
						
						if(user_auth_cd == 'ROLE_GUEST'){
							$('input:checkbox[id="ROLE_GUEST"]').prop("checked", true);
						}else if(user_auth_cd == 'ROLE_USER'){
							$('input:checkbox[id="ROLE_USER"]').prop("checked", true);
						}else if(user_auth_cd == 'ROLE_ADMIN'){
							$('input:checkbox[id="ROLE_ADMIN"]').prop("checked", true);
						}else if(user_auth_cd == 'ROLE_CHARGE'){
							$('input:checkbox[id="ROLE_CHARGE"]').prop("checked", true);
						}else{
						
						}
						
	        		});
	        	}
	        	
	        	pagetotalCnt=0;
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
}

// 조회 조건 초기화
function fn_clear(){
	//변수들 초기화
	
	$("form")[0].reset();
	$("form").find('input[type=text]').val(''); 
}


</script>
</head>

<body>

<form  action="" id="userForm" name ="userForm"  method="post"  >
	<input type="hidden" id="user_id" name="user_id" value="" alt="파라미터">
</form>
<form action="" name="chgForm" id="chgForm" method="post">
	<input type="hidden" name="loginid" id="loginid" value="" /> 
</form>

<div id="container">
	<div id="divRefreshArea">
		<input type="hidden" name="user_sn" id="user_sn" value="" /> 
		<input type="hidden" id="page" name="page" value="${PTLLoginVo.page}" /> 
		<input type="hidden" id="save_type" name="save_type" value="" /> 

		<%-- <h1 class="page_title" ><spring:message code="menu.user.poolMgmt" /></h1> --%>
		<h3 class="page_title">사용자 권한</h3>
		<div class="grid_box" style="grid-template-columns: repeat(auto-fit, minmax(30px, auto));">
			<label for="">권한그룹</label>
			<div class="custom-select selectRow">
				<label for="auth" class="hidden-access"></label>
				<select id="auth" name="auth">
					<option value="">전체</option>
					<c:forEach items="${auth_list}" var="auth" varStatus="state">   
						<option value="${auth.cd}" <c:if test="${PTLLoginVo.usrAuth == auth.cd}" >selected</c:if> ><label for="${org.cd}">${auth.cd_nm}</label></option>	
					</c:forEach>
				</select>
			</div>
			<div class="custom-select selectRow">
				<label for="status" class="hidden-access"></label>
				<select id="status" name="status">
					<option value="name" <c:if test="${PTLLoginVo.searchStatus == 'name'}" >selected</c:if>  >사용자명</option>
					<option value="id" <c:if test="${PTLLoginVo.searchStatus == 'id'}" >selected</c:if> >사용자ID</option>
					<option value="sn" <c:if test="${PTLLoginVo.searchStatus == 'sn'}" >selected</c:if> >사용자번호</option>
				</select>
			</div>
			<label for="search_input" class="hide">검색창</label>
			<input type="text" name="search_input" id="search_input" value="" onkeypress="javascript:fn_keySearch(1)" > 
			<a href="javascript:fn_search(1)" class="btn btn-primary" name="" title="조회 버튼"><span>조회</span></a>
		</div>
		<div class="clear admin-system reverse">
			<div>
				<table class="table_v bor_l" cellspacing="0" cellpadding="0">
					<colgroup>
						<col width="25%">
						<col width="25%">
						<col width="20%">					
					</colgroup>
					<thead>
					<tr>
						<th>번호</th>  
						<th>ID</th> 
						<th>이름</th>				
					</tr>   
					</thead>
				
					<!-- DATA 바인드 -->
					<tbody id="userList">
					</tbody>
				</table>
			
				<!-- 페이징 처리 -->
				<div id="paging" class="paginate"></div>

			</div>
			<div class="ma_t_-20 part">
				<div class="text_c w_full ma_b_5"  >
					<!-- save_btn -->
					<span id="sel_user"></span>
				</div>
				<form name="authForm" id="authForm" action="" method="post">
				<input type="hidden" name="user_id" id="user_id"  value="" />
		        <table class="table_v bor_top" border="1" cellspacing="0" summary="권한 관리 테이블 입니다."> 
					<caption>권한관리</caption> 
					<colgroup>
						<col width="30%" />
						<col width="70%" />
					 </colgroup> 
					<thead>  
					</thead>  
					<tbody id="auth_list"> 
						<c:forEach items="${auth_list}" var="auth" varStatus="state">
							<c:if test="${auth.cd != 'ROLE_GUEST' && auth.cd != 'ROLE_USER'}">
								<tr name="${auth.ref_val1}" > 
									<th>${auth.cd_nm} 권한</th>
									<td style="cursor: pointer;"><!--<c:if test="${ptlInformVo.newDataYn == '0'}">checked</c:if>  -->
										<label for="${auth.cd}" class="hidden-access"></label>
										<c:choose>
											<c:when test="${auth.cd_nm == 'BJ'}">
												<input type="checkbox" id="${auth.cd}" name="auth" disabled="disabled" value="${auth.cd}"/>${auth.cd_nm} <br />
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="${auth.cd}" name="auth" value="${auth.cd}"/>${auth.cd_nm} <br />
											</c:otherwise>
										</c:choose>										
											
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody> 
				</table>     
				</form>
				<div class="text_r">
					
						<a href = "javascript:void(0);" onclick="fn_save();"  class="btn btn-secondary" >저장</a>
				</div>
			</div>
		</div>    
	</div>
</div>

</body>
</html>