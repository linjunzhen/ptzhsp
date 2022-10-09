/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bespeak.model.UserInfo;

/**
 * 描述
 * @author Panda Chen
 * @created 2016-12-1 下午05:14:14
 */
@SuppressWarnings("unchecked")
public interface BespeakOnlineService extends BaseService {
    /**
     * 
     * 描述 根据父亲ID获取部门数据
     * @author Panda Chen
     * @created 2016-12-1 下午05:20:16
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId);
    /**
     * 
     * 描述保存树形结构
     * @author Panda Chen
     * @created 2016-12-2 上午11:25:18
     * @param parentId
     * @param department
     * @return
     */
    public String saveOrUpdateTreeData(String parentId,Map<String,Object> department);
    
    /**
     * 
     * 描述根据sqlfilter获取到数据列表
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:24:56
     * @param sqlFilter
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String tableName);
    /**
     * 
     * 描述更新字段顺序
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:35:18
     * @param column
     * @param tablename
     * @param configIds
     * @see net.evecom.platform.business.service.CommonService#updateSn(java.lang.String,
     *      java.lang.String, java.lang.String[])
     */
    public void updateSn(String column, String tablename, String[] configIds);
    /**
     * 
     * 描述获取最大排序
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:44:04
     * @param tablename
     * @param column
     * @return
     */
    public int getMaxSn(String tablename, String column);
    /**
     * 
     * 描述根据sql获取列表
     * 
     * @author Panda Chen
     * @created 2016年3月23日 下午3:17:00
     * @param sql
     * @param queryParams
     * @return
     */
    public List<Map<String, Object>> getListBySql(String sql, Object[] queryParams);
    
    /**
     * 
     * 描述运行sql
     * 
     * @author Panda Chen
     * @created 2016年3月23日 下午3:16:53
     * @param sql
     * @param queryParams
     */
    public void executeSql(String sql, Object[] queryParams);
    /**
     * 
     * 描述根据usercode调用星榕基webservice获取登陆用户信息
     * @author Panda Chen
     * @created 2016-12-6 下午04:40:51
     * @param userCode
     * @return
     */
    public UserInfo getUser(String userCode);
    /**
     * 
     * 描述 发送短信
     * @author Panda Chen
     * @created 2016-12-7 上午09:59:58
     * @param variables
     */
    public void sendMsg(Map variables);
    
    /**
     * 
     * 描述 根据部门ID作废预约号
     * @author Rider Chen
     * @created 2017年6月23日 上午9:39:28
     * @param date
     */
    public void passAppointmentToDeptId(String deptId);
}
