/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.base.vo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.go.rastech.base.BaseObj;
import kr.go.rastech.commons.login.vo.UserVo;

/**
 *
 * <pre>
 * FileName: BaseVo.java
 * Package : kr.go.ncmiklib.base.vo
 *
 * 프로젝트 최상위 Value Object.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class BaseVo extends BaseObj {
	   /**
    *
    * <pre>
    * 로그인한 사용자를 가져온다.
    *
    * </pre>
    * @author : rastech
    * @date   : 2023. 3. 9.
    * @return
    */
   protected String getUserId() {
       ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = sra.getRequest();
       UserVo user = (UserVo) request.getSession().getAttribute("userVo");
       String loginid = "";
       if(user != null){
    	   loginid = user.getLoginid();
       }else{
    	   loginid = "system";
       }
       return loginid;
   }
}
