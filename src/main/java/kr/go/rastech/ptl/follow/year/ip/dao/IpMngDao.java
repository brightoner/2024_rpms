package kr.go.rastech.ptl.follow.year.ip.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 *연차사후관리 지적재산권  dao
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Repository
public interface IpMngDao {

	/**
	 * <pre>
	 * 연차사후관리 지적재산권 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectYearIpListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 list
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectYearIpList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 상세보기
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectYearIpDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertYearIp(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int deleteYearIp(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
}
