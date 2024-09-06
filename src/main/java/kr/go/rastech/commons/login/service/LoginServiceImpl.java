package kr.go.rastech.commons.login.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.dao.LoginDao;
import kr.go.rastech.commons.login.vo.UserVo;

/**
 * <pre>
 * FileName: LoginServiceImpl.java
 * Package : kr.go.ncmiklib.commons.login.service
 * 로그인 Service impl.
 * 
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 23.
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.commons.login.service.LoginService#selectIdCheck(java.lang.String)
	 */
	public int selectIdCheck(String loginid) throws IOException, SQLException , NullPointerException{
		return loginDao.selectIdCheck(loginid);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.commons.login.service.LoginService#selectUser(java.lang.String)
	 */
	public UserVo selectUser(String loginid)  throws IOException, SQLException , NullPointerException {
		UserVo userVo = loginDao.selectUser(loginid);
		if (userVo != null) {
			List<String> list = loginDao.selectUserAuthList(loginid);
			if (list == null || list.isEmpty()) {
				list = new ArrayList<String>();
			}
			list.add("ROLE_GUEST");
			list.add("ROLE_USER");
			userVo.setAuth(list);
		}
		return userVo;
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.commons.login.service.LoginService#listUrlAuth()
	 */
	public Map<RequestMatcher, Collection<ConfigAttribute>> listUrlAuth() throws IOException, SQLException , NullPointerException {
		List<Map<String, String>> list = loginDao.selectUrlAuthList();
		
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		String preUrl = null;
		String url = null;
		Collection<ConfigAttribute> configList = null;
		for(Map<String, String> map: list) {
			url = map.get("url");
			if (StringUtils.equals(url, preUrl)) {
				configList.add(new SecurityConfig(map.get("role")));
			} else {
				if (configList != null){ requestMap.put(new AntPathRequestMatcher(preUrl), configList);}
				configList = new LinkedList<ConfigAttribute>();
				configList.add(new SecurityConfig(map.get("role")));
			}
			preUrl = url;
		}
		if (list != null && !list.isEmpty()) {requestMap.put(new AntPathRequestMatcher(preUrl), configList);}

		return requestMap;
	}



	/* (non-Javadoc)
	 * @see kr.go.ncmiklib.commons.login.service.LoginService#insertStatLogin(java.lang.String)
	 */
	@Override
	public void insertStatLogin(Map<String, Object> param) throws IOException, SQLException , NullPointerException {
		loginDao.insertStatLogin(param);
	}

	/**
	 * <pre>
	 *
	 * 핸드폰 중복 체크 
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 6. 6. 
	 * @param vo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@Override
	public UserVo selectMbtl(String mbtlNum) throws IOException, SQLException , NullPointerException {
			return loginDao.selectMbtl(mbtlNum);

	}
	
	/**
	 * <pre>
	 *
	 * 이메일 중복 체크 
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 6. 18. 
	 * @param vo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@Override
	public UserVo selectEmail(String email) throws IOException, SQLException , NullPointerException {
			return loginDao.selectEmail(email);

	}


	@Override
	public void insertUser(Map<String, Object> ptlUserRegVo)throws IOException, SQLException, NullPointerException {
		loginDao.insertUser(ptlUserRegVo);
	}

	@Override
	public void insertEmail(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		loginDao.insertEmail(param);
	}

	@Override
	public void updateUser(Map<String, Object> ptlUserRegVo) throws IOException, SQLException, NullPointerException {
		loginDao.updateUser(ptlUserRegVo);
	}

	@Override
	public int updateEmail(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		return loginDao.updateEmail(param);
	}

	@Override
	public int deleteUser(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		int cnt = loginDao.deleteUser(param);
		param.put("ui_email","");  
		loginDao.updateEmail(param);
		return cnt;
	}

	@Override
	public String selectUserEmail(String mbtlnum) throws IOException, SQLException, NullPointerException {
		return loginDao.selectUserEmail(mbtlnum);
	}

	@Override
	public void changePwd(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		loginDao.changePwd(param);
	}


	/* (non-Javadoc)
	 * @see kr.go.ncmik.commons.login.service.LoginService#insertStatLogin(java.lang.String)
	 */
	@Override
	public void insertStatLogin(String user_id) throws IOException, SQLException , NullPointerException {
		loginDao.insertStatLogin(user_id);
	}

	@Override
	public String selectfindUserChk(String sMobileNo) throws IOException, SQLException, NullPointerException {
		return loginDao.selectfindUserChk(sMobileNo);
	}

	/**
	  * 아이디찾기
	  * @작성일 : 2023. 4. 26.
	  * @작성자 : ljk
	  * @param mbtlnum
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	@Override
	public String selectfindUserId(String mbtlnum) throws IOException, SQLException, NullPointerException {
		return loginDao.selectfindUserId(mbtlnum);
	}

	
	/**
	  * 비밀번호찾기
	  * @작성일 : 2023. 4. 26.
	  * @작성자 : ljk
	  * @param mbtlnum
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	@Override
	public String selectfindUserKey(String mbtlnum) throws IOException, SQLException, NullPointerException {
		return loginDao.selectfindUserKey(mbtlnum);
	}

	@Override
	public UserVo UserIdPassCheck(Map<String, Object> usrMap) throws IOException, SQLException, NullPointerException {
		return loginDao.UserIdPassCheck(usrMap);
	}

}
