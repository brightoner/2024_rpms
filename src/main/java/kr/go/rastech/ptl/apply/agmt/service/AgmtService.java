package kr.go.rastech.ptl.apply.agmt.service;



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
public interface AgmtService {
	
	
	
	/**
	 * <pre>
	 * 과제협약 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectAgmtListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 과제협약 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectAgmtList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	
}
