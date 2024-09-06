package kr.go.rastech.ptl.execute.bgMake.dao;


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
public interface BgMakeDao {

	/**
	 * <pre>
	 * 예산 편성 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectProjBgMakeList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	
	public List<Map<String, Object>> selectProjAccountSubjectList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;
	
	public void insertProjAcctSubj(Map<String, Object> params) throws IOException, SQLException , NullPointerException ;

	public void updateProjBudget(Map<String, Object> params) throws IOException, SQLException , NullPointerException ;
	
	public void deleteProjBudget(Map<String, Object> params) throws IOException, SQLException , NullPointerException ;
	
	
	public void updateProjBudgetExec(Map<String, Object> params) throws IOException, SQLException , NullPointerException ;
	
}
