package kr.go.rastech.ptl.opsmng.qna.vo;



import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;

/**
 * <pre>
 *
 * </pre>
 * @author : Administrator
 * @date   : 2023. 3. 28.
 */
@Alias("qnaMngVo")
public class QnaMngVo extends CommonsVo implements Serializable {

	
	/** serialVersionUID */
	private static final long serialVersionUID = -736301903272282856L;
	
	private String alit_id;   // 공지사항 id 
	private String alit_sj;  // 공지사항 제목
	private String alit_cn;  // 공지사항 내용
	private String rdcnt;  // 조회수
	private String top_status;  // 탑 공지
	private String atch_link_id;  //첨부파일 id
	
	public String getAlit_id() {
		return alit_id;
	}
	public void setAlit_id(String alit_id) {
		this.alit_id = alit_id;
	}
	public String getAlit_sj() {
		return alit_sj;
	}
	public void setAlit_sj(String alit_sj) {
		this.alit_sj = alit_sj;
	}
	public String getAlit_cn() {
		return alit_cn;
	}
	public void setAlit_cn(String alit_cn) {
		this.alit_cn = alit_cn;
	}
	public String getRdcnt() {
		return rdcnt;
	}
	public void setRdcnt(String rdcnt) {
		this.rdcnt = rdcnt;
	}
	public String getTop_status() {
		return top_status;
	}
	public void setTop_status(String top_status) {
		this.top_status = top_status;
	}
	public String getAtch_link_id() {
		return atch_link_id;
	}
	public void setAtch_link_id(String atch_link_id) {
		this.atch_link_id = atch_link_id;
	}

	
	
	
}
