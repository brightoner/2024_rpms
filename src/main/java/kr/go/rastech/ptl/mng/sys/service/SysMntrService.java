package kr.go.rastech.ptl.mng.sys.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.ptl.mng.sys.vo.SysMntrVo;





/**
 * <pre>
 * FileName: sysService.java
 * Package : kr.go.ncmiklib.ptl.mng.sys.service
 * 
 * 시스템관리 서비스 구현
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 22.
 */
public interface SysMntrService {
	
	/**
	 * <pre>
	 * 모니터링 등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 23.
	 * @param sysVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertSysMntr(Map<String, Object> map) throws IOException, SQLException , NullPointerException;

	
	/**
	 * <pre>
	 * 모니터링 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 23.
	 * @param sysVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<Map<String,Object>> selectSysMntrList(SysMntrVo sysMntrVo)throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 모니터링 정보 수정 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 24.
	 * @param sysMntrVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateSysMntr(SysMntrVo sysMntrVo)throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 *  스켸줄러 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 6. 7.
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<SysMntrVo> selectListSch(SysMntrVo sysVo) throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 스켸쥴시간 저장
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 6. 7.
	 * @param sysMntrVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void saveSch(SysMntrVo sysMntrVo)throws IOException, SQLException , NullPointerException;
	
}
