package kr.go.rastech.ptl.content.popup.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.commons.imgFile.vo.ImgFileVo;


/**
 * <pre>
 * FileName: PopMngService.java
 * Package : kr.go.rastech.ptl.mng.pop.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
public interface PopMngService {
	
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
	public int selectPopTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
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
	public List<Map<String, Object>> selectPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
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
	public List<Map<String, Object>> selectMainPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	/**
	  * @Method Name : selectPopDtl
	  * @작성일 : 2023. 7. 13.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectPopDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	public int insertPopMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	public void updatePopMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	public void deletePop(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
}
