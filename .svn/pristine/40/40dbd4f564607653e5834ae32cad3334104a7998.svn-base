/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.CommonControl;

/**
 * 描述 食药业务自定义下拉框
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月7日 下午2:39:51
 * copy by fjfda project package by keravon
 */
public class FdaEveSelect extends TagSupport {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaEveSelect.class);
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
     * 组件的onchange事件
     */
    private String onchange;
    /**
     * 组件的数据源接口
     */
    private String datainterface;
    /**
     * 组件的数据源参数
     */
    private String dataparams;
    /**
     * 缺省提示值
     */
    private String defaultemptytext;
    /**
     * 缺省值
     */
    private String value;

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
            String selectStr = CommonControl.getEveSelectString(id, name, clazz,
                    datainterface, dataparams, onchange, 
                    defaultemptytext, value,null);
            out.print(selectStr);
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
     * @return the onchange
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * @param onchange the onchange to set
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
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
     * @return the dataparams
     */
    public String getDataparams() {
        return dataparams;
    }

    /**
     * @param dataparams the dataparams to set
     */
    public void setDataparams(String dataparams) {
        this.dataparams = dataparams;
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
    
}
