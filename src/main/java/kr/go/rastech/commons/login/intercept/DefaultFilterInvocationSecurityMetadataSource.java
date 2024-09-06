/******************************************************************
 * Copyright RASTECH 2016
 ******************************************************************/
package kr.go.rastech.commons.login.intercept;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import kr.go.rastech.base.BaseObj;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.ptl.mng.auth.service.MngAuthService;
import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;

/**
 * <pre>
 * FileName: DefaultFilterInvocationSecurityMetadataSource.java
 * Package : kr.go.ncmiklib.commons.login.intercept
 *
 * URL과 권한을 메핑하여 처리한다.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 9. 22.
 */
public class DefaultFilterInvocationSecurityMetadataSource extends BaseObj implements FilterInvocationSecurityMetadataSource {

	/**
	 * URL과 권한 메핑 맵.
	 */
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	private static List<MngAuthVo> authMap;

	@Resource
	private LoginService loginService;

	@Resource
	private MngAuthService mngAuthService;
	
	private static long lAST_TIME;
	
	static{
		lAST_TIME = 0;
	}

	/**
	 *
	 * <pre>
	 * 초기화를 수행한다.
	 * 권한 관련 내용이 DB에 변경된 경우 호출해 주면 재적용 된다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 1.
	 */
	public void init()  {
		try {
			this.requestMap = loginService.listUrlAuth();
			this.authMap= mngAuthService.listMenuAuth();
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {
				logger.debug("error SQLException init");
			}
		}catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.debug("error IOException init");
			}
		}
	}

	/**
	 * <pre>
	 * URL의 권한을 반환.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 22.
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		long now = System.currentTimeMillis();
		if (now > lAST_TIME) {
			init();
			lAST_TIME = now + 60000;
		}
		Collection<ConfigAttribute> result = null;
		FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getHttpRequest();
        for (RequestMatcher requestMatcher: requestMap.keySet()) {
        	if (requestMatcher.matches(request)) {
        		result = requestMap.get(requestMatcher);
        		break;
        	}
        }

		return result;
	}

	/**
	 * <pre>
	 * 모든 권한 반환.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 22.
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		long now = System.currentTimeMillis();
		if (now > lAST_TIME) {
			init();
			lAST_TIME = now + 60000;
		}
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	/**
	 * <pre>
	 * 메뉴별 권한 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @param location_path
	 * @param request
	 * @return
	 */
	public List<MngAuthVo> getMenuAuth(String location_path,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		final List<String> roleList;
		final List<String> roleList2 = new ArrayList<String>();
		final String url_path = location_path;
		
		if(session.getAttribute("userVo") == null){
			roleList = Arrays.asList("ROLE_GUEST");
		}else{
			UserVo userVo = (UserVo) session.getAttribute("userVo");
			roleList = userVo.getRoleList();
		}
		if(roleList != null){
			for (int i = 0; i < roleList.size(); i++) {
				roleList2.add(StringUtil.filterXss(roleList.get(i)));
			}
		}

		List<MngAuthVo> authList =  Lists.newArrayList(Collections2.filter(authMap,new Predicate<MngAuthVo>() {
	  		public boolean apply(MngAuthVo mngAuthVo) {
	  				return StringUtils.equals(mngAuthVo.getUrl(),url_path) && roleList2.contains(mngAuthVo.getRol_id());
	  			}
		}));
		
		return authList;
	}

}
