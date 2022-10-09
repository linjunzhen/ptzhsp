/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchange.service.BusAuditChangeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
@Controller
@RequestMapping("/busAuditChangeController")
public class BusAuditChangeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusAuditChangeController.class);
    /**
     * busColumnService
     */
    @Resource
    private BusAuditChangeService busAuditChangeService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "BusAuditView")
    public ModelAndView busAuditView(HttpServletRequest request) {
        return new ModelAndView("flowchange/busAuditChange/BusAuditChangeView");
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
        filter.addSorted("T.TREE_SN", "asc");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String busCode = request.getParameter("Q_T.BUS_CODE_EQ");
        if (StringUtils.isNotBlank(busCode)) {
            list = busAuditChangeService.findBySqlFilter(filter);
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
        } else {
            this.setListToJsonString(0, list, null, JsonUtil.EXCLUDE, response);
        }

    }

    /**
     * 变更提交审核
     * 
     * @author John Zhang
     * @created 2015-9-7 上午09:34:08
     * @param request
     * @return
     */
    @RequestMapping(params = "submitCheck")
    @ResponseBody
    public AjaxJson submitCheck(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN", "asc");
        List<Map<String, Object>> list;
        String applyId = request.getParameter("Q_T.APPLY_ID_EQ");
        if (StringUtils.isNotBlank(applyId)) {
            list = busAuditChangeService.findBySqlFilter(filter);
            if (list.size() > 0) {
                int count = 0;
                boolean check = true;
                for (Map<String, Object> map : list) {
                    if (!"1".equals(map.get("STATUS").toString())) {
                        check = false;
                        break;
                    } else {
                        count++;
                    }
                }
                if (check) {
                    j.setMsg("提交审核成功");
                    j.setSuccess(true);

                } else {
                    j.setMsg(list.get(count).get("CONFIG_NAME").toString() + "尚未确认变更，不能提交审核");
                    j.setSuccess(false);
                }
            } else {
                j.setMsg("核对项目不足，不能提交审核");
                j.setSuccess(false);
            }
        } else {
            j.setMsg("请选择一项业务");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 跳转到提交审核界面
     * 
     * @author John Zhang
     * @created 2015-9-10 下午03:05:41
     * @param request
     * @return
     */
    @RequestMapping(params = "busAuditChangeInfo")
    public ModelAndView busAuditInfo(HttpServletRequest request) {
        request.setAttribute("BUS_CODE", request.getParameter("busCode"));
        request.setAttribute("APPLY_ID", request.getParameter("applyId"));
        Map<String, Object> applyInfo = busAuditChangeService.getByJdbc("T_LCJC_APPLYINFO", new String[] { "APPLY_ID" },
                new Object[] { request.getParameter("applyId") });
        StringBuffer fileIds=new StringBuffer();
        if (applyInfo != null && applyInfo.get("ATTACHMENT") != null) {
            String[] ids = applyInfo.get("ATTACHMENT").toString().split(",");
            for (int i = 0; i < ids.length; i++) {
                fileIds.append("&fileIds[]=").append(ids[i]);
            }
        }
        request.setAttribute("APPLYINFO", applyInfo);
        request.setAttribute("fileIds", fileIds);
        return new ModelAndView("flowchange/busAuditChange/busAuditChangeInfo");
    }

    /**
     * 提交审核
     * 
     * @author John Zhang
     * @created 2015-8-19 下午03:13:42
     * @param request
     * @return
     */
    @RequestMapping(params = "submitAudit")
    @ResponseBody
    public AjaxJson submitAudit(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);

        Map<String, Object> applyInfo = busAuditChangeService.getByJdbc("T_LCJC_APPLYINFO",
                new String[] { "APPLY_ID" }, new Object[] { variables.get("APPLY_ID") });

        Map<String, Object> flowVars = new HashMap<String, Object>();
        applyInfo.put("REMARK", variables.get("REMARK"));
        applyInfo.put("ATTACHMENT", variables.get("ATTACHMENT"));
        flowVars.putAll(applyInfo);
        flowVars.put("EFLOW_CREATORID", sysUser.getUserId());
        flowVars.put("EFLOW_CREATORACCOUNT", sysUser.getUsername());
        flowVars.put("EFLOW_CREATORNAME", sysUser.getFullname());
        busAuditChangeService.saveOrUpdate(applyInfo, "T_LCJC_APPLYINFO", applyInfo.get("APPLY_ID").toString());
        flowVars.put("EFLOW_DEFKEY", "changeaudit");//流程key
        flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
        flowVars.put("EFLOW_BUSRECORDID", applyInfo.get("APPLY_ID"));
        flowVars.put("EFLOW_BUSTABLENAME", "T_LCJC_APPLYINFO");
        flowVars.put("EFLOW_SUBJECT", applyInfo.get("APPLY_NAME"));
        
        if(applyInfo.get("EXE_ID") != null){
            Map<String, Object> taskInfo = busAuditChangeService.getByJdbc("JBPM6_TASK",
                    new String[]{"FROMTASK_IDS","IS_AUDITED"}, new Object[]{applyInfo.get("TASK_ID"),"1"});
            flowVars.put("EFLOW_EXEID", applyInfo.get("EXE_ID"));
            flowVars.put("EFLOW_NEWTASK_NODENAMES", taskInfo.get("FROMTASK_NODENAMES"));
            flowVars.put("EFLOW_CURRENTTASK_ID", taskInfo.get("TASK_ID"));
            applyInfo.put("REMARK", variables.get("REMARK"));
            applyInfo.put("ATTACHMENT", variables.get("ATTACHMENT"));
        }
        
        try {
            flowVars = jbpmService.startFlow(flowVars);
            busAuditChangeService.changeStatus(applyInfo.get("APPLY_ID").toString(), applyInfo.get("BUS_CODE")
                    .toString(), "2");
            applyInfo.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
            busAuditChangeService.saveOrUpdateForAssignId(applyInfo, 
                    "T_LCJC_APPLYINFO", applyInfo.get("APPLY_ID").toString());
            j.setMsg("提交审核成功!");
            sysLogService.saveLog("专项编码为[" + variables.get("BUS_CODE")
                    + "]提交了变更审核", SysLogService.OPERATE_TYPE_EDIT);
            j.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            j.setMsg("提交审核时出现异常，请联系管理员");
            j.setSuccess(false);
            return j;
        }
        return j;
    }
}
