package kr.go.rastech.ptl.execute.plan.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.apply.req.service.ReqService;
import kr.go.rastech.ptl.execute.bgMake.service.BgMakeService;
import kr.go.rastech.ptl.execute.plan.service.PlanService;
import kr.go.rastech.ptl.execute.wbs.service.WbsService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 연차과제계획 관리 처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class PlanController extends BaseController {

	@Resource
	private PlanService planService;
	
	@Resource
	private ReqService reqService;
	
	@Resource
	private OrgMngService orgMngService;
	
	@Resource
	private WbsService wbsService;
	

	@Resource
	private BgMakeService bgMakeService;
	

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
	@RequestMapping("/execute/plan/projPlanList.do")
	public String projPlanList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		model.addAttribute("dataParam", param);
		
		return "/execute/plan/projPlanList.subPlatForm";
			
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
	@RequestMapping(value="/execute/plan/readProjPlanList.do")  
	public String readProjPlanList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = planService.selectProjPlanList(param);
    	int totalCnt = planService.selectProjPlanListCount(param);
    	
		model.addAttribute("planList", list);
    	model.addAttribute("planTotal", totalCnt);
    	model.addAttribute("planPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	
	/**
	 * <pre>
	 * 연차과제계획 관리 등록 페이지 이동
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/plan/projPlanWrite.do")
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
			return "redirect:/execute/plan/projPlanList.do";
		}
		
		// 과제협약 정보
		Map<String,Object> dtl = planService.selectProjPlanDtl(param);
		
		model.addAttribute("plan", param);
		model.addAttribute("data", dtl);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));	// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));	// 공통코드 - 보안등급
		
		return "/execute/plan/projPlanWrite.subPlatForm";
			
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
	@RequestMapping("/execute/plan/projPlanDetail.do")
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
			return "redirect:/execute/plan/projPlanList.do";
		}
		
		// 연차과제정보
		Map<String,Object> dtl = planService.selectProjPlanDtl(param);
		
		// 연차과제 실무책임자 정보 
		List<Map<String,Object>> resYearpList =  planService.selectYearRespPerson(param);
		if(CollectionUtils.isEmpty(resYearpList)) {
			model.addAttribute("resYearpList", null);
		}else {
			model.addAttribute("resYearpList", resYearpList);
		}
		
		
		//  협약과제 참여기관 정보
		param.put("proj_id", dtl.get("proj_id"));
		List<Map<String,Object>> partList =  reqService.selectPartOrg(param);
		if(CollectionUtils.isEmpty(partList)) {
			model.addAttribute("partList", null);
		}else {
			model.addAttribute("partList", partList);
		}
		
		// 협약과제 총괄책임자 정보 
		Map<String,Object> lRespMap =  reqService.selectLRespPerson(param);
		if(MapUtils.isEmpty(lRespMap)) {
			model.addAttribute("lRespMap", null);
		}else {
			model.addAttribute("lRespMap", lRespMap);
		}
		
		
		
		// 협약과제 실무책임자 정보 
		List<Map<String,Object>> respList =  reqService.selectRespPerson(param);
		if(CollectionUtils.isEmpty(respList)) {
			model.addAttribute("respList", null);
		}else {
			model.addAttribute("respList", respList);
		}
		
		
		
		// wbs 정보 
		Map<String,Object> wbsBaseInfo = wbsService.selectWbsInfo(param);
		
		// 예산 정보 
		List<Map<String,Object>> budGetlist = bgMakeService.selectProjBgMakeList(param);
				
		
		model.addAttribute("data", dtl);
		model.addAttribute("plan", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));		// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));		// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));	// 공통코드 - RND여부

		model.addAttribute("wbsBaseInfo", wbsBaseInfo);
		model.addAttribute("budGetlist", budGetlist);

		
		return "/execute/plan/projPlanDetail.subPlatForm";
	}

	
	
	/**
	 * <pre>
	 * 연차과제계획 관리 수정
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value = "/execute/plan/updateProjPlan.do")
	public String updateProjPlan(@RequestBody  Map<String, Object> param, Model model) throws IOException, SQLException , NullPointerException,FdlException {
		
//		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		param.put("modify_id", userVo.getEmplyrkey());
		// 로그인 확인
		
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("proj_year_id") == null) {
			return "redirect:/execute/plan/projPlanList.do";
		}
		
		result= planService.updateProjPlan(param);
		
		model.addAttribute("result",result);
		
		return "jsonView";
	}
	
	
	
	/**
	 * <pre>
	 * 연차과제계획 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/plan/deleteProjPlan.do")
	public String deleteReq(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("proj_year_id") == null) {
			return "redirect:/execute/plan/projPlanList.do";
		}
		
		result= planService.deleteProjPlan(param);
		
		return "redirect:/execute/plan/projPlanList.do";
	}


}