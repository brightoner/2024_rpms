package kr.go.rastech.ptl.follow.end.sales.controller;



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
import kr.go.rastech.ptl.follow.end.sales.service.EndSalesService;
import kr.go.rastech.ptl.follow.year.sales.service.SalesService;


/**
 * 
 * 매출관리  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class EndSalesController extends BaseController {

	@Resource
	private EndSalesService endSalesService;
	
	
	@Resource
	private SalesService salesService;
	
	
	
	
	
	/**
	 * <pre>
	 * 종료사후관리 매출 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/sales/endSalesList.do")
	public String endSalesList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			
		model.addAttribute("sales", param);
		
		return "/follow/end/sales/endSalesInfo.intiles";
			
	}
	
	
	/**
	 * <pre>
	 * 종료사후관리 매출  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/sales/readEndSalesgList.do")  
	public String readEndSalesgList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	// 종료사후관리 매출 정보
    	List<Map<String, Object>> salesList = endSalesService.selectEndSalesList(param);
    	int salesTotalCnt = endSalesService.selectEndSalesListCount(param);
    	
    	
    	model.addAttribute("sales", param);
    	// 종료사후관리 매출 정보
    	model.addAttribute("salesList", salesList);
    	model.addAttribute("salesTotal", salesTotalCnt);
    	model.addAttribute("salesPageTotal", Math.ceil((double)salesTotalCnt/(double)5));
    	

    	return "jsonView";
	}

	
	

	/**
	 * <pre>
	 * 매출관리  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/sales/readSalesMngList.do")  
	public String readSalesMngList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	List<Map<String,Object>> list = endSalesService.selectEndSaleMngList(param);
    	int totalCnt = endSalesService.selectEndSaleMngListCount(param);
    	
    	model.addAttribute("mng", param);
		model.addAttribute("mngList", list);
    	model.addAttribute("mngTotal", totalCnt);
    	model.addAttribute("mngPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}
	
	
	
	
	
	/**
	 * <pre>
	 * 종료사후관리 매출  등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/sales/insertEndSales.do")  
	 public String insertEndSales(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
			String create_id = vo.getEmplyrkey();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("mng_ids");
		  		String proj_end_id = (String) param.get("proj_end_id_mng");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("create_id", create_id);
					param.put("proj_end_id", proj_end_id);
					param.put("sales_id", idsParams[i]);
					result = endSalesService.insertEndSales(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }
	
	
	
	/**
	 * <pre>
	 * 종료사후관리 매출 수정
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/sales/updateEndSales.do")  
	 public String updateEndSales(@RequestBody  Map<String, Object> param, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result = 0;
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		param.put("modify_id", userVo.getEmplyrkey());
		// 로그인 확인
		
		if(userVo != null) {
			
			List<Map<String, Object>> salesList = (List<Map<String, Object>>) param.get("salesList");
			
			
			int idx = 0;
			if (salesList != null && !salesList.isEmpty()) {
				for (Map<String, Object> salesMap : salesList) {
					endSalesService.updateEndSales(salesMap);
					idx ++;
					result  ++;
	            }
			}
			
			model.addAttribute("result",result);
			
		}else {
			model.addAttribute("sMessage", "F");
		}
        return "jsonView";
   }
	
	
	
	/**
	 * <pre>
	 * 종료사후관리 매출 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/sales/deleteEndSales.do")  
	 public String deleteEndSales(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("end_sales_ids");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("end_sales_id", idsParams[i]);
					result = endSalesService.deleteEndSales(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }


}