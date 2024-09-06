package kr.go.rastech.ptl.execute.evaluate.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 *평가관리  dao
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Repository
public interface EvalDao {

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
	public int selectProjEvalListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
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
	public List<Map<String, Object>> selectProjEvalList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
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
	public Map<String, Object> selectProjEvalDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
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
	public int insertProjEval(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	
	
	
	
	
}
