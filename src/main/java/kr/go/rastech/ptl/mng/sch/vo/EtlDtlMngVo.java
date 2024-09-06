package kr.go.rastech.ptl.mng.sch.vo;


public class EtlDtlMngVo
{
	String etl_sn;         //etl순번
	String etl_dtl_sn;     //etl상세순번
	String etl_prc_dt;     //etl실행일시
	String etl_cnt;        //etl_건수
	String etl_err_yn;     //etl에러여부
	
	public String getEtl_sn()
    {
    	return etl_sn;
    }
	public void setEtl_sn(String etl_sn)
    {
    	this.etl_sn = etl_sn;
    }
	public String getEtl_dtl_sn()
    {
    	return etl_dtl_sn;
    }
	public void setEtl_dtl_sn(String etl_dtl_sn)
    {
    	this.etl_dtl_sn = etl_dtl_sn;
    }
	public String getEtl_prc_dt()
    {
    	return etl_prc_dt;
    }
	public void setEtl_prc_dt(String etl_prc_dt)
    {
    	this.etl_prc_dt = etl_prc_dt;
    }
	public String getEtl_cnt()
    {
    	return etl_cnt;
    }
	public void setEtl_cnt(String etl_cnt)
    {
    	this.etl_cnt = etl_cnt;
    }
	public String getEtl_err_yn()
    {
    	return etl_err_yn;
    }
	public void setEtl_err_yn(String etl_err_yn)
    {
    	this.etl_err_yn = etl_err_yn;
    }

}
