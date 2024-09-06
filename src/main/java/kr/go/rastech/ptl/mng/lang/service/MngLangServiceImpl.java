package kr.go.rastech.ptl.mng.lang.service;

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
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.mng.auth.dao.MngAuthDao;
import kr.go.rastech.ptl.mng.auth.vo.MngAuthVo;
import kr.go.rastech.ptl.mng.lang.dao.MngLangDao;
import kr.go.rastech.ptl.mng.lang.vo.MngLangVo;
import kr.go.rastech.ptl.mng.menu.service.MngMenuService;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuRolVo;

/**
 *
 * <pre>
 * FileName: MngLangServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.lang.service
 *
 * mng Url 관리 ServiceImpl
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 26.
 */
@Service
public class MngLangServiceImpl extends BaseServiceImpl implements MngLangService {

	@Resource
	private MngLangDao mngLangDao;
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
  
        MngLangVo mngLangVo = null;
          
        for (int i = 0; i < cdInfoArray.size(); i++) {

           // 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
           JSONObject cdObject = (JSONObject) cdInfoArray.get(i);
           // JSON name으로 추출
           mngLangVo = new MngLangVo();
           
           mngLangVo.setSeq(String.valueOf(cdObject.get("seq")));
           mngLangVo.setPage_id(String.valueOf(cdObject.get("page_id")));
           mngLangVo.setTag_gbn(String.valueOf(cdObject.get("tag_gbn")));
           mngLangVo.setTag(String.valueOf(cdObject.get("tag")));
           mngLangVo.setTag_val_ko(String.valueOf(cdObject.get("tag_val_ko")));
           mngLangVo.setTag_val_eng(String.valueOf(cdObject.get("tag_val_eng")));

           if(StringUtils.equals(String.valueOf(cdObject.get("save_type")), "I")){
        	   mngLangDao.insertLang(mngLangVo);
           }else{
        	   mngLangDao.updateLang(mngLangVo);
           }
        }
		
        return menu_id;
	}

	/* (non-Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.lang.service.MngLangService#deleteLang(kr.go.ncmiklib.ptl.mng.lang.vo.MngLangVo)
	 */
	@Override
	public void deleteLang(MngLangVo mngLangVo) throws IOException, SQLException , NullPointerException {
		mngLangDao.deleteLang(mngLangVo);
	}

	/* (non-Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.lang.service.MngLangService#selectLang(kr.go.ncmiklib.ptl.mng.lang.vo.MngLangVo)
	 */
	@Override
	public List<MngLangVo> selectLang(MngLangVo mngLangVo) throws IOException, SQLException, NullPointerException {
		return mngLangDao.selectLang(mngLangVo);
	}


}
