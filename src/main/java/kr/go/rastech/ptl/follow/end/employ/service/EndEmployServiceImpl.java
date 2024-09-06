package kr.go.rastech.ptl.follow.end.employ.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.end.employ.dao.EndEmployDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EndEmployServiceImpl extends BaseServiceImpl implements EndEmployService {

	@Resource
	private EndEmployDao endEmployDao;

	
	/**
	 * <pre>
	 * 종료사후관리 채용 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndEmployListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEmployDao.selectEndEmployListCount(param);
	}

	/**
	 * <pre>
	 * 종료사후관리 채용 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndEmployList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEmployDao.selectEndEmployList(param);
	}

	
	/**
	 * <pre>
	 * 종료사후관리 채용 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectEndEmployDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEmployDao.selectEndEmployDtl(param);
	}

	
	/**
	 * <pre>
	 * 종료사후관리 채용 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertEndEmploy(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEmployDao.insertEndEmploy(param);
	}

	
	/**
	 * <pre>
	 * 종료사후관리 채용 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteEndEmploy(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endEmployDao.deleteEndEmploy(param);
	}
	
	
	
}
