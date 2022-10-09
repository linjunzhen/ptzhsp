/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.usercenter.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-4-28 下午4:53:39
 */
public interface TagDao extends BaseDao {
    /**
     * 
     * 根据类别ID删除掉数据
     * @author Danto Huang
     * @created 2017-5-2 下午2:22:55
     * @param typeId
     */
    public void removeByTypeId(String typeId);
    /**
     * 判断某个目录类别下是否存在相同编码的目录
     *    
     * @author Danto Huang
     * @created 2017-5-2 下午2:38:31
     * @param typeId
     * @param key
     * @return
     */
    public boolean isExist(String typeId, String key);
}
