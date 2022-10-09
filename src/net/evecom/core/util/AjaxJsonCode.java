/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月7日 上午10:12:35
 */
public class AjaxJsonCode extends AjaxJson {
    /**
     * 001:正常返回数据（默认）
     * 002：查询无数据
     * 003：参数不合法
     * 004：查询错误
     * 005:其他情况
     */
    private String code = "001";

    /**
     ** 001:正常返回数据（默认）
     * 002：查询无数据
     * 003：参数不合法
     * 004：查询错误
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * * 001:正常返回数据（默认）
     * 002：查询无数据
     * 003：参数不合法
     * 004：查询错误
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
