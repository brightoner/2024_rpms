package kr.go.rastech.ptl.mng.user.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;



@Alias("mngUserAuthVo")
public class MngUserAuthVo extends MngCodeVo  implements Serializable   {

	/** serialVersionUID */
	private static final long serialVersionUID = 4001698408979408002L;

	private String loginid;	        /* 사용자ID*/		
	private String user_auth_cd;	/* 사용자 권한*/
	
	public String getloginid() {
		return loginid;
	}
	public void setloginid(String loginid) {
		this.loginid = loginid;
	}
	public String getUser_auth_cd() {
		return user_auth_cd;
	}
	public void setUser_auth_cd(String user_auth_cd) {
		this.user_auth_cd = user_auth_cd;
	}

}
