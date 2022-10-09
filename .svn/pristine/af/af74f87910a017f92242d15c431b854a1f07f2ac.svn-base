/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述
 * @author Kester Chen
 * @created 2017-11-21 上午9:33:20
 */
public interface SwbItemDataService extends BaseService {
    /**
     * 
     * 描述 获取需要创建的事项
     * @author Kester Chen
     * @created 2017-11-21 上午9:33:27
     * @return
     */
    public List<Map<String,Object>> findNeedToCreate();
    /**
     * 
     * 描述 获取需要创建的事项
     * @author Kester Chen
     * @created 2017-11-21 上午9:33:27
     * @return
     */
    public List<Map<String, Object>> findNeedToCreateByIds(String unid);
    /**
     * 
     * 描述 根据ID移除事项
     * @author Kester Chen
     * @created 2018-6-1 下午3:28:29
     * @param unid
     */
    public void deleteSwbItemByIds(String unid);
    /**
     *
     * 描述 创建省网下发事项
     * @author Kester Chen
     * @created 2017-11-21 上午9:34:33
     * @param swbData
     * @return
     * @throws Exception
     */
    public Map<String,Object> createItem(Map<String,Object> swbData) throws Exception;
    /**
     * 
     * 描述 创建省网下发事项
     * @author Kester Chen
     * @created 2017-11-21 上午9:34:33
     * @param swbData
     * @return
     * @throws Exception
     */
    public Map<String,Object> createItemNew(Map<String,Object> swbData) throws Exception;
    int isExistRightbillCatalog(String projidMain);
}
