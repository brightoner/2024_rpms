/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

/**
 * <pre>
 * FileName: LoginFailureHandler.java
 * Package : kr.go.ncmiklib.commons.login.handler
 *
 * 로그인 실패 처리 Handler.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 9. 21.
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

	/**
	 * <pre>
	 * 로그인 실패를 처리한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 21.
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String message = null;
		if (exception instanceof SessionAuthenticationException) {
			message = "중복 로그인을 할 수 없습니다.";
		} else {
			message = exception.getMessage();
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/login/loginErr.do").forward(request, response);
	}

}
