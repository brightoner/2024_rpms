/******************************************************************
 * Copyright RASTECH 2016
 ******************************************************************/
package kr.go.rastech.ptl.opsmng.acctSubjMng.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.opsmng.acctSubjMng.service.AcctSubjMngService;
import kr.go.rastech.ptl.opsmng.acctSubjMng.vo.AcctSubjMngVo;


/**
 * <pre>
 * FileName: AcctSubjMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.controller
 * 
 * mng menu 관리 Controller
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 11.
 */
@Controller
public class AcctSubjMngController extends BaseController {

	@Resource
	private AcctSubjMngService acctSubjMngService;

	

	/**
	 * <pre>
	 * 메뉴관리페이지
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param sendMenuNo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/opsmng/acctSubjMng/acctSubjMngList.do") 
	public String acctSubjMngList(String sel_subj_id,Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("sel_subj_id", sel_subj_id);

		
		return "opsmng/acctSubjMng/acctSubjMngList.mngPlatForm";
	}
	
	/**
	 * <pre>
	 * 
	 * 메뉴정보 조회
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/opsmng/acctSubjMng/readAcctSubjMngList.do")
	public void readAcctSubjMngList(HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException {

		String xml="";
		List<AcctSubjMngVo> menuList  = acctSubjMngService.readAcctSubjMngList(null);
		if(menuList != null){
			if(menuList.size() > 0){
				xml = XmlUtil.listToXml(menuList);
			}
		}
 	    resp.setContentType("text/xml");
 	    resp.setCharacterEncoding("UTF-8");
 	    resp.setHeader("Cache-Control", "no-cache");
 	    resp.setHeader("Pragma", "no-cache");
 	    resp.setDateHeader("Expires", -1);

 	    out.write(xml);
 	    
 	    out.flush();

	}
	
	/**
	 * <pre>
	 * 
	 * 메뉴 id 중복체크
	 * </pre>
	 * @author : wonki
	 * @date   : 2023. 05. 19.
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/opsmng/acctSubjMng/duplChkAcctSubjMngList.do")
	public void duplChkAcctSubjMngList(HttpServletResponse resp, Writer out , AcctSubjMngVo acctSubjMngVo)  throws IOException, SQLException , NullPointerException {

		String xml="";
		List<AcctSubjMngVo> menuListChk  = acctSubjMngService.readAcctSubjMngList(acctSubjMngVo);
		
		if(menuListChk.size() > 0){
			xml = XmlUtil.listToXml(menuListChk);
		}
    	
 	    resp.setContentType("text/xml");
 	    resp.setCharacterEncoding("UTF-8");
 	    resp.setHeader("Cache-Control", "no-cache");
 	    resp.setHeader("Pragma", "no-cache");
 	    resp.setDateHeader("Expires", -1);

 	    out.write(xml);
 	    
 	    out.flush();

	}


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param acctSubjMngVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/opsmng/acctSubjMng/saveAcctSubjMng.do")
	public String saveAcctSubjMng(AcctSubjMngVo acctSubjMngVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
	
		acctSubjMngVo.setCreate_id();
		acctSubjMngVo.setModify_id();
		
		if(StringUtils.equals(acctSubjMngVo.getSave_type() , "I" )){
			acctSubjMngService.insertAcctSubjMng(acctSubjMngVo);
		}else{
			acctSubjMngService.updateAcctSubjMng(acctSubjMngVo);
			if(acctSubjMngVo.getSubj_levl() == 0){
				//상위메뉴가 변경시 하위메뉴의 사용유무를 변경
				acctSubjMngService.updateAcctSubjMngDtl(acctSubjMngVo);
			}
			
		}
	

		return "redirect:acctSubjMngList.do?sel_subj_id="+acctSubjMngVo.getSubj_id();
	}
	
	/**
	 * <pre>
	 * 메뉴관리- 메뉴삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param acctSubjMngVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="/opsmng/acctSubjMng/delAcctSubjMng.do")
	public String delAcctSubjMng(AcctSubjMngVo acctSubjMngVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
		// 상위메뉴코드와 메뉴코드 전부 비어있으면 오류 처리
		if(StringUtils.isBlank(acctSubjMngVo.getSubj_id()) && StringUtils.isBlank(acctSubjMngVo.getSubj_prts_id()) ){
			throw new NullPointerException("삭제 정보가 없습니다."); 
		}
		
		acctSubjMngService.deleteAcctSubjMng(acctSubjMngVo);
		
		
		return "redirect:acctSubjMngList.do";
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
	@RequestMapping("/opsmng/acctSubjMng/acctSubjMngPopList.do")
	public String acctSubjMngPopList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("acctSubjMng", param);
		
		return "opsmng/acctSubjMng/acctSubjMngPopList.popPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 기관 관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 05.
	 * @param request
	 * @param request
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/opsmng/acctSubjMng/readAcctSubjMngPopList.do")  
	public String readAcctSubjMngPopList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
	
    	
    	List<Map<String,Object>> list =acctSubjMngService.selectAcctSubjMngPopList(param);
    
    	int totalCnt =acctSubjMngService.selectAcctSubjMngTotalPopCount(param);
    	
    	
    			
		model.addAttribute("acctSubjMngList", list);
    	model.addAttribute("acctSubjMngTotal", totalCnt);
    	model.addAttribute("acctSubjMngPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}
	
}
