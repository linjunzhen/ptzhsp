/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.StringUtil;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年7月1日 下午4:46:54
 */
public class TestRandom {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(TestRandom.class);
    /**
     * 描述
     * @author Flex Hu
     * @created 2015年7月1日 下午4:46:54
     * @param args
     */
    public static void main(String[] args) {
     // TODO Auto-generated method stub
        log.info(StringUtil.getRandomIntNumber(11));
    }

}
