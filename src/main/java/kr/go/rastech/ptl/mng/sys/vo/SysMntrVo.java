package kr.go.rastech.ptl.mng.sys.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;

@Alias("sysMntrVo")
public class SysMntrVo extends CommonsVo  implements Serializable  { 
	
	/** serialVersionUID */
	private static final long serialVersionUID = 4485037675121623854L;
	private int mntr_seq;	
	private String mntr_dt;		
	private String mntr_gbn;
	private String mntr_gbn_nm;
	private String mntr_url;
    private int ptl_cnt;	
    private int eml_cnt;		    private int sms_cnt;		    private String err_yn;		    private String err_desc;	    private String err_prc_yn;
    private String err_prc_desc;
    
    private String sch_gb;		//스켸쥴 구분
    private String sch_gb_nm;	//스켸쥴 구분명
    private String sch_dt;		//스켸쥴 일자
    private String sch_time;	//스켸쥴 시간
    private String sch_h_time;	//스켸쥴 시
    private String sch_m_time;	//스켸쥴 분
    
   
    
    public SysMntrVo(){}

	public int getMntr_seq() {
		return mntr_seq;
	}

	public void setMntr_seq(int mntr_seq) {
		this.mntr_seq = mntr_seq;
	}

	public String getMntr_dt() {
		return mntr_dt;
	}

	public void setMntr_dt(String mntr_dt) {
		this.mntr_dt = mntr_dt;
	}

	public String getMntr_gbn() {
		return mntr_gbn;
	}

	public void setMntr_gbn(String mntr_gbn) {
		this.mntr_gbn = mntr_gbn;
	}

	public String getMntr_gbn_nm() {
		return mntr_gbn_nm;
	}

	public void setMntr_gbn_nm(String mntr_gbn_nm) {
		this.mntr_gbn_nm = mntr_gbn_nm;
	}

	public String getMntr_url() {
		return mntr_url;
	}

	public void setMntr_url(String mntr_url) {
		this.mntr_url = mntr_url;
	}
	
	public int getPtl_cnt() {
		return ptl_cnt;
	}

	public void setPtl_cnt(int ptl_cnt) {
		this.ptl_cnt = ptl_cnt;
	}
	public int getEml_cnt() {
		return eml_cnt;
	}

	public void setEml_cnt(int eml_cnt) {
		this.eml_cnt = eml_cnt;
	}

	public int getSms_cnt() {
		return sms_cnt;
	}

	public void setSms_cnt(int sms_cnt) {
		this.sms_cnt = sms_cnt;
	}

	public String getErr_yn() {
		return err_yn;
	}

	public void setErr_yn(String err_yn) {
		this.err_yn = err_yn;
	}

	public String getErr_desc() {
		return err_desc;
	}

	public void setErr_desc(String err_desc) {
		this.err_desc = err_desc;
	}

	public String getErr_prc_yn() {
		return err_prc_yn;
	}

	public void setErr_prc_yn(String err_prc_yn) {
		this.err_prc_yn = err_prc_yn;
	}


	public String getErr_prc_desc() {
		return err_prc_desc;
	}

	public void setErr_prc_desc(String err_prc_desc) {
		this.err_prc_desc = err_prc_desc;
	}

	public String getSch_gb() {
		return sch_gb;
	}

	public void setSch_gb(String sch_gb) {
		this.sch_gb = sch_gb;
	}

	public String getSch_gb_nm() {
		return sch_gb_nm;
	}

	public void setSch_gb_nm(String sch_gb_nm) {
		this.sch_gb_nm = sch_gb_nm;
	}

	public String getSch_dt() {
		return sch_dt;
	}

	public void setSch_dt(String sch_dt) {
		this.sch_dt = sch_dt;
	}

	public String getSch_time() {
		return sch_time;
	}

	public void setSch_time(String sch_time) {
		this.sch_time = sch_time;
	}

	public String getSch_h_time() {
		return sch_h_time;
	}

	public void setSch_h_time(String sch_h_time) {
		this.sch_h_time = sch_h_time;
	}

	public String getSch_m_time() {
		return sch_m_time;
	}

	public void setSch_m_time(String sch_m_time) {
		this.sch_m_time = sch_m_time;
	}

	
	
}            