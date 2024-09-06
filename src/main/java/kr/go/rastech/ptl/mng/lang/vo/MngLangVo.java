package kr.go.rastech.ptl.mng.lang.vo;

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

@Alias("mngLangVo")
public class MngLangVo extends CommonsVo   implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4875903717631746679L;

	String seq;
	String page_id;
	String tag_gbn;
	String tag;
	String tag_val_ko;
	String tag_val_eng;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getPage_id() {
		return page_id;
	}
	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}
	public String getTag_gbn() {
		return tag_gbn;
	}
	public void setTag_gbn(String tag_gbn) {
		this.tag_gbn = tag_gbn;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag_val_ko() {
		return tag_val_ko;
	}
	public void setTag_val_ko(String tag_val_ko) {
		this.tag_val_ko = tag_val_ko;
	}
	public String getTag_val_eng() {
		return tag_val_eng;
	}
	public void setTag_val_eng(String tag_val_eng) {
		this.tag_val_eng = tag_val_eng;
	}
}
