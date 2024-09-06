package kr.go.rastech.ptl.execute.wbs.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.execute.wbs.dao.WbsDao;
	


/**
 * <pre>
 *       
 *
 * </pre>          
 * @author : lwk
 * @date   :2023. 06. 02.
 */
@Service
public class WbsServiceImpl extends BaseServiceImpl implements WbsService { 

	@Resource
	private WbsDao wbsDao;
	

	/**
	 * <pre>
	 * wbs 전체 조회
	 *   
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectWbsList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return wbsDao.selectWbsList(param);
	}
	
	@Override
	public List<Map<String,Object>> selectWbsProgressRateList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return wbsDao.selectWbsProgressRateList(param);
	}
	
	
	@Override
	public List<Map<String,Object>> selectWbsCalendarList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return wbsDao.selectWbsCalendarList(param);
	}
	@Override
	public Map<String,Object> selectWbsInfo(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return wbsDao.selectWbsInfo(param);
	}
	
	@Override
	public List<Map<String,Object>> selectWbsScheduleList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return wbsDao.selectWbsScheduleList(param);
	}
	
	@Override
	public void insertWbsItem(Map<String, Object> params) throws IOException, SQLException , NullPointerException  {
		 UserVo vo = getUser();

		 List<Map<String, Object>> itemList = (List<Map<String, Object>>) params.get("itemList");
		 List<Map<String, Object>> planList = (List<Map<String, Object>>) params.get("planList");
		 List<Map<String, Object>> perforList = (List<Map<String, Object>>) params.get("perforList");
		 Map<String, Object> wbsMap = new HashMap<String, Object>();
		 wbsMap.put("proj_year_id", params.get("proj_year_id"));
		
		Map<String,Object> info = wbsDao.selectWbsInfo(wbsMap);		 
		String wbs_id = Objects.toString(info.get("wbs_id"),"");
		if(StringUtils.isNotBlank(wbs_id)) {
			 if (itemList != null && !itemList.isEmpty()) {
					 for (int i = 0; i < itemList.size(); i++) {
							if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "I") 
									|| StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "D")
									|| StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "U")
									) {				
								if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "I")) {
			
									itemList.get(i).put("create_id", vo.getEmplyrkey());				
									itemList.get(i).put("wbs_id", wbs_id);				
									itemList.get(i).put("use_yn", "Y");				
									wbsDao.insertWbsItem(itemList.get(i));
								}else if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "D")) {									
									itemList.get(i).put("modify_id", vo.getEmplyrkey());
									itemList.get(i).put("wbs_id", wbs_id);				
									itemList.get(i).put("use_yn", "N");
									wbsDao.deleteWbsItem(itemList.get(i));
									
								}else if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "U")) {
									itemList.get(i).put("modify_id", vo.getEmplyrkey());												
									itemList.get(i).put("wbs_id", wbs_id);				
									
									wbsDao.updateWbsItem(itemList.get(i));
								
								
							
									wbsDao.deleteAllWbsSchedule(itemList.get(i));
									
								}
								
								if (planList != null && !planList.isEmpty()) {
								
									for (int planIdx = 0; planIdx < planList.size(); planIdx++) {									
										
										if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_id"),"X") , Objects.toString(planList.get(planIdx).get("item_id"),"XX")))
										{
											if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "I")) {
												planList.get(planIdx).put("create_id", vo.getEmplyrkey());
												planList.get(planIdx).put("wbs_id", wbs_id);
												
												wbsDao.insertWbsSchedule(planList.get(planIdx));								
											}else if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "D")){
											
												wbsDao.deleteWbsSchedule(planList.get(planIdx));
											}else if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "U")){
												planList.get(planIdx).put("create_id", vo.getEmplyrkey());
												planList.get(planIdx).put("wbs_id", wbs_id);
												wbsDao.insertWbsSchedule(planList.get(planIdx));
											} 
										}
									}
										
								 }
								 
								 if (perforList != null && !perforList.isEmpty()) {
								
									for (int perforIdx = 0; perforIdx < perforList.size(); perforIdx++) {
										
										if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_id"),"X") , Objects.toString(perforList.get(perforIdx).get("item_id"),"XX")))
										{
											if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "I")) {
												perforList.get(perforIdx).put("create_id", vo.getEmplyrkey());
											
												perforList.get(perforIdx).put("wbs_id", wbs_id);
												wbsDao.insertWbsSchedule(perforList.get(perforIdx));				
											}else if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "D")){
												
												wbsDao.deleteWbsSchedule(perforList.get(perforIdx));
											}else if(StringUtils.equals( Objects.toString(itemList.get(i).get("item_state"),"") ,  "U")){
												perforList.get(perforIdx).put("create_id", vo.getEmplyrkey());
												perforList.get(perforIdx).put("wbs_id", wbs_id);
												wbsDao.insertWbsSchedule(perforList.get(perforIdx));
											}
										}
									}
							        
								 }
							}
					
					}
		    }
			 
			
		}
		
	
	}


	@Override
	public void insertWbsBaseInfo(Map<String, Object> params) throws IOException, SQLException, NullPointerException {
		
		// TODO Auto-generated method stub
		
		 UserVo vo = getUser();
		 params.put("create_id", vo.getEmplyrkey());				
		wbsDao.insertWbsBaseInfo(params);
	}


	
}
