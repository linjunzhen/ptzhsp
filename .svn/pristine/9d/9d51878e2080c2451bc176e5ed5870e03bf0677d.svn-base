/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.ems.service.EmsService;
import net.evecom.platform.ems.util.EmsSend;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FromTask;
import net.evecom.platform.hflow.service.*;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.wsbs.service.BspjService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述 流程实例操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("executionService")
public class ExecutionServiceImpl extends BaseServiceImpl implements ExecutionService {
    
    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(ExecutionServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 引入Service
     */
    @Resource
    private BspjService bspjService;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * SysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * SysUserService
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    
    /**
     * emsService
     */
    @Resource
    private EmsService emsService;
    
    /**
     * 引入Service
     */
    @Resource
    private PrintAttachService printAttachService;
    
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/27 12:48:00
     * @param
     * @return
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    /**
     * 引入service
     */
    @Resource
    private JbpmService jbpmService;
    
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
     * 描述 保存流程实例
     * @author Flex Hu
     * @created 2015年8月18日 下午2:26:14
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> doSaveExecution(Map<String,Object> flowVars){
        //先获取流程实例申报号
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        //定义流程实例对象
        Map<String,Object> flowExe = null;
        if(StringUtils.isNotEmpty(exeId)){
            flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"}, new Object[]{exeId});
            if(flowExe!=null){
                //获取当前环节名称
                String CUR_STEPNAMES = (String) flowExe.get("CUR_STEPNAMES");
                //获取是否是暂存操作
                //String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE");
               // if(StringUtils.isNotEmpty(CUR_STEPNAMES)&&isTempSave.equals("-1")){
                    String EFLOW_ISBACK= (String) flowVars.get("EFLOW_ISBACK");
                    //获取标题名称
                    String EFLOW_SUBJECT = (String) flowVars.get("EFLOW_SUBJECT");
                    if(!(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true"))){
                        //获取申报名称
                        String sbmc = (String) flowVars.get("SBMC");
                        if(StringUtils.isNotEmpty(EFLOW_SUBJECT)&&StringUtils.isNotEmpty(sbmc)){
                            flowExe.put("SUBJECT", EFLOW_SUBJECT);
                        }
                        //获取申报状态的值
                        String EFLOW_APPLY_STATUS = (String) flowVars.get("EFLOW_APPLY_STATUS");
                        if(StringUtils.isNotEmpty(EFLOW_APPLY_STATUS)){
                            flowExe.put("APPLY_STATUS",EFLOW_APPLY_STATUS);
                        }
                        flowExe.putAll(flowVars);
                        dao.saveOrUpdate(flowExe, "JBPM6_EXECUTION", exeId);
                        //获取开始节点名称
                        String defId = (String) flowExe.get("DEF_ID");
                        int flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
                        String startNodeName = flowNodeService.getNodeName(defId,flowVersion,Jbpm6Constants.START_NODE);
                        //获取申请方式
                        String sqfs = (String) flowVars.get("SQFS");
                        if(startNodeName.equals(CUR_STEPNAMES)){
                            int applyStatus = 1;
                            StringBuffer sql = new StringBuffer("update JBPM6_EXECUTION J");
                            sql.append(" SET J.APPLY_STATUS=? WHERE J.EXE_ID=? ");
                            if("1".equals(sqfs)){
                                applyStatus = 1;
                            }else if("2".equals(sqfs)){
                                applyStatus = 5;
                            }
                            dao.executeSql(sql.toString(), new Object[]{applyStatus,exeId});
                        }
                    }
                //}
            }
        }else{
            //说明是启动流程
            //获取流程定义信息
            Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",
                new String[]{"DEF_ID"},new Object[]{flowVars.get("EFLOW_DEFID")});
            flowExe = new HashMap<String,Object>();
            flowExe.putAll(flowVars);
            flowExe.put("DEF_ID", flowVars.get("EFLOW_DEFID"));
            //定义流程标题
            StringBuffer subject = new StringBuffer("");
            if(flowVars.get("EFLOW_SUBJECT")!=null){
                subject.append(flowVars.get("EFLOW_SUBJECT"));
            }else{
                subject.append(flowDef.get("DEF_NAME"));
            }
            subject.append("【发起人:").append(flowVars.get("EFLOW_CREATORNAME")).append("】");
            flowExe.put("SUBJECT", subject.toString());
            flowExe.put("CREATOR_NAME", flowVars.get("EFLOW_CREATORNAME"));
            if(flowVars.get("EFLOW_ISTEMPSAVE").toString().equals("1")){
                //说明是暂存操作
                flowExe.put("RUN_STATUS",Jbpm6Constants.RUNSTATUS_DRAFT);
            }else{
                flowExe.put("RUN_STATUS",Jbpm6Constants.RUNSTATUS_RUNING);
            }
            flowExe.put("DEF_VERSION", flowDef.get("VERSION"));
            flowExe.put("CREATOR_ACCOUNT", flowVars.get("EFLOW_CREATORACCOUNT"));
            flowExe.put("CREATOR_ID", flowVars.get("EFLOW_CREATORID"));
            flowExe.put("CREATOR_PHONE", flowVars.get("EFLOW_CREATORPHONE"));
            flowExe.put("BUS_RECORDID", flowVars.get("EFLOW_BUSRECORDID"));
            flowExe.put("BUS_TABLENAME", flowVars.get("EFLOW_BUSTABLENAME"));
            if(flowVars.get("SOURCE")!=null){
                flowExe.put("SOURCE", flowVars.get("SOURCE"));
            }
            if(flowVars.get("EFLOW_HANDLE_OVERTIME")!=null){
                flowExe.put("HANDLE_OVERTIME", flowVars.get("EFLOW_HANDLE_OVERTIME"));
            }
            if(flowVars.get("EFLOW_CREATETIME")!=null){
                flowExe.put("CREATE_TIME", flowVars.get("EFLOW_CREATETIME"));
            }
            //获取申请方式
            String sqfs = (String) flowVars.get("SQFS");
            if("1".equals(sqfs)){
                flowExe.put("APPLY_STATUS",1);
            }else if("2".equals(sqfs)){
                flowExe.put("APPLY_STATUS",5);
            }
            //获取申报状态的值
            String EFLOW_APPLY_STATUS = (String) flowVars.get("EFLOW_APPLY_STATUS");
            if(StringUtils.isNotEmpty(EFLOW_APPLY_STATUS)){
                flowExe.put("APPLY_STATUS",EFLOW_APPLY_STATUS);
            }
            //结束保存申请人相关信息---------------------------------------
            //保存流程实例
            //获取申报号
            exeId = (String) flowVars.get("EFLOW_ASSIGNED_EXEID");//获取省网下发申报号
            if(StringUtils.isNotEmpty(exeId)){
                
            }else{
                exeId = this.getNextExeId(flowVars);
            }
            exeId = dao.saveOrUpdateForAssignId(flowExe, "JBPM6_EXECUTION", exeId);
            //exeId = dao.saveOrUpdate(flowExe, "JBPM6_EXECUTION",exeId,"SEQ_JBPM6_EXECUTION");
            flowVars.put("EFLOW_EXEID", exeId);
        }
        return flowVars;
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Map<String,Object>> list = dao.findBySqlFilter(sqlFilter);
        
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        
        return list;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilterAll(SqlFilter sqlFilter){
        List<Map<String,Object>> list = dao.findBySqlFilterAll(sqlFilter);
        
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        
        return list;
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
     * 描述 更新流程实例数据
     * @author Flex Hu
     * @created 2015年8月21日 上午10:04:43
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> updateFlowExe(Map<String,Object> flowVars){
        //获取流程申报号
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        int runStatus = Jbpm6Constants.RUNSTATUS_RUNING;
        //判断是否还存在运行任务
        boolean isHaveRunningTask = flowTaskService.isExistRunningTask(exeId);
        StringBuffer sql =new StringBuffer("update JBPM6_EXECUTION J ");
        sql.append(" SET J.CUR_STEPNAMES=?,J.CUR_STEPAUDITNAMES=?,J.CUR_STEPAUDITACCOUNTS=?,");
        sql.append("J.RUN_STATUS=? ");
        if(!isHaveRunningTask){
            sql.append(",J.FINAL_HANDLEOPINION=?,J.END_TIME=?,J.WORK_HOURS=?");
            String eFlowIsAgree = (String) flowVars.get("EFLOW_ISAGREE");
            if(StringUtils.isNotEmpty(eFlowIsAgree)&&eFlowIsAgree.equals("-1")){
                runStatus = Jbpm6Constants.RUNSTATUS_OVERSHBTG;
            }else if(StringUtils.isNotEmpty(eFlowIsAgree)&&eFlowIsAgree.equals("1")){
                runStatus = Jbpm6Constants.RUNSTATUS_OVERSHTG;
            }else{
                runStatus = Jbpm6Constants.RUNSTATUS_OVERZCJS;
            }
            flowVars.put("EFLOW_EXERUNSTATUS", String.valueOf(runStatus));
        }
        sql.append(" WHERE J.EXE_ID=? ");
        if(isHaveRunningTask){
            //获取新任务环节名称
            String newTaskNodeNames = (String) flowVars.get("EFLOW_NEWTASK_NODENAMES");
            Map<String,String> newCurrentTaskInfo  = null;
            if(StringUtils.isNotEmpty(newTaskNodeNames)){
                newCurrentTaskInfo = this.getNewCurrentTaskInfo(flowVars, exeId,true);
            }else{
                newCurrentTaskInfo = this.getNewCurrentTaskInfo(flowVars, exeId, false);
            }
            dao.executeSql(sql.toString(), new Object[]{newCurrentTaskInfo.get("CUR_STEPNAMES"),
                newCurrentTaskInfo.get("CUR_STEPAUDITNAMES"),
                newCurrentTaskInfo.get("CUR_STEPAUDITACCOUNTS"),runStatus,exeId});
        }else{
            Map<String,Object> flowExe = this.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
            String beginTime = (String) flowExe.get("CREATE_TIME");
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String handleOpinion = (String) flowVars.get("EFLOW_HANDLE_OPINION");
            String workHours = DateTimeUtil.getInternalTime(beginTime, endTime);
            dao.executeSql(sql.toString(), new Object[]{"","","",runStatus,handleOpinion,endTime,workHours,exeId});
        }
        
        return flowVars;
    }
    
    /**
     * 
     * 描述 获取最终环节信息
     * @author Flex Hu
     * @created 2015年8月22日 上午9:53:21
     * @param flowVars
     * @param exeId
     * @return
     */
    private Map<String,String> getNewCurrentTaskInfo(Map<String,Object> flowVars,String exeId,boolean isAssignNewTask){
        //获取新任务环节名称
        StringBuffer newTaskNodeNames = null;
        //获取新任务环节审核人编码
        StringBuffer newTaskHandlerCodes = null;
        StringBuffer newTaskHandlerNames = null;
        if(isAssignNewTask){
            newTaskNodeNames = new StringBuffer((String)flowVars.get("EFLOW_NEWTASK_NODENAMES"));
            newTaskHandlerCodes = new StringBuffer((String) flowVars.get("EFLOW_NEWTASK_HANDLERCODES"));
            newTaskHandlerNames = new StringBuffer((String) flowVars.get("EFLOW_NEWTASK_HANDLERNAMES"));
        }else{
            newTaskNodeNames = new StringBuffer("");
            newTaskHandlerCodes = new StringBuffer("");
            newTaskHandlerNames  = new StringBuffer("");
        }
        //获取当前正在运行的流程任务
        List<Map<String,Object>> runningTask = flowTaskService.findRunningTask(exeId);
        Map<String,String> currentTaskInfo = this.getNewestCurNodeInfo(exeId, newTaskNodeNames,
                newTaskHandlerCodes, newTaskHandlerNames,runningTask);
        return currentTaskInfo;
    }
    
    /**
     * 
     * 描述 获取最新的任务运行信息
     * @author Flex Hu
     * @created 2015年8月22日 下午7:58:26
     * @param exeId
     * @param newTaskNodeNames
     * @param newTaskHandlerCodes
     * @param newTaskHandlerNames
     * @return
     */
    private Map<String,String> getNewestCurNodeInfo(String exeId,StringBuffer newTaskNodeNames,
            StringBuffer newTaskHandlerCodes,StringBuffer newTaskHandlerNames,List<Map<String,Object>> runningTask){
        for(int i =0;i<runningTask.size();i++){
            Map<String,Object> task = runningTask.get(i);
            if(i==0){
                newTaskNodeNames.append(",");
                newTaskHandlerCodes.append(",");
                newTaskHandlerNames.append(",");
            }
            newTaskNodeNames.append(task.get("TASK_NODENAME")).append(",");
            newTaskHandlerCodes.append(task.get("TEAM_CODES")).append(",");
            newTaskHandlerNames.append(task.get("TEAM_NAMES")).append(",");
        }
        newTaskNodeNames.deleteCharAt(newTaskNodeNames.length()-1);
        newTaskHandlerCodes.deleteCharAt(newTaskHandlerCodes.length()-1);
        newTaskHandlerNames.deleteCharAt(newTaskHandlerNames.length()-1);
        Set<String> currentNodeNames = StringUtil.getSet(newTaskNodeNames.toString());
        Set<String> currentNodeHandlerCodes = StringUtil.getSet(newTaskHandlerCodes.toString());
        Set<String> currentNodeHandlerNames = StringUtil.getSet(newTaskHandlerNames.toString());
        Map<String,String> currentTaskInfo = new HashMap<String,String>();
        currentTaskInfo.put("CUR_STEPNAMES",StringUtil.getString(currentNodeNames));
        currentTaskInfo.put("CUR_STEPAUDITNAMES",StringUtil.getString(currentNodeHandlerNames));
        currentTaskInfo.put("CUR_STEPAUDITACCOUNTS",StringUtil.getString(currentNodeHandlerCodes));
        return currentTaskInfo;
    }
    
    /**
     * 
     * 描述 更新当前最新所处环节信息
     * @author Flex Hu
     * @created 2015年8月22日 下午7:52:09
     * @param exeId
     */
    @SuppressWarnings("unchecked")
    public void updateCurNodeInfo(String exeId) {
        StringBuffer sql = new StringBuffer("update JBPM6_EXECUTION J ");
        sql.append(" SET J.CUR_STEPNAMES=?,J.CUR_STEPAUDITNAMES=?,J.CUR_STEPAUDITACCOUNTS=?");

        List<Map<String, Object>> runningTask = flowTaskService.findAllRunningTask(exeId);
        if (runningTask != null && runningTask.size() > 0) {
            sql.append(" WHERE J.EXE_ID=? ");
            // 获取新任务环节名称
            StringBuffer newTaskNodeNames = new StringBuffer("");
            // 获取新任务环节审核人编码
            StringBuffer newTaskHandlerCodes = new StringBuffer("");
            StringBuffer newTaskHandlerNames = new StringBuffer("");
            Map<String, String> currentTaskInfo = this.getNewestCurNodeInfo(exeId, newTaskNodeNames,
                    newTaskHandlerCodes, newTaskHandlerNames, runningTask);
            dao.executeSql(sql.toString(),
                    new Object[] { currentTaskInfo.get("CUR_STEPNAMES"), currentTaskInfo.get("CUR_STEPAUDITNAMES"),
                            currentTaskInfo.get("CUR_STEPAUDITACCOUNTS"), exeId });
        } else {
            sql.append(",J.RUN_STATUS=?,J.END_TIME=?,J.WORK_HOURS=? ");
            sql.append(" WHERE J.EXE_ID=? ");
            Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
            String beginTime = (String) flowExe.get("CREATE_TIME");
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String workHours = DateTimeUtil.getInternalTime(beginTime, endTime);
            dao.executeSql(sql.toString(),
                    new Object[] {"","","",Jbpm6Constants.RUNSTATUS_OVERZCJS,endTime,workHours,exeId });
        }

    }
    
    /**
     * 
     * 描述 获取被某个用户办理的流程
     * @author Flex Hu
     * @created 2015年8月22日 下午5:12:29
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledByUser(SqlFilter sqlFilter,String userAccount){
        return dao.findHandledByUser(sqlFilter, userAccount);
    }
    /**
     * 
     * 描述：获取办理流程，不限制用户
     * @author Water Guo
     * @created 2017-10-16 下午2:33:58
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledAllUser(SqlFilter sqlFilter,String userAccount){
        return dao.findHandledAllUser(sqlFilter, userAccount);
    }
    /**
     * 
     * 描述 获取商事被某个用户办理的流程
     * @author Flex Hu
     * @created 2015年8月22日 下午5:12:29
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findZzhyHandledByUser(SqlFilter sqlFilter,String userAccount){
        return dao.findZzhyHandledByUser(sqlFilter, userAccount);
    }
    /**
     * 
     * 描述 删除掉流程实例,并且级联删除掉流程任务
     * @author Flex Hu
     * @created 2015年8月23日 上午7:07:59
     * @param exeIds
     */
    public void deleteByExeId(String[] exeIds){
        for(String exeId:exeIds){
            this.flowTaskService.remove("JBPM6_TASK","EXE_ID",new Object[]{exeId});
        }
        dao.remove("JBPM6_EXECUTION","EXE_ID",exeIds);
    }
    
    /**
     * 
     * 描述 终结流程实例
     * @author Flex Hu
     * @created 2015年8月23日 上午7:15:34
     * @param exeIds
     */
    public void endByExeId(String[] exeIds,String handleOpinion){
        StringBuffer sql =new StringBuffer("update JBPM6_EXECUTION J ");
        sql.append(" SET J.CUR_STEPNAMES=?,J.CUR_STEPAUDITNAMES=?,J.CUR_STEPAUDITACCOUNTS=?");
        sql.append(",J.RUN_STATUS=?,J.END_TIME=?,J.WORK_HOURS=?");
        sql.append(" WHERE J.EXE_ID=? ");
        for(String exeId:exeIds){
            this.flowTaskService.updateTaskToEnd(exeId,handleOpinion);
            //获取实例信息
            Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
            String beginTime = (String) flowExe.get("CREATE_TIME");
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String workHours = DateTimeUtil.getInternalTime(beginTime, endTime);
            dao.executeSql(sql.toString(), new Object[]{"","","",
                Jbpm6Constants.RUNSTATUS_OVERQZJS,endTime,workHours,exeId});
            if ("签章超期未通过自动退件".equals(handleOpinion)) {
                
            }else if (AppUtil.getLoginUser() != null) {
                // 开始保存工程建设项目审批事项办理详细信息
                if (StringUtils.isEmpty(handleOpinion)) {
                    handleOpinion = "审核不通过";
                }
                projectApplyService.saveAfterToXmspsxblxxxxb(13, exeId, AppUtil.getLoginUser().getFullname(),
                        handleOpinion);
                // 结束保存工程建设项目审批事项办理详细信息
            }
        }
        
    }
    
    /**
     * 
     * 描述 办结流程
     * @author Flex Hu
     * @created 2016年3月16日 下午3:55:54
     * @param exeId:实例ID
     * @param runStatus:流程状态
     * @param handleOverOpinion:办结意见
     */
    public void endByExeId(String exeId,int runStatus,String handleOverOpinion){
        StringBuffer sql =new StringBuffer("update JBPM6_EXECUTION J ");
        sql.append(" SET J.CUR_STEPNAMES=?,J.CUR_STEPAUDITNAMES=?,J.CUR_STEPAUDITACCOUNTS=?");
        sql.append(",J.RUN_STATUS=?,J.END_TIME=?,J.WORK_HOURS=?,J.FINAL_HANDLEOPINION=? ");
        sql.append(" WHERE J.EXE_ID=? ");
        this.flowTaskService.updateTaskToEnd(exeId,handleOverOpinion);
        //获取实例信息
        Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
        String beginTime = (String) flowExe.get("CREATE_TIME");
        String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String workHours = DateTimeUtil.getInternalTime(beginTime, endTime);
        String handleOpinion = "";
        if(StringUtils.isNotEmpty(handleOverOpinion)){
            handleOpinion = handleOverOpinion;
        }
        dao.executeSql(sql.toString(), new Object[]{"","","",
            runStatus,endTime,workHours,handleOpinion,exeId});
    }
    
    /**
     * 
     * 描述 获取我的申请数据
     * @author Flex Hu
     * @created 2015年8月25日 上午9:03:45
     * @param sqlFilter
     * @param busTableName
     * @return
     */
    public List<Map<String,Object>> findMyApply(SqlFilter sqlFilter,String busTableName,Set<String> queryFields){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,");
        //获取主键名称
        String primaryKeyName = (String) this.getPrimaryKeyName(busTableName).get(0);
        for(String fieldName:queryFields){
            sql.append("BUS.").append(fieldName).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" FROM JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID");
        sql.append(" LEFT JOIN ").append(busTableName).append(" BUS ON T.Bus_Recordid=BUS.").append(primaryKeyName);
        sql.append(" WHERE T.BUS_TABLENAME='").append(busTableName.toUpperCase()).append("' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = dao.setExeWorkHours(list);
        return list;
    }
    
    /**
     * 
     * 描述 获取经我审批的业务数据
     * @author Flex Hu
     * @created 2015年8月25日 上午9:21:02
     * @param sqlFilter
     * @param busTableName
     * @param queryFields
     * @return
     */
    public List<Map<String,Object>> findHandledByMe(SqlFilter sqlFilter,String busTableName
            ,Set<String> queryFields,String userAccount){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,");
        //获取主键名称
        String primaryKeyName = (String) this.getPrimaryKeyName(busTableName).get(0);
        for(String fieldName:queryFields){
            sql.append("BUS.").append(fieldName).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" FROM JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID");
        sql.append(" LEFT JOIN ").append(busTableName).append(" BUS ON T.Bus_Recordid=BUS.").append(primaryKeyName);
        sql.append(" WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA WHERE TA.ASSIGNER_CODE=? ");
        params.add(userAccount);
        sql.append(" AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' )");
        sql.append(" AND T.BUS_TABLENAME=? ");
        params.add(busTableName.toUpperCase());
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = dao.setExeWorkHours(list);
        return list;
    }
    
    /**
     * 
     * 描述 删除刚版本正在跑的数据
     * @author Flex Hu
     * @created 2015年8月27日 上午11:06:33
     * @param defId
     * @param flowVersion
     */
    public void deleteExeCascadeTask(String defId,int flowVersion){
        //获取获取正在跑的流程
        List<String> exeIds = dao.findExeIds(defId, flowVersion);
        for(String exeId:exeIds){
            this.flowTaskService.deleteByExeId(exeId);
            dao.remove("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
        }
    }
    
    /***
     * 
     * 描述 获取流程申报号
     * @author Flex Hu
     * @created 2015年11月12日 下午4:10:43
     * @return
     */
    public String getNextExeId(Map<String,Object> flowVars){
        return dao.getNextExeId(flowVars);
    }

    /**
     * 
     * 描述 获取前台我的办件列表
     * @author Faker Li
     * @created 2015年12月1日 上午9:55:53
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> findfrontWdbjList(String page, String rows,String yhzh) {
        Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer( "SELECT RS.IS_OPEN,E.EXE_ID,E.ITEM_NAME,E.RUN_STATUS,E.ITEM_CODE,E.SQRMC CREATOR_NAME,");
        sql.append("E.FINAL_HANDLEOPINION,E.CREATE_TIME,E.APPLY_STATUS,B.BJXX_ID,E.CUR_STEPNAMES, ");
        sql.append("E.ISNEEDSIGN,E.ISGETBILL,E.ISFACESIGN,RS.RESULT_FILE_ID, ");
        sql.append(" (case when  R.REVOKE_STATUS is null then 2  else R.REVOKE_STATUS end) REVOKE_STATUS");
        sql.append(" FROM VIEW_JBPM6_EXECUTION_NEW E ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON E.EXE_ID=B.EXE_ID ");
        sql.append(" LEFT JOIN VIEW_JBPM6_FLOW_RESULT_NEW RS ON E.EXE_ID=RS.EXE_ID ");
        sql.append(" LEFT JOIN JBPM6_REVOKE R   ON E.EXE_ID = R.EXE_ID AND R.REVOKE_STATUS=0");
        sql.append(" WHERE E.CREATOR_ACCOUNT = '"+yhzh+"' ");
        sql.append(" AND E.SQFS = '1' ");
        sql.append(" ORDER BY E.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object>  bspj = null;
            String exeId=list.get(i).get("EXE_ID").toString();
            bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
                    new String[]{"EXE_ID"},new Object[]{list.get(i).get("EXE_ID").toString()});
            if(bspj!=null){
                list.get(i).put("isPj", "1");//已评价
            }else{
                list.get(i).put("isPj", "0");//未评价
            }
            //设置状态标签
            String runstatus=list.get(i).get("RUN_STATUS").toString();
            String applystatus=list.get(i).get("APPLY_STATUS").toString();
            String revokeStatus=list.get(i).get("REVOKE_STATUS")==null?"":list.get(i).get("REVOKE_STATUS").toString();
            if("0".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">草稿</b>");
            }else if("1".equals(runstatus)){
                String taskStatus=null;
                boolean preAuditFlag=false;
                StringBuffer sql2 = new StringBuffer("SELECT TASK_ID,FROMTASK_IDS,TASK_NODENAME," +
                        "ASSIGNER_CODE,TASK_STATUS,fromtask_nodenames from VIEW_JBPM6_TASK_NEW T ");
                sql2.append(" WHERE (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') AND T.EXE_ID=? ");
                sql2.append(" AND T.ASSIGNER_TYPE = '2' ");
                sql2.append(" AND T.IS_AUDITED=1  ORDER BY T.CREATE_TIME DESC");
                //查询当前待办的任务,获取前一个任务的审批意见
                List<Map<String,Object>> tasks = dao.findBySql(sql2.toString(),new Object[]{exeId}, null);
                if(tasks!=null&&tasks.size()>0){
                    String fromTaskIds = (String) tasks.get(0).get("FROMTASK_IDS");
                    if (StringUtils.isNotEmpty(fromTaskIds)) {
                        Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                                new Object[] { fromTaskIds.split(",")[0] });
                        // 获取退回意见
                        String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                        list.get(i).put("BACKOPINION", BACKOPINION);
                        if(tasks.get(0).get("TASK_NODENAME").toString().contains("预审")||
                                "预审".equals(tasks.get(0).get("TASK_NODENAME").toString())||
                                "网上预审".equals(tasks.get(0).get("TASK_NODENAME").toString())){
                            list.get(i).put("preAuditState", "1");
                            preAuditFlag=true;
                        }
                    }
                    if(yhzh.equals(tasks.get(0).get("ASSIGNER_CODE"))
//                            &&"2".equals(tasks.get(0).get("ASSIGNER_TYPE"))
                            ){
                        list.get(i).put("TASK_STATUS", tasks.get(0).get("TASK_STATUS"));//待办；
                        list.get(i).put("TASK_ID", tasks.get(0).get("TASK_ID"));
                        taskStatus=tasks.get(0).get("TASK_STATUS").toString();
                    }
                }
                String nodeName="";
                if("1".equals(taskStatus)){
                    String fromtaskNodenames=String.valueOf(tasks.get(0).get("fromtask_nodenames"));
                    nodeName=String.valueOf(tasks.get(0).get("TASK_NODENAME"));
                    if("预审意见汇总".equals(fromtaskNodenames)&&"开始".equals(nodeName)) {
                        list.get(i).put("runStatusStr", "<b style=\"color: red;\" >预审不通过</b>");
                    }else{
                        list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">待办</b>");
                    }
                }else if("-1".equals(taskStatus)){
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">退件</b>");
                }else if(preAuditFlag){
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">预审中</b>");
                }else{
                    if("5".equals(applystatus)){
                        String curName=StringUtil.getString(list.get(i).get("CUR_STEPNAMES"));
                        list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">"+curName+"</b>");
                    }else{
                        list.get(i).put("runStatusStr","<b style=\"color: #008000;\">正在办理</b>");
                        String EXE_ID=StringUtil.getString(list.get(i).get("EXE_ID"));
                        String IS_SIGN = bdcQlcMaterGenAndSignService.getSignStatusByExeId(EXE_ID);
                        if (StringUtils.isNotEmpty(IS_SIGN) && "2".equals(IS_SIGN)) {
                            list.get(i).put("runStatusStr","<b style=\"color: #008000;\">已签章</b>");
                        } else if (StringUtils.isNotEmpty(IS_SIGN) && !"2".equals(IS_SIGN)) {
                            list.get(i).put("runStatusStr","<b style=\"color: #008000;\">待签章</b>");
                        }
                    }
                }
            }else if("2".equals(runstatus)||"6".equals(runstatus)||"3".equals(runstatus)
                    ||"4".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue\">已办结</b>");
            }else if("5".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue;\">退件办结</b>");
            }else if("7".equals(runstatus)){
                list.get(i).put("runStatusStr","<b style=\"color:blue;\">已办结（预审不通过）</b>");
            }
            if("0".equals(revokeStatus)){
                list.get(i).put("runStatusStr","<b style=\"color: #008000;\">申请撤办</b>");           
            }
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    /**
     * 
     * 描述      获取前台我的办件列表 新增事项编码参数
     * @author Yanisin Shi
     * @param page
     * @param rows
     * @param yhzh
     * @return
     * @see net.evecom.platform.hflow.service.ExecutionService#findfrontWdbjList(java.lang.String, java.lang.String, java.lang.String)
     */
    public Map<String, Object> findfrontWdbjList(String page, String rows,String yhzh,String itemCode) {
        Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer( "SELECT RS.IS_OPEN,E.EXE_ID,E.ITEM_NAME,E.RUN_STATUS,E.ITEM_CODE,E.SQRMC CREATOR_NAME,");
        sql.append("E.FINAL_HANDLEOPINION,E.CREATE_TIME,E.APPLY_STATUS,B.BJXX_ID,E.CUR_STEPNAMES, ");
        sql.append("E.ISNEEDSIGN,E.ISGETBILL,E.ISFACESIGN,RS.RESULT_FILE_ID, ");
        sql.append(" (case when  R.REVOKE_STATUS is null then 2  else R.REVOKE_STATUS end) REVOKE_STATUS,E.SUBJECT ");
        sql.append(" FROM JBPM6_EXECUTION E ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON E.EXE_ID=B.EXE_ID ");
        sql.append(" LEFT JOIN JBPM6_FLOW_RESULT RS ON E.EXE_ID=RS.EXE_ID ");
        sql.append(" LEFT JOIN JBPM6_REVOKE R   ON E.EXE_ID = R.EXE_ID AND R.REVOKE_STATUS=0");
        sql.append(" WHERE E.CREATOR_ACCOUNT = '"+yhzh+"' AND E.SQFS ='1' ");
       if(StringUtils.isNotEmpty(itemCode)){
        sql.append("  AND E.ITEM_CODE='"+ itemCode+"'");
       }
        sql.append(" ORDER BY E.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object>  bspj = null;
            String exeId=list.get(i).get("EXE_ID").toString();
            bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
                    new String[]{"EXE_ID"},new Object[]{list.get(i).get("EXE_ID").toString()});
            if(bspj!=null){
                list.get(i).put("isPj", "1");//已评价
            }else{
                list.get(i).put("isPj", "0");//未评价
            }
            //设置状态标签
            String runstatus=list.get(i).get("RUN_STATUS").toString();
            String applystatus=list.get(i).get("APPLY_STATUS").toString();
            String revokeStatus=list.get(i).get("REVOKE_STATUS")==null?"":list.get(i).get("REVOKE_STATUS").toString();
            if("0".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">草稿</b>");
            }else if("1".equals(runstatus)){
                String taskStatus=null;
                boolean preAuditFlag=false;
                StringBuffer sql2 = new StringBuffer("SELECT TASK_ID,FROMTASK_IDS,TASK_NODENAME," +
                        "ASSIGNER_CODE,TASK_STATUS,fromtask_nodenames from JBPM6_TASK T ");
                sql2.append(" WHERE (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') AND T.EXE_ID=? ");
                sql2.append(" AND T.ASSIGNER_TYPE = '2' ");
                sql2.append(" AND T.IS_AUDITED=1  ORDER BY T.CREATE_TIME DESC");
                //查询当前待办的任务,获取前一个任务的审批意见
                List<Map<String,Object>> tasks = dao.findBySql(sql2.toString(),new Object[]{exeId}, null);
                if(tasks!=null&&tasks.size()>0){
                    String fromTaskIds = (String) tasks.get(0).get("FROMTASK_IDS");
                    if (StringUtils.isNotEmpty(fromTaskIds)) {
                        Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                                new Object[] { fromTaskIds.split(",")[0] });
                        // 获取退回意见
                        String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                        list.get(i).put("BACKOPINION", BACKOPINION);
                        if(tasks.get(0).get("TASK_NODENAME").toString().contains("预审")||
                                "预审".equals(tasks.get(0).get("TASK_NODENAME").toString())||
                                "网上预审".equals(tasks.get(0).get("TASK_NODENAME").toString())){
                            list.get(i).put("preAuditState", "1");
                            preAuditFlag=true;
                        }
                    }
                    if(yhzh.equals(tasks.get(0).get("ASSIGNER_CODE"))
//                            &&"2".equals(tasks.get(0).get("ASSIGNER_TYPE"))
                            ){
                        list.get(i).put("TASK_STATUS", tasks.get(0).get("TASK_STATUS"));//待办；
                        list.get(i).put("TASK_ID", tasks.get(0).get("TASK_ID"));
                        taskStatus=tasks.get(0).get("TASK_STATUS").toString();
                    }
                }
                String nodeName="";
                if("1".equals(taskStatus)){
                    String fromtaskNodenames=String.valueOf(tasks.get(0).get("fromtask_nodenames"));
                    nodeName=String.valueOf(tasks.get(0).get("TASK_NODENAME"));
                    if("预审意见汇总".equals(fromtaskNodenames)&&"开始".equals(nodeName)) {
                        list.get(i).put("runStatusStr", "<b style=\"color: red;\" >预审不通过</b>");
                    }else{
                        list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">待办</b>");
                    }
                }else if("-1".equals(taskStatus)){//-1时为退回补件，runstatus为4时代表退件。
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">办理</b>");
                }else if(preAuditFlag){
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">预审中</b>");
                }else{
                    if("5".equals(applystatus)){
                        String curName=StringUtil.getString(list.get(i).get("CUR_STEPNAMES"));
                        list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">"+curName+"</b>");
                    }else{
                        list.get(i).put("runStatusStr","<b style=\"color: #008000;\">正在办理</b>");
                        String EXE_ID=StringUtil.getString(list.get(i).get("EXE_ID"));
                        String IS_SIGN = bdcQlcMaterGenAndSignService.getSignStatusByExeId(EXE_ID);
                        if (StringUtils.isNotEmpty(IS_SIGN) && "2".equals(IS_SIGN)) {
                            list.get(i).put("runStatusStr","<b style=\"color: #008000;\">已签章</b>");
                        } else if (StringUtils.isNotEmpty(IS_SIGN) && !"2".equals(IS_SIGN)) {
                            list.get(i).put("runStatusStr","<b style=\"color: #008000;\">待签章</b>");
                        }
                    }
                }
            }else if("2".equals(runstatus)||"6".equals(runstatus)||"3".equals(runstatus)
                    ||"4".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue\">已办结</b>");
            }else if("5".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue;\">退件办结</b>");
            }else if("7".equals(runstatus)){
                list.get(i).put("runStatusStr","<b style=\"color:blue;\">已办结（预审不通过）</b>");
            }
            if("0".equals(revokeStatus)){
                list.get(i).put("runStatusStr","<b style=\"color: #008000;\">申请撤办</b>");           
            }
            String curName=StringUtil.getString(list.get(i).get("CUR_STEPNAMES"));
            if("-1".equals(runstatus) && "开始".equals(curName)){
                list.get(i).put("runStatusStr","<b style=\"color: #008000;\">退回补件</b>");
            }
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    /**
     * 
     * 描述 获取记录条数面向网站用户
     * @author Flex Hu
     * @created 2015年12月3日 下午2:42:42
     * @param userAccount
     * @return
     */
    public Map<String,String> getReportCountForWebSite(String userAccount){
        return dao.getReportCountForWebSite(userAccount);
    }    

    /**
     * 
     * 描述 获取已办结流程实例（首页我要查）
     * @author Danto Huang
     * @created 2015-12-4 上午10:12:35
     * @return
     */
    public List<Map<String,Object>> getEndExecution(){
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean(0, 7);
        StringBuffer sql = new StringBuffer("SELECT TSD.DEPART_NAME,T.ITEM_NAME,T.ITEM_CODE,");
        sql.append("SUBSTR(T.CREATE_TIME,0,10) CREATE_TIME,SUBSTR(T.END_TIME,0,10) END_TIME,'办结' STATUS ");
        sql.append("FROM JBPM6_EXECUTION T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT TSD ON TSD.DEPART_CODE = T.SSBMBM ");
        sql.append("WHERE T.END_TIME IS NOT NULL AND T.ITEM_CODE IS NOT NULL ORDER BY T.CREATE_TIME DESC");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        return list;
    }

    /**
     * 
     * 描述 根据办件时间和状态统计数量
     * @author Faker Li
     * @created 2015年12月8日 上午10:18:46
     * @param time
     * @param state
     * @return
     */
    public int getTjByStateAndTime(String time, String state) {
        return dao.getTjByStateAndTime(time,state);
    }
    /**
     * 
     * 描述：根据办结时间和 状态来统计
     * @author Water Guo
     * @created 2017-6-16 下午4:40:45
     * @param time
     * @param state
     * @return
     */
    public int getTjByEndTime(String time,String state){
        return dao.getTjByEndTime(time,state);
    }
    /**
     * 
     * 描述 获取前台热门办件
     * @author Faker Li
     * @created 2015年12月10日 上午10:45:01
     * @param page
     * @param rows
     * @return
     * @see net.evecom.platform.hflow.service.ExecutionService#findByRdbs(java.lang.String, java.lang.String)
     */
    public Map<String, Object> findByRdbs(String page, String rows) {
        Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        /**
         *  2017年1月12日 10:31:39 更改SQL提高查询效率
         *  @author Rider Chen
         */
        StringBuffer sql = new StringBuffer("SELECT B.ITEM_ID,B.ITEM_CODE, B.ITEM_NAME,D.DEPART_NAME,B.RZBSDTFS,");
        sql.append(" COUNT(*) AS itemCount");
        sql.append(" FROM T_WSBS_SERVICEITEM B ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=B.SSBMBM ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION T on  T.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" WHERE B.FWSXZT = '1' ");
        sql.append(" GROUP BY  B.ITEM_ID, B.ITEM_CODE,B.ITEM_NAME, D.DEPART_NAME, B.RZBSDTFS");
        sql.append(" ORDER BY itemCount DESC");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> e = busTypeService.getIdAndNamesByItemId((String)list.get(i).get("ITEM_ID"));
            String busTypenames = "";
            String typeids = e.get("TYPE_IDS").toString();
            if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                    || typeids.contains("4028818c512273e70151227569a40001")
                    || typeids.contains("4028818c512273e70151229ae7e00003")
                    || typeids.contains("4028818c512273e7015122a38a130004")) {
                busTypenames += "个人";
            }
            if (typeids.contains("4028818c512273e7015122a452220005")
                    || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                    || typeids.contains("4028818c512273e7015122c81f850007")
                    || typeids.contains("4028818c512273e7015122c872dc0008")
                    || typeids.contains("402883494fd4f3aa014fd4fc68260003")) {
                if (busTypenames.length() > 1) {
                    busTypenames += ",";
                }
                busTypenames += "企业";
            }
            list.get(i).put("BUS_TYPENAMES", busTypenames);
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    /**
     * 
     * 描述：更新推送状态
     * @author Water Guo
     * @created 2017-9-28 下午12:01:16
     * @param exeId
     */
    public void updateRes(String exeId){
        dao.updateRes(exeId);
    }
    @Override
    public List<Map<String, Object>> findWXWdbjList(String yhzh) {
        //Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer( "SELECT E.EXE_ID,E.ITEM_NAME,E.RUN_STATUS,E.ITEM_CODE,");
        sql.append("E.FINAL_HANDLEOPINION,E.CREATE_TIME  FROM JBPM6_EXECUTION E ");
        sql.append(" WHERE E.CREATOR_ACCOUNT = '"+yhzh+"' ");
        sql.append(" AND E.SQFS = '1' ");
        sql.append(" ORDER BY E.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(),null);
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object>  bspj = null;
            bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
                    new String[]{"EXE_ID"},new Object[]{list.get(i).get("EXE_ID").toString()});
            if(bspj!=null){
                list.get(i).put("isPj", "1");//已评价
            }else{
                list.get(i).put("isPj", "0");//未评价
            }
        }
        return list;
    }

    /**
     * 
     * 描述 根据部门编码获取头15条已办结数据
     * @author Faker Li
     * @created 2016年1月28日 下午2:14:10
     * @return
     */
    public List<Map<String, Object>> getEndExecutionByDepartCode(String departCode) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean(0, 15);
        StringBuffer sql = new StringBuffer("SELECT TSD.DEPART_NAME,T.ITEM_NAME,T.ITEM_CODE,");
        sql.append("SUBSTR(T.CREATE_TIME,0,10) CREATE_TIME,SUBSTR(T.END_TIME,0,10) END_TIME,'办结' STATUS ");
        sql.append("FROM JBPM6_EXECUTION T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT TSD ON TSD.DEPART_CODE = T.SSBMBM ");
        sql.append("WHERE T.END_TIME IS NOT NULL AND T.ITEM_CODE IS NOT NULL ");
        sql.append(" AND T.SSBMBM = ?  ORDER BY T.CREATE_TIME DESC");
        params.add(departCode);
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        return list;
    }
    
    
    /**
     * 
     * 描述 获取退回流程信息对象
     * @author Flex Hu
     * @created 2016年1月29日 下午3:56:21
     * @param flowSubmitInfoJson
     * @return
     */
    public Map<String,Object> getBackFlowObj(String flowSubmitInfoJson){
        Map<String,Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson,Map.class);
        //获取当前审核人所处的任务ID
        String currentTaskId = (String) flowSubmitInfo.get("EFLOW_CURRENTTASK_ID");
        //获取当前任务信息
        Map<String,Object> currentTask = this.flowTaskService.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{currentTaskId});
        currentTask = this.getFirstOverSameTask(currentTask);
        String fromTaskJson = (String) currentTask.get("FROMTASK_JSON");
        StringBuffer backNodeNames = new StringBuffer("");
        if(StringUtils.isEmpty(fromTaskJson)){
            String parentTaskId = (String) currentTask.get("PARENT_TASKID");
            Map<String,Object> parentTask = this.flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{parentTaskId});
            fromTaskJson = (String) parentTask.get("FROMTASK_JSON");
        }
        //将来源JSON转换成下一环节列表
        List<FromTask> formTaskList = JSON.parseArray(fromTaskJson, FromTask.class);
