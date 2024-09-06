<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

	<script type="text/javascript">
	
	var wideGbn = true; 
	
	$(function(){     
		
	
	}); 

	
	// 탭변경
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
	 
	
	function fn_wideContent(){
		if (wideGbn == true){
			$(".col-lg-13").css("display", "none");
			$(".row.section-divided-normal").css("grid-template-columns", "auto");
			$(".admin").css("width", "100%");
			wideGbn = false;
		}else {
			$(".col-lg-13").css("display", "");
			$(".row.section-divided-normal").css("grid-template-columns", "300px auto");
			$(".admin").css("width", "1550px");
			wideGbn = true;
		}
	}
</script>
<!-- 본문내용 -->
<!-- 본문내용 -->
<div id="right_content">     


	<div class="tab clear tab-trade ma_b_-1">
	    <button class="tablinks active" type="button" onclick="openYearProjTab(event, 'info')" id="defaultOpen" title="선택됨">연차과제 정보</button>
	 
	    <c:if test="${wbsBaseInfo ne null }">
	   	 <button class="tablinks" type="button" onclick="openYearProjTab(event, 'wbs')">WBS</button>
	    </c:if>
	       <c:if test="${ budGetlist ne null && fn:length(budGetlist) != 0}">
	    <button class="tablinks" type="button" onclick="openYearProjTab(event, 'budget')">예산 편성</button>
	    </c:if>
	        <c:if test="${ budGetlist ne null && fn:length(budGetlist) != 0}">
	    <button class="tablinks" type="button" onclick="openYearProjTab(event, 'budgetExec')">예산 집행</button>
	    </c:if>
	    
	</div>
 
	<div id="includedPage">
		<div id="info" class="tabcontent" >
			<jsp:include page="projPlanInfo.jsp" />
		</div> 
		<c:if test="${wbsBaseInfo ne null }">
			<div id="wbs" class="tabcontent" >
			 <c:import url="/execute/wbsMng/projWbsMngList.do" >
				
				</c:import>
		
			</div>
		</c:if>
		
		<div id="budget" class="tabcontent" >
				 <c:import url="/execute/bgMake/projBgMakeList.do" >
				
				</c:import>
		
		</div>
		<div id="budgetExec" class="tabcontent" >
				 <c:import url="/execute/bgExec/projBgExecList.do" >
				
				</c:import>
		
			</div>
	</div>
</div>	

	<div id="bdMakeLayer"></div>
	
<script type="text/javascript">

 
//Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>

	