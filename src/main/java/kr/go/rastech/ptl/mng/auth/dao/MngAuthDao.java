package kr.go.rastech.ptl.mng.auth.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;


/**
 * <pre>
 * FileName: MngAuthDao.java
 * Package : kr.go.ncmiklib.ptl.mng.auth.dao
 * 
 * mng Url 관리 Dao
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 15.
 */
@Repository
public interface MngAuthDao {

	/**
	 * <pre>
	 * URL 권한  관리 - URL 권한정보 저장
	 *
	 * </pre>
	 * 
	 * @author : rastech
	 * @date : 2023. 2. 15.
	 * @param valueMap
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertUrlAuth(MngAuthVo mngAuthVo) throws IOException, SQLException , NullPointerException ,ParseException;

	/**
	 * <pre>
	 * URL 권한  관리 - URL 권한정보 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param mngAuthVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngAuthVo> readUrlAuth(MngAuthVo mngAuthVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * URL 권한  관리 - URL 권한정보 수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 17.
	 * @param mngAuthVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateUrlAuth(MngAuthVo mngAuthVo) throws IOException, SQLException , NullPointerException;
       
	/**
	 * <pre>
	 * URL 권한  관리 - URL 권한정보 삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 17.
	 * @param url_seq
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteUrlAuth(MngAuthVo mngAuthVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 *  메뉴별 권한정보 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngAuthVo> readMenuAuth() throws IOException, SQLException , NullPointerException;

}
