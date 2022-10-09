/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowconfig.dao.FlowConfigDistributeDao;
import net.evecom.platform.flowconfig.service.FlowConfigDistributeService;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.controller.ProjectApplyController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 工程建设项目流程分发管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings("rawtypes")
@Service("flowConfigDistributeService")
public class FlowConfigDistributeServiceImpl extends BaseServiceImpl implements FlowConfigDistributeService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowConfigDistributeServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private FlowConfigDistributeDao dao;
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> afterDistributeStart(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        // 主流程流水号
        String exeId = flowDatas.get("EFLOW_EXEID") == null ? "" : flowDatas.get("EFLOW_EXEID").toString();
        String ITEM_CODE = flowDatas.get("ITEM_CODE") == null ? "" : flowDatas.get("ITEM_CODE").toString();
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        // 子流程事项编码
        String S_ITEM_CODE = flowDatas.get("S_ITEM_CODE") == null ? "" : flowDatas.get("S_ITEM_CODE").toString();
        Map<String, Object> distribute = null;
        // 删除流程分发配置表数据
        dao.remove("T_FLOW_CONFIG_DISTRIBUTE", "M_EXE_ID", new Object[] { exeId });
        if (StringUtils.isNotEmpty(S_ITEM_CODE)) {
            String[] itemCodes = S_ITEM_CODE.split(",");
            for (String s_itemCode : itemCodes) {
                // 获取事项信息
                Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(s_itemCode);
                if (null != serviceItem) {
                    flowDatas.remove("EFLOW_EXEID");
                    flowDatas.remove("busRecord[EXE_ID]");
                    flowDatas.remove("EFLOW_BUSRECORDID");
                    Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                            new String[] { "DEF_ID" }, new Object[] { serviceItem.get("BDLCDYID") });
                    flowDatas.put("EFLOW_DEFKEY", serviceItem.get("DEF_KEY"));
                    flowDatas.put("EFLOW_BUSTABLENAME", serviceItem.get("FORM_KEY"));
                    flowDatas.put("EFLOW_DEFID", serviceItem.get("BDLCDYID"));
                    flowDatas.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));
                    flowDatas.put("ITEM_NAME", serviceItem.get("ITEM_NAME"));
                    flowDatas.put("ITEM_CODE", serviceItem.get("ITEM_CODE"));
                    flowDatas.put("SSBMBM", serviceItem.get("SSBMBM"));
                    flowDatas.put("SOURCE", "自动分发启动");
                    String SQRMC = flowDatas.get("SQRMC").toString();
                    flowDatas.put("EFLOW_SUBJECT", SQRMC + "-" + serviceItem.get("ITEM_NAME"));
                    flowDatas.put("SUBJECT", SQRMC + "-" + serviceItem.get("ITEM_NAME"));

                    // 获取当前运行节点
                    String currentOperNodeName = flowNodeService.getNodeName(serviceItem.get("BDLCDYID").toString(),
                            Integer.valueOf(flowDef.get("VERSION").toString()), Jbpm6Constants.START_NODE);
                    flowDatas.put("EFLOW_CUREXERUNNINGNODENAMES", currentOperNodeName);
                    flowDatas.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);

                    if (StringUtils.isNotEmpty(submitMaterFileJson)) {
                        String newSubmitMaterFileJson = getSubmitMaterFileJson("", submitMaterFileJson, s_itemCode);
                        flowDatas.put("EFLOW_SUBMITMATERFILEJSON", newSubmitMaterFileJson);
                    }
                    String nextStepJson = jbpmService.getNextStepsJson(serviceItem.get("BDLCDYID").toString(),
                            Integer.valueOf(flowDef.get("VERSION").toString()), currentOperNodeName, flowDatas);
                    if (StringUtils.isNotEmpty(nextStepJson)) {
                        flowDatas.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                    }
                    try {
                        Map<String, Object> resultMap = jbpmService.doFlowJob(flowDatas);
                        String s_exeId = (String) resultMap.get("EFLOW_EXEID");
                        distribute = new HashMap<String, Object>();
                        distribute.put("M_EXE_ID", exeId);
                        distribute.put("M_ITEM_CODE", ITEM_CODE);
                        distribute.put("S_EXE_ID", s_exeId);
                        distribute.put("S_ITEM_CODE", s_itemCode);
                        distribute.put("STAGE_ID", flowDatas.get("STAGE_ID"));
                        distribute.put("PROJECT_CODE", flowDatas.get("PROJECT_CODE"));
                        distribute.put("TYPE_ID", flowDatas.get("TYPE_ID"));
                        dao.saveOrUpdate(distribute, "T_FLOW_CONFIG_DISTRIBUTE", null);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        log.error("自动分发流程启动报错", e);
                    }
                }
            }
        }
        return flowDatas;
    }
    /**
     * 
     * 描述：根据事项编码获取上传材料信息
     * 
     * @author Rider Chen
     * @created 2020年9月9日 上午9:42:01
     * @param flowDatas
     * @param submitMaterFileJson
     * @param itemCode
     * @return
     */
    private String getSubmitMaterFileJson(String exeId, String submitMaterFileJson, String itemCode) {
        // 获取材料信息列表 仅获取事项材料列表
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        String itemId = serviceItem.get("ITEM_ID").toString();
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        List<Map> fileList = JSON.parseArray(submitMaterFileJson, Map.class);
        List<Map> newFileList = new ArrayList<Map>();
        if (null != applyMaters && applyMaters.size() > 0) {
            for (Map<String, Object> map : applyMaters) {
                String MATER_CODE = map.get("MATER_CODE").toString();
                for (Map fileMap : fileList) {
                    String ATTACH_KEY = fileMap.get("ATTACH_KEY").toString();
                    if (MATER_CODE.equals(ATTACH_KEY)) {
                        newFileList.add(fileMap);
                    }
                }
            }
        }
        String newSubmitMaterFileJson = JSON.toJSONString(newFileList);
        return newSubmitMaterFileJson;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> afterDistributePreAudit(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        String EFLOW_CURRENTTASK_ID = flowDatas.get("EFLOW_CURRENTTASK_ID") == null ? ""
                : flowDatas.get("EFLOW_CURRENTTASK_ID").toString();
        String EFLOW_HANDLE_OPINION = flowDatas.get("EFLOW_HANDLE_OPINION") == null ? ""
                : flowDatas.get("EFLOW_HANDLE_OPINION").toString();
        Map<String, Object> flowTask = this.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                new Object[] { EFLOW_CURRENTTASK_ID });
        String ASSIGNER_CODE = flowTask.get("ASSIGNER_CODE") == null ? "" : flowTask.get("ASSIGNER_CODE").toString();
        String ASSIGNER_NAME = flowTask.get("ASSIGNER_NAME") == null ? "" : flowTask.get("ASSIGNER_NAME").toString();
        String DISTRIBUTE_JSON = flowDatas.get("DISTRIBUTE_JSON") == null ? ""
                : flowDatas.get("DISTRIBUTE_JSON").toString();
        if (StringUtils.isNotEmpty(DISTRIBUTE_JSON)) {
            List<Map> distributeList = JSON.parseArray(DISTRIBUTE_JSON, Map.class);
            for (Map map : distributeList) {
                String AUDITDISTRIBUTE_RESULTS = map.get("AUDITDISTRIBUTE_RESULTS") == null ? ""
                        : map.get("AUDITDISTRIBUTE_RESULTS").toString();
                if (StringUtils.isNotEmpty(AUDITDISTRIBUTE_RESULTS) && AUDITDISTRIBUTE_RESULTS.equals("1")) {// 预审通过
                    flowAutoProcess(map,EFLOW_HANDLE_OPINION, ASSIGNER_CODE, ASSIGNER_NAME);
                } else if (StringUtils.isNotEmpty(AUDITDISTRIBUTE_RESULTS) && AUDITDISTRIBUTE_RESULTS.equals("0")) {// 预审不通过
                    preNoPass(map, ASSIGNER_CODE, ASSIGNER_NAME);
                }
            }
        }
        return flowDatas;
    }
    
    /**
     * 
     * 描述： 流程自动流转
     * 
     * @author Rider Chen
     * @created 2020年8月28日 上午10:53:49
     * @param exeId
     */
    @SuppressWarnings({ "unchecked" })
    private void flowAutoProcess(Map map,String HANDLE_OPINION, String ASSIGNER_CODE, String ASSIGNER_NAME) {
        String exeId = map.get("AUDITDISTRIBUTE_EXEID").toString();
        Map<String, Object> flowexe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        Map<String, Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { flowexe.get("DEF_ID") });
        String itemCode = (String) flowexe.get("ITEM_CODE");
        String curStepNames = (String) flowexe.get("CUR_STEPNAMES");

        Map<String, Object> flowTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE", "ASSIGNER_NAME", "TASK_STATUS" },
                new Object[] { exeId, curStepNames, ASSIGNER_CODE, ASSIGNER_NAME, "1" });
        Map<String, Object> flowVars = new HashMap<String, Object>();
        flowVars.put("EFLOW_EXEID", exeId);

        flowVars.put("EFLOW_DEFKEY", flowDef.get("DEF_KEY"));
        flowVars.put("EFLOW_DEFID", flowDef.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));

        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", flowexe.get("CUR_STEPNAMES"));
        flowVars.put("EFLOW_CURUSEROPERNODENAME", flowexe.get("CUR_STEPNAMES"));
        flowVars.put("ITEM_CODE", itemCode);
        flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
        flowVars.put("EFLOW_CURRENTTASK_ID", flowTask.get("TASK_ID"));
        flowVars.put("EFLOW_HANDLE_OPINION", HANDLE_OPINION);

        flowVars.put("EFLOW_CREATORNAME", ASSIGNER_NAME);

        // 用于判断是否来源与接口调用的办件过程执行
        flowVars.put("DATAFROM", "webservice");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        flowVars.put("EXECUTE_TIME", df.format(new Date()));
        try {
            Map<String, Object> nextMap = new HashMap<String, Object>();
            String nextNodeName = map.get("AUDITDISTRIBUTE_FLOWNODENAME").toString();
            String handlers = map.get("AUDITDISTRIBUTE_HANDLERS").toString();
            if (StringUtils.isNotEmpty(handlers)) {
                nextMap.put(nextNodeName, JSON.parseArray(handlers, Map.class));
                if (nextMap.size() > 0) {
                    String nextStepJson = JSON.toJSONString(nextMap);
                    flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                }
            }
            jbpmService.doFlowJob(flowVars);
        } catch (Exception e) {
            log.error("流程自动流转失败", e);
        }

    }
    
    /**
     * 
     * 描述：预审不通过
     * @author Rider Chen
     * @created 2020年9月10日 下午3:24:18
     * @param map
     * @param EFLOW_HANDLE_OPINION
     * @param ASSIGNER_CODE
     * @param ASSIGNER_NAME
     */
    @SuppressWarnings("unchecked")
    private void preNoPass(Map map,String ASSIGNER_CODE, String ASSIGNER_NAME) {
        Map<String, Object> variables = new HashMap<String, Object>();
        try {
            String exeId = map.get("AUDITDISTRIBUTE_EXEID").toString();
            String EFLOW_HANDLE_OPINION = map.get("HANDLE_OPINION").toString();
            Map<String, Object> flowexe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String curStepNames = (String) flowexe.get("CUR_STEPNAMES");
            Map<String, Object> flowTask = this.getByJdbc("JBPM6_TASK",
                    new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE", "ASSIGNER_NAME", "TASK_STATUS" },
                    new Object[] { exeId, curStepNames, ASSIGNER_CODE, ASSIGNER_NAME, "1" });
            variables.put("EFLOW_CURRENTTASK_ID", flowTask.get("TASK_ID"));
            variables.put("EFLOW_EXEID", exeId);
            variables.put("EFLOW_HANDLE_OPINION", EFLOW_HANDLE_OPINION);
            variables.put("BJCLLB", map.get("BJCLLB"));
            variables.put("EFLOW_APPMATERFILEJSON", "");
            variables.put("EFLOW_ISTEMPSAVE", "-1");
            executionService.updateExeToNoPass(exeId, EFLOW_HANDLE_OPINION, variables);
        } catch (Exception e) {
            log.error("流程自动流转失败",e);
        }
    }
}
