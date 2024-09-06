<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
var isdoDblCheck=false;           
var isdoHpCheck=false;           
           
function frame_Check(){
  if(xFrameViewer.object==null){
    document.getElementById("cdcframe").style.display = 'block';
    document.getElementById("main_page").style.display = 'none';    
  }
}
              
           
 
function winClose()
{
	window.close();
}
 
function doDeptPop(){

    var f = document.getElementById("frm") ;
    
    
    var form = document.paramForm;
    var url = "${ctxt}${ctxt}pop/listAg.do";
    
    window.open("","paramForm","width=850 height=550 scrollbars=yes menubar=no location=no") ;    
    
    form.action =url; 
	form.method="post";
	form.target="paramForm";

	form.submit();	
}   

var codeCheck = function(acCode) {//비밀번호 구성 조건(영문,숫자,특수문자 하나 이상 포함하여 9자리 이상으로 입력)
	var pattern1 = /[a-zA-Z]{1,}/g ;
	var pattern2 = /[^0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힝]{1,}/g ;
	var pattern3 = /[0-9]{1,}/g ;
	var pattern4 = /[ㄱ-ㅎㅏ-ㅣ가-힝]{1,}/g ;
	var acCodeHanName ="비밀번호";
	var returnVal = false; 
	
	if (!pattern1.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 영문을 포함하여 입력하셔야 합니다.");
	}
	else if (!pattern2.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 특수문자를 포함하여 입력하셔야 합니다");
	}
	else if (!pattern3.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 숫자를 포함하여 입력하셔야 합니다.");
	}
	else if (pattern4.test(acCode)) {
		fn_showCustomAlert(acCodeHanName + "는 한글을 쓸 수 없습니다.");
	}
	else if (acCode.length < 9) {
		fn_showCustomAlert(acCodeHanName + "는 9자리 이상으로 입력하셔야 합니다.");
	}
	else {
		returnVal = true;
	}
	
	return returnVal;

};
    
function doOrgTChanged(obj){
    var f = document.frm ;  
    f.insttnm.value = "" ;          
    f.insttsn.value = "" ;        
    f.selauth2.value = "" ;       
    window.focus();
}

function removeBlank(str){ 
    str = str.replace(/\s/g,''); 
    return str;
}

var isTime = true;
var stDate = new Date().getTime();
var edDate = new Date().setTime(new Date().getTime()+ 180000); // 종료날짜
var RemainDate = edDate - stDate;
 
function msg_time() {
  var hours = Math.floor((RemainDate % (1000 * 60 * 60 * 24)) / (1000*60*60));
  var miniutes = Math.floor((RemainDate % (1000 * 60 * 60)) / (1000*60));
  var seconds = Math.floor((RemainDate % (1000 * 60)) / 1000);
  if(seconds<10){
	  seconds ="0"+seconds;
  }
  m =  "0"+miniutes + ":" + seconds ; 
  
  document.all.timer.innerHTML = m;  
  
  if (RemainDate == 0) {      
    // 시간이 종료 되었으면..
    isTime = false;
	$('#mbtlnum_yn').val("N");
	$('#c_btn').attr('style',"display:none;");
	$('#r_btn').attr('style',"");
    clearInterval(tid);   // 타이머 해제
  }else{
    RemainDate = RemainDate - 1000; // 남은시간 -1초  
  }
}

function doSmsCheck(){
	$('#sendNum').val('');
	$.ajax({
		url: '${ctxt}/login/sms/crateConfimNum.do',
		data: {"phnum": '${vo.mbtlnum}'},
		type: 'POST',
		cache: false,  
		success: function(result){
			msg_time(); 
		}, error: function(e){
			fn_showCustomAlert('관리자에게 문의 해주세요.');
		}  

	});
	
	tid=setInterval('msg_time()',1000); // 타이머 1초간격으로 수행
}


function fn_reconfirm(){

	isTime = true;
	stDate = new Date().getTime();
	edDate = new Date().setTime(new Date().getTime()+ 180000); // 종료날짜
	RemainDate = edDate - stDate;
	
	doSmsCheck();
	
	$('#c_btn').attr('style',"");
	$('#r_btn').attr('style',"display:none;");
}

/*  
 * sms확인
 */
function fn_confirm(){
    isTime = false;
    clearInterval(tid);   // 타이머 해제
	
	$.ajax({
		url: '${ctxt}/login/sms/confirm.do',
		data: {"sendNum": $('#sendNum').val()
			 , "phnum": '${vo.mbtlnum}'
		},
		type: 'POST',
		cache: false,
		success: function(result){
			if(result == "Y"){
				isdoHpCheck = true;
				$('.page_title').text('회원탈퇴');   
				$('#reqestBody').hide();   
				$('#deleteForm').show();   
				$('#box_1').hide();   
				$('#box_2').show();  
			}else{
				isdoHpCheck = false;
				$('#c_btn').attr('style',"display:none;");
				$('#r_btn').attr('style',"");
				$('#sendNum').val('');
				fn_showCustomAlert("본인인증이 실패되었습니다.");				
			}
		}, error: function(e){
			fn_showCustomAlert('관리자에게 문의 해주세요.');
		}

	});
}



