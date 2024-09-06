<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
.title:after {
    content: '';
    width: 5px;
    height: 5px;
    border: 3px solid #333;
    border-radius: 50%;
    position: absolute;
    left: 0.2rem;
    top: 1.1em;
}
.title {
	color: #333;
	padding-left: 1.25rem;
}

@media screen and (max-width: 768px) {
	.title:after {
	    width: 3px;
	    height: 3px;
	}
}
</style>

<h2 class="title bor_b">개인정보처리방침(예시)</h2><br>
<div class="content_body">
	<div class="row section clear"> 
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보의 처리 목적, 개인정보의 처리 및 보유기간, 처리하는 개인정보의 항목</h2></li>
			<li class="barMark ma_t_10">질병관리청 개인정보파일 등록사항 공개는 개인정보보호위원회 개인정보보호 포털(<a href="https://www.privacy.go.kr/" target="_blank" rel="noopener noreferrer" title="새 창에서 열림">www.privacy.go.kr</a>) → 민원마당 → 개인정보열람등 요구 → 개인정보파일 목록검색 메뉴를 활용해주시기 바랍니다.</li>
			<li class="pa_l_10 ma_t_10">개인정보 파일목록 [첨부파일 다운로드]</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보의 제3자 제공에 관한 사항</h2></li>
			<li class="barMark ma_t_10">질병관리청은 원칙적으로 정보주체의 개인정보를 수집·이용 목적으로 명시한 범위 내에서 처리하며, 다음의 경우를 제외하고는 정보주체의 사전 동의 없이는 본래의 목적 범위를 초과하여 처리하거나 제3자에게 제공하지 않습니다.</li>
			<li>
				<ul class="pa_l_15">
					<li class="interpunctMark ma_t_10">정보주체로부터 별도의 동의를 받는 경우</li>
					<li class="interpunctMark ma_t_10">법률에 특별한 규정이 있는 경우</li>
					<li class="interpunctMark ma_t_10">정보주체 또는 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우</li>
					<li class="interpunctMark ma_t_10">통계작성 및 학술연구 등의 목적을 위하여 필요한 경우로서 특정 개인을 알아 볼 수 없는 형태로 개인정보를 제공하는 경우</li>
					<li class="interpunctMark ma_t_10">개인정보를 목적 외의 용도로 이용하거나 이를 제3자에게 제공하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우로서 보호위원회의 심의·의결을 거친 경우</li>
					<li class="interpunctMark ma_t_10">조약, 그 밖의 국제협정의 이행을 위하여 외국정부 또는 국제기구에 제공하기 위하여 필요한 경우</li>
					<li class="interpunctMark ma_t_10">범죄의 수사와 공소의 제기 및 유지를 위하여 필요한 경우</li>
					<li class="interpunctMark ma_t_10">법원의 재판업무 수행을 위하여 필요한 경우</li>
					<li class="interpunctMark ma_t_10">형 및 감호, 보호처분의 집행을 위하여 필요한 경우</li>
				</ul>
			</li>
			<li class="barMark ma_t_10">제3자가 질병관리청의 동의 없이 개인정보를 제3자에게 재 제공하여 제3자가 처리하고 있는 것이 확인될 경우, 다음과 같이 해당 개인정보의 제공을 중단하고 이용을 금지시키고 있습니다.</li>
			<!-- <li>
				<ul class="pa_l_15 pa_t_10">
					<li class="interpunctMark">개인정보를 제공받은 대상기관의 개인정보 재 제공사항 확인</li>
					<li class="interpunctMark">개인정보 보호담당자에 의한 경고</li>
					<li class="interpunctMark">개인정보 재 제공 사실의 재 확인</li>
					<li class="interpunctMark">내부결재를 통해 승인을 득한 후 개인정보 제공 중단관련 공문 발송 및 개인정보 제공 중단</li>
				</ul>
			</li> -->
			<li class="pa_l_10 ma_t_10">개인정보의 제3자 제공 현황 [첨부파일 다운로드]</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보처리 위탁에 관한 사항</h2></li>
			<li class="barMark ma_t_10">질병관리청은 개인정보의 처리업무를 위탁하는 경우 다음의 내용이 포함된 문서에 의하여 처리하고 있습니다.</li>
			<li>
				<ul class="pa_l_15">
					<li class="interpunctMark ma_t_10">위탁업무 수행 목적 외 개인정보의 처리 금지에 관한 사항</li>
					<li class="interpunctMark ma_t_10">개인정보의 관리적·기술적 보호조치에 관한 사항</li>
					<li class="interpunctMark ma_t_10">개인정보의 안전관리에 관한 사항</li>
					<li class="interpunctMark ma_t_10">위탁업무의 목적 및 범위, 재위탁 제한에 관한 사항, 개인정보 안전성 확보 조치에 관한 사항, 위탁업무와 관련하여 보유하고 있는 개인정보의 관리현황점검 등 감독에 관한 사항, 수탁자가 준수하여야 할 의무를 위반한 경우의 손해배상책임에 관한 사항</li>
					<li class="interpunctMark ma_t_10">위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개</li>
				</ul>
			</li>
			<li class="barMark ma_t_10">질병관리청 개인정보파일 위탁현황 사항 공개는 개인정보보호위원회 개인정보보호 포털(<a href="https://www.privacy.go.kr/" target="_blank" rel="noopener noreferrer" title="새 창에서 열림">www.privacy.go.kr</a>) → 민원마당 → 개인정보열람등 요구 → 개인정보파일 목록검색 메뉴를 활용해주시기 바랍니다.</li>
			<li class="pa_l_10 ma_t_10">개인 정보 위탁 현황 [첨부파일 다운로드]</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">정보주체의 권리의무 및 그 행사방법에 관한 사항</h2></li>
			<li class="barMark ma_t_10">정보주체는 다음과 같은 권리를 행사 할 수 있으며, 만14세 미만 아동의 법정대리인은 그 아동의 개인정보에 대한 열람, 정정, 삭제, 처리정지를 요구할 수 있습니다.</li>
			<li>	
				<ul class="pa_l_15 pa_t_10">
					<li class="interpunctMark">(개인정보 열람 요구) 질병관리청에서 보유하고 있는 개인정보파일은「개인정보보호법」제35조(개인정보의 열람)에 따라 자신의 개인정보에 대한 열람을 요구할 수 있습니다. 다만, 개인정보 열람 요구는 법 제35조 4항에 의하여 다음과 같이 제한될 수 있습니다.</li>
					<li>
						<ul class="pa_l_15">
							<li class="barMark ma_t_10">법률에 따라 열람이 금지되거나 제한되는 경우</li>
							<li class="barMark ma_t_10">다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</li>
							<li class="barMark ma_t_10">공공기관이 다음 어느 하나에 해당하는 업무를 수행할 때 중대한 지장을 초래하는 경우</li>
							<li>
								<ul class="pa_l_15 pa_b_10">
									<li class="ma_t_10">1. 조세의 부과·징수 또는 환급에 관한 업무</li>
									<li class="ma_t_10">2.「초·중등교육법」및「고등교육법」에 따른 각급 학교,「평생교육법」에 따른 평생교육시설, 그 밖의 다른 법률에 따라 설치 된 고등교육기관에서의 성적 평가 또는 입학자 선발에 관한 업무
									<li class="ma_t_10">3. 학력·기능 및 채용에 관한 시험, 자격 심사에 관한 업무</li>
									<li class="ma_t_10">4. 보상금·급부금 산정 등에 대하여 진행 중인 평가 또는 판단에 관한 업무</li>
									<li class="ma_t_10">5. 다른 법률에 따라 진행 중인 감사 및 조사에 관한 업무</li>
								</ul>
							</li>
						</ul>
					</li>
					<li class="interpunctMark ma_t_10">(개인정보 정정·삭제 요구) 질병관리청에서 보유하고 있는 개인정보파일은「개인정보보호법」제36조(개인정보의 정정·삭제)에 따라 정정·삭제를 요구할 수 있습니다. 다만, 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</li>
					<li class="interpunctMark ma_t_10">(개인정보 처리정지 요구) 질병관리청에서 보유하고 있는 개인정보파일은 「개인정보보호법」 제37조(개인정보의 처리정지 등)에 따라 처리정지를 요구할 수 있습니다. 다만, 개인정보 처리정지 요구 시 법 제37조 2항에 의하여 처리정지 요구가 거절될 수 있습니다.</li>
					<li>
						<ul class="pa_l_15">
							<li class="barMark ma_t_10">법률에 특별한 규정이 있거나 법령상 의무를 준수하기 위하여 불가피한 경우</li>
							<li class="barMark ma_t_10">다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 그 밖의 이익을 부당하게 침해할 우려가 있는 경우</li>
							<li class="barMark ma_t_10">공공기관이 개인정보를 처리하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우</li>
							<li class="barMark ma_t_10">개인정보를 처리하지 아니하면 정보주체와 약정한 서비스를 제공하지 못하는 등 계약의 이행이 곤란한 경우로서 정보주체가 그 계약의 해지 의사를 명확하게 밝히지 아니한 경우</li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보 파기</h2></li>
			<li class="barMark ma_t_10">질병관리청은 원칙적으로 개인정보의 보유기간이 경과했거나 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 다만, 다른 법률에 따라 보존하여야 하는 경우에는 그러하지 않습니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.</li>
			<li>
				<ul class="pa_l_15">
					<li class="interpunctMark ma_t_10">파기절차 : 이용자가 입력한 정보는 보유기간이 경과했거나 처리목적이 달성 후 내부 방침 및 관련 법령에 따라 파기합니다.</li>
					<li class="interpunctMark ma_t_10">파기기한 : 이용자의 개인정보는 개인정보의 보유기간이 경과된 경우 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</li>
					<li class="interpunctMark ma_t_10">파기방법 : 질병관리청에서 처리하는 개인정보를 파기할 때에는 다음의 방법으로 파기합니다.</li>
					<li>
						<ul class="pa_l_15">
							<li class="ma_t_10">1. (전자적 파일) 복원이 불가능한 방법으로 영구삭제</li>
							<li class="ma_t_10">2. (전자적 파일의 형태 외의 기록물, 인쇄물, 서면, 그 밖의 기록매체) 파쇄 또는 소각</li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보의 안전성 확보 조치</h2></li>
			<li class="barMark ma_t_10">질병관리청은 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적·관리적 및 물리적 조치를 하고 있습니다.</li>
			<li>
				<ul class="pa_l_15">
					<li class="interpunctMark ma_t_10">(개인정보 취급 직원의 최소화 및 교육) 개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(정기적인 자체 감사 실시) 개인정보 취급 관련 안정성 확보를 위해 정기적으로 자체 감사를 실시하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(내부관리계획의 수립 및 시행) 개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(개인정보의 암호화) 이용자의 개인정보는 보안구역에 저장 및 관리되고 있으며, 중요한 데이터는 파일 및 전송 데이터를 암호화하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(해킹 등에 대비한 기술적 대책) 질병관리청는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신·점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적·물리적으로 감시 및 차단하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(개인정보에 대한 접근 제한) 개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(접속기록의 보관 및 위변조 방지) 정보처리시스템에 접속한 기록을 최소 6개월 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 보안기능 사용하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(문서보안을 위한 잠금장치 사용) 개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(비인가자에 대한 출입 통제) 개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.</li>
					<li class="interpunctMark ma_t_10">(개인정보 이용 로그 생성) 개인정보를 검색, 조회할 경우에 접근기록이 생성되어 보관됩니다. 이는 개인정보를 누가 언제 조회하였는지에 대한 내용을 기록하여 개인정보의 유출 등에 대한 자료로 사용이 됩니다.</li>
				</ul>
			</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보 자동수집 장치의 설치·운영 및 거부에 관한 사항</h2></li>
			<li class="barMark ma_t_10">질병관리청은 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용하지 않습니다.</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보 보호책임자 및 담당자 연락처</h2></li>
			<li class="barMark ma_t_10">질병관리청은 개인정보를 보호하고 이하 관련된 업무를 수행하기 위하여 아래와 같이 개인정보 보호책임자 및 실무담당자를 지정하고 있습니다. (개인정보보호법 제31조 제1항에 따른 개인정보 보호책임자)</li>
			<li>
				<ul class="pa_l_15 pa_t_10">
					<li>
						<table class="view_table">
							<caption>
								개인정보 보호 책임자 및 보호 담당자 정보 - 부서, 성명, 연락처
							</caption>
							<colgroup>
								<col style="width:20%">
								<col style="width:40%">
								<col style="width:40%">
							</colgroup>
							<tbody class="report">
								<tr class="first">
									<th scope="col">구분</th>
									<th scope="col">개인정보 보호책임자</th>
									<th scope="col">개인정보 보호담당자</th>
								</tr>
								<tr class="first">
									<th scope="col">담당부서</th>
									<td scope="col">기획조정관</td>
									<td scope="col">정보통계담당관</td>
								</tr>
								<tr class="first">
									<th scope="col">성명</th>
									<td scope="col">OOO</td>
									<td scope="col">OOO</td>
								</tr>
								<tr class="first">
									<th scope="col">연락처</th>
									<td scope="col">043-000-0000</td>
									<td scope="col">043-000-0000</td>
								</tr>
							</tbody>
						</table>
					</li>
					<li class="font_xsm starMark ma_t_3 font_blue">부서별(분야별) 개인정보보호 책임자 및 담당자 지정 운영</li> 			
				</ul>
			</li> 	
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보 열람청구</h2></li>
			<li class="barMark ma_t_10">정보주체는 개인정보 보호법 제35조에 따른 개인정보의 열람 청구를 할 수 있습니다.</li>
			<li>
				<ul class="pa_l_15 pa_t_10">
					<li>※ 개인정보보호위원회 개인정보보호 포털(<a href="https://www.privacy.go.kr/" target="_blank" rel="noopener noreferrer" title="새 창에서 열림">www.privacy.go.kr</a>) → 민원마당 → 개인정보 열람등 요구</li>
					<table class="table_v">
						<caption>
							개인정보 열람청구 - 온라인(인터넷), 오프라인(서면)
						</caption>
						<colgroup>
							<col style="width:40%">
							<col style="width:60%">
						</colgroup>
						<tbody class="report">
							<tr class="first">
								<th scope="col">온라인(인터넷)</th>
								<th scope="col">오프라인(서면)</th>
							</tr>
							<tr class="first">
								<td scope="col">
								<ul>
									<li>1) 개인정보종합지원포털(<a href="https://www.privacy.go.kr/" target="_blank" rel="noopener noreferrer" title="새 창에서 열림">www.privacy.go.kr</a>)에 접속</li>
									<li>2) 민원마당 &gt; 개인정보열람 등 요구안내 &gt;[개인정보 열람 등 청구] 클릭</li>
									<li>3) I-PIN인증으로 본인 확인</li>
									<li>4) [개인정보파일 목록검색]을 통한 대상기관 선택</li>
									<li>5) 신청유형 선택 (개인정보 열람, 정정·삭제, 처리정지 중 택 1)</li>
									<li>6) 요구서 작성 후 민원청구</li>
								</ul>
								</td>
								
							</tr>
						</tbody>
					</table>
				</ul>
			</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">권익침해 구제방법</h2></li>
			<li class="barMark ma_t_10">정보주체는 개인정보침해로 인한 구제를 받기 위하여 개인정보분쟁조정위원회, 한국인터넷진흥원, 개인정보침해신고센터 등에 분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보 침해의 신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다.</li>
			<li>
				<ul class="pa_l_15">
					<li class="interpunctMark ma_t_10">개인정보침해 신고센터</li>
					<li>
						<ul class="pa_l_15">
							<li class="interpunctMark ma_t_10">소관업무 : 개인정보 침해사실 신고, 상담 신청</li>
							<li class="interpunctMark ma_t_10">홈페이지 : <a href="https://privacy.kisa.or.kr/" target="_blank" rel="noopener noreferrer" title="개인정보침해 신고센터(새 창에서 열림)">http://privacy.kisa.or.kr</a></li>
							<li class="interpunctMark ma_t_10">전화 : (국번없이) 118</li>
							<li class="interpunctMark ma_t_10">주소 : (58324) 전남 나주시 진흥길 9 3층</li>
						</ul>
					</li>
					<li class="interpunctMark ma_t_10">개인정보 분쟁조정위원회 </li>
					<li>
						<ul class="pa_l_15">
							<li class="interpunctMark ma_t_10">소관업무 : 개인정보 분쟁조정신청, 집단분쟁조정(민사적 해결)</li>
							<li class="interpunctMark ma_t_10">홈페이지 : <a href="https://www.kopico.go.kr/" target="_blank" rel="noopener noreferrer" title="개인정보 분쟁조정위원회(새 창에서 열림)">http://www.kopico.go.kr</a></li>
							<li class="interpunctMark ma_t_10">전화 : (국번없이) 1833-6972</li>
							<li class="interpunctMark ma_t_10">주소 : (03171) 서울시 송파구 세종대로 209</li>
						</ul>
					</li>
					<li class="interpunctMark ma_t_10">1301 검찰 콜센터 : (국번없이) 1301 (<a href="http://www.spo.go.kr" target="_blank" rel="noopener noreferrer" title="1301 검찰 콜센터(새 창에서 열림)">www.spo.go.kr</a>)</li>
					<li class="interpunctMark ma_t_10">경찰청 사이버수사국 : (국번없이) 182 (<a href="http://cyberbureau.police.go.kr/" target="_blank" rel="noopener noreferrer" title="경찰청 사이버수사국(새 창에서 열림)">cyberbureau.police.go.kr</a>)</li>
				</ul>
			<li class="barMark ma_t_10">「개인정보보호법」제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대하여  공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.</li>
			<li class="pa_l_10 ma_t_10">※ 행정심판에 대해 자세한 사항은 법제처(<a href="http://www.moleg.go.kr/" target="_blank" rel="noopener noreferrer" title="새 창에서 열림">http://www.moleg.go.kr/</a>) 홈페이지를 참고하시기 바랍니다.</li>
		</ul>
		<ul class="ma_b_20">
			<li><h2 class="title bor_b">개인정보 처리방침의 변경</h2></li>
			<li class="barMark ma_t_10">이 개인정보 처리방침은 2021. 07. 05.부터 적용됩니다</li>
			<li class="pa_l_10 ma_t_10">개인정보 처리방침(2021.05.06~2021.07.05) [<a title="개인정보 처리방침(2021.05.06~2021.07.05) 첨부파일 다운로드" >첨부파일 다운로드</a>]</li>
			<li class="pa_l_10 ma_t_10">이전 개인정보 처리방침(2020.03.23~2021.05.05) [<a title="개인정보 처리방침(2020.03.23~2021.05.05) 첨부파일 다운로드" >첨부파일 다운로드</a>]</li>
		</ul>
	</div>
</div>