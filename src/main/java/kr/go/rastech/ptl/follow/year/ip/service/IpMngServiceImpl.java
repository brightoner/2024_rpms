package kr.go.rastech.ptl.follow.year.ip.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.follow.year.ip.dao.IpMngDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class IpMngServiceImpl extends BaseServiceImpl implements IpMngService {

	@Resource
	private IpMngDao ipMngDao;

	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectYearIpListCount(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return ipMngDao.selectYearIpListCount(param);
	}

	/**
	 * <pre>
	 * 연차사후관리 지적재산권 list
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String, Object>> selectYearIpList(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return ipMngDao.selectYearIpList(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 상세보기
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectYearIpDtl(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return ipMngDao.selectYearIpDtl(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 등록
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertYearIp(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return ipMngDao.insertYearIp(param);
	}

	
	/**
	 * <pre>
	 * 연차사후관리 지적재산권 삭제
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int deleteYearIp(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return ipMngDao.deleteYearIp(param);
	}
	
	
	
}
