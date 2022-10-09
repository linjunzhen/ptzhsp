/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 流程任务操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FlowTaskDao extends BaseDao {

    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId);
    /**
     * 
     * 描述 判断当前是否是发起流程
     * @author Flex Hu
     * @created 2015年8月20日 下午3:13:39
     * @param exeId
     * @param defId
     * @return
     */
    public boolean isStartFlow(String exeId,String defId);
    /**
     * 
     * 描述：查看当前剩余可挂起环节
     * @author Water Guo
     * @created 2017-9-11 下午2:34:16
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findRestSpecialLink(String exeId);
    /**
     * 
     * 描述 根据申报号获取当前最大任务步骤号
     * @author Flex Hu
     * @created 2015年8月20日 下午5:26:30
     * @param exeId
     * @return
     */
    
    public int getMaxStepSeq(String exeId);
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
            String handleOpinion,String taskStatus,String handleExeDesc,String currentTaskId,String isPass);
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
            String handleOpinion,String taskStatus,String handleExeDesc,int isRealHandled,String isPass);
    /**
     * 
     * 描述 判断和同来源任务ID的同伴任务是否都已经办理完毕
     * @author Flex Hu
     * @created 2015年8月22日 上午6:29:13
     * @param taskId
     * @return
     */
    public boolean isOtherSameFromTaskHandleOver(String taskId);
    /**
     * 
     * 描述 验证该任务是否已经发给某个审核人
     * @author Flex Hu
     * @created 2015年8月22日 上午7:30:28
     * @param exeId
     * @param nextTaskName
     * @param handlerCode
     * @return
     */
    public boolean isAssignedUserTask(String exeId,String nextTaskName,String handlerCode);
    /**
     * 
     * 描述 判断是否已经将任务发给某个团队
     * @author Flex Hu
     * @created 2015年8月22日 上午8:03:01
     * @param exeId
     * @param nextTaskName
     * @param teamCodes
     * @return
     */
    public boolean isAssignedTeamTask(String exeId,String nextTaskName,String teamCodes);
    /**
     * 
     * 描述 判断当前系统中是否还有运行的流程任务
     * @author Flex Hu
     * @created 2015年8月22日 上午11:35:14
     * @param exeId
     * @param excludeTaskId
     * @return
     */
    public boolean isExistRunningTask(String exeId,String excludeTaskId);
    /**
     * 
     * 描述 判断是否还存在正在运行的任务
     * @author Flex Hu
     * @created 2015年8月22日 下午12:14:53
     * @param exeId
     * @return
     */
    public boolean isExistRunningTask(String exeId);
    /**
     * 
     * 描述 将除传入的excludeTaskId流程任务状态进行更新
     * @author Flex Hu
     * @created 2015年8月22日 下午2:48:46
     * @param taskStatus
     * @param exeId
     * @param excludeTaskId
     */
    public void updateTask(String taskStatus,String exeId,String excludeTaskId);
    /**
     * 
     * 描述 获取最后一条已经办理的流程任务ID
     * @author Flex Hu
     * @created 2015年8月22日 下午7:31:49
     * @param exeId
     * @param handlerAccount
     * @return
     */
    public String getLatestHandlerOverTask(String exeId,String handlerAccount);
    /**
     * 
     * 描述 获取最后一条前台已经办理的流程任务ID
     * @author Flex Hu
     * @created 2015年8月22日 下午7:31:49
     * @param exeId
     * @param handlerAccount
     * @return
     */
    public String getFrontLatestOverTask(String exeId,String handlerAccount);
    /**
     * 
     * 描述 判断下一环节任务是否被处理过
     * @author Flex Hu
     * @created 2015年8月22日 下午7:37:04
     * @param fromTaskId
     * @return
     */
    public boolean isNextTasksHaveHandled(String fromTaskId);
    /**
     * 
     * 描述 根据来源任务ID删除掉记录
     * @author Flex Hu
     * @created 2015年8月22日 下午7:49:19
     * @param fromTaskId
     */
    public void deleteByFromTaskId(String fromTaskId);
    /**
     * 
     * 描述 将流程任务更新为办结
     * @author Flex Hu
     * @created 2015年8月23日 上午7:21:27
     * @param exeId
     */
    public void updateTaskToEnd(String exeId,String handleOpinion);
    /**
     * 
     * 描述 更加申报号和节点名称删除任务
     * @author Flex Hu
     * @created 2015年8月23日 上午11:18:20
     * @param exeId
     * @param nodeName
     */
    public void deleteByExeIdAndNodeName(String exeId,String nodeName,String taskStatus);
    /**
     * 
     * 描述 获取同组的任务IDS
     * @author Flex Hu
     * @created 2015年8月24日 上午9:45:12
     * @param taskId
     * @param parentTaskId
     * @return
     */
    public String findSameTeamTaskIds(String taskId,String parentTaskId);
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
    public boolean isAllowAssignJoinNodeTask(String taskId,String parentTaskId,String exeId,String joinNodeNames);
    /**
     * 
     * 描述 获取回退目标节点的来源JSON
     * @author Flex Hu
     * @created 2015年8月24日 下午1:16:31
     * @param fromTaskId
     * @return
     */
    public String getBackTaskFormTaskJson(String fromTaskId);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 下午5:20:08
     * @param fromTaskIds
     * @param fromTaskNames
     * @return
     */
    public Map<String,Object> getBackTaskForm(String[] fromTaskIds,String[] fromTaskNames,Map<String,Object> fromTask);
    /**
     * 
     * 描述 更新同来源节点的任务
     * @author Flex Hu
     * @created 2015年8月24日 下午1:51:21
     * @param excludeTaskId:排除外的任务ID
     * @param fromTaskId
     * @param taskStatus
     */
    public void updateSameFromTaskId(String excludeTaskId,String fromTaskId,String taskStatus);
    /**
     * 
     * 描述 获取同来源任务ID的任务列表
     * @author Flex Hu
     * @created 2015年8月24日 下午1:53:08
     * @param fromTaskId
     * @param excludeTaskId
     * @return
     */
    public List<Map<String,Object>> findSameFromTaskId(String fromTaskId,String excludeTaskId);
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
            String currentTaskId,String taskName);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月27日 上午11:10:33
     * @param exeId
     */
    public void deleteByExeId(String exeId);
    /**
     * 
     * 描述 更新下一串审核人的任务状态
     * @author Flex Hu
     * @created 2015年9月4日 上午11:56:14
     * @param fromTaskId
     * @param taskNodeName
     * @param preTaskSn
     */
    public void updateNextTaskOrderStatus(String fromTaskId,String taskNodeName,int preTaskSn);
    /**
     * 
     * 描述 获取任务名称列表
     * @author Flex Hu
     * @created 2015年12月21日 下午3:18:27
     * @param exeId:实例ID
     * @param taskStatus:任务状态
     * @return
     */
    public List<String> findTaskNames(String exeId,String taskStatus);
    /**
     * 
     * 描述 根据流程实例ID获取已经办理的流程任务列表
     * @author Flex Hu
     * @created 2016年4月14日 上午10:40:57
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findHandleOverTaskForLog(String exeId);
    
    public List<Map<String, Object>> getListByJdbc(String tableName, String taskId);
}
