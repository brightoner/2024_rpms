package kr.go.rastech.ptl.apply.req.controller;



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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.apply.req.service.ReqService;
import kr.go.rastech.ptl.opsmng.orgMng.service.OrgMngService;


/**
 * 
 * 연구과제신청 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class ReqController extends BaseController {

	@Resource
	private ReqService reqService;
	
	@Resource
	private OrgMngService orgMngService;
	

	

	/**
	 * <pre>
	 * 연구과제 신청 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/reqProjList.do")
	public String reqProjList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// 소관부처 select
		param.put("org_gb", "1");
		List<Map<String, Object>> deptList = orgMngService.selectOrgMngCodeList(param);
		//전담기관 select
		param.put("org_gb", "2");
		List<Map<String, Object>> ddctList = orgMngService.selectOrgMngCodeList(param);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("ddctList", ddctList);
		
		model.addAttribute("reqParam", param);
		
		return "/apply/req/reqProjList.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/apply/req/readReqProjList.do")  
	public String readReqProList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = reqService.selectReqList(param);
    	int totalCnt = reqService.selectReqListCount(param);
    	
		model.addAttribute("reqList", list);
    	model.addAttribute("reqTotal", totalCnt);
    	model.addAttribute("reqPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	
	/**
	 * <pre>
	 * 연구과제 신청 등록 페이지 이동
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/reqProjWrite.do")
	public String reqProjWrite(Model model,  HttpServletRequest request, UserVo userVo) throws IOException, SQLException , NullPointerException {
	
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
		if(param.get("ann_id") == null) {
			return "redirect:/apply/req/reqProjList.do";
		}
		
		// 연구과제, 공고 정보
		Map<String,Object> dtl = reqService.selectReqDtl(param);
		
		model.addAttribute("reqParam", param);
		
		model.addAttribute("data", dtl);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));	// 공통코드 - 과제유형 
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));	// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));	// 공통코드 - RND여부
		
		return "/apply/req/reqProjWrite.subPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/insertReqProj.do")
	public String insertReq(Model model, HttpServletRequest request, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		int result = 0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
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
			return "redirect:/apply/req/reqProjList.do";
		}
		
		result = reqService.insertReq(param);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("proj_id", param.get("proj_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/apply/req/reqProjDetail.do";
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/reqProjDetail.do")
	public String reqProjDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
			return "redirect:/apply/req/reqProjList.do";
		}
		
		// 연구과제 신청정보
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
		model.addAttribute("reqParam", param);
		model.addAttribute("projTyList", getCodeList("PROJ_TYPE_CD"));	// 공통코드 - 과제유형
		model.addAttribute("secuList", getCodeList("SECURITY_LEVL"));	// 공통코드 - 보안등급
		model.addAttribute("rndList", getCodeList("RND_GB"));	// 공통코드 - RND여부
		
		return "apply/req/reqProjDetail.subPlatForm";
	}


	/**
	 * <pre>
	 * 연구과제 신청 수정
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/updateReqProj.do")
	public String updateReq(Model model, @RequestBody  Map<String, Object> param, RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
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
			return "redirect:/apply/req/reqList.do";
		}
		
		result= reqService.updateReq(param);
		
		model.addAttribute("result",result);
		
		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 삭제시 선정단계인지 체크 - SEL_GB : 0 만 삭제 가능 
	 *
	 * </pre>
	 * @author : ljk  
	 * @date   : 2024. 06. 17.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/chkSelGb.do")
	public String chkSelGb(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
			return "redirect:/apply/req/reqProjList.do";
		}
		
		result= reqService.chkSelGb(param);
		
		model.addAttribute("result",result);
		
		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 연구과제 신청 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/apply/req/deleteReqProj.do")
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
		if(param.get("ann_id") == null) {
			return "redirect:/apply/req/reqProjList.do";
		}
		
		result= reqService.deleteReq(param);
		
		return "redirect:/apply/req/reqProjList.do";
	}

}