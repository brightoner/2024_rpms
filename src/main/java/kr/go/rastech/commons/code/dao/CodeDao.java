/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.code.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.commons.code.vo.CodeVo;

/**
 * <pre>
 * FileName: CodeDao.java
 * Package : kr.go.ncmiklib.commons.code.dao
 *
 * 공통 코드 정보 조회 DAO interface.
 *
 * </pre>
 * @author :
 * @date   : 2023. 7. 10.
 */
@Repository
public interface CodeDao {
    /**   
     *
     * <pre>
     * 상위 코드를 파라미터로 코드 목록을 조회한다.
     *
     * </pre>
     * @author :
     * @date   : 2023. 7. 10.
     * @param
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
//	@Cacheable(cacheName="headerSearchKeyword")
    public List<CodeVo> selectReportList(Map<String, Object> map) throws IOException, SQLException , NullPointerException;
    
    
  
    
}
