package kr.go.rastech.ptl.follow.planInfo.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.apply.req.service.ReqService;
import kr.go.rastech.ptl.follow.planInfo.service.EndPlanInfoService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 종료사후관리 기본정보  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class EndPlanInfoController extends BaseController {

	@Resource
	private EndPlanInfoService endplanInfoService;
	
	@Resource
	private OrgMngService orgMngService;
	
	@Resource
	private ReqService reqService;
	
	
	
	/**
	 * <pre>
	 * 종료된 협약과제 list (종료사후과제의 대상 목록 list) ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/planInfo/selectEndTargetList.do")  
	public String selectEndTargetList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = endplanInfoService.selectEndTargetList(param);
    	int totalCnt = endplanInfoService.selectEndTargetListCount(param);
    	
		model.addAttribute("targetList", list);
    	model.addAttribute("targetTotal", totalCnt);
    	model.addAttribute("targetPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}
	
	
	
	/**
	 * <pre>
	 * 종료과제 유무 확인 
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/year/employ/chkEndPlan.do")
	public String chkEndPlan(Model model,  @RequestBody  Map<String, Object> param) throws IOException, SQLException , NullPointerException,FdlException {
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("proj_id_target") == null) {
			return "redirect:/follow/end/planInfo/endFollowList.do";
		}
		
		String proj_id_target = Objects.toString(param.get("proj_id_target") ,"") ;
		String proj_end_gb = Objects.toString(param.get("proj_end_gb") ,"") ;
		param.put("proj_id_target", proj_id_target);
		param.put("proj_end_gb", proj_end_gb);
		
		UserVo vo = getUser();
	  	if(vo != null) {
			if(StringUtils.isNotBlank( Objects.toString(param.get("proj_id_target"),"")) 
					&& StringUtils.isNotBlank( Objects.toString(param.get("proj_end_gb"),""))){
				result= endplanInfoService.chkEndPlan(param);
				model.addAttribute("sMessage", "Y");
			}
		}
		model.addAttribute("result",result);
		
		
		return "jsonView";
	}
	
	
	
	
	/**
	 * <pre>
	 * 종료과제 생성
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/employ/insertEndPlan.do")  
	 public String insertEndPlan( @RequestBody  Map<String, Object> param , Model model) throws IOException, SQLException , NullPointerException  {
		
		String proj_id_target = Objects.toString(param.get("proj_id_target") ,"") ;
		String proj_end_gb = Objects.toString(param.get("proj_end_gb") ,"") ;
		String end_file_group = Objects.toString(param.get("end_file_group") ,"") ;
		param.put("proj_id_target", proj_id_target);
		param.put("proj_end_gb", proj_end_gb);
		param.put("end_file_group", end_file_group);
		
		UserVo vo = getUser();
		  	if(vo != null) {
				if(StringUtils.isNotBlank( Objects.toString(param.get("proj_id_target"),"")) 
						&& StringUtils.isNotBlank( Objects.toString(param.get("proj_end_gb"),"")) 
						&& StringUtils.isNotBlank( Objects.toString(param.get("end_file_group"),""))){
					param.put("create_id", vo.getEmplyrkey());		
					param.put("modify_id", vo.getEmplyrkey());		
					endplanInfoService.insertEndPlan(param);
					model.addAttribute("sMessage", "Y");
				}else {
					model.addAttribute("sMessage", "F");	
				}
			}else {
				model.addAttribute("sMessage", "N");
			}
        return "jsonView";
   }
	
	
	
	
	
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/planInfo/endFollowList.do")
	public String endFollowList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
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
		
		return "/follow/end/planInfo/endFollowList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/planInfo/readEndFollowList.do")  
	public String readEndFollowList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = endplanInfoService.selectEndPlanInfoList(param);
    	int totalCnt = endplanInfoService.selectEndPlanInfoListCount(param);
    	
		model.addAttribute("planList", list);
    	model.addAttribute("planTotal", totalCnt);
    	model.addAttribute("planPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 종료사후관리 기본정보  상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/planInfo/endFollowDetail.do")
	public String endFollowDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("proj_end_id") == null) {
			return "redirect:/follow/end/planInfo/endFollowList.do";
		}
		
		// 종료과제정보
		Map<String,Object> dtl = endplanInfoService.selectEndPlanInfoDtl(param);
		
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
		model.addAttribute("followParam", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));		// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));		// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));				// 공통코드 - RND여부

		return "/follow/end/planInfo/endFollowDetail.subPlatForm";
	}
	
	
	
	


}