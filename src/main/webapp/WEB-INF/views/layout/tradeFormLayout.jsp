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
    <tiles:insertAttribute name="tradeFormMeta" /> 
</head>


<body id="test"> 
	<!-- 헤더영역 -->
    <tiles:insertAttribute name="tradeFormHeader" /> 
    
    
    <!-- Page content-->
	<div class="container">
	     <div class="row"> 
		    <!-- 바디영역 -->
<!-- 		    <div class="col-lg-8"> -->
		    	<tiles:insertAttribute name="tradeFormBody" />
<!-- 		    </div> -->
	     </div>
    </div>
    
	<!-- 하단영역 -->
  	<tiles:insertAttribute name="tradeFormFooter" /> 
  
</body>  
</html>