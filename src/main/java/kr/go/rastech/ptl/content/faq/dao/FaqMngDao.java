package kr.go.rastech.ptl.content.faq.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * FileName: FaqMngDao.java
 * 
 * 공지사항관리 dao
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 04. 24.
 */
@Repository
public interface FaqMngDao {

	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectFaqTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	/**
	 * <pre>
	 * 게시물 전체 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectFaqList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	/**
	 * <pre>
	 * 메인용 전체 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectMainFaqList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;

	/**
	  * @Method Name : selectFaqDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectFaqDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : selectFaqSeq
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public int selectFaqSeq() throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : insertFaqMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void insertFaqMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : updateFaqMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void updateFaqMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	public void deleteFaq(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
}
