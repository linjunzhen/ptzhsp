/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 叫号管理
 * @author Danto Huang
 * @created 2016-1-13 上午11:27:01
 */
public interface CallService extends BaseService {
    /**
     * 
     * 描述 获取叫号窗口管理列表
     * @author Danto Huang
     * @created 2016-1-13 下午3:21:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取VIP管理列表
     * @author Danto Huang
     * @created 2016-1-13 下午3:21:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findVIPBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取窗口办件叫号列表
     * @author Danto Huang
     * @created 2016-1-13 下午3:21:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingBySqlFilter(SqlFilter sqlFilter);
    /**
     *
     * 描述排队信息展示页面
     * @created 2019年03月17日 下午4:04:15
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> callingLineInfo(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取取票分类
     * @author Danto Huang
     * @created 2016-1-16 下午2:32:00
     * @return
     */
    public List<Map<String,Object>> findDepartWait(String roomNo);
    /**
     * 
     * 描述 获取取票分类
     * @author Danto Huang
     * @created 2016-1-16 下午2:32:00
     * @return
     */
    public List<Map<String,Object>> findAppointDepartWait(String cardNo);
    /**
     * 
     * 描述 取号人是否VIP
     * @author Danto Huang
     * @created 2016-1-17 上午10:40:30
     * @param cardNo
     * @return
     */
    public boolean isVip(String cardNo);
    /**
     * 
     * 描述 获取最大取号顺序
     * @author Danto Huang
     * @created 2016-1-17 上午10:48:20
     * @param winNo
     * @return
     */
    public int getMaxTakeSn(String winNo);
    /**
     * 
     * 描述 根据部门ID获取窗口列表
     * @author Danto Huang
     * @created 2016-1-18 上午9:38:20
     * @param departId
     * @return
     */
    public List<Map<String,Object>> getWinListByDepartId(String departId);
    /**
     * 
     * 描述 根据部门ID获取所属单位下一个可生成排队号窗口列表
     * @author Danto Huang
     * @created 2016-1-18 上午10:15:56
     * @param departId
     * @return
     */
    public String getNextWinByDepartId(String departId);
    /**
     * 
     * 描述 窗口等待人数
     * @author Danto Huang
     * @created 2016-1-19 上午11:54:40
     * @param winNo
     * @return
     */
    public String getWaitCountByWinNO(String winNo);
    /**
     * 
     * 描述 获取等待广播的叫号记录
     * @author Danto Huang
     * @created 2016-1-25 下午6:24:44
     * @return
     */
    public List<Map<String,Object>> getWaitCall();
    /**
     * 
     * 描述   是否窗口办事部门
     * @author Danto Huang
     * @created 2016-5-22 下午2:05:56
     * @param winNo
     * @return
     */
    public boolean isWinDepart(String winNo);
    /**
     * 
     * 描述  是否已在等待队列中
     * @author Danto Huang
     * @created 2016-2-2 下午3:22:31
     * @param winNo
     * @param cardNo
     * @return
     */
    public boolean isWaiting(String winNo,String cardNo);
    /**
     * 
     * 描述 根据用户名获取窗口信息
     * @author Danto Huang
     * @created 2016-2-17 下午5:22:14
     * @param username
     * @return
     */
    public Map<String,Object> getWinInfoByUsername(String username);
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-2-26 下午4:13:24
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String winNo);
    /**
     * 
     * 描述 获取取号等待数据列表
     * @author Danto Huang
     * @created 2016-2-29 下午3:22:09
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findNolistBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取窗口单位
     * @author Danto Huang
     * @created 2016-3-16 下午3:02:28
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinDepartBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取窗口单位子部门
     * @author Danto Huang
     * @created 2016-3-16 下午3:02:28
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findChildDepartBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取屏编号
     * @author Danto Huang
     * @created 2016-3-24 下午4:41:20
     * @param winNo
     * @return
     */
    public String getScreenNoByWinNo(String winNo);
    /**
     * 
     * 描述 获取窗口屏绑定关系列表
     * @author Danto Huang
     * @created 2016-4-19 下午2:34:44
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinScreenBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-2-26 下午4:13:24
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findWinsForSelect(String win);
    /**
     * 
     * 描述   定时过号
     * @author Danto Huang
     * @created 2016-5-22 下午1:12:50
     * @param date
     */
    public void overDueNo(String date);
    /**
     * 
     * 描述   获取取号部门大类
     * @author Danto Huang
     * @created 2016-5-22 下午2:32:12
     * @param departId
     * @return
     */
    public String getWinDepartNo(String departId);
    /**
     * 
     * 描述   获取排队号留意窗口
     * @author Danto Huang
     * @created 2016-5-23 下午6:47:50
     * @param winDepartNo
     * @return
     */
    public String getCareWin(String winDepartNo);
    /**
     * 
     * 描述    获取叫号记录列表
     * @author Danto Huang
     * @created 2016-5-24 上午8:56:22
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getCallRecord(SqlFilter sqlFilter);
    /**
     * 
     * 描述   根据部门id获取所属大厅
     * @author Danto Huang
     * @created 2016-6-20 下午2:53:24
     * @param deptId
     * @return
     */
    public Map<String,Object> getRoomNoByDeptId(String deptId);
    /**
     * 
     * 描述   根据窗口号获取所属大厅
     * @author Danto Huang
     * @created 2016-6-20 下午4:01:03
     * @param winNo
     * @return
     */
    public String getRoomNoByWinNo(String winNo);
    /**
     * 
     * 描述   获取部门业务关系对应列表
     * @author Danto Huang
     * @created 2016-6-22 上午11:37:23
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getDepartBusList(SqlFilter filter);
    /**
     * 
     * 描述   获取取号子部门
     * @author Danto Huang
     * @created 2016-7-5 下午3:25:15
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId);
    /**
     * 
     * 描述   获取预约列表
     * @author Danto Huang
     * @created 2016-12-5 上午9:33:04
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAppointmentBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述   定时作废网上预约
     * @author Danto Huang
     * @created 2016-12-9 下午2:14:46
     * @param date
     */
    public void passAppointment(String date);
    public List<Map<String, Object>> findAppointmentDataBySqlFilter(SqlFilter filter);
    public List<Map<String, Object>> findAgainDataBySqlFilter(SqlFilter filter);
    public boolean isExist(String lineNo);
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年10月24日 下午4:42:06
     * @param lineNo
     * @param lineDate
     * @return
     */
    public Map<String,Object> getRecordByNoAndDate(String lineNo,String lineDate);
    /**
     * 
     * 描述    根据业务编码获取排队信息
     * @author Danto Huang
     * @created 2019年4月30日 上午10:11:15
     * @param sqlFilter
     * @return
     */
   public List<Map<String,Object>> callingLineInfoForMarriage(SqlFilter sqlFilter);
   /**
    * 
    * 描述    根据业务编码获取排队信息
    * @author Danto Huang
    * @created 2019年4月30日 上午10:11:15
    * @param sqlFilter
    * @return
    */
    public List<Map<String, Object>> yqyzCallingLineInfo(SqlFilter filter);
    /**
     * 
     * 描述 获取海坛叫号数据
     * @author Kester Chen
     * @created 2020年6月17日 上午10:58:36
     * @param filter
     * @return
     */
    public List<Map<String, Object>> htCallingLineInfo(SqlFilter filter);
    /**
     * 
     * 描述 获取君山叫号数据
     * @author Kester Chen
     * @created 2020年9月1日 下午5:06:52
     * @param filter
     * @param roomNo
     * @return
     */
    public List<Map<String, Object>> jsCallingLineInfo(SqlFilter filter, String roomNo);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/30 10:17:00
     * @param 
     * @return 
     */
    List<Map<String, Object>> xmtzCallingLineInfo(SqlFilter filter);
}
