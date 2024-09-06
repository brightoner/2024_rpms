package kr.go.rastech.ptl.execute.plan.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.apply.req.dao.ReqDao;
import kr.go.rastech.ptl.execute.plan.dao.PlanDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class PlanServiceImpl extends BaseServiceImpl implements PlanService {

	@Resource
	private PlanDao planDao;

	@Resource
	private ReqDao reqDao;
	
	/**
	 * <pre>
	 * 과제진도 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectProjPlanListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return planDao.selectProjPlanListCount(param);
	}

	/**
	 * <pre>
	 * 과제진도 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectProjPlanList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return planDao.selectProjPlanList(param);
	}

	
	/**
	 * <pre>
	 * 과제진도 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectProjPlanDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return planDao.selectProjPlanDtl(param);
	}

	
	/**
	 * <pre>
	 * 연차과제 신청 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateProjPlan(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		int result = 0;
		
		
		Map<String, Object> mainParam = (Map<String, Object>) param.get("mainItem");
		List<Map<String, Object>> respList = (List<Map<String, Object>>) param.get("respList");
		String proj_year_id = Objects.toString(param.get("proj_year_id") ,"") ;
		
		if(StringUtils.isNotBlank(proj_year_id)) {
			result = planDao.updateProjPlan(mainParam);
			
			if(result > 0) {
	
				// 참여연구원 삭제
				planDao.deleteYearRespPerson(param);
				
				// 참여연구원 등록
				int idx = 0;
				if (respList != null && !respList.isEmpty()) {
					for (Map<String, Object> respMap : respList) {
						respMap.put("sub_year_resp_id", idx);
						respMap.put("proj_year_id", proj_year_id);
						planDao.instYearRespPerson(respMap);
						
						idx ++;
		            }
				}
				
				
			}
		
		}else {
			result = 0;
		}
		
		return result;
	}
		

	
	
	/**
	 * <pre>
	 * 연차과제 신청 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteProjPlan(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return planDao.deleteProjPlan(param);
	}


	/**
	 * <pre>
	 * 연구과제 신청 상세보기 시 실무책임자 select
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectYearRespPerson(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return planDao.selectYearRespPerson(param);
	}

	
	

	/**
	 * <pre>
	 * Wbs 연구자 매핑 활용
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectWbsYearRespPerson(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return planDao.selectWbsYearRespPerson(param);
	}

	
	
	
	
}
