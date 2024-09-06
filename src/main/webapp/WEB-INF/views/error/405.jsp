<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
 
<style type="text/css">
body {
    background-color: #f9f9f9;
}

.error_wrap {
    line-height: 1.5;
    font-family: 'Noto Sans KR', sans-serif;
    word-wrap: break-word;
    word-break: break-all;
    display: grid;
    height: 100vh;
    background: url(/resources/images/common/error405.png) left 100px no-repeat;
    width: 700px;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
}

.error_container_1 {
    width: 650px;
    display: grid;
    gap: 40px;
    margin: 45% 0 0 20px;
}

.error_text_1 {
	color: #888;
}

.error_text_1 p {
	margin: 4px 0;
}

.error_text_1 strong {
	color: #333;
}

.goHome{
    margin: 0 10px 0 0;
    text-align: center;
    font-size: 14px;
    color:  #F97E15;
    border-radius: 3px;
    padding: 10px 15px;
    border: 1px solid #F97E15;
    text-decoration: none;
    transition: all ease 0.1s;
}

.goHome:hover {
    background: #f97315;
    color: #fff;
}
</style>

<!-- error_wrap -->
<div class="error_wrap">
	<div class="error_container_1">
		<!-- 에러메시지 텍스트 7줄 이상 넘어가지 마세요~ -->
		<div class="error_text_1">
			<p><strong>죄송합니다. 정상적인 접근이 아닙니다.</strong></p>
			<p>Sorry, Not a normal approach.</p>
		</div>
		<!-- //에러메시지 텍스트 -->
		<div class="error_text_2">
			<a href="javascript:window.history.back();" class="goHome">이전 페이지로 돌아가기</a>
	      	<a href="${ctxt}/" class="goHome">메인 페이지로 이동하기</a>
		</div>
	</div>
</div>
<!-- //error_wrap -->
