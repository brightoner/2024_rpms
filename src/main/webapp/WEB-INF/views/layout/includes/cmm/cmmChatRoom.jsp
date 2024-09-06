<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
   
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">

<style>
    
    .chat-container {
	    max-width: 800px;
	    height: auto;
	    margin: 0 auto;
	    background-color: #fff;
	    border-radius: 10px;
	    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	    border: 1px solid #eee;
	}
    
    .chat-header {

      height: 40px;
	background-color: #F97E15;
	padding: 12px;
	color: #fff;
	font-weight: bold; 
	text-align: center;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
	position: relative;
    }
    
    .chat-messages {
      max-height: 400px;
      height : 500px;   
      overflow-y: scroll;
      padding: 10px;
    }
    
    
    .message {
      display: flex;
      align-items: flex-start;
      margin-bottom: 5px;
    }
    
    .sender-info {
      display: flex;
      align-items: center;
   /*    margin-bottom: 5px; */
    }
    
    .sender-name {
      margin-right: 5px;
      color : #314873;      
    }
    
    .message-time {
      color: #999;
      font-size: 12px;
    }
    
    .message-content {
	    width: 100%;
	}
    .message-text {
	    color: #000;
	    background-color: #f2f2f2;
	    border-radius: 10px;
	    padding: 5px 10px;
	    margin: 4px 0 8px;
	    display: inline-block;
	    max-width: 90%;
	    position: relative;
	    left: 5px;
	    overflow: hidden;
	}
    
    .own-message {
      flex-direction: row-reverse;
    }
    
    .own-message .message-content {
      background-color: #DCF8C6;
    }
    
    .own-message .sender-info {
      justify-content: flex-end;
    }
    
    .own-message .sender-name {
      margin-left: 5px;
      margin-right: 0;
    }
    
    .own-message .message-time {
      order: -1;
    }
    
/*     .chat-input {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 5px;
    }
 */
     .chat-input {
	    display: grid;
	    align-items: center;
	    padding: 5px;
	    grid-template-columns: auto 80px;
	    gap: 5px;
	}
    
    .chat-input input {
      height: 40px;
      padding: 8px;
      border-radius: 5px;
      border: 1px solid #ccc;
      width: 100%;
    }
    
    .chat-input button, .chat-input a.btn {
      width: 80px;
      height: 40px;
      border-radius: 5px;
      background-color: #fff;
      color: #5e5e5e;
      border: 1px solid #dbdbdb;
      cursor: pointer;
    }
    
    .chat-input button:hover{
	    color: #fff !important;
	    border-color: #ff9813;
	    background: #ff9813;
	}
		
    @media screen and (max-width: 600px) {
      .chat-container {
        max-width: 90%;
      }
    }
    
    
.icons-container {
	position: absolute;
	top: 50%;
	right: 10px;
	transform: translateY(-50%);
}

.icons-container i {
	font-size: 19px; /* 아이콘의 크기를 20픽셀로 설정 */
	color: #fff; /* 아이콘의 색상을 검정색으로 설정 */
	margin-left: 5px; /* 아이콘 왼쪽 여백을 5픽셀로 설정 */
}
    
.userList-blackList-main {
	display: none;
	position: absolute;
/* 	max-height: 300px; */
/* 	max-width: 370px; */
	width: 450px;
	top: 40px;

	border: 1px solid #ddd;
	padding: 3px;
	background: #fff;
	z-index: 9999;
}

