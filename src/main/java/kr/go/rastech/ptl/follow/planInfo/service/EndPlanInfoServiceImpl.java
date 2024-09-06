package kr.go.rastech.ptl.follow.planInfo.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.planInfo.dao.EndPlanInfoDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EndPlanInfoServiceImpl extends BaseServiceImpl implements EndPlanInfoService {

	@Resource
	private EndPlanInfoDao endPlanInfoDao;
	
	
	/**
	 * <pre>
	 * 종료된 협약과제 list count (종료사후과제의 대상 목록 list count)
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndTargetListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endPlanInfoDao.selectEndTargetListCount(param);
	}

	
	/**
	 * <pre>
	 * 종료된 협약과제 list (종료사후과제의 대상 목록 list)
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndTargetList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endPlanInfoDao.selectEndTargetList(param);
	}
	
	

	/**
	 * <pre>
	 *종료과제 유무 확인 
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int chkEndPlan(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endPlanInfoDao.chkEndPlan(param);
	}
	
	
	/**
	 * <pre>
	 * 종료과제 생성
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertEndPlan(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endPlanInfoDao.insertEndPlan(param);
	}

	
	/**
	 * <pre>
	 * 종료사후관리 기본정보 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndPlanInfoListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return endPlanInfoDao.selectEndPlanInfoListCount(param);
	}

	/**
	 * <pre>
	 * 종료사후관리 기본정보 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectEndPlanInfoList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		return endPlanInfoDao.selectEndPlanInfoList(param);
	}

	
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectEndPlanInfoDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endPlanInfoDao.selectEndPlanInfoDtl(param);
	}





	

	
		

	
	

	
	
	
	
	
	
}
