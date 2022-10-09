/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.test.system.WorkDayTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:16:25
 */
public class DateTimeUtilTest {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(DateTimeUtilTest.class);
    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:16:25
     * @param args
     */
    public static void main(String[] args) {
        /*String beginDate = "2016-03-10 23:33:33";
        log.info(DateTimeUtil.getStrOfDate(DateTimeUtil.
                getDateOfStr(beginDate, "yyyy-MM-dd HH:mm:ss"), "yyyyMMddHHmmss"));*/
        log.info(DateTimeUtil.getStrOfDate(new Date(), "HH"));
        log.info(DateTimeUtil.getStrOfDate(new Date(), "mm"));
        log.info(DateTimeUtil.getCurrentMonth());
        log.info(DateTimeUtil.getStrOfDate(new Date(), "dd"));
        log.info(DateTimeUtil.getWeek(DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")));
    }

}
