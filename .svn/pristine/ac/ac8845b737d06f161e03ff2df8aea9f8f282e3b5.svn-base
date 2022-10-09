/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.tpl.service.ModelDsService;

/**
 * 
 * 描述 当前位置导航。
 * 
 * @author Rider Chen
 * @created 2015-12-10 上午11:04:05
 */
public class ModuleCurrentNavTag extends BodyTagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ModuleCurrentNavTag.class);
    /**
     * 属性-3805148461261134806L
     */
    private static final long serialVersionUID = -3805148461261134806L;
    /**
     * 属性filterid
     */
    private String filterid;

    /**
     * 方法doStartTag
     * 
     * @return 返回值int
     */
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        StringBuffer nav = new StringBuffer("<div class=\"current\">");
        nav.append("<span>您现在所在的位置：</span>");
        if ((!filterid.equals("")) && filterid.indexOf("!{param") == -1) {
            if (filterid.contains("_")) {// 文章祥细页时用。
                filterid = filterid.substring(filterid.indexOf("_") + 1,
                        filterid.lastIndexOf("_"));
            }
            ModelDsService modelDsService = (ModelDsService)AppUtil.getBean("modelDsService");
            Map<String,Object>  articleModule = modelDsService.getByJdbc("T_CMS_ARTICLE_MODULE",
                    new String[]{"MODULE_ID"},new Object[]{filterid});
            nav.append("<a href=\"javascript:void(0);\" target=\"_top\">首页</a> > ");
            String path = (String)articleModule.get("path");
            String pids = path.replace(".", ",")
                    .substring(2, path.length() - 1);
            String[] pidsArray = pids.split(",");
            for (int i = 0; i < pidsArray.length; i++) {
                String pid = pidsArray[i];
                Map<String, Object> module = modelDsService.getByJdbc("T_CMS_ARTICLE_MODULE",
                        new String[] { "MODULE_ID" }, new Object[] { pid });
                if (String.valueOf(module.get("module_id")).equals(pid)) {
                    nav.append("<a href=\"javascript:void(0);\">" + module.get("module_name") + "</a>");
                    if (i != (pidsArray.length - 1)) {
                        nav.append(" > ");
                    }
                }

            }
        }
        nav.append("</div>");
        try {
            pageContext.getOut().println(nav.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return SKIP_BODY; // 本标签主体为空,所以直接跳过主体
    }

    /**
     * 方法doEndTag
     * 
     * @return 返回值int
     */
    @Override
    public int doEndTag() throws JspException {
        super.release();// 调用父类回资源
        return EVAL_PAGE;
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

}
