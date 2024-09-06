package kr.go.rastech.ptl.content.popup.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;
import kr.go.rastech.ptl.content.popup.dao.PopMngDao;



/**
 * <pre>
 * FileName: PopMngServiceImpl.java
 * Package :  kr.go.rastech.ptl.mng.pop.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
@Service
public class PopMngServiceImpl extends BaseServiceImpl implements PopMngService {

	@Resource
	private PopMngDao popDao;
	private HashMap<String, Object> hashMap = new HashMap<String, Object>();
	
	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param popMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectPopTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return popDao.selectPopTotalCount(param);
	}

	/**
	 * <pre>
	 * 게시물 전체 조회
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param popMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return popDao.selectPopList(param);
	}
	
	/**
	 * <pre>
	 * 메인용 팝업 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2019. 12. 11.
	 * @param popMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectMainPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return popDao.selectMainPopList(param);
	}


	@Override
	public Map<String, Object> selectPopDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return popDao.selectPopDtl(param);
	}

	@Override
	public int insertPopMng(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		int seq = popDao.selectPopSeq();
		param.put("pop_seq", seq);
		popDao.insertPopMng(param);
		return seq;
	}

	@Override
	public void updatePopMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		popDao.updatePopMng(param);
	}


	@Override
	public void deletePop(Map<String, Object> param) throws IOException, 	SQLException, NullPointerException {
		
 
		popDao.deletePop(param);    	
		
		
		
	}
	
}
