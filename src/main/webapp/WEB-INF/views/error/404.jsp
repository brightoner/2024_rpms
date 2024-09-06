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
    width: 700px;
    margin: 6% auto 0;
    justify-content: center;
    align-items: center;
}

.error_title {
    display: grid;
}

.error_title:before {
    content: '';
    width: 130px;
    height: 10px;
    background: #ccc;
    display:block;
}

.error_name {
    font-size: 40px;
    font-weight: 600;
    margin: 0;
    color: #333;
}

.error_code {
    font-size: 380px;
    margin: 0 0 40px;
    background: #FEAC5E;
    background: -webkit-linear-gradient(to right, #4BC0C8, #C779D0, #FEAC5E);
    background: linear-gradient(to right, #4BC0C8, #C779D0, #FEAC5E);
    -webkit-text-fill-color: transparent;
    -webkit-background-clip: text;
    opacity: 0.8;
    font-weight: 700;
    height: 100%;
    line-height: 1;
}

.error_container_1 {
    display: grid;
    gap: 40px;
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
	<div class="error_title">
        <p class="error_name">Not found</p>
        <p class="error_code">404</p>
    </div>
	<div class="error_container_1">
		<!-- 에러메시지 텍스트 7줄 이상 넘어가지 마세요~ -->
		<div class="error_text_1">
			<p><strong>죄송합니다. 페이지를 찾을 수 없습니다.</strong></p>
			<p>Sorry, but the page you are looking for doesn't exist or an other error occurred.</p>
		</div>
		<!-- //에러메시지 텍스트 -->
		<div class="error_text_2">
			<a href="javascript:window.history.back();" class="goHome">이전 페이지로 돌아가기</a>
	      	<a href="${ctxt}/" class="goHome">메인 페이지로 이동하기</a>
		</div>
	</div>
</div>
<!-- //error_wrap -->
