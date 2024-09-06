package kr.go.rastech.ptl.center.qna.controller;



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
import kr.go.rastech.ptl.center.qna.service.QnaService;






/**
 * <pre>
 * FileName: QnaController.java
 * Package : kr.go.rastech.ptl.center.qna.controller
 * 
 * 1:1 문의 qna 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class QnaController extends BaseController {

	@Resource
	private QnaService qnaService;
	

	/**
	 * <pre>
	 * 1:1 문의 qna 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param sendCd
	 * @param mode
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/center/qna/qnaList.do")
	public String qnaList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
	
		
		model.addAttribute("qna", param);
		
		return "center/qna/qnaList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 1:1 문의 qna list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param request
	 * @param qnaMngVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	
	@RequestMapping(value="/center/qna/readQnaList.do")  
	public void readqnaList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		UserVo userVo = getUser();		
		String xml = "";
		if(userVo != null){				
			
			param.put("loginKey",userVo.getEmplyrkey());
	    	
	    	List<Map<String,Object>> list = qnaService.selectQnaList(param);
	    
	    	int totalCnt = qnaService.selectQnaTotalCount(param);
	    	
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
	 * 1:1 문의 qna 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param qnaMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("center/qna/qnaWrite.do")
	public String qnaWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("qna_code", getCodeList("QNA_CODE"));
		model.addAttribute("qna", param);
		
		return "center/qna/qnaWrite.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 1:1 문의 qna 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param qnaMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/center/qna/qnaDetail.do")
	public String qnaDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
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
		if( param.get("qna_id") == null) {
			return "redirect:/center/qna/qnaList.do";
		}
		
		param.put("loginKey",userVo.getEmplyrkey());
		Map<String,Object> dtl = qnaService.selectQnaDtl(param);
		
	
		
		model.addAttribute("qna_code", getCodeList("QNA_CODE"));
		model.addAttribute("data", dtl);
		
		model.addAttribute("qna", param);
		
		return "center/qna/qnaDetail.mngPlatForm";
			
	}

	
	@RequestMapping("center/qna/insertQna.do")
	public String insertQna(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest ,RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo == null){
			return "redirect:/center/qna/qnaList.do";
		}
	
		
		String loginKey = userVo.getEmplyrkey();
		
		String createId = loginKey;
		param.put("create_id", createId);
		
		
		seq = qnaService.insertQna(param);

		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("qna_id", param.get("qna_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/center/qna/qnaDetail.do";
	}
	

	@RequestMapping("center/qna/updateQna.do")
	public String updateQna(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest ,RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/center/qna/qnaList.do";
		}		
		// key 확인		
		if(param.get("qna_id") == null) {
			return "redirect:/center/qna/qnaList.do";
		}
		
		String loginKey = userVo.getEmplyrkey();
		
		String modifyId = loginKey;
		param.put("modify_id", modifyId);
		qnaService.updateQna(param);

				
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("qna_id", param.get("qna_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		
		return "redirect:/center/qna/qnaDetail.do";
	}
	
	
	
	

	/**
     * 1:1 문의 qna을 삭제처리한다. 
     * @param qnaMngVo
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/center/qna/deleteQna.do")
    public String deleteQna(Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);

    	qnaService.deleteQna(param);
    
    	return "redirect:/center/qna/qnaList.do";
    }

}