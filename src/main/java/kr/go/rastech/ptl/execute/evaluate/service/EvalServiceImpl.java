package kr.go.rastech.ptl.execute.evaluate.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.execute.evaluate.dao.EvalDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EvalServiceImpl extends BaseServiceImpl implements EvalService {

	@Resource
	private EvalDao evalDao;
	
	/**
	 * <pre>
	 * 평가관리 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectProjEvalListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return evalDao.selectProjEvalListCount(param);
	}

	/**
	 * <pre>
	 * 평가관리 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectProjEvalList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		return evalDao.selectProjEvalList(param);
	}

	
	
	/**
	 * <pre>
	 * 평가관리 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectProjEvalDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return evalDao.selectProjEvalDtl(param);
	}

	
	
	/**
	 * <pre>
	 * 평가관리 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertProjEval(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return evalDao.insertProjEval(param);
	}
	
	
	
	
	
	
	
}
