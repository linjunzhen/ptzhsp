/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.commercial.dao.ExtraDicTypeDao;
import net.evecom.platform.commercial.service.ExtraDicTypeService;

import org.springframework.stereotype.Service;

/**
 * 
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 上午11:41:33
 */
@Service("extraDicTypeService")
public class ExtraDicTypeServiceImpl extends BaseServiceImpl implements ExtraDicTypeService {
    /**
     * 所引入的dao
     */
    @Resource
    private ExtraDicTypeDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 根据字典类别ID删除掉数据
     * @author Danto Huang
     * @created 2021年3月30日 上午11:47:44
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        dao.removeByTypeId(typeId);
    }
    
    /**
     * 
     * 根据父亲ID获取类别数据
     * @author Danto Huang
     * @created 2021年3月30日 上午11:54:15
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId){
        return dao.findByParentId(parentId);
    }
}
