/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.login.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.go.rastech.base.vo.BaseVo;

/**
 * <pre>
 * FileName: UserVo.java
 * Package : kr.go.ncmiklib.commons.login.vo
 * 
 * 사용자관리 -
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 23.
 */


@Alias("userVo")
public class UserVo extends BaseVo implements UserDetails {
    /** serialVersionUID */
    private static final long serialVersionUID = -3830662233084098293L;

    private String loginid;			    // 사용자 ID
	private String password;			// 사용자 비밀번호
	private String email;		   	    // 이메일
	private String id;                  // 사용자순번
	private String emplyrkey;           // 사용자순번	
	private String nicknm;            	// 닉네임 
	private String brthdy;				//생년월일
	private String username;			//유저명
	private String secsnat;             // 탈퇴여부
	private String mbtlnum;				// 전화번호
	private String mbtlnum_yn;			// pass 인증여부
	private String classify;			// 회원가입구분(00.사이트가입자,01.소셜가입자)
	private String reg_date;			// 등록일
	private String agree;				// 약관동의
	private String smsagree;			// 문자메시지수신동의
	private String emailagree;			// 메일수신동의

	
	private String sha2 = "Y";		    // sha여부	
	private String usrIp;        	    // 사용자 IP
	private String internalIp;          // 내부이용자 유
	
	
	private List<String> roleList;      // 사용자 권한
	private String engYn = "N";         // 영문메뉴여부
	private String sel_Menu = "";       // 선택 메뉴

	
	private Map<String,String> msgMap = new HashMap<String, String>();
	
	// 권한
	public List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	/**
	 *
	 * <pre>
	 * 권한을 가지고 있는지 확인한다.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 1.
	 * @param authCd
	 * @return
	 */
	public boolean isAuth(String authCd) {
		boolean isAuth = false;
		for (GrantedAuthority ga: authorities) {
			if (StringUtils.equals(ga.getAuthority(), authCd)) {
				isAuth = true;
				break;
			}
		}
		return isAuth;
	}

	public String getSha2() {
		return sha2;
	}

	public void setSha2(String sha2) {
		this.sha2 = sha2;
	}

	public void setAuth(List<String> list) {

		if(list != null){
			this.roleList = new ArrayList<String>(list.size());
			for (int i = 0; i < list.size(); ++i){
				this.roleList.add(i, list.get(i));
			}
			for(String auth: list) authorities.add(new SimpleGrantedAuthority(auth));
		}		
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmplyrkey() {
		return emplyrkey;
	}

	public void setEmplyrkey(String emplyrkey) {
		this.emplyrkey = emplyrkey;
	}

	public List<String> getRoleList() {
		
		List<String> newRoleList = null; 
		if(this.roleList !=null) {
			newRoleList = 	new ArrayList<String>();
			newRoleList = this.roleList;
		}					
	    return newRoleList;	
	}

	public void setRoleList(List<String> roleList) {
		if(roleList != null){
			this.roleList = new ArrayList<String>(roleList.size());
			for (int i = 0; i < roleList.size(); ++i){
				this.roleList.add(i, roleList.get(i));
			}
		}	
	}

	public String getEngYn() {
		return engYn;
	}

	public void setEngYn(String engYn) {
		this.engYn = engYn;
	}

	public String getSel_Menu() {
		return sel_Menu;
	}

	public void setSel_Menu(String sel_Menu) {
		this.sel_Menu = sel_Menu;
	}

	public String getNicknm() {
		return nicknm;
	}

	public void setNicknm(String nicknm) {
		this.nicknm = nicknm;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getSecsnat() {
		return secsnat;
	}

	public void setSecsnat(String secsnat) {
		this.secsnat = secsnat;
	}

	public String getUsrIp() {
		return usrIp;
	}

	public void setUsrIp(String usrIp) {
		this.usrIp = usrIp;
	}

	public String getInternalIp() {
		return internalIp;
	}

	public void setInternalIp(String internalIp) {
		this.internalIp = internalIp;
	}
	

	public String getMbtlnum() {
		return mbtlnum;
	}

	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}
	
	public String getMbtlnum_yn() {
		return mbtlnum_yn;
	}

	public void setMbtlnum_yn(String mbtlnum_yn) {
		this.mbtlnum_yn = mbtlnum_yn;
	}



	public Map<String, String> getMsgMap() {
		return msgMap;
	}

	public void setMsgMap(Map<String, String> msgMap) {
		this.msgMap = msgMap;
	}

	public String getBrthdy() {
		return brthdy;
	}

	public void setBrthdy(String brthdy) {
		this.brthdy = brthdy;
	}
	


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getSmsagree() {
		return smsagree;
	}

	public void setSmsagree(String smsagree) {
		this.smsagree = smsagree;
	}

	public String getEmailagree() {
		return emailagree;
	}

	public void setEmailagree(String emailagree) {
		this.emailagree = emailagree;
	}

	

	

}
