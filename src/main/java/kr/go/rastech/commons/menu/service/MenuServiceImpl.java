/******************************************************************
 * Copyright RASTECH 2015
 ******************************************************************/
package kr.go.rastech.commons.menu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.menu.dao.MenuDao;
import kr.go.rastech.commons.menu.vo.MenuVo;

/**
 * <pre>
 * FileName: MenuServiceImpl.java
 * Package : kr.go.ncmik.commons.menu.service
 *
 * 사용자 메뉴 Service impl.
 *
 * </pre>
 * @author : rastech
 * @date   : 2015. 10. 2.
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
	// 권한별 TOP 메뉴 정보를 가지고 있는 MAP
	private static Map<Object, List<MenuVo>> tOP_MENU_MAP = new HashMap<Object, List<MenuVo>>();
	// 권한별 LEFT 메뉴 정보를 가지고 있는 MAP
	private static Map<Object, List<MenuVo>> lEFT_MENU_MAP = new HashMap<Object, List<MenuVo>>();
	// 권한별 LEFT 메뉴 정보를 가지고 있는 MAP
	private static Map<Object, List<MenuVo>> lEFT_TOTAL_MAP = new HashMap<Object, List<MenuVo>>();
	// 전체 URL 목록을 가지고 있는 LIST
	private static List<MenuVo> tOTAL_URL_LIST = new ArrayList<MenuVo>();
	// 통계 메뉴 URL 접두어
	private String prefixStatUrl = "/stat/";

	private static long  lAST_TIME;
	
	static{
		lAST_TIME = 0;
	}
	
	@Resource
	private MenuDao menuDao;

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
	public List<MenuVo> listTopMenu(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("userVo");
        String url = request.getRequestURI();
		
		if (userVo == null) {
			userVo = new UserVo();
			List<String> roleList = new ArrayList<String>();
			roleList.add("ROLE_GUEST");
			userVo.setAuth(roleList);
		}

		long now = System.currentTimeMillis();
		if (now > lAST_TIME) {
			reload();
			lAST_TIME = now + 60000;
		}


		List<MenuVo> list = tOP_MENU_MAP.get(userVo.getAuthorities());
		if (list == null) {

			String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
	
			if(StringUtils.equals(locale, "en")){
				userVo.setEngYn("E");
			}else{
				userVo.setEngYn("K");
			}

			list = new ArrayList<MenuVo>();
			List<MenuVo> menuList = menuDao.selectTopMenuList(userVo);
			List<MenuVo> subList = null;
			MenuVo menuVo = null;
			for(MenuVo vo: menuList) {
				if (StringUtils.equals(vo.getLvl(), "0")) {
					if (menuVo != null) {
						menuVo.setSubList(subList);
						list.add(menuVo);
					}
					menuVo = vo;
					subList = new ArrayList<MenuVo>();
				} else {
					if(subList != null){
						subList.add(vo);
					}
				}
			}
			if (menuVo != null) {
				if (subList != null && !subList.isEmpty()) {menuVo.setSubList(subList);}
				list.add(menuVo);
			}
			if (!list.isEmpty()) {tOP_MENU_MAP.put(userVo.getAuthorities(), list);}
		}

		String param = request.getParameter("searchSubTarget");
		
		return checkUrl(list, url,userVo,param);
	}

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
	public  List<MenuVo>  listLeftMenu(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("userVo");

        if (userVo == null) {
			userVo = new UserVo();
			List<String> roleList = new ArrayList<String>();
			roleList.add("ROLE_GUEST");
			userVo.setAuth(roleList);
        }
			
        String url = request.getRequestURI();
        if(url != null){
        	url = url.replace(request.getContextPath(), "");
        }

        Map<String,String> param = new HashMap<String, String>();
        
		String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		
		if(StringUtils.equals(locale, "en")){
			userVo.setEngYn("E");
		}else{
			userVo.setEngYn("K");
		}
  
        /* 0레벨 메뉴 */
        List<MenuVo> menuList = menuDao.selectLeftMenuList(userVo);
		
		return checkleftUrl(menuList, url);

	}
	
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
	public  List<MenuVo>  listPopMenu(HttpServletRequest request) {
       HttpSession session = request.getSession();
       UserVo userVo = (UserVo) session.getAttribute("userVo");

       if (userVo == null) {
			userVo = new UserVo();
			List<String> roleList = new ArrayList<String>();
			roleList.add("ROLE_GUEST");
			userVo.setAuth(roleList);
       }
			
       String url = request.getRequestURI();
       if(url != null){
       	url = url.replace(request.getContextPath(), "");
       }

       Map<String,String> param = new HashMap<String, String>();
       
		String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		
		if(StringUtils.equals(locale, "en")){
			userVo.setEngYn("E");
		}else{
			userVo.setEngYn("K");
		}
 
       /* 0레벨 메뉴 */
       List<MenuVo> menuList = menuDao.selectPopMenuList(userVo);
		
		return menuList;

	}
	private List<MenuVo> checkleftUrl(List<MenuVo> list,String url) {
		
		String reqUrl = url.split("/")[1];
		
		List<MenuVo> menuList = new ArrayList<MenuVo>();
		
		for(MenuVo vo: list) {
			
			if(StringUtils.equals(reqUrl, vo.getUrl().split("/")[1])){
				vo.setSelYn(false);
				//상세나 등록 들어가면 메뉴 안칠해져서 바꿧다
				//URL 등록은 반드시  /그룹명/업무명/화면.do     뎁스를  /그룹명/업무명/ 2단계 이상 지켜줄것  
				/*
				if (StringUtils.equalsIgnoreCase(vo.getUrl(), url)) {
					vo.setSelYn(true);
				}
			 	*/
				if (StringUtils.equalsIgnoreCase(vo.getUrl().split("/")[2], url.split("/")[2])) {
					vo.setSelYn(true);
				}
				menuList.add(vo);
			}
		}
		return menuList;
	}

	/**
	 *
	 * <pre>
	 * 메뉴 목록을 갱신한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 2.
	 */
	public void reload() {
		// 메뉴정보를 가지고 있는 MAP을 클리어 하여 다시 조회하도록 한다.
		tOP_MENU_MAP.clear();
		lEFT_MENU_MAP.clear();
		tOTAL_URL_LIST.clear();
	}

	/**
	 *
	 * <pre>
	 * 자동완성 목록을 조회한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 8.
	 * @param userVo
	 * @param searchWord
	 * @return
	 */
	public List<MenuVo> listAutocomplete(UserVo userVo) {
		if (userVo == null) {
			userVo = new UserVo();
			List<String> roleList = new ArrayList<String>();
			roleList.add("ROLE_GUEST");
			userVo.setAuth(roleList);
		}
		List<MenuVo> list = lEFT_MENU_MAP.get(userVo.getAuthorities());
		List<MenuVo> result = new ArrayList<MenuVo>();
		for (MenuVo vo: list) {
			if (vo.getSubList() != null && StringUtils.startsWith(vo.getUrl(), prefixStatUrl)) {
				for (MenuVo menuVo: vo.getSubList()) {
					result.add(menuVo);
				}
			}
		}
		return result;
	}

	/**
	 *
	 * <pre>
	 * URL 값과 메뉴값이 동일한 값을 비교하여 flag를 설정한다.
	 * 또한 deep copy를 수행한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 5.
	 * @param list
	 * @param url
	 * @return
	 */
	private List<MenuVo> checkUrl(List<MenuVo> list, String url,UserVo userVo,String param) {
		List<MenuVo> result = new ArrayList<MenuVo>();
		MenuVo copyVo = null;
		boolean isCheck = false;
		for (MenuVo menuVo: list) {
			copyVo = menuVo.runClone();
			if (StringUtils.equalsIgnoreCase(copyVo.getUrl(), url)) {
				copyVo.setSelYn(true);
				isCheck = true;
			}
			if (copyVo.getSubList() != null) {
				String subUrl ="";
				String subUrlParam = "";
				for (MenuVo subMenuVo: copyVo.getSubList()) {

					if(StringUtils.isBlank(param)){
						subUrl = subMenuVo.getUrl();
						if (StringUtils.equalsIgnoreCase(subUrl, url)) {
							subMenuVo.setSelYn(true);
							userVo.setSel_Menu(url);
							copyVo.setSelYn(true);
							isCheck = true;
						}
					}else{
						if(subMenuVo.getUrl().indexOf("?") != -1){
							subUrl = subMenuVo.getUrl().split("\\?")[0];
							subUrlParam = subMenuVo.getUrl().split("\\?")[1];
							if (StringUtils.equalsIgnoreCase(subUrl, url)) {
								if(subUrlParam.indexOf(param) != -1){
									subMenuVo.setSelYn(true);
									userVo.setSel_Menu(url);
									copyVo.setSelYn(true);
									isCheck = true;
								}
							}
						}

					}
				}
			}
			result.add(copyVo);
		}

		if (!isCheck) {
			List<MenuVo> urlList = listUrl();
			for (MenuVo urlVo: urlList) {
				if (urlVo.getSubList() != null) {
					for (MenuVo subUrlVo: urlVo.getSubList()) {
						if (StringUtils.equalsIgnoreCase(subUrlVo.getUrl(), url)) {
							url = urlVo.getUrl();
							isCheck = true;
							break;
						}
					}
					if (isCheck) break;
				}
			}

			if (isCheck) {
				result = new ArrayList<MenuVo>();
				for (MenuVo menuVo: list) {
					copyVo = menuVo.runClone();
					if (StringUtils.equalsIgnoreCase(copyVo.getUrl(), url)) {
						copyVo.setSelYn(true);
					}
					if (copyVo.getSubList() != null) {
						for (MenuVo subMenuVo: copyVo.getSubList()) {
							if (StringUtils.equalsIgnoreCase(subMenuVo.getUrl(), url)) {
								userVo.setSel_Menu(url);
								subMenuVo.setSelYn(true);
								copyVo.setSelYn(true);
							}
						}
					}
					result.add(copyVo);
				}
			}
		}
		/**
		 * 페이지의 상세보기시에 상위 메뉴를 선택하게 처리
		 */
		if(!isCheck){

			String sel_menu= userVo.getSel_Menu();		
			int cnt =0;
			
			for (MenuVo menuVo : result) {
	
				String[] url_split = url.split("/");
				String[] menu_split = menuVo.getUrl().split("/");
				// 예) 메인링크에서 메뉴 클릭시 메뉴 표시해 줄수 있도록 처리.. 
				if(url_split != null){
					if(url_split.length > 2){
	//		logger.debug("url_split.length" + url_split.length);
/*			임시주석처리		
 * 			String r_url = url_split[1]+"/"+url_split[2];
						String r_menu = menu_split[1]+"/"+ menu_split[2];
						if(r_url.equals(r_menu)){
							sel_menu = menuVo.getUrl();
						
						}*/
					}
				}
								
				if(menuVo.getUrl().indexOf(sel_menu) != -1){
				
					menuVo.setSelYn(true);
					result.get(cnt).setSelYn(true);
				}
				if (result.get(cnt) != null) {
			
					if(result.get(cnt).getSubList() != null ){
					
						int dtl_cnt=0;
						
						for (MenuVo subMenuVo: result.get(cnt).getSubList()) {

							if (StringUtils.equals(subMenuVo.getUrl(), sel_menu)) {			
								menuVo.setSelYn(true);
								result.get(cnt).getSubList().get(dtl_cnt).setSelYn(true);
							}
							dtl_cnt++;
						}
					}
				}
				cnt++;
			}
			
			
		}

		return result;
	}

	/**
	 *
	 * <pre>
	 * 전체 URL 목록 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 6.
	 * @return
	 */
	private List<MenuVo> listUrl() {
		long now = System.currentTimeMillis();
		if (now > lAST_TIME) {
			reload();
			lAST_TIME = now + 60000;
		}
		List<MenuVo> list = tOTAL_URL_LIST;
		if (list.isEmpty()) {
			list = new ArrayList<MenuVo>();
			List<MenuVo> menuList = menuDao.selectUrlList();
			List<MenuVo> subList = null;
			MenuVo menuVo = null;
			for(MenuVo vo: menuList) {
				if (StringUtils.equals(vo.getLvl(), "0")) {
					if (menuVo != null) {
						menuVo.setSubList(subList);
						list.add(menuVo);
					}
					menuVo = vo;
					subList = new ArrayList<MenuVo>();
				} else {
					subList.add(vo);
				}
			}
			if (menuVo != null) {
				if (subList != null && !subList.isEmpty()) {menuVo.setSubList(subList);}
				list.add(menuVo);
			}
			if (!list.isEmpty()) {tOTAL_URL_LIST = list;}
		}
		return list;
	}
	
	
	/**
	 *
	 * <pre>
	 * 통계를 위한 URL목록 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2015. 10. 6.
	 * @return
	 */
	public List<String> urlList(HttpServletRequest request){
		List<String> uRL_LIST = new ArrayList<String>();
		
		List<MenuVo> list = menuDao.selectUrlList();
		for(MenuVo menuVo : list){
			if( !(menuVo.getUrl().contains("/mylib/") || menuVo.getUrl().contains("/member/") || menuVo.getUrl().contains("/login/")) ){
				uRL_LIST.add(menuVo.getUrl());
			}
		}
		
		uRL_LIST.add("/index/index.do");
		
		return uRL_LIST;
	}
}
