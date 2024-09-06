package kr.go.rastech.ptl.opsmng.chatBlackList.controller;



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
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.opsmng.chatBlackList.service.ChatBlackListService;






/**
 * <pre>
 * FileName: ChatBlackListController.java
 * package : kr.go.rastech.ptl.opsmng.chatBlackList.controller;
 * 
 * 블랙리스트 관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class ChatBlackListController extends BaseController {

	@Resource
	private ChatBlackListService chatBlackListService;
	

	/**
	 * <pre>
	 * 블랙리스트 페이지 이동
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
	@RequestMapping("/opsmng/chatBlackList/chatBlackList.do")
	public String chatBlackList(Model model, HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		
		model.addAttribute("blackList", param);
		
		return "opsmng/chatBlackList/chatBlackList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 블랙리스트 관리 list
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
	@RequestMapping(value="/opsmng/chatBlackList/readChatBlackList.do")  
	public void readChatBlackList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();						
		String loginKey = userVo.getEmplyrkey();				
		param.put("emplyrkey", loginKey);
			
    	List<Map<String,Object>> list = chatBlackListService.selectChatBlackList(param);
    
    	int totalCnt = chatBlackListService.selectChatBlackListTotalCount(param);
    	
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
	 * 블랙리스트 관리 list
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
	@RequestMapping(value="/opsmng/chatBlackList/readMainChatBlackList.do")  
	public String readMainChatBlackList(Model model,  HttpServletRequest request)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();						
		String loginKey = userVo.getEmplyrkey();				
		param.put("emplyrkey", loginKey);
			
    	List<Map<String,Object>> list = chatBlackListService.selectMainChatBlackList(param);
    
    	int totalCnt = chatBlackListService.selectMainChatBlackListTotalCount(param);
    	
    	model.addAttribute("blackList", list);
    	model.addAttribute("blackListTotal", totalCnt);
    	model.addAttribute("blackListPageTotal", Math.ceil((double)totalCnt/(double)10));
    	
		return "jsonView";
	}


	

	/**
     * 블랙리스트을 삭제처리한다. 
     * @param 
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/opsmng/chatBlackList/deleteChatBlackList.do")
    @ResponseBody	
    public void deleteChatBlackList(Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
		String chk_blackList_id = request.getParameter("chk_blackList_id");
    	
	
		chatBlackListService.deleteChatBlackList(chk_blackList_id);
	
    }
    
    /**
  	 * <pre>
  	 * 스트리밍방 채팅 메시지 저장
  	 * 
  	 * </pre>
  	 * @author : rastech
  	 * @date   : 2023. 1. 18.
  	 * @param request
  	 * @return
  	 * @throws IOException , SQLException , NullPointerException
  	 */
    @RequestMapping(value = "/opsmng/chatBlackList/selectChatBlackListChkYn.do")
    public String selectChatBlackListChkYn(Model model , HttpServletRequest request) throws SQLException , IOException, NullPointerException {

		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
    	UserVo userVo = getUser();

 		if(userVo != null ) {
 			
		
			
		
 			param.put("bl_emplyrkey", userVo.getEmplyrkey());
 			Map<String,Object> chkBlack= chatBlackListService.selectChatBlackListChkYn(param);
 		
 			if(chkBlack!= null && chkBlack.size() > 0) { 	
 			
				model.addAttribute("chkBanStatus", "C");
	
 			}
 			
 			model.addAttribute("loginYn", "Y");
 			
 		}else {
 		
 			model.addAttribute("loginYn", "N");
 		}
    	 
    	 
		return "jsonView";
    }

   

	@RequestMapping("/opsmng/chatBlackList/insertBlackList.do")
	public String insertBlackList(Model model, HttpServletRequest request) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;		
		String returnVal = "no";
		
		// loginKey에서  사용자 정보 가져오기
		UserVo userVo = getUser();
		
		if(userVo != null){
			String loginKey = userVo.getEmplyrkey();
			String loginId = userVo.getLoginid();
			
			param.put("admin_id", loginKey); // bj id
			param.put("bl_userId", param.get("bl_userId"));
			param.put("create_id", loginKey);
			param.put("emplyrkey", loginKey);
			
			List<Map<String,Object>> list= chatBlackListService.selectChatBlackListDupleUserChk(param);
			if (list == null || list.isEmpty()) {
				
				seq = chatBlackListService.insertChatBlackList(param);

			    returnVal = "ok";
			  
			}else {

				seq = chatBlackListService.updateChatBlackList(param);
				
			    returnVal = "ok";
			}
			
		}else {
			returnVal = "notLogin";
		}
	 
	
		model.addAttribute("returnVal" , returnVal);
			
		return "jsonView";
	}
	
	
}