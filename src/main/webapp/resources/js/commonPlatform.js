var ctxt = '';

//운영  또는 https(로컬도 https 환경으로 만들었다면 이것을 사용);
//var chatEndPoint = 'https://moneybunny.kr/stomp/chatting';

//로컬  http 환경
var chatEndPoint = '/stomp/chatting';


// 마이 채널의 상단 채널명과 채널 소개를 호출하는 스크립트.
// 마이 채널 / 방문채널 공통으로 쓴다 paramChUserId 이거는 방문채널일떄만 사용
// gbn =  mych : 마이채널에서 사용 /  vch : 방문채널에서 사용
function fn_chUserInfo(gbn , chId){

	if(gbn == null || gbn == '' || gbn == undefined){
		console.log('##### gbn 이 없다 #####');
		return false;
	}

	// 방문채널인데 user 값이 없으면 실행 안한다.
	if(gbn == 'vch'){
		if(chId == null || chId == '' || chId == undefined){
			console.log('##### 방문채널인데 유저값이 없다 id 없다 확인 필요 #####');
			return false;
		}
	}
		
	var params = {};
		params.chGbn    = gbn;  
		params.vChUserId    = chId;
		
		$.ajax({
			url : ctxt + '/myCh/ch/chMngUserInfo.do',
			type:'POST',
			data:params,	
			dataType:"text",
			async : false,
			success : function(result) {

	        	var html="";				
	        	var obj = $(result);						
				var ch_nm = obj.find('ch_nm').text();  //채널명
				var ch_noti_title = obj.find('ch_contents').text(); // 채널 소개 				
				var ch_nickNm = obj.find('ch_nickNm').text(); // 닉네임 			
				
				//저장된 채널속성이 존재하면 셋팅 없으면 닉네임과 조합해서 표현
				if(ch_nm != '' && ch_nm != undefined  && ch_nm != null ){
					$('#chTitle').html(ch_nm);	
				}else{
				
					$('#chTitle').html(ch_nickNm);	
				}
			
				if(ch_noti_title != '' && ch_noti_title != undefined  && ch_noti_title != null ){
					 $('#chContents').html(ch_noti_title);	
				}else{
					var chCon = ch_nickNm + '님의 채널입니다.' ;
					 $('#chContents').html(chCon);	
				}
			   			
				
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
			
			}
		});
	
}


