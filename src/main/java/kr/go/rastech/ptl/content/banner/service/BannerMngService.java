package kr.go.rastech.ptl.content.banner.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.commons.imgFile.vo.ImgFileVo;


/**
 * <pre>
 * FileName: BannerMngService.java
 * Package : kr.go.rastech.ptl.mng.pop.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
public interface BannerMngService {
	
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
	public int selectBannerTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 게시물 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectBannerList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	/**
	 * <pre>
	 * 게시물 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectMainBannerList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	/**
	  * @Method Name : selectBannerDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectBannerDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	public int insertBannerMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	public void updateBannerMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	public void updateBannerImgFile(Map<String, Object> param ,ImgFileVo imgFileVo) throws IOException, SQLException , NullPointerException;
	
	
	public void deleteBanner(Map<String, Object> param ,ImgFileVo imgFileVo) throws IOException, SQLException , NullPointerException;
}
