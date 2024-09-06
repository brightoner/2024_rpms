package kr.go.rastech.ptl.follow.year.planInfo.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.execute.plan.service.PlanService;
import kr.go.rastech.ptl.follow.year.planInfo.service.PlanInfoService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 연차사후관리 기본정보  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class PlanInfoController extends BaseController {

	@Resource
	private PlanInfoService planInfoService;
	
	
	@Resource
	private PlanService planService;
	
	
	@Resource
	private OrgMngService orgMngService;
	
	/**
	 * <pre>
	 * 연차사후관리 기본정보 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/year/planInfo/yearFollowList.do")
	public String yearFollowList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		model.addAttribute("followParam", param);
		
		return "/follow/year/planInfo/yearFollowList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 연차사후관리 기본정보  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/planInfo/readYearFollowList.do")  
	public String readYearFollowList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = planInfoService.selectPlanInfoList(param);
    	int totalCnt = planInfoService.selectPlanInfoListCount(param);
    	
		model.addAttribute("planList", list);
    	model.addAttribute("planTotal", totalCnt);
    	model.addAttribute("planPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 연차사후관리 기본정보  상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/year/planInfo/yearFollowDetail.do")
	public String yearFollowDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
			return "redirect:/follow/year/planInfo/yearFollowList.do";
		}
		
		// 연차과제정보
		Map<String,Object> dtl = planInfoService.selectPlanInfoDtl(param);
		
		// 연차과제 실무책임자 정보 
		List<Map<String,Object>> resYearpList =  planService.selectYearRespPerson(param);
		if(CollectionUtils.isEmpty(resYearpList)) {
			model.addAttribute("resYearpList", null);
		}else {
			model.addAttribute("resYearpList", resYearpList);
		}
		
		
		model.addAttribute("data", dtl);
		model.addAttribute("followParam", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));		// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));		// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));				// 공통코드 - RND여부
		model.addAttribute("endGbList", getCodeList("YEAR_END_GB"));		// 공통코드 - 최종여부
		model.addAttribute("endEvalList", getCodeList("END_EVAL"));			// 공통코드 - 최종평가
		model.addAttribute("yearEvalList", getCodeList("YEAR_EVAL"));		// 공통코드 - 연차평가

		return "/follow/year/planInfo/yearFollowDetail.subPlatForm";
	}
	
	
	
	


}