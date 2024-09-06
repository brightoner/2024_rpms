package kr.go.rastech.ptl.follow.year.article.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.year.article.dao.ArticleDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl implements ArticleService {

	@Resource
	private ArticleDao articleDao;

	
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
	@Override
	public int selectYearArticleListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return articleDao.selectYearArticleListCount(param);
	}

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
	@Override
	public List<Map<String, Object>> selectYearArticleList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return articleDao.selectYearArticleList(param);
	}

	
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
	@Override
	public Map<String, Object> selectYearArticleDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return articleDao.selectYearArticleDtl(param);
	}

	
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
	@Override
	public int insertYearArticle(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return articleDao.insertYearArticle(param);
	}

	
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
	@Override
	public int deleteYearArticle(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return articleDao.deleteYearArticle(param);
	}
	
	
	
}
