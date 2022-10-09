/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.LinkWarnMsgService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

/**
 * 描述 短信发送服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("linkWarnMsgService")
public class LinkWarnMsgServiceImpl extends BaseServiceImpl implements LinkWarnMsgService {
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
        // 当日办结提醒
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String thisDay = sdf.format(d);
        thisDay = "%" + thisDay + "%";
        StringBuffer sql=new StringBuffer();
        sql.append("select e.exe_id,h.*,u.mobile,u.user_id from jbpm6_hanginfo h  ");
        sql.append(" left join jbpm6_task k on h.task_id=k.task_id ");
        sql.append(" left join jbpm6_execution e on k.exe_id=e.exe_id ");
        sql.append(" left join t_msjw_system_sysuser u on h.userid=u.user_id");
        sql.append(" where h.link_status='1' and");
        sql.append(" h.link_end_time like ? ");
        List<Map<String, Object>> toDayProWarnList = this.dao.findBySql
                (sql.toString(), new Object[] { thisDay }, null);
        for (Map<String, Object> map : toDayProWarnList) {
            String mobileString = map.get("MOBILE") == null ? "" : map.get("MOBILE").toString();
            String mobile2=map.get("LINK_MAN_TEL")==null?"": map.get("LINK_MAN_TEL").toString();
            if (StringUtils.isNotBlank(mobileString)) {
                String exeId = map.get("EXE_ID").toString();
                String content = "您有办件挂起今日到期，明日即逾期，申报号为" + exeId + "，请及时办理！区综合审批服务平台";
                String user_id = map.get("USER_ID").toString();
                String mobile = map.get("MOBILE").toString();
                Map<String, Object> msg = new HashMap<String, Object>();
                msg.put("CONTENT", content);
                msg.put("RECEIVER_ID", user_id);
                msg.put("RECEIVER_MOB", mobile);
                msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                msg.put("SEND_STATUS", "0");
                this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
                msg.put("RECEIVER_MOB", mobile2);
                msg.put("RECEIVER_ID", map.get("LINK_MAN"));
                this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
            }
        }
    }
}
