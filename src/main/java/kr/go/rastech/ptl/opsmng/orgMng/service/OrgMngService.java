package kr.go.rastech.ptl.opsmng.orgMng.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.commons.login.vo.UserVo;


/**
 * <pre>
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
public interface OrgMngService {
	
	/**
	 * <pre>
	 * 기관 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectOrgMngTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 기관 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectOrgMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 기관 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectOrgMngCodeList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 기관 상세 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectOrgMngDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 기관관리 update
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int updateOrgMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException , NullPointerException;
	

	/**
	 * <pre>
	 * 기관관리 update
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertOrgMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 기관관리 update
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int deleteOrgMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException , NullPointerException;
	

	
}
