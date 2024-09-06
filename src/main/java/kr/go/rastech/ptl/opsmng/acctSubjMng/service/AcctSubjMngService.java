package kr.go.rastech.ptl.opsmng.acctSubjMng.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.ptl.opsmng.acctSubjMng.vo.AcctSubjMngVo;

/**
 *
 * <pre>
 * FileName: AcctSubjMngService.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.service
 *
 * mng AcctSubjMng 관리 interface Service
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 11.
 */
public interface AcctSubjMngService {


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param mngAcctSubjMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<AcctSubjMngVo> readAcctSubjMngList(AcctSubjMngVo mngAcctSubjMngVo)  throws IOException, SQLException , NullPointerException ;

	
	/**
	 * <pre>
	 * 메뉴관리 - 메뉴등록
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 12.
	 * @param mngAcctSubjMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertAcctSubjMng(AcctSubjMngVo mngAcctSubjMngVo) throws IOException, SQLException , NullPointerException ;


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 12.
	 * @param mngAcctSubjMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateAcctSubjMng(AcctSubjMngVo mngAcctSubjMngVo) throws IOException, SQLException , NullPointerException ;


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 16.
	 * @param mngAcctSubjMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteAcctSubjMng(AcctSubjMngVo mngAcctSubjMngVo) throws IOException, SQLException , NullPointerException ;





	/**
	 * <pre>
	 * 메뉴관리 - 하위메뉴사용유무 수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 25.
	 * @param mngAcctSubjMngVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateAcctSubjMngDtl(AcctSubjMngVo acctSubjMngVo)throws IOException, SQLException , NullPointerException ;

	
	/**
	 * <pre>
	 * 기관 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectAcctSubjMngTotalPopCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
	/**
	 * <pre>
	 * 기관 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectAcctSubjMngPopList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  ;
	
	
}
