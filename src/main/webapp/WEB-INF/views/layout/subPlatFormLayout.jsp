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
    <tiles:insertAttribute name="subPlatFormMeta" /> 
</head>
 

<body id="test"> 
	<!-- 헤더영역 -->
    <tiles:insertAttribute name="subPlatFormHeader" /> 
    
    
    <!-- Page content-->
	<div class="container">
	     <div class="row section-divided-normal"> 
	        <div class="col-lg-13">
	           <tiles:insertAttribute name="subPlatFormLeftMenu" /> 
	        </div>                      
		    <!-- 바디영역 -->
		    
		    <!-- 20240614 -->
<!-- 		    <div class="col-lg-8"> -->
		    <div class="col-lg-9 cont-mh">
		    	<tiles:insertAttribute name="subPlatFormBody" flush="true"  />
		    </div>
		    <!-- 20240614 -->
		   <%--  <div class="col-lg-4">
		     	<tiles:insertAttribute name="subPlatFormRight" />
		    </div> --%>
	     </div>
    </div>
    
	<!-- 하단영역 -->
  	<tiles:insertAttribute name="subPlatFormFooter" /> 
 <div id="loading-spinner" style="display: none;">
    <div class="spinner"></div>
</div>
</body>  
</html>