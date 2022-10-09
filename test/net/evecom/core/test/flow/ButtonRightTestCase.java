/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.hflow.service.ButtonRightService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月12日 上午11:29:47
 */
public class ButtonRightTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ButtonRightTestCase.class);
    /**
     * buttonRightService
     */
    @Resource
    private ButtonRightService buttonRightService;
    
    @Test
    public void getButtonRight() {
        Map<String, Object> map = buttonRightService.getByJdbc("JBPM6_BUTTONRIGHT", new String[] { "DEF_ID",
            "NODE_NAME", "BUTTON_KEY", "DEF_VERSION" }, new Object[] { "1", "规划选址及用地意向申请", "BackFlow", 6 });
        log.info(map);
    }
    
    @Test
    public void copyButtonRights(){
        buttonRightService.copyButtonRights("40288b9f537ccbc301537d47c09e00db", 1,"-1");
    }
}
