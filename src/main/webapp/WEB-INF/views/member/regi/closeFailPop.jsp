<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
$(function(){
	

	
	
	
});
</script>
<body>

본인인증이 실패하였습니다.<br>
    <table border=1>
       
        <tr>
            <td>요청 번호</td>
            <td>${sRequestNumber}</td>
        </tr>            
        <tr>
            <td>본인인증 실패 코드</td>
           <td>${sErrorCode}</td>
        </tr>            
        <tr>
           <td>인증수단</td>
           <td>${sAuthType}</td>
        </tr>
     </table><br><br>        
     
</body>