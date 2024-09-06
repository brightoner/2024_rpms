package kr.go.rastech.ptl.follow.end.etc.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.end.etc.dao.EndEtcMngDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EndEtcMngServiceImpl extends BaseServiceImpl implements EndEtcMngService {

	@Resource
	private EndEtcMngDao endEtcMngDao;

	
	/**
	 * <pre>
	 * 최종사후관리 기타 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndEtcListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEtcMngDao.selectEndEtcListCount(param);
	}

	/**
	 * <pre>
	 * 최종사후관리 기타 list
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndEtcList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEtcMngDao.selectEndEtcList(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 기타 상세보기
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectEndEtcDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEtcMngDao.selectEndEtcDtl(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 기타 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertEndEtc(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEtcMngDao.insertEndEtc(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 기타 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteEndEtc(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEtcMngDao.deleteEndEtc(param);
	}
	
	
	
}
