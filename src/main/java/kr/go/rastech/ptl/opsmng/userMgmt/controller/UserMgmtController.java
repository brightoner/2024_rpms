package kr.go.rastech.ptl.opsmng.userMgmt.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.opsmng.userMgmt.service.UserMgmtService;


/**
 * 
 * 사용자 목록 관리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2023. 06. 02.
 */
@Controller
public class UserMgmtController extends BaseController {

	@Resource
	private UserMgmtService userMgmtService;
	


	/**
	 * <pre>
	 * 사용자목록 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/userMgmt/userMgmtList.do")
	public String approvalList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);


		List<Map<String,Object>> list =  null;
		
		model.addAttribute("mailTempList", list);
		
		model.addAttribute("userMgmtParam", param);
		
		return "/opsmng/userMgmt/userMgmtList.mngPlatForm";
			
	}
	
	
	/**
	 * <pre>
	 * 사용자 목록 관리 list
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/userMgmt/readUserMgmtList.do")  
	public String readApprovalList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		String xml = "";
    	
    	List<Map<String,Object>> list = userMgmtService.selectUserMgmtList(param);
    
    	int totalCnt = userMgmtService.selectUserMgmtTotalCount(param);
    	
    	
    			
		model.addAttribute("userList", list);
    	model.addAttribute("userTotal", totalCnt);
    	model.addAttribute("userPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 사용자 목록 상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param model
	 * @param approvalVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/userMgmt/userMgmtDetail.do")
	public String approvalDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
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
		if(param.get("bj_approval_id") == null) {
			return "redirect:/opsmng/userMgmt/userMgmtList.do";
		}
	
		Map<String,Object> dtl = userMgmtService.selectUserMgmtDtl(param);
	
		model.addAttribute("data", dtl);
		model.addAttribute("userMgmtParam", param);
		
		return "opsmng/userMgmt/userMgmtDetail.mngPlatForm";
			
	}



	/**
	 * <pre>
	 * 사용자 목록 관리 list
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/userMgmt/sendUserMail.do")  
	public String sendMail( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		String sendYn = "Y";
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = null;// getUser();
		
		if(userVo == null){
			sendYn = "N";
			
		}
    	int resultCnt  = userMgmtService.selectUserSendMail(param);
		
    	model.addAttribute("sendYn", sendYn);
		model.addAttribute("resultCnt", resultCnt);
    	
		
    	return "jsonView";
	}

	
	
}