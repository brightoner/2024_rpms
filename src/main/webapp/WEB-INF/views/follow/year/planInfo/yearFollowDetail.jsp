<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<script type="text/javascript">
	
	var cuurPage = 1;
	
	$(function(){     
		
	
	}); 

	
	function openYearProjTab(evt, tabName) {
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
	 
	  
</script>
<!-- 본문내용 -->
<!-- 본문내용 -->
<div id="right_content">     

	<div class="tab clear tab-trade ma_b_-1">
	    <button class="tablinks active" type="button" onclick="openYearProjTab(event, 'planInfo')" id="defaultOpen" title="선택됨">과제정보</button>
   		<button class="tablinks" type="button" onclick="openYearProjTab(event, 'employInfo')">채용 (<span id="empCnt">0</span>)</button>
    	<button class="tablinks" type="button" onclick="openYearProjTab(event, 'ipInfo')">지적재산권 (<span id="ipCnt">0</span>)</button>
    	<button class="tablinks" type="button" onclick="openYearProjTab(event, 'salesInfo')">매출 (<span id="slsCnt">0</span>)</button>
    	<button class="tablinks" type="button" onclick="openYearProjTab(event, 'articleInfo')">논문 (<span id="atcCnt">0</span>)</button>
    	<button class="tablinks" type="button" onclick="openYearProjTab(event, 'etcInfo')">기타 (<span id="etcCnt">0</span>)</button>
	    
	</div>
 
	<div id="includedPage">
		<div id="planInfo" class="tabcontent" >
			<jsp:include page="yearPlanInfo.jsp" />
		</div> 
		<div id="employInfo" class="tabcontent" >
			<c:import url="/follow/year/employ/yearEmployList.do" ></c:import>
		</div>
		<div id="ipInfo" class="tabcontent" >
			<c:import url="/follow/year/ip/yearIpList.do" ></c:import>
		</div>
		<div id="salesInfo" class="tabcontent" >
			<c:import url="/follow/year/sales/yearSalesList.do" ></c:import>
		</div>
		<div id="articleInfo" class="tabcontent" >
			<c:import url="/follow/year/article/yearArticleList.do" ></c:import>
		</div>
		<div id="etcInfo" class="tabcontent" >
			<c:import url="/follow/year/etc/yearEtcList.do" ></c:import>
		</div>
	</div>
</div>	

	<div id="bdMakeLayer"></div>
	
<script type="text/javascript">

 
//Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>

	