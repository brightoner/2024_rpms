package kr.go.rastech.ptl.mng.sch.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.mng.sch.dao.SchDao;
import kr.go.rastech.ptl.mng.sch.vo.EtlMngVo;

@Service
public class SchServiceImpl extends BaseServiceImpl  implements SchService 
{

	@Resource
	SchDao schDao;
	
	/**
	 * <pre>
	 * 데이터 xml파일로 저장
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 3. 27.
	 * @param params
	 */
	@SuppressWarnings("unchecked")
    public void saveScList(List<Map<String,Object>> params)
    {		
		Map<String,Object> map = null;		
		for(int i =0 ; i<params.size() ; i++)
		{	
			 map = (Map<String, Object>) params.get(i);
			 
			 //등록
			 if(((String)map.get("rowStatus")).equals("C")){
				 schDao.insertScList(map);
		     //수정
			 }else if(((String)map.get("rowStatus")).equals("U")){
				 schDao.updateScList(map);
		     //삭제
			 }else{
				 schDao.deleteScList(map);
			 }
		}
    }
	
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
	public List<Map<String, Object>> selectSpList(Map<String, Object> params)
    {
		List<Map<String, Object>> list = schDao.selectSpList(params);
		
	    return list;
    }

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
	public List<Map<String, Object>> selectScList(Map<String, Object> params)
    {
		List<Map<String, Object>> list = schDao.selectScList(params);
		
	    return list;
    }
	
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
    public String getValue(Element parent, String nodeName) {    

    	String resultVal="";
    	if(parent.getElementsByTagName(nodeName).item(0).getFirstChild() != null){
    		if(parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue() != null){
    			resultVal=parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue().trim();	
    		}
		}
    	return resultVal;     
    }  
    
    
    
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
	public void saveEtlList(List<EtlMngVo> params) throws NumberFormatException, ParseException
    {
		
		List<EtlMngVo> list = new ArrayList<EtlMngVo>();
		EtlMngVo mngVo = null;
		Map<String, Object> param;
		
		Calendar cal = Calendar.getInstance();

		Date dt = new Date();
		SimpleDateFormat sds = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String dt1 = sds.format(dt);
		
		String dt2 = sd.format(dt);
	
		for(int rowStatus = 0; rowStatus < params.size(); rowStatus++)
        {
			mngVo = null;
			mngVo = (EtlMngVo) params.get(rowStatus);
			//TODO				
			if(!StringUtils.equals("D", mngVo.getSave_type())){
			
				String yn = "Y";
				
				String prctime = dt1 +" "+ mngVo.getEtlHh()+":"+mngVo.getEtlSs();
				/**
				 * 다음 실행일자를 세팅
				 */
				while(yn.equals("Y"))
		        {
					if(sd.parse(prctime).compareTo(sd.parse(dt2)) <= 0){
						
						cal.setTime(sd.parse(prctime));
						
						if(StringUtils.equals("1", mngVo.getEtlType())){//1 :  주기(분)
							cal.add(Calendar.MINUTE, Integer.parseInt(mngVo.getEtlTime()));
						}else if(StringUtils.equals("2", mngVo.getEtlType())){//2 : 주기(시간)
							cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(mngVo.getEtlTime()));
						}else{//3 : 주기(일)
							cal.add(Calendar.DAY_OF_MONTH,Integer.parseInt(mngVo.getEtlTime()));
						}
							
						prctime = sd.format(cal.getTime());
									
					}else{
						yn = "N";
					}
		        }
				mngVo.setNextDt(prctime);
	
				param = new HashMap<>();

				param.put("etl_id", mngVo.getEtlId());
				param.put("etl_nm", mngVo.getEtlNm());
				param.put("etl_type", mngVo.getEtlType());
				param.put("etl_time", mngVo.getEtlTime());
				param.put("etl_tb", mngVo.getEtlTb());
				param.put("etl_gbn", mngVo.getEtlGbn());
				param.put("etl_hh", mngVo.getEtlHh());
				param.put("etl_ss", mngVo.getEtlSs());
				param.put("next_dt", mngVo.getNextDt());
				param.put("use_yn", mngVo.getUseYn());
				param.put("etl_sn", mngVo.getEtlSn());
				
	            if(StringUtils.equals("I", mngVo.getSave_type())){    
				    schDao.insertEtl(param);
				    
	            }else{
		           schDao.updateEtl(param);
	            }
	
			}else{
				//삭제 순번 존재시
				if(StringUtils.isNotBlank(mngVo.getEtlSn())){
         
					param = new HashMap<>();
					param.put("etl_sn", mngVo.getEtlSn());
					
					schDao.deleteEtlDtl(param);
					schDao.deleteEtl(param);
					
				}
				
			}
        }
    }
	
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
	public List<Map<String, Object>> selectEtlList(Map<String, Object> params)
    {
		List<Map<String, Object>> list  = schDao.selectEtlList(params);
	    return list;
    }

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
	public List<Map<String, Object>> selectEtlDtlList(Map<String, Object> params)
    {
	    List<Map<String, Object>> list  = schDao.selectEtlDtlList(params);
	    return list;
    }

	

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 6. 29.
	 * @param scMap
	 */
	public void updateNextDt(Map<String, Object> scMap)
    {
		schDao.updateNextDt(scMap);
    }

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
	public List<Map<String, Object>> selectEtlUpdList(Map<String, String> map)
    {
		return schDao.selectEtlUpdList(map);
    }

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @param map 
	 * @date   : 2017. 7. 10.
	 */
	public void updateEtlCnt(Map<String, Object> map)
    {
		schDao.updateEtlCnt(map);
    }

	/**
	 * <pre>
	 * 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 10.
	 * @param scMap
	 */
	public void insertEtlDtl(Map<String, Object> scMap)
    {
		schDao.insertEtlDtl(scMap);
    }

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
	public List<Map<String, Object>> selectBdsDataList(Map<String, Object> scMap)
    {
		List<Map<String, Object>> list  = schDao.selectBdsDataList(scMap);
	    return list;
    }

	/**
	 * <pre>
	 * 메인화면 터널 전력사용증감률 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 7. 31.
	 * @return
	 */
	public List<Map<String, Object>> selectTerRtList()
    {
		List<Map<String, Object>> list  = schDao.selectTerRtList();
	    return list;
    }

	/**
	 * <pre>
	 * ETL 시간 초기화
	 *
	 * </pre>
	 * @author : Administrator
	 * @throws SQLException 
	 * @date   : 2017. 8. 14.
	 */
	public void timeInit() throws SQLException
    {
		
		List<Map<String, Object>> list  = schDao.selectUseEtlList();
		
		for(Map<String, Object> map : list)
        {
			map.put("proc_type", "m");
			this.updNextDt(map);
        }
		
    }

	/**
	 * <pre>
	 * ETL 상태 초기화
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 11. 16.
	 */
	public void updateEtlStat()
    {
		schDao.updateEtlStat();
    }
	
    /**
     * <pre>
     * 다음 실행일자 업데이트
     *
     * </pre>
     * @author : Administrator
     * @throws ParseException 
     * @throws NumberFormatException 
     * @date   : 2017. 8. 1.
     */
    public void updNextDt(Map<String, Object> scMap) throws SQLException{

		String yn = "Y";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();
		String dt2 = sd.format(dt);
		/**
		 * 다음 실행일자를 세팅
		 */
		String prctime = scMap.get("next_dt").toString();
		while(yn.equals("Y"))
        {
			try
            {
                if(sd.parse(prctime).compareTo(sd.parse(dt2)) <= 0){
                	
                	cal.setTime(sd.parse(prctime));
                	
                	if(StringUtils.equals("1", scMap.get("etl_type").toString())){//1 :  주기(분)
                		cal.add(Calendar.MINUTE, Integer.parseInt(scMap.get("etl_time").toString()));
                	}else if(StringUtils.equals("2", scMap.get("ETL_TYPE").toString())){//2 : 주기(시간)
                		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(scMap.get("etl_time").toString()));
                	}else{//3 : 주기(일)
                		cal.add(Calendar.DAY_OF_MONTH,Integer.parseInt(scMap.get("etl_time").toString()));
                	}
                	prctime = sd.format(cal.getTime());
                			
                }else{
                	yn = "N";
                }
            }
            catch(NumberFormatException e)
            {
            	logger.info("Error: " + "updNextDt 오류발생! ");
            }
            catch(ParseException e)
            {
            	logger.info("Error: " + "updNextDt 오류발생! ");
            }
        }
		scMap.put("next_dt", prctime);
		
		this.updateNextDt(scMap);
		//실제 실행시간
		
		if(!scMap.get("proc_type").toString().equals("a")){
			scMap.put("next_dt", sd.format(dt).toString());
	   	}

    }

}
