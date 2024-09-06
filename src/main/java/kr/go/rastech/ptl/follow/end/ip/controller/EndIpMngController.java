package kr.go.rastech.ptl.follow.end.ip.controller;



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
import kr.go.rastech.ptl.follow.end.ip.serivce.EndIpMngService;


/**
 * 
 * 최종사후관리 지적재산권  처리 컨트롤러
 *
 * </pre>
 * @author : ljk
 * @date   : 2024. 06. 17.
 */
@Controller
public class EndIpMngController extends BaseController {

	@Resource
	private EndIpMngService endipMngService;
	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권 리스트 페이지 이동
	 * 
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 02.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/ip/endIpList.do")
	public String endIpList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
			
		model.addAttribute("ip", param);
		model.addAttribute("ipGbList", getCodeList("IP_GB"));					// 공통코드 - 등록/출원구분
		model.addAttribute("ipDmstGbList", getCodeList("IP_DOMESTIC_GB"));		// 공통코드 - 국내여부구분
		
		return "/follow/end/ip/endIpInfo.intiles";
			
	}
	
	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권  리스트 ajax
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/ip/readEndIpList.do")  
	public String readendIpList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 5);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 5 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 5 );
		
    	
    	List<Map<String,Object>> list = endipMngService.selectEndIpList(param);
    	int totalCnt = endipMngService.selectEndIpListCount(param);
    	
		model.addAttribute("ipList", list);
    	model.addAttribute("ipTotal", totalCnt);
    	model.addAttribute("ipPageTotal", Math.ceil((double)totalCnt/(double)5));

    	return "jsonView";
	}

	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권  상세보기
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/follow/end/ip/endIpDetail.do")
	public String endIpDetail(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
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
		if(param.get("end_ip_id") == null) {
			return "redirect:/follow/end/ip/endIpList.do";
		}
		
		// 최종과제 채용정보
		Map<String,Object> dtl = endipMngService.selectEndIpDtl(param);
		
		model.addAttribute("data", dtl);
		model.addAttribute("ip", param);

		return "jsonView";
	}
	
	
	/**
	 * <pre>
	 * 최종사후관리 지적재산권  등록
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/ip/insertEndIp.do")  
	 public String insertEndIp( @RequestBody  Map<String, Object> param , Model model) throws IOException, SQLException , NullPointerException  {

		Map<String, Object> mainParam = (Map<String, Object>) param.get("mainItem");
		String proj_end_id_ip = Objects.toString(param.get("proj_end_id_ip") ,"") ;
		mainParam.put("proj_end_id_ip", proj_end_id_ip);
		
		UserVo vo = getUser();
		  	if(vo != null) {
				if(StringUtils.isNotBlank( Objects.toString(param.get("proj_end_id_ip"),""))){
					param.put("create_id", vo.getEmplyrkey());		
					param.put("modify_id", vo.getEmplyrkey());		
					endipMngService.insertEndIp(mainParam);
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
	 * 최종사후관리 지적재산권 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2024. 06. 17.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/follow/end/ip/deleteEndIp.do")  
	 public String deleteEndIp(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException  {
		
		int result =0;
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		UserVo vo = getUser();
		  	if(vo != null) {
		  		String idsParam = (String) param.get("end_ip_ids");
				String[] idsParams;
				if (idsParam != null) {
					idsParams = idsParam.split(","); // 쉼표로 구분된 문자열일 경우
				} else {
					idsParams = new String[0]; // null이면 빈 배열
				}
				
				for (int i = 0; i < idsParams.length; i++) {
					param.put("end_ip_id", idsParams[i]);
					result = endipMngService.deleteEndIp(param);
				}
			}else {
				model.addAttribute("sMessage", "F");
			}
		  	
        return "jsonView";
   }


}