function certcheck(btn){
    if ( btn.checked == true ) {
        document.getElementById("dis").style.display = "" ;
    } else {
        document.getElementById("dis").style.display = "none" ;
    }
}

function choice(o){
    var v = o.value ;
    document.frm.selauth2.value = v ;
}

function checkNumber(obj) {
    
    if ( !( ( event.keyCode >= 37 && event.keyCode <= 57 )
            || ( event.keyCode >= 96 && event.keyCode <= 105 ) 
            || event.keyCode == 8 || event.keyCode == 9
          )     
       ) {
        fn_showCustomAlert( "숫자만 입력해 주세요.") ;
        event.returnValue = false ;
    } else {
        var n = obj.value.replace(/\-/g , "") ;
        var len = n.length + 1 ;
        var number = n ;
        if ( n.substring ( 0 , 2 ) == "02" ) {
               if ( len > 2 ) {
                   number = n.substring( 0 , 2 ) + "-" ;
                   if ( len > 2 && len < 6 ) {
                       number += n.substring( 2 ) ;    
                   } else if ( len > 5 && len < 10 ) {
                       number += n.substring( 2 , 5 ) + "-" ;
                       number += n.substring( 5 ) ;
                   } else if ( len == 10 ) {
                       number += n.substring( 2 , 6 ) + "-" ;
                       number += n.substring( 6 ) ;
                   }
               }
        } else {
            if ( len > 3 ) {
                number = n.substring( 0 , 3 ) + "-" ;
                if ( len > 3 && len < 7 ) {
                    number += n.substring( 3 ) ;    
                } else if ( len > 6 && len < 11 ) {
                    number += n.substring( 3 , 6 ) + "-" ;
                    number += n.substring( 6 ) ;
                } else if ( len == 11 ) {
                    number += n.substring( 3 , 7 ) + "-" ;
                    number += n.substring( 7 ) ;
                }
            }
        }
        obj.value = number ;
    }
}

function doSave(){
		var userpwd = document.getElementById("userPw");
		var userpwd1 = document.getElementById("userPw1");
		
		if(userpwd.value != ''){
	    	if($('#userPw').val() != $('#userPw1').val()){
	    		fn_showCustomAlert('비밀번호와 비밀번화 학인이 다릅니다 확인하세요.');
	    		$('#chg_userPw').focous();
	    		return;
	    	}
		}
		
		$.ajax({
				url : '${ctxt}/login/user/mng/saveUser.do',  
				data : $("form[name=frm]").serialize(),
				processData : false,
				//contentType: false,
				type : 'POST',
				traditional : true,
				cache: false,
				success : function(result) {
					fn_showCustomAlert("저장이 완료되었습니다.");
					setTimeout(function(){location.reload(); Close();},1000);
					
				},
				error : function() { // Ajax 전송 에러 발생시 실행  
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행
					
				}
			});
		
		//self.close();
		
	}

	function doDelete(){
		$('#div_view').show()
	}
	
	
	$(function(){
	
	})
	
	function fn_userDel(id,uid){
		if (confirm(id + " 회원탈퇴를 하시겠습니까?")) {
			$.ajax({
				url : '${ctxt}/mng/user/deleteUserMng.do',  
				data : {"ui_idno":uid},
				type : 'POST',
				cache: false,
				success : function(result) {
					fn_showCustomAlert(id+" 회원탈퇴가 처리 되었습니다.");
					opener.fn_search(1);
					setTimeout(function(){window.close();},1000);
				},
				error : function() { // Ajax 전송 에러 발생시 실행
					fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
				},
				complete : function(result) { //  success, error 실행 후 최종적으로 실행
					
				}
			});
		}  
	}
	
</script>
</head>  

<form action="" name="popForm" target="hidden_fr" method="post">
	<input type="hidden" name="phnum" value="" />
	<input type="hidden" name="type" value="U" />
</form>

<form  action=""  name ="paramForm"  method="post"  >
		<input type="hidden" id="apre" name="apre" value="user_registry" alt="파라미터">
