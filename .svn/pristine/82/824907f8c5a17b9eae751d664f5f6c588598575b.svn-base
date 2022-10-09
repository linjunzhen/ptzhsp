/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 
 * 描述 常用意见管理操作dao
 * 
 * @author Derek Zhang
 * @created 2015年10月7日 下午2:45:38
 */

@SuppressWarnings("rawtypes")
public interface CommonOpinionDao extends BaseDao {
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
    public List<Map<String, Object>> findCommonOpinionList(String sysuserid, String busName, String busType);

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
    public boolean isExist(String opinionId, String userid, String busName, String busType, String content);

}
