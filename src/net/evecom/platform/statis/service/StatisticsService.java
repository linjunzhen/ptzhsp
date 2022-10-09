/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2016年3月8日 上午11:17:58
 */
public interface StatisticsService extends BaseService {
    /**
     * 
     * 描述 根据日期保存统计数据
     * 
     * @author Flex Hu
     * @created 2016年3月8日 下午1:46:44
     * @param beginDate
     * @param endDate
     */
    public void saveOrUpdateStatis(String date);

    /**
     * 
     * 描述 更加时间段保存数据
     * 
     * @author Flex Hu
     * @created 2016年3月8日 下午4:13:07
     * @param beginDate
     * @param endDate
     */
    public void saveOrUpdateStatis(String beginDate, String endDate);

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：乡镇统计service
     * @author Water Guo
     * @created 2017-10-19 上午11:55:10
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findXZBySqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 获取
     * 
     * @author Flex Hu
     * @created 2016年3月9日 上午11:23:57
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findJbjlList(SqlFilter sqlFilter);

    /**
     * 根据sqlfilter获取到数据列表 (行政服务中心人员办件评议)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByUserItemSqlFilter(SqlFilter sqlFilter);

    /**
     * 根据sqlfilter获取到数据列表 (审批事项统计表)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByEffiItemSqlFilter(SqlFilter sqlFilter);

    /**
     * 根据sqlfilter获取到数据列表 (时间段总效率报表)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByCountEffiItemSqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 获取时限类型统计数据
     * 
     * @author Flex Hu
     * @created 2016年3月10日 上午11:12:30
     * @param filter
     * @return
     */
    public Map<String, Object> getSxlxStatist(SqlFilter filter);

    /**
     * 
     * 描述 获取办件统计总数数据
     * 
     * @author Rider Chen
     * @created 2016-3-10 下午03:43:12
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getBjzstjStatist(SqlFilter filter);

    /**
     * 
     * 描述 获取评议结果报表数据
     * 
     * @author Rider Chen
     * @created 2016-3-10 下午03:43:12
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getPyjgtjStatist(SqlFilter filter);

    /**
     * 
     * 描述 获取收件类型统计数据
     * 
     * @author Rider Chen
     * @created 2016-3-10 下午03:43:12
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSjlxtjStatist(SqlFilter filter);

    /**
     * 
     * 描述 根据日期获取办结的数量列表
     * 
     * @author Flex Hu
     * @created 2016年3月10日 下午5:28:03
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> findOver(String beginDate, String endDate);

    /**
     * 
     * 描述 根据时间获取办结效率信息
     * 
     * @author Flex Hu
     * @created 2016年3月10日 下午7:57:59
     * @param beginDate
     * @param endDate
     * @return
     */
    public Map<String, Object> getBjxlStatist(String beginDate, String endDate);

    /**
     * 
     * 描述 获取窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-15 上午09:54:17
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getCkbjltjStatist(SqlFilter filter);
    /**
     * 当即办件在下班后创建，往后顺延一天
     * @param createTimeDate  yyyy-MM-dd HH:mm
     * @param deadLineDate   yyyy-MM-dd
     * @param sxlx  即办件
     * @return
     */
    public String getJbjDeadLineDate(Date createTimeDate, String deadLineDate, String sxlx) ;

    /**
     * 根据sqlfilter获取到部门效率数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findDeptEffByFilter(SqlFilter sqlFilter, String begin, String end, String code);

    /**
     * 根据sqlfilter获取到横向效率数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findHorizonEffByFilter(SqlFilter sqlFilter, String begin, String end);

    /**
     * 根据sqlfilter获取到阶段总效率数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findStageTotalByFilter(SqlFilter sqlFilter, String begin, String end);

    /**
     * 
     * 描述 办件状态统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-15 上午09:54:17
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getBjzttjStatist(SqlFilter filter);

    /**
     * 
     * 描述 审批事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSpsxmxStatist(SqlFilter filter);

    /**
     * 
     * 描述 审批事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSpsxmxStatistForExp(SqlFilter filter);

    /**
     * 
     * 描述 处理事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getClsxmxStatist(SqlFilter filter);

    /**
     * 
     * 描述 逾期办结事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getYqbjmxStatist(SqlFilter filter);

    /**
     * 根据逾期办结事项明细记录获取办件筛选为即办件的记录
     * @param list
     * @return
     */
    List<Map<String,Object>> getInstantWork(List<Map<String,Object>> list);

