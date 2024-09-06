/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.base.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.go.rastech.base.BaseObj;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.ptl.mng.code.service.MngCodeService;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;
import kr.go.rastech.ptl.mng.lang.service.MngLangService;
import kr.go.rastech.ptl.mng.lang.vo.MngLangVo;
 
/**
 * <pre>
 * FileName: BaseController.java
 * Package : kr.go.ncmiklib.base.controller
 *
 * 프로젝트 최상위 Controller.
 * 
 * </pre>   
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class BaseController extends BaseObj implements ServletContextAware{

    // 팝업용경로  첨부파일 경로 + 파일명 생성용 pattern
    private FastDateFormat saveFilePattern = FastDateFormat.getInstance("/yyyyMMddHHmmssSS");
    
    // 공통 Logger.
    protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
    
    // 공지사항, FAQ
    private FastDateFormat saveBoardFilePattern = FastDateFormat.getInstance("/yyyy/MM/dd/yyyyMMddHHmmssSS");
    // Spring Message Source
    @Resource
    protected MessageSource messageSource;

	protected  ServletContext servletContext;

	@Resource
	private MngCodeService mngCodeService;
	
	@Resource
	private MngLangService mngLangService;
	

	
    /**
     *
     * <pre>
     * 메시지 프로퍼티에서 값을 가져온다.
     *
     * </pre>
     *
     * @author : rastech
     * @date : 2023. 3. 9.
     * @param code
     * @return
     */
    protected String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     *
     * <pre>
     * 메시지 프로퍼티에서 값을 가져온다.
     * 아큐먼트에 값을 세팅한다.
     *
     * ex) {0}은 {1}입니다. => getMessage('code', {'라면', '삼양'}) => 라면은 삼양입니다.
     * </pre>
     *
     * @author : rastech
     * @date : 2023. 3. 9.
     * @param code
     * @param args
     * @return
     */
    protected String getMessage(String code, String[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

   /**
    *
    * <pre>
    * 사용자를 세션에 저장한다.
    *
    * </pre>
    * @author : rastech
    * @date   : 2023. 3. 9.
    * @param userVo
    * @return
    */
   protected void setUser(UserVo userVo) {
       ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = sra.getRequest();
       request.getSession().setAttribute("userVo", userVo);
   }

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
   protected UserVo getUser() {
       ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = sra.getRequest();
       return (UserVo) request.getSession().getAttribute("userVo");
   }



  
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
   
	
	public List<MngCodeVo> getCodeList(String code) throws IOException, SQLException , NullPointerException {
		return mngCodeService.getCodeList(code);
	}
	

	
	public void getCodeLists(Model model, String[] code) throws IOException, SQLException , NullPointerException {
		for (String str : code) {
			model.addAttribute(str, mngCodeService.getCodeList(str));
		}
	}
	
	public List<MngLangVo> getLangList(String page_id) throws IOException, SQLException , NullPointerException {
		MngLangVo mngLangVo = new MngLangVo();
		mngLangVo.setPage_id(page_id);
		return mngLangService.selectLang(mngLangVo);
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
   
   
   
   /**
	 * <pre>
	 *  숫자 확인
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 4. 
	 * @param str
	 * @return
	 */
	public boolean isNumber(String str){
	   
	   return str.matches("^[0-9]*$");
   }
	
	public String rtnUrl(String url){
	    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = sra.getRequest();
	    String if_yn  = StringUtil.nvl(request.getSession().getAttribute("if_yn"));
	    String rtn_url = "";
		if("Y".equals(if_yn)){
			rtn_url = url +".ptiles";
		}else{
			rtn_url = url + ".stiles";
		}
		return rtn_url;
	}
	

	

}


