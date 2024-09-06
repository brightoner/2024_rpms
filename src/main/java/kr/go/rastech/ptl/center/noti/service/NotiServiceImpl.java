package kr.go.rastech.ptl.center.noti.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.center.noti.dao.NotiDao;



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
public class NotiServiceImpl extends BaseServiceImpl implements NotiService {

	@Resource
	private NotiDao notiDao;

	

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
		
		return notiDao.selectNotiTotalCount(param);
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

		return notiDao.selectNotiList(param);
	}
	

	@Override
	public Map<String, Object> selectNotiDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return notiDao.selectNotiDtl(param);
	}

	@Override
	public void updateNotiViewCnt(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		notiDao.updateNotiViewCnt(param);
	}

}
