package kr.go.rastech.ptl.center.faq.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.center.faq.dao.FaqDao;



/**
 * <pre>
 * FileName: FaqMngServiceImpl.java
 * 
 *
 * </pre>
 * @author : lwk
 * @date   :2023. 04. 24.
 */
@Service	
public class FaqServiceImpl extends BaseServiceImpl implements FaqService {

	@Resource
	private FaqDao faqDao;
	

	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectFaqTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return faqDao.selectFaqTotalCount(param);
	}

	/**
	 * <pre>
	 * 게시물 전체 조회
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectFaqList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return faqDao.selectFaqList(param);
	}
	


	@Override
	public Map<String, Object> selectFaqDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return faqDao.selectFaqDtl(param);
	}

}
