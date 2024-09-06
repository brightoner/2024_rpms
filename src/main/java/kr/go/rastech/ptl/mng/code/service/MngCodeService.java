package kr.go.rastech.ptl.mng.code.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;


/**
 * <pre>
 * FileName: MngCodeService.java
 * Package : kr.go.ncmiklib.ptl.mng.code.service
 * 
 * 코드관리 서비스
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 1. 19.
 */
public interface MngCodeService {
	
	/**
	 * <pre>
	 *  코드 관리 -코드등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 19.
	 * @param mngCodeVo
	 * @throws IOException , SQLException , NullPointerException
	 */
	public void insertCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException ;

	
	/**
	 * <pre>
	 * 코드 관리 -코드 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 19.
	 * @param mngCodeVo
	 * @return 
	 * @throws IOException , SQLException , NullPointerException
	 */
	public List<MngCodeVo> selectCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException ;


	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 20.
	 * @param mngCodeVo
	 */
	public void updateCdMng(MngCodeVo mngCodeVo)throws IOException , SQLException , NullPointerException ;

	/**
	 * <pre>
	 * 코드 리스트 가져오기
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 26.
	 * @param up_cd
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	public List<MngCodeVo> getCodeList(String up_cd) throws IOException , SQLException , NullPointerException;
	
	/**
	 * <pre>
	 * 코드값 가져오기
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 26.
	 * @param up_cd
	 * @param cd
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	public MngCodeVo getCode(String up_cd, String cd) throws IOException , SQLException , NullPointerException;


	/**
	 * <pre>
	 * 코드관리 - 코드삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @param mngCodeVo
	 * @throws IOException , SQLException , NullPointerException
	 */
	public void deleteCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException;

	
	
	public String selectSearchnm(String cd)throws IOException , SQLException , NullPointerException;


	public List<MngCodeVo> getKomsCodeList(String code)throws IOException , SQLException , NullPointerException;
	
}
