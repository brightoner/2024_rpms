/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.code.vo;

import java.io.Serializable;

import kr.go.rastech.base.vo.BaseVo;

/**
 *
 * <pre>
 * FileName: CodeVo.java
 * Package : kr.go.ncmiklib.commons.code.vo
 *
 * 코드정보 VO.
 *
 * </pre>
 * @author :
 * @date   : 2023. . .
 */

public class CodeVo extends BaseVo implements Serializable {
    

    /**
	 * 
	 */
	private static final long serialVersionUID = 8129671575157364926L;
	private String cd;
    private String cdNm;

	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
}
