package kr.go.rastech.ptl.center.noti.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.commons.imgFile.vo.ImgFileVo;


/**
 * <pre>
 * FileName: NotiMngService.java
 * Package : kr.go.rastech.ptl.mng.pop.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 04. 24.
 */
public interface NotiService {
	
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
	public int selectNotiTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
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
	public List<Map<String, Object>> selectNotiList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	  * @Method Name : selectNotiDtl
	  * @작성일 : 2023. 04. 24.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public Map<String, Object> selectNotiDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	
	/**
	  * @Method Name : updateNotiViewCnt
	  * @작성일 : 2023. 04. 24.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public void updateNotiViewCnt(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


}
