/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述 复选框组
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月29日 上午9:20:36
 */
public class CheckBoxGroup extends TagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CheckBoxGroup.class);
    /**
     * 宽度
     */
    protected String width;
    /**
     * 默认值
     */
    protected String defaultvalue;
    /**
     * 选中值
     */
    protected String value;
    /**
     * 字典类别编码
     */
    protected String typecode;
    /**
     * 字段名称
     */
    protected String fieldname;
    
    /**
     * @author Flex Hu
     * @created 2014年9月29日 上午10:44:26
     * @param width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月29日 上午10:44:26
     * @param defaultvalue
     */
    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月29日 上午10:44:26
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月29日 上午10:44:26
     * @param typecode
     */
    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月29日 上午10:44:26
     * @param fieldname
     */
    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }
    
    
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
            StringBuffer sb = new StringBuffer();
            sb.append("<div class=\"eve-chcekbox\" style=\"width:").append(width).append("px;\">");
            sb.append("<ul>");
            DictionaryService dictionaryService = (DictionaryService)AppUtil.getBean("dictionaryService");
            List<Map<String,Object>> list = dictionaryService.findByTypeCode(typecode);
            for(Map<String,Object> map:list){
                String dicName = (String) map.get("DIC_NAME");
                String dicCode = (String) map.get("DIC_CODE");
                sb.append("<li>").append(dicName)
                .append("<input type=\"checkbox\" name=\"")
                .append(fieldname).append("\" value=\"")
                .append(dicCode).append("\" ");
                if(StringUtils.isNotEmpty(value)){
                    if(value.indexOf(dicCode)!=-1){
                        sb.append(" checked=\"checked\" ");
                    }
                }else if(StringUtils.isNotEmpty(defaultvalue)&&StringUtils.isEmpty(value)){
                    if(defaultvalue.indexOf(dicCode)!=-1){
                        sb.append(" checked=\"checked\" ");
                    }
                }
                sb.append("></li>");
            }
            sb.append("</ul></div>");
            out.print(sb.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
