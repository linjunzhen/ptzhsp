/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.AppUtil;
/**
 * 描述  食药业务
 * @author Flex Hu
 * @version 1.0
 * @created 2016年5月23日 下午4:30:15
 * copy by fjfda project package by keravon
 */
public class FdaExSelect extends TagSupport {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaExSelect.class);
    /**
     * 组件ID
     */
    private String id;
    /**
     * 组件命名
     */
    private String name;
    /**
     * 组件值
     */
    private String value;
    /**
     * 静态值
     */
    private String staticvalues;
    /**
     * 静态标签值
     */
    private String staticlables;
    /**
     * 动态数据源接口
     */
    private String datainterface;
    /**
     * 动态数据源接口参数JSON
     */
    private String queryparamjson;
    /**
     * 缺省选择提示,例如(请选择性别)
     */
    private String defaultemptytext;
    /**
     * 是否允许为空
     * true,false
     */
    private String allowblank;
    /**
     * 样式
     */
    private String clazz;
    /**
     * style样式
     */
    private String style;
    /**
     * 禁用
     */
    private String disabled;

    /**
     * 方法doStartTag
     * 
     * @return 返回值int
     */
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }
    
    /**
     * 方法doEndTag
     * 
     * @return 返回值int
     */
    public int doEndTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            StringBuffer sb = new StringBuffer();
            if(StringUtils.isNotEmpty(this.staticvalues)){
                String[] radioValuesArray = staticvalues.split(",");
                String[] radioLablesArray = staticlables.split(",");
                for(int i =0;i<radioValuesArray.length;i++){
                    Map<String,Object> data = new HashMap<String,Object>();
                    data.put("VALUE", radioValuesArray[i]);
                    data.put("TEXT", radioLablesArray[i]);
                    list.add(data);
                }
            }else{
                String beanId = datainterface.split("[.]")[0];
                String method = datainterface.split("[.]")[1];
                Object serviceBean = AppUtil.getBean(beanId);
                if (serviceBean != null) {
                    Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                            new Class[] { String.class });
                    list = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                            new Object[] { queryparamjson });
                }
            }
            sb.append("<select name=\"").append(name).append("\" ");
            if(StringUtils.isNotEmpty(id)){
                sb.append(" id=\"").append(id).append("\" ");
            }
            if(StringUtils.isNotEmpty(style)){
                sb.append(" style=\"").append(style).append("\" ");
            }
            if(StringUtils.isNotEmpty(disabled)){
                sb.append(" disabled=\"").append(disabled).append("\" ");
            }
            if(StringUtils.isNotEmpty(clazz)){
                sb.append(" class=\"").append(clazz);
            }else{
                sb.append(" class=\"eve-input");
            }
            if(StringUtils.isNotEmpty(allowblank)&&allowblank.equals("false")){
                sb.append(" validate[required]\" ");
            }else{
                sb.append(" \"");
            }
            sb.append(" ");
            if(StringUtils.isNotEmpty(defaultemptytext)){
                sb.append("defaultemptytext=\"").append(defaultemptytext).append("\" ");
            }
            if(StringUtils.isNotEmpty(datainterface)){
                sb.append("datainterface=\"").append(datainterface).append("\" ");
            }
            if(StringUtils.isNotEmpty(queryparamjson)){
                sb.append("queryparamjson=\"").append(queryparamjson).append("\" ");
            }
            sb.append(">");
            //加入面向单选框的缺省提示选择值
            if(StringUtils.isNotEmpty(defaultemptytext)){
                sb.append("<option value=\"\">").append(defaultemptytext).append("</option>");
            }
            sb.append(this.getSelectContent(list, value));
            sb.append("</select>");
            out.print(sb.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
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
        return EVAL_PAGE;
    }
    
    /**
     * 
     * 描述 获取下拉框内容字符串
     * @created 2016年4月8日 上午11:43:07
     * @param list
     * @param value
     * @return
     */
    public static String getSelectContent(List<Map<String, Object>> list, String value){
        StringBuffer sb = new StringBuffer("");
        //定义缺省值集合
        List<String> values = new ArrayList<String>();
        if(StringUtils.isNotEmpty(value)){
            values =  Arrays.asList(value.split(","));   
        }
        for (Map<String, Object> data:list) {
            //获取值
            String optionValue = (String) data.get("VALUE");
            String optionLable = (String) data.get("TEXT");
            sb.append("<option value=\"").append(optionValue).append("\" ");
            if(values.contains(optionValue)){
                sb.append(" selected=\"selected\" ");
            }
            sb.append(" text=\"").append(optionLable).append("\" ");
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                    sb.append(e.getKey()).append("=\"").append(e.getValue())
                    .append("\" ");
                }
            }
            sb.append(">").append(optionLable).append("</option>");
        }
        return sb.toString();
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the staticvalues
     */
    public String getStaticvalues() {
        return staticvalues;
    }

    /**
     * @param staticvalues the staticvalues to set
     */
    public void setStaticvalues(String staticvalues) {
        this.staticvalues = staticvalues;
    }

    /**
     * @return the staticlables
     */
    public String getStaticlables() {
        return staticlables;
    }

    /**
     * @param staticlables the staticlables to set
     */
    public void setStaticlables(String staticlables) {
        this.staticlables = staticlables;
    }

    /**
     * @return the datainterface
     */
    public String getDatainterface() {
        return datainterface;
    }

    /**
     * @param datainterface the datainterface to set
     */
    public void setDatainterface(String datainterface) {
        this.datainterface = datainterface;
    }

    /**
     * @return the queryparamjson
     */
    public String getQueryparamjson() {
        return queryparamjson;
    }

    /**
     * @param queryparamjson the queryparamjson to set
     */
    public void setQueryparamjson(String queryparamjson) {
        this.queryparamjson = queryparamjson;
    }

    /**
     * @return the defaultemptytext
     */
    public String getDefaultemptytext() {
        return defaultemptytext;
    }

    /**
     * @param defaultemptytext the defaultemptytext to set
     */
    public void setDefaultemptytext(String defaultemptytext) {
        this.defaultemptytext = defaultemptytext;
    }

    /**
     * @return the allowblank
     */
    public String getAllowblank() {
        return allowblank;
    }

    /**
     * @param allowblank the allowblank to set
     */
    public void setAllowblank(String allowblank) {
        this.allowblank = allowblank;
    }

    /**
     * @return the clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the disabled
     */
    public String getDisabled() {
        return disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }


}
