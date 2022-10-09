/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.base.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月17日 下午3:45:17
 */
public interface DataSourceService extends BaseService {
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
    /**
     * 判断一个流程记录状态在[1:正在办理中]是否存在相同的单元号记录
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public Map<String, Object> findAllBdcdyh(Object fieldValue,String exeId);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/11 17:00:00
     * @param 
     * @return 
     */
    boolean isExistsFlowToBdcdyhRecord(String busTableName, String fieldValue , String busRecordId);
}
