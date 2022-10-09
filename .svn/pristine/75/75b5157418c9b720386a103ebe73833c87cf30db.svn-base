/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

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
import net.evecom.platform.flowchart.service.BusAuditService;
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
@RequestMapping("/busAuditController")
public class BusAuditController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusAuditController.class);
    /**
     * busColumnService
     */
    @Resource
    private BusAuditService busAuditService;
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
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "BusAuditView")
    public ModelAndView busAuditView(HttpServletRequest request) {
        return new ModelAndView("flowchart/busAudit/BusAuditView");
    }

    /**
     * 跳转到提交审核界面
     * 
     * @author John Zhang
     * @created 2015-9-10 下午03:05:41
     * @param request
     * @return
     */
    @RequestMapping(params = "busAuditInfo")
    public ModelAndView busAuditInfo(HttpServletRequest request) {
        request.setAttribute("BUS_CODE", request.getParameter("busCode"));
        Map<String, Object> applyInfo = busAuditService.getByJdbc("T_LCJC_APPLYINFO", new String[] { "EXE_ID" },
                new Object[] { request.getParameter("exeId") });
        StringBuffer fileIds=new StringBuffer("applyInfo");
        if (applyInfo != null && applyInfo.get("ATTACHMENT") != null) {
            String[] ids = applyInfo.get("ATTACHMENT").toString().split(",");
            for (int i = 0; i < ids.length; i++) {
                fileIds.append("&fileIds[]=").append(ids[i]);
            }
        }
        request.setAttribute("APPLYINFO", applyInfo);
        request.setAttribute("fileIds", fileIds);
        
        return new ModelAndView("flowchart/busAudit/busAuditInfo");
    }

    /**
     * 查看提交材料界面
     * 
     * @author John Zhang
     * @created 2015-9-10 下午03:05:41
     * @param request
     * @return
     */
    @RequestMapping(params = "auditInfo")
    public ModelAndView auditInfo(HttpServletRequest request) {
        Map<String, Object> applyInfo = busAuditService.getByJdbc("T_LCJC_APPLYINFO", new String[] { "APPLY_ID" },
                new Object[] { request.getParameter("applyId") });
        StringBuffer fileIds=new StringBuffer();
        if (applyInfo.get("ATTACHMENT") != null) {
            String[] ids = applyInfo.get("ATTACHMENT").toString().split(",");
            for (int i = 0; i < ids.length; i++) {
                fileIds.append("&fileIds[]=").append(ids[i]);
            }
        }
        request.setAttribute("APPLYINFO", applyInfo);
        request.setAttribute("fileIds", fileIds);
        return new ModelAndView("flowchart/busAudit/auditInfo");
    }

    /**
     * 提交审核
     * 
     * @author John Zhang
     * @created 2015-8-19 下午03:13:42
     * @param request
     * @return
     */
    @RequestMapping(params = "submitAuditOld")
    @ResponseBody
    public AjaxJson submitAuditOld(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String busCode = String.valueOf(variables.get("BUS_CODE"));
        Map<String, Object> applyInfo = new HashMap<String, Object>();
        Map<String, Object> busSpecial = busAuditService.getByJdbc("T_LCJC_BUS_SPECIAL", new String[] { "BUS_CODE" },
                new Object[] { busCode });
        applyInfo.put("APPLY_ID", busCode + "." + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmm"));
        applyInfo.put("APPLY_USERID", sysUser.getUserId());
        applyInfo.put("APPLY_USERNAME", sysUser.getUsername());
        applyInfo.put("BUS_UNITCODE", busSpecial.get("UNIT_CODE"));
        applyInfo.put("APPLY_TYPE", "1");
        applyInfo.put("APPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        applyInfo.put("LAST_APPLY_ID", busSpecial.get("APPLY_ID"));
        applyInfo.put("BUS_CODE", busCode);
        applyInfo.put("REMARK", variables.get("REMARK"));
        applyInfo.put("ATTACHMENT", variables.get("ATTACHMENT"));
        applyInfo.put("APPLY_TYPE", "1");

        Map<String, Object> flowVars = new HashMap<String, Object>();
        flowVars.putAll(applyInfo);
        flowVars.put("EFLOW_CREATORID", sysUser.getUserId());
        flowVars.put("EFLOW_CREATORACCOUNT", sysUser.getUsername());
        flowVars.put("EFLOW_CREATORNAME", sysUser.getFullname());
        String status = busSpecial.get("STATUS").toString();
        String version = busSpecial.get("VERSION").toString();
        if ("1".equals(version) && !"3".equals(status) && !"2".equals(status)) {
            applyInfo.put("APPLY_NAME", busSpecial.get("BUS_NAME") + "[第一阶段审核]");
            applyInfo.put("TYPE_STAGE", "1");
        }
        if ("1".equals(version) && "3".equals(status)) {
            busSpecial.put("VERSION", "2");
            applyInfo.put("TYPE_STAGE", "2");
            busAuditService.saveOrUpdate(busSpecial, "T_LCJC_BUS_SPECIAL", busSpecial.get("BUS_ID").toString());
            applyInfo.put("APPLY_NAME", busSpecial.get("BUS_NAME") + "[第二阶段审核]");
            busAuditService.secondSubmit(applyInfo.get("APPLY_ID").toString(), busCode);
        }
        if ("2".equals(version) && !"3".equals(status) && !"2".equals(status)) {
            applyInfo.put("TYPE_STAGE", "2");
            applyInfo.put("APPLY_NAME", busSpecial.get("BUS_NAME") + "[第二阶段审核]");
        }
        busAuditService.saveOrUpdateForAssignId(applyInfo, "T_LCJC_APPLYINFO", applyInfo.get("APPLY_ID").toString());
        if ("2".equals(applyInfo.get("TYPE_STAGE").toString())) {
            busAuditService.secondSubmit(applyInfo.get("APPLY_ID").toString(), busCode);
        } else {
            busAuditService.firstAudit(applyInfo.get("APPLY_ID").toString(), busCode);
        }

        flowVars.put("EFLOW_DEFKEY", "dyaudit");
        flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
        flowVars.put("EFLOW_BUSRECORDID", applyInfo.get("APPLY_ID"));
        flowVars.put("EFLOW_BUSTABLENAME", "T_LCJC_APPLYINFO");
        flowVars.put("EFLOW_SUBJECT", applyInfo.get("APPLY_NAME"));
        try {
            jbpmService.exeFlow(flowVars);
            if ("2".equals(applyInfo.get("TYPE_STAGE").toString())) {
                busAuditService.changeStatus(busCode, "2");
            } else {
                busAuditService.firstStatus(busCode, "2");
            }

            j.setMsg("提交审核成功!");
            sysLogService.saveLog("专项编码为[" + busCode + "]提交了第" + busSpecial.get("VERSION") + "阶段审核",
                    SysLogService.OPERATE_TYPE_EDIT);
            j.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            j.setMsg("提交审核时出现异常，请联系管理员");
            j.setSuccess(false);
            return j;
        }
        return j;
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
        String busCode = String.valueOf(variables.get("BUS_CODE"));
        String exeId =(String) variables.get("EXE_ID");
        Map<String, Object> busSpecial = busAuditService.getByJdbc("T_LCJC_BUS_SPECIAL", new String[] { "BUS_CODE" },
                new Object[] { busCode });
        try { 
            flowSubmit(sysUser, variables, busCode, busSpecial, exeId);
            j.setMsg("提交审核成功!");
            sysLogService.saveLog("专项编码为[" + busCode + "]提交了第" + busSpecial.get("VERSION") + "阶段审核",
                    SysLogService.OPERATE_TYPE_EDIT);
            j.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            j.setMsg("提交审核时出现异常，请联系管理员");
            j.setSuccess(false);
            return j;
        }
        return j;
    }

    
    private void flowSubmit(SysUser sysUser, Map<String, Object> variables,
            String busCode, Map<String,Object> busSpecial, String exeId)
        throws Exception {
        Map<String, Object> applyInfo;
        Map<String, Object> flowVars = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(exeId)){
            applyInfo = busAuditService.getByJdbc("T_LCJC_APPLYINFO", new String[]{"EXE_ID"}, 
                    new Object[]{exeId});
            Map<String, Object> taskInfo = busAuditService.getByJdbc("JBPM6_TASK",
                    new String[]{"FROMTASK_IDS","IS_AUDITED"}, new Object[]{applyInfo.get("TASK_ID"),"1"});
            
            flowVars.put("EFLOW_EXEID", exeId);
            flowVars.put("EFLOW_NEWTASK_NODENAMES", taskInfo.get("FROMTASK_NODENAMES"));
            flowVars.put("EFLOW_CURRENTTASK_ID", taskInfo.get("TASK_ID"));
            applyInfo.put("REMARK", variables.get("REMARK"));
            applyInfo.put("ATTACHMENT", variables.get("ATTACHMENT"));
        }else{
            applyInfo = getApplyInfo(sysUser, variables, busCode, busSpecial);
        }
        flowVars.putAll(applyInfo);
        flowVars.put("EFLOW_CREATORID", sysUser.getUserId());
        flowVars.put("EFLOW_CREATORACCOUNT", sysUser.getUsername());
        flowVars.put("EFLOW_CREATORNAME", sysUser.getFullname());
        busAuditService.saveOrUpdateForAssignId(applyInfo, "T_LCJC_APPLYINFO", applyInfo.get("APPLY_ID").toString());
        if ("2".equals(applyInfo.get("TYPE_STAGE").toString())) {
            busAuditService.secondSubmit(applyInfo.get("APPLY_ID").toString(), busCode);
            applyInfo.put("APPLY_NAME", busSpecial.get("BUS_NAME") + "[第二阶段审核]");
            busAuditService.secondSubmit(applyInfo.get("APPLY_ID").toString(), busCode);
            flowVars.put("EFLOW_DEFKEY", "deaudit");//第二阶段流程key
        } else {
            applyInfo.put("APPLY_NAME", busSpecial.get("BUS_NAME") + "[第一阶段审核]");
            busAuditService.firstAudit(applyInfo.get("APPLY_ID").toString(), busCode);
            flowVars.put("EFLOW_DEFKEY", "dyaudit");//第一阶段流程key
        }
        
        flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
        flowVars.put("EFLOW_BUSRECORDID", applyInfo.get("APPLY_ID"));
        flowVars.put("EFLOW_BUSTABLENAME", "T_LCJC_APPLYINFO");
        flowVars.put("EFLOW_SUBJECT", applyInfo.get("APPLY_NAME"));
        flowVars = jbpmService.startFlow(flowVars);
        if ("2".equals(applyInfo.get("TYPE_STAGE").toString())) {
            busAuditService.changeStatus(busCode, "2");
        } else {
            busAuditService.firstStatus(busCode, "2");
        }
        applyInfo.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
        busSpecial.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
        busSpecial.put("STATUS", "2");
        busSpecial.put("APPLY_ID", applyInfo.get("APPLY_ID"));
        busAuditService.saveOrUpdateForAssignId(applyInfo, "T_LCJC_APPLYINFO", applyInfo.get("APPLY_ID").toString());
        busAuditService.saveOrUpdate(busSpecial, "T_LCJC_BUS_SPECIAL", busSpecial.get("BUS_ID").toString());
    }

    private Map<String, Object> getApplyInfo(SysUser sysUser, Map<String, Object> variables, String busCode,
            Map<String, Object> busSpecial) {
        Map<String, Object> applyInfo = new HashMap<String, Object>();
        applyInfo.put("APPLY_ID", busCode + "." + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmm"));
        applyInfo.put("APPLY_USERID", sysUser.getUserId());
        applyInfo.put("APPLY_USERNAME", sysUser.getUsername());
        applyInfo.put("BUS_UNITCODE", busSpecial.get("UNIT_CODE"));
        applyInfo.put("APPLY_TYPE", "1");
        applyInfo.put("APPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        applyInfo.put("LAST_APPLY_ID", busSpecial.get("APPLY_ID"));
        applyInfo.put("BUS_CODE", busCode);
        applyInfo.put("REMARK", variables.get("REMARK"));
        applyInfo.put("ATTACHMENT", variables.get("ATTACHMENT"));
        applyInfo.put("APPLY_TYPE", "1");
        String status = busSpecial.get("STATUS").toString();
        String version = busSpecial.get("VERSION").toString();
        if ("1".equals(version) && !"3".equals(status)) {
            applyInfo.put("TYPE_STAGE", "1");
        }
        if ("1".equals(version) && "3".equals(status)) {
            busSpecial.put("VERSION", "2");
            applyInfo.put("TYPE_STAGE", "2");
        }
        if ("2".equals(version)) {
            applyInfo.put("TYPE_STAGE", "2");
        }
        return applyInfo;
    }

    /**
     * 提交第一阶段
     * 
     * @author John Zhang
     * @created 2015-8-19 下午03:13:42
     * @param request
     * @return
     */
    @RequestMapping(params = "submitFirst")
    @ResponseBody
    public AjaxJson submitFirst(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //SysUser sysUser = AppUtil.getLoginUser();
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN", "asc");
        List<Map<String, Object>> list;
        String busCode = request.getParameter("Q_T.BUS_CODE_EQ");
        // busAuditService.judgeAudit(busCode);
        if (StringUtils.isNotBlank(busCode)) {
            list = busAuditService.findBySqlFilter(filter);
            if (list.size() > 0) {
                int count = 0;
                boolean check = true;
                for (Map<String, Object> map : list) {
                    if ("1".equals(map.get("STATUS").toString())) {
                        count++;
                    } else {
                        check = false;
                        break;
                    }
                    if (count == 2) {
                        break;
                    }
                }
                if (check && count == 2) {
                    j.setMsg("提交第一阶段成功");
                    j.setSuccess(true);

                } else {
                    if (check) {
                        j.setMsg("核对项目不足，不能提交第一阶段");
                        j.setSuccess(false);
                    } else {
                        String msg = list.get(count).get("CONFIG_NAME") + "材料不足，不能提交第一阶段";
                        j.setMsg(msg);
                        j.setSuccess(false);
                    }
                }
            } else {
                j.setMsg("核对项目不足，不能提交第一阶段");
                j.setSuccess(false);
            }
        } else {
            j.setMsg("请选择一项业务");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 提交第二阶段
     * 
     * @author John Zhang
     * @created 2015-9-10 上午09:27:28
     * @param request
     * @return
     */
    @RequestMapping(params = "submitSecond")
    @ResponseBody
    public AjaxJson submitSecond(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //SysUser sysUser = AppUtil.getLoginUser();
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN", "asc");
        List<Map<String, Object>> list;
        String busCode = request.getParameter("Q_T.BUS_CODE_EQ");
        // busAuditService.judgeAudit(busCode);
        if (StringUtils.isNotBlank(busCode)) {
            list = busAuditService.findBySqlFilter(filter);
            if (list.size() > 0) {
                int count = 0;
                boolean check = true;
                for (Map<String, Object> map : list) {
                    if ("1".equals(map.get("STATUS").toString())) {
                        count++;
                    } else {
                        check = false;
                        break;
                    }
                }
                if (check && count == 5) {
                    j.setMsg("提交第二阶段成功");
                    j.setSuccess(true);
                } else {
                    if (check) {
                        j.setMsg("核对项目不足，不能提交第二阶段");
                        j.setSuccess(false);
                    } else {
                        String msg = list.get(count).get("CONFIG_NAME") + "材料不足，不能提交第二阶段";
                        j.setMsg(msg);
                        j.setSuccess(false);
                    }
                }
            } else {
                j.setMsg("核对项目不足，不能提交第二阶段");
                j.setSuccess(false);
            }
        } else {
            j.setMsg("请选择一项业务");
            j.setSuccess(false);
        }

        return j;
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
        // filter.addSorted("A.TREE_SN","asc");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String busCode = request.getParameter("Q_T.BUS_CODE_EQ");
        // busAuditService.judgeAudit(busCode);
        if (StringUtils.isNotBlank(busCode)) {
            list = busAuditService.findBySqlFilter(filter);
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
        } else {
            this.setListToJsonString(0, list, null, JsonUtil.EXCLUDE, response);
        }

    }
}
