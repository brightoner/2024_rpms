package kr.go.rastech.ptl.mng.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.ptl.mng.user.vo.MngUserAuthVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserClsVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserLogVo;
import kr.go.rastech.ptl.mng.user.vo.PTLLoginVo;



/**
 * <pre>
 * FileName: MngUserService.java
 * Package : kr.go.ncmiklib.ptl.mng.user.service
 * 
 * mng 사용자 관리 관리 interface Service
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 29.
 */
public interface MngUserService {


	/**
	 * <pre>
	 * 회원관리 - 사용자정보 수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 24.
	 * @param PTLLoginVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateUser(PTLLoginVo pTLLoginVo)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 회원관리 - 사용자관심 저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 24.
	 * @param mngUserClsVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertUserCls(MngUserClsVo mngUserClsVo)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 회원관리 - 사용자관심 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 24.
	 * @param loginid
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngUserClsVo> selectUserCls(String loginid)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 회원관리 - 사용자관심 삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 24.
	 * @param loginid
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteUserCls(String loginid)throws IOException, SQLException , NullPointerException ;

	
	/**
	 * <pre>
	 * 회원관리 - 사용자수 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 29.
	 * @param PTLLoginVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public int selectUserCnt(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 회원관리 - 회원리스트 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 29.
	 * @param PTLLoginVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<PTLLoginVo> selectUserList(Map<String, Object> param)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 회원관리 - 회원권한삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 2.
	 * @param loginid
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteUserAuth(String loginid) throws IOException, SQLException , NullPointerException ;
	
	/**
	 * <pre>
	 * 회원관리 - BJ 권한 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 6. 27.
	 * @param mngUserAuthVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteBjAuth(MngUserAuthVo mngUserAuthVo) throws IOException, SQLException , NullPointerException ;


	/**
	 * <pre>
	 * 회원관리 - 회원권한등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 2.
	 * @param mngUserAuthVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertUserAuth(MngUserAuthVo mngUserAuthVo) throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 회원관리 - 회원권한조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 2.
	 * @param loginid
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngUserAuthVo> selectUserAuth(String loginid) throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 사용자로그 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 4. 26.
	 * @param PTLLoginVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngUserLogVo> selectUserLogList(MngUserLogVo mngUserLogVo)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 *  사용자로그 조회(엑셀다운로드용)
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 4. 28.
	 * @param mngUserLogVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<Map<String, String>> selectUserLogMap(MngUserLogVo mngUserLogVo)throws IOException, SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 사용자 비밀번호 변경 랜덤숫자문자
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 4. 29.
	 * @param PTLLoginVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateChgPwd(PTLLoginVo pTLLoginVo)throws IOException, SQLException , NullPointerException ;

}
