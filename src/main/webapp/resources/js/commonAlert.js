// icon : success , error , warning ,  info , question


function fn_showCustomConfirm(icon ,message , confirmCallback) {
	
    Swal.fire({
        icon: icon,
        title: message,
        showCancelButton: true,
        confirmButtonText: '예',
        cancelButtonText: '아니오',
        confirmButtonColor: '#429f50',
        cancelButtonColor: '#d33',
    }).then(result => {
        if (result.isConfirmed) {
            if (confirmCallback && typeof confirmCallback === 'function') {
                confirmCallback();
            }
        }
    });
}



function fn_showCustomAlert(message , icon) {

	if (isEmpty(icon)){ 
		icon = 'info';
	}
	if(icon == 'e'){
		icon = 'error';
	}else if (icon == 'c'){
		icon = 'info';
	}
	Swal.fire({
		  icon: icon,
          title: message,
          text: '',
		});
	
}


function showCustomPopup() {
    Swal.fire({
        title: '종료 과제 연차별 성과등록 생성',
        width : 1600,
        html: `
        	<div class="divPopArea">
        	<form name="writeForm" method="post" action="">
			
    		
			<div class="grid_box">
			
				
				<div class="custom-select selectRow">
					<label for="dept_org" class="hidden-access"></label>
					<select id="dept_org" name="dept_org"> 
						<option value="">소관부처</option> 
				        <c:forEach var="dept" items="${deptList}">
				        	<option value="${dept.org_nm}">${dept.org_nm}</option>
				        </c:forEach>
					</select>
				</div>
				
			
				<div class="custom-select selectRow">
					<label for="ddct_org" class="hidden-access"></label>
					<select id="ddct_org" name="ddct_org"> 
						<option value="">전담기관</option> 
				        <c:forEach var="ddct" items="${ddctList}">
				        	<option value="${ddct.org_nm}">${ddct.org_nm}</option>
				        </c:forEach>
					</select>
				</div>
				  
				
				신청과제명 : <input type="text" name="proj_nm_kor" id="proj_nm_kor"  value="" style="" onkeypress="javascript:if(event.keyCode == 13) { fn_search(1); return false;}" />
				
				<a href="javascript:void(0);" id="btnSearch" class="btn btn-primary" onclick="fn_search(1)" >검색</a>
			 	<input type="hidden" name="pageSize" value="10"/>
				<input type="hidden"  id="page" name="page" value="" />				
				<input type="hidden"  id="proj_id" name="proj_id" value="" />				
			</div>
		</form>
		
		
	
		<div class="admin ma_t_20"> 
			<table class="table_h" cellpadding="0" cellspacing="0" border="0" id="admTable"> 
				<caption>
				 	 연차과제 -  번호, 소관기관 , 전담기관, 공고명, 신청과제명, 협약일
				</caption>
				<colgroup>
					<col style="width:5%">	
					<col style="width:12%">
					<col style="width:12%">						 	
					<col style="width:*">				
					<col style="width:13%">		
				</colgroup>
				<thead>
					<tr>
						<th scope='col'>번호</th>												
						<th scope='col'>소관기관</th>
						<th scope='col'>전담기관</th>
						<th scope='col'>협약과제명</th>																							
						<th scope='col'>협약일</th>
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
		
			<div id="paging" class="paginate"></div>
		
		</div>
		</div> 
        `,
        showCancelButton: true,
        showConfirmButton: false,
        /* confirmButtonText: '', */
        cancelButtonText: '닫기',
        preConfirm: () => {
        
        }
    }).then((result) => {
        if (result.isConfirmed) {
        
        }
    });
	fn_search(1); 
} 