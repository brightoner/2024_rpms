package kr.go.rastech.ptl.apply.req.service;


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
import kr.go.rastech.ptl.apply.req.dao.ReqDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class ReqServiceImpl extends BaseServiceImpl implements ReqService {

	@Resource
	private ReqDao reqDao;

	
	/**
	 * <pre>
	 * 연구과제 신청 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectReqListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return reqDao.selectReqListCount(param);
	}

	/**
	 * <pre>
	 * 연구과제 신청 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectReqList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return reqDao.selectReqList(param);
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertReq(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		int result = 0;
		
		// 연구과제 신청 등록
		result = reqDao.insertReq(param);
		
		if(result > 0) {
			
			// 참여기관 등록
			String partOrgCdsParam = (String) param.get("part_org_cds");
			String[] part_org_cd;
			if (partOrgCdsParam != null) {
			    part_org_cd = partOrgCdsParam.split(","); // 쉼표로 구분된 문자열일 경우
			} else {
			    part_org_cd = new String[0]; // null이면 빈 배열
			}

			for (int i = 0; i < part_org_cd.length; i++) {
				
				param.put("sub_part_org_id", i);
				param.put("part_org_cd", part_org_cd[i]);
				reqDao.insertPartOrg(param);
			}
			
			// 총괄책임자 등록
			Map<String, Object> lPrepMap = new HashMap<String,Object>();
			lPrepMap.put("proj_id", param.get("proj_id"));
			lPrepMap.put("sub_resp_id","0");
			lPrepMap.put("resp_cd", param.get("lResp_cd"));
			lPrepMap.put("resp_gb", "L");
			reqDao.insertRespPerson(lPrepMap);
			
			
			// 실무책임자 등록
			String respCdsParam = (String) param.get("resp_cds");
			String[] resp_cd;
			if (respCdsParam != null) {
				resp_cd = respCdsParam.split(","); // 쉼표로 구분된 문자열일 경우
			} else {
				resp_cd = new String[0]; // null이면 빈 배열
			}

			for (int i = 0; i < resp_cd.length; i++) {
				
				param.put("sub_resp_id", i);
				param.put("resp_cd", resp_cd[i]);
				param.put("resp_gb", "R");
				reqDao.insertRespPerson(param);
			}
			
		}
		
		return result;
		
	}
	

	/**
	 * <pre>
	 * 연구과제 신청 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectReqDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return reqDao.selectReqDtl(param);
	}

	
	/**
	 * <pre>
	 * 연구과제 신청 상세보기 시 참여기관 select
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectPartOrg(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return reqDao.selectPartOrg(param);
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
	public List<Map<String, Object>> selectRespPerson(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return reqDao.selectRespPerson(param);
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 상세보기 시 총괄책임자 select
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectLRespPerson(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return reqDao.selectLRespPerson(param);
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateReq(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		int result = 0;
		
		
		Map<String, Object> mainParam = (Map<String, Object>) param.get("mainItem");
		List<Map<String, Object>> respList = (List<Map<String, Object>>) param.get("respList");
		List<Map<String, Object>> orgList = (List<Map<String, Object>>) param.get("orgList");
		String proj_id = Objects.toString(param.get("proj_id") ,"") ;
		
		if(StringUtils.isNotBlank(proj_id)) {
			
			result = reqDao.updateReq(mainParam);
			
			if(result > 0) {
				// 참여기관 삭제
				reqDao.deletePartOrg(param);
				
				// 실무자 삭제
				reqDao.deleteRespPerson(param);
				
				// 참여기관 등록
				int orgIdx = 0;
				if (orgList != null && !orgList.isEmpty()) {
					for (Map<String, Object> orgMap : orgList) {
						orgMap.put("proj_id", proj_id);
						orgMap.put("sub_part_org_id", orgIdx);
						orgMap.put("part_org_cd", orgList.get(orgIdx).get("part_org_cd"));
						reqDao.insertPartOrg(orgMap);
						
						orgIdx ++;
		            }
				}
				
				// 총괄책임자 등록
				mainParam.put("sub_resp_id","0");
				mainParam.put("resp_gb", "L");
				mainParam.put("proj_id", proj_id);
				reqDao.insertRespPerson(mainParam);
				
				
				// 실무책임자 등록
				
				int respIdx = 0;
				if (respList != null && !respList.isEmpty()) {
					for (Map<String, Object> respMap : respList) {
						respMap.put("proj_id", proj_id);
						respMap.put("sub_resp_id", respIdx);
						respMap.put("resp_cd", respList.get(respIdx).get("resp_cd"));
						respMap.put("resp_gb", "R");
						reqDao.insertRespPerson(respMap);
						
						respIdx ++;
		            }
				}
			}else {
				result = 0;
			}
			
		}
		
		return result;
	}

	
	/**
	 * <pre>
	 * 연구과제 신청 삭제시 선정단계인지 체크 - SEL_GB : 0 만 삭제 가능 
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int chkSelGb(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return reqDao.chkSelGb(param);
	}

	
	
	/**
	 * <pre>
	 * 연구과제 신청 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteReq(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		int result = 0;
		
		// 연구과제 신청 삭제
		result =  reqDao.deleteReq(param);
		
		if(result != 0) {
			// 참여기관정보 삭제
			reqDao.deletePartOrg(param);
			// 책임자 정보 삭제
			reqDao.deleteRespPerson(param);
		}
		return result;
	}

	

	

	

	

	
	



	
	
}
