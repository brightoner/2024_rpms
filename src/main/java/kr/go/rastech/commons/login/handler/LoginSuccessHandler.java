/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.login.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.SessionChecker;

/**
 * <pre>
 * FileName: LoginSuccessHandler.java
 * Package : kr.go.ncmiklib.commons.login.handler
 *
 * 로그인 성공 처리 handler.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 9. 21.
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private LoginService loginService;
	
	private String meshURL;
	private String errorURL;
	private Set<String> reviewerURL;
	private Set<String> indexerURL;
		
	
    public String getMeshURL() {
		return meshURL;
	}

	public void setMeshURL(String meshURL) {
		this.meshURL = meshURL;
	}

	public String getErrorURL() {
		return errorURL;
	}

	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}

	public Set<String> getReviewerURL() {
		return reviewerURL;
	}

	public void setReviewerURL(Set<String> reviewerURL) {
		this.reviewerURL = reviewerURL;
	}

	public Set<String> getIndexerURL() {
		return indexerURL;
	}

	public void setIndexerURL(Set<String> indexerURL) {
		this.indexerURL = indexerURL;
	}
	
	/**
	 * <pre>
	 * 로그인 성공시 처리.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 21.
	 * @param request
	 * @param response
	 * @param authentication
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		UserVo usrVo = (UserVo) authentication.getDetails();
		//로그인시 기본 ROLE_USER 등록
		usrVo.getRoleList().add("ROLE_USER");
		
		/** 로그인 통계 현황 저장 **/
		// 사용자 IP 가져오기
		String ip = request.getHeader("X-Forwarded-For");  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_FORWARDED");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_VIA");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("REMOTE_ADDR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getRemoteAddr();  
		} 

		usrVo.setUsrIp(ip);
	
		request.getSession().setAttribute("userVo", usrVo);
	
		//사용자 접속통계
		try {
			loginService.insertStatLogin(usrVo.getEmplyrkey());
		} catch (SQLException e) {			
			Logger.getLogger(LoginSuccessHandler.class).debug("IGNORED: onAuthenticationSuccess");
		}
		

		//20240614
		String returnURL =  "/index/index.do";
	
		
		response.sendRedirect(request.getContextPath() + returnURL);
	}
	
    /**
     * check if IP address match pattern
     * 
     * @param pattern
     *            *.*.*.* , 192.168.1.0-255 , *
     * @param address
     *            - 192.168.1.1<BR>
     *            <code>address = 10.2.88.12  pattern = *.*.*.*   result: true<BR>
     *                address = 10.2.88.12  pattern = *   result: true<BR>
     *                address = 10.2.88.12  pattern = 10.2.88.12-13   result: true<BR>
     *                address = 10.2.88.12  pattern = 10.2.88.13-125   result: false<BR></code>
     * @return true if address match pattern
     */
    public static boolean checkIPMatching(String pattern, String address) {
    
    	if (pattern.equals("*.*.*.*") || pattern.equals("*")) return true;
	
	    String[] mask = pattern.split("\\.");
	    String[] ip_address = address.split("\\.");
	    for (int i = 0; i < mask.length; i++) {
		    if (mask[i].equals("*") || mask[i].equals(ip_address[i]))
		    continue;
		    else if (mask[i].contains("-")) {
			    byte min = Byte.parseByte(mask[i].split("-")[0]);
			    byte max = Byte.parseByte(mask[i].split("-")[1]);
			    byte ip = Byte.parseByte(ip_address[i]);
			    if (ip < min || ip > max) return false;
		    }
		    else {
		    	return false;
		    }
	    }
	    return true;
    }

  	/*
   	 * login session
   	 */
   	private boolean isLoggedIn(UserVo usrVo) {
   		return (usrVo != null) ? true : false;
   	}
   	
   	@SuppressWarnings("unused")
   	private String getLoginID(UserVo usrVo) {
   		if (!isLoggedIn(usrVo)) {
   			return "";
   		}
   		
   		return usrVo.getLoginid();
   	}
   	
  
}
