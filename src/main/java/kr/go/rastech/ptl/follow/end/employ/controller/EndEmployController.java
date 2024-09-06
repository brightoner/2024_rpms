package kr.go.rastech.ptl.follow.end.employ.controller;



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
import kr.go.rastech.ptl.follow.end.employ.service.EndEmployService;


/**
 * 
 * 종료사후관리 기본정보  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class EndEmployController extends BaseController {

	@Resource
	private EndEmployService endEmployService;
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/employ/endEmployList.do")
	public String endEmployList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			
		model.addAttribute("employ", param);
		model.addAttribute("employGbList", getCodeList("EMPLOY_GB"));				// 공통코드 - 채용구분
		model.addAttribute("employYouthGbList", getCodeList("EMPLOY_YOUTH_GB"));	// 공통코드 - 청년채용구분
		
		return "/follow/end/employ/endEmployInfo.intiles";
			
	}
	
	
	/**
	 * <pre>
	 * 종료사후관리 기본정보  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/employ/readEndEmployList.do")  
	public String readEndEmployList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	List<Map<String,Object>> list = endEmployService.selectEndEmployList(param);
    	int totalCnt = endEmployService.selectEndEmployListCount(param);
    	
		model.addAttribute("employList", list);
    	model.addAttribute("employTotal", totalCnt);
    	model.addAttribute("employPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 종료사후관리 기본정보  상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/employ/endEmployDetail.do")
	public String endEmployDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("end_employ_id") == null) {
			return "redirect:/follow/end/employ/endEmployList.do";
		}
		
		// 연차과제 채용정보
		Map<String,Object> dtl = endEmployService.selectEndEmployDtl(param);
		
		model.addAttribute("data", dtl);
		model.addAttribute("employ", param);

		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 종료사후관리 채용  등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/employ/insertEndEmploy.do")  
	 public String insertEndEmploy(  @RequestBody  Map<String, Object> param, Model model) throws IOException, SQLException , NullPointerException  {
	
		Map<String, Object> mainParam = (Map<String, Object>) param.get("mainItem");
		String proj_end_id_employ = Objects.toString(param.get("proj_end_id_employ") ,"") ;
		mainParam.put("proj_end_id_employ", proj_end_id_employ);
		
		UserVo vo = getUser();
		  	if(vo != null) {
				if(StringUtils.isNotBlank( Objects.toString(param.get("proj_end_id_employ"),""))){
					param.put("create_id", vo.getEmplyrkey());		
					param.put("modify_id", vo.getEmplyrkey());		
					endEmployService.insertEndEmploy(mainParam);
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
	 * 종료사후관리 채용 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/employ/deleteEndEmploy.do")  
	 public String deleteEndEmploy(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("end_employ_ids");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("end_employ_id", idsParams[i]);
					result = endEmployService.deleteEndEmploy(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
		  	model.addAttribute("result", result);
		  	
        return "jsonView";
   }


}