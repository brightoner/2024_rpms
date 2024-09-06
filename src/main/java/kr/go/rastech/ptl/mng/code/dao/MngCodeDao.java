package kr.go.rastech.ptl.mng.code.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;



/**
 * <pre>
 * FileName: MngCodeDao.java
 * Package : kr.go.ncmiklib.ptl.mng.code.dao
 * 
 *  코드관리 Dao
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 1. 19.
 */
@Repository
public interface MngCodeDao{


	/**
	 * <pre>
	 * 코드관리 - 코드 정보 등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 19.
	 * @param mngCodeVo
	 * @throws IOException , SQLException , NullPointerException
	 */
	public void insertCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException;

	
	/**
	 * <pre>
	 * 코드관리 - 코드 정보 조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 19.
	 * @param mngCodeVo
	 * @throws IOException , SQLException , NullPointerException
	 */
	public List<MngCodeVo> selectCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException;


	/**
	 * <pre>
	 * 코드관리 - 코드 정보 수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 20.   
	 * @param mngCodeVo
	 */
	public void updateCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException;


	/**
	 * <pre>
	 * 코드관리 -코드삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @param mngCodeVo
	 * @throws IOException , SQLException , NullPointerException
	 */
	public void deleteCdMng(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException;
	
	
	
	public String selectSearchnm(String cd) throws IOException , SQLException , NullPointerException;


	public List<MngCodeVo> selectKomsCodeList(MngCodeVo mngCodeVo) throws IOException , SQLException , NullPointerException;
	
	
	

}
