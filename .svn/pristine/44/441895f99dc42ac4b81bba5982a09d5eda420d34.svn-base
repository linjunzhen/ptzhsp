/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.tpl.service.ModelDsService;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 
 * 描述
 * 
 * @author Rider Chen
 * @created 2015-12-29 下午03:46:58
 */
public class ObjTag extends BodyTagSupport {
    /**
     * 属性-3805148461261134806L
     */
    private static final long serialVersionUID = -3805148461261134806L;

    /**
     * 属性dsid
     */
    private int dsid;
    /**
     * 描述：数据源名称。默认为main
     */
    private String dsname;
    /**
     * 属性filterid
     */
    private String filterid;
    /**
     * 属性var
     */
    private String var;
    /**
     * 属性skipbody
     */
    private boolean skipbody;
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
            return SKIP_BODY;// 非法参数，直接终于页面。
        }
        if (filterid.equals("") || filterid.indexOf("!{param") != -1) {
            return EVAL_BODY_INCLUDE;
        }

        ModelDsService modelDsService = (ModelDsService)AppUtil.getBean("modelDsService");
        Map<String,Object>  modelDs = modelDsService.getByJdbc("T_CMS_TPL_MODELDS",
                    new String[]{"DSID"},new Object[]{dsid});
        String sql = (String)modelDs.get("DSCODE");
        Object[] parasArray;
        if (paras!=null&&!"".equals(paras)) {
            parasArray = (Object[]) paras.split(":");
        }else{
            parasArray = new Object[]{filterid};
        }
        List<Map<String, Object>>  items =  modelDsService.findBySql(sql, parasArray);
        if (items == null || items.size() == 0) {
            return skipbody ? SKIP_BODY : EVAL_BODY_INCLUDE;
        }
        pageContext.setAttribute(var, items.get(0));
        return EVAL_BODY_INCLUDE;

    }

    /**
     * 方法doAfterBody
     * 
     * @return 返回值int
     */
    @Override
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    /**
     * 方法doEndTag
     * 
     * @return 返回值int
     */
    @Override
    public int doEndTag() throws JspException {
        super.release();// 调用父类回资源
        pageContext.removeAttribute(var);// 清除pageContext中的值。
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
     * 方法setSkipbody
     * 
     * @param skipbody
     *            传入参数
     */
    public void setSkipbody(boolean skipbody) {
        this.skipbody = skipbody;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    public void setDsname(String dsname) {
        this.dsname = dsname;
    }

}