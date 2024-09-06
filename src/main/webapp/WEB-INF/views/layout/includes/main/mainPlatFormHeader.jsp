<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver"%>

<script src="${ctxt}/resources/js/jquery.cookie.js"></script>
<script type="text/javaScript">

  
	$(function(){
		
		// 메인 팝업을 호출한다.	
		fn_popUpList();
	});
	
	
	// 쿠키 설정
	function setCookie(name, value, expiredays) {
		var today = new Date();
		    today.setDate(today.getDate() + expiredays);
	 
		    document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
	}
	// 팝업 닫기 
	function closePopPeriod(id, period){	     
	
		if($("#chk"+id).is(":checked") == true) {
			setCookie("popCookLayer" + id, "Y" , 1);
		}
        $("#pop_txtarea" + id).css("display", "none");
	}
	
	// 팝업 목록 조회
	function fn_popUpList(){
	
		$('#popUpZone').children().remove(); // 팝업존 지우기 하위 .
	
		var xmlList;
		var popHtml = "";
		var params = {};
		
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
						if($.cookie('popCookLayer_'+cnt) != "Y"){  // 쿠기에 설정되지 않은 팝업만 오픈한다.
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
									//if(pop_link == 'Y'){
										popHtml+='<a href="#pop_link" onclick="lf_pop_link(\''+pop_url+'\',\''+pop_link+'\');" title="팝업 이동하기(자세히보기)" >자세히 보기</a>';		
									//}													
									popHtml+='</p>' ;
								}
								popHtml+='<div class="pop_foot">';
									popHtml+='<input type="checkbox" id="chk_'+cnt+'">';
									popHtml+='<label for="chk_'+cnt+'">오늘 하루 닫기</label>';
									popHtml+='<a href="javascript:;" onclick="closePopPeriod(\'_'+cnt+'\');" class="btn_close1">닫기</a>';
								popHtml+='</div>';
							popHtml+='</div>';
						
							popCnt++;
						}
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
 <style>
 
 </style>

    
    <!-- 팝업 div 생성존  -->
    <div id = popUpZone>
	 
	</div>
    
    
    
    <!-- 헤더 영역 -->
    <header id= "header" class="py-5 bg-light border-bottom mb-4">
       
      	<div class="gnb-wrap">
		<%@include file="./mainPlatFormMenu.jsp"%>
			<div class="menu-back"></div>   
		</div>
		
         <div class="container">
          <!--    <div class="text-center my-5">
                 <h1 class="fw-bolder">이미지</h1>
             </div>
             -->
         </div>
         
    </header>
