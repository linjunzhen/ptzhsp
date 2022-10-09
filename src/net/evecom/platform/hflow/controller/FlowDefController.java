/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowButtonService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowFormService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.FlowTypeService;
import net.evecom.platform.hflow.service.HistDeployService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.QueryButtonAuthService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  流程定义Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowDefController")
public class FlowDefController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowDefController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowDefService flowDefService;
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
     * flowTypeService
     */
    @Resource
    private FlowTypeService flowTypeService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * histDeployService
     */
    @Resource
    private HistDeployService histDeployService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 
     */
    @Resource
    private FlowFormService flowFormService;
    /**
     * flowButtonService
     */
    @Resource
    private FlowButtonService flowButtonService;
    /**
     * queryButtonAuthService
     */
    @Resource
    private QueryButtonAuthService queryButtonAuthService;

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
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = flowDefService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hflow/flowdef/flowDefView");
    }

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "configview")
    public ModelAndView configview(HttpServletRequest request) {
        String defId = request.getParameter("defId");
        String flowVersion = request.getParameter("flowVersion");
        // 获取审批按钮列表
        List<Map<String, Object>> buttonList = flowButtonService.findList();
        List<Map<String, Object>> queryButtonList = queryButtonAuthService.saveOrGetList(buttonList, defId,
                Integer.parseInt(flowVersion));
        // 过滤掉重复的流程按钮
        for (int i = 0; i < queryButtonList.size() - 1; i++) {
            for (int j = queryButtonList.size() - 1; j > i; j--) {
                if (queryButtonList.get(j).get("BUTTON_KEY").equals(queryButtonList.get(i).get("BUTTON_KEY"))) {
                    queryButtonList.remove(j); 
                }
            }
        }
        request.setAttribute("queryButtonList", queryButtonList);
        request.setAttribute("defId", defId);
        request.setAttribute("flowVersion", flowVersion);
        return new ModelAndView("hflow/flowdef/flowDefAllConf");
    }

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        flowDefService.deleteFlowDef(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 流程定义记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                    new Object[] { entityId });
            request.setAttribute("flowDef", flowDef);
        }
        return new ModelAndView("hflow/flowDef/info");
    }

    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            String entityId = request.getParameter("DEF_ID");
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if (StringUtils.isEmpty(entityId)) {
                // 获取流程定义KEY
                String defKey = request.getParameter("DEF_KEY");
                boolean isExists = flowDefService.isExists(defKey);
                if (isExists) {
                    j.setSuccess(false);
                    j.setMsg("抱歉!该流程定义KEY已经存在,请更换!");
                    return j;
                }
                variables.put("VERSION", 1);
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                int version = Integer.parseInt(request.getParameter("VERSION"));
                version += 1;
                variables.put("VERSION", version);
            }
            flowDefService.deployFlow(variables, entityId);
            j.setMsg("保存成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("服务器出错,保存失败!");
        }
        return j;
    }

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "godesign")
    public ModelAndView godesign(HttpServletRequest request) {
        Map<String, Object> flowDef;
        String typeId = request.getParameter("TYPE_ID");
        String typeName = request.getParameter("TYPE_NAME");
        String defId = request.getParameter("DEF_ID");
        if (StringUtils.isNotEmpty(defId)) {
            flowDef = this.flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" }, new Object[] { defId });
            if (flowDef.get("TYPE_ID") != null) {
                // 获取类别
                Map<String, Object> flowType = flowDefService.getByJdbc("JBPM6_FLOWTYPE", new String[] { "TYPE_ID" },
                        new Object[] { flowDef.get("TYPE_ID") });
                if (flowType != null) {
                    flowDef.put("TYPE_NAME", flowType.get("TYPE_NAME"));
                }
            }
            // 获取定义的XML
            String defJson = (String) flowDef.get("DEF_JSON");
            flowDef.put("DEF_JSON", StringEscapeUtils.escapeHtml3(defJson));
            // 获取绑定的表单ID
            String bindFormId = (String) flowDef.get("BIND_FORMID");
            if (StringUtils.isNotEmpty(bindFormId)) {
                Map<String, Object> flowForm = flowDefService.getByJdbc("JBPM6_FLOWFORM", new String[] { "FORM_ID" },
                        new Object[] { bindFormId });
                flowDef.put("BIND_FORMNAME", flowForm.get("FORM_NAME"));
            }
        } else {
            flowDef = new HashMap<String, Object>();
            flowDef.put("TYPE_ID", typeId);
            flowDef.put("TYPE_NAME", typeName);
        }
        request.setAttribute("flowDef", flowDef);
        return new ModelAndView("hflow/flowdef/flowDesign");
    }

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "showDesignCode")
    public ModelAndView showDesignCode(HttpServletRequest request) {
        String operType = request.getParameter("operType");
        request.setAttribute("operType", operType);
        return new ModelAndView("hflow/flowdef/flowDesignCode");
    }

    /**
     * 调整到发起流程界面
     * 
     * @return
     */
    @RequestMapping(params = "selectDef")
    public ModelAndView selectDef(HttpServletRequest request) {
        List<Map<String, Object>> types = flowTypeService.findDefForStart(request);
        request.setAttribute("types", types);
        return new ModelAndView("hflow/flowdef/flowDefSelect");
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String defIds = request.getParameter("defIds");
        String defNames = "";
        if (StringUtils.isNotEmpty(defIds)) {
            defNames = flowDefService.getDefNames(defIds);
        }
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("defIds", defIds);
        request.setAttribute("defNames", defNames);
        return new ModelAndView("hflow/flowdef/flowDefSelector");
    }

    /**
     * 调整到发起流程界面
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "flowDefConfig")
    public ModelAndView flowDefConfig(HttpServletRequest request) {
        String defId = request.getParameter("defId");
        String version = request.getParameter("flowVersion");
        Map<String, Object> flowDef = this.flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { defId });
        if (StringUtils.isNotEmpty(version)) {
            int flowVersion = Integer.parseInt(version);
            // 获取历史流程定义数据
            Map<String, Object> history = this.histDeployService.getByJdbc("JBPM6_HIST_DEPLOY",
                    new String[] { "DEF_ID", "DEF_VERSION" }, new Object[] { defId, flowVersion });
            flowDef.put("VERSION", flowVersion);
            flowDef.put("DEF_JSON", history.get("DEF_JSON"));
        }
        // 获取流程表单的信息
        Map<String, Object> flowForm = this.flowDefService.getByJdbc("JBPM6_FLOWFORM", new String[] { "FORM_ID" },
                new Object[] { flowDef.get("BIND_FORMID") });
        String defJson = (String) flowDef.get("DEF_JSON");
        flowDef.put("DEF_JSON", StringEscapeUtils.escapeHtml3(defJson));
        String url = flowFormService.getUrlByFlowForm(flowForm, null);
        flowDef.put("FORM_URL", url);
        request.setAttribute("flowDef", flowDef);
        return new ModelAndView("hflow/flowdef/flowDefConfig");
    }

    /**
     * 
     * 描述 获取需要展现的节点名称
     * @author Flex Hu
     * @created 2015年12月21日 下午4:17:09
     * @param currentNodeNames
     * @param overNodeNames
     * @return
     */
    private String getShowNodes(Set<String> currentNodeNames, Set<String> overNodeNames) {
        StringBuffer nodeNames = new StringBuffer("");
        for (String cName : currentNodeNames) {
            nodeNames.append(cName).append(",");
        }
        if (currentNodeNames.size() >= 1) {
            nodeNames = nodeNames.deleteCharAt(nodeNames.length() - 1);
        }
        for (String nodeName : overNodeNames) {
            nodeNames.append(nodeName).append(",");
        }
        if (overNodeNames.size() >= 1) {
            nodeNames = nodeNames.deleteCharAt(nodeNames.length() - 1);
        }
        if (nodeNames.length() > 1) {
            return nodeNames.toString();
        } else {
            return null;
        }
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "flowimg")
    public ModelAndView flowimg(HttpServletRequest request) {
        // 获取流程定义ID
        String defId = request.getParameter("defId");
        // 获取流程实例申报号
        String exeId = request.getParameter("exeId");
        int flowVersion = 0;
        String defXml = "";
        String diskPath = "";
        Map<String, Set<String>> showNodeNames = new HashMap<String, Set<String>>();
        Set<String> currentNodeNamesSet = new HashSet<String>();
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> flowExe = executionService.getByJdbc(
                    "1".equals(request.getParameter("isFiled")) ? "JBPM6_EXECUTION_EVEHIS" : "JBPM6_EXECUTION",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
            // 获取历史部署数据
            Map<String, Object> histDeploy = histDeployService.getByJdbc("JBPM6_HIST_DEPLOY",
                    new String[] { "DEF_ID", "DEF_VERSION" }, new Object[] { defId, flowVersion });
            defXml = (String) histDeploy.get("DEF_XML");
            // 获取当前环节节点名称
            String currentNodeNames = (String) flowExe.get("CUR_STEPNAMES");
            if (StringUtils.isNotEmpty(currentNodeNames)) {
                for (String nodeName : currentNodeNames.split(",")) {
                    currentNodeNamesSet.add(nodeName);
                }
                String code = StringUtil.getMd5Encode(currentNodeNames);
                diskPath = "webpage/hflow/flowimages/" + defId + flowVersion + code + ".png";
            } else {
                diskPath = "webpage/hflow/flowimages/" + defId + flowVersion + ".png";
            }
            // 获取已走的节点名称
            Set<String> overedNodeNames1 = flowTaskService.findTaskNames(exeId, "2");
            // 定义
            Set<String> overNodeNames = new HashSet<String>();
            if (overedNodeNames1 != null && overedNodeNames1.size() > 0) {
                if (currentNodeNamesSet != null && currentNodeNamesSet.size() > 0) {
                    for (String overNodeName : overedNodeNames1) {
                        if (!currentNodeNamesSet.contains(overNodeName)) {
                            overNodeNames.add(overNodeName);
                        }
                    }
                }
                showNodeNames.put(AllConstant.COLOR_BLUE, overNodeNames);
            }
            if (currentNodeNamesSet != null && currentNodeNamesSet.size() > 0) {
                showNodeNames.put(AllConstant.COLOR_GREEN, currentNodeNamesSet);
            }
            // 获取需要展现的节点名称
            String changeNodeNames = this.getShowNodes(currentNodeNamesSet, overNodeNames);
            if (StringUtils.isNotEmpty(changeNodeNames)) {
                String code = StringUtil.getMd5Encode(changeNodeNames);
                diskPath = "webpage/hflow/flowimages/" + defId + flowVersion + code + ".png";
            }
        } else {
            Map<String, Object> flowDef = this.flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                    new Object[] { defId });
            flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            defXml = (String) flowDef.get("DEF_XML");
            diskPath = "webpage/hflow/flowimages/" + defId + flowVersion + ".png";
        }
        String targetPath = AppUtil.getAppAbsolutePath() + diskPath;
        if (showNodeNames.size() >= 1) {
            flowDefService.genFlowImgToDisk(defXml, targetPath, showNodeNames);
            // flowDefService.genFlowImgToDisk(defXml,currentNodeNamesSet, targetPath);
        } else {
            flowDefService.genFlowImgToDisk(defXml, targetPath, null);
            // flowDefService.genFlowImgToDisk(defXml,null, targetPath);
        }
        request.setAttribute("EFLOW_IMGPATH", diskPath);
        return new ModelAndView("hflow/flowdef/flowimg");
    }

    /**
     * 版本管理界面
     * 
     * @return
     */
    @RequestMapping(params = "versionManager")
    public ModelAndView versionManager(HttpServletRequest request) {
        String defId = request.getParameter("defId");
        request.setAttribute("defId", defId);
        return new ModelAndView("hflow/flowdef/flowVersionView");
    }

    /**
     * 
     * 
     * 加载选择器信息
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = flowDefService.findIdAndName();
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("DEF_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 (部门服务事项流程选择器)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "deptDefSelector")
    public ModelAndView deptDefSelector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String defIds = request.getParameter("defIds");
        String defNames = "";
        if (StringUtils.isNotEmpty(defIds)) {
            defNames = flowDefService.getDefNames(defIds);
        }
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("defIds", defIds);
        request.setAttribute("defNames", defNames);
        return new ModelAndView("hflow/flowdef/flowDefSelectorForDept");
    }

    /**
     * 
     * 
     * 加载选择器信息
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadForDept")
    public void loadForDept(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = flowDefService.findIdAndNameForDept();
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("DEF_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
