package kr.go.rastech.ptl.execute.wbs.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.execute.plan.service.PlanService;
import kr.go.rastech.ptl.execute.wbs.service.WbsService;

/**
 * 
 * 기관 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
@Controller
public class WbsController extends BaseController {

	@Resource
	private PlanService planService;
	
	
	@Resource
	private WbsService wbsService;
	

	@RequestMapping("/execute/wbsMng/excelDown.do")
	public String statsEexcelDown(Model model, HttpServletRequest request, String printTitle, String printData,
			String printHead, String printBody, HttpServletResponse response)
			throws IOException, SQLException, NullPointerException {

		/*
		 * java.text.SimpleDateFormat formatter = new
		 * java.text.SimpleDateFormat("yyyyMMdd"); String today =
		 * formatter.format(new java.util.Date()); String file_name =
		 * "kdcaExcelDown.xls"; response.setHeader("Content-Disposition",
		 * "attachment; filename="+today+"_"+file_name);
		 * response.setHeader("Content-Description", "JSP Generated Data");
		 * response.setContentType("application/vnd.ms-excel");
		 */

		model.addAttribute("printTitle", printTitle);
		model.addAttribute("printData", printData);
		model.addAttribute("printHead", printHead);
		model.addAttribute("printBody", printBody.replaceAll("	", "").replaceAll("(\r\n|\r|\n|\n\r)", ""));

		return "execute/wbsMng/statsExcelDownNew";
	}
	/**
	 * <pre>
	 * 기관 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/wbsMng/projWbsMngList.do")
	public String wbsList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
	
		// 실무책임자 정보 
		List<Map<String,Object>> respList =  planService.selectWbsYearRespPerson(param);
		if(CollectionUtils.isEmpty(respList)) {
			model.addAttribute("respWbsList", null);
		}else {
			model.addAttribute("respWbsList", respList);
		}
				
	
		model.addAttribute("wbs", param);
		
		return "execute/wbsMng/projWbsMngList.intiles";
	// return "execute/wbsMng/projWbsMngList.mngPlatForm";
		
	}
	
	
	/**
	 * <pre>
	 * wbs list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/wbsMng/readProjWbsMngList.do")  
	public String readWbsList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		Map<String,Object> info = wbsService.selectWbsInfo(param);
		Map<String, Object> wbsMap = new HashMap<String, Object>();
		wbsMap.put("wbs_id", info.get("wbs_id"));
    	
    	List<Map<String,Object>> list = wbsService.selectWbsList(wbsMap);
    
   
    			
		model.addAttribute("wbsList", list);
    

    	return "jsonView";
	}
	
	/**
	 * <pre>
	 * wbs 진척율
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/wbsMng/readWbsProRateList.do")  
	public String readWbsProRateList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		Map<String,Object> info = wbsService.selectWbsInfo(param);
		Map<String, Object> wbsMap = new HashMap<String, Object>();
		wbsMap.put("wbs_id", info.get("wbs_id"));
    	
    	List<Map<String,Object>> list = wbsService.selectWbsProgressRateList(wbsMap);
    
   
    			
		model.addAttribute("wbsProRateList", list);
    

    	return "jsonView";
	}
	
	
	@RequestMapping(value="/execute/wbsMng/readWbsCalendarList.do")  
	public String selectWbsCalendarList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		Map<String,Object> info = wbsService.selectWbsInfo(param);
    	
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("strdate", info.get("wbs_str_dt"));
		dt.put("enddate", info.get("wbs_end_dt"));
		
    	List<Map<String,Object>> list = wbsService.selectWbsCalendarList(dt);
   
 	
    	
		model.addAttribute("wbsCalendarList", list);
	
    

    	return "jsonView";
	}
	
	@RequestMapping(value="/execute/wbsMng/readWbsScheduleList.do")  
	public String readWbsScheduleList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);

		Map<String,Object> info = wbsService.selectWbsInfo(param);
    	
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("wbs_id", info.get("wbs_id"));
		
   
		List<Map<String,Object>> scheduleList = wbsService.selectWbsScheduleList(dt);
    	
    	model.addAttribute("wbsScheduleList", scheduleList);
    	
    	
    

    	return "jsonView";
	}
	
	@RequestMapping(value="/execute/wbsMng/insertWbsItem.do")  
	 public String insertWbsItem(@RequestBody  Map<String, Object> params , Model model) throws IOException, SQLException , NullPointerException  {
		// params는 JSON 배열로부터 파싱된 List<Map<String, String>> 형식입니다.
		UserVo vo = getUser();
	  
		
		    if(vo != null) {
		        wbsService.insertWbsItem(params);
				model.addAttribute("sMessage", "Y");
			}else {
				model.addAttribute("sMessage", "N");
			}
			
		    
	        
	        return "jsonView";
	    }
	
	
	@RequestMapping(value="/execute/wbsMng/insertWbsBaseInfo.do")  
	 public String insertWbsBaseInfo(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
	        // params는 JSON 배열로부터 파싱된 List<Map<String, String>> 형식입니다.
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			UserVo vo = getUser();
			
			if(vo != null) {
				wbsService.insertWbsBaseInfo(param);
				model.addAttribute("sMessege", "Y");
			}else {
				model.addAttribute("sMessege", "N");
			}
			
		    
		
	        // 원하는 로직을 여기에 추가하세요.
	        
	        
	        return "jsonView";
	    }

}