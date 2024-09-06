package kr.go.rastech.ptl.member.regi.service;

import java.io.IOException;
import java.sql.SQLException;

import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;

/**
 * <pre>
 * PTL_UserReg Service interface 구현
 * 
 * </pre>
 * @FileName : PtlUserRegService.java
 * @package  : kr.go.ncmik.ptl.member.regi.service
 * @author   : user
 * @date     : 2018. 7. 4.
 * 
 */
public interface PtlUserRegService {

	
	
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
	public int selectNicknmCheck(String nicknm) throws IOException, SQLException , NullPointerException;


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
	public void insertPtlUserReg(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 * 회원가입을 수행한다.
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 3. 2. 
	 * @param ptlUserRegVo
	 * @param ptlInformVo
	 * @param userVo
	 * @param MngYieldVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public String addPtlUserReg(PtlUserRegVo ptlUserRegVo, UserVo userVo) throws IOException, SQLException , NullPointerException;
	

	
}
