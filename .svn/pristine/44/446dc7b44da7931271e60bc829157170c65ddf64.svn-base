/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 增强checkBoxGroup
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月29日 上午7:20:55
 */
public class ExCheckBox extends TagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ExCheckBox.class);
    /**
     * 组件ID
     */
    private String id;
    /**
     * 组件名称
     */
    private String name;
    /**
     * 组件样式名称
     */
    private String clazz;
    /**
     * 组件onclick事件
     */
    private String onclick;
    /**
     * 组件的数据源接口
     */
    private String dataInterface;
    /**
     * 组件的数据源参数
     */
    private String dataParams;
    /**
     * 缺省值
     */
    private String value;
    /**
     * 组件的宽度
     */
    private String width;
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
    @SuppressWarnings("unchecked")
    public int doEndTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            StringBuffer sb = new StringBuffer();
            sb.append("<div class=\"eve-chcekbox\"");
            sb.append(" style=\"width:").append(width).append("\" ");
            if(StringUtils.isNotEmpty(id)){
                sb.append(" id=\"").append(id).append("\" ");
            }
            sb.append(" dataInterface=\"").append(dataInterface).append("\" ");
            sb.append(" dataParams=\"").append(dataParams).append("\" ");
            sb.append(" clazz=\"").append(clazz).append("\" ");
            sb.append(" value=\"").append(value).append("\" ");
            sb.append(" name=\"").append(name).append("\" ");
            sb.append("><ul>");
            String beanId = dataInterface.split("[.]")[0];
            String method = dataInterface.split("[.]")[1];
            Object serviceBean = AppUtil.getBean(beanId);
            Set<String> checkValues = new HashSet<String>();
            if(StringUtils.isNotEmpty(value)){
                for(String v:value.split(",")){
                    checkValues.add(v);
                }
            }
            if (serviceBean != null) {int paramNum = dataParams.split(":").length;
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
                    StringBuffer li = new StringBuffer("<li>");
                    //获取value值
                    String dataValue = (String) data.get("VALUE");
                    //获取text值
                    String text = (String) data.get("TEXT");
                    li.append("<input type=\"checkbox\" ");
                    li.append(" name=\"").append(name).append("\" ");
                    if(StringUtils.isNotEmpty(onclick)){
                        li.append(" onclick=\"").append(onclick).append("\" ");
                    }
                    if(StringUtils.isNotEmpty(clazz)){
                        li.append(" class=\"").append(clazz).append("\" ");
                    }
                    li.append(" value=\"").append(dataValue).append("\" ");
                    if(StringUtils.isNotEmpty(value)){
                        if(checkValues.contains(dataValue)){
                            li.append(" checked=\"checked\" ");
                        }
                    }
                    Iterator it = data.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                        if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                            li.append(e.getKey()).append("=\"").append(e.getValue())
                            .append("\" ");
                        }
                    }
                    li.append(">").append(text).append("</li>");
                    sb.append(li);
                }
                sb.append("</ul></div>");
            }
            out.print(sb.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        } 
        return EVAL_PAGE;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getId() {
        return id;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getName() {
        return name;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getClazz() {
        return clazz;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param clazz
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getOnclick() {
        return onclick;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param onclick
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getDataInterface() {
        return dataInterface;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param dataInterface
     */
    public void setDataInterface(String dataInterface) {
        this.dataInterface = dataInterface;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getDataParams() {
        return dataParams;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param dataParams
     */
    public void setDataParams(String dataParams) {
        this.dataParams = dataParams;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @return type
     */
    public String getValue() {
        return value;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:22:30
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:24:19
     * @return type
     */
    public String getWidth() {
        return width;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月29日 上午7:24:19
     * @param width
     */
    public void setWidth(String width) {
        this.width = width;
    }
}
