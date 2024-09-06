package kr.go.rastech.ptl.member.inform.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.Utils;
import kr.go.rastech.ptl.member.inform.dao.PtlInformDao;
import kr.go.rastech.ptl.member.inform.service.PtlInformService;
import kr.go.rastech.ptl.member.inform.vo.PtlBjApprlVo;
import kr.go.rastech.ptl.member.inform.vo.PtlInformVo;
import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;

@Controller
public class PtlInformController extends BaseController{

	@Resource
    private PtlInformService ptlInformService;
	
	@Resource
	private LoginService loginService;
	
	@Resource(name="ptlInformDao")
	private PtlInformDao ptlInformDao;
	

	  
	
	/**
     * <pre>
     *
     * 아이디찾기 화면이동
     * </pre>
     * @author : ljk
     * @date   : 2023. 5. 31. 
     * @param model
     * @param ptlUserRegVo
     * @param request
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/login/info/findId.do")
    public String findId(Model model, PtlUserRegVo ptlUserRegVo, HttpServletRequest request)  throws IOException, SQLException , NullPointerException { 
    	
    	return "member/regi/findId.subPlatForm";
    }
    
    
    /**
     * <pre>
     *
     * 비밀번호찾기 화면이동
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 3. 14. 
     * @param model
     * @param ptlUserRegVo
     * @param request
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/login/infoPw/findPw.do")
    public String findPw(Model model, PtlUserRegVo ptlUserRegVo, HttpServletRequest request)  throws IOException, SQLException , NullPointerException { 
    	
    	return "member/regi/findPw.subPlatForm";
    }
    

    
    
    /**
     * <pre>
     *
     *  핸드폰 중복 검사후 인증번호를 넘겨준다. 
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 3. 2. 
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/authenticationPhone.do")
    @ResponseBody
   public String authenticationPhone( PtlUserRegVo ptlUserRegVo, PtlInformVo ptlInformVo, UserVo userVo , HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
    
    	String message = null;
    	
    		 message = ptlInformService.authenticationPhone(ptlUserRegVo, ptlInformVo, userVo , request);
    		 
    		 if(message == null){
    			 message = "F";
    		 }
    	
    	return message;
   }
    
    /**
     * <pre>
     *
     * 인증번호 비교
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 3. 2. 
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/authNumCompare.do")
    @ResponseBody
   public String authNumCompare( HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
    
    	String message = null;
    	String authNumInput = request.getParameter("authNumInput");
    		
    	message = ptlInformService.authNumCompare(authNumInput,  request);
    		 
    		 if(message == null){
    			 message = "FAIL";
    		 }
    	
    	return message;
   }
    
    
    
    
    /**
     * <pre>
     *
     *  이메일 중복 검사후 인증번호를 넘겨준다. 
     * </pre>
     * @author : ljk
     * @date   : 2023. 6. 16. 
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/authenticationEmail.do")
    @ResponseBody
   public String authenticationEmail( PtlUserRegVo ptlUserRegVo, PtlInformVo ptlInformVo, UserVo userVo , HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
    
    	String message = null;
    	
    		 message = ptlInformService.authenticationEmail(ptlUserRegVo, ptlInformVo, userVo , request);
    		 
    		 if(message == null){
    			 message = "F";
    		 }
    	
    	return message;
   }
    
    /**
     * <pre>
     *
     * 인증번호 비교
     * </pre>
     * @author : ljk
     * @date   : 2023. 06. 16. 
     * @param ptlUserRegVo
     * @param ptlInformVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/authEmailCompare.do")
    @ResponseBody
   public String authEmailCompare( HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
    
    	String message = null;
    	String authEmailInput = request.getParameter("authEmailInput");
    		
    	message = ptlInformService.authEmailCompare(authEmailInput,  request);
    		 
    		 if(message == null){
    			 message = "FAIL";
    		 }
    	
    	return message;
   }
    
    
    
    /**
     * <pre>
     *
     * 회원 ID 찾기
     * </pre>
     * @author : 전병욱
     * @date   : 2021. 8. 9. 
     * 변경이력    : ljk 2023. 4. 26
     * @param 
     * @param 
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/regi/selectId.do")
    @ResponseBody
    public String selectId(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException{
    	
    	String mbtlnum = request.getParameter("mbtlnum");
    	String find_id = loginService.selectfindUserId(mbtlnum);
    	
    	return find_id;
    }
    
	
	/**
     * <pre>
     * 
     * 닉네임 변경 화면으로 이동한다.
     * </pre>
     * @author : ljk
     * @date   : 2022. 4. 24.
     * @param model
     * @param request
     * @param ptlInformVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping("/member/infoChN/memberNickNm.do")
    public String memberNickNm(Model model, HttpServletRequest request, PtlInformVo ptlInformVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
    	userVo = getUser();
    	if(userVo == null){
			return "redirect:/index/index.do";
		}
    	
		model.addAttribute("userVo",userVo);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		
		String returnUrl = "member/regi/memberNickNm.mngPlatForm";
		
    	return returnUrl;
    	
    }
    
    /**
     * 
      * <pre>
      * 
      * 닉네임을 수정한다.
      * </pre>
      * @author : ljk
      * @date   : 2023. 4. 24.
      * @param ptlInformVo
      * @param ptlUserRegVo
      * @param userVo
      * @return
      * @throws IOException, SQLException , NullPointerException
      */
    @RequestMapping("/member/regi/memberNickNmEdit.do")
    @ResponseBody
    public String memberNickNmEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
    	userVo = getUser();
    	String message = null;
    	try{
    		message = ptlInformService.memberNickNmEdit(ptlUserRegVo, userVo);
    		if(StringUtils.isBlank(message)){
    			message = "F";
    		}
    	} catch(SQLException e){
    	    message = "E";
    	}catch(NullPointerException e){
    	    message = "N";
    	}
    	
