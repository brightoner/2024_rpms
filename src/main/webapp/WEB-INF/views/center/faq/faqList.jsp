<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


		


<script type="text/javascript">

var html="";

$(function(){
	fn_search();
  /* 
	$(".fold_box").click(function(){
		alert("ononon");
		
	});
	 */
	
	$("#faqBox > li").click(function(){
		console.log("ononon");
		$(this).children("ul").toggleClass("on");
		$(this).children("ul").toggleClass("shadow2");
		if ($(this).children("ul").hasClass("on")) {
			$(this).children("button").attr("title","닫기");
		} else {
			$(this).children("button").attr("title","열기");	
		}
	});
	

});

/*
* 페이징 처리 2 : 페이지 num를 조회조건으로 넘겨서 조회, 페이지 이동시 ajax 재호출
*/
function fn_search(){
	//현재 페이지 세팅
	
	
		var params = {};
		
    $.ajax({
        url: '${ctxt}/center/faq/readFaqList.do',
        data: params,
        type: 'POST',
        async: false,
        //formData로 data 보낼경우 false로 세팅,그외에는 밑에 text/xml데이터로 세
      //  contentType: "text/xml;charset=utf-8",
        //contentType: false,
        dataType: 'text',
       
	    //processData: false,
        success: function(rtnXml) {
   
        	xmlList = $(rtnXml).find('item');

        
        	html="";
			if(xmlList.length > 0){
				//총페이지수
				
				xmlList.each(function(cnt){
			
					var obj = $(this);
					
					var faq_id = obj.find('faq_id').text();
					var faq_title = obj.find('faq_title').text(); 		
					var faq_contents = obj.find('faq_contents').text(); 	
							console.log(faq_contents);			
					
					
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터
					****************************************************************/
			
					html += '<li>';
						html += '<button class="reset" title="열기">'+faq_title+'</button>';
							html += '<ul>';
								html += '<li>';
								html += faq_contents.replace(/\r\n/ig, '<br>').replace(/\\n/ig, '<br>').replace(/\n/ig, '<br>'); 
								html += '</li>';
							html += '</ul>';
					html += '</li>';
				
					
		        });				
				
			    $('.fold_box').html(html);
				
			
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
			
				
				$("#dataList").html('<tr><td colspan="3" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
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

function fn_faqdtl(seq){
	$('#faq_id').val(seq);
	var form = document.writeForm;
	form.action = '${ctxt}/center/faq/faqDetail.do';
	form.submit();	
}
function fn_write(){
	$('#faq_id').val("");
	var form = document.writeForm;
	form.action = '${ctxt}/center/faq/faqWrite.do';
	form.submit();	
}

</script>




<div id="container">
	<div id="divRefreshArea">
		<div id="spot" class="cscenter">
			<div class="img-box"><h1 class="page_title">자주 묻는 질문</h1></div>				 
		</div>
		<ul class="fold_box" id= "faqBox">		
		</ul>
	</div>
</div>  
