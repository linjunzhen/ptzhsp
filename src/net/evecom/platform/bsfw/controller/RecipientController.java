/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.FlowTypeService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 收件管理业务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月2日 下午4:07:01
 */
@Controller
@RequestMapping("/recipientController")
public class RecipientController extends BaseController {
    
    /**
     * flowTypeService
     */
    @Resource
    private FlowTypeService flowTypeService;
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;

    /**
     * 调整到发起流程界面
     * 
     * @return
     */
    @RequestMapping(params = "xzyw")
    public ModelAndView xzyw(HttpServletRequest request) {
        List<Map<String,Object>> types = flowTypeService.findDefForStart(request);
        request.setAttribute("types", types);
        /*查找是否有   产业奖补目录查询   权限*/
        SysUser loginUser = AppUtil.getLoginUser();
        Set<String> roleCodes = loginUser.getRoleCodes();
        String username = loginUser.getUsername();
        if (roleCodes.contains("cyjbmlcx") || Objects.equals(username,"admin")) {
            request.setAttribute("cyjbmlcx", true);
        } else {
            request.setAttribute("cyjbmlcx", false);
        }
        return new ModelAndView("bsfw/recipient/xzyw");
    }
    
    /**
     * 收件管理
     * 
     * @return
     */
    @RequestMapping(params = "sjgl")
    public ModelAndView sjgl(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/sjgl");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandleAndSjgl")
    public void needMeHandleAndSjgl(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("E.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandleAndSjgl(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转收件补件界面
     * 
     * @return
     */
    @RequestMapping(params = "bjxx")
    public ModelAndView bjxx(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String exeId = request.getParameter("exeId");
        String sqfs = request.getParameter("sqfs");
        String itemCode = request.getParameter("itemCode");
        if (StringUtils.isNotEmpty(itemCode)) {
            List<Map<String, Object>> materList = applyMaterService.findMaterByItemCode(itemCode);

            // 定义材料列表JSON
            String applyMatersJson = JsonUtil.jsonStringFilter(materList, new String[] { "MATER_ID", "MATER_NAME",
                "MATER_TYPE", "MATER_SIZE", "MATER_PATH", "MATER_ISNEED" }, true);
            request.setAttribute("materList", materList);
            request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        }
        if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
            request.setAttribute("TASK_STATUS", "-1");
            request.setAttribute("APPLY_STATUS", "7");
        } else if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("1")) {
            request.setAttribute("TASK_STATUS", "-1");
            request.setAttribute("APPLY_STATUS", "4");
        }
        request.setAttribute("taskId", taskId);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("bsfw/recipient/bjxx");
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "savebjxx")
    @ResponseBody
    public AjaxJson savebjxx(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        /*String entityId = request.getParameter("BJXX_ID");*/
        /*String taskstatus = request.getParameter("TASK_STATUS");*/
        String applystatus = request.getParameter("APPLY_STATUS");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        //Map<String, Object> tasks = new HashMap<String, Object>();
        //tasks.put("TASK_ID", (String) variables.get("TASK_ID"));
        //tasks.put("TASK_STATUS", taskstatus);
        //更新流程任务 把任务状态改为挂起
        //flowTaskService.saveOrUpdate(tasks, "JBPM6_TASK", (String) variables.get("TASK_ID"));
        Map<String, Object> executions = new HashMap<String, Object>();
        executions.put("EXE_ID", (String) variables.get("EXE_ID"));
        executions.put("APPLY_STATUS", applystatus);
        //更新流程实例表 把申报状态改为 收件补件
        executionService.saveOrUpdate(executions, "JBPM6_EXECUTION", (String) variables.get("EXE_ID"));
        /*String recordId = bjxxService.saveOrUpdate(variables, "T_WSBS_BJXX",
                entityId);*/
        j.setMsg("提交成功");
        return j;
    }
    /**
     * 收件补件管理
     * 
     * @return
     */
    @RequestMapping(params = "sjbjgl")
    public ModelAndView sjbjgl(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/sjbjgl");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandleAndSjbj")
    public void needMeHandleAndSjbj(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("E.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandleAndSjbj(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandleAndYsgl")
    public void needMeHandleAndysgl(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("E.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandleAndysgl(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandleAndYsbj")
    public void needMeHandleAndysbj(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.FROMTASK_ASSIGNER_CODES_EQ",sysUser.getUserId());
        filter.addSorted("E.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandleAndysbj(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 全部预审件
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandleAndQbysj")
    public void needMeHandleAndQbysj(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("E.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandleAndQbysj(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 收件管理
     * 
     * @return
     */
    @RequestMapping(params = "ysgl")
    public ModelAndView ysgl(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/ysgl");
    }

    /**
     * 所有预审
     * 
     * @return
     */
    @RequestMapping(params = "syys")
    public ModelAndView syys(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/syys");
    }
    
    
    /**
     * 预审补件管理
     * 
     * @return
     */
    @RequestMapping(params = "ysbjgl")
    public ModelAndView ysbjgl(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/ysbjgl");
    }
    /**
     * 预审通过
     * 
     * @return
     */
    @RequestMapping(params = "ystg")
    public ModelAndView ystg(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/ystg");
    }
    /**
     * 预审不通过
     * 
     * @return
     */
    @RequestMapping(params = "ysbtg")
    public ModelAndView ysbtg(HttpServletRequest request) {
        return new ModelAndView("bsfw/recipient/ysbtg");
    }
}
