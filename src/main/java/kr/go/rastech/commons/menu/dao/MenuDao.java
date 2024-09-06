/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.menu.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.menu.vo.MenuVo;

/**
 * <pre>
 * FileName: MenuDao.java
 * Package : kr.go.ncmiklib.commons.menu.dao
 *
 * 사용자 메뉴 목록 DAO.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 10. 2.
 */
@Repository
public interface MenuDao {
	/**
	 *
	 * <pre>
	 * Top 메뉴 목록 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 2.
	 * @param userVo
	 * @return
	 */
	public List<MenuVo> selectTopMenuList(UserVo userVo);

	/**
	 *
	 * <pre>
	 * Left 메뉴 목록 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 2.
	 * @param userVo
	 * @return
	 */
	public List<MenuVo> selectLeftMenuList(UserVo userVo);
	
	/**
	 *
	 * <pre>
	 * 사용자 팝업 메뉴 목록 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 2.
	 * @param userVo
	 * @return
	 */
	public List<MenuVo> selectPopMenuList(UserVo userVo);

	/**
	 *
	 * <pre>
	 * 전체 URL 목록 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 6.
	 * @return
	 */
	public List<MenuVo> selectUrlList();

	/**
	  * @Method Name : selectPrtsId
	  * @작성일 : 2023. 6. 29.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  */
	public String selectPrtsId(Map<String, String> param);

	public List<MenuVo> selectLv3Menu(UserVo userVo);
}
