package kr.go.rastech.ptl.follow.year.employ.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 *연차사후관리 채용  dao
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Repository
public interface EmployDao {

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
	public int selectYearEmployListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
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
	public List<Map<String, Object>> selectYearEmployList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
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
	public Map<String, Object> selectYearEmployDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
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
	public int insertYearEmploy(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
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
	public int deleteYearEmploy(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
}
