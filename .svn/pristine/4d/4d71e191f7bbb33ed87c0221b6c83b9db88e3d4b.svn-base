/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSON;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.hibernate.collection.PersistentBag;
import org.hibernate.proxy.map.MapProxy;
import flexjson.JSONSerializer;
/**
 * 
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年3月27日 下午5:15:08
 */
public class JsonUtils{
    /**
     * 深度转换OBJ对象成JSON字符串
     * @author Flex Hu
     * @param obj
     * @param exclude
     * @return
     */
    public static String deepSerializeObjToJson(Object obj,String[] exclude){
        JSONSerializer jsonSerializer = new JSONSerializer();
        List<String> excludes = new ArrayList<String>();
        excludes.add("*.class");
        if(exclude!=null&&exclude.length>0){
            for(String ex:exclude){
                excludes.add(ex);
            }
        }
        return jsonSerializer.exclude(excludes.toArray(new String[0])).deepSerialize(obj);
    }
    /**
     * 将对象转换成JSON数据格式
     * @param obj:对象
     * @param exclude:不需要转换的字段
     * @return
     */
    public static String convertObjToJson(Object obj,String[] exclude){
        JSONSerializer jsonSerializer = new JSONSerializer();
        List<String> excludes = new ArrayList<String>();
        excludes.add("*.class");
        if(exclude!=null&&exclude.length>0){
            for(String ex:exclude){
                excludes.add(ex);
            }
        }
        return jsonSerializer.exclude(excludes.toArray(new String[0])).serialize(obj);
    }
    /**
     * 将对象转换成JSON数据格式
     * @author Flex Hu
     * @param obj:被转换成JSON的对象
     * @param excludeOrInclude:排除或者包含
     * @return
     */
    public static String convertObjToJson(Object obj,String[] excludeOrInclude,boolean isExclude){
        JSONSerializer jsonSerializer = new JSONSerializer();
        List<String> params = new ArrayList<String>();
        if(isExclude){
            params.add("*.class");
        }
        if(excludeOrInclude!=null&&excludeOrInclude.length>0){
            for(String param:excludeOrInclude){
                params.add(param);
            }
        }
        if(isExclude){
            return jsonSerializer.exclude(params.toArray(new String[0])).serialize(obj);
        }else{
            return jsonSerializer.include(params.toArray(new String[0]))
                    .exclude(new String[]{"*.class","*"}).serialize(obj);
        }
        
    }
    
    
    /**
     * java对像转成json
     * 
     * @param source
     *            要转换的java对象
     * @param isfilter
     *            是否为过滤状态。true:滤掉.fale:不滤掉
     *            要转换的java对象中的字符串数组
     * @param datePattern
     *            java对象中日期字段的格式化格式
     * @return
     */
    public static JSON toJson(Object source, final boolean isfilter,
            final String[] propertys, String datePattern) {
        JsonConfig jsonConfig = getConfigJson(datePattern);
        if (propertys != null && propertys.length > 0) {
            final List<String> propertyList = Arrays.asList(propertys);
            // a b c d e
            jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
                public boolean apply(Object source, String name, Object value) {
                    // 返回 true, 表示这个属性将被过滤掉
                    if (isfilter) {// 1 过滤a isfilter=true propertyList=[a]
                        return propertyList.contains(name);
                    } else {// 2 不过滤a isfilter=false propertyList=[a]
                        return !propertyList.contains(name);
                    }
                }

            });
        }
        JSON json = net.sf.json.JSONSerializer.toJSON(source, jsonConfig);
        return json;
    }
    
    /**
     * 配置json-lib需要的excludes和datePattern.
     * 
     *            不需要转换的属性数组
     * @param datePattern
     *            yyyy-MM-dd HH:mm:ss 日期转换模式
     * @return JsonConfig 根据excludes和dataPattern生成的jsonConfig，用于write
     */
    public static JsonConfig getConfigJson(final String datePattern) {
        final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"hibernateLazyInitializer","handler"});
        jsonConfig.setIgnoreDefaultExcludes(false);// 默认为false，即过滤默认的key 转换Map对象时，要设为true 要不key会被过滤掉
      //循环检测策略 这里指明如果含有自包含的时候怎么外理，如树形
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonValueProcessor() {
                    public Object processArrayValue(Object value,
                            JsonConfig arg1) {
                        if (value!=null) {
                            return new SimpleDateFormat(
                                    datePattern == null ? defaultDatePattern
                                            : datePattern).format(value);
                        }
                        return null;
                    }

                    public Object processObjectValue(String key, Object value,
                            JsonConfig arg2) {
                        if (value!=null) {
                            return new SimpleDateFormat(
                                    datePattern == null ? defaultDatePattern
                                            : datePattern).format(value);
                        }
                        return null;
                    }

                });
        jsonConfig.registerDefaultValueProcessor(Long.class,
                new MyDefaultValueProcessor());//无值时设为null 否则会默认会被设为0
        jsonConfig.registerDefaultValueProcessor(Integer.class,
                new MyDefaultValueProcessor());//无值时设为null 否则会默认会被设为0
        jsonConfig.registerDefaultValueProcessor(Double.class,
                new MyDefaultValueProcessor());//无值时设为null 否则会默认会被设为0

        return jsonConfig;
    }
    
    /**  * 默认值为null时，返回null 否则默认会被设为0
     */
    public static class MyDefaultValueProcessor implements DefaultValueProcessor {
        public Object getDefaultValue(Class type) {
            return null;
        }
    }

}
