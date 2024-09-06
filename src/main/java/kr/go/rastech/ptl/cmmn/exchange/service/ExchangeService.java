package kr.go.rastech.ptl.cmmn.exchange.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * 환율 interface 구현
 * <pre>
 * FileName: ExchangeService.java
 * Package : kr.go.rastech.ptl.cmmn.exchange.service
 * 
 * </pre>
 * @author : lwk
 * @date   : 2023. 5. 14.
 */
public interface ExchangeService {


	
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
