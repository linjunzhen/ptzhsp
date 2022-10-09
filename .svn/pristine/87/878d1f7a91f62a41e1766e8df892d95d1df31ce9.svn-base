/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述：许可dao
 * @author Water Guo
 * @created 2017-4-12 上午9:41:58
 */
@SuppressWarnings("rawtypes")
public interface CommonXKDao extends BaseDao{
    /**
     * 
     * 描述 判断 许可是否已存在
     * 
     * @author Derek Zhang
     * @created 2015年10月16日 上午9:28:00
     * @param busName
     * @param busType
     * @param content
     * @return
     */
    public String getXKID(String itemId,String xkcontent);
    /**
     * 
     * 描述：查找许可list
     * @author Water Guo
     * @created 2017-4-12 上午11:52:46
     * @param xktype
     * @param departId
     * @return
     */
    public List<Map<String,Object>> findXKList(String itemId,String departId);
}
