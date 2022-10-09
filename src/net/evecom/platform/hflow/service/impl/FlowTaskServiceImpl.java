/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.dao.FlowTaskDao;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.model.FromTask;
import net.evecom.platform.hflow.service.*;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.zzhy.model.TableNameEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述 流程任务操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("flowTaskService")
public class FlowTaskServiceImpl extends BaseServiceImpl implements FlowTaskService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowTaskServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private FlowTaskDao dao;
    /**
     * nodeConfigService
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
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
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    /**
     * 引入Service
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * flowEventService
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    
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
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        dao.deleteByDefId(defId);
    }

    /**
     *
     * 描述 获取当前正在运行的流程任务列表,只包含被监控的任务
     * @author Flex Hu
     * @created 2015年8月22日 上午9:14:33
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findRunningTask(String exeId){
        StringBuffer sql = new StringBuffer("SELECT TASK_NODENAME,T.TEAM_CODES,T.TEAM_NAMES");
        sql.append(" FROM JBPM6_TASK T ");
        sql.append(" WHERE T.TASK_STATUS='1' AND T.STEP_SEQ!=0 ");
        sql.append(" AND T.EXE_ID=? ");
        return this.dao.findBySql(sql.toString(),new Object[]{exeId}, null);
    }

    /**
     *
     * 描述 描述 获取当前所有正在运行的流程任务列表
     * @author Flex Hu
     * @created 2015年8月22日 下午8:07:48
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findAllRunningTask(String exeId){
        StringBuffer sql = new StringBuffer("SELECT TASK_NODENAME,T.TEAM_CODES,T.TEAM_NAMES");
        sql.append(" FROM JBPM6_TASK T ");
        sql.append(" WHERE T.TASK_STATUS='1' ");
        sql.append(" AND T.EXE_ID=? ");
        return this.dao.findBySql(sql.toString(),new Object[]{exeId}, null);
    }

    /**
     *
     * 描述 保存开始任务
     * @author Flex Hu
     * @created 2015年8月20日 下午3:23:53
     * @param flowVars
     * @return
     */
    public Map<String,Object> saveFlowStartTask(Map<String,Object> flowVars){
        Map<String,Object> flowTask = new HashMap<String,Object>();
        //获取流程申报号
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        //获取流程定义ID
        String defId = (String) flowVars.get("EFLOW_DEFID");
        //获取当前操作环节名称
        String curOperNodeName = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
        String assignerCode = (String) flowVars.get("EFLOW_CREATORACCOUNT");
        String assignerName = (String) flowVars.get("EFLOW_CREATORNAME");
        String assignerPhone =  (String) flowVars.get("EFLOW_CREATORPHONE");
        String taskStatus = Jbpm6Constants.TASKSTATUS_YSH;
        int taskNature = Jbpm6Constants.TASKNATURE_SIGNLER;
        flowTask.put("EXE_ID", exeId);
        flowTask.put("DEF_ID", defId);
        flowTask.put("TASK_NODENAME", curOperNodeName);
        flowTask.put("TEAM_CODES", assignerCode);
        flowTask.put("TEAM_NAMES", assignerName);
        flowTask.put("ASSIGNER_CODE", assignerCode);
        flowTask.put("ASSIGNER_NAME", assignerName);
        flowTask.put("ASSIGNER_PHONE", assignerPhone);
        flowTask.put("TASK_STATUS", taskStatus);
        flowTask.put("TASK_NATURE", taskNature);
        flowTask.put("IS_MONITOR", 1);
        flowTask.put("STEP_SEQ", 1);
        //判断接口数据来源时间
        if(flowVars.get("DATAFROM")!=null&&flowVars.get("DATAFROM").equals("webservice")){
            flowTask.put("CREATE_TIME",flowVars.get("EXECUTE_TIME"));
            flowTask.put("END_TIME",flowVars.get("EXECUTE_TIME"));
        }else{
            //定义开始时间
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            //定义结束时间
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            flowTask.put("CREATE_TIME", createTime);
            flowTask.put("END_TIME", endTime);
        }
        flowTask.put("WORK_HOURS", "0分钟");
        String taskId = dao.saveOrUpdate(flowTask, "JBPM6_TASK",null);
        flowTask.put("TASK_ID", taskId);
        return flowTask;
    }

    /**
     *
     * 描述 获取任务决策后的分组审核人列表
     * @author Flex Hu
     * @created 2015年8月20日 下午4:40:22
     * @param taskDecideCode
     * @param nodeName
     * @param handlers
     * @return
     */
    private List<FlowNextStep> getTaskDecidedResult(String taskDecideCode,String nodeName,
            List<FlowNextHandler> handlers,Map<String, Object> flowVars){
        String[] beanMethods = taskDecideCode.split("[.]");
        String beanId = beanMethods[0];
        String method = beanMethods[1];
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean != null) {
            Method invokeMethod;
            try {
                if(taskDecideCode.equals("commercialSetService.conventTaskToMulitItem")){
                    invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                            new Class[] { String.class,List.class,Map.class });
                    return (List<FlowNextStep>) invokeMethod.invoke(serviceBean,
                            new Object[] { nodeName, handlers, flowVars });
                }else{
                    invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                            new Class[] { String.class,List.class });
                    return (List<FlowNextStep>) invokeMethod.invoke(serviceBean, new Object[]{nodeName,handlers});
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * 描述 获取串审核调用结果
     * @author Flex Hu
     * @created 2015年9月4日 上午11:25:10
     * @param taskOrderCode
     * @param steps
     * @return
     */
    private List<FlowNextStep> getTaskOrderResult(String taskOrderCode,List<FlowNextStep> steps){
        String[] beanMethods = taskOrderCode.split("[.]");
        String beanId = beanMethods[0];
        String method = beanMethods[1];
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean != null) {
            Method invokeMethod;
            try {
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] {List.class });
                return (List<FlowNextStep>) invokeMethod.invoke(serviceBean, new Object[]{steps});
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * 描述 指派子任务
     * @author Flex Hu
     * @created 2015年8月21日 上午9:48:25
     * @param exeId
     * @param defId
     * @param handler
     * @param nextNodeName
     * @param parentTaskId
     * @return
     */
    private String assignSubTask(String exeId,String defId,FlowNextHandler handler,
            String nextNodeName,String parentTaskId,Map<String,Object> fromTask,Map<String,Object> flowVars){
        Map<String,Object> subTask = new HashMap<String,Object>();
        subTask.put("EXE_ID",exeId);
        subTask.put("DEF_ID", defId);
        subTask.put("TASK_NODENAME", nextNodeName);
        subTask.put("ASSIGNER_CODE", handler.getNextStepAssignerCode());
        subTask.put("ASSIGNER_NAME", handler.getNextStepAssignerName());
        if(StringUtils.isNotEmpty(handler.getNextStepAssignerType())){
            subTask.put("ASSIGNER_TYPE", handler.getNextStepAssignerType());
        }
        //subTask.put("ASSIGNER_MOBILE", handler.getNextStepAssignerMobile());
        if(handler.getTaskOrder()!=0){
            if(handler.getTaskOrder()==1){
                subTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
            }else{
                subTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_GQ);
            }
            subTask.put("TASK_SN", handler.getTaskOrder());
        }else{
            subTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
        }
        subTask.put("PARENT_TASKID", parentTaskId);
        subTask.put("IS_MONITOR", -1);
        subTask.put("IS_AUDITED", 1);
        subTask.put("FROMTASK_IDS", fromTask.get("FROMTASK_IDS"));
        subTask.put("FROMTASK_NODENAMES", fromTask.get("FROMTASK_NODENAMES"));
        subTask.put("FROMTASK_ASSIGNER_CODES", fromTask.get("FROMTASK_ASSIGNER_CODES"));
        subTask.put("FROMTASK_ASSIGNERNAMES", fromTask.get("FROMTASK_ASSIGNERNAMES"));
        //获取是否设置了截止日期
        String EFLOW_TASKDEADLINETIME = (String) flowVars.get("EFLOW_TASKDEADLINETIME");
        if(StringUtils.isNotEmpty(EFLOW_TASKDEADLINETIME)){
            subTask.put("TASK_DEADTIME", EFLOW_TASKDEADLINETIME);
        }else{
            //查看在配置信息是否配置了工作日限制
            String taskDeadTime = this.nodeConfigService.getDeadLineDate(nextNodeName,flowVars);
            if(StringUtils.isNotEmpty(taskDeadTime)){
                subTask.put("TASK_DEADTIME",taskDeadTime);
            }

            //判断是否为即时办理环节
            String flowVersion = String.valueOf(flowVars.get("EFLOW_DEFVERSION"));
            Map<String, Object> nodeConfig = dao.getByJdbc("JBPM6_NODECONFIG", new String[] { "NODE_NAME", "DEF_ID",
                "DEF_VERSION" }, new Object[] { nextNodeName, defId, flowVersion });
            int WORKDAY_LIMIT = Integer.parseInt(nodeConfig.get("WORKDAY_LIMIT").toString());
            String sql = "select s.sxlx from t_wsbs_serviceitem s left join jbpm6_execution e "
                    + "on e.item_code=s.item_code where e.exe_id=?";
            String sxlx = dao.getByJdbc(sql, new Object[]{exeId}).get("SXLX").toString();
            
            if("1".equals(sxlx) || WORKDAY_LIMIT==-1){
                subTask.put("IS_IMMEDIATE",1);//即办
            }else if(WORKDAY_LIMIT==0){
                subTask.put("IS_IMMEDIATE",2);//环节不计时
            }
        }
        String createTime=String.valueOf(flowVars.get("NEW_END_TIME"));
        subTask.put("CREATE_TIME", createTime);
        return dao.saveOrUpdate(subTask, "JBPM6_TASK", null);
    }

    /**
     *
     * 描述 指派流程任务给用户
     * @author Flex Hu
     * @created 2015年8月20日 下午5:24:29
     * @param decideResultSteps
     * @param fromTask
     * @param defId
     * @param exeId
     * @return
     */
    public Map<String,String> assignTaskToUsers(List<FlowNextStep> decideResultSteps,
            Map<String,Object> fromTask,String defId,String exeId,Map<String,Object> flowVars){
        Map<String,String> newTaskResults = new HashMap<String,String>();
        StringBuffer stepSeqDesc = new StringBuffer("");
        //定义下一环节审核人帐号
        StringBuffer nextStepHandlerAccounts = new StringBuffer("");
        //定义下一环节审核人名称
        StringBuffer nextStepHandlerNames =new StringBuffer("");
        //定义下一环节审核人电话
        //StringBuffer nextStepHandlerMobiles = new StringBuffer("");
        //定义新产生的流程任务ID(存储的都是父任务ID)
        StringBuffer nextStepTaskIds = new StringBuffer("");
        for(FlowNextStep step:decideResultSteps){
            //获取下一步环节名称
            String nextNodeName = step.getFlowNodeName();
            //获取下一步任务性质
            int nextTaskNature = step.getTaskNature();
            //获取下一步审核人
            List<FlowNextHandler> handlers = step.getHandlers();
            if(nextTaskNature==Jbpm6Constants.TASKNATURE_TEAM){
                //进行父任务的保存
                //获取最大步骤序列值
                int stepSeq = dao.getMaxStepSeq(exeId)+1;
                stepSeqDesc.append(stepSeq).append(",");
                //获取团队信息
                Map<String,String> teamInfo = this.getTeamInfo(handlers);
                String teamCodes = teamInfo.get("TEAM_CODES");
                //判断是否给过任务给团队
                boolean isAssignedToTeam = dao.isAssignedTeamTask(exeId, nextNodeName, teamCodes);
                if(!isAssignedToTeam){
                    String teamNames = teamInfo.get("TEAM_NAMES");
                    nextStepHandlerAccounts.append(teamCodes).append(",");
                    nextStepHandlerNames.append(teamNames).append(",");
                    //先保存父任务
                    String parentTaskId = this.saveParentTask(nextNodeName, exeId, defId,
                            teamInfo, nextTaskNature,handlers.get(0), stepSeq, fromTask, step,flowVars);
                    nextStepTaskIds.append(parentTaskId).append(",");
                    //开始保存子任务
                    for(FlowNextHandler handler:handlers){
                        //判断是否给发送任务给个人
                        boolean isAssignedToUser = dao.isAssignedUserTask(exeId, nextNodeName,
                                handler.getNextStepAssignerCode());
                        if(!isAssignedToUser){
                            this.assignSubTask(exeId, defId, handler, nextNodeName, parentTaskId,fromTask,flowVars);
                        }
                    }
                     //有特殊前置事件的下一环节需要挂起,不纳入统计时间
                     flowHangUp(flowVars,parentTaskId,nextNodeName);
                }
            }else if(nextTaskNature==Jbpm6Constants.TASKNATURE_SIGNLER){
                for(FlowNextHandler handler:handlers){
                    //判断是否给发送任务给个人
                    boolean isAssignedToUser = dao.isAssignedUserTask(exeId, nextNodeName,
                            handler.getNextStepAssignerCode());
                    if(!isAssignedToUser){
                        nextStepHandlerAccounts.append(handler.getNextStepAssignerCode()).append(",");
                        nextStepHandlerNames.append(handler.getNextStepAssignerName()).append(",");
                        //nextStepHandlerMobiles.append(handler.getNextStepAssignerMobile()).append(",");
                        Map<String,String> teamInfo = new HashMap<String,String>();
                        teamInfo.put("TEAM_CODES", handler.getNextStepAssignerCode());
                        teamInfo.put("TEAM_NAMES", handler.getNextStepAssignerName());
                        //teamInfo.put("TEAM_MOBILES", handler.getNextStepAssignerMobile());
                         //获取最大步骤序列值
                        int stepSeq = dao.getMaxStepSeq(exeId)+1;
                        stepSeqDesc.append(stepSeq).append(",");
                        String parentTaskId = this.saveParentTask(nextNodeName, exeId, defId,teamInfo,
                                nextTaskNature, handler, stepSeq, fromTask, step,flowVars);
                        nextStepTaskIds.append(parentTaskId).append(",");
                    }
                }
            }
        }
        if(stepSeqDesc.length()>=1){
            stepSeqDesc.deleteCharAt(stepSeqDesc.length()-1);
        }
        if(nextStepHandlerAccounts.length()>=1){
            nextStepHandlerAccounts.deleteCharAt(nextStepHandlerAccounts.length()-1);
        }
        if(nextStepHandlerNames.length()>=1){
            nextStepHandlerNames.deleteCharAt(nextStepHandlerNames.length()-1);
        }
        if(nextStepTaskIds.length()>=1){
            nextStepTaskIds.deleteCharAt(nextStepTaskIds.length()-1);
        }
        newTaskResults.put("NEXT_STEPSEQDESC", stepSeqDesc.toString());
        newTaskResults.put("EFLOW_NEWTASK_HANDLERCODES", nextStepHandlerAccounts.toString());
        newTaskResults.put("EFLOW_NEWTASK_HANDLERNAMES", nextStepHandlerNames.toString());
        newTaskResults.put("EFLOW_NEWTASK_PARENTIDS", nextStepTaskIds.toString());
        return newTaskResults;
    }
    /**
     *
     * 描述：前置事件为flowRestar的下一环节挂起
     * @author Water Guo
     * @created 2018-1-12 下午4:30:51
     * @param flowVars
     * @param parentTaskId  挂起的taskid
     */
    public void flowHangUp(Map<String,Object> flowVars,String parentTaskId,String nextNodeName){
        String invokeFlag = "";
        //获取流程版本号
        int flowVersion = Integer.parseInt(flowVars.get("EFLOW_DEFVERSION").toString());
        String eventType=Jbpm6Constants.EVENTTYPE_FRONT;
        invokeFlag = (String) flowVars.get("EFLOW_INVOKEFRONTCODE");
        if(!(StringUtils.isNotEmpty(invokeFlag)&&invokeFlag.equals("-1"))){
            List<String> exeCodeList = flowEventService.findEventCodeList((String)flowVars.get("EFLOW_DEFID"),
                    nextNodeName,eventType,flowVersion);
            for (String exeCode:exeCodeList) {
                if("flowTaskService.flowRestar".equals(exeCode)){
                    //flowTaskService.handUpFlowTask((String) flowVars.get("EFLOW_EXEID"),parentTaskId);
                    Map<String, Object> flowHangInfo = new HashMap<String, Object>();
                    flowHangInfo.put("TASK_ID",parentTaskId);
                    String beginTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                    flowHangInfo.put("BEGIN_TIME", beginTime);
                    dao.saveOrUpdate(flowHangInfo, "JBPM6_HANGINFO",null);
                }
            }
        }
    }
    /**
     *
     * 描述：流程重启
     * @author Water Guo
     * @created 2018-1-14 下午4:38:02
     * @param flowVars
     * @return
     */
    public Map<String,Object> flowRestar(Map<String,Object> flowVars){
        String currentTaskId=(String)flowVars.get("EFLOW_CURRENTTASK_ID");
        String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        Map<String,Object> task=this.dao.getByJdbc("JBPM6_TASK", new String[]{"TASK_ID"}, new Object[]{
                currentTaskId});
        String parentTaskId=(String)task.get("PARENT_TASKID");
        /*StringBuffer sqlTask=new StringBuffer("UPDATE JBPM6_TASK SET TASK_STATUS=1 WHERE");
        sqlTask.append(" (TASK_ID=?  OR  PARENT_TASKID=?) AND TASK_STATUS=-1");
        dao.executeSql(sqlTask.toString(), new Object[]{parentTaskId,parentTaskId});*/
        StringBuffer sqlHangUp = new StringBuffer("UPDATE JBPM6_HANGINFO T SET T.END_TIME = ? ");
        sqlHangUp.append(" WHERE T.HANG_ID IN ( ");
        sqlHangUp.append(" SELECT JH.HANG_ID FROM(SELECT * FROM JBPM6_HANGINFO HI WHERE HI.TASK_ID = ? ");
        sqlHangUp.append(" ORDER BY HI.BEGIN_TIME DESC)JH WHERE ROWNUM=1 ) ");
        dao.executeSql(sqlHangUp.toString(), new Object[]{endTime,parentTaskId});
        return flowVars;
    }
    /**
     *
     * 描述 保存父亲任务
     * @author Flex Hu
     * @created 2015年8月20日 下午8:37:00
     * @param nextNodeName
     * @param exeId
     * @param defId
     * @param taskNature
     * @param handlers
     * @param stepSeq
     * @param fromTask
     * @param step
     * @return
     */
    private String saveParentTask(String nextNodeName,String exeId,String defId,Map<String,String> teamInfo,
            int taskNature,FlowNextHandler handler,int stepSeq,Map<String,Object> fromTask,FlowNextStep step
            ,Map<String,Object> flowVars){
        Map<String,Object> parentTask = new HashMap<String,Object>();
        parentTask.put("EXE_ID",exeId);
        parentTask.put("DEF_ID", defId);
        parentTask.put("TASK_NODENAME", nextNodeName);
        parentTask.put("TEAM_CODES", teamInfo.get("TEAM_CODES"));
        parentTask.put("TEAM_NAMES", teamInfo.get("TEAM_NAMES"));
        if(taskNature==Jbpm6Constants.TASKNATURE_SIGNLER){
            parentTask.put("ASSIGNER_CODE", handler.getNextStepAssignerCode());
            parentTask.put("ASSIGNER_NAME", handler.getNextStepAssignerName());
            if(StringUtils.isNotEmpty(handler.getNextStepAssignerType())){
                parentTask.put("ASSIGNER_TYPE", handler.getNextStepAssignerType());
            }
            //parentTask.put("ASSIGNER_MOBILE",handler.getNextStepAssignerMobile());
            parentTask.put("IS_AUDITED",1);
            if(handler.getTaskOrder()!=0){
                if(handler.getTaskOrder()==1){
                    parentTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
                }else{
                    parentTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_GQ);
                }
                parentTask.put("TASK_SN", handler.getTaskOrder());
            }else{
                parentTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
            }
        }else{
            parentTask.put("IS_AUDITED",-1);
            parentTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
        }
        parentTask.put("TASK_NATURE",taskNature);
        parentTask.put("FROMTASK_IDS", fromTask.get("FROMTASK_IDS"));
        parentTask.put("FROMTASK_NODENAMES", fromTask.get("FROMTASK_NODENAMES"));
        parentTask.put("FROMTASK_ASSIGNER_CODES", fromTask.get("FROMTASK_ASSIGNER_CODES"));
        parentTask.put("FROMTASK_ASSIGNERNAMES", fromTask.get("FROMTASK_ASSIGNERNAMES"));
        if(fromTask.get(nextNodeName)!=null){
            parentTask.put("FROMTASK_JSON", fromTask.get(nextNodeName));
        }else{
            parentTask.put("FROMTASK_JSON", fromTask.get("FROMTASK_JSON"));
        }
        parentTask.put("IS_MONITOR", 1);
        parentTask.put("IS_FREE", step.getIsFree());
        parentTask.put("STEP_SEQ", stepSeq);
        //获取是否设置了截止日期
        String EFLOW_TASKDEADLINETIME = (String) flowVars.get("EFLOW_TASKDEADLINETIME");
        if(StringUtils.isNotEmpty(EFLOW_TASKDEADLINETIME)){
            parentTask.put("TASK_DEADTIME", EFLOW_TASKDEADLINETIME);
        }else{
            //查看在配置信息是否配置了工作日限制
            String taskDeadTime = this.nodeConfigService.getDeadLineDate(nextNodeName,flowVars);
            if(StringUtils.isNotEmpty(taskDeadTime)){
                parentTask.put("TASK_DEADTIME",taskDeadTime);
            }

            //判断是否为即时办理环节
            String flowVersion = String.valueOf(flowVars.get("EFLOW_DEFVERSION"));
            Map<String, Object> nodeConfig = dao.getByJdbc("JBPM6_NODECONFIG", new String[] { "NODE_NAME", "DEF_ID",
                "DEF_VERSION" }, new Object[] { nextNodeName, defId, flowVersion });
            int WORKDAY_LIMIT = Integer.parseInt(nodeConfig.get("WORKDAY_LIMIT").toString());
            String sql = "select s.sxlx from t_wsbs_serviceitem s left join jbpm6_execution e "
                    + "on e.item_code=s.item_code where e.exe_id=?";
            String sxlx = dao.getByJdbc(sql, new Object[]{exeId}).get("SXLX").toString();
            
            if("1".equals(sxlx) || WORKDAY_LIMIT==-1){
                parentTask.put("IS_IMMEDIATE",1);
            }else if(WORKDAY_LIMIT==0){
                parentTask.put("IS_IMMEDIATE",2);//环节不计时
            }
        }
        if(handler!=null&&StringUtils.isNotEmpty(handler.getNextStepAssignerType())){
            parentTask.put("ASSIGNER_TYPE", handler.getNextStepAssignerType());
        }
        //定义任务的开始时间
        String createTime=String.valueOf(flowVars.get("NEW_END_TIME"));
        parentTask.put("CREATE_TIME", createTime);
        //保存父亲任务
        String parentTaskId = dao.saveOrUpdate(parentTask,"JBPM6_TASK",null);
        return parentTaskId;
    }
    
    /**
     * 
     * 描述 获取团队信息
     * @author Flex Hu
     * @created 2015年8月20日 下午8:12:35
     * @param handlers
     * @return
     */
    private Map<String,String> getTeamInfo(List<FlowNextHandler> handlers){
        Map<String,String> teamInfo =new HashMap<String,String>();
        StringBuffer teamCodes = new StringBuffer("");
        StringBuffer teamNames = new StringBuffer("");
        for(FlowNextHandler handler:handlers){
            teamCodes.append(handler.getNextStepAssignerCode()).append(",");
            teamNames.append(handler.getNextStepAssignerName()).append(",");
        }
        teamCodes.deleteCharAt(teamCodes.length()-1);
        teamNames.deleteCharAt(teamNames.length()-1);
        teamInfo.put("TEAM_CODES", teamCodes.toString());
        teamInfo.put("TEAM_NAMES", teamNames.toString());
        return teamInfo;
    }
    
    /**
     * 
     * 描述 更新当前操作人所处理的任务的信息
     * @author Flex Hu
     * @created 2015年8月21日 上午8:53:37
     * @param assignTaskResult
     * @param currentTask
     */
    private void updateCurrentTask(Map<String,String> assignTaskResult,Map<String,Object> currentTask,
            boolean isStartFlow,Map<String,Object> flowVars){
        //获取当前任务ID
        String currentTaskId = (String) currentTask.get("TASK_ID");
        String assignerName = (String) currentTask.get("ASSIGNER_NAME");
        //String fromtaskNodenames = (String) currentTask.get("FROMTASK_NODENAMES");//TASK_STATUS = 2,
        //String taskStatus = (String) currentTask.get("TASK_STATUS");//TASK_STATUS = 2,
        //获取流程实例ID
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String nextStepDesc = "";
        //定义当前审核人操作流程任务的状态
        String curHandlerTaskStatus = Jbpm6Constants.TASKSTATUS_YSH;
        //判断当前流程是否还有运行任务
        boolean isHaveRunningTask = dao.isExistRunningTask(exeId, currentTaskId);
        if(!isHaveRunningTask){
            //说明流程结束了
            curHandlerTaskStatus = Jbpm6Constants.TASKSTATUS_JSLC;
        }
        //获取流程任务是否是回退操纵
        String EFLOW_ISBACK = (String) flowVars.get("EFLOW_ISBACK");
        if(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true")){
            curHandlerTaskStatus = Jbpm6Constants.TASKSTATUS_TH;
        }
        //获取是否有同意不同意的值
        String eflowIsAgree = (String) flowVars.get("EFLOW_ISAGREE");
        if(StringUtils.isNotEmpty(eflowIsAgree)&&eflowIsAgree.equals("-1")){
           //说明流程结束了
            curHandlerTaskStatus = Jbpm6Constants.TASKSTATUS_JSLC;
        }
        //获取通过不通过的值
        String eflowIsPass = (String) flowVars.get("EFLOW_ISPASS");
        if(StringUtils.isEmpty(eflowIsPass)){
            eflowIsPass = "1";
        }
        if(assignTaskResult!=null){
            nextStepDesc = assignTaskResult.get("NEXT_STEPSEQDESC");
        }
        //获取办理意见内容
        String handleOpinion = (String) flowVars.get("EFLOW_HANDLE_OPINION");
        //获取任务的开始时间
        String beginTime = (String) currentTask.get("CREATE_TIME");
        if(isStartFlow){
            StringBuffer exeHandleDesc = new StringBuffer("[").append(assignerName);
            exeHandleDesc.append("]提交了申请,流转到步骤[").append(nextStepDesc).append("]");
            currentTask.put("EXE_HANDLEDESC", exeHandleDesc.toString());
            currentTask.put("NEXTSTEP_DESC", nextStepDesc);
            currentTask.put("IS_REAL_HANDLED", 1);
            //获取经办人类型
            if(flowVars.get("EFLOW_ASSIGNER_TYPE")!=null){
                currentTask.put("ASSIGNER_TYPE",flowVars.get("EFLOW_ASSIGNER_TYPE"));
            }
            dao.saveOrUpdate(currentTask, "JBPM6_TASK", currentTaskId);
        }else{
            String exeHandleDescBefore = (String) currentTask.get("EXE_HANDLEDESC");
            String parentTaskId = (String) currentTask.get("PARENT_TASKID");
            StringBuffer exeHandleDesc = new StringBuffer("");
            if (StringUtils.isNotEmpty(exeHandleDescBefore)) {
                exeHandleDesc.append(exeHandleDescBefore);
                exeHandleDesc.append("\r\n");
            }
            exeHandleDesc.append("[");
            exeHandleDesc.append(assignerName);
            exeHandleDesc.append("]");
            if(curHandlerTaskStatus.equals(Jbpm6Constants.TASKSTATUS_YSH)){
                exeHandleDesc.append("已审核.");
            }else if(curHandlerTaskStatus.equals(Jbpm6Constants.TASKSTATUS_JSLC)){
                exeHandleDesc.append("办结流程.");
            }else if(curHandlerTaskStatus.equals(Jbpm6Constants.TASKSTATUS_TH)){
                exeHandleDesc.append("已退回.");
            }
            if(assignTaskResult!=null){
                exeHandleDesc.append("流转到步骤[").append(nextStepDesc).append("]");
            }
            //定义任务的结束时间
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            endTime=flowVars.get("NEW_END_TIME")==null?endTime
                    :flowVars.get("NEW_END_TIME").toString();
            //计算工时
            String workHours = DateTimeUtil.getInternalTime(beginTime, endTime);
            if(StringUtils.isNotEmpty(parentTaskId)){
                dao.updateParentTaskInfo(parentTaskId, endTime, workHours, handleOpinion,
                        curHandlerTaskStatus, exeHandleDesc.toString(),currentTaskId,eflowIsPass);
            }else{
                dao.updateTaskInfo(currentTaskId, endTime, workHours, 
                        handleOpinion,curHandlerTaskStatus, exeHandleDesc.toString(),1,eflowIsPass);
            }
        }
        if(StringUtils.isNotEmpty(eflowIsAgree)&&eflowIsAgree.equals("-1")){
            //将其它流程任务的状态进行更新
            dao.updateTask(Jbpm6Constants.TASKSTATUS_JSLC, exeId, currentTaskId);
        }
    }
    
    /**
     * 
     * 描述 获取新产生的流程任务结果Map
     * @author Flex Hu
     * @created 2015年8月21日 上午9:20:54
     * @param nextSteps
     * @param defId
     * @return
     */
    private Map<String,String> getNewTaskResultMap(Map<String,Object> nextSteps,String defId,
            String exeId,Map<String,Object> fromTask,Map<String,Object> flowVars){
        //获取下一环节是否经过同步节点
        String nextIsJoin = (String) flowVars.get("EFLOW_NEXTISJOIN");
        //获取流程版本号
        int flowVersion = Integer.parseInt(flowVars.get("EFLOW_DEFVERSION").toString());
        if(StringUtils.isNotEmpty(nextIsJoin)&&nextIsJoin.equals("true")){
            //获取是否允许发出同步任务
            String taskId = (String) fromTask.get("FROMTASK_IDS");
            String parentTaskId = (String) fromTask.get("FROMTASK_PARENTTASK_ID");
            String joinPreNodeNames = (String) flowVars.get("EFLOW_JOINPRENODENAMES");
            boolean isAllowAssignJoinTask = dao.isAllowAssignJoinNodeTask(taskId, 
                    parentTaskId, exeId, joinPreNodeNames);
            //log.info("允许的结果是："+isAllowAssignJoinTask);
            if(!isAllowAssignJoinTask){
                return null;
            }
        }
        if(nextSteps!=null){
          //定义下一环节名称
            StringBuffer nextStepNames = new StringBuffer("");
            //定义下一环节步骤描述
            StringBuffer stepSeqDesc = new StringBuffer("");
            //定义下一环节审核人帐号
            StringBuffer nextStepHandlerAccounts = new StringBuffer("");
            //定义下一环节审核人名称
            StringBuffer nextStepHandlerNames =new StringBuffer("");
            //定义新产生的流程任务ID(存储的都是父任务ID)
            StringBuffer nextStepTaskIds = new StringBuffer("");
            //定义新产生任务的返回Map
            Map<String,String> newTaskMap= new HashMap<String,String>();
            Iterator it = nextSteps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String,Object> entry = (Map.Entry<String,Object>) it.next();
                String nextNodeName = (String) entry.getKey();
                List<FlowNextHandler> handlers = JSON.parseArray(entry.getValue().toString(), FlowNextHandler.class);
                //获取任务决策代码
                String taskDecideCode = nodeConfigService.getTaskDecideCode(defId, nextNodeName,flowVersion);
                //获取分组后的任务结果
                List<FlowNextStep> decideResultSteps = this.getTaskDecidedResult(taskDecideCode, nextNodeName, handlers,
                        flowVars);
                //获取节点配置信息
                Map<String,Object> nodeConfig = nodeConfigService.getByJdbc("JBPM6_NODECONFIG",
                        new String[]{"NODE_NAME","DEF_ID","DEF_VERSION"},new Object[]{nextNodeName,defId,flowVersion});
                //获取是否串审
                int isTaskOrder = -1;
                if(nodeConfig.get("IS_TASKORDER")!=null){
                    isTaskOrder = Integer.parseInt(nodeConfig.get("IS_TASKORDER").toString());
                    if(isTaskOrder==1){
                        //获取串审接口编码
                        String taskOrderCode = (String) nodeConfig.get("TASK_ORDER_CODE");
                        decideResultSteps = this.getTaskOrderResult(taskOrderCode, decideResultSteps);
                    }
                }
                //将任务指派给用户
                Map<String, String> newTaskResult = this.assignTaskToUsers(decideResultSteps, fromTask, defId, exeId,
                        flowVars);
                //加入环节
                nextStepNames.append(nextNodeName).append(",");
                stepSeqDesc.append(newTaskResult.get("NEXT_STEPSEQDESC")).append(",");
                nextStepHandlerAccounts.append(newTaskResult.get("EFLOW_NEWTASK_HANDLERCODES")).append(",");
                nextStepHandlerNames.append(newTaskResult.get("EFLOW_NEWTASK_HANDLERNAMES")).append(",");
                nextStepTaskIds.append(newTaskResult.get("EFLOW_NEWTASK_PARENTIDS")).append(",");
            }
            if(nextStepNames.length()>=1){
                nextStepNames.deleteCharAt(nextStepNames.length()-1);
            }
            if(stepSeqDesc.length()>=1){
                stepSeqDesc.deleteCharAt(stepSeqDesc.length()-1);
            }
            if(nextStepHandlerAccounts.length()>=1){
                nextStepHandlerAccounts.deleteCharAt(nextStepHandlerAccounts.length()-1);
            }
            if(nextStepHandlerNames.length()>=1){
                nextStepHandlerNames.deleteCharAt(nextStepHandlerNames.length()-1);
            }
            if(nextStepTaskIds.length()>=1){
                nextStepTaskIds.deleteCharAt(nextStepTaskIds.length()-1);
            }
            if(nextStepHandlerAccounts.length()<1){
                return null;
            }else{
                newTaskMap.put("NEXT_STEPSEQDESC", stepSeqDesc.toString());
                newTaskMap.put("EFLOW_NEWTASK_HANDLERCODES", nextStepHandlerAccounts.toString());
                newTaskMap.put("EFLOW_NEWTASK_HANDLERNAMES", nextStepHandlerNames.toString());
                newTaskMap.put("EFLOW_NEWTASK_PARENTIDS", nextStepTaskIds.toString());
                newTaskMap.put("EFLOW_NEWTASK_NODENAMES", nextStepNames.toString());
                return newTaskMap;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 获取来源任务的JSON信息
     * @author Flex Hu
     * @created 2015年8月21日 下午6:03:41
     * @param currentTaskList
     * @return
     */
    private String getFromTaskJson(List<Map<String,Object>> currentTaskList){
        List<FromTask> taskList = new ArrayList<FromTask>();
        for(Map<String,Object> currentTask:currentTaskList){
            FromTask fromTask = new FromTask();
            fromTask.setTaskName((String)currentTask.get("FROMTASK_NODENAMES"));
            String handlerCodes = currentTask.get("FROMTASK_ASSIGNER_CODES").toString();
            String handlerNames = currentTask.get("FROMTASK_ASSIGNERNAMES").toString();
            List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
            for(int i =0;i<handlerCodes.split(",").length;i++){
                FlowNextHandler handler = new FlowNextHandler();
                handler.setNextStepAssignerCode(handlerCodes.split(",")[i]);
                handler.setNextStepAssignerName(handlerNames.split(",")[i]);
                handler.setNextStepAssignerType((String)currentTask.get("ASSIGNER_TYPE"));
                handlers.add(handler);
            }
            fromTask.setHandlers(handlers);
            taskList.add(fromTask);
        }
        String json = JSON.toJSONString(taskList);
        return json;
    }
    
    /**
     * 
     * 描述 获取当前任务列表
     * @author Flex Hu
     * @created 2015年8月21日 下午8:53:43
     * @param currentTaskId
     * @return
     */
    public List<Map<String,Object>> findCurrentTaskList(String currentTaskId){
        List<Map<String,Object>> currentTaskList = new ArrayList<Map<String,Object>>();
        //获取任务的信息
        Map<String,Object> currentTask = dao.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{currentTaskId});
        //获取父亲任务Id
        String parentTaskId = (String) currentTask.get("PARENT_TASKID");
        //先判断是不是自由流程
        String isFree = currentTask.get("IS_FREE").toString();
        if(StringUtils.isNotEmpty(parentTaskId)||(StringUtils.isEmpty(parentTaskId)&&isFree.equals("1"))){
            //说明是团队任务
            currentTask.put("FROMTASK_NODENAMES", currentTask.get("TASK_NODENAME"));
            currentTask.put("FROMTASK_ASSIGNER_CODES", currentTask.get("ASSIGNER_CODE"));
            currentTask.put("FROMTASK_ASSIGNERNAMES", currentTask.get("ASSIGNER_NAME"));
            currentTaskList.add(currentTask);
        }else{
            currentTask.put("FROMTASK_NODENAMES", currentTask.get("TASK_NODENAME"));
            //获取相同来源的任务列表
            List<Map<String,Object>> sameFromTaskList = this.findSameFromTasks(currentTaskId);
            StringBuffer handlerCodes = new StringBuffer("");
            StringBuffer handlerNames = new StringBuffer("");
            for(Map<String,Object> task:sameFromTaskList){
                handlerCodes.append(task.get("ASSIGNER_CODE")).append(",");
                handlerNames.append(task.get("ASSIGNER_NAME")).append(",");
            }
            if(handlerCodes!=null&&!"".equals(handlerCodes.toString())){
                currentTask.put("FROMTASK_ASSIGNER_CODES",handlerCodes.deleteCharAt(handlerCodes.length()-1));
            }
            if(handlerNames!=null&&!"".equals(handlerNames.toString())){
                currentTask.put("FROMTASK_ASSIGNERNAMES", handlerNames.deleteCharAt(handlerNames.length()-1));
            }
            currentTaskList.add(currentTask);
        }
        return currentTaskList;
    }
    
    /**
     * 
     * 描述 获取当前流程任务
     * @author Flex Hu
     * @created 2015年8月24日 下午5:06:33
     * @param isStartFlow
     * @param flowVars
     * @return
     */
    private Map<String,Object> getCurrentTask(boolean isStartFlow,Map<String,Object> flowVars){
        Map<String,Object> currentTask = null;
        if(isStartFlow){
            //那么先要保存开始节点的任务
            currentTask = this.saveFlowStartTask(flowVars);
            currentTask.put("FROMTASK_ASSIGNER_CODES", currentTask.get("ASSIGNER_CODE"));
            currentTask.put("FROMTASK_ASSIGNERNAMES", currentTask.get("ASSIGNER_NAME"));
            currentTask.put("FROMTASK_NODENAMES", currentTask.get("TASK_NODENAME"));
            String assignerType = (String) flowVars.get("EFLOW_ASSIGNER_TYPE");
            if(StringUtils.isNotEmpty(assignerType)){
                currentTask.put("ASSIGNER_TYPE",assignerType);
            }
        }else{
            //获取当前审核人所在的任务ID
            String currentTaskId = "";
            String  EFLOW_CURRENTTASK_ID = StringUtil.getString(flowVars.get("EFLOW_CURRENTTASK_ID"));
            if(StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)){
                currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
            }else{
              StringBuffer sql = new StringBuffer();
              sql.append(" SELECT T.TASK_ID FROM JBPM6_TASK T ");
              sql.append(" WHERE T.TASK_NODENAME = ? AND T.ASSIGNER_CODE= ? AND T.EXE_ID = ? ");
              sql.append(" ORDER BY T.CREATE_TIME DESC ");
              List<Map<String, Object>> flowTaskList = dao.findBySql(sql.toString(), 
                      new Object[]{StringUtil.getString(flowVars.get("TASK_NODENAME")),
                              StringUtil.getString(flowVars.get("ASSIGNER_CODE")),
                              StringUtil.getString(flowVars.get("EFLOW_EXEID"))}, 
                      null);
              if(flowTaskList.size()>0){//按时间顺序，取最新的审核任务
                  currentTaskId = StringUtil.getString(flowTaskList.get(0).get("TASK_ID"));
                  flowVars.put("EFLOW_CURRENTTASK_ID", currentTaskId);
              }
            }
            //获取任务的信息
            currentTask = dao.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{currentTaskId});
        }
        return currentTask;
    }
    
    /**
     * 
     * 
     * 描述 获取来源任务对象
     * @author Flex Hu
     * @created 2015年8月24日 下午4:33:51
     * @param isStartFlow
     * @param flowVars
     * @param currentTask
     * @return
     */
    private Map<String,Object> getFromTask(boolean isStartFlow,
            Map<String,Object> flowVars,Map<String,Object> currentTask,String defId,String exeId){
        //定义来源任务对象
        Map<String,Object> fromTask = new HashMap<String,Object>();
        //开始获取当前审核人的所在任务ID
        String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        if(isStartFlow){
            List<Map<String,Object>> currentTaskList = new ArrayList<Map<String,Object>>();
            currentTaskList.add(currentTask);
            String fromTaskJson = this.getFromTaskJson(currentTaskList);
            fromTask.put("FROMTASK_JSON", fromTaskJson);
        }else{
            //获取是否是退回流程操作
            String EFLOW_ISBACK = (String) flowVars.get("EFLOW_ISBACK");
            //获取是否是进行到同步节点
            String EFLOW_NEXTISJOIN = (String) flowVars.get("EFLOW_NEXTISJOIN");
            if(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true")){
                String fromTaskIds = (String) currentTask.get("FROMTASK_IDS");
                String fromTaskNames = (String) currentTask.get("FROMTASK_NODENAMES");
                String[] fromTaskIdArray = fromTaskIds.split(",");
                String[] fromTaskNameArray = fromTaskNames.split(",");
                if(fromTaskIdArray.length>1){
                    fromTask = dao.getBackTaskForm(fromTaskIdArray, fromTaskNameArray, fromTask);
                }else{
                    String fromTaskJson = dao.getBackTaskFormTaskJson(fromTaskIds);
                    fromTask.put("FROMTASK_JSON", fromTaskJson);
                }
            }else if(StringUtils.isNotEmpty(EFLOW_NEXTISJOIN)&&EFLOW_NEXTISJOIN.equals("true")){
                //获取同步节点的名称
                String joinNodeName = (String) flowVars.get("EFLOW_NEXTJOINNODENAME");
                int flowVersion= Integer.parseInt(flowVars.get("EFLOW_DEFVERSION").toString());
                Map<String,String> fromMap = this.getJoinNodeFromInfo(currentTaskId, joinNodeName, 
                        defId, flowVersion, exeId);
                String fromTaskJson = fromMap.get("FROMTASK_JSON");
                fromTask.put("FROMTASK_JSON", fromTaskJson);
                fromTask.put("FROMTASK_IDS", fromMap.get("FROMTASK_IDS"));
                fromTask.put("FROMTASK_NODENAMES",fromMap.get("FROMTASK_NODENAMES"));
            }else{
                List<Map<String,Object>> currentTaskList = this.findCurrentTaskList(currentTaskId);
                String fromTaskJson = this.getFromTaskJson(currentTaskList);
                fromTask.put("FROMTASK_JSON", fromTaskJson);
            }
        }
        if(fromTask.get("FROMTASK_IDS")==null){
            fromTask.put("FROMTASK_IDS", currentTask.get("TASK_ID"));
            fromTask.put("FROMTASK_NODENAMES",currentTask.get("TASK_NODENAME"));
        }
        fromTask.put("FROMTASK_ASSIGNER_CODES",currentTask.get("ASSIGNER_CODE"));
        fromTask.put("FROMTASK_ASSIGNERNAMES",currentTask.get("ASSIGNER_NAME"));
        fromTask.put("FROMTASK_PARENTTASK_ID", currentTask.get("PARENT_TASKID"));
        return fromTask;
    }
    
    /**
     * 
     * 描述 获取下一环节办理截止时间
     * @author Flex Hu
     * @created 2015年11月7日 上午10:38:32
     * @param flowVars
     * @return
     */
    private String getNextDeadLineTime(Map<String,Object> flowVars){
        //获取是否有指定截止方式
        String EFLOW_QXZDFS = (String) flowVars.get("EFLOW_QXZDFS");
        if(StringUtils.isNotEmpty(EFLOW_QXZDFS)){
            //如果是工作日方式
            if(EFLOW_QXZDFS.equals("1")){
                //获取工作日的数量
                String workDayLimit = (String) flowVars.get("EFLOW_WORKDAYLIMIT");
                if(!workDayLimit.equals("0")){
                    String today = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
                    String deadLineDate = workdayService.getDeadLineDate(today,Integer.parseInt(workDayLimit));
                    return deadLineDate+" 23:59";
                }else{
                    return null;
                }
            }else if(EFLOW_QXZDFS.equals("2")){
                //获取具体截止时间
                String EFLOW_DEADLINETIME = (String) flowVars.get("EFLOW_DEADLINETIME");
                return EFLOW_DEADLINETIME;
            }
            return null;
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 指派流程任务API
     * 1:如果是初次启动流程,那么需要保存开始节点任务
     * 2:派发出新的流程任务
     * 3:将当前的任务信息进行更新
     * @author Flex Hu
     * @created 2015年8月20日 下午2:29:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> assignFlowTask(Map<String,Object> flowVars){
        //获取流程申报号
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        //获取流程定义ID
        String defId = (String) flowVars.get("EFLOW_DEFID");
        //获取下一步环节信息
        String nextStepJson = (String) flowVars.get("EFLOW_NEXTSTEPSJSON");
        //获取下一环节截止时间
        String nextDeadLineTime = this.getNextDeadLineTime(flowVars);
        if(StringUtils.isNotEmpty(nextDeadLineTime)){
            flowVars.put("EFLOW_TASKDEADLINETIME", nextDeadLineTime);
        }
        //将其转换成Map
        Map<String,Object> nextSteps = null;
        if(StringUtils.isNotEmpty(nextStepJson)){
            nextSteps = JSON.parseObject(nextStepJson,Map.class);
        }
        //获取是否是发起流程
        boolean isStartFlow = dao.isStartFlow(exeId, defId);
        flowVars.put("isStartFlow", isStartFlow);
        Map<String,Object> currentTask = this.getCurrentTask(isStartFlow, flowVars);
        //获取来源对象
        Map<String,Object> fromTask = this.getFromTask(isStartFlow, flowVars, currentTask, defId, exeId);
        //获取新产生的流程任务Map
        Map<String,String> assignTaskResult= null;
        //当前任务结束时间
        if(flowVars.get("DATAFROM")!=null&&flowVars.get("DATAFROM").equals("webservice")){
            flowVars.put("NEW_END_TIME",flowVars.get("EXECUTE_TIME"));
        }else{
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            flowVars.put("NEW_END_TIME",time);
        }
        //如果不是启动流程
        if(!isStartFlow){
            //获取父亲任务ID
            String parentTaskId = (String) currentTask.get("PARENT_TASKID");
            //获取是否同意插件的值
            String eflowIsAgree = (String) flowVars.get("EFLOW_ISAGREE");
            if(StringUtils.isNotEmpty(parentTaskId)){
                //出现不同意的情况,那么中断流程运行
                if(!(StringUtils.isNotEmpty(eflowIsAgree)&&eflowIsAgree.equals("-1"))){
                    assignTaskResult = this.getNewTaskResultMap(nextSteps, defId,exeId,fromTask,flowVars);
                }
                //开始更新当前流程任务信息
                this.updateCurrentTask(assignTaskResult, currentTask, isStartFlow, flowVars);
            }else{
                //如果是退回流程,更新其它任务全部退回
                String EFLOW_ISBACK = (String) flowVars.get("EFLOW_ISBACK");
                if(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true")){
                    dao.updateSameFromTaskId((String)currentTask.get("TASK_ID"),(String)currentTask.get("FROMTASK_IDS"),
                            Jbpm6Constants.TASKSTATUS_TH);
                }
                //获取是否是自由流程任务
                String isFree = currentTask.get("IS_FREE").toString();
                if(isFree.equals("1")){
                    //出现不同意的情况,那么中断流程运行
                    if(!(StringUtils.isNotEmpty(eflowIsAgree)&&eflowIsAgree.equals("-1"))){
                        assignTaskResult = this.getNewTaskResultMap(nextSteps, defId,exeId,fromTask,flowVars);
                    }
                    //开始更新当前流程任务信息
                    this.updateCurrentTask(assignTaskResult, currentTask, isStartFlow, flowVars);
                }else{
                    //开始获取当前审核人的所在任务ID
                    String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
                    //判断兄弟任务是否已经全部办结,如果办结,那么可以往下执行
                    boolean brotherTaskOver = dao.isOtherSameFromTaskHandleOver(currentTaskId);
                    if(brotherTaskOver){
                        //出现不同意的情况,那么中断流程运行
                        if(!(StringUtils.isNotEmpty(eflowIsAgree)&&eflowIsAgree.equals("-1"))){
                            assignTaskResult = this.getNewTaskResultMap(nextSteps, defId,exeId,fromTask,flowVars);
                        }
                    }
                    //开始更新当前流程任务信息
                    this.updateCurrentTask(assignTaskResult, currentTask, isStartFlow, flowVars);
                    if(!(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true"))){
                        //获取当前流程任务的排序值
                        if(currentTask.get("TASK_SN")!=null){
                            int taskSn = Integer.parseInt(currentTask.get("TASK_SN").toString());
                            if(taskSn!=0){
                                //那么开始更新下一串审人的状态
                                String fromTaskId = (String) currentTask.get("FROMTASK_IDS");
                                String taskNodeName = (String) currentTask.get("TASK_NODENAME");
                                dao.updateNextTaskOrderStatus(fromTaskId, taskNodeName, taskSn);
                            }
                        }
                    }
                }
            }
        }else{
            assignTaskResult = this.getNewTaskResultMap(nextSteps, defId,exeId,fromTask,flowVars);
            //开始更新当前流程任务信息
            this.updateCurrentTask(assignTaskResult, currentTask, isStartFlow, flowVars);
        }
        if(assignTaskResult!=null){
            //往变量里面存放新任务结果数据
            flowVars.put("EFLOW_NEWTASK_HANDLERCODES",assignTaskResult.get("EFLOW_NEWTASK_HANDLERCODES"));
            flowVars.put("EFLOW_NEWTASK_HANDLERNAMES",assignTaskResult.get("EFLOW_NEWTASK_HANDLERNAMES"));
            flowVars.put("EFLOW_NEWTASK_PARENTIDS",assignTaskResult.get("EFLOW_NEWTASK_PARENTIDS"));
            flowVars.put("EFLOW_NEWTASK_NODENAMES",assignTaskResult.get("EFLOW_NEWTASK_NODENAMES"));
        }
        return flowVars;
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        // 是否流程归档明细查看
        if("1".equals(sqlFilter.getRequest().getParameter("isFiled"))) {
            sql.append("select T.*,E.ITEM_CODE,E.END_TIME AS EXE_END_TIME,");
            sql.append("case when T.STEP_SEQ=1 then T.ASSIGNER_NAME else (select R.ASSIGNER_NAME ");
            sql.append("from JBPM6_TASK r where R.PARENT_TASKID=T.TASK_ID and R.IS_REAL_HANDLED=1) end REAL_HANDLER ");
            sql.append("FROM JBPM6_TASK_EVEHIS T ");
            sql.append("JOIN JBPM6_EXECUTION_EVEHIS E ON T.EXE_ID=E.EXE_ID where 1=1 ");
        } else {
            sql.append("select T.*,E.ITEM_CODE,E.END_TIME AS EXE_END_TIME,");
            sql.append("case when T.STEP_SEQ=1 then T.ASSIGNER_NAME else (select R.ASSIGNER_NAME ");
            sql.append("from JBPM6_TASK r where R.PARENT_TASKID=T.TASK_ID and R.IS_REAL_HANDLED=1) end REAL_HANDLER ");
            sql.append("FROM JBPM6_TASK T ");
            sql.append("JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID where 1=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        String bgbazxsxbm = getDicCodes("BGBAZXSXBM");
        String bgsxbm = dictionaryService.getDicCode("BGBAZXSXBM", "变更");
        String gsspBeginTime = "";
        int workDayCount = 1;
        for(Map<String,Object> task:list){
            String taskId = (String) task.get("TASK_ID");
            String beginTime = (String) task.get("CREATE_TIME");
            //获取办结时间
            String endTime = (String) task.get("END_TIME");
            //获取办件办结时间
            String exeEndTime = (String) task.get("EXE_END_TIME");
            if(StringUtils.isEmpty(exeEndTime)){
                exeEndTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            }
            //事项编码
            String itemCode = (String) task.get("ITEM_CODE");
            //获取省网事项编码
            StringBuffer servicesql=new StringBuffer();
            servicesql.append("select w.* from T_WSBS_SERVICEITEM w where w.ITEM_CODE=?");
            List<Map<String,Object>> serviceList=dao.findBySql(servicesql.toString(),
                    new Object[]{itemCode},null);
            if(serviceList.size()>0&&serviceList!=null) {
                Map<String,Object> serviceMap = serviceList.get(0);
                String SWB_ITEM_CODE = serviceMap.get("SWB_ITEM_CODE")==null?"":serviceMap.get("SWB_ITEM_CODE").toString();
                task.put("SWB_ITEM_CODE", SWB_ITEM_CODE);
            }
            //获取流程节点
            String taskNodeName = (String) task.get("TASK_NODENAME");
            if(StringUtils.isEmpty(endTime)){
                endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                task.put("WORK_HOURS",DateTimeUtil.getWorkTime(beginTime, endTime));
            }else{
                task.put("WORK_HOURS",DateTimeUtil.getWorkTime(beginTime, endTime));
            }
            if (bgbazxsxbm.contains(itemCode)) {// 变更备案注销
                task.put("IS_BGBAZX", 1);//列表展示标识    
                if(itemCode.equals(bgsxbm)){
                    workDayCount = 3;
                }            
                if(taskNodeName.equals("工商审批")){
                    gsspBeginTime = beginTime;
                }
                if(StringUtils.isNotEmpty(gsspBeginTime)){
                    Date taskBeginDay = DateTimeUtil.getDateOfStr(gsspBeginTime, "yyyy-MM-dd HH:mm:ss");
                    String beginDate = DateTimeUtil.getStrOfDate(taskBeginDay, "yyyy-MM-dd");
                    Date taskEndDay = DateTimeUtil.getDateOfStr(exeEndTime, "yyyy-MM-dd HH:mm:ss");
                    String endDate = DateTimeUtil.getStrOfDate(taskEndDay, "yyyy-MM-dd");
                    if(endDate.equals(beginDate)){
                        task.put("LEFT_WORKDAY", 0);
                    }else{
                        int leftWorkDay = this.workdayService.getWorkDayCount(beginDate, endDate);
                        if(leftWorkDay==0){
                            task.put("LEFT_WORKDAY", -1);
                        }else{
                            task.put("LEFT_WORKDAY", leftWorkDay);
                        }
                    }
                }
            }else{
                task.put("IS_BGBAZX", -1);//列表展示标识
                if(taskNodeName.contains("工商审批")){    
                    gsspBeginTime = beginTime;
                }
                if(StringUtils.isNotEmpty(gsspBeginTime)){
                    task.put("LEFT_WORKDAY", new DateTimeUtil().getWorkMinute(gsspBeginTime, endTime));
                }
            }
            task.put("WORKDAYCOUNT", workDayCount);
            
            task.put("IS_HANG",0);
            List<Map<String, Object>> hangList = flowTaskService.getListByJdbc("JBPM6_HANGINFO",  taskId );
            if (hangList.size()>0) {
                task.put("IS_HANG",1);
            }
            //获取截止时间
            String taskDeadTime = (String) task.get("TASK_DEADTIME");
            if(StringUtils.isNotEmpty(taskDeadTime)){
                //获取挂起的工作天数
                int hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(taskId);
                Date taskDeadDay = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
                String endDate = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
                if(hangAllTime>0){
                    String eDate = this.workdayService.getDeadLineDate(endDate, hangAllTime);
                    if(StringUtils.isNotEmpty(eDate)){
                        endDate = eDate;
                    }
                }
                endDate +=" 23:59"; 
                task.put("TASK_DEADTIME",endDate);
                //获取当前时间
                Date currentTime = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
                Date deadTime = DateTimeUtil.getDateOfStr(endDate, "yyyy-MM-dd HH:mm");
                if(currentTime.after(deadTime)){
                    task.put("IS_OVERTIME","1");
                }else{
                    task.put("IS_OVERTIME","-1");
                }
                //在审核挂起环节判断是否超期，如果挂起有超期，审核环节就是超期了
                boolean isOver= executionService.isOverTime(taskId);
                if(isOver){
                    task.put("IS_OVERTIME","1");
                }
            }else{
                task.put("IS_OVERTIME","-1");
                //即办件是否超时
                jbjIsOver(task,sqlFilter.getRequest().getParameter("isFiled"));
            }
            //获取任务审核的类型
            String assignerType = (String) task.get("ASSIGNER_TYPE");
            if(AllConstant.ASSIGNER_TYPE_SYSTEMUSER.equals(assignerType)){
                //获取团队审核人编码
                String teamCodes = (String) task.get("TEAM_CODES");
                //定义审核部门名称
                StringBuffer assignerDepNames = new StringBuffer("");
                List<String> depNames = new ArrayList<String>();
                for(String userAccount:teamCodes.split(",")){
                    Map<String,Object> userInfo = sysUserService.getUserInfo(userAccount);
                    if(userInfo!=null){
                        //获取部门名称
                        String departName = (String) userInfo.get("DEPART_NAME");
                        if(StringUtils.isNotEmpty(departName)){
                            if(!depNames.contains(departName)){
                                assignerDepNames.append(departName).append(",");
                            }
                            depNames.add(departName);
                        }
                    }
                }
                if(depNames.size()>=1){
                    assignerDepNames.deleteCharAt(assignerDepNames.length()-1);
                }
                task.put("ASSIGNER_DEPNAMES", assignerDepNames.toString());
            }
        }
        return list;
    }
    /**
     * 
     * 描述：审核过程明细即办件是否超时
     * @author Water Guo
     * @created 2018-1-2 下午3:43:09
     * @param task
     */
    private  void jbjIsOver(Map<String,Object> task,String isFiled){
        String taskId=(String)task.get("TASK_ID");
        String tableName = "JBPM6_TASK";
        if("1".equals(isFiled)){
            tableName = "JBPM6_TASK_EVEHIS";
        }
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT S.SXLX,E.CREATE_TIME,JT.END_TIME");
        sql.append("  FROM ").append(tableName).append(" JT LEFT JOIN JBPM6_EXECUTION E ");
        sql.append(" ON JT.EXE_ID=E.EXE_ID LEFT JOIN ");
        sql.append(" T_WSBS_SERVICEITEM S  ON E.ITEM_CODE=S.ITEM_CODE");
        sql.append(" WHERE  S.SXLX='1'  AND JT.END_TIME IS NOT NULL AND");
        sql.append(" JT.TASK_ID=? AND");
        sql.append(" JT.TASK_NODENAME <>'开始'");
        Map<String,Object> map=this.dao.
                getByJdbc(sql.toString(), new Object[]{taskId});
        if(map!=null){
            String createTime=(String)map.get("CREATE_TIME");
            String endTime=(String)map.get("END_TIME");
            createTime=createTime.substring(0, 10);
            endTime=endTime.substring(0, 10);
            if(!createTime.equals(endTime)){
                task.put("IS_OVERTIME", "-2");
            }
        }
        
    }
    /**
     * 
     * 描述：查询同一环节挂起两次的信息
     * @author Water Guo
     * @created 2017-10-9 下午2:50:47
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHangSpe(SqlFilter sqlFilter){
        String overtime="";
        String againHang="";
        String exp="";
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql=new StringBuffer();
        sql.append("select k.task_id,e.exe_id,d.depart_name,h.link_id,e.subject,l.link_name,");
        sql.append("h.begin_time,l.link_limittime,");
        sql.append("h.end_time,s.item_name,h.link_end_time,h.explain,h.HANG_FILE_ID");
        sql.append(",u.fullname,h.link_man,h.link_man_tel from jbpm6_hanginfo h ");
        sql.append("left join jbpm6_task k on h.task_id=k.task_id");
        sql.append(" left join jbpm6_execution e on e.exe_id=k.exe_id");
        sql.append(" left join t_wsbs_serviceitem s on e.item_code=s.item_code");
        sql.append(" left join t_wsbs_serviceitem_link l on h.link_id=l.record_id");
        sql.append(" left join t_msjw_system_sysuser u on h.userid=u.user_id");
        //sql.append(" left join t_wsbs_serviceitem_catalog cl on s.catalog_code=cl.catalog_code");
        //sql.append(" left join t_msjw_system_department d on cl.depart_id=d.depart_id");

        sql.append(" left join t_msjw_system_department d on s.SSBMBM=d.depart_code");
        sql.append(" where (h.link_id is not null or h.end_time is  null) and k.task_id is not null ");
        sql.append(" and k.task_nodename <>'开始'");
        //查询重复挂起的特殊环节
        if(sqlFilter.getQueryParams().get("Q_E.AGAINHANGUP_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_E.AGAINHANGUP_LIKE").equals("")){
            againHang=sqlFilter.getQueryParams().get("Q_E.AGAINHANGUP_LIKE").toString();
            sqlFilter.removeFilter("Q_E.AGAINHANGUP_LIKE");
        }
        //查询超期的特殊环节挂起
        if(sqlFilter.getQueryParams().get("Q_E.OVERTIEM_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_E.OVERTIEM_LIKE").equals("")){
            overtime=sqlFilter.getQueryParams().get("Q_E.OVERTIEM_LIKE").toString();
            sqlFilter.removeFilter("Q_E.OVERTIEM_LIKE");
        }
        if(sqlFilter.getQueryParams().get("Q_E.EXP_LIKE")!=null){
            exp="1";
            sqlFilter.removeFilter("Q_E.EXP_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(),null);
        if(StringUtils.isNotEmpty(exp)){
              list = extractSpeExp(overtime, againHang, list);
            }else{
              list = extractSpeShow(overtime, againHang, list);
          }
        return list;
    }
    /**
     * 
     * 描述：list重复挂起，超期查询条件组合list
     * @author Water Guo
     * @created 2017-12-5 下午4:16:29
     * @param overtime
     * @param againHang
     * @param list
     * @return
     */
    private List<Map<String, Object>> extractSpeShow(String overtime, String againHang, 
            List<Map<String, Object>> list) {
        List<Map<String,Object>> overList=new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> againList=new ArrayList<Map<String,Object>>();
        for(Map<String,Object> map :list){
            //附件
            StringBuffer files=new StringBuffer();
            String ids = map.get("HANG_FILE_ID")==null?"":map.get("HANG_FILE_ID").toString();
            List<Map<String,Object>> fileList=fileAttachService.findListForResult(ids);
            for(Map<String, Object> filemap : fileList){
                files.append("<p style=\"margin-left: 5px; margin-right: 5px;line-height: 20px;\">");
                files.append("<a style=\"color: blue;\" href=\"javascript:AppUtil.downLoadFile(\'");
                files.append(filemap.get("FILE_ID")).append("\');\">");
                files.append(filemap.get("FILE_NAME")).append("</a>");
                files.append("</p>");
            }
            map.put("FILES", files.toString());
            //判断特殊环节挂起是否过期
            String linkEndTime=map.get("LINK_END_TIME")==null?"":
                map.get("LINK_END_TIME").toString();
            String endTime=map.get("END_TIME")==null?"":
                map.get("END_TIME").toString();
            if(linkEndTime!=""){
                //day:-2过期，0当天截止，正数剩余多少天数
                int day;
                if(StringUtils.isEmpty(endTime)){
                    day= this.getLeftDayFromNow(linkEndTime);
                }else{
                    day=getBewWorkDay(endTime, linkEndTime);
                }
                map.put("OVERTIME",day);
                //超期overList，1：超期查询，0：未超期查询
                if(day==-2){
                    if("1".equals(overtime))
                       overList.add(map);
                }else{
                    if("0".equals(overtime)) 
                        overList.add(map);
                }
            }else{
                map.put("OVERTIME", "-3");
            }
            //判断特殊环节是否重复挂起
            String link_id=map.get("LINK_ID")==null?"":
                map.get("LINK_ID").toString();
            String task_id=map.get("TASK_ID")==null?"":
                map.get("TASK_ID").toString();
            map.put("AGAINHANGUP",0);
            if(link_id!=""&&task_id!=""){
                StringBuffer query=new StringBuffer();
                query.append("SELECT hang_id,task_id FROM JBPM6_HANGINFO");
                query.append(" WHERE  TASK_ID=? AND LINK_ID=? ");
                List<Map<String,Object>>  queryList=this.dao.findBySql
                (query.toString(), new Object[]{task_id,link_id}, null);
                if(queryList.size()>1){
                    map.put("AGAINHANGUP", 1);
                    //重复挂起 1：重复挂起件查询。0：未重复挂起件查询
                    if("1".equals(againHang))
                        againList.add(map);
                }else{
                    if("0".equals(againHang))
                        againList.add(map);
                }
            } 
            
        }
        if(StringUtils.isNotEmpty(overtime)){
            list= overList;
        }
        if(StringUtils.isNotEmpty(againHang)){
            list= againList;
        }
        if(StringUtils.isNotEmpty(overtime)&&StringUtils.isNotEmpty(againHang)){
            overList.retainAll(againList);
            list=overList;
        }
        return list;
    }
    /**
     * 
     * 描述：导出list重复挂起，超期查询条件组合list
     * @author Water Guo
     * @created 2017-12-5 下午4:16:29
     * @param overtime
     * @param againHang
     * @param list
     * @return
     */
    private List<Map<String, Object>> extractSpeExp(String overtime, String againHang, 
            List<Map<String, Object>> list) {
        List<Map<String,Object>> overList=new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> againList=new ArrayList<Map<String,Object>>();
        for(Map<String,Object> map :list){
            //附件
            StringBuffer files=new StringBuffer();
            String ids = map.get("HANG_FILE_ID")==null?"":map.get("HANG_FILE_ID").toString();
            List<Map<String,Object>> fileList=fileAttachService.findListForResult(ids);
            for(Map<String, Object> filemap : fileList){
                files.append(filemap.get("FILE_NAME")).append(",");
            }
            map.put("HANG_FILE_ID", files.toString());
            //判断特殊环节挂起是否过期
            String linkEndTime=map.get("LINK_END_TIME")==null?"":
                map.get("LINK_END_TIME").toString();
            String endTime=map.get("END_TIME")==null?"":
                map.get("END_TIME").toString();
            if(linkEndTime!=""){
                //day:-2过期，0当天截止，正数剩余多少天数
                int day;
                if(StringUtils.isEmpty(endTime)){
                    day= this.getLeftDayFromNow(linkEndTime);
                }else{
                    day=getBewWorkDay(endTime, linkEndTime);
                }
                if(day==-2){
                    map.put("ITEM_NAME", "是");
                  }else{
                    map.put("ITEM_NAME","否"); 
                  }
                //超期overList，1：超期查询，0：未超期查询
                if(day==-2){
                    if("1".equals(overtime))
                       overList.add(map);
                }else{
                    if("0".equals(overtime)) 
                        overList.add(map);
                }
            }else{
                map.put("ITEM_NAME","否"); 
            }
            //判断特殊环节是否重复挂起
            String link_id=map.get("LINK_ID")==null?"":
                map.get("LINK_ID").toString();
            String task_id=map.get("TASK_ID")==null?"":
                map.get("TASK_ID").toString();
            map.put("LINK_END_TIME","否");
            if(link_id!=""&&task_id!=""){
                StringBuffer query=new StringBuffer();
                query.append("SELECT hang_id,task_id FROM JBPM6_HANGINFO");
                query.append(" WHERE  TASK_ID=? AND LINK_ID=? ");
                List<Map<String,Object>>  queryList=this.dao.findBySql
                (query.toString(), new Object[]{task_id,link_id}, null);
                if(queryList.size()>1){
                    map.put("LINK_END_TIME", "是");
                    //重复挂起 1：重复挂起件查询。0：未重复挂起件查询
                    if("1".equals(againHang))
                        againList.add(map);
                }else{
                    if("0".equals(againHang))
                        againList.add(map);
                }
            } 
            
        }
        if(StringUtils.isNotEmpty(overtime)){
            list= overList;
        }
        if(StringUtils.isNotEmpty(againHang)){
            list= againList;
        }
        if(StringUtils.isNotEmpty(overtime)&&StringUtils.isNotEmpty(againHang)){
            overList.retainAll(againList);
            list=overList;
        }
        return list;
    }
    /**
     * 
     * 描述：两个时间段间隔的工作天数，0当天截止，-2超期，正数剩余天数
     * @author Water Guo
     * @created 2017-12-6 上午9:24:48
     * @param date1
     * @param date2
     * @return
     */
    public int getBewWorkDay(String date1,String date2){
        date1=date1.substring(0,10);
        date2=date2.substring(0, 10);
        if(date1.equals(date2)){
            return 0;
        }else{
            int day=workdayService.getWorkDayCount(date1, date2);
            if(day!=0){
                return day;
            }else{
                return -2;
            }
        }
    }
    /**
     * 获取待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp){
        String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SQFS,E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,T.IS_IMMEDIATE,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.PARENT_TASKID,S.ZBCS,E.PAY_STATUS,CH.PAY_STATUS AS PAY_STATUSNEW");
        sql.append(" ,(case when R.REVOKE_STATUS is null then 2  else R.REVOKE_STATUS end) REVOKE_STATUS");
        sql.append(",Q.RECORD_ID,Q.EVALUATE,Q.EVALUATETIME,E.CREATOR_ID ");
        sql.append(",'" + nowDate + "' AS NOW_DATE ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE=S.ITEM_CODE ");
        sql.append(" LEFT JOIN T_BDC_CHARGE CH ON CH.EXE_ID=E.EXE_ID ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" LEFT JOIN JBPM6_REVOKE R ON E.EXE_ID = R.EXE_ID AND R.REVOKE_STATUS=0 ");
        sql.append(" LEFT JOIN T_CKBS_QUEUERECORD Q ON E.EXE_ID = Q.EXE_ID AND Q.EVALUATETYPE = 3 ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE NOT IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        sql.append(" AND E.PROJECT_CODE IS NULL ");
        //身份证和组织机构代码查询
        if(sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_E.SQRSFZ_LIKE").equals("")){
            String zjhm=sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            zjhm = sm4Utils.encryptDataCBC(zjhm);
            sql.append(" and (E.SQRSFZ  like '%").append(zjhm).append("%' OR  E.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_E.SQRSFZ_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
        params.toArray(), sqlFilter.getPagingBean());
        for (Map<String, Object> map : list) {
            // 获取退件的ID
            //String bjxxId = (String) map.get("BJXX_ID");
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            //挂起流程根据挂起特殊环节判断剩余时间和是否超期
            String taskStatus=map.get("TASK_STATUS")==null?"":map.get("TASK_STATUS").toString();
            if("-1".equals(taskStatus)){
                String parentTaskId=(String)map.get("PARENT_TASKID");
                boolean isOver=executionService.isOverTime(parentTaskId);
                if(isOver){
                    map.put("LEFT_WORKDAY", -1);
                }else{
                    int leftWorkDay=this.getLinkLeftDay(parentTaskId);
                    map.put("LEFT_WORKDAY", leftWorkDay);
                }
                //挂起件挂起的特殊环节名称
                String hangUpName=this.getHangUpName(parentTaskId);
                if(StringUtils.isNotEmpty(hangUpName)){
                    map.put("TASK_NODENAME",hangUpName);
                }
            }
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = "";
                if (fromTask != null) {
                    BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                }
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        //倒计时半小时
        for (Map<String, Object> map : list) {
            String createTime = map.get("CREATE_TIME")==null?
                    "":map.get("CREATE_TIME").toString();
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleFormat.format(new Date());
//            createTime = "2018-05-20 15:30:27";
//            nowTime = "2018-05-20 16:00:28";
            long workMinute = new DateTimeUtil().getWorkMinute(createTime, nowTime);
//            已经有扣除中午了
//            int createHour = Integer.parseInt(createTime.substring(11, 13));
//            int nowTimeHour = Integer.parseInt(nowTime.substring(11, 13));
//            if (createHour<=12||nowTimeHour>=14) {
//                workMinute = workMinute - 120;
//            }
            long leftMinute = 30 - workMinute;
            map.put("LEFTMINUTE", leftMinute);
        }
        //获取签署状态 2020/8/23 Luffy
        for (Map<String, Object> map : list) {
            String exeId = map.get("EXE_ID").toString();
            String signStatus = bdcQlcMaterGenAndSignService.getSignStatusByExeId(exeId);
            map.put("SIGN_STATUS", signStatus);
        }
        try {
            // 按截至时间进行排序
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    int day0 = Integer.parseInt(arg0.get("LEFT_WORKDAY").toString());
                    int day1 = Integer.parseInt(arg1.get("LEFT_WORKDAY").toString());
                    day0 = day0==-2 ? 99 : day0;
                    day1 = day1==-2 ? 99 : day1;
                    if (day1 > day0) {
                        return -1;
                    } else if (day1 == day0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
        return list;
    }
    /**
     * 获取投资项目待办列表
     * 
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public List<Map<String,Object>> findTzxmNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,T.IS_IMMEDIATE,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.PARENT_TASKID,S.ZBCS ");
        sql.append(" ,(case when  R.REVOKE_STATUS is null then 2  else R.REVOKE_STATUS end) REVOKE_STATUS");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE=S.ITEM_CODE ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" LEFT JOIN JBPM6_REVOKE R   ON E.EXE_ID = R.EXE_ID AND R.REVOKE_STATUS=0 ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE NOT IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        sql.append(" AND E.PROJECT_CODE IS NOT NULL ");
        //身份证和组织机构代码查询
        if(sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_E.SQRSFZ_LIKE").equals("")){
            String zjhm=sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE").toString();
            sql.append(" and (E.SQRSFZ  like '%").append(zjhm).append("%' OR  E.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_E.SQRSFZ_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), null);
        for (Map<String, Object> map : list) {
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            //挂起流程根据挂起特殊环节判断剩余时间和是否超期
            String taskStatus=map.get("TASK_STATUS")==null?"":map.get("TASK_STATUS").toString();
            if("-1".equals(taskStatus)){
                String parentTaskId=(String)map.get("PARENT_TASKID");
                boolean isOver=executionService.isOverTime(parentTaskId);
                if(isOver){
                    map.put("LEFT_WORKDAY", -1);
                }else{
                    int leftWorkDay=this.getLinkLeftDay(parentTaskId);
                    map.put("LEFT_WORKDAY", leftWorkDay);
                }
                //挂起件挂起的特殊环节名称
                String hangUpName=this.getHangUpName(parentTaskId);
                if(StringUtils.isNotEmpty(hangUpName)){
                    map.put("TASK_NODENAME",hangUpName);
                }
            }
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        //倒计时半小时
        for (Map<String, Object> map : list) {
            String createTime = map.get("CREATE_TIME")==null?
                    "":map.get("CREATE_TIME").toString();
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleFormat.format(new Date());
            long workMinute = new DateTimeUtil().getWorkMinute(createTime, nowTime);
            long leftMinute = 30 - workMinute;
            map.put("LEFTMINUTE", leftMinute);
        }
        try {
            // 按截至时间进行排序
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    int day0 = Integer.parseInt(arg0.get("LEFT_WORKDAY").toString());
                    int day1 = Integer.parseInt(arg1.get("LEFT_WORKDAY").toString());
                    day0 = day0==-2 ? 99 : day0;
                    day1 = day1==-2 ? 99 : day1;
                    if (day1 > day0) {
                        return -1;
                    } else if (day1 == day0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
        return list;
    }
    /**
     * 
     * 描述：获取特殊环节剩余时间
     * @author Water Guo
     * @created 2017-10-10 上午9:23:54
     * @param parentTaskId
     * @return
     */
    public int getLinkLeftDay(String parentTaskId){
        int leftWorkDay=0;
        StringBuffer sql=new StringBuffer();
        sql.append("select * from jbpm6_hanginfo h");
        sql.append("  where h.task_id=? ");
        sql.append(" and h.link_status='1'");
        List<Map<String,Object>> list=
                this.dao.findBySql(sql.toString(), new Object[]{parentTaskId}, null);
        //如果是以前挂起的件 ，时限显示无限制
        if(list.size()==0){
            leftWorkDay=-2;
        }
        for(Map<String,Object> map:list){
            String linkEndTime=map.get("LINK_END_TIME")==null?
                 "":map.get("LINK_END_TIME").toString();
            if(linkEndTime!=""){
                leftWorkDay=getLeftDayFromNow(linkEndTime);
            }
        }
        return leftWorkDay;
    }
    /**
     * 
     * 描述：待我审批根据taskParentID查找挂起环节名称
     * @author Water Guo
     * @created 2017-11-1 上午10:40:54
     * @param parentTaskId
     * @return
     */
    public String getHangUpName(String parentTaskId){
        String nodeName="";
        StringBuffer sql=new StringBuffer();
        sql.append(" select l.link_name from jbpm6_hangInfo h ");
        sql.append(" left join t_wsbs_serviceitem_link l on h.link_id=l.record_id");
        sql.append("  where h.task_id=? ");
        sql.append(" and h.link_status='1'");
        List<Map<String,Object>> list=
                this.dao.findBySql(sql.toString(), new Object[]{parentTaskId}, null);
        if (list.size()>0){
            nodeName=(String)list.get(0).get("link_name");
        }
        return nodeName;
    }
    /**
     * 
     * 描述：获取某个时间距离现在还有剩余多少工作日，返回-2过期，0当天截止，正数剩余多少天数
     * @author Water Guo
     * @created 2017-10-10 上午9:48:12
     * @param strDate
     */
    public int getLeftDayFromNow(String strDate){
        Date endDate=DateTimeUtil.getDateOfStr(strDate, "yyyy-MM-dd");
        strDate=DateTimeUtil.getStrOfDate(endDate, "yyyy-MM-dd");
        Date curDate=new Date();
        String curDay=DateTimeUtil.getStrOfDate(curDate, "yyyy-MM-dd");
        if(strDate.equals(curDay)){
            return 0;
        }
        int leftDay=workdayService.getWorkDayCount(curDay, strDate);
        if(leftDay==0){
            return -2;
        }else{
            return leftDay;
        }
    }
    /**
     * 获取商事待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findZzhyNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,T.IS_IMMEDIATE,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.END_TIME,E.ISNEEDSIGN,E.ITEM_CODE ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());

        String bgbazxsxbm = getDicCodes("BGBAZXSXBM");
        String bgsxbm = dictionaryService.getDicCode("BGBAZXSXBM", "变更");
        List<Map<String, Object>> tasklist = null;
        int workDayCount = 1;
        for (Map<String, Object> map : list) {
            String gsspBeginTime = "";
            // 获取退件的ID
            //String bjxxId = (String) map.get("BJXX_ID");
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            //流水号
            String exeId = (String) map.get("EXE_ID");
            //获取流程节点
            String taskNodeName = (String) map.get("TASK_NODENAME");
            //事项编码
            String itemCode = (String) map.get("ITEM_CODE");
            //结束时间
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");  
            if(!taskNodeName.contains("预审")){
                if(StringUtils.isNotEmpty(bgbazxsxbm) && bgbazxsxbm.contains(itemCode)){//变更注销备案事项
                    if(itemCode.equals(bgsxbm)){
                        workDayCount = 3;
                    }
                    tasklist =  this.findTaskList(exeId, "工商审批");
                    for (Map<String, Object> map2 : tasklist) {
                        //获取任务开始时间
                        gsspBeginTime = (String) map2.get("CREATE_TIME");
                        break;                    
                    }       
                    map.put("isYs", true);
                    if(StringUtils.isNotEmpty(gsspBeginTime)){
                        Date taskBeginDay = DateTimeUtil.getDateOfStr(gsspBeginTime, "yyyy-MM-dd HH:mm:ss");
                        String beginDate = DateTimeUtil.getStrOfDate(taskBeginDay, "yyyy-MM-dd");
                        String endDate = "";
                        String eDate = this.workdayService.getDeadLineDate(beginDate, workDayCount);
                        if(StringUtils.isNotEmpty(eDate)){
                             endDate = eDate;
                        }
                        //获取当前日期
                        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
                        if(endDate.equals(currentDate)){
                            map.put("LEFT_WORKDAY", 0);
                        }else{
                            int leftWorkDay = this.workdayService.getWorkDayCount(currentDate, endDate);
                            if(leftWorkDay==0){
                                map.put("LEFT_WORKDAY", -1);
                            }else{
                                map.put("LEFT_WORKDAY", leftWorkDay);
                            }
                        }
                    }else{
                        gsspBeginTime = (String) map.get("TASK_CTIME"); 
                        map.put("isYs", true);
                        map.put("LEFT_WORKDAY", -2);
                    }
                } else{
                    tasklist =  this.findTaskList(exeId, "工商审批");
                    for (Map<String, Object> map2 : tasklist) {
                        //获取任务开始时间
                        gsspBeginTime = (String) map2.get("CREATE_TIME");
                        break;                    
                    }              
                    map.put("isYs", false);
                    if(StringUtils.isNotEmpty(gsspBeginTime)){
                        map.put("LEFT_WORKDAY", new DateTimeUtil().getWorkMinute(gsspBeginTime, endTime));
                    }
                }
            }else{
                gsspBeginTime = (String) map.get("TASK_CTIME"); 
                map.put("isYs", true);
                if(StringUtils.isNotEmpty(taskDeadTime)){
                    int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                    map.put("LEFT_WORKDAY", leftWorkDay);
                }else{
                    map.put("LEFT_WORKDAY", -2);
                }
            }
            if(StringUtils.isNotEmpty(gsspBeginTime)){
                map.put("WORK_HOURS",DateTimeUtil.getWorkTime(gsspBeginTime, endTime));                
            }
       
            
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        return list;
    }
    /**
     * 
     * 描述 获取变更备案注销事项编码
     * @author Rider Chen
     * @created 2021年6月25日 下午6:11:29
     * @param typeCode
     * @return
     */
    private String getDicCodes(String typeCode) {
        String bgbazxsxbm ="";
        StringBuffer dicsql = new StringBuffer("SELECT WMSYS.WM_CONCAT(D.dic_code) as DIC_CODE");
        dicsql.append(" FROM T_MSJW_SYSTEM_DICTIONARY D ");
        dicsql.append(" WHERE D.TYPE_ID=(SELECT T.TYPE_ID ");
        dicsql.append(" FROM T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=?) ");
        dicsql.append(" ORDER BY D.DIC_SN ASC ");
        Map<String, Object> dicMap = dao.getByJdbc(dicsql.toString(), new Object[]{typeCode});
        if(null!=dicMap && dicMap.size()>0){
            bgbazxsxbm = StringUtil.getString(dicMap.get("DIC_CODE"));
        }
        return bgbazxsxbm;
    }
    /**
     * 获取标准化建设数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBzhjsMyPortal(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT T.* FROM T_CMS_ARTICLE_CONTENT T ");
        sql.append(" LEFT JOIN T_CMS_ARTICLE_MODULE M ");
        sql.append(" ON T.MODULE_ID = M.MODULE_ID ");
        sql.append(" WHERE T.CONTENT_STATUS = '1' ");
        sql.append(" AND M.PATH LIKE '0.663.%' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
    }

    /**
     * 金融办审核办件
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> financeNeedMeHandle(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" select T.*,f.file_name,f.file_path from ");
        sql.append(" JBPM6_CYY_FINANCE T left join ");
        sql.append(" T_MSJW_SYSTEM_FILEATTACH f on T.file_id=f.file_id ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 社会保险登记表列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findSocialFormList(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        /*sql.append(" SELECT T.EXE_ID,F.FILE_NAME,F.FILE_ID,T.SUBJECT,T.CREATE_TIME ");
        sql.append(" FROM JBPM6_FLOW_RESULT  R ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION T ON R.EXE_ID=T.EXE_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F ON R.SOCIAL_FILEID=F.FILE_ID ");
        sql.append(" WHERE R.SOCIAL_FILEID IS NOT NULL and t.exe_id is not null ");*/
        sql.append("select * from ( ");
        sql.append(" SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_COMMERCIAL_COMPANY B ON A.BUS_RECORDID = B.COMPANY_ID ");
        sql.append(" WHERE A.ITEM_CODE = '201605170002XK00101' AND B.ISSOCIALREGISTER = '1' ");
        sql.append(" AND B.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2') ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.BRANCH_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append("' AND n.ISSOCIALREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.INTERNAL_STOCK_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append("' AND n.ISSOCIALREGISTER = '1' ");
        sql.append("  AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.SOLELY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append("' AND n.ISSOCIALREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.COMPANY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append("' AND n.ISSOCIALREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" ) T WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        //获取员工参保材料信息
        for (Map<String, Object> map : list) {
            Map<String, Object> busRecord = exeDataService.getBuscordMap(StringUtil.getString(map.get("EXE_ID")));
            map.put("FILE_ID3", StringUtil.getString(busRecord.get("FILE_ID3")));
        }
        return list;
    }

    /**
     * 描述:医疗保险登记表列表
     *
     * @author Madison You
     * @created 2020/10/19 16:39:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findMedicialFormList(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select * from ( ");
        sql.append(" SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_COMMERCIAL_COMPANY B ON A.BUS_RECORDID = B.COMPANY_ID ");
        sql.append(" WHERE A.ITEM_CODE = '201605170002XK00101' AND B.ISMEDICALREGISTER = '1' ");
        sql.append(" AND B.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2') ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.BRANCH_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append("' AND n.ISMEDICALREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.INTERNAL_STOCK_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append("' AND n.ISMEDICALREGISTER = '1' ");
        sql.append("  AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.SOLELY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append("' AND n.ISMEDICALREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.COMPANY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append("' AND n.ISMEDICALREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" ) A WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());

        //获取员工参保材料信息
        for (Map<String, Object> map : list) {
            Map<String, Object> busRecord = exeDataService.getBuscordMap(StringUtil.getString(map.get("EXE_ID")));
            map.put("FILE_ID4", StringUtil.getString(busRecord.get("FILE_ID4")));
        }
        return list;
    }

    /**
     * 获取商事待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findZzhyCallNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.END_TIME ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), null);       
        return list;
    }
    /**
     * 获取办件预警审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findProcessWarningData(SqlFilter sqlFilter,boolean isHaveHandUp){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,T.IS_IMMEDIATE,");
        sql.append("E.JBR_NAME,E.SQRMC,T.PARENT_TASKID ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 and e.run_status = 1");
        sql.append(" and E.ITEM_CODE NOT IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }

        SysUser curLoginUser = AppUtil.getLoginUser();
        if(StringUtils.isNotEmpty(curLoginUser.getAuthDepCodes())){
            sql.append(" and T.ASSIGNER_CODE in (")
            .append(" select username from t_msjw_system_sysuser u ");
            sql.append(" join T_MSJW_SYSTEM_DEPARTMENT d on u.depart_id = d.depart_id ");
            sql.append("  where d.depart_code in ")
            .append(StringUtil.getValueArray(curLoginUser.getAuthDepCodes()));
            sql.append(") ");            
        }
        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        StringBuffer filterSql = new StringBuffer(exeSql);
        filterSql.append(" GROUP BY T.TASK_NODENAME,  E.EXE_ID, E.APPLY_STATUS, T.FROMTASK_IDS,");
        filterSql.append(" E.SUBJECT, E.CREATOR_NAME, E.CREATE_TIME, B.BJXX_ID,  T.TASK_DEADTIME,T.PARENT_TASKID,  ");
        filterSql.append(" T.TASK_STATUS,T.IS_IMMEDIATE,  E.JBR_NAME, E.SQRMC order by E.CREATE_TIME desc");
        List<Map<String, Object>> list = dao.findBySql(filterSql.toString(),
                params.toArray(), null);
        for (Map<String, Object> map : list) {
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("PARENT_TASKID");
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDayByParentId(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        //特殊环节预警
        //特殊件list
        List<Map<String,Object>> linkList=this.findLink(sqlFilter);
        
        List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : list) {
            int leftWorkday = (Integer) map.get("LEFT_WORKDAY");
            if (leftWorkday==0||leftWorkday<0) {
                returnList.add(map);
            }
        }
        returnList.addAll(linkList);
        try {
            // 按截至时间进行排序
            Collections.sort(returnList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    int day0 = Integer.parseInt(arg0.get("LEFT_WORKDAY").toString());
                    int day1 = Integer.parseInt(arg1.get("LEFT_WORKDAY").toString());
                    day0 = day0==-2 ? 99 : day0;
                    day1 = day1==-2 ? 99 : day1;
                    if (day1 > day0) {
                        return -1;
                    } else if (day1 == day0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
//        return list;
        return returnList;
    }
    /**
     * 
     * 描述：特殊环节挂起预警list
     * @author Water Guo
     * @created 2017-9-20 下午3:00:04
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findLink(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("h.link_end_time,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append("E.JBR_NAME,E.SQRMC ");
        sql.append(" from jbpm6_hanginfo h left join ");
        sql.append(" JBPM6_TASK T on h.task_id=T.task_id ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE  h.link_status = 1");
        sql.append(" and E.ITEM_CODE NOT IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");   
        SysUser curLoginUser = AppUtil.getLoginUser();
        if(curLoginUser != null && StringUtils.isNotEmpty(curLoginUser.getAuthDepCodes())){
            sql.append(" and T.TEAM_CODES LIKE '%");
            sql.append(curLoginUser.getUsername()).append("%' ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        StringBuffer filterSql = new StringBuffer(exeSql);
        filterSql.append(" order by E.CREATE_TIME desc");
        List<Map<String, Object>> list = dao.findBySql(filterSql.toString(),
                params.toArray(), null);
        int relDay;
        List<Map<String,Object>> linkList=new ArrayList<Map<String,Object>>();
        //计算剩余时间，剩余一天以内提醒
        for(Map<String,Object> map : list){
            String linkEndTime=map.get("LINK_END_TIME")==null?"":
                map.get("LINK_END_TIME").toString();
            Date taskDeadDay = DateTimeUtil.getDateOfStr(linkEndTime, "yyyy-MM-dd HH:mm");
            linkEndTime = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
            Date curDay=new Date();
            String curTime=DateTimeUtil.getStrOfDate(curDay, "yyyy-MM-dd");
            //剩余时间
            relDay=workdayService.getWorkDayCount(curTime,linkEndTime);
            if(curTime.equals(linkEndTime)){
                map.put("LEFT_WORKDAY", 0);
                linkList.add(map);
            }else{
                if(relDay==1){
                    map.put("LEFT_WORKDAY", 1);
                    linkList.add(map);
                 }else if(relDay==0){
                     map.put("LEFT_WORKDAY", -1);
                     linkList.add(map);
                 }
            }
        }
        return linkList;
    }
    /**
     * 
     * 描述 获取移动端需要被审批的任务
     * @author Flex Hu
     * @created 2016年4月17日 上午8:58:42
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public List<Map<String,Object>> findNeedHandleForMobile(SqlFilter sqlFilter,boolean isHaveHandUp){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,T.TASK_DEADTIME,T.TASK_STATUS");
        sql.append(",E.DEF_ID ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN JBPM6_NODECONFIG C ON C.DEF_ID=E.DEF_ID AND C.DEF_VERSION=E.DEF_VERSION");
        sql.append(" AND C.NODE_NAME=T.TASK_NODENAME WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1");
        sql.append(" AND C.MOBILE_AUDIT=1  ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据任务截止时间获取剩余工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    public int getLeftWorkDay(String taskDeadTime,String taskId){
        //获取挂起的工作天数
        int hangAllTime = 0;
        if(StringUtils.isNotEmpty(taskId)){
          //获取流程实例信息
            Map<String,Object> flowTask = this.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{taskId});
            String parentTaskId = (String)flowTask.get("PARENT_TASKID");
            //获取挂起的工作天数
            hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(parentTaskId);
        }
        
        Date taskDeadDay = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
        String endDate = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
        if(hangAllTime>0){
            String eDate = this.workdayService.getDeadLineDate(endDate, hangAllTime);
            if(StringUtils.isNotEmpty(eDate)){
                 endDate = eDate;
            }
        }
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        if(endDate.equals(currentDate)){
            return 0;
        }else{
            int leftWorkDay = this.workdayService.getWorkDayCount(currentDate, endDate);
            if(leftWorkDay==0){
                return -1;
            }else{
                return leftWorkDay;
            }
        }
    }
    
    /**
     * 
     * 描述 根据任务截止时间获取剩余工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    public int getLeftWorkDayByParentId(String taskDeadTime,String parentTaskId){
        //获取挂起的工作天数
        int hangAllTime = 0;
        if(StringUtils.isNotEmpty(parentTaskId)){
            //获取挂起的工作天数
            hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(parentTaskId);
        }
        
        Date taskDeadDay = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
        String endDate = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
        if(hangAllTime>0){
            String eDate = this.workdayService.getDeadLineDate(endDate, hangAllTime);
            if(StringUtils.isNotEmpty(eDate)){
                 endDate = eDate;
            }
        }
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        if(endDate.equals(currentDate)){
            return 0;
        }else{
            int leftWorkDay = this.workdayService.getWorkDayCount(currentDate, endDate);
            if(leftWorkDay==0){
                return -1;
            }else{
                return leftWorkDay;
            }
        }
    }
    /**
     * 
     * 描述 根据任务截止时间获取超期工作日数量
     * @author Water Guo
     * @created 2016年11月18日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    @Override
    public int getOverdueWorkDay(String taskDeadTime, String taskId, Date endTimedDate) {
      //获取流程实例信息
        Map<String,Object> flowTask = this.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{taskId});
        String parentTaskId =null;
        if (taskId!=null) {
            parentTaskId = (String)flowTask.get("PARENT_TASKID");
        }
        //获取挂起的工作天数
        int hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(parentTaskId);
        //获取任务截止时间  
        Date taskDeadDay = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
        String taskDeadDayString = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
        if(hangAllTime>0){
            String eDate = this.workdayService.getDeadLineDate(taskDeadDayString, hangAllTime);
            if(StringUtils.isNotEmpty(eDate)){
                taskDeadDayString = eDate;
            }
        }
        //获取流程办结时间或当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        if (endTimedDate!=null) {
            currentDate = DateTimeUtil.getStrOfDate(endTimedDate, "yyyy-MM-dd");;
        }
        if(taskDeadDayString.equals(currentDate)){
            return 0;
        }else{
            int overdueWorkDay = this.workdayService.getWorkDayCount(taskDeadDayString, currentDate);
            if(overdueWorkDay==0){
                return -1;
            }else{
                return overdueWorkDay;
            }
        }
    }
    
    /**
     * 获取某个用户代办业务数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findTaskBusDatas(SqlFilter sqlFilter,String busTableName
            ,Set<String> queryFields,String userAccount){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.SUBJECT,");
        sql.append("E.CREATOR_NAME,E.CREATE_TIME,");
        //获取主键名称
        String primaryKeyName = (String) this.getPrimaryKeyName(busTableName).get(0);
        for(String fieldName:queryFields){
            sql.append("BUS.").append(fieldName).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN ").append(busTableName).append(" BUS ON E.Bus_Recordid=BUS.").append(primaryKeyName);
        sql.append(" WHERE T.IS_AUDITED=1 AND T.ASSIGNER_TYPE=1 AND T.TASK_STATUS=1 AND T.ASSIGNER_CODE=? ");
        params.add(userAccount);
        sql.append(" AND E.BUS_TABLENAME=? ");
        params.add(busTableName.toUpperCase());
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据任务ID获取和这个任务同样来源的任务列表信息
     * @author Flex Hu
     * @created 2015年8月22日 上午12:06:33
     * @param taskId
     * @return
     */
    public List<Map<String,Object>> findSameFromTasks(String taskId){
        StringBuffer sql = new StringBuffer("select T.* from JBPM6_TASK t WHERE T.FROMTASK_IDS=");
        sql.append("(SELECT S.FROMTASK_IDS FROM JBPM6_TASK S WHERE S.TASK_ID=?)");
        return dao.findBySql(sql.toString(), new Object[]{taskId}, null);
    }
    
    /**
     * 
     * 描述 判断是否还存在正在运行的任务
     * @author Flex Hu
     * @created 2015年8月22日 下午12:14:53
     * @param exeId
     * @return
     */
    public boolean isExistRunningTask(String exeId){
        return dao.isExistRunningTask(exeId);
    }
    
    /**
     * 
     * 描述 判断下一环节任务是否被处理过
     * @author Flex Hu
     * @created 2015年8月22日 下午7:37:04
     * @param fromTaskId
     * @return
     */
    public boolean isNextTasksHaveHandled(String fromTaskId){
        return dao.isNextTasksHaveHandled(fromTaskId);
    }
    /**
     * 
     * 描述 获取最后一条已经办理的流程任务ID
     * @author Flex Hu
     * @created 2015年8月22日 下午7:31:49
     * @param exeId
     * @param handlerAccount
     * @return
     */
    public String getLatestHandlerOverTask(String exeId,String handlerAccount){
        return dao.getLatestHandlerOverTask(exeId, handlerAccount);
    }
    
    /**
     * 
     * 描述 追回流程任务
     * @author Flex Hu
     * @created 2015年8月22日 下午7:45:47
     * @param taskId
     * @param exeId
     */
    public void revokeTask(String taskId,String exeId){
        //获取原任务信息
        Map<String,Object> flowTask = dao.getByJdbc("JBPM6_TASK",new String[]{"TASK_ID"},new Object[]{taskId});
        flowTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
        flowTask.put("END_TIME","");
        flowTask.put("WORK_HOURS","");
        flowTask.put("EXE_HANDLEDESC","");
        flowTask.put("HANDLE_OPINION","");
        flowTask.put("IS_AUDITED", "1");
        dao.saveOrUpdate(flowTask, "JBPM6_TASK", taskId);
        //获取父亲任务Id
        String parentTaskId = (String) flowTask.get("PARENT_TASKID");
        if(StringUtils.isNotEmpty(parentTaskId)){
            Map<String,Object> parentTask = dao.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{parentTaskId});
            parentTask.put("TASK_STATUS",Jbpm6Constants.TASKSTATUS_ZZSH);
            parentTask.put("END_TIME","");
            parentTask.put("WORK_HOURS","");
            parentTask.put("EXE_HANDLEDESC","");
            parentTask.put("HANDLE_OPINION","");
            dao.saveOrUpdate(parentTask, "JBPM6_TASK", parentTaskId);
        }
        //删除掉下一环节任务
        dao.deleteByFromTaskId(taskId);
        //将实例更新为最新状态
        this.executionService.updateCurNodeInfo(exeId);
    }
    
    /**
     * 
     * 描述 将流程任务更新为办结
     * @author Flex Hu
     * @created 2015年8月23日 上午7:21:27
     * @param exeId
     */
    public void updateTaskToEnd(String exeId,String handleOpinion){
        dao.updateTaskToEnd(exeId,handleOpinion);
    }
    
    /**
     * 
     * 描述 获取是可以被审批的任务列表
     * @author Flex Hu
     * @created 2015年8月23日 上午8:05:52
     * @param exeId
     * @param taskStatus
     * @return
     */
    public List<Map<String,Object>> findIsAuditTask(String exeId,String taskStatus){
        StringBuffer sql = new StringBuffer("select t.task_id,t.task_nodename,t.assigner_name");
        sql.append(",t.create_time FROM JBPM6_TASK t WHERE ");
        sql.append("T.EXE_ID=? AND T.IS_AUDITED=1 AND T.ASSIGNER_TYPE=1 AND T.TASK_STATUS=? ");
        sql.append(" ORDER BY T.CREATE_TIME ASC");
        return this.dao.findBySql(sql.toString(), new Object[]{exeId,taskStatus}, null);
    }
    
    /**
     * 
     * 描述 将父亲任务的信息更新
     * @author Flex Hu
     * @created 2015年8月23日 上午8:23:15
     * @param parentTaskId
     */
    public void updateParentTask(String parentTaskId){
        //先获取目前还有的子任务
        StringBuffer subSql = new StringBuffer("select T.ASSIGNER_CODE,");
        subSql.append("T.ASSIGNER_NAME FROM JBPM6_TASK T WHERE T.PARENT_TASKID=?");
        List<Map<String,Object>> subTaskList = dao.findBySql(subSql.toString(),
                new Object[]{parentTaskId},null);
        if(subTaskList!=null&&subTaskList.size()>0){
            StringBuffer teamCodes = new StringBuffer("");
            StringBuffer teamNames = new StringBuffer("");
            for(Map<String,Object> task:subTaskList){
                teamCodes.append(task.get("ASSIGNER_CODE")).append(",");
                teamNames.append(task.get("ASSIGNER_NAME")).append(",");
            }
            teamCodes.deleteCharAt(teamCodes.length()-1);
            teamNames.deleteCharAt(teamNames.length()-1);
            StringBuffer sql = new StringBuffer("update JBPM6_TASK J");
            sql.append(" SET J.TEAM_CODES=?,J.TEAM_NAMES=? ");
            sql.append(" WHERE J.TASK_ID=? ");
            dao.executeSql(sql.toString(), new Object[]{teamCodes.toString(),teamNames.toString(),parentTaskId});
        }else{
            dao.remove("JBPM6_TASK","TASK_ID",new Object[]{parentTaskId});
        }
    }
    
    /**
     * 
     * 描述 删除掉流程任务
     * @author Flex Hu
     * @created 2015年8月23日 上午8:15:26
     * @param taskIds
     */
    public void deleteTask(String[] taskIds,String exeId){
        for(String taskId:taskIds){
            Map<String,Object> flowTask = dao.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{taskId});
            //先删除掉任务
            dao.remove("JBPM6_TASK","TASK_ID",new Object[]{taskId});
            //获取父亲任务ID
            String parentTaskId = (String) flowTask.get("PARENT_TASKID");
            if(StringUtils.isNotEmpty(parentTaskId)){
                this.updateParentTask(parentTaskId);
            }
        }
        this.executionService.updateCurNodeInfo(exeId);
    }
    
    /**
     * 
     * 描述 转发流程任务
     * @author Flex Hu
     * @created 2015年8月23日 上午9:05:13
     * @param taskId
     * @param exeId
     * @param handlerAccount
     */
    public void changeTaskHandler(String taskId,String exeId,String handlerAccount,String handlerName){
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_TASK J SET J.ASSIGNER_CODE=?");
        sql.append(",J.ASSIGNER_NAME=? WHERE J.TASK_ID=? ");
        dao.executeSql(sql.toString(), new Object[]{handlerAccount,handlerName,taskId});
        Map<String,Object> flowTask = dao.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{taskId});
        //获取父亲任务ID
        String parentTaskId = (String) flowTask.get("PARENT_TASKID");
        if(StringUtils.isNotEmpty(parentTaskId)){
            this.updateParentTask(parentTaskId);
        }
        this.executionService.updateCurNodeInfo(exeId);
    }
    
    /**
     * 
     * 描述 更改流程环节
     * @author Flex Hu
     * @created 2015年8月23日 上午10:54:27
     * @param exeId
     * @param changeList
     */
    public void changeTaskNode(String exeId,List<Map> changeList){
        StringBuffer queryOldSql = new StringBuffer("SELECT * FROM JBPM6_TASK J");
        queryOldSql.append(" WHERE J.EXE_ID=? AND J.TASK_NODENAME=? AND J.TASK_STATUS=? ");
        queryOldSql.append(" AND J.STEP_SEQ!=0 ");
        for(Map<String,String> changeTask:changeList){
            String oldNodeName = changeTask.get("oldNodeName");
            String changeNodeName = changeTask.get("changeNodeName");
            Map<String,Object> user = dao.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[]{"USER_ID"},new Object[]{changeTask.get("changeHandlerId")});
            String userAccount = (String) user.get("USERNAME");
            String userName = (String) user.get("FULLNAME");
            //查找目标旧任务
            List<Map<String,Object>> oldTaskList = dao.findBySql(queryOldSql.toString(),new Object[]{exeId,oldNodeName,
                Jbpm6Constants.TASKSTATUS_ZZSH},null);
            Map<String,Object> oldTask = oldTaskList.get(0);
            Map<String,Object> newTask = oldTask;
            newTask.put("TASK_ID", "");
            newTask.put("TASK_NODENAME", changeNodeName);
            newTask.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            newTask.put("ASSIGNER_CODE", userAccount);
            newTask.put("ASSIGNER_NAME", userName);
            newTask.put("TEAM_CODES",userAccount);
            newTask.put("TEAM_NAMES",userName);
            newTask.put("PARENT_TASKID", "");
            newTask.put("TASK_NATURE", Jbpm6Constants.TASKNATURE_SIGNLER);
            newTask.put("IS_AUDITED", 1);
            newTask.put("IS_REAL_HANDLED", 1);
            //指派任务
            dao.saveOrUpdate(newTask, "JBPM6_TASK", null);
            //将旧任务删除
            dao.deleteByExeIdAndNodeName(exeId, oldNodeName, Jbpm6Constants.TASKSTATUS_ZZSH);
        }
        this.executionService.updateCurNodeInfo(exeId);
    }
    
    /**
     * 
     * 描述 获取同步节点来源JSON
     * @author Flex Hu
     * @created 2015年8月24日 下午3:48:53
     * @param currentTaskId
     * @param joinNodeName
     * @param defId
     * @param flowVersion
     * @param exeId
     * @return
     */
    public Map<String,String> getJoinNodeFromInfo(String currentTaskId,String joinNodeName,
            String defId,int flowVersion,String exeId){
        List<String> fromNodeNames = flowNodeService.findFromTaskNodeNames(defId,flowVersion,joinNodeName);
        List<FromTask> taskList = new ArrayList<FromTask>();
        Map<String,String> joinNodeMap = new HashMap<String,String>();
        StringBuffer taskIds =new StringBuffer("");
        StringBuffer taskNames = new StringBuffer("");
        for(String fromNodeName:fromNodeNames){
            FromTask fromTask = new FromTask();
            fromTask.setTaskName(fromNodeName);
            Set<String> handlerCodes = new HashSet<String>();
            List<Map<String,Object>> handlers = dao.findHandlerList(defId, exeId, currentTaskId,fromNodeName);
            List<FlowNextHandler> taskHandlerList = new ArrayList<FlowNextHandler>();
            for(Map<String,Object> handler:handlers){
                String taskId = (String) handler.get("TASK_ID");
                String taskName = (String) handler.get("TASK_NODENAME");
                String handlerCode = (String) handler.get("ASSIGNER_CODE");
                String handlerName = (String) handler.get("ASSIGNER_NAME");
                String assignerType = (String) handler.get("ASSIGNER_TYPE");
                if(!handlerCodes.contains(handlerCode)){
                    taskIds.append(taskId).append(",");
                    taskNames.append(taskName).append(",");
                    FlowNextHandler taskHandler = new FlowNextHandler();
                    taskHandler.setNextStepAssignerCode(handlerCode);
                    taskHandler.setNextStepAssignerName(handlerName);
                    taskHandler.setNextStepAssignerType(assignerType);
                    taskHandlerList.add(taskHandler);
                }
                handlerCodes.add(handlerCode);
            }
            fromTask.setHandlers(taskHandlerList);
            taskList.add(fromTask);
        }
        taskIds.deleteCharAt(taskIds.length()-1);
        taskNames.deleteCharAt(taskNames.length()-1);
        String json = JSON.toJSONString(taskList);
        joinNodeMap.put("FROMTASK_JSON", json);
        joinNodeMap.put("FROMTASK_IDS", taskIds.toString());
        joinNodeMap.put("FROMTASK_NODENAMES", taskNames.toString());
        return joinNodeMap;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月27日 上午11:10:33
     * @param exeId
     */
    public void deleteByExeId(String exeId){
        dao.deleteByExeId(exeId);
    }

    /**
     * 
     * 描述 获取待我审批的收件管理列表
     * @author Faker Li
     * @created 2015年11月5日 下午4:00:45
     * @param sqlFilter
     * @return 
     */
    public List<Map<String, Object>> findNeedMeHandleAndSjgl(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,");
        sql.append("E.SQRMC,E.SQRSJH,E.ITEM_NAME,E.ITEM_CODE,E.SQFS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME FROM JBPM6_TASK T");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" WHERE T.IS_AUDITED=1 AND T.ASSIGNER_TYPE=1 AND  T.TASK_STATUS=1");
        sql.append(" AND E.APPLY_STATUS in(5,8) AND E.SQFS=2 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return encryptionService.listDecrypt(list,"JBPM6_EXECUTION");
    }
    /**
     * 
     * 描述  获取待我审批的收件补件列表
     * @author Faker Li
     * @created 2015年11月5日 下午4:00:45
     * @param sqlFilter
     * @return 
     */
    public List<Map<String, Object>> findNeedMeHandleAndSjbj(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,");
        sql.append("E.SQRMC,E.SQRSJH,E.ITEM_NAME,E.ITEM_CODE,E.SQFS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME FROM JBPM6_TASK T");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" WHERE T.IS_AUDITED=1 AND T.ASSIGNER_TYPE=1 "
                + " AND  T.TASK_STATUS=3 AND E.APPLY_STATUS = 7 AND E.SQFS=2");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        return list;
    }
    
    /**
     * 
     * 描述 获取待我审批的预审管理列表
     * @author Faker Li
     * @created 2015年11月5日 下午4:00:45
     * @param sqlFilter
     * @return 
     */
    public List<Map<String, Object>> findNeedMeHandleAndysgl(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.CREATOR_PHONE,");
        sql.append("E.SQRMC,E.SQRSJH,E.ITEM_NAME,E.ITEM_CODE,E.SQFS,E.CUR_STEPAUDITNAMES,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME FROM JBPM6_TASK T");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" WHERE T.IS_AUDITED=1 AND T.ASSIGNER_TYPE=1 AND T.TASK_STATUS=1");
        sql.append(" AND (E.APPLY_STATUS = 1 or t.task_nodename like '%待受理%') ");
        sql.append(" AND (E.SQFS = 1 or E.SQFS = 3) ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        for (Map<String, Object> map : list) {
            //获取任务截止时间  （预审截止时间为收件时间之后两个工作日。）
            String createTime = (String) map.get("CREATE_TIME");
            Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
            //获取日历
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(createTimeDate); 
//            calendar.add(Calendar.DAY_OF_YEAR, 2);
//            Date date = calendar.getTime();
//            String endDate = DateTimeUtil.getStrOfDate(date, "yyyy-MM-dd HH:mm");
//            String endDate = workdayService.getDeadLineDate(createTime, 2);
            String endDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
            
            endDate = workdayService.getDeadLineDate(endDate, 2);
            endDate += " 00:00:00";
            
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if(StringUtils.isNotEmpty(endDate)){
                int leftWorkDay = this.getLeftWorkDay(endDate,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
        }
        
        return list;
    }
    /**
     * 
     * 描述   获取全部预审件列表
     * @author Rider Chen
     * @created 2017年1月20日 下午1:44:25
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleAndQbysj(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,D.DEPART_NAME,E.JBR_NAME,u.ZJHM,dic.DIC_NAME,");
        sql.append("E.SQRMC,E.SQRSJH,E.ITEM_NAME,E.ITEM_CODE,E.SQFS,e.run_status,E.CREATOR_PHONE,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,E.CUR_STEPAUDITNAMES ");
        /*sql.append(" ,(select TA.TASK_ID from JBPM6_TASK TA WHERE TA.EXE_ID = E.EXE_ID "
                + "AND TA.TASK_STATUS = -1 AND rownum = 1 ) as TASK_ID ");
        sql.append(",(select JT.TASK_NODENAME FROM JBPM6_TASK JT WHERE JT.EXE_ID=E.EXE_ID "
                +" AND JT.TASK_STATUS=1 AND ROWNUM=1) AS TASK_NODENAME ");*/
        sql.append(",E.CUR_STEPNAMES ");
        sql.append(" FROM JBPM6_EXECUTION E ");
        sql.append(" left join t_wsbs_serviceitem s on s.item_code = e.item_code");
        sql.append(" left join t_wsbs_serviceitem_catalog c on c.catalog_code = s.catalog_code ");
        sql.append(" left join t_msjw_system_department d on d.depart_id = c.child_depart_id ");
        sql.append(" LEFT JOIN T_BSFW_USERINFO u on e.creator_id = u.user_id ");
        sql.append(" left join T_MSJW_SYSTEM_DICTIONARY dic on ");
        sql.append(" u.zjlx = dic.dic_code and dic.type_code = 'DocumentType' ");
        sql.append(" WHERE  E.APPLY_STATUS = 1 AND E.SQFS=1 and e.run_status = 1 ");
        /*sql.append(" and (select JT.TASK_NODENAME FROM JBPM6_TASK JT  WHERE ")
        .append(" JT.EXE_ID = E.EXE_ID AND JT.TASK_STATUS = 1 AND ROWNUM = 1) like '%预审%'");*/
        sql.append(" and e.cur_stepnames like '%预审' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        list = encryptionService.listDecrypt(list, "T_BSFW_USERINFO");
        for (Map<String, Object> map : list) {
            //获取任务截止时间  （预审截止时间为收件时间之后两个工作日。）
            String createTime = (String) map.get("CREATE_TIME");
            Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
            
            String endDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");    
            endDate = workdayService.getDeadLineDate(endDate, 2);
            endDate += " 00:00:00";
            String taskNodeName=(String)map.get("TASK_NODENAME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if(StringUtils.isNotEmpty(endDate)){
                int leftWorkDay = this.getLeftWorkDay(endDate,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            if("开始".equals(taskNodeName)){
                map.put("LEFT_WORKDAY", 3);
            }
        }
        
        return list;
    }
    /**
     * 
     * 描述  获取待我审批的预审补件列表
     * @author Faker Li
     * @created 2015年11月5日 下午4:00:45
     * @param sqlFilter
     * @return 
     */
    public List<Map<String, Object>> findNeedMeHandleAndysbj(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,");
        sql.append("E.SQRMC,E.SQRSJH,E.ITEM_NAME,E.ITEM_CODE,E.SQFS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME FROM JBPM6_TASK T");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" WHERE T.IS_AUDITED=1 AND T.ASSIGNER_TYPE=2 "
                + " AND  T.TASK_STATUS=-1 AND E.APPLY_STATUS = 4 AND E.SQFS=1");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
   
    /**
     * 
     * 描述 获取网站用户的待办任务列表
     * @author Flex Hu
     * @created 2015年12月3日 上午11:27:49
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findWebSiteUserTask(String userAccount){
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.EXE_ID,E.ITEM_CODE,E.APPLY_STATUS");
        sql.append(",E.ITEM_NAME,T.CREATE_TIME,B.BJXX_ID,T.FROMTASK_IDS FROM JBPM6_TASK T");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID  ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.ASSIGNER_CODE=? AND T.ASSIGNER_TYPE='2' AND (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') ");
        sql.append("AND T.IS_AUDITED=1 ORDER BY T.CREATE_TIME DESC");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{userAccount}, null);
        for (Map<String, Object> map : list) {
            // 获取退件的ID
            //String bjxxId = (String) map.get("BJXX_ID");
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        return list;
    }
    
    /**
     * 
     * 描述 根据流程实例ID和状态获取任务名称
     * @author Flex Hu
     * @created 2015年12月21日 下午3:05:02
     * @param exeId
     * @param taskStatus
     * @return
     */
    public Set<String> findTaskNames(String exeId,String taskStatus){
        List<String> taskNames = dao.findTaskNames(exeId, taskStatus);
        Set<String> setting = new HashSet<String>();
        for(String taskNodeName:taskNames){
            setting.add(taskNodeName);
        }
        return setting;
    }
    
    /**
     * 
     * 描述 挂起流程任务
     * @author Flex Hu
     * @created 2016年3月18日 下午3:33:28
     * @param exeId
     * @param currentTaskId
     */
    public void handUpFlowTask(String exeId,String currentTaskId){
        Map<String,Object> flowTask = dao.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{currentTaskId});
        //获取流程任务名称
        String taskName = (String) flowTask.get("TASK_NODENAME");
        //将流程任务更新为挂启
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_TASK J");
        sql.append(" SET J.TASK_STATUS=? WHERE J.EXE_ID=? ");
        sql.append(" AND J.TASK_NODENAME=? AND J.TASK_STATUS=? ");
        dao.executeSql(sql.toString(), new Object[]{"-1",exeId,taskName,"1"});
    }
    /**
     * 
     * 描述：根据父环节更新环节描述
     * @author Water Guo
     * @created 2017-9-22 下午2:34:02
     * @param parentTaskId
     */
    public void updateDescInfo(String parentTaskId,String descInfo){
        StringBuffer sql=new StringBuffer("");
        sql.append("UPDATE JBPM6_TASK T SET T.EXE_HANDLEDESC=? WHERE ");
        sql.append(" T.TASK_ID=? OR T.PARENT_TASKID=?");
        dao.executeSql(sql.toString(), new Object[]{descInfo,parentTaskId,parentTaskId});
    }
    /**
     * 
     * 描述 退回补件
     * @author Water Guo
     * @created 2016年11月21日 下午3:33:28
     * @param exeId
     * @param currentTaskId
     */
    public void returnFlowTask(String exeId, String currentTaskId){
        Map<String,Object> flowTask = dao.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{currentTaskId});
        //获取流程任务名称
        String taskName = (String) flowTask.get("TASK_NODENAME");
        //将流程任务更新为挂启
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_TASK J");
        sql.append(" SET J.TASK_STATUS=? WHERE J.EXE_ID=? ");
        sql.append(" AND J.TASK_NODENAME=? AND J.TASK_STATUS=? ");
        dao.executeSql(sql.toString(), new Object[]{"3",exeId,taskName,"1"});
    }
    /**
     * 描述：获取挂起信息
     * @author Water Guo
     * @created 2017-9-21 下午5:20:32
     * @param taskId
     * @return
     */
    public Map<String,Object> getHangInfo(String taskId){
        StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT * FROM  JBPM6_HANGINFO h");
        sql.append(" where  h.task_id=? order by begin_time desc) HANG WHERE ROWNUM=1 ");
        List<Map<String,Object>> list=dao.findBySql(sql.toString(), new String[]{taskId}, null);
        return list.get(0);
    }
    /**
     * 
     * 描述 批量重启流程任务
     * @author Flex Hu
     * @created 2016年3月18日 下午4:28:18
     * @param taskIds
     */
    public void startUpFlowTask(String taskIds){
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_TASK T SET T.TASK_STATUS='1'");
        sql.append(" WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK ");
        sql.append(" TA WHERE TA.Task_Id in ").append(StringUtil.getValueArray(taskIds));
        sql.append(" ) AND T.TASK_NODENAME IN (SELECT TA.Task_Nodename");
        sql.append(" FROM JBPM6_TASK TA WHERE TA.Task_Id IN ").append(StringUtil.getValueArray(taskIds));
        sql.append(" ) AND T.parent_taskid IN (SELECT TA.parent_taskid");
        sql.append(" FROM JBPM6_TASK TA WHERE TA.Task_Id IN ").append(StringUtil.getValueArray(taskIds));
        sql.append(" ) AND T.TASK_STATUS IN ('-1','3') ");
        dao.executeSql(sql.toString(), null);
    }
    
    /**
     * 
     * 描述 根据流程实例ID获取已经办理的流程任务列表
     * @author Flex Hu
     * @created 2016年4月14日 上午10:40:57
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findHandleOverTaskForLog(String exeId){
        return dao.findHandleOverTaskForLog(exeId);
    }
    /**
     * 
     * 描述：查看当前剩余可挂起环节
     * @author Water Guo
     * @created 2017-9-11 下午2:34:16
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findRestSpecialLink(String queryParam){
        String exeId = queryParam.split(",")[0];
        return dao.findRestSpecialLink(exeId);
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年4月20日 上午11:44:02
     * @param exeId
     * @param taskNodeName
     * @return
     */
    public List<Map<String, Object>> findTaskList(String exeId, String taskNodeName) {
        StringBuffer sql = new StringBuffer("select t.* from JBPM6_TASK t ");
        sql.append(" where t.task_nodename = ? and t.exe_id = ? ");
        sql.append(" and t.fromtask_ids in ( ");
        sql.append(" select fromtask_ids from JBPM6_TASK where task_nodename = ?  and exe_id = ? ");
        sql.append(" group by fromtask_ids ) order by t.create_time desc");
        return dao.findBySql(sql.toString(), new Object[]{taskNodeName,exeId,taskNodeName,exeId},null);
    }
    /**
     * 
     * 描述  根据流程实例和环节名称获取环节列表
     * @author Rider Chen
     * @created 2017年3月4日 下午6:26:44
     * @param exeId
     * @param taskNodeName
     * @return
     */
    public List<Map<String, Object>> findTaskToDodeList(String exeId, String taskNodeName) {
        StringBuffer sql = new StringBuffer("select t.* from JBPM6_TASK t ");
        sql.append(" where t.task_nodename = ? and t.exe_id = ? ");
        sql.append(" order by t.create_time desc");
        return dao.findBySql(sql.toString(), new Object[]{taskNodeName,exeId},null);
    }
    @Override
    public String findTaskId(String exeId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID FROM JBPM6_TASK T WHERE T.IS_AUDITED = 1 ");
        sql.append(" AND T.ASSIGNER_TYPE = 1  AND T.TASK_STATUS IN ('-1','1')  AND T.EXE_ID = ?");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{exeId},null);
        if(null!=list&&list.size()>0){
            return list.get(0).get("TASK_ID").toString();
        }else{
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findItemForDeptView(SqlFilter sqlFilter,
            boolean isHaveHandUp,String leftDayRange) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.SQRMC,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append(" T.ASSIGNER_CODE,T.ASSIGNER_NAME,D.DEPART_NAME from ("+setFromSql(sqlFilter,isHaveHandUp)+") T");
        sql.append("  LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" left join t_wsbs_serviceitem s on s.item_code = e.item_code");
        sql.append(" left join t_wsbs_serviceitem_catalog c on c.catalog_code = s.catalog_code ");
        sql.append(" left join t_msjw_system_department d on d.depart_id = c.child_depart_id ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());

        sqlFilter.getPagingBean().setPageSize(sqlFilter.getPagingBean().getTotalItems());
        
        list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        for (Map<String, Object> map : list) {
            // 获取退件的ID
            //String bjxxId = (String) map.get("BJXX_ID");
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
        //leftDayRange: 1剩余天数大于1天，0剩余天数为1天 或当天截止 -1已超期
        if(StringUtils.isNotEmpty(leftDayRange)){
            if (leftDayRange.equals("1")) {
                for (Map<String, Object> map : list) {
                    int leftWorkday = (Integer) map.get("LEFT_WORKDAY");
                    if (leftWorkday>1) {
                        returnList.add(map);
                    }
                }
            }else if(leftDayRange.equals("0")){
                for (Map<String, Object> map : list) {
                    int leftWorkday = (Integer) map.get("LEFT_WORKDAY");
                    if (leftWorkday==0||leftWorkday==1) {
                        returnList.add(map);
                    }
                }
            }else if(leftDayRange.equals("-1")){
                for (Map<String, Object> map : list) {
                    int leftWorkday = (Integer) map.get("LEFT_WORKDAY");
                    if (leftWorkday<0) {
                        returnList.add(map);
                    }
                }
            }
        }else{
            for (Map<String, Object> map : list) {
                    returnList.add(map);
            }
        }
        return returnList;
    }
    /**
     * from sql
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public String setFromSql(SqlFilter sqlFilter,
            boolean isHaveHandUp){
//        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.EXE_ID,T.CREATE_TIME,T.TASK_NODENAME,T.FROMTASK_IDS,");
        sql.append("T.TASK_DEADTIME,T.TASK_STATUS,T.ASSIGNER_CODE, T.ASSIGNER_NAME from  JBPM6_TASK T ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1  AND T.TASK_NATURE='1' ");
        
        SysUser curLoginUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = curLoginUser.getResKeys();
        /**
         * 2017年1月9日 15:18:37修改，当前部门改为授权部门
         * @author Rider Chen
         */
        String depCodes = curLoginUser.getAuthDepCodes();
        if ("__ALL".equals(resKey)){ 
            depCodes = curLoginUser.getDepCode();
        }
        if(!curLoginUser.getDepCode().equals("350128")){ 
            StringBuffer codes=new StringBuffer();
            String[] codeArray = depCodes.split(",");
            for (String code : codeArray) {
                if(!code.equals("350128")){
                    codes.append(",").append(code);
                }
            }
            if(StringUtils.isNotEmpty(codes.toString())){
                depCodes = codes.substring(1, codes.length());
            }
        }
        sql.append(" and T.ASSIGNER_CODE in (")
        .append(" select username from t_msjw_system_sysuser u where u.depart_id in")
        .append("(").append("select T.DEPART_ID from T_MSJW_SYSTEM_DEPARTMENT t WHERE t.depart_code in ")
        .append(StringUtil.getValueArray(depCodes)).append(")")
        .append(" )");    
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        sql.append(" union ");
        sql.append("SELECT T.TASK_ID,T.EXE_ID,T.CREATE_TIME,T.TASK_NODENAME,T.FROMTASK_IDS,");
        sql.append("T.TASK_DEADTIME,T.TASK_STATUS,T.TEAM_CODES ASSIGNER_CODE,T.TEAM_NAMES ASSIGNER_NAME ");
        sql.append(" from  JBPM6_TASK T WHERE T.ASSIGNER_TYPE=1  AND T.TASK_NATURE='2' AND T.TASK_ID IN ");
        sql.append("(select distinct parent_taskid  from JBPM6_TASK t where task_nature is null ");
        sql.append(" and parent_taskid is not null");

        
        sql.append(" and T.ASSIGNER_CODE in (")
        .append(" select username from t_msjw_system_sysuser u where u.depart_id in")
        .append("(").append("select T.DEPART_ID from T_MSJW_SYSTEM_DEPARTMENT t WHERE t.depart_code in ")
        .append(StringUtil.getValueArray(depCodes)).append(")")
        .append(" )");
        
        sql.append(") ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        //String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return sql.toString();
    }
    /**
     * team iterm
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public String findTeamItemForDeptView(SqlFilter sqlFilter,
            boolean isHaveHandUp) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append("T.TEAM_CODES ASSIGNER_CODE,T.TEAM_NAMES ASSIGNER_NAME FROM JBPM6_TASK T LEFT JOIN ");
        sql.append(" JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.ASSIGNER_TYPE=1 ");
        sql.append(" AND T.TASK_NATURE='2' AND T.TASK_ID IN ");
        sql.append("(select distinct parent_taskid  from JBPM6_TASK t where task_nature is null");
        sql.append(" and parent_taskid is not null ");
        SysUser curLoginUser = AppUtil.getLoginUser();
        sql.append(" and T.ASSIGNER_CODE in (")
        .append(" select username from t_msjw_system_sysuser u where u.depart_id='")
        .append(curLoginUser.getDepId()+"') ");
        sql.append(") ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return exeSql;
    }    

    /**
     * 
     * 描述   更新任务状态
     * @author Danto Huang
     * @created 2016-11-17 下午3:47:01
     * @param exeId
     * @param nodeName
     */
    public void updateByExeIdAndNodeName(String exeId,String nodeName){
        //将流程任务更新为发起人追回
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_TASK J");
        sql.append(" SET J.TASK_STATUS=? ,J.END_TIME=? WHERE J.EXE_ID=? ");
        sql.append(" AND J.TASK_NODENAME=? AND J.TASK_STATUS=? ");
        dao.executeSql(
                sql.toString(),
                new Object[] {
                        "7",
                        DateTimeUtil.getStrOfDate(new Date(),
                                "yyyy-MM-dd HH:mm:ss"), exeId, nodeName, "1" });
    }

    @Override
    public List<Map<String, Object>> getListByJdbc(String tableName, String taskId) {
        return dao.getListByJdbc(tableName, taskId);
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-17 下午4:10:43
     * @param exeId
     * @return
     */
    public int getMaxStepSeq(String exeId){
        return dao.getMaxStepSeq(exeId);
    }

    @Override
    public String getFrontLatestOverTask(String exeId, String handlerAccount) {
        return dao.getFrontLatestOverTask(exeId, handlerAccount);
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-14 上午9:58:31
     * @param fromTaskIds
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findFromtasks(String fromTaskIds,String exeId){
        String[] fromtaskNodes = fromTaskIds.split(",");
        StringBuffer sql=new StringBuffer();
        sql.append("select t.* from JBPM6_TASK t where t.exe_id=? and t.task_id in( ");
        for(int i=0;i<fromtaskNodes.length;i++){
            if(i==0){
                sql.append("?");
            }else{
                sql.append(",?");
            }
        }
        sql.append(")");
        String params = exeId + "," + fromTaskIds;
        return dao.findBySql(sql.toString(), params.split(","), null);
    }
    /**
     * 
     * 描述    获取食药监待我审批
     * @author Danto Huang
     * @created 2019年2月22日 下午4:01:49
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public List<Map<String,Object>> findFdaNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.END_TIME ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        String gsspBeginTime = "";
        for (Map<String, Object> map : list) {
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            //结束时间
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            gsspBeginTime = (String) map.get("TASK_CTIME"); 
            map.put("isYs", true);
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            if(StringUtils.isNotEmpty(gsspBeginTime)){
                map.put("WORK_HOURS",DateTimeUtil.getWorkTime(gsspBeginTime, endTime));                
            }
       
            
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/10 11:59:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findFundsFormList(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ( ");
        sql.append(" SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_COMMERCIAL_COMPANY B ON A.BUS_RECORDID = B.COMPANY_ID ");
        sql.append(" WHERE A.ITEM_CODE = '201605170002XK00101' AND B.ISFUNDSREGISTER = '1' ");
        sql.append(" AND B.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2') ");

        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.COMPANY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append("' AND n.ISFUNDSREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.BRANCH_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append("' AND n.ISFUNDSREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.INTERNAL_STOCK_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append("' AND n.ISFUNDSREGISTER = '1' ");
        sql.append("    AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.SOLELY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append("' AND n.ISFUNDSREGISTER = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" ) A  WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2021年10月8日 上午10:41:21
     * @param sqlFilter
     * @return
     * @see net.evecom.platform.hflow.service.FlowTaskService#findFundsFormList(net.evecom.core.util.SqlFilter)
     */
    @Override
    public List<Map<String, Object>> findTaxFormList(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ( ");
        sql.append(" SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_COMMERCIAL_COMPANY B ON A.BUS_RECORDID = B.COMPANY_ID ");
        sql.append(" WHERE A.ITEM_CODE = '201605170002XK00101' AND B.ISGETBILL = '1' ");
        sql.append(" AND B.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2') ");

        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.COMPANY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal());
        sql.append("' AND n.ISGETBILL = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.BRANCH_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_BRANCH.getVal());
        sql.append("' AND n.ISGETBILL = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.INTERNAL_STOCK_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_INTERNAL_STOCK.getVal());
        sql.append("' AND n.ISGETBILL = '1' ");
        sql.append("    AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" union all ");
        sql.append("  SELECT A.EXE_ID, A.SUBJECT, A.CREATE_TIME,'' DOWNLOAD FROM JBPM6_EXECUTION A ");
        sql.append("  LEFT JOIN  ").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append(" n ON A.BUS_RECORDID = n.SOLELY_ID   ");
        sql.append("    WHERE A.BUS_TABLENAME ='").append(TableNameEnum.T_COMMERCIAL_SOLELYINVEST.getVal());
        sql.append("' AND n.ISGETBILL = '1' ");
        sql.append("    AND n.SOCIAL_CREDITUNICODE IS NOT NULL AND (A.CUR_STEPNAMES = '办结' OR A.RUN_STATUS = '2')  ");
        sql.append(" ) A  WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    已办结流程，非实际办理人任务记录删除
     * @author Danto Huang
     * @created 2021年7月26日 上午9:14:19
     * @param exeId
     */
    public void deleteUndoTaskInfosByExeId(String exeId){
        String sql = "delete from JBPM6_TASK t where t.exe_id=? and t.is_monitor<>'1' and t.is_real_handled<>'1'";
        dao.executeSql(sql, new Object[]{exeId});
    }
}
