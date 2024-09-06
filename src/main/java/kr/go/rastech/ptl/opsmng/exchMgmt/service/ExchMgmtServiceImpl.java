package kr.go.rastech.ptl.opsmng.exchMgmt.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.imgFile.service.ImgFileService;
import kr.go.rastech.ptl.opsmng.exchMgmt.dao.ExchMgmtDao;



/**
 * <pre>
 * FileName: ChMngServiceImpl.java
 * Package :  kr.go.rastech.ptl.mng.banner.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
@Service
public class ExchMgmtServiceImpl extends BaseServiceImpl implements ExchMgmtService {

	@Resource
	private ExchMgmtDao exchMgmtDao;

	  /** ImgFileService */
    @Resource
    private ImgFileService imgFileService;





	@Override
	public Map<String, Object> selectExchBaseInfoDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return exchMgmtDao.selectExchBaseInfoDtl(param);
	}

	
	/**
	 * <pre>
	 * 환전 이력 목록
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectExchInfoList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return exchMgmtDao.selectExchInfoList(param);
	}



	/**
	 * <pre>
	 *  환전 이력 total cnt
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectExchInfoCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return exchMgmtDao.selectExchInfoCount(param);
	}
	
	/**
	 * <pre>
	 * item  현황
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectUserExchSituationData(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return exchMgmtDao.selectUserExchSituationData(param);
	}

	
	
	@Override
	public int updateExchState(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return exchMgmtDao.updateExchState(param);
	}
	

	
}
