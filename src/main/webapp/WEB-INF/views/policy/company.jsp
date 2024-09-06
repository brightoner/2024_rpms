<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<span id="page_name" class="hide">저작권 정책</span>

	<div class="content_body">
		<div class="row section clear">
			<h2 class="title bor_b">회사소개(예시)</h2><br>
				<div class="container mt-4">
					  <h5>(주)라스테크</h5>
					  	  <h5>Reliable, Available and Serviceable Technology</h5>
					  <ul>
					    <li>(주)라스테크는 KAIST 연구소 기업으로서, 기술개발, 고객만족, 인재중심, 품질경영, 양질의 서비스라는 기업이념을 바탕으로, 빅데이터 및 DB, DW 구축 등 학술정보 전문 서비스, 시스템통합, IT 솔루션 서비스 및 IoT를 기반으로 한 지능형 서비스 로봇 개발 등 스마트 시대를 선도하는 ICT 기업입니다.</li>					 
					  </ul>
				</div>
		</div> 
	</div>		
	

<!-- 공통  필수 PARAM  -->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt = "token" />
