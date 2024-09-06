package kr.go.rastech.ptl.mng.auth.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;

import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;

/**
 *
 * <pre>
 * FileName: MngUrlService.java
 * Package : kr.go.ncmiklib.ptl.mng.url.service
 *
 * mng Url 관리 interface Service
 *
 * </pre>
 * @author : sbkang
 * @date   : 2023. 9. 30.
 */
public interface MngAuthService {



	/**
	 * <pre>
	 * URL 권한  관리 - URL 권한정보 저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 15.
	 * @param request
	 * @throws IOException, SQLException , NullPointerException
	 * @throws ParseException 
	 */
	public String insertUrlAuth(HttpServletRequest request)  throws IOException, SQLException , NullPointerException , ParseException;

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
	 * 메뉴별 권한정보 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngAuthVo> listMenuAuth()throws IOException, SQLException , NullPointerException;


}