    /**
     * 获取即办件超期时间的记录
     * @param allList
     * @param instantWorkList
     * @return
     */
    List<String> calcOverDueDayWorkDay(List<Map<String,Object>> allList,List<Map<String,Object>> instantWorkList);

    /**
     * 删除未超期的即办件记录
     * @param list
     */
    void deleteNoOverDue(List<String> list);

    /**
     * 获取即办件的任务开始时间
     * @param map
     * @param instantWorkList
     * @return
     */
    String getCreateTime(Map<String,Object> map,List<Map<String,Object>> instantWorkList);

    /**
     * 
     * 描述 逾期办结事项明细（导出）
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getYqbjmxStatistForExp(SqlFilter filter);

    /**
     * 
     * 描述 投资事项逾期办结事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getTzxmyqbjmxStatist(SqlFilter filter);
    
    /**
     * 
     * 描述 获取累计办件数据
     * @author Rider Chen
     * @created 2016年9月9日 下午10:22:18
     * @return
     */
    public Map<String, Object> getLjbjsj();
    /**
     * 
     * 描述 获取累计办件数据(按年)
     * @author Rider Chen
     * @created 2016年9月9日 下午10:22:18
     * @return
     */
    public Map<String, Object> getYearLjbjsj();
    /**
     * 
     * 描述 获取累计办件数据(按月)
     * @author Rider Chen
     * @created 2016年9月9日 下午10:22:18
     * @return
     */
    public Map<String, Object> getMonthLjbjsj();
    /**
     * 
     * 描述 获取累计办件数据(按日)
     * @author Rider Chen
     * @created 2016年9月9日 下午10:22:18
     * @return
     */
    public Map<String, Object> getDayLjbjsj();
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-19 下午4:21:10
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getTzxmyqbjmxStatistForExp(SqlFilter filter);

    
    /**
     * 根据sqlfilter获取到数据列表 (产业奖补列表)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByCyjbItemSqlFilter(SqlFilter sqlFilter);
    /**
     * 根据sqlfilter获取到数据列表 (产业奖补Excel)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByCyjbExcelSqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-31 上午11:08:37
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getSljPjStatist(SqlFilter filter);
    
   /**
    * 
    * 描述
    * @author Rider Chen
    * @created 2016年11月10日 上午9:56:57
    * @param filter
    * @return
    */
    public List<Map<String,Object>> findBySwbItemSqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述
     * @author Rider Chen
     * @created 2016年11月10日 上午9:56:57
     * @param filter
     * @return
     */
     public List<Map<String,Object>> findBySwbMaterSqlFilter(SqlFilter sqlFilter);
     /**
      * 
      * 描述
      * @author Rider Chen
      * @created 2016年11月10日 上午9:56:57
      * @param filter
      * @return
      */
      public List<Map<String,Object>> findBySwbLogSqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：事项汇总
     * @author Water Guo
     * @created 2017-3-10 下午3:34:01
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getAllItemForExp(SqlFilter filter);

    /**
     * 
     * 描述：事项数量一览
     * @author Water Guo
     * @created 2017-3-23 下午3:34:01
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findsxslylDataBySqlFilter(SqlFilter filter);

    /**
     * 描述：一窗通用办办件明细表获取数据
     *
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
//    public List<Map<String, Object>> findbjmxbDataBySqlFilter(SqlFilter filter);


//    /**
//     * 描述：一窗通办办件等待时长统计表
//     *
//     * @author Madison You
//     * @created 2019-07-03 上午11:28:01
//     */
//    List<Map<String, Object>> findddsctjDataBySqlFilter(SqlFilter filter);


    /**
     * 描述：一窗通办办件统计表数据
     *
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
//    List<Map<String, Object>> findycbjtjDataBySqlFilter(SqlFilter filter);
//
//
//    /**
//     * 描述：一窗通办办件统计表数据
//     *
//     * @author Madison You
//     * @created 2019-07-05 上午11:28:01
//     */
//    List<Map<String, Object>> findsxxsszDataBySqlFilter(SqlFilter filter);
//
//    /**
//     * 描述：修改事项系数表的修改数据
//     *
//     * @author Madison You
//     * @created 2019-07-12 上午11:28:01
//     */
//    List<Map<String, Object>> findfzxsszDataBySqlFilter(SqlFilter filter);
    
    /**
     * 
     * 描述 根据配置的SQL导出Excel文件
     * @author Rider Chen
     * @created 2021年3月26日 下午2:17:53
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getSqlStatist(SqlFilter filter);
}
