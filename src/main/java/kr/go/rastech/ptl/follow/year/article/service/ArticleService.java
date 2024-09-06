package kr.go.rastech.ptl.follow.year.article.service;



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
public interface ArticleService {
	
	/**
	 * <pre>
	 * 연차사후관리 논문 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectYearArticleListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 연차사후관리 논문 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectYearArticleList(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 논문 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectYearArticleDtl(Map<String, Object> param)  throws IOException, SQLException , NullPointerException;

	
	/**
	 * <pre>
	 * 연차사후관리 논문 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int insertYearArticle(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 연차사후관리 논문 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int deleteYearArticle(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
}
