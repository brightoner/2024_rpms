package kr.go.rastech.ptl.opsmng.orgMng.controller;



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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 기관 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
@Controller
public class OrgMngController extends BaseController {

	@Resource
	private OrgMngService orgMngService;
	

	/**
	 * <pre>
	 * 기관 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/orgMngList.do")
	public String orgMngList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("orgMng", param);
		model.addAttribute("org_class_code", getCodeList("ORG_CLASS"));
		
		return "opsmng/orgMng/orgMngList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 기관 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/orgMngPopList.do")
	public String orgMngPopList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("orgMng", param);
		
		
		return "opsmng/orgMng/orgMngPopList.popPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 기관 관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/orgMng/readOrgMngList.do")  
	public String readOrgMngList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
	
    	
    	List<Map<String,Object>> list = orgMngService.selectOrgMngList(param);
    
    	int totalCnt = orgMngService.selectOrgMngTotalCount(param);
    	
    	
    			
		model.addAttribute("orgMngList", list);
    	model.addAttribute("orgMngTotal", totalCnt);
    	model.addAttribute("orgMngPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 기관 상세보기
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	 * @param orgMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/orgMngDetail.do")
	public String orgMngDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
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
		if(param.get("org_id") == null) {
			return "redirect:/opsmng/orgMng/orgMngList.do";
		}
		
		Map<String,Object> dtl = orgMngService.selectOrgMngDtl(param);
	
		model.addAttribute("data", dtl);
		model.addAttribute("orgMng", param);
		model.addAttribute("org_class_code", getCodeList("ORG_CLASS"));
		
		return "opsmng/orgMng/orgMngDetail.mngPlatForm";
			
	}

	
	/**
	 * <pre>
	 * 기관관리
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/updateOrgMng.do")
	public String updateOrgMng(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("org_id") == null) {
			return "redirect:/opsmng/orgMng/orgMngList.do";
		}
		
		result= orgMngService.updateOrgMng(param, userVo);
		
		model.addAttribute("result",result);
		
		
		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 기관 관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.	
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/orgMngWrite.do")
	public String orgMngWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("orgMng", param);
		model.addAttribute("org_class_code", getCodeList("ORG_CLASS"));
		
		return "opsmng/orgMng/orgMngWrite.mngPlatForm";
			
	}
	/**
	 * <pre>
	 * 기관관리
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/insertOrgMng.do")
	public String insertOrgMng(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("org_id") == null) {
			return "redirect:/opsmng/orgMng/orgMngList.do";
		}
		
		result= orgMngService.insertOrgMng(param, userVo);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("org_id", param.get("org_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/opsmng/orgMng/orgMngDetail.do";
	}
	
	/**
	 * <pre>
	 * 기관관리
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param model
	
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/orgMng/deleteOrgMng.do")
	public String deleteOrgMng(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("org_id") == null) {
			return "redirect:/opsmng/orgMng/orgMngList.do";
		}
		
		result= orgMngService.deleteOrgMng(param, userVo);
		
	
		
		return "redirect:/opsmng/orgMng/orgMngList.do";
	}
	
	
}