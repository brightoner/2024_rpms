package kr.go.rastech.ptl.member.inform.vo;

import org.apache.ibatis.type.Alias;


@Alias("ptlInformVo")
public class PtlInformVo {
	//private static final long serialVersionUID = 1L;
	
	/** USER ID */
	private String user_id;
	
	/** 약관동의 */
	private String agree;
	
	/** 문자동의 */
	private String smsAgree;
	
	/** 메일동의 */
	private String emailAgree;
	
	/** 이메일 */
	private String email;
	
	/** 이메일1 */
	private String email1;
	
	/** 이메일2 */
	private String email2;
	
	/** totcnt */
	private int totcnt;
	
	
	/** Age Agree */
	private String ageAgree;

	/** 사용자 이름 */
	private String username;
	
	/** 사용자 닉네임 */
	private String nickNm;

	
    /** MBTLNUM */
    private String mbtlnum;

    /** MBTLNUM_YN */
    private String mbtlnumYn;
    
    /** BRTHDY */
    private String brthdy;    

    private String reg_date; 
    

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public int getTotcnt() {
		return totcnt;
	}

	public void setTotcnt(int totcnt) {
		this.totcnt = totcnt;
	}

	public String getAgeAgree() {
		return ageAgree;
	}

	public void setAgeAgree(String ageAgree) {
		this.ageAgree = ageAgree;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getNickNm() {
		return nickNm;
	}

	public void setNickNm(String nickNm) {
		this.nickNm = nickNm;
	}


	public String getMbtlnum() {
		return mbtlnum;
	}

	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}


	public String getMbtlnumYn() {
		return mbtlnumYn;
	}

	public void setMbtlnumYn(String mbtlnumYn) {
		this.mbtlnumYn = mbtlnumYn;
	}

	public String getBrthdy() {
		return brthdy;
	}

	public void setBrthdy(String brthdy) {
		this.brthdy = brthdy;
	}

	
	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	
}
