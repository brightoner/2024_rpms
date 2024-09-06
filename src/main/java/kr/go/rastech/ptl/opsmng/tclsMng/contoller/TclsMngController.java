/******************************************************************
 * Copyright RASTECH 2016
 ******************************************************************/
package kr.go.rastech.ptl.opsmng.tclsMng.contoller;

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
import kr.go.rastech.ptl.opsmng.tclsMng.service.TclsMngService;
import kr.go.rastech.ptl.opsmng.tclsMng.vo.TclsMngVo;


/**
 * <pre>
 * FileName: TclsMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.controller
 * 
 * mng menu 관리 Controller
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 11.
 */
@Controller
public class TclsMngController extends BaseController {

	@Resource
	private TclsMngService tclsMngService;

	

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
	@RequestMapping("/opsmng/tclsMng/tclsMngList.do") 
	public String tclsMngList(String sel_tcls_id,Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("sel_tcls_id", sel_tcls_id);

		
		return "opsmng/tclsMng/tclsMngList.mngPlatForm";
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
	@RequestMapping("/opsmng/tclsMng/readTclsMngList.do")
	public void readTclsMngList(HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException {

		String xml="";
		List<TclsMngVo> menuList  = tclsMngService.readTclsMngList(null);
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
	@RequestMapping("/opsmng/tclsMng/duplChkTclsMngList.do")
	public void duplChkTclsMngList(HttpServletResponse resp, Writer out , TclsMngVo tclsMngVo)  throws IOException, SQLException , NullPointerException {

		String xml="";
		List<TclsMngVo> menuListChk  = tclsMngService.readTclsMngList(tclsMngVo);
		
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
	 * @param tclsMngVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/opsmng/tclsMng/saveTclsMng.do")
	public String saveTclsMng(TclsMngVo tclsMngVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
	
		tclsMngVo.setCreate_id();
		tclsMngVo.setModify_id();
		
		if(StringUtils.equals(tclsMngVo.getSave_type() , "I" )){
			tclsMngService.insertTclsMng(tclsMngVo);
		}else{
			tclsMngService.updateTclsMng(tclsMngVo);
			if(tclsMngVo.getTcls_levl() == 0){
				//상위메뉴가 변경시 하위메뉴의 사용유무를 변경
				tclsMngService.updateTclsMngDtl(tclsMngVo);
			}
			
		}
	

		return "redirect:tclsMngList.do?sel_tcls_id="+tclsMngVo.getTcls_id();
	}
	
	/**
	 * <pre>
	 * 메뉴관리- 메뉴삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param tclsMngVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="/opsmng/tclsMng/delTclsMng.do")
	public String delTclsMng(TclsMngVo tclsMngVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
		// 상위메뉴코드와 메뉴코드 전부 비어있으면 오류 처리
		if(StringUtils.isBlank(tclsMngVo.getTcls_id()) && StringUtils.isBlank(tclsMngVo.getTcls_prts_id()) ){
			throw new NullPointerException("삭제 정보가 없습니다."); 
		}
		
		tclsMngService.deleteTclsMng(tclsMngVo);
		
		
		return "redirect:tclsMngList.do";
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
	@RequestMapping("/opsmng/tclsMng/tclsMngPopList.do")
	public String tclsMngPopList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("tclsMng", param);
		
		return "opsmng/tclsMng/tclsMngPopList.popPlatForm";
			
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
	@RequestMapping(value="/opsmng/tclsMng/readTclsMngPopList.do")  
	public String readTclsMngPopList( Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
	
    	
    	List<Map<String,Object>> list =tclsMngService.selectTclsMngPopList(param);
    
    	int totalCnt =tclsMngService.selectTclsMngTotalPopCount(param);
    	
    	
    			
		model.addAttribute("tclsMngList", list);
    	model.addAttribute("tclsMngTotal", totalCnt);
    	model.addAttribute("tclsMngPageTotal", Math.ceil((double)totalCnt/(double)10));

    	return "jsonView";
	}
	
}
