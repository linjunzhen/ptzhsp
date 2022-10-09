/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.system.dao.SysUserDao;
import net.evecom.platform.system.service.OtherSysUserService;

import org.springframework.stereotype.Service;

/**
 * 描述 与外部系统用户关系绑定服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月23日 上午9:15:42
 */
@SuppressWarnings("rawtypes")
@Service("otherSysUserService")
public class OtherSysUserServiceImpl extends BaseServiceImpl implements
        OtherSysUserService {
    /**
     * dao
     */
    @Resource
    SysUserDao dao;

    /**
     * 
     * 描述 根据帐号判断是否存在绑定关系记录
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param userId
     * @return 1:说明已存在绑定关系 0:不存在绑定关系
     */
    @SuppressWarnings("unchecked")
    public boolean isExistsOtherSysUser(String userId, String sysCode) {
        String sql = "select count(*) as mcount from t_msjw_system_otherusers o where o.user_id= ? and sys_code= ? ";
        Map<String, Object> m = dao.getByJdbc(sql, new Object[] { userId,
            sysCode });
        if (m == null || m.get("mcount") == null
                || ("" + m.get("mcount")).equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * 描述 获取外部系统用户关系表信息
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param username
     * @return 1:说明已存在绑定关系 0:不存在绑定关系
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getOtherSysUser(String userId, String sysCode) {
        Map<String, Object> m = dao
                .getByJdbc(
                        "select * from t_msjw_system_otherusers o where o.user_id= ?  and sys_code= ? ",
                        new Object[] { userId, sysCode });
        return m;
    }

    /**
     * 
     * 描述 保存与外部系统用户关系数据
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param username
     * @return 1:说明已存在绑定关系 0:不存在绑定关系
     */
    @SuppressWarnings("unchecked")
    public boolean saveOtherSysUser(Map<String, Object> othUser) {
        try {
            dao.saveOrUpdate(othUser, "t_msjw_system_otherusers", "user_id");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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
    public void delOtherSysUser(String userId, String sysOtherSystemCode) {
        dao.remove("t_msjw_system_otherusers", new String[] { "USER_ID",
            "SYS_CODE" }, new Object[] { userId, sysOtherSystemCode });
    }

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
    public void updateLastLoginTime(String userId, String sysOtherSystemCode) {
        dao.executeSql(
                "update t_msjw_system_otherusers t set t.last_logintime = ? where t.user_id = ? and t.sys_code = ? ",
                new Object[] { DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), userId, sysOtherSystemCode });
    }

    /**
     * 
     * 描述 返回dao
     * 
     * @author Derek Zhang
     * @created 2015年11月23日 上午9:15:42
     * @param username
     * @return 1:说明已存在绑定关系 0:不存在绑定关系
     */
    @Override
    protected GenericDao getEntityDao() {
        return null;
    }

}