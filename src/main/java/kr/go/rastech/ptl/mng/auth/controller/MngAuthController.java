/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.ptl.mng.auth.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.intercept.DefaultFilterInvocationSecurityMetadataSource;
import kr.go.rastech.commons.menu.service.MenuService;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.mng.auth.service.MngAuthService;
import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;
import kr.go.rastech.ptl.mng.code.service.MngCodeService;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;
import kr.go.rastech.ptl.mng.user.service.MngUserService;


/**
 * <pre>
 * FileName: MngAuthController.java
 * Package : kr.go.ncmiklib.ptl.mng.auth.controller
 * 
 * mng 권한 관리 Controller
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 15.
 */
@Controller
public class MngAuthController extends BaseController {
	
	@Resource
	private MngAuthService mngAuthService;

	@Resource
	private MngUserService mngUserService;
	
	@Resource
	private MngCodeService mngCodeService;

	@Resource
	private MenuService menuService;


	// 사용자별 권한 정의 객체
    @Autowired
    private DefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;


	/**
	 * <pre>
	 * 권한관리 - Url권한관리
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param sel_menu_id
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("mng/auth/listAuth.do")
	public String listUrl(String sel_menu_id, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {

		List<MngCodeVo> auth_cd = mngCodeService.getCodeList("AUTH_CD");
		
		model.addAttribute("sel_menu_id", sel_menu_id);
		model.addAttribute("auth_cd", auth_cd);
			
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		return "mng/auth/listAuth.mngPlatForm";//rtnUrl("mng/auth/listAuth");
	}

	
	/**
	 * <pre>
	 * 권한관리- URL정보 저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/auth/saveUrlAuth.do")
	@ResponseBody
	public String saveUrlAuth(Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException ,ParseException {
		
		String sel_menu_id = mngAuthService.insertUrlAuth(request);
		// 사용자별 권한 정의 갱신(초기화)
		databaseSecurityMetadataSource.init();
		//상단메뉴와 왼쪽메뉴를 재구성
		menuService.reload();
		
		return sel_menu_id;
	}
	
	/**
	 * <pre>
	 * URL정보 삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/auth/delUrlAuth.do")
	@ResponseBody
	public String delUrlAuth(Model model, HttpServletRequest request,String url_seq,String sel_menu_id)  throws IOException, SQLException , NullPointerException {
		
		MngAuthVo mngAuthVo = new MngAuthVo();
		mngAuthVo.setUrl_seq(Integer.parseInt(url_seq));

		mngAuthService.deleteUrlAuth(mngAuthVo);
		// 사용자별 권한 정의 갱신(초기화)
		databaseSecurityMetadataSource.init();
		//상단메뉴와 왼쪽메뉴를 재구성
		menuService.reload();
				
		return sel_menu_id;
	}
	
	
	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("mng/auth/readUrlAuth.do")
	public void readUrlAuth(HttpServletResponse resp, Writer out,MngAuthVo mngAuthVo,String menu_id)  throws IOException, SQLException , NullPointerException {

		String xml="";
		mngAuthVo.setMenu_id(menu_id);
		List<MngAuthVo> authList  = mngAuthService.readUrlAuth(mngAuthVo);
		
		if(authList.size() > 0){
			xml = XmlUtil.listToXml(authList);
		}
    	
 	    resp.setContentType("text/xml");
 	    resp.setCharacterEncoding("UTF-8");
 	    resp.setHeader("Cache-Control", "no-cache");
 	    resp.setHeader("Pragma", "no-cache");
 	    resp.setDateHeader("Expires", -1);

 	    out.write(xml);
 	    
 	    out.flush();

	}
	
}
