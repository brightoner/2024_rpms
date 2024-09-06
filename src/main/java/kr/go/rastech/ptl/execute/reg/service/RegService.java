package kr.go.rastech.ptl.execute.reg.service;



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
public interface RegService {
	
	
	
	/**
	 * <pre>
	 * 협약과제 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectProjRegListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 협약과제 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectProjRegList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 협약과제 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectProjRegDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
	/**
	 * <pre>
	 * 연차과제  등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertProjYear(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 연차과제 생성시 생성 연차 확인
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int chkYear(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
}
