package kr.go.rastech.ptl.follow.year.etc.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.year.etc.dao.EtcMngDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EtcMngServiceImpl extends BaseServiceImpl implements EtcMngService {

	@Resource
	private EtcMngDao etcMngDao;

	
	/**
	 * <pre>
	 * 연차사후관리 기타 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectYearEtcListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return etcMngDao.selectYearEtcListCount(param);
	}

	/**
	 * <pre>
	 * 연차사후관리 기타 list
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectYearEtcList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return etcMngDao.selectYearEtcList(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 기타 상세보기
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectYearEtcDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return etcMngDao.selectYearEtcDtl(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 기타 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertYearEtc(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return etcMngDao.insertYearEtc(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 기타 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteYearEtc(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return etcMngDao.deleteYearEtc(param);
	}
	
	
	
}
