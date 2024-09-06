package kr.go.rastech.ptl.mng.ip.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.go.rastech.commons.vo.CommonsVo;

@Alias("ipVo")
public class IpVo extends CommonsVo  implements Serializable  { 

	String ip_type;
	String ip;
	public String getIp_type() {
		return ip_type;
	}
	public void setIp_type(String ip_type) {
		this.ip_type = ip_type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}            