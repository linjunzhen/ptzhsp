/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.ProWarnMsgService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 短信发送服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("proWarnMsgService")
public class ProWarnMsgServiceImpl extends BaseServiceImpl implements ProWarnMsgService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ProWarnMsgServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;

    /**
     * 
     * 描述 获取dao
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:52:27
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 办件警告短信
     * 
     * @author Water Guo
     * @created 2017年03月02日 下午1:28:08
     * @param dataAbutment
     */
    @Override
    public void proWarnMsg(Log log) {
        // TODO Auto-generated method stub
        //明日办结提醒
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date  wd = calendar.getTime();
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd");
        String nextDay = ndf.format(wd);
        String warningDay ="";
        warningDay="%"+nextDay+"%";
        String nextDayProWarnSql = " select us.user_id,us.username, us.mobile,t.exe_id from JBPM6_TASK t " +
                " left join T_MSJW_SYSTEM_SYSUSER us on us.username = t.assigner_code " +
                " left join jbpm6_execution e on e.exe_id = t.exe_id " +
                " left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code" +
                " where t.task_status = 1 and t.task_deadtime like ? " + 
                " and s.ITEM_CODE not IN ( select dic.dic_code from " +
                " T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') " +
                " and s.sxlx <> 1";
        
        List<Map<String, Object>> nextDayProWarnList = 
                this.dao.findBySql(nextDayProWarnSql, new Object[] {warningDay}, null);
        for (Map<String, Object> map : nextDayProWarnList) {
            String mobileString =map.get("mobile")==null?
                    "":map.get("mobile").toString();
            if (StringUtils.isNotBlank(mobileString)) {
                String exeId= map.get("exe_id").toString();
                String content = "您有办件明日到期，申报号为"+exeId+"，请及时办理！区综合审批服务平台";
                String user_id= map.get("user_id").toString();
                String mobile= map.get("mobile").toString();
                Map<String, Object> msg = new HashMap<String, Object>();
                msg.put("CONTENT", content);
                msg.put("RECEIVER_ID", user_id);
                msg.put("RECEIVER_MOB", mobile);
                msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                msg.put("SEND_STATUS", "0");
                this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
            }
        }
        
        //当日办结提醒
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String thisDay = sdf.format(d);
        thisDay="%"+thisDay+"%";
        String toDayProWarnSql = " select us.user_id,us.username, us.mobile,t.exe_id from JBPM6_TASK t " +
                " left join T_MSJW_SYSTEM_SYSUSER us on us.username = t.assigner_code " +
                " left join jbpm6_execution e on e.exe_id = t.exe_id " +
                " left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code" +
                " where t.task_status = 1 and t.task_deadtime like ? " + 
                " and s.ITEM_CODE not IN ( select dic.dic_code from " +
                " T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') " +
                " and s.sxlx <> 1";
        
        List<Map<String, Object>> toDayProWarnList = this.dao.findBySql(toDayProWarnSql, new Object[] {thisDay }, null);
        for (Map<String, Object> map : toDayProWarnList) {
            String mobileString =map.get("mobile")==null?
                    "":map.get("mobile").toString();
            if (StringUtils.isNotBlank(mobileString)) {
                String exeId= map.get("exe_id").toString();
                String content = "您有办件今日到期，明日即逾期，申报号为"+exeId+"，请及时办理！区综合审批服务平台";
                String user_id= map.get("user_id").toString();
                String mobile= map.get("mobile").toString();
                Map<String, Object> msg = new HashMap<String, Object>();
                msg.put("CONTENT", content);
                msg.put("RECEIVER_ID", user_id);
                msg.put("RECEIVER_MOB", mobile);
                msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                msg.put("SEND_STATUS", "0");
                this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
            }
        }
        //预期提醒
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = timesdf.format(d);
        String overDayProWarnSql = " select us.user_id,us.username, us.mobile, t.exe_id from JBPM6_TASK t " +
                " left join T_MSJW_SYSTEM_SYSUSER us on us.username = t.assigner_code " +
                " left join jbpm6_execution e on e.exe_id = t.exe_id " +
                " left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code" +
                " where t.task_status = 1 and t.task_deadtime < ? " +
                " and s.ITEM_CODE not IN ( select dic.dic_code from " +
                " T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') " +
                " and s.sxlx <> 1" ;
        List<Map<String, Object>> overDayProWarnList = 
                this.dao.findBySql(overDayProWarnSql, new Object[] {nowTime }, null);
        for (Map<String, Object> map : overDayProWarnList) {
            String mobileString =map.get("mobile")==null?
                    "":map.get("mobile").toString();
            if (StringUtils.isNotBlank(mobileString)) {
                String exeId= map.get("exe_id").toString();
                String content = "您有产生逾期件，已被监察，申报号为"+exeId+"，请及时反馈！区综合审批服务平台";
                String user_id= map.get("user_id").toString();
                String mobile= map.get("mobile").toString();
                Map<String, Object> msg = new HashMap<String, Object>();
                msg.put("CONTENT", content);
                msg.put("RECEIVER_ID", user_id);
                msg.put("RECEIVER_MOB", mobile);
                msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                msg.put("SEND_STATUS", "9");
                this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
            }
        }
        
    }
}
