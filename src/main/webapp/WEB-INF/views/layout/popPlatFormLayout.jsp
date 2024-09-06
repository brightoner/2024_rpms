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
    <tiles:insertAttribute name="popPlatFormMeta" />

</head>
 

<body id="test"> 
    
    
    <!-- Page content-->
 <div class="container refit"> 
	      <div class="row"> 
		    <!-- 바디영역 -->
		    <!-- <div class="col-lg-8"> -->
		    	<tiles:insertAttribute name="popPlatFormBody" flush="true"  />
		    <!-- </div> -->
    	  </div> 
    </div> 
    
  
</body>  
</html>