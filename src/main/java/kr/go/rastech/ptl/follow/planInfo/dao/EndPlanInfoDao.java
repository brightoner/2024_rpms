package kr.go.rastech.ptl.follow.planInfo.dao;


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
public interface EndPlanInfoDao {
	
	/**
	 * <pre>
	 * 종료된 협약과제 list count (종료사후과제의 대상 목록 list count)
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectEndTargetListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 종료된 협약과제 list (종료사후과제의 대상 목록 list)
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectEndTargetList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 *종료과제 유무 확인 
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int chkEndPlan(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
	
	/**
	 * <pre>
	 * 종료과제 생성
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertEndPlan(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	

	/**
	 * <pre>
	 * 종료사후관리 기본정보 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectEndPlanInfoListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectEndPlanInfoList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectEndPlanInfoDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
	
	
	
	
}
