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

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowconfig.service.FlowConfigService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 集成流程配置Controller
 * 
 * @author Neil Yu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowConfigController")
public class FlowConfigController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowConfigController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowConfigService flowConfigService;

    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
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
        flowConfigService.remove("T_FLOW_CONFIG_CATEGORY", "ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 集成流程配置记录", SysLogService.OPERATE_TYPE_DEL);
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
        String parentId = request.getParameter("PARENT_ID");
        String type = request.getParameter("type");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> flowConfig = flowConfigService.getByJdbc("T_FLOW_CONFIG_CATEGORY",
                    new String[] { "ID" }, new Object[] { entityId });
            request.setAttribute("flowConfig", flowConfig);
        } else {
            Map<String, Object> flowConfig = new HashMap<String, Object>();
            flowConfig.put("PARENT_ID", parentId);
            flowConfig.put("TYPE", type);
            request.setAttribute("flowConfig", flowConfig);
        }
        return new ModelAndView("flowconfig/info");
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goTaskList")
    public ModelAndView goTaskList(HttpServletRequest request) {
        String parentId = request.getParameter("PARENT_ID");
        request.setAttribute("PARENT_ID", parentId);
        return new ModelAndView("flowconfig/taskList");
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
        String parentId = request.getParameter("PARENT_ID");
        String type = request.getParameter("TYPE");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            variables.put("PUSH_STATUS", "0");
        }
        String recordId = "";
        // 判断该类型下的排序是否已经存在
        Map<String, Object> flowConfig = flowConfigService.getByJdbc("T_FLOW_CONFIG_CATEGORY",
                new String[] { "PARENT_ID", "TREE_SN" },
                new Object[] { variables.get("PARENT_ID"), variables.get("TREE_SN") });
        if (flowConfig != null) {
            if (flowConfig != null && !flowConfig.get("ID").equals(entityId)) {
                j.setSuccess(false);
                j.setMsg("该排序数字已存在");
                return j;
            }
        }

        if (StringUtils.isNotBlank(type) && type.equals("1")) {
            recordId = flowConfigService.saveOrUpdate(variables, "T_FLOW_CONFIG_CATEGORY", entityId);
        } else {
            variables.put("TYPE", "0");
            recordId = flowConfigService.saveOrUpdateTreeData(parentId, variables, "T_FLOW_CONFIG_CATEGORY", null);

        }
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 集成流程配置记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 集成流程配置记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "doAddDefaultTask")
    @ResponseBody
    public AjaxJson doAddDefaultTask(HttpServletRequest request) {

        AjaxJson j = new AjaxJson();
        // String typeCode = request.getParameter("DICT_TYPE_CODE");
        String parentId = request.getParameter("parentId");
        Map<String, Object> mapCate = flowConfigService.getByJdbc("T_FLOW_CONFIG_CATEGORY", new String[] { "ID" },
                new String[] { parentId });
        String flowTypeId = (String) mapCate.get("FLOW_TYPE_ID");
        if (StringUtils.isEmpty(flowTypeId)) {
            j.setSuccess(false);
            j.setMsg("请先选择分类【" + mapCate.get("NAME") + "】的流程类型");
            return j;
        }
        Map<String, Object> mapType = flowConfigService.getByJdbc("T_FLOW_CONFIG_TYPE", new String[] { "TYPE_ID" },
                new String[] { flowTypeId });

        List<Map<String, Object>> list = dictionaryService.findByTypeCode((String) mapType.get("FLOW_TYPE_DIC_CODE"));


        if (list != null && list.size() > 0) {
            Map<String, Object> variables = new HashMap<String, Object>();
            int i = 1;
            for (Map<String, Object> map : list) {
                variables.put("NAME", map.get("DIC_NAME"));
                variables.put("TYPE", "1");
                variables.put("PARENT_ID", parentId);
                variables.put("TREE_SN", i);
                variables.put("DYBZSPJDXH", map.get("DIC_CODE"));
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                flowConfigService.saveOrUpdate(variables, "T_FLOW_CONFIG_CATEGORY", null);
                i++;
            }
        }
        j.setMsg("添加默认流程成功");
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
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowConfigService.findBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowConfigView")
    public ModelAndView criminalView(HttpServletRequest request) {
        return new ModelAndView("flowconfig/configView");
    }
}
