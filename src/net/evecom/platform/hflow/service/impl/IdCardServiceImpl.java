/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.hflow.dao.IdCardDao;
import net.evecom.platform.hflow.service.IdCardService;

/**
 * 身份证信息
 * 
 * @author Charlie Wei
 */
@Service("idCardService")
public class IdCardServiceImpl extends BaseServiceImpl implements IdCardService {
    /**
     * dao
     */
    @Resource
    private IdCardDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

}
