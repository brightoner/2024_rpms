package kr.go.rastech.ptl.index.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/*import kr.go.ncmiklib.commons.scheduler.Scheduler;*/
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.intercept.DefaultFilterInvocationSecurityMetadataSource;
import kr.go.rastech.commons.menu.service.MenuService;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.content.banner.service.BannerMngService;
import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;

/**
 * <pre>
 * FileName: IndexController.java
 * Package : kr.go.ncmiklib.ptl.index.controller
 * 
 * 메인페이지 - Controller
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 17.
 */
@Controller("indexController")
public class IndexController  extends BaseController {

    @Resource
	private MenuService menuService;
   

    // 사용자별 권한 정의 객체
    @Autowired
    private DefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

    @Resource
	private BannerMngService bannerMngService;
    
    @Autowired
    private HttpSession httpSession;
    /**
	 * <pre>
	 * 초기화면을 호출한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 4. 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	  @RequestMapping(value="/")
	  public String index(HttpServletRequest request) throws IOException, SQLException , NullPointerException {
		   String rtn_url = "";
		  
		   rtn_url = "redirect:index/index.do";
		
	       return rtn_url;
	  }



    /**
	 * <pre>
	 * 초기화면을 호출한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 4. 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	  */
	  @RequestMapping(value="/index/index.do")
	  public String intro(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException, SQLException , NullPointerException {
		
			  request.getSession().setAttribute("if_yn", "N"); 
		  		
			  int sessionTimeout = httpSession.getMaxInactiveInterval();
			    System.out.println("세션 타임아웃 값: " + sessionTimeout + "초");
			  //로그인 시도 후의 결과값을 화면에 넘겨주기 위해 셋팅 STR
			  Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
	
				String message = "";
				if (flashMap != null) {
					message = (String) flashMap.get("message");
				}
	
				if (StringUtils.isNotBlank(message)) {
				//	model.addAttribute("loginid", loginid);
					model.addAttribute("message", message);
					model.addAttribute("result", "false");
				}
				if (request.getSession().getAttribute("message") != null) {
					if (request.getSession().getAttribute("message") != "") {
						model.addAttribute("message", request.getSession().getAttribute("message"));
						model.addAttribute("result", "false");
						request.getSession().setAttribute("message", "");
					}
					// 없어도 문제 없을듯 ? 
					/* else {
						model.addAttribute("result", "");
					}*/
				}
			
			
		
			return "index/index.mainPlatForm";
	   }
	  
	  @RequestMapping(value="/index/index1.do")
	  public String intro1(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException, SQLException , NullPointerException {
		
			  request.getSession().setAttribute("if_yn", "N"); 
		  
			  String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		
			  model.addAttribute("message", request.getSession().getAttribute("msg"));
			  request.getSession().setAttribute("msg","");
			  
			//  model.addAttribute("test", komsTopicKeyword);
			  
			return "index/index1.subPlatForm";
	  }
	  
	  @RequestMapping(value="/index/index2.do")
	  public String intro2(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException, SQLException , NullPointerException {
		
			  request.getSession().setAttribute("if_yn", "N"); 
		  
			  String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		
			  model.addAttribute("message", request.getSession().getAttribute("msg"));
			  request.getSession().setAttribute("msg","");
	
			  
			return "index/index2.subPlatForm";
	  }
	  
	  @RequestMapping(value="/index/index3.do")
	  public String intro3(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException, SQLException , NullPointerException {
		
			  request.getSession().setAttribute("if_yn", "N"); 
		  
			  String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		
			  model.addAttribute("message", request.getSession().getAttribute("msg"));
			  request.getSession().setAttribute("msg","");
	
			  
			return "index/index3.subPlatForm";
	  }
	  
	  @RequestMapping(value="/index/index4.do")
	  public String intro4(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException, SQLException , NullPointerException {
		
			  request.getSession().setAttribute("if_yn", "N"); 
		  
			  String locale = String.valueOf(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		
			  model.addAttribute("message", request.getSession().getAttribute("msg"));
			  request.getSession().setAttribute("msg","");
			  
			 
			  
			//  model.addAttribute("test", komsTopicKeyword);
			  
			return "index/index4.subPlatForm";
	  }
	  
	/**
	 * <pre>
	 * 권한관리 - 메뉴별 권한조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param resp
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping("/index/chgLang.do")
	public void chgLang(HttpServletRequest request, HttpServletResponse response,String lang)  throws IOException, SQLException , NullPointerException , RuntimeException, ClassCastException {
	
		HttpSession session = request.getSession();
		Locale locales = null;
		// 넘어온 파라미터에 ko가 있으면 Locale의 언어를 한국어로 바꿔준다.
		// 그렇지 않을 경우 영어로 설정한다.
		if (lang.matches("K")) {
			locales = Locale.KOREAN;
		} else {
			locales = Locale.ENGLISH;
		}
	
		// 세션에 존재하는 Locale을 새로운 언어로 변경해준다.
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locales);
	
		menuService.reload();
		
	}
	
	@RequestMapping("/index/chgTabMode.do")
	@ResponseBody
	public String chgTabMode(HttpServletRequest request, HttpServletResponse response,String lang)  throws IOException, SQLException , NullPointerException , RuntimeException, ClassCastException {
		
		HttpSession session = request.getSession();
	    String if_yn  = StringUtil.nvl(request.getSession().getAttribute("if_yn"));
	    String rtn_val = "";
		if("Y".equals(if_yn)){
			session.setAttribute("if_yn", "N");
			rtn_val="N";
		}else{
			session.setAttribute("if_yn", "Y");
			rtn_val="Y";
		}
		return rtn_val;
	}
	
	/**
	 * <pre>
	 * 권한관리 - 메뉴별 권한조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param resp
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping("/index/getLang.do")
	public void getLang(HttpServletRequest request, HttpServletResponse resp,Writer out, String page_id)  throws IOException, SQLException , NullPointerException , RuntimeException, ClassCastException {
		
		String xml = "";
		
		if(getLangList(page_id) .size() > 0){
			xml = XmlUtil.listToXml(getLangList(page_id));
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
	 * 권한관리 - 메뉴별 권한조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/index/auth/readMenuAuth.do")
	public void readMenuAuth(HttpServletRequest request, HttpServletResponse resp, Writer out,String menu_url)  throws IOException, SQLException , NullPointerException {
		
		String xml="";
		List<MngAuthVo> mngAuthVoList = databaseSecurityMetadataSource.getMenuAuth(menu_url, request);
		
		if(mngAuthVoList.size() > 0){
			xml = XmlUtil.listToXml(mngAuthVoList);
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
	 * 배너관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param request
	 * @param bannerMngVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/index/banner/readBannerList.do")  
	public void readbannerList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);	
		
		String xml = "";
    	
    	List<Map<String,Object>> list = bannerMngService.selectMainBannerList(param);
    
    	
    	
    	if(list != null){
    		if(list.size() > 0){
    			
    			xml = XmlUtil.listMapToXml(list);
    			/*
    			 * 총카운터 세팅
    			 */
    	    	StringBuffer sb = new StringBuffer();
    	    	String[] str = xml.split("<items>");
    	    	
    	    	sb.append(str[0]);
    	    	sb.append("<items>");		
    	    	sb.append(str[1]);
    	    	
    	    	xml = sb.toString();
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
	

	
	
}
