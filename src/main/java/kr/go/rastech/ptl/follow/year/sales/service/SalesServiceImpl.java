package kr.go.rastech.ptl.follow.year.sales.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.year.sales.dao.SalesDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class SalesServiceImpl extends BaseServiceImpl implements SalesService {

	@Resource
	private SalesDao salesDao;

	
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
	public int selectSaleMngListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.selectSaleMngListCount(param);
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
	public List<Map<String, Object>> selectSaleMngList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.selectSaleMngList(param);
	}
	
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 매출 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectYearSalesListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.selectYearSalesListCount(param);
	}

	/**
	 * <pre>
	 * 연차사후관리 매출 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectYearSalesList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.selectYearSalesList(param);
	}

	

	
	/**
	 * <pre>
	 * 연차사후관리 매출 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertYearSales(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.insertYearSales(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 매출 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateYearSales(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.updateYearSales(param);
	}
	
	
	/**
	 * <pre>
	 * 연차사후관리 매출 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteYearSales(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return salesDao.deleteYearSales(param);
	}


	
	
	
}
