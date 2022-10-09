/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.cms.service.AuditContentService;
import net.evecom.platform.cms.service.ContentService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 文章审核Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/auditContentController")
public class AuditContentController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(AuditContentController.class);
    /**
     * 引入Service
     */
    @Resource
    private AuditContentService auditContentService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private ContentService contentService;

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        auditContentService.remove("T_CMS_ARTICLE_AUDIT", "AUDIT_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 文章审核记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        String exeId = request.getParameter("contentId");
        request.setAttribute("contentId", exeId);
        return new ModelAndView("cms/auditContent/list");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "viewlist")
    public ModelAndView viewlist(HttpServletRequest request) {
        String exeId = request.getParameter("contentId");
        request.setAttribute("contentId", exeId);
        return new ModelAndView("cms/auditContent/viewlist");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "asc");
        List<Map<String, Object>> list = auditContentService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "viewdatagrid")
    public void viewdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "asc");
        List<Map<String, Object>> list = auditContentService.findViewBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String contentId = request.getParameter("contentId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> auditContent = auditContentService.getByJdbc("T_CMS_ARTICLE_AUDIT",
                    new String[] { "AUDIT_ID" }, new Object[] { entityId });
            request.setAttribute("auditContent", auditContent);
        }
        request.setAttribute("contentId", contentId);
        return new ModelAndView("cms/auditContent/info");
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "viewinfo")
    public ModelAndView viewinfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String contentId = request.getParameter("contentId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> auditContent = auditContentService.getByJdbc("T_CMS_ARTICLE_AUDIT",
                    new String[] { "AUDIT_ID" }, new Object[] { entityId });
            request.setAttribute("auditContent", auditContent);
        }
        request.setAttribute("contentId", contentId);
        return new ModelAndView("cms/auditContent/viewinfo");
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showInfo")
    public ModelAndView showInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String contentId = request.getParameter("contentId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> auditContent = auditContentService.getByJdbc("T_CMS_ARTICLE_AUDIT",
                    new String[] { "AUDIT_ID" }, new Object[] { entityId });
            request.setAttribute("auditContent", auditContent);
        }
        request.setAttribute("contentId", contentId);
        return new ModelAndView("cms/auditContent/showInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("AUDIT_ID");
        String contentId = request.getParameter("CONTENT_ID");
        int state = Integer.parseInt(request.getParameter("state"));

        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        int auditStatus = 0;
        Map<String, Object> content = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(contentId)) {
            content = contentService.getByJdbc("T_CMS_ARTICLE_CONTENT", new String[] { "CONTENT_ID" },
                    new Object[] { contentId });
            if (null != content) {
                auditStatus = null == content.get("AUDIT_STATUS") ? 0 : Integer.parseInt(content.get("AUDIT_STATUS")
                        .toString());
            }
        }
        int status = 0;
        if (state == -1) {// 退回上一步
            auditStatus = auditStatus - 2;
            status = -1;
        } else if (state == 0) {// 审核不通过，退回到信息采编
            auditStatus = -1;
            status = -1;
        }else{
            status = state;
        }
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("CONTENT_ID", contentId);
        //variables.put("AUDIT_USERNAME", sysUser.getUsername());
        //variables.put("AUDIT_USERID", sysUser.getUserId());
        variables.put("CURRENT_STATUS", auditStatus + 1);
        variables.put("NEXT_STATUS", auditStatus + 2);
        variables.put("AUDIT_STATUS", "0");

        auditContentService.updateAuditStatus(contentId, variables.get("auditOPinion").toString(), status);// 更改审核信息

        if ((auditStatus + 1) == 3) {
            content.put("CONTENT_STATUS", 0);
        } else if ((auditStatus + 1) == 0) {
            content.put("CONTENT_STATUS", -2);
        }
        content.put("AUDIT_STATUS", auditStatus + 1);
        
        //判断在处室领导状态时，修改审核人
        if(auditStatus == 1){
            content.put("CHECKER", sysUser.getFullname());
        }
        // 更改文章状态
        contentService.saveOrUpdateContent(content, "T_CMS_ARTICLE_CONTENT", contentId);
        // 添加下一步审核操作信息
        String recordId = auditContentService.saveOrUpdate(variables, "T_CMS_ARTICLE_AUDIT", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 文章审核记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 文章审核记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 方法multiPublish
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "multiAudit")
    @ResponseBody
    public AjaxJson multiAudit(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        SysUser sysUser = AppUtil.getLoginUser();
        String selectColNames = request.getParameter("selectColNames");
        int status = Integer.parseInt(request.getParameter("status"));
        if (StringUtils.isNotEmpty(selectColNames)) {
            String[] ids = selectColNames.split(",");
            for (String id : ids) {
                Map<String, Object> content = new HashMap<String, Object>();
                if (status == 1) {
                    content.put("AUDIT_STATUS", 1);
                    content.put("CONTENT_STATUS", -1);
                } else if (status == -1) {
                    content.put("AUDIT_STATUS", 0);
                    content.put("CONTENT_STATUS", -2);
                }
                contentService.saveOrUpdateContent(content, "T_CMS_ARTICLE_CONTENT", id);
                Map<String, Object> audit = new HashMap<String, Object>();
                audit.put("CONTENT_ID", id);
                audit.put("AUDIT_USERNAME", sysUser.getUsername());
                audit.put("AUDIT_USERID", sysUser.getUserId());
                audit.put("CURRENT_STATUS", status);
                audit.put("NEXT_STATUS", status + 1);
                audit.put("AUDIT_STATUS", "0");
                audit.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                auditContentService.updateAuditStatus(id, "", status);// 更改审核状态
                auditContentService.saveOrUpdate(audit, "T_CMS_ARTICLE_AUDIT", "");

            }
        }
        sysLogService.saveLog("发送审核了ID为[" + selectColNames + "]的 文章记录", SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("操作成功");
        return j;
    }
}
