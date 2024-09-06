<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver"%>


<script>


$(function(){
	
	// top 5 list
	fn_weekTop5Rank();
	
}); 


	//현물 전월 랭킹 
	function fn_weekTop5Rank(){
		//현재 페이지 세팅
		wt_html=""; 
		
		var params = {};
	
		$.ajax({
		    url: '${ctxt}/rank/readWeekTopRankList.do',
		    data: params,
		    type: 'POST',     
		    dataType: 'json',
		    success: function(result) {
		    	if(!isEmpty(result) && !isEmpty(result.top5List)){
					
		    		$.each(result.top5List, function(idx, item){
						wt_html += '<li class="rankLi">'+item.rank+'.&nbsp;'+item.nicknm+'</li>';
					});
					
				    $("#weekTop5").html(wt_html);
		    	}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					
					$("#weekTop5").html('<ul><li>랭킹 데이터가 없습니다.</li></ul>');
				}
		    },
		    error : function(){                              // Ajax 전송 에러 발생시 실행
		    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		    },
		    complete : function(){
		    
		    }
		});
	
	}

	//랭킹 상세보기 페이지 이동
	function fn_rankList(){
		window.location.href = "/rank/rank.do";
	}
</script>



<!-- 배너 영역 -->

     <!-- 모의투자 랭킹 -->
     <div class="card mb-4 rank">
         <div class="rank-header">주간 랭킹
         	<button class="btn btn-secondary" id="rankList" onclick="javascript:fn_rankList();" title="랭킹 리스트 더 보기 버튼">+</button>
         </div>
         <div class="card-body relative">
             <i class="fas fa-medal"></i>
             <div class="row">
                 <div class="col-sm-6">
                 	<!-- 주간랭킹 영역 -->
                     <ul class="list-unstyled mb-0" id="weekTop5">
                     
                     </ul>
                 </div>
              </div>
         </div>
     </div>
     <!-- 광고 배너 -->
     <div class="card banner">
         <a href="#!"><img class="card-img-top" src="${ctxt}/resources/images/new/banner.png" alt="..." /></a>
     </div>
