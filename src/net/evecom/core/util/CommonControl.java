/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月20日 下午4:05:55
 */
public class CommonControl {
    /**
     * 获取扩展单选框组字符串对象
     * @param paramMap
     * @return
     */
    public static String getExRadioGroup(Map<String, Object> paramMap) {
        String puremode = (String) paramMap.get("puremode");
        String name = (String) paramMap.get("name");
        String width = (String) paramMap.get("width");
        String datainterface = (String) paramMap.get("datainterface");
        String allowblank = (String) paramMap.get("allowblank");
        String radiovalues = (String) paramMap.get("radiovalues");
        String radiolables = (String) paramMap.get("radiolables");
        String queryparamjson = (String) paramMap.get("queryparamjson");
        String isstartp = (String) paramMap.get("isstartp");
        String value = (String) paramMap.get("value");
        String selectfirst = (String) paramMap.get("selectfirst");
        String disabled = (String) paramMap.get("disabled");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        StringBuffer sb = new StringBuffer();
        if (!(StringUtils.isNotEmpty(puremode) && puremode.equals("true"))) {
            sb.append("<div class=\"eve-chcekbox\" ");
            sb.append(" id=\"").append(name + "_RADIO").append("\" ");
            sb.append(" style=\"width:").append(width).append("px;\" ");
            sb.append(" datainterface=\"").append(datainterface).append("\" ");
            sb.append(" allowblank=\"").append(allowblank).append("\" ");
            sb.append("><ul>");
        }
        if (StringUtils.isNotEmpty(radiovalues)) {
            String[] radioValuesArray = radiovalues.split(",");
            String[] radioLablesArray = radiolables.split(",");
            for (int i = 0; i < radioValuesArray.length; i++) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("VALUE", radioValuesArray[i]);
                data.put("TEXT", radioLablesArray[i]);
                list.add(data);
            }
        } else {
            String beanId = datainterface.split("[.]")[0];
            String method = datainterface.split("[.]")[1];
            Object serviceBean = AppUtil.getBean(beanId);
            if (serviceBean != null) {
                try {
                    Method invokeMethod = serviceBean.getClass().getDeclaredMethod(
                            method, new Class[] { String.class });
                    list = (List<Map<String, Object>>) invokeMethod.invoke(
                            serviceBean, new Object[] { queryparamjson });
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
        int index = 0;
        for (Map<String, Object> data : list) {
            // 获取值
            String radioValue = (String) data.get("VALUE");
            String radioLable = (String) data.get("TEXT");
            if (!(StringUtils.isNotEmpty(puremode) && puremode.equals("true"))) {
                sb.append("<li>");
            } else {
                if (StringUtils.isNotEmpty(isstartp) && isstartp.equals("true")) {
                    sb.append("<p>");
                }
            }
            sb.append("<label><input type=\"radio\" ");
            if (StringUtils.isNotEmpty(allowblank)&& "false".equals(allowblank)) {
                sb.append(" class=\"validate[required]\" ");
            }
            sb.append(" value=\"").append(radioValue).append("\" ");
            sb.append(" text=\"").append(radioLable).append("\" ");
            sb.append(" name=\"").append(name).append("\" ");
            if (StringUtils.isNotEmpty(value) && radioValue.equals(value)) {
                sb.append(" checked=\"checked\" ");
            } else if (StringUtils.isNotEmpty(selectfirst)&& selectfirst.equals("true")) {
                if (index == 0) {
                    sb.append(" checked=\"checked\" ");
                }
            }
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                if (!e.getKey().equals("VALUE") && !e.getKey().equals("TEXT")) {
                    sb.append(e.getKey()).append("=\"").append(e.getValue()).append("\" ");
                }
            }
            if (StringUtils.isNotEmpty(disabled)) {
                sb.append(" disabled=\"").append(disabled).append("\" ");
            }
            sb.append(">").append(radioLable).append("</label>");
            if (!(StringUtils.isNotEmpty(puremode) && puremode.equals("true"))) {
                sb.append("</li>");
            } else {
                if (StringUtils.isNotEmpty(isstartp) && isstartp.equals("true")) {
                    sb.append("</p>");
                }
            }
            index++;
        }
        if (!(StringUtils.isNotEmpty(puremode) && puremode.equals("true"))) {
            sb.append("</ul></div>");
        }
        return sb.toString();
    }
    
    /**
     * 
     * 描述 获取自定义下拉框的字符串
     * @author Flex Hu
     * @created 2016年1月20日 下午4:07:14
     * @param id
     * @param name
     * @param clazz
     * @param dataInterface
     * @param dataParams
     * @param onchange
     * @param defaultEmptyText
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public static String getEveSelectString(String id, String name, String clazz, String dataInterface,
            String dataParams, String onchange, String defaultEmptyText, String value, String required)
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        StringBuffer sb = new StringBuffer();
        sb.append("<select ");
        if(StringUtils.isNotEmpty(id)){
            sb.append(" id=\"").append(id).append("\" ");
        }
        if(StringUtils.isNotEmpty(name)){
            sb.append(" name=\"").append(name).append("\" ");
        }
        if(StringUtils.isNotEmpty(clazz)){
            sb.append(" class=\"").append(clazz).append("\" ");
        }
        if(StringUtils.isNotEmpty(required)){
            sb.append(" required=\"").append(required).append("\" ");
        }
        sb.append(" dataInterface=\"").append(dataInterface).append("\" ");
        sb.append(" dataParams=\"").append(dataParams).append("\" ");
        sb.append(" defaultEmptyText=\"").append(defaultEmptyText).append("\" ");
        
        if(StringUtils.isNotEmpty(onchange)){
            sb.append(" onchange=\"").append(onchange).append("\" ");
        }
        sb.append(">");
        if(StringUtils.isNotEmpty(defaultEmptyText)){
            sb.append("<option value=\"\" >").append(defaultEmptyText).append("</option>");
        }
        String beanId = dataInterface.split("[.]")[0];
        String method = dataInterface.split("[.]")[1];
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean != null) {
            int paramNum = dataParams.split(":").length;
            Method invokeMethod = null;
            if(paramNum==1){
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { String.class });
            }else if(paramNum==2){
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { String.class,String.class });
            }
            List<Map<String,Object>> datas = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                    dataParams.split(":"));
            for(Map<String,Object> data:datas){
                StringBuffer option = new StringBuffer("<option");
                //获取value值
                String dataValue = (String) data.get("VALUE");
                //获取text值
                String text = (String) data.get("TEXT");
                option.append(" value=\"").append(dataValue).append("\" ");
                if(StringUtils.isNotEmpty(value)&&value.equals(dataValue)){
                    option.append(" selected=\"selected\" ");
                }
                Iterator it = data.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                    if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                        option.append(e.getKey()).append("=\"").append(e.getValue())
                        .append("\" ");
                    }
                }
                option.append(">").append(text).append("</option>");
                sb.append(option);
            }
            sb.append("</select>");
        }
        return sb.toString();
    }
    /**
     *
     * 描述 获取自定义下拉框的字符串
     * @author Flex Hu
     * @created 2019年3月20日 下午4:07:14
     * @param id
     * @param name
     * @param clazz
     * @param dataInterface
     * @param dataParams
     * @param onchange
     * @param defaultEmptyText
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String getEveSelectStringWithDefaultValue(String id, String name, String clazz, String dataInterface,
                                            String dataParams, String onchange, String defaultEmptyText, String value,
                                                            String required,String defaultEmptyValue)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        StringBuffer sb = new StringBuffer();
        sb.append("<select ");
        if(StringUtils.isNotEmpty(id)){
            sb.append(" id=\"").append(id).append("\" ");
        }
        if(StringUtils.isNotEmpty(name)){
            sb.append(" name=\"").append(name).append("\" ");
        }
        if(StringUtils.isNotEmpty(clazz)){
            sb.append(" class=\"").append(clazz).append("\" ").append("style=\"width:186px;\" ");
        }
        if(StringUtils.isNotEmpty(defaultEmptyValue)){
            sb.append(" defaultEmptyValue=\"").append(defaultEmptyValue).append("\" ");
        }
        if(StringUtils.isNotEmpty(required)){
            sb.append(" required=\"").append(required).append("\" ");
        }
        sb.append(" dataInterface=\"").append(dataInterface).append("\" ");
        sb.append(" dataParams=\"").append(dataParams).append("\" ");
        sb.append(" defaultEmptyText=\"").append(defaultEmptyText).append("\" ");

        if(StringUtils.isNotEmpty(onchange)){
            sb.append(" onchange=\"").append(onchange).append("\" ");
        }
        sb.append(">");
        if(StringUtils.isNotEmpty(defaultEmptyText)){
            sb.append("<option value=\"\" >").append(defaultEmptyText).append("</option>");
        }
        String beanId = dataInterface.split("[.]")[0];
        String method = dataInterface.split("[.]")[1];
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean != null) {
            int paramNum = dataParams.split(":").length;
            Method invokeMethod = null;
            if(paramNum==1){
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { String.class });
            }else if(paramNum==2){
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { String.class,String.class });
            }
            List<Map<String,Object>> datas = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                    dataParams.split(":"));
            for(Map<String,Object> data:datas){
                StringBuffer option = new StringBuffer("<option");
                //获取value值
                String dataValue = (String) data.get("VALUE");
                //获取text值
                String text = (String) data.get("TEXT");
                option.append(" value=\"").append(dataValue).append("\" ");
                if(StringUtils.isNotEmpty(value)&&value.equals(dataValue)){
                    option.append(" selected=\"selected\" ");
                }else if(StringUtils.isEmpty(value)&&StringUtils.isNotEmpty(defaultEmptyValue)
                        &&defaultEmptyValue.equals(dataValue)){
                    option.append(" selected=\"selected\" ");
                }
                Iterator it = data.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                    if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                        option.append(e.getKey()).append("=\"").append(e.getValue())
                                .append("\" ");
                    }
                }
                option.append(">").append(text).append("</option>");
                sb.append(option);
            }
            sb.append("</select>");
        }
        return sb.toString();
    }
}
