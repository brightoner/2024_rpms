package kr.go.rastech.ptl.opsmng.qna.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.imgFile.service.ImgFileService;
import kr.go.rastech.ptl.opsmng.qna.dao.QnaMngDao;



/**
 * <pre>
 * FileName: QnaMngServiceImpl.java
 * 
 *
 * </pre>
 * @author : lwk
 * @date   :2023. 04. 24.
 */
@Service
public class QnaMngServiceImpl extends BaseServiceImpl implements QnaMngService {

	@Resource
	private QnaMngDao qnaMngDao;
	private HashMap<String, Object> hashMap = new HashMap<String, Object>();
	
	  /** ImgFileService */
    @Resource
    private ImgFileService imgFileService;

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
	public int selectQnaTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return qnaMngDao.selectQnaTotalCount(param);
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
	public List<Map<String,Object>> selectQnaList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return qnaMngDao.selectQnaList(param);
	}
	
	

	@Override
	public Map<String, Object> selectQnaDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return qnaMngDao.selectQnaDtl(param);
	}


	@Override
	public void updateQnaMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		qnaMngDao.updateQnaMng(param);
	}
	

	@Override
	public void deleteQna(Map<String, Object> param) throws IOException, 	SQLException, NullPointerException {
		
		// 먼저 베너를 DELETE 한후   해당 첨부파일의 USE_YN을 N으로 셋팅하여 준다.
		qnaMngDao.deleteQna(param);    	
		
		
	}
}
