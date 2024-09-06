package kr.go.rastech.ptl.mng.user.vo;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.utils.CryptoUtil;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;



@Alias("mngUserLogVo")
public class MngUserLogVo extends MngCodeVo  implements Serializable  {


	/** serialVersionUID */
	private static final long serialVersionUID = 1148645982551553029L;
	
	private int seq;
	private String loginid;
	private String user_nm;
	private String log_ip;
	private String log_dttm;
	private String user_log_cont;
	private String view_loginid;
	private String view_user_sn;
	private String mdfy_dttm;
	private String st_dt;
	private String ed_dt;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getloginid() {
		return loginid;
	}
	public void setloginid(String loginid) {
		this.loginid = loginid;
	}
	public String getLog_ip() {
		return log_ip;
	}
	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}
	public String getUser_log_cont() {
		return user_log_cont;
	}
	public void setUser_log_cont(String user_log_cont) {
		this.user_log_cont = user_log_cont;
	}
	public String getView_loginid() {
		return view_loginid;
	}
	public void setView_loginid(String view_loginid) {
		this.view_loginid = view_loginid;
	}
	public String getMdfy_dttm() {
		return mdfy_dttm;
	}
	public void setMdfy_dttm(String mdfy_dttm) {
		this.mdfy_dttm = mdfy_dttm;
	}
	public String getSt_dt() {
		return st_dt;
	}
	public void setSt_dt(String st_dt) {
		this.st_dt = st_dt;
	}
	public String getEd_dt() {
		return ed_dt;
	}
	public void setEd_dt(String ed_dt) {
		this.ed_dt = ed_dt;
	}
	
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getLog_dttm() {
		return log_dttm;
	}
	public void setLog_dttm(String log_dttm) {
		this.log_dttm = log_dttm;
	}
	public String getView_user_sn() {
		return view_user_sn;
	}
	public void setView_user_sn(String view_user_sn) {
		this.view_user_sn = view_user_sn;
	}

}
