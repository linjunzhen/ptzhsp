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

import net.evecom.core.util.AppUtil;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 描述  食药业务单选框组
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月29日 上午10:00:38
 * copy by fjfda project package by keravon
 */
public class FdaRadioGroup extends TagSupport {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaRadioGroup.class);
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
     * click事件函数名
     */
    protected String onclick;

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
     * @author Mason Wang
     * @created 2014年11月14日 下午2:50:56
     * @param onclick
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
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
            DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
            List<Map<String, Object>> list = dictionaryService.findByTypeCode(typecode);
            for (Map<String, Object> map : list) {
                String dicName = (String) map.get("DIC_NAME");
                String dicCode = (String) map.get("DIC_CODE");
                sb.append("<li>").append("<input type=\"radio\" name=\"").append(fieldname)
                        .append("\" value=\"").append(dicCode).append("\" ");
                if (StringUtils.isNotEmpty(value)) {
                    if (value.equals(dicCode)) {
                        sb.append(" checked=\"checked\" ");
                    }
                } else if (StringUtils.isNotEmpty(defaultvalue) && StringUtils.isEmpty(value)) {
                    if (defaultvalue.equals(dicCode)) {
                        sb.append(" checked=\"checked\" ");
                    }
                }
                if(StringUtils.isNotEmpty(onclick)) {
                    sb.append(" onclick=\"").append(onclick).append("\" ");
                }
                sb.append(">").append(dicName).append("</li>");
            }
            sb.append("</ul></div>");
            out.print(sb.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
