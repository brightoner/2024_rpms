package kr.go.rastech.ptl.execute.wbs.dao;


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
public interface WbsDao {

	/**
	 * <pre>
	 * wbd 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectWbsList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	
	public List<Map<String, Object>> selectWbsProgressRateList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	public List<Map<String, Object>> selectWbsCalendarList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	
	public Map<String, Object> selectWbsInfo(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	
	public List<Map<String, Object>> selectWbsScheduleList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	
	
	public void insertWbsItem(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;

	public void insertWbsSchedule(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	public void updateWbsItem(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	
	public void deleteWbsItem(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	public void deleteWbsSchedule(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	public void deleteAllWbsSchedule(Map<String, Object> param) throws IOException, SQLException , NullPointerException ;
	
	
	public void insertWbsBaseInfo(Map<String, Object> params) throws IOException, SQLException , NullPointerException ;
	
	
}
