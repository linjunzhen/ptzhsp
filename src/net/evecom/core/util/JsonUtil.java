/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月7日 上午7:52:06
 */
public class JsonUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(JsonUtil.class);
    /**
     * 过滤类型为去除
     */
    public static final boolean EXCLUDE = true;
    /**
     * 过滤类型为包含
     */
    public static final boolean INCLUDE = false;

    /**
     * 
     * 描述 将对象输出成JSON到客户端
     * 
     * @author Flex Hu
     * @created 2015年3月29日 下午12:10:38
     * @param response
     * @param obj
     */
    public static void printJson(HttpServletResponse response, Object obj) {
        String json = JSON.toJSONString(obj);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 根据操作符过滤字段
     * 
     * @param obj
     * @param filter
     * @param operator
     * @return
     */
    public static String jsonStringFilter(Object obj, String[] filter, final boolean operator) {
        if (filter == null) {
            filter = new String[] {};
        }
        final List<String> list = new ArrayList<String>();
        for (String f : filter) {
            list.add(f);
        }
        PropertyFilter propertyfilter = new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if (list.contains(name)) {
                    if (operator) {
                        return false;
                    } else {
                        return true;
                    }

                } else {
                    if (operator) {
                        return true;
                    } else {
                        return false;
                    }

                }
            }
        };
        SerializeWriter sw = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(sw);
        serializer.getPropertyFilters().add(propertyfilter);
        serializer.write(obj);
        return sw.toString();
    }

    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject json = JSONObject.parseObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            // 如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

}
