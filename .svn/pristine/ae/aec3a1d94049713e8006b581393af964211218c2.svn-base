/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.StringUtil;
import net.evecom.core.util.WebUtil;

/**
 * 
 * 描述SubStringTag
 * 
 * @author Rider Chen
 * @created 2015-12-8 下午02:57:14
 */
public class SubStringTag extends BodyTagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SubStringTag.class);
    /**
     * 属性-494268427788969609L
     */
    private static final long serialVersionUID = -494268427788969609L;
    /**
     * 属性要取的字符串
     */
    private String str; // 要取的字符串
    /**
     * 属性默认值
     */
    private String def; // 默认值
    /**
     * 属性结束位置。
     */
    private int endindex; // 结束位置。
    /**
     * 属性是否要有...
     */
    private Boolean ellipsis;// 是否要有...
    /**
     * 属性像的时间。一般指发布时间
     */
    private String objdate;// 像的时间。一般指发布时间
    /**
     * 属性默认为0。不显示
     */
    private int timeout;// new图标显示天数 默认为0。不显示

    /**
     * 方法_构造方法
     * 
     */
    public SubStringTag() {
        super();
        init();
    }

    /**
     * 方法init
     * 
     */
    private void init() {
        str = def = objdate = "";// 把name和def设成""
        timeout = 0;//
        endindex = 0;
        ellipsis = true;
    }

    /**
     * 方法release
     * 
     */
    public void release() {
        super.release();
        init();
    }

    // 标签开始时执行此方法
    /**
     * 方法doStartTag
     * 
     * @return 返回值int
     */
    public int doStartTag() throws JspException {
        JspWriter w = pageContext.getOut();// 获取向页面输出信息的对象
        try {
            if (str == null) {// 值为空时，输出默认值
                w.write(def);
            } else {// 值不为空时，
                str = WebUtil.subString4JSPveiw(str, endindex, ellipsis, objdate, timeout);
                w.write(str);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return SKIP_BODY; // 跳过标签体内容
    }

    /**
     * 方法setDef
     * 
     * @param def
     *            传入参数
     */
    public void setDef(String def) {
        this.def = def;
    }

    /**
     * 方法setEndindex
     * 
     * @param endindex
     *            传入参数
     */
    public void setEndindex(int endindex) {
        this.endindex = endindex;
    }

    /**
     * 方法setEllipsis
     * 
     * @param ellipsis
     *            传入参数
     */
    public void setEllipsis(Boolean ellipsis) {
        this.ellipsis = ellipsis;
    }

    /**
     * 方法setTimeout
     * 
     * @param timeout
     *            传入参数
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 方法setStr
     * 
     * @param str
     *            传入参数
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * 方法setObjdate
     * 
     * @param objdate
     *            传入参数
     */
    public void setObjdate(String objdate) {
        this.objdate = objdate;
    }

}