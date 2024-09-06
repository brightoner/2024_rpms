package kr.go.rastech.ptl.mng.ip.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.cmmn.service.Globals;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.mng.ip.service.IpService;
import kr.go.rastech.ptl.mng.ip.vo.IpVo;

@Controller
public class IpController extends BaseController {

	@Resource
	IpService ipService;
	   

	/**
	 * <pre>
	 * ip 화면 이동
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 25.
	 * @param req
	 * @param model
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws NullPointerException 

	 */
	@RequestMapping("/mng/ip/listIp.do")
	public String etlList(HttpServletRequest request, Model model) throws NullPointerException, IOException, SQLException{
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		model.addAttribute("if_yn", param.get("if_yn"));

		return rtnUrl("mng/ip/listIp");
		
	}
	
	/**
	 * <pre>
	 * ip 설정정보 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 25.
	 * @param req
	 * @param model
	 * @throws IOException 

	 */
	@RequestMapping("/mng/ip/selectIpList.do")
	public void selectETLList(HttpServletRequest req, HttpServletResponse resp, Writer out) throws IOException{
		
		List<String> list = Globals.CONFIM_IP;
		
		String xml = "";
    	if(list != null){
    		xml = XmlUtil.listToXml(list);
    	}
 	    
 	    resp.setContentType("text/xml");
 	    resp.setCharacterEncoding("UTF-8");
 	    resp.setHeader("Cache-Control", "no-cache");
 	    resp.setHeader("Pragma", "no-cache");
 	    resp.setDateHeader("Expires", -1);

 	    out.write(xml);
 	    
 	    out.flush();
	}
	
	@ResponseBody
	@RequestMapping("/mng/ip/saveIp.do")
	public void saveIp(HttpServletRequest req, HttpServletResponse resp, IpVo ipVo) throws IOException{

		if("I".equals(ipVo.getIp_type())){
			Globals.CONFIM_IP.add(ipVo.getIp());
		}else if("D".equals(ipVo.getIp_type())){
			Globals.CONFIM_IP.remove(ipVo.getIp());

		}
	
	}
	
}
