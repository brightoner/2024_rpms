package kr.go.rastech.ptl.opsmng.userMgmt.service;



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
 * @author : ljk
 * @date   : 2023. 06. 02.
 */
public interface UserMgmtService {
	
	
	
	/**
	 * <pre>
	 * 사용자 목록 전체 조회
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectUserMgmtList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 사용자 목록 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectUserMgmtTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 사용자 목록 메일 발송 처리
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectUserSendMail(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 사용자 목록 상세 조회
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectUserMgmtDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	
}
