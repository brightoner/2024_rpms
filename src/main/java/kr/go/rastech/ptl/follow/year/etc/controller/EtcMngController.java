package kr.go.rastech.ptl.follow.year.etc.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.ptl.follow.year.etc.service.EtcMngService;


/**
 * 
 * 연차사후관리 기타  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class EtcMngController extends BaseController {

	@Resource
	private EtcMngService etcMngService;
	
	/**
	 * <pre>
	 * 연차사후관리 기타 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/year/etc/yearEtcList.do")
	public String yearEtcList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			
		model.addAttribute("etc", param);
		
		return "/follow/year/etc/yearEtcInfo.intiles";
			
	}
	
	
	/**
	 * <pre>
	 * 연차사후관리 기타  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/etc/readYearEtcList.do")  
	public String readYearEtcList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	List<Map<String,Object>> list = etcMngService.selectYearEtcList(param);
    	int totalCnt = etcMngService.selectYearEtcListCount(param);
    	
		model.addAttribute("etcList", list);
    	model.addAttribute("etcTotal", totalCnt);
    	model.addAttribute("etcPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 연차사후관리 기타  상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/year/etc/yearEtcDetail.do")
	public String yearEtcDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("year_etc_id") == null) {
			return "redirect:/follow/year/etc/yearEtcList.do";
		}
		
		// 연차과제 채용정보
		Map<String,Object> dtl = etcMngService.selectYearEtcDtl(param);
		
		model.addAttribute("data", dtl);
		model.addAttribute("etc", param);

		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 연차사후관리 기타  등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/etc/insertYearEtc.do")  
	 public String insertYearEtc( @RequestBody  Map<String, Object> param , Model model) throws IOException, SQLException , NullPointerException  {

		Map<String, Object> mainParam = (Map<String, Object>) param.get("mainItem");
		String proj_year_id_etc = Objects.toString(param.get("proj_year_id_etc") ,"") ;
		mainParam.put("proj_year_id_etc", proj_year_id_etc);
		
		UserVo vo = getUser();
		  	if(vo != null) {
				if(StringUtils.isNotBlank( Objects.toString(param.get("proj_year_id_etc"),""))){
					param.put("create_id", vo.getEmplyrkey());		
					param.put("modify_id", vo.getEmplyrkey());		
					etcMngService.insertYearEtc(mainParam);
					model.addAttribute("sMessage", "Y");
				}else {
					model.addAttribute("sMessage", "F");	
				}
			}else {
				model.addAttribute("sMessage", "N");
			}
        return "jsonView";
   }
	
	
	/**
	 * <pre>
	 * 연차사후관리 기타 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/year/etc/deleteYearEtc.do")  
	 public String deleteYearEtc(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("year_etc_ids");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("year_etc_id", idsParams[i]);
					result = etcMngService.deleteYearEtc(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }


}