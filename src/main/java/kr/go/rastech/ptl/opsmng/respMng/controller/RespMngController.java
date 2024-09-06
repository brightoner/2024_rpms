package kr.go.rastech.ptl.opsmng.respMng.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.opsmng.respMng.service.RespMngService;


/**
 * 
 * 연구자 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
@Controller
public class RespMngController extends BaseController {

	@Resource
	private RespMngService respMngService;
	

	/**
	 * <pre>
	 * 연구자 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/respMngList.do")
	public String respMngList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("respMng", param);
		
		return "opsmng/respMng/respMngList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 전문가 목록
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respExpert/respExpertList.do")
	public String respExpertList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("respExpert", param);
		
		return "opsmng/respExpert/respExpertList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 연구자 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/respMngPopList.do")
	public String respMngPopList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("searchopt", "resp_org");
		param.put("searchword", "라스테크");
		
		model.addAttribute("respMng", param);
		
		
		return "opsmng/respMng/respMngPopList.popPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 연구자 관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/respMng/readRespMngList.do")  
	public String readRespMngList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
	
    	
    	List<Map<String,Object>> list = respMngService.selectRespMngList(param);
    
    	int totalCnt = respMngService.selectRespMngTotalCount(param);
    	
    	
    			
		model.addAttribute("respMngList", list);
    	model.addAttribute("respMngTotal", totalCnt);
    	model.addAttribute("respMngPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 연구자 상세보기
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	 * @param respMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/respMngDetail.do")
	public String respMngDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
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
		if(param.get("resp_id") == null) {
			return "redirect:/opsmng/respMng/respMngList.do";
		}
		
		Map<String,Object> dtl = respMngService.selectRespMngDtl(param);
	
		model.addAttribute("data", dtl);
		model.addAttribute("respMng", param);
		model.addAttribute("degree_code", getCodeList("RESP_DEGREE"));
		model.addAttribute("affil_code", getCodeList("RESP_AFFIL"));
		model.addAttribute("major_code", getCodeList("MAJOR_CODE"));
		
		return "opsmng/respMng/respMngDetail.mngPlatForm";
			
	}

	/**
	 * <pre>
	 * 연구자 상세보기
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	 * @param respMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respExpert/respExpertDetail.do")
	public String respExpertDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
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
		if(param.get("resp_id") == null) {
			return "redirect:/opsmng/respExpert/respExpertList.do";
		}
		
		Map<String,Object> dtl = respMngService.selectRespMngDtl(param);
	
		model.addAttribute("data", dtl);
		model.addAttribute("respExpert", param);
		model.addAttribute("degree_code", getCodeList("RESP_DEGREE"));
		model.addAttribute("affil_code", getCodeList("RESP_AFFIL"));
		model.addAttribute("major_code", getCodeList("MAJOR_CODE"));
		
		return "opsmng/respExpert/respExpertDetail.mngPlatForm";
			
	}

	
	/**
	 * <pre>
	 * 연구자관리
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/updateRespMng.do")
	public String updateRespMng(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("resp_id") == null) {
			return "redirect:/opsmng/respMng/respMngList.do";
		}
		
		result= respMngService.updateRespMng(param, userVo);
		
		model.addAttribute("result",result);
		
		
		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 연구자 관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.	
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/respMngWrite.do")
	public String respMngWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("respMng", param);
		model.addAttribute("degree_code", getCodeList("RESP_DEGREE"));
		model.addAttribute("affil_code", getCodeList("RESP_AFFIL"));
		model.addAttribute("major_code", getCodeList("MAJOR_CODE"));
		
		return "opsmng/respMng/respMngWrite.mngPlatForm";
			
	}
	/**
	 * <pre>
	 * 연구자관리
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/insertRespMng.do")
	public String insertRespMng(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("resp_id") == null) {
			return "redirect:/opsmng/respMng/respMngList.do";
		}
		
		result= respMngService.insertRespMng(param, userVo);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("resp_id", param.get("resp_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/opsmng/respMng/respMngDetail.do";
	}
	
	/**
	 * <pre>
	 * 연구자관리
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/respMng/deleteRespMng.do")
	public String deleteRespMng(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("resp_id") == null) {
			return "redirect:/opsmng/respMng/respMngList.do";
		}
		
		result= respMngService.deleteRespMng(param, userVo);
		
	
		
		return "redirect:/opsmng/respMng/respMngList.do";
	}
	
	
}