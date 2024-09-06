package kr.go.rastech.ptl.opsmng.tclsMng.vo;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;


/**
 * <pre>
 * FileName: MngMenuVo.java
 * Package : kr.go.ncmiklib.ptl.mng.tcls.vo
 * mng Menu 관리 Vo.
 * 
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 16.
 */

@Alias("tclsMngVo")
public class TclsMngVo extends CommonsVo implements Serializable   {

	/** serialVersionUID */
	private static final long serialVersionUID = 519380524109527938L;

	private String tcls_id;             /* 산업분류고유ID */ 
	private String tcls_nm;             /* 산업분류명 */ 
	private int tcls_levl;              /* 메뉴레벨 */ 
	private String tcls_prts_id;        /* 상위 산업분류ID */ 
	private String use_yn;         /* 산업분류사용여부 */
	public String getTcls_id() {
		return tcls_id;
	}
	public void setTcls_id(String tcls_id) {
		this.tcls_id = tcls_id;
	}
	public String getTcls_nm() {
		return tcls_nm;
	}
	public void setTcls_nm(String tcls_nm) {
		this.tcls_nm = tcls_nm;
	}
	public int getTcls_levl() {
		return tcls_levl;
	}
	public void setTcls_levl(int tcls_levl) {
		this.tcls_levl = tcls_levl;
	}
	public String getTcls_prts_id() {
		return tcls_prts_id;
	}
	public void setTcls_prts_id(String tcls_prts_id) {
		this.tcls_prts_id = tcls_prts_id;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	} 

	
	
}
