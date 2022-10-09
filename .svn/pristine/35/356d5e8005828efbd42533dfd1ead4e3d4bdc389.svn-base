/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.call.dao.CallDao;
import net.evecom.platform.pay.service.PayService;

/**
 * 
 * 描述
 * @author Kester Chen
 * @created 2020年2月8日 上午10:53:00
 */
@Service("payService")
public class PayServiceImpl extends BaseServiceImpl implements PayService {

    /**
     * 引入dao
     */
    @Resource
    private CallDao dao;

    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2020年2月8日 上午10:53:46
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }    

}
