package kr.go.rastech.ptl.mng.sch.vo;

import java.io.Serializable;

import kr.go.rastech.commons.vo.CommonsVo;


public class EtlMngVo extends CommonsVo implements Serializable
{
	String etlSn;
	String etlId;
	String etlNm;
	String etlType;
	String etlHh;
	String etlSs;
	String etlTime;
	String etlTb;
	String etlGbn;
	String useYn;
	String lastDt;
	String nextDt;
	
	public String getEtlSn()
    {
    	return etlSn;
    }
	public void setEtlSn(String etlSn)
    {
    	this.etlSn = etlSn;
    }
	public String getEtlId()
    {
    	return etlId;
    }
	public void setEtlId(String etlId)
    {
    	this.etlId = etlId;
    }
	public String getEtlNm()
    {
    	return etlNm;
    }
	public void setEtlNm(String etlNm)
    {
    	this.etlNm = etlNm;
    }
	public String getEtlType()
    {
    	return etlType;
    }
	public void setEtlType(String etlType)
    {
    	this.etlType = etlType;
    }
	public String getEtlHh()
    {
    	return etlHh;
    }
	public void setEtlHh(String etlHh)
    {
    	this.etlHh = etlHh;
    }
	public String getEtlSs()
    {
    	return etlSs;
    }
	public void setEtlSs(String etlSs)
    {
    	this.etlSs = etlSs;
    }
	public String getEtlTime()
    {
    	return etlTime;
    }
	public void setEtlTime(String etlTime)
    {
    	this.etlTime = etlTime;
    }
	public String getEtlTb()
    {
    	return etlTb;
    }
	public void setEtlTb(String etlTb)
    {
    	this.etlTb = etlTb;
    }
	public String getEtlGbn()
    {
    	return etlGbn;
    }
	public void setEtlGbn(String etlGbn)
    {
    	this.etlGbn = etlGbn;
    }
	public String getUseYn()
    {
    	return useYn;
    }
	public void setUseYn(String useYn)
    {
    	this.useYn = useYn;
    }
	public String getLastDt()
    {
    	return lastDt;
    }
	public void setLastDt(String lastDt)
    {
    	this.lastDt = lastDt;
    }
	public String getNextDt()
    {
    	return nextDt;
    }
	public void setNextDt(String nextDt)
    {
    	this.nextDt = nextDt;
    }
}
