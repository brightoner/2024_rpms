package kr.go.rastech.ptl.execute.bgMake.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.execute.bgMake.service.BgMakeService;
import kr.go.rastech.ptl.execute.plan.service.PlanService;

/**
 * 
 *  예산 편성과 예산 집행을 관리한다. 
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
@Controller
public class BgMakeController extends BaseController {

	@Resource
	private PlanService planService;
	
	
	@Resource
	private BgMakeService bgMakeService;
	

	/**
	 * <pre>
	 * 예산편성 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/bgMake/projBgMakeList.do")
	public String projBgMakeList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
	
	
	
		model.addAttribute("bgMake", param);
		
		return "execute/bgMake/projBgMakeList.intiles";
		//return "execute/bgMake/projBgMakeList.mngPlatForm";
		
	}
	
	

	/**
	 * <pre>
	 * 예산편성 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/bgExec/projBgExecList.do")
	public String projBgExecList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
	
	
	
		model.addAttribute("bgExec", param);
		
		return "execute/bgExec/projBgExecList.intiles";
		//return "execute/bgMake/projBgMakeList.mngPlatForm";
		
	}
	
	
	/**
	 * <pre>
	 * 계정과목 목록을 불러온다 (계정과목 관리 테이블)
	 * 계정과목을 트리구조로 보여주기위함
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/bgMake/readProjAccountSubjectList.do")  
	public String readProjBgMakeList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);


    	
    	List<Map<String,Object>> acctSubjectlist = bgMakeService.selectProjAccountSubjectList(param);
    	List<Map<String,Object>> list = bgMakeService.selectProjBgMakeList(param);
   
    			
		model.addAttribute("bgMakeList", list);
		model.addAttribute("acctSubjectlist", acctSubjectlist);

    	return "jsonView";
	}
	
	/**
	 * <pre>
	 * 예산 편성 및 집행 화면  데이터 조회 , 
	 * 동일 테이블의 동일 정보로 url만 다르게 하여 각각 호출
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value= {"/execute/bgMake/readAcctSubjRowList.do" ,  "/execute/bgExec/readAcctSubjRowList.do" })  
	public String readAcctSubjRowList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);


    	
    
    	List<Map<String,Object>> list = bgMakeService.selectProjBgMakeList(param);
   
    			
		model.addAttribute("rowAcctSubjList", list);
		

    	return "jsonView";
	}
	
	// 예산 편성 시 편성 계정과목을 생성
	@RequestMapping(value="/execute/bgMake/insertProjAcctSubj.do")  
	 public String insertProjAcctSubj(@RequestBody  Map<String, Object> params , Model model) throws IOException, SQLException , NullPointerException  {
		// params는 JSON 배열로부터 파싱된 List<Map<String, String>> 형식입니다.
		UserVo vo = getUser();
	  
		
		    if(vo != null) {
		    	bgMakeService.insertProjAcctSubj(params);
				model.addAttribute("sMessage", "Y");
			}else {
				model.addAttribute("sMessage", "N");
			}
			
		    
	        
	        return "jsonView";
    }
	
	// 예산 편성 update 
	@RequestMapping(value="/execute/bgMake/updateProjBudget.do")  
	 public String updateProjBudget(@RequestBody  Map<String, Object> params , Model model) throws IOException, SQLException , NullPointerException  {
		// params는 JSON 배열로부터 파싱된 List<Map<String, String>> 형식입니다.
		UserVo vo = getUser();
	  
		
		    if(vo != null) {
		    	bgMakeService.updateProjBudget(params);
				model.addAttribute("sMessage", "Y");
			}else {
				model.addAttribute("sMessage", "N");
			}
			
		    
	        
	        return "jsonView";
	    }
	
	// 예산 집행 update 
	@RequestMapping(value="/execute/bgExec/updateProjBudgetExec.do")  
	 public String updateProjBudgetExec(@RequestBody  Map<String, Object> params , Model model) throws IOException, SQLException , NullPointerException  {
		// params는 JSON 배열로부터 파싱된 List<Map<String, String>> 형식입니다.
			UserVo vo = getUser();

		    if(vo != null) {
		    	bgMakeService.updateProjBudgetExec(params);
				model.addAttribute("sMessage", "Y");
			}else {
				model.addAttribute("sMessage", "N");
			}
			

	        return "jsonView";
	    }
	
	
}