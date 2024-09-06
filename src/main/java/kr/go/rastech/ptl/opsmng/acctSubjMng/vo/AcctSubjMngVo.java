package kr.go.rastech.ptl.opsmng.acctSubjMng.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;


/**
 * <pre>
 * FileName: MngMenuVo.java
 * Package : kr.go.ncmiklib.ptl.mng.subj.vo
 * mng Menu 관리 Vo.
 * 
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 16.
 */

@Alias("acctSubjMngVo")
public class AcctSubjMngVo extends CommonsVo implements Serializable   {

	/** serialVersionUID */
	private static final long serialVersionUID = 519380524109527938L;

	private String subj_id;             /* ID */ 
	private String subj_nm;             /* 산업분류명 */ 
	private int subj_levl;              /* 메뉴레벨 */ 
	private String subj_prts_id;        /*  ID */ 
	private String subj_no;        /* no */ 
	private String use_yn;         /* 사용여부 */
	public String getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(String subj_id) {
		this.subj_id = subj_id;
	}
	public String getSubj_nm() {
		return subj_nm;
	}
	public void setSubj_nm(String subj_nm) {
		this.subj_nm = subj_nm;
	}
	public int getSubj_levl() {
		return subj_levl;
	}
	public void setSubj_levl(int subj_levl) {
		this.subj_levl = subj_levl;
	}
	public String getSubj_prts_id() {
		return subj_prts_id;
	}
	public void setSubj_prts_id(String subj_prts_id) {
		this.subj_prts_id = subj_prts_id;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getSubj_no() {
		return subj_no;
	}
	public void setSubj_no(String subj_no) {
		this.subj_no = subj_no;
	} 

	
	
}
