package kr.go.rastech.ptl.center.faq.controller;



import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.center.faq.service.FaqService;






/**
 * <pre>
 * FileName: FaqMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.faq.controller
 * 
 * 자주 묻는 질문 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class FaqController extends BaseController {

	@Resource
	private FaqService faqService;


	/**
	 * <pre>
	 * 자주 묻는 질문 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/center/faq/faqList.do")
	public String faqList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("faq", param);
		
		return "center/faq/faqList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 자주 묻는 질문 관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param request
	 * @param faqMngVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/center/faq/readFaqList.do")  
	public void readfaqList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);		
		
		
		String xml = "";
    	
    	List<Map<String,Object>> list = faqService.selectFaqList(param);

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

	/**
	 * <pre>
	 * 자주 묻는 질문 관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param faqMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("center/faq/faqWrite.do")
	public String faqWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("faq", param);
		
		return "center/faq/faqWrite.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 자주 묻는 질문 관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/center/faq/faqDetail.do")
	public String faqDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		
		
		// key 확인		
		if( param.get("faq_id") == null) {
			return "redirect:/center/faq/faqList.do";
		}
		
		
		Map<String,Object> dtl = faqService.selectFaqDtl(param);
		
	
		if(dtl != null ) {
			
			if(dtl.get("faq_contents") != null) {
				dtl.put("faq_contents", StringEscapeUtils.unescapeHtml3((String) dtl.get("faq_contents")));
			}
			
			model.addAttribute("data", dtl);		
			model.addAttribute("faq", param);
		}
		return "center/faq/faqDetail.mngPlatForm";
			
	}

	
	
	
	

}