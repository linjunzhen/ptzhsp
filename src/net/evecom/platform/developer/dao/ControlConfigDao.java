/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 配置控件操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ControlConfigDao extends BaseDao {
    /**
     * 
     * 描述 根据任务ID判断是否存在控件
     * @author Flex Hu
     * @created 2014年9月18日 上午11:48:36
     * @param missionId
     * @return
     */
    public boolean isExists(String missionId);
    /**
     * 
     * 描述 根据父亲ID获取到孩子记录列表
     * @author Flex Hu
     * @created 2014年9月23日 下午4:53:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId);
    
}
