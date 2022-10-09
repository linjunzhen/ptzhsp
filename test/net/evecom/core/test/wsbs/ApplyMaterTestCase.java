/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.wsbs;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.test.util.XmlUtilTestCase;
import net.evecom.platform.wsbs.dao.ApplyMaterDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年9月23日 上午10:55:59
 */
public class ApplyMaterTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ApplyMaterTestCase.class);
    /**
     * 
     */
    @Resource
    private ApplyMaterDao applyMaterDao;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年9月23日 上午10:56:45
     */
    @Test
    public void getMaxSn(){
        int maxSn = applyMaterDao.getMaxSn("dd");
        log.info(maxSn);
    }
}
