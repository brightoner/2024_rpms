package kr.go.rastech.ptl.apply.sel.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.apply.sel.dao.SelDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class SelServiceImpl extends BaseServiceImpl implements SelService {

	@Resource
	private SelDao selDao;

	
	/**
	 * <pre>
	 * 선정결과 처리 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectSelListCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return selDao.selectSelListCount(param);
	}

	/**
	 * <pre>
	 * 선정결과 처리 전체 조회
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectSelList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return selDao.selectSelList(param);
	}
	
	
	
	/**
	 * <pre>
	 * 선정결과 처리 수정
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateSel(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		
		int result = 0;
		
		// 연구과제신청 정보 수정
		result = selDao.updateSel(param);
		
		return result;
	}

	
	
	
}
