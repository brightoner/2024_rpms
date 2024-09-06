<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<style>
.bx-viewport {
	height: auto !important;
}
	</style>
	
	<script type="text/javascript">	

	var liveAddPage = 1; // 스트리밍 용 
	var pagetotalCnt = 0;
	$(function() {
		
				
		// 이벤트 배너영역 호출
		fn_mainBannerSearch();
		
		//공지
		fn_mainNotiSearch(1);
	
		//라이브 스트리밍
		fn_liveStreamList();
	});
	
	//배너 
	function fn_mainBannerSearch(){
		
		var params = {};								
	  	  	
	    $.ajax({
	        url: '${ctxt}/index/banner/readBannerList.do',
	        data: params,
	        type: 'POST',
	        //async : false,
	        dataType: 'text',		
	        success: function(rtnXml) {
	   
	        	xmlList = $(rtnXml).find('item');

	        	pagetotalCnt=0;
	        	html="";
				if(xmlList.length > 0){
					//총페이지수
					
					xmlList.each(function(cnt){
				
						var obj = $(this);
						
						var banner_seq = obj.find('banner_seq').text();
						var banner_title = obj.find('banner_title').text(); 
						var atch_img_id  = obj.find('atch_img_id').text();
						var img_yn  = obj.find('img_yn').text();
						var create_dttm_al  = obj.find('create_dttm_al').text();
						var edit_date_al  = obj.find('edit_date_al').text();
						
						/***************************************************************
						* 화면별로 따로 세팅 테이블 데이터
						****************************************************************/
						html += '<div class= "bxslider">';
								html += '<div>';
									html += '<a  onclick = "javascript:fn_bannerDtl(\''+banner_seq+'\');">';																								
										html += '<img class="card-img-top"  src="${ctxt}/cmm/fms/getImage.do?atch_img_id='+atch_img_id+'&fileSn=0" alt="'+banner_title+'로고 이미지" title="'+banner_title+'" >';																
									html += '</a>';
						
								html += '</div>';						
						html += '</div>';
						
			        });				
					
				    $('#eventBanner').html(html);

				    $('#eventBanner').bxSlider({
						 slideWidth: 600,
						 auto: true,
					     autoControls: false,
					 	 stopAutoOnClick: true,
					     pager: true,
					     touchEnabled : false,
					     controls: false
				    });


				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					var notHtml=''; 
					notHtml += '<div class= "bxslider"><div class=""><a href = "javascript:void(0);"><img class="card-img-top" src="${ctxt}/resources/images/new/bannerNoImage600x400.png" ></a></div></div>';		
					notHtml += '<div class= "bxslider"><div class=""><a href = "javascript:void(0);"><img class="card-img-top" src="${ctxt}/resources/images/new/bannerNoImage600x400.png" ></a></div></div>';
					notHtml += '<div class= "bxslider"><div class=""><a href = "javascript:void(0);"><img class="card-img-top" src="${ctxt}/resources/images/new/bannerNoImage600x400.png" ></a></div></div>';
					notHtml += '<div class= "bxslider"><div class=""><a href = "javascript:void(0);"><img class="card-img-top" src="${ctxt}/resources/images/new/bannerNoImage600x400.png" ></a></div></div>';
				
					$("#eventBanner").html(notHtml);
					

				    $('#eventBanner').bxSlider({
						 slideWidth: 600,
						 auto: true,
					     autoControls: false,
					 	 stopAutoOnClick: true,
					     pager: true,
					     touchEnabled : false,
					     controls: false
				    });

				}
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
	}


	/*
	* 공지사항
	*/
	function fn_mainNotiSearch(page){
		//현재 페이지 세팅

		var params = {};
			params.page    = page;   

	  	  	
	    $.ajax({
	        url: '${ctxt}/center/noti/readNotiList.do',
	        data: params,
	        type: 'POST',
	        dataType: 'text',
	        success: function(rtnXml) {
	   
	        	xmlList = $(rtnXml).find('item');

	        	var pagetotalCnt=0;
	        	var notiHtml="";
				if(xmlList.length > 0){
					//총페이지수
					var start_num = Number($(rtnXml).find('totalDataCnt').text()) - ((page -1) *10)
						pagetotalCnt = Number($(rtnXml).find('totalCnt').text());			

					
					xmlList.each(function(cnt){
						if(cnt < 5){
							var obj = $(this);
							
							var noti_id = obj.find('noti_id').text();
							var noti_title = obj.find('noti_title').text(); 				
							var create_dttm  = obj.find('create_dttm').text();
							var atch_link_id  = obj.find('atch_link_id').text();
							var top_status = obj.find('top_status').text();
							var rdcnt = obj.find('rdcnt').text();
							
							/***************************************************************
							* 화면별로 따로 세팅 테이블 데이터
							****************************************************************/
							notiHtml += '<li>';  
							notiHtml += '<a type="button" href="javascript:fn_mainNotiDtl(\''+noti_id+'\')" class="con-box" title="공지사항 상세 페이지로 이동">';
							if(top_status == '1'){
								
								notiHtml += '<strong class="notiTit important"><span class="ir_so">중요 공지</span>'+noti_title + '</strong>';	
							}else{
								notiHtml += '<strong class="notiTit">'+noti_title+'</strong>';
							}
							notiHtml += '<span class="date" style="font-family:Noto Sans KR;">'+create_dttm+'</span>';
							notiHtml += '</a>';
							notiHtml += '</li>';
						}
			        });				
				
					
				    $('#notice-list').html(notiHtml);
					
				
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
				
					$("#notice-list").html('<li class="empty">조회된 내용이 없습니다.</>');
				}
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
	}

	// 스트리밍
	function fn_liveStreamList(gbn){

		var html ='';	
		
		var params = {};
		params.page    = liveAddPage;   
		
		$.ajax({
			url:"${ctxt}/live/stream/readLiveList.do",
			type : 'POST',
			data : params, 
			dataType : 'json',
			cache: false,
			success:function(result){
			
				if(!isEmpty(result) && !isEmpty(result.liveList) ){  
						$.each(result.liveList, function(idx, item){
							if(item.bcs_adult_set == '01'){
								html +='<li class="card adult">';
							}else {
								html +='<li class="card">';
							}
									html+='<a href="javascript:fn_liveStreamDtl(\''+item.bc_id+'\');">';									
										html +='<img class="card-img-top" src="https://codehivesec.dnstrip.net:8086/thumbnail?application=codelive&streamname='+item.bcs_stream_key+'&size=640x360&fitmode=letterbox" alt="..." />';
									html+='</a>';
									html+='<div class="card-live-body">';
										html +='<a href="javascript:fn_goVisitChannel(\''+item.bc_bj_id+'\')" >';
											html +='<img class="thumbnail"  src="${ctxt}/cmm/fms/getImage.do?atch_img_id='+item.atch_img_id+'&fileSn=0" alt="프로필 이미지" >';
										html +='</a>';
										html+='<div class="details">';
											html +='<a href="javascript:fn_liveStreamDtl(\''+item.bc_id+'\');">';
												html+='<p class="card-title-live">'+item.bcs_title+'</p>';
											html +='</a>';	
											html +='<a href="javascript:fn_goVisitChannel(\''+item.bc_bj_id+'\')" >';	
												html+='<p class="card-text-live">'+item.bc_bj_nicknm+'</p>';
											html +='</a>';	
										html+='</div>';
									html+='</div>';
								html+='</li>';
						});
						
						$("#liveContentList").append(html);
						liveAddPage++;
					
				}else{
					if(gbn == "B"){
						fn_showCustomAlert("LIVE 방송이 존재하지 않습니다.");
					}
				}
			
			},
			error:function(){
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			}
		}); 
		
	} 

	
	function fn_bannerDtl(seq){
	    
		$('#banner_seq').val(seq);
		var form = document.moveForm;
		form.action = '${ctxt}/content/banner/mainBannerDetail.do';
		form.submit();	
	}

	function fn_mainNotiDtl(seq){ 
		$('#noti_id').val(seq);
		var form = document.moveForm;
		form.action = '${ctxt}/center/noti/notiDetail.do';
		form.submit();	
	}
	


	function fn_liveStreamDtl(seq){
		$('#bc_id').val(seq);
		var form = document.liveMoveForm;
		form.action = '${ctxt}/live/stream/liveView.do';
		form.submit();	
	}
	
	

	function fn_goVisitChannel(val){
		if(val == '' || val == undefined){
			
			fn_showCustomAlert("채널 ID가 존재하지 않습니다.");
			
			return false;
		}

		var form = document.visitChForm;
		form.visitChId.value= val;
		form.action = '${ctxt}/vCh/visit/vChMain.do';
		form.submit();	
	}

	</script>
	
	<form name="liveMoveForm" method="post" action="">
	   	<input type="hidden"  id="page" name="page" value="" />         
	 	<input type="hidden"  id="bc_id" name="bc_id" value="" />
	</form>
	
	<form name="moveForm" method="post" action="">
		<input type="hidden"  id="banner_seq" name="banner_seq" value="" />
		<input type="hidden"  id="noti_id" name="noti_id" value="" />
	</form>
	
	
	<form name="visitChForm" method="post" action="">
	 	<input type="hidden" name="paramChUserId" id="visitChId" value=""/>
	</form>
	 
                    <!-- 사이트간략설명-->
                 <div class="card mb-4">
                	<div class="mainImgArea">
	                  <div>
	                     <img class="card-img-top" src="${ctxt}/resources/images/new/img2.png" alt="메인 대표 이미지" />
	                  </div>
	                 <%--    <div>
	                     <img class="card-img-top" src="${ctxt}/resources/images/new/main_img.png" alt="메인 대표 이미지" />
	                  </div> --%>
                	</div>
<!-- 				
                        <div class="card-body">
                            <div class="small text-muted">사이트간략설명</div>
                            <h2 class="card-title">제목제목제목제목제목제목제목제목제목제목</h2>
                            <p class="card-text">내용</p>
                            <a class="btn btn-primary" href="javascript:void(0);">바로가기 →</a>
                        </div>
-->                        
                </div>
                <div class="row main">
                   <!--  <div class="tit">거래량 순 코인 정보</div> -->
                     <div class="col-sm-3">
                    		  <!-- TradingView Widget BEGIN -->
					<div class="tradingview-widget-container">
					  <div class="tradingview-widget-container__widget"></div>
					  <!-- <div class="tradingview-widget-copyright">트레이딩뷰 제공 <a href="https://kr.tradingview.com/symbols/BTCUSDT/?exchange=BINANCE" rel="noopener" target="_blank"><span class="blue-text">BTCUSDT 환율</span></a></div> -->
					  <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-mini-symbol-overview.js" async>
					  {
					  "symbol": "BINANCE:BTCUSDT",
					  "width": "100%",
					  "height": "100%",
					  "locale": "kr",
					  "dateRange": "12M",
					  "colorTheme": "light",
					  "trendLineColor": "rgba(0, 0, 255, 1)",
					  "underLineColor": "rgba(255, 255, 255, 1)",
					  "underLineBottomColor": "rgba(255, 255, 255, 1)",
					  "isTransparent": false,
					  "autosize": true,
					  "largeChartUrl": "",
					  "chartOnly": false
					}
					  </script>
					</div>
					<!-- TradingView Widget END -->
                    </div>
                    <div class="col-sm-3">
                      			 <!-- TradingView Widget BEGIN -->
					<div class="tradingview-widget-container">
					  <div class="tradingview-widget-container__widget"></div>
					  <!-- <div class="tradingview-widget-copyright">트레이딩뷰 제공 <a href="https://kr.tradingview.com/symbols/BTCUSDT/?exchange=BINANCE" rel="noopener" target="_blank"><span class="blue-text">BTCUSDT 환율</span></a></div> -->
					  <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-mini-symbol-overview.js" async>
					  {
					  "symbol": "BINANCE:ETHUSDT",
					  "width": "100%",
					  "height": "100%",
					  "locale": "kr",
					  "dateRange": "12M",
					  "colorTheme": "light",
					  "trendLineColor": "rgba(0, 0, 255, 1)",
					  "underLineColor": "rgba(255, 255, 255, 1)",
					  "underLineBottomColor": "rgba(255, 255, 255, 1)",
					  "isTransparent": false,
					  "autosize": true,
					  "largeChartUrl": "",
					  "chartOnly": false
					}
					  </script>
					</div>
                    </div>                       
                    <div class="col-sm-3">
                      		  <!-- TradingView Widget BEGIN -->
					<div class="tradingview-widget-container">
					  <div class="tradingview-widget-container__widget"></div>
					  <!-- <div class="tradingview-widget-copyright">트레이딩뷰 제공 <a href="https://kr.tradingview.com/symbols/BTCUSDT/?exchange=BINANCE" rel="noopener" target="_blank"><span class="blue-text">BTCUSDT 환율</span></a></div> -->
					  <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-mini-symbol-overview.js" async>
					  {
					  "symbol": "BINANCE:XRPUSDT",
					  "width": "100%",
					  "height": "100%",
					  "locale": "kr",
					  "dateRange": "12M",
					  "colorTheme": "light",
					  "trendLineColor": "rgba(0, 0, 255, 1)",
					  "underLineColor": "rgba(255, 255, 255, 1)",
					  "underLineBottomColor": "rgba(255, 255, 255, 1)",
					  "isTransparent": false,
					  "autosize": true,
					  "largeChartUrl": "",
					  "chartOnly": false
					}
					  </script>
					</div>
                    </div>
                    <div class="col-sm-3">
                    
                       			 <!-- TradingView Widget BEGIN -->
					  <div class="tradingview-widget-container">
					     
					  <div class="tradingview-widget-container__widget"></div>
					
					  <!-- <div class="tradingview-widget-copyright">트레이딩뷰 제공 <a href="https://kr.tradingview.com/symbols/BTCUSDT/?exchange=BINANCE" rel="noopener" target="_blank"><span class="blue-text">BTCUSDT 환율</span></a></div> -->
					  <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-mini-symbol-overview.js" async>
					  {
					  "symbol": "BINANCE:DOGEUSDT",
					  "width": "100%",
					  "height": "100%",
					  "locale": "kr",
					  "dateRange": "12M",
					  "colorTheme": "light",
					  "trendLineColor": "rgba(0, 0, 255, 1)",
					  "underLineColor": "rgba(255, 255, 255, 1)",
					  "underLineBottomColor": "rgba(255, 255, 255, 1)",
					  "isTransparent": false,
					  "autosize": true,
					  "largeChartUrl": "",
					  "chartOnly": false
					}
					  </script>
					
						</div>
			
                    </div>                          
                </div>  
                <!-- 이벤트 배너 영역  -->
               <div  class="row main notice">
               
                	<div class="w_52">
                		<div class="box-notice">
	                   		<div class="h-box ma_b_20">
								<h2 class="notiTit">공지사항</h2>
								<a href="${ctxt}/center/noti/notiList.do" class="more-btn" title="공지사항 더 보기 버튼">
									<img src="${ctxt}/resources/images/main/se03_more.png" alt="공지사항 더보기" />
								</a>
							</div>
							<ul class="notice-list" id ="notice-list">
								
							</ul>
						</div>
                   	</div>
                   	
                   	<div class="ma_t_30">                  	                   	
	                   	<div id="eventBanner">
	                   	
	                   	</div>
                   	</div>
                                       
               </div>
                <!-- 스트리밍 리스트 -->
                <div class="row main">
                	<div class="h-box ma_b_20">
	                    <h2 class="notiTit">스트리밍 리스트<span class="highlight">지금 바로 실시간 투자 방송에 참여해 보세요.</span></h2>
					</div>
                    <ul class="broadlist" id = "liveContentList">
                    <%-- 	
                    <li class="card">
                   		<a href="javascript:void(0);">
                           	<img class="card-img-top" src="${ctxt}/resources/images/new/img.png" alt="..." />
                         </a>
                         <div class="card-live-body">
                         		<a href="javascript:void(0);" class="thumbnail sample"></a>
                         		<div class="details">
                         			<p class="card-title-live">비트코인 웨돔 Live 혼란스러운 장세에서 살아남으셔야 합니다.</p>
                         			<p class="card-text-live">웨돔N브로</p>
                         		</div>
						</div>
                   	</li>
					 --%>
                    </ul>
                    <a href= "javascript:void(0);" onclick="javasctipt:fn_liveStreamList('B');" class="btn btn-primary btn-viewmore d-block w-100">더 보기</a>
                </div>