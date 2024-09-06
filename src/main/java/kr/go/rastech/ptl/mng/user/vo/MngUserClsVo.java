package kr.go.rastech.ptl.mng.user.vo;

import java.io.Serializable;
import java.security.PrivateKey;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.utils.CryptoUtil;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;


/**
 * <pre>
 * FileName: MngUserClsVo.java
 * Package : kr.go.ncmiklib.ptl.mng.user.vo
 * 사용자관리 - 사용자관심분류
 * 
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 24.
 */

@Alias("mngUserClsVo")
public class MngUserClsVo extends MngCodeVo  implements Serializable  {

	
	/** serialVersionUID */
	private static final long serialVersionUID = -7411836355236598335L;
	
	private String loginid;	        /* 사용자ID*/		
	private String cls_gbn;	    	/* 분류구분*/	
	private String cls_val;	        /* 분류코드*/
	public String getloginid() {
		return loginid;
	}
	public void setloginid(String loginid) {
		this.loginid = loginid;
	}
	public String getCls_gbn() {
		return cls_gbn;
	}
	public void setCls_gbn(String cls_gbn) {
		this.cls_gbn = cls_gbn;
	}
	public String getCls_val() {
		return cls_val;
	}
	public void setCls_val(String cls_val) {
		this.cls_val = cls_val;
	}
			
}
