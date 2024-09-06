<%@ page language="java" contentType="application/vnd.ms-excel;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.sql.*, java.util.*" %>
<%
String xlsTitle = (String)pageContext.getAttribute("xlsTitle");

java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
String today = formatter.format(new java.util.Date());
String file_name = today+"_excelDown";

//파일명 한글안나오길래 바꿔봄
String excelName  = new String(file_name.getBytes("8859_1"),"utf-8")+".xls";
//String excelName  = new String(file_name.getBytes("euc-kr"),"8859_1")+".xls";
System.out.println("excelName=" + excelName);
 
response.setHeader("Content-Disposition", "attachment; filename=" + excelName);
response.setHeader("Content-Description", "JSP Generated Data"); 
response.setContentType("application/vnd.ms-excel");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${printTitle}" /> PDF출력</title>

<style>
.sumRow {background:#c1c1c1;}
#excelWbsTable td {vertical-align :middle;text-align :center;border:thin solid gray;}
#excelWbsTable th {border:thin solid gray;background:#c1c1c1;}
<%-- #excelWbsTable{background:#fffeb7 !important;} --%>

caption {opacity: 0;   height: 0;}
.wbsThLine{width:25px;}
</style>

</head> 

<script type="text/javascript">

$(function(){
	
});

</script>
<body>	
<div id="printWap">
<h2 class="nanum"><c:out value="${printTitle}"/></h2>
	<div id="printCon">
	<%-- 	<c:out value="${printHead}" escapeXml="false"/> --%>
	<c:out value="${printBody}" escapeXml="false"/>
	</div>
</div>	
</body>
</html>

<c:set var="xlsTitle" value="${printTitle}" />
<%-- <%
String xlsTitle = (String)pageContext.getAttribute("xlsTitle");

java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
String today = formatter.format(new java.util.Date());
String file_name = "kdcaExcelDown";

//파일명 한글안나오길래 바꿔봄
String excelName  = new String(file_name.getBytes("8859_1"),"utf-8")+".xls";
//String excelName  = new String(file_name.getBytes("euc-kr"),"8859_1")+".xls";
System.out.println("excelName=" + excelName);
 
response.setHeader("Content-Disposition", "attachment; filename=" + excelName);
response.setHeader("Content-Description", "JSP Generated Data"); 
response.setContentType("application/vnd.ms-excel");
%>  --%>