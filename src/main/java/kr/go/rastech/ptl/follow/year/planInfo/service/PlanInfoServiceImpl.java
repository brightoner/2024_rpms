package kr.go.rastech.ptl.follow.year.planInfo.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.year.planInfo.dao.PlanInfoDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class PlanInfoServiceImpl extends BaseServiceImpl implements PlanInfoService {

	@Resource
	private PlanInfoDao planInfoDao;
	
	/**
	 * <pre>
	 * 연차사후관리 기본정보 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectPlanInfoListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return planInfoDao.selectPlanInfoListCount(param);
	}

	/**
	 * <pre>
	 * 연차사후관리 기본정보 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectPlanInfoList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		return planInfoDao.selectPlanInfoList(param);
	}

	
	
	/**
	 * <pre>
	 * 연차사후관리 기본정보 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectPlanInfoDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return planInfoDao.selectPlanInfoDtl(param);
	}

	
		

	
	

	
	
	
	
	
	
}
