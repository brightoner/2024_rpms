package kr.go.rastech.ptl.mng.menu.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.mng.menu.vo.MngMenuRolVo;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuVo;

/**
 *
 * <pre>
 * FileName: MngMenuDao.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.dao
 *
 * mng Menu 관리 Dao
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 11.
 */
@Repository
public interface MngMenuDao {

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param mngMenuVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngMenuVo> readMenuList(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 12.
	 * @param mngMenuVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertMenu(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 12.
	 * @param mngMenuVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateMenu(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngMenuVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteMenu(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴권한삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngMenuVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public int deleteMenuRol(MngMenuRolVo mngMenuRolVo)throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴권한등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngMenuVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertMenuRol(MngMenuRolVo mngMenuRolVo)throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 메뉴관리 - 메뉴권한등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngMenuVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void mergeMenuRol(MngMenuRolVo mngMenuRolVo)throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴권한조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 17.
	 * @param menu_id
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngMenuVo> readMenuRolList(String menu_id) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 메뉴관리 - 하위메뉴사용유무수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 25.
	 * @param mngMenuVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateMenuDtl(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException;

}
