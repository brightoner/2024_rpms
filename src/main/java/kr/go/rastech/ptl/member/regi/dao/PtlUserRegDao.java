package kr.go.rastech.ptl.member.regi.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;

/**
 * <pre>
 * PTL_UserReg Dao interface 구현
 * 
 * </pre>
 * @FileName : PtlUserRegDao.java
 * @package  : kr.go.ncmik.ptl.member.regi.dao
 * @author   : user
 * @date     : 2018. 7. 4.
 * 
 */
@Repository
public interface PtlUserRegDao {


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
	
	


	
}
