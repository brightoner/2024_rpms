/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <pre>
 * FileName: BaseObj.java
 * Package : kr.go.ncmiklib.base
 *
 * 프로젝트 최상위 Object.
 *
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
public class BaseObj {
    // 공통 Logger.
    protected transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * <pre>
     * 공통 toString() 오버라이드.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
