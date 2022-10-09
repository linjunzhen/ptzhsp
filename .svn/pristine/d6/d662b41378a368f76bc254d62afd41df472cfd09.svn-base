/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * JSON 工具类
 * 
 * @author Luffy Cai
 *
 */
public class JsonUtil {

    /**
     * Json序列化
     * 
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Json 反序列化
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> listFromJson(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
