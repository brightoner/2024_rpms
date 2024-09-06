package kr.go.rastech.ptl.member.inform.vo;

import org.apache.ibatis.type.Alias;

@Alias("PtlBjApprlVo")
public class PtlBjApprlVo {
	
	
	/** ptl_bj_apprl - bj 승인관리테이블 */
	/** BJ 승인 시퀀스 */
	private String bj_approval_id;
	/** 사용자키 */
	private String emplyrkey;
	/** 닉네임 */
	private String user_id;
	/** 아이디 */
	private String nicknm;
	/** 방송컨텐츠 */
	private String bc_contents;
	/** 방송예정일 */
	private String bc_schdl_date;
	/** 방송주기 */
	private String bc_cycle;
	/** 방송이력 */
	private String bc_history;
	/**기타정보  */
	private String bc_etc;
	/**신청일  */
	private String bc_apply_dttm;
	/** BJ 권한(Y : 승인 ,N : 반려 ,F : 승인취소) */
	private String bj_auth;
	
	/** 반려사유 */
	private String confirm_comment;
	/** 처리날짜 */
	private String confirm_dttm;
	
	
	public String getBj_approval_id() {
		return bj_approval_id;
	}
	public void setBj_approval_id(String bj_approval_id) {
		this.bj_approval_id = bj_approval_id;
	}
	public String getEmplyrkey() {
		return emplyrkey;
	}
	public void setEmplyrkey(String emplyrkey) {
		this.emplyrkey = emplyrkey;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getNicknm() {
		return nicknm;
	}
	public void setNicknm(String nicknm) {
		this.nicknm = nicknm;
	}
	public String getBc_contents() {
		return bc_contents;
	}
	public void setBc_contents(String bc_contents) {
		this.bc_contents = bc_contents;
	}
	public String getBc_schdl_date() {
		return bc_schdl_date;
	}
	public void setBc_schdl_date(String bc_schdl_date) {
		this.bc_schdl_date = bc_schdl_date;
	}
	public String getBc_cycle() {
		return bc_cycle;
	}
	public void setBc_cycle(String bc_cycle) {
		this.bc_cycle = bc_cycle;
	}
	public String getBc_history() {
		return bc_history;
	}
	public void setBc_history(String bc_history) {
		this.bc_history = bc_history;
	}
	public String getBc_etc() {
		return bc_etc;
	}
	public void setBc_etc(String bc_etc) {
		this.bc_etc = bc_etc;
	}
	public String getBj_auth() {
		return bj_auth;
	}
	public void setBj_auth(String bj_auth) {
		this.bj_auth = bj_auth;
	}
	public String getBc_apply_dttm() {
		return bc_apply_dttm;
	}
	public void setBc_apply_dttm(String bc_apply_dttm) {
		this.bc_apply_dttm = bc_apply_dttm;
	}
	public String getConfirm_comment() {
		return confirm_comment;
	}
	public void setConfirm_comment(String confirm_comment) {
		this.confirm_comment = confirm_comment;
	}
	public String getConfirm_dttm() {
		return confirm_dttm;
	}
	public void setConfirm_dttm(String confirm_dttm) {
		this.confirm_dttm = confirm_dttm;
	}
	
	
	

}
