package kr.go.rastech.ptl.execute.rpMng.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ExcelDownUtil;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.execute.rpMng.service.RpMngService;

/**
 * 
 * 연구자 참여율 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 06. 02.
 */
@Controller
public class RpMngController extends BaseController {

	
	@Resource
	private RpMngService rpMngService;
	

	/**
	 * <pre>
	 * 연구자 참여율 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/execute/rpMng/projRpMngList.do")
	public String projRpMngList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		
	
		
		
		
		return "execute/rpMng/projRpMngList.subPlatForm";
	
		
	}
	

	/**
	 * <pre>
	 * 
	 *참여연구원 참여율 조회
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/rpMng/readProjRpMngList.do")  
	public String readProjRpMngList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		

    	Map<String, Object> param = ReqUtils.getParameterMap(request);
		
    	List<Map<String,Object>> list = rpMngService.selectProjRpMngList(param);

    	
		model.addAttribute("rpMngList", list);
	


    	return "jsonView";
	}
	
	

	/**
	 * <pre>
	 * 
	 *참여연구원 참여율 존재 체크
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/execute/rpMng/chkProjRpRate.do")  
	public String chkProjRpRate( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		

    	Map<String, Object> param = ReqUtils.getParameterMap(request);
		
    	Map<String,Object> rateInfo = rpMngService.selectChkProjRpRate(param);

    	
		model.addAttribute("rateInfo", rateInfo);
	


    	return "jsonView";
	}
	
	
	@RequestMapping(value="/execute/rpMng/insertProjRpMng.do")  
	 public String updateProjRpMng(  @RequestBody  Map<String, Object> params , Model model) throws IOException, SQLException , NullPointerException  {
	
		UserVo vo = getUser();
	  	if(vo != null) {
								
	    	rpMngService.insertProjRpMng(params);
	    	model.addAttribute("sMessage", "Y");
		}else {
			
			model.addAttribute("sMessage", "N");
		}

        return "jsonView";
	        
    }
	
    
    @RequestMapping(value ="/execute/rpMng/projRpExcelDownLoad.do")
  
    public void projRpExcelDownLoad(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException, SQLException , NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);
    	
    	//List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		ArrayList<HashMap<String, Object>> colinfoList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hMap = null;
		
		
			String[] col_info = new String[]{"연구자명","구분","참여기간(참여율%)","(수행기간)과제명","1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
			String[] col_name = new String[]{"resp_nm","year_resp_nm","year_part","proj_nm_kor_from", "jan_rate","feb_rate", "mar_rate", "apr_rate" , "may_rate", "jun_rate", "jul_rate", "aug_rate", "sept_rate", "oct_rate","nov_rate", "dec_rate"};
			int[] col_size = new int[]{4000,4000,7000,19000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000,2000};
			
			for (int i = 0; i < col_size.length; i++) {
				hMap = new HashMap<String, Object>();
				hMap.put("COL_INFO", col_info[i]);
				hMap.put("COL_NAME", col_name[i]);
				hMap.put("COL_SIZE", col_size[i]);
				colinfoList.add(hMap);
			}
			
			String filename = "과제_참여율_관리"; 
			String title = "과제 참여율 관리";
			
			
		
			List<Map<String,Object>> dataList = rpMngService.selectExcelProjRpMngList(param);
		    
		     ExcelDownUtil excelDU = new ExcelDownUtil();
		    
		     excelDU.excelWrite(response, dataList, colinfoList, filename, title);
    }	
	
}