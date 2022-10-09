/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 字段权限操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FieldRightDao extends BaseDao {
    /**
     * 
     * 描述 根据流程定义ID判断是否已经配置权限
     * @author Flex Hu
     * @created 2015年8月12日 上午10:39:15
     * @param defId
     * @return
     */
    public boolean isExists(String defId,int flowDefVersion);
    /**
     * 
     * 描述 判断是否存在记录
     * @author Flex Hu
     * @created 2015年8月12日 上午11:25:38
     * @param defId
     * @param nodeName
     * @param fieldName
     * @return
     */
    public boolean isExists(String defId,String nodeName,String fieldName,int flowDefVersion);
    /**
     * 
     * 描述 更新字段的权限值
     * @author Flex Hu
     * @created 2015年8月12日 下午3:53:51
     * @param rightId
     * @param rightFlag
     */
    public void updateRightFlag(String rightId,int rightFlag);
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion);
}
