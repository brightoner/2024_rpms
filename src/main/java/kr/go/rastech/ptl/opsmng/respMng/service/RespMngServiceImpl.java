package kr.go.rastech.ptl.opsmng.respMng.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;



import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserAuthVo;
import kr.go.rastech.ptl.opsmng.respMng.dao.RespMngDao;
	


/**
 * <pre>
 *       
 *
 * </pre>          
 * @author : lwk
 * @date   :2023. 06. 02.
 */
@Service
public class RespMngServiceImpl extends BaseServiceImpl implements RespMngService { 

	@Resource
	private RespMngDao respMngDao;
	
	
	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectRespMngTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return respMngDao.selectRespMngTotalCount(param);
	}

	/**
	 * <pre>
	 * bj승인관리 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectRespMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return respMngDao.selectRespMngList(param);
	}
	


	/**
	 * <pre>
	 * bj승인관리 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectRespMngCodeList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return respMngDao.selectRespMngCodeList(param);
	}
	
	/**
	 * <pre>
	 * bj승인관리 상세 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectRespMngDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return respMngDao.selectRespMngDtl(param);
	}


	/**
	 * <pre>
	 * 책임자관리 update
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateRespMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException, NullPointerException {
		if(StringUtils.isNotBlank(Objects.toString(param.get("email1"), "")) && StringUtils.isNotBlank(Objects.toString(param.get("email2"), "") )){
			param.put("resp_email", param.get("email1")+"@"+param.get("email2"));	
		}
		
		
		
		param.put("modify_id", userVo.getEmplyrkey());
		// 1. ptl_bj_apprl 테이블
		return respMngDao.updateRespMng(param);
		
		
	
		
		
	}
	
	/**
	 * <pre>
	 * 책임자관리 insert
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertRespMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException, NullPointerException {
		if(StringUtils.isNotBlank(Objects.toString(param.get("email1"), "")) && StringUtils.isNotBlank(Objects.toString(param.get("email2"), "") )){
			param.put("resp_email", param.get("email1")+"@"+param.get("email2"));
		}
		
		param.put("create_id", userVo.getEmplyrkey());
		// 1. ptl_bj_apprl 테이블
		return respMngDao.insertRespMng(param);
		
		
	
		
		
	}
	
	/**
	 * <pre>
	 * 책임자관리 delete
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteRespMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException, NullPointerException {
	   
		
		param.put("modify_id", userVo.getEmplyrkey());
		// 1. ptl_bj_apprl 테이블
		return respMngDao.deleteRespMng(param);
		
		
	
		
		
	}
	
	
}
