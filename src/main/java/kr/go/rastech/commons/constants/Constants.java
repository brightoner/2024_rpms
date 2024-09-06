/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.constants;

import egovframework.cmmn.service.EgovProperties;

/**
 * <pre>
 * FileName: Constants.java
 * Package : kr.go.platform.commons.constants
 *
 * 프로젝트 상수 객체.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class Constants {
	
	
	public static final String CONTEXTPATH= "${ctxt}/";
	
		
    public static final String ENCODING_TYPE = "UTF-8";
 
	
    
    /** 페이지 개수 */
    public static final int RECORDS = 10;
    
    
   /* public static final String PAYLETTER_URL = EgovProperties.getProperty("Globals.payletterUrl");  // 결제 요청 URL
    public static final String PAYCANCEL_URL = EgovProperties.getProperty("Globals.payCancelUrl");  // 결제 취소 URL
    public static final String PAYLETTER_CLIENT_ID = EgovProperties.getProperty("Globals.client_id"); // 가맹점 ID
    public static final String PAYLETTER_IP_ADDR = EgovProperties.getProperty("Globals.ip_addr"); // 결제 취소 URL
*/    
}
