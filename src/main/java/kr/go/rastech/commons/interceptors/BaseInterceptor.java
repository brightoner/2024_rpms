/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * <pre>
 * FileName: BaseInterceptor.java
 * Package : kr.go.ncmiklib.commons.interceptors
 *
 * Base 인터셉터 클래스 정의
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * <pre>
     * 클라이언트의 요청을 컨트롤러에게 전달하기 전에 호출.
     * false 인 경우 다음 handlerinterceptor 혹은 컨트롤러를 실행시키지 않고 요청 처리를 종료.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	    	
    	String userAgent = request.getHeader("User-Agent").toUpperCase();
    	HttpSession session = request.getSession();
    	if(userAgent.indexOf("MOBI") > -1) {
    		session.setAttribute("isMoblie", "Y");
    	} else {
    		session.setAttribute("isMoblie", "N");
    	}
    	
    	return super.preHandle(request, response, handler);
    }

    /**
     *
     * <pre>
     * 컨트롤러가 요청을 실행한 후 처리.
     * 컨트롤러 실행도중 예외 발생인 경우 postHandle()는 실행되지 않음
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception 
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	super.postHandle(request, response, handler, modelAndView);
    };

    /**
     *
     * <pre>
     * 클라이언트의 요청을 처리한 뒤에 실행.
     * 컨트롤러 처리 도중이나 view 생성과정 중에 예외가 발생하더라도 afterCompletion()은 실행된다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws IOException, SQLException , NullPointerException
     * @throws Exception 
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws  Exception {
    	super.afterCompletion(request, response, handler, ex);
    }
}
