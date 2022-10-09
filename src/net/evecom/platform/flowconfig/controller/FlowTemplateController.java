/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.controller;

import java.util.Date;
import java.util.HashMap;
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
import net.evecom.platform.flowconfig.service.FlowConfigService;
import net.evecom.platform.flowconfig.service.FlowTemplateService;
import net.evecom.platform.hflow.service.FlowTypeService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 描述：工程建设项目流程模版配置Controller
 * 
 * @author Rider Chen
 * @created 2019年9月18日 下午6:28:55
 */
@Controller
@RequestMapping("/flowTemplateController")
public class FlowTemplateController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowTemplateController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowTemplateService flowTemplateService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * flowTypeService
     */
    @Resource
    private FlowTypeService flowTypeService;

    /**
     * 引入Service
     */
    @Resource
    private FlowConfigService flowConfigService;

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("flowconfig/flowTemplate/flowTemplateView");
    }

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "stageMultiDel")
    @ResponseBody
    public AjaxJson stageMultiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        // 删除阶段表数据
        flowTemplateService.remove("T_FLOW_CONFIG_STAGE", "STAGE_ID", selectColNames.split(","));
        // 删除阶段事项表数据
        flowTemplateService.remove("T_FLOW_CONFIG_ITEM", "STAGE_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 工程建设项目流程模版阶段配置记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "stageInfo")
    public ModelAndView stageInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String typeId = request.getParameter("TYPE_ID");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> flowStage = flowTemplateService.getByJdbc("T_FLOW_CONFIG_STAGE",
                    new String[] { "STAGE_ID" }, new Object[] { entityId });
            String itemId = flowStage.get("ITEM_ID") == null ? "" : flowStage.get("ITEM_ID").toString();
            if (StringUtils.isNotEmpty(itemId)) {
                Map<String, Object> serviceItem = flowConfigService.findServiceItemById(itemId);
                flowStage.put("ITEM_NAME", serviceItem.get("ITEM_NAME"));
            }
            request.setAttribute("flowStage", flowStage);
        } else {
            Map<String, Object> flowStage = new HashMap<String, Object>();
            flowStage.put("TYPE_ID", typeId);
            request.setAttribute("flowStage", flowStage);
        }
        return new ModelAndView("flowconfig/flowTemplate/stageInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "stageSaveOrUpdate")
    @ResponseBody
    public AjaxJson stageSaveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("STAGE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            variables.put("PUSH_STATUS", "0");
        }

        // 判断该类型下的排序是否已经存在
        Map<String, Object> flowStage = flowTemplateService.getByJdbc("T_FLOW_CONFIG_STAGE",
                new String[] { "TYPE_ID", "TREE_SN" },
                new Object[] { variables.get("TYPE_ID"), variables.get("TREE_SN") });
        if (flowStage != null) {
            if (flowStage != null && !flowStage.get("STAGE_ID").equals(entityId)) {
                j.setSuccess(false);
                j.setMsg("该排序数字已存在");
                return j;
            }
        }
        String recordId = flowTemplateService.saveOrUpdate(variables, "T_FLOW_CONFIG_STAGE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 工程建设项目流程模版阶段配置记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 工程建设项目流程模版阶段配置记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
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
    @RequestMapping(params = "stageDatagrid")
    public void stageDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowTemplateService.findStageBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "itemMultiDel")
    @ResponseBody
    public AjaxJson itemMultiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        // 删除阶段事项表数据
        flowTemplateService.remove("T_FLOW_CONFIG_ITEM", "ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 工程建设项目流程模版阶段事项配置记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "itemOffShelves")
    @ResponseBody
    public AjaxJson itemOffShelves(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("IS_OFF_SHELVES", "1");
        for (String selectColName : selectColNames.split(",")) {
            flowTemplateService.saveOrUpdate(variables, "T_FLOW_CONFIG_ITEM", selectColName);
        }
        sysLogService.saveLog("下架了ID为[" + selectColNames + "]的 工程建设项目流程模版阶段事项配置记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("下架成功");
        return j;
    }

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "reductionItem")
    @ResponseBody
    public AjaxJson reductionItem(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ID = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("IS_OFF_SHELVES", "0");
        flowTemplateService.saveOrUpdate(variables, "T_FLOW_CONFIG_ITEM", ID);
        sysLogService.saveLog("还原下架了ID为[" + ID + "]的 工程建设项目流程模版阶段事项配置记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("操作成功");
        return j;
    }

    /**
     * 跳转到下架事项列表页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goOffShelvesItemList")
    public ModelAndView goOffShelvesItemList(HttpServletRequest request) {
        String STAGE_ID = request.getParameter("STAGE_ID");
        if (StringUtils.isNotEmpty(STAGE_ID) && !STAGE_ID.equals("undefined")) {
            request.setAttribute("STAGE_ID", STAGE_ID);
        }
        return new ModelAndView("flowconfig/flowTemplate/offShelvesItemList");
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "itemInfo")
    public ModelAndView itemInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String stageId = request.getParameter("STAGE_ID");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> flowConfigItem = flowTemplateService.getByJdbc("T_FLOW_CONFIG_ITEM",
                    new String[] { "ID" }, new Object[] { entityId });
            String itemId = (String) flowConfigItem.get("ITEM_ID");
            Map<String, Object> serviceItem = flowConfigService.findServiceItemById(itemId);

            flowConfigItem.put("ITEM_NAME", serviceItem.get("ITEM_NAME"));
            request.setAttribute("flowConfigItem", flowConfigItem);
        } else {
            Map<String, Object> flowConfigItem = new HashMap<String, Object>();
            flowConfigItem.put("STAGE_ID", stageId);
            request.setAttribute("flowConfigItem", flowConfigItem);
        }
        return new ModelAndView("flowconfig/flowTemplate/itemInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "itemSaveOrUpdate")
    @ResponseBody
    public AjaxJson itemSaveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            variables.put("PUSH_STATUS", "0");
        }

        // 判断该类型下的排序是否已经存在
        Map<String, Object> flowConfig = flowTemplateService.getByJdbc("T_FLOW_CONFIG_ITEM",
                new String[] { "STAGE_ID", "SORT" }, new Object[] { variables.get("STAGE_ID"), variables.get("SORT") });
        if (flowConfig != null) {
            if (flowConfig != null && !flowConfig.get("ID").equals(entityId)) {
                j.setSuccess(false);
                j.setMsg("该排序数字已存在");
                return j;
            }
        }
        String recordId = flowTemplateService.saveOrUpdate(variables, "T_FLOW_CONFIG_ITEM", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 工程建设项目流程模版事项配置记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 工程建设项目流程模版事项配置记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
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
    @RequestMapping(params = "itemDatagrid")
    public void itemDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.SORT", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowTemplateService.findItemBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 调整到发起流程界面
     * 
     * @return
     */
    @RequestMapping(params = "itemSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        List<Map<String, Object>> types = flowTypeService.findDefForStart(request);
        request.setAttribute("types", types);
        String STAGE_ID = request.getParameter("STAGE_ID");
        request.setAttribute("STAGE_ID", STAGE_ID);
        return new ModelAndView("flowconfig/flowTemplate/itemSelector");
    }

    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "checkFlow")
    @ResponseBody
    public AjaxJson checkFlow(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.SORT", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowTemplateService.findItemBySqlFilter(filter);
        if (list != null & list.size() > 0) {
            // 已添加该事项
            j.setSuccess(false);
            return j;
        }

        return j;
    }
}
