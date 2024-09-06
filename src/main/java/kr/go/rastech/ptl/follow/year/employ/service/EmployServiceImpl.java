package kr.go.rastech.ptl.follow.year.employ.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.year.employ.dao.EmployDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EmployServiceImpl extends BaseServiceImpl implements EmployService {

	@Resource
	private EmployDao employDao;

	
	/**
	 * <pre>
	 * 연차사후관리 채용 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectYearEmployListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return employDao.selectYearEmployListCount(param);
	}

	/**
	 * <pre>
	 * 연차사후관리 채용 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectYearEmployList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return employDao.selectYearEmployList(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 채용 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectYearEmployDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return employDao.selectYearEmployDtl(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 채용 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertYearEmploy(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return employDao.insertYearEmploy(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 채용 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteYearEmploy(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return employDao.deleteYearEmploy(param);
	}
	
	
	
}
