/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowconfig.service.FlowConfigDistributeService;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

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
 * 
 * 描述：工程建设项目流程分发管理Controller
 * 
 * @author Rider Chen
 * @created 2020年9月8日 下午3:07:37
 */
@Controller
@RequestMapping("/flowConfigDistributeController")
public class FlowConfigDistributeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowConfigDistributeController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowConfigDistributeService flowConfigDistributeService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 引入projectWebsiteApplyService
     */
    @Resource
    private ProjectWebsiteApplyService projectWebsiteApplyService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * 
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;

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
        flowConfigDistributeService.remove("T_FLOW_CONFIG_DISTRIBUTE", "ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 工程建设项目流程分发管理记录", SysLogService.OPERATE_TYPE_DEL);
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
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> flowConfigDistribute = flowConfigDistributeService.getByJdbc("T_FLOW_CONFIG_DISTRIBUTE",
                    new String[] { "ID" }, new Object[] { entityId });
            request.setAttribute("flowConfigDistribute", flowConfigDistribute);
        }
        return new ModelAndView("flowconfig/flowConfigDistribute/info");
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
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = flowConfigDistributeService.saveOrUpdate(variables, "T_FLOW_CONFIG_DISTRIBUTE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 工程建设项目流程分发管理记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 工程建设项目流程分发管理记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到事项材料列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/applyItemMaterList")
    public ModelAndView applyItemMaterList(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String exeId = request.getParameter("exeId");// 获取流程申报号
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");// 投资项目编号（工程建设项目申报）
        String itemCode = request.getParameter("itemCode");// 获取服务事项编码
        String STAGE_ID = request.getParameter("STAGE_ID");// 阶段ID
        String isWebsite = request.getParameter("isWebsite");// 引入标识
        Map<String, Object> execution = null;
        int flowVersion = 0;
        // 是否归档流程查看
        String isFiled = request.getParameter("isFiled");
        String currentNodeName = null;
        String busRecordId = "", busTableName = "";
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        if (null != serviceItem) {
            setMatersAttribute(request, exeId, PROJECT_CODE, STAGE_ID, execution, flowVersion, isFiled, currentNodeName,
                    busRecordId, busTableName, serviceItem);
        }
        request.setAttribute("isWebsite", isWebsite);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("bsdt/applyform/gcjsxm/applyItemMaterList");
    }

    /**
     * 
     * 描述： 设置办件材料参数
     * 
     * @author Rider Chen
     * @created 2020年9月9日 下午2:48:55
     * @param request
     * @param exeId
     * @param PROJECT_CODE
     * @param STAGE_ID
     * @param execution
     * @param flowVersion
     * @param isFiled
     * @param currentNodeName
     * @param busRecordId
     * @param busTableName
     * @param serviceItem
     */
    @SuppressWarnings("unchecked")
    private void setMatersAttribute(HttpServletRequest request, String exeId, String PROJECT_CODE, String STAGE_ID,
            Map<String, Object> execution, int flowVersion, String isFiled, String currentNodeName, String busRecordId,
            String busTableName, Map<String, Object> serviceItem) {
        String formKey = (String) serviceItem.get("FORM_KEY");
        String itemId = (String) serviceItem.get("ITEM_ID");// 获取项目ID
        // 获取材料信息列表 仅获取事项材料列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        // 获取材料业务办理子项分类列表
        List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeList(itemId);
        List<Map<String, Object>> filterApplyMaters = new ArrayList<Map<String, Object>>();
        if (applyMaters != null && applyMaters.size() > 0) {
            filterApplyMaters.addAll(applyMaters);
        }
        if (StringUtils.isNotEmpty(exeId)) {
            String tableName = "JBPM6_EXECUTION";
            if ("1".equals(isFiled)) {
                tableName = "JBPM6_EXECUTION_EVEHIS";
            }
            execution = applyMaterService.getByJdbc(tableName, new String[] { "EXE_ID" }, new Object[] { exeId });
            if (null != execution) {
                if (execution.get("PROJECT_CODE") != null) {
                    /* 工程建设项目查看是否有国标行业与产业指导目录字段 */
//                    execution = projectWebsiteApplyService.checkProjectIndustry(exeId, execution);
                    PROJECT_CODE = execution.get("PROJECT_CODE").toString();
                    if (STAGE_ID == null || "".equals(STAGE_ID)) {
                        Map<String, Object> stageInfo = projectWebsiteApplyService.findStageInfo(exeId, PROJECT_CODE);
                        if (stageInfo != null && stageInfo.get("STAGE_ID") != null) {
                            STAGE_ID = stageInfo.get("STAGE_ID").toString();
                        }
                    }
                    List<Map<String, Object>> materList = projectWebsiteApplyService.findMaterList(STAGE_ID,
                            PROJECT_CODE);
                    if (materList != null && materList.size() > 0) {
                        request.setAttribute("materListValue", true);
                        request.setAttribute("materList", materList);
                    }
                } // 获取流程状态
                String runStatus = execution.get("RUN_STATUS").toString();
                if (!runStatus.equals("0")) {
                    // 获取当前流程流转节点
                    currentNodeName = (String) execution.get("CUR_STEPNAMES");
                }
                if (flowVersion == 0) {
                    String defKey = (String) serviceItem.get("DEF_KEY");// 获取流程定义KEY
                    Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_KEY" },
                            new Object[] { defKey });
                    flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
                    String defId = (String) flowDef.get("DEF_ID");
                    currentNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                }
                String defId = (String) execution.get("DEF_ID");
                Map<String, Object> nodeConfig = nodeConfigService.getByJdbc("JBPM6_NODECONFIG",
                        new String[] { "NODE_NAME", "DEF_ID", "DEF_VERSION" },
                        new Object[] { currentNodeName, defId, flowVersion });
                if (nodeConfig != null) {
                    request.setAttribute("nodeConfig", nodeConfig);
                }

                //busRecordId = execution.get("BUS_RECORDID") == null ? "" : execution.get("BUS_RECORDID").toString();// 获取业务ID
                //busTableName = execution.get("BUS_TABLENAME") == null ? "" : execution.get("BUS_TABLENAME").toString();// 获取业务表名称
            }
        }
        if (StringUtils.isNotEmpty(currentNodeName) && StringUtils.isNotEmpty(exeId) && applyMaters != null
                && applyMaters.size() > 0) {
            String sysUserName = AppUtil.getLoginUser() == null ? "" : AppUtil.getLoginUser().getUsername();
            List<Map<String, Object>> filterMater = applyMaterService.findFilterMater(sysUserName, currentNodeName,
                    exeId);
            if (filterMater != null && filterMater.size() > 0) {
                applyMaters.clear();
                for (int i = 0; i < filterMater.size(); i++) {
                    Map<String, Object> m = filterMater.get(i);
                    for (int j = 0; j < filterApplyMaters.size(); j++) {
                        Map<String, Object> fmap = filterApplyMaters.get(j);
                        if (m.get("MATER_ID").toString().equals(fmap.get("MATER_ID").toString())) {
                            applyMaters.add(fmap);
                        }
                    }
                }
            }
        }
