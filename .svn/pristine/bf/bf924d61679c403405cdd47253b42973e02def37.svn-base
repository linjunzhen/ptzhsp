/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 用户组操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface UserGroupService extends BaseService {
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存用户组和用户中间表
     * @author Flex Hu
     * @created 2014年10月11日 下午5:23:23
     * @param userIds
     * @param groupId
     */
    public void saveGroupUsers(String[] userIds,String groupId);
    /**
     * 
     * 描述 删除用户组信息表,级联删除掉中间表数据
     * @author Flex Hu
     * @created 2014年10月11日 下午5:34:17
     * @param groupIds
     */
    public void removeCascade(String groupIds);
}
