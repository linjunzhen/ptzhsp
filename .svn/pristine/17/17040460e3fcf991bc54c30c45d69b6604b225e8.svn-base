/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月7日 上午11:01:49
 */
public class WorkDayTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WorkDayTestCase.class);
    /**
     * dataSourceService
     */
    @Resource
    private DataSourceService dataSourceService;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年11月7日 上午11:02:45
     */
    @Test
    public void testSave(){
        Date firstDay = DateTimeUtil.getDateOfStr("2018-01-01", "yyyy-MM-dd");
        for(int i =0;i<=1000;i++){
            Date date = DateTimeUtil.getNextDay(firstDay, i);
            String dateValue = DateTimeUtil.getStrOfDate(date, "yyyy-MM-dd");
            int workNum = DateTimeUtil.getWeekNum(dateValue);
            Map<String,Object> workDay = new HashMap<String,Object>();
            workDay.put("W_DATE", dateValue);
            if(workNum==6||workNum==7){
                workDay.put("W_SETID",1);
            }else{
                workDay.put("W_SETID",2);
            }
            dataSourceService.saveOrUpdate(workDay, "T_MSJW_SYSTEM_WORKDAY",null);
        }
        
    }
    
    @Test
    public void workdayService(){
        String dateLineTime = workdayService.getDeadLineDate("2015-11-07", 3);
        log.info(dateLineTime);
    }
}
