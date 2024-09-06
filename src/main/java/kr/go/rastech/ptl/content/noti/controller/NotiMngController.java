package kr.go.rastech.ptl.content.noti.controller;



import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.atchFile.service.AtchFileService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.content.noti.service.NotiMngService;







/**
 * <pre>
 * FileName: NotiMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.noti.controller
 * 
 * 공지사항 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class NotiMngController extends BaseController {

	@Resource
	private NotiMngService notiMngService;
	
	 @Resource
	 private AtchFileService atchFileService;


	/**
	 * <pre>
	 * 공지사항 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("/content/noti/notiList.do")
	public String notiList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("noti", param);
		
		return "content/noti/notiList.mngPlatForm";
			
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
	@RequestMapping(value="/content/noti/readNotiList.do")  
	public void readnotiList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
    	
    	List<Map<String,Object>> list = notiMngService.selectNotiList(param);
    
    	int totalCnt = notiMngService.selectNotiTotalCount(param);
    	
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
	 * 공지사항 관리 등록 url이동
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
	@RequestMapping("content/noti/notiWrite.do")
	public String notiWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("noti", param);
		
		return "content/noti/notiWrite.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 공지사항 관리 등록 url이동
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
	@RequestMapping("/content/noti/notiDetail.do")
	public String notiDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
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
		if(param.get("noti_id") == null) {
			return "redirect:/content/noti/notiList.do";
		}
		
		
		Map<String,Object> dtl = notiMngService.selectNotiDtl(param);
		
	
		
	
		model.addAttribute("data", dtl);
		
		model.addAttribute("noti", param);
		
		return "content/noti/notiDetail.mngPlatForm";
			
	}

	
	@RequestMapping("content/noti/insertNoti.do")
	public String insertNoti(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest ,RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo == null){
			return "redirect:/index/index.do";
		}
	
		
		String loginKey = userVo.getEmplyrkey();
		
		String createId = loginKey;
		param.put("create_id", createId);
	
		seq = notiMngService.insertNotiMng(param);
	
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("noti_id", param.get("noti_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		return "redirect:/content/noti/notiDetail.do";
	}
	

	@RequestMapping("content/noti/updateNoti.do")
	public String updateNoti(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest ,RedirectAttributes redirect) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		// 로그인 확인
		if(userVo == null ){
			return "redirect:/index/index.do";
		}		
		// key 확인		
		if(param.get("noti_id") == null) {
			return "redirect:/content/noti/notiList.do";
		}
		
		String loginKey = userVo.getEmplyrkey();
		
		String modifyId = loginKey;
		param.put("modify_id", modifyId);
		notiMngService.updateNotiMng(param);
		
		
		 

				
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("noti_id", param.get("noti_id"));
		
		redirect.addFlashAttribute("paramMap", map);
		
		
		return "redirect:/content/noti/notiDetail.do";
	}
	
	
	
	

	/**
     * 공지사항을 삭제처리한다. 
     * @param notiMngVo
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/content/noti/deleteNoti.do")
    public String deleteNoti(Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);
    	String atch_link_id = request.getParameter("atch_link_id");
    	
    	

    	notiMngService.deleteNoti(param);
    	//atchFileService.deleteAllAttFile(atch_link_id);
    
    	return "redirect:/content/noti/notiList.do";
    }

}