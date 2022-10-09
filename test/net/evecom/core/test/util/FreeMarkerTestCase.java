/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FreeMarkerUtil;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年3月31日 下午5:02:35
 */
public class FreeMarkerTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FreeMarkerTestCase.class);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年3月31日 下午5:02:48
     * @param args
     */
    public static void main(String[] args){
        String str = "fdjskfdjskffdjskf";
        Map<String,Object> ftlRoot = new HashMap<String,Object>();
        String forwordToJspResult = FreeMarkerUtil.getResultString(str, ftlRoot);
        log.info(forwordToJspResult);
    }
}
