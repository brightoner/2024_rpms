<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
.error_wrap{line-height:1.5; font-family:dotum, Arial, Verdana, Helvetica, sans-serif; word-wrap:break-word; word-break:break-all;}
.error_container_1{position:relative; width:620px; height:384px; margin:80px auto 0 auto; background:url(${ctxt}/resources/images/common/error_bg_2.jpg) left top no-repeat;}
.error_text_1{position:absolute; top:160px; left:340px; width:230px; font-size:12px; margin-top: 4%;}
.error_text_1 strong{color:#ca4e00;}
.error_text_2{position:absolute; top:400px; left:180px; width:400px; font-size:12px;}
.goHome{
    width: 120%;
    height: 45px;
    margin-bottom: 15px;
    text-align: center;
    font-size: 14px;
    font-weight: bold;
    color:  #225796;
    border-radius: 3px;
    box-shadow: 0 1px 1px #aaa;
}
</style>

<!-- error_wrap -->
<div class="error_wrap">
	<div class="error_container_1">


		<!-- 에러메시지 텍스트 7줄 이상 넘어가지 마세요~ -->
		<div class="error_text_1">
			<strong>죄송합니다.</strong> <br />	
			<c:out value="${msg.STATUS_CODE}"></c:out> : <c:out value="${msg.MESSAGE}"></c:out> <br />
							
		</div>
		<!-- //에러메시지 텍스트 -->
		<div class="error_text_2">
			<a href="javascript:window.history.back();" class="goHome">이전 페이지로 돌아가기</a>&nbsp;&nbsp;
	      	<a href="${ctxt}/" class="goHome">메인 페이지로 이동하기</a>
		</div>
	</div>
</div>
<!-- //error_wrap -->
