package kr.go.rastech.ptl.apply.agmt.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.apply.agmt.dao.AgmtDao;
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
public class AgmtServiceImpl extends BaseServiceImpl implements AgmtService {

	@Resource
	private AgmtDao agmtDao;

	@Resource
	private ReqDao reqDao;
	
	/**
	 * <pre>
	 * 과제협약 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectAgmtListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return agmtDao.selectAgmtListCount(param);
	}

	/**
	 * <pre>
	 * 과제협약 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectAgmtList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return agmtDao.selectAgmtList(param);
	}
	
	
	
	
	
}
