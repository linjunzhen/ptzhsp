/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import net.evecom.core.service.BaseService;

/**
 * 描述 数据归档
 * 
 * @author Reuben Bao
 * @created 2019年3月28日 上午10:24:40
 */
@SuppressWarnings("rawtypes")
public interface ProcessArchiveService extends BaseService {

    /**
     * 描述 数据归档
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午10:24:29
     * @param endTime
     */
    public void processArchive();
    
    /**
     * 描述 根据exeId做流程归档
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午3:06:14
     * @param exeIds
     */
    public void processArchiveByExeId(String exeIds);
    
    /**
     * 描述 执行归档操作并删除已归档数据
     * 
     * @author Reuben Bao
     * @created 2019年8月15日 下午2:37:24
     * @param exeIds
     */
    public void processArchive(String exeIds);
    
    /**
     * 描述 删除已归档流程
     * 
     * @author Reuben Bao
     * @created 2019年8月16日 上午9:20:58
     */
    public void deleteFiledFlows();
}
