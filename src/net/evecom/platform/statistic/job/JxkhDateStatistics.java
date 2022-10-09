/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 *
 */
package net.evecom.platform.statistic.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.statistic.service.StatisticsNewService;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.commons.logging.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:绩效考核每日统计当天受理办件得分
 *
 * @author Madison You
 * @created 2019/09/30 14:32
 */
public class JxkhDateStatistics implements Job {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/10/16 18:08:00
     * @param
     * @return
     */
    private StatisticsNewService statisticsNewService;

    /**
     * 描述:日志
     *
     * @author Madison You
     * @created 2019/10/16 18:08:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(JxkhDateStatistics.class);

    /**
     * 描述:构造方法
     *
     * @author Madison You
     * @created 2019/10/16 18:08:00
     * @param
     * @return
     */
    public JxkhDateStatistics(){
        super();
        if (statisticsNewService == null) {
            statisticsNewService = (StatisticsNewService) AppUtil.getBean("statisticsNewService");
        }
    }

    /**
     * 描述:绩效考核每日统计当天受理办件得分
     *
     * @author Madison You
     * @created 2019/9/30 14:35:00
     * @param
     * @return
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        log.info("开始统计:"+today+"的办件得分数据");
        List<Map<String, Object>> userList = statisticsNewService.jxkhdfryData();
        List<Map<String, Object>> scoreList = statisticsNewService.jxkhbjfzData(today);
        for (Map<String,Object> user : userList) {
            double SLDF = 0;
            double SHDF = 0;
            for(Map<String,Object> score : scoreList) {
                if (score.get("SLRY").equals(user.get("USER_ID"))) {
                    SLDF += (double)score.get("SLDF");
                }
                if (score.get("SHRY").equals(user.get("USER_ID"))) {
                    SHDF += (double)score.get("SHDF");
                }
            }
            user.put("SLDF", SLDF);
            user.put("SHDF", SHDF);
        }
        for(Map<String,Object> user : userList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("USER_ID", user.get("USER_ID"));
            map.put("CREATE_DATE", today);
            if ((double)user.get("SLDF")!= 0 || (double)user.get("SHDF") != 0 ) {
                map.put("SLDF", net.evecom.platform.bespeak.util.StringUtils.getNumFormat((double) user.get("SLDF")));
                map.put("SHDF", net.evecom.platform.bespeak.util.StringUtils.getNumFormat((double) user.get("SHDF")));
                statisticsNewService.saveOrUpdate(map, "JBPM6_JXKHSTATIST", null);
            }
        }
        log.info("结束统计:"+today+"的办件得分数据");
    }

}
