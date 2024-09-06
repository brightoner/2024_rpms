package kr.go.rastech.ptl.execute.rpMng.service;



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
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
public interface RpMngService {
	
 
	public List<Map<String, Object>> selectProjRpMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	public List<Map<String, Object>> selectExcelProjRpMngList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;

	public Map<String, Object> selectChkProjRpRate(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;

	
	public void insertProjRpMng(Map<String, Object> params) throws IOException, SQLException , NullPointerException ;
}
