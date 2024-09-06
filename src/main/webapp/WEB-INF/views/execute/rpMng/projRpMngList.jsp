<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
table.table_h, table.table_h th , table.table_h td {
     padding: 15px 3px;
  
 
}
.partRate {
    color: red;  
}

.sumRow{
	background-color : #f9ecd6;
}
</style>
<script type="text/javascript">  
 
 var wideGbn = true; 
$(function(){ 
	 
		 // 포커스가 벗어났을 때 소수점으로 끝나면 소수점을 제거
		$(document).on('blur' , '.rateInput' , function() {
	        var $input = $(this);
	        var value = $input.val();
	
	        if (value.endsWith('.')) {
	            value = value.slice(0, -1);
	        }
	
	        $input.val(value);
	    });

		 // input에 기본적으로 0이 셋팅됨.
		 // 0에 이어서 012  입력해도 무방하지만 편의를 주기위해 0일떄는 비움 
		$(document).on('click' , '.rateInput' , function() {
	        var $input = $(this);
	        var value = $input.val();
	
	        if (value < 1) {
	            value = '';
	        }
	
	        $input.val(value);
	    });
		 
		$(document).on('input' , '.rateInput' , function() {
			$("#errArea").html('');
			var $inputVal = $(this);
			 $inputVal.val(function(index, value) {
				 // 숫자와 소수점 이외의 문자를 제거
		            value = value.replace(/[^\d.]/g, '');

		            // 소수점이 여러 번 입력되는 것을 방지
		            var parts = value.split('.');
		            if (parts.length > 2) {
		                value = parts[0] + '.' + parts.slice(1).join('');
		            }

		            // 소수점 이하 두 자리까지 제한
		            if (parts[1] && parts[1].length > 2) {
		                value = parts[0] + '.' + parts[1].substring(0, 2);
		            }
		        
		            // 값이 100을 초과하면 0으로 설정
		            if (parseFloat(value) > 100) {
		                return '0';
		            }

		            return value;
		        });
	         
	         if($inputVal.val() > 0 ){ 
	        	 $inputVal.css('background-color', 'yellow'); 
	       	 }else{
	       		$inputVal.css('background-color', '');
	         }
	          
	      	// year_resp_cd와 proj_year_id로 그룹화된 사용자 데이터를 저장할 객체
	         // year_resp_cd와 proj_year_id로 그룹화된 사용자 데이터를 저장할 객체
	         /**********************************************************************/
	 		//조회하는 즉시 최초로 사용자 별로 월별 집계를 구성한다.str
	 		//구성된 userData 집계 데이터는 월별 input 의  input 이벤트에서 활용된다.
	 		//조회시 마다 호출되면 부하가 생기기 때문에 년도를 특정하여 조회시만 실행한다. (년도를 특정 후 조회해야 저장을 할 수 있기때문)
	 		if (!isEmpty( $("#yearParam").val()) ){
	 			var userData = {};
	 	 		
	 			$('#dataList tr').filter(function() {
	 	 		    return $(this).attr('name') != 'sumRow'; // name  이 sumRow이 아닌 요소들만 필터링
	 	 		}).each(function() {
	 				
	 					var $tr = $(this);		
	 					var yearRespCd = $tr.find('input[name="year_resp_cd"]').val();
	 			         
	 		            var userName = $tr.children('td').eq(1).text(); // 이름 
	 		            var key = userName + '-' + yearRespCd ;

	 		            // 만약 이 키에 해당하는 데이터가 없다면 초기화
	 		            if (!userData[key]) {
	 		                userData[key] = {
	 		                    'userName': userName,
	 		                    'yearRespCd': yearRespCd,           
	 		                    'jan_rate': 0,
	 		                    'feb_rate': 0,
	 		                    'mar_rate': 0,
	 		                    'apr_rate': 0,
	 		                    'may_rate': 0,
	 		                    'jun_rate': 0,
	 		                    'jul_rate': 0,
	 		                    'aug_rate': 0,
	 		                    'sept_rate': 0,
	 		                    'oct_rate': 0,
	 		                    'nov_rate': 0,
	 		                    'dec_rate': 0
	 		                };
	 		            } 
	   
	 		            // 각 월별 참여율을 더함
	 		            userData[key]['jan_rate'] += parseFloat($tr.find('input[name="jan_rate"]').val()) || 0;
	 		            userData[key]['feb_rate'] += parseFloat($tr.find('input[name="feb_rate"]').val()) || 0;
	 		            userData[key]['mar_rate'] += parseFloat($tr.find('input[name="mar_rate"]').val()) || 0;
	 		            userData[key]['apr_rate'] += parseFloat($tr.find('input[name="apr_rate"]').val()) || 0;
	 		            userData[key]['may_rate'] += parseFloat($tr.find('input[name="may_rate"]').val()) || 0;
	 		            userData[key]['jun_rate'] += parseFloat($tr.find('input[name="jun_rate"]').val()) || 0;
	 		            userData[key]['jul_rate'] += parseFloat($tr.find('input[name="jul_rate"]').val()) || 0;
	 		            userData[key]['aug_rate'] += parseFloat($tr.find('input[name="aug_rate"]').val()) || 0;
	 		            userData[key]['sept_rate'] += parseFloat($tr.find('input[name="sept_rate"]').val()) || 0;
	 		            userData[key]['oct_rate'] += parseFloat($tr.find('input[name="oct_rate"]').val()) || 0;
	 		            userData[key]['nov_rate'] += parseFloat($tr.find('input[name="nov_rate"]').val()) || 0;
	 		            userData[key]['dec_rate'] += parseFloat($tr.find('input[name="dec_rate"]').val()) || 0;
	 				
	 	 		});
	 		            //조회하는 즉시 최초로 사용자 별로 월별 집계를 구성한다.end
	 				console.log(userData);
	 		        // 각 사용자 그룹의 월별 합계를 검사하여 100을 초과하는지 확인
	 		        // 조회후 구성된 userData를 활용하여 유저별 월별 100이 넘는지 확인한다.
	 	           if (Object.keys(userData).length > 0) {
	 			        for (var key in userData) {
	 			            var months = userData[key];
	 			            for (var month in months) {
	 			                if (month !== 'userName' && month !== 'yearRespCd' && months[month] > 100) {
	 			                    console.log('Error: ' + months.userName + ' (' + months.yearRespCd + ') - ' + month + ' rate exceeds 100');
	 			                    var korMon = ''; 
	 			                    if( month == 'jan_rate'){
	 			                    	korMon = '1월'; 			                    
	 			                    }else if( month == 'feb_rate'){
	 			                    	korMon = '2월'; 
	 			                    }else if( month == 'mar_rate'){
	 			                    	korMon = '3월'; 
	 			                    }else if( month == 'apr_rate'){
	 			                    	korMon = '4월'; 
	 			                    }else if( month == 'may_rate'){
	 			                    	korMon = '5월'; 
	 			                    }else if( month == 'jun_rate'){ 
	 			                    	korMon = '6월'; 
	 			                    }else if( month == 'jul_rate'){
	 			                    	korMon = '7월'; 
	 			                    }else if( month == 'aug_rate'){
	 			                    	korMon = '8월'; 
	 			                    }else if( month == 'sept_rate'){
	 			                    	korMon = '9월'; 
	 			                    }else if( month == 'oct_rate'){
	 			                    	korMon = '10월'; 
	 			                    }else if( month == 'nov_rate'){
	 			                    	korMon = '11월'; 
	 			                    }else if( month == 'dec_rate'){
	 			                    	korMon = '12월'; 
	 			                    }
	 			                    
	 			                   $("#errArea").html('연구자 : ' + '<span style = "color:black;">' + months.userName + ' </span> 의 '+  korMon + ' 참여율이  100%를 초과 합니다.');
	 		                     
	 		                        // 현재나의 값을 0으로 대체 
	 		                        $inputVal.val(0);
	 		                        $inputVal.focus();
	 		                        $inputVal.css('background-color', 'red');
	 		                         
	 			                }
	 			            } 
	 			        }
	 				}
	 			} 
	      
	      
		}); 
	 
	 	var $select = $('#yearSelect');
	    var currentYear = new Date().getFullYear();
	    var startYear = currentYear;
	    var endYear = currentYear - 6 ;
	
	  
	    $select.append($('<option>', {
	        value: '',
	        text: '전체'
	    }));
	    for (var year = startYear; year >= endYear; year--) {
	        $select.append($('<option>', {
	            value: year,
	            text: year + '년',
	            selected: year == currentYear
	        }));
	    }
	    
	    
		fn_bgExecSearch();

	  
}); 
 

