package kr.go.rastech.ptl.apply.ann.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.apply.ann.dao.AnnDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class AnnServiceImpl extends BaseServiceImpl implements AnnService {

	@Resource
	private AnnDao annDao;

	
	/**
	 * <pre>
	 * 공고현황 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectAnnListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return annDao.selectAnnListCount(param);
	}

	/**
	 * <pre>
	 * 공고현황 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectAnnList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return annDao.selectAnnList(param);
	}
	
	
	/**
	 * <pre>
	 * 공고현황 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertAnn(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return annDao.insertAnn(param);
	}
	

	/**
	 * <pre>
	 * 공고현황 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectAnnDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return annDao.selectAnnDtl(param);
	}

	
	/**
	 * <pre>
	 * 공고현황 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateAnn(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return annDao.updateAnn(param);
	}

	
	/**
	 * <pre>
	 * 공고현황 삭제시 ann_id 체크
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int chkAnnId(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return annDao.chkAnnId(param);
	}

	
	
	/**
	 * <pre>
	 * 공고현황 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteAnn(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		return annDao.deleteAnn(param);
	}

	
	



	
	
}
