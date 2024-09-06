package kr.go.rastech.ptl.apply.ann.controller;



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
import kr.go.rastech.ptl.apply.ann.service.AnnService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 공고현황 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class AnnController extends BaseController {

	@Resource
	private AnnService annService;
	
	@Resource
	private OrgMngService orgMngService;
	

	

	/**
	 * <pre>
	 * 공고현황 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/ann/annList.do")
	public String annList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		model.addAttribute("annParam", param);
		
		return "/apply/ann/annList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 공고현황 리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/apply/ann/readAnnList.do")  
	public String readApprovalList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = annService.selectAnnList(param);
    
    	int totalCnt = annService.selectAnnListCount(param);
    	
		model.addAttribute("annList", list);
    	model.addAttribute("annTotal", totalCnt);
    	model.addAttribute("annPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	
	/**
	 * <pre>
	 * 공고현황 등록 페이지 이동
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("apply/ann/annWrite.do")
	public String annWrite(Model model,  HttpServletRequest request, UserVo userVo) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		userVo = getUser();
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		model.addAttribute("annParam", param);
		
		return "/apply/ann/annWrite.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 공고현황 등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/ann/insertAnn.do")
	public String insertAnn(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// login 확인
		if(userVo == null){
			return "redirect:/index/index.do";
		}
		String loginKey = userVo.getEmplyrkey();
		
		String createId = loginKey;
		param.put("create_id", createId);
		
		// key 확인		
		if(param.get("ann_id") == null) {
			return "redirect:/apply/ann/annList.do";
		}
		
		result = annService.insertAnn(param);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("ann_id", param.get("ann_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/apply/ann/annDetail.do";
	}
	
	
	/**
	 * <pre>
	 * 공고현황 상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/ann/annDetail.do")
	public String annDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("ann_id") == null) {
			return "redirect:/apply/ann/annList.do";
		}
		
		// 공고현황 정보
		Map<String,Object> dtl = annService.selectAnnDtl(param);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		model.addAttribute("data", dtl);
		model.addAttribute("annParam", param);
		
		return "apply/ann/annDetail.subPlatForm";
	}


	/**
	 * <pre>
	 * 공고현황 수정
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/ann/updateAnn.do")
	public String updateAnn(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		param.put("modify_id", userVo.getEmplyrkey());
		// 로그인 확인
		
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("ann_id") == null) {
			return "redirect:/apply/ann/annList.do";
		}
		
		result= annService.updateAnn(param);
		
		model.addAttribute("result",result);
		
		
		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 공고현황 삭제시 ann_id 체크
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/ann/chkAnnId.do")
	public String chkAnnId(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("ann_id") == null) {
			return "redirect:/apply/ann/annList.do";
		}
		
		result= annService.chkAnnId(param);
		
		model.addAttribute("result",result);
		
		
		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 공고현황 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/ann/deleteAnn.do")
	public String deleteAnn(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		param.put("modify_id", userVo.getEmplyrkey());
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("ann_id") == null) {
			return "redirect:/apply/ann/annList.do";
		}
		
		result= annService.deleteAnn(param);
		
		return "redirect:/apply/ann/annList.do";
	}

}