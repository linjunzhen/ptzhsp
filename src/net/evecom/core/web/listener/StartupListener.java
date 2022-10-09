/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.job.CommercialAutoJumpJob;
import net.evecom.platform.project.job.ProjectDataTimerJob;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.GlobalUrlService;
import net.evecom.platform.system.service.ScheduleService;
import net.evecom.platform.weixin.util.WeixinUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 描述 继承spring的contextLoad监听器，以便启动的时候初始化系统
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午5:15:41
 */
public class StartupListener extends ContextLoaderListener {
    /**
     * 日志工具
     */
    private static Log logger = LogFactory.getLog(StartupListener.class);

    /**
     * 
     * 描述 容器初始化
     * 
     * @author Flex Hu
     * @created 2014年9月29日 上午11:05:12
     * @param event
     */
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        AppUtil.init(event.getServletContext());
        DictionaryService dictionaryService =(DictionaryService) AppUtil.getBean("dictionaryService");
        GlobalUrlService globalUrlService = (GlobalUrlService) AppUtil.getBean("globalUrlService");
        String dictionaryCode = dictionaryService.getDicCode("ISLOGINREPEATED", "是否允许重复登录");
        AppUtil.setIsAllowLoginRepeat(dictionaryCode);

        //获取公共URL权限
        Set<String> allAnonUrlSet = new HashSet<String>();
        List<String> filterUrls = globalUrlService.findByFilterType("1");
        allAnonUrlSet.addAll(filterUrls);
        AppUtil.setAllAnonUrlSet(allAnonUrlSet);
        
        Set<String> allSessionUrlSet = new HashSet<String>();
        List<String> allSessionUrls = globalUrlService.findByFilterType("2");
        allSessionUrlSet.addAll(allSessionUrls);
        AppUtil.setAllSessionUrlSet(allSessionUrlSet);
        
