package kr.go.rastech.ptl.content.faq.controller;



import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.content.faq.service.FaqMngService;






/**
 * <pre>
 * FileName: FaqMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.faq.controller
 * 
 * 공지사항 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class FaqMngController extends BaseController {

	@Resource
	private FaqMngService faqMngService;


	/**
	 * <pre>
	 * 공지사항 페이지 이동
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
	@RequestMapping("/content/faq/faqList.do")
	public String faqList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("faq", param);
		
		return "content/faq/faqList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 공지사항 관리 list
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
	@RequestMapping(value="/content/faq/readFaqList.do")  
	public void readfaqList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
    	
    	List<Map<String,Object>> list = faqMngService.selectFaqList(param);
    
    	int totalCnt = faqMngService.selectFaqTotalCount(param);
    	
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
				sb.append("<totalCnt>"+Math.ceil((double)totalCnt/(double)10)+"</totalCnt>\n");
				sb.append("<totalDataCnt>"+totalCnt+"</totalDataCnt>\n");
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
	 * 공지사항 관리 등록 url이동
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
	@RequestMapping("content/faq/faqWrite.do")
	public String faqWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("faq", param);
		
		return "content/faq/faqWrite.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 공지사항 관리 등록 url이동
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
	@RequestMapping("/content/faq/faqDetail.do")
	public String faqDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
	    
		if(flashMap != null){
			param = (Map<String, Object>) flashMap.get("paramMap");
	    }
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo == null){
			return "redirect:/index/index.do";
		}
		
		// key 확인		
		if( param.get("faq_id") == null) {
			return "redirect:/content/faq/faqList.do";
		}
		
		
		Map<String,Object> dtl = faqMngService.selectFaqDtl(param);
		
	
		model.addAttribute("data", dtl);
		
		model.addAttribute("faq", param);
		
		return "content/faq/faqDetail.mngPlatForm";
			
	}

	
	@RequestMapping("content/faq/insertFaq.do")
	public String insertFaq(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest ,RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo == null){
			return "redirect:/index/index.do";
		}
	
		
		String loginKey = userVo.getEmplyrkey();
		
		String createId = loginKey;
		param.put("create_id", createId);
		
		
		seq = faqMngService.insertFaqMng(param);

		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("faq_id", seq);
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/content/faq/faqDetail.do";
	}
	

	@RequestMapping("content/faq/updateFaq.do")
	public String updateFaq(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest ,RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/content/faq/faqList.do";
		}		
		// key 확인		
		if(param.get("faq_id") == null) {
			return "redirect:/content/faq/faqList.do";
		}
		
		String loginKey = userVo.getEmplyrkey();
		
		String modifyId = loginKey;
		param.put("modify_id", modifyId);
		faqMngService.updateFaqMng(param);
		
		
		 
	
				
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("faq_id", param.get("faq_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		
		return "redirect:/content/faq/faqDetail.do";
	}
	
	
	
	
	

	/**
     * 공지사항을 삭제처리한다. 
     * @param faqMngVo
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/content/faq/deleteFaq.do")
    public String deleteFaq(Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);

    	faqMngService.deleteFaq(param);
    
    	return "redirect:/content/faq/faqList.do";
    }

}