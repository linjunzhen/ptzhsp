/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 前台用户管理操作dao
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface UserInfoDao extends BaseDao {
    /**
     * 
     * 描述 根据帐号密码判断是否存在记录
     * 1:说明激活状态用户存在 -2:用户名或者密码错误 -1:用户被删除 0 锁定
     * 
     * @author Rider Chen
     * @created 2015-11-20 下午03:12:01
     * @param username
     * @param password
     * @return
     */
    public int isExistsUser(String username, String password);
    /**
     * 
     * 描述 更新状态
     * @author Faker Li
     * @created 2015年11月20日 下午5:27:56
     * @param selectColNames
     * @param parseInt
     */
    public void updateYHZT(String selectColNames, int parseInt);
}
