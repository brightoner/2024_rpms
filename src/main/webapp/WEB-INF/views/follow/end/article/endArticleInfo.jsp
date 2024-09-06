<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">
	
	$(function(){
		
		// 첨부파일 로드
		getFileList($("#end_file_group_article").val(), '14');
		
		// 채용리스트 로드
		/* var articlePage =$('#articlePage').val();
		if(articlePage == '' ){
			articlePage=1;
		} */
		var articlePage=1;
		
		fn_articleSearch(articlePage);
		
	});
	
	
	//첨부파일 다운로드
	function fn_fileDownload(val){
		var frm = document.articleFileForm;
		frm.action = "/atchFile/CmmnFileDown.do" + "?fileId_value="+val;
		frm.submit();
	}

	//첨부파일등록 팝업 호출
	function fn_egov_file_popup14() {
		var reqManageVo=document.articleFileForm;
		var file_group = reqManageVo.end_file_group_article.value;
		var file_gb = "14";

		popAttfileModify(file_group, file_gb, 'fileContainerRefresh');

  	}
	
	
	// 채용인원 리스트
	function fn_articleSearch(articlePage){

		cuurPage= articlePage;
		var proj_end_id_article = $("#proj_end_id_article").val();

		var params = {};
			params.page    = cuurPage;   
			params.proj_end_id = proj_end_id_article;
			
	  	$('#articlePage').val(cuurPage);
		  	
	$.ajax({
	    url: '${ctxt}/follow/end/article/readEndArticleList.do',
	    data: params,
	    type: 'POST',
	    dataType: 'json',
	    success: function(result) {
	    	var html ='';
	    	pagetotalCnt = 0;
	    	if(!isEmpty(result)){
				if(!isEmpty(result.articleList)){
					
					$("#atcCnt").text(result.articleTotal);
					
					$("#articleDataList").children().remove();
					
					var start_num = Number(result.articleTotal) - ((cuurPage -1) *5)
					pagetotalCnt =Number(result.articlePageTotal);	
					
					$.each(result.articleList, function(idx, item){
						html += '<tr name="listTr">';
							html += '<td class="text_c">' + (start_num - idx) + '</td>';
							html += '<td class="text_c">'+'<input type="checkbox" name="articleChkObj" title="선택하기'+idx+'" value='+item.end_artcl_id+'>';		
							html += '<td class="text_c"><a href="javascript:fn_articleDtl(\''+item.end_artcl_id+'\');">'+((isEmpty(item.end_artcl_nm)) ? '' : item.end_artcl_nm) +'</a></td>';
							html += '<td class="text_c">'+((isEmpty(item.end_artcl_sci_gb)) ? '' : item.end_artcl_sci_gb)+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.end_artcl_domestic_gb)) ? '' : item.end_artcl_domestic_gb)+'</td>';												
						html += '</tr>';						
					});
					
					//트리코드 선택시 첫번째 행 선택
					//페이징처리
				    $('#articlePaging').paging({
				    	
						 current:cuurPage
						,max:pagetotalCnt
						,length:5
						,onclick:function(e,page){
							cuurPage=page;
							fn_articleSearch(cuurPage);
						}
					});
					
				 	$('#articleDataList').html(html);
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#articlePaging').children().remove();
					
					$("#articleDataList").html('<tr><td colspan="5" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
				}
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$("#total_cash").val(0);
				
				$('#articlePaging').children().remove();
				
				$("#articleDataList").html('<tr><td colspan="5" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
			}
	    },
	    error : function(){                              // Ajax 전송 에러 발생시 실행
	    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	    }
	});
	}
	
	
	
	// 체용인원 상세
	function fn_articleDtl(seq){
		
		var end_artcl_id = seq;
		
		var params = {};
		params.end_artcl_id = end_artcl_id;
		
		
		$.ajax({
	        url: '${ctxt}/follow/end/article/endArticleDetail.do',
	        data: params,
	        type: 'POST', 
	        dataType: 'json',	 
			success: function(result){		
				
				$("#end_artcl_id").val(result.data.end_artcl_id);
				$("#end_artcl_nm").val(result.data.end_artcl_nm);
				$("#end_artcl_auth").val(result.data.end_artcl_auth);
				$("#end_jounal_info").val(result.data.end_jounal_info);
				$('[name=end_artcl_sci_gb]').val(result.data.end_artcl_sci_gb);
				$('[name=end_artcl_domestic_gb]').val(result.data.end_artcl_domestic_gb);   
				
				fn_articleSearch(1);
			},
			error: function(request,status,error) {
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	  	  });
		 
	  	  
	}
	
	
	// 논문정보 저장
	function fn_saveArticle(){
		
		// 논문명 validation
		if($("#end_artcl_nm").val() == "" || $("#end_artcl_nm").val() == null){
			fn_showCustomAlert("논문명을 입력 하세요.");
			$('#end_artcl_nm').focus();
			return false;
		}
		
		/* // 저널정보 validation
		if($("#end_jounal_info").val() == "" || $("#end_jounal_info").val() == null){
			fn_showCustomAlert("저널정보를 입력 하세요.");
			$('#end_jounal_info').focus();
			return false;
		} */
		
		// SCI여부 validation
		if($('[name=end_artcl_sci_gb] :selected').val() == "" || $('[name=end_artcl_sci_gb] :selected').val() == null){
			fn_showCustomAlert("SCI여부를 선택 하세요.");
			$('#end_artcl_sci_gb').focus();
			return false;
		}
		
		// 국내외구분 validation
		if($('[name=end_artcl_domestic_gb] :selected').val() == "" || $('[name=end_artcl_domestic_gb] :selected').val() == null){
			fn_showCustomAlert("국내외구분을 선택 하세요.");
			$('#end_artcl_domestic_gb').focus();
			return false;
		}
		
		// 논문저자 validation
		if($("#end_artcl_auth").val() == "" || $("#end_artcl_auth").val() == null){
			fn_showCustomAlert("논문저자를 입력 하세요.");
			$('#end_artcl_auth').focus();
			return false;
		}
		

		var item = serializeFormToJSON($("form[name='articleFrm']"));
		var proj_end_id_article = $("#proj_end_id_article").val();

		var requestData = {
        		mainItem : item,
        		proj_end_id_article : proj_end_id_article
            };

	    $.ajax({
		        url: '${ctxt}/follow/end/article/insertEndArticle.do',
		        data: JSON.stringify(requestData),
		        contentType: 'application/json',
		        type: 'POST', 
		        dataType: 'json',	      
				success: function(result){			
					
					if(result.sMessage == "Y"){
						fn_showCustomAlert("저장이 완료되었습니다.");
						fn_articleSearch(1);
						fn_clearArticle();
					}else if(result.sMessage == "N"){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
					}else if(result.sMessage == "F"){
						fn_showCustomAlert("과제 키가 존재하지 않습니다.");
					} 
				},
				error: function(request,status,error) {
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
	  	  });
	}
	
	
	// 초기화
	function fn_clearArticle(){
		document.getElementById('articleFrm').reset();
		$("#end_artcl_id").val('');
	}
	
	// 삭제 버튼 
	function fn_delArticle(){
	    var articleVal = [];
	    $('input[name="articleChkObj"]:checked').each(function() {
	    	articleVal.push($(this) ? $(this).val() : '');
	    });
	    $('input[name="end_article_ids"]').val('');
        $('input[name="end_article_ids"]').val(articleVal);
	    
        var sendData = $("form[name='articlesForm']").serialize();
        
		if(confirm("삭제하시겠습니까?") ==true ){
	    		
	        $.ajax({
	            url: '${ctxt}/follow/end/article/deleteEndArticle.do',
	            type: 'POST',
	            data: sendData,
	            type: 'POST', 
	            dataType: 'json',
	            success: function(result) {
	            	if(result.result != 0){
						fn_showCustomAlert("삭제가 완료되었습니다.");
						fn_articleSearch(1);
					}else if(result.result == 0){
						fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
				
					}else if(result.sMessage == "F"){
						fn_showCustomAlert("채용자 키가 존재하지 않습니다.");
					} 
	            },
	            error: function(error) {
	                // 실패 시 처리할 코드
	                console.log('삭제 실패', error);
	            }
	        });
	    }
	}
	
</script>



 <div id="container">
	<div id="divRefreshArea">
		<h3 class="page_title" id="title_div"><span class="adminIcon"></span>논문 성과 등록</h3>
		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;논문 성과 등록</h4>
			<div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_clearArticle();" class="btn btn-secondary">초기화</a>				  				
				<a href="javascript:void(0);" onclick="javascript:fn_saveArticle();" class="btn btn-secondary">저장</a>			
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>			  		
			</div>		
		</div>
		<!--게시판-->
		<div class="article">
			<form id="articleFrm" name="articleFrm" method="post"  action="">
				
		
				<table id="articleTable" class="table_h" cellpadding="0" cellspacing="0" border="0" >
	
					<caption>논문정보</caption>
					<colgroup>
						<col style="width: 20%;">		
						<col style="width: 10%;">								
						<col style="width: 10%;">				
						<col style="width: 30%;">				
						<col style="width: 30%;">				
					</colgroup>
					<thead>
						<tr>
							<th scope='col'><font color="red" class="last-child">*</font>논문명</th>
							<th scope='col'><font color="red" class="last-child">*</font>SCI구분</th>
							<th scope='col'><font color="red" class="last-child">*</font>국내외구분</th>
							<th scope='col'><font color="red" class="last-child">*</font>논문저자</th>											
							<th scope='col'>학술지정보</th>
						</tr>
					</thead>
					<tbody>
						<tr>		
							<td>
								<input type="hidden" id="end_artcl_id" name="end_artcl_id" value="">
								<input type="text" id="end_artcl_nm" name = "end_artcl_nm" value=""/>
							</td>
							
							<td>
								<div class="custom-select selectRow">
									<select id="end_artcl_sci_gb" name="end_artcl_sci_gb" class="select">
										<option value="">선택하세요.</option>
									    <c:forEach var="sciGbList" items="${sciGbList}">
									        <option value="${sciGbList.cd_nm}">${sciGbList.cd_nm}</option>
									    </c:forEach>
									</select>
								</div>
							</td>
							<td>
								<div class="custom-select selectRow">
									<select id="end_artcl_domestic_gb" name="end_artcl_domestic_gb" class="select">
									    <option value="">선택하세요.</option>
									    <c:forEach var="dmstcGbList" items="${dmstcGbList}">
									        <option value="${dmstcGbList.cd_nm}">${dmstcGbList.cd_nm}</option>
									    </c:forEach>
									</select>
								</div>
							</td>
							<td>
								<textarea id="end_artcl_auth" name="end_artcl_auth" rows="5" style="width:100%;ime-mode:active;"></textarea>
							</td>
							<td>
								<textarea id="end_jounal_info" name="end_jounal_info" rows="5" style="width:100%;ime-mode:active;"></textarea>
							</td>
						</tr>
					</tbody>
	
				</table>
			</form>
		</div>


		<div class="titles">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;첨부파일 등록</h4>
		</div>
		<div class="attch">
			<table class="table_v">  
				<colgroup>
					<col width="20%">
					<col width="30%">  
					<col width="20%">
					<col width="30%">    
				</colgroup>
				<tbody>
					<tr> 
					<th>첨부파일</th>
				  	<td colspan="3">
						 <a href="javascript:void(0);" onclick="javascript:fn_egov_file_popup14();" class="btn btn-secondary">파일 올리기</a>
						 <div id="fileContainer14" class="file-container"></div>
				    </td>
				</tr>
				</tbody>
			</table>
		</div>

		
		<div class="titles" style="margin-top: 40px;">
			<h4 style="margin-top: 20px;"><i class="fas fa-chevron-circle-right  fa-sm" style="color:#314873" aria-hidden="true"></i>&nbsp;논문 성과 정보</h4>
			<div class="btn_wrap" >			
			  	<a href="javascript:void(0);" onclick="javascript:fn_delArticle();" class="btn btn-secondary">삭제</a>				  		
			</div>		
		</div>
		
		<!--게시판-->
		<div class="article">
			
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" >

				<caption>논문</caption>
				<colgroup>
					<col style="width: 5%;">		
					<col style="width: 5%;">		
					<col style="width: 50%;">		
					<col style="width: 20%;">								
					<col style="width: 20%;">				
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>선택</th>
						<th scope='col'>번호</th>
						<th scope='col'>논문명</th>
						<th scope='col'>SCI구분</th>											
						<th scope='col'>국내외구분</th>
					</tr>
				</thead>
				<tbody id="articleDataList">
				</tbody>

			</table>
			<div id="articlePaging" class="paginate"></div>
			</div>
		</div>
	</div>


<form name ="articlesForm" id="articlesForm" method="post" action="">
	<input type="hidden" id="end_article_ids" name="end_article_ids"/>
</form>

<form name ="articleFileForm" id="articleFileForm" method="post" action="">
	<input type="hidden" id = "end_file_group_article" name="end_file_group_article" value="${data.end_file_group}" />
</form>

<input type="hidden" id = "articlePage" name="articlePage" value="" />
<input type="hidden" id = "proj_end_id_article" name="proj_end_id_article" value="${data.proj_end_id}" />



