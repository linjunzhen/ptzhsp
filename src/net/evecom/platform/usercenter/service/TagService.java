/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.usercenter.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-4-28 下午4:56:23
 */
public interface TagService extends BaseService {

    /**
     * 
     * 获取用户中心标签列表
     * @author Danto Huang
     * @created 2017-5-2 上午9:36:38
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findCatalogBySqlFilter(SqlFilter filter);

    /**
     * 
     * 根据字典类别ID删除掉数据
     * @author Danto Huang
     * @created 2017-5-2 下午2:23:37
     * @param typeId
     */
    public void removeByTypeId(String typeId);
    /**
     * 
     *  判断某个标签类别下是否存在相同编码的标签
     * @author Danto Huang
     * @created 2017-5-2 下午2:39:46
     * @param typeId
     * @param key
     * @return
     */
    public boolean isExist(String typeId,String key);
    /**
     * 
     * 根据标签IDS获取列表数据
     * @author Danto Huang
     * @created 2017-5-3 下午2:23:39
     * @param tagIds
     * @return
     */
    public List<Map<String,Object>> findByTagId(String tagIds);
    /**
     * 
     * 描述   获取一级标签
     * @author Danto Huang
     * @created 2017-5-15 上午10:27:34
     * @param typeId
     * @return
     */
    public List<Map<String,Object>> findParentForSelect(String typeId);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-5-18 上午9:03:19
     * @param typeId
     * @return
     */
    public List<Map<String,Object>> findByTypeId(String typeId);
}
