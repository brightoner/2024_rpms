/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.code.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kr.go.rastech.commons.code.vo.CodeVo;
import kr.go.rastech.commons.login.vo.UserVo;

/**
 * <pre>
 * FileName: CodeService.java
 * Package : kr.go.ncmiklib.commons.code.service
 *
 * 공통 코드 정보 조회 Service interface.
 *
 * </pre>
 * @author :
 * @date   : 2023. . .
 */
public interface CodeService {

    /**
     *
     * <pre>
     *
     *
     * </pre>
     *
     * @author :
     * @date : 2023. . .
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public List<CodeVo> listReport(UserVo userVo, String param) throws IOException, SQLException , NullPointerException;
    
    
   


}
