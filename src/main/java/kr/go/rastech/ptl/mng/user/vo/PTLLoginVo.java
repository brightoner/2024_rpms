package kr.go.rastech.ptl.mng.user.vo;

import java.io.IOException;
import java.io.Serializable;
import java.security.PrivateKey;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;
import org.bouncycastle.crypto.InvalidCipherTextException;

import kr.go.rastech.commons.utils.CryptoUtil;
import kr.go.rastech.commons.vo.CommonsVo;


/**
 * <pre>
 * LoginVO
 * 
 * </pre>
 * @FileName : PTLLoginVo.java
 * @package  : kr.go.ncmiklib.ptl.mng.user.vo
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
@Alias("ptlLoginVo")
public class PTLLoginVo extends CommonsVo   implements Serializable  {
	private static final long serialVersionUID = -8274004534207618049L;
	
	private String elibagree;		// 전자자원승인여부
	private String elibDt;			// 전자자원승인일
	private String ageagree;		// 16세이상 여부
	private String pmcconfirm;		// pmc 사용승인여부
	private String pmcconfirmDt;	// pmc 사용승인일시
	private String mouLoan;	// pmc 사용승인일시
	
	private String emplyrkey;
	private String id;
	private String uniqId;
	private String loginid;
	private String nicknm;
	private String password;
	private String oldPassword;
	private String clsfcode;
	private String uchrgjob;
	private String email;
	private String mbtlnum;
	private String telno;
	private String fxnum;
	private String secsnat;
	private String brthdy;
	private String adstrdcode;
	private String ogcr;
	private String empat;
	private String emptelno;
	private String empchrgjob;
	private String empsortordr;
	private String empuseat;
	private Date creatdt;
	private String lastupdusrkey;
	private Date lastupddt;
	private String secsnopetrkey;
	private Date secsntredt;
	private Date lastlogindt;
	private Date lastlogoutdt;
	private String insttsn;
	private String insttnm;
	private String instttyid;
	private String insttsttus;
	private String sha2;
	private String mbtlnumyn;
	private String pwmoddate;
	private String pwmodafter;
	private String chk;
	
	private String agree;			// 약관동의
	private String newdatayn;		// 신착자료 수신여부
	private String newdatacnt;		// 신착자료 알림일수
	private String smsagree;		// 문자동의
	private String emailagree;		// 메일동의
	private String useremail;		// 이메일
	private String addressagree;	// 정보동의
	private String address;			// 주소
	private String pmcagree;		// PMC동의
	private String orcid;			// ORCID
	private String senumber;		// 과학기술인등록번호
	private String meshagree;		// MeSH동의
	private String mesh;			// MeSH 이용서비스
	private String pmc;				// PMC 이용서비스
	
	private String searchCondition1;
	private String searchCondition2;
	private String searchCondition3;
	private String searchCondition4;
	
	/** 공인인증서 로그인 */
	private String result;			//S: 성공, F: 실패
	private String rcode;			//[EMPLYRKEY]: 사용자KEY값(result가 S인경우), 1: 인증서의 서명이 유효하지 않습니다, 2: 인증서의 유효기간이 지났습니다, 3: 인증서가 폐기되었습니다, 4: 인증서가 효력정지상태입니다, 9: 알수없는 인증시스템 오류, 11: 등록되어 있지 않은 인증서, 12: 탈퇴한 사용자, 99: 알수없는 시스템 오류

	private String usrIp;           // 사용자 IP
	private String internalIp;        // 내부이용자 유
	
	
	private String empip;
	private String mouOrg;
	private String mouSub;	
	private String usrAuth;
	private String searchStatus;
	private String searchText;
	
	
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getPmc() {
		return pmc;
	}

	public void setPmc(String pmc) {
		this.pmc = pmc;
	}

	public String getEmplyrkey() {
		return emplyrkey;
	}

	public void setEmplyrkey(String emplyrkey) {
		this.emplyrkey = emplyrkey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getUniqId() {
		return uniqId;
	}

	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
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

	public String getClsfcode() {
		return clsfcode;
	}

	public void setClsfcode(String clsfcode) {
		this.clsfcode = clsfcode;
	}

	public String getUchrgjob() {
		return uchrgjob;
	}

	public void setUchrgjob(String uchrgjob) {
		this.uchrgjob = uchrgjob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMbtlnum() {
		return mbtlnum;
	}

	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getFxnum() {
		return fxnum;
	}

	public void setFxnum(String fxnum) {
		this.fxnum = fxnum;
	}

	public String getSecsnat() {
		return secsnat;
	}

	public void setSecsnat(String secsnat) {
		this.secsnat = secsnat;
	}

	public String getBrthdy() {
		return brthdy;
	}

	public void setBrthdy(String brthdy) {
		this.brthdy = brthdy;
	}

	public String getAdstrdcode() {
		return adstrdcode;
	}

	public void setAdstrdcode(String adstrdcode) {
		this.adstrdcode = adstrdcode;
	}

	public String getOgcr() {
		return ogcr;
	}

	public void setOgcr(String ogcr) {
		this.ogcr = ogcr;
	}

	public String getEmpat() {
		return empat;
	}

	public void setEmpat(String empat) {
		this.empat = empat;
	}

	public String getEmptelno() {
		return emptelno;
	}

	public void setEmptelno(String emptelno) {
		this.emptelno = emptelno;
	}

	public String getEmpchrgjob() {
		return empchrgjob;
	}

	public void setEmpchrgjob(String empchrgjob) {
		this.empchrgjob = empchrgjob;
	}

	public String getEmpsortordr() {
		return empsortordr;
	}

	public void setEmpsortordr(String empsortordr) {
		this.empsortordr = empsortordr;
	}

	public String getEmpuseat() {
		return empuseat;
	}

	public void setEmpuseat(String empuseat) {
		this.empuseat = empuseat;
	}

	public Date getCreatdt() {
		return creatdt;
	}

	public void setCreatdt(Date creatdt) {
		this.creatdt = creatdt;
	}

	public String getLastupdusrkey() {
		return lastupdusrkey;
	}

	public void setLastupdusrkey(String lastupdusrkey) {
		this.lastupdusrkey = lastupdusrkey;
	}

	public Date getLastupddt() {
		return lastupddt;
	}

	public void setLastupddt(Date lastupddt) {
		this.lastupddt = lastupddt;
	}

	public String getSecsnopetrkey() {
		return secsnopetrkey;
	}

	public void setSecsnopetrkey(String secsnopetrkey) {
		this.secsnopetrkey = secsnopetrkey;
	}

	public Date getSecsntredt() {
		return secsntredt;
	}

	public void setSecsntredt(Date secsntredt) {
		this.secsntredt = secsntredt;
	}

	public Date getLastlogindt() {
		return lastlogindt;
	}

	public void setLastlogindt(Date lastlogindt) {
		this.lastlogindt = lastlogindt;
	}

	public Date getLastlogoutdt() {
		return lastlogoutdt;
	}

	public void setLastlogoutdt(Date lastlogoutdt) {
		this.lastlogoutdt = lastlogoutdt;
	}

	public String getInsttsn() {
		return insttsn;
	}

	public void setInsttsn(String insttsn) {
		this.insttsn = insttsn;
	}

	public String getInsttnm() {
		return insttnm;
	}

	public void setInsttnm(String insttnm) {
		this.insttnm = insttnm;
	}

	public String getInstttyid() {
		return instttyid;
	}

	public void setInstttyid(String instttyid) {
		this.instttyid = instttyid;
	}

	public String getInsttsttus() {
		return insttsttus;
	}

	public void setInsttsttus(String insttsttus) {
		this.insttsttus = insttsttus;
	}

	public String getSha2() {
		return sha2;
	}

	public void setSha2(String sha2) {
		this.sha2 = sha2;
	}

	public String getMbtlnumyn() {
		return mbtlnumyn;
	}

	public void setMbtlnumyn(String mbtlnumyn) {
		this.mbtlnumyn = mbtlnumyn;
	}

	public String getPwmoddate() {
		return pwmoddate;
	}

	public void setPwmoddate(String pwmoddate) {
		this.pwmoddate = pwmoddate;
	}

	public String getPwmodafter() {
		return pwmodafter;
	}

	public void setPwmodafter(String pwmodafter) {
		this.pwmodafter = pwmodafter;
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getNewdatayn() {
		return newdatayn;
	}

	public void setNewdatayn(String newdatayn) {
		this.newdatayn = newdatayn;
	}

	public String getNewdatacnt() {
		return newdatacnt;
	}

	public void setNewdatacnt(String newdatacnt) {
		this.newdatacnt = newdatacnt;
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

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getAddressagree() {
		return addressagree;
	}

	public void setAddressagree(String addressagree) {
		this.addressagree = addressagree;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPmcagree() {
		return pmcagree;
	}

	public void setPmcagree(String pmcagree) {
		this.pmcagree = pmcagree;
	}

	public String getOrcid() {
		return orcid;
	}

	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}

	public String getSenumber() {
		return senumber;
	}

	public void setSenumber(String senumber) {
		this.senumber = senumber;
	}

	public String getMeshagree() {
		return meshagree;
	}

	public void setMeshagree(String meshagree) {
		this.meshagree = meshagree;
	}

	public String getMesh() {
		return mesh;
	}

	public void setMesh(String mesh) {
		this.mesh = mesh;
	}

	public String getSearchCondition1() {
		return searchCondition1;
	}

	public void setSearchCondition1(String searchCondition1) {
		this.searchCondition1 = searchCondition1;
	}

	public String getSearchCondition2() {
		return searchCondition2;
	}

	public void setSearchCondition2(String searchCondition2) {
		this.searchCondition2 = searchCondition2;
	}

	public String getSearchCondition3() {
		return searchCondition3;
	}

	public void setSearchCondition3(String searchCondition3) {
		this.searchCondition3 = searchCondition3;
	}

	public String getSearchCondition4() {
		return searchCondition4;
	}

	public void setSearchCondition4(String searchCondition4) {
		this.searchCondition4 = searchCondition4;
	}

	public String getPmcconfirm() {
		return pmcconfirm;
	}

	public void setPmcconfirm(String pmcconfirm) {
		this.pmcconfirm = pmcconfirm;
	}

	public String getPmcconfirmDt() {
		return pmcconfirmDt;
	}

	public void setPmcconfirmDt(String pmcconfirmDt) {
		this.pmcconfirmDt = pmcconfirmDt;
	}
	
	public String getMouLoan() {
		return mouLoan;
	}

	public void setMouLoan(String mouLoan) {
		this.mouLoan = mouLoan;
	}

	public String getAgeagree() {
		return ageagree;
	}

	public void setAgeagree(String ageagree) {
		this.ageagree = ageagree;
	}

	public String getElibagree() {
		return elibagree;
	}

	public void setElibagree(String elibagree) {
		this.elibagree = elibagree;
	}

	public String getElibDt() {
		return elibDt;
	}

	public void setElibDt(String elibDt) {
		this.elibDt = elibDt;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
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

	public String getEmpip() {
		return empip;
	}

	public void setEmpip(String empip) {
		this.empip = empip;
	}
	
	public String getMouOrg() {
		return mouOrg;
	}

	public void setMouOrg(String mouOrg) {
		this.mouOrg = mouOrg;
	}

	public String getMouSub() {
		return mouSub;
	}

	public void setMouSub(String mouSub) {
		this.mouSub = mouSub;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUsrAuth() {
		return usrAuth;
	}

	public void setUsrAuth(String usrAuth) {
		this.usrAuth = usrAuth;
	}
	

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	/**
    *
    * <pre>
    * 클라이언트(브라우저)에서 받은 RSA로 암호화 된 값을 복호화 한다.
    *
    * </pre>
    * @author : rastech
    * @date   : 2023. 11. 17.
    * @param privateKey
    * @throws IOException, SQLException , NullPointerException
    */
   public void decryptRSA(PrivateKey privateKey) throws IOException, SQLException , NullPointerException , InvalidCipherTextException {
      this.loginid = CryptoUtil.decryptRSA(this.loginid, privateKey);
      if(!StringUtils.isBlank(this.password)){
         this.password = CryptoUtil.decryptRSA(this.password, privateKey);
         this.oldPassword = CryptoUtil.decryptRSA(this.oldPassword, privateKey);
      }
   }

   /**
    *
    * <pre>
    * 사용자 정보를 AES로 암호화한다.
    *
    * </pre>
    * @author : rastech
    * @date   : 2023. 11. 17.
    */
   public void encryptAES() {
     //if (StringUtils.isNoneEmpty(this.eml)) this.eml = CryptoUtil.encrypt(this.eml);
	   logger.debug("xx");
   }
}
