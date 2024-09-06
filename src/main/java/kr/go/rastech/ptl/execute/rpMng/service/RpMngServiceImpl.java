package kr.go.rastech.ptl.execute.rpMng.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.execute.rpMng.dao.RpMngDao;
	


/**
 * <pre>
 *       
 *
 * </pre>          
 * @author : lwk
 * @date   :2023. 06. 02.
 */
@Service
public class RpMngServiceImpl extends BaseServiceImpl implements RpMngService { 

	@Resource
	private RpMngDao rpMngDao;
	

	/**
	 * <pre>
	 * 참여연구원 참여율 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectProjRpMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return rpMngDao.selectProjRpMngList(param);
	}

	/**
	 * <pre>
	 * 참여연구원 참여율 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectExcelProjRpMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return rpMngDao.selectExcelProjRpMngList(param);
	}
	/**
	 * <pre>
	 * 참여연구원 참여율 존재 체크
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String,Object> selectChkProjRpRate(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return rpMngDao.selectChkProjRpRate(param);
	}


	
	@Override
	public void insertProjRpMng(Map<String, Object> params) throws IOException, SQLException , NullPointerException  {
		UserVo vo = getUser();

		List<Map<String, Object>> rateList = (List<Map<String, Object>>) params.get("rateList");	
		 if (rateList != null && !rateList.isEmpty()) {
			 for (Map<String, Object> rateMap : rateList) {
				 rateMap.put("create_id", vo.getEmplyrkey());	
				 rateMap.put("modify_id", vo.getEmplyrkey());		
				 rpMngDao.insertProjRpMng(rateMap);
			 }
		 }
		
		 
	}


}
