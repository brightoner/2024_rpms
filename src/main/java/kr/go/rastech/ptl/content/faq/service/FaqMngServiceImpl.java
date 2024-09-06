package kr.go.rastech.ptl.content.faq.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.content.faq.dao.FaqMngDao;



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
public class FaqMngServiceImpl extends BaseServiceImpl implements FaqMngService {

	@Resource
	private FaqMngDao faqMngDao;
	

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
		
		return faqMngDao.selectFaqTotalCount(param);
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

		return faqMngDao.selectFaqList(param);
	}
	
	/**
	 * <pre>
	 * 메인용 팝업 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectMainFaqList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return faqMngDao.selectMainFaqList(param);
	}


	@Override
	public Map<String, Object> selectFaqDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return faqMngDao.selectFaqDtl(param);
	}

	@Override
	public int insertFaqMng(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		int seq = faqMngDao.selectFaqSeq();
		param.put("faq_id",seq);
		faqMngDao.insertFaqMng(param);
		return seq;
	}

	@Override
	public void updateFaqMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		faqMngDao.updateFaqMng(param);
	}
	
	@Override
	public void deleteFaq(Map<String, Object> param) throws IOException, 	SQLException, NullPointerException {
		
		// 먼저 베너를 DELETE 한후   해당 첨부파일의 USE_YN을 N으로 셋팅하여 준다.
		faqMngDao.deleteFaq(param);    	
		
		
	}
}
