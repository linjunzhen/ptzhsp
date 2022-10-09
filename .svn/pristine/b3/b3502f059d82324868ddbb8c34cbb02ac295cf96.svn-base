/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 AllMaterialsDao
 * @author Water Guo
 * @created 2015-8-31 下午5:19:13
 */
public interface AllMaterialsDao extends BaseDao {

    /**
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息（从历史表中或相应操作申报号的环节信息）
     * @author Water Guo
     * @created 2015-9-1 上午8:57:08
     * @param applyId
     * @param buscode
     * @return
     */
    public List<TacheFlow> getFlowByApplyId(String applyId, String buscode);

    /**
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息（从变更表中获取相应操作流水的环节信息）
     * @author Water Guo
     * @created 2015-10-10 下午11:13:46
     * @param applyId
     * @param buscode
     * @return
     */
    public List<TacheFlow> getChangeFlowByApplyId(String applyId, String buscode);

}
