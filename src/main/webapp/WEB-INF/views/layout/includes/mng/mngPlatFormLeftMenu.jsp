<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- leftMenu -->
<script type="text/javascript">
$(function(){

	$(".sub_left_ul>li>a").click(function(){
		$(".sidebar-left2 ul ul").slideUp();
		if(!$(this).next().is(":visible")){
			$(this).next().slideDown();
		}
	})
	   
	  if($(".sidebar-left2 ul li a").is(".menuLvl2")===true){
		$(".menuLvl2").parents().parents().slideDown();
		$(".menuLvl2").parents().parents().siblings('a').addClass("menuLvl1");
	}  

	if($("#menuGubun").val() != undefined && $("#menuGubun").val() != ''){
		var menuGubun = $("#menuGubun").val().split('/');
		switch(menuGubun[1]){
		case 'elib':
			 $('.sidebar-left1').css('background-color','#5c755f');
			 break;
		case 'archive':
			 $('.sidebar-left1').css('background-color','#aa7d5e');
			 break;
		case 'mlib':
			 $('.sidebar-left1').css('background-color','#9c9f84');
			 break; 
		case 'data':
			 $('.sidebar-left1').css('background-color','#4d8963');
			 break;
		
		} 
	}
	
	/* 1024px 이하 화면에서 햄버거 메뉴 처리 */
	$("button.mo-menu-btn").click(function() {
		$("#sidebar-left").addClass("view");
 	});
	
	$("#btnClose").click(function() {
		$("#sidebar-left").removeClass("view");
	});		
	
})

function fn_open(obj){
		$(".sub_left_ul ul ul").slideUp();
		if(!$("#"+obj).is(":visible")){
			$("#"+obj).slideDown();
		}
		
}

function lv1_click(obj){
		$('.menuLvl1').removeClass();
		$('#lv1_'+obj).addClass('menuLvl1');
}


function fn_goChMain(val){
	if(val == '' || val == undefined){
		
		fn_showCustomAlert("채널 ID가 존재하지 않습니다.");
		
		return false;
	}

	var form = document.goChMainForm;
	form.chMainId.value= val;
	form.action = '${ctxt}/vCh/visit/vChMain.do';
	form.submit();	
}


</script>

<style>
/* 	@media all and (max-width:990px) {
		#sidebar-left{
			width:0;
			display:none;
		}
	} */
	.sub_left_ul li a.on {color:rgba(237,125,49,0);}
