package kr.go.rastech.ptl.opsmng.userMgmt.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 * bj승인신청 dao
 *
 * </pre>
 * @author : ljk
 * @date   : 2023. 06. 02.
 */
@Repository
public interface UserMgmtDao {

	/**
	 * <pre>
	 * bj승인신청 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectUserMgmtTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	/**
	 * <pre>
	 * bj승인신청 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectUserMgmtList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	

	/**
	 * <pre>
	 * 사용자 목록 메일 대상 조회
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectUserSendMailList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * bj승인신청 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectUserMgmtDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;


	
}
