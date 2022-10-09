/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程任务操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowTaskService extends BaseService {
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
     * 描述 指派流程任务API
     * @author Flex Hu
     * @created 2015年8月20日 下午2:29:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> assignFlowTask(Map<String,Object> flowVars);
    /**
     * 描述：获取挂起信息
     * @author Water Guo
     * @created 2017-9-21 下午5:20:32
     * @param taskId
     * @return
     */
    public Map<String,Object> getHangInfo(String taskId);
    /**
     * 
     * 描述：流程重启计时
     * @author Water Guo
     * @created 2018-1-14 下午4:38:02
     * @param flowVars
     * @return
     */
    public Map<String,Object> flowRestar(Map<String,Object> flowVars);
    /**
     * 
     * 描述：根据父环节更新环节描述
     * @author Water Guo
     * @created 2017-9-22 下午2:34:02
     * @param parentTaskId
     */
    public void updateDescInfo(String parentTaskId,String descInfo);
    /**
     * 社会保险登记表列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findSocialFormList(SqlFilter sqlFilter);

    /**
     * 描述:医疗保险登记表列表
     *
     * @author Madison You
     * @created 2020/10/19 16:39:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findMedicialFormList(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存开始任务
     * @author Flex Hu
     * @created 2015年8月20日 下午3:23:53
     * @param flowVars
     * @return
     */
    public Map<String,Object> saveFlowStartTask(Map<String,Object> flowVars);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：查询同一环节挂起两次的信息
     * @author Water Guo
     * @created 2017-10-9 下午2:50:47
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHangSpe(SqlFilter sqlFilter);
    /**
     * 
     * 描述：查看当前剩余可挂起环节
     * @author Water Guo
     * @created 2017-9-11 下午2:34:16
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findRestSpecialLink(String queryParam);
    /**
     * 获取待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp);
    /**
     * 获取投资项目待办列表
     * 
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public List<Map<String,Object>> findTzxmNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp);
    /**
     * 获取商事待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findZzhyNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp);
    /**
     * 获取标准化建设数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBzhjsMyPortal(SqlFilter sqlFilter);
    /**
     * 金融办审核办件
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> financeNeedMeHandle(SqlFilter sqlFilter);
    /**
     * 获取商事待我审批列表(叫号使用)
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findZzhyCallNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp);
    /**
     * 获取办件预警审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findProcessWarningData(SqlFilter sqlFilter,boolean isHaveHandUp);
    
    /**
     * 获取部门办件列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findItemForDeptView(SqlFilter sqlFilter,boolean isHaveHandUp,String leftDayRange);
    /**
     * 
     * 描述 根据任务ID获取和这个任务同样来源的任务列表信息
     * @author Flex Hu
     * @created 2015年8月22日 上午12:06:33
     * @param taskId
     * @return
     */
    public List<Map<String,Object>> findSameFromTasks(String taskId);
    /**
     * 
     * 描述 获取当前正在运行的流程任务列表,只包含被监控的任务
     * @author Flex Hu
     * @created 2015年8月22日 上午9:14:33
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findRunningTask(String exeId);
    /**
     * 
     * 描述 描述 获取当前所有正在运行的流程任务列表
     * @author Flex Hu
     * @created 2015年8月22日 下午8:07:48
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findAllRunningTask(String exeId);
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
     * 描述 判断下一环节任务是否被处理过
     * @author Flex Hu
     * @created 2015年8月22日 下午7:37:04
     * @param fromTaskId
     * @return
     */
    public boolean isNextTasksHaveHandled(String fromTaskId);
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
     * 描述 获取最后一条已经办理的前台流程任务ID
     * @author Flex Hu
     * @created 2015年8月22日 下午7:31:49
     * @param exeId
     * @param handlerAccount
     * @return
     */
    public String getFrontLatestOverTask(String exeId,String handlerAccount);
    /**
     * 
     * 描述 追回流程任务
     * @author Flex Hu
     * @created 2015年8月22日 下午7:45:47
     * @param taskId
     * @param exeId
     */
    public void revokeTask(String taskId,String exeId);
    
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
     * 描述 获取是可以被审批的任务列表
     * @author Flex Hu
     * @created 2015年8月23日 上午8:05:52
     * @param exeId
     * @param taskStatus
     * @return
     */
    public List<Map<String,Object>> findIsAuditTask(String exeId,String taskStatus);
    /**
     * 
     * 描述 删除掉流程任务
     * @author Flex Hu
     * @created 2015年8月23日 上午8:15:26
     * @param taskIds
     */
    public void deleteTask(String[] taskIds,String exeId);
    /**
     * 
     * 描述 将父亲任务的信息更新
     * @author Flex Hu
     * @created 2015年8月23日 上午8:23:15
     * @param parentTaskId
     */
    public void updateParentTask(String parentTaskId);
    /**
     * 
     * 描述 转发流程任务
     * @author Flex Hu
     * @created 2015年8月23日 上午9:05:13
     * @param taskId
     * @param exeId
     * @param handlerAccount
     */
    public void changeTaskHandler(String taskId,String exeId,String handlerAccount,String handlerName);
    /**
     * 
     * 描述 更改流程环节
     * @author Flex Hu
     * @created 2015年8月23日 上午10:54:27
     * @param exeId
     * @param changeList
     */
    public void changeTaskNode(String exeId,List<Map> changeList);
    /**
     * 
     * 描述：两个时间段间隔的工作天数，0当天截止，-2超期，正数剩余天数
     * @author Water Guo
     * @created 2017-12-6 上午9:24:48
     * @param date1
     * @param date2
     * @return
     */
    public int getBewWorkDay(String date1,String date2);
    /**
     * 
     * 描述 获取同步节点来
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
            String defId,int flowVersion,String exeId);
    /**
     * 获取某个用户代办业务数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findTaskBusDatas(SqlFilter sqlFilter,String busTableName
            ,Set<String> queryFields,String userAccount);
    
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
     * 描述 获取待我审批的收件列表
     * @author Faker Li
     * @created 2015年11月5日 下午3:59:45
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleAndSjgl(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取待我审批的收件补件列表
     * @author Faker Li
     * @created 2015年11月5日 下午3:59:45
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleAndSjbj(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述 获取预审管理列表
     * @author Faker Li
     * @created 2015年11月5日 下午3:59:45
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleAndysgl(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取预审补件列表
     * @author Faker Li
     * @created 2015年11月5日 下午3:59:45
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findNeedMeHandleAndysbj(SqlFilter sqlFilter);
    /**
     * 
     * 描述   获取全部预审件列表
     * @author Rider Chen
     * @created 2017年1月20日 下午1:44:25
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleAndQbysj(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取网站用户的待办任务列表
     * @author Flex Hu
     * @created 2015年12月3日 上午11:27:49
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findWebSiteUserTask(String userAccount);
    /**
     * 
     * 描述 根据流程实例ID和状态获取任务名称
     * @author Flex Hu
     * @created 2015年12月21日 下午3:05:02
     * @param exeId
     * @param taskStatus
     * @return
     */
    public Set<String> findTaskNames(String exeId,String taskStatus);
    /**
     * 
     * 描述 根据任务截止时间获取剩余工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    public int getLeftWorkDay(String taskDeadTime,String taskId);
    /**
     * 
     * 描述 根据任务截止时间获取超期工作日数量
     * @author Water Guo
     * @param endTimedDate 
     * @created 2016年11月18日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    public int getOverdueWorkDay(String deadLineDate, String taskId, Date endTimedDate);
    /**
     * 
     * 描述 挂起流程任务
     * @author Flex Hu
     * @created 2016年3月18日 下午3:33:28
     * @param exeId
     * @param currentTaskId
     */
    public void handUpFlowTask(String exeId,String currentTaskId);
    /**
     * 
     * 描述 退回补件
     * @author Water Guo
     * @created 2016年11月21日 下午3:33:28
     * @param exeId
     * @param currentTaskId
     */
    public void returnFlowTask(String exeId, String currentTaskId);
    /**
     * 
     * 描述 批量重启流程任务
     * @author Flex Hu
     * @created 2016年3月18日 下午4:28:18
     * @param taskIds
     */
    public void startUpFlowTask(String taskIds);
    /**
     * 
     * 描述 根据流程实例ID获取已经办理的流程任务列表
     * @author Flex Hu
     * @created 2016年4月14日 上午10:40:57
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findHandleOverTaskForLog(String exeId);
    /**
     * 
     * 描述 获取移动端需要被审批的任务
     * @author Flex Hu
     * @created 2016年4月17日 上午8:58:42
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public List<Map<String,Object>> findNeedHandleForMobile(SqlFilter sqlFilter,boolean isHaveHandUp);
    /**
     * 
     * 描述 根据流程实例和环节名称获取环节列表
     * @author Faker Li
     * @created 2016年4月20日 上午11:43:09
     * @param exeId
     * @param taskNodeName
     * @return
     */
    public List<Map<String, Object>> findTaskList(String exeId, String taskNodeName);
    
    /**
     * 
     * 描述 根据申报号获取当前挂起与正在运行的任务ID
     * @author Rider Chen
     * @created 2016年8月11日 下午3:15:10
     * @param exeId
     * @return
     */
    public String findTaskId(String exeId);
    /**
     * 
     * 描述   更新任务状态
     * @author Danto Huang
     * @created 2016-11-17 下午3:47:01
     * @param exeId
     * @param nodeName
     */
    public void updateByExeIdAndNodeName(String exeId,String nodeName);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-17 下午4:10:43
     * @param exeId
     * @return
     */
    public int getMaxStepSeq(String exeId);
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-14 上午9:58:39
     * @param tableName
     * @param taskId
     * @return
     */
    public List<Map<String, Object>> getListByJdbc(String tableName, String taskId);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-14 上午9:58:31
     * @param fromTaskIds
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findFromtasks(String fromTaskIds,String exeId);
    
    
    /**
     * 
     * 描述  根据流程实例和环节名称获取环节列表
     * @author Rider Chen
     * @created 2017年3月4日 下午6:26:44
     * @param exeId
     * @param taskNodeName
     * @return
     */
    public List<Map<String, Object>> findTaskToDodeList(String exeId, String taskNodeName);
    /**
     * 
     * 描述    获取食药监待我审批
     * @author Danto Huang
     * @created 2019年2月22日 下午4:01:49
     * @param sqlFilter
     * @param isHaveHandUp
     * @return
     */
    public List<Map<String,Object>> findFdaNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp);

    /**
     * 描述:公积金材料列表
     *
     * @author Madison You
     * @created 2020/11/10 11:58:00
     * @param
     * @return
     */
    List<Map<String, Object>> findFundsFormList(SqlFilter filter);

    /**
     * 
     * 描述    税务登记材料列表
     * @author Danto Huang
     * @created 2021年10月8日 上午10:41:12
     * @param filter
     * @return
     */
    List<Map<String, Object>> findTaxFormList(SqlFilter filter);
    /**
     * 
     * 描述    已办结流程，非实际办理人任务记录删除
     * @author Danto Huang
     * @created 2021年7月26日 上午9:14:19
     * @param exeId
     */
    public void deleteUndoTaskInfosByExeId(String exeId);
}
