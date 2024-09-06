package kr.go.rastech.ptl.opsmng.qna.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * FileName: QnaMngDao.java
 * 
 * 공지사항관리 dao
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 04. 24.
 */
@Repository
public interface QnaMngDao {

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
	public int selectQnaTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
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
	public List<Map<String, Object>> selectQnaList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
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
	public List<Map<String, Object>> selectMainQnaList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;

	/**
	  * @Method Name : selectQnaDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectQnaDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	

	/**
	  * @Method Name : updateQnaMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void updateQnaMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	

	
	public void deleteQna(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
}
