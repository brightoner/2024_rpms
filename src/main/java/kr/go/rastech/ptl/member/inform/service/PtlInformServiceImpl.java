package kr.go.rastech.ptl.member.inform.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.utils.Utils;
import kr.go.rastech.ptl.member.inform.dao.PtlInformDao;
import kr.go.rastech.ptl.member.inform.vo.PtlBjApprlVo;
import kr.go.rastech.ptl.member.inform.vo.PtlInformVo;
import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;


/**
 * <pre>
 * 회원정보 Impl
 * 
 * </pre>
 * @FileName : PtlInformServiceImpl.java
 * @package  : kr.go.ncmik.ptl.member.inform.service
 * @author   : wonki0138
 * @date     : 2018. 3. 2.
 * 
 */
@Service
public class PtlInformServiceImpl extends BaseServiceImpl implements PtlInformService {

	@Resource(name="ptlInformDao")
	private PtlInformDao ptlInformDao;
	
	@Resource
	private LoginService loginService;
	
	
	/**
	  * <pre>
	  * 
	  * 회원가입여부 확인
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 6. 20.
	  * @param ptlInformVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public int idDuplChk(String user_id) throws IOException, SQLException, NullPointerException {
		return ptlInformDao.idDuplChk(user_id);
	}
	
	
	/**
	  * <pre>
	  * 
	  * 회원정보Cnt를 조회한다.
	  * </pre>
	  * @author : wonki0138
	  * @date   : 2018. 3. 21.
	  * @param ptlInformVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public int selectInformDetailCnt(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException{
		return ptlInformDao.selectInformDetailCnt(ptlUserRegVo);
	}
	
	/**
	  * <pre>
	  * 
	  * 회원정보를 조회한다.
	  * </pre>
	  * @author : wonki0138
	  * @date   : 2018. 3. 22.
	  * @param ptlInformVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public PtlUserRegVo selectInformDetail(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException{
		return ptlInformDao.selectInformDetail(ptlUserRegVo);
	}

	
	
	
	
	
	
	
	
	/**
	  * 닉네임을 수정한다.
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlInformVo
	  * @param ptlUserRegVo
	  * @param userVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public String memberNickNmEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo) throws IOException, SQLException , NullPointerException{
		
		String message="";
		String emplyrkey = userVo.getEmplyrkey();
		
		ptlUserRegVo.setEmplyrkey(emplyrkey);
		
		
		// 사용자 DB 등록
		this.updateNicknm(ptlUserRegVo);
		
		userVo.setNicknm(ptlUserRegVo.getNicknm());
		
		message="Y";
				
		return message;
	}
	
	
	/**
	  * 닉네임을 수정한다.
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void updateNicknm(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException, NullPointerException {
		ptlInformDao.updateNicknm(ptlUserRegVo);
	}

	
	/**
	  * 비밀번호 변경을 수행한다.
	  * @author : wonki0138
	  * @date   : 2018. 3. 23.
	  * @param ptlUserRegVo
	  * @param userVo
	  * @param rpassword
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public String updatePtlEmplyrPW(PtlUserRegVo ptlUserRegVo, UserVo userVo, String rpassword) throws IOException, SQLException , NullPointerException{
		String message="";
		String emplyrkey = userVo.getEmplyrkey();
		ptlUserRegVo.setEmplyrkey(emplyrkey);
		
		// 패스워드 확인
		String password = ptlUserRegVo.getPassword();
		
		String password2 = userVo.getPassword();	
		//비밀번호 검증
		String sha2 = userVo.getSha2();
		rpassword = Utils.passwordEncryption(rpassword, emplyrkey, sha2);//패스워드 암호화
		
		if (rpassword.equals(password2)) {
			// 회원 패스워드 암호화
			password = Utils.passwordEncryption(password, emplyrkey, sha2);
			// 암호화된 패스워드 입력
			ptlUserRegVo.setEmplyrkey(emplyrkey);
			ptlUserRegVo.setPassword(password);
			
			ptlInformDao.updatePtlEmplyrPW(ptlUserRegVo);
			
			message="Y";
		 } else {
			
	        message="N";
		 }
			return message;
	}
	
	
	/**
	 * <pre>
	 * PTL_EMPLYR PW를 수정한다.
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 3. 12. 
	 * @param ptlUserRegVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	@Override
	public void updatePtlEmplyrPW(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException {
		ptlInformDao.updatePtlEmplyrPW(ptlUserRegVo);
	}
	
	

	/**
	  * <pre>
	  * 
	  * bj 승인신청 화면 > bj권한 여부 확인, 반려사유 조회
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 5. 5.
	  * @param ptlBjApprlVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public Map<String, Object> selectBjAuth(PtlBjApprlVo ptlBjApprlVo) throws IOException, SQLException, NullPointerException {
		return ptlInformDao.selectBjAuth(ptlBjApprlVo);
	}


	/**
	  * <pre>
	  * 
	  * BJ 승인신청 insert : ptl_bj_apprl
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 5. 5.
	  * @param ptlBjApprlVo
	  * @param ptlBjApprlVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public String goInsertPtlBjApprl(PtlBjApprlVo ptlBjApprlVo) throws IOException, SQLException, NullPointerException {
		
		String message= null;
		
		if(ptlBjApprlVo != null) {
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			   resultMap = ptlInformDao.selectBjAuth(ptlBjApprlVo);
			if(resultMap != null) {
				ptlInformDao.updatePtlBjApprl(ptlBjApprlVo);
			}else {
				// BJ 권한 부여 insert
				ptlInformDao.insertPtlBjApprl(ptlBjApprlVo);
				
			}
			
			
			message = "Y";
		}
		
		return message;
	}
	
	/**
	  * <pre>
	  * 
	  * BJ 승인신청 update
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 08. 29.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void updatePtlBjApprl(PtlBjApprlVo ptlBjApprlVo) throws IOException, SQLException, NullPointerException {
		ptlInformDao.updatePtlBjApprl(ptlBjApprlVo);
	}
	
	
	
	/**
	  * <pre>
	  * 
	  * PASS 실명인증 (MBTLNUM_YN 수정) : bj승인신청, 병풍선구매, 19금 인증시
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 6. 1.
	  * @param mbtlnum
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void passAuthentication(Map<String,Object> param) throws IOException, SQLException, NullPointerException {
		ptlInformDao.passAuthentication(param);
	}
	
	
	
	
	
	/**
	  * <pre>
	  * 
	  * 개인정보 중 휴대전화번호 수정한다
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 6. 18.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void updatePtlEmplyrHp(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException{
		ptlInformDao.updatePtlEmplyrHp(ptlUserRegVo);
	}
	
	
	
	/**
	  * <pre>
	  * 
	  * 개인정보 중 휴대전화번호 수정한다.
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlUserRegVo
	  * @param userVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public String infoMemberHpEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo) throws IOException, SQLException , NullPointerException{
		
		String message="";
		String emplyrkey = userVo.getEmplyrkey();
		
		ptlUserRegVo.setEmplyrkey(emplyrkey);
		
		// 사용자 DB 등록
		this.updatePtlEmplyrHp(ptlUserRegVo);
		
		message="Y";
		
		return message;
	}
	

	/**
	  * <pre>
	  * 
	  * 개인정보 중 이메일 주소를 수정한다.
	  * </pre>
	  * @author : wonki0138
	  * @date   : 2018. 3. 22.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void updatePtlEmplyrEmail(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException{
		ptlInformDao.updatePtlEmplyrEmail(ptlUserRegVo);
	}
	

	/**
	  * <pre>
	  * 
	  * 개인정보 중 이메일 주소를 수정한다.
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 6. 18.
	  * @param ptlUserRegVo
	  * @param userVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public String infoMemberEmailEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo) throws IOException, SQLException , NullPointerException{
		
		String message="";
		String emplyrkey = userVo.getEmplyrkey();
		
		String email1 = ptlUserRegVo.getEmail1();
		String email2 = ptlUserRegVo.getEmail2();
		String email = email1+"@"+email2;
		
		ptlUserRegVo.setEmail(email);
		ptlUserRegVo.setEmplyrkey(emplyrkey);
		
		// 사용자 DB 등록
		this.updatePtlEmplyrEmail(ptlUserRegVo);
		
		message="Y";
		
		return message;
	}
	
	
	/**
	  * 광고성정보 수신여부를 수정한다.
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlUserRegVo
	  * @param ptlInformVo
	  * @param userVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public String infoMemberAgreeEdit(PtlUserRegVo ptlUserRegVo, UserVo userVo) throws IOException, SQLException , NullPointerException{
		
		String message="";
		String emplyrkey = userVo.getEmplyrkey();
		
		ptlUserRegVo.setEmplyrkey(emplyrkey);
		String email =  ptlUserRegVo.getEmail1() + "@" + ptlUserRegVo.getEmail2();
		ptlUserRegVo.setEmail(email);
		// 사용자 DB 등록
		this.updateAgree(ptlUserRegVo);
		 
		message="Y";
		
		return message;
	}
	
	
	/**
	  * 광고성정보 수신여부를 수정한다.
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlInformVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void updateAgree(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException, NullPointerException {
		ptlInformDao.updateAgree(ptlUserRegVo);
	}

	
	
	
	
	
	/**
	  * <pre>
	  * 
	  * 회원탈퇴를 수행한다.
	  * </pre>
	  * @author : wonki0138
	  * @date   : 2018. 3. 23.
	  * @param ptlUserRegVo
	  * @param userVo
	  * @param ptlInformVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void deleteAccount(UserVo userVo) throws IOException, SQLException , NullPointerException{
		
		String message="";
		
		String emplyrkey = userVo.getEmplyrkey();
		PtlUserRegVo ptlUserRegVo =  new PtlUserRegVo();
		ptlUserRegVo.setEmplyrkey(emplyrkey);
		
		this.deletePtlEmplyr(ptlUserRegVo);
		
	}
	
	
	/**
	  * <pre>
	  * 
	  * 회원삭제시 PTL_EMPLYR을 수정한다.(초기화)
	  * </pre>
	  * @author : wonki0138
	  * @date   : 2018. 3. 23.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	@Override
	public void deletePtlEmplyr(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException{
		ptlInformDao.deletePtlEmplyr(ptlUserRegVo);
	}
	
	
	


	@Override
	public String authNumCompare(String authNumInput, HttpServletRequest request)throws IOException, SQLException, NullPointerException {
		String message ="";
		String authNum = (String) request.getSession().getAttribute("AUTHPHONE");

		Boolean chkNullVal = true;
		String chkNumVal = "N";
		
		try{
			if(StringUtils.isBlank(authNumInput)){					
				message = "inputNull";
				chkNullVal = false;
			}
			
		    if(StringUtils.isBlank(authNum)){			
				message = "numNull";
				chkNullVal = false;
			}
		 
		    if(chkNullVal != false){
		    	if(StringUtils.equals(authNumInput, authNum)){
		    		chkNumVal = "Y";
		    	}else{
		    		chkNumVal = "N";
		    	}
		    }
		    
		    if(StringUtils.equals(chkNumVal, "Y") && chkNullVal == true){
		    	message = "Y";
		    }else{
		    	message = "N";
		    }
	    
		}catch (NullPointerException ex ){
			logger.info("#############################################################");
 			logger.info("#############################################################");
 			logger.info("##### 인증번호   비교  NullPointerException 오류!!!!!!!!!!!!!!!!!#####");
 			logger.info("#############################################################");
 			logger.info("#############################################################");
 			message= "ERROR";
		}catch(RuntimeException ex ){
			logger.info("#############################################################");
 			logger.info("#############################################################");
 			logger.info("##### 인증번호   비교  RuntimeException 오류!!!!!!!!!!!!!!!!!#####");
 			logger.info("#############################################################");
 			logger.info("#############################################################");
 			message= "ERROR";
		}
		
		
		return message;
	}



	

	
	@Override
	public String authEmailCompare(String authEmailInput, HttpServletRequest request)throws IOException, SQLException, NullPointerException {
		String message ="";
		String authEmail = (String) request.getSession().getAttribute("AUTHEMAIL");

		Boolean chkNullVal = true;
		String chkEmailVal = "N";
		
		try{
			if(StringUtils.isBlank(authEmailInput)){					
				message = "inputNull";
				chkNullVal = false;
			}
			
		    if(StringUtils.isBlank(authEmail)){			
				message = "emailNull";
				chkNullVal = false;
			}
		 
		    if(chkNullVal != false){
		    	if(StringUtils.equals(authEmailInput, authEmail)){
		    		chkEmailVal = "Y";
		    	}else{
		    		chkEmailVal = "N";
		    	}
		    }
		    
		    if(StringUtils.equals(chkEmailVal, "Y") && chkNullVal == true){
		    	message = "Y";
		    }else{
		    	message = "N";
		    }
	    
		}catch (NullPointerException ex ){
			logger.info("#############################################################");
 			logger.info("#############################################################");
 			logger.info("##### 인증번호   비교  NullPointerException 오류!!!!!!!!!!!!!!!!!#####");
 			logger.info("#############################################################");
 			logger.info("#############################################################");
 			message= "ERROR";
		}catch(RuntimeException ex ){
			logger.info("#############################################################");
 			logger.info("#############################################################");
 			logger.info("##### 인증번호   비교  RuntimeException 오류!!!!!!!!!!!!!!!!!#####");
 			logger.info("#############################################################");
 			logger.info("#############################################################");
 			message= "ERROR";
		}
		
		
		return message;
	}


	@Override
	public String authenticationPhone(PtlUserRegVo ptlUserRegVo, PtlInformVo ptlInformVo, UserVo userVo,
			HttpServletRequest request) throws IOException, SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String authenticationEmail(PtlUserRegVo ptlUserRegVo, PtlInformVo ptlInformVo, UserVo userVo,
			HttpServletRequest request) throws IOException, SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}


	

	



}
