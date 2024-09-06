package kr.go.rastech.ptl.opsmng.respMng.service;



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
public interface RespMngService {
	
	/**
	 * <pre>
	 * bj승인관리 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectRespMngTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * bj승인관리 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectRespMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * bj승인관리 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectRespMngCodeList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * bj승인관리 상세 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectRespMngDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


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
	public int updateRespMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException , NullPointerException;
	

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
	public int insertRespMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException , NullPointerException;
	
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
	public int deleteRespMng(Map<String, Object> param, UserVo userVo) throws IOException, SQLException , NullPointerException;
	

	
}
