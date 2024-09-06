package kr.go.rastech.ptl.opsmng.salesMng.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 *매출관리  dao
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Repository
public interface SalesMngDao {

	/**
	 * <pre>
	 * 매출관리 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectSaleMngListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 매출관리 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectSaleMngList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	/**
	 * <pre>
	 * 매출관리 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectSaleMngDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
	/**
	 * <pre>
	 * 매출관리 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertSaleMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 매출관리 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int deleteSaleMng(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
}
