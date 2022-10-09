/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.base.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月17日 下午3:44:04
 */
public interface DataSourceDao extends BaseDao {
    /**
     * 判断一个记录是否存在
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsRecord(String tableName,String fieldName,Object fieldValue,String recordId);
    /**
     * 判断一个流程记录状态在[1:正在办理中,2:已办结(正常结束),3:已办结(审核通过)。]是否存在相同的公司名称记录
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsFlowRecord(String tableName,String fieldName,Object fieldValue,String recordId);
    /**
     * 判断一个流程记录状态在[1:正在办理中,2:已办结(正常结束),3:已办结(审核通过)。]是否存在相同的单元号记录
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsFlowToBdcdyhRecord(String tableName,String fieldName,Object fieldValue,String recordId);
}
