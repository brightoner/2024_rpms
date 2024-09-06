package kr.go.rastech.ptl.apply.req.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 
 * 
 *
 * </pre>
 * @author : ljk
 * @date   : 2023. 06. 02.
 */
public interface ReqService {
	
	
	
	/**
	 * <pre>
	 * 연구과제 신청 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectReqListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	/**
	 * <pre>
	 * 연구과제 신청 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectReqList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	

	/**
	 * <pre>
	 * 연구과제 신청 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertReq(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 연구과제 신청 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectReqDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 연구과제 신청 상세보기 시 참여기관 select
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectPartOrg(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 연구과제 신청 상세보기 시 책임자 select
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectRespPerson(Map<String, Object> param)throws IOException, SQLException , NullPointerException;	
	
	
	/**
	 * <pre>
	 * 연구과제 신청 상세보기 시 총괄책임자 select
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectLRespPerson(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 연구과제 신청 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int updateReq(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 연구과제 신청 삭제시 선정단계인지 체크 - SEL_GB : 0 만 삭제 가능 
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int chkSelGb(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	
	
	/**
	 * <pre>
	 * 연구과제 신청 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int deleteReq(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
}
