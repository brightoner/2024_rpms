package kr.go.rastech.ptl.member.regi.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.Utils;
import kr.go.rastech.ptl.member.inform.service.PtlInformService;
import kr.go.rastech.ptl.member.regi.dao.PtlUserRegDao;
import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;

/**
 * <pre>
 * PTL_UserReg ServiceImpl 로직 구현
 * 
 * </pre>
 * @FileName : PtlUserRegServiceImpl.java
 * @package  : kr.go.ncmik.ptl.member.regi.service
 * @author   : user
 * @date     : 2018. 7. 4.
 * 
 */
@Service
public class PtlUserRegServiceImpl extends BaseServiceImpl implements PtlUserRegService {

	@Resource(name="ptlUserRegDao")
	private PtlUserRegDao ptlUserRegDao;
    
    @Resource
    private PtlInformService ptlInformService;
    
    
    
    @Resource
	private LoginService loginService;
 
    
    
    /**
    *
    * <pre>
    * 닉네임 중복 체크
    *
    * </pre>
    * @author : ljk
    * @date   : 2023. 4. 24.
    * @param nicknm
    * @return
    */
    public int selectNicknmCheck(String nicknm) throws IOException, SQLException , NullPointerException{
		return ptlUserRegDao.selectNicknmCheck(nicknm);
	}
    
    
    
	/**
	 * <pre>
	 *
	 * PTL_UserReg을 등록한다.
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 3. 2. 
	 * @param PtlUserRegVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	@Override
	public void insertPtlUserReg(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException {
		ptlUserRegDao.insertPtlUserReg(ptlUserRegVo);
	}
	
	
	


	/**
	 *  회원가입 수행한다.
	 * @author : ljk
	 * @date   : 2023. 4. 24.
	 * @param ptlUserRegVo
	 * @param ptlInformVo
	 * @param userVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@Override
	public String addPtlUserReg(PtlUserRegVo ptlUserRegVo, UserVo userVo) throws IOException, SQLException , NullPointerException {
	   	
		//로그인 키
	   	String emplyrkey = null;
	   	String message="";
	   	String loginid = null;
	   	
	   	String email = null;

		
	   	UserVo chkUser = null;
	   	
	    loginid = ptlUserRegVo.getUser_id();
	   	
	    //01. 사용자DB에 있는지 확인
	    chkUser = new UserVo();
	    chkUser = loginService.selectUser(loginid);
		

		
	   	if (chkUser == null) {  //  회원가입가능
			
	   		//UserRegkey생성
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
			String strDT = dayTime.format(new Date(time)); 
			emplyrkey = strDT;
			
			ptlUserRegVo.setEmplyrkey(emplyrkey);
	
			//패스워드암호화
			String sha2 = "Y";
			String password=ptlUserRegVo.getPassword();
			password = Utils.passwordEncryption(password, emplyrkey, sha2);//패스워드 암호화
			
			ptlUserRegVo.setPassword(password);

			//실명인증여부
			ptlUserRegVo.setMbtlnum_yn("N");  //실명인증모듈탑재시 Y
//			ptlUserRegVo.setMbtlnum_yn("Y");  //실명인증모듈탑재시 Y
			//암호화방식
			ptlUserRegVo.setSha2("Y");
			
			// 이메일 양식 변경 (형식 : email1 + @ + email2)
			email =  ptlUserRegVo.getEmail1() + "@" + ptlUserRegVo.getEmail2();
			ptlUserRegVo.setEmail(email);
			
			
			//02. 사용자 DB 등록
			this.insertPtlUserReg(ptlUserRegVo);
			
		
			message="Y";
			
	   	}else { //중복가입회원
	   		
   			message="ID";
	   			
	   	}
	   	return message;    
	}



	

	
	
}
