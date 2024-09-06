package kr.go.rastech.ptl.execute.evaluate.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.execute.evaluate.service.EvalService;
import kr.go.rastech.ptl.execute.plan.service.PlanService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 평가관리 처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class EvalController extends BaseController {

	@Resource
	private EvalService evalService;
	
	
	@Resource
	private PlanService planService;
	
	
	@Resource
	private OrgMngService orgMngService;
	
	/**
	 * <pre>
	 * 연차과제계획 관리 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/evaluate/projEvalList.do")
	public String projEvalList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		
		model.addAttribute("evalParam", param);
		
		return "/execute/evaluate/projEvalList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 연차과제계획 관리 리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/evaluate/readProjEvalList.do")  
	public String readProjEvalList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = evalService.selectProjEvalList(param);
    	int totalCnt = evalService.selectProjEvalListCount(param);
    	
		model.addAttribute("evalList", list);
    	model.addAttribute("evalTotal", totalCnt);
    	model.addAttribute("evalPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 연차과제계획 관리 상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/evaluate/projEvalDetail.do")
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
		if(param.get("proj_year_id") == null) {
			return "redirect:/execute/evaluate/projEvalList.do";
		}
		
		// 연차과제정보
		Map<String,Object> dtl = evalService.selectProjEvalDtl(param);
		
		// 연차과제 실무책임자 정보 
		List<Map<String,Object>> resYearpList =  planService.selectYearRespPerson(param);
		if(CollectionUtils.isEmpty(resYearpList)) {
			model.addAttribute("resYearpList", null);
		}else {
			model.addAttribute("resYearpList", resYearpList);
		}
		
		
		model.addAttribute("data", dtl);

		model.addAttribute("evalParam", param);
		
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));		// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));		// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));				// 공통코드 - RND여부
		model.addAttribute("endGbList", getCodeList("YEAR_END_GB"));		// 공통코드 - 최종여부
		model.addAttribute("endEvalList", getCodeList("END_EVAL"));			// 공통코드 - 최종평가
		model.addAttribute("yearEvalList", getCodeList("YEAR_EVAL"));		// 공통코드 - 연차평가

		return "/execute/evaluate/projEvalDetail.subPlatForm";
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
	@RequestMapping("/execute/evaluate/insertProjEval.do")
	public String insertProjEval(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// login 확인
		String loginKey = userVo.getEmplyrkey();
		String createId = loginKey;
		param.put("create_id", createId);
		
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("proj_year_id") == null) {
			return "redirect:/execute/evaluate/projEvalList.do";
		}
		
		result = evalService.insertProjEval(param);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("proj_year_id", param.get("proj_year_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/execute/evaluate/projEvalDetail.do";
	}
	
	


}