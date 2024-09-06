package kr.go.rastech.ptl.execute.reg.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.apply.req.dao.ReqDao;
import kr.go.rastech.ptl.execute.reg.dao.RegDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class RegServiceImpl extends BaseServiceImpl implements RegService {

	@Resource
	private RegDao regDao;

	@Resource
	private ReqDao reqDao;
	
	/**
	 * <pre>
	 * 협약과제 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectProjRegListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return regDao.selectProjRegListCount(param);
	}

	/**
	 * <pre>
	 * 협약과제 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectProjRegList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return regDao.selectProjRegList(param);
	}

	
	/**
	 * <pre>
	 * 협약과제 상세 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectProjRegDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return regDao.selectProjRegDtl(param);
	}

	
	/**
	 * <pre>
	 * 연차과제  등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertProjYear(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return regDao.insertProjYear(param);
	}

	
	/**
	 * <pre>
	 * 연차과제 생성시 생성 연차 확인
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int chkYear(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return regDao.chkYear(param);
	}
	
	
	
	
	
}
