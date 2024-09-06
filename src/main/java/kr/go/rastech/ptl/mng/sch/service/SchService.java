package kr.go.rastech.ptl.mng.sch.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;

import kr.go.rastech.ptl.mng.sch.vo.EtlMngVo;

public interface SchService
{

	/**
	 * <pre>
	 * 데이터 xml파일로 저장
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 3. 27.
	 * @param params
	 */
    public void saveScList(List<Map<String,Object>> params);
	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 13.
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectSpList(Map<String, Object> params);

	/**
	 * <pre>
	 * xml 데이터 조회
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2017. 3. 27.
	 * @param params
	 * @return
	 
	 */
	public List<Map<String, Object>> selectScList(Map<String, Object> params);
    /**
     * <pre>
     * 컬럼 데이터 가져오기
     *
     * </pre>
     * @author : rastech
     * @date   : 2017. 3. 27.
     * @param parent
     * @param nodeName
     * @return
     */
    public String getValue(Element parent, String nodeName) ;
    
    
	/**
	 * <pre>
	 * etl 관리 데이터 저장
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 1.
	 * @param params
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  
	 */
	public void saveEtlList(List<EtlMngVo> params) throws NumberFormatException, ParseException;
	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 9.
	 * @param params
	 * @return
	  
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> selectEtlList(Map<String, Object> params);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 10.
	 * @param params
	 * @return
	 
	 */
	public List<Map<String, Object>> selectEtlDtlList(Map<String, Object> params);

	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param scMap
	 */
	public void updateNextDt(Map<String, Object> scMap);
	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param scMap
	 */
	public void updNextDt(Map<String, Object> scMap) throws SQLException;
	
	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @param map 
	 * @return 
	 * @date   : 2017. 7. 10.
	 */
	public List<Map<String, Object>> selectEtlUpdList(Map<String, String> map);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @param map 
	 * @date   : 2017. 7. 10.
	 */
	public void updateEtlCnt(Map<String, Object> map);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 10.
	 * @param scMap
	 */
	public void insertEtlDtl(Map<String, Object> scMap);

	/**
	 * <pre>
	 * 분석서버 교통량 정보 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 30.
	 * @param scMap
	 * @return
	 */
	public List<Map<String, Object>> selectBdsDataList(Map<String, Object> scMap);

	/**
	 * <pre>
	 * 메인화면 터널 전력사용증감률 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 31.
	 * @return
	 */
	public List<Map<String, Object>> selectTerRtList();

	/**
	 * <pre>
	 * ETL 시간 초기화
	 *
	 * </pre>
	 * @author : Administrator
	 * @throws SQLException 
	 * @date   : 2017. 8. 14.
	 */
	public void timeInit() throws SQLException;

	/**
	 * <pre>
	 * ETL 상태 초기화
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 11. 16.
	 */
	public void updateEtlStat();

}
