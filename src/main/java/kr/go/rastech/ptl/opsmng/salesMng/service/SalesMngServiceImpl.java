package kr.go.rastech.ptl.opsmng.salesMng.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.opsmng.salesMng.dao.SalesMngDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class SalesMngServiceImpl extends BaseServiceImpl implements SalesMngService {

	@Resource
	private SalesMngDao salesMngDao;

	
	/**
	 * <pre>
	 * 매출관리 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectSaleMngListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesMngDao.selectSaleMngListCount(param);
	}

	/**
	 * <pre>
	 * 매출관리 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectSaleMngList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesMngDao.selectSaleMngList(param);
	}

	
	/**
	 * <pre>
	 * 매출관리 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectSaleMngDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesMngDao.selectSaleMngDtl(param);
	}

	
	/**
	 * <pre>
	 * 매출관리 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertSaleMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesMngDao.insertSaleMng(param);
	}

	
	/**
	 * <pre>
	 * 매출관리 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteSaleMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesMngDao.deleteSaleMng(param);
	}
	
	
	
}
