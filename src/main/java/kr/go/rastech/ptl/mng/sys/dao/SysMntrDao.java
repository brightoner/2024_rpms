package kr.go.rastech.ptl.mng.sys.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.mng.sys.vo.SysMntrVo;

/**
 * <pre>
 * FileName: SysDao.java
 * Package : kr.go.ncmiklib.ptl.mng.sys.dao
 * 
 * 시스템 관리 DAO
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 22.
 */
@Repository
public interface SysMntrDao {

	/**
	 * <pre>
	 * 모니터링 저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 23.
	 * @param sysVo
	 */
	void insertSysMntr(Map<String, Object> map) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 모니터링 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 23.
	 * @param sysVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	List<Map<String,Object>> selectSysMntrList(SysMntrVo sysVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 모니터링 수정 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 24.
	 * @param sysMntrVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	void updateSysMntr(SysMntrVo sysMntrVo)throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 *  스켸쥴러 조회 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 6. 7.
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	List<SysMntrVo> selectListSch(SysMntrVo sysVo)throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 6. 7.
	 * @param sysMntrVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	void saveSch(SysMntrVo sysMntrVo)throws IOException, SQLException , NullPointerException;
		
}
