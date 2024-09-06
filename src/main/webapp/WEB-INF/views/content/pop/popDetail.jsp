<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<link rel="stylesheet" href="${ctxt}/resources/daumeditor/css/editor.css" type="text/css" />
	 <script src="${ctxt}/resources/daumeditor/js/editor_loader.js" type="text/javascript" ></script> 

	<script type="text/javascript">
	
	
	$(function(){   
		
		lf_cal_add($(".datepicker"));	
		
		fn_daumEditor("pop_contents");
		 
	});
	   

	function lf_cal_add(ths){
		ths.one().datepicker({
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear: true,
			yearRange: '2010:c+20',
			dateFormat: 'yy-mm-dd', //데이터 포멧형식
			buttonImageOnly: true,
			dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], // 요일의 한글 형식.
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] // 월의 한글 형식.
			//년월 셀박크기는 js에..
		}); 
		$(".cal").click(function(){
			$(this).parent().find(".datepicker").focus();
		});
	}
	

	
	function fn_save(){
		if($("#pop_title").val().trim() == "")
		{
			fn_showCustomAlert("팝업제목을 입력 하세요.");
			$("#pop_title").val("");
			$('#pop_title').focus();
			return false;
		}
		if($("#start_date").val().trim() == "")
		{
			fn_showCustomAlert("게시기간 시작일을 선택하세요.");
			$("#start_date").val("");
			$('#start_date').focus();
			return false;
		}
		if($("#end_date").val().trim() == "")
		{
			fn_showCustomAlert("게시기간 종료일을 선택하세요.");
			$("#end_date").val("");
			$('#end_date').focus();
			return false;
		}
		if($(':input:radio[name=pop_type]:checked').val()=="P")
		{
			if($("#pop_width").val().trim() == "")
			{
				fn_showCustomAlert("팝업 넓이를 입력하세요.");
				$("#pop_width").val("");
				$("#pop_width").focus();
				return false;
			}
			if($("#pop_width").val().trim() < 300 || $("#pop_width").val().trim() > 1000)
			{
				fn_showCustomAlert("팝업 넓이는 300~1000 사이값을 입력하세요.");
				$("#pop_width").val("");
				$("#pop_width").focus();
				return false;
			}
			if($("#pop_height").val().trim() == "")
			{
				fn_showCustomAlert("팝업 높이를 입력하세요.");
				$("#pop_height").val("");
				$("#pop_height").focus();
				return false;
			}
			if($("#pop_height").val().trim() < 400 || $("#pop_height").val().trim() > 1000)
			{
				fn_showCustomAlert("팝업 높이는 400~1000 사이값을 입력하세요.");
				$("#pop_height").val("");
				$("#pop_height").focus();
				return false;
			}
			if($("#pop_x").val().trim() == "")
			{
				fn_showCustomAlert("팝업 X좌표를 입력하세요.");
				$("#pop_x").val("");
				$("#pop_x").focus();
				return false;
			}
			if($("#pop_x").val().trim() < 0 || $("#pop_x").val().trim() > 1000)
			{
				fn_showCustomAlert("팝업 X좌표는 0~1000 사이값을 입력하세요.");
				$("#pop_x").val("");
				$("#pop_x").focus();
				return false;
			}
			if($("#pop_y").val().trim() == "")
			{
				fn_showCustomAlert("팝업 Y좌표를 입력하세요.");
				$("#pop_y").val("");
				$("#pop_y").focus();
				return false;
			}	
			if($("#pop_y").val().trim() < 0 || $("#pop_y").val().trim() > 1000)
			{
				fn_showCustomAlert("팝업 Y좌표는 0~1000 사이값을 입력하세요.");
				$("#pop_y").val("");
				$("#pop_y").focus();
				return false;
			}	
		}
		if(confirm("저장하시겠습니까?") ==true ){
			
			$("#pop_contents").val(Editor.getContent());
			//
			var form = document.popupFrm;
			form.action = '${ctxt}/content/pop/savePop.do';
			form.submit();	
		}
	}
	
	function fn_delete(){
		
		if(confirm("삭제 하시겠습니까?") ==true ){
			
			$("#pop_contents").val(Editor.getContent());
			//
			var form = document.popupFrm;
			form.action = '${ctxt}/content/pop/deletePop.do';
			form.submit();	
		}
	}
	
	function fn_back(){
		var form = document.popupList;
		form.action = '${ctxt}/content/pop/popList.do';
		form.submit();	
	}
	
	// 팝업 닫기 
	function closePopPeriod(id, period){	     
	
		
        $("#pop_txtarea" + id).css("display", "none");
	}
	
	// 팝업 목록 조회
	function fn_popUpList(){
	
		$('#popUpZone').children().remove(); // 팝업존 지우기 하위 .
		var xmlList;
		var popHtml = "";
		
		var params = {};
		params.pop_seq = '${data.pop_seq}';
		params.searchGbn = 'preview';

	    $.ajax({
	        url: '${ctxt}/content/pop/readMainPopList.do',
	        data: params,
	        type: 'GET',
	        //formData로 data 보낼경우 false로 세팅,그외에는 밑에 text/xml데이터로 세
	        contentType: "text/xml;charset=utf-8",
	        //contentType: false,
	        dataType: 'text',
		    //processData: false,
	        success: function(rtnXml) {
	   	
	        	xmlList = $(rtnXml).find('item');	       	        
	   
				if(xmlList.length > 0){					
					var popCnt = 0;
					xmlList.each(function(cnt){
						
							var obj = $(this);
										        	
							var seq = obj.find('pop_seq').text();
							var pop_title = obj.find('pop_title').text();							
							var pop_contents = obj.find('pop_contents').text();
							var pop_url = obj.find('pop_url').text();
							var pop_link = obj.find('pop_link').text();
							var pop_x = obj.find('pop_x').text();
							var pop_y = obj.find('pop_y').text();
							var pop_width = obj.find('pop_width').text();
							var pop_height = obj.find('pop_height').text();
						
							//popHtml +='<div class="popWindow popLeft ui-draggable" id="popWindow_'+cnt+'"style="display: block; left: 478px; top: 143px;">';
							//popHtml +='<div class="popWindow popLeft ui-draggable" id="popWindow_'+cnt+'"style="display: block; left: '+pop_x+'px; top: '+pop_y+'px; width:'+pop_width +'px; height:'+pop_height+'px;">';
							popHtml +='<div class="pop_txtarea" id="pop_txtarea_'+cnt+'"style="display: block; left: '+pop_x+'px; top: '+pop_y+'px;">';						
								popHtml+='<pre>'+pop_contents+'</pre>';
								if(pop_url != ''){													
									popHtml+='<p class="npop_btn">';								
									if(pop_link == 'Y'){
										popHtml+='<a href="#pop_link" onclick="lf_pop_link(\''+pop_url+'\',\''+pop_link+'\');" title="팝업 이동하기(자세히보기)" >자세히 보기</a>';		
									}													
									popHtml+='</p>' ;
								}
								popHtml+='<div class="pop_foot">';
									popHtml+='<input type="checkbox" id="chk_'+cnt+'">';
									popHtml+='<label for="chk_'+cnt+'">오늘 하루 닫기</label>';
									popHtml+='<a href="javascript:;" onclick="closePopPeriod(\'_'+cnt+'\');" class="btn_close1">닫기</a>';
								popHtml+='</div>';
							popHtml+='</div>';
						
							popCnt++;
						
			        });				
					
					$("#popUpZone").append(popHtml);
					
					if(popCnt > 0){
						for(var i = 0 ; i <= popCnt ; i++){
							$("#pop_txtarea_"+ i).draggable();
						}
						
					}
					
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
				
					
				
				}
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
	}
</script>
 <!-- 팝업 미리보기div 생성존  -->
    <div id = popUpZone>
	 
	</div>
<!-- 본문내용 -->
<div id="right_content">   
	<h3 class="page_title" id="title_div"><span class="adminIcon"></span>팝업관리 상세</h3>  
	<form name="popupList" method="post"  action="">	
		<input type="hidden" name="searchopt" value="${popup.searchopt}" />
		<input type="hidden" name="searchword" value="${popup.searchword}" />
		<input type="hidden" name="page" value="${popup.page}" />
	</form>
	<form name="popupFrm" method="post"  action="">	
		<input type="hidden" name="save_type" id="save_type" value="U" />
		<input type="hidden" name="pop_seq" value="${data.pop_seq}" />
	<table class="table_v">  
		<colgroup>
			<col width="20%">
			<col width="*">  
		</colgroup>
		<tbody>
		<tr>
			<th>팝업형태</th>
			<td class="bor_top flex_box">
				<%-- <div><input type="radio" name="pop_type" id="pop_type_y"  value="M" <c:if test="${data.pop_type == 'M' }">checked="checked"</c:if>> <label for="pop_type_y">레이어팝업</label></div> --%>
				<div><input type="radio" name="pop_type" id="pop_type_n"  value="P" <c:if test="${data.pop_type == 'P' }">checked="checked"</c:if>> <label for="pop_type_n">일반팝업</label></div>
			</td>
		</tr>
		<tr>
			<th>팝업제목</th>
			<td>
				<input type="text" name="pop_title" id="pop_title" value="${data.pop_title}" style="width:100%;ime-mode:active">
			</td>
		</tr>
		<tr>
			<th>팝업시점</th>
			<td class="flex_box">
				<div><input type="radio" name="pop_target" id="pop_target_n" value="N" <c:if test="${data.pop_target == 'N' }">checked="checked"</c:if> > <label for="pop_target_n">접속시</label></div>
				<div><input type="radio" name="pop_target" id="pop_target_y" value="L" <c:if test="${data.pop_target == 'L' }">checked="checked"</c:if> > <label for="pop_target_y">로그인시</label></div>
			</td>
		</tr>						
		<tr>
			<th>게시기간</th>
			<td class="flex_box">
			<div><input class="datepicker picker_l" type="text" name="start_date" id="start_date" value="${data.start_date}" maxlength="10" /></div>
			<div><input class="datepicker picker_l" type="text" name="end_date" id="end_date" value="${data.end_date}" maxlength="10" /></div>
			</td>      
		</tr>
		<!-- 텍스트입력 -->
		<tr>
			<th>내용</th>  
			<td>				
				<textarea name="pop_contents" id="pop_contents" rows="10" style="width:100%;ime-mode:active;display:none;">${data.pop_contents}</textarea>
				<div id="editor_frame"></div>
			</td>     
		</tr>         
		<tr>
			<th>자세히보기 URL</th>
			<td> 
				<input type="text" name="pop_url" id="pop_url" value="${data.pop_url}" placeholder="http:// 또는 https:// 로 시작" style="width:100%;ime-mode:disabled">
			</td>
		</tr>
		<tr>
			<th>링크방법</th>
			<td class="flex_box">
				<div><input type="radio" name="pop_link" id="pop_link_n" value="N" <c:if test="${data.pop_link == 'N' }">checked="checked"</c:if> > <label for="pop_link_n">바로 열기</label></div>
				<div><input type="radio" name="pop_link" id="pop_link_y" value="Y" <c:if test="${data.pop_link == 'Y' }">checked="checked"</c:if> > <label for="pop_link_y">새창으로 열기</label></div>
			</td>
		</tr>							
		<tr>
			<th>팝업크기</th>
			<td>						
				<div class="flex_box">
				<!-- div의 형태의 팝업으로 변경하서 기존 width height 항목은 입력 하지 않는다 . 에디터에서 처리       // db에  기본값은 넣도록 그냥 둠.-->
				 	<div style="display: none;">WIDTH : <input type="text" name="pop_width" id="pop_width" size="4" maxlength="4" value="<c:choose><c:when test="${empty data.pop_width}">400</c:when><c:otherwise>${data.pop_width}</c:otherwise></c:choose>" onKeyPress='onlyNumber(this)' style="text-align:center;ime-mode:disabled"></div>
				 	<div style="display: none;">HEIGHT : <input type="text" name="pop_height" id="pop_height" size="4" maxlength="4" value="<c:choose><c:when test="${empty data.pop_height}">500</c:when><c:otherwise>${data.pop_height}</c:otherwise></c:choose>" onKeyPress='onlyNumber(this)' style="text-align:center;ime-mode:disabled"></div> 
					<div class="ma_r_5">X좌표 : <input type="text" name="pop_x" id="pop_x" size="4" maxlength="4" value="<c:choose><c:when test="${empty data.pop_x}">100</c:when><c:otherwise>${data.pop_x}</c:otherwise></c:choose>" onkeyup='onlyNumber(this)' style="ime-mode:disabled" class="ma_t_3 text_c"></div>
					<div>Y좌표 : <input type="text" name="pop_y" id="pop_y" size="4" maxlength="4" value="<c:choose><c:when test="${empty data.pop_y}">100</c:when><c:otherwise>${data.pop_y}</c:otherwise></c:choose>" onkeyup='onlyNumber(this)' style="ime-mode:disabled" class="ma_t_3 text_c"></div>
				</div>
			</td>	  
		</tr>
		</tbody>
	</table>
	 </form>       
	 <div class="flex_box">
		
	  	<div align="right">
	  		<c:if test="${!empty data.pop_seq}" >	  			
	  			<a href="javascript:void(0);" onclick="javascript:fn_popUpList();" class="btn btn-secondary">팝업 미리보기</a>	
	  		</c:if>
	  		
				<a href="javascript:void(0);" onclick="javascript:fn_save();" class="btn btn-secondary"><c:choose><c:when test="${empty data.pop_seq}" >등록</c:when><c:otherwise>수정</c:otherwise></c:choose></a>		  		
			<c:if test="${!empty data.pop_seq}" >
		 
		 		<a href="javascript:void(0);" onclick="javascript:fn_delete();" class="btn btn-secondary">삭제</a>	
			</c:if>
		
				<a href="javascript:void(0);" onclick="javascript:fn_back();" class="btn btn-secondary">목록</a>	
		</div>
	</div>
	         
</div>

<form name ="popViewForm">
	<input type="hidden" id="seq" name="seq" value="" alt="fParameter" />
	
</form> 

    
   
	<!-- //right_content -->

	