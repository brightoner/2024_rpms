<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<title>이미지파일목록</title>
<script type="text/javascript">

  
</script>	
<div width="100%" cellspacing="0" cellpadding="0" class="borderless attachment">
	<c:choose>
		<c:when test="${fn:length(fileList) > 0}">
			<c:forEach var="fileVO" items="${fileList}" varStatus="status">      
		      <div class="borderless text_c">
		       <div>
		       		<c:choose>
			         	<c:when test="${workGbn eq 'profile'}" > 
			       			<img  src='<c:url value='/cmm/fms/getImage.do'/>?atch_img_id=<c:out value="${fileVO.atch_img_id}"/>&fileSn=<c:out value="${fileVO.file_sn}"/>' class="profile" alt="해당파일이미지"/>
			       		 </c:when>
			       		 <c:when test="${workGbn eq 'chMain'}" > 
			       			<img src='<c:url value='/cmm/fms/getImage.do'/>?atch_img_id=<c:out value="${fileVO.atch_img_id}"/>&fileSn=<c:out value="${fileVO.file_sn}"/>' class="chmain" alt="해당파일이미지"/>
			       		 </c:when>
			       		  <c:when test="${workGbn eq 'identity'}" > 
			       		 	<img  src='<c:url value='/cmm/fms/getImage.do'/>?atch_img_id=<c:out value="${fileVO.atch_img_id}"/>&fileSn=<c:out value="${fileVO.file_sn}"/>' alt="해당파일이미지"/>
			       		 			
			       		 </c:when>
			       		  <c:when test="${workGbn eq 'bankbook'}" > 
				       		 <img src='<c:url value='/cmm/fms/getImage.do'/>?atch_img_id=<c:out value="${fileVO.atch_img_id}"/>&fileSn=<c:out value="${fileVO.file_sn}"/>' alt="해당파일이미지"/> 
			       		 </c:when>
			       		 <c:otherwise>
			       		 	<img src='<c:url value='/cmm/fms/getImage.do'/>?atch_img_id=<c:out value="${fileVO.atch_img_id}"/>&fileSn=<c:out value="${fileVO.file_sn}"/>'   alt="해당파일이미지"/>
			       		 </c:otherwise>
		       		 </c:choose>
		       </div>
		      </div>
		      <c:if test="${workGbn eq 'banner'}" > 
			      <div class="borderless">
			       <div>
			       		<a href="#" class="btn btn-secondary" onclick="fn_delete_imgFile('<c:out value="${fileVO.atch_img_id}"/>'); return false;">이미지 삭제</a>
			       </div>
			      </div>
		      </c:if>
		      <c:if test="${workGbn eq 'profile'}" > 
			      <div class="borderless">
			       <div>
			       		<a href="#" class="btn btn-secondary" onclick="fn_delete_ch_imgFile('<c:out value="${fileVO.atch_img_id}"/>' , 'profile'); return false;">이미지 삭제</a>
			       </div>
			      </div>
		      </c:if>
		       <c:if test="${workGbn eq 'chMain'}" > 
			      <div class="borderless">
			       <div>
			       		<a href="#" class="btn btn-secondary" onclick="fn_delete_ch_imgFile('<c:out value="${fileVO.atch_img_id}"/>' , 'chMain'); return false;">이미지 삭제</a>
			       </div>
			      </div>
		      </c:if>
		       <c:if test="${workGbn eq 'identity'}" > 
			      <div class="borderless">
			       <div>
			       	<%-- 	<a href="#" class="btn btn-secondary" onclick="fn_delete_exch_imgFile('<c:out value="${fileVO.atch_img_id}"/>' , 'identity'); return false;">이미지 삭제</a> --%>
			       </div>
			      </div>
		      </c:if>
		       <c:if test="${workGbn eq 'bankbook'}" > 
			      <div class="borderless">
			       <div>
			       		<%-- <a href="#" class="btn btn-secondary" onclick="fn_delete_exch_imgFile('<c:out value="${fileVO.atch_img_id}"/>' , 'bankbook'); return false;">이미지 삭제</a> --%>
			       </div>
			      </div>
		      </c:if>
		    </c:forEach>
		</c:when>
		<c:otherwise>
			<!-- not Image -->
		</c:otherwise>
	</c:choose>
   	
</div>


		
		