// 즐겨찾기 여부를 확인해서 즐겨찾기 마크를 변환해준다
function fn_bookMarkChkYn(chUserId){

		
	var params = {};
		params.chUserId    = chUserId;  
		
		$.ajax({
			url : ctxt + '/myCh/chBmMng/chBmMngChk.do',
			type:'POST',
			data: params,	
			dataType:"text",
			async : false,
			success : function(result) {

				//즐겨찾기 여부 체크 
				if(result == 'Y'){ 
					$("#chBookMark").attr('class','btn btn-favor on');
				}else{
					$("#chBookMark").attr('class','btn btn-favor');
				}
				
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				console.log('fn_bookMarkChkYn(); 즐겨찾기 체크 기능 오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
			
			}
		});
	
}



//******** 코인 즐겨찾기 여부를 확인해서 즐겨찾기 마크를 변환 STR *********
function fn_bookMarkCoinYn(invest_gbn){
	var params = {};
		params.invest_gbn	= invest_gbn;  
		
		$.ajax({
			url : ctxt + '/simInvest/coinBmMng/coinBmMngChk.do',
			type:'POST',
			data: params,	
			dataType:"json",
			async : false,
			success : function(data) {
				
				//즐겨찾기 여부 체크 
				if( data.result == 'Y'){ 
					$(data.coinBmList).each(function(cnt){
						var obj = this;
						$("#coinBookMark_"+obj.coin_id).attr('class','btn btn-coin-favor on');
					});
				}
				else{
					$("#coinBookMark").attr('class','btn btn-coin-favor');
				}
			},
			error : function() { // Ajax 전송 에러 발생시 실행
				console.log('fn_bookMarkCoinYn(); 즐겨찾기 체크 기능 오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			},
			complete : function(result) { //  success, error 실행 후 최종적으로 실행
			
			}
		});
	
}
//******** 코인 즐겨찾기 여부를 확인해서 즐겨찾기 마크를 변환 END *********




//******************** 아이템 선물하기 STR ********************

var totalItemCnt = "";


	// 아이템 선물하기 팝업창
	function fn_sendItemPopUp(userId,userNickNm, sInfo , gbn){
		
		if (sInfo == "" || sInfo == null || sInfo == undefined) {
			
	    	fn_showCustomAlert("로그인 후 이용해 주십시오.");
			
	    	return false;
		}  
		
		var popHtml = "";
		var params = {};
		
		
	    $.ajax({
	        url: ctxt+ '/item/send/itemSendPop.do',
	        data: params,
			type : 'POST',
			dataType: 'text',
			cache: false,
	        success: function(result) {	        		        		
        		totalItemCnt = result;
        		
        		//드래그 가능한 div
        		popHtml += '<div class="draggable-div" style="width: 400px; inset: 210px auto auto 760px; height: 260px;">';
        			//입력창
        			popHtml += '<div class="form-group ma_0">';
		        		popHtml += '<label for="inputText" class="form-label"><strong>'+ userNickNm +'</strong>님께 선물</label>';
		        		popHtml += '<br>';
		        		popHtml += '<label for="inputText" class="form-label item-have">보유한 아이템 개수 : '+ result +'개</label>';
		        		popHtml += '<div class="balloon-to-give">';
			        		popHtml += '<label>선물할 별풍선</label>';
			        		popHtml += '<input type="text" class="form-control" id="itemCnt" name="itemCnt" value="" style="width:100%" autocomplete="off"><span>개</span>';
			        	popHtml += '</div>';
			        	popHtml += '<div class="ma_t_30">';						
			        	    popHtml += '<a href="javascript:goItemSend(\''+userId+'\' , \''+gbn+'\');" class="float_n btn btn-primary">선물하기</a>';
  			        	    popHtml += '<a href="javascript:closePopPeriod();" class="btn btn-secondary">닫기</a>';
		        		popHtml += '</div>';
		        	popHtml += '</div>';
		        popHtml += '</div>';
        		
        		$("#sendItemZone").html(popHtml);
        		$(".draggable-div").draggable();
	        		
        		$('html').scrollTop(0); // 저장 후에 맨위로 올려주려고 셋팅
	    		// 숫자만 입력 
	    	 	$("#itemCnt").on("input", function () {
	    	         var inputValue = $(this).val();
	    	         var intValue = parseInt(inputValue);

	    	         if (isNaN(intValue)) {
	    	             $(this).val("");
	    	         } else {
	    	             $(this).val(intValue);
	    	         }
	    	    }); 
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
					
	}
	
	// 아이템 선물하기 팝업 닫기 
	function closePopPeriod(){	     
		 var element = document.getElementById('sendItemZone');
		 element.innerText = '';
	}
	
	// 아이템 선물하기 실행
	function goItemSend(userId , gbn){
	
		if (userId == "" || userId == null || userId == undefined) {
			
	    	fn_showCustomAlert("유저 ID 정보가 존재하지 않습니다.");
			
	    	return false;
		}  
		
		// ** 아이템 선물 validateion STR **
		var totalItem = Number(totalItemCnt);			// 보유한 전체 아이템 수
		var sendItem = Number($("#itemCnt").val());		// 선물할 아이템 수
		
		// 아이템 갯수 숫자 정규식
		var check = /^[0-9]+$/;
		if (!check.test(sendItem)) {
			fn_showCustomAlert("정수인 숫자만 넣어 주세요.");
			$("#itemCnt").val("");
			$("#itemCnt").focus();
			return;
		}
		
		if (sendItem == "") {
			fn_showCustomAlert("선물할 아이템 개수를 넣어 주세요.");
			$("#itemCnt").focus();
			return;
		}
		
		if (sendItem == 0) {
			fn_showCustomAlert("0개는 선물할 수 없습니다.");
			$("#itemCnt").focus();
			return;
		}
		
		if(totalItem < sendItem){
			fn_showCustomAlert("보유한 아이템 개수보다 큰 값입니다.");
			$("#itemCnt").focus();
			return;
		}
		// ** 아이템 선물 validateion END **
		
		
		var strConfirm;	
		strConfirm = confirm("아이템을 선물하시겠습니까?");
		
		var params = {};
		params.rcv_user_id    = userId;
		params.itemCnt = $("#itemCnt").val();
		
		if(strConfirm){	
			  $.ajax({
		 		    url: ctxt+'/item/send/goItemSend.do',
		 		    data: params,  
		 		    type: 'POST',
		 		    dataType: 'text',
		 		    cache: false,
		 		   	async: false,
		 		    success: function(result) {
		 		    	
		 		    	if(result == "Y"){// 성공  					
		 					 $("#sendItemZone").children().remove();
		 					 fn_totalItemPoint();	// 아이템, 포인트 개수 최신화
		 					 
		 					 // 채팅방 message
		 					 if(gbn == 'chat'){
		 						 streamStomp.send('/pub/chat/stream/message.do', {}, JSON.stringify({chatRoomNo: streamChatRoomId, chatMessage: sendItem, chatMessageType:'IMG', chatWriter: streamChatUserNickNm}));
		 					 }
		 					 
		 				  }else if(result == "NS"){
		 					 fn_showCustomAlert("로그인 상태를 다시 확인해 주세요.");
		 				  }else if(result == "ER"){
				        		fn_showCustomAlert("선물할 아이템 개수를 다시 확인해 주세요.");	 
		 				  }else{ 					  
		 					 fn_showCustomAlert("아이템 선물에 실패했습니다."); 					  
		 				  }
		 		    	
		 		   },
		 		   error : function(){                  
		 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		 		   }
		 	});
		}
	}
	

//******************** 아이템 선물하기 END ********************
	

//******************** 포인트 선물하기 STR ********************
	var totalPointCnt = "";


	// 포인트 선물하기 팝업창
	function fn_sendPointPopUp(rsvUserId,rsvUserNickNm, giveUserId , giveNicknm , gbn){ // rsvUserId,rsvUserNickNm  받는사람 정보  giveUserId , giveNicknm  주는사람 정보
		
		if (giveUserId == "" || giveUserId == null || giveUserId == undefined) {
			
	    	fn_showCustomAlert("로그인 후 이용해 주십시오.");
			
	    	return false;
		}  
		
		var popHtml = "";
		var params = {};
		
		
	    $.ajax({
	        url: ctxt+ '/point/send/pointSendPop.do',
	        data: params,
			type : 'POST',
			dataType: 'text',
			cache: false,
	        success: function(result) {	        		        		
	    		totalPointCnt = result;
	    		
	    		//드래그 가능한 div
	    		popHtml += '<div class="draggable-div" style="width: 400px; inset: 210px auto auto 760px; height: 260px;">';
	    			//입력창
	    			popHtml += '<div class="form-group ma_0">';
		        		popHtml += '<label for="inputText" class="form-label"><strong>'+ rsvUserNickNm +'</strong>님께 선물</label>';
		        		popHtml += '<br>';
		        		popHtml += '<label for="inputText" class="form-label item-have">보유한 포인트 개수 : '+  addComma(Math.trunc(result)) +' Point</label>';
		        		popHtml += '<div class="point-to-give">';
			        		popHtml += '<label>선물할 포인트</label>';
			        		popHtml += '<input type="text" class="form-control" id="pointCnt" name="pointCnt" value="" style="width:100%" autocomplete="off"><span> Point</span>';
			        	popHtml += '</div>';
			        	popHtml += '<div class="ma_t_30">';						
			        	    popHtml += '<a href="javascript:goPointSend(\''+rsvUserId+'\' , \''+gbn+'\' ,\''+rsvUserNickNm+'\' , \''+giveNicknm+'\'  );" class="float_n btn btn-primary">선물하기</a>';
			        	    popHtml += '<a href="javascript:closePointPop();" class="btn btn-secondary">닫기</a>';
		        		popHtml += '</div>';
		        	popHtml += '</div>';
		        popHtml += '</div>';
	    		
	    		$("#sendPointZone").html(popHtml);
	    		$(".draggable-div").draggable();
	    		$('html').scrollTop(0); // 저장 후에 맨위로 올려주려고 셋팅	
	    		// 숫자만 입력 
	    	 	$("#pointCnt").on("input", function () {
	    	         var inputValue = $(this).val();
	    	         var intValue = parseInt(inputValue);

	    	         if (isNaN(intValue)) {
	    	             $(this).val("");
	    	         } else {
	    	             $(this).val(intValue);
	    	         }
	    	    }); 
	        },
	        error : function(){                              // Ajax 전송 에러 발생시 실행
	        	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
	        }
	    });
					
	}

	// 포인트 선물하기 팝업 닫기 
	function closePointPop(){	     
		 var element = document.getElementById('sendPointZone');
		 element.innerText = '';
	}

	// 포인트 선물하기 실행
	function goPointSend(rsvUserId , gbn , rsvUserNickNm , sendUserNickNm){  // rsvUserId , rsvUserNickNm 받는사람 정보    sendUserNickNm 주는사람 정보

		if (rsvUserId == "" || rsvUserId == null || rsvUserId == undefined) {
			
	    	fn_showCustomAlert("유저 ID 정보가 존재하지 않습니다.");
			
	    	return false;
		}  
		
		// ** 포인트 선물 validateion STR **
		var totalPoint = Number(totalPointCnt);			// 보유한 전체 포인트 수
		var sendPoint = Number($("#pointCnt").val());		// 선물할 포인트 수
		
		// 포인트 갯수 숫자 정규식
		var check = /^[0-9]+$/;
		if (!check.test(sendPoint)) {
			fn_showCustomAlert("정수인 숫자만 넣어 주세요.");
			$("#pointCnt").val("");
			$("#pointCnt").focus();
			return;
		}
		
		if (sendPoint == "") {
			fn_showCustomAlert("선물할 포인트 개수를 넣어 주세요.");
			$("#pointCnt").focus();
			return;
		}
		
		if (sendPoint == 0) {
			fn_showCustomAlert("0개는 선물할 수 없습니다.");
			$("#pointCnt").focus();
			return;
		}
		
		if(totalPoint < sendPoint){
			fn_showCustomAlert("보유한 포인트 개수보다 큰 값입니다.");
			$("#pointCnt").focus();
			return;
		}
		// ** 포인트 선물 validateion END **
		
		
		var strConfirm;	
		strConfirm = confirm("포인트를 선물하시겠습니까?");
		
		var params = {};
		params.rcv_user_id    = rsvUserId;
		params.pointCnt = $("#pointCnt").val();
		
		if(strConfirm){	
			  $.ajax({
		 		    url: ctxt+'/point/send/goPointSend.do',
		 		    data: params,  
		 		    type: 'POST',
		 		    dataType: 'text',
		 		    cache: false,
		 		   	async: false,
		 		    success: function(result) {
		 		    	
		 		    	if(result == "Y"){// 성공  					
		 		    		 
		 					 
		 					 // 채팅방 message
		 					 if(gbn == 'chat'){
		 						 // 여기서 chatWriter는 받는사람이 된다.
		 						 streamStomp.send('/pub/chat/pointSendMessage.do', {}, JSON.stringify({chatRoomNo: streamChatRoomId, chatMessage: sendUserNickNm +" 님께서 " +rsvUserNickNm +" 님에게 <br/>" +  sendPoint +"포인트를 선물 하였습니다.", chatMessageType:'POINT', chatWriter: rsvUserNickNm}));
		 					 }
		 					fn_showCustomAlert("포인트 선물하기를 완료하였습니다.");
		 					 
		 				  }else if(result == "NS"){
		 					 fn_showCustomAlert("로그인 상태를 다시 확인해 주세요.");
		 				  }else if(result == "ER"){
			        	    	fn_showCustomAlert("선물할 포인트 개수를 다시 확인해 주세요.");	 
		 				  }else{ 					  
		 					 fn_showCustomAlert("포인트 선물에 실패했습니다."); 					  
		 				  }
		 		    	
		 		   },
		 		   error : function(){                  
		 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		 		   },
		 		   complete : function (){
		 			   
		 				 $("#sendPointZone").children().remove();
	 					 fn_totalItemPoint();	// 아이템, 포인트 개수 최신화
		 		   }
		 	});
		}
	}


//******************** 포인트 선물하기 END ********************
		
			
	//******************** 방송 신고하기  STR ********************
	


	// 신고하기 팝업창
	function fn_bcNotify(bc_id , userId){

		if (isEmpty(userId)) {
			
	    	fn_showCustomAlert("로그인 후 이용해 주십시오.");
			
	    	return false;
		}  
		
		
		var popHtml = "";

		//드래그 가능한 div
		popHtml += '<div class="draggable-div" style="width: 500px; inset: 190px auto auto 710px; height: 290px;">';
			//입력창
			popHtml += '<div class="form-group ma_0">';
				popHtml += '<label for="inputText" class="form-label ma_b_20"><strong>방송 신고하기</strong></label>';
				popHtml += '<div class="point-to-give report">';
					popHtml += '<textarea type="text" class="form-control" maxlength="100" id="notify_contents" name="notify_contents" value="" autocomplete="off" placeholder="신고 사유를 적어 주세요.">';
					popHtml += '</textarea>';
				popHtml += '</div>';
				popHtml += '<div class="ma_t_30">';						
					popHtml += '<a href="javascript:goBcNofity(\''+bc_id+'\' , \''+userId+'\');" class="float_n btn btn-primary">신고</a>';
					popHtml += '<a href="javascript:closeBcNotifyPop();" class="btn btn-secondary">닫기</a>';
				popHtml += '</div>';
			popHtml += '</div>';
		popHtml += '</div>';
		
		$("#bcNotifyZone").html(popHtml);
		$(".draggable-div").draggable();
			
		$('html').scrollTop(0); // 저장 후에 맨위로 올려주려고 셋팅
		
			
	}

	// 포인트 선물하기 팝업 닫기 
	function closeBcNotifyPop(){	     
		 var element = document.getElementById('bcNotifyZone');
		 element.innerText = '';
	}

	// 포인트 선물하기 실행
	function goBcNofity(bc_id , userId ){ 
		
		if (isEmpty($("#notify_contents").val())) {
			
	    	fn_showCustomAlert("신고 내용을 작성해 주십시오.");
			
	    	return false;
		}  
		if($("#notify_contents").val().length > 100){
			fn_showCustomAlert("신고 내용은 100자 이내로 작성해 주십시오.");
			
	    	return false;
		}
		if (isEmpty(userId)) {
			
	    	fn_showCustomAlert("로그인 후 이용해 주십시오.");
			
	    	return false;
		}  
		
		var strConfirm;	
		strConfirm = confirm("해당 방송을 신고하시겠습니까?");
		
		var params = {};	
		params.bc_id = bc_id;
		params.notify_contents = $("#notify_contents").val();
		
		
		if(strConfirm){	
			  $.ajax({
		 		    url: ctxt+'/broadCast/bc/goBcNotify.do',
		 		    data: params,  
		 		    type: 'POST',
		 		    dataType: 'text',
		 		    cache: false,
		 		   	async: false,
		 		    success: function(result) {
		 		    	
		 		    	if(result == "Y"){// 성공  					
		 				 fn_showCustomAlert("신고가 접수되었습니다.");
		 				
		 				  }else if(result == "NS"){
		 					 fn_showCustomAlert("로그인 상태를 다시 확인해 주세요.");
		 				  }else if(result == "ER"){
		 					 fn_showCustomAlert("해당 방송이 존재하지 않습니다.");
		 				  }else{ 					  
		 					 fn_showCustomAlert("오류가 발생하였습니다.<br/>새로고침 후 다시 시도해 주십시오.");
		 				  }
		 		    	
		 		   },
		 		   error : function(){                  
		 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		 		   },
		 		   complete : function(){
		 			  closeBcNotifyPop();
		 		   }
		 	});
		}
	}


//********************방송 신고하기 END ********************
		
	
	//******************** VOD 신고하기  STR ********************


	// 신고하기 팝업창
	function fn_vodNotify(vod_id , userId){

		if (isEmpty(userId)) {
			
	    	fn_showCustomAlert("로그인 후 이용해 주십시오.");
			
	    	return false;
		}  
		
		
		var popHtml = "";

		//드래그 가능한 div
		popHtml += '<div class="draggable-div" style="width: 500px; inset: 190px auto auto 710px; height: 290px;">';
			//입력창
			popHtml += '<div class="form-group ma_0">';
				popHtml += '<label for="inputText" class="form-label ma_b_20"><strong>VOD 신고하기</strong></label>';
				popHtml += '<div class="point-to-give report">';
					popHtml += '<textarea type="text" class="form-control" maxlength="100" id="vodNotify_contents" name="vodNotify_contents" value="" autocomplete="off" placeholder="신고 사유를 적어 주세요.">';
					popHtml += '</textarea>';
				popHtml += '</div>';
				popHtml += '<div class="ma_t_30">';						
					popHtml += '<a href="javascript:goVodNofity(\''+vod_id+'\' , \''+userId+'\');" class="float_n btn btn-primary">신고</a>';
					popHtml += '<a href="javascript:closeVodNotifyPop();" class="btn btn-secondary">닫기</a>';
				popHtml += '</div>';
			popHtml += '</div>';
		popHtml += '</div>';
		
		$("#vodNotifyZone").html(popHtml);
		$(".draggable-div").draggable();
			
		$('html').scrollTop(0); // 저장 후에 맨위로 올려주려고 셋팅
		
			
	}

	// 포인트 선물하기 팝업 닫기 
	function closeVodNotifyPop(){	     
		 var element = document.getElementById('vodNotifyZone');
		 element.innerText = '';
	}

	// 포인트 선물하기 실행
	function goVodNofity(vod_id , userId ){ 
		
		if (isEmpty($("#vodNotify_contents").val())) {
			
	    	fn_showCustomAlert("신고 내용을 작성해 주십시오.");
			
	    	return false;
		}  
		if($("#vodNotify_contents").val().length > 100){
			fn_showCustomAlert("신고 내용은 100자 이내로 작성해 주십시오.");
			
	    	return false;
		}
		if (isEmpty(userId)) {
			
	    	fn_showCustomAlert("로그인 후 이용해 주십시오.");
			
	    	return false;
		}  
		
		var strConfirm;	
		strConfirm = confirm("해당 방송을 신고하시겠습니까?");
		
		var params = {};	
		params.ch_vod_id = vod_id;
		params.notify_contents = $("#vodNotify_contents").val();
		
		
		if(strConfirm){	
			  $.ajax({
		 		    url: ctxt+'/opsmng/vodNotify/goVodNotify.do',
		 		    data: params,  
		 		    type: 'POST',
		 		    dataType: 'text',
		 		    cache: false,
		 		   	async: false,
		 		    success: function(result) {
		 		    	
		 		    	if(result == "Y"){// 성공  					
		 				 fn_showCustomAlert("신고가 접수되었습니다.");
		 				
		 				  }else if(result == "NS"){
		 					 fn_showCustomAlert("로그인 상태를 다시 확인해 주세요.");
		 				  }else if(result == "ER"){
		 					 fn_showCustomAlert("해당 VOD가 존재하지 않습니다.");
		 				  }else{ 					  
		 					 fn_showCustomAlert("오류가 발생하였습니다.<br/>새로고침 후 다시 시도해 주십시오.");
		 				  }
		 		    	
		 		   },
		 		   error : function(){                  
		 		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		 		   },
		 		   complete : function(){
		 			  closeVodNotifyPop();
		 		   }
		 	});
		}
	}


//********************VOD 신고하기 END ********************
			
//********* 메인화면에서 보유 아이템, 보유 포인트 불러오기 STR **********

function fn_totalItemPoint(){
	var totalItem = "";
	var totalPoint = "";
     
     $.ajax({
		    url: ctxt + '/member/inform/totalItemPoint.do',
		    type: 'POST',
		    dataType: 'json',
		    cache: false,
//		    async: false,
		    success: function(result) {
		    	
		    	if( !isEmpty(result)){		    		
//		    		if(result.totalItemCnt != "" && result.totalItemCnt != null && result.totalItemCnt != undefined){
		    			totalItem = result.totalItemCnt;
		    			totalItemRsv = result.totalItemRsvCnt 
			    		totalPoint = result.totalPointCnt;
		    			
			    		// left상단 정보
			    		$('#itemId').html(addComma(totalItem));
			    		$('#itemRsvId').html(addComma(totalItemRsv));	
			    		
			    		// 모의투자에서 엣지의 개념이 포함되므로 음수(-) 일때 포인트는 0으로 셋팅
		    			if(totalPoint < 0){
		    				totalPoint = 0;
		    				$('#pointId').html(totalPoint);
		    			}else{
		    				$('#pointId').html(addComma(Math.trunc(totalPoint)));
		    			}
		    			
		    			// 현물 > 매수 > 시장가
//		    			var totalDalla = addComma(Number(totalPoint.toFixed(8)));	// 1달려 = 1포인트. 추후 비율 정해지면 '/1' 부분 수정
//		    			$('#buy_mk_av_lb').html(totalDalla);
//		    			$('#buy_lm_av_lb').html(totalDalla);
//		    			$('#open_lm_av_lb').html(totalDalla);
//		    			$('#open_mk_av_lb').html(totalDalla);
//		    		}
		    		
		    	}else{
		    		totalItem = 0;
		    		totalPoint = 0;
		    		
		    		// left상단 정보
		    		$('#itemId').html(totalItem);
		    		$('#itemRsvId').html(0);	
	    			$('#pointId').html(totalPoint);
		    				    			
		    	}
		   },
		   error : function(){                  
//		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		   }
	}); 
}		
	
//********* 메인화면에서 보유 아이템, 보유 포인트 불러오기 END ********** 

//********* 모의 투자 화면에서 Available Point (거래 가능 point) 불러오기 STR **********
function fn_availablePoint(){

	var availablePoint = "";
     
     $.ajax({
		    url: ctxt + '/simInvest/futures/totalAvailablePoint.do',
		    type: 'POST',
		    dataType: 'json',
		    cache: false,
//		    async: false,
		    success: function(result) {
		    	
		    	if( !isEmpty(result)){		    		
		    		
		    		availablePoint = result.availablePoint;
			    		 
		    			// 현물, 선물 > 매수 > 거래 가능 금액
		    			var totalAvailablepoint = addComma(Number(availablePoint.toFixed(8)));
		    			$('#buy_mk_av_lb').html(totalAvailablepoint);
		    			$('#buy_lm_av_lb').html(totalAvailablepoint);
		    			$('#open_lm_av_lb').html(totalAvailablepoint);
		    			$('#open_mk_av_lb').html(totalAvailablepoint);
//		    		}
		    		
		    	}else{
		    		availablePoint = 0;
		    				    			
		    	}
		   },
		   error : function(){                  
//		     fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
		   }
	}); 
}		
//********* 모의 투자 화면에서 Available Point (거래 가능 point) 불러오기 END **********


//방문 채털  또는 마이	채널  등 프로필 이미지를 셋팅하기 위해 채널 정보를 가져오기 채널명 , 프로필 등등 .. 마이채널관리 메뉴 > 채널 관리 에서 사용되는 항목
// 타겟 object , 채널 구분, 방문채널 유저 id
function fn_chProfilImg(obj , gbn , chUserId){
	var html = '';	
	var params = {};
	params.chGbn    = gbn;
	params.chUserId    = chUserId;


	$.ajax({
		url : ctxt+'/myCh/chMng/chUserInfo.do',
		type:'POST',
		data:params,	
		dataType:"json", 
		//async : false,
		success : function(result) {
			

			if(!isEmpty(result)){
				if(!isEmpty(result.chInfo)){
					if(!isEmpty(result.chInfo.atch_img_id)){
						html += '<img class="card-img-top"  src="'+ctxt+'/cmm/fms/getImage.do?atch_img_id='+result.chInfo.atch_img_id+'&fileSn=0" alt="프로필 이미지" >';
					}else{
						html += '<img class="card-img-top"  src="'+ctxt+'/resources/images/sub/profile.png" alt="프로필 이미지" >';
					}
				}else{
					html += '<img class="card-img-top"  src="'+ctxt+'/resources/images/sub/profile.png" alt="프로필 이미지" >';
				}
			}else{
				html += '<img class="card-img-top"  src="'+ctxt+'/resources/images/sub/profile.png" alt="프로필 이미지" >';
			}
			$('#'+obj).html(html);
		},
		error : function() { // Ajax 전송 에러 발생시 실행
			console.log(' fn_chProfilImg(obj) 오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.');
			html += '<img class="card-img-top"  src="'+ctxt+'/resources/images/sub/profile.png" alt="프로필 이미지" >';
			$('#'+obj).html(html);
		},
		complete : function(result) { //  success, error 실행 후 최종적으로 실행
			
		}
	});

}

// 마이 채널 대문 이미지
function fn_chMainImg(obj , gbn , chUserId){
	var html = '';	
	var params = {};
	params.chGbn    = gbn;
	params.chUserId    = chUserId;


	$.ajax({
		url : ctxt+'/myCh/chMng/chUserInfo.do',
		type:'POST',
		data:params,	
		dataType:"json", 
		//async : false,
		success : function(result) {
			

			if(!isEmpty(result)){
				if(!isEmpty(result.chInfo)){
					if(!isEmpty(result.chInfo.atch_ch_img_id)){
						html += '<img class="mych-img-top"  src="'+ctxt+'/cmm/fms/getImage.do?atch_img_id='+result.chInfo.atch_ch_img_id+'&fileSn='+result.chInfo.file_sn+'" alt="프로필 이미지" >';
					}else{
						html += '<img class="mych-img-top"  src="'+ctxt+'/resources/images/new/mych_img.png" alt="프로필 이미지" >';
					}
				}else{
					html += '<img class="mych-img-top"  src="'+ctxt+'/resources/images/new/mych_img.png" alt="프로필 이미지" >';
				}
			}else{
				html += '<img class="mych-img-top"  src="'+ctxt+'/resources/images/new/mych_img.png" alt="프로필 이미지" >';
			}
			$('#'+obj).html(html);
		},
		error : function() { // Ajax 전송 에러 발생시 실행
			console.log(' fn_chMainImg(obj) 오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.');
			html += '<img class="mych-img-top"  src="'+ctxt+'/resources/images/new/mych_img.png" alt="프로필 이미지" >';
			$('#'+obj).html(html);
		},
		complete : function(result) { //  success, error 실행 후 최종적으로 실행
			
		}
	});

}


var timerInterval; // 타이머 인터벌 변수
var remainingTime = 0; // 초기 남은 시간 (초)


//참여 유저  cnt  정보 및 방송 상태 정보를 가져온다.  (스트리밍 상세 , 방송 설정)
function fn_bcViewerAndStatusInfo(val){
	if( !isEmpty(val)){
		var params = {};
  			params.bc_id = val;
  		

	 	$.ajax({
			url : ctxt+"/broadCast/bc/readBcAddInfo.do", 
			type : 'POST',
			data : params,
			dataType : 'json',                 
			cache: false,			
			success : function(result) {
				if(!isEmpty(result) && !isEmpty(result.viewerInfo)){
					$("#nowUserCnt").text(result.viewerInfo.now_user_cnt);
					$("#accuUserCnt").text(result.viewerInfo.accu_user_cnt);
					

					// 상태 정보를 이용해 타이머를 실행					
					if(result.viewerInfo.live_status == 'I' && result.viewerInfo.chk_status == 'ING'){
						if(isEmpty(localStorage.getItem('timerValue'))){	
							localStorage.setItem('timerValue' ,result.viewerInfo.end_sec);
							
						    startTimer(result.viewerInfo.end_sec);
						    
						}
					}else{
						if(!isEmpty(localStorage.getItem('timerValue'))){
							   $("#cover_wait").hide();
							   localStorage.removeItem('timerValue');
							  
					           clearInterval(timerInterval); // 타이머 종료
						}
					}
					
					// 방송 설정화면에서 방송중 또는 방송 대기중 / 참여인원수  업데이트를 막기위해 						
					if((result.viewerInfo.live_status == 'I' && result.viewerInfo.chk_status == 'ING' )||result.viewerInfo.live_status == 'Y'){

						if(result.viewerInfo.use_item_yn == 'Y'){
										
							$("#use_item_yn_text").text(result.viewerInfo.use_item_nm + ' 끌올 아이템 사용 중');
							
						}else{
								
							$("#use_item_yn_text").text('');
						}  
						
						//방송 대기중 						
						  $("#bcs_wait_set").attr("readonly", "readonly");

						  // onFocus 및 onChange 속성 추가
						  $("#bcs_wait_set").attr("onfocus", "this.initialSelect = this.selectedIndex;");
						  $("#bcs_wait_set").attr("onchange", "this.selectedIndex = this.initialSelect;");
						  
						  // 참여인원
						  $("#bcs_max").prop("disabled", true);
						  
						// 방송 상태
						  if(result.viewerInfo.live_status == 'Y'){
							  $("#live_status").val("Y");
						  }else{
							  $("#live_status").val("N");
						  }
					}else{
						//아이템 종료 
						$("#use_item_yn_text").text('');
						//방송 대기중 							
						 $("#bcs_wait_set").removeAttr("readonly");

						  // onFocus 및 onChange 속성 제거
						  $("#bcs_wait_set").removeAttr("onfocus");
						  $("#bcs_wait_set").removeAttr("onchange");
						  
						  // 참여인원
						  $("#bcs_max").prop("disabled", false);
						  
						  // 방송상태
						  $("#live_status").val("N");
					}
			
				}else{
					$("#nowUserCnt").text(0);
					$("#accuUserCnt").text(0);
					
					$("#use_item_yn_text").text('');
					
				    $("#live_status").val("N");
				}
			},
			error : function() {
			
				console.log('오류가 발생했습니다. 관리자에게 문의 바랍니다.');
			}
	    });
		
	}
}
	

function startTimer(val) {
    clearInterval(timerInterval); // 기존 타이머를 초기화
    remainingTime = val; // 남은 시간을 60으로 초기화
    
    // 1초마다 타이머를 갱신하고 화면에 표시
    timerInterval = setInterval(function() {
        if (remainingTime > 0) {
        	$("#cover_wait").show();
        
            //$("#cover_wait").text('<p><i class="far fa-pause-circle"></i>방송 대기 '+remainingTime+' 초  <br/>BJ가 대기 시간 안에 방송을 시작하면 시청하실 수 있습니다.</p>');
        	$("#cover_text").html('방송 대기 '+remainingTime+' 초  <br/>BJ가 대기 시간 안에 방송을 시작하면 시청하실 수 있습니다.');
           
        	
          
            remainingTime--;
            localStorage.setItem('timerValue', remainingTime);
        } else {
        	$("#cover_wait").hide();
            clearInterval(timerInterval); // 타이머 종료
            
         
            
            localStorage.removeItem('timerValue');
        }
    }, 1000);
}

