package kr.go.rastech.ptl.opsmng.chatBlackList.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * FileName: ChNotiMngMngService.java
 * Package : kr.go.rastech.ptl.mng.pop.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 04. 24.
 */
public interface ChatBlackListService {
	
	/**
	 * <pre>
	 * 블랙리스트 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectChatBlackListTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;

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
	public List<Map<String, Object>> selectChatBlackList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;

	/**
	 * <pre>
	 * 방송 설정용 블랙리스트 전체  count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectMainChatBlackListTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;

	/**
	 * <pre>
	 * 방송설정용 블랙리스트 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectMainChatBlackList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;

	
	/**
	 * <pre>
	 * 블랙리스트 등록
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertChatBlackList(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 블랙리스트 수정
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int updateChatBlackList(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	/**
	 * <pre>
	 * 블랙리스트 삭제
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public void deleteChatBlackList(String param ) throws IOException, SQLException , NullPointerException;
	
	

	/**
	 * <pre>
	 * 블랙리스트 포함 여부 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectChatBlackListChkYn(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	
	/**
	 * <pre>
	 * 블랙리스트 중복체크
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectChatBlackListDupleUserChk(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
}
