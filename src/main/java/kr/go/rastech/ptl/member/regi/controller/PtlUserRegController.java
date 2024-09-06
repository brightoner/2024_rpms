package kr.go.rastech.ptl.member.regi.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.member.inform.service.PtlInformService;
import kr.go.rastech.ptl.member.regi.dao.PtlUserRegDao;
import kr.go.rastech.ptl.member.regi.service.PtlUserRegService;
import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;

  
/**
 * <pre>
 * PTL_UserReg Controller 구현
 * 
 * </pre>
 * @FileName : PtlUserRegController.java
 * @package  : kr.go.ncmik.ptl.member.regi.controller
 * @author   : user
 * @date     : 2018. 7. 4.
 * 
 */
@Controller
public class PtlUserRegController extends BaseController {

    @Resource
    private PtlUserRegService ptlUserRegService;
    
    @Resource(name="ptlUserRegDao")
	private PtlUserRegDao ptlUserRegDao;
    
    @Resource
    private PtlInformService ptlInformService;
    
    @Resource
	private LoginService loginService;
    
    /**
     * <pre>
     *
     * 회원가입 화면으로 이동한다.
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 2. 28. 
     * @param model
     * @param MemberRegiVo
     * @param request
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/login/regi/writeMember.do")
    public String writeMember(Model model, PtlUserRegVo memberRegiVo, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
 
    	String returnUrl = "member/regi/writeMember.popPlatForm";

    	return returnUrl;
    }


    /**
     * <pre>
     *  id 중복체크를 수행
     *
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 2. 28. 
     * @param loginVO
     * @param model
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/memChk.do")
    @ResponseBody
    public String duplPtlUserReg(UserVo loginVO, Model model )  throws IOException, SQLException , NullPointerException {
        
    	//로그인 키
    	String UserRegkey = null;
		UserVo userVo = null;
		String loginid = null;
		String memChk = "";
		
		try {
			
			loginid = loginVO.getLoginid();
			if(StringUtils.isNotBlank(loginid)){
				
				userVo = loginService.selectUser(loginid);

				if(userVo != null){
					memChk = "Y";				
				}else{
					memChk = "N";
				}				
			}else{
				
				memChk = "F";
			}
			
		} catch (SQLException e) {
			if (logger.isErrorEnabled()){ logger.debug("err SQLException duplPtlUserReg");}
			throw new BadCredentialsException("DB 접속에 오류가 있습니다.", e);
		}catch (BadCredentialsException e) {
			if (logger.isErrorEnabled()){ logger.debug("err  BadCredentialsException duplPtlUserReg");}
			throw new BadCredentialsException("DB 접속에 오류가 있습니다.", e);
		}
		
	
        return memChk;
    	
    }
    
    
    /**
     * <pre>
     *  닉네임 중복체크를 수행
     *
     * </pre>
     * @author : ljk
     * @date   : 2023. 04. 24. 
     * @param loginVO
     * @param model
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/nickChk.do")
    @ResponseBody
    	public String nickChk(PtlUserRegVo ptlUserRegVo, Model model )  throws IOException, SQLException , NullPointerException {
        
    	String nicknm = null;
    	String nickChk = null;
		try {
			
			nicknm = ptlUserRegVo.getNicknm();
			if(StringUtils.isNotBlank(nicknm)){
				
				int cnt = ptlUserRegService.selectNicknmCheck(nicknm);

				if(cnt != 0){
					nickChk = "Y";				
				}else{
					nickChk = "N";
				}				
			}else{
				
				nickChk = "F";
			}
			
		} catch (SQLException e) {
			if (logger.isErrorEnabled()){ logger.debug("err SQLException duplPtlUserReg");}
			throw new BadCredentialsException("DB 접속에 오류가 있습니다.", e);
		}catch (BadCredentialsException e) {
			if (logger.isErrorEnabled()){ logger.debug("err  BadCredentialsException duplPtlUserReg");}
			throw new BadCredentialsException("DB 접속에 오류가 있습니다.", e);
		}
		
        return nickChk;
    	
    }	
    
     /**
     * <pre>
     *
     *  회원등록을 수행한다.
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 3. 2. 
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/addPtlUserReg.do")
    @ResponseBody
   public String addPtlUserReg( PtlUserRegVo ptlUserRegVo, UserVo userVo )  throws IOException, SQLException , NullPointerException {
    
    	String message = null;
    	String classify = "00";		// 이 메소드는 사이트 가입자용.  회원가입구분(00.사이트가입자,01.소셜가입자) 
    	ptlUserRegVo.setClassify(classify);
    	    
		 message = ptlUserRegService.addPtlUserReg(ptlUserRegVo, userVo);
		 
		 if(message == "M") {
			 return "redirect:/index/index.do";
		 }
		 if(message == null){
			 message = "F";
		 }
		 
    	
    	return message;
   }
    
    
    /** 
     * 소셜 로그인시 회원가입이 되어있는지 체크
	 * @author : ljk
	 * @date : 2023.06.20
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
    @RequestMapping("/member/regi/idDuplChkBySocial.do")
    @ResponseBody
   public String idDuplChkBySocial(HttpServletRequest request, PtlUserRegVo ptlUserRegVo)  throws IOException, SQLException , NullPointerException {
    	
    	if(StringUtils.isBlank(request.getParameter("userId"))) {
    		return "redirect:/index/index.do";
    	}
    	
    	String message = null;
    	String user_id = request.getParameter("userId");
    	
    	int idCnt = ptlInformService.idDuplChk(user_id);
    	
    	if(idCnt == 0) {	// 신규 회원 이면	
    		message = "Y";
    	}else {				// 이미 가입되어있으면 
    		message = "N";
    	}
    	
    	return message;
    }
    
    
    /** 
     * 소셜 로그인시 이메일, 전화번호, 광고동의 수집 화면으로 이동
	 * @author : ljk
	 * @date : 2023.06.20
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
    @RequestMapping("/member/regi/writeMemberBySocial.do")
    public String writeMemberBySocial(Model model, PtlUserRegVo ptlUserRegVo, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {

    	if(ptlUserRegVo == null) {
    		return "redirect:/index/index.do";
    	}
    	
    	model.addAttribute("ptlUserRegVo", ptlUserRegVo);
    	
       	return "member/regi/writeMemberBySocial.subPlatForm";
    }
    
    
    
  
    
    
   /** 
    * 소셜로그인(네이버) callback URL
	 * @author : ljk
	 * @date : 2023.03.13
    * @throws IOException
    * @throws SQLException
    * @throws NullPointerException
    */
   @RequestMapping(value = "/member/api/callback.do")
   public String  naverLogin()  throws IOException, SQLException , NullPointerException {

		return "member/api/cmmPlatFormNaver.popPlatForm";	 
	}
	
    
}
