/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import net.evecom.core.dao.BaseDao;

/**
 * 描述 用户组操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface UserGroupDao extends BaseDao {
    /**
     * 
     * 描述 保存用户组和用户中间表
     * @author Flex Hu
     * @created 2014年10月11日 下午5:23:23
     * @param userIds
     * @param groupId
     */
    public void saveGroupUsers(String[] userIds,String groupId);
}
