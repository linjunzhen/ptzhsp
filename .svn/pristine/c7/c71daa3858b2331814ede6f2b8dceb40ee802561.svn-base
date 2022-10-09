/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.system.dao.UserGroupDao;

/**
 * 描述  用户组操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("userGroupDao")
public class UserGroupDaoImpl extends BaseDaoImpl implements UserGroupDao {

    /**
     * 
     * 描述 保存用户组和用户中间表
     * @author Flex Hu
     * @created 2014年10月11日 下午5:23:23
     * @param userIds
     * @param groupId
     * @return
     */
    public void saveGroupUsers(String[] userIds,String groupId){
      //先清除掉数据
        StringBuffer delSql = new StringBuffer("delete ").append(" FROM T_SYSTEM_SYSUSER_GROUP WHERE GROUP_ID=? ");
        this.jdbcTemplate.update(delSql.toString(), new Object[] { groupId });
        StringBuffer sql = new StringBuffer("insert into ")
                .append("T_SYSTEM_SYSUSER_GROUP(GROUP_ID,USER_ID) VALUES(?,?) ");
        for(String userId:userIds){
            this.jdbcTemplate.update(sql.toString(), new Object[]{groupId,userId});
        }
    }
}
