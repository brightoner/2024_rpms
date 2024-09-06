package kr.go.rastech.ptl.follow.end.article.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.end.article.dao.EndArticleDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EndArticleServiceImpl extends BaseServiceImpl implements EndArticleService {

	@Resource
	private EndArticleDao endArticleDao;

	
	/**
	 * <pre>
	 * 최종사후관리 논문 리스트 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndArticleListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endArticleDao.selectEndArticleListCount(param);
	}

	/**
	 * <pre>
	 * 최종사후관리 논문 리스트 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndArticleList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endArticleDao.selectEndArticleList(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 논문 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectEndArticleDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endArticleDao.selectEndArticleDtl(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 논문 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertEndArticle(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endArticleDao.insertEndArticle(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 논문 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteEndArticle(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endArticleDao.deleteEndArticle(param);
	}
	
	
	
}
