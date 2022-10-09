/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月20日 下午4:05:55
 */
public class FdaCommonControl {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaCommonControl.class);
    /**
     * 获取扩展复选框字符串对象
     * @param paramMap
     * @return
     */
    public static String getExCheckBoxGroup(Map<String,Object> paramMap){
        String id = (String) paramMap.get("id");
        String puremode = (String) paramMap.get("puremode");
        String name = (String) paramMap.get("name");
        String width = (String) paramMap.get("width");
        String datainterface = (String) paramMap.get("datainterface");
        String queryparamjson = (String) paramMap.get("queryparamjson");
        String isstartp = (String) paramMap.get("isstartp");
        String value = (String) paramMap.get("value");
        String disabled = (String) paramMap.get("disabled");
        String clazz = (String) paramMap.get("clazz");
        String checkvalues = (String) paramMap.get("checkvalues");
        String checklables = (String) paramMap.get("checklables");
        String datavalidate = (String) paramMap.get("datavalidate");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        StringBuffer sb = new StringBuffer();
        getStartStr(id, puremode, name, width, datainterface, queryparamjson,
                value, clazz, sb);
        Set<String> checkValues = new HashSet<String>();
        list = getResultData(datainterface, queryparamjson, value, checkvalues,
                checklables, list, checkValues);
        for (Map<String, Object> data:list) {
            StringBuffer li = new StringBuffer("");
            if(!(StringUtils.isNotEmpty(puremode)&&puremode.equals("true"))){
                li.append("<li>");
            }else{
                if(StringUtils.isNotEmpty(isstartp)&&isstartp.equals("true")){
                    li.append("<p>");
                }
            }
            //获取值
            String checkValue = (String) data.get("VALUE");
            String checkLable = (String) data.get("TEXT");
            li.append("<label><input type=\"checkbox\" ");
            if(StringUtils.isNotEmpty(datavalidate)){
                li.append(" class=\"").append(datavalidate).append("\" ");
            }
            li.append(" value=\"").append(checkValue).append("\" ");
            li.append(" name=\"").append(name).append("\" ");
            if(checkValues.contains(checkValue)){
                li.append(" checked=\"checked\" ");
            }
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                    li.append(e.getKey()).append("=\"").append(e.getValue())
                    .append("\" ");
                }
            }
            if(StringUtils.isNotEmpty(disabled)){
            li.append(" disabled=\"").append(disabled).append("\" ");
            }
            li.append(">").append(checkLable).append("</label>");
            if(!(StringUtils.isNotEmpty(puremode)&&puremode.equals("true"))){
            li.append("</li>");
            }else{
                if(StringUtils.isNotEmpty(isstartp)&&isstartp.equals("true")){
                    li.append("</p>");
                }
            }
            sb.append(li);
        }
        if(!(StringUtils.isNotEmpty(puremode)&&puremode.equals("true"))){
            sb.append("</ul></div>");
        }
        return sb.toString();
    }

    /**
     * 描述
     * @author Faker Li
     * @created 2017年5月11日 上午9:25:32
     * @param datainterface
     * @param queryparamjson
     * @param value
     * @param checkvalues
     * @param checklables
     * @param list
     * @param checkValues
     * @return
     */
    public static List<Map<String, Object>> getResultData(String datainterface,
            String queryparamjson, String value, String checkvalues,
            String checklables, List<Map<String, Object>> list,
            Set<String> checkValues) {
        if(StringUtils.isNotEmpty(value)){
            for(String v:value.split(",")){
                checkValues.add(v);
            }
        }
        if(StringUtils.isNotEmpty(checkvalues)){
            String[] checkValuesArray = checkvalues.split(",");
            String[] checkLablesArray = checklables.split(",");
            for(int i =0;i<checkValuesArray.length;i++){
                Map<String,Object> data = new HashMap<String,Object>();
                data.put("VALUE", checkValuesArray[i]);
                data.put("TEXT", checkLablesArray[i]);
                list.add(data);
            }
        }else{
            String beanId = datainterface.split("[.]")[0];
            String method = datainterface.split("[.]")[1];
            Object serviceBean = AppUtil.getBean(beanId);
            if (serviceBean != null) {
                try {
                    Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                            new Class[] { String.class });
                    list = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                            new Object[] { queryparamjson });
                } catch (NoSuchMethodException e) {
                    log.error(e.getMessage());
                } catch (SecurityException e) {
                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return list;
    }

    /**
     * 描述
     * @author Faker Li
     * @created 2017年5月11日 上午9:20:04
     * @param id
     * @param puremode
     * @param name
     * @param width
     * @param datainterface
     * @param queryparamjson
     * @param value
     * @param clazz
     * @param sb
     */
    public static void getStartStr(String id, String puremode, String name,
            String width, String datainterface, String queryparamjson,
            String value, String clazz, StringBuffer sb) {
        if(!(StringUtils.isNotEmpty(puremode)&&puremode.equals("true"))){
            sb.append("<div class=\"eve-chcekbox\"");
            sb.append(" style=\"width:").append(width).append("\" ");
            if(StringUtils.isNotEmpty(id)){
                sb.append(" id=\"").append(id).append("\" ");
            }else{
                sb.append(" id=\"").append(name).append("_CHECKBOX\" ");
            }
            sb.append(" datainterface=\"").append(datainterface).append("\" ");
            sb.append(" queryparamjson=\"").append(queryparamjson).append("\" ");
            sb.append(" clazz=\"").append(clazz).append("\" ");
            sb.append(" value=\"").append(value).append("\" ");
            sb.append(" name=\"").append(name).append("\" ");
            sb.append("><ul>");
        }
    }
    
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
                } catch (Exception e) { log.error(e.getMessage());
                }
            }
        }
        int index = 0;
        for (Map<String, Object> data : list) {
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
            if (StringUtils.isNotEmpty(allowblank)
                    && "false".equals(allowblank)) {
                sb.append(" class=\"validate[required]\" ");
            }
            sb.append(" value=\"").append(radioValue).append("\" ");
            sb.append(" text=\"").append(radioLable).append("\" ");
            sb.append(" name=\"").append(name).append("\" ");
            if (StringUtils.isNotEmpty(value) && radioValue.equals(value)) {
                sb.append(" checked=\"checked\" ");
            } else if (StringUtils.isNotEmpty(selectfirst)
                    && selectfirst.equals("true")) {
                if (index == 0) {
                    sb.append(" checked=\"checked\" ");
                }
            }
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> e = (Map.Entry<String, Object>) it
                        .next();
                if (!e.getKey().equals("VALUE") && !e.getKey().equals("TEXT")) {
                    sb.append(e.getKey()).append("=\"").append(e.getValue())
                            .append("\" ");
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
            Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                    new Class[] { String.class });
            List<Map<String,Object>> datas = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                    new Object[] { dataParams });
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
}
