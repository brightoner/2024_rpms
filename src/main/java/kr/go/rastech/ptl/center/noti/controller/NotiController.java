package kr.go.rastech.ptl.center.noti.controller;



import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.center.noti.service.NotiService;






/**
 * <pre>
 * FileName: NotiMngController.java
 * Package : kr.go.rastech.ptl.center.noti.controller
 * 
 * 공지사항 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class NotiController extends BaseController {

	@Resource
	private NotiService notiService;
	
  
	/**
	 * <pre>
	 * 공지사항 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/center/noti/notiList.do")
	public String notiList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("noti", param);
		
		return "center/noti/notiList.mngPlatForm";
			
	}
	

	/**
	 * <pre>
	 * 공지사항 관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param request
	 * @param notiMngVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="/center/noti/readNotiList.do")  
	public void readnotiList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
    	
    	List<Map<String,Object>> list = notiService.selectNotiList(param);
    
    	int totalCnt = notiService.selectNotiTotalCount(param);
    	
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
	 * 공지사항 상세 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param model
	 * @param notiMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/center/noti/notiDetail.do")
	public String notiDetail(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);


		
		// key 확인		
		if(param.get("noti_id") == null) {
			return "redirect:/center/noti/notiList.do";
		}
		
		if(StringUtils.equals((CharSequence) param.get("upd_yn"), "Y")){
			notiService.updateNotiViewCnt(param);
		}
		
		Map<String,Object> dtl = notiService.selectNotiDtl(param);
		
		if(dtl != null ) {
			if(dtl.get("noti_contents") != null) {
				dtl.put("noti_contents", StringEscapeUtils.unescapeHtml3((String) dtl.get("noti_contents")));
			}
			
			model.addAttribute("data", dtl);		
			model.addAttribute("noti", param);
		}
		
		
		return "center/noti/notiDetail.mngPlatForm";
			
	}


}