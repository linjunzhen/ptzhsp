/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.hibernate.HibernateModel;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * 
 * 描述
 * 
 * @author Flex Hu
 * @version 1.1
 * 
 */
public class BeanUtil {
    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(BeanUtil.class);
    
    /**
     * 
     * 描述 拷贝MAP
     * @author Flex Hu
     * @created 2015年7月9日 上午10:30:23
     * @param dest
     * @param orig
     */
    public static void copyNotNullForMap(Map<String,Object> dest,Map<String,Object> orig){
        Iterator entries = ((Map) orig).entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String,Object> entry = (Map.Entry<String,Object>) entries.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value!=null){
                dest.put(key, value);
            }
        }
    }

    /**
     * 拷贝一个bean中的非空属性于另一个bean中
     * 
     * @param dest
     *            :目标bean
     * @param orig
     *            :源bean
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyNotNullProperties(Object dest, Object orig) {
        BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
        }

        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
                    .getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                // Need to check isReadable() for WrapDynaBean
                // (see Jira issue# BEANUTILS-61)
                if (beanUtils.getPropertyUtils().isReadable(orig, name)
                        && beanUtils.getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    try {
                        beanUtils.copyProperty(dest, name, value);
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    }
                }
            }
        } else if (orig instanceof Map) {
            Iterator entries = ((Map) orig).entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String name = (String) entry.getKey();
                if (beanUtils.getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        beanUtils.copyProperty(dest, name, entry.getValue());
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    }
                }
            }
        } else /* if (orig is a standard JavaBean) */{
            PropertyDescriptor[] origDescriptors = beanUtils.getPropertyUtils()
                    .getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (beanUtils.getPropertyUtils().isReadable(orig, name)
                        && beanUtils.getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value = beanUtils.getPropertyUtils()
                                .getSimpleProperty(orig, name);
                        if (value != null) {
                            beanUtils.copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        // Should not happen
                        log.error(e.getMessage());
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    }
                }
            }
        }

    }

    /**
     * 将值进行转换
     * 
     * @author Flex Hu
     * @param convertUtil
     * @param value
     * @param type
     * @return
     */
    public static Object convertValue(ConvertUtilsBean convertUtil,
            Object value, Class type) {
        Converter converter = convertUtil.lookup(type);
        if (converter == null)
            return value;
        Object newValue = null;
        if ((value instanceof String))
            newValue = converter.convert(type, (String) value);
        else if ((value instanceof String[]))
            newValue = converter.convert(type, ((String[]) value)[0]);
        else {
            newValue = converter.convert(type, value);
        }
        return newValue;
    }

    /**
     * 获取requst请求参数的所有值
     * 
     * @author Flex Hu
     * @param request
     * @return
     */
    public static Map<String, Object> getMapFromRequest(
            HttpServletRequest request) {
        Map reqMap = request.getParameterMap();
        HashMap<String, Object> datas = new HashMap<String, Object>();
        Iterator it = reqMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String[] val = (String[]) entry.getValue();
            if (val.length == 1) {
                if (StringUtils.isNotEmpty(val[0])) {
                    datas.put(key, val[0]);
                }else{
                    datas.put(key, "");
                }
            } else {
                if (val != null) {
                    datas.put(key, val);
                }else{
                    datas.put(key, "");
                }
            }
        }
        return datas;
    }

    /**
     * 转换字段的值
     * 
     * @author Flex Hu
     * @param fieldType
     *            :字段类型
     * @param fieldValue
     *            :字段值
     * @return
     */
    public static Object convertFieldValue(String fieldType, String fieldValue,
            String operateType) {
        if (StringUtils.isEmpty(fieldValue))
            return null;
        Object value = null;
        try {
            if ("S".equals(fieldType)) {// 大部的查询都是该类型，所以放至在头部
                value = fieldValue;
            } else if ("D".equals(fieldType)) {
                SimpleDateFormat sdf = null;
                if (fieldValue.contains("T00:00:00")) {
                    // 获取操作类型
                    // String operateType= fieldInfo[2];
                    if (operateType.equals("<=")) {
                        fieldValue = fieldValue.replace("T00:00:00",
                                " 23:59:59");
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        fieldValue = DateTimeUtil.getStrOfDate(sdf
                                .parse(fieldValue), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        fieldValue = fieldValue.replace("T", " ");
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        fieldValue = DateTimeUtil.getStrOfDate(sdf
                                .parse(fieldValue), "yyyy-MM-dd");
                    }
                } else {
                    if (operateType.equals("<=")) {
                        fieldValue += " 23:59:59";
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        fieldValue = DateTimeUtil.getStrOfDate(sdf
                                .parse(fieldValue), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        // fieldValue = fieldValue.replace("T", " ");
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        fieldValue = DateTimeUtil.getStrOfDate(sdf
                                .parse(fieldValue), "yyyy-MM-dd");
                    }
                }
                value = fieldValue;
            } else if ("L".equals(fieldType)) {
                value = Long.valueOf(fieldValue);
            } else if ("N".equals(fieldType)) {
                value = Integer.valueOf(fieldValue);
            } else if ("BD".equals(fieldType)) {
                value = new BigDecimal(fieldValue);
            } else if ("FT".equals(fieldType)) {
                value = new Float(fieldValue);
            } else if ("SN".equals(fieldType)) {
                value = Short.valueOf(fieldValue);
            } else if ("DB".equals(fieldType)) {
                value = new Double(fieldValue);
            } else {
                value = fieldValue;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return value;
    }

    /**
     * 判断一个对象是否为空
     * 
     * @author Flex Hu
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null)
            return true;
        if ((o instanceof String)) {
            if (((String) o).trim().length() == 0) {
                return true;
            }
        } else if ((o instanceof Collection)) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if ((o instanceof Map)) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else if ((o instanceof Long)) {
            if ((Long) o == null) {
                return true;
            }
        } else if ((o instanceof Short)) {
            if ((Short) o == null) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 判断一个对象不为空
     * 
     * @author Flex Hu
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 获取某个类的绝对路径
     * 
     * @author Flex Hu
     * @param clazz
     * @return
     */
    public static String getClassPath(Class clazz) {
        String className = clazz.getName();
        String classNamePath = className.replace(".", "/") + ".class";
        URL is = clazz.getClassLoader().getResource(classNamePath);
        String path = is.getFile();
        path = StringUtils.replace(path, "%20", " ");
        return StringUtils.removeStart(path, "/");
    }

    /**
     * 获取某个类的绝对路径文件夹路径
     * 
     * @author Flex Hu
     * @param clazz
     * @return
     */
    public static String getClassFoldPath(Class clazz) {
        String path = BeanUtil.getClassPath(clazz);
        path = path.substring(0, path.lastIndexOf("/"));
        return path;
    }

    /**
     * 获取某个实体类的Hibernate配置文件路径
     * 
     * @author Flex Hu
     * @param clazz
     * @return
     */
    public static String getHibernateXmlPath(Class clazz) {
        String path = getClassPath(clazz);
        String xmlpath = path.substring(0, path.lastIndexOf(".") + 1);
        xmlpath = xmlpath + "hbm.xml";
        return xmlpath;
    }

    /**
     * 获取某个实体类的某个字段名称的值
     * 
     * @author Flex Hu
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassFieldValue(Object obj, String fieldName) {
        if (invokeMethod(obj, fieldName, null) != null) {
            return invokeMethod(obj, fieldName, null);
        } else {
            return null;
        }
    }

    /**
     * 利用反射调用方法
     * 
     * @author Flex Hu
     * @param owner
     * @param fieldName
     * @param args
     * @return
     */
    public static Object invokeMethod(Object owner, String fieldName,
            Object[] args) {
        Class<? extends Object> ownerClass = owner.getClass();
        // fieldName -> FieldName
        String methodName = fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
        try {
            Method method = ownerClass.getMethod("get" + methodName);
            return method.invoke(owner);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    
    /**
     * 根据CLASS获取hibernate模型信息
     * 
     * @author Flex Hu
     * @param clazz
     * @return
     */
    public static HibernateModel getHibernateModel(Class clazz, Object obj) {
        String xmlPath = getHibernateXmlPath(clazz);
        Document document = XmlUtil.load(new File(xmlPath));
        Element root = document.getRootElement();
        Element primaryEle = (Element) root.selectSingleNode("//id");
        HibernateModel model = new HibernateModel();
        model.setPrimaryKeyName(primaryEle.attributeValue("name"));
        model.setEntityName(clazz.getSimpleName());
        List<Node> list = root.selectNodes("//property");
        model.setPrimaryKeyValue(getClassFieldValue(obj,
                model.getPrimaryKeyName()).toString());
        Map<String, Object> properties = new HashMap<String, Object>();
        for (Node node : list) {
            Element ele = (Element) node;
            String fieldName = ele.attributeValue("name");
            Object fieldValue = getClassFieldValue(obj, fieldName);
            if (fieldValue != null
                    && !fieldName.equals(model.getPrimaryKeyName())) {
                properties.put(fieldName, fieldValue);
            }
        }
        model.setProperties(properties);
        return model;
    }
    
}
