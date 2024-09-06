package kr.go.rastech.ptl.opsmng.chatBlackList.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.ptl.opsmng.chatBlackList.dao.ChatBlackListDao;



/**
 * <pre>
 * FileName: ChNotiMngMngServiceImpl.java
 * 
 *
 * </pre>
 * @author : lwk
 * @date   :2023. 04. 24.
 */
@Service
public class ChatBlackListServiceImpl extends BaseServiceImpl implements ChatBlackListService {

	@Resource
	private ChatBlackListDao chatBlackListDao;
	

	/**
	 * <pre>
	 * 블랙리스트 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectChatBlackListTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return chatBlackListDao.selectChatBlackListTotalCount(param);
	}

	/**
	 * <pre>
	 * 방송설정용 블랙리스트 전체 조회
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectMainChatBlackList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return chatBlackListDao.selectMainChatBlackList(param);
	}
	
	/**
	 * <pre>
	 * 방송설정용 블랙리스트 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectMainChatBlackListTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return chatBlackListDao.selectMainChatBlackListTotalCount(param);
	}

	/**
	 * <pre>
	 * 블랙리스트 전체 조회
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectChatBlackList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return chatBlackListDao.selectChatBlackList(param);
	}

	/**
	 * <pre>
	 * 블랙리스트 등록
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int insertChatBlackList(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		
		int seq = chatBlackListDao.insertChatBlackList(param);
		return seq;
	}


	/**
	 * <pre>
	 * 블랙리스트 수정
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int updateChatBlackList(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		
		int seq = chatBlackListDao.updateChatBlackList(param);
		return seq;
	}


	/**
	 * <pre>
	 * 블랙리스트 삭제
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2023. 04. 24.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public void deleteChatBlackList(String param) throws IOException, 	SQLException, NullPointerException {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		UserVo userVo = getUser();
		
		String loginKey = userVo.getEmplyrkey();
		
		
		if(StringUtils.isNotBlank(param)) {
	    	String[] arrForbid = param.split(",");
	    	
	    	if(arrForbid != null) {
	    		if(arrForbid.length > 0 ) {
			    	for (int i = 0; i < arrForbid.length; i++) {	
			    		mapParam.put("bl_id", arrForbid[i]);
			    		mapParam.put("emplyrkey", loginKey);
			    		chatBlackListDao.deleteChatBlackList(mapParam);
					}
	    		}
	    	}
    	}
	}
	
	/**
	 * <pre>
	 * 블랙리스트 포함 여부 조회
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String,Object> selectChatBlackListChkYn(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return chatBlackListDao.selectChatBlackListChkYn(param);
	}
	
	/**
	 * <pre>
	 * 블랙리스트 중복체크 
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectChatBlackListDupleUserChk(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return chatBlackListDao.selectChatBlackListDupleUserChk(param);
	}
}
