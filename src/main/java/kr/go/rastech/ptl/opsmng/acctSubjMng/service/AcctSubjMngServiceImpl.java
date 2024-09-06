package kr.go.rastech.ptl.opsmng.acctSubjMng.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.opsmng.acctSubjMng.dao.AcctSubjMngDao;
import kr.go.rastech.ptl.opsmng.acctSubjMng.vo.AcctSubjMngVo;


/**
 * <pre>
 * FileName: AcctSubjMngServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.service
 * 
 * mng AcctSubjMng 관리 ServiceImpl
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 16.
 */
@Repository
public class AcctSubjMngServiceImpl extends BaseServiceImpl implements AcctSubjMngService {


	@Resource
	private AcctSubjMngDao acctSubjMngDao;
	
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param mngAcctSubjMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<AcctSubjMngVo> readAcctSubjMngList(AcctSubjMngVo mngAcctSubjMngVo)  throws IOException, SQLException , NullPointerException{
		return acctSubjMngDao.readAcctSubjMngList(mngAcctSubjMngVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.AcctSubjMngService#insertAcctSubjMng(kr.go.ncmiklib.ptl.mng.menu.vo.AcctSubjMngVo)
	 */
	@Override
	public void insertAcctSubjMng(AcctSubjMngVo mngAcctSubjMngVo) throws IOException, SQLException , NullPointerException {
		acctSubjMngDao.insertAcctSubjMng(mngAcctSubjMngVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.AcctSubjMngService#updateAcctSubjMng(kr.go.ncmiklib.ptl.mng.menu.vo.AcctSubjMngVo)
	 */
	@Override
	public void updateAcctSubjMng(AcctSubjMngVo mngAcctSubjMngVo) throws IOException, SQLException , NullPointerException {
		acctSubjMngDao.updateAcctSubjMng(mngAcctSubjMngVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.AcctSubjMngService#deleteAcctSubjMng(kr.go.ncmiklib.ptl.mng.menu.vo.AcctSubjMngVo)
	 */
	@Override
	public void deleteAcctSubjMng(AcctSubjMngVo acctSubjMngVo) throws IOException, SQLException , NullPointerException {
	
		acctSubjMngDao.deleteAcctSubjMng(acctSubjMngVo);
	}





	@Override
	public void updateAcctSubjMngDtl(AcctSubjMngVo acctSubjMngVo) throws IOException, SQLException , NullPointerException {
		acctSubjMngDao.updateAcctSubjMngDtl(acctSubjMngVo);
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
	public int selectAcctSubjMngTotalPopCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return acctSubjMngDao.selectAcctSubjMngTotalPopCount(param);
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
	public List<Map<String,Object>> selectAcctSubjMngPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return acctSubjMngDao.selectAcctSubjMngPopList(param);
	}
}
