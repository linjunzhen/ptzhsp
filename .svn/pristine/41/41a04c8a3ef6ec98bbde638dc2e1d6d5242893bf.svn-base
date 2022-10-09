/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.bsfw.service.EsuperResService;
import net.evecom.platform.bsfw.service.SwbDataResService;
import net.evecom.platform.hflow.dao.JbpmDao;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.ButtonRightService;
import net.evecom.platform.hflow.service.ExePassInfoService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FieldRightService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowFormService;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeAuditerService;
import net.evecom.platform.hflow.service.QueryButtonAuthService;
import net.evecom.platform.hflow.service.SendTaskNoticeService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.model.GcjsPushStatusFactory;
import net.evecom.platform.project.model.GcjsPushStatusTemplate;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.website.service.WebSiteService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月6日 下午5:41:30
 */
@Service("jbpmService")
public class JbpmServiceImpl extends BaseServiceImpl implements JbpmService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(JbpmServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private JbpmDao dao;
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * nodeAuditerService
     */
    @Resource
    private NodeAuditerService nodeAuditerService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * flowFormService
     */
    @Resource
    private FlowFormService flowFormService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;
    /**
     * buttonRightService
     */
    @Resource
    private ButtonRightService buttonRightService;
    /**
     * fieldRightService
     */
    @Resource
    private FieldRightService fieldRightService;
    /***
     * webSiteService
     */
    @Resource
    private WebSiteService webSiteService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * sendTaskNoticeService
     */
    @Resource
    private SendTaskNoticeService sendTaskNoticeService;
    /**
     * 
     */
    @Resource
    private SwbDataResService swbDataResService;
    /**
     * 
     */
    @Resource
    private QueryButtonAuthService queryButtonAuthService;
    /**
     * 
     */
    @Resource
    private ExePassInfoService exePassInfoService;
    /**
     * 
     */
    @Resource
    private EsuperResService esuperResService;
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 根据定义的JSON获取相同节点名称的节点
     * @author Flex Hu
     * @created 2015年8月7日 上午8:35:47
     * @param defJson
     * @return
     */
    public String getSameNode(String defJson){
        Map<String,Object> map = JSON.parseObject(defJson,Map.class);
        JSONArray nodeDataArray =  (JSONArray) map.get("nodeDataArray");
        //获取节点类型
        Set<String> nodeNames = new HashSet<String>();
        String sameNodeName = "";
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> data = (Map<String, Object>) nodeDataArray.get(i);
            //获取节点类型
            String nodeType = (String) data.get("nodeType");
            //获取节点名称
            String nodeName = (String) data.get("text");
            if(nodeType.equals("start")||nodeType.equals("task")){
                if(nodeNames.contains(nodeName)){
                    sameNodeName = nodeName;
                    break;
                }else{
                    nodeNames.add(nodeName);
                }
            }
        }
        return sameNodeName;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月14日 下午3:01:32
     * @param flowVars
     * @return
     */
    public Map<String,Object> getStartFlowUser(Map<String,Object> flowVars){
        return null;
    }
    
    /**
     * 
     * 描述 动态调用事件代码
     * @author Flex Hu
     * @created 2015年8月18日 下午2:17:24
     * @param flowVars
     * @param eventType
     * @return
     */
    private Map<String,Object> invokeEventCode(Map<String,Object> flowVars,String eventType) throws Exception{
        //获取事件是否调用标记
        String invokeFlag = "";
        //获取流程版本号
        int flowVersion = Integer.parseInt(flowVars.get("EFLOW_DEFVERSION").toString());
        if(eventType.equals(Jbpm6Constants.EVENTTYPE_FRONT)){
            invokeFlag = (String) flowVars.get("EFLOW_INVOKEFRONTCODE");
        }else if(eventType.equals(Jbpm6Constants.EVENTTYPE_BUS)){
            invokeFlag = (String) flowVars.get("EFLOW_INVOKEBUSSAVE");
        }else if(eventType.equals(Jbpm6Constants.EVENTTYPE_BACK)){
            invokeFlag = (String) flowVars.get("EFLOW_INVOKEBACKCODE");
        }
        if(!(StringUtils.isNotEmpty(invokeFlag)&&invokeFlag.equals("-1"))){
            List<String> exeCodeList = flowEventService.findEventCodeList((String)flowVars.get("EFLOW_DEFID"),
                    (String)flowVars.get("EFLOW_CURUSEROPERNODENAME"),eventType,flowVersion);
            for (String exeCode:exeCodeList) {
                if(StringUtils.isNotEmpty(exeCode)){
                    String[] beanMethods = exeCode.split("[.]");
                    String beanId = beanMethods[0];
                    String method = beanMethods[1];
                    Object serviceBean = AppUtil.getBean(beanId);
                    if (serviceBean != null) {
                        Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                                new Class[] {Map.class});
                        flowVars = (Map<String, Object>) invokeMethod.invoke(serviceBean,
                                new Object[] {flowVars});
                    }
                }
            }
        }
        return flowVars;
    }
    
    /**
     * 
     * 描述 进行流程动作的执行
     * @author Flex Hu
     * @created 2015年8月18日 上午10:31:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> doFlowJob(Map<String,Object> flowVars) throws Exception{
        //开始调用前置事件代码接口----------------------------------------
        flowVars = this.invokeEventCode(flowVars, Jbpm6Constants.EVENTTYPE_FRONT);
        //全程网办-签章状态判断
        if(flowVars.get("SIGN_FLAG")!=null && !(boolean)flowVars.get("SIGN_FLAG")){
            flowVars.put("OPER_SUCCESS", false);
            flowVars.put("SIGN_MSG", flowVars.get("SIGN_MSG"));
            throw new Exception(StringUtil.getString(flowVars.get("SIGN_MSG")));
        }
        //结束调用前置事件代码接口-----------------------------------------
        //---------------开始调用缺省业务数据存储接口-----------------------
        if(flowVars!=null){
            flowVars = this.invokeEventCode(flowVars, Jbpm6Constants.EVENTTYPE_BUS);
        }
        //---------------结束调用缺省业务数据存储接口-----------------------
        //开始调用保存流程实例API
        flowVars = executionService.doSaveExecution(flowVars);
        //结束调用保存流程实例API
        //开始调用指派流程任务API
        //获取是否是暂存操作
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE");
        if(isTempSave.equals("-1")){
            flowVars = flowTaskService.assignFlowTask(flowVars);
            //开始更新流程实例数据
            flowVars = executionService.updateFlowExe(flowVars);
            String EFLOW_APPMATERFILEJSON = (String) flowVars.get("EFLOW_APPMATERFILEJSON");
            if(StringUtils.isNotEmpty(EFLOW_APPMATERFILEJSON)&&!EFLOW_APPMATERFILEJSON.equals("[]")){
                fileAttachService.saveAuditerFiles(EFLOW_APPMATERFILEJSON,flowVars);
            }
        }
        //结束调用指派流程任务API
        //发送短信
        String isSendMessage = flowVars.get("isSendMessage")==null?
                "":flowVars.get("isSendMessage").toString();
        if (isSendMessage.isEmpty()||"1".endsWith(isSendMessage)) {
            sendTaskNoticeService.sendMobileMsg(flowVars);
        }
        //保存传阅人信息,获取传阅人IDS
        String EFLOW_PERULA_IDS = (String) flowVars.get("EFLOW_PERULA_IDS");
        if(StringUtils.isNotEmpty(EFLOW_PERULA_IDS)&&!"undefined".equals(EFLOW_PERULA_IDS)){
            //获取流程实例ID
            String exeId = (String) flowVars.get("EFLOW_EXEID");
            exePassInfoService.saveExePassInfo(EFLOW_PERULA_IDS, exeId);
        }
        //---------------开始调用后置事件代码接口-------------------------
        if(flowVars!=null){
            flowVars = this.invokeEventCode(flowVars, Jbpm6Constants.EVENTTYPE_BACK);
        }
        //---------------结束调用后置事件代码接口-------------------------
        //开始保存指令表数据
        swbDataResService.saveSwbDataRes(flowVars);
        //结束保存指令表数据
        //开始保存电子监察数据
        //esuperResService.saveEsuperRes(flowVars);
        //结束保存电子监察数据
        
        //开始保存工程建设项目审批事项办理详细信息
        projectApplyService.saveAfterToXmspsxblxxxxb(flowVars);
        //结束保存工程建设项目审批事项办理详细信息
        return flowVars;
    }
    
    /**
     * 
     * 描述 获取任务节点下一环节信息
     * @author Flex Hu
     * @created 2015年8月19日 下午9:24:46
     * @param nodeName
     * @param nextNodeName
     * @param defId
     * @param flowVars
     * @return
     */
    private FlowNextStep getTaskNodeNextStep(String nodeName,String nextNodeName,
            String defId,Map<String,Object> flowVars){
        FlowNextStep nextStep = new FlowNextStep();
        //获取版本号
        int flowVersion = Integer.parseInt(flowVars.get("EFLOW_DEFVERSION").toString());
        //获取流程节点审核人配置
        Map<String,Object> nodeAuditConfig = nodeAuditerService.getNodeAuditer(nodeName,
                nextNodeName, defId,flowVersion);
        //获取节点类型
        String AUDITER_TYPE = (String) nodeAuditConfig.get("AUDITER_TYPE");
        nextStep.setFlowNodeName(nextNodeName);
        flowVars.put("EFLOW_NEXTNODENAME", nextNodeName);
        if(AUDITER_TYPE.equals(Jbpm6Constants.AUDITTYPE_SELECTOR)){
            nextStep.setSelectorName((String)nodeAuditConfig.get("CONFIG_NAME"));
            nextStep.setSelectorVars((String)nodeAuditConfig.get("VARS_VALUE"));
            nextStep.setSelectorRule((String)nodeAuditConfig.get("AUDITER_RULE"));
            nextStep.setSelectorUrl((String)nodeAuditConfig.get("SELECTOR_URL"));
            nextStep.setSelectorHeight(Integer.parseInt(nodeAuditConfig.get("SELECTOR_HEIGHT").toString()));
            nextStep.setSelectorWeight(Integer.parseInt(nodeAuditConfig.get("SELECTOR_WIDTH").toString()));
            String userAccounts = (String)nodeAuditConfig.get("VARS_VALUE");
            if(StringUtils.isNotEmpty(userAccounts)){
                List<FlowNextHandler> handlers = sysUserService.getHandlers(userAccounts);
                if(handlers!=null&&handlers.size()>0){
                    StringBuffer flowHandlerNames = new StringBuffer("");
                    for(FlowNextHandler handler:handlers){
                        flowHandlerNames.append(handler.getNextStepAssignerName()).append(",");
                    }
                    flowHandlerNames.deleteCharAt(flowHandlerNames.length()-1);
                    nextStep.setFlowHandlerNames(flowHandlerNames.toString());
                    nextStep.setHandlers(handlers);
                }
            }
        }else if(AUDITER_TYPE.equals(Jbpm6Constants.AUDITTYPE_INTERFACE)){
            List<FlowNextHandler> handlers = nodeAuditerService.findNextHandler(flowVars,nodeAuditConfig);
            StringBuffer flowHandlerNames = new StringBuffer("");
            if(handlers.size()>0){
                for(FlowNextHandler handler:handlers){
                    flowHandlerNames.append(handler.getNextStepAssignerName()).append(",");
                }
                flowHandlerNames.deleteCharAt(flowHandlerNames.length()-1);
            }else{
                flowHandlerNames.append("未找到环节审核人");
            }
            nextStep.setFlowHandlerNames(flowHandlerNames.toString());
            nextStep.setHandlers(handlers);
        }
        return nextStep;
    }
    /**
     * 
     * 描述 获取决策节点下一环节信息
     * @author Flex Hu
     * @created 2015年8月19日 下午9:28:03
     * @param defId
     * @param nextNodeName
     * @param flowVars
     * @param flowVersion
     * @return
     */
    private List<Map<String,Object>> getDecidedNodeNextStep(String defId,String nextNodeName,
            Map<String,Object> flowVars,int flowVersion){
        List<String> exeCodeList = flowEventService.findEventCodeList(defId,
                nextNodeName.trim(),Jbpm6Constants.EVENTTYPE_DECIDE,flowVersion);
        for (String exeCode:exeCodeList) {
            if(StringUtils.isNotEmpty(exeCode)){
                String[] beanMethods = exeCode.split("[.]");
                String beanId = beanMethods[0];
                String method = beanMethods[1];
                Object serviceBean = AppUtil.getBean(beanId);
                if (serviceBean != null) {
                    Method invokeMethod;
                    try {
                        invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                                new Class[] {Map.class});
                        Set<String> targetNodeNames = (Set<String>) invokeMethod.invoke(serviceBean,
                                new Object[] {flowVars});
                        List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
                        for(String targetNodeName:targetNodeNames){
                            Map<String,Object> flowNode = flowNodeService.
                                    getFlowNode(defId, flowVersion, targetNodeName);
                            nodeList.add(flowNode);
                        }
                        return nodeList;
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        return null;
                    }
                }
            }
        }

        return null;
    }
    
    /**
     * 
     * 描述 获取下一环节信息
     * @author Flex Hu
     * @created 2015年8月19日 下午5:03:19
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FlowNextStep> findNextSteps(String defId,String nodeName,int flowVersion,
            Map<String,Object> flowVars,List<FlowNextStep> nextTrans,List<Map<String,Object>> nextNodes){
        if(nextTrans==null){
            nextTrans = new ArrayList<FlowNextStep>();
        }
        for(Map<String,Object> nextNode:nextNodes){
            String nodeType = (String) nextNode.get("NODE_TYPE");
            String nextNodeName = (String) nextNode.get("NODE_NAME");
            if(nodeType.equals(Jbpm6Constants.TASK_NODE)||nodeType.equals(Jbpm6Constants.START_NODE)){
                FlowNextStep nextStep = this.getTaskNodeNextStep(nodeName, nextNodeName, defId, flowVars);
                //并行任务判断根据业务办理筛选下一步并行任务环节
                if (flowVars.get("ISPARALLEL") != null && "true".equals(flowVars.get("ISPARALLEL"))
                        && flowVars.get("nextNodeNames") != null) {
                    //表单提交根据业务办理项筛选下一步并行任务环节参数
                    String nextNodeNames = (String) flowVars.get("nextNodeNames");
                    String[] nextNodeNameArr = nextNodeNames.split(",");
                    if(!Arrays.asList(nextNodeNameArr).contains(nextStep.getFlowNodeName())){
                        continue;
                    }
                }
                nextTrans.add(nextStep);
            }else if(nodeType.equals(Jbpm6Constants.DECISION_NODE)){
                List<Map<String,Object>> nodeList = this.getDecidedNodeNextStep(defId,
                        nextNodeName, flowVars, flowVersion);
                return this.findNextSteps(defId, nodeName, flowVersion, flowVars, nextTrans, nodeList);
            }else if(nodeType.equals(Jbpm6Constants.PARALLEL_NODE)){
                flowVars.put("ISPARALLEL","true");
                List<Map<String,Object>> parallelNodes = flowNodeService.
                        findNextFlowNodes(defId, nextNodeName, flowVersion);
                return this.findNextSteps(defId, nodeName, flowVersion, flowVars, nextTrans, parallelNodes);
            }else if(nodeType.equals(Jbpm6Constants.JOIN_NODE)){
                flowVars.put("EFLOW_NEXTISJOIN", "true");
                flowVars.put("EFLOW_NEXTJOINNODENAME", nextNodeName);
                List<Map<String,Object>> joinNodes = flowNodeService.
                        findNextFlowNodes(defId, nextNodeName, flowVersion);
                return this.findNextSteps(defId, nodeName, flowVersion, flowVars, nextTrans, joinNodes);
            }else if(nodeType.equals(Jbpm6Constants.END_NODE)){
                FlowNextStep nextStep = new FlowNextStep();
                nextStep.setIsEndNode("true");
                nextStep.setFlowNodeName(nextNodeName);
                nextTrans.add(nextStep);
            }
        }
        return nextTrans;
    }
    
    /**
     * 
     * 描述 将环节审核转换成单人审核就往下执行任务
     * @author Flex Hu
     * @created 2015年8月20日 下午4:12:52
     * @param nextSteps
     * @return
     */
    public List<FlowNextStep> convertTaskToSingle(String nodeName,List<FlowNextHandler> handlers){
        FlowNextStep step = new FlowNextStep();
        step.setFlowNodeName(nodeName);
        step.setHandlers(handlers);
        step.setTaskNature(Jbpm6Constants.TASKNATURE_TEAM);
        step.setIsFree(-1);
        List<FlowNextStep> steps = new ArrayList<FlowNextStep>();
        steps.add(step);
        return steps;
    }
    
    
    /**
     * 
     * 描述 将环节审核转换成部门审批(自由流转)
     * @author Flex Hu
     * @created 2015年8月24日 下午5:54:49
     * @param nodeName
     * @param handlers
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FlowNextStep> conventTaskToMulitDep(String nodeName,List<FlowNextHandler> handlers){
        List<FlowNextStep> steps = new ArrayList<FlowNextStep>();
        //定义部门MAP
        Map<String,List<FlowNextHandler>> depMap = new HashMap<String,List<FlowNextHandler>>();
        for(FlowNextHandler handle:handlers){
            Map<String,Object> userInfo = dao.getByJdbc("T_MSJW_SYSTEM_SYSUSER",new String[]{"USERNAME"},
                    new Object[]{handle.getNextStepAssignerCode()});
            String depId = (String) userInfo.get("DEPART_ID");
            List<FlowNextHandler> handlerList = depMap.get(depId);
            if(!(handlerList!=null&&handlerList.size()>0)){
                handlerList = new ArrayList<FlowNextHandler>();
            }
            if(!handlerList.contains(handle)){
                handlerList.add(handle);
            }
            depMap.put(depId, handlerList);
        }
        Iterator it = depMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,List<FlowNextHandler>> entry = (Map.Entry<String,List<FlowNextHandler>>) it.next();
            List<FlowNextHandler> handlerList = entry.getValue();
            FlowNextStep step = new FlowNextStep();
            step.setFlowNodeName(nodeName);
            step.setHandlers(handlerList);
            step.setTaskNature(Jbpm6Constants.TASKNATURE_TEAM);
            step.setIsFree(-1);
            steps.add(step);
        }
        return steps;
    }
    
    /**
     * 
     * 描述 将环节审核转换成多人审核(禁止流转)
     * @author Flex Hu
     * @created 2015年8月21日 下午2:10:16
     * @param nodeName
     * @param handlers
     * @return
     */
    public List<FlowNextStep> conventTaskToMulitNoFree(String nodeName,List<FlowNextHandler> handlers){
        List<FlowNextStep> steps = new ArrayList<FlowNextStep>();
        for(FlowNextHandler handler:handlers){
            List<FlowNextHandler> newHandlers = new ArrayList<FlowNextHandler>();
            newHandlers.add(handler);
            FlowNextStep step = new FlowNextStep();
            step.setFlowNodeName(nodeName);
            step.setHandlers(newHandlers);
            step.setTaskNature(Jbpm6Constants.TASKNATURE_SIGNLER);
            step.setIsFree(-1);
            steps.add(step);
        }
        return steps;
    }
    
    /**
     * 
     * 描述  将环节审核转换成多人审核(自由流转)
     * @author Flex Hu
     * @created 2015年8月21日 下午2:19:11
     * @param nodeName
     * @param handlers
     * @return
     */
    public List<FlowNextStep> conventTaskToMulitFree(String nodeName,List<FlowNextHandler> handlers){
        List<FlowNextStep> steps = new ArrayList<FlowNextStep>();
        for(FlowNextHandler handler:handlers){
            List<FlowNextHandler> newHandlers = new ArrayList<FlowNextHandler>();
            newHandlers.add(handler);
            FlowNextStep step = new FlowNextStep();
            step.setFlowNodeName(nodeName);
            step.setHandlers(newHandlers);
            step.setTaskNature(Jbpm6Constants.TASKNATURE_SIGNLER);
            step.setIsFree(1);
            steps.add(step);
        }
        return steps;
    }
    
    
    /**
     * 
     * 描述 启动流程
     * @author Flex Hu
     * @created 2015年8月26日 下午3:46:22
     * @param flowVars
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> exeFlow(Map<String,Object> flowVars) throws Exception{
        //获取当前任务ID
        String EFLOW_CURRENTTASK_ID = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        //获取流程版本号
        int flowVersion = 0;
        String defId = "";
        String currentOperNodeName = "";
        if(StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)){
            flowVersion = Integer.parseInt(flowVars.get("EFLOW_DEFVERSION").toString());
            defId = (String) flowVars.get("EFLOW_DEFID");
            currentOperNodeName = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
        }else{
            //获取流程定义KEY
            String defKey = (String) flowVars.get("EFLOW_DEFKEY");
            //获取流程定义信息
            Map<String,Object> flowDef = flowDefService.
                    getByJdbc("JBPM6_FLOWDEF",new String[]{"DEF_KEY"},new Object[]{defKey});
            //获取流程定义ID
            defId = (String) flowDef.get("DEF_ID");
            //获取流程版本号
            flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            //获取所绑定的表单信息
            Map<String,Object> flowForm = flowFormService.getByJdbc("JBPM6_FLOWFORM",new String[]{"FORM_ID"},
                new Object[]{flowDef.get("BIND_FORMID")});
            //定义当前任务审核人操作的节点名称
            currentOperNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
            //定义当前实例正在允许的任务节点
            String currentRunningNodeNames = currentOperNodeName;
            flowVars.put("EFLOW_BUSTABLENAME", (String)flowForm.get("FORM_KEY"));
            flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", currentRunningNodeNames);
            flowVars.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);
            flowVars.put("EFLOW_DEFID", defId);
            flowVars.put("EFLOW_DEFVERSION", flowVersion);
            flowVars.put("EFLOW_ISQUERYDETAIL","false");
            
            //2016年11月21日 16:06:28 Rider Chen更改            
            //获取是否是暂存操作
            String isTempSave = (null == flowVars.get("EFLOW_ISTEMPSAVE") ? "-1"
                    : flowVars.get("EFLOW_ISTEMPSAVE").toString());
            flowVars.put("EFLOW_ISTEMPSAVE",isTempSave);
        }
        String nextStepJsonString = this.getNextStepsJson(defId, flowVersion, currentOperNodeName, flowVars);
        flowVars.put("EFLOW_NEXTSTEPSJSON",nextStepJsonString);
        return this.doFlowJob(flowVars);
    }
    
    /**
     * 
     * 描述 获取下一环节的相关经办人信息JSON
     * @author Flex Hu
     * @created 2016年1月26日 上午11:29:28
     * @param defId
     * @param flowVersion
     * @param nodeName
     * @return
     */
    public String getNextStepsJson(String defId,int flowVersion,String currentOperNodeName,Map<String,Object> flowVars){
        List<Map<String,Object>> nextNodes = flowNodeService.findNextFlowNodes(defId,currentOperNodeName, flowVersion);
        List<FlowNextStep> nextTrans = this.findNextSteps(defId, currentOperNodeName,
                flowVersion, flowVars, null, nextNodes);
        Map<String,Object> nextMap = new HashMap<String,Object>();
        for(FlowNextStep nextTran:nextTrans){
            String nextNodeName = nextTran.getFlowNodeName();
            if(nextTran.getHandlers()!=null&&nextTran.getHandlers().size()>0){
                nextMap.put(nextNodeName,nextTran.getHandlers());
            }
        }
        if(nextMap.size()>0){
            String jsonString = JSON.toJSONString(nextMap);
            return jsonString;
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 启动流程
     * @author Flex Hu
     * @created 2015年8月26日 下午3:46:22
     * @param flowVars
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> startFlow(Map<String,Object> flowVars) throws Exception{
        //获取流程定义KEY
        String defKey = (String) flowVars.get("EFLOW_DEFKEY");
        //获取流程定义信息
        Map<String,Object> flowDef = flowDefService.
                getByJdbc("JBPM6_FLOWDEF",new String[]{"DEF_KEY"},new Object[]{defKey});
        //获取流程定义ID
        String defId = (String) flowDef.get("DEF_ID");
        //获取流程版本号
        int flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
        //获取所绑定的表单信息
        Map<String,Object> flowForm = flowFormService.getByJdbc("JBPM6_FLOWFORM",new String[]{"FORM_ID"},
            new Object[]{flowDef.get("BIND_FORMID")});
        //定义当前任务审核人操作的节点名称
        String currentOperNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
        //定义当前实例正在允许的任务节点
        String currentRunningNodeNames = currentOperNodeName;
        if(flowVars.get("EFLOW_BUSTABLENAME") == null){
            flowVars.put("EFLOW_BUSTABLENAME", (String)flowForm.get("FORM_KEY"));
        }
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", currentRunningNodeNames);
        flowVars.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);
        flowVars.put("EFLOW_DEFID", defId);
        flowVars.put("EFLOW_DEFVERSION", flowVersion);
        flowVars.put("EFLOW_ISQUERYDETAIL","false");
        flowVars.put("EFLOW_ISTEMPSAVE","-1");
        List<Map<String,Object>> nextNodes = flowNodeService.findNextFlowNodes(defId,currentOperNodeName, flowVersion);
        List<FlowNextStep> nextTrans = this.findNextSteps(defId, currentOperNodeName,
                flowVersion, flowVars, null, nextNodes);
        Map<String,Object> nextMap = new HashMap<String,Object>();
        for(FlowNextStep nextTran:nextTrans){
            String nextNodeName = nextTran.getFlowNodeName();
            nextMap.put(nextNodeName,nextTran.getHandlers());
        }
        String jsonString = JSON.toJSONString(nextMap);
        flowVars.put("EFLOW_NEXTSTEPSJSON",jsonString);
        return this.doFlowJob(flowVars);
    }

    /**
     * 
     * 描述 退回补件
     * @author Faker Li
     * @created 2015年11月30日 下午4:28:07
     * @param variables
     * @return
     * @throws Exception
     * @see net.evecom.platform.hflow.service.JbpmService#doBjFlowJob(java.util.Map)
     */
    public Map<String, Object> doBjFlowJob(Map<String, Object> flowVars) throws Exception {
        flowVars = this.doFlowJob(flowVars);
        bjxxService.removeByExeid((String) flowVars.get("EFLOW_EXEID"));
        fileAttachService.updateFileShzt("1", flowVars,null);
        if(!StringUtils.isEmpty(flowVars.get("BJCLLB").toString())){
           
           /* if(flowVars.get("APPLY_STATUS").toString().equals("1")){
                executions.put("APPLY_STATUS", "7");
            }else{
                executions.put("APPLY_STATUS", "4");    
            }*/
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("EXE_ID", (String) flowVars.get("EFLOW_EXEID"));
            variables.put("TASK_ID", (String) flowVars.get("EFLOW_CURRENTTASK_ID"));
            variables.put("BJCLLB", (String) flowVars.get("BJCLLB"));
            variables.put("SFFDX", "1");//默认发送短信
            variables.put("BJYJ", (String) flowVars.get("EFLOW_HANDLE_OPINION"));
            bjxxService.saveOrUpdate(variables, "T_WSBS_BJXX","");
            List bjclList = JSON.parseArray((String)flowVars.get("BJCLLB"), Map.class);
            if(bjclList.size()>0){
                for (int i = 0; i < bjclList.size(); i++) {
                    Map<String, Object> e = (Map<String, Object>) bjclList.get(i);
                    fileAttachService.updateFileShzt("-1", flowVars,(String) e.get("MATER_CODE"));
                }  
            }
           
        }
        
        Map<String, Object> executions = new HashMap<String, Object>();
        executions.put("EXE_ID", (String) flowVars.get("EFLOW_EXEID"));
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        Map<String,Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION", 
                new String[]{"EXE_ID"}, new Object[]{exeId});
        String sqfs = (String)exeMap.get("SQFS");
        if(StringUtils.isNotEmpty(sqfs)&&sqfs.equals("1")&&flowVars.get("APPLY_STATUS").toString().equals("1")){
             executions.put("APPLY_STATUS", "4");
        }else{
             executions.put("APPLY_STATUS", "7"); 
        }
        //更新流程实例表 把申报状态改为 收件补件
        executionService.saveOrUpdate(executions, "JBPM6_EXECUTION", (String) flowVars.get("EFLOW_EXEID"));
        
        //执行退回挂起代码 EFLOW_NEWTASK_PARENTIDS
        Map<String, Object> flowHangInfo = new HashMap<String, Object>();
        flowHangInfo.put("TASK_ID",(String)flowVars.get("EFLOW_NEWTASK_PARENTIDS"));
        String beginTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        flowHangInfo.put("BEGIN_TIME", beginTime);
        flowTaskService.handUpFlowTask((String) flowVars.get("EFLOW_EXEID"),
                    (String)flowVars.get("EFLOW_NEWTASK_PARENTIDS"));
//        flowTaskService.returnFlowTask((String) flowVars.get("EFLOW_EXEID"),
//                (String)flowVars.get("EFLOW_NEWTASK_PARENTIDS"));
        String EFLOW_ISBACK_PATCH = (String) flowVars.get("EFLOW_ISBACK_PATCH");
        flowHangInfo.put("EFLOW_ISBACK_PATCH", EFLOW_ISBACK_PATCH);
        String recordId = flowHangInfoService.saveOrUpdate(flowHangInfo, "JBPM6_HANGINFO", "");
        //保存计时暂停指令数据
        this.saveHandUpDataRes(recordId, exeId);

        // 开始保存工程建设项目审批事项办理详细信息
        // 获取意见内容
        String EFLOW_HANDLE_OPINION = (String) flowVars.get("EFLOW_HANDLE_OPINION");
        if(StringUtils.isEmpty(EFLOW_HANDLE_OPINION)){
            EFLOW_HANDLE_OPINION = "退回补件";
        }
        String userFullName = "";
        //判断是否定时器执行自动退件
        String type = StringUtil.getString(flowVars.get("type"));
        if("auto".equals(type)){
          userFullName = flowVars.get("ASSIGNER_NAME") == null ? "" : flowVars.get("ASSIGNER_NAME").toString();
        }else{
            // 获取当前用户信息
            SysUser curUser = AppUtil.getLoginUser();
            if (null != curUser) {
                userFullName = curUser.getFullname();
            } else {
                // 通过接口传输
                userFullName = flowVars.get("ASSIGNER_NAME") == null ? "" : flowVars.get("ASSIGNER_NAME").toString();
            } 
        }

        GcjsPushStatusTemplate gcjsPushStatusTemplate= GcjsPushStatusFactory.getGcjsPushStatusTemplage(flowVars);
        //如果流程是新定义的流程模板，走新的推送逻辑，不然走原来的推送逻辑
        if(Objects.nonNull(gcjsPushStatusTemplate)){
            projectApplyService.saveAfterToXmspsxblxxxxb(6, exeId, userFullName, EFLOW_HANDLE_OPINION);
        }else{
            projectApplyService.saveAfterToXmspsxblxxxxb(9, exeId, userFullName, EFLOW_HANDLE_OPINION);
        }
        // 结束保存工程建设项目审批事项办理详细信息
        return flowVars;
    }
    
    /**
     * 
     * 保存计时暂停指令数据
     * @author Danto Huang
     * @created 2019年5月10日 上午11:19:49
     * @param hangInfoId
     * @param exeId
     */
    private void saveHandUpDataRes(String hangInfoId,String exeId){
        Map<String, Object> handUpDataRes = new HashMap<String, Object>();
        handUpDataRes.put("EXE_ID", exeId);
        handUpDataRes.put("DATA_TYPE", "21");
        handUpDataRes.put("OPER_TYPE", "I");
        handUpDataRes.put("HAS_ATTR", 0);
        handUpDataRes.put("INTER_TYPE", "10");
        handUpDataRes.put("TASK_ID", hangInfoId);
        handUpDataRes.put("OTHER_STATUS", 1);
        flowTaskService.saveOrUpdate(handUpDataRes, "T_BSFW_SWBDATA_RES", null);
    }
    
    /**
     * 
     * 描述 获取流程信息对象信息
     * @author Flex Hu
     * @created 2015年12月4日 上午9:54:17
     * @param defKey
     * @param exeId
     * @param request
     * @param isQueryDetail
     * @return
     */
    public Map<String,Object> getFlowAllObj(String defKey,String exeId,String isQueryDetail,HttpServletRequest request){
        //定义流程信息全部对象
        Map<String,Object> flowAllObj = new HashMap<String,Object>();
        //开始定义流程信息对象
        Map<String,Object> eflowObj = new HashMap<String,Object>();
        //获取流程实例信息
        Map<String,Object> flowExe = null;
        //获取流程定义信息
        Map<String,Object> flowDef = null;
        //定义流程变量MAP
        Map<String,Object> flowVars = BeanUtil.getMapFromRequest(request);
        // 是否归档流程查看
        String isFiled = request.getParameter("isFiled");
        int flowVersion = 0;
        if(StringUtils.isNotEmpty(exeId)){
            String tableName = "JBPM6_EXECUTION";
            if("1".equals(isFiled)) {
                tableName = "JBPM6_EXECUTION_EVEHIS";
            }
            flowExe = executionService.getByJdbc(tableName, new String[]{"EXE_ID"},new Object[]{exeId});
            flowDef = flowDefService.
                    getByJdbc("JBPM6_FLOWDEF",new String[]{"DEF_ID"},new Object[]{flowExe.get("DEF_ID")});
            flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
        }else{
            //获取流程定义信息
            flowDef = flowDefService.
                getByJdbc("JBPM6_FLOWDEF",new String[]{"DEF_KEY"},new Object[]{defKey});
          //获取流程版本号
            flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
        }
        //获取流程定义ID
        String defId = (String) flowDef.get("DEF_ID");
        //获取所绑定的表单信息
        Map<String,Object> flowForm = flowFormService.getByJdbc("JBPM6_FLOWFORM",new String[]{"FORM_ID"},
            new Object[]{flowDef.get("BIND_FORMID")});
        //定义当前任务审核人操作的节点名称
        String currentOperNodeName = null;
        //定义当前实例正在允许的任务节点
        String currentRunningNodeNames = null;
        //如果实例申报号为空,那么说明是发起流程
        if(StringUtils.isNotEmpty(exeId)){
            //获取当前任务环节名称
            String currentNodeNames = (String) flowExe.get("CUR_STEPNAMES");
            if(StringUtils.isNotEmpty(currentNodeNames)){
                //获取流程任务ID
                String taskId = request.getParameter("taskId");
                if(StringUtils.isNotEmpty(taskId)){
                    eflowObj.put("EFLOW_CURRENTTASK_ID", taskId);
                    Map<String,Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                            new String[]{"TASK_ID"},new Object[]{taskId});
                    //获取任务名称
                    currentOperNodeName = (String) flowTask.get("TASK_NODENAME");
                }
                currentRunningNodeNames = currentNodeNames; 
                eflowObj = webSiteService.getFlowCurNodeAndOper(flowExe, eflowObj);
            }else{
                currentOperNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                currentRunningNodeNames = currentOperNodeName;
            }
        }else{
            currentOperNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
            currentRunningNodeNames = currentOperNodeName;
        }
        //获取分配的按钮权限值
        List<Map<String,Object>> buttonRights = null;
        //获取表单字段的权限控制值
        List<Map<String,Object>> fieldRights = null;
        //获取查询明细时的按钮权限控制
        List<Map<String,Object>> queryButtonRights = null;
        if(isQueryDetail.equals("false")){
            buttonRights = buttonRightService.findList(currentOperNodeName,defId,true,flowVersion);
            buttonRights = buttonRightService.getFilterAuthList(buttonRights, flowVars);
            fieldRights = fieldRightService.findList(defId, currentOperNodeName,flowVersion);
        }else{
            queryButtonRights = queryButtonAuthService.findList(defId, flowVersion);
        }
        eflowObj.put("EFLOW_DEFID", defId);
        eflowObj.put("EFLOW_DEFKEY", (String)flowDef.get("DEF_KEY"));
        eflowObj.put("EFLOW_DEFVERSION", flowVersion);
        eflowObj.put("EFLOW_CUREXERUNNINGNODENAMES", currentRunningNodeNames);
        eflowObj.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);
        eflowObj.put("EFLOW_BUSTABLENAME", (String)flowForm.get("FORM_KEY"));
        eflowObj.put("currentNodeFieldRights",fieldRights);
        eflowObj.put("EFLOW_ISQUERYDETAIL", isQueryDetail);
        if(flowExe!=null){
            //开始获取业务数据
            //获取业务表名称
            String busTableName = (String)flowExe.get("BUS_TABLENAME");
            //获取业务表记录ID
            String busRecordId = (String)flowExe.get("BUS_RECORDID");
            //内资表单，将虚拟主表名替换真实主表名称
            if(busTableName.equals("T_COMMERCIAL_DOMESTIC")){
                busTableName = "T_COMMERCIAL_COMPANY";
            }
            //内资表单，将虚拟主表名替换真实主表名称
            if(busTableName.equals("T_COMMERCIAL_FOREIGN")){
                busTableName = "T_COMMERCIAL_COMPANY";
            }
            //食药监保健食品生产虚拟主表替换真实表名称
            if(busTableName.equals("T_FJFDA_SPSCXK_JBXX1")){
                busTableName = "T_FJFDA_SPSCXK_JBXX";
            }
            //国有转移7个事项虚拟主表替换真实表名称
            if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                busTableName = "T_BDCQLC_GYJSJFWZYDJ";
            }
            // 获取业务表的主键名称
            String pkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> busRecord = executionService.getByJdbc(busTableName, new String[] { pkName },
                    new Object[] { busRecordId });
            busRecord.putAll(flowExe);
            // 获取多证合一数据
            Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                    new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("BUS_RECORDID") });
            if (null != multiple) {
                busRecord.putAll(multiple);
            }
            //设置商事变更业务数据
            flowAllObj.put("busRecord", busRecord);
            flowAllObj.put("EFLOW_FLOWEXE", flowExe);
            eflowObj.put("busRecord", busRecord);
            eflowObj.put("EFLOW_EXEID", exeId);
            eflowObj.put("EFLOW_BUSRECORDID", busRecordId);
            eflowObj.put("EFLOW_EXERUNSTATUS", flowExe.get("RUN_STATUS"));
            eflowObj.put("EFLOW_CREATORID", flowExe.get("CREATOR_ID"));
            eflowObj.put("EFLOW_CREATORACCOUNT", flowExe.get("CREATOR_ACCOUNT"));
            eflowObj.put("EFLOW_CREATORNAME", flowExe.get("CREATOR_NAME"));
            eflowObj.put("EFLOW_CREATORPHONE", flowExe.get("CREATOR_PHONE"));
            eflowObj.put("EFLOW_FLOWSTAGE", flowExe.get("FLOW_STAGE"));
        }
        //转换成JSON
        String flowObjJson = JSON.toJSONString(eflowObj);
        flowAllObj.put("EFLOWOBJ", eflowObj);
        flowAllObj.put("EFLOW_FLOWOBJ", StringEscapeUtils.escapeHtml3(flowObjJson));
        flowAllObj.put("EFLOW_BUTTONRIGHTS", buttonRights);
        flowAllObj.put("EFLOW_FLOWDEF", flowDef);
        flowAllObj.put("EFLOW_FLOWFORM", flowForm);
        flowAllObj.put("EFLOW_QUERYBTNRIGHTS", queryButtonRights);
        return flowAllObj;
    }
    /**
     * 
     * 描述 前台提交流程
     * @author Faker Li
     * @created 2016年5月4日 下午2:11:56
     * @param flowVars
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> exeFrontFlow(Map<String,Object> flowVars) throws Exception{
        String EFLOW_CURRENTTASK_ID =(String) flowVars.get("EFLOW_CURRENTTASK_ID");
        if(StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)){
             flowTaskService.startUpFlowTask(EFLOW_CURRENTTASK_ID);
             flowHangInfoService.endHangTime(EFLOW_CURRENTTASK_ID);
        }
        return this.exeFlow(flowVars);
    }
}
