/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述返回信息类
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年11月27日 下午3:14:27
 */
public class ReturnInfoUtil {

    /**
     * 描述:返回错误信息
     *
     * @author Madison You
     * @created 2020/11/27 11:12:00
     * @param
     * @return
     */
    public static Map<String, Object> returnErrMsg(String errMsg) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        returnMap.put("msg", errMsg);
        return returnMap;
    }

}
