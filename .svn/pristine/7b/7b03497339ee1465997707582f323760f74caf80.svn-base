/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 业务类别操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface BusTypeDao extends BaseDao {
    /**
     * 
     * 描述 根据材料ID获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月17日 下午5:41:55
     * @param materId
     * @return
     */
    public Map<String,Object> getIdAndNamesByMaterId(String materId);
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getIdAndNamesByItemId(String itemId);
    /**
     * 
     * 描述 根据项目Id获取ParentNames和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public List<Map<String, Object>> getParentNamesAndNamesByItemId(String itemId);
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getLsIdAndNamesByItemId(String itemId);
    /**
     * 
     * 描述 保存类别项目中间表
     * @author Flex Hu
     * @created 2015年9月22日 下午3:17:40
     * @param typeIds
     * @param itemId
     */
    public void saveBusTypeItem(String[] typeIds,String itemId);
}
