/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年6月26日 上午11:09:05
 */
public interface CallSetService extends BaseService {

    /**
     * 
     * 描述    业务管理数据
     * @author Danto Huang
     * @created 2018年6月27日 上午9:24:32
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getBusManageData(SqlFilter filter);
    
    /**
     * 
     * 描述    业务选择数据
     * @author Danto Huang
     * @created 2018年6月27日 下午5:23:34
     * @return
     */
    public List<Map<String,Object>> findBusinessForSelect();
    
    /**
     * 
     * 描述    获取业务名称
     * @author Danto Huang
     * @created 2018年6月27日 下午6:37:08
     * @param businessCodes
     * @return
     */
    public String getBusinessNamees(String businessCodes);
    
    /**
     * 
     * 描述    根据业务编码获取列表数据
     * @author Danto Huang
     * @created 2018年7月12日 下午4:06:04
     * @param businessCodes
     * @return
     */
    public List<Map<String,Object>> findBybusinessCode(String businessCodes);
    
    /**
     * 
     * 描述    业务数据是否已被使用
     * @author Danto Huang
     * @created 2018年9月26日 下午2:39:00
     * @param businessCode
     * @return
     */
    public boolean isUsingBusData(String businessCode);
    
    /**
     * 
     * 描述    取号部门图标数据列表
     * @author Danto Huang
     * @created 2018年6月28日 下午4:02:24
     * @return
     */
    public List<Map<String,Object>> findDepartIconList(SqlFilter filter);
    
    /**
     * 
     * 描述    窗口屏数据
     * @author Danto Huang
     * @created 2018年6月27日 下午2:46:49
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinScreenBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    窗口编号选择数据
     * @author Danto Huang
     * @created 2018年6月28日 上午10:05:59
     * @return
     */
    public List<Map<String,Object>> findWinNoForSelect();
    /**
     * 
     * 描述    窗口屏编号选择数据
     * @author Danto Huang
     * @created 2018年6月28日 上午10:05:59
     * @return
     */
    public List<Map<String,Object>> findScreenNoForSelect();
    /**
     * 
     * 描述    窗口人员数据
     * @author Danto Huang
     * @created 2018年6月27日 下午5:02:15
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinUserBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    窗口分组选择列表
     * @author Danto Huang
     * @created 2019年3月13日 上午10:38:35
     * @return
     */
    public List<Map<String,Object>> findWinGroupForSelect();
    /**
     * 
     * 描述    获取正在使用窗口
     * @author Danto Huang
     * @created 2018年7月13日 下午5:21:38
     * @return
     */
    public List<Map<String,Object>> findUsingWin();
    /**
     * 
     * 描述    获取业务设置部门
     * @author Danto Huang
     * @created 2018年7月19日 上午9:40:19
     * @return
     */
    public List<Map<String, Object>> findDepart();
    /**
     * 
     * 描述    根据部门ID获取业务数据
     * @author Danto Huang
     * @created 2018年7月19日 上午11:21:00
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId);
    /**
     * 
     * 描述    获取网上预约时段设置数据列表
     * @author Danto Huang
     * @created 2018年7月20日 上午10:21:43
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findAppointSetBySqlFilter(SqlFilter filter);

    public List<Map<String, Object>> findWinUser();

    public List<Map<String, Object>> findWinUserByWinNo(String winNo);

}
