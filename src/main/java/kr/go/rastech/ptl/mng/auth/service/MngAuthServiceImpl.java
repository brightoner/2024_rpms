package kr.go.rastech.ptl.mng.auth.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.mng.auth.dao.MngAuthDao;
import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;
import kr.go.rastech.ptl.mng.menu.service.MngMenuService;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuRolVo;

/**
 *
 * <pre>
 * FileName: MngUrlServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.url.service
 *
 * mng Url 관리 ServiceImpl
 *
 * </pre>
 * @author : sbkang
 * @date   : 2023. 9. 30.
 */
@Repository
public class MngAuthServiceImpl extends BaseServiceImpl implements MngAuthService {

	@Resource
	private MngAuthDao mngAuthDao;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();

	@Resource
	private MngMenuService mngMenuService;
	
	
	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.auth.service.MngAuthService#insertUrlAuth(kr.go.ncmiklib.ptl.mng.auth.vo.MngAuthVo)
	 */
	public String insertUrlAuth(HttpServletRequest request)  throws IOException, SQLException , NullPointerException , ParseException {
		
		// 01. Request >> Map 객체로 전환
		Enumeration em = request.getParameterNames();

        String parameterName = (String)em.nextElement();

        JSONParser jsonParser = new JSONParser();
        // JSON데이터를 넣어 JSON Object 로 만들어 준다.

        JSONObject jsonObject = (JSONObject) jsonParser.parse(parameterName);
        // books의 배열을 추출
        JSONArray cdInfoArray = (JSONArray) jsonObject.get("list");
        
        String menu_id = (String) jsonObject.get("sel_menu_id");
  
        MngAuthVo mngAuthVo = null;
          
        for (int i = 0; i < cdInfoArray.size(); i++) {

           // 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
           JSONObject cdObject = (JSONObject) cdInfoArray.get(i);
           // JSON name으로 추출
           mngAuthVo = new MngAuthVo();
           
           mngAuthVo.setMenu_id(String.valueOf(cdObject.get("menu_id")));
           mngAuthVo.setAuth_gbn(String.valueOf(cdObject.get("auth_gbn")));
           mngAuthVo.setUrl(String.valueOf(cdObject.get("url")));
           mngAuthVo.setUrl_desc(String.valueOf(cdObject.get("url_desc")));
           mngAuthVo.setModify_id();
           mngAuthVo.setCreate_id(); 
           
           if(StringUtils.equals(String.valueOf(cdObject.get("save_type")), "I")){
        	   mngAuthDao.insertUrlAuth(mngAuthVo);
        	   //신규등록시 관리자 권한은 기본 등록
        	   	MngMenuRolVo mngMenuRolVo = new MngMenuRolVo();
				mngMenuRolVo.setMenu_id(String.valueOf(cdObject.get("menu_id")));
				mngMenuRolVo.setAuth_gbn(String.valueOf(cdObject.get("auth_gbn")));
				mngMenuRolVo.setRol_id("ROLE_ADMIN");
				mngMenuRolVo.setModify_id();
				mngMenuRolVo.setCreate_id();
				mngMenuService.mergeMenuRol(mngMenuRolVo);
           }else{
        	   mngAuthVo.setUrl_seq(Integer.parseInt((String) cdObject.get("url_seq")));
        	   mngAuthVo.setModify_id();
        	   mngAuthDao.updateUrlAuth(mngAuthVo);
           }
        }
		
        return menu_id;
	}


	@Override
	public List<MngAuthVo> readUrlAuth(MngAuthVo mngAuthVo) throws IOException, SQLException , NullPointerException {
		return mngAuthDao.readUrlAuth(mngAuthVo);
	}


	@Override
	public void deleteUrlAuth(MngAuthVo mngAuthVo) throws IOException, SQLException , NullPointerException {
		mngAuthDao.deleteUrlAuth(mngAuthVo);
	}


	@Override
	public List<MngAuthVo> listMenuAuth() throws IOException, SQLException , NullPointerException {

		List<MngAuthVo> mngAuthList = mngAuthDao.readMenuAuth();
		
		return mngAuthList;
	}

}
