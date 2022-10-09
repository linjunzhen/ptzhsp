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
 * 描述 事项目录操作dao
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface CatalogDao extends BaseDao {
    /**
     * 
     * 描述 获取表中最大的排序值
     * @author Faker Li
     * @created 2015年10月27日 下午3:05:34
     * @return
     */
    public int getMaxSn();
    /**
     * 
     * 描述 根据部门编码+目录性质字典值获取最大次序
     * @author Faker Li
     * @created 2015年10月28日 上午9:39:18
     * @param departsxxzcode
     * @return
     */
    public int getMaxNumCode(String departsxxzcode);
    /**
     * 
     * 描述 更新排序字段
     * @author Faker Li
     * @created 2015年10月28日 上午11:07:05
     * @param catalogIds
     */
    public void updateSn(String[] catalogIds);
    /**
     * 
     * 描述 根据catalogCode获取catalogname
     * @author Faker Li
     * @created 2015年10月29日 上午9:00:56
     * @param catalogCode
     * @return
     */
    public Map<String,Object> getCatalogByCatalogCode(String catalogCode);

}
