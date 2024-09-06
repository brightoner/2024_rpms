package kr.go.rastech.ptl.content.popup.controller;



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
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.content.popup.service.PopMngService;
import kr.go.rastech.ptl.content.popup.vo.PopMngVo;






/**
 * <pre>
 * FileName: PopMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.pop.controller
 * 
 * 팝업관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class PopMngController extends BaseController {

	@Resource
	private PopMngService popMngService;
	
	

	/**
	 * <pre>
	 * 팝업 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/pop/popList.do")
	public String popList(Model model, PopMngVo popMngVo, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("popup", param);
		
		return "content/pop/popList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 팝업관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param request
	 * @param popMngVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="content/pop/readPopList.do")  
	public void readpopList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
    	
    	List<Map<String,Object>> list = popMngService.selectPopList(param);
    
    	int totalCnt = popMngService.selectPopTotalCount(param);
    	
    	if(list != null){
    		if(list.size() > 0){
    			
    			xml = XmlUtil.listMapToXml(list);
    			/*
    			 * 총카운터 세팅
    			 */
    	    	StringBuffer sb = new StringBuffer();
    	    	String[] str = xml.split("<items>");
    	    	
    	    	sb.append(str[0]);
    	    	sb.append("<items>");
				sb.append("<totalCnt>"+Math.ceil((double)totalCnt/(double)10)+"</totalCnt>\n");
				sb.append("<totalDataCnt>"+totalCnt+"</totalDataCnt>\n");
    	    	sb.append(str[1]);
    	    	
    	    	xml = sb.toString();
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
	 * 팝업관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param model
	 * @param popMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/pop/popWrite.do")
	public String popWrite(Model model, PopMngVo popMngVo, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("popup", param);
		
		return "content/pop/popWrite.mngPlatForm";
			
	}
	/**
	 * <pre>
	 * 팝업관리 상세 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param model
	 * @param popMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/pop/popDetail.do")
	public String popDetail(Model model, PopMngVo popMngVo, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		Map<String,Object> dtl = popMngService.selectPopDtl(param);
		
		if(!"".equals(param.get("pop_seq"))){
			String contents = String.valueOf(dtl.get("pop_contents"));
			contents = contents.replaceAll("&lt;", "<");
			contents = contents.replaceAll("&gt;", ">");
			dtl.put("pop_contents", contents);
		}
		model.addAttribute("data", dtl);
		
		model.addAttribute("popup", param);
		
		return "content/pop/popDetail.mngPlatForm";
			
	}
	
	@RequestMapping("content/pop/savePop.do")
	public String savePop(Model model, HttpServletRequest request )  throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;
		if(StringUtils.equals(String.valueOf(param.get("save_type")) , "I" )){
			seq = popMngService.insertPopMng(param);
		}else{
			popMngService.updatePopMng(param);
			seq = Integer.parseInt(param.get("pop_seq").toString());
		}
	
		return "redirect:popDetail.do?pop_seq="+seq;
	}
	
	
	/**
	 * <pre>
	 * 메인용 팝업 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2019. 12. 11.
	 * @param popMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	// 메인화면에서 팝업을 호출한다.
	// 현재 url은 관리자 페이지의 팝업관리의  팝업 미리보기에서도  사용된다. 변경시 참고 ! !  주의 !
	@RequestMapping(value="/content/pop/readMainPopList.do")   
	public void readMainPopList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		
		if(!StringUtil.nvl(getUserId()).equals("")){
			param.put("pop_target", "L");
			param.put("pop_type", "P");			
		}else{
			param.put("pop_target", "N");
			param.put("pop_type", "P");
		}
		String xml = "";
    	
    	List<Map<String,Object>> list = popMngService.selectMainPopList(param);
        	
    	
    	if(list != null){
    		if(list.size() > 0){
    			
    			xml = XmlUtil.listMapToXml(list);
    			/*
    			 * 총카운터 세팅
    			 */
    	    	StringBuffer sb = new StringBuffer();
    	    	String[] str = xml.split("<items>");
    	    	
    	    	sb.append(str[0]);    
    	     	sb.append("<items>");			
    	    	sb.append(str[1]);
    	    	xml = sb.toString();
    		}
    	}	
    //	System.out.println(xml);
    	
    	resp.setContentType("text/xml");
    	resp.setCharacterEncoding("UTF-8");
    	resp.setHeader("Cache-Control", "no-cache");
    	resp.setHeader("Pragma", "no-cache");
    	resp.setDateHeader("Expires", -1);
    	out.write(xml);
   	
    	out.flush();
	}

	/**
     * 팝업 삭제처리한다.
     * @param imgFileVo
     * @param bannerMngVo
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/content/pop/deletePop.do")    
    public String deletePop(Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);
    
    
    	if(StringUtils.isNotBlank(String.valueOf(param.get("pop_seq")))){
    		popMngService.deletePop(param);	
    	}
    	return "redirect:popList.do";   
    }

}