function fn_bgExecSearch(){

	
	
	var params = {};
		params.searchopt    = $('[name=searchopt] :selected').val();   
		params.searchword    = $('#searchword').val();   		
		params.searchoptYear = $('[name=searchoptYear] :selected').val();
		
	  	
$.ajax({
    url: '${ctxt}/execute/rpMng/readProjRpMngList.do',
    data: params,
    type: 'POST',
    dataType: 'json',
    success: function(result) {
    	$("#yearParam").val($('[name=searchoptYear] :selected').val());
    	var html ='';
    
    	if(!isEmpty(result)){
			if(!isEmpty(result.rpMngList)){
				
				$("#dataList").children().remove();
				
				$.each(result.rpMngList, function(idx, item){
					if(item.order_sn == '2'){
						html += '<tr name = "sumRow" class="sumRow">';	
							html += '<td class="text_c" style ="display:none;">';							
							html += '</td>';							
							html += '<td class="text_c" colspan = "4">[' +((isEmpty(item.resp_nm)) ? '' : (item.resp_nm)) +']&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;계</td>';																															
							 
							html += '<td class="text_r">'+((isEmpty(item.jan_rate)) ? '0' : (item.jan_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.feb_rate)) ? '0' : (item.feb_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.mar_rate)) ? '0': (item.mar_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.apr_rate)) ? '0' : (item.apr_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.may_rate)) ? '0' : (item.may_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.jun_rate)) ? '0' : (item.jun_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.jul_rate)) ? '0' : (item.jul_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.aug_rate)) ? '0' : (item.aug_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.sept_rate)) ? '0' : (item.sept_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.oct_rate)) ? '0' : (item.oct_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.nov_rate)) ? '0' : (item.nov_rate))+'</td>';																									
							html += '<td class="text_r">'+((isEmpty(item.dec_rate)) ? '0' : (item.dec_rate))+'</td>';																									
															
						html += '</tr>';	
					}else{
						html += '<tr>';
							html += '<td class="text_c" style ="display:none;">';
								html +='<input type = "text"  name = "year_resp_cd"  value = "'+((isEmpty(item.year_resp_cd)) ? '' : (item.year_resp_cd))+'"/>';
								html +='<input type = "text"  name = "proj_year_id"  value = "'+((isEmpty(item.proj_year_id)) ? '' : (item.proj_year_id))+'"/>';					
							html += '</td>';
							html += '<td class="text_c">'+((isEmpty(item.resp_nm)) ? '' : (item.resp_nm))+'</td>';
							html += '<td class="text_c">'+((isEmpty(item.year_resp_nm)) ? '' : (item.year_resp_nm))+'</td>';																															
							html += '<td class="text_c">'+((isEmpty(item.year_part)) ? '' : (item.year_part))+'</td>';			
							html += '<td class="text_l">'+'('+( isEmpty(item.cur_strtdt) ? '' :  item.cur_strtdt +'~'  ) + ((isEmpty(item.cur_enddt)) ? '0' : (item.cur_enddt)) + ') <br/> '+   ((isEmpty(item.proj_nm_kor)) ? '' : (item.proj_nm_kor))+'</td>';												
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "jan_rate"  value ="'+((isEmpty(item.jan_rate)) ? '0' : (item.jan_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "feb_rate"  value ="'+((isEmpty(item.feb_rate)) ? '0' : (item.feb_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "mar_rate"  value ="'+((isEmpty(item.mar_rate)) ? '0': (item.mar_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "apr_rate"  value ="'+((isEmpty(item.apr_rate)) ? '0' : (item.apr_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "may_rate"  value ="'+((isEmpty(item.may_rate)) ? '0' : (item.may_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "jun_rate"  value ="'+((isEmpty(item.jun_rate)) ? '0' : (item.jun_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "jul_rate"  value ="'+((isEmpty(item.jul_rate)) ? '0' : (item.jul_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "aug_rate"  value ="'+((isEmpty(item.aug_rate)) ? '0' : (item.aug_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "sept_rate" value ="'+((isEmpty(item.sept_rate)) ? '0' : (item.sept_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "oct_rate"  value ="'+((isEmpty(item.oct_rate)) ? '0' : (item.oct_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "nov_rate"  value ="'+((isEmpty(item.nov_rate)) ? '0' : (item.nov_rate))+'" />'+'</td>';																									
							html += '<td class="text_r">'+'<input type = "text" class="rateInput text_r" name = "dec_rate"  value ="'+((isEmpty(item.dec_rate)) ? '0' : (item.dec_rate))+'" />'+'</td>';																									
															
						html += '</tr>';				
					}
							
				});
				
				 
			 	$('#dataList').html(html);

				if (isEmpty( $("#yearParam").val()) ){
					 $('#rateTable input').prop('disabled', true);
					 $('#rateTable input').addClass('form-control');
					 $('#rpSaveBtn').hide();
				}else{
					 $('#rateTable input').prop('disabled', false);
					 $('#rateTable input').removeClass('form-control');
					 $('#rpSaveBtn').show();
				}
			 	 $('#dataList tr').filter(function() {
		 	 		    return $(this).attr('name') != 'sumRow'; // name  이 sumRow이 아닌 요소들만 필터링
		 	 		}).each(function() {
						var $tr = $(this);
						
						var jan_rate = $tr.find('input[name="jan_rate"]');
						var feb_rate = $tr.find('input[name="feb_rate"]');
						var mar_rate = $tr.find('input[name="mar_rate"]');
						var apr_rate = $tr.find('input[name="apr_rate"]');
						var may_rate = $tr.find('input[name="may_rate"]');
						var jun_rate = $tr.find('input[name="jun_rate"]');
						var jul_rate = $tr.find('input[name="jul_rate"]');
						var aug_rate = $tr.find('input[name="aug_rate"]');
						var sept_rate = $tr.find('input[name="sept_rate"]');
						var oct_rate = $tr.find('input[name="oct_rate"]');
						var nov_rate = $tr.find('input[name="nov_rate"]');
						var dec_rate = $tr.find('input[name="dec_rate"]');
						
						if(jan_rate.val() > 0 ){jan_rate.css('background-color', 'yellow'); }
						if(feb_rate.val() > 0 ){feb_rate.css('background-color', 'yellow'); }
						if(mar_rate.val() > 0 ){mar_rate.css('background-color', 'yellow'); }
						if(apr_rate.val() > 0 ){apr_rate.css('background-color', 'yellow'); }
						if(may_rate.val() > 0 ){may_rate.css('background-color', 'yellow'); }
						if(jun_rate.val() > 0 ){jun_rate.css('background-color', 'yellow'); }
						if(jul_rate.val() > 0 ){jul_rate.css('background-color', 'yellow'); }
						if(aug_rate.val() > 0 ){aug_rate.css('background-color', 'yellow'); }
						if(sept_rate.val() > 0 ){sept_rate.css('background-color', 'yellow'); }
						if(oct_rate.val() > 0 ){oct_rate.css('background-color', 'yellow'); }
						if(nov_rate.val() > 0 ){nov_rate.css('background-color', 'yellow'); }
						if(dec_rate.val() > 0 ){dec_rate.css('background-color', 'yellow'); }
					
						
			          
				 });
			 	 
			 	
			}else{
				/***************************************************************
				* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
				****************************************************************/
				
				$("#dataList").html('<tr><td colspan="16" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');	
			}
		}else{
			/***************************************************************
			* 화면별로 따로 세팅 테이블 데이터 조회 없는경우
			****************************************************************/
			
			$("#dataList").html('<tr><td colspan="16" style="text-align: center" >조회된 내용이 없습니다.</td></tr>');
		}
    },
    error : function(){                              // Ajax 전송 에러 발생시 실행
    	fn_showCustomAlert('오류가 발생했습니다.<br /> 관리자에게 문의 바랍니다.','e');
    }
});
}

