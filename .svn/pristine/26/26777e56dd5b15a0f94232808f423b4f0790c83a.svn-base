/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2015-3-23 上午11:20:07
 */
public interface FormValidateDao extends BaseDao {

    /**
     * 
     * 描述 根据验证配置ID删除数据
     * @author Danto Huang
     * @created 2015-3-23 上午11:17:20
     * @param ruleId
     */
    public void removeByFormId(String fromId);
    /**
     * 
     * 描述  获取某个表单验证规则的最大排序
     * @author Danto Huang
     * @created 2015-7-22 下午4:15:51
     * @param fromId
     * @return
     */
    public int getMaxSn(String fromId);
    /**
     * 
     * 描述  更新排序字段
     * @author Danto Huang
     * @created 2015-7-22 下午4:19:47
     * @param ruleIds
     */
    public void updateSn(String[] ruleIds);
}
