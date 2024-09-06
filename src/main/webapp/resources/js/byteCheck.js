// 글자수 제한 (한글 2Byte, 그외 1Byte)

function cutMsg(str,len){
	var ret='';
	var i;
	var msglen=0;

	for(i=0;i<str.length;i++){
		var ch=str.charAt(i);

		if(escape(ch).length >4){
			msglen += 2;
		}else{
			msglen++;
		}
		if(msglen > len) break;
		ret += ch;
	}
	return ret;
}
 
function reCount(str){
	var i;
	var msglen=0;

	for(i=0;i<str.length;i++){
	var ch=str.charAt(i);

		if(escape(ch).length >4){
			msglen += 2;
		}else{
			msglen++;
		}
	}
	return msglen;
}
 
function byteCheck(id,len){

	var msglen=0;

	var target = document.getElementById(id);
	
	msglen = reCount(target.value);

	document.getElementById('byte_'+id).innerHTML = msglen;	
	
	if(msglen > len){
		rem = msglen - len;
		alert('입력하신 문장의 총길이는 ' + msglen + '입니다.\n초과되는 ' + rem + '바이트는 삭제됩니다.');
		document.getElementById(id).value = cutMsg(str,len);		
	}
}