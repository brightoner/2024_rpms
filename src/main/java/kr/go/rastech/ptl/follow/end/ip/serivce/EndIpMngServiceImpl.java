package kr.go.rastech.ptl.follow.end.ip.serivce;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.end.ip.dao.EndIpMngDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class EndIpMngServiceImpl extends BaseServiceImpl implements EndIpMngService {

	@Resource
	private EndIpMngDao endIpMngDao;

	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectEndIpListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endIpMngDao.selectEndIpListCount(param);
	}

	/**
	 * <pre>
	 * 최종사후관리 지적재산권 list
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectEndIpList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endIpMngDao.selectEndIpList(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권 상세보기
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectEndIpDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endIpMngDao.selectEndIpDtl(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertEndIp(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endIpMngDao.insertEndIp(param);
	}

	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteEndIp(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return endIpMngDao.deleteEndIp(param);
	}
	
	
	
}
