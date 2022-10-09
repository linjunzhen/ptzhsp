/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.yb.dao.YbYrdwcbDao;
import net.evecom.platform.yb.dao.impl.YbYrdwcbDaoImpl;
import net.evecom.platform.yb.service.YbYrdwcbService;

/**
 * 描述  用人单位基本医疗保险、生育保险参保登记业务service
 * @author Allin Lin
 * @created 2019年11月12日 下午3:31:25
 */
@Service("ybYrdwcbService")
public class YbYrdwcbServiceImpl extends BaseServiceImpl implements YbYrdwcbService{
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbYrdwcbServiceImpl.class);
    
    
    /**
     * 引入dao
     */
    @Resource
    private YbYrdwcbDao dao;

    /**
     * 描述
     * @author Allin Lin
     * @created 2019年11月12日 下午3:32:38
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

}
