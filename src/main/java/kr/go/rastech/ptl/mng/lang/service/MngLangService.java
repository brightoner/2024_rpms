package kr.go.rastech.ptl.mng.lang.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;

import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;
import kr.go.rastech.ptl.mng.lang.vo.MngLangVo;

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
public interface MngLangService {



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
	 * 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 27.
	 * @param url_seq
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteLang(MngLangVo mngLangVo) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 27.
	 * @param url_seq
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngLangVo> selectLang(MngLangVo mngLangVo)throws IOException, SQLException , NullPointerException;




}
