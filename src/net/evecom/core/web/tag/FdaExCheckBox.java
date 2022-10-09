/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.FdaCommonControl;
/**
 * 描述  食药业务增强checkBoxGroup
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月29日 上午7:20:55
 * copy by fjfda project package by keravon
 */
public class FdaExCheckBox extends TagSupport {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaExCheckBox.class);
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
     * 静态复选框可选值
     */
    private String checkvalues;
    /**
     * 静态复选框标签值
     */
    private String checklables;
    /**
     * 动态数据源接口
     */
    private String datainterface;
    /**
     * 动态数据源接口参数JSON
     */
    private String queryparamjson;
    /**
     * 缺省值
     */
    private String value;
    /**
     * 组件的宽度
     */
    private String width;
    /**
     * 验证表达式
     */
    private String datavalidate;
    /**
     * 禁用
     */
    private String disabled;
    /**
     * 是否纯净模式
     * true,false
     */
    private String puremode;
    /**
     * puremode为true并且前后带P
     */
    private String isstartp;

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
            Map<String,Object> paramMap = new HashMap<String,Object>();
            Field[] fields = this.getClass().getDeclaredFields();
            for(Field f:fields){
                try {
                    paramMap.put(f.getName(),f.get(this));
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                }
            }
            String htmlStr = FdaCommonControl.getExCheckBoxGroup(paramMap);
            try {
                out.print(htmlStr);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } 
        return EVAL_PAGE;
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
     * @return the onclick
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * @param onclick the onclick to set
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    /**
     * @return the checkvalues
     */
    public String getCheckvalues() {
        return checkvalues;
    }

    /**
     * @param checkvalues the checkvalues to set
     */
    public void setCheckvalues(String checkvalues) {
        this.checkvalues = checkvalues;
    }

    /**
     * @return the checklables
     */
    public String getChecklables() {
        return checklables;
    }

    /**
     * @param checklables the checklables to set
     */
    public void setChecklables(String checklables) {
        this.checklables = checklables;
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
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the datavalidate
     */
    public String getDatavalidate() {
        return datavalidate;
    }

    /**
     * @param datavalidate the datavalidate to set
     */
    public void setDatavalidate(String datavalidate) {
        this.datavalidate = datavalidate;
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

    /**
     * @return the puremode
     */
    public String getPuremode() {
        return puremode;
    }

    /**
     * @param puremode the puremode to set
     */
    public void setPuremode(String puremode) {
        this.puremode = puremode;
    }

    /**
     * @return the isstartp
     */
    public String getIsstartp() {
        return isstartp;
    }

    /**
     * @param isstartp the isstartp to set
     */
    public void setIsstartp(String isstartp) {
        this.isstartp = isstartp;
    }
}
