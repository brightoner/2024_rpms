<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<meta charset="utf-8">
  
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" /> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no"/>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="${ctxt}/resources/images/new/favicon.ico" />

<!-- MS Edge tel링크제거 -->

<!-- jquery -->
<script src="${ctxt}/resources/js/jquery-1.11.3.min.js?ver=1"></script>
<script src="${ctxt}/resources/js/jquery.easing.min.js?ver=1"></script>
<script src="${ctxt}/resources/js/html5shiv.min.js?ver=1"></script>  
<script src="${ctxt}/resources/js/chart/jui/jquery-ui.min.js?ver=1"></script>
<script src="${ctxt}/resources/js/common/valcheck2.js?ver=1" ></script>
<script src="${ctxt}/resources/js/jquery.cookie.js?ver=1"></script>
<script src="${ctxt}/resources/js/jquery.fileDownload.js?ver=1"></script>

<!--[if lt ie 9]><![endif]-->
<script src="${ctxt}/resources/js/visual.js?ver=1"></script>

<!-- common js -->

<script src="${ctxt}/resources/js/jquery.popupoverlay.js?ver=1"></script>
<script src="${ctxt}/resources/js/dtree.js?ver=1"></script>
<script src="${ctxt}/resources/js/commonAlert.js?ver=1"></script>
<script src="${ctxt}/resources/js/common.js?ver=1"></script>

<script src="${ctxt}/resources/js/commonPlatform.js?ver=1"></script>  
<script src="${ctxt}/resources/js/menu.js?ver=1"></script>
<script src="${ctxt}/resources/js/paging/jquery.paging.min.js?ver=1"></script>

<%-- <script src="${ctxt}/resources/js/commAttfile.js?ver=1"></script> --%>
<script src="${ctxt}/resources/js/cmmAtchFile.js?ver=1"></script>	<!-- 첨부파일관련 - ljk -->

<!-- 제스처 인식 -->
<script type="text/javascript" src="${ctxt}/resources/js/gestures/wz_jsgraphics.js?ver=1"></script>
<script type="text/javascript" src="${ctxt}/resources/js/jquery.treeview.js?ver=1"></script>

<!-- 프린트 구분  -->
<script type="text/javascript" src="${ctxt}/resources/js/jquery.print-preview.js?ver=1"></script>
<script type="text/javascript" src="${ctxt}/resources/js/jquery.printElement.js?ver=1"></script>

<!-- css -->

 <!-- 차트 구성 SCRIPT STR-->
<script type="text/javascript" src="${ctxt}/resources/js/chart/jui/jquery-ui.min.js?ver=1"></script>
<script type="text/javascript" src="${ctxt}/resources/js/chart/jui/core.js?ver=1" charset="utf-8"></script>
<script type="text/javascript" src="${ctxt}/resources/js/chart/jui/chart.js?ver=1" ></script>
<script type="text/javascript" src="${ctxt}/resources/js/chart/jui/jui-chart.js?ver=1" ></script>
<link rel="stylesheet" href="${ctxt}/resources/js/chart/jui/jquery-ui.min.css?ver=1" />
<!-- 차트 구성 SCRIPT END-->


<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/new/styles.css"/><!-- Core theme CSS (includes Bootstrap)-->
<%-- <link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/new/streaming.css"/> --%>

<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/mngMenu.css?ver=1">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/reset.css?ver=1">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/font.css?ver=1">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/rules.css?ver=1">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/layout.css?ver=1">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/sub.css?ver=1">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/pop.css?ver=1">
<%-- <link rel="stylesheet" type="text/css" href="${ctxt}/resources/css/responsive.css?ver=1"> --%>
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/vendor/fontawesome-free-5.11.2-web/css/all.css?ver=1" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/vendor/fontawesome-free-5.11.2-web/css/font-awesome-animation.min.css?ver=1" crossorigin="anonymous">
<link rel="stylesheet" href="${ctxt}/resources/css/print/print-preview.css?ver=1" media="screen" />
<link rel="stylesheet" href="${ctxt}/resources/css/print/print.css?ver=1" media= "print"/>
<link rel="stylesheet" href="${ctxt}/resources/css/print/screen.css?ver=1" media = "screen"/>

<!-- jstree  -->
<script type="text/javascript" src="${ctxt}/resources/js/jstree/dist/jstree.min.js"></script>
<link rel="stylesheet" href="${ctxt}/resources/js/jstree/dist/themes/default/style.min.css" />

<!-- 달력  -->		
<script type="text/javascript" src="${ctxt}/resources/utils/datepicker/js/datepicker.js?ver=1"></script>
<link rel="stylesheet" href="${ctxt}/resources/utils/datepicker/css/datepicker.css?ver=1" /> 

<!-- 슬라이드 -->
<script src="${ctxt}/resources/js/swiper/swiper.min.js?ver=1"></script>
<link rel="stylesheet" type="text/css" href="${ctxt}/resources/js/swiper/swiper.css?ver=1"/>

<!-- Scoroll -->
<script type="text/javascript" src="${ctxt}/resources/vendor/bootstrap/js/bootstrap.js?ver=1"></script>
<!-- Scoroll END -->


<!-- sweet alert 2 -->
<link rel="stylesheet" href="${ctxt}/resources/js/sweetalert2/sweetalert2.min.css?ver=1">
<script src="${ctxt}/resources/js/sweetalert2/sweetalert2.min.js?ver=1"></script>