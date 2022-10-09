/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.wsbs.dao.CommonOpinionDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * 描述 常用意见管理
 * 
 * @author Derek Zhang
 * @created 2015年10月7日 下午2:46:30
 */
@SuppressWarnings("rawtypes")
@Repository("commonOpinionDao")
public class CommonOpinionDaoImpl extends BaseDaoImpl implements CommonOpinionDao {
    /**
     * 
     * 描述 按登录用户和业务名以及业务子类名获取常用审批意见
     * 
     * @author Derek Zhang
     * @created 2015年10月15日 下午4:26:23
     * @param sysuserid
     * @param busName
     * @param busType
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findCommonOpinionList(String sysuserid, String busName, String busType) {
        String sql = "SELECT T.OPINION_CONTENT AS text,T.OPINION_ID AS value "
                + " FROM T_WSBS_OPINION T WHERE T.BUSINESS_NAME = ? "
                + " AND T.BUSINESS_TYPE = ? AND T.USER_ID = ? ORDER BY T.CREATE_TIME DESC ";
        return this.findBySql(sql, new Object[] { busName, busType, sysuserid }, null);
    }

    /**
     * 
     * 描述 判断 常用意见是否已存在
     * 
     * @author Derek Zhang
     * @created 2015年10月16日 上午9:28:00
     * @param userid
     * @param busName
     * @param busType
     * @param content
     * @return
     */
    @Override
    public boolean isExist(String opinionId, String userid, String busName, String busType, String content) {
        StringBuffer sql = new StringBuffer("select count(*) from T_WSBS_OPINION");
        sql.append(" where ").append(" BUSINESS_NAME = ?  and BUSINESS_TYPE = ? and USER_ID=? and ");
        Object[] object = null;
        if (StringUtils.isEmpty(opinionId)) {
            if (StringUtils.isEmpty(content)) {
                return false;
            } else {
                sql.append("OPINION_CONTENT = ? ");
                object = new Object[] { busName, busType, userid, content };
            }
        } else {
            sql.append("OPINION_CONTENT = ? and OPINION_ID=? ");
            object = new Object[] { busName, busType, content, opinionId };
        }
        long count = this.jdbcTemplate.queryForLong(sql.toString(), object);
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
}
