/******************************************************************
 * Copyright RASTECH 2016
 ******************************************************************/
package kr.go.rastech.ptl.mng.menu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.intercept.DefaultFilterInvocationSecurityMetadataSource;
import kr.go.rastech.commons.menu.service.MenuService;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.mng.auth.service.MngAuthService;
import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;
import kr.go.rastech.ptl.mng.code.service.MngCodeService;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;
import kr.go.rastech.ptl.mng.menu.service.MngMenuService;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuRolVo;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuVo;
import kr.go.rastech.ptl.mng.user.service.MngUserService;


/**
 * <pre>
 * FileName: MngMenuController.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.controller
 * 
 * mng menu 관리 Controller
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 11.
 */
@Controller
public class MngMenuController extends BaseController {

	@Resource
	private MngMenuService mngMenuService;

	@Resource
	private MngUserService mngUserService;

	@Resource
	private MenuService menuService;

	@Resource
	private MenuService menuServiceImpl;
	
	@Resource
	private MngCodeService mngCodeService;
	
	@Resource
	private MngAuthService mngAuthService;

	// 사용자별 권한 정의 객체
    @Autowired
    private DefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;
    
	/**
	 * <pre>
	 * 메뉴관리페이지
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param sendMenuNo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/menu/listMenu.do")
	public String listMenu(String sel_menu_id,Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		List<MngCodeVo> auth_gp = mngCodeService.getCodeList("AUTH_GP");
		List<MngCodeVo> auth_cd = mngCodeService.getCodeList("AUTH_CD");
		
		model.addAttribute("sel_menu_id", sel_menu_id);
		model.addAttribute("auth_gp", auth_gp);
		model.addAttribute("auth_cd", auth_cd);
		
		return "mng/menu/listMenu.mngPlatForm";//rtnUrl("mng/menu/listMenu");
	}
	
	/**
	 * <pre>
	 * 
	 * 메뉴정보 조회
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("mng/menu/readMenuList.do")
	public void readMenuList(HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException {

		String xml="";
		List<MngMenuVo> menuList  = mngMenuService.readMenuList(null);
		if(menuList != null){
			if(menuList.size() > 0){
				xml = XmlUtil.listToXml(menuList);
			}
		}
 	    resp.setContentType("text/xml");
 	    resp.setCharacterEncoding("UTF-8");
 	    resp.setHeader("Cache-Control", "no-cache");
 	    resp.setHeader("Pragma", "no-cache");
 	    resp.setDateHeader("Expires", -1);

 	    out.write(xml);
 	    
 	    out.flush();

	}
	
	/**
	 * <pre>
	 * 
	 * 메뉴 id 중복체크
	 * </pre>
	 * @author : wonki
	 * @date   : 2023. 05. 19.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("mng/menu/duplChkMenuList.do")
	public void readMenuList(HttpServletResponse resp, Writer out , MngMenuVo mngMenuVo)  throws IOException, SQLException , NullPointerException {

		String xml="";
		List<MngMenuVo> menuListChk  = mngMenuService.readMenuList(mngMenuVo);
		
		if(menuListChk.size() > 0){
			xml = XmlUtil.listToXml(menuListChk);
		}
    	
 	    resp.setContentType("text/xml");
 	    resp.setCharacterEncoding("UTF-8");
 	    resp.setHeader("Cache-Control", "no-cache");
 	    resp.setHeader("Pragma", "no-cache");
 	    resp.setDateHeader("Expires", -1);

 	    out.write(xml);
 	    
 	    out.flush();

	}

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴권한 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 17.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/menu/readMenuRolList.do")
	public void readMenuRolList(HttpServletResponse resp, Writer out,String menu_id)  throws IOException, SQLException , NullPointerException {
		
		String xml="";
		List<MngMenuVo> menuRolList  = mngMenuService.readMenuRolList(menu_id);
		
		if(menuRolList.size() > 0){
			xml = XmlUtil.listToXml(menuRolList);
		}
		
		resp.setContentType("text/xml");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", -1);
		
		out.write(xml);
		
		out.flush();
		
	}

	/**
	 * <pre>
	 * 메뉴관리 - 메뉴저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngMenuVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/menu/saveMenu.do")
	public String saveMenu(MngMenuVo mngMenuVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
	
		mngMenuVo.setCreate_id();
		mngMenuVo.setModify_id();
		
		if(StringUtils.equals(mngMenuVo.getSave_type() , "I" )){
			mngMenuService.insertMenu(mngMenuVo);
		}else{
			mngMenuService.updateMenu(mngMenuVo);
			if(mngMenuVo.getMenu_levl() == 0){
				//상위메뉴가 변경시 하위메뉴의 사용유무를 변경
				mngMenuService.updateMenuDtl(mngMenuVo);
			}
			
		}
		
		List<String>  rol_list = mngMenuVo.getMenu_rol_list();
		
		if(StringUtils.equals(mngMenuVo.getRol_save_yn() , "U" )){
			
			MngMenuRolVo mngMenuRolVo;
			String menu_id=mngMenuVo.getMenu_id();
			mngMenuRolVo = new MngMenuRolVo();
			mngMenuRolVo.setMenu_id(menu_id);
			
			int cnt = mngMenuService.deleteMenuRol(mngMenuRolVo);

			for (String str : rol_list) {
	
				mngMenuRolVo = new MngMenuRolVo();
				mngMenuRolVo.setMenu_id(menu_id);
				mngMenuRolVo.setAuth_gbn(str.split("\\|")[0]);
				mngMenuRolVo.setRol_id(str.split("\\|")[1]);
				mngMenuRolVo.setModify_id();
				mngMenuRolVo.setCreate_id();
				mngMenuService.insertMenuRol(mngMenuRolVo);
			}
		}
		// 사용자별 권한 정의 갱신(초기화)
		databaseSecurityMetadataSource.init();
		//상단메뉴와 왼쪽메뉴를 재구성
		menuService.reload();

		return "redirect:listMenu.do?sel_menu_id="+mngMenuVo.getMenu_id();
	}
	
	/**
	 * <pre>
	 * 메뉴관리- 메뉴삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngMenuVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/menu/delMenu.do")
	public String delMenu(MngMenuVo mngMenuVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
		// 상위메뉴코드와 메뉴코드 전부 비어있으면 오류 처리
		if(StringUtils.isBlank(mngMenuVo.getMenu_id()) && StringUtils.isBlank(mngMenuVo.getMenu_prts_id()) ){
			throw new NullPointerException("삭제 메뉴정보가 없습니다."); 
		}
		
		mngMenuService.deleteMenu(mngMenuVo);
		
		MngAuthVo mngAuthVo = new MngAuthVo();
		mngAuthVo.setMenu_id(mngMenuVo.getMenu_id());
		//해당 메뉴의 권한URL 삭제
		mngAuthService.deleteUrlAuth(mngAuthVo);

		// 사용자별 권한 정의 갱신(초기화)
		databaseSecurityMetadataSource.init();
		//상단메뉴와 왼쪽메뉴를 재구성
		menuService.reload();
		
		return "redirect:listMenu.do";
	}
	
	/**
	 *
	 * <pre>
	 * 메뉴 관리 - menu xml 생성
	 *
	 * </pre>
	 * @author : sbkang
	 * @date   : 2023. 10. 12.
	 * @param resultList
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public StringBuffer resultXml(List<MngMenuVo> resultList) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("<resultSet>\n");
		for(int i = 0; i < resultList.size(); i++){
			sb.append("	<result>\n");
			sb.append("		<menu_id><![CDATA[" + resultList.get(i).getMenu_id() + "]]></menu_id>\n");
			sb.append("	</result>\n");
		}
		sb.append("</resultSet>");
		return sb;
	}
	
}
