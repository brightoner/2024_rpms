<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="kr.go.rastech.ptl.mng.menu.vo.MngMenuVo"%>
<%@page import="kr.go.rastech.ptl.mng.code.vo.MngCodeVo"%>
<%@page import="kr.go.rastech.base.controller.BaseController"%>
<%@page import="java.util.List"%>
<script type="text/javascript">
var cuurPage;
var pagetotalCnt = 0;
function fn_search(){
	
	$('#boardlist').children().remove();
    $.ajax({
        url: '${ctxt}/mng/ip/selectIpList.do',
        data : {},
        type: 'GET',   
        contentType: "text/xml;charset=utf-8",   
        dataType: 'text',
        success: function(rtnXml) {
        	var xmlObj = $(rtnXml).find('item');
        	pagetotalCnt=0;
			if(xmlObj.length > 0){
				var html="";
				var sel_opt="";
				xmlObj.each(function(cnt){   
					html += '<tr onclick="fn_setRow(this,\'iplist\')">';
					html += '<td class="center">'+ (cnt +1) +'</td>';
         			html += '<td>'+$(this).text() +'</td>';
         			html += '<td class="center"><input type=\"button\" class="line_h_10 h_25"  value=\"삭제\" onclick=\"fn_del(this);\"></td>';
         			html += '</tr>';     
				});
				$('#iplist').html(html);   
				<c:if test="${if_yn == 'Y'}"> 
					parent.calcHeight();
				</c:if>
				
				fn_rowOn('example0');
			}
        },
        error: function(x, o, e) {
	            var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
	            fn_showCustomAlert(msg); 
	    }            
    }); 
}

function fn_add(){
	var params = {};
 		params.ip_type = "I";
 		params.ip = $('#save_ip').val();
 		
 	var isIp = checkIpForm($('#save_ip').val());	
 	
 	if(isIp){
		 fn_showCustomAlert("IP 주소 형식이 틀립니다.");
		 return;
 	}
 	
 	if(confirm("저장하시겠습니까?")){	
		$.ajax({
	        url: '${ctxt}/mng/ip/saveIp.do',
	        data : params,
	        type: 'get',
	        dataType: 'text',
	        cache: false,
	        success: function(rtnXml) {
	        	fn_showCustomAlert("ip추가완료!");  
	        	fn_search();
	        },
	        error: function(x, o, e) {
		            var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
		            fn_showCustomAlert(msg); 
		    }              
	    }); 
 	} 	
}

function fn_del(obj){    
	var params = {};
	 	params.ip_type = "D";
	 	params.ip = $(obj).parent().parent().find("td:eq(1)").text();
	 if(confirm("삭제하시겠습니까?")){
		$.ajax({
	        url: '${ctxt}/mng/ip/saveIp.do',  
	        data : params,
	        type: 'POST',   
	        dataType: 'text',
	        success: function(rtnXml) {
	        	fn_showCustomAlert("ip삭제완료!");  
	        	fn_search();
	        },
	        error: function(x, o, e) {
		            var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e; 
		            fn_showCustomAlert(msg); 
		    }            
	    }); 
	 }
}


$(function(){   
	fn_search(1);
});

</script>

<div id="container">
	<div id="divRefreshArea">
	<h1 class="page_title" >IP 관리</h1>    
	    <br />
     	<table id="example0" class="table_h"> 
        	<colgroup>
        			<col width="*" />
	         		<col width="*" />
	         		<col width="*" />
	           </colgroup> 
	           <thead>    
	         	<tr>            
	         		<th>순번</th>
	         		<th>IP</th>
	         		<th>삭제</th>      
	        	 </tr>           
	       	</thead>
	       	<tbody id="iplist">       
	        </tbody>
     	</table>  
	</div>
	<div class="text_r">
		등록 ip : <input type="text" id="save_ip" class="w_15">&nbsp;&nbsp;&nbsp;
		<input type="button" value="등록" onclick="fn_add();">
	</div>
</div>	

