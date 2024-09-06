/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.base.dao;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * <pre>
 * FileName: BaseDaoImpl.java
 * Package : kr.go.ncmiklib.base.dao
 *
 * 프로젝트 최상위 Data Access Object.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class BaseDaoImpl{
    // 공통 Sql Session Template.
    @Resource
    protected SqlSessionTemplate sqlSessionTemplate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
