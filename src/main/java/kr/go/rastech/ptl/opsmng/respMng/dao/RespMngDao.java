package kr.go.rastech.ptl.opsmng.respMng.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 * 책임자dao
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
@Repository
public interface RespMngDao {

	/**
	 * <pre>
	 * 책임자전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectRespMngTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	/**
	 * <pre>
	 * 책임자전체 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectRespMngList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	/**
	 * <pre>
	 * 책임자전체 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectRespMngCodeList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	

	/**
	 * <pre>
	 * 책임자상세 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectRespMngDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 책임자관리 update
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int updateRespMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 책임자관리 insert
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertRespMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	

	/**
	 * <pre>
	 * 책임자관리 delete
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int deleteRespMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	
}
