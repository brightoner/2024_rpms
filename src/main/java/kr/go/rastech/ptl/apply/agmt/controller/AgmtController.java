package kr.go.rastech.ptl.apply.agmt.controller;



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
import kr.go.rastech.ptl.apply.agmt.service.AgmtService;
import kr.go.rastech.ptl.apply.req.service.ReqService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 과제협약 처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class AgmtController extends BaseController {

	@Resource
	private AgmtService agmtService;
	
	@Resource
	private ReqService reqService;
	
	@Resource
	private OrgMngService orgMngService;
	

	

	/**
	 * <pre>
	 * 과제협약 처리 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/agmt/agmtProjList.do")
	public String agmtProjList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);

		model.addAttribute("agmtParam", param);
		
		return "/apply/agmt/agmtProjList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 과제협약 처리 리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/apply/agmt/readAgmtProjList.do")  
	public String readAgmtProjList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = agmtService.selectAgmtList(param);
    	int totalCnt = agmtService.selectAgmtListCount(param);
    	
		model.addAttribute("agmtList", list);
    	model.addAttribute("agmtTotal", totalCnt);
    	model.addAttribute("agmtPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	
	
	/**
	 * <pre>
	 * 과제협약 처리 상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/agmt/agmtProjDetail.do")
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
		if(param.get("ann_id") == null) {
			return "redirect:/apply/agmt/agmtProjList.do";
		}
		
		// 과제협약 정보
		Map<String,Object> dtl = reqService.selectReqDtl(param);
		
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
		model.addAttribute("agmtParam", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));	// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));	// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));	// 공통코드 - RND여부
		
		return "apply/agmt/agmtProjDetail.subPlatForm";
	}


	/**
	 * <pre>
	 * 과제협약 처리 수정
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/agmt/updateAgmtProj.do")
	public String updateAgmtProj(Model model, @RequestBody  Map<String, Object> param, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		param.put("modify_id", userVo.getEmplyrkey());
		// 로그인 확인
		
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		
		// key 확인		
		if(param.get("proj_id") == null) {
			return "redirect:/apply/agmt/agmtProjList.do";
		}
		
		result= reqService.updateReq(param);
		
		model.addAttribute("result",result);
		
		return "jsonView";
	}
	
	
	

}