        // 调用定时器方法
        try {
            logger.info("<-----服务器启动,定时任务加载---->:时间[" + new java.util.Date().toLocaleString() + "]");
            Scheduler scheduler = (Scheduler) AppUtil.getBean("schedulerFactory");
            ScheduleService scheduleService = (ScheduleService) AppUtil.getBean("scheduleService");
            scheduler.pauseAll();
            List<Map<String, Object>> list = scheduleService.findByStatus(ScheduleService.ACTIVE_STATUS);
            for (Map<String, Object> schedule : list) {
                String jobName = (String) schedule.get("JOB_NAME");
                if (!jobName.endsWith("(内网)")) {//外网定时器使用
//                if (jobName.endsWith("(内网)")) {//内网定时器使用
                    String className = (String) schedule.get("CLASS_NAME");
                    String jobCron = (String) schedule.get("JOB_CRON");
                    String uuid = UUID.randomUUID().toString();
                    String trigname = uuid.replace("-", "").substring(0, 8);
                    JobDetail job = new JobDetail(jobName, Scheduler.DEFAULT_GROUP, Class.forName(className));
                    CronTrigger triggers = new CronTrigger(trigname, Scheduler.DEFAULT_GROUP, jobCron);
                    scheduler.scheduleJob(job, triggers);
                }
            }
            scheduler.deleteJob("schedulerJobDetail", Scheduler.DEFAULT_GROUP);
            scheduler.resumeAll();
            String[] strs = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
            for (int i = 0; i < strs.length; i++) {
                logger.info("<--------执行定时任务名称" + i + ": " + strs[i] + "--------->");
            }
//-----------------------------------商事启动定时器内网时注释--------------------------------------------
            // 商事定时器改到系统配置中 ComNeedAutoJumpJob.java
//            Timer timer2 = new Timer();
//            TimerTask task2 = new TimerTask(){
//                @Override
//                public void run() {
//                    new CommercialAutoJumpJob();
//                }
//            };
//            timer2.schedule(task2, 60*1000,120000);
//------------------------------------------------------------------------------------------
            
//-----------------------------------工程建设定时器定时器内网开启，外网时注释--------------------------------------------
          /*  ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
            long initialDelay = 30;//第一次延时30秒执行
            long period = 60;//每60秒执行一次
            service.scheduleAtFixedRate(new Runnable() {                
                @Override
                public void run() {
                    new ProjectDataTimerJob();
                }
            }, initialDelay, period, TimeUnit.SECONDS);*/
//------------------------------------------------------------------------------------------
            // 开始初始化业务编码MAP
            this.initInterfaceCodeMap();

            // 获取access_token
//-----------------------------------本地或内网时注释掉--------------------------------------
//            WeixinUtil.initWxAccessToken();
//------------------------------------------------------------------------------------------
            // 创建微信公众号自定义菜单
//            WeixinUtil.createMenuDef();
            
        } catch (Exception e) {
            logger.error("定时器出错:" + e.getMessage());
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年5月28日 上午9:21:36
     */
    public void initInterfaceCodeMap() {
        Map<String, String> map = new HashMap<String, String>();
        // 初始化网安类
        map.put("T_MSJW_FWDT_WANEWBARCARD", "0056");
        map.put("T_MSJW_FWDT_WAWBBGCS", "0056");
        map.put("T_MSJW_FWDT_WAWBWLAQSH", "0056");
        // 初始化户政和治安类别
        map.put("T_MSJW_FWDT_DOMESTICNOBIRTH", "0754");
        map.put("T_MSJW_FWDT_DOMESTICABROAD", "0755");
        map.put("T_MSJW_FWDT_DOMESTICOUR", "0753");
        map.put("T_MSJW_FWDT_IMMIGRATIONPARENT", "0711");
        map.put("T_MSJW_FWDT_IMMIGRATIONCHILD", "0712");
        map.put("T_MSJW_FWDT_IMMIGRATIONSPOUSE", "0713");
        map.put("T_MSJW_FWDT_IMMIGRATIONDA", "0732");
        map.put("T_MSJW_FWDT_IMMIGRATIONEMTR", "0741");
        map.put("T_MSJW_FWDT_CHANGEBIRTHDAY", "0702");
        map.put("T_MSJW_FWDT_CHANGENATION", "0704");
        map.put("T_MSJW_FWDT_CHANGENAME", "0705");
        map.put("T_MSJW_FWDT_CHANGESEX", "0706");
        map.put("T_MSJW_FWDT_DOMESTICSTUDENT", "0737");
        map.put("T_MSJW_FWDT_IMMIGRATIONDOM", "0736");
        map.put("T_MSJW_FWDT_IMMIGRATIONPOSTDOR", "0734");
        map.put("T_MSJW_FWDT_IMMIGRATIONMONK", "0752");
        map.put("T_MSJW_FWDT_IMMIGRATIONAARW", "0731");
        map.put("T_MSJW_FWDT_IMMIGRATIONEMPLOY", "0738");
        map.put("T_MSJW_FWDT_DOMESTICOVERSEE", "0733");
        map.put("T_MSJW_FWDT_DOMESTICOUR", "0753");
        map.put("T_MSJW_FWDT_DOMESTICSETTLE", "0761");
        // 初始化治安类别
        map.put("T_MSJW_FWDT_ZALGYTZHYXKZSQ", "0801");
        map.put("T_MSJW_FWDT_ZADDYTZHYXKZSQ", "0803");
        map.put("T_MSJW_FWDT_ZATZHYGGCSBABGSQ", "0841");
        map.put("T_MSJW_FWDT_ZAGZKZYTZHYXKZSQ", "0802");
        map.put("T_MSJW_FWDT_ZABFQCHSYBA", "0832");
        map.put("T_MSJW_FWDT_ZAYSYBADJ", "0831");
        map.put("T_MSJW_FWDT_ZAAMFWCSBADJ", "0836");
        map.put("T_MSJW_FWDT_ZAYZKZYBADJ", "0835");
        map.put("T_MSJW_FWDT_ZAFJJSSGYBADJ", "0833");
        map.put("T_MSJW_FWDT_ZATZHYXKZBGSQ", "0840");
        map.put("T_MSJW_FWDT_ZAJDCXLBADJ", "0834");
        map.put("T_MSJW_FWDT_ZABPGCJSRYWSBM", "0816");
        map.put("T_MSJW_FWDT_ZABPYXKSQ", "0813");
        map.put("T_MSJW_FWDT_ZABPZYDWFYYXXKSQ", "0812");
        map.put("T_MSJW_FWDT_CIVEXPOTRANSLIC", "0815");
        map.put("T_MSJW_FWDT_CIVEXPOBUYLIC", "0814");
        map.put("T_MSJW_FWDT_ZASJHYXKSQ", "0821");
        // 初始化边防类别
        map.put("T_MSJW_FWDT_BFCBHKB", "0059");
        map.put("T_MSJW_FWDT_BFCHYMDJ", "0060");
        // 初始化监管类别
        map.put("T_MSJW_FWDT_JGLSFXRY", "0068");
        map.put("T_MSJW_FWDT_JGBJGRYLS", "0069");
        // 配置技防类别
        map.put("T_MSJW_FWDT_JFAQJSFFCP", "0064");
        map.put("T_MSJW_FWDT_JFNJDJ", "0065");
        map.put("T_MSJW_FWDT_JFJGCPCCSJFA", "0066");
        map.put("T_MSJW_FWDT_JFJGCPCCYS", "0067");
        map.put("T_MSJW_FWDT_JFGGAQBA", "0101");
        // 配置出入境类别
        map.put("T_MSJW_FWDT_CRJCXYY", "0048");
        map.put("T_MSJW_FWDT_CRJGACRJZJXPJC", "0071");
        map.put("T_MSJW_FWDT_CRJWLGATQ", "00265b");
        map.put("T_MSJW_FWDT_CRJWSYY", "0025_1");
        map.put("T_MSJW_FWDT_CRJBZJDCX", "0050");
        map.put("T_MSJW_FWDT_CRJBZJDCX", "0050");
        map.put("T_MSJW_FWDT_CRJWGRYSL", "00729b");
        map.put("T_MSJW_FWDT_CRJCGZYSL", "00967b");
        map.put("T_MSJW_FWDT_CRJXMFJ", "00708b");
        map.put("T_MSJW_FWDT_CONTACTKENDORSE", "0024b");
        AppUtil.setInterfaceCodesMap(map);
    }
}
