package kr.go.rastech.ptl.content.banner.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * FileName: BannerMngDao.java
 * Package : kr.go.rastech.ptl.mng.pop.dao;
 * 
 * 팝업관리 dao
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
@Repository
public interface BannerMngDao {

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
	public int selectBannerTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
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
	public List<Map<String, Object>> selectBannerList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
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
	public List<Map<String, Object>> selectMainBannerList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;

	/**
	  * @Method Name : selectBannerDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectBannerDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : selectBannerSeq
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public int selectBannerSeq() throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : insertBannerMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void insertBannerMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : updateBannerMng
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void updateBannerMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	public void updateBannerImgFile(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	public void deleteBanner(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
}
