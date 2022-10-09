/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.model;

import java.io.Serializable;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年10月5日 上午8:33:02
 */
public class ExcelQueryParams implements Serializable {
    /**
     * 参数名称
     */
    private String queryName;
    /**
     * 参数值
     */
    private String queryValue;
    
    /**
     * 描述
     * @author Flex Hu
     * @created 2014年10月5日 上午8:33:44
     * @param queryName
     * @param queryValue
     */
    public ExcelQueryParams(String queryName, String queryValue) {
        super();
        this.queryName = queryName;
        this.queryValue = queryValue;
    }
    
    /**
     * @author Flex Hu
     * @created 2014年10月5日 上午8:33:30
     * @return type
     */
    public String getQueryName() {
        return queryName;
    }
    /**
     * @author Flex Hu
     * @created 2014年10月5日 上午8:33:30
     * @param queryName
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
    /**
     * @author Flex Hu
     * @created 2014年10月5日 上午8:33:30
     * @return type
     */
    public String getQueryValue() {
        return queryValue;
    }
    /**
     * @author Flex Hu
     * @created 2014年10月5日 上午8:33:30
     * @param queryValue
     */
    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }
}
