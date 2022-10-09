/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.bsfw;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.bsfw.service.ShtzService;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月2日 上午11:29:50
 */
public class ShtzServiceTestCase extends BaseTestCase {
    
    /**
     * 
     */
    @Resource
    private ShtzService shtzService;
    
    @Test
    public void setProjectNumber(){
        shtzService.setProjectNumber(null);
    }
}
