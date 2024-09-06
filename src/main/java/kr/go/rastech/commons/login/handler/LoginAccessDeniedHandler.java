/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <pre>
 * FileName: LoginAccessDeniedHandler.java
 * Package : kr.go.ncmiklib.commons.login.handler
 *
 * 접근 권한이 없는 요청을 처리하는 handler.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 9. 22.
 */
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * <pre>
	 * 접근 권한이 없는 경우 호출.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 22.
	 * @param request
	 * @param response
	 * @param accessDeniedException
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect(request.getContextPath() + "/error/403.do");
	}

}
