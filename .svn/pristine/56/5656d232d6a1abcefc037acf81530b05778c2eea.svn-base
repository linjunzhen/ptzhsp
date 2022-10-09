/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程实例操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ExecutionDao extends BaseDao {

    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    public List<Map<String, Object>> findBySqlFilterAll(SqlFilter sqlFilter);
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
     * 描述：获取所有的办理的流程
     * @author Water Guo
     * @created 2017-10-16 下午2:36:12
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledAllUser(SqlFilter sqlFilter,String userAccount);
    /**
     * 
     * 描述   获取商事被某个用户办理的流程
     * @author Flex Hu
     * @created 2015年8月22日 下午5:12:29
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findZzhyHandledByUser(SqlFilter sqlFilter,String userAccount);
    /**
     * 
     * 描述 获取被挂起的流程
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHandledByHangup(SqlFilter sqlFilter);
    /**
     * 
     * 描述 设置工时
     * @author Flex Hu
     * @created 2015年8月22日 下午5:13:49
     * @param list
     * @return
     */
    public List<Map<String,Object>> setExeWorkHours(List<Map<String, Object>> list);
    /**
     * 
     * 描述 获取申报号
     * @author Flex Hu
     * @created 2015年8月27日 上午11:07:58
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<String> findExeIds(String defId,int flowVersion);
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
     * 描述 获取记录条数面向网站用户
     * @author Flex Hu
     * @created 2015年12月3日 下午2:42:42
     * @param userAccount
     * @return
     */
    public Map<String,String> getReportCountForWebSite(String userAccount);
    /**
     * 
     * 描述 根据时间和状态获取统计数
     * @author Faker Li
     * @created 2015年12月8日 上午10:29:59
     * @param time
     * @param state
     * @return
     */
    public int getTjByStateAndTime(String time, String state);
    /**
     * 
     * 描述：根据办结时间和 状态来统计
     * @author Water Guo
     * @created 2017-6-16 下午4:50:01
     * @param time
     * @param state
     * @return
     */
    public int getTjByEndTime(String time, String state);
    /**
     * 
     * 描述 根据时间和申请状态统计数
     * @author Faker Li
     * @created 2016年3月10日 上午9:59:40
     * @param time
     * @param sqfs
     * @return
     */
    public int getSjsByTime(String time, String sqfs);
    /**
     * 
     * 描述 根据申请方式和申请时间、申请小时获取统计数
     * @author Faker Li
     * @created 2016年3月10日 下午3:54:21
     * @param sTime
     * @param eTime
     * @param hour
     * @return
     */
    public int getCountByHour(String sTime, String eTime, String hour,String sqfs);
    /**
     * 
     * 描述 获取所有为补件状态的数据
     * @author Faker Li
     * @created 2016年3月23日 下午4:31:50
     * @return
     */
    public List<Map<String, Object>> findBjList();
    /**
     * 
     * 描述 根据流程实例申报号获取承诺的工作日
     * @author Flex Hu
     * @created 2016年3月30日 上午10:56:48
     * @param exeId
     * @return
     */
    public int getPromiseWorkDayForItem(String exeId);
    
     /**
     * 描述 根据申报号获取不动产首次登记办件list
     * @author Yanisin Shi
     * @return
     * @create 2021年7月10日
     */
     
    public List<Map<String, Object>> findBdcScdjInfoList(String exeId,String tableName);
}
