var ctxt = "";
var closePop; 
//var pagetotalCnt=0;


var pageLen=10; // 한페이지 갯수 (ajax) 


var all_search_yn="N";

var tabIndex = ""; //모달팝업 탭인덱스
var focus_back = ""; //팝업종료시 돌아갈 포커스 id값

function logoutTabOn(){
	$(".userTooltip").addClass("on");
}

function logoutTabOff(){
	$(".userTooltip").removeClass("on");
}

function slideTogleOut(){
	$(".reset").removeClass("on");
	$(".nav-02").css("display", "none");
}

$(function(){

	
	
}); 
 
$(window).load(function() {
		
	var url = location.pathname;
	//메뉴 타이틀 세팅
	if(url.indexOf("index") == -1){
		$('#page_title').text($('#page_name').text());     
	 //	$('title').text($('#page_name').text());            
	}

	
});

function myFunction() {
	  var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
	  var height =
	    document.documentElement.scrollHeight -
	    document.documentElement.clientHeight;
	  var scrolled = winScroll / height * 100;
	  if($('#myBar').length > 0 ){
		  document.getElementById("myBar").style.width = scrolled + "%";
	  }
}

$(document).ready(function(){
	
	var url = location.pathname;
	if(url.indexOf("index") == -1){
		$('#page_title').text($('#page_name').text());   
	//	$('title').text($('#page_name').text());   
	}
	 $('#fadeandscale').popup({
	       pagecontainer: '#container_box',
	       transition: 'all 0.3s'
	});

	
	//권한체크
	//fn_getAuth(location.pathname);


    
    });

    function fn_lang(lang){

        $.ajax({
            url: ctxt +'/index/chgLang.do',
            data:{"lang":lang},
            async: false,
            cache: false,    //cache가 있으면 새로운 내용이 업데이트 되지 않는다.
            type: 'GET'
        });
        
        location.href= ctxt+"/index/index.do";
    }

    /*TR STYLE*/   
    function fn_rowOn(tb_nm){
        $("#"+tb_nm+ " tr").click(function() {
        	$("#"+tb_nm+" tr").removeClass("on"); 
        	$(this).addClass("on");   
        	$("#"+tb_nm+" tr").css('cursor','pointer');
        });
    }   

    function fn_getLang(page_id){
    	
    	$.ajax({
    		url: ctxt +'/index/getLang.do',
    		data:{"page_id":page_id},
    		async: false,
    		cache: false,    //cache가 있으면 새로운 내용이 업데이트 되지 않는다.
    		type: 'GET',
    		success : function(result) {
    			var locale = $('#user_locale').val();
    			if(locale == 'null' || locale == ''){
    				locale ='ko';
    			}
               	var xmlObj = $(result);
               	xmlObj.find('item').each(function(cnt){
               		var tag_gbn = $(this).find('tag_gbn').text();
               		var tag = $(this).find('tag').text();  
               		var tag_val = "";
               		if(locale == 'ko'){
               			tag_val = $(this).find('tag_val_ko').text();
               		}else{
               			tag_val = $(this).find('tag_val_eng').text();
               		}
               		
               		switch (tag_gbn) {
    				case "ID":
    					$('#'+tag).text(tag_val);
    					break;
    				case "NM":
    					$('input[name='+tag+']').text(tag_val);
    					break;
    				case "CLS":
    					$('.'+tag).text(tag_val);
    					break;
    				case "SCRIPT":
    					tag = tag.replace('#val',"'"+tag_val+"'");
    					eval(tag);
    					break;

    				default:
    					break;
    				}
               		
               	});  			        
    	    },
    	    error : function() { // Ajax 전송 에러 발생시 실행
    	           fn_showCustomAlert("언어조회에 실패하였습니다.");   
    	    },
    	});
    }

    /**
      * 화면별 버튼 권한 설정
      * param[0] : 화면URL
      * return 
      */
    function fn_getAuth(location_path){
    	//화면 버튼 숨기기
    	var location_path  = location_path.replace(ctxt , '');
    	$.ajax({
    	    url : ctxt +'/index/auth/readMenuAuth.do',
    	    data : {"menu_url":location_path},   
    	    contentType: 'text/xml;charset=utf-8',   //서버로 데이터를 보낼 때 사용.
    	    type : 'GET',
    	    dataType : 'text',   
    	    async: false,
    	    cache:false,
    	    success : function(result) {
    	           	var xmlObj = $(result);
    	           	var sel_menu_id="";
    	           	var r_yn ='N';var s_yn ='N';var d_yn ='N';var l_yn ='N';var e_yn ='N';var u_yn ='N';var c_yn ='N';
    	           	xmlObj.find('item').each(function(cnt){
    	           		
    	           		var auth_gbn = $(this).find('auth_gbn').text();
    	           		
    	           		if(auth_gbn == "READ"){ r_yn="Y";
    	           		}else if(auth_gbn == "SAVE"){ s_yn="Y";
    	           		}else if(auth_gbn == "DEL"){ d_yn="Y";
    	           		}else if(auth_gbn == "DOWNLOAD"){ l_yn="Y";
    	           		}else if(auth_gbn == "ETC"){ e_yn="Y";
    	           		}else if(auth_gbn == "UPD"){ u_yn="Y";
    	           		}else if(auth_gbn == "ALL_SEARCH"){
    	           			all_search_yn = "Y";    
    	           		}
    	           	});  
    	           	//if(r_yn == 'N'){ $('input[name=search_btn]').detach(); $('button[name=search_btn]').detach(); $('a[name=search_btn]').detach();};
    				if(s_yn == 'N'){ $('input[name=save_btn]').detach();$('button[name=save_btn]').detach();$('a[name=save_btn]').detach();};
    				if(d_yn == 'N'){ $('input[name=del_btn]').detach();$('button[name=del_btn]').detach();$('a[name=del_btn]').detach();};
    				if(l_yn == 'N'){ $('input[name=download_btn]').detach();$('button[name=download_btn]').detach();$('a[name=download_btn]').detach();};
    				if(e_yn == 'N'){ $('input[name=etc_btn]').detach();$('button[name=etc_btn]').detach();$('a[name=etc_btn]').detach();};
    				if(u_yn == 'N'){ $('input[name=upd_btn]').detach();$('button[name=upd_btn]').detach();$('a[name=upd_btn]').detach();};
    	         	   
    				
    				     
    	    },
    	    error : function() { // Ajax 전송 에러 발생시 실행
    	           fn_showCustomAlert("메뉴권한조회에 실패하였습니다.");   
    	    },
    	});
    }

    function fn_tbvalchk(tbody_id, param_idx, param_nm){
    	
    	var isVal = true;
    	
    	$('#'+tbody_id+' tr').each(function(cnt){
    		var trObj = $(this);
    		for(var i=0; i < param_idx.length;i++){
    			if(trObj.find('td:eq('+param_idx[i]+') input[type=text]').val() == ''){
    				isVal= false;
    				msg= cnt+1+"번째 줄 "+param_nm[i]+"는 필수 입력 사항입니다.";
    				paramObj=trObj;
    				trObj.find('td:eq('+param_idx[i]+') input[type=text]').focus();
    				return false;
    			}
    		}
    	});
    	return isVal;
    }
    /**
     * 필수값 체크 
     * param[0] : 체크colum list
     * param[1] : 체크colum list
     * return 
     */
    function checkVal(colList,nameList){

    	var isErr= false;
    	
    	for(var i=0; i < colList.length; i ++){
    		if( $('#'+colList[i]).val() == ''  ||  $('#'+colList[i]).val() == undefined ){
    			fn_showCustomAlert(nameList[i]+"는/은 필수입력사항입니다.",'c')
    			isErr= true;
    			break;
    		}
    	}
    	return isErr;
    }

    /**알림창 
     * @param 알림 메세지
     * @param 알림창 타입
     */
    function fn_alert(msg,type){
    	
    	var strType="";
    	
    	if(type == undefined){
    		strType='n';
    	}else{
    		strType=type;
    	}
        
    	var popHtml  = '<div>';
    	// 이미지 디자인 하셔서 이미지url변경 하시면 됩니다.
    	if(strType == "n"){
    		$('#fadeandscale > div').addClass('attention clear shadow4');
    		$('#fadeandscale > .attention > i').addClass('faa-shake animated fa fa-info-circle');
    	}else if(strType == "e"){
    		$('#fadeandscale > div').attr('class','warning clear shadow4');
    		$('#fadeandscale > .attention > i').addClass('faa-flash animated fa fa-exclamation-triangle');
    	}else if(strType == "c"){
    		$('#fadeandscale > div').attr('class','complete clear shadow4');
    		$('#fadeandscale > .attention > i').addClass('faa-tada animated far fa-check-circle');
    		
    	}
    	popHtml += '<div class="text_c">'+msg+'</div></div>';

    	$('#msg_content').html(popHtml);
    	$('a[name=fadeandscale]').click();
    	
    	$('#fadeandscale').contents().remove('br');
   

    }
    
    


    /**
     * 메소드 : 날짜 가져오기
     * @param 날짜 , 더하는 일자, 포맷, 마지막일 L 넘겨주면 해당월 마지막일자
     * @returns {String}
     * sample fn_getDate("2016/05/01",7,"-")
     * @param 없으면 현재일자 20160403 리턴
     */
    function fn_getDate(){
    	var myDate; 
    	var result = "";
    	
    	if(arguments[0] == undefined || arguments[0] == "" ){
    		myDate = new Date();
    	}else{
    		myDate = new Date(arguments[0]);
    	} 

    	if(arguments[1] != undefined){
    		myDate.setDate (myDate.getDate() + arguments[1]);
    	}
    	var data_fomat = arguments[2];
    	
    	var year = myDate.getFullYear();
    	var month = myDate.getMonth() +1;
    	var day = myDate.getDate();
    	
    	if(month < 10){
    		month = "0"+month;
    	}
    	if(day < 10){
    		day = "0"+day;
    	}
    	
    	if(arguments[3] != undefined || arguments[3] == "L"){
    		day = ( new Date( year, month, 0) ).getDate();
    	}
    	
    	if(data_fomat == undefined || data_fomat == "" ){
    		result = year+""+ month+""+day;
    	}else{
    		result = year+ data_fomat + month+ data_fomat +day;
    	}
    	
    	return result;
    	
    }


    /**
     * 날짜 검증
     * @param stDt
     * @param edDt
     * @returns {Boolean}
     */
    function fn_dateChk2(stDt, edDt){
    	
    	if($("#"+stDt).val() != "" ){
    		if ($("#"+stDt).val().replace(/-/g, "").length != '8') {
    			fn_showCustomAlert('시작 년월일을 정확하게 입력하세요.','c');
    			$("#"+stDt).focus();
    			return false;
    		}
    		if($("#"+stDt).val().replace(/-/g, "") != $("#"+stDt).val().replace(/[^0-9]/gi,'')){
    			fn_showCustomAlert("시작일자는 숫자로만 입력 가능합니다.",'c');
    			$("#"+stDt).focus();
    			return false;
    		}
    		if($("#"+edDt).val() == ''){
    			fn_showCustomAlert("종료 일자를 입력해 주세요!",'c');
    			return false;
    		}
    	}
        
    	if($("#"+edDt).val() != "" ){
    		if($("#"+stDt).val() == ''){
    			fn_showCustomAlert("시작 일자를 입력해 주세요!",'c');
    			return false;
    		}
    		if ($("#"+edDt).val().replace(/-/g, "").length != '8') {
    			fn_showCustomAlert('종료 년월일을 정확하게 입력하세요.','c');
    			$("#"+edDt).focus();
    			return false;
    		}
    		if($("#"+edDt).val().replace(/-/g, "") != $("#"+edDt).val().replace(/[^0-9]/gi,'')){
    			fn_showCustomAlert("종료 일자는 숫자로만 입력 가능합니다.",'c');
    			$("#"+edDt).focus();
    			return false;
    		}
    		if ($("#"+stDt).val().replace(/-/g, "") > $("#"+edDt).val().replace(/-/g, "")) {
    			fn_showCustomAlert('시작 일자가 종료 일자보다 클 수 없습니다.','c');
    			$("#"+stDt).focus();
    			return false;
    		}
    	}
    }

    ///파일 업로드 사이즈 체크
    function fileCheck(val , fileMaxSize , view  )  //  value ,  파일사이즈 mb , 화면 param
    {

    	   var agent = navigator.userAgent.toLowerCase(); 

    	   var fileType = getFileType(val.value);
    	   var fileIndex = $(val).parent().parent().index();	


    	   var fileName =  $("input[name=uploadFile]")[fileIndex];

    	   fileType = fileType.toLowerCase();

    	   if( val.value != ""){
    		   if (view == 'report'){ // 팝업 게시판 
    			   	if(fileType != 'jpg'  && fileType != 'gif'  && fileType != 'png'  && fileType != 'jpeg' && fileType != 'hwp' && fileType != 'pdf'  ){
    			
    			   		fn_showCustomAlert("확장자는  jpg, gif, png, jpeg, hwp, pdf만 가능합니다. ");
    				    	
    				      	if (agent.indexOf("msie") != -1) {
    				       		$(val).replaceWith( $(val).clone(true) );
    				    	} else {
    				    		$(val).val("");
    				    	}
    				      	
    				      	return false ;
    			     	
    			     }
    			   	
    		   }else if(view == 'rnd'){
    			   
    			   	if(fileType != 'jpg'  && fileType != 'gif'  && fileType != 'png'  && fileType != 'jpeg' && fileType != 'hwp' && fileType != 'png'  ){
    			
    			   		fn_showCustomAlert("확장자는  jpg, gif, png, jpeg, hwp, png만 가능합니다. ");
    				    	
    				      	if (agent.indexOf("msie") != -1) {
    				       		$(val).replaceWith( $(val).clone(true) );
    				    	} else {
    				    		$(val).val("");
    				    	}			      	
    				      	return false ;
    			     }  	
    			   
    		   }else{ 	
    			   
    			   if(fileType != 'jpg'  && fileType != 'gif'  && fileType != 'png'  && fileType != 'jpeg'){
    				
    				   fn_showCustomAlert("확장자는  jpg, gif, png, jpeg만 가능합니다. ");
    			    	
    			      	if (agent.indexOf("msie") != -1) {
    			       		$(val).replaceWith( $(val).clone(true) );
    			    	} else {
    			    		$(val).val("");
    			    	}
    			      	
    			      	return false ;    	
    			   	}
    		  } 
    		       
    	}


       //메가바이트 단위로 받아서  = > byte로 변환해 준다.
       var maxSize = fileMaxSize    //   메가바이트 30mb  
       	maxSize = maxSize*1024*1024 ;  //byte
       
       var fileSize = Math.round(fileName.files[0].size);  //byte 	
       
       if(fileSize > maxSize)
       {
    	   fn_showCustomAlert("최대용량을 초과하였습니다.\n\n 최대 사이즈 : "+maxSize +"byte , 업로드 사이즈 : "+fileSize+"byte");
           
           return false;
           
       }


    }

    //파일 업로드 확장자 체크 
    function getFileType(filePath)
    {
       var index = -1;
           index = filePath.lastIndexOf('.');
           
       var type = "";

       if(index != -1)// 파일이 존재하면
        {
            type = filePath.substring(index+1, filePath.length);
        }
        else
        {
            type = "";
        }
       return type;
    }

    /**
     * @param id
     * @param before
     * @param next
     */
    function getYear(id,before,next){
    	var today = new Date();
    	var html = "";
    	var year = today.getFullYear();
    	var val = 0;
    	//현재기준 과거 년도
    	for (var int = before; int >= 0; int--) {
    		val = Number(year) - int;
    		
    		if(int == 0){
    			html += '<option value="'+ val +'" selected="selected" >'+ val +'년</option>';
    		}else{
    			html += '<option value="'+ val +'" >'+ val +'년</option>';
    		}
    	}
    	//현재기준 미래 년도
    	for (var int2 = 1; int2 < next+1; int2++) {
    		val = Number(year) + int2;
    		html += '<option value="'+ val +'" >'+ val +'년</option>';
    	}
    	
    	$('#'+id).html(html);
    	
    }

    function fn_loginPop(){

    	window.open('/cmmn/pop/loginPop.do','search','width=400,height=420,scrollbars=no');
    }

    function sendEml(){

    	var params = {};
    	params.loginid = $('#loginid').val();
    	params.user_nm_kor = $('#user_nm_kor').val();
    	
    	 $.ajax({
    	        url: '/login/sendMail.do',
    	        data: params,
    	        type: 'POST',
    	        dataType: 'text',
    	        cache: false,
    	        success: function(rtnXml) {
    	        	fn_showCustomAlert('이메일이 정상 발송되었습니다.');
    	        	setTimeout(fn_close, 1000);
    	        },
    	 		error : function(){                              // Ajax 전송 에러 발생시 실행
    	 			fn_showCustomAlert('오류가 발생했습니다. 관리자에게 문의 바랍니다.','e');
    	 		}
    	 });
    	
    }

    /**
     * 팝업창 닫기
     */
    function fn_close(){
    	window.close();
    }

    /**
     * 인증번호 발송
     */
    function fn_send(){
    	var strRegEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    		
    	$('#certifyCheck').attr('style','color:red;');
    	$('#certifyCheck').text('인증메일 미발송 시 e-mail 정보를 확인하세요.');
    	
    	// null체크 - e-mail 
    	if ($("#mail_id").val() == '') {
    		fn_showCustomAlert('E-mail을 입력하세요.',"c");
    		$("#mail_id").focus();
    		return;
    	}
    	
    	// null체크 - e-mail 
    	if ($("#mail_domain").val() == '') {
    		fn_showCustomAlert('E-mail 도메인을 입력하세요.',"c");
    		$("#mail_domain").focus();
    		return;
    	}
    	
        if(!strRegEmail.test($('#mail_id').val() + '@' + $('#mail_domain').val())) {
            fn_showCustomAlert('올바른 전자우편를 입력하세요.',"c");
            $('#mail_id').focus();
            return;
        }
        
    	// e-mail - 중복확인 버튼 체크
    	if($("#checkedYN").val() != "Y"){
     		fn_showCustomAlert("E-mail 중복 확인을 하십시오.","c");
    		return;
    	}
    	
    	var params = {};
    		params.loginid = $('#mail_id').val()+"@"+$("#mail_domain").val();
    	
    	 $.ajax({
    	        url: '/login/sendNum.do',
    	        data: params,
    	        type: 'POST',
    	        dataType: 'text',
    	        cache: false,
    	        success: function(rtnXml) {
    	        	certify_num = rtnXml;
    	        	
    	        	var html ="※ 발송된 <span>이메일의 인증번호</span>를 입력하세요.";
    	        	$('#pop_id').html(html);
    	        	$("#modal_content").modal(); 
    	        	fn_timer(179);
    	        },
    	 		error : function(){                              // Ajax 전송 에러 발생시 실행
    	 			fn_showCustomAlert('인증메일 정보를 확인하세요.(오류발생)','e');
    	 		}
    	 });
    	
    }

    /**
     * 인증번호 확인
     */
    function fn_confirm(){

    	if($('#confirm_num').val() == ''){
    		fn_showCustomAlert('인증번호를 입력하세요.');
    		return;
    	}
    	
    	if (certify_num != $('#confirm_num').val()) {
    		fn_showCustomAlert('잘못된 인증번호입니다.','c');
    		$("#confirm_num").focus();
    	}else{
    		$('#certifyCheck').attr('style','color:green;');
    		$('#certifyCheck').text('인증이 완료되었습니다.');
    		fn_close();
    	}

    	$('#confirm_num').val('');
    	$('#certifyYN').val('Y');
    	
    }

    var interval;

    function fn_timer(duration) {
        
        var timer = duration;
        var hours, minutes, seconds;
        
        interval = setInterval(function(){
            hours	= parseInt(timer / 3600, 10);
            minutes = parseInt(timer / 60 % 60, 10);
            seconds = parseInt(timer % 60, 10);
    		
            hours 	= hours < 10 ? "0" + hours : hours;
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;
    		
            $('#timer').text(minutes+":"+seconds);
            
            if (--timer < 0) {
                timer = 0;
                clearInterval(interval);
                fn_showCustomAlert('인증에 실패했습니다. 인증메일을 재발송 해주세요.');
        		fn_close();
            }
        }, 1000);
        
    }




    /* Contents ZoomIn/Out */
    function contentsZoom(obj){
    	
    	var id = obj.id;
    	
    	if( document.ifrmmain )
    	{
    		document.ifrmmain.contentsZoom(id);
    	}
    	else
    	{
    		clickHandler(id);
    	}
    }

    //현재 폰트 사이즈를 저장할 전역 변수
    var currentFontSize ;
    var currentFontSize01 ;
    var currentFontSize02 ;
    var currentFontSize03 ;
    var currentFontSize04 ;
    var currentFontSize05 ;
    var currentFontSize06 ;
    var currentFontSize07 ;
    var currentFontSize08 ;
    var currentFontSize09 ;
    var currentFontSize10 ;
    var currentFontSize11 ;
    var currentFontSize12 ;
    var currentFontSize13 ;
    var currentFontSize14 ;
    var currentFontSize15 ;

    //크게, 작게 링크가 클릭되었을때 처리하는 함수
    function clickHandler(e)
    {
    	

    	/*
    	//이벤트 전파를 막는다.
    	e.preventDefault(); 
    	
    	//크게, 작게 중 어느것이 클릭되었는지 판별
    	var whichClicked = $(this).attr("id");
    	*/
    	var whichClicked = e;
    	
    	
    	
    	//현재의 폰트 사이즈를 전역 변수에 저장한다.
    	currentFontSize = parseInt($(".inSection").css("font-size"));
    	currentFontSize01 = parseInt($("line-height").css("font-size"));
    	currentFontSize02 = parseInt($("h3").css("font-size"));
//    	currentFontSize03 = parseInt($("dl").css("font-size"));
//    	currentFontSize04 = parseInt($("dt").css("font-size"));
//    	currentFontSize05 = parseInt($("li").css("font-size"));
    	currentFontSize06 = parseInt($(".inSection>tbody>tr>th").css("font-size"));
    	currentFontSize07 = parseInt($(".inSection>tbody>tr>td").css("font-size"));
    	currentFontSize08 = parseInt($(".inSection>dt").css("font-size"));
    	currentFontSize09 = parseInt($(".inSection *").css("font-size"));
    	currentFontSize10 = parseInt($(".inSection>dd.substance").css("line-height"));
//    	currentFontSize06 = parseInt($(".nation_introduce_form *").css("font-size")); /* 검색서브 소개페이지 */
    	currentFontSize11 = parseInt($(".bookInfo").css("font-size"));
    	currentFontSize12 = parseInt($(".Dnew_title3>ul>li").css("font-size"));
    	currentFontSize13 = parseInt($(".viewLevel2>ul>li").css("font-size"));
    //
    	//클릭된 링크에 따라 폰트를 크게 혹은 작게 설정합니다.
    	switch(whichClicked)
    	{
    		case "larger" :
    			//1폰트 크게한다.
    			setFontSize(1) ;
    		break ;
    		
    		case "smaller" :
    			//1폰트 작게한다.
    			setFontSize(-1) ;
    		break ;
    		default:
    			setFontSize(0) ;
    		break;
    	}

    }

    function setFontSize($size)
    {
    	var totalFontSize = currentFontSize + $size ;
    	var totalFontSize01 = currentFontSize01 + $size ;
    	var totalFontSize02 = currentFontSize02 + $size ;
//    	var totalFontSize03 = currentFontSize03 + $size ;
//    	var totalFontSize04 = currentFontSize04 + $size ;
//    	var totalFontSize05 = currentFontSize05 + $size ;
    	var totalFontSize06 = currentFontSize06 + $size ;
    	var totalFontSize07 = currentFontSize07 + $size ;
    	var totalFontSize08 = currentFontSize08 + $size ;
    	var totalFontSize09 = currentFontSize09 + $size ;
    	var totalFontSize10 = currentFontSize10+ $size ;
    	var totalFontSize11 = currentFontSize11 + $size ;
    	var totalFontSize12 = currentFontSize12 + $size ;
    	var totalFontSize13 = currentFontSize13 + $size ;
    	var totalFontSize14 = currentFontSize14 + $size ;
    	var totalFontSize15 = currentFontSize15 + $size ;
    	
    	$(".inSection").css({"font-size":totalFontSize+"px"});
    	$("line-height").css({"line-height":totalFontSize01+"px"}); 
    	$("h3").css({"font-size":totalFontSize02+"px"});
//    	$("dl").css({"font-size":totalFontSize03+"px"});
//    	$("dt").css({"font-size":totalFontSize04+"px"});
//    	$("li").css({"font-size":totalFontSize05+"px"});
    	$(".inSection>tbody>tr>th").css({"font-size":totalFontSize06+"px"});
    	$(".inSection>tbody>tr>td").css({"font-size":totalFontSize07+"px"});
    	$(".inSection>dt").css({"font-size":totalFontSize08+"px"});
    	$(".inSection *").css({"font-size":totalFontSize09+"px"});
    	$(".inSection>dd.substance").css({"font-size":totalFontSize10+"px"});
    	$(".bookInfo").css({"font-size":totalFontSize11+"px"});
    	$(".Dnew_title3>ul>li").css({"font-size":totalFontSize12+"px"});
    	$(".viewLevel2>ul>li").css({"font-size":totalFontSize13+"px"});

    }

    /*SNS로전송 */
    function sendSNS(sns,title){

    	  var HOST = location.protocal + "//" + location.host;
    	  var TITLE = encodeURIComponent(title);
    	  var FACEBOOK = "http://www.facebook.com/sharer/sharer.php?s=100&p[url]=" + location.href+"&p[title]="+TITLE;
    	  var TWITTER = "http://twitter.com/share?text=" + TITLE + "&url=" + location.href;
    	  var ME2DAY = "http://me2day.net/posts/new?new_post[body]=" + TITLE + ":" + location.href + "&new_post[tags]=" + HOST;
    	  
    		switch(sns){
    		 case "facebook":
    		  window.open(FACEBOOK, "facebookWindow",'width=960,height=500,directories=0,resizable=1,menubar=0,status=0,toolbar=0,scrollbars=1');
    		  break;
    		 case "twitter":
    		  window.open(TWITTER, "twitterWindow",'width=960,height=500,directories=0,resizable=1,menubar=0,status=0,toolbar=0,scrollbars=1');
    		  break;
    		 case "me2day":
    		  window.open(ME2DAY, "me2dayWindow",'width=960,height=500,directories=0,resizable=1,menubar=0,status=0,toolbar=0,scrollbars=1');
    	     break;
    		 default:
    			 window.open(FACEBOOK, "facebookWindow",'width=960,height=500,directories=0,resizable=1,menubar=0,status=0,toolbar=0,scrollbars=1');
    			break;
    		} 

    }


    /**
     * 목록으로 이동
     */
    function fn_returnListUrl(){

    	var form = document.reqForm;
    	var returnPage = form.returnListPage.value;	
    	form.action = returnPage;
    	form.submit();	
    }

    /**
     * 엔터 조회
     * @param param
     */
    function fn_enter_search(param){
    	var id_val = $(param).attr('id');	
    	
    	if(event.keyCode == 13)
    	{
    		event.preventDefault();
    		fn_search(1);
    	}	
    }

    ///////////////////////////////////////////////////////////
    //팝업 기능관련
    //@url URL
    //@w 폭
    //@h 너비
    //@s 스크롤바 여부 1, 'Y'이면 보여줌, 0, '', 'N'이면 숨김
    function popup(url,name,w,h,s){
    	var l, t, objPopup;
    	l = (screen.width-w)/2;
    	t = (screen.height-h)/2;
    	if(s==1 || s=="Y") 
    		objPopup  = window.open(url,'name','_blank','width='+w+',height='+h+',left='+l+',top='+t+',resizable=0,scrollbars=1');
    	else if (s=="" || s==0 || s=="N" || !s) 
    		objPopup = window.open(url,'','width='+w+',height='+h+',left='+l+',top='+t+',resizable=0,scrollbars=0,status=0');
    	else if (s=="E") //전자자원 팝업 
    		objPopup = window.open(url,'_blank','width='+w+',height='+h+',left='+l+',top='+t+',resizable=1,menubar=0,toolbar=no,scrollbars=1,status=0');
    	else
    		objPopup = window.open(url,'_blank','width='+w+',height='+h+',left='+l+',top='+t+',resizable=1,menubar=1,toolbar=yes,scrollbars=1,status=0');
    	if (objPopup == null) { 
    		fn_showCustomAlert("차단된 팝업창을 허용해 주십시오."); 
    	} 
    	return objPopup;
    }



    //숫자만 입력하게 한다.
    //onkeydown="return onlyNumber();"

    /**
     * 바이트 문자 입력가능 문자수 체크
     * 
     
     * @param title : tag title
     * @param maxLength : 최대 입력가능 수 (byte)
     * @returns {Boolean}
     * 2017-07-12 wonki
     */
    /*function maxLengthCheck(id, title, maxLength){
    	
         var obj = $("#"+id);
         if(maxLength == null) {
             maxLength = obj.attr("maxLength") != null ? obj.attr("maxLength") : 1000;
         }
     
         if(Number(byteCheck(obj)) > Number(maxLength)) {
             alert(title + "이(가) 입력가능문자수를 초과하였습니다.\n(영문, 숫자, 일반 특수문자 : " + maxLength + " / 한글, 한자, 기타 특수문자 : " + parseInt(maxLength/2, 10) + ").");
             obj.focus();
             return false;
         } else {
             return true;
         }
    }*/
    /**
    * 바이트수 반환  
    *    
    * @param el : tag jquery object
    * @returns {Number}
    */


    function byteCheck(id,len){

    	var msglen=0;

    	var target = document.getElementById(id);
    	
    	msglen = reCount(target.value);

    	document.getElementById('byte_'+id).innerHTML = msglen;	
    	
    	if(msglen > len){
    		rem = msglen - len;
    		fn_showCustomAlert('입력하신 문장의 총 길이는 ' + msglen + '입니다.\n초과되는 ' + rem + '바이트는 삭제됩니다.');
    		document.getElementById(id).value = cutMsg(str,len);		
    	}
    }

    function reCount(str){
    	var i;
    	var msglen=0;
    	if(str != '' && str != undefined){
    		for(i=0;i<str.length;i++){
    		var ch=str.charAt(i);
    	
    			if(escape(ch).length >4){
    				msglen += 2;
    			}else{
    				msglen++;
    			}
    		}
    	}
    	return msglen;
    }

    /**
    * 바이트수 반환2  
    * 
    * @param el : tag jquery object
    * @returns {Number}
    */


    function byteCheck2(value){
        var codeByte = 0;
        for (var idx = 0; idx < value.length; idx++) {
            var oneChar = escape(value.charAt(idx));
            if ( oneChar.length == 1 ) {
                codeByte ++;
            } else if (oneChar.indexOf("%u") != -1) {
                codeByte += 2;
            } else if (oneChar.indexOf("%") != -1) {
                codeByte ++;
            }
        }
        return codeByte;
    }
    
    // 바이트수 반환 
    function getByteLength(str) {
	  let byteLength = 0;

	  for (let i = 0; i < str.length; i++) {
	    const charCode = str.charCodeAt(i);

	    // ASCII 문자는 1바이트로 계산
	    if (charCode <= 0x7f) {
	      byteLength += 1;
	    }
	    // 기타 문자는 2바이트로 계산 (UTF-8 기준)
	    else {
	      byteLength += 2;
	    }
	  }

	  return byteLength;
	}

    /**
     * 입력값이 null인지 체크하고 null인경우 해당 input box에 포커스를 준다.
     * @param objs : 체크할 input 선택자(목록)
     * wonki
     */
    function cmmfn_check_inputnull(objs)
    {
    	var addcontinue = true;
    	objs.each(function(){
    		if($(this).val() == "")
    		{
    			fn_showCustomAlert($(this).attr("title") + " 은/는 반드시 입력해 주십시오.");
    			$(this).focus();
    			addcontinue = false;
    			return false;
    		}
    	});
    	
    	return addcontinue;
    }

    /**
     * 유효성검사
     */	
    var cmmCheck ={
    		/**
    		 * 필드가 비어있는 지 검사
    		 */
    		isValue: function(str){
    			if((str == null) || (str == "") || cmmCheck.isblank(str))
    			return false;
    		return true;
    		},
    	
    		/**
    		 * 필드가 비어있는 지 검사
    		 */
    		isblank: function(str) {
    			for(var i = 0; i < str.length; i++){
    				var c = str.charAt(i);
    				if( (c != ' ') && (c != '\n') && (c != '\et')){
    					return false;
    				}
    			}
    		return true;
    		},
    	
    		/**
    		 * 정규식(Regular Exxpression)을 사용한 앞뒤 트림이다.
    		 */
    		trim: function(str) {
    			regExp = /([^\s*$]?)(\s*$)/;
    			newStr = str.replace(regExp, "$1");
    			regExp = /(^\s*)(.+)/;
    			newStr = newStr.replace(regExp, "$2");
    	
    			return newStr;
    		}
    	
    	}

    //날짜 유효성 체크
    function fn_validDate(obj) {
     	var str =  $(obj).val();

    	  if(str!=""){
    	
    		  oDate = new Date();
    		  oDate.setFullYear(str.substring(0, 4));
    		  oDate.setMonth(parseInt(str.substring(5, 7)) - 1);
    		  oDate.setDate(str.substring(8,10));

    		  if( oDate.getFullYear()     != str.substring(0, 4)
    		      || oDate.getMonth() + 1 != str.substring(5, 7)
    		      || oDate.getDate()      != str.substring(8,10) ) {
    	   	    
    		  fn_showCustomAlert("날짜 형식이 유효하지 않습니다.");
    		  $(obj).val("");
    		  $(obj).focus();
    		  
    	      return false;
    	      
    	 	  }
    	  }
    }

 // "", null, undefined, 이상한 숫자, 빈 Object, 빈 Array도 검사
    function isEmpty(data) {
        if(typeof(data) === 'object') {
            if(!data) {
                return true;
            }
            else if(JSON.stringify(data) === '{}' || JSON.stringify(data) === '[]') {
                return true;
            }
            return false;
        }
        else if(typeof(data) === 'string') {
            if(!data.trim()) {
                return true;
            }
            return false;
        }
        else if(typeof(data) === 'undefined') {
            return true;
        }
        else if(isNaN(data) === true) {
            return true;
        }
        else {
            return false;
        }
    }


    function fdate(id){
    	
    	$.datepicker.regional['ko'] = {
    	        closeText: '닫기',
    	        prevText: '이전달',
    	        nextText: '다음달',
    	        currentText: '오늘',
    	        monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
    	        '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
    	        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
    	        '7월','8월','9월','10월','11월','12월'],
    	        dayNames: ['일','월','화','수','목','금','토'],
    	        dayNamesShort: ['일','월','화','수','목','금','토'],
    	        dayNamesMin: ['일','월','화','수','목','금','토'],
    	        weekHeader: 'Wk',
    	        dateFormat: 'yy-mm-dd',
    	        firstDay: 0,
    	        isRTL: false,
    	        showMonthAfterYear: true,
    	        yearSuffix: '',
    	        showOn: 'both',
    	        buttonImage: 'http://images.jautour.com/jautour/images/main/search/btn_cale.gif',
    	        buttonImageOnly:true,
    	        buttonText: "달력",
    	        changeMonth: true,
    	        changeYear: true,
    	        showButtonPanel: true,
    	        yearRange: '2010:c+20',
    	    };
    	    $.datepicker.setDefaults($.datepicker.regional['ko']);
    	  
    	    var datepicker_default = {
    	        showOn: 'both',
    	        buttonText: "달력",
    	        currentText: "이번달",
    	        changeMonth: true,
    	        changeYear: true,
    	        showButtonPanel: true,
    	        yearRange: '2010:c+20',
    	        showOtherMonths: true,
    	        selectOtherMonths: true
    	    }
    	    
    	  
    	    datepicker_default.closeText = "선택";
    	    datepicker_default.dateFormat = "yy-mm";
    	    datepicker_default.onClose = function (dateText, inst) {
    	        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
    	        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
    	        $(this).datepicker( "option", "defaultDate", new Date(year, month, 1) );
    	        $(this).datepicker('setDate', new Date(year, month, 1));
    	    }
    	  
    	    datepicker_default.beforeShow = function () {
    	        var selectDate = $("#"+id).val().split("-");
    	        var year = Number(selectDate[0]);
    	        var month = Number(selectDate[1]) - 1;
    	        $(this).datepicker( "option", "defaultDate", new Date(year, month, 1) );
    	    }
    	    
    	    $("#"+id).datepicker(datepicker_default);
    	
    }


    function ldate(id){
    	
    	$.datepicker.regional['ko'] = {
    	        closeText: '닫기',
    	        prevText: '이전달',
    	        nextText: '다음달',
    	        currentText: '오늘',
    	        monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
    	        '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
    	        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
    	        '7월','8월','9월','10월','11월','12월'],
    	        dayNames: ['일','월','화','수','목','금','토'],
    	        dayNamesShort: ['일','월','화','수','목','금','토'],
    	        dayNamesMin: ['일','월','화','수','목','금','토'],
    	        weekHeader: 'Wk',
    	        dateFormat: 'yy-mm-dd',
    	        firstDay: 0,
    	        isRTL: false,
    	        showMonthAfterYear: true,
    	        yearSuffix: '',
    	        showOn: 'both',
    	        buttonText: "달력",
    	        changeMonth: true,
    	        changeYear: true,
    	        showButtonPanel: true,
    	        yearRange: '2010:c+20',
    	    };
    	    $.datepicker.setDefaults($.datepicker.regional['ko']);
    	  
    	    var datepicker_default = {
    	        showOn: 'both',
    	        buttonText: "달력",
    	        currentText: "이번달",
    	        changeMonth: true,
    	        changeYear: true,
    	        showButtonPanel: true,
    	        yearRange: '2010:c+20',
    	        showOtherMonths: true,
    	        selectOtherMonths: true
    	    }
    	  
    	    datepicker_default.closeText = "선택";
    	    datepicker_default.dateFormat = "yy-mm";
    	    datepicker_default.onClose = function (dateText, inst) {
    	        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
    	        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
    	        $(this).datepicker( "option", "defaultDate", new Date(year, month, 1) );
    	        $(this).datepicker('setDate', new Date(year, month, 1));
    	    }
    	  
    	    datepicker_default.beforeShow = function () {
    	        var selectDate = $("#"+id).val().split("-");
    	        var year = Number(selectDate[0]);
    	        var month = Number(selectDate[1]) - 1;
    	        $(this).datepicker( "option", "defaultDate", new Date(year, month, 1) );
    	    }
    	  
    	    $("#"+id).datepicker(datepicker_default);
    	
    }
    
  //천단위 콤마 넣기 이것으로 쓸것
    function addComma(num) {
        // 입력값이 숫자가 아니면 함수를 종료합니다.
    	
        if (typeof num !== 'number') {
            console.error("유효한 숫자가 아닙니다.");
            return num;
        }

        // 숫자를 문자열로 변환합니다.
        let numStr = String(num);

        // 소수점이 있는 경우, 정수부분과 소수부분을 분리합니다.
        let parts = numStr.split(".");
        let integerPart = parts[0];
        let decimalPart = parts.length > 1 ? "." + parts[1] : "";
//        console.log(integerPart);
//        console.log(parts[1]);
     
//        console.log(decimalPart);
        // 정수부분에 천단위 쉼표를 추가합니다.
        let regex = /(\d)(?=(\d{3})+(?!\d))/g;
        integerPart = integerPart.replace(regex, "$1,");
 
        // 최종 결과를 반환합니다.
        return integerPart + decimalPart;
    }
    
    //천단위 콤마 넣기
    function comma(str) {
    	if(str != "" && str != 'undefined' ){ 
    		  str = String(str);
    		  return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    	}
    } 

    //풀기
    function uncomma(str) {
    	if(str != "" && str != 'undefined' ){ 
    	    str = String(str);
    	    return str.replace(/[^\d]+/g, '');
    	}   
    }
      
    /**
     * text상자에 데이트피커를 적용한다.
     * @param {Object} selobjs : 셀렉터 String
     * @param {Object} format : 날짜 포맷 (yy-mm-dd)
     * by wonki
    */
    function cmmfn_set_datepicker(selobjs, format)
    {
       //var dateform = (format == null || format == undefined) ? 'yy-mm-dd' : format;
       var dateform = (format == null || format == undefined) ? "%Y-%m-%d" : format;
       selobjs.each(function(){
          var pickobj = $(this);
          if(pickobj.prop('type') == "text")
          {
          did = pickobj.attr("id");
          eval("datePickerController.destroyDatePicker(\"" + did + "\")");
          eval("\
                datePickerController.createDatePicker({\
                   formElements:{\"" + did +"\" :\"%Y-%m-%d\"},\
                });\
             ");
          
          pickobj
             .css({"font-size":"11px","width":"65px","height":"24px","text-align":"center", "z-index":"11", "vertical-align" : "middle"});
             //.prop("placeholder", cmmfn_date_to_string(new Date(), "DD", "-"));
          
          pickobj.attr("readonly",true);
          
          }
       });
    }   

    //달력형식 입력
    function fn_calInput(event){//fn_calInput(event){ 

         var time = new Date() 
           , y = String(time.getFullYear()) 
           , m = time.getMonth() 
           , d = time.getDate() 
           , h = '-' 
           , lists = { 
                    keyup : [ 
          // 숫자, - 외 제거 
          [/[^\d\-]/, ''] 
          // 0000 > 00-00 
          , [/^(\d\d)(\d\d)$/, '$1-$2'] 
          // 00-000 > 00-00-0 
          , [/^(\d\d\-\d\d)(\d+)$/, '$1-$2'] 
          // 00-00-000 > 0000-00-0 
          , [/^(\d\d)-(\d\d)-(\d\d)(\d+)$/, '$1$2-$3-$4'] 
          // 00-00-0-0 > 0000-0-0 
          , [/^(\d\d)-(\d\d)-(\d\d?)(-\d+)$/, '$1$2-$3$4'] 
          // 0000-0000 > 0000-00-00 
          , [/^(\d{4}-\d\d)(\d\d)$/, '$1-$2'] 
          // 00000000 > 0000-00-00 
          , [/^(\d{4})(\d\d)(\d\d)$/, '$1-$2-$3'] 
          // 이탈 제거 
          , [/(\d{4}-\d\d?-\d\d).+/, '$1'] 
          ] 
          , blur : [ 
            // 날짜만 있을 때 월 붙이기 
          [/^(0?[1-9]|[1-2][0-9]|3[0-1])$/, m+'-$1', 1] 
          // 월-날짜 만 있을 때 연도 붙이기 
          , [/^(0?[1-9]|1[0-2])\-?(0[1-9]|[1-2][0-9]|3[01])$/, y+'-$1-$2'] 
          , [/^((?:0m?[1-9]|1[0-2])\-[1-9])$/, y+'-$1'] 
          // 연도 4 자리로 
          , [/^(\d\-\d\d?\-\d\d?)$/, y.substr(0, 3)+'$1', 1] 
          , [/^(\d\d\-\d\d?\-\d\d?)$/, y.substr(0, 2)+'$1', 1] 
          // 0 자리 붙이기 
          , [/(\d{4}-)(\d-\d\d?)$/, '$10$2', 1] 
          , [/(\d{4}-\d\d-)(\d)$/, '$10$2'] 
          ] 
         } 
         event = event || window.event; 
         var input = event.target || event.srcElement 
           , value = input.value 
           , list = lists[event.type] 
           ; 
         for(var i=0, c=list.length, match; i<c; i++){ 
            match = list[i]; 
            if(match[0].test(value)){ 
                   input.value = value.replace(match[0], match[1]); 
                   if(!match[2]) 
                       break; 
            } 
         } 
    } 

    function onlyFCPhone(){
    	if ( ((event.keyCode < 48) || (event.keyCode > 57)) && (event.keyCode != 43 && event.keyCode != 45)  ) 
    	{
    		event.preventDefault?event.preventDefault():event.returnValue = false;
    	}
    }

    function isNum (v){
    	return (v.toString() && !/\D/.test(v));
    }

    function onlyNumber(obj){
    	$(obj).val($(obj).val().replace(/[^0-9]/g,''));   
    }
    function onlySysNumber(){
    	if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;
    }

    /*
    내    용 : Radio Box 체크
    파라미터 :	 pRadioBoxName - 라디오박스 이름
    				pCheckedValue - 체크할 값
    Return값 : 없슴
    */
    function fGetRadioChecked(pRadioBoxName){

    	var vRtn = "";
    	// 파라미터로 받은 이름을 가진 Element 의 배열을 변수에 저장
    	var vRadioBoxArray = document.getElementsByName(pRadioBoxName);
    	// 배열의 갯수만큼 loop

    	for(var i=0; i<vRadioBoxArray.length; i++){
    		// 체크상태 변경
    		if(vRadioBoxArray[i].checked){
    			vRtn = vRadioBoxArray[i].value;break;
    		}
    	}
    	return vRtn
    }

    //replaceAll prototype 선언
    String.prototype.replaceAll = function(org, dest) {
        return this.split(org).join(dest);
    }

    function fn_daumEditor(objid){
    	// 다음에디터 설정
    
    	$.ajax({
                url : ctxt+"/resources/daumeditor/pages/template/editor_template.html",
                success : function(data){
                	
                	$("#editor_frame").html(data);
                    
                	var config = {
                			txHost: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
                			txPath: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
                			txService: 'sample', /* 수정필요없음. */
                			txProject: 'sample', /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
                			initializedId: "", /* 대부분의 경우에 빈문자열 */
                			wrapper: "tx_trex_container", /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
                			form: 'tx_editor_form'+"", /* 등록하기 위한 Form 이름 */
                			txIconPath: "/resources/daumeditor/images/icon/editor/", /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
                			txDecoPath: "/resources/daumeditor/images/deco/contents/", /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
                			canvas: {
                                exitEditor:{
                                    /*
                                    desc:'빠져 나오시려면 shift+b를 누르세요.',
                                    hotKey: {
                                        shiftKey:true,
                                        keyCode:66
                                    },
                                    nextElement: document.getElementsByTagName('button')[0]
                                    */
                                },
                    			styles: {
                    				color: "#123456", /* 기본 글자색 */
                    				fontFamily: "나눔고딕", /* 기본 글자체 */
                    				fontSize: "9pt", /* 기본 글자크기 */
                    				backgroundColor: "#fff", /*기본 배경색 */
                    				lineHeight: "1.5", /*기본 줄간격 */
                    				padding: "10px" /* 위지윅 영역의 여백 */
                    			},
                    			showGuideArea: false
                    		},
                    		events: {
                    			preventUnload: false
                    		},
                    		sidebar: {
                    			attachbox: {
                    				show: true,
                    				confirmForDeleteAll: true
                    			},
                    			capacity: {
                    				available : 1024 * 1024 * 10, /* 첨부 용량 제한 */
                    				maximum: 1024 * 1024 * 10     /* 첨부 용량 제한 */
                    			}
                    		},
                    		toolbar :{
                    			fontfamily:{
                    				options:[
                    				        {label:'나눔고딕(<span class ="tx-txt">가나가라</span>)', title:'나눔고딕', data:'"나눔고딕" ,Nanum Gothic ,sans-sertif' ,klass :'tx-nanumgothic' },
                    				        { label: ' 맑은고딕 (<span class="tx-txt">가나다라</span>)', title: '맑은고딕', data: '"맑은 고딕",AppleGothic,sans-serif', klass: 'tx-gulim' },
                    		                { label: ' 굴림 (<span class="tx-txt">가나다라</span>)', title: '굴림', data: 'Gulim,굴림,AppleGothic,sans-serif', klass: 'tx-gulim' },
                    		                { label: ' 바탕 (<span class="tx-txt">가나다라</span>)', title: '바탕', data: 'Batang,바탕', klass: 'tx-batang' },
                    		                { label: ' 돋움 (<span class="tx-txt">가나다라</span>)', title: '돋움', data: 'Dotum,돋움', klass: 'tx-dotum' },
                    		                { label: ' 궁서 (<span class="tx-txt">가나다라</span>)', title: '궁서', data: 'Gungsuh,궁서', klass: 'tx-gungseo' },
                    		                { label: ' Arial (<span class="tx-txt">abcde</span>)', title: 'Arial', data: 'Arial', klass: 'tx-arial' },
                    		                { label: ' Verdana (<span class="tx-txt">abcde</span>)', title: 'Verdana', data: 'Verdana', klass: 'tx-verdana' },
                    		                { label: ' Arial Black (<span class="tx-txt">abcde</span>)', title: 'Arial Black', data: 'Arial Black', klass: 'tx-arial-black' }
                    		         /*       { label: ' Book Antiqua (<span class="tx-txt">abcde</span>)', title: 'Book Antiqua', data: 'Book Antiqua', klass: 'tx-book-antiqua' },
                    		                { label: ' Comic Sans MS (<span class="tx-txt">abcde</span>)', title: 'Comic Sans MS', data: 'Comic Sans MS', klass: 'tx-comic-sans-ms' },
                    		                { label: ' Courier New (<span class="tx-txt">abcde</span>)', title: 'Courier New', data: 'Courier New', klass: 'tx-courier-new' },
                    		                { label: ' Georgia (<span class="tx-txt">abcde</span>)', title: 'Georgia', data: 'Georgia', klass: 'tx-georgia' },
                    		                { label: ' Helvetica (<span class="tx-txt">abcde</span>)', title: 'Helvetica', data: 'Helvetica', klass: 'tx-helvetica' },
                    		                { label: ' Impact (<span class="tx-txt">abcde</span>)', title: 'Impact', data: 'Impact', klass: 'tx-impact' },
                    		                { label: ' Symbol (<span class="tx-txt">abcde</span>)', title: 'Symbol', data: 'Symbol', klass: 'tx-symbol' },
                    		                { label: ' Tahoma (<span class="tx-txt">abcde</span>)', title: 'Tahoma', data: 'Tahoma', klass: 'tx-tahoma' },
                    		                { label: ' Terminal (<span class="tx-txt">abcde</span>)', title: 'Terminal', data: 'Terminal', klass: 'tx-terminal' },
                    		                { label: ' Times New Roman (<span class="tx-txt">abcde</span>)', title: 'Times New Roman', data: 'Times New Roman', klass: 'tx-times-new-roman' },
                    		                { label: ' Trebuchet MS (<span class="tx-txt">abcde</span>)', title: 'Trebuchet MS', data: 'Trebuchet MS', klass: 'tx-trebuchet-ms' },
                    		                { label: ' Webdings (<span class="tx-txt">abcde</span>)', title: 'Webdings', data: 'Webdings', klass: 'tx-webdings' },
                    		                { label: ' Wingdings (<span class="tx-txt">abcde</span>)', title: 'Wingdings', data: 'Wingdings', klass: 'tx-wingdings' }*/
                    				        ]
                    			}
                    		},
                    		size: {
                    			//contentWidth: 670 /* 지정된 본문영역의 넓이가 있을 경우에 설정 */
                    		}
                    	};

                    	EditorJSLoader.ready(function(Editor) {
                    		var editor = new Editor(config);
                    		Editor.getCanvas().setCanvasSize({height:250});
                    			
                    		Editor.modify({
                                "content": document.getElementById(objid)/* 내용 문자열, 주어진 필드(textarea) 엘리먼트 */
                                
                    		});
                    		
                    	});
                    	
                }
            });
    	
    }


    function fn_download(file_type){
    	$('#file_type').val(file_type);
    	fileForm.action= ctxt+'/getFileDownload.do';
    	fileForm.submit();
    }




    // html2canvas 를 이용한 화면 캡쳐 다운로드 저장
    function sreenShot(target , img_id) {
    	if (target != null && target.length > 0) {
       
       	var t = target[0];	
       
       	var shotWidth = $(t).width();
       	var shotHeight = $(t).height();
    	 html2canvas(t , {
    		  width: shotWidth,
    		  height: shotHeight
    		}).then(function(canvas) {
    		
    		var myImg = canvas.toDataURL("image/png");
    		myImg = myImg.replace("data:image/png;base64,", "");

    		$.ajax({
    			type : "POST",
    			data : {
    				"imgSrc" : myImg
    			},
    			dataType : "text",
    			async: false,
    			url : ctxt +"/mng/stat/saveScreenShot.do",
    			success : function(data) {
    				console.log(data);
    				
    				//var chart_html = $('#excel_target').html().replace("screenShotPath3","https://library.nih.go.kr/ncmiklib/resources/images/screenshot/"    +data);
    				
    				$("#"+img_id).attr("src","https://library.nih.go.krlibrary.nih.go.kr/ncmiklib/resources/images/screenshot/"+data);
    				var chart_html = $('#excel_target').html();
    				url = ctxt+"/mng/stat/ncmiklibStatsExcel.do";
    				$('#chart_html').val(chart_html);
    				excel_form.action=url;
    				excel_form.submit();
    			},
    			error : function(a, b, c) {			
    				
    			}
    		});
    	}); 
    	 
    	}
    }


    function lf_pop_link(url, target)
    {
    	if(target=="Y")	//새창
    	{
    		window.open(url);
    		self.close();
    	}
    	else	//부모창
    	{
    		location.href = url;
    		self.close();
    	}
    }

    function removeHan(obj){
    	str = $(obj).val();
    	$(obj).val($(obj).val().replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ));
    	check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
    	if(check.test(str)){ 
    		setTimeout(function(){fn_showCustomAlert("영문을 입력하세요.");},200);
    	}
    }

    function isPhone(phoneNum) { 
    	var regExp =/(02|0[3-9]{1}[0-9]{1})[1-9]{1}[0-9]{2,3}[0-9]{4}$/; 
    	var regExp =/(02)([0-9]{3,4})([0-9]{4})$/; 
    	var myArray; 
    	if(regExp.test(phoneNum)){ 
    		myArray = regExp.exec(phoneNum); 
    		return true; 
    		} else { 
    			regExp =/(0[3-9]{1}[0-9]{1})([0-9]{3,4})([0-9]{4})$/; 
    			if(regExp.test(phoneNum)){ myArray = regExp.exec(phoneNum); 
    				return true; 
    			}else{ 
    				return false; 
    			} 
    		}
    	}

    function print2(printArea)
    {
    		win = window.open(); 
    		win.document.open();
    		/*
    			1. div 안의 모든 태그들을 innerHTML을 사용하여 매개변수로 받는다.
    			2. window.open() 을 사용하여 새 팝업창을 띄운다.
    			3. 열린 새 팝업창에 기본 <html><head><body>를 추가한다.
    			4. <body> 안에 매개변수로 받은 printArea를 추가한다.
    			5. window.print() 로 인쇄
    			6. 인쇄 확인이 되면 팝업창은 자동으로 window.close()를 호출하여 닫힘
    		*/
    		win.document.write('<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">');
    		win.document.write('<html>'); 
    		win.document.write('<head>');
        
    		win.document.write('<style>#printZone{width: 100%;height:90%; !important;} .table_v{height:90%; !important;}</style>' );
        	win.document.write('<script>function fn_print(){window.print();window.close();}<\/script>' );
            win.document.write('</head>');
            win.document.write('<body onload="fn_print()">');
        	/*
        	win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/new/styles.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/reset.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/font.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/layout.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/sub.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/responsive.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/css/rules.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='http://localhost:9090"+ctxt +"/resources/vendor/fontawesome-free-5.11.2-web/css/all.css'>");
    		*/
            win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/new/styles.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/reset.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/font.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/layout.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/sub.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/responsive.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/css/rules.css'>");
    		win.document.write("<link rel='stylesheet' type='text/css' href='https://moneybunny.kr"+ctxt +"/resources/vendor/fontawesome-free-5.11.2-web/css/all.css'>");
            win.document.write(printArea);		
            win.document.write('</body>' );
            win.document.write('</html>');
            win.document.close();
            $('svg').eq(1).remove();
         
    }

    function checkIpForm(ip_addr){
    	 
    	 var filter = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/;

    	 if (filter.test(ip_addr) == true){
    		 return false;
    	 } else{
    		 return true;
    	 }
    }

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

    function fn_map() {
    	  var map = {};
    	  map.value = {};
    	  map.getKey = function(id) {
    	    return id;
    	  };
    	  map.put = function(id, value) {
    	    var key = map.getKey(id);
    	    map.value[key] = value;
    	  };
    	  map.contains = function(id) {
    	    var key = map.getKey(id);
    	    if(map.value[key]) {
    	      return true;
    	    } else {
    	      return false;
    	    }
    	  };
    	  map.get = function(id) {
    	    var key = map.getKey(id);
    	    if(map.value[key]) {
    	      return map.value[key];
    	    }
    	    return null;
    	  };
    	  map.remove = function(id) {
    	    var key = map.getKey(id);
    	    if(map.contains(id)){
    	      map.value[key] = undefined;
    	    }
    	  };
    	 
    	  return map;
    }

    function fn_ajax(map) {
    	
    	var url = map.get("url");
    	var data = map.get("data")==null?"":map.get("data");
    	var type = map.get("type")==null?"GET":map.get("type");
    	var contentType = map.get("contentType")==null?"text/xml;charset=utf-8":map.get("contentType");
    	var dataType = map.get("dataType")==null?"text":map.get("dataType");
    	var callBack = map.get("callBack")==null?"":map.get("callBack");
    	
    	if(url == null){
    		fn_showCustomAlert("URL값이 없습니다.");
    		return;
    	}
    	
    	$.ajax({
            url: url,
            data : data,
            type: type,
            contentType: contentType,
            dataType: dataType,
            success: function(rtnXml) {
            	if(callBack != ""){
            		eval(callBack);
            	}
            },
            error: function(x, o, e) {
    	            var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
    	            fn_showCustomAlert(msg); 
    	    }            
        }); 
    }

    function ConvertSystemSourcetoHtml(str){
    	 str = str.replace(/</g,"&lt;");
    	 str = str.replace(/>/g,"&gt;");
    	 str = str.replace(/\"/g,"&quot;");
    	 str = str.replace(/\'/g,"&#39;");
    	 str = str.replace(/\n/g,"<br />");
    	 return str;
    }

    // 문자 복사하기 
    function fn_copyToClipboard(text) {
        var $tempInput = $("<input>");
        $("body").append($tempInput);
        $tempInput.val(text).select();
        document.execCommand("copy");
        $tempInput.remove();
        
        fn_showCustomAlert("복사가 완료 되었습니다.");
    }

    /*geenyungC 추가*/
    /*Tab Style*/
    function openTab(evt, tabName) {
    	  var i, tabcontent, tablinks;
    	  tabcontent = document.getElementsByClassName("tabcontent");
    	  for (i = 0; i < tabcontent.length; i++) {
    	    tabcontent[i].style.display = "none";
    	  }
    	  tablinks = document.getElementsByClassName("tablinks");
    	  for (i = 0; i < tablinks.length; i++) {
    	    tablinks[i].className = tablinks[i].className.replace(" active", "");
    	    tablinks[i].removeAttribute("title");
    	  }
    	  document.getElementById(tabName).style.display = "block";
    	  evt.currentTarget.className += " active";
    	  evt.currentTarget.title += "선택됨"
    	  if($('#select_tab').length > 0){
    		  $('#select_tab').val($('#'+tabName).index());
    	  }
    	}
    
    
    function openMutiTab(evt, tabName, id) {
    	var i, tabcontent, tablinks;
    	$('#'+id).find('.tabcontent').each(function(){
    		$(this).hide();
    	});
    	$('#'+id).find('.tablinks').each(function(){
    		$(this).removeClass('active');
    	});
    	$('#'+id).find('#'+tabName).css('display',"block");
    	evt.currentTarget.className += " active";
    	if($('#'+id).find('#select_tab').length > 0){
    		$('#'+id).find('#select_tab').val($('#'+id).find('#'+tabName).index());
    	}
    }
    /*Tab Style END*/
    
    function popClose(a) { 
    	$('#'+a).attr('style','display:none')
    }

    function popOpen(a) { 
    	// 새창을 클릭시 다른창은 닫도록 제어..
    	if(closePop!= ""){
    		if(a !=closePop){
    			popClose(closePop);
    		}else if(a == closePop){
    			popClose(closePop);
    			closePop = "";
    			return false;
    		}
    	}

    	closePop = a;
    	a = "";
    	document.getElementById(closePop).style.display = "block";
    }   
    /**
     * @param html
     * 모달팝업 오픈
     */
    function fn_popOpen(html){
		$('#pop_content').children().remove();		
		$('#pop_content').html(html);
		$('#footer_popup').addClass('is-visible');
		$('body').css('overflow', 'hidden');
		$("#popClose").focus();
		
		tabIndex = $("#pop_content").on().find('button, a, tabindex').get();
    }
    
    function fn_popClose(){
    	$('#footer_popup').removeClass('is-visible');
		$('body').css('overflow', 'auto');
		$("#"+focus_back+"").focus();
    }
    
    function fn_fileList(fileGroup){
    	
    	var urld= ctxt +"/attFile/attFileDown.do?fileGroup=" + fileGroup;
    	$.ajax({
    		url : urld,
    		success : function(result){
    			fn_popOpen(result);
    		}
    	});
    }
    
    function fn_fileDown(fileGroup){
		$("#file_group").val(fileGroup);
		
		var url = ctxt + "/ajax/attFile/downloadFileInfo.do";
		
		$("#downForm").attr('action', url);
		$("#downForm").submit();
	}
    
    /**
     * 로딩바
     */
    function fn_load(){
    	$('.loading').show();	
    }
    
    /**
     * 로딩바 종료
     */
    function fn_loadClose(){
    	$('.loading').fadeOut();  
    }
    
    
    
    
    
jQuery.fn.highlight = function(pat) {
	 function innerHighlight(node, pat) {
	  var skip = 0;
	  if (node.nodeType == 3) {
	   var pos = node.data.toUpperCase().indexOf(pat);
	   pos -= (node.data.substr(0, pos).toUpperCase().length - node.data.substr(0, pos).length);
	   if (pos >= 0) {
	    var spannode = document.createElement('span');
	    spannode.className = 'highlight';
	    var middlebit = node.splitText(pos);
	    var endbit = middlebit.splitText(pat.length);
	    var middleclone = middlebit.cloneNode(true);
	    spannode.appendChild(middleclone);
	    middlebit.parentNode.replaceChild(spannode, middlebit);
	    skip = 1;
	   }
	  }
	  else if (node.nodeType == 1 && node.childNodes && !/(script|style)/i.test(node.tagName)) {
	   for (var i = 0; i < node.childNodes.length; ++i) {
	    i += innerHighlight(node.childNodes[i], pat);
	   }
	  }
	  return skip;
	 }
	 return this.length && pat && pat.length ? this.each(function() {
	  innerHighlight(this, pat.toUpperCase());
	 }) : this;
	};

	jQuery.fn.removeHighlight = function() {
	 return this.find("span.highlight").each(function() {
	  this.parentNode.firstChild.nodeName;
	  with (this.parentNode) {
	   replaceChild(this.firstChild, this);
	   normalize();
	  }
	 }).end();
	};
	
	
	
	function serializeFormToJSON(form) {
	       var array = $(form).serializeArray();
	       var json = {};
	       $.each(array, function() {
	           json[this.name] = this.value || '';
	       });
	       return json;
	   }

	