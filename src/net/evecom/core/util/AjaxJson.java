/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.Serializable;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月7日 上午10:12:35
 */
public class AjaxJson implements Serializable {
    /**
     * 是否成功
     */
    private boolean success = true;
    /**
     * 提示信息
     */
    private String msg = "操作成功";
    /**
     * JSON字符串
     */
    private String jsonString;
    
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午10:18:33
     * @return type
     */
    public String getJsonString() {
        return jsonString;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午10:18:33
     * @param jsonString
     */
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午10:13:39
     * @return type
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午10:13:39
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午10:13:39
     * @return type
     */
    public String getMsg() {
        return msg;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午10:13:39
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
