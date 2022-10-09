/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 流程定义操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FlowDefDao extends BaseDao {
    /**
     * 
     * 描述 根据流程定义KEY判断是否存在
     * @author Flex Hu
     * @created 2015年8月7日 上午9:01:13
     * @param defKey
     * @return
     */
    public boolean isExists(String defKey);
    /**
     * 
     * 描述 将绑定表单的ID更新为空
     * @author Flex Hu
     * @created 2015年8月10日 上午10:49:42
     * @param formId
     */
    public void updateBindFormIdToNull(String formId);
    /**
     * 
     * 描述 根据流程定义获取流程版本号
     * @author Flex Hu
     * @created 2015年8月15日 下午1:12:57
     * @param defId
     * @return
     */
    public int getFlowVersion(String defId);
    /**
     * 
     * 描述 根据流程定义ID获取流程定义名称S
     * @author Flex Hu
     * @created 2015年10月26日 下午5:20:54
     * @param defIds
     * @return
     */
    public String getDefNames(String defIds);
}
