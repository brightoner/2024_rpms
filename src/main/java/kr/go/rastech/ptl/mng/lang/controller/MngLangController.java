/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.ptl.mng.lang.controller;

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
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;
import kr.go.rastech.ptl.mng.lang.service.MngLangService;
import kr.go.rastech.ptl.mng.lang.vo.MngLangVo;
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
public class MngLangController extends BaseController {
	
	@Resource
	private MngLangService mngLangService;

	@Resource
	private MngUserService mngUserService;

	// 사용자별 권한 정의 객체
    @Autowired
    private DefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;


	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 27.
	 * @param sel_menu_id
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("mng/lang/listLang.do")
	public String listLang(String sel_menu_id, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {

		List<MngCodeVo> tag_type = getCodeList("TAG_TYPE");
		
		model.addAttribute("sel_menu_id", sel_menu_id);
		model.addAttribute("tag_type", tag_type);
			
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		model.addAttribute("if_yn", param.get("if_yn"));
		
		return rtnUrl("mng/lang/listLang");
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
	@RequestMapping("mng/lang/selectLang.do")
	public void selectLang(HttpServletResponse resp, Writer out,MngLangVo mngLangVo,String menu_id)  throws IOException, SQLException , NullPointerException {

		String xml="";
		//mngLangVo.setMenu_id(menu_id);
		List<MngLangVo> authList  = mngLangService.selectLang(mngLangVo);
		
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
	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 27.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/lang/saveLang.do")
	@ResponseBody
	public void saveLang(Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException ,ParseException {
		
		mngLangService.insertUrlAuth(request);

	}
	
	/**
	 * <pre>
	 * 다국어정보 삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="mng/lang/delLang.do")
	@ResponseBody
	public void delLang(Model model, HttpServletRequest request,String url_seq,String sel_menu_id)  throws IOException, SQLException , NullPointerException {
		
		MngLangVo mngLangVo = new MngLangVo();
		mngLangVo.setSeq(url_seq);

		mngLangService.deleteLang(mngLangVo);
				
	}
	
}
