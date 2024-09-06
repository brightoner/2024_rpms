package kr.go.rastech.commons.login.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.commons.login.vo.UserVo;

/**
 *
 * <pre>
 * FileName: LoginDao.java
 * Package : kr.go.ncmiklib.commons.login.dao
 *
 * 로그인 DAO interface.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 1. 23.
 */
@Repository
public interface LoginDao {

    /**
     *
     * <pre>
     * 사용자 관리 - 사용자 아이디 체크
     *
     * </pre>
     * @author : sbkang
     * @date   : 2023. 8. 31.
     * @param loginid
     * @return
     */
	public int selectIdCheck(String loginid) throws IOException, SQLException , NullPointerException;


	/**
	 * <pre>
	 * 사용자 관리 - 사용자 가입
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 23.
	 * @param PTLLoginVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertUser(Map<String, Object> ptlUserRegVo) throws IOException, SQLException , NullPointerException;


	/**
	 *
	 * <pre>
	 * 사용자 로그인 id 체크
	 *
	 * </pre>
	 * @author : sbkang
	 * @date   : 2023. 8. 31.
	 * @param loginid
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public UserVo selectUser(String loginid) throws IOException, SQLException , NullPointerException;


	/**
	 *
	 * <pre>
	 * 사용자 권한 조회.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 22.
	 * @param loginid
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<String> selectUserAuthList(String loginid) throws IOException, SQLException , NullPointerException;

	/**
	 *
	 * <pre>
	 * 사용자 URL 접속 권한 목록.
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 9. 30.
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<Map<String, String>> selectUrlAuthList() throws IOException, SQLException , NullPointerException;


	/**
	 *
	 * <pre>
	 * 사용자 로그인정보 등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 10. 16.
	 * @param loginid
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertStatLogin(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	
	
	/**
	 * <pre>
	 * 핸드폰 중복 체크 
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 3. 2. 
	 * @param vo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public UserVo selectMbtl(String mbtlNum) throws IOException, SQLException , NullPointerException;
	
	
	/**
	 * <pre>
	 *
	 * 이메일 중복 체크 
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 16. 
	 * @param email
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public UserVo selectEmail(String email) throws IOException, SQLException , NullPointerException;




	/**
	  * @Method Name : insertEmail
	  * @작성일 : 2023. 8. 11.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void insertEmail(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	/**
	  * @Method Name : updateUser
	  * @작성일 : 2023. 8. 11.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param ptlUserRegVo
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public void updateUser(Map<String, Object> ptlUserRegVo) throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : updateEmail
	  * @작성일 : 2023. 8. 20.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public int updateEmail(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	/**
	  * @Method Name : deleteUser
	  * @작성일 : 2023. 8. 20.
	  * @작성자 : User
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param param
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public int deleteUser(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	public String selectUserEmail(String mbtlnum) throws IOException, SQLException , NullPointerException;


	public void changePwd(Map<String, Object> param) throws IOException, SQLException , NullPointerException;


	public void insertStatLogin(String user_id)throws IOException, SQLException , NullPointerException;


	public String selectfindUserChk(String sMobileNo) throws IOException, SQLException , NullPointerException;


	/**
	  * 아이디 찾기
	  * @작성일 : 2023. 4. 26.
	  * @작성자 : ljk
	  * @param mbtlnum
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public String selectfindUserId(String mbtlnum) throws IOException, SQLException , NullPointerException;

	/**
	  * 비밀번호 찾기
	  * @작성일 : 2023. 4. 26.
	  * @작성자 : ljk
	  * @param mbtlnum
	  * @return
	  * @throws IOException
	  * @throws SQLException
	  * @throws NullPointerException
	  */
	public String selectfindUserKey(String mbtlnum) throws IOException, SQLException , NullPointerException;


	public UserVo UserIdPassCheck(UserVo userVo) throws IOException, SQLException , NullPointerException;


	public UserVo UserIdPassCheck(Map<String, Object> usrMap) throws IOException, SQLException , NullPointerException;
	
	
}
