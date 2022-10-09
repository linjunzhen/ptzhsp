/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.hflow.service.MaterConfigService;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月29日 上午9:12:06
 */
public class MaterConfigTestCase extends BaseTestCase {
    
    /**
     * 
     */
    @Resource
    private MaterConfigService materConfigService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月29日 上午9:12:59
     */
    @Test
    public void testSave(){
        Map<String,Object> materConfig = new HashMap<String,Object>();
        materConfig.put("DEF_ID", "402881a350fe9b190150fed8db2f0026");
        materConfig.put("NODE_NAME","预审");
        materConfig.put("TPL_ID","402881ae52874a320152877476150005");
        materConfig.put("CONFIG_SN",1);
        materConfigService.saveOrUpdate(materConfig, "JBPM6_MATERCONFIG",null);
    }
}
