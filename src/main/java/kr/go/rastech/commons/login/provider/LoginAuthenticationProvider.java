/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.login.provider;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import kr.go.rastech.base.BaseObj;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.Utils;
 
/**
 * <pre>
 * FileName: LoginAuthenticationProvider.java
 * Package : kr.go.ncmiklib.commons.login.provider
 *
 * 로그인을 처리하는 Provider.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 9. 18.
 */
public class LoginAuthenticationProvider extends BaseObj implements AuthenticationProvider {
	@Autowired
	private LoginService loginService;
	

	/**
	 * <pre>
	 * 로그인을 수행한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 18.
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginid = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		// Spring RequestContextHolder를 사용하여 session 가져오기.
		// web.xml 에 다음 설정이 있어야 함.
		// <listener>
	    //   <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
	    // </listener>
		/*  
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = sra.getRequest().getSession(true);

		Object privateKey = session.getAttribute(Constants.RSA_KEY_LGIN);
		*/

		UserVo userVo = null;
		try {
			userVo = loginService.selectUser(loginid);
			
		}  catch (IOException e) {
			if (logger.isErrorEnabled()){
				logger.debug("err authenticate");
			}
			throw new BadCredentialsException("DB 접속에 오류가 있습니다.", e);
		} catch (SQLException e) {
			if (logger.isErrorEnabled()){
				logger.debug("err authenticate");
			}
			throw new BadCredentialsException("DB 접속에 오류가 있습니다.", e);
		}
	
		if (userVo == null) {
			throw new BadCredentialsException("존재하지 않는 아이디입니다.");
		} else {

			if(StringUtils.equals(userVo.getSecsnat(),"Y")){
				throw new BadCredentialsException("탈퇴된 사용자입니다. 시스템관리자에게 문의하십시오!");
			}
			
			//******  소셜로그인인경우  STR *********
			if(StringUtils.isNotBlank(userVo.getClassify())) {
				if (loginid.startsWith("K_") || loginid.startsWith("N_") || loginid.startsWith("G_")) {	// K_ : 카카오 , N_ : 네이버, G_ : 구글 가입자 
					if(StringUtils.equals(userVo.getClassify(), "01")) {								// classify 00 : 사이트가입자, 01 : 소셜가입자	
						return getAuthToken(userVo);
					}else {
						throw new BadCredentialsException("소셜 가입자가 아닙니다. 계정을 다시 확인해 주세요.");
					}
				} 
			}
			//******  소셜로그인인경우  END *********
			
			
			//비밀번호 검증
			//AS-IS 암호화방식 유지
			String sha = "X";
			String sha2 = userVo.getSha2();
			
			if ("E".equals(sha2)) {
				sha = "M";
			}
			else if ("Y".equals(sha2)) {
				sha = "S";
			}
			else if ("D".equals(sha2)) {
				sha = "D";
			}
			String hashPw = Utils.passwordEncryption(password, userVo.getEmplyrkey(), sha2);//패스워드 암호화
			
			if (StringUtils.equals(hashPw, userVo.getPassword())) { // 비밀번호 확인
				return getAuthToken(userVo);
			} else {
				throw new BadCredentialsException("아이디 혹은 비밀번호가 일치하지 않습니다.");
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	/**
	 *
	 * <pre>
	 * 인증에 사용될 정보를 생성.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 11. 12.
	 * @param userVo
	 * @return 
	 * @return
	 */
	private  UsernamePasswordAuthenticationToken getAuthToken(UserVo userVo) {
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userVo.getLoginid(), userVo.getPassword(), userVo.getAuthorities());
		result.setDetails(userVo);

		return result;
	}
	
}
