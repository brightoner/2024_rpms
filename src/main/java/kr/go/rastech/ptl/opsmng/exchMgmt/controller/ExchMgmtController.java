package kr.go.rastech.ptl.opsmng.exchMgmt.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.imgFile.service.ImgFileService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ImgFileUtil;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.opsmng.exchMgmt.service.ExchMgmtService;






/**
 * <pre>
 * FileName: ExchMgmtController.java
 * Package :  kr.go.rastech.ptl.usermng.exchMgmt.controller
 * 
 * 환전  관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 3. 28.
 */
@Controller
public class ExchMgmtController extends BaseController {

	@Resource
	private ExchMgmtService exchMgmtService;
	
	
    /** ImgFileUtil */
    @Resource
    private ImgFileUtil imgFileUtil;

	
    /** ImgFileService */
    @Resource
    private ImgFileService imgFileService;

    
    
	/**
	 * <pre>
	 * 환전하기
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/exchMgmt/exchMgmtList.do")
	public String exchMgmtList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo == null){
			request.getSession().setAttribute("message", "접근 권한이 없습니다.<br> 로그인 후 이용해 주십시오");
			return "redirect:/index/index.do";
		}		
		
		
		model.addAttribute("exch", param);

		return "/opsmng/exchMgmt/exchMgmtList.mngPlatForm";
			
	}
    
    

	
	/**
	 * <pre>
	 * 환전 내역 정보
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 09.
	 * @param request
	 * @param resp
	 * @param out
	 * @return 
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/exchMgmt/readExchMgmtList.do")  
	public String readExchMgmtList(HttpServletRequest request,  Model model, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException  {
		
		UserVo userVo = getUser();
		List<Map<String,Object>> list  = null;
		int totalCnt = 0;
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
	
		
		if(userVo != null){	
			
			String emplyrkey = userVo.getEmplyrkey();
			param.put("emplyrkey", emplyrkey);
			

			list = exchMgmtService.selectExchInfoList(param);
			
			totalCnt = exchMgmtService.selectExchInfoCount(param);


			model.addAttribute("exchInfoList", list);
	    	model.addAttribute("exchInfoListTotal", totalCnt);
	    	model.addAttribute("exchInfoListPageTotal", Math.ceil((double)totalCnt/(double)10));
		}

		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 환전 내역 상세 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param notiMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/exchMgmt/exchMgmtDetail.do")
	public String exchMgmtDetail(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo == null){
			request.getSession().setAttribute("message", "다시 로그인 후 이용해 주십시오");
			return "redirect:/index/index.do";
		}		
		
		
		// key 확인		
		if(param.get("exch_base_id") == null) {
			return "redirect:/opsmng/exchMgmt/exchMgmtList.do";
		}
	
		Map<String,Object> dtl = exchMgmtService.selectExchBaseInfoDtl(param);
		
	
		
		model.addAttribute("data", dtl);		
	
		model.addAttribute("exch", param);
	
		
		return "opsmng/exchMgmt/exchMgmtDetail.mngPlatForm";
			
	}
	

	/**
	 * <pre>
	 * 환전 수량 정보
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 09.
	 * @param request
	 * @param resp
	 * @param out
	 * @return 
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/exchMgmt/readUserExchSituation.do")  
	public String readExchSituation(HttpServletRequest request,  Model model, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException  {
		
		UserVo userVo = getUser();
		Map<String,Object> situationData  = null;

		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		
		situationData = exchMgmtService.selectUserExchSituationData(param);
	
		model.addAttribute("situationData", situationData);

		return "jsonView";
	}
	
	
	@RequestMapping("/opsmng/exchMgmt/saveExchState.do")	
	public String saveExchState(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		String returnVal = "ok";
		String loginChk = "N";
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();		
		if(userVo == null){		
			loginChk = "N";
		}else {
			
			loginChk = "Y";
			String loginKey = userVo.getEmplyrkey();

			param.put("modify_id", loginKey);
			param.put("emplyrkey", param.get("req_emplyrkey"));
			
			seq = exchMgmtService.updateExchState(param);	
			
			
		   if(seq > 0) {
			   returnVal = "ok";
		   }else {
			   returnVal = "no";
		   }
		   
		   
	
		}	
		model.addAttribute("successYn", returnVal);
		model.addAttribute("loginChk", loginChk);
				
	   return "jsonView";
	}
	
	
}