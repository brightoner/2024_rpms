package kr.go.rastech.ptl.follow.end.sales.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.end.sales.dao.EndSalesDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EndSalesServiceImpl extends BaseServiceImpl implements EndSalesService {

	@Resource
	private EndSalesDao endSalesDao;

	
	/**
	 * <pre>
	 * 매출 관리 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndSaleMngListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.selectEndSaleMngListCount(param);
	}

	
	/**
	 * <pre>
	 * 매출 관리 리스트 
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndSaleMngList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.selectEndSaleMngList(param);
	}
	
	
	
	
	/**
	 * <pre>
	 * 최종사후관리 매출 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndSalesListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.selectEndSalesListCount(param);
	}

	/**
	 * <pre>
	 * 최종사후관리 매출 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndSalesList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.selectEndSalesList(param);
	}

	

	
	/**
	 * <pre>
	 * 최종사후관리 매출 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertEndSales(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.insertEndSales(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 매출 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateEndSales(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.updateEndSales(param);
	}
	
	
	/**
	 * <pre>
	 * 최종사후관리 매출 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteEndSales(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endSalesDao.deleteEndSales(param);
	}


	
	
	
}
