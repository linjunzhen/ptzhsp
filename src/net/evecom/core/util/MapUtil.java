/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import net.evecom.platform.hflow.controller.MaterConfigController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述
 * @author Danto Huang
 * @created 2019年7月26日 下午5:06:32
 */
public class MapUtil {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);

    /**
     * Map key 排序
     * @param map map
     * @return map
     */
    public static Map<String,String> order(Map<String, String> map){
        HashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>( map.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }


    /**
     * 转换对象为map
     * @param object object
     * @param ignore ignore
     * @return map
     */
    public static Map<String,String> objectToMap(Object object,String... ignore){
        Map<String,String> tempMap = new LinkedHashMap<String, String>();
        for(Field f : getAllFields(object.getClass())){
            if(!f.isAccessible()){
                f.setAccessible(true);
            }
            boolean ig = false;
            if(ignore!=null&&ignore.length>0){
                for(String i : ignore){
                    if(i.equals(f.getName())){
                        ig = true;
                        break;
                    }
                }
            }
            if(ig){
                continue;
            }else{
                Object o = null;
                try {
                    o = f.get(object);
                } catch (IllegalArgumentException e) {
                    log.info(e.getMessage());
                } catch (IllegalAccessException e) {
                    log.info(e.getMessage());
                }
                tempMap.put(f.getName(), o==null?"":o.toString());
            }
        }
        return tempMap;
    }

    /**
     * 获取所有Fields,包含父类field
     * @param clazz clazz
     * @return list
     */
    private static List<Field> getAllFields(Class<?> clazz){
        if(!clazz.equals(Object.class)){
            List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
            List<Field> fields2 = getAllFields(clazz.getSuperclass());
            if(fields2!=null){
                fields.addAll(fields2);
            }
            return fields;
        }else{
            return null;
        }
    }

    /**
     * url 参数串连
     * @param map map
     * @param keyLower keyLower
     * @param valueUrlencode valueUrlencode
     * @return string
     */
    public static String mapJoinUrl(Map<String, String> map,boolean keyLower,boolean valueUrlencode){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key :map.keySet()){
            if(map.get(key)!=null&&!"".equals(map.get(key))){
                try {
                    String temp = (key.endsWith("_")&&key.length()>1)?key.substring(0,key.length()-1):key;
                    stringBuilder.append(keyLower ? temp.toLowerCase() : temp)
                            .append("=").append(valueUrlencode
                                    ? URLEncoder.encode(map.get(key), "utf-8").replace("+", "%20") : map.get(key))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    log.info(e.getMessage());
                }
            }
        }
        if(stringBuilder.length()>0){
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        return stringBuilder.toString();
    }


    /**
     * value串联
     * @param map map
     * @param keyLower keyLower
     * @param valueUrlencode valueUrlencode
     * @return string
     */
    public static String mapJoin(Map<String, String> map,boolean keyLower,boolean valueUrlencode){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key :map.keySet()){
            if(map.get(key)!=null&&!"".equals(map.get(key))){
                try {
                    String temp = (key.endsWith("_")&&key.length()>1)?key.substring(0,key.length()-1):key;
                    stringBuilder.append(valueUrlencode
                                    ? URLEncoder.encode(map.get(key), "utf-8").replace("+", "%20") : map.get(key));
                } catch (UnsupportedEncodingException e) {
                    log.info(e.getMessage());
                }
            }
        }
        if(stringBuilder.length()>0){
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        return stringBuilder.toString();
    }

    /**
     * 
     * 描述：map键转换为小写
     * 
     * @author Rider Chen
     * @created 2020年7月14日 上午9:31:26
     * @param orgMap
     * @return
     */
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();

        if (null == orgMap || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toLowerCase();
            resultMap.put(newKey, orgMap.get(key));
        }

        return resultMap;
    }

    /*public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("noncestr", "1234567");
        map.put("jsapi_ticket", "asdfgh");
        map.put("timestamp", "99999");
        map.put("url", "kkkkkkkk");
        
        Map<String, String> tmap = MapUtil.order(map);
        String str = MapUtil.mapJoinUrl(tmap,true,false);
        System.out.print(str);
    }*/
}
