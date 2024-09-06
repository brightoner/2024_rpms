package kr.go.rastech.ptl.cmmn.exchange.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.cmmn.exchange.dao.ExchangeDao;

/**
 * <pre>
 * 
 *  환율
 * </pre>
 * @FileName : ExchangeServiceImpl.java
 * @package  : kr.go.rastech.ptl.cmmn.exchange.service
 * @author   : lwk
 * @date     : 2023. 6. 28.
 * 
 */
@Service
public class ExchangeServiceImpl extends BaseServiceImpl implements ExchangeService {

	@Resource
	private ExchangeDao exchangeDao;

	@Override
	public Map<String, Object> selectFinancialExchange(Map<String, Object> param) 	throws IOException, SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return exchangeDao.selectFinancialExchange(param);
	}


}
