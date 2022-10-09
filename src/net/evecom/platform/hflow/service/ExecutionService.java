/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述 流程实例操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ExecutionService extends BaseService {
    /**
     * 
     * 描述 保存流程实例
     * @author Flex Hu
     * @created 2015年8月18日 下午2:26:14
     * @param flowVars
     * @return
     */
    public Map<String,Object> doSaveExecution(Map<String,Object> flowVars);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    public List<Map<String, Object>> findBySqlFilterAll(SqlFilter filter);
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
     * 描述：更新推送状态
     * @author Water Guo
     * @created 2017-9-28 下午12:01:16
     * @param exeId
     */
    public void updateRes(String exeId);
    /**
     * 
     * 描述：根据parentTaskId判断是否超期
     * @author Water Guo
     * @created 2017-9-29 下午2:56:52
     * @param parentTaskId
     * @return
     */
    public boolean isOverTime(String parentTaskId);
    /**
     * 
     * 描述 更新流程实例数据
     * @author Flex Hu
     * @created 2015年8月21日 上午10:04:43
     * @param flowVars
     * @return
     */
    public Map<String,Object> updateFlowExe(Map<String,Object> flowVars);
    /**
     * 
     * 描述 获取被某个用户办理的流程
     * @author Flex Hu
     * @created 2015年8月22日 下午5:12:29
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledByUser(SqlFilter sqlFilter,String userAccount);
    /**
     * 
     * 描述：获取办理流程，不限制用户
     * @author Water Guo
     * @created 2017-10-16 下午2:33:58
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledAllUser(SqlFilter sqlFilter,String userAccount);

    /**
     * 
     * 描述  获取商事被某个用户办理的流程
     * @author Rider Chen
     * @created 2017年3月4日 下午5:47:11
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findZzhyHandledByUser(SqlFilter sqlFilter,String userAccount);
    /**
     * 
     * 描述 获取挂起的流程
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHandledByHangup(SqlFilter sqlFilter);
    /**
     * 
     * 描述 更新当前最新所处环节信息
     * @author Flex Hu
     * @created 2015年8月22日 下午7:52:09
     * @param exeId
     */
    public void updateCurNodeInfo(String exeId);
    /**
     * 
     * 描述 删除掉流程实例,并且级联删除掉流程任务
     * @author Flex Hu
     * @created 2015年8月23日 上午7:07:59
     * @param exeIds
     */
    public void deleteByExeId(String[] exeIds);
    /**
     * 
     * 描述 终结流程实例
     * @author Flex Hu
     * @created 2015年8月23日 上午7:15:34
     * @param exeIds
     */
    public void endByExeId(String[] exeIds,String handleOpinion);
    /**
     * 
     * 描述 办结流程
     * @author Flex Hu
     * @created 2016年3月16日 下午3:55:54
     * @param exeId:实例ID
     * @param runStatus:流程状态
     * @param handleOverOpinion:办结意见
     */
    public void endByExeId(String exeId,int runStatus,String handleOverOpinion);
    /**
     * 
     * 描述 获取我的申请数据
     * @author Flex Hu
     * @created 2015年8月25日 上午9:03:45
     * @param sqlFilter
     * @param busTableName
     * @return
     */
    public List<Map<String,Object>> findMyApply(SqlFilter sqlFilter,String busTableName,Set<String> queryFields);
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
    public List<Map<String,Object>> findHandledByMe(SqlFilter sqlFilter,String busTableName,
            Set<String> queryFields,String useAccount);
    /**
     * 
     * 描述 删除刚版本正在跑的数据
     * @author Flex Hu
     * @created 2015年8月27日 上午11:06:33
     * @param defId
     * @param flowVersion
     */
    public void deleteExeCascadeTask(String defId,int flowVersion);
    /***
     * 
     * 描述 获取流程申报号
     * @author Flex Hu
     * @created 2015年11月12日 下午4:10:43
     * @return
     */
    public String getNextExeId(Map<String,Object> flowVars);
    /**
     * 
     * 描述 获取我的办件列表
     * @author Faker Li
     * @created 2015年12月1日 上午9:54:33
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> findfrontWdbjList(String page, String rows,String yhzh);
    /**
     * 
     * 描述 获取我的办件列表 （最新） 新增事项编码参数
     * @author Yanisin Shi
     * @param page
     * @param rows
     * @param yhzh
     * @param itemCode
     * @return
     */
    public Map<String, Object> findfrontWdbjList(String page, String rows,String yhzh,String itemCode);
    /**
     * 
     * 描述 获取微信我的办件列表
     * @return
     */
    public List<Map<String,Object>> findWXWdbjList(String yhzh);
    /**
     * 
     * 描述 获取记录条数面向网站用户
     * @author Flex Hu
     * @created 2015年12月3日 下午2:42:42
     * @param userAccount
     * @return
     */
    public Map<String,String> getReportCountForWebSite(String userAccount);
    /**
     * 
     * 描述 获取已办结流程实例（首页我要查）
     * @author Danto Huang
     * @created 2015-12-4 上午10:12:35
     * @return
     */
    public List<Map<String,Object>> getEndExecution();
    /**
     * 
     * 描述 根据办件状态和时间获取统计数量
     * @author Faker Li
     * @created 2015年12月8日 上午10:18:00
     * @param time
     * @param state
     * @return
     */
    public int getTjByStateAndTime(String time, String state);
    /**
     * 
     * 描述：根据办结时间和 状态来统计
     * @author Water Guo
     * @created 2017-6-16 下午4:40:45
     * @param time
     * @param state
     * @return
     */
    public int getTjByEndTime(String time,String state);
    /**
     * 
     * 描述 获取前台热门办件
     * @author Faker Li
     * @created 2015年12月10日 上午10:44:05
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findByRdbs(String page, String rows);
    /**
     * 
     * 描述 根据部门编码获取头15条已办结数据
     * @author Faker Li
     * @created 2016年1月28日 下午2:13:15
     * @return
     */
    public List<Map<String,Object>> getEndExecutionByDepartCode(String departCode);
    /**
     * 
     * 描述 获取退回流程信息对象
     * @author Flex Hu
     * @created 2016年1月29日 下午3:56:21
     * @param flowSubmitInfoJson
     * @return
     */
    public Map<String,Object> getBackFlowObj(String flowSubmitInfoJson);
    /**
     * 
     * 描述 更新流程为预审不通过状态
     * @author Flex Hu
     * @created 2016年2月24日 上午10:22:39
     * @param exeId
     */
    public void updateExeToNoPass(String exeId,String handlerOpinion,Map<String, Object> variables);
    /**
     * 
     * 描述 根据申请方式和申请时间获取统计数
     * @author Faker Li
     * @created 2016年3月10日 上午9:57:00
     * @param string
     * @param sqfs
     * @return
     */
    public int getSjsByTime(String time, String sqfs);
    /**
     * 
     * 描述根据申请方式和申请时间、申请小时获取统计数
     * @author Faker Li
     * @created 2016年3月10日 下午3:52:30
     * @param sTime
     * @param eTime
     * @param string
     * @return
     */
    public int getCountByHour(String sTime, String eTime, String hour, String sqfs);
    /**
     * 
     * 描述 获取所有为补件状态的数据
     * @author Faker Li
     * @created 2016年3月23日 下午4:30:11
     * @return
     */
    public List<Map<String, Object>> findBjList();
    
    /**
     * 
     * 描述 根据申报号和业务表获取不动产首次登记抵押权人list
     * @author Yanisin Shi
     * @param exeId
     * @return
     * @create 2021年7月10日
     */
    public List<Map<String, Object>> findBdcScdjInfoList(String exeId,String tableName);
    
    /**
     * 
     * 描述 根据流程实例ID获取任务的截止的时间
     * @author Flex Hu
     * @created 2016年3月30日 上午11:08:38
     * @param exeId
     * @return
     */
    public String getDeadLineTimeForItem(String exeId,String nodeType);
    /**
     * 
     * 描述   根据流程实例ID获取挂起天数
     * @author Danto Huang
     * @created 2016-8-8 下午8:10:28
     * @param exeId
     * @return
     */
    public int getHangDayCount(String exeId);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-18 下午4:31:35
     */
    public void tzxmBjxl(String beginDate,String endDate);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-18 下午4:31:35
     */
    public void ptjxl(String beginDate,String endDate);
    /**
     * 
     * 描述：ptjSpe
     * @author Water Guo
     * @created 2017-9-15 上午11:32:06
     * @param beginDate
     * @param endDate
     */
    public void ptjSpe(String beginDate);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-18 下午4:31:35
     */
    public void ptjxl222222222222222222(String beginDate,String endDate);
    /**
     * 
     * 描述   更新流程为收件不受理状态
     * @author Danto Huang
     * @created 2016-10-24 上午10:14:14
     * @param exeId
     * @param handlerOpinion
     * @param variables
     */
    public void updateExeToNotAccept(String exeId,String handlerOpinion,Map<String, Object> variables);
    
    /**
     * 
     * 描述   更新流程为收件不受理状态(针对自动退件)
     * @author Danto Huang
     * @created 2016-10-24 上午10:14:14
     * @param exeId
     * @param handlerOpinion
     * @param variables
     */
    public void updateExeToNotAcceptForAuto(String exeId,String handlerOpinion,Map<String, Object> variables);
    /**
     * 
     * 描述   追回个人申请
     * @author Danto Huang
     * @created 2016-11-17 下午2:11:20
     * @param exeId
     */
    public void getBackMyApply(String exeId);
    
    /**
     * 
     * 描述  获取流程草稿实例
     * @author Rider Chen
     * @created 2017年1月17日 下午1:37:46
     * @param timeNum
     *      时间差
     */
    public List<Map<String, Object>> getDraftApply(int timeNum);


    /**
     * 
     * 描述  获取流程任务
     * @author Rider Chen
     * @created 2017年1月17日 下午1:37:46
     * @param timeNum
     *      时间差
     */
    public List<Map<String, Object>> getDraftTask(int timeNum);
    
    /**
     * 
     * 描述    食药登记用户审批列表
     * @author Danto Huang
     * @created 2019年2月25日 上午9:33:38
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findFdaHandledByUser(SqlFilter sqlFilter,String userAccount);

    /**
     * 描述:获取环土局发来的文件
     *
     * @author Madison You
     * @created 2019/8/19 20:24:00
     * @param
     * @return
     */
    Map<String, Object> fetchHPFile(HttpServletRequest request);
    
    /**
     * 描述 获取省网数据回流附件数据
     * 
     * @author Reuben Bao
     * @created 2019年9月29日 下午3:04:23
     * @param request
     * @return
     */
    public List<Map<String, Object>> getProvinceAttrs(HttpServletRequest request);
    /**
     * 判断工程建设项目是否是撤回件
     * @param variables
     * @return
     */
    public boolean projectRecallIsExist(Map<String, Object> variables);

    /**
     * 描述:根据申报号获取收费清单列表
     *
     * @author Madison You
     * @created 2020/2/26 11:12:00
     * @param
     * @return
     */
    List<Map<String, Object>> getPayListData(String exeId);

    /**
     * 描述:判断在是否存在20信息，没有的话，添加一条20信息
     *
     * @author Madison You
     * @created 2020/3/5 11:23:00
     * @param
     * @return
     */
    void addProcessDataRes(String exeId, String eflow_currenttask_id);
    /**
     * 
     * 描述    获取结果附件
     * @author Danto Huang
     * @created 2020年3月13日 下午2:27:18
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findResultFiles(String exeId);
    
    /**
     * 描述    流程退回方法
     * @author Allin Lin
     * @created 2020年12月3日 下午4:10:48
     * @param flowVars
     * @return
     */
    public Map<String, Object> goBackFlow(Map<String, Object> flowVars);

    /**
     * 描述:离婚登记申报成功后生成一条流水号
     *
     * @author Madison You
     * @created 2020/12/8 9:05:00
     * @param
     * @return
     */
    public Map<String, Object> numberIncrementLhdj(Map<String, Object> flowVars);
    
    /**
     * 
     * 描述    办结结果快递信息
     * @author Danto Huang
     * @created 2020年3月9日 下午3:23:21
     * @param variables
     * @param msg
     * @return
     */
    public String sendEms(Map<String,Object> variables,String msg);
   
    /**
     * 
     * 描述    退回补件
     * @author Allin Lin
     * @created 2021年8月23日 下午2:46:59
     * @param flowInfo
     */
    public void exeThBj(Map<String, Object> flowInfo);
}
