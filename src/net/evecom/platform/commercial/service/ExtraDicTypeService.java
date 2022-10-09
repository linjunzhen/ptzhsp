/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 上午11:39:47
 */
public interface ExtraDicTypeService extends BaseService {

    /**
     * 
     * 根据字典类别ID删除掉数据
     * @author Danto Huang
     * @created 2021年3月30日 上午11:47:44
     * @param typeId
     */
    public void removeByTypeId(String typeId);
    /**
     * 
     * 根据父亲ID获取类别数据
     * @author Danto Huang
     * @created 2021年3月30日 上午11:54:15
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId);
}
