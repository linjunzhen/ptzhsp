/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.FlowTaskDao;
import net.evecom.platform.hflow.util.Jbpm6Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * 描述  流程任务操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("flowTaskDao")
public class FlowTaskDaoImpl extends BaseDaoImpl implements FlowTaskDao {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowTaskDaoImpl.class);
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_TASK R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
    }
    /**
     * 
     * 描述：查看当前剩余可挂起环节
     * @author Water Guo
     * @created 2017-9-11 下午2:34:16
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findRestSpecialLink(String exeId){
        StringBuffer sql=new StringBuffer("SELECT L.LINK_NAME AS TEXT,L.RECORD_ID AS VALUE");
        sql.append(" FROM T_WSBS_SERVICEITEM S LEFT JOIN"); 
        sql.append(" T_WSBS_SERVICEITEM_LINK L ON S.ITEM_ID=L.ITEM_ID");
        sql.append(" LEFT JOIN  JBPM6_EXECUTION E ON S.ITEM_CODE=E.ITEM_CODE");
        sql.append(" WHERE E.EXE_ID=? ");
        return this.findBySql(sql.toString(), new Object[] { exeId }, null);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月27日 上午11:10:33
     * @param exeId
     */
    public void deleteByExeId(String exeId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_TASK R");
        sql.append(" WHERE R.EXE_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{exeId});
    }
    
    /**
     * 
     * 描述 判断当前是否是发起流程
     * @author Flex Hu
     * @created 2015年8月20日 下午3:13:39
     * @param exeId
     * @param defId
     * @return
     */
    public boolean isStartFlow(String exeId,String defId){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_TASK J ");
        sql.append(" WHERE J.EXE_ID=? AND J.DEF_ID=?　");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId,defId});
        if(count!=0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 
     * 描述 根据申报号获取当前最大任务步骤号
     * @author Flex Hu
     * @created 2015年8月20日 下午5:26:30
     * @param exeId
     * @return
     */
    public int getMaxStepSeq(String exeId){
        StringBuffer sql = new StringBuffer("SELECT max(T.STEP_SEQ) ");
        sql.append("FROM JBPM6_TASK T WHERE T.EXE_ID=? ");
        int maxStep = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{exeId});
        return maxStep;
    }
    
    /**
     * 
     * 描述 更新流程团队性质的任务
     * @author Flex Hu
     * @created 2015年8月21日 下午9:38:05
     * @param parentTaskId
     * @param endTime
     * @param workHours
     * @param handleOpinion
     * @param taskStatus
     * @param handleExeDesc
     */
    public void updateParentTaskInfo(String parentTaskId,String endTime,String workHours,
            String handleOpinion,String taskStatus,String handleExeDesc,String currentTaskId,String isPass){
        this.updateTaskInfo(parentTaskId, endTime, workHours, handleOpinion, taskStatus, handleExeDesc,-1,isPass);
        StringBuffer updateSubSql = new StringBuffer("UPDATE JBPM6_TASK J SET J.TASK_STATUS=?");
        updateSubSql.append(",J.END_TIME=?,J.WORK_HOURS=?,J.EXE_HANDLEDESC=?,J.HANDLE_OPINION=?");
        updateSubSql.append(",J.IS_PASS=? ");
        updateSubSql.append(" WHERE J.PARENT_TASKID=? ");
        this.jdbcTemplate.update(updateSubSql.toString(), new Object[]{taskStatus,endTime,workHours,
            handleExeDesc,handleOpinion,isPass,parentTaskId});
        //更新是否真正被办理的任务
        String sql = "update JBPM6_TASK T SET T.IS_REAL_HANDLED=? WHERE T.TASK_ID=? ";
        this.jdbcTemplate.update(sql, new Object[]{1,currentTaskId});
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月21日 下午9:52:20
     * @param taskId
     * @param endTime
     * @param workHours
     * @param handleOpinion
     * @param taskStatus
     * @param handleExeDesc
     */
    public void updateTaskInfo(String taskId,String endTime,String workHours,
            String handleOpinion,String taskStatus,String handleExeDesc,int isRealHandled,String isPass){
        StringBuffer updateSql = new StringBuffer("UPDATE JBPM6_TASK J SET J.TASK_STATUS=?");
        updateSql.append(",J.END_TIME=?,J.WORK_HOURS=?,J.EXE_HANDLEDESC=?,J.HANDLE_OPINION=?");
        updateSql.append(",J.IS_REAL_HANDLED=?,J.IS_PASS=?");
        updateSql.append(" WHERE J.TASK_ID=? ");
        this.jdbcTemplate.update(updateSql.toString(), new Object[]{taskStatus,endTime,workHours,
            handleExeDesc,handleOpinion,isRealHandled,isPass,taskId});
    }
    
    /**
     * 
     * 描述 判断和同来源任务ID的同伴任务是否都已经办理完毕
     * @author Flex Hu
     * @created 2015年8月22日 上午6:29:13
     * @param taskId
     * @return
     */
    public boolean isOtherSameFromTaskHandleOver(String taskId){
        StringBuffer sql = new StringBuffer("SELECT count(*) from JBPM6_TASK T ");
        sql.append("where T.fromtask_ids=(select S.FROMTASK_IDS ");
        sql.append("from JBPM6_TASK S WHERE S.TASK_ID=? )");
        sql.append(" AND T.TASK_ID!=? AND (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{taskId,taskId});
        if(count!=0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 
     * 描述 验证该任务是否已经发给某个审核人
     * @author Flex Hu
     * @created 2015年8月22日 上午7:30:28
     * @param exeId
     * @param currentTaskName
     * @param handlerCode
     * @return
     */
    public boolean isAssignedUserTask(String exeId,String nextTaskName,String handlerCode){
        StringBuffer sql = new StringBuffer("select T.ASSIGNER_CODE from JBPM6_TASK T WHERE T.EXE_ID=?");
        sql.append(" AND T.TASK_NODENAME=? AND T.ASSIGNER_TYPE=1 AND T.TASK_STATUS='1'");
        sql.append(" AND T.ASSIGNER_CODE IS NOT NULL ");
        try{
            String userCode = jdbcTemplate.queryForObject(sql.toString(), 
                    new Object[]{exeId,nextTaskName},String.class);
            if(userCode.equals(handlerCode)){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            //log.error(e.getMessage());
            return false;
        }
        
    }
    
    /**
     * 
     * 描述 判断是否已经将任务发给某个团队
     * @author Flex Hu
     * @created 2015年8月22日 上午8:03:01
     * @param exeId
     * @param currentTaskName
     * @param teamCodes
     * @return
     */
    public boolean isAssignedTeamTask(String exeId,String nextTaskName,String teamCodes){
        try{
            Set<String> nextTeamCodes = new HashSet<String>();
            for(String teamCode:teamCodes.split(",")){
                nextTeamCodes.add(teamCode);
            }
            StringBuffer sql = new StringBuffer("select T.TEAM_CODES from JBPM6_TASK T WHERE T.EXE_ID=?");
            sql.append(" AND T.TASK_NODENAME=? AND T.TASK_STATUS='1' AND T.TEAM_CODES is not null ");
            String existTeamCodes = jdbcTemplate.queryForObject(sql.toString(), 
                    new Object[]{exeId,nextTaskName},String.class);
            Set<String> existTeamSet = new HashSet<String>();
            for(String code:existTeamCodes.split(",")){
                existTeamSet.add(code);
            }
            if(existTeamSet.containsAll(nextTeamCodes)){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            //log.error(e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * 描述 判断当前系统中是否还有运行的流程任务
     * @author Flex Hu
     * @created 2015年8月22日 上午11:35:14
     * @param exeId
     * @param excludeTaskId
     * @return
     */
    public boolean isExistRunningTask(String exeId,String excludeTaskId){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_TASK T");
        sql.append(" WHERE T.EXE_ID=? AND (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') AND T.TASK_ID!=? ");
        sql.append(" AND T.IS_AUDITED=1 ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId,excludeTaskId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
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
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_TASK T");
        sql.append(" WHERE T.EXE_ID=? AND T.TASK_STATUS='1' ");
        sql.append(" AND T.IS_AUDITED=1 ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 将除传入的excludeTaskId流程任务状态进行更新
     * @author Flex Hu
     * @created 2015年8月22日 下午2:48:46
     * @param taskStatus
     * @param exeId
     * @param excludeTaskId
     */
    public void updateTask(String taskStatus,String exeId,String excludeTaskId){
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_TASK T ");
        sql.append("SET T.TASK_STATUS=? WHERE T.EXE_ID=?");
        sql.append(" AND T.TASK_ID!=? AND T.TASK_STATUS='1' ");
        this.jdbcTemplate.update(sql.toString(),new Object[]{taskStatus,exeId,excludeTaskId});
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
        StringBuffer sql = new StringBuffer("SELECT TA.TASK_ID FROM JBPM6_TASK TA WHERE ");
        sql.append("TA.Assigner_Code=? AND TA.IS_AUDITED=1 ");
        sql.append("AND TA.TASK_STATUS='2' AND TA.EXE_ID=? ");
        sql.append("ORDER BY TA.END_TIME DESC");
        List<String> taskIds = this.jdbcTemplate.queryForList(sql.toString(), 
                new Object[]{handlerAccount,exeId}, String.class);
        return taskIds.get(0);
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
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_TASK T ");
        sql.append("WHERE T.FROMTASK_IDS=? AND T.TASK_STATUS!='1' ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{fromTaskId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 根据来源任务ID删除掉记录
     * @author Flex Hu
     * @created 2015年8月22日 下午7:49:19
     * @param fromTaskId
     */
    public void deleteByFromTaskId(String fromTaskId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_TASK J");
        sql.append(" WHERE J.FROMTASK_IDS=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{fromTaskId});
    }
    
    /**
     * 
     * 描述 将流程任务更新为办结
     * @author Flex Hu
     * @created 2015年8月23日 上午7:21:27
     * @param exeId
     */
    public void updateTaskToEnd(String exeId,String handleOpinion){
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        StringBuffer sql = new StringBuffer("update JBPM6_TASK J");
        sql.append(" SET J.TASK_STATUS=?,J.HANDLE_OPINION=? ,J.END_TIME=? ");
        sql.append(" WHERE J.EXE_ID=? AND J.TASK_STATUS='1' ");
        this.jdbcTemplate.update(sql.toString(), new Object[] {
                Jbpm6Constants.TASKSTATUS_JSLC, handleOpinion, currentDate,
                exeId });
    }
    
    /**
     * 
     * 描述 更加申报号和节点名称删除任务
     * @author Flex Hu
     * @created 2015年8月23日 上午11:18:20
     * @param exeId
     * @param nodeName
     */
    public void deleteByExeIdAndNodeName(String exeId,String nodeName,String taskStatus){
        StringBuffer sql = new StringBuffer("delete from JBPM6_TASK J");
        sql.append(" WHERE J.EXE_ID=? AND J.TASK_NODENAME=? AND J.TASK_STATUS=? ");
        this.jdbcTemplate.update(sql.toString(),new Object[]{exeId,nodeName,taskStatus});
    }
    
    /**
     * 
     * 描述 获取同组的任务IDS
     * @author Flex Hu
     * @created 2015年8月24日 上午9:45:12
     * @param taskId
     * @param parentTaskId
     * @return
     */
    public String findSameTeamTaskIds(String taskId,String parentTaskId){
        if(StringUtils.isNotEmpty(parentTaskId)){
            StringBuffer sql = new StringBuffer("select T.TASK_ID from JBPM6_TASK t WHERE T.PARENT_TASKID");
            sql.append("=? OR T.TASK_ID = ? ");
            List<String> taskIds = this.jdbcTemplate.queryForList(sql.toString(), 
                    new Object[]{parentTaskId,parentTaskId}, String.class);
            StringBuffer targetIds = new StringBuffer("");
            for(String tId:taskIds){
                targetIds.append(tId).append(",");
            }
            return targetIds.deleteCharAt(targetIds.length()-1).toString();
        }else{
            return taskId;
        }
    }
    
    /**
     * 
     * 描述 是否允许发起合并节点任务
     * @author Flex Hu
     * @created 2015年8月24日 上午10:04:51
     * @param taskId
     * @param parentTaskId
     * @param exeId
     * @param joinNodeNames
     * @return
     */
    public boolean isAllowAssignJoinNodeTask(String taskId,String parentTaskId,String exeId,String joinNodeNames){
        //获取目标任务IDS
        String notTaskIds = this.findSameTeamTaskIds(taskId, parentTaskId);
        String nodeNames = StringUtil.getValueArray(joinNodeNames);
        String taskIds = StringUtil.getValueArray(notTaskIds);
        StringBuffer sql = new StringBuffer("SELECT count(*) FROM JBPM6_TASK TA WHERE TA.TASK_STATUS='1' ");
        sql.append(" AND TA.EXE_ID=? AND TA.TASK_NODENAME IN ").append(nodeNames);
        sql.append(" AND TA.TASK_ID NOT IN ").append(taskIds);
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{exeId});
        if(count!=0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 
     * 描述 获取回退目标节点的来源JSON
     * @author Flex Hu
     * @created 2015年8月24日 下午1:16:31
     * @param fromTaskId
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getBackTaskFormTaskJson(String fromTaskId){
        Map<String,Object> backTask = this.getByJdbc("JBPM6_TASK",new String[]{"TASK_ID"},
                new Object[]{fromTaskId});
        //获取父亲任务ID
        String parentTaskId = (String) backTask.get("PARENT_TASKID");
        if(StringUtils.isNotEmpty(parentTaskId)){
            Map<String,Object> parentTask = this.getByJdbc("JBPM6_TASK",new String[]{"TASK_ID"},
                    new Object[]{parentTaskId});
            return (String) parentTask.get("FROMTASK_JSON");
        }else{
            return (String) backTask.get("FROMTASK_JSON");
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 下午5:20:08
     * @param fromTaskIds
     * @param fromTaskNames
     * @return
     */
    public Map<String,Object> getBackTaskForm(String[] fromTaskIds,String[] fromTaskNames,Map<String,Object> fromTask){
        for(int i = 0;i<fromTaskIds.length;i++){
            fromTask.put(fromTaskNames[i], this.getBackTaskFormTaskJson(fromTaskIds[i]));
        }
        return fromTask;
    }
    
    /**
     * 
     * 描述 获取同来源任务ID的任务列表
     * @author Flex Hu
     * @created 2015年8月24日 下午1:53:08
     * @param fromTaskId
     * @param excludeTaskId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findSameFromTaskId(String fromTaskId,String excludeTaskId){
        StringBuffer sql = new StringBuffer("select * from JBPM6_TASK ");
        sql.append(" J WHERE J.FROMTASK_IDS=? AND J.TASK_ID!=? ");
        return this.findBySql(sql.toString(), new Object[]{fromTaskId,excludeTaskId}, null);
    }
    
    /**
     * 
     * 描述 更新同来源节点的任务
     * @author Flex Hu
     * @created 2015年8月24日 下午1:51:21
     * @param excludeTaskId:排除外的任务ID
     * @param fromTaskId
     * @param taskStatus
     */
    public void updateSameFromTaskId(String excludeTaskId,String fromTaskId,String taskStatus){
        List<Map<String,Object>> sameFromTasks = this.findSameFromTaskId(fromTaskId, excludeTaskId);
        for(Map<String,Object> sameFromTask:sameFromTasks){
            String taskId = (String) sameFromTask.get("TASK_ID");
            String beginTime = (String) sameFromTask.get("CREATE_TIME");
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String workHours = DateTimeUtil.getInternalTime(beginTime, endTime);
            sameFromTask.put("END_TIME",endTime);
            sameFromTask.put("WORK_HOURS",workHours);
            sameFromTask.put("TASK_STATUS",taskStatus);
            this.saveOrUpdate(sameFromTask, "JBPM6_TASK", taskId);
        }
    }
    
    /**
     * 
     * 描述 获取任务办理人信息
     * @author Flex Hu
     * @created 2015年8月24日 下午4:01:49
     * @param defId
     * @param exeId
     * @param currentTaskId
     * @param taskName
     * @return
     */
    public List<Map<String,Object>> findHandlerList(String defId,String exeId,
            String currentTaskId,String taskName){
        StringBuffer sql = new StringBuffer("select J.TASK_ID,J.TASK_NODENAME,J.Assigner_Code,J.Assigner_Name");
        sql.append(",J.ASSIGNER_TYPE from JBPM6_TASK J WHERE J.TASK_NODENAME=? AND J.EXE_ID=? AND J.DEF_ID=?");
        sql.append(" AND (J.IS_REAL_HANDLED=1 OR J.TASK_ID=? )");
        sql.append(" AND J.FROMTASK_IDS=(SELECT S.FROMTASK_IDS FROM JBPM6_TASK S WHERE S.TASK_ID=?)");
        return this.findBySql(sql.toString(), new Object[]{taskName,exeId,
            defId,currentTaskId,currentTaskId}, null);
    }
    
    /**
     * 
     * 描述 更新下一串审核人的任务状态
     * @author Flex Hu
     * @created 2015年9月4日 上午11:56:14
     * @param fromTaskId
     * @param taskNodeName
     * @param preTaskSn
     */
    public void updateNextTaskOrderStatus(String fromTaskId,String taskNodeName,int preTaskSn){
        StringBuffer sql = new StringBuffer("update JBPM6_TASK J");
        sql.append(" SET J.TASK_STATUS=? WHERE J.FROMTASK_IDS=? AND ");
        sql.append("J.TASK_NODENAME=? AND J.TASK_SN=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{Jbpm6Constants.TASKSTATUS_ZZSH,
            fromTaskId,taskNodeName,preTaskSn+1});
    }
    
    /**
     * 
     * 描述 获取任务名称列表
     * @author Flex Hu
     * @created 2015年12月21日 下午3:18:27
     * @param exeId:实例ID
     * @param taskStatus:任务状态
     * @return
     */
    public List<String> findTaskNames(String exeId,String taskStatus){
        StringBuffer sql = new StringBuffer("SELECT DISTINCT(T.TASK_NODENAME)");
        sql.append(" FROM JBPM6_TASK T WHERE T.EXE_ID=? AND T.TASK_STATUS=? ");
        List<String> list = this.jdbcTemplate.queryForList(sql.toString(), 
                new Object[]{exeId,taskStatus}, String.class);
        return list;
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
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,T.END_TIME,");
        sql.append("T.ASSIGNER_NAME,T.TASK_STATUS,T.HANDLE_OPINION FROM JBPM6_TASK T");
        sql.append(" WHERE T.EXE_ID=? AND T.IS_REAL_HANDLED=1 ");
        sql.append("AND T.END_TIME IS NOT NULL AND T.TASK_STATUS IN ('2','3','6') ");
        sql.append(" ORDER BY T.END_TIME ASC");
        return this.findBySql(sql.toString(), new Object[]{exeId}, null);
    }


    @Override
    public List<Map<String, Object>> getListByJdbc(String tableName, String taskId) {
        String sql ="select t.* from "+ tableName +" t where t.task_id = '"+taskId+"'";
        return this.jdbcTemplate.queryForList(sql.toString());
    }


    @Override
    public String getFrontLatestOverTask(String exeId, String handlerAccount) {
        StringBuffer sql = new StringBuffer("SELECT TA.TASK_ID FROM JBPM6_TASK TA WHERE ");
        sql.append("TA.Assigner_Code=? ");
        sql.append("AND TA.TASK_STATUS='2' AND TA.EXE_ID=? ");
        sql.append("ORDER BY TA.END_TIME DESC");
        List<String> taskIds = this.jdbcTemplate.queryForList(sql.toString(), 
                new Object[]{handlerAccount,exeId}, String.class);
        return taskIds.get(0);
    }
}
