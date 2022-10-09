/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.system.dao.DictionaryDao;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DataPWDChanceService;
import net.evecom.platform.system.service.SysMsgService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 提示修改密码服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("dataPWDChanceService")
public class DataPWDChanceServiceImpl extends BaseServiceImpl implements DataPWDChanceService {
    /**
     * sysSendMsgDao
     */
    @Resource
    private SysSendMsgDao dao;
    /**
     * dictionaryDao
     */
    @Resource
    private DictionaryDao dictionaryDao;

    /**
     * 
     * 描述 获取dao
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:52:27
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述：数据库密码定时修改提醒
     * @author Water Guo
     * @created 2017-12-11 上午9:05:38
     */
    @Override
    public void dataPWDChanceMsg() {
        String sql="select username,expiry_date from dba_users where username='PTZHSP'";
        Map<String,Object> map=dao.getByJdbc(sql, null);
        String expiry_date=map.get("EXPIRY_DATE")==null?"":
            map.get("EXPIRY_DATE").toString();
        if(StringUtils.isNotEmpty(expiry_date)){
            Date expiryDate=DateTimeUtil.format(expiry_date, "yyyy-MM-dd");
            Date curDate=new Date();
            int remainDay=(int)((expiryDate.getTime()-curDate.getTime())/ (1000*3600*24));
            if(remainDay<=20){
                pwdChanceMsg(remainDay);
            }
        }
    }
    /**
     * 
     * 描述：短信提醒修改密码
     * @author Water Guo
     * @created 2017-12-11 上午10:53:08
     * @param day
     */
    private void pwdChanceMsg(int day){
        String mobile=dictionaryDao.getDicCode("DataPWDChance", "pwdchanmob");
        String[] mobileArr=mobile.split(",");
        StringBuffer msgInfo=new StringBuffer("数据库密码快过期了，");
        msgInfo.append("剩余").append(day).append("天，请及时修改!");
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("CONTENT", msgInfo.toString());
        msg.put("RECEIVER_ID", "wbsyjry");
        msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        msg.put("SEND_STATUS", "0");
        for(String mob:mobileArr){
            msg.put("RECEIVER_MOB", mob);
            this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
        }
    }
}