//        List<Map<String, Object>> ysqMaters = null;
//        if (StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(busTableName)) {
//            // 根据业务表名和id获取该办件已收取的电子档材料附件
//            ysqMaters = fileAttachService.findYsqByList(busTableName, busRecordId);
//        }
        // 获取流程实例信息
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            applyMaters = getFlowCaseInfo(execution, request, applyMaters);
//            ysqMaters = getFlowCaseInfo(execution, request, ysqMaters);
        }
        // 定义材料列表JSON
//        String ysqMatersJson = "";
//        if (ysqMaters != null && ysqMaters.size() > 0) {
//            ysqMatersJson = JsonUtil.jsonStringFilter(ysqMaters, new String[] { "uploadFiles" }, true);
//        }
//        if (ysqMaters != null && ysqMaters.size() > 0) {
//            request.setAttribute("ysqMatersValue", true);
//        }
//        request.setAttribute("ysqMaters", ysqMaters);
//        request.setAttribute("ysqMatersJson", StringEscapeUtils.escapeHtml3(ysqMatersJson));
        // 开始设定工程建设项目相同附件描述智能回填
        if (null == execution && StringUtils.isNotEmpty(PROJECT_CODE)) {
            applyMaterService.setSameUploadFiles(applyMaters, formKey, PROJECT_CODE);
            applyMaterService.setSameKeyUploadFiles(applyMaters, formKey, PROJECT_CODE);
        }
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("handleTypes", handleTypes);
        request.setAttribute("serviceItem", serviceItem);
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
    }

    /**
     * 设置值
     * 
     * @param execution
     * @param request
     * @param applyMaters
     * @return
     */
    public List<Map<String, Object>> getFlowCaseInfo(Map<String, Object> execution, HttpServletRequest request,
            List<Map<String, Object>> applyMaters) {
        String busRecordId = (String) execution.get("BUS_RECORDID");// 获取业务ID
        String busTableName = (String) execution.get("BUS_TABLENAME");// 获取业务表名称
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);

        return applyMaters;
    }

    /**
     * 
     * 描述：根据办件流水号获取下一步审核人信息
     * 
     * @author Rider Chen
     * @created 2020年9月10日 上午10:31:56
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getNextsteps")
    public void getNextsteps(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> flowVars = new HashMap<String, Object>();
        Map<String, Object> flowexe = serviceItemService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { flowexe.get("DEF_ID") });
        String defId = flowDef.get("DEF_ID").toString();
        String nodeName = flowexe.get("CUR_STEPNAMES").toString();
        int flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
        flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));
        flowVars.put("EFLOW_DEFID", defId);
        flowVars.put("ITEM_CODE", flowexe.get("ITEM_CODE"));
        List<Map<String, Object>> nextNodes = flowNodeService.findNextFlowNodes(defId, nodeName, flowVersion);
        List<FlowNextStep> nextTrans = jbpmService.findNextSteps(defId, nodeName, flowVersion, flowVars, null,
                nextNodes);
        data.put("nextTrans", nextTrans);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述：根据办件流水号、事项编码获取材料信息列表
     * 
     * @author Rider Chen
     * @created 2020年9月10日 下午4:07:32
     * @param request
     * @param response
     */
    @RequestMapping("/getMatersList")
    public void getMatersList(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        String itemCode = request.getParameter("itemCode");
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        String itemId = (String) serviceItem.get("ITEM_ID");// 获取项目ID
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        String json = JSON.toJSONString(applyMaters);
        this.setJsonString(json, response);
    }
}
