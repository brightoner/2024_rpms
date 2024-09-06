package kr.go.rastech.ptl.mng.auth.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.vo.CommonsVo;

/**
 *
 * <pre>
 * FileName: MngUrlVo.java
 * Package : kr.go.ncmiklib.ptl.mng.url.vo
 *
 * mng Url 관리 Vo.
 *
 * </pre>
 * @author : sbkang
 * @date   : 2023. 9. 30.
 */

@Alias("mngAuthVo")
public class MngAuthVo extends CommonsVo   implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7696455000740226412L;

	private int url_seq;				/* URL순번*/
	private String menu_id;				/* 메뉴id*/
	private String menu_nm;				/* 메뉴명*/
	private String auth_gbn;			/* 권한구분*/
	private String url;					/* url*/
	private String url_desc;			/* url설명*/
	private String rol_id;				/* 권한아이디*/

	public int getUrl_seq() {
		return url_seq;
	}
	public void setUrl_seq(int url_seq) {
		this.url_seq = url_seq;
	}
	public String getMenu_id() {
		return StringUtil.filterXss(menu_id);
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = StringUtil.filterXss(menu_id);
	}
	public String getAuth_gbn() {
		return StringUtil.filterXss(auth_gbn);
	}
	public void setAuth_gbn(String auth_gbn) {
		this.auth_gbn = StringUtil.filterXss(auth_gbn);
	}
	public String getUrl() {
		return StringUtil.filterXss(url);
	}
	public void setUrl(String url) {
		this.url = StringUtil.filterXss(url);
	}
	public String getUrl_desc() {
		return StringUtil.filterXss(url_desc);
	}
	public String getMenu_nm() {
		return StringUtil.filterXss(menu_nm);
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = StringUtil.filterXss(menu_nm);
	}
	public String getRol_id() {
		return StringUtil.filterXss(rol_id);
	}
	public void setRol_id(String rol_id) {
		this.rol_id = StringUtil.filterXss(rol_id);
	}
	public void setUrl_desc(String url_desc) {
		this.url_desc = StringUtil.filterXss(url_desc);
	}

}
