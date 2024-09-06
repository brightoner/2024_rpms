package kr.go.rastech.commons.login.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.dao.LoginDao;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.menu.service.MenuService;
import kr.go.rastech.commons.utils.SessionChecker;
import kr.go.rastech.ptl.mng.code.service.MngCodeService;
import kr.go.rastech.ptl.mng.user.service.MngUserService;

/**
 * <pre>
 * FileName: LoginController.java
 * Package : kr.go.ncmiklib.commons.login.controller
 * 로그인 관리 - controller
 * 
 *
 * </pre>
 * 
 * @author : rastech
 * @date : 2023. 2. 22.
 */
@Controller("loginController")
public class LoginController extends BaseController {

	@Resource
	private SessionChecker sessionChecker;

	@Resource
	private LoginService loginService;

	@Resource
	private MngUserService mngUserService;

	@Resource
	private MngCodeService mngCodeService;


	@Resource
	private LoginDao loginDao;

	@Resource
	private MenuService menuService;

	

	/**
	 * <pre>
	 * 로그인 - 로그인 화면 이동
	 *
	 * </pre>
	 * 
	 * @author : ljk
	 * @date : 2023.04.05
	 * @param loginid
	 * @param req
	 * @param model
	 * @return
	 * @throws IOException,
	 *             SQLException , NullPointerException
	 */
	@RequestMapping(value = "/login/user/login.do")
	public String login(String loginid, HttpServletRequest request, Model model) throws IOException, SQLException, NullPointerException {
		/*
		String url = "login/login.stiles";

		if (request.getSession().getAttribute("userVo") != null) {
			url = "redirect:/index/index.do";
		} else {
			if (StringUtils.isNotBlank(request.getParameter("returnURL"))) {
				request.getSession().setAttribute("returnURL", request.getParameter("returnURL"));
			}
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

			String message = "";
			if (flashMap != null) {
				message = (String) flashMap.get("message");
			}

			if (!StringUtils.equals("", message)) {
				model.addAttribute("loginid", loginid);
				model.addAttribute("message", message);
				model.addAttribute("result", "false");
			}
			if (request.getSession().getAttribute("message") != null) {
				if (request.getSession().getAttribute("message") != "") {
					model.addAttribute("message", request.getSession().getAttribute("message"));
					model.addAttribute("result", "false");
					request.getSession().setAttribute("message", "");
				} else {
					model.addAttribute("result", "");
				}
			}
		}
		return url;
		*/
		
		return "redirect:/index/index.do";
	}	

	/**
	 * <pre>
	 * 로그인 실패
	 *
	 * </pre>
	 * 
	 * @author : rastech
	 * @date : 2023. 2. 19.
	 * @param loginid
	 * @param request
	 * @param redirectAttr
	 * @return
	 * @throws IOException,
	 *             SQLException , NullPointerException
	 */
	@RequestMapping(value = "/login/loginErr.do")
	public String loginErr(String loginid, HttpServletRequest request, RedirectAttributes redirectAttr) throws IOException, SQLException, NullPointerException {
		String message = (String) request.getAttribute("message");
		if (!StringUtils.equals("", message)) {
			redirectAttr.addFlashAttribute("message", message);
		}
		//return "redirect:/login/user/login.do";
		return "redirect:/index/index.do";
	}


	/**
	 * <pre>
	 * 구글 reCAPTCHA
	 *
	 * </pre>
	 * 
	 * @author : rastech
	 * @date : 2023. 2. 19.
	 * @param loginid
	 * @param request
	 * @param redirectAttr
	 * @return
	 * @throws IOException,
	 *             SQLException , NullPointerException
	 */
	@RequestMapping("/verify/recaptcha/verifyRecaptcha.do")
	public  String verifyRecaptcha(Model model, HttpServletRequest request ) throws IOException, SQLException, NullPointerException  {
		
		String secret = "6LdT03QnAAAAACC522CtpGX8hdk90p8SlJGrHBNr"; // v2 (체크박스)
		
		DataOutputStream wr = null;
		BufferedReader in = null;
		String returnData = "";
		 JSONObject jsonObject = null;
		 JSONParser parser = new JSONParser();
	    try {
	        String url = "https://www.google.com/recaptcha/api/siteverify";
	        String params = "secret="+secret
	                + "&response=" + request.getParameter("vertify");

	        URL obj = new URL(url);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("POST");
	        con.setDoOutput(true);
	        
	        wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(params);
	        wr.flush();

	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            StringBuffer responseBuffer = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                responseBuffer.append(inputLine);
	            }

	         
	            returnData = responseBuffer.toString();	            
	            
	            jsonObject = (JSONObject) parser.parse(returnData);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // finally 블록에서 리소스를 닫습니다.
	        try {
	            wr.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        try {
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    model.addAttribute("returnData", jsonObject);
	    
	    return "jsonView";
	    
	}


}
