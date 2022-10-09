/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.zzhy.service.CreatTaskInterService;
import net.evecom.platform.zzhy.service.MsgSendService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送服务类
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("msgSendService")
public class MsgSendServiceImpl extends BaseServiceImpl implements MsgSendService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CreatTaskInterService.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;
    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     *  保存短信，由定时器发送
     */
    @Override
    public void saveMsg(String content,String mobile) {
        Map<String,Object> msgInfo1=new HashMap<>();
        msgInfo1.put("MSG_INFO",content);
        msgInfo1.put("RECEIVER_MOB",mobile);
        msgInfo1.put("SEND_STATUS",0);
        dao.saveOrUpdate(msgInfo1,"T_MESSAGE_INFO","");
    }
}
