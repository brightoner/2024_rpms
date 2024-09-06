package kr.go.rastech.ptl.opsmng.tclsMng.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.opsmng.tclsMng.vo.TclsMngVo;

/**
 *
 * <pre>
 * FileName: TclsMngDao.java
 *
 * mng TclsMng 관리 Dao
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 11.
 */
@Repository
public interface TclsMngDao {

	/**
	 * <pre>
	 * 산업기술분류 - 메뉴조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param mngTclsMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<TclsMngVo> readTclsMngList(TclsMngVo mngTclsMngVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 산업기술분류 - 메뉴등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 12.
	 * @param mngTclsMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertTclsMng(TclsMngVo mngTclsMngVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 산업기술분류 - 메뉴수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 12.
	 * @param mngTclsMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateTclsMng(TclsMngVo mngTclsMngVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 산업기술분류 - 메뉴삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngTclsMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteTclsMng(TclsMngVo mngTclsMngVo) throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 산업기술분류 - 메뉴권한조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 17.
	 * @param menu_id
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<TclsMngVo> readTclsMngRolList(String menu_id) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 산업기술분류 - 하위메뉴사용유무수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 25.
	 * @param mngTclsMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateTclsMngDtl(TclsMngVo tclsMngVo) throws IOException, SQLException , NullPointerException;

	
	

	/**
	 * <pre>
	 * 산업기술분류 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectTclsMngTotalPopCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 산업기술분류 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectTclsMngPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
}
