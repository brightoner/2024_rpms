package kr.go.rastech.ptl.opsmng.exchMgmt.service;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.commons.imgFile.vo.ImgFileVo;


/**
 * <pre>
 * FileName: ChMngService.java
 * Package : kr.go.rastech.ptl.mng.pop.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
public interface ExchMgmtService {
	
	
	/**
	 * <pre>
	 * 환전 정보 세부 (미사용)
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectExchBaseInfoDtl(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 환전 이력 목록
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public List<Map<String, Object>> selectExchInfoList(Map<String, Object> param) throws IOException, SQLException , NullPointerException;

	/**
	 * <pre>
	 * 환전 이력 total cnt
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public int selectExchInfoCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	


	/**
	 * <pre>
	 * 환전 신청 유저 item 현황
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2023. 06. 09.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	public Map<String, Object> selectUserExchSituationData(Map<String, Object> param)throws IOException, SQLException , NullPointerException;
	
	
	
	public int updateExchState(Map<String, Object> param) throws IOException, SQLException , NullPointerException;
	
	
	
}
