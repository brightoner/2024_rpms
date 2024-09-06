package kr.go.rastech.ptl.execute.reg.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.apply.req.service.ReqService;
import kr.go.rastech.ptl.execute.reg.service.RegService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 연차과제생성 처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class RegController extends BaseController {

	@Resource
	private RegService regService;
	
	@Resource
	private ReqService reqService;
	
	@Resource
	private OrgMngService orgMngService;
	

	

	/**
	 * <pre>
	 * 협약과제 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/reg/projRegList.do")
	public String projRegList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		
		model.addAttribute("regParam", param);
		
		return "/execute/reg/projRegList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 협약과제 리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/reg/readProjRegList.do")  
	public String readProjRegList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = regService.selectProjRegList(param);
    	int totalCnt = regService.selectProjRegListCount(param);
    	
		model.addAttribute("regList", list);
    	model.addAttribute("regTotal", totalCnt);
    	model.addAttribute("regPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}

	
	
	/**
	 * <pre>
	 * 협약과제 상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/reg/projRegDetail.do")
	public String agmtProjDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("proj_id") == null) {
			return "redirect:/execute/reg/projRegList.do";
		}
		
		// 과제협약 정보
		Map<String,Object> dtl = regService.selectProjRegDtl(param);
		
		// 참여기관 정보
		param.put("proj_id", dtl.get("proj_id"));
		List<Map<String,Object>> partList =  reqService.selectPartOrg(param);
		if(CollectionUtils.isEmpty(partList)) {
			model.addAttribute("partList", null);
		}else {
			model.addAttribute("partList", partList);
		}
		
		// 총괄책임자 정보 
		Map<String,Object> lRespMap =  reqService.selectLRespPerson(param);
		if(MapUtils.isEmpty(lRespMap)) {
			model.addAttribute("lRespMap", null);
		}else {
			model.addAttribute("lRespMap", lRespMap);
		}
		
		// 실무책임자 정보 
		List<Map<String,Object>> respList =  reqService.selectRespPerson(param);
		if(CollectionUtils.isEmpty(respList)) {
			model.addAttribute("respList", null);
		}else {
			model.addAttribute("respList", respList);
		}
		
				
		model.addAttribute("data", dtl);
		model.addAttribute("regParam", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));		// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));		// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));	// 공통코드 - RND여부
		
		return "/execute/reg/projRegDetail.subPlatForm";
	}
	
	
	
	
	/**
	 * <pre>
	 * 연차과제 등록 페이지 이동
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */	
	@RequestMapping("/execute/reg/projPlanWrite.do")
	public String projPlanWrite(Model model,  HttpServletRequest request, UserVo userVo) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null){
			param = (Map<String, Object>) flashMap.get("paramMap");
	    }
		
		userVo = getUser();
		if(userVo == null){
			return "redirect:/index/index.do";
		}
		// key 확인		
		if(param.get("proj_id") == null) {
			return "redirect:/execute/reg/projRegList.do";
		}
		
		// 과제협약 정보
		Map<String,Object> dtl = regService.selectProjRegDtl(param);
		
		// 참여기관 정보
		param.put("proj_id", dtl.get("proj_id"));
		List<Map<String,Object>> partList =  reqService.selectPartOrg(param);
		if(CollectionUtils.isEmpty(partList)) {
			model.addAttribute("partList", null);
		}else {
			model.addAttribute("partList", partList);
		}
		
		// 총괄책임자 정보 
		Map<String,Object> lRespMap =  reqService.selectLRespPerson(param);
		if(MapUtils.isEmpty(lRespMap)) {
			model.addAttribute("lRespMap", null);
		}else {
			model.addAttribute("lRespMap", lRespMap);
		}
		
		// 실무책임자 정보 
		List<Map<String,Object>> respList =  reqService.selectRespPerson(param);
		if(CollectionUtils.isEmpty(respList)) {
			model.addAttribute("respList", null);
		}else {
			model.addAttribute("respList", respList);
		}
		
				
		model.addAttribute("data", dtl);
		model.addAttribute("reg", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));		// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));		// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));	// 공통코드 - RND여부
		
		return "/execute/reg/projPlanWrite.subPlatForm";
			
	}
	

	
	/**
	 * <pre>
	 * 연차과제 등록 수행
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/reg/insertProjPlan.do")
	public String insertProjPlan(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("proj_id") == null) {
			return "redirect:/execute/reg/projRegList.do";
		}
		
		result = regService.insertProjYear(param);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("proj_id", param.get("proj_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/execute/reg/projRegDetail.do";
	}
	
	
	/**
	 * <pre>
	 * 연차과제 생성시 생성 연차 확인
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/reg/chkYear.do")
	public String chkYear(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("proj_id") == null) {
			return "redirect:/execute/reg/projRegList.do";
		}
		
		result= regService.chkYear(param);
		
		model.addAttribute("result",result);
		
		
		return "jsonView";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

}