</form>
<div id="wrapper">
	<div id="container">
		<h3 class="page_title">회원정보</h3>
    <form name="frm" id="frm" method="post" action="">
    <input type="hidden" name="form_nm"    id="form_nm"   value="user"  />
    <input type="hidden" name="insttsn"    id="insttsn"   value="${vo.insttsn}"   />
    <input type="hidden" name="dblchk"     id="dblchk"     value="0" />
    <input type="hidden" name="dblchkID"   id="dblchkID"   value="0" />
    <input type="hidden" name="dblchkHP"   id="dblchkHP"   value="0" />
    <input type="hidden" name="mbtlnum_yn" id="mbtlnum_yn" value="Y" />
	<input type="hidden" name="ui_email" id="ui_email" value="" />
	<input type="hidden" name="crud_type" id="crud_type" value="U" />
	<input type="hidden" name="emplyrkey" id="emplyrkey" value="${vo.emplyrkey}" />
    
    <table class="table_v bor_t"  cellspacing="0" border="0" >
        <colgroup>
	        <col width="12%" />
	        <col width="38%" />
	        <col width="12%" />
	        <col width="38%" />
        </colgroup>
        <caption>회원가입</caption>
        <tbody>
        <tr>
            <th scope="row"  nowrap>휴대폰번호<br ></th>
            <td>
                <input type="text" name="mbtlnum" id="mbtlnum" class="disable w_full" tabindex="2" size="11" maxlength="11" readonly="readonly" value="${vo.mbtlnum}" title="본인확인된 전화번호" /> 
            </td>  
            <th scope="row" ><label for="nicknm">이름<br ></label></th>
            <td>
                <input type="text" name="nicknm" id="nicknm" class="disable" tabindex="4" maxlength="40" readonly="readonly" value="${vo.nicknm}" style="ime-mode:active;" title="이름 입력"  >
            </td>
        </tr>  
        <tr>
            <th scope="row" ><label for="loginid">아이디</label></th>
            <td>
                <input type="text" name="loginid" id="loginid" tabindex="5" class="disable w_full" readonly="readonly" maxlength="25" value="${vo.loginid}" style="ime-mode:disabled;width:231px;"  onkeydown="javascript:frm.dblchkID.value=0;" title="아이디 입력">
            </td>
            <th scope="row" class=""><label for="orgtype">이메일</label></th>
            <td nowrap>
                <input type="text" id="insttnm" name="insttnm" value="" class="w_full"  tabindex="10" readonly="readonly" title="기관(부서) 입력">
            </td>
        </tr>
        <tr>
           <th scope="row" ><label for="telno">주소</label></th>
           <td colspan="3" class="clear">
               <input type="text" name="adress" id="adress" value="" tabindex="7" class="w_full" />
           </td>
        </tr>
        </tbody>
        </table>
        <br />
        <h3 class="page_title">비밀번호 변경</h3>
        <table class="table_v bor_t"  cellspacing="0" border="0" >
        <colgroup>
        	<col width="12%" />
	        <col width="38%" />
	        <col width="12%" />
	        <col width="38%" />
        </colgroup>
        <caption>비밀번호 변경</caption>
        <tbody>
	        <tr>
	            <th scope="row" class=""><label for="userPw">비밀번호</label></th>
	            <td><input type="password" name="userPw" id="userPw" value='' autocomplete="new-password" maxlength="30" tabindex="7"></td>
	            <th scope="row" class=""><label for="userPw1">비밀번호 확인</label></th>
	            <td><input type="password" name="userPw1" id="userPw1" value='' autocomplete="new-password" maxlength="30" tabindex="8"></td>
	        </tr>
        </tbody>
        </table>
    </form>

	<div class="button_box text_r">	
	    <input type="button" name="dis" id="dis" value="저장" class="Btn60" onclick="doSave();" style="" tabindex="16" />
	    <input type="button" name="userDel" id="userDel" value="탈퇴" class="Btn60" onclick="doDelete();" style="" tabindex="16" />
	</div>

	<iframe name="savePop" id="savePop" title="의미없는 아이프레임" style="display:none;" width="0" height="0"></iframe>
	<iframe id="eduauthor" name="eduauthor" style="width:0px; height:0px; top: 0px; left:0px; position: absolute; visibility:hidden;"></iframe>
	<iframe name="hidden_fr" id="hidden_fr" title="의미없는 아이프레임" style="display:none;" width="0" height="0"></iframe>    

	</div>
</div>
	<div id="div_view" class="modal" >
		<div class="modal-content relative" style="width:300px;">	
			<h3 class="page_title">회원탈퇴</h3>  
			<form name="deleteForm" id="deleteForm" class="text_l ma_l_20r">  
				<p><span>아이디</span> : ${vo.loginid}</p>      
				<p class="ma_t_5"><span>이&nbsp;&nbsp;&nbsp;름</span> : ${vo.nicknm}</p>      
				<br />
				<input type="hidden" value="${vo.emplyrkey}" name="ui_idno">
			</form>     
			<br />
			<div class="button_box" id="box_2">  
				<input type="button" id="c_btn" value="탈퇴" style="cursor:pointer;" onclick="fn_userDel('${vo.loginid}','${vo.emplyrkey}');" />
				<button type="button" class="close" onclick="" >닫기</button>
			</div>
		</div>  
	</div>  
	  
<script>
/* MODAL */
var modal = document.getElementById("div_view");
var span = document.getElementsByClassName("close")[0];

span.onclick = function() {
  modal.style.display = "none";
}

</script>