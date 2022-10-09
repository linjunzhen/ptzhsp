/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
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

import net.evecom.core.util.CommonControl;
/**
 * 描述  食药业务
 * @author Flex Hu
 * @version 1.0
 * @created 2016年5月23日 下午3:57:15
 * copy by fjfda project package by keravon
 */
public class FdaExRadioGroup extends TagSupport {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaExRadioGroup.class);
    /**
     * 单选框命名
     */
    private String name;
    /**
     * 单选框值
     */
    private String value;
    /**
     * 宽度
     */
    private String width;
    /**
     * 静态单选框可选值
     */
    private String radiovalues;
    /**
     * 静态单选框标签值
     */
    private String radiolables;
    /**
     * 动态数据源接口
     */
    private String datainterface;
    /**
     * 动态数据源接口参数JSON
     */
    private String queryparamjson;
    /**
     * 是否允许为空
     * true,false
     */
    private String allowblank;
    /**
     * 是否纯净模式
     * true,false
     */
    private String puremode;
    /**
     * 禁用
     */
    private String disabled;
    /**
     * puremode为true并且前后带P
     */
    private String isstartp;
    /**
     * 是否选中第一个单选框:true,false
     */
    private String selectfirst;

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
        String htmlStr = CommonControl.getExRadioGroup(paramMap);
        try {
            out.print(htmlStr);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return EVAL_PAGE;
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
     * @return the radiovalues
     */
    public String getRadiovalues() {
        return radiovalues;
    }

    /**
     * @param radiovalues the radiovalues to set
     */
    public void setRadiovalues(String radiovalues) {
        this.radiovalues = radiovalues;
    }

    /**
     * @return the radiolables
     */
    public String getRadiolables() {
        return radiolables;
    }

    /**
     * @param radiolables the radiolables to set
     */
    public void setRadiolables(String radiolables) {
        this.radiolables = radiolables;
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
    
    /**
     * @return the selectfirst
     */
    public String getSelectfirst() {
        return selectfirst;
    }

    /**
     * @param selectfirst the selectfirst to set
     */
    public void setSelectfirst(String selectfirst) {
        this.selectfirst = selectfirst;
    }

    
}
