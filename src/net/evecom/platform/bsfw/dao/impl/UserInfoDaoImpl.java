/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.UserInfoDao;

/**
 * 描述  前台用户管理操作dao
 * @author  Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao {
    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(UserInfoDaoImpl.class);

    @Override
    public int isExistsUser(String username, String password) {
        StringBuffer sql = new StringBuffer("select YHZT from T_BSFW_USERINFO ");
        sql.append("WHERE YHZH=? AND DLMM=? ");
        int status = -2;
        try{
            status = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{username,password});
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return status;
    }

    /**
     * 
     * 描述 更新状态
     * @author Faker Li
     * @created 2015年11月20日 下午5:28:17
     * @param selectColNames
     * @param parseInt
     */
    public void updateYHZT(String selectColNames, int parseInt) {
        StringBuffer sql = new StringBuffer("update T_BSFW_USERINFO T SET "
                + "").append(" T.YHZT=? WHERE T.USER_ID in ");
        String newUserIds = StringUtil.getValueArray(selectColNames);
        sql.append(newUserIds);
        this.jdbcTemplate.update(sql.toString(), new Object[]{parseInt});  
    }

}
