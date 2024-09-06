<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



	<div class="content_body">
		<div class="row section clear">
			<h2 class="title bor_b">청소년 보호 정책(예시)</h2><br>
					<div class="container mt-4">
					OOOO 주식회사(이하 회사'라 함)은 청소년이 건전한 인격체로 성장할 수 있도록 하고, 청소년 유해정보로부터 청소년을 보호하며,청소년의 권익보호를 위하여 정보통신망 이용촉진 및 정보보호등에 관한 법률 및 청소년보호법에 근거하여 청소년 보호정책을 수립, 시행하고 있습니다.
					</div>
					<div class="container mt-4">
					  <h5>1. 유해정보에 대한 청소년 접근제한 및 관리조치</h5>
					  <ul>
					    <li>청소년이 아무런 제한장치 없이 유해정보에 노출되지 않도록 청소년 유해매체물에 대해서는 인증장치를 마련•적용하고 있으며, 유해정보가 노출되지 않게 하기 위한 예방 차원의 조치를 마련하고 있습니다.</li>					 
					  </ul>
					</div>
					<div class="container mt-4">
					  <h5>2. 이용자 인식의 제고</h5>
					  <ul>
					    <li>불건전한 행위를 할 경우 이용제한 및 민.형사상의 책임질 수 있음을 쉽게 인식하도록 안내 및 신종 유해정보 발생시 대처요령에 대해 필요한 조치를 마련하고 있습니다.</li>					 
					  </ul>
					</div>
					<div class="container mt-4">
					  <h5>3. 개인정보의 보호</h5>
					  <ul>
					    <li>회사는 서비스 약관 등을 통하여 타인의 개인정보를 도용할 경우 민.형사상 책임을 받을 수 있음을 고지하고 있으며, 미성년 이용자에 대한 부모(법정대리인)의 실질적인 동의를 얻을 수 있는 장치를 마련하고 있습니다.</li>					 
					  </ul>
					</div>
					<div class="container mt-4">
					  <h5>4. 불량이용자의 대응</h5>
					  <ul>
					    <li>청소년의 유해정보로부터 보호하고 피해확산을 방지하기 위하여 모니터링을 실시하고 있으며, 권리침해센터를 통한 피해상담 및 고충처리를 하고 있습니다.</li>					 
					    <li>청소년보호 관련 기관</li>					 
					    <li>- 방송통신심의위원회 (http://www.singo.or.kr/)</li>					 
					    <li>- 보건복지가족부 아동청소년정책실 (http://www.youth.go.kr)</li>
					    
					  </ul>
					</div>
				
					
					<div class="container mt-4">
					  <h5>5. 청소년보호 책임자의 지정</h5>
					  <ul>
					  	<li>회사는 청소년 유해정보로 인한 피해상담 및 고충처리 전문인력을 배치하여 그 피해가 확산되지않도록 하고 있습니다. 이용자 분들께서는 하단에 명시한 더이앤엠 주식회사 청소년보호 책임자 및 담당자의 소속, 성명 및 연락처를 확인 참고하여 전화나 메일을 통하여 피해상담 및 고충처리를 요청할 수 있습니다.</li>
					    <li>개인정보/청소년보호 관리 책임자</li>
					      <ul>
					        <li>이름 : 홍길동</li>
					        <li>소속 : OOO 부서</li>
					        <li>직위 : 이사</li>
					        <li>E-mail: OOO@naver.com</li>
					      </ul>
					    <li>개인정보/청소년보호 관리 담당자</li>
					      <ul>
					        <li>이름 : 김철수</li>
					        <li>소속 : OOO 부서</li>
					        <li>직위 : 팀장</li>
					        <li>E-mail: OOO@naver.com</li>
					      </ul>
					  </ul>
					</div>
		</div> 
	</div>		
	

<!-- 공통  필수 PARAM  -->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt = "token" />
