/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.base.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;

/**
 *
 * <pre>
 * FileName: BaseServiceImpl.java
 * Package : kr.go.ncmiklib.base.service
 *
 * 프로젝트 최상위 Service
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class BaseServiceImpl extends EgovAbstractServiceImpl {
	
	
    // 공통 Logger.
    protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
    protected String getUserId() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        UserVo user = (UserVo) request.getSession().getAttribute("userVo");
        String loginid = "";
        if(user != null){
     	   loginid = user.getLoginid();
        }else{
     	   loginid = null;
        }
        return loginid;
    }
    
    protected UserVo getUser() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        return (UserVo) request.getSession().getAttribute("userVo");
    }
    protected String getEmplyrkey() {
 	   ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
 	   HttpServletRequest request = sra.getRequest();
 	   UserVo user = (UserVo) request.getSession().getAttribute("userVo");
 	   String emplyrkey = "";
 	   if(user != null){
 		   emplyrkey = user.getEmplyrkey();
 	   }else{
 		   emplyrkey = null;
 	   }
 	   return emplyrkey;
    }
    
  

}
