package kr.go.rastech.ptl.follow.year.planInfo.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 
 *연차사후관리 기본정보  dao
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Repository
public interface PlanInfoDao {

	/**
	 * <pre>
	 * 연차사후관리 기본정보 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectPlanInfoListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 연차사후관리 기본정보 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectPlanInfoList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 기본정보 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectPlanInfoDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
	
	
	
	
}
