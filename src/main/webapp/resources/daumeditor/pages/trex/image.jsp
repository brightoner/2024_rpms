<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta charset="utf-8">
    <title>
        Daum에디터 - 이미지 첨부
    </title>
    <link rel="stylesheet" href="../../css/popup.css" type="text/css" charset="utf-8" />
    <link href="../../css/custom/web/kor/basic.css" rel="stylesheet" type="text/css"/>
    <style>
        #pre_view img {
            max-width: 125px;
            max-height: 125px;
            float: left;
            margin-bottom: 3px;
        }
        .thumbnail {
            float: left;
            border: 1px solid #999;
            margin: 0 5px 5px 0;
            padding: 5px;
        }
    </style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/core/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/plani/plani_common.js"></script>
    <script src="../../js/popup.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var file_list_div = "";
        var pre_view_div = "";
        
        jQuery(document).ready(function(){
        	
        	
        });
        
        function goUpload()
        {
        	/* if(cmmfn_check_fext(jQuery("#file_upload"), "IMG") == false)
        		return; */
        	
        	var form = document.reqForm;
        	form.action = "${pageContext.request.contextPath}/utl/daumeditor/daumFileUpLoad.do"; 
        	form.submit();
        }
    </script>
</head>
<body>
   <form name="reqForm" id="reqForm" method="post" enctype="multipart/form-data">
        <div class="header">
            <h1>
          이미지 첨부
        </h1>
        </div>
        <dl class="alert">
            <dt>
          이미지 첨부 확인
        </dt>
            <dd>
                업로드를 누르시면 이미지가 첨부 됩니다.
                <br />
            </dd>
        </dl>
        <div id="file_list">
            
        </div>
        <div class="daumfooter">
	        <input type="file" id="file_upload" name="file_upload" size="60"/>
            <input type="button" value="업로드" id="saveBtn" onclick="javascript:goUpload()"/>
            <input type="button" value="취소" onclick="closeWindow()"/>
        </div>
        <div id="pre_view">
        </div>
    </form>
    
</body>
</html>