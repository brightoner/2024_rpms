package kr.go.rastech.ptl.member.regi.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;



@Alias("ptlUserRegVo")
public class PtlUserRegVo extends CommonsVo implements Serializable  {


	private static final long serialVersionUID = -2973577475097405362L;

	/** 사용자KEY */
	private String emplyrkey;
	
	/** 로그인ID */
	private String user_id;
	
	/** 닉네임 */
	private String nicknm;
	
	/** 비밀번호 */
	private String password;
	
	/** 휴대폰번호 */
	private String mbtlnum;
	
	/** 이메일 */
	private String email;
	/** @ 앞부분*/
	private String email1;
	/** @ 뒷부분 */
	private String email2;
	
	/** 핸드폰(성인) 인증 유무 */
	private String mbtlnum_yn;
	
	/** SHA2암호화여부 */
	private String sha2;
	
	/** 회원가입구분(00.사이트가입자,01.소셜가입자) */
	private String classify;
	
	/** 탈퇴처리자KEY */
	private String secsnopetrkey;
	
	/** 탈퇴일시 */
	private String secsntredt;
	
	/** 탈퇴여부 */
	private String secsnat;
	
	/** 최종로그인일시 */
	private String lastlogindt;
	
	/** 최종로그아웃일시 */
	private String lastlogoutdt;
	
	/** 최종수정일시 */
	private String lastupddt;
	
	/** 최종수정자KEY */
	private String lastupdusrkey;
	
	/** 등록일 */
	private String reg_date;
	
	/** 이용약관 */
	private String agree;

	/** 문자메시지수신동의 */
	private String smsAgree;
	
	/** 메일수신동의 */
	private String emailAgree;
	
	private String orgnm;
	
	private String deptnm;
	
	private String posnm;

	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getSha2() {
		return sha2;
	}

	public void setSha2(String sha2) {
		this.sha2 = sha2;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getSecsnopetrkey() {
		return secsnopetrkey;
	}

	public void setSecsnopetrkey(String secsnopetrkey) {
		this.secsnopetrkey = secsnopetrkey;
	}

	public String getSecsntredt() {
		return secsntredt;
	}

	public void setSecsntredt(String secsntredt) {
		this.secsntredt = secsntredt;
	}

	public String getSecsnat() {
		return secsnat;
	}

	public void setSecsnat(String secsnat) {
		this.secsnat = secsnat;
	}

	public String getLastlogindt() {
		return lastlogindt;
	}

	public void setLastlogindt(String lastlogindt) {
		this.lastlogindt = lastlogindt;
	}

	public String getLastlogoutdt() {
		return lastlogoutdt;
	}

	public void setLastlogoutdt(String lastlogoutdt) {
		this.lastlogoutdt = lastlogoutdt;
	}

	public String getLastupddt() {
		return lastupddt;
	}

	public void setLastupddt(String lastupddt) {
		this.lastupddt = lastupddt;
	}

	public String getLastupdusrkey() {
		return lastupdusrkey;
	}

	public void setLastupdusrkey(String lastupdusrkey) {
		this.lastupdusrkey = lastupdusrkey;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getSmsAgree() {
		return smsAgree;
	}

	public void setSmsAgree(String smsAgree) {
		this.smsAgree = smsAgree;
	}

	public String getEmailAgree() {
		return emailAgree;
	}

	public void setEmailAgree(String emailAgree) {
		this.emailAgree = emailAgree;
	}

	public String getOrgnm() {
		return orgnm;
	}

	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}

	public String getDeptnm() {
		return deptnm;
	}

	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}

	public String getPosnm() {
		return posnm;
	}

	public void setPosnm(String posnm) {
		this.posnm = posnm;
	}

	
	
	
}
