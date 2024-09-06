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

@Alias("mngMenuRolVo")
public class MngMenuRolVo extends CommonsVo implements Serializable   {

	/** serialVersionUID */
	private static final long serialVersionUID = -8041545996274737112L;
	
	private String menu_id;	/*메뉴ID*/			
	private String auth_gbn;	/*권한구분*/		
	private String rol_id;	/*권한ID*/					
	
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getAuth_gbn() {
		return auth_gbn;
	}
	public void setAuth_gbn(String auth_gbn) {
		this.auth_gbn = auth_gbn;
	}
	public String getRol_id() {
		return rol_id;
	}
	public void setRol_id(String rol_id) {
		this.rol_id = rol_id;
	}
}
