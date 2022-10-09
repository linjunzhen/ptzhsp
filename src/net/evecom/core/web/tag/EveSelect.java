/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.CommonControl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 自定义下拉框
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月7日 下午2:39:51
 */
public class EveSelect extends TagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EveSelect.class);
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
    private String dataInterface;
    /**
     * 组件的数据源参数
     */
    private String dataParams;
    /**
     * 缺省提示值
     */
    private String defaultEmptyText;
    /**
     * 缺省值
     */
    private String value;
    /**
     * defaultEmptyValue
     */
    private String defaultEmptyValue;

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
            String selectStr = CommonControl.getEveSelectStringWithDefaultValue(id, name, clazz,
                    dataInterface, dataParams, onchange, 
                    defaultEmptyText, value,null,defaultEmptyValue);
            out.print(selectStr);
        } catch (Exception e) {
            log.error(e.getMessage());
        } 
        return EVAL_PAGE;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @return type
     */
    public String getId() {
        return id;
    }

    /**
     * defaultEmptyValue
     * @return
     */
    public String getDefaultEmptyValue(){
        return defaultEmptyValue;
    }

    /**
     * defaultEmptyValue
     * @param defaultEmptyValue
     */
    public void setDefaultEmptyValue(String defaultEmptyValue){
        this.defaultEmptyValue=defaultEmptyValue;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @return type
     */
    public String getName() {
        return name;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @return type
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @param clazz
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @return type
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @param onchange
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @return type
     */
    public String getDataInterface() {
        return dataInterface;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:01:15
     * @param dataInterface
     */
    public void setDataInterface(String dataInterface) {
        this.dataInterface = dataInterface;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:06:06
     * @return type
     */
    public String getDataParams() {
        return dataParams;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:06:06
     * @param dataParams
     */
    public void setDataParams(String dataParams) {
        this.dataParams = dataParams;
    }
    
    
    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:31:21
     * @return type
     */
    public String getValue() {
        return value;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:31:21
     * @param defaultValue
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:53:03
     * @return type
     */
    public String getDefaultEmptyText() {
        return defaultEmptyText;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月7日 下午3:53:03
     * @param defaultEmptyText
     */
    public void setDefaultEmptyText(String defaultEmptyText) {
        this.defaultEmptyText = defaultEmptyText;
    }
}
