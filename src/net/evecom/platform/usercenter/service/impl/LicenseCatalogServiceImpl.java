/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.usercenter.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.usercenter.dao.LicenseCatalogDao;
import net.evecom.platform.usercenter.service.LicenseCatalogService;

/**
 * 描述    证照目录
 * @author Danto Huang
 * @created 2020年3月4日 下午3:24:56
 */
@Service("licenseCatalogService")
public class LicenseCatalogServiceImpl extends BaseServiceImpl implements LicenseCatalogService {

    /**
     * 
     */
    @Resource
    private LicenseCatalogDao dao;
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年3月4日 下午3:26:17
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected LicenseCatalogDao getEntityDao() {
        return dao;
    }
}
