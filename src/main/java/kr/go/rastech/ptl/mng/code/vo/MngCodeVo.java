package kr.go.rastech.ptl.mng.code.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;


/**
 * <pre>
 * FileName: MngCodeVo.java
 * Package : kr.go.ncmiklib.ptl.mng.code.vo
 * 
 * 코드관리 vo
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 1. 19.
 */

@Alias("mngCodeVo")
public class MngCodeVo extends CommonsVo implements Serializable   {

	/** serialVersionUID */
	private static final long serialVersionUID = -3052825353744511974L;

	private String up_cd;	      //상위 공통 코드				
	private String cd;	          //코드	
	private String cd_gbn;	          //코드	
	private String cd_nm;	      //코드 명				
	private String cd_desc;	      //코드 설명				
	private String cd_ord;	      //코드 정렬 순서				
	private String ref_val1;	  //참조 칼럼 1 내용				
	private String ref_val2;	  //참조 칼럼 2 내용				
	private String ref_val3;	  //참조 칼럼 3 내용				
	private String use_yn;	      //삭제여부			
	private String cd_lang;		  //코드언어
	private String grp_cd;		  //코드언어
	
	
	public String getUp_cd() {
		return up_cd;
	}
	public void setUp_cd(String up_cd) {
		this.up_cd = up_cd;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCd_gbn() {
		return cd_gbn;
	}
	public void setCd_gbn(String cd_gbn) {
		this.cd_gbn = cd_gbn;
	}
	public String getCd_nm() {
		return cd_nm;
	}
	public void setCd_nm(String cd_nm) {
		this.cd_nm = cd_nm;
	}
	public String getCd_desc() {
		return cd_desc;
	}
	public void setCd_desc(String cd_desc) {
		this.cd_desc = cd_desc;
	}
	public String getCd_ord() {
		return cd_ord;
	}
	public void setCd_ord(String cd_ord) {
		this.cd_ord = cd_ord;
	}
	public String getRef_val1() {
		return ref_val1;
	}
	public void setRef_val1(String ref_val1) {
		this.ref_val1 = ref_val1;
	}
	public String getRef_val2() {
		return ref_val2;
	}
	public void setRef_val2(String ref_val2) {
		this.ref_val2 = ref_val2;
	}
	public String getRef_val3() {
		return ref_val3;
	}
	public void setRef_val3(String ref_val3) {
		this.ref_val3 = ref_val3;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getCd_lang() {
		return cd_lang;
	}
	public void setCd_lang(String cd_lang) {
		this.cd_lang = cd_lang;
	}
	public String getGrp_cd() {
		return grp_cd;
	}
	public void setGrp_cd(String grp_cd) {
		this.grp_cd = grp_cd;
	}

}
