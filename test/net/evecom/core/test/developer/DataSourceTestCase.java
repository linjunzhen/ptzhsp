/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.developer;

import java.util.List;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.base.dao.DataSourceDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年10月13日 下午2:49:14
 */
public class DataSourceTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(DataSourceTestCase.class);
    /**
     * dataSourceDao
     */
    @Resource
    private DataSourceDao dataSourceDao;
    
    @Test
    public void findSqlServerTables(){
        List<String> tables = dataSourceDao.findSqlServerTables();
        for(String table:tables){
            log.info("table:"+table);
        }
    }
    
    @Test
    public void getSqlServerTableInfo(){
        dataSourceDao.getSqlServerTableInfo("T_MSJW_DEMO_PRODUCT");
    }
    
    @Test
    public void getSqlServerPrimaryKeys(){
        List<String> keys = dataSourceDao.getSqlServerPrimaryKeys("T_MSJW_DEMO_PRODUCT");
        log.info(keys.toString());
    }
}
