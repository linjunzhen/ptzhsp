/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.SwbBsWarnMsgService;

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
@Service("swbBsWarnMsgService")
public class SwbBsWarnMsgServiceImpl extends BaseServiceImpl implements SwbBsWarnMsgService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbBsWarnMsgServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;

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
    public void swbBWarnMsg(Log log) {
        // TODO Auto-generated method stub
        //未报送数据超出数量提示
        String noSubmitNumSql = "select count(*) as noSubmitNum from T_BSFW_SWBDATA_RES t " +
                " where t.oper_status = 0 and t.data_type in ('10','20','30') ";
        List<Map<String, Object>> nextDayProWarnList = 
                this.dao.findBySql(noSubmitNumSql, new Object[] {}, null);
        String noSubmitNumString =nextDayProWarnList.get(0).get("noSubmitNum").toString();
        String lemitNumString = 
                dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SWBWBS_YJSL");
        int noSubmitNum = Integer.valueOf(noSubmitNumString).intValue();
        int lemitNum = Integer.valueOf(lemitNumString).intValue();  
        List<Map<String, Object>> yuryList = 
                dicTypeService.getListByJdbc("T_MSJW_SYSTEM_DICTIONARY", "SWBWBSYJRY");
        if (noSubmitNum>500) {
            for (Map<String, Object> map : yuryList) {
                String mobileString = map.get("DIC_CODE").toString();
                if (mobileString.equals("18650465653")) {
                    String content = "审批平台未报送数据已达"+noSubmitNum+"条请注意!";
                    Map<String, Object> msg = new HashMap<String, Object>();
                    msg.put("CONTENT", content);
                    msg.put("RECEIVER_ID", "wbsyjry");
                    msg.put("RECEIVER_MOB", mobileString);
                    msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    msg.put("SEND_STATUS", "0");
                    this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
                }
            }
        }
        if (noSubmitNum>lemitNum) {
            for (Map<String, Object> map : yuryList) {
                String mobileString = map.get("DIC_CODE").toString();
                String content = "审批平台未报送数据已达"+noSubmitNum+"条请注意!";
                Map<String, Object> msg = new HashMap<String, Object>();
                msg.put("CONTENT", content);
                msg.put("RECEIVER_ID", "wbsyjry");
                msg.put("RECEIVER_MOB", mobileString);
                msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                msg.put("SEND_STATUS", "0");
                this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
            }
        }
    
    }
}
