package kr.go.rastech.ptl.execute.bgMake.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.execute.bgMake.dao.BgMakeDao;
	


/**
 * <pre>
 *       
 *
 * </pre>          
 * @author : lwk
 * @date   :2023. 06. 02.
 */
@Service
public class BgMakeServiceImpl extends BaseServiceImpl implements BgMakeService { 

	@Resource
	private BgMakeDao bgMakeDao;
	

	/**
	 * <pre>
	 * 예산 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectProjBgMakeList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return bgMakeDao.selectProjBgMakeList(param);
	}
	
	@Override
	public List<Map<String,Object>> selectProjAccountSubjectList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return bgMakeDao.selectProjAccountSubjectList(param);
	}
	
	@Override
	public void insertProjAcctSubj(Map<String, Object> params) throws IOException, SQLException , NullPointerException  {
		 UserVo vo = getUser();

		 List<Map<String, Object>> itemList = (List<Map<String, Object>>) params.get("itemList");
		
	
		 if (itemList != null && !itemList.isEmpty()) {
				 for (int i = 0; i < itemList.size(); i++) {						
						itemList.get(i).put("create_id", vo.getEmplyrkey());				
						itemList.get(i).put("modify_id", vo.getEmplyrkey());				
							
						bgMakeDao.insertProjAcctSubj(itemList.get(i));

				}
				
		 }
	  
		 

	}


	@Override
	public void updateProjBudget(Map<String, Object> params) throws IOException, SQLException , NullPointerException  {
		 UserVo vo = getUser();

		 List<Map<String, Object>> itemList = (List<Map<String, Object>>) params.get("itemList");
		
	
		 if (itemList != null && !itemList.isEmpty()) {
				 for (int i = 0; i < itemList.size(); i++) {													
						itemList.get(i).put("modify_id", vo.getEmplyrkey());				
						String delgbn = Objects.toString( itemList.get(i).get("delgbn") , "N");
						if(StringUtils.equals(delgbn, "Y") ) {							
							itemList.get(i).put("modify_id", vo.getEmplyrkey());
							bgMakeDao.deleteProjBudget(itemList.get(i));
						}else {
							bgMakeDao.updateProjBudget(itemList.get(i));
						}
				}
				
		 }
	  
		 

	}



	@Override
	public void updateProjBudgetExec(Map<String, Object> params) throws IOException, SQLException , NullPointerException  {
		 UserVo vo = getUser();

		 List<Map<String, Object>> itemList = (List<Map<String, Object>>) params.get("itemList");
		
	
		 if (itemList != null && !itemList.isEmpty()) {
				 for (int i = 0; i < itemList.size(); i++) {													
						itemList.get(i).put("modify_id", vo.getEmplyrkey());				
						
						
						bgMakeDao.updateProjBudgetExec(itemList.get(i));
						
				}
				
		 }
	  
		 

	}


}
