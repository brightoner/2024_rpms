<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>실명인증완료</title>
</head>
<script type="text/javascript">
$(function(){
	
	if('${user_id}' != ''){
		window.opener.document.getElementById("user_id").value = '${user_id}';
	}
	if('${message}' != ''){
		opener.parent.fn_showCustomAlert('${message}');	
	}
	window.close();
	
});
</script>
<body>

</body>
</html>