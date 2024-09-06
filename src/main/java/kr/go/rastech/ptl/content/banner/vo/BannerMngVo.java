package kr.go.rastech.ptl.content.banner.vo;



import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;

/**
 * <pre>
 * FileName: PopVo.java
 * Package : kr.co.rastech.nst.recruit.mng.pop.vo
 * 
 * 팝업관리
 *
 * </pre>
 * @author : Administrator
 * @date   : 2023. 3. 28.
 */
@Alias("bannerMngVo")
public class BannerMngVo extends CommonsVo implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -736301903272282856L;
	
	private int pop_sn;   //순번
	private String pop_titl;  // 제목
	private String pop_ctts;  // 내용
	private String pop_link;  // 링크
	private String pop_str_dt;  // 팝업_시작_일자
	private String pop_end_dt;  // 팝업_종료_일자
	private String pop_left;
	private String pop_top;
	private String pop_width;
	private String pop_height;
	private String pop_page;
	private String pop_pos;
	private String post_yn;    // 전송여부
	private String del_yn;  // 삭제 여부
	private int total_count;
	private String write_id;
	
	//검색을 위한..
	private String param_search;
	private String param_combo;
	
	//파일..
	private int pop_file_sn;
	private String pop_file_conv_nm;
	private String pop_file_path;
	private String pop_file_nm;
	private long pop_file_size;
	private String file_del;
	

	public String getFile_del() {
		return file_del;
	}
	public void setFile_del(String file_del) {
		this.file_del = file_del;
	}
	public int getPop_file_sn() {
		return pop_file_sn;
	}
	public void setPop_file_sn(int popFileSn) {
		this.pop_file_sn = popFileSn;
	}
	public String getPop_file_conv_nm() {
		return pop_file_conv_nm;
	}
	public void setPop_file_conv_nm(String pop_file_conv_nm) {
		this.pop_file_conv_nm = pop_file_conv_nm;
	}
	public String getPop_file_path() {
		return pop_file_path;
	}
	public void setPop_file_path(String pop_file_path) {
		this.pop_file_path = pop_file_path;
	}
	public String getPop_file_nm() {
		return pop_file_nm;
	}
	public void setPop_file_nm(String pop_file_nm) {
		this.pop_file_nm = pop_file_nm;
	}
	public long getPop_file_size() {
		return pop_file_size;
	}
	public void setPop_file_size(long pop_file_size) {
		this.pop_file_size = pop_file_size;
	}
	public String getParam_combo() {
		return param_combo;
	}
	public void setParam_combo(String param_combo) {
		this.param_combo = param_combo;
	}
	public String getParam_search() {
		return param_search;
	}
	public void setParam_search(String param_search) {
		this.param_search = param_search;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getPop_sn() {
		return pop_sn;
	}
	public void setPop_sn(int pop_sn) {
		this.pop_sn = pop_sn;
	}
	public String getPop_titl() {
		return pop_titl;
	}
	public void setPop_titl(String pop_titl) {
		this.pop_titl = pop_titl;
	}
	public String getPop_ctts() {
		return  this.pop_ctts; 
	}
	public void setPop_ctts(String pop_ctts) {
		this.pop_ctts = pop_ctts;
	}
	public String getPop_link() {
		return pop_link;
	}
	public void setPop_link(String pop_link) {
		this.pop_link = pop_link;
	}
	public String getPop_str_dt() {
		return pop_str_dt;
	}
	public void setPop_str_dt(String pop_str_dt) {
		this.pop_str_dt = pop_str_dt;
	}
	public String getPop_end_dt() {
		return pop_end_dt;
	}
	public void setPop_end_dt(String pop_end_dt) {
		this.pop_end_dt = pop_end_dt;
	}		
	public String getPop_left() {
		return pop_left;
	}
	public void setPop_left(String pop_left) {
		this.pop_left = pop_left;
	}
	public String getPop_top() {
		return pop_top;
	}
	public void setPop_top(String pop_top) {
		this.pop_top = pop_top;
	}
	public String getPop_width() {
		return pop_width;
	}
	public void setPop_width(String pop_width) {
		this.pop_width = pop_width;
	}
	public String getPop_height() {
		return pop_height;
	}
	public void setPop_height(String pop_height) {
		this.pop_height = pop_height;
	}
	public String getPop_page() {
		return pop_page;
	}
	public void setPop_page(String pop_page) {
		this.pop_page = pop_page;
	}
	public String getPop_pos() {
		return pop_pos;
	}
	public void setPop_pos(String pop_pos) {
		this.pop_pos = pop_pos;
	}
	public String getPost_yn() {
		return post_yn;
	}
	public void setPost_yn(String post_yn) {
		this.post_yn = post_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getWrite_id() {
		return write_id;
	}
	public void setWrite_id(String write_id) {
		this.write_id = write_id;
	}
	
}
