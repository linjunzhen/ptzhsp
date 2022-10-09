/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowconfig.service.FlowConfigLinkService;
import net.evecom.platform.flowconfig.service.FlowConfigService;
import net.evecom.platform.hflow.service.FlowTypeService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 集成流程关联配置Controller
 * 
 * @author Neil Yu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowConfigLinkController")
public class FlowConfigLinkController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowConfigLinkController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowConfigLinkService flowConfigLinkService;
    /**
     * 引入Service
     */
    @Resource
    private FlowConfigService flowConfigService;

    /**
     * flowTypeService
     */
    @Resource
    private FlowTypeService flowTypeService;

    /**
     * flowTypeService
     */
    @Resource
    private ServiceItemService serviceItemService;
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
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        flowConfigLinkService.remove("T_FLOW_CONFIG_Link", "ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 集成流程关联配置记录", SysLogService.OPERATE_TYPE_DEL);
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
        String cateId = request.getParameter("cate_id");

        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> flowConfigLink = flowConfigLinkService.getByJdbc("T_FLOW_CONFIG_Link",
                    new String[] { "ID" }, new Object[] { entityId });
            String itemId = (String) flowConfigLink.get("ITEM_ID");
            Map<String, Object> serviceItem = flowConfigService.findServiceItemById(itemId);

            flowConfigLink.put("ITEM_NAME", serviceItem.get("ITEM_NAME"));
            request.setAttribute("flowConfigLink", flowConfigLink);
        } else {
            Map<String, Object> flowConfigLink = new HashMap<String, Object>();
            flowConfigLink.put("CATE_ID", cateId);
            request.setAttribute("flowConfigLink", flowConfigLink);
        }
        return new ModelAndView("flowconfig/infoLink");
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
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            variables.put("PUSH_STATUS", "0");
        }

        // 判断该类型下的排序是否已经存在
        Map<String, Object> flowConfig = flowConfigLinkService.getByJdbc("T_FLOW_CONFIG_LINK",
                new String[] { "CATE_ID", "SORT" }, new Object[] { variables.get("CATE_ID"), variables.get("SORT") });
        if (flowConfig != null) {
            if (flowConfig != null && !flowConfig.get("ID").equals(entityId)) {
                j.setSuccess(false);
                j.setMsg("该排序数字已存在");
                return j;
            }
        }

        String recordId = flowConfigLinkService.saveOrUpdate(variables, "T_FLOW_CONFIG_LINK", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 集成流程关联配置记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 集成流程关联配置记录", SysLogService.OPERATE_TYPE_ADD);
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
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.SORT", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowConfigLinkService.findBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
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
        List<Map<String, Object>> list = flowConfigLinkService.findBySqlFilter(filter);
        if (list != null & list.size() > 0) {
            // 已添加该事项
            j.setSuccess(false);
            return j;
        }

        return j;
    }

    /**
     * 调整到发起流程界面
     * 
     * @return
     */
    @RequestMapping(params = "xzyw")
    public ModelAndView xzyw(HttpServletRequest request) {
        List<Map<String, Object>> types = flowTypeService.findDefForStart(request);
        request.setAttribute("types", types);
        String cateId = request.getParameter("cate_id");
        request.setAttribute("cate_id", cateId);
        return new ModelAndView("flowconfig/xzyw");
    }
}
