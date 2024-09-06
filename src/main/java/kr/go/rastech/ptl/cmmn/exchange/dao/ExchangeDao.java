package kr.go.rastech.ptl.cmmn.exchange.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * <pre>
 *  환율
 * 
 * </pre>
 * @FileName : ExchangeDao.java
 * @package  : kr.go.rastech.ptl.cmmn.exchange.dao
 * @author   : lwk
 * @date     : 2023. 2. 23.
 * 
 */
@Repository
public interface ExchangeDao {


	
	/**
	 * <pre>
	 * 환율정보 조회
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectFinancialExchange(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	 
	
}
