/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.interceptors;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.menu.service.MenuService;
import kr.go.rastech.ptl.mng.sys.service.SysMntrService;
import kr.go.rastech.ptl.mng.user.service.MngUserService;


/**
 *
 * <pre>
 * FileName: LoginInterceptor.java
 * Package : kr.go.ncmiklib.solar.commons.interceptor
 *
 * Login 인터셉터 클래스 정의
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 04. 21.
 */
@Component
public class LoginInterceptor extends BaseInterceptor {

	@Autowired
	private MenuService menuService;
	
    @Resource
    SysMntrService sysMntrService;

    @Resource
    MngUserService mngUserService;
	
	@Resource
	private LoginService loginService;
	
	

	
	/**
     *
     * <pre>
     * 로그인 확인.
     * 로그인을 하지 않았으면 초기화면("/")으로 이동.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
        String sessionid = request.getSession().getId();
        
        response.addHeader("Set-Cookie", "JSESSIONID="+ sessionid+"; Path=/; Secure; SameSite=None");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");		
		response.setDateHeader("Expires", -1);
	
	
	 	String url =  request.getRequestURI();
	 	
	 //	System.out.println("@@@@@@@@@@@");
	// 	System.out.println(url);
	 //	System.out.println("@@@@@@@@@@@");
	 	// AJAX 요청 실행안함
	 	// stomp , cmm  == url으로 실행되는 요청 수행 안함
		if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
				&&  StringUtils.indexOf(url, "stomp/") < 0    
				&&  StringUtils.indexOf(url, "cmm/") < 0   
				) {
		
	        request.setAttribute("sys_topMenu", menuService.listTopMenu(request));
	        request.setAttribute("sys_leftMenu", menuService.listLeftMenu(request));
	        request.setAttribute("sys_popMenu", menuService.listPopMenu(request));
	        
	    
		}

     
	     	
        return super.preHandle(request, response, handler);
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    	super.afterCompletion(request, response, handler, ex);
    }
    
  
}
