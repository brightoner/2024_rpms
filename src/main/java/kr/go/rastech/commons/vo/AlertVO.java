package kr.go.rastech.commons.vo;

import java.io.Serializable;

import kr.go.rastech.base.vo.BaseVo;
import kr.go.rastech.commons.page.Navigator;
import kr.go.rastech.commons.page.PagingSupport;

/**
 *
 * <pre>
 * FileName: CommonsVo.java
 * Package : kr.go.ncmiklib.commons.vo
 * 
 * 검색용 Value Object.
 *
 * </pre>
 * 
 * @author : rastech
 * @date : 2023. 3. 9.
 */

public class AlertVO extends CommonsVo  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3750332146563338414L;
	
	String tas_id;
	String send_type;
	String auth_key;
	String user_name;
	String user_email;
	String sender;
	String map_content;
	String subject;
	String sender_name;
	String template_code;
	
	
		
	public String getTas_id() {
		return tas_id;
	}
	public void setTas_id(String tas_id) {
		this.tas_id = tas_id;
	}
	public String getSend_type() {
		return send_type;
	}
	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}
	public String getAuth_key() {
		return auth_key;
	}
	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}
	public String getTemplate_code() {
		return template_code;
	}
	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMap_content() {
		return map_content;
	}
	public void setMap_content(String map_content) {
		this.map_content = map_content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSender_name() {
		return sender_name;
	}
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}
	
	
}
