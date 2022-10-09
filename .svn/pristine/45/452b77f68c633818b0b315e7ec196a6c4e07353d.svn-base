/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statistic.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述 新报表操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface StatisticsNewService extends BaseService {
    /**
     * 
     * 描述：
     * @author Rider Chen
     * @created 2018年9月25日 下午3:37:10
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getSljPjStatist(SqlFilter filter);


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
     * 描述：一窗通用办办件明细表获取数据
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    List<Map<String, Object>> findbjmxbDataBySqlFilter(SqlFilter filter);

    /**
     * 描述：一窗通办办件等待时长统计表
     *
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    List<Map<String, Object>> findddsctjDataBySqlFilter(SqlFilter filter);

    /**
     * 描述：一窗通办办件统计表数据
     *
     * @author Madison You
     * @created 2019-07-05 上午11:28:01
     */
    List<Map<String, Object>> findycbjtjDataBySqlFilter(SqlFilter filter);

    /**
     * 描述：修改事项系数表的修改数据
     *
     * @author Madison You
     * @created 2019-07-12 上午11:28:01
     */
    List<Map<String, Object>> findsxxsszDataBySqlFilter(SqlFilter filter);


    /**
     * 描述:环土局专属行业列表
     *
     * @author Madison You
     * @created 2019/8/22 10:35:00
     * @param
     * @return
     */
    List<Map<String, Object>> htIndustryList(HttpServletRequest request);

    /**
     * 描述:绩效考核表格人员分组
     *
     * @author Madison You
     * @created 2019/9/2 16:27:00
     * @param
     * @return
     */
    List<Map<String, Object>> ryfzData(SqlFilter filter);

    /**
     * 描述:工程建设项目查询数据
     *
     * @author Madison You
     * @created 2019/9/10 10:17:00
     * @param
     * @return
     */
    List<Map<String, Object>> gcjsxmcxData(SqlFilter filter);

    /**
     * 描述:工程建设项目查询获取所有已上传材料
     *
     * @author Madison You
     * @created 2019/9/16 14:26:00
     * @param
     * @return
     */
    List<Map<String, Object>> fetchGcjsxmcxMater(SqlFilter filter);

    /**
     * 描述:工程建设项目当前已办理事项和办件
     *
     * @author Madison You
     * @created 2019/9/19 8:50:00
     * @param
     * @return
     */
    List<Map<String, Object>> gcjsxmcxDealItemList(SqlFilter filter);

    /**
     * 描述:绩效考核得分人员数据(定时器)
     *
     * @author Madison You
     * @created 2019/9/26 15:23:00
     * @param
     * @return
     */
    List<Map<String, Object>> jxkhdfryData();

    /**
     * 描述:绩效考核办件分值(定时器)
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/9/26 16:33:00
     */
    List<Map<String, Object>> jxkhbjfzData(String date);

    /**
     * 描述:绩效考核得分计算
     *
     * @author Madison You
     * @created 2019/10/8 8:46:00
     * @param
     * @return
     */
    List<Map<String, Object>> jxkhdfCal(SqlFilter filter);

    /**
     * 描述:绩效考核加分扣分计算
     *
     * @author Madison You
     * @created 2019/10/15 15:10:00
     * @param
     * @return
     */
    List<Map<String, Object>> jxkhjfkfCal(SqlFilter filter , List<Map<String,Object>> ryList);

    /**
     * 描述:根据岗位类型和部门获取人数
     *
     * @author Madison You
     * @created 2019/10/14 16:27:00
     * @param 
     * @return 
     */
    int getNumPeopleByGWLXandDep(String gwlx, String department);

    /**
     * 描述:绩效考核加分减分数据
     *
     * @author Madison You
     * @created 2019/10/14 17:13:00
     * @param
     * @return
     */
    List<Map<String,Object>> jxjfkfData(SqlFilter filter);

    /**
     * 描述:统计所有还在网上预审的办件
     *
     * @author Madison You
     * @created 2019/10/16 10:13:00
     * @param
     * @return
     */
    List<Map<String, Object>> wsysData(SqlFilter filter);

    /**
     * 描述:预约取号统计数据
     *
     * @author Madison You
     * @created 2020/2/1 11:35:00
     * @param
     * @return
     */
    List<Map<String, Object>> callAppointData(SqlFilter filter);

    /**
     * 描述:不动产业务受理情况数据
     *
     * @author Madison You
     * @created 2020/4/7 9:53:00
     * @param
     * @return
     */
    List<Map<String, Object>> bdcCallData(SqlFilter filter);

    /**
     * 描述:统计不动产每日业务受理情况数据
     *
     * @author Madison You
     * @created 2020/4/7 10:22:00
     * @param
     * @return
     */
    void bdcCallDataCal(String today);

    /**
     * 描述:获取某一时间不动产某项业务的受理情况
     *
     * @author Madison You
     * @created 2020/4/7 10:53:00
     * @param
     * @return
     */
    public List<Map<String,Object>> bdcCalByBusinessCode(String today , String businessCode);

    /**
     * 描述:查询部门申请人数据
     *
     * @author Madison You
     * @created 2021/1/18 15:40:00
     * @param
     * @return
     */
    List<Map<String, Object>> getDepartSqrData(SqlFilter filter);

    /**
     * 描述:每日受理统计（线上）
     *
     * @author Madison You
     * @created 2021/3/23 9:29:00
     * @param
     * @return
     */
    List<Map<String, Object>> mrsltjXsData(SqlFilter filter);

    /**
     * 描述:每日受理统计（线下）
     *
     * @author Madison You
     * @created 2021/3/23 9:29:00
     * @param
     * @return
     */
    List<Map<String, Object>> mrsltjXxData(SqlFilter filter);
}