</style> 
	<div id="sidebar-left" class="sidebar-left"> 	
				<div class="sidebar-left1">
			       <c:if test = "${fn:length(sys_leftMenu) ne 0  }" >		           
			           <c:forEach var="menuTitle" items="${sys_leftMenu}" varStatus="status">
			           	  <c:if test="${menuTitle.lvl eq '0'}">
			           	  		<span class="sub_left_text"><c:out value="${menuTitle.menuNm}" /></span>
			           	  		<input type="hidden" id="menuGubun" value="${menuTitle.url}">
			           	  </c:if>
			           </c:forEach>
			       </c:if>		
      		  </div>
         
        
          	<c:if test = "${fn:length(sys_leftMenu) ne 0  }" >		           
		           <c:forEach var="menuTitle" items="${sys_leftMenu}" varStatus="status">
		           	  <c:if test="${menuTitle.lvl eq '0'}">
		 					<c:if test="${menuTitle.menuNm == '마이 채널'}">
								<div class="sidebar-left-channel">
									<div class="sidebar-left-div profile" id = "chProfilArea">
										
									</div>
			 						<p class="text_c ma_t_10">		   			 						        	
									    <a href="${ctxt}/myCh/ch/chMain.do" role="button" style="font-weight: 700; color: #f97e15;" title="정보"><c:out value="${userVo.nicknm}"/></a>		
									    <br/>
									    <ul>
									  	<li>채널에 오신걸 환영합니다.</li>
									  	</ul>
									</p>
								</div>			    			   	
		 					</c:if>
		 					<c:if test="${menuTitle.menuNm == '방문 채널'}">
								<div class="sidebar-left-channel">
								<div class="sidebar-left-div profile" id = "chProfilArea">
										
								</div>
			 						<p class="text_c ma_t_10">		   			 						        	
									    <a onclick="javasctipt:fn_goChMain('${chUserId}');" role="button" style="font-weight: 700; color: #f97e15;" title="정보"><c:out value="${chUserNicknm}"/></a>		
									    <br/>
									  	<ul>
									  	<li>채널에 오신걸 환영합니다.</li>
									  	</ul>
									</p>
								</div>			    			   	
		 					</c:if>
		 
		           	  </c:if>
		           </c:forEach>
	       </c:if>			     		
        
		<div class="sidebar-left2">
		
			<ul class="sub_left_ul">
	           <c:if test = "${fn:length(sys_leftMenu) ne 0  }" >
		            <c:forEach var="menuVo1" items="${sys_leftMenu}" varStatus="status">
				           <%--  <c:if test="${menuVo1.lvl eq '0'}">
				            	<li><a href="#" id="lv1_${status.index}" onclick="javascript:lv1_click(${status.index})" role="button"> <c:out value="${menuVo1.menuNm} = ${menuVo1.menu_con}"/></a></li>
				            </c:if>	 --%>   
			 
		                       	<c:if test="${menuVo1.lvl ne '0'}">		                 		
			                 		<c:if test="${menuVo1.lvl eq '1'}">		
				                 		<li>
				                 		      <c:if test="${menuVo1.menu_con eq '0'}">				                 		    	    				                 		  	
	                 		    				<a href="#" id="lv1_${status.index}" onclick="javascript:lv1_click(${status.index})" role="button"> <c:out value="${menuVo1.menuNm}"/></a>		                 		    									                 		    				                 		 
				                 		    </c:if>  
				                 		  <c:if test="${menuVo1.menu_con eq '1'}">					                 		        
				                 		    	 <c:choose>	
					                 		    	<c:when test="${menuVo1.selYn eq true}">
														<a id="lv1_${status.index}" class="menuLvl1" href="${ctxt}<c:out value='${menuVo1.url}'/>" >  <c:out value="${menuVo1.menuNm}"/></a>						                 		   					                 		    	
					                 		    	</c:when>
					                 		    	<c:otherwise>
														<a href="${ctxt}<c:out value='${menuVo1.url}'/>" > <c:out value="${menuVo1.menuNm}"/></a>
					                 	
					                 		    	</c:otherwise>	
				                 		    	</c:choose>			                		    	
				                 		    	
			                 		    				                 		                     		  						                 		    	
				                 		    </c:if>						                 	
				                 			
				                 				<!-- 2레벨 -->
				                 				<c:forEach var="menuVo2" items="${sys_leftMenu}" varStatus="status">	
				                 					
					                 				<c:if test="${menuVo1.menu_id eq menuVo2.menu_prts_id}">
						                 				<c:if test="${status.index eq 0}">
						                 					<ul>			                 			
						                 				</c:if>
						                 					<li>					                 					
						                 						<c:if test="${menuVo2.menu_con eq '0'}">
									                 		    	<a  href="javascript:fn_open('lv3_${status.index}')" class="menuLvl1"> <c:out value="${menuVo2.menuNm}"/></a>
									                 		    </c:if>
									                 		    <c:if test="${menuVo2.menu_con eq '1'}">
									                 		    
									                 		    	
									                 		    			<a <c:if test="${menuVo2.selYn eq true}">class="menuLvl2"</c:if> href="${ctxt}<c:out value='${menuVo2.url}'/>" ><c:out value="${menuVo2.menuNm}"/> </a>	
									                 		    	
									                 		    	
									                 		    </c:if>
						                 					</li>
							                					<!-- 3레벨 -->
							                					<li>
							                						<ul id="lv3_<c:out value='${status.index}'/>">
									                					<c:forEach var="menuVo3" items="${sys_leftMenu}" varStatus="status">
											                 				<c:if test="${menuVo2.menu_id eq menuVo3.menu_prts_id}">
	<%-- 										                 			<li><a <c:if test="${menuVo3.selYn eq true}">class="menuLvl2"</c:if> href="/ncmik_is<c:out value='${menuVo3.url}'/>" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-> <c:out value="${menuVo3.menuNm}"/></a></li> --%>
											                 				<li><a <c:if test="${menuVo3.selYn eq true}">class="menuLvl2"</c:if> href="${ctxt}<c:out value='${menuVo3.url}'/>" ><img src="/images/common/bg_dot.png" aria-hidden="true" alt="dot 이미지" style="margin-right:5px;margin-bottom:4px;"/> <c:out value="${menuVo3.menuNm}"/></a></li>
											                 				</c:if>
										                 				</c:forEach>
								                 					</ul>
								                 				</li>
						                 				<c:if test="${status.index eq 0}">
						                 					</ul>			                 			
						                 				</c:if>	
					                 				</c:if>
					                 			
				                 				</c:forEach>
				                 					
				                 					                 		
				                 		</li>
			                 		</c:if>
		                 </c:if>
		                 
		           </c:forEach>
		           
		            
	           </c:if>
			</ul>
		</div>
	
	</div>

	
	
 <form name="goChMainForm" method="post" action="">
 	<input type="hidden" name="paramChUserId" id="chMainId" value=""/>
 </form>
 