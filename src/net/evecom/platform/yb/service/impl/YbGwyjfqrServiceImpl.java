/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.yb.dao.YbGwyjfqrDao;
import net.evecom.platform.yb.service.YbGwyjfqrService;

/**
 * 描述  公务员医疗补助费缴费确认业务操作service
 * @author Allin Lin
 * @created 2019年11月5日 下午3:16:58
 */
@Service("ybGwyjfqrService")
public class YbGwyjfqrServiceImpl extends BaseServiceImpl implements YbGwyjfqrService{
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbGwyjfqrServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private YbGwyjfqrDao dao;
    
    /**
     * 描述  覆盖DAO的实体方法
     * @author Allin Lin
     * @created 2019年11月5日 下午3:19:00
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
}