.stream-chat-userList-blackList-main {
    border: 1px solid transparent; 
	border-color : #314873;
	height: 40px;
	background-color: #314873;
	padding: 10px;
	color: #fff;
	/* font-weight: bold; */
	text-align: left;
	position: relative;	
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.refresh-button, .chatuser-close-button {
	width: 24px;
	height: 24px;
	background-color: #fff;
	border: 1px solid #ccc;
	border-radius: 50%;
	padding: 4px;
	cursor: pointer;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-left: 5px;
	 transition: opacity 0.3s ease-in-out;
}

.button-group {
	display: flex;
	align-items: center;
}

.refresh-button:hover, .chatuser-close-button:hover  {
	background-color: #f2f2f2;
    opacity: 0.7;
}

.refresh-button i {
	font-size: 12px;
    stroke-width: 1.5px;
    color: #314873;
}


.chatuser-close-button i {
	font-size: 12px;
    stroke-width: 1.5px;
    color: #314873;
}

.main-chat-userList-layer2 {
    border: 1px solid transparent;
	border-color : #306090;
	height: 40px;
	background-color: #306090;
	padding: 10px;
	color: #fff;
	/* font-weight: bold; */
	text-align: left;
	position: relative;
	display: flex;
	justify-content: space-between;
	align-items: center;
}					
						
						
.main-sender-info {
	display: flex;
	align-items: center;
}

.main-sender-name {
	margin-right: 5px;
	color: #314873;
}

.main-userList-layer2{
    display: none;
    position: absolute;
    max-height: 300px;
    max-width: 250px;
    width: 100%;
   top: 200px; 
    /* right: 10px; */
    border: 1px solid #ddd;
    padding: 3px;
    background: #fff;
    z-index: 2;
}


.main-userInfo-layer2 {
	position: relative;
	height: 25px;
    border: 1px solid #dbdbdb;
    margin-bottom: 2px;
    margin-top: 2px;
}

.main-userInfo-layer2 > a {
	display: flex;
	align-items: center;
	height: 100%;
	padding: 0 14px;
	transition: all 0.3s cubic-bezier(0.56, 0.12, 0.12, 0.98);
	font-size:12px;
}

</style>
  
 
<script type="text/javascript">
	var lastMessageTime = 0; // 마지막 메시지 시간 초기화
	var delayBetweenMessages = 1000; // 메시지 간 딜레이 (밀리초 단위)
	var maxConsecutiveMessages = 5; // 최대 연속 메시지 허용 횟수
	var consecutiveMessageCnt = 0; // 현재 연속 메시지 수
	var messageCooldownTime = 10000; // 메시지 전송 제한 시간 (밀리초 단위)
	var chatEnabled = true; // 채팅 활성화 상태
	
	var roomName = '${mainRoom.chatTitle}';
	var roomId = '${mainRoom.chatRoomNo}';
	var userNicknm = '${userNicknm}';
	var mainChatUserId = '${mainChatUserId}';
	var chatLoginYn = '${chatLoginYn}';
	var chatMainhtml = '';
	
	//console.log( roomName + ", " + roomId + ", " + userNicknm);
	var sockJS = '';
	var stomp = '';
	
	

	$(function() {
	  
		if(roomId != null && roomId != '' && roomId != undefined){
			fn_mainChatMessage(roomId); // 이전 채팅 내용을 불러온다
			//if( userNicknm != null && userNicknm != '' && userNicknm != undefined){
			
				sockJS =  new SockJS(chatEndPoint);
		

				stomp = Stomp.over(sockJS);
				fn_chatConnect();
			//}
		}
		
		$("#button-send").on("click", function(e){
			 
			
			
			//console.log($('#chat-messages > .message').length);
			var msg = document.getElementById("chatMsgInput");
			
			if(chatLoginYn != 'Y'){
				fn_showCustomAlert('로그인 후 채팅 이용이 가능합니다.');
				msg.value = '';
				
				return false;  
			}
			if(isEmpty(msg.value)){
		 		return false;
		 	}
			
			// 블랙리스트 채크 str
		    var chkStatus = fn_mainChatBlackListChk();
		    
		    if(chkStatus == 'C' || chkStatus == 'X'){
		    	fn_showCustomAlert("관리자에 의해서 채팅이 금지된 사용자입니다.");
		    	return false;
		    }else if(chkStatus == 'NOUSER'){
		    	fn_showCustomAlert("세션이 만료되었습니다. 다시 로그인 해주세요.");	
		    	
		    	return false;
		    }
		 	// 블랙리스트 채크 end
		 	
			if(roomId == null || roomId == '' || roomId == undefined
			|| userNicknm == null || userNicknm == '' || userNicknm == undefined){
				fn_showCustomAlert('채팅방이 활성화 되지 않았습니다. <br/>다시 로그인 해주십시오.');
				msg.value = '';
				return false;
			}
			
			
			fn_mainChatWait(); // 도배방지
	       
	        // send(path, header, message)로 메시지를 보낼 수 있다. 
	        // StompChatController의 @MessageMapping(value = "/chat/message") 부분으로 메시지가 보내진다.
			stomp.send('/pub/chat/main/message.do', {}, JSON.stringify({chatRoomNo: roomId, chatMessage: msg.value,chatMessageType:'TEXT', chatWriter: userNicknm}));
			msg.value = '';
			
		
		});
	 }); 

	function submitChatMainOnEnter(event) {
		  if (event.keyCode === 13) { // 13은 Enter 키의 keyCode입니다.
			  event.preventDefault(); // 기본 동작인 줄바꿈을 방지합니다.
			 
			
			var msg = document.getElementById("chatMsgInput");
			
			if(chatLoginYn != 'Y'){
				fn_showCustomAlert('로그인 후 채팅 이용이 가능합니다.');
				msg.value = '';
				
				return false;  
			}
			if(isEmpty(msg.value)){
		 		return false;
		 	}
		 	
			
			// 블랙리스트 채크 str
		    var chkStatus = fn_mainChatBlackListChk();
		    
		    if(chkStatus == 'C' || chkStatus == 'X'){
		    	fn_showCustomAlert("관리자에 의해서 채팅이 금지된 사용자입니다.");
		    	return false;
		    }else if(chkStatus == 'NOUSER'){
		    	fn_showCustomAlert("세션이 만료되었습니다. 다시 로그인 해주세요.");	
		    	
		    	return false;
		    }
		 	// 블랙리스트 채크 end
			
			if(roomId == null || roomId == '' || roomId == undefined
					|| userNicknm == null || userNicknm == '' || userNicknm == undefined){
				fn_showCustomAlert('채팅방이 활성화 되지 않았습니다. <br/>다시 로그인 해주십시오.');
				msg.value = '';
				return false;
			}
			
			fn_mainChatWait(); // 도배방지
			
	        // send(path, header, message)로 메시지를 보낼 수 있다. 
	        // StompChatController의 @MessageMapping(value = "/chat/message") 부분으로 메시지가 보내진다.
			stomp.send('/pub/chat/main/message.do', {}, JSON.stringify({chatRoomNo: roomId, chatMessage: msg.value,chatMessageType:'TEXT', chatWriter: userNicknm}));
			
		
	        // 입력 필드 초기화
	        msg.value = '';
		  }
	}
	
	function fn_saveChatMainMessage(chatParam){
		
		var params = {};
	  		params.chatRoomNo = chatParam.chatRoomNo;
	  		params.chatWriter=chatParam.chatWriter;
	  		params.chatMessage=chatParam.chatMessage;

		 	$.ajax({
				url : "${ctxt}/chat/room/saveChatMainMessage.do", 
				type : 'POST',
				data : params,
				dataType : 'json',
				cache: false,
				success : function(result) {
					
				},
				error : function() {
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.', 'e');
					//console.log('fn_saveChatStreamMessage  메시지 저장 오류가 발생했습니다 관리자에게 문의 바랍니다.');
				}
			});
			 
		}
	
	function fn_chatConnect(){
		// StompWebSocketConfig의 EndPoint로 작성했던 부분 "/stomp/chat"
		var mainChatIdx = 100;
		// Stomp 연결 시 실행
		stomp.connect({}, function (){
			console.log("Stomp Connection")
		  
			// subscribe(path, callback)으로 메세지를 받을 수 있다. 
	        // StompChatController의 @MessageMapping(value = "/chat/message") 부분 중 template.convertAndSend()를 통해 메시지가 전달된다.
			stomp.subscribe("/sub/chat/main/room/" + roomId, function (chat) {
				mainChatIdx++;	
				var content = JSON.parse(chat.body);	
				var writer = content.chatWriter;
				var writerId = content.chatWriterId;
				var chatMessageType = content.chatMessageType;
				var message = content.chatMessage;
				var str = '';				
				if(chatMessageType == 'TEXT'){ // 일반 TEXT 메시지 데이터
						if(writer === userNicknm) {
							// 메시지를 저장한다.
							// 꼭 위치를 여기 둘것. 자신이 쓴 메시지만 저장되어야한다.
							fn_saveChatMainMessage(content); 
							
							// var str='<div class="message own-message">';
							 var str='<div class="message">';
						     	str+='<div class="message-content">';
				    	 	 		str+='<div class="sender-info">';
					    		 		    str+='<div class="sender-name">'+writer+' :'+'</div>';
					    			 		//str+='<div class="message-time">10:35 AM</div>';
				    				str+='</div>';
			   				        str+='<div class="message-text">'+message+'</div>';
							 	str+='</div>';
						    str+='</div>';
						    
							$("#chat-messages").append(str);
						} else {
						    var str='<div class="message">';
							     	 str+='<div class="message-content">';
					    	 	 		str+='<div class="main-sender-info">';
					    	 	 			str+='<a href="javascript:void(0);" onclick="javascript:fn_mainChatUserClickLayer(\''+mainChatIdx+'\');" class="" title="스트리밍 유저 블랙리스트 기능">';
					    		 		    	str+='<div class="main-sender-name">'+writer+' :'+'</div>';
					    		 		    str+='</a>';			    					 
					    				str+='</div>';
				   				        str+='<div class="message-text">'+message+'</div>';
								 	str+='</div>';
						    	str+='</div>';
						   	 // 사용자 제어 추가 팝업 div str
						   str +='<sec:authorize access="hasRole(\'ROLE_ADMIN\')">';  	 
							    str +='<div class="main-userList-layer2" id="main-userCon-layer2-'+mainChatIdx+'" style="display: none;">'; //div str
			    					str +='<div class="main-chat-userList-layer2">';										
			    						str +=writer +'('+ writerId + ')'; 
			    						str +='<div class="button-group">';	    								  
			    								str +='<div class="chatuser-close-button" onclick="javascript:fn_mainChatUserClickLayer(\''+mainChatIdx+'\');" title="닫기 버튼">';
												str +='<i class="fas fa-times"></i>';
												str +='</div>';
										str +='</div>';
			    					str +='</div>';
			    					str +='<div>';    				
			    						str +='<div class ="main-userInfo-layer2">';
			    							str +='<a href="javascript:void(0);" onclick="javascript:fn_mainChatBlackListSet(\''+writerId+'\' , \''+writer+'\' , \'C\', \''+mainChatIdx+'\');" class="" title="스트리밍 유저 블랙리스트 기능">';
			    							str +='채팅 금지';
			    							str +='</a>';														
			    						str +='</div>';    				
			    					str +='</div>';				
								str +='</div>';		 //div end
								 str +='</sec:authorize>';
								// 사용자 제어 추가 팝업 div end
								$("#chat-messages").append(str);
						}
				}
				// 채팅 메세지가 50가 넘어가면 맨 위에거 부터 하나씩 지운다.
				if($('#chat-messages > .message').length > 50){
					$('#chat-messages > .message').eq(0).remove();
				}
				
				fn_scrollToBottom();
				
			});
			 // 연결 종료 시 처리
		    $(window).on('beforeunload', function() {
				  stomp.disconnect();
	        }); 
		});

	}
	
	/*
	* 이전 채팅 내용 불러오기
	*/
	function fn_mainChatMessage(roomId){
		var mainDbChatIdx = 1000;
		$.ajax({
			url:"${ctxt}/chat/mainChatRoom/readMessage.do",
			type:'POST',
			data:{"chatRoomNo":roomId},
			dataType:"text",
			success:function(rtnXml){
				
				var xmlObj = $(rtnXml).find('item');

	        	if(xmlObj.length > 0){

					xmlObj.each(function(cnt){
						mainDbChatIdx++;	
						var obj = $(this);
						var chatNo =  obj.find('chatNo').text();
						var chatRoomNo =  obj.find('chatRoomNo').text();
						var chatMessage =  obj.find('chatMessage').text();						
						var chatWriter =  obj.find('chatWriter').text();	
						var chatWriterId =  obj.find('chatWriterId').text();	
					//	console.log(chatWriter + '::'+ userNicknm);	
						if(chatWriter === userNicknm) {
						
						  // chatMainhtml+='<div class="message own-message">';
						   chatMainhtml+='<div class="message">';
							 chatMainhtml+='<div class="message-content">';
								 chatMainhtml+='<div class="sender-info">';
									 chatMainhtml+='<div class="sender-name">'+chatWriter+' :'+'</div>';
			    				chatMainhtml+='</div>';
			    				chatMainhtml+='<div class="message-text">'+chatMessage+'</div>';
		    				chatMainhtml+='</div>';
		    				chatMainhtml+='</div>';
					    
						
						}else{
								 chatMainhtml+='<div class="message">';
						     	 chatMainhtml+='<div class="message-content">';
				    	 	 		chatMainhtml+='<div class="main-sender-info">';
				    	 	 			chatMainhtml+='<a href="javascript:void(0);" onclick="javascript:fn_mainChatUserClickLayer(\''+mainDbChatIdx+'\');" class="" title="스트리밍 유저 블랙리스트 기능">';
				    		 		    	chatMainhtml+='<div class="main-sender-name">'+chatWriter+' :'+'</div>';
				    		 		    chatMainhtml+='</a>';			    					 
				    				chatMainhtml+='</div>';
			   				        chatMainhtml+='<div class="message-text">'+chatMessage+'</div>';
							 	chatMainhtml+='</div>';
					    	chatMainhtml+='</div>';
					   	 // 사용자 제어 추가 팝업 div chatMainhtml
					   chatMainhtml +='<sec:authorize access="hasRole(\'ROLE_ADMIN\')">';  	 
						    chatMainhtml +='<div class="main-userList-layer2" id="main-userCon-layer2-'+mainDbChatIdx+'" style="display: none;">'; //div chatMainhtml
		    					chatMainhtml +='<div class="main-chat-userList-layer2">';										
		    						chatMainhtml +=chatWriter +'('+ chatWriterId + ')'; 
		    						chatMainhtml +='<div class="button-group">';	    								  
		    								chatMainhtml +='<div class="chatuser-close-button" onclick="javascript:fn_mainChatUserClickLayer(\''+mainDbChatIdx+'\');">';
											chatMainhtml +='<i class="fas fa-times"></i>';
											chatMainhtml +='</div>';
									chatMainhtml +='</div>';
		    					chatMainhtml +='</div>';
		    					chatMainhtml +='<div>';    				
		    						chatMainhtml +='<div class ="main-userInfo-layer2">';
		    							chatMainhtml +='<a href="javascript:void(0);" onclick="javascript:fn_mainChatBlackListSet(\''+chatWriterId+'\' , \''+chatWriter+'\' , \'C\', \''+mainDbChatIdx+'\');" class="" title="스트리밍 유저 블랙리스트 기능">';
		    							chatMainhtml +='채팅 금지';
		    							chatMainhtml +='</a>';														
		    						chatMainhtml +='</div>';    				
		    					chatMainhtml +='</div>';				
							chatMainhtml +='</div>';		 //div end
							 chatMainhtml +='</sec:authorize>';
		    			
						}
					});			
				
					$("#chat-messages").append(chatMainhtml);
					fn_scrollToBottom();
					
					
				}else{
					
				}

			},
			error:function(){
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			}
		});
	}
	
	function fn_saveChatMainMessage(chatParam){
		
		var params = {};
	  		params.chatRoomNo = chatParam.chatRoomNo;
	  		params.chatWriter=chatParam.chatWriter;
	  		params.chatMessage=chatParam.chatMessage;

		 	$.ajax({
				url : "${ctxt}/chat/room/saveChatMainMessage.do", 
				type : 'POST',
				data : params,
				dataType : 'json',
				cache: false,
				success : function(result) {
					
				},
				error : function() {
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.', 'e');
					//console.log('fn_saveChatMessage  메시지 저장 오류가 발생했습니다 관리자에게 문의 바랍니다.');
				}
			});
			 
		}

	 // 도배방지
	function fn_mainChatWait(){
		var currentTime = new Date().getTime(); // 현재 시간
		var remainingCooldownTime = Math.ceil((lastMessageTime + messageCooldownTime - new Date().getTime()) / 1000);
		    
	    if (!chatEnabled) {
	      var remainingCooldownTime = Math.ceil((lastMessageTime + messageCooldownTime - new Date().getTime()) / 1000);
	      fn_showCustomAlert('제한 시간 동안 채팅이 비활성화됩니다. ' + remainingCooldownTime + '초 동안 대기하세요.');
	      return;
	    }
	    
		// 연속으로 메시지를 보낸 경우 카운트 증가
		if (currentTime - lastMessageTime < delayBetweenMessages * maxConsecutiveMessages) {
			      consecutiveMessageCnt++;
			      if (consecutiveMessageCnt >= maxConsecutiveMessages) {
			        
			    	  var remainingCooldownTime = Math.ceil((lastMessageTime + messageCooldownTime - currentTime) / 1000);
			          fn_showCustomAlert('연속으로 메시지를 너무 많이 전송하고 있습니다!');
			          chatEnabled = false; // 채팅 비활성화
			        
			       	  var cooldownInterval = setInterval(function () {
							          remainingCooldownTime = Math.ceil((lastMessageTime + messageCooldownTime - new Date().getTime()) / 1000);
							          if (remainingCooldownTime <= 0) {
							           	 	clearInterval(cooldownInterval);
							            	chatEnabled = true;
							         
							          }
						      }, 1000);
		        
		       			 return;
		      		}
	  } else {
	    consecutiveMessageCnt = 0;
	  }
		 
		 
	  // 메시지를 보낸 후 시간 갱신
	  lastMessageTime = currentTime;
	}


	// 채팅창 스크롤
	function fn_scrollToBottom() {
		var chatContainer = $('#chat-messages');		
	    chatContainer.scrollTop(chatContainer.prop('scrollHeight'));
	}
    
	/*
	* 블랙리스트  유저 조회
	*/
	var mainBlackcuurPage;
	var mainBlackPagetotalCnt=0;
	
	function fn_chatMainUserBlackList(page){
		var html;
		//현재 페이지 세팅
		mainBlackcuurPage= page;
	
		var params = {};
			params.page    = mainBlackcuurPage;   
  		
		$.ajax({
			url:"${ctxt}/opsmng/chatBlackList/readMainChatBlackList.do",
			type : 'POST',
			data : params,
			dataType : 'json',
			cache: false,
			success:function(result){
				if(!isEmpty(result) && !isEmpty(result.blackList)){
					
						var start_num = Number(result.blackListTotal) - ((mainBlackcuurPage -1) *10);
						pagetotalCnt =Number(result.blackListPageTotal);	
						
						$.each(result.blackList, function(idx, item){
							html += '<tr>';
							html += '<td class="text_c">'+'<input type="checkbox" name="chkMainBlackObj" title="선택하기'+idx+'" value='+item.bl_id+'>'+'<input type="hidden" name='+item.bl_id+' id='+item.bl_id+' value='+item.bl_id+'>'+'</td>';		
							html += '<td>'+item.bl_nicknm+'('+item.bl_userid+')'+'</td>';            			       			        				      
					        html += '<td>'+item.bl_ban_yn+'</td>';
							html += '</tr>';
							
						});
						
						//트리코드 선택시 첫번째 행 선택
						//페이징처리
					    $('#mainBlackPaging').paging({
					    	
							 current:mainBlackcuurPage
							,max:pagetotalCnt
							,length:pageLen
							,onclick:function(e,page){
								cuurPage=page;
								fn_chatMainUserBlackList(mainBlackcuurPage);
							}
						});
						
					 	$('#mainBlackListData').html(html);
					
				}else{
					/***************************************************************
					* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
					****************************************************************/
					$('#mainBlackPaging').children().remove();
					
					$("#mainBlackListData").html('<tr><td colspan="3" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
				}
			
				
			},
			error:function(){
				fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			}
		});
		$('.userList-blackList-main').show();
		$('.userList').hide();
		 
	}

	
	// 블랙리스트 삭제 
	function fn_mainChat_delBlack(){
		  var chk_blackList_id = "";
		  var chkNum =0;
		  
		  $( "input[name='chkMainBlackObj']:checked" ).each (function (){
			  chk_blackList_id = chk_blackList_id + $(this).val()+"," ;
			  chkNum++;
		  });
		  chk_blackList_id = chk_blackList_id.substring(0,chk_blackList_id.lastIndexOf(","));
		 
		  if(chk_blackList_id == ''){
		    fn_showCustomAlert("삭제(해제) 유저를 선택해주세요.");
		    return false;
		  }
		 
		  var bool = confirm(chkNum+"개의 항목이 선택되었습니다. 삭제(해제) 하시겠습니까?");
		  
		  if(bool){
			  $.ajax({
			        url: '${ctxt}/opsmng/chatBlackList/deleteChatBlackList.do',
			        data: {"chk_blackList_id":chk_blackList_id},
			        type: 'POST',
			        dataType: 'text',
			        cache: false,
			 	    
			        success: function(rtnXml) {
			        	fn_showCustomAlert('정상적으로 삭제(해제)를 진행하였습니다.');
			        	fn_chatMainUserBlackList(mainBlackcuurPage);
							
			        },
			        error : function(){                              // Ajax 전송 에러 발생시 실행
			            fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
			          }
			  });
		  }
		}

	/**
	*	블랙리스트 set
	*/
	function fn_mainChatBlackListSet(idVal , nickNm ,bl_ban_yn, divIdx){
		if(idVal == '' || bl_ban_yn ==''){
			fn_showCustomAlert("선택된 유저가 존재 하지 않습니다.");
			
			return false;
		}
		
		var strConfirm;		
		strConfirm = confirm("저장하시겠습니까?");
		
		var params = {};
		params.bl_userId    = idVal;
		params.bl_ban_yn    = bl_ban_yn;   
	 
		if(strConfirm){			

			$.ajax({
				url : '${ctxt}/opsmng/chatBlackList/insertBlackList.do',
				type:'POST',
				data:params,	
				dataType:"json", 
				async : false,
				success : function(result) {

					if(result.returnVal == 'ok'){
						fn_showCustomAlert("저장을 완료 하였습니다.");
					
					}else if(result.returnVal == 'no'){
						fn_showCustomAlert("저장된 내역이 존재 하지 않습니다.");
						
					}else if(result.returnVal =='notLogin'){
						fn_showCustomAlert('로그인 정보가 존재하지 않습니다. <br/>다시 로그인 해주십시오.');
					}
									
						
				},
				error : function() { // Ajax 전송 에러 발생시 실행
					console.log('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.fn_mainChatBlackListSet','e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행
				//	fn_chatUserSetLayer( divIdx);
				}
			});
		
		}
		
	}
	
	//블랙리스트 유저 목록 닫기 SHOW HIDE
	function fn_closeMainChatBlackList(){
		 if ($('.userList-blackList-main').css("display")=='none') {
	    	 $('.userList-blackList-main').show();
	     } else {
	    	 $('.userList-blackList-main').hide();
	     }
	
	} 
	
	

	// 체크
	function fn_mainChatBlackListChk(){
	
		var mainBlackListChk;
		var params = {};
	  		params.bj_id = $("#bj_id").val();
	  		

		 	$.ajax({
				url : "${ctxt}/opsmng/chatBlackList/selectChatBlackListChkYn.do", 
				type : 'POST',
				data : params,
				dataType : 'json',
				cache: false,
				async : false,
				success : function(result) {
					if(!isEmpty(result) ){
						if(result.loginYn == 'Y'){
							if(result.chkBanStatus == 'C'){
								mainBlackListChk = 'C'; // 채팅만
								
							}else if(result.chkBanStatus == 'X'){
								mainBlackListChk = 'X'; // 전체 금지
							}
							
						}else {
							mainBlackListChk = 'NOUSER';
						
						}						
					}
				},
				error : function() {
					mainBlackListChk = 'F';
					console.log('오류가 발생했습니다. 관리자에게 문의 바랍니다.');
				}
		    });
			
		 	return mainBlackListChk;
	}
	
	// 채팅창 유저 닉네임 클릭했을때 SHOW HIDE
	function fn_mainChatUserClickLayer(idx){
		 if ($('#main-userCon-layer2-'+idx).css("display")=='none') {
			 $('[id^="main-userCon-layer2-"]').not("#main-userCon-layer2-" + idx).hide(); // 다른 buttonContainer 숨기기
	    	 $('#main-userCon-layer2-'+idx).show();
	     } else {
	    	 $('#main-userCon-layer2-'+idx).hide();
	     }
	} 
</script>
  
<!-- 채팅창 -->
<div class="chat-container">
    <div class="chat-header">자유 채팅방
    	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 			 <!--111  -->
	  			<div class="icons-container">
	   	     	   <a href="javascript:void(0);" onclick="javascript:fn_chatMainUserBlackList(1);" class="" title="블랙리스트 목록"> <i class="fas fa-user-alt-slash" aria-hidden="true"></i></a>								
	     	 	</div>			  				
				<div class="userList-blackList-main" style="display: none;">
				    <div class="stream-chat-userList-blackList-main">
				    	블랙리스트 유저 목록
				    	<div class="button-group">	    
						  	<div class="refresh-button" onclick="javascript:fn_chatMainUserBlackList(1);" title="새로고침 버튼">
						  		<i class="fas fa-sync-alt"></i>
							</div>
							<div class="chatuser-close-button" onclick="javascript:fn_closeMainChatBlackList();" title="닫기 버튼">
							 	<i class="fas fa-times"></i> 
							</div>
						</div>
				    </div>
				    <div class = "userListHtml-blackList">
					    <div style="float: right;margin-top: 10px;" >						
							<input type="button" onclick="javascript:fn_mainChat_delBlack();" name="" value="블랙리스트 해제" />		      	
						</div>	
						<div class="clear"></div>
				    	<table class="table_h" cellpadding="0" cellspacing="0" border="0" >
				         	<colgroup>
				         		<col width="10%" />					                        
				            	<col width="60%" />				      
				            	<col width="30%" />
				            </colgroup> 
				            <thead>    
					         	<tr>            
					         		<th>선택</th>					         	
						            <th>닉네임(ID)</th>    						          				                  
						            <th>구분</th>        
					        	 </tr>        
				        	</thead>
				        	<tbody id="mainBlackListData" style="color: black;">       
					        </tbody>
				      	</table>
				      	<!-- 페이징 처리 -->
						<div id="mainBlackPaging" class="paginate"></div>
				    </div>			       
				</div>
				 <!--111  -->
			</sec:authorize>
	 </div>
   
    
    <div class="chat-messages" id ="chat-messages">
      
    </div>
    
    <div class="chat-input">
      <input type="text" id="chatMsgInput" onkeydown="submitChatMainOnEnter(event)"   placeholder="메시지를 입력하세요...">
      <button id="button-send">전송</button>
    </div>
  </div>


