/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 系统资源操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface SysResDao extends BaseDao {
    /**
     * 
     * 描述 根据角色ID删除掉资源和角色中间表数据
     * @author Flex Hu
     * @created 2014年10月3日 下午4:43:14
     * @param roleId
     */
    public void removeSysResRoleByRoleId(String roleId);
    /**
     * 
     * 描述 根据角色ID获取到当前被勾选的资源
     * @author Flex Hu
     * @created 2014年10月3日 下午4:52:32
     * @param roleId
     * @return
     */
    public String getGrantResIds(String roleId);
    /**
     * 
     * 描述 根据角色ID获取被授权的资源KEYS
     * @author Flex Hu
     * @created 2014年10月11日 下午4:19:34
     * @param roleId
     * @return
     */
    public String getGrantKeys(String roleId);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 上午9:34:53
     * @param roleId
     * @return
     */
    public List<Map<String,Object>> getResList(String roleId);
}
