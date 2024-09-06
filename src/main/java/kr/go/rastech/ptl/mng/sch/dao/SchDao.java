package kr.go.rastech.ptl.mng.sch.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface SchDao
{
	
	/**
	 * <pre>
	 * etl 리스트 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 23.
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectEtlList(Map<String, Object> params);

	/**
	 * <pre>
	 * etl 상세 리스트 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 23.
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectEtlDtlList(Map<String, Object> params);

	/**
	 * <pre>
	 *  ETL 정보 저장
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param mngVo
	 */
	public void insertEtl(Map<String,Object> param);

	/**
	 * <pre>
	 * ETL 정보 수정
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param mngVo
	 */
	public void updateEtl(Map<String,Object> param);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param param
	 */
	public void deleteEtlDtl(Map<String, Object> param);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param param
	 */
	public void deleteEtl(Map<String, Object> param);

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
	 *  화면 관리 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param params
	 */
	public List<Map<String, Object>>  selectScList(Map<String, Object> params);

	/**
	 * <pre>
	 * 스페이스 정보 조회
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
	 * 화면 관리 등록
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param map
	 */
	public void insertScList(Map<String, Object> map);

	/**
	 * <pre>
	 * 화면 관리 수정
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param map
	 */
	public void updateScList(Map<String, Object> map);

	/**
	 * <pre>
	 * 화면 관리 삭제
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param map
	 */
	public void deleteScList(Map<String, Object> map);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @param map 
	 * @date   : 2017. 7. 10.
	 * @return
	 */
	public List<Map<String, Object>> selectEtlUpdList(Map<String, String> map);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 10.
	 * @param map
	 */
	public void updateEtlCnt(Map<String, Object> map);

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 19.
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
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 9. 21.
	 * @return
	 */
	public List<Map<String, Object>> selectTerRtList();
	/**
	 * <pre>
	 * etl정보 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 8. 14.
	 * @return
	 */
	public List<Map<String, Object>> selectUseEtlList();

	/**
	 * <pre>
	 * ETL 현황
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 9. 24.
	 * @param params
	 * @return 
	 */
	public List<Map<String, Object>> selectEtlStat(Map<String, Object> params);
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