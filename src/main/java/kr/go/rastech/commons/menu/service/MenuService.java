/******************************************************************
 * Copyright RASTECH 2015
 ******************************************************************/
package kr.go.rastech.commons.menu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.menu.vo.MenuVo;

/**
 * <pre>
 * FileName: MenuService.java
 * Package : kr.go.ncmik.commons.menu.service
 *
 * 사용자 메뉴 Service interface.
 *
 * </pre>
 * @author : rastech
 * @date   : 2015. 10. 2.
 */
public interface MenuService {
	/**
	 *
	 * <pre>
	 * Top 메뉴를 조회한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 2.
	 * @param userVo
	 * @param url
	 * @return
	 */
	public List<MenuVo> listTopMenu(HttpServletRequest request);

	/**
	 *
	 * <pre>
	 * Left 메뉴를 조회한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 2.
	 * @param userVo
	 * @param url
	 * @return
	 */
	public List<MenuVo> listLeftMenu(HttpServletRequest request);
	
	/**
	 *
	 * <pre>
	 * 사용자 팝업 메뉴를 조회한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 2.
	 * @param userVo
	 * @param url
	 * @return
	 */
	public List<MenuVo> listPopMenu(HttpServletRequest request);

	/**
	 *
	 * <pre>
	 * 메뉴 목록을 갱신한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 2.
	 */
	public void reload();

	/**
	 *
	 * <pre>
	 * 자동완성 목록을 조회한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 7.
	 * @param userVo
	 * @param searchWord
	 * @return
	 */
	public List<MenuVo> listAutocomplete(UserVo userVo);
	
	/**
	 *
	 * <pre>
	 * 메뉴URL을 조회한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 7.
	 * @param userVo
	 * @param searchWord
	 * @return
	 */
	public List<String> urlList(HttpServletRequest request);
	
}