//        //获取商事流程，并判断当前节点是工商审批
        List<String> arrlist = Arrays.asList("ss001","ss002","ss003","ss004"); 
        String eflowDefkey=flowSubmitInfo.get("EFLOW_DEFKEY").toString();//流程定义key
        //只有商事审批的流程并且在工商审批环节执行一下操作
        if(arrlist.indexOf(eflowDefkey)!=-1&&"工商审批".equals(currentTask.get("TASK_NODENAME"))){
            int eflowDefversion=Integer.parseInt(flowSubmitInfo.get("EFLOW_DEFVERSION").toString());//流程版本号码
            String eflowDefid=flowSubmitInfo.get("EFLOW_DEFID").toString();//流程定义ID
            String romtaskNodenames=currentTask.get("FROMTASK_NODENAMES").toString();
            Map reviewerMap=
                    flowNodeService.getFlowNodeReviewerByKey(eflowDefid, 
                            eflowDefversion, eflowDefkey, romtaskNodenames);
            if(reviewerMap!=null){
                String varsValue= reviewerMap.get("VARS_VALUE").toString();
                List<FlowNextHandler> handlerList=sysUserService.getHandlers(varsValue);
                formTaskList.get(0).setHandlers(handlerList);
            }
        }
        for(FromTask task:formTaskList){
            backNodeNames.append(task.getTaskName()).append(",");
        }
        backNodeNames.deleteCharAt(backNodeNames.length()-1);
        flowSubmitInfoJson = JSON.toJSONString(flowSubmitInfo);
        Map<String,Object> backFlowObj = new HashMap<String,Object>();
        backFlowObj.put("backNodeNames", backNodeNames.toString());
        backFlowObj.put("nextTrans", formTaskList);
        backFlowObj.put("flowSubmitInfoJson",StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        return backFlowObj;
    }
    
    /**
     * 
     * 描述 获取最先产生的相同名称任务的环节
     * @author Flex Hu
     * @created 2016年3月16日 下午4:35:42
     * @param currentTask
     * @return
     */
    private Map<String,Object> getFirstOverSameTask(Map<String,Object> currentTask){
        String exeId = (String) currentTask.get("EXE_ID");
        String taskNodeName = (String) currentTask.get("TASK_NODENAME");
        String assignerCode = (String) currentTask.get("ASSIGNER_CODE");
        StringBuffer sql = new StringBuffer("SELECT * FROM JBPM6_TASK T ");
        sql.append("WHERE T.EXE_ID=? AND T.ASSIGNER_CODE=? ");
        sql.append("AND T.TASK_NODENAME=? ORDER BY T.CREATE_TIME ASC");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{exeId,assignerCode,taskNodeName},
                null);
        return list.get(0);
    }
    
    /**
     * 
     * 描述 更新流程为预审不通过状态
     * @author Flex Hu
     * @created 2016年2月24日 上午10:22:39
     * @param exeId
     */
    public void updateExeToNoPass(String exeId,String handlerOpinion,Map<String, Object> variables){
        this.endByExeId(new String[]{exeId},handlerOpinion);
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_EXECUTION J");
        sql.append(" SET J.FINAL_HANDLEOPINION=?,J.APPLY_STATUS=?,J.RUN_STATUS=? ");
        sql.append(" WHERE J.EXE_ID=? ");
        this.dao.executeSql(sql.toString(), new Object[]{handlerOpinion,"3","7",exeId});
        sql = new StringBuffer("update JBPM6_TASK t set t.IS_REAL_HANDLED='1' where t.EXE_ID=? and t.TASK_ID=?");
        this.dao.executeSql(sql.toString(), new Object[]{exeId,variables.get("EFLOW_CURRENTTASK_ID")});
        //预审不通过task描述
        String taskId=(String)variables.get("EFLOW_CURRENTTASK_ID");
        Map<String,Object> task=this.dao.getByJdbc("JBPM6_TASK", new String[]{"TASK_ID"}, new Object[]{taskId});
        String parentTaskId=(String)task.get("PARENT_TASKID");
        String userName=AppUtil.getLoginUser().getFullname();
        String desc="["+userName+"]"+"预审环节，审核不通过";
        StringBuffer updateDesc=new StringBuffer("UPDATE JBPM6_TASK T SET");
        updateDesc.append(" T.EXE_HANDLEDESC=? WHERE T.EXE_ID=? AND T.TASK_ID=?");
        this.dao.executeSql(updateDesc.toString(), new Object[]{desc,exeId,parentTaskId});
        
        Map<String,Object> taskupd = new HashMap<String, Object>();
        taskupd.put("IS_REAL_HANDLED", 1);
        dao.saveOrUpdate(taskupd, "JBPM6_TASK", taskId);
        
        Map<String, Object> bjxx = new HashMap<String, Object>();
        bjxx.put("EXE_ID", (String) variables.get("EFLOW_EXEID"));
        bjxx.put("TASK_ID", (String) variables.get("EFLOW_CURRENTTASK_ID"));
        bjxx.put("BJCLLB", (String) variables.get("BJCLLB"));
        bjxx.put("SFFDX", "0");//默认发送短信
        bjxx.put("BJYJ", (String) variables.get("EFLOW_HANDLE_OPINION"));
        dao.saveOrUpdate(bjxx, "T_WSBS_BJXX","");
        //获取流程实例信息
        Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
        //获取申报的项目
        //String itemName = (String) flowExe.get("ITEM_NAME");
        //获取经办人的手机号
        String mobileNum = (String) flowExe.get("JBR_MOBILE");
        //获取申报时间
        //String createTime = (String) flowExe.get("CREATE_TIME");
//        StringBuffer mobileMsg = new StringBuffer("您好,您于");
//        mobileMsg.append(createTime).append("申报的[").append(itemName);
//        mobileMsg.append("]项目预审不通过,不通过原因是:").append(handlerOpinion);
        String curdate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String subject = (String)flowExe.get("SUBJECT");
        int len = subject.indexOf("【");
        if(len!=-1){
            subject = subject.substring(0, len);
        }
        StringBuffer mobileMsg = new StringBuffer("您好,您于").append(flowExe.get("CREATE_TIME"));
        mobileMsg.append("在区行政服务中心网上办事大厅申请的办件申报号是:").append(exeId).append("，标题为“");
        mobileMsg.append(subject).append("”,已于").append(curdate);
        mobileMsg.append("预审不通过").append(",请及时查阅。");
        if(StringUtils.isNotEmpty(mobileNum)){
            log.info("接收的手机号:"+mobileNum+",内容是:"+mobileMsg.toString());
            SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobileNum);
        }
        String EFLOW_APPMATERFILEJSON = (String) variables.get("EFLOW_APPMATERFILEJSON");
        if(StringUtils.isNotEmpty(EFLOW_APPMATERFILEJSON)&&!EFLOW_APPMATERFILEJSON.equals("[]")){
            fileAttachService.saveAuditerFiles(EFLOW_APPMATERFILEJSON,variables);
        }
        
        //已办结流程，非实际办理人任务记录删除
        flowTaskService.deleteUndoTaskInfosByExeId(exeId);
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月10日 上午9:57:53
     * @param string
     * @param sqfs
     * @return
     */
    public int getSjsByTime(String time, String sqfs) {
        return dao.getSjsByTime(time,sqfs);
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月10日 下午3:53:30
     * @param sTime
     * @param eTime
     * @param hour
     * @return
     */
    public int getCountByHour(String sTime, String eTime, String hour, String sqfs) {
        return dao.getCountByHour(sTime,eTime,hour,sqfs);
    }

    /**
     * 
     * 描述 获取所有为补件状态的数据
     * @author Faker Li
     * @created 2016年3月23日 下午4:31:15
     * @return
     */
    public List<Map<String, Object>> findBjList() {
        return dao.findBjList();
    }
    /**
     * 
     * 描述 
     * @author Yanisin Shi
     * @return
     * @create 2021年7月10日
     */
    public List<Map<String, Object>> findBdcScdjInfoList(String exeId,String tableName) {
        return dao.findBdcScdjInfoList(exeId,tableName);
    }
    /**
     * 
     * 描述：根据parentTaskId判断该审核环节的特殊环节是否超期
     * @author Water Guo
     * @created 2017-9-29 下午2:56:52
     * @param parentTaskId
     * @return
     */
    public boolean isOverTime(String parentTaskId){
        boolean flag=false;
        StringBuffer sql=new StringBuffer();
        sql.append("select t.exe_id,h.begin_time,h.end_time,h.link_end_time ");
        sql.append(" from jbpm6_hanginfo h left join jbpm6_task t");
        sql.append(" on h.task_id=t.task_id ");
        sql.append(" where  h.task_id=? and h.link_end_time is not null");
        List<Map<String,Object>> list=dao.findBySql(sql.toString(), 
                new Object[]{parentTaskId},null);
        for(Map<String,Object> map:list){
            //获取当前日期
            String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            String endTime=map.get("END_TIME")==null?currentDate:map.get("END_TIME").toString();
            String linkEndTime=String.valueOf(map.get("LINK_END_TIME"));
            int workDay=flowTaskService.getBewWorkDay(endTime, linkEndTime);
            if(workDay<0){
                flag=true;
            }
        }
        return flag;
    }
    /**
     * 
     * 描述 根据流程实例ID获取任务的截止的时间
     * @author Flex Hu
     * @created 2016年3月30日 上午11:08:38
     * @param exeId
     * @return
     */
    public String getDeadLineTimeForItem(String exeId,String nodeType){
        //获取事项的承诺工作日
        int promiseWorkDay = dao.getPromiseWorkDayForItem(exeId);
        //获取流程实例信息
        Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        if(promiseWorkDay!=0){
            String sskey = "";
            List<Map<String,Object>> keylist = dictionaryService.findByTypeCode("ssdjlc");
            for(Map<String,Object> key : keylist){
                sskey = sskey.concat((String) key.get("DIC_CODE"));
            }
            String defKey = dao.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                            new Object[] { flowExe.get("DEF_ID") }).get("DEF_KEY").toString();
            String createTime = (String) flowExe.get("CREATE_TIME");
            String createDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                    .getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
            String today = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            if(createDay.equals(today)||(nodeType.equals("start")&&sskey.indexOf(defKey)>=0)){
                String deadLineDate = workdayService.getDeadLineDate(today, promiseWorkDay);
                if(StringUtils.isNotEmpty(deadLineDate)){
                    return deadLineDate+" 23:59";
                }else{
                    return null;
                }
            }else{
                int count = workdayService.getWorkDayCount(createDay, today);
                int hangCount = this.getHangDayCount(exeId);
                int leftCount = promiseWorkDay+hangCount-count;
                String deadLineDate = null;
                if(leftCount>0){
                    deadLineDate= workdayService.getDeadLineDate(today, leftCount);
                    if(StringUtils.isNotEmpty(deadLineDate)){
                        return deadLineDate+" 23:59";
                    }else{
                        return null;
                    }
                }else if(leftCount==0){
                    return today+" 23:59";
                } else{
                    deadLineDate = workdayService.getDeadLineDate(createDay, promiseWorkDay);
                    if(StringUtils.isNotEmpty(deadLineDate)){
                        return deadLineDate+" 23:59";
                    }else{
                        return null;
                    }
                }
            }
            
        }else{
            String createTime = (String) flowExe.get("CREATE_TIME");
            String goOffTime = dictionaryService.findByDicCodeAndTypeCode("goOffTime", "commutingtime");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            long result = 0L;
            try {
                result = sdf.parse(createTime.substring(11)).getTime() - sdf.parse(goOffTime).getTime();
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
            //即办件晚于下班时间收件或在周末收件截至时间为最近一个工作日
            if(result > 0L || workdayService.isHoliDay(createTime)){
                String deadDay = workdayService.getDeadLineDate(createTime, 1);
                return deadDay+" 23:59";
            }else{
                String createDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                        .getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                return createDay+" 23:59";
            }
        }
    }    

    /**
     * 
     * 描述   根据流程实例ID获取挂起天数
     * @author Danto Huang
     * @created 2016-8-8 下午8:10:28
     * @param exeId
     * @return
     */
    public int getHangDayCount(String exeId){
        String sql = "select t.begin_time,t.end_time from JBPM6_HANGINFO t "
                + "where t.task_id in (select task_id from jbpm6_task where exe_id=?) " ;
//                "and t.end_time is not null";
        List<Map<String,Object>> hangs = dao.findBySql(sql, new Object[]{exeId},null);
        if(hangs!=null&&hangs.size()>0){
            int count = 0;
            for(Map<String,Object> hangInfo : hangs){
                String beginDate = hangInfo.get("begin_time").toString();
                beginDate = DateTimeUtil
                        .getStrOfDate(DateTimeUtil.getDateOfStr(beginDate,
                                "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                String endDate = hangInfo.get("end_time")==null?
                        "":hangInfo.get("end_time").toString();
                if(StringUtils.isEmpty(endDate)){
                    endDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                  }
                endDate = DateTimeUtil.getStrOfDate(
                        DateTimeUtil.getDateOfStr(endDate, "yyyy-MM-dd HH:mm:ss"),
                        "yyyy-MM-dd");
                count += workdayService.getWorkDayCount(beginDate, endDate);
            }
            
            return count;
        }else{
            return 0;
        }
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-18 下午4:31:35
     */
    public void tzxmBjxl(String beginDate,String endDate){
        beginDate += " 00:00:00";
        endDate += " 23:59:59";
        StringBuffer sql = new StringBuffer("select e.* from jbpm6_execution e ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append("where e.run_status in(2,3,4,5,6) and e.project_code is not null and e.end_time>=? ");
        sql.append(" and e.end_time<=? ");
        //限制只统计字典表里的项目工程事项
        sql.append(" and  EXISTS ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='projectItemCode' ");
        sql.append("AND DIC.DIC_CODE=E.ITEM_CODE )");

        StringBuffer taskSql = new StringBuffer();
        taskSql.append("select t.* from jbpm6_task t where t.assigner_type=1 and t.is_real_handled=1 ");
        taskSql.append("and t.exe_id=?  and t.task_nodename not in ('网站公示','集中公示','联合审查申请', ");
        taskSql.append(" '协调结果','异议协调') and t.task_nodename not like '%拟批复%' ");
        taskSql.append(" order by t.create_time ");
        
//        String insertSql = "insert into JBPM6_EFFICINFO(EFLOW_EXEID,EFFI_DESC) values(?,?)";
        
        String mxInsertSql = "insert into T_BSFW_TZXMYQMX(TASK_ID,EXE_ID,TASK_NODENAME,"
                + "ASSIGNER_NAME,CREATE_TIME,END_TIME,DEAD_TIME,SUBJECT ) values(?,?,?,?,?,?,?,?)";
        
        List<Map<String,Object>> exes = dao.findBySql(sql.toString(), new Object[]{beginDate,endDate}, null);
        for(Map<String ,Object> exe : exes){
            String exeId = exe.get("EXE_ID").toString();
            int workDays = 0;
            int hangCount = this.getHangDayCount(exeId);
            List<Map<String,Object>> tasks = dao.findBySql(taskSql.toString(), new String[]{exeId}, null);
            String preNodeName = "";
            int predays = 0;
            for(Map<String,Object> task : tasks){
                if(task.get("TASK_DEADTIME")!=null){
                    String endTime = task.get("END_TIME").toString();
                    String endDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                            .getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    String createTime = task.get("CREATE_TIME").toString();
                    String createDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                            .getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    int days = workdayService.getWorkDayCount(createDay, endDay);
                    if(!preNodeName.equals(task.get("TASK_NODENAME").toString())){
                        workDays += predays;
                        preNodeName = task.get("TASK_NODENAME").toString();
                        predays = days;
                    }else{
                        if(predays<days){
                            predays = days;
                        }
                    }
                    
                    boolean flag = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss")
                            .before(DateTimeUtil.getDateOfStr(
                                    task.get("TASK_DEADTIME").toString()+":59",
                                    "yyyy-MM-dd HH:mm:ss"));
                    if(!flag){
                       this.remove("T_BSFW_TZXMYQMX", "TASK_ID", new Object[]{task.get("TASK_ID")});
                       dao.executeSql(
                                mxInsertSql,
                                new Object[] { task.get("TASK_ID"), exeId,
                                        task.get("TASK_NODENAME"),
                                        task.get("ASSIGNER_NAME"),
                                        task.get("CREATE_TIME"),
                                        task.get("END_TIME"),
                                        task.get("TASK_DEADTIME"),
                                        exe.get("SUBJECT")});
                    }
                }
            }
            workDays = workDays - hangCount;
            int promiseWorkDay = dao.getPromiseWorkDayForItem(exeId);
            String desc = "";
            if(workDays > promiseWorkDay){
                desc = "3";
            }else if(workDays == promiseWorkDay){
                desc = "2";
            }else if(workDays < promiseWorkDay){
                desc = "1";
            }
            Map<String,Object> efficInfo = new HashMap<String, Object>();
            efficInfo.put("EFFI_DESC", desc);
//            this.remove("JBPM6_EFFICINFO", "EFLOW_EXEID", new Object[]{exeId});
//            dao.executeSql(insertSql, new Object[]{exeId,desc});
        }
    }
    @Override
//    public void ptjxl222222222222222222(String beginDate, String endDate) {
    public void ptjxl(String beginDate, String endDate) {
        // TODO Auto-generated method stub
        //beginDate += " 00:00:00";
        //endDate += " 23:59:59";
        StringBuffer sql = new StringBuffer(" select e.* from jbpm6_execution e ");
        sql.append(" left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append(" where e.run_status = '1' and s.sxlx = '2' ");
        sql.append(" and e.exe_id not in (select ec.eflow_exeid from JBPM6_EFFICINFO ec) ");
        sql.append(" and s.ITEM_CODE not IN  ");
        sql.append(" (select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC ");
        sql.append(" WHERE DIC.TYPE_CODE='zzhyCode') ");

        StringBuffer taskSql = new StringBuffer();
        taskSql.append("select t.* from jbpm6_task t where t.assigner_type=1 ");
        taskSql.append("and (t.is_real_handled=1 or t.end_time is null ) ");
//        taskSql.append("and t.task_nature <> 2 ");
        taskSql.append("and t.exe_id=? ");
        taskSql.append(" and t.task_nodename<>'开始' and t.task_nodename<>'网上预审' and t.task_nodename<>'待预审' "); 
        taskSql.append(" order by t.create_time");
        
        String insertSql = "insert into JBPM6_EFFICINFO(EFLOW_EXEID,EFFI_DESC) values(?,?)";
        
        List<Map<String,Object>> exes = dao.findBySql(sql.toString(), new Object[]{}, null);
        for(Map<String ,Object> exe : exes){
            String exeId = exe.get("EXE_ID").toString();
//            if (!exeId.equals("FJPT17071185657")) {
//                continue;
//            }
            int workDays = 0;
            List<Map<String,Object>> tasks = dao.findBySql(taskSql.toString(), new String[]{exeId}, null);
            String preNodeName = "";
            int predays = 0;
            for(Map<String,Object> task : tasks){
                if(task.get("TASK_DEADTIME")!=null){
                    String endTime = task.get("END_TIME")==null?
                            "":task.get("END_TIME").toString();
                    if (StringUtils.isEmpty(endTime)) {
                        endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                    }
                    String endDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                            .getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    String createTime = task.get("CREATE_TIME").toString();
                    String createDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                            .getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    
                    int days = workdayService.getWorkDayCount(createDay, endDay);
                    if(!preNodeName.equals(task.get("TASK_NODENAME").toString())){
                        preNodeName = task.get("TASK_NODENAME").toString();
                        predays = days;
                        workDays += predays;
                    }else{
                        if(predays<days){
                            predays = days;
                        }
                    }
                }
            }
            
            int hangCount = this.getHangDayCount(exeId);
            workDays = workDays - hangCount;
            int promiseWorkDay = dao.getPromiseWorkDayForItem(exeId);
            String desc = "";
            if(workDays > promiseWorkDay){
                desc = "3";
                Map<String,Object> efficInfo = new HashMap<String, Object>();
                efficInfo.put("EFFI_DESC", desc);
                dao.executeSql(insertSql, new Object[]{exeId,desc});
            }else{
                continue;
            }
        }
        
    }
    /**
     * 
     * 描述：特殊环节
     * @author Water Guo
     * @created 2017-9-15 上午11:34:16
     * @param beginDate
     * @see net.evecom.platform.hflow.service.ExecutionService#ptjSpe(java.lang.String, java.lang.String)
     */
    @Override
    public void ptjSpe(String beginDate) {
        String curDay = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String insersql="INSERT INTO JBPM6_EFFICINFO(EFLOW_EXEID,EFFI_DESC) VALUES(?,?)";
        Date curDate=DateTimeUtil.getDateOfStr(curDay, "yyyy-MM-dd HH:mm:ss");
        StringBuffer sql = new StringBuffer();
        sql.append(" select h.link_end_time ,e.exe_id   from");
        sql.append(" jbpm6_hanginfo h left join jbpm6_task t");
        sql.append(" on h.task_id = t.task_id ");
        sql.append(" left join jbpm6_execution e on t.exe_id=e.exe_id");
        sql.append("  left join t_wsbs_serviceitem s on s.item_code=e.item_code");
        sql.append(" where h.link_status='1' ");
        sql.append(" and e.exe_id not in (select ec.eflow_exeid from JBPM6_EFFICINFO ec) ");
        sql.append(" and s.ITEM_CODE not IN  ");
        sql.append(" (select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC ");
        sql.append(" WHERE DIC.TYPE_CODE='zzhyCode') ");
        List<Map<String,Object>> list=dao.findBySql(sql.toString()
                , new Object[]{}, null);
        for(Map<String,Object> map:list){
            String exeId=map.get("EXE_ID").toString();
            String endTime=map.get("link_end_time").toString();
            Date endDay=DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss");
            if(curDate.after(endDay)){
                dao.executeSql(insersql, new Object[]{exeId,"3"});
            }
        }
    }
    @Override
    public void ptjxl222222222222222222(String beginDate, String endDate) {
//    public void ptjxl(String beginDate, String endDate) {
        // TODO Auto-generated method stub
        //beginDate += " 00:00:00";
        //endDate += " 23:59:59";
        StringBuffer sql = new StringBuffer(" select e.* from jbpm6_execution e ");
        sql.append(" left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append(" where e.run_status = '1' and s.sxlx = '2' ");
        sql.append(" and e.exe_id not in (select ec.eflow_exeid from JBPM6_EFFICINFO ec) ");
        sql.append(" and s.ITEM_CODE not IN  ");
        sql.append(" (select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        
        String insertSql = "insert into JBPM6_EFFICINFO(EFLOW_EXEID,EFFI_DESC) values(?,?)";
        
        List<Map<String,Object>> exes = dao.findBySql(sql.toString(), new Object[]{}, null);
        for(Map<String ,Object> exe : exes){
            String exeId = exe.get("EXE_ID").toString();
//            if (!exeId.equals("FJPT17041258063")) {
//                continue;
//            }
            String runStatus = exe.get("RUN_STATUS").toString();
            String createTime = exe.get("CREATE_TIME").toString();
            String createDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                    .getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String endDay = DateTimeUtil.getStrOfDate(DateTimeUtil
                    .getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
            int days = workdayService.getWorkDayCount(createDay, endDay);
            int workDays = 0;
            workDays = days;
            int hangCount = this.getHangDayCount(exeId);
            workDays = workDays - hangCount;
            int promiseWorkDay = dao.getPromiseWorkDayForItem(exeId);
            String desc = "";
            if(workDays > promiseWorkDay){
                desc = "3";
                Map<String,Object> efficInfo = new HashMap<String, Object>();
                efficInfo.put("EFFI_DESC", desc);
                dao.executeSql(insertSql, new Object[]{exeId,desc});
            }else{
                continue;
            }
        }
        
    }

    /**
     * 
     * 描述   更新流程为收件不受理状态
     * @author Danto Huang
     * @created 2016-10-24 上午10:14:14
     * @param exeId
     * @param handlerOpinion
     * @param variables
     */
    public void updateExeToNotAccept(String exeId,String handlerOpinion,Map<String, Object> variables){
        this.endByExeId(new String[]{exeId},handlerOpinion);
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_EXECUTION J");
        sql.append(" SET J.FINAL_HANDLEOPINION=?,J.APPLY_STATUS=?,J.RUN_STATUS=? ");
        sql.append(" WHERE J.EXE_ID=? ");
        this.dao.executeSql(sql.toString(), new Object[]{handlerOpinion,"6","4",exeId});

        //退件task描述
        String taskId=(String)variables.get("EFLOW_CURRENTTASK_ID");
        Map<String,Object> task=this.dao.getByJdbc("JBPM6_TASK", new String[]{"TASK_ID"}, new Object[]{taskId});
        String parentTaskId=(String)task.get("PARENT_TASKID");
        String userName="";
        if (AppUtil.getLoginUser()!= null) {
            userName = AppUtil.getLoginUser().getFullname();
        } else {
            userName = StringUtils.isNotEmpty(StringUtil.getString(variables.get("ASSIGNER_NAME")))
                    ? StringUtil.getString(variables.get("ASSIGNER_NAME"))
                    : StringUtil.getString(variables.get("ASSIGNER_CODE"));
        }
        String desc="["+userName+"]"+task.get("TASK_NODENAME")+"环节，审核不通过";
        StringBuffer updateDesc=new StringBuffer("UPDATE JBPM6_TASK T SET");
        updateDesc.append(" T.EXE_HANDLEDESC=? WHERE T.EXE_ID=? AND T.TASK_ID=?");
        this.dao.executeSql(updateDesc.toString(), new Object[]{desc,exeId,parentTaskId});
        
        Map<String,Object> taskupd = new HashMap<String, Object>();
        taskupd.put("IS_REAL_HANDLED", 1);
        dao.saveOrUpdate(taskupd, "JBPM6_TASK", taskId);
        //获取流程实例信息
        Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
        //获取申报的项目
        String itemName = (String) flowExe.get("ITEM_NAME");
        //获取经办人的手机号
        String mobileNum = (String) flowExe.get("JBR_MOBILE");
        //获取申报时间
        String createTime = (String) flowExe.get("CREATE_TIME");
        StringBuffer mobileMsg = new StringBuffer("您好,您于");
        mobileMsg.append(createTime).append("申报的[").append(itemName);
        mobileMsg.append("]项目已退件,不通过原因是:").append(handlerOpinion);
        if(StringUtils.isNotEmpty(mobileNum)){
            log.info("接收的手机号:"+mobileNum+",内容是:"+mobileMsg.toString());
            SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobileNum);
        }
        String EFLOW_APPMATERFILEJSON = (String) variables.get("EFLOW_APPMATERFILEJSON");
        if(StringUtils.isNotEmpty(EFLOW_APPMATERFILEJSON)&&!EFLOW_APPMATERFILEJSON.equals("[]")){
            fileAttachService.saveAuditerFiles(EFLOW_APPMATERFILEJSON,variables);
        }
        
        //已办结流程，非实际办理人任务记录删除
        flowTaskService.deleteUndoTaskInfosByExeId(exeId);
    }    
    
    /**
     * 
     * 描述   更新流程为收件不受理状态(针对自动退件)
     * @author Danto Huang
     * @created 2016-10-24 上午10:14:14
     * @param exeId
     * @param handlerOpinion
     * @param variables
     */
    public void updateExeToNotAcceptForAuto(String exeId,String handlerOpinion,Map<String, Object> variables){
        this.endByExeId(new String[]{exeId},handlerOpinion);
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_EXECUTION J");
        sql.append(" SET J.FINAL_HANDLEOPINION=?,J.APPLY_STATUS=?,J.RUN_STATUS=? ");
        sql.append(" WHERE J.EXE_ID=? ");
        this.dao.executeSql(sql.toString(), new Object[]{handlerOpinion,"6","4",exeId});

        //退件task描述
        String taskId=(String)variables.get("EFLOW_CURRENTTASK_ID");
        Map<String,Object> task=this.dao.getByJdbc("JBPM6_TASK", new String[]{"TASK_ID"}, new Object[]{taskId});
        String parentTaskId=(String)task.get("PARENT_TASKID");
        String userName="";
        userName = StringUtils.isNotEmpty(StringUtil.getString(variables.get("ASSIGNER_NAME")))
                ? StringUtil.getString(variables.get("ASSIGNER_NAME"))
                : StringUtil.getString(variables.get("ASSIGNER_CODE"));
        
        String desc="["+userName+"]"+task.get("TASK_NODENAME")+"环节，审核不通过";
        StringBuffer updateDesc=new StringBuffer("UPDATE JBPM6_TASK T SET");
        updateDesc.append(" T.EXE_HANDLEDESC=? WHERE T.EXE_ID=? AND T.TASK_ID=?");
        this.dao.executeSql(updateDesc.toString(), new Object[]{desc,exeId,parentTaskId});
        
        Map<String,Object> taskupd = new HashMap<String, Object>();
        taskupd.put("IS_REAL_HANDLED", 1);
        dao.saveOrUpdate(taskupd, "JBPM6_TASK", taskId);
        //获取流程实例信息
        Map<String,Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"},new Object[]{exeId});
        //获取申报的项目
        String itemName = (String) flowExe.get("ITEM_NAME");
        //获取经办人的手机号
        String mobileNum = (String) flowExe.get("JBR_MOBILE");
        //获取申报时间
        String createTime = (String) flowExe.get("CREATE_TIME");
        StringBuffer mobileMsg = new StringBuffer("您好,您于");
        mobileMsg.append(createTime).append("申报的[").append(itemName);
        mobileMsg.append("]项目已退件,不通过原因是:").append(handlerOpinion);
        if(StringUtils.isNotEmpty(mobileNum)){
            log.info("接收的手机号:"+mobileNum+",内容是:"+mobileMsg.toString());
            SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobileNum);
        }
        String EFLOW_APPMATERFILEJSON = (String) variables.get("EFLOW_APPMATERFILEJSON");
        if(StringUtils.isNotEmpty(EFLOW_APPMATERFILEJSON)&&!EFLOW_APPMATERFILEJSON.equals("[]")){
            fileAttachService.saveAuditerFiles(EFLOW_APPMATERFILEJSON,variables);
        }
        
        //已办结流程，非实际办理人任务记录删除
        flowTaskService.deleteUndoTaskInfosByExeId(exeId);
    }

    /**
     * 
     * 描述   追回个人申请
     * @author Danto Huang
     * @created 2016-11-17 下午2:11:20
     * @param exeId
     */
    public void getBackMyApply(String exeId){
        Map<String,Object> exeMap = this.getByJdbc("JBPM6_EXECUTION", 
                new String[]{"EXE_ID"}, new Object[]{exeId});
        String defId = exeMap.get("DEF_ID").toString();
        int defVersion = Integer.parseInt(exeMap.get("DEF_VERSION").toString());
        String startNode = flowNodeService.getNodeName(defId, defVersion, "start");
        StringBuffer queryOldSql = new StringBuffer("SELECT * FROM JBPM6_TASK J");
        queryOldSql.append(" WHERE J.EXE_ID=? AND J.TASK_STATUS in(1,-1) ");
        queryOldSql.append(" AND J.STEP_SEQ!=0 ");
        //查找目标旧任务
        List<Map<String,Object>> oldTaskList = dao.findBySql(queryOldSql.toString(),new Object[]{exeId},null);
        Map<String,Object> oldTask = oldTaskList.get(0);
        //String oldNodeName = oldTask.get("TASK_NODENAME").toString();
        Map<String,Object> newTask = new HashMap<String, Object>();
        newTask.putAll(oldTask);
        newTask.put("TASK_NODENAME", startNode);
        newTask.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        newTask.put("ASSIGNER_CODE", exeMap.get("CREATOR_ACCOUNT"));
        newTask.put("ASSIGNER_NAME", exeMap.get("CREATOR_NAME"));
        newTask.put("TEAM_CODES",exeMap.get("CREATOR_ACCOUNT"));
        newTask.put("TEAM_NAMES",exeMap.get("CREATOR_NAME"));
        newTask.put("TASK_STATUS", "1");
        newTask.put("PARENT_TASKID", "");
        newTask.put("TASK_DEADTIME", "");
        newTask.put("FROMTASK_IDS", oldTask.get("TASK_ID"));
        newTask.put("FROMTASK_NODENAMES", oldTask.get("TASK_NODENAME"));
        newTask.put("FROMTASK_ASSIGNER_CODES", oldTask.get("ASSIGNER_CODE"));
        newTask.put("FROMTASK_ASSIGNERNAMES", oldTask.get("ASSIGNER_NAME"));
        newTask.put("STEP_SEQ", flowTaskService.getMaxStepSeq(exeId)+1);
        newTask.put("FROMTASK_JSON", "");
        newTask.put("TASK_NATURE", Jbpm6Constants.TASKNATURE_SIGNLER);
        newTask.put("IS_AUDITED", 1);
        newTask.put("IS_REAL_HANDLED", 1);
        newTask.put("TASK_ID", "");
        String sqfs = (String)exeMap.get("SQFS");
        if(StringUtils.isNotEmpty(sqfs)&&sqfs.equals("1")){
            newTask.put("ASSIGNER_TYPE", "2");
        }else{
            newTask.put("ASSIGNER_TYPE","1");
        }
        for(Map<String,Object> preTask : oldTaskList){
            flowTaskService.updateByExeIdAndNodeName(exeId, preTask.get("TASK_NODENAME").toString());
        }

        //指派任务
        dao.saveOrUpdate(newTask, "JBPM6_TASK", null);
        this.updateCurNodeInfo(exeId);
        exeDataService.setAllSignBack(exeId);
    }

    @Override
    public List<Map<String, Object>> findHandledByHangup(SqlFilter sqlFilter) {
        return dao.findHandledByHangup(sqlFilter);
    }

    @Override
    public List<Map<String, Object>> getDraftApply(int timeNum) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.*,to_date(trunc(sysdate)) -");
        sql.append(" to_date(trunc(to_date(t.create_time,'yyyy-mm-dd hh24:mi:ss'))) as timenum");
        sql.append(" from JBPM6_EXECUTION t where t.Run_Status = 0");
        sql.append(" and to_date(trunc(sysdate)) - to_date(trunc(to_date(t.create_time,'yyyy-mm-dd hh24:mi:ss'))) > ?");
        params.add(timeNum);
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> getDraftTask(int timeNum) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT t.* FROM JBPM6_TASK T  ");
        sql.append("  join JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID where e.run_status = 1 ");
        sql.append(" and t.task_nodename='开始' and t.task_status = -1  and t.is_audited = 1 ");
        sql.append(" and to_date(trunc(sysdate)) - to_date(trunc(to_date(t.create_time,'yyyy-mm-dd hh24:mi:ss'))) > ?");
        params.add(timeNum);
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述    食药登记用户审批列表
     * @author Danto Huang
     * @created 2019年2月25日 上午9:33:38
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findFdaHandledByUser(SqlFilter sqlFilter,String userAccount){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,D.DEF_KEY FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE TA.ASSIGNER_CODE=? AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' )");
        sql.append(" and T.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        params.add(userAccount);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = dao.setExeWorkHours(list);
        return list;
    }

    /**
     * 描述:获取环土局发来的文件
     *
     * @author Madison You
     * @created 2019/8/19 20:24:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> fetchHPFile(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String busRecordID = request.getParameter("busRecordID");
        String attachKey = request.getParameter("attachKey");
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from t_msjw_system_fileattach where 1 = 1  ");
        sql.append(" and bus_tablerecordid = ? and attach_key = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{busRecordID, attachKey}, null);
        if (list != null && list.size() > 0) {
            map.put("status", "success");
            map.put("files", list);
            return map;
        } else {
            map.put("status", "fail");
            return map;
        }
    }
    
    /**
     * 描述 获取省网数据回流附件数据
     * 
     * @author Reuben Bao
     * @created 2019年9月29日 下午3:04:23
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProvinceAttrs(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        if(StringUtils.isNotEmpty(exeId)) {
            String attrsSql = "select attrs.uuid, attrs.unid, attrs.sn, attrs.filename, 'MZT_ACCEPT_ATTRS' "
                    + " as fileTableType from mzt_accept_attrs attrs"
                    + " where attrs.sn = ? union "
                    + " select docus.uuid, docus.unid, docus.sn, docus.name as filename, 'MZT_ACCEPT_DOCUMENTS' "
                    + " as fileTableType from mzt_accept_documents docus"
                    + " where docus.sn = ? ";
            return dao.findBySql(attrsSql, new Object[] {exeId, exeId}, null);
        } else {
            return null;
        }

    }

    /**
     * 判断工程建设项目是否是撤回件
     * @param variables
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean projectRecallIsExist(Map<String, Object> variables) {
        String projectCode = "";
        String exeId = "";
        if(variables.get("PROJECT_CODE")!=null) {
            projectCode =variables.get("PROJECT_CODE").toString();
        }else {
            return false;
        }
        if(variables.get("EXE_ID")!=null) {
            exeId =variables.get("EXE_ID").toString();
        }else {
            return false;
        }
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append(" SELECT T.* FROM T_WSBS_PROJECT_RECALL T WHERE T.PROJECT_CODE = ?  ");
        sql.append(" AND T.EXE_ID = ? AND T.CHECK_STATUS=0 ");
        params.add(projectCode);
        params.add(exeId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if(list!=null && list.size()>0) {
            return true;
        }
        return false;
    }

    /**
     * 描述:根据申报号获取收费清单列表
     *
     * @author Madison You
     * @created 2020/2/26 11:12:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getPayListData(String exeId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        params.add(exeId);
        sql.append(" select rownum,c.*,a.EXE_ID from JBPM6_EXECUTION a left join T_WSBS_SERVICEITEM ");
        sql.append(" b on a.ITEM_CODE = b.ITEM_CODE left join T_WSBS_SERVICEITEM_CHARGE ");
        sql.append(" c on b.ITEM_ID = c.ITEM_ID where EXE_ID = ? ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 描述:判断在是否存在20信息，没有的话，添加一条20信息
     *
     * @author Madison You
     * @created 2020/3/5 11:23:00
     * @param
     * @return
     */
    @Override
    public void addProcessDataRes(String exeId, String eflow_currenttask_id) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        params.add(exeId);
        sql.append(" select * from t_bsfw_swbdata_res where exe_id = ? and data_type = '20' ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (list.size() == 0) {
            // 开始保存办件信息
            Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
            bjxxDataRes.put("EXE_ID", exeId);
            bjxxDataRes.put("DATA_TYPE", "20");
            bjxxDataRes.put("OPER_TYPE", "I");
            // 定义是否有附件
            int HAS_ATTR = 0;
            bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
            bjxxDataRes.put("INTER_TYPE", "10");
            bjxxDataRes.put("TASK_ID", eflow_currenttask_id);
            dao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
        }
    }

    /**
     * 
     * 描述    获取结果附件
     * @author Danto Huang
     * @created 2020年3月13日 下午2:27:18
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findResultFiles(String exeId){
        String sql = "select t.* from JBPM6_FLOW_RESULT t where t.xkfile_num=?";
        return dao.findBySql(sql, new Object[]{exeId}, null);
    }
    
    /**
     * 描述    流程退回方法
     * @author Allin Lin
     * @created 2020年12月3日 下午4:10:48
     * @param flowVars
     * @return
     */
    public Map<String, Object> goBackFlow(Map<String, Object> flowVars){
        //获取当前审核人所处的任务ID
        String currentTaskId = StringUtil.getString(flowVars.get("EFLOW_CURRENTTASK_ID"));
        //获取当前任务信息
        Map<String,Object> currentTask = this.flowTaskService.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{currentTaskId});
        currentTask = this.getFirstOverSameTask(currentTask);
        String fromTaskJson = (String) currentTask.get("FROMTASK_JSON");
        if(StringUtils.isEmpty(fromTaskJson)){
            String parentTaskId = (String) currentTask.get("PARENT_TASKID");
            Map<String,Object> parentTask = this.flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{parentTaskId});
            fromTaskJson = (String) parentTask.get("FROMTASK_JSON");
        }
        //将来源JSON转换成下一环节列表
        List<FromTask> formTaskList = JSON.parseArray(fromTaskJson, FromTask.class);
        Map<String, Object> backJson = new HashMap<String, Object>();
        for (FromTask fromTask : formTaskList) {
            List<Map<String, Object>> handlerList = new ArrayList<Map<String, Object>>();
            for (FlowNextHandler handler : fromTask.getHandlers()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nextStepAssignerCode", handler.getNextStepAssignerCode());
                map.put("nextStepAssignerName", handler.getNextStepAssignerName());
                map.put("nextStepAssignerType", handler.getNextStepAssignerType());
                handlerList.add(map);
            }
            backJson.put(fromTask.getTaskName(), handlerList);
        }
        flowVars.put("EFLOW_NEXTSTEPSJSON", JSON.toJSONString(backJson));
        //退回参数设置
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("EFLOW_ISBACK", "true");
        flowVars.remove("SIGN_MSG");
        flowVars.remove("SIGN_FLAG");
        try {
            flowVars = jbpmService.doFlowJob(flowVars);
            log.info("流程申报号:"+flowVars.get("EFLOW_EXEID")+"-执行退回流程成功！");
        } catch (Exception e) {
            log.error("流程申报号:"+flowVars.get("EFLOW_EXEID")+"-执行退回流程失败：" + e.getMessage(), e);
        }
        return flowVars;
    }

    /**
     * 描述:离婚登记申报成功后生成一条流水号
     *
     * @author Madison You
     * @created 2020/12/8 9:06:00
     * @param 
     * @return 
     */
    @Override
    public Map<String, Object> numberIncrementLhdj(Map<String, Object> flowVars) {
        String exeId = StringUtil.getValue(flowVars, "EFLOW_EXEID");
        FlowData flowData = new FlowData(exeId, FlowData.BUS_MAP);
        Map<String, Object> busMap = flowData.getBusMap();
        String lsh1 = StringUtil.getValue(busMap, "LSH");
        if (StringUtils.isEmpty(lsh1)) {
            List<Map<String,Object>> list = dao.findBySql("select count(*) SUM from t_bsfw_lhdj", null, null);
            if (list != null && !list.isEmpty()) {
                long num = 350128000000l;
                long sum = Long.parseLong(list.get(0).get("SUM").toString());
                num = num + sum;
                String lsh = String.valueOf(num);
                Map<String,Object> variables = new HashMap<>();
                variables.put("LSH", lsh);
                dao.saveOrUpdate(variables, "T_BSFW_LHDJ", StringUtil.getValue(busMap, "YW_ID"));
            }
        }
        return flowVars;
    }
    
    /**
     * 
     * 描述    办结结果快递信息
     * @author Danto Huang
     * @created 2020年3月9日 下午3:23:21
     * @param variables
     * @param msg
     * @return
     */
    public String sendEms(Map<String,Object> variables,String msg){
        String itemCode = variables.get("ITEM_CODE").toString();
        Map<String, Object> item = this.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_CODE" }, new Object[] { itemCode });
        if (variables.get("FINISH_GETTYPE") != null && "02".equals(variables.get("FINISH_GETTYPE"))
                && item.get("PAPERSUB") != null && item.get("PAPERSUB").toString().contains("2")) {
            List<Map<String,Object>> OrderNormals = new ArrayList<Map<String,Object>>();
            Map<String,Object> OrderNormal = new HashMap<String, Object>();
            OrderNormal.put("txLogisticID", variables.get("EFLOW_EXEID"));
            OrderNormal.put("serviceType", "1");
            Map<String,Object> sender = new HashMap<String, Object>();
            sender.put("name", item.get("ITEM_SEND_ADDRESSEE"));
            sender.put("postCode", item.get("ITEM_SEND_POSTCODE"));
            sender.put("prov", item.get("ITEM_SEND_PROV"));
            sender.put("city", item.get("ITEM_SEND_CITY"));
            sender.put("address", item.get("ITEM_SEND_ADDR"));
            sender.put("mobile", item.get("ITEM_SEND_MOBILE"));
            OrderNormal.put("sender", sender);
            Map<String,Object> receiver = new HashMap<String, Object>();
            receiver.put("name", variables.get("RESULT_SEND_ADDRESSEE"));
            receiver.put("mobile", variables.get("RESULT_SEND_MOBILE"));
            receiver.put("postCode", variables.get("RESULT_SEND_POSTCODE"));
            receiver.put("prov", variables.get("RESULT_SEND_PROV"));
            receiver.put("city", variables.get("RESULT_SEND_CITY"));
            receiver.put("county", variables.get("RESULT_SEND_COUNTY"));
            receiver.put("address", variables.get("RESULT_SEND_ADDR"));                            
            OrderNormal.put("receiver", receiver);
            OrderNormals.add(OrderNormal);
            Map<String,Object> OrderNormalsMap = new HashMap<String, Object>();
            OrderNormalsMap.put("orderNormals", OrderNormals);
            Map<String,String> busParams = new HashMap<String, String>();
            busParams.put("orderNormal", JSON.toJSONString(OrderNormalsMap));
            busParams.put("size", "1");
            /*String authorization = dictionaryService.getDicCode("EMSDJCS", "authorization");
            busParams.put("authorization", authorization);*/
            Map<String, Object> result = EmsSend.emsSend(busParams);
            Map<String,Object> emsInfo = new HashMap<String, Object>();
            emsInfo.put("EXE_ID", variables.get("EFLOW_EXEID"));
            emsInfo.put("ADDRESSEE_NAME", variables.get("RESULT_SEND_ADDRESSEE"));
            emsInfo.put("ADDRESSEE_PHONE", variables.get("RESULT_SEND_MOBILE"));
            emsInfo.put("ADDRESSEE_ADDR", variables.get("RESULT_SEND_ADDR"));
            emsInfo.put("ADDRESSEE_ZIPCODE", variables.get("RESULT_SEND_POSTCODE"));
            if((boolean) result.get("success")){
                emsInfo.put("MAIL_NO", result.get("mailNo"));
                emsInfo.put("LOGISTICS_ID", result.get("txLogisticID"));
                msg += ",邮件号：" + result.get("mailNo");
            }else{
                msg += ",EMS订单下单失败，错误信息：" + result.get("errorMsg");
            }
            emsService.saveOrUpdate(emsInfo, "JBPM6_FLOW_EMSINFO", null);
        }
        return msg;
    }
    
    
    /**
     * 
     * 描述    退回补件
     * @author Allin Lin
     * @created 2021年8月23日 下午2:46:59
     * @param flowInfo
     */
    public void exeThBj(Map<String, Object> flowInfo){
        String exeId = flowInfo.get("exeId") == null ? "" : flowInfo.get("exeId").toString();// 流水号
        String assignerCode = flowInfo.get("ASSIGNER_CODE") == null ? "" : 
            flowInfo.get("ASSIGNER_CODE").toString();// 任务审核人
        String BJCLLB = flowInfo.get("BJCLLB") == null ? "[]" : flowInfo.get("BJCLLB").toString();// 补件材料列表
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> exeMap = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if (null != exeMap) {
                String startNode = flowNodeService.getNodeName(exeMap.get("DEF_ID").toString(),
                        Integer.parseInt(exeMap.get("DEF_VERSION").toString()), Jbpm6Constants.START_NODE);
                List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
                Map<String, Object> nextStepAssigner = new HashMap<String, Object>();
                nextStepAssigner.put("nextStepAssignerCode", exeMap.get("CREATOR_ACCOUNT"));
                nextStepAssigner.put("nextStepAssignerName", exeMap.get("CREATOR_NAME"));
                String sqfs = (String) exeMap.get("SQFS");
                if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("1")) {
                    nextStepAssigner.put("nextStepAssignerType", "2");
                } else {
                    nextStepAssigner.put("nextStepAssignerType", "1");
                }
                nextStepAssigner.put("taskOrder", 0);
                listMap.add(nextStepAssigner);
                Map<String, Object> backJson = new HashMap<String, Object>();
                backJson.put(startNode, listMap);
                // 获取审核任务信息
                Map<String, Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                        new String[] { "EXE_ID", "IS_AUDITED", "ASSIGNER_TYPE", "TASK_STATUS", "ASSIGNER_CODE" },
                        new Object[] { exeId, "1", "1", "1", assignerCode });
                boolean isPassTime = false;
                if (flowTask != null) {
                    // 获取状态
                    String TASK_STATUS = flowTask.get("TASK_STATUS").toString();
                    if (!TASK_STATUS.equals("1")) {
                        isPassTime = true;
                    }
                    if (isPassTime) {
                        log.info("流程申报号："+exeId+"-执行退回补件失败原因：数据有误,该流程任务已经被其他经办人处理,您的流程任务被取消!");
                    } else {
                        try {
                            flowInfo.put("EFLOW_EXEID", exeId);
                            flowInfo.put("BJCLLB", BJCLLB);
                            flowInfo.put("EFLOW_CURRENTTASK_ID", flowTask.get("TASK_ID"));
                            flowInfo.put("EFLOW_NEXTSTEPSJSON", JSON.toJSONString(backJson));
                            flowInfo.put("EFLOW_DEFVERSION", exeMap.get("DEF_VERSION"));
                            flowInfo.put("EFLOW_DEFID", exeMap.get("DEF_ID"));
                            flowInfo.put("APPLY_STATUS", exeMap.get("APPLY_STATUS"));
                            Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                                    new String[] { "DEF_ID" }, new Object[] { exeMap.get("DEF_ID") });
                            flowInfo.put("EFLOW_DEFKEY", flowDef.get("DEF_KEY"));
                            flowInfo.put("EFLOW_ISTEMPSAVE", "-1");
                            flowInfo.put("ASSIGNER_NAME", flowTask.get("ASSIGNER_NAME"));
                            jbpmService.doBjFlowJob(flowInfo);
                            log.info("流程申报号："+exeId+"-执行退回补件成功!");
                        } catch (Exception e) {
                            log.info("流程申报号："+exeId+"-执行退回补件失败原因："+e.getMessage());
                        }
                    }
                } else {
                    log.info("流程申报号："+exeId+"-执行退回补件失败原因：数据有误,在审批平台未查询到对应办件任务，exeId或ASSIGNER_CODE错误！");
                }
            } else {
                log.info("流程申报号："+exeId+"-执行退回补件失败原因：数据有误,在审批平台未查询到对应办件任务，exeId或ASSIGNER_CODE错误！");
            }
        } else {
            log.info("流程申报号："+exeId+"-执行退回补件失败原因：缺失流程关键数据，流水号exeId！");
        }
    }
}
