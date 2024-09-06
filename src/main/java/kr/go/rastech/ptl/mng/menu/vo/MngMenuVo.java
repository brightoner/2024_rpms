package kr.go.rastech.ptl.mng.menu.vo;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;


/**
 * <pre>
 * FileName: MngMenuVo.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.vo
 * mng Menu 관리 Vo.
 * 
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 16.
 */

@Alias("mngMenuVo")
public class MngMenuVo extends CommonsVo implements Serializable   {

	/** serialVersionUID */
	private static final long serialVersionUID = 519380524109527938L;

	private String menu_id;             /* 메뉴 고유ID */ 
	private String menu_nm;             /* 메뉴 명 */ 
	private String menu_eng_nm;			/* 메뉴 영문명*/
	private int menu_levl;              /* 메뉴레벨 */ 
	private String menu_prts_id;        /* 상위 메뉴 ID */ 
	private String menu_use_yn;         /* 메뉴 사용여부 */ 
	private int menu_ord;               /* 메뉴순서 */ 
	private String top_menu_yn;         /* 상단 메뉴 여부 */ 
	private String left_menu_yn;        /* 왼쪽 메뉴 여부 */ 
	private String pop_menu_yn;        /* 팝업 메뉴 여부 */ 
	private String ip_use_yn;        /* 왼쪽 메뉴 여부 */ 
	private String eng_yn;             /* 영어 메뉴 여부 */ 
	private String del_yn;              /* 하위메뉴삭제여부 */ 
	private ArrayList<String> menu_rol_list; /* 메뉴권한 */ 
	private String rol_save_yn; 		/* 권한수정여부 */ 
	
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getMenu_eng_nm() {
		return menu_eng_nm;
	}
	public void setMenu_eng_nm(String menu_eng_nm) {
		this.menu_eng_nm = menu_eng_nm;
	}
	public int getMenu_levl() {
		return menu_levl;
	}
	public void setMenu_levl(int menu_levl) {
		this.menu_levl = menu_levl;
	}
	public String getMenu_prts_id() {
		return menu_prts_id;
	}
	public void setMenu_prts_id(String menu_prts_id) {
		this.menu_prts_id = menu_prts_id;
	}
	public String getMenu_use_yn() {
		return menu_use_yn;
	}
	public void setMenu_use_yn(String menu_use_yn) {
		this.menu_use_yn = menu_use_yn;
	}
	public int getMenu_ord() {
		return menu_ord;
	}
	public void setMenu_ord(int menu_ord) {
		this.menu_ord = menu_ord;
	}
	public String getTop_menu_yn() {
		return top_menu_yn;
	}
	public void setTop_menu_yn(String top_menu_yn) {
		this.top_menu_yn = top_menu_yn;
	}
	public String getLeft_menu_yn() {
		return left_menu_yn;
	}
	public void setLeft_menu_yn(String left_menu_yn) {
		this.left_menu_yn = left_menu_yn;
	}
	
	
	public String getPop_menu_yn() {
		return pop_menu_yn;
	}
	public void setPop_menu_yn(String pop_menu_yn) {
		this.pop_menu_yn = pop_menu_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public ArrayList<String> getMenu_rol_list() {
		ArrayList<String> newMenu_rol_list = null; 
		if(this.menu_rol_list !=null) {
			newMenu_rol_list = 	new ArrayList<String>();
			newMenu_rol_list = this.menu_rol_list;
		}					
	    return newMenu_rol_list;

	}
	
	public void setMenu_rol_list(ArrayList<String> menu_rol_list) {
		if(menu_rol_list != null){
			this.menu_rol_list = new ArrayList<String>(menu_rol_list.size());
			for (int i = 0; i < menu_rol_list.size(); ++i){
				this.menu_rol_list.add(i, menu_rol_list.get(i));
			}
		}	

	}
	public String getRol_save_yn() {
		return rol_save_yn;
	}
	public void setRol_save_yn(String rol_save_yn) {
		this.rol_save_yn = rol_save_yn;
	}
	public String getIp_use_yn() {
		return ip_use_yn;
	}
	public void setIp_use_yn(String ip_use_yn) {
		this.ip_use_yn = ip_use_yn;
	}
	public String getEng_yn() {
		return eng_yn;
	}
	public void setEng_yn(String eng_yn) {
		this.eng_yn = eng_yn;
	} 
}
