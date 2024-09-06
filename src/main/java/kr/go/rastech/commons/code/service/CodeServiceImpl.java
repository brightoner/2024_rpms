/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.code.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.code.dao.CodeDao;
import kr.go.rastech.commons.code.vo.CodeVo;
import kr.go.rastech.commons.login.vo.UserVo;

/**
 * <pre>
 * FileName: CodeServiceImpl.java
 * Package : kr.go.ncmiklib.commons.code.service
 *
 * 공통 코드 정보 조회 Service implements.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 5. 7.
 */
@Service
public class CodeServiceImpl extends BaseServiceImpl implements CodeService {

    @Resource
    private CodeDao codeDao;

    /**
     *
     * <pre>
     *
     *
     * </pre>
     *
     * @author :
     * @date : 2023. 7. 10.
     * @param CommonsVo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public List<CodeVo> listReport(UserVo userVo, String searchWord) throws IOException, SQLException , NullPointerException {
    	Map<String, Object> map = new HashMap<String, Object>();
    	if (userVo == null) {
    		List<String> roleList = new ArrayList<String>();
    		roleList.add("ROLE_GUEST");
    		map.put("roleList", roleList);
    	} else {
    		map.put("roleList", userVo.getRoleList());
    	}
    	map.put("searchWord", searchWord);
    	List<CodeVo> list = codeDao.selectReportList(map);
        return list;
    }



}