    	return message;
    }
	
    
    /**
     * <pre>
     * 
     * 비밀번호 변경화면으로 이동한다.
     * </pre>
     * @author : ljk
     * @date   : 2024. 4. 24.
     * @param request
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/infoChP/memberpw.do")
   public String memberpw(HttpServletRequest request)  throws IOException, SQLException , NullPointerException { 
   	
   	return "member/regi/memberpw.mngPlatForm";
   }
   
   
   /**
     * <pre>
     * 
     * 비밀번호 변경을 수행한다.
     * </pre>
     * @author : ljk
     * @date   : 2024. 4. 24.
     * @param ptlUserRegVo
     * @param userVo
     * @param rpassword
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/regi/pwModiPtlEmplyr.do")
   @ResponseBody
   public String pwModiPtlEmplyr(PtlUserRegVo ptlUserRegVo, UserVo userVo, @RequestParam("rpassword") String rpassword)  throws IOException, SQLException , NullPointerException { 
   	userVo = getUser();
   	String message = null;
   	try{
   		message = ptlInformService.updatePtlEmplyrPW(ptlUserRegVo, userVo, rpassword);
   		if(StringUtils.isBlank(message)){
   			message = "F";
   		}
   	} catch(SQLException e){
   	    message = "F";
   	}
   	
   	return message;
   }
   
   
   /**
    * <pre>
    * 
    * 개인정보 수정 화면으로 이동한다.(광고성정보 수신설정, 휴대전화 수정)
    * </pre>
    * @author : ljk
    * @date   : 2022. 4. 24.
    * @param model
    * @param request
    * @param ptlInformVo
    * @param userVo
    * @return
    * @throws IOException, SQLException , NullPointerException
    */
   @RequestMapping("/member/infoChI/infoMember.do")
   public String infoMember(Model model, HttpServletRequest request, PtlUserRegVo ptlUserRegVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
		   	
	   		userVo = getUser();
		   	
			
		   	if(userVo == null){
				return "redirect:/index/index.do";
			}
		   	
		   	
		 	String emplyrkey = userVo.getEmplyrkey();
		   	ptlUserRegVo.setEmplyrkey(emplyrkey);
		   	
	   		ptlUserRegVo = ptlInformService.selectInformDetail(ptlUserRegVo);
			
	   		model.addAttribute("userVo",userVo);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	   		model.addAttribute("ptlUserRegVo",ptlUserRegVo);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			
			String returnUrl = "member/regi/infoMember.mngPlatForm";
	
			return returnUrl;
   	
   }
   
   /**
    * 
     * <pre>
     * 
     * 개인정보 중 광고성정보 수신 여부를 수정한다.
     * </pre>
     * @author : ljk
     * @date   : 2023. 4. 24.
     * @param ptlUserRegVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/regi/infoMemberAgreeEdit.do")
   @ResponseBody
   public String infoMemberAgreeEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
   	userVo = getUser();
   	String message = null;
   	try{
   		message = ptlInformService.infoMemberAgreeEdit(ptlUserRegVo, userVo);
   		if(StringUtils.isBlank(message)){
   			message = "F";
   		}
   	} catch(SQLException e){
   	    message = "E";
   	}catch(NullPointerException e){
   	    message = "N";
   	}
   	
   	return message;
   }
   
   
   /**
    * 
     * <pre>
     * 
     * 개인정보 중 휴대폰번호를 수정한다.
     * </pre>
     * @author : ljk
     * @date   : 2023. 4. 24.
     * @param ptlUserRegVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/regi/infoMemberHpEdit.do")
   @ResponseBody
   public String infoMemberHpEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
   	userVo = getUser();
   	String message = null;
   	try{
   		message = ptlInformService.infoMemberHpEdit(ptlUserRegVo, userVo);
   		
   		if(StringUtils.isBlank(message)){
   			message = "F";
   		}
   	} catch(SQLException e){
   	    message = "E";
   	}catch(NullPointerException e){
   	    message = "N";
   	}
   	
   	return message;
   }
   
   /**
    * 
     * <pre>
     * 
     * 개인정보 중 이메일을 수정한다.
     * </pre>
     * @author : ljk
     * @date   : 2023. 6. 18.
     * @param ptlUserRegVo
     * @param userVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/regi/infoMemberEmailEdit.do")
   @ResponseBody
   public String infoMemberEmailEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
   	userVo = getUser();
   	String message = null;
   	try{
   		message = ptlInformService.infoMemberEmailEdit(ptlUserRegVo, userVo);
   		if(StringUtils.isBlank(message)){
   			message = "F";
   		}
   	} catch(SQLException e){
   	    message = "E";
   	}catch(NullPointerException e){
   	    message = "N";
   	}
   	
   	return message;
   }
   
  
   
   /**
     * <pre>
     * 
     * 회원탈퇴화면으로 이동한다.
     * </pre>
     * @author : wonki0138
     * @date   : 2018. 3. 23.
     * @param request
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/inform/memberOut.do")
   public String memberOut(HttpServletRequest request)  throws IOException, SQLException , NullPointerException { 
   	
   	return "member/regi/memberOut.mngPlatForm";
   }
   
   
   
   /**
    * <pre>
    * 
    * 회원탈퇴시 본인인증 절차 수행 > 회원탈퇴 수행
    * </pre>
    * @author : ljk
    * @date   : 2023. 10. 16.
    * @param request
    * @return
    * @throws IOException, SQLException , NullPointerException
    */
  @RequestMapping("/member/inform/passChkBeforeMemOut.do")
  public String passChkBeforeMemOut(Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException { 
  	
		  	HttpSession session = request.getSession();
		  	String sMessage= "";
		  	String rtnUrl= "";
		  	UserVo userVo = new UserVo();
		  	 userVo = getUser();
  	
         
        
        	  
        	  ptlInformService.deleteAccount(userVo);
        	  
        	  
        
          
          model.addAttribute("message", "Y");
          
    
 
          
     
  	return "jsonView";
  }
  
   
   
   
   
 
    
	
	/**
     * <pre>
     * 
     * BJ 승인신청 화면으로 이동한다.
     * </pre>
     * @author : ljk
     * @date   : 2023. 5. 03.
     * @param request
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
   @RequestMapping("/member/infoChB/memberBj.do")
   public String memberBj(Model model, UserVo userVo, PtlBjApprlVo ptlBjApprlVo)  throws IOException, SQLException , NullPointerException { 
   	
	   userVo = getUser();
	   if(userVo == null){
		   return "redirect:/index/index.do";
	   }
   	
	   String emplyrkey = userVo.getEmplyrkey();
	   ptlBjApprlVo.setEmplyrkey(emplyrkey);
	   
	   Map<String, Object> resultMap = new HashMap<String, Object>();
	   resultMap = ptlInformService.selectBjAuth(ptlBjApprlVo);
	   
	   // bj 승인 신청 이력이 없어서 return값이 없을때
	   if(resultMap == null) {
		   model.addAttribute("userVo",userVo);  
		   
		   return "member/inform/memberBj.mngPlatForm";
	   }
	   
	   model.addAttribute("resultMap", resultMap);
	   model.addAttribute("userVo",userVo);  
	   
   	return "member/inform/memberBj.mngPlatForm";
   }
   
   /**
    * <pre>
    * 
    * BJ 승인신청 > 신청등록 화면으로 이동한다.
    * </pre>
    * @author : ljk
    * @date   : 2023. 5. 03.
    * @param request
    * @return
    * @throws IOException, SQLException , NullPointerException
    */
  @RequestMapping("/member/inform/approvalBj.do")
  public String approvalBj(Model model,  PtlBjApprlVo ptlBjApprlVo, HttpServletRequest request )  throws IOException, SQLException , NullPointerException { 
  	
	  UserVo userVo = getUser();
	   if(userVo == null){
		 request.getSession().setAttribute("message", "접근 권한이 없습니다.<br> 로그인 후 이용해 주십시오");
		   return "redirect:/index/index.do";
	   }
	   
	   String mbtlnum_yn = userVo.getMbtlnum_yn();
	   
	   // pass인증(실명, 19세)이 되지 않은 경우
	   if(mbtlnum_yn.equals("N") || mbtlnum_yn == "N") {
		   
//		   model.addAttribute("userVo",userVo); 
		   return "cmm/passAuth/passAuthentication.subPlatForm";
	   }
	   
//	  model.addAttribute("userVo",userVo);  
  	return "member/inform/regBj.subPlatForm";
  }
  
  
  /**
   * <pre>
   * 
   * BJ 승인신청 > 신청등록 화면으로 이동한다.
   * </pre>
   * @author : ljk
   * @date   : 2023. 5. 03.
   * @param request
   * @return
   * @throws IOException, SQLException , NullPointerException
   */
 @RequestMapping("/member/inform/regBj.do")
 public String regBj(Model model, UserVo userVo, PtlBjApprlVo ptlBjApprlVo)  throws IOException, SQLException , NullPointerException { 
 	
	   userVo = getUser();
	   if(userVo == null){
		   return "redirect:/index/index.do";
	   }
	   
	   String mbtlnum_yn = userVo.getMbtlnum_yn();
	   
 	return "member/inform/regBj.mngPlatForm";
 }
 
 
 
 
 /**
  * <pre>
  * 
  * BJ 승인신청 등록
  * </pre>
  * @author : ljk
  * @date   : 2023. 5. 05.
  * @param request
  * @return
  * @throws IOException, SQLException , NullPointerException
  */
	 @RequestMapping("/member/inform/insertBjApprl.do")
	 @ResponseBody
	 public String insertBjApprl(PtlBjApprlVo ptlBjApprlVo, UserVo userVo)  throws IOException, SQLException , NullPointerException {
	 	userVo = getUser();
	 	String message = null;
	 	if(userVo != null) {
		 	String emplyrkey = userVo.getEmplyrkey();
		 	String user_id = userVo.getLoginid();
		 	String nicknm = userVo.getNicknm();
		 	
		 	
		 	ptlBjApprlVo.setEmplyrkey(emplyrkey);
		 	ptlBjApprlVo.setUser_id(user_id);
		 	ptlBjApprlVo.setNicknm(nicknm);
		 	
		 	try{
		 		
			 	message = ptlInformService.goInsertPtlBjApprl(ptlBjApprlVo);
		 		if(StringUtils.isBlank(message)){
		 			message = "F";
		 		}
			 } catch(SQLException e){
			    message = "E";
			 }catch(NullPointerException e){
			    message = "N";
			 }
	 	}else{
		    message = "NOTLOGIN";
	 	}
	 	
	 	return message;
	 }
	 
	 
	 /**
	     * <pre>
	     *
	     * PASS 찾기 -> 비밀번호 변경 처리
	     * </pre>
	     * @author : 전병욱
	     * @date   : 2021. 8. 9. 
	     * @param 
	     * @param 
	     * @param userVo
	     * @return
	     * @throws IOException, SQLException , NullPointerException
	     */
	    @RequestMapping("/member/regi/changePass.do")
	    public String changePass(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException{
	    	
	    	String emplyrkey = request.getParameter("emplyrkey");
	    	String password = request.getParameter("password");
	    	PtlUserRegVo ptlUserRegVo = new PtlUserRegVo();
	    	
	    	String pp = ""; 
	    	pp = Utils.passwordEncryption(password, emplyrkey, "Y");//패스워드 암호화
	    	
	    	
	    	ptlUserRegVo.setEmplyrkey(emplyrkey);
	    	ptlUserRegVo.setPassword(pp);
	    	
	    	ptlInformDao.updatePtlEmplyrPW(ptlUserRegVo);
	    	
	    	return "redirect:/login/user/login.do";
	    }
	    

	    /**
	     * <pre>
	     *
	     * pass 본인인증 처리 (ID 찾기 실명인증)
	     * </pre>
	     * @author : ljk
	     * @date   : 2023. 5. 31. 
	     * @param 
	     * @param 
	     * @return
	     * @throws IOException, SQLException , NullPointerException
	     */
	    @RequestMapping("/member/regi/passId.do")
	    public String passId(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException{
	    	
	    	HttpSession session = request.getSession();
	    	
	    	NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
	        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
//	        String sSiteCode = "BV085";				// NICE로부터 부여받은 사이트 코드
//	        String sSiteSeq = "HFJExTZtx7NL";			// NICE로부터 부여받은 사이트 패스워드
	        String sSiteCode = "CB653";				// NICE로부터 부여받은 사이트 코드
	    	String sSiteSeq = "MwNcgyVOPtnS";			// NICE로부터 부여받은 사이트 패스워드

	        String sCipherTime = "";			// 복호화한 시간
	        String sRequestNumber = "";			// 요청 번호
	        String sResponseNumber = "";		// 인증 고유번호
	        String sAuthType = "";				// 인증 수단
	        String sName = "";					// 성명
	        String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
	        String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
	        String sBirthDate = "";				// 생년월일(YYYYMMDD)
	        String sGender = "";				// 성별
	        String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
	    	String sMobileNo = "";				// 휴대폰번호
	    	String sMobileCo = "";				// 통신사
	        String sMessage = "";
	        String sPlainData = "";
	        
	        String rtnUrl = "";
	        int iReturn = niceCheck.fnDecode(sSiteCode, sSiteSeq, sEncodeData);
	        
	        if( iReturn == 0 )
	        {
	            sPlainData = niceCheck.getPlainData();
	            sCipherTime = niceCheck.getCipherDateTime();
	            
	            // 데이타를 추출합니다.
	            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	            
	            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	            sResponseNumber = (String)mapresult.get("RES_SEQ");
	            sAuthType		= (String)mapresult.get("AUTH_TYPE");
	            sName			= (String)mapresult.get("NAME");
	    		//sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
	            sBirthDate		= (String)mapresult.get("BIRTHDATE");
	            sGender			= (String)mapresult.get("GENDER");
	            sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
	            sDupInfo		= (String)mapresult.get("DI");
	            sConnInfo		= (String)mapresult.get("CI");
	            sMobileNo		= (String)mapresult.get("MOBILE_NO");
	            sMobileCo		= (String)mapresult.get("MOBILE_CO");
	            
	            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	            if(!sRequestNumber.equals(session_sRequestNumber))
	            {
	                sMessage = "세션값 불일치 오류입니다.";
	                sResponseNumber = "";
	                sAuthType = "";
	            }
	            
	            Map<String, Object> checkMap = new HashMap<String, Object>();
	            
	            String mbtlnum = loginService.selectfindUserChk(sMobileNo);
	            
//	            sMessage = "실명인증이 완료되었습니다.";
	            
	            String user_id = loginService.selectfindUserId(mbtlnum);
	            
	            
	            model.addAttribute("message", sMessage);
	            model.addAttribute("user_id", user_id);	
	        	
	        	rtnUrl = "member/regi/closeFindId.subPlatForm";
	            
	        }
	    	return rtnUrl;
	    }
	    
	  
	    
	    /**
	     * <pre>
	     *
	     * pass 인증 - BJ승인신정, 별풍선결제시 인증여부
	     * </pre>
	     * @author : ljk
	     * @date   : 2023. 6. 1. 
	     * @param 
	     * @param 
	     * @return
	     * @throws IOException, SQLException , NullPointerException
	     */
	    @RequestMapping("/member/regi/passAuthentication.do")
	    public String passAuthentication(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException{
	    	String niceYn = "";
    	    UserVo userVo = getUser();
	    	
    		
    		if(userVo == null){
    			request.getSession().setAttribute("message", "접근 권한이 없습니다.<br> 로그인 후 이용해 주십시오");
    			return "redirect:/index/index.do";
    		}		
    		
	    	HttpSession session = request.getSession();
	    	
	    	NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
	        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

	        String sSiteCode = "CB653";				// NICE로부터 부여받은 사이트 코드
	    	String sSiteSeq = "MwNcgyVOPtnS";			// NICE로부터 부여받은 사이트 패스워드

	        String sCipherTime = "";			// 복호화한 시간
	        String sRequestNumber = "";			// 요청 번호
	        String sResponseNumber = "";		// 인증 고유번호
	        String sAuthType = "";				// 인증 수단
	        String sName = "";					// 성명
	        String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
	        String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
	        String sBirthDate = "";				// 생년월일(YYYYMMDD)
	        String sGender = "";				// 성별
	        String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
	    	String sMobileNo = "";				// 휴대폰번호
	    	String sMobileCo = "";				// 통신사
	        String sMessage = "";
	        String sPlainData = "";
	        
	        String rtnUrl = "";
	        int iReturn = niceCheck.fnDecode(sSiteCode, sSiteSeq, sEncodeData);
	        
	        if( iReturn == 0 )
	        {
	            sPlainData = niceCheck.getPlainData();
	            sCipherTime = niceCheck.getCipherDateTime();
	            
	            // 데이타를 추출합니다.
	            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	            
	            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	            sResponseNumber = (String)mapresult.get("RES_SEQ");
	            sAuthType		= (String)mapresult.get("AUTH_TYPE");
	            sName			= (String)mapresult.get("NAME");
	    		//sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
	            sBirthDate		= (String)mapresult.get("BIRTHDATE");
	            sGender			= (String)mapresult.get("GENDER");
	            sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
	            sDupInfo		= (String)mapresult.get("DI");
	            sConnInfo		= (String)mapresult.get("CI");
	            sMobileNo		= (String)mapresult.get("MOBILE_NO");
	            sMobileCo		= (String)mapresult.get("MOBILE_CO");
	            
	            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	            if(!sRequestNumber.equals(session_sRequestNumber))
	            {
	                sMessage = "세션값 불일치 오류입니다.";
	                sResponseNumber = "";
	                sAuthType = "";
	                
	                niceYn="X";
	            }
	            
	            
	            Map<String, Object> checkMap = new HashMap<String, Object>();
	            
	            String mbtlnum = loginService.selectfindUserChk(sMobileNo);
	            
	            
	            // **** 만 19세 이상인지 체크 **** 
	            int birthYear = Integer.parseInt(sBirthDate.substring(0, 4));
	            int birthMonth = Integer.parseInt(sBirthDate.substring(4, 6));
	            int birthDay = Integer.parseInt(sBirthDate.substring(6, 8));
	            
	            LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
	            LocalDate now = LocalDate.now();
	            
	            Period age = Period.between(birthDate, now);	
	            int resultAge = age.getYears();		// 만 나이
	            
	          
	            
	            if(StringUtils.isBlank(mbtlnum)) {
	            	sMessage = "인증한 전화번호로 가입된 회원 정보가 존재하지 않습니다.";
	            	niceYn="NULL";
	            }else {
	            	
		            // 만 19세 이상일 경우만 MBTLNUM_YN(PASS실명인증여부)를 Y로 수정	            
		            if(resultAge >= 19) {
		            	if(StringUtils.isNotBlank(mbtlnum) && StringUtils.isNotBlank(sBirthDate) && StringUtils.isNotBlank(sName) ) {
			            	Map<String , Object> param = new HashMap<String,Object>();
			            	param.put("mbtlnum", mbtlnum);
			            	param.put("brthdy", sBirthDate);
			            	param.put("username", sName);
			            	ptlInformService.passAuthentication(param);	// 인증여부를 Y로 수정 , 생년월일 , 유저명
			            	
			            	userVo.setMbtlnum_yn("Y");					// session의 인증여부를 Y로 최신화
			            	userVo.setBrthdy(sBirthDate);
			            	userVo.setUsername(sName);
			            	request.getSession().setAttribute("userVo", userVo);
			            	
			            	sMessage = "실명인증이 완료되었습니다.";
			            	
			            	niceYn="Y";
		            	}else {
		            		sMessage = "다시 로그인 후 수행해 주십시오.(실명인증 필수값 누락)";
			            	
			            	niceYn="PARAM_NULL";
		            	}
		            }else {
		            	sMessage = "만 19세 미만 입니다.";
		            	
		            	niceYn="F";
		            }
	            }
	            model.addAttribute("message", sMessage);
	            model.addAttribute("niceYn", niceYn);
	        	rtnUrl = "member/regi/closePassAuth.popPlatForm";
	  
	        }
	    	return rtnUrl;
	    }
	    
	 
		 /**
	     * <pre>
	     *
	     * 인증 실패 공통
	     * </pre>
	     * @author : ljk
	     * @date   : 2023. 6. 1. 
	     * @param 
	     * @param 
	     * @return
	     * @throws IOException, SQLException , NullPointerException
	     */
	    @RequestMapping("/member/regi/checkplusFail.do")
	    public String checkplusFail(HttpServletRequest request, Model model) throws IOException, SQLException , NullPointerException{
	    	
	    	
	    	
	    	HttpSession session = request.getSession();
	    	
	    	NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
	        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

	        String sSiteCode = "CB653";				// NICE로부터 부여받은 사이트 코드
	    	String sSiteSeq = "MwNcgyVOPtnS";			// NICE로부터 부여받은 사이트 패스워드

	    	String sCipherTime = "";			// 복호화한 시간
    	    String sRequestNumber = "";			// 요청 번호
    	    String sErrorCode = "";				// 인증 결과코드
    	    String sAuthType = "";				// 인증 수단
    	    String sMessage = "";
    	    String sPlainData = "";
	        
	        String rtnUrl = "";
	        int iReturn = niceCheck.fnDecode(sSiteCode, sSiteSeq, sEncodeData);
	        
	        if( iReturn == 0 )
	        {
	            sPlainData = niceCheck.getPlainData();
	            sCipherTime = niceCheck.getCipherDateTime();
	            
	            // 데이타를 추출합니다.
	            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	            
	            sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
	            sErrorCode 		= (String)mapresult.get("ERR_CODE");
	            sAuthType 		= (String)mapresult.get("AUTH_TYPE");
	  
	        }else if(iReturn == -1) {
	        	 sMessage = "복호화 시스템 에러입니다.";
	        }else if(iReturn == -4) {
	        	 sMessage = "복호화 처리오류입니다.";
	        }else if(iReturn == -5) {
	        	 sMessage = "복호화 해쉬 오류입니다.";
	        }else if(iReturn == -6) {
	        	 sMessage = "복호화 데이터 오류입니다.";
	        }else if(iReturn == -9) {
	        	 sMessage = "입력 데이터 오류입니다.";
	        }else if(iReturn == -12) {
	        	 sMessage = "사이트 패스워드 오류입니다.";
	        }else {
	        	 sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	        }
	        
	        model.addAttribute("failMessage", sMessage);
	        model.addAttribute("sRequestNumber", sRequestNumber);
	        model.addAttribute("sErrorCode", sErrorCode);
	        model.addAttribute("sAuthType", sAuthType);

            
        	rtnUrl = "member/regi/closeFailPop.popPlatForm";
        	
	    	return rtnUrl;
	    }
	


		private String requestReplace(String paramValue, String gubun) {

		        String result = "";
		        
		        if (paramValue != null) {
		        	
		        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

		        	paramValue = paramValue.replaceAll("\\*", "");
		        	paramValue = paramValue.replaceAll("\\?", "");
		        	paramValue = paramValue.replaceAll("\\[", "");
		        	paramValue = paramValue.replaceAll("\\{", "");
		        	paramValue = paramValue.replaceAll("\\(", "");
		        	paramValue = paramValue.replaceAll("\\)", "");
		        	paramValue = paramValue.replaceAll("\\^", "");
		        	paramValue = paramValue.replaceAll("\\$", "");
		        	paramValue = paramValue.replaceAll("'", "");
		        	paramValue = paramValue.replaceAll("@", "");
		        	paramValue = paramValue.replaceAll("%", "");
		        	paramValue = paramValue.replaceAll(";", "");
		        	paramValue = paramValue.replaceAll(":", "");
		        	paramValue = paramValue.replaceAll("-", "");
		        	paramValue = paramValue.replaceAll("#", "");
		        	paramValue = paramValue.replaceAll("--", "");
		        	paramValue = paramValue.replaceAll("-", "");
		        	paramValue = paramValue.replaceAll(",", "");
		        	
		        	if(gubun != "encodeData"){
		        		paramValue = paramValue.replaceAll("\\+", "");
		        		paramValue = paramValue.replaceAll("/", "");
		            paramValue = paramValue.replaceAll("=", "");
		        	}
		        	
		        	result = paramValue;
		            
		        }
		        return result;
		  }
		
		
}
