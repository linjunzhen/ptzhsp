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
import net.evecom.platform.yb.dao.YbGwyssfwqrDao;
import net.evecom.platform.yb.service.YbGwyssfwqrService;

/**
 * 描述  国家公务员医疗补助实施范围确认业务操作service
 * @author Allin Lin
 * @created 2019年11月1日 下午3:46:04
 */
@Service("ybGwyssfwqrService")
public class YbGwyssfwqrServiceImpl extends BaseServiceImpl implements YbGwyssfwqrService{
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbGwyssfwqrServiceImpl.class);
    
    /**
     * 引入的dao
     */
    @Resource
    private YbGwyssfwqrDao dao;
    
    /**
     * 描述 覆盖DAO的实体方法
     * @author Allin Lin
     * @created 2019年11月1日 下午3:53:25
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    

}
