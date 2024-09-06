package kr.go.rastech.ptl.opsmng.salesMng.controller;



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
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.opsmng.salesMng.service.SalesMngService;


/**
 * 
 * 매출관리  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class SalesMngController extends BaseController {

	@Resource
	private SalesMngService salesMngService;
	
	/**
	 * <pre>
	 * 매출관리 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/salesMng/salesMngList.do")
	public String salesMngList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			
		model.addAttribute("sales", param);
		
		return "/opsmng/salesMng/salesMngList.mngPlatForm";
			
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
	@RequestMapping(value="/opsmng/salesMng/readSalesMngList.do")  
	public String readSalesMngList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	List<Map<String,Object>> list = salesMngService.selectSaleMngList(param);
    	int totalCnt = salesMngService.selectSaleMngListCount(param);
    	
    	model.addAttribute("sales", param);
		model.addAttribute("salesList", list);
    	model.addAttribute("salesTotal", totalCnt);
    	model.addAttribute("salesPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 매출관리  상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/opsmng/salesMng/salesMngDetail.do")
	public String salesMngDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("sales_id") == null) {
			return "redirect:/index/index.do";
		}
		
		// 매출관리
		Map<String,Object> dtl = salesMngService.selectSaleMngDtl(param);
		
		model.addAttribute("data", dtl);
		model.addAttribute("article", param);

		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 매출관리  등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/salesMng/insertSalesMng.do")  
	 public String insertSalesMng( @RequestBody  Map<String, Object> param , Model model) throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> mainParam = (Map<String, Object>) param.get("mainItem");
		
		UserVo vo = getUser();
		  	if(vo != null) {
				mainParam.put("create_id", vo.getEmplyrkey());		
				mainParam.put("modify_id", vo.getEmplyrkey());		
				salesMngService.insertSaleMng(mainParam);
					model.addAttribute("sMessage", "Y");
			}else {
				model.addAttribute("sMessage", "N");
			}
        return "jsonView";
   }
	
	
	/**
	 * <pre>
	 * 매출관리 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/salesMng/deleteSalesMng.do")  
	 public String deleteSalesMng(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("sales_ids");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("sales_id", idsParams[i]);
					result = salesMngService.deleteSaleMng(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }


}