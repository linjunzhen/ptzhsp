/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 审批材料操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface MaterConfigDao extends BaseDao {
    /**
     * 
     * 描述 根据流程定义ID和环节名称获取最大排序值
     * @author Flex Hu
     * @created 2016年1月29日 上午9:58:32
     * @param defId
     * @param nodeName
     * @return
     */
    public int getMaxSn(String defId,String nodeName);
    
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] configIds);
    /**
     * 
     * 描述 设置是否回填
     * @author Faker Li
     * @created 2016年4月1日 下午3:39:01
     * @param isBackfill
     * @param configIds
     */
    public void updateIsBackfill(String isBackfill, String configIds);
    
}
