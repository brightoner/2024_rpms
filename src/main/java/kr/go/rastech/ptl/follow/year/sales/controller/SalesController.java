package kr.go.rastech.ptl.follow.year.sales.controller;



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
public class SalesController extends BaseController {

	@Resource
	private SalesService salesService;
	
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 매출 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/year/sales/yearSalesList.do")
	public String yearSalesList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			
		model.addAttribute("sales", param);
		
		return "/follow/year/sales/yearSalesInfo.intiles";
			
	}
	
	
	/**
	 * <pre>
	 * 연차사후관리 매출  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/sales/readYearSalesgList.do")  
	public String readYearSalesgList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	// 연차사후관리 매출 정보
    	List<Map<String, Object>> salesList = salesService.selectYearSalesList(param);
    	int salesTotalCnt = salesService.selectYearSalesListCount(param);
    	
    	
    	model.addAttribute("sales", param);
    	// 연차사후관리 매출 정보
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
	@RequestMapping(value="/follow/year/sales/readSalesMngList.do")  
	public String readSalesMngList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	List<Map<String,Object>> list = salesService.selectSaleMngList(param);
    	int totalCnt = salesService.selectSaleMngListCount(param);
    	
    	model.addAttribute("mng", param);
		model.addAttribute("mngList", list);
    	model.addAttribute("mngTotal", totalCnt);
    	model.addAttribute("mngPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}
	
	
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 매출  등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/sales/insertYearSales.do")  
	 public String insertYearSales(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
			String create_id = vo.getEmplyrkey();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("mng_ids");
		  		String proj_year_id = (String) param.get("proj_year_id_mng");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("create_id", create_id);
					param.put("proj_year_id", proj_year_id);
					param.put("sales_id", idsParams[i]);
					result = salesService.insertYearSales(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }
	
	
	
	/**
	 * <pre>
	 * 연차사후관리 매출 수정
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/sales/updateYearSales.do")  
	 public String updateYearSales(@RequestBody  Map<String, Object> param, Model model) throws IOException, SQLException , NullPointerException  {
		
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
					salesService.updateYearSales(salesMap);
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
	 * 연차사후관리 매출 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/sales/deleteYearSales.do")  
	 public String deleteYearSales(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("year_sales_ids");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("year_sales_id", idsParams[i]);
					result = salesService.deleteYearSales(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }


}