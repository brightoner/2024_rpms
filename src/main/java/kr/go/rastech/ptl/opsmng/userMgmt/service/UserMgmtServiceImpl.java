package kr.go.rastech.ptl.opsmng.userMgmt.service;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.utils.SmsUtil;
import kr.go.rastech.ptl.opsmng.userMgmt.dao.UserMgmtDao;



/**
 * <pre>
 * 
 *
 * </pre>
 * @author : ljk
 * @date   :2023. 06. 02.
 */
@Service
public class UserMgmtServiceImpl extends BaseServiceImpl implements UserMgmtService {

	@Resource
	private UserMgmtDao userMgmtDao;

	

	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param	
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectUserMgmtTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return userMgmtDao.selectUserMgmtTotalCount(param);
	}

	/**
	 * <pre>
	 * 사용자 목록 전체 조회
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectUserMgmtList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return userMgmtDao.selectUserMgmtList(param);
	}
	
	/**
	 * <pre>
	 * 사용자 목록 메일 방송 처리
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int  selectUserSendMail(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		int sendCnt =0; 
		//List<Map<String,Object>> mailTemplist = mailTempDao.selectMailTempSendList(param);
		List<Map<String,Object>> mailTemplist = null;
		if(mailTemplist != null) {
			if(mailTemplist.size() > 0) {
				String ad = "";
				param.put("mailtemp_gbn", mailTemplist.get(0).get("mailtemp_gbn"));
				if(StringUtils.equals("MAIL_04", (CharSequence) mailTemplist.get(0).get("mailtemp_gbn"))) {
					ad = "(광고)";
				}
				List<Map<String,Object>> userList =userMgmtDao.selectUserSendMailList(param);
				
				if(userList!= null && userList.size()>0) {
					SmsUtil smsUtil = new SmsUtil();
					for(Map<String,Object> map : userList) {
						if(map.get("email") != null) {
							String user_name = (String) map.get("user_id");	
							String email = (String) map.get("email");
							String mailtemp_title = ad + (String) mailTemplist.get(0).get("mailtemp_title");
							String mailtemp_contents = (String) mailTemplist.get(0).get("mailtemp_contents");  
							try {
								smsUtil.sendMail(user_name ,email , mailtemp_title ,mailtemp_contents );
								sendCnt++;
							} catch (URISyntaxException e) {
								
								e.printStackTrace();
							}
						}
					}
					
				}
				
			}
		}
	
		return sendCnt;
	}
	

	/**
	 * <pre>
	 * 사용자 목록 상세 조회
	 *   
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 06. 02.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public Map<String, Object> selectUserMgmtDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return userMgmtDao.selectUserMgmtDtl(param);
	}



	
	
}
