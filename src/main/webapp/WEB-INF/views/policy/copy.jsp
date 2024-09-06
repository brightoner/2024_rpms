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
			<h2 class="title bor_b">저작권 정책(예시)</h2><br>
				<ul>
					<li>
						<ul>
							<li class="barMark">우리 기관 홈페이지에서 제공하는 모든 자료는 저작권법에 의하여 보호받는 저작물로서, 별도의 저작권 표시 또는 출처를 명시한 경우를 제외하고는 원칙적으로 우리 기관에 저작권이 있으며, 이를 무단 복제, 배포하는 경우에는 저작권법 제 97조 5조에 의한 저작재산권 침해죄에 해당함을 유념하시기 바랍니다.</li><br>
							<li class="barMark">우리 기관에서 제공하는 자료로 수익을 얻거나 이에 상응하는 혜택을 얻고자 하는 경우에는 우리 기관과 사전에 별도의 협의를 하거나 허락을 얻어야 하며, 협의 또는 허락에 의한 경우에도 출처가 질병관리청임을 반드시 명시해야 합니다.</li><br>
							<li class="barMark">우리 기관의 컨텐츠를 적법한 절차에 따라 다른 인터넷 사이트에 게재하는 경우에도 단순한 오류 정정 이외에 내용의 무단 변경을 금지하여, 이를 위반할 때에는 형사 처벌을 받을 수 있습니다. 또한 다른 인터넷 사이트에서 우리 기관 홈페이지로 링크하는 경우에도 링크 사실을 우리 기관에 반드시 통지하여야 합니다.</li><br>
							<li class="barMark">저작권법 제24조의2에 따라 질병관리청에서 저작재산권의 전부를 보유한 저작물의 경우에는 별도의 이용허락 없이 자유이용이 가능합니다.
												단, 자유이용이 가능한 자료는 <span>"공공저작물 자유이용허락 표시 기준(공공누리,KOGL) 제1유형"</span>&nbsp;<img src="${ctxt}/resources/images/sub/open_mark.jpg" alt= "공공누리 마크(OPEN:공공저작물의 열린 이용과 공유를 의미; 태극마크:공공누리의 공공성을 의미; 청록색:저작권의 올바른 활용(그린정보이용)을 의미" style="vertical-align: middle;">&nbsp;공공누리 공공저작물 자유이용허락을 부착하여 개방하고 있으므로 공공누리 표시가 부착된 저작물인지를 확인한 이후에 자유이용하시기 바랍니다. 자유이용의 경우에는 반드시 저작물의 출처를 구체적으로 표시하여야 합니다. 공공누리가 부착되지 않은 자료들을 사용하고자 할 경우에는 담당자와 사전에 협의한 이후에 이용하여 주시기 바랍니다.</li>
						</ul>
					</li>
				</ul>
		</div> 
	</div>		
	

<!-- 공통  필수 PARAM  -->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" alt = "token" />
