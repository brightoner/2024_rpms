package kr.go.rastech.ptl.content.popup.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * FileName: PopMngDao.java
 * Package : kr.go.rastech.ptl.mng.pop.dao;
 * 
 * 팝업관리 dao
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
@Repository
public interface PopMngDao {

	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectPopTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	/**
	 * <pre>
	 * 게시물 전체 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2019. 12. 11.
	 * @param popMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectPopList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	/**
	 * <pre>
	 * 메인용 전체 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2019. 12. 11.
	 * @param popMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectMainPopList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;

	/**
	  * @Method Name : selectPopDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectPopDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : selectPopSeq
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public int selectPopSeq() throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : insertPopMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void insertPopMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : updatePopMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void updatePopMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	public void deletePop(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
}
