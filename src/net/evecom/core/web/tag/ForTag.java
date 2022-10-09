/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.tag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.tpl.service.ModelDsService;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/**
 * 
 * 描述 
 * 
 * @author Rider Chen
 * @created 2015-12-7 上午11:18:51
 */
public class ForTag extends TagSupport {
    /**
     * 描述：
     */
    private static final long serialVersionUID = -3805148461261134806L;
    /**
     * 描述：
     */
    private String var;
    /**
     * 描述：
     */
    private int dsid;
    /**
     * 描述：
     */
    private String filterid;
    /**
     * 描述：
     */
    private int begin;
    /**
     * 描述：
     */
    private int end;
    /**
     * 描述：
     */
    private Iterator<Map<String, Object>> it;

    /**
     * 要查询的参数：多个时以":"分隔。
     */
    private String paras;

    /**
     * 方法doStartTag
     * 
     * @return 返回值int
     */
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        if (!Jsoup.isValid(filterid, Whitelist.none())) {
            return SKIP_PAGE;// 非法参数，直接终于页面。
        }
        if (filterid.equals("") || filterid.indexOf("!{param") != -1) {
            return EVAL_BODY_INCLUDE;
        }
        ModelDsService modelDsService = (ModelDsService)AppUtil.getBean("modelDsService");
        Map<String,Object>  modelDs = modelDsService.getByJdbc("T_CMS_TPL_MODELDS",
                    new String[]{"DSID"},new Object[]{dsid});
        String sql = (String)modelDs.get("DSCODE");
        Object[] parasArray;
//        Object[] parasArray = new Object[0];
        if (paras != null && !"".equals(paras)) {
            parasArray = (Object[]) paras.split(":");
        } else {
            parasArray = new Object[] { filterid, end, begin };
        }

        List<Map<String, Object>>  items =  modelDsService.findBySql(sql, parasArray);
        
        if (items == null || items.size() == 0) {
            return SKIP_BODY;
        }
        it = items.iterator();
        if (it.hasNext()) {
            Map<String, Object> r = (Map<String, Object>) it.next();
            pageContext.setAttribute(var, r);
        }
        pageContext.setAttribute(var + "size", items.size());
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 方法doAfterBody
     * 
     * @return 返回值int
     */
    @Override
    public int doAfterBody() throws JspException {
        if (it != null && it.hasNext()) {
            Map<String, Object> r = (Map<String, Object>) it.next();
            pageContext.setAttribute(var, r);
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    /**
     * 方法doEndTag
     * 
     * @return 返回值int
     */
    @Override
    public int doEndTag() throws JspException {
        super.release();
        pageContext.removeAttribute(var);
        pageContext.removeAttribute(var + "size");
        return EVAL_PAGE;
    }

    /**
     * 方法setDsid
     * 
     * @param dsid
     *            传入参数
     */
    public void setDsid(int dsid) {
        this.dsid = dsid;
    }


    /**
     * 方法setFilterid
     * 
     * @param filterid
     *            传入参数
     */
    public void setFilterid(String filterid) {
        this.filterid = filterid.replace("eve_moduleid_", "");
    }

    /**
     * 方法setVar
     * 
     * @param var
     *            传入参数
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * 方法setBegin
     * 
     * @param begin
     *            传入参数
     */
    public void setBegin(int begin) {
        this.begin = begin;
    }

    /**
     * 方法setEnd
     * 
     * @param end
     *            传入参数
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * 方法setIt
     * 
     * @param it
     *            传入参数
     */
    public void setIt(Iterator<Map<String, Object>> it) {
        this.it = it;
    }

    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }
}