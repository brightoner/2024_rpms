package kr.go.rastech.ptl.opsmng.tclsMng.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.opsmng.tclsMng.dao.TclsMngDao;
import kr.go.rastech.ptl.opsmng.tclsMng.vo.TclsMngVo;


/**
 * <pre>
 * FileName: TclsMngServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.service
 * 
 * mng TclsMng 관리 ServiceImpl
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 16.
 */
@Repository
public class TclsMngServiceImpl extends BaseServiceImpl implements TclsMngService {


	@Resource
	private TclsMngDao tclsMngDao;
	
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param mngTclsMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<TclsMngVo> readTclsMngList(TclsMngVo mngTclsMngVo)  throws IOException, SQLException , NullPointerException{
		return tclsMngDao.readTclsMngList(mngTclsMngVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.TclsMngService#insertTclsMng(kr.go.ncmiklib.ptl.mng.menu.vo.TclsMngVo)
	 */
	@Override
	public void insertTclsMng(TclsMngVo mngTclsMngVo) throws IOException, SQLException , NullPointerException {
		tclsMngDao.insertTclsMng(mngTclsMngVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.TclsMngService#updateTclsMng(kr.go.ncmiklib.ptl.mng.menu.vo.TclsMngVo)
	 */
	@Override
	public void updateTclsMng(TclsMngVo mngTclsMngVo) throws IOException, SQLException , NullPointerException {
		tclsMngDao.updateTclsMng(mngTclsMngVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.TclsMngService#deleteTclsMng(kr.go.ncmiklib.ptl.mng.menu.vo.TclsMngVo)
	 */
	@Override
	public void deleteTclsMng(TclsMngVo tclsMngVo) throws IOException, SQLException , NullPointerException {
	
		tclsMngDao.deleteTclsMng(tclsMngVo);
	}





	@Override
	public void updateTclsMngDtl(TclsMngVo tclsMngVo) throws IOException, SQLException , NullPointerException {
		tclsMngDao.updateTclsMngDtl(tclsMngVo);
	}
	
	
	
	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectTclsMngTotalPopCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return tclsMngDao.selectTclsMngTotalPopCount(param);
	}

	/**
	 * <pre>
	 * bj승인관리 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectTclsMngPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return tclsMngDao.selectTclsMngPopList(param);
	}
}
