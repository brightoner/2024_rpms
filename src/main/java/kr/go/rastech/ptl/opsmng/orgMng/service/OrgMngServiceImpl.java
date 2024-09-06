package kr.go.rastech.ptl.opsmng.orgMng.service;


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
import kr.go.rastech.ptl.opsmng.orgMng.dao.OrgMngDao;
	


/**
 * <pre>
 *       
 *
 * </pre>          
 * @author : lwk
 * @date   :2023. 06. 02.
 */
@Service
public class OrgMngServiceImpl extends BaseServiceImpl implements OrgMngService { 

	@Resource
	private OrgMngDao orgMngDao;
	
	
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
	public int selectOrgMngTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return orgMngDao.selectOrgMngTotalCount(param);
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
	public List<Map<String,Object>> selectOrgMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return orgMngDao.selectOrgMngList(param);
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
	public List<Map<String,Object>> selectOrgMngCodeList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return orgMngDao.selectOrgMngCodeList(param);
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
	public Map<String, Object> selectOrgMngDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return orgMngDao.selectOrgMngDtl(param);
	}


	/**
	 * <pre>
	 * 기관관리 update
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateOrgMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException, NullPointerException {
	   
		if(StringUtils.isNotBlank(Objects.toString(param.get("org_charge_email1"), "")) && StringUtils.isNotBlank(Objects.toString(param.get("org_charge_email2"), "") )){
			param.put("org_charge_email", param.get("org_charge_email1")+"@"+param.get("org_charge_email2"));	
		}
		param.put("modify_id", userVo.getEmplyrkey());
		// 1. ptl_bj_apprl 테이블
		return orgMngDao.updateOrgMng(param);
		
		
	
		
		
	}
	
	/**
	 * <pre>
	 * 기관관리 insert
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertOrgMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException, NullPointerException {
		if(StringUtils.isNotBlank(Objects.toString(param.get("org_charge_email1"), "")) && StringUtils.isNotBlank(Objects.toString(param.get("org_charge_email2"), "") )){
			param.put("org_charge_email", param.get("org_charge_email1")+"@"+param.get("org_charge_email2"));	
		}
		
		param.put("create_id", userVo.getEmplyrkey());
		// 1. ptl_bj_apprl 테이블
		return orgMngDao.insertOrgMng(param);
		
		
	
		
		
	}
	
	/**
	 * <pre>
	 * 기관관리 delete
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteOrgMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException, NullPointerException {
	   
		
		param.put("modify_id", userVo.getEmplyrkey());
		// 1. ptl_bj_apprl 테이블
		return orgMngDao.deleteOrgMng(param);
		
		
	
		
		
	}
	
	
}
