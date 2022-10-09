/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao;

import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 面向社会投资项目流程业务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月10日 上午10:41:29
 */
public interface ShtzDao extends BaseDao {
    /**
     * 
     * 描述 获取项目编号的序列号
     * @author Flex Hu
     * @created 2015年12月2日 上午11:23:35
     * @param flowVars
     * @return
     */
    public String getXmbhSeq(Map<String,Object> flowVars);
}
