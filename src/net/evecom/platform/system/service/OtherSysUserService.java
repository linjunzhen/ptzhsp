/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 外部其它系统用户关系服务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月23日 上午9:15:42
 */
@SuppressWarnings("rawtypes")
public interface OtherSysUserService extends BaseService {
    /**
     * 
     * 描述 根据帐号判断是否存在绑定关系记录
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param userId
     * @return true:说明已存在绑定关系 false:不存在绑定关系
     */
    public boolean isExistsOtherSysUser(String userId, String sysCode);

    /**
     * 
     * 描述 获取外部系统用户关系表信息
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param username
     * @return 1:说明已存在绑定关系 0:不存在绑定关系
     */
    public Map<String, Object> getOtherSysUser(String userId, String sysCode);

    /**
     * 
     * 描述 保存与外部系统用户关系数据
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param username
     * @return 1:说明已存在绑定关系 0:不存在绑定关系
     */
    public boolean saveOtherSysUser(Map<String, Object> othUser);

    /**
     * 
     * 描述 删除已存在的数据（与外部系统用户关系数据）
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午13:15:42
     * @param userId
     * @param sysOtherSystemCode
     * @return
     */
    public void delOtherSysUser(String userId, String sysOtherSystemCode);

    /**
     * 
     * 描述 更新最后一次跳转登录时间
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午13:15:42
     * @param userId
     * @param sysOtherSystemCode
     * @return
     */
    public void updateLastLoginTime(String userId, String sysOtherSystemCode);

}
