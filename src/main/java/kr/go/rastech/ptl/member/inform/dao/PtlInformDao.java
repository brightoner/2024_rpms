package kr.go.rastech.ptl.member.inform.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.member.inform.vo.PtlBjApprlVo;
import kr.go.rastech.ptl.member.regi.vo.PtlUserRegVo;

/**
 * <pre>
 *  회원정보 Dao
 * 
 * </pre>
 * @FileName : PtlInformDao.java
 * @package  : kr.go.ncmik.ptl.member.inform.dao
 * @author   : wonki0138
 * @date     : 2018. 3. 2.
 * 
 */
@Repository
public interface PtlInformDao {

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
	public int idDuplChk(String user_id) throws IOException, SQLException , NullPointerException;
	
	
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
	public int selectInformDetailCnt(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
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
	public PtlUserRegVo selectInformDetail(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	
	/**
	  * <pre>
	  * 
	  * 닉네임을 수정한다.
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	public void updateNicknm(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;

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
	public void updatePtlEmplyrPW(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	
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
	public Map<String, Object> selectBjAuth(PtlBjApprlVo ptlBjApprlVo) throws IOException, SQLException , NullPointerException;
	
	
	/**
	  * <pre>
	  * 
	  * BJ 승인신청 insert : ptl_bj_apprl
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 5. 5.
	  * @param ptlBjApprlVo
	  * @return
	  * @throws IOException, SQLException , NullPointerException
	  */
	public int insertPtlBjApprl(PtlBjApprlVo ptlBjApprlVo) throws IOException, SQLException, NullPointerException;
	
	
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
	public void updatePtlBjApprl(PtlBjApprlVo ptlBjApprlVo) throws IOException, SQLException , NullPointerException;
	
	
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
	public void passAuthentication(Map<String,Object> param) throws IOException, SQLException , NullPointerException;
	
	/**
	  * <pre>
	  * 
	  * 개인정보중 핸즈폰 번호를 수정한다.
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 6. 18.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	public void updatePtlEmplyrHp(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 
	 * 개인정보중 이메일 주소를 수정한다.
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 6. 18.
	 * @param ptlUserRegVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updatePtlEmplyrEmail(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	
	/**
	  * <pre>
	  * 
	  * 광고성정보 수신여부를 수정한다.
	  * </pre>
	  * @author : ljk
	  * @date   : 2023. 4. 24.
	  * @param ptlInformVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	public void updateAgree(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	
	
	/**
	  * <pre>
	  * 
	  * 회원탈퇴시 PTL_EMPLYR을 수정한다.(초기화)
	  * </pre>
	  * @author : wonki0138
	  * @date   : 2018. 3. 23.
	  * @param ptlUserRegVo
	  * @throws IOException, SQLException , NullPointerException
	  */
	public void deletePtlEmplyr(PtlUserRegVo ptlUserRegVo) throws IOException, SQLException , NullPointerException;
	
	
	

		
	
}
