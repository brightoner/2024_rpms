<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.servlet.i18n.SessionLocaleResolver"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<script type="text/javascript">
		
</script>

<title>라스테크 연구과제관리시스템</title>
    <tiles:insertAttribute name="mngPlatFormMeta" /> 
</head>


<body id="test"> 

	
	<!-- 헤더영역 -->
    <tiles:insertAttribute name="mngPlatFormHeader" /> 
    
    
    <!-- Page content-->
	<div class="container">
	<!-- 20240614 -->
<!-- 	     <div class="row section-divided-normal mng">  -->
	     <div class="row section-divided-normal1 mng"> 
	     	 <div class="col-lg-1-1-mng">
	 	        <tiles:insertAttribute name="mngPlatFormLeftMenu" /> 
			</div>  
			<!-- 20240614 -->
			<%-- <div class="col-lg-2-mng">
		 	    <tiles:insertAttribute name="mngPlatFormLeftMenu" />     
			</div>   --%>
			    <!-- 바디영역 -->
			<!-- 20240614 -->
<!-- 		    <div class="col-lg-3-mng"> -->
		    <div class="col-lg-10 cont-mh">
		    	<tiles:insertAttribute name="mngPlatFormBody" />
		    </div>		
		    <!-- 20240614 -->
		    <%-- <div class="col-lg-4-mng">
		    	<tiles:insertAttribute name="mngPlatFormRight" />
		    </div> --%>
	     </div>
    </div>
    
	<!-- 하단영역 -->
  	<tiles:insertAttribute name="mngPlatFormFooter" /> 
 <div id="loading-spinner" style="display: none;">
    <div class="spinner"></div>
</div>
</body>  
</html>