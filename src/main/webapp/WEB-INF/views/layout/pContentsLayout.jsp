<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- <script>document.domain = "cdc.go.kr"</script> -->
<script type="text/javascript">
	<%-- googleAnalytics (Start) --%>
	var _gaq = _gaq || [];
	_gaq.push([ '_setAccount', 'UA-49580594-1' ]);  
	_gaq.push([ '_setDomainName', 'nih.go.kr' ]);
	_gaq.push([ '_setAllowLinker', true ]);
	_gaq.push([ '_trackPageview' ]);

	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
	<%--  googleAnalytics (End) --%>
		
</script>  

<title ></title>
    <tiles:insertAttribute name="pMeta" />   
</head>
<body class="relative">
	 <!-- <div id="container">   -->	
	 	<div id="container_box">
			<div id="container-wrap" class="clear relative">
				<div id="content relative">
				 	<tiles:insertAttribute name="pBody"/>
				</div>
			</div>
	 	</div>       
	<tiles:insertAttribute name="pFooter"/>
	
	<!-- LOADING -->
	<div class="loading">
		<div>
			<div>G</div>
			<div>N</div>
			<div>I</div>
			<div>D</div>
			<div>A</div>
			<div>O</div>
			<div>L</div>
		</div>
	</div>
</body>

</html>