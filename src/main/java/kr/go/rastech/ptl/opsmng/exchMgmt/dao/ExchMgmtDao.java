package kr.go.rastech.ptl.opsmng.exchMgmt.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * FileName: ExchMgmtInfoDao.java
 * Package : kr.go.rastech.ptl.usermng.exchMgmt.dao
 * 
 * 환전관리 dao
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 12. 11.
 */
@Repository
public interface ExchMgmtDao {

	
	
	
	/**
	  * @Method Name : selectChDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectExchBaseInfoDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 환전 이력 목록
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectExchInfoList(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	

	/**
	 * <pre>
	 * 환전 이력 total cnt
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectExchInfoCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	

	/**
	 * <pre>
	 * 환전 신청 유저 item 현황
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectUserExchSituationData(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	/**
	  * @Method Name : updateChMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public int updateExchState(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
}
