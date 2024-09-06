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
	
	
// 	if('${message}' != ''){
		
// 		opener.parent.fn_showCustomAlert('${message}');	
		
// 		setTimeout(function() { 
// 			window.close();
// 			opener.parent.window.location.href = '/login/logout.do';
// 		}, 1000);
		
// 	}else{
// 		window.close();
// 	}

	if('${message}' != ''){
		
		opener.parent.fn_showCustomAlert('${message}');	
		window.close();
		
	}else{
		opener.parent.window.location.href = '/login/logout.do';
	}
		
	
});
</script>
<body>

</body>
</html>