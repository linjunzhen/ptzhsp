/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowconfig.service.FlowConfigTypeService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 集成流程类型配置Controller
 * 
 * @author Neil Yu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowConfigTypeController")
public class FlowConfigTypeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowConfigTypeController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowConfigTypeService flowConfigTypeService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] ids = selectColNames.split(",");
        // 删除流程类型
        flowConfigTypeService.remove("T_FLOW_CONFIG_TYPE", "TYPE_ID", ids);
        List<Map<String, Object>> stageList;
        for (String id : ids) {
            stageList = flowConfigTypeService.getAllByJdbc("T_FLOW_CONFIG_STAGE", new String[] { "TYPE_ID" },
                    new Object[] { id });
            for (Map<String, Object> map : stageList) {
                String stageId = map.get("STAGE_ID").toString();
                // 删除阶段事项表数据
                flowConfigTypeService.remove("T_FLOW_CONFIG_ITEM", "STAGE_ID", new String[] { stageId });
            }
            // 删除阶段表数据
            flowConfigTypeService.remove("T_FLOW_CONFIG_STAGE", "TYPE_ID", new String[] { id });
        }
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 集成流程类型配置记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
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
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> flowConfigType = flowConfigTypeService.getByJdbc("T_FLOW_CONFIG_TYPE",
                    new String[] { "TYPE_ID" }, new Object[] { entityId });
            request.setAttribute("flowConfigType", flowConfigType);
        }
        return new ModelAndView("flowconfig/flowTypeInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("TYPE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else{
            variables.put("PUSH_STATUS", 0);
        }
        String recordId = flowConfigTypeService.saveOrUpdate(variables, "T_FLOW_CONFIG_TYPE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 集成流程类型配置记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 集成流程类型配置记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowTypeView")
    public ModelAndView criminalView(HttpServletRequest request) {
        return new ModelAndView("flowconfig/flowTypeView");
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
        // filter.addSorted("T.TREE_SN", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowConfigTypeService.findBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
}