function fn_saveRpMng(){
	
     
     
	 var rateList = [];
	 
	 $('#dataList tr').filter(function() {
		    return $(this).attr('name') != 'sumRow'; // name  이 sumRow이 아닌 요소들만 필터링
		}).each(function() {
			var $tr = $(this);
			var proj_year_id = $tr.find('input[name="proj_year_id"]');
			var year_resp_cd = $tr.find('input[name="year_resp_cd"]');
			
			var jan_rate = $tr.find('input[name="jan_rate"]');
			var feb_rate = $tr.find('input[name="feb_rate"]');
			var mar_rate = $tr.find('input[name="mar_rate"]');
			var apr_rate = $tr.find('input[name="apr_rate"]');
			var may_rate = $tr.find('input[name="may_rate"]');
			var jun_rate = $tr.find('input[name="jun_rate"]');
			var jul_rate = $tr.find('input[name="jul_rate"]');
			var aug_rate = $tr.find('input[name="aug_rate"]');
			var sept_rate = $tr.find('input[name="sept_rate"]');
			var oct_rate = $tr.find('input[name="oct_rate"]');
			var nov_rate = $tr.find('input[name="nov_rate"]');
			var dec_rate = $tr.find('input[name="dec_rate"]');
			
		    var rate = {
		  		proj_year_id: proj_year_id.val(),
		  		year_resp_cd: year_resp_cd.val(),
		  		jan_rate: isEmpty(jan_rate.val()) ?  '0' : jan_rate.val(),  
		  		feb_rate: isEmpty(feb_rate.val()) ?  '0' :feb_rate.val(), 
		  		mar_rate: isEmpty(mar_rate.val()) ?  '0' :mar_rate.val(), 
		  		apr_rate: isEmpty(apr_rate.val()) ?  '0' :apr_rate.val(), 
		  		may_rate: isEmpty(may_rate.val()) ?  '0' :may_rate.val(), 
		  		jun_rate: isEmpty(jun_rate.val()) ?  '0' :jun_rate.val(), 
		  		jul_rate: isEmpty(jul_rate.val()) ?  '0' :jul_rate.val(), 
		  		aug_rate: isEmpty(aug_rate.val()) ?  '0' :aug_rate.val(), 
		  		sept_rate: isEmpty(sept_rate.val()) ?  '0' :sept_rate.val(), 
		  		oct_rate: isEmpty(oct_rate.val()) ?  '0' :oct_rate.val(), 
		  		nov_rate: isEmpty(nov_rate.val()) ?  '0' :nov_rate.val(), 
		  		dec_rate: isEmpty(dec_rate.val()) ?  '0' :dec_rate.val()
		      };
		  		 		
		   rateList.push(rate);
		   
	 });
	 
	 var requestData = {
		 rateList: rateList,
       
     };
    $.ajax({
	        url: '${ctxt}/execute/rpMng/insertProjRpMng.do',
	        data: JSON.stringify(requestData),
	        type: 'POST', 
	    	cache: false, 	
	    	 async : false,
		    contentType: 'application/json', // Content-Type을 설정합니다.
	    	dataType : 'json',    
			success: function(result){			
				
				if(result.sMessage == "Y"){
					fn_showCustomAlert("저장이 완료되었습니다.");
					fn_bgExecSearch();
				}else if(result.sMessage == "N"){
					fn_showCustomAlert("로그인이 해제 되었습니다.다시 로그인 해주십시오.");
			
				}else if(result.sMessage == "F"){
					fn_showCustomAlert("과제 키가 존재하지 않습니다.");
			
				} 
			},
			error: function(request,status,error) {
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
  	  });
}


function fn_wideContent(){
	if (wideGbn == true){
		$(".col-lg-13").css("display", "none");
		$(".row.section-divided-normal").css("grid-template-columns", "auto");
		$(".admin").css("width", "100%");
		wideGbn = false;
	}else {
		$(".col-lg-13").css("display", "");
		$(".row.section-divided-normal").css("grid-template-columns", "300px auto");
		$(".admin").css("width", "1550px");
		wideGbn = true;
	}
}



function fn_excelDown(){				    
	var params = {};
	params.searchopt    = $('[name=searchopt] :selected').val();   
	params.searchword    = $('#searchword').val();   		
	params.searchoptYear = $('[name=searchoptYear] :selected').val();
	
	 $.fileDownload('${pageContext.request.contextPath}/execute/rpMng/projRpExcelDownLoad.do',{
		  httpMethod: "POST",
		  data:params,
		  prepareCallback:function (url){
			 
			// $("#loading-spinner").show();		  
		  },
		  successCallback: function (url) {
			
		//	$("#loading-spinner").hide();
		  },
		  failCallback: function(responesHtml, url) {
		
		//	$("#loading-spinner").hide();
			console.log('관리자에게 문의 주세요.');
		  }
	});
}
</script>

<div id="container">
	<div id="divRefreshArea">
	<h3 class="page_title" id="title_div"> <span class="adminIcon"></span>
			과제 참여율 관리 
			</h3>
			<span class="starMark btnBox_r_t"><span class="ir_so">중요 표시</span>소계는 저장 후 결과를 확인할 수 있습니다.</span>
		<div class="searchAreaFlex">
			
			<div class="grid_box" style="grid-template-columns:100px 100px 300px 70px 500px; "> 
			
				 
				<!-- 닉네임 검색 -->
				
				<div class="custom-select selectRow">
					<select name="searchoptYear" id="yearSelect">					
															
					</select>
				</div>
				<div class="custom-select selectRow">  
					<select name="searchopt">					
						<option value="resp_nm" <c:if test="${rpMng.searchopt == 'resp_nm'}">selected="selected"</c:if>>연구자명</option>
						<option value="proj_nm" <c:if test="${rpMng.searchopt == 'proj_nm'}">selected="selected"</c:if>> 과제명</option> 															
					</select>
				
				</div>
				<input type="text" name="searchword" id="searchword"  value="${rpMng.searchword}" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_bgExecSearch(); return false;}" />
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_bgExecSearch()" >검색</a>
				<span id = "errArea" style="color:red; text-align: center;"></span>		
			</div>
			<div class="btn_wrap" >	
					<a href="javascript:void(0);" class="btn btn-secondary" style ="min-width: 10rem;" onclick="javascript:fn_wideContent();">화면 확대/축소</a>
					<a href="javascript:void(0);"  class="btn btn-secondary" onclick="javascript:fn_excelDown();" >Excel</a>							
					<a href="javascript:void(0);"  class="btn btn-secondary" id= "rpSaveBtn" onclick="javascript:fn_saveRpMng();" >저장</a>							
			</div>		
		</div>
		
		
		 	

		<!--게시판-->
		<div class="rpMngDiv">
		<input type="hidden" name="yearParam" id="yearParam"  value=""  />
 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id = "rateTable">

				<caption>참여율 관리</caption>
				<colgroup>
					
					<col style="width: 6%;">																
					<col style="width: 6%;">
					<col style="width: 15%;">										
					<col style="width: 28%;">					
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					<col style="width: 50px;">
					
				</colgroup>
				<thead>  

					<tr>
						<th scope='col'>연구자명</th>
						<th scope='col'>구분</th>																					
						<th scope='col'>참여기간(참여율%)</th>						
						<th scope='col'>(수행기간)과제명</th>
						
						<th scope='col'>1월</th>
						<th scope='col'>2월</th>
						<th scope='col'>3월</th>
						<th scope='col'>4월</th>
						<th scope='col'>5월</th>
						<th scope='col'>6월</th>
						<th scope='col'>7월</th>
						<th scope='col'>8월</th>
						<th scope='col'>9월</th>
						<th scope='col'>10월</th>
						<th scope='col'>11월</th>
						<th scope='col'>12월</th>
						
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>

			</table>
			
		</div>
	</div>
</div>

