package kr.go.rastech.ptl.content.noti.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.content.noti.dao.NotiMngDao;



/**
 * <pre>
 * FileName: NotiMngServiceImpl.java
 * 
 *
 * </pre>
 * @author : lwk
 * @date   :2023. 04. 24.
 */
@Service
public class NotiMngServiceImpl extends BaseServiceImpl implements NotiMngService {

	@Resource
	private NotiMngDao notiMngDao;
	

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
	public int selectNotiTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return notiMngDao.selectNotiTotalCount(param);
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
	public List<Map<String,Object>> selectNotiList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return notiMngDao.selectNotiList(param);
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
	public List<Map<String,Object>> selectMainNotiList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return notiMngDao.selectMainNotiList(param);
	}


	@Override
	public Map<String, Object> selectNotiDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return notiMngDao.selectNotiDtl(param);
	}

	@Override
	public int insertNotiMng(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		int seq = notiMngDao.selectNotiSeq();
		param.put("noti_id",seq);
		notiMngDao.insertNotiMng(param);
		return seq;
	}

	@Override
	public void updateNotiMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		notiMngDao.updateNotiMng(param);
	}
	
	@Override
	public void deleteNoti(Map<String, Object> param) throws IOException, 	SQLException, NullPointerException {
		
		// 먼저 베너를 DELETE 한후   해당 첨부파일의 USE_YN을 N으로 셋팅하여 준다.
		notiMngDao.deleteNoti(param);    	
		
		
	}
}
