/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月14日 上午11:24:40
 */
public interface SendTaskNoticeService extends BaseService {

    /**
     * 
     * 描述 发送流程短信信息
     * @author Flex Hu
     * @created 2015年12月14日 下午2:25:52
     * @param flowVars
     * @return
     */
    public Map<String,Object> sendMobileMsg(Map<String,Object> flowVars);
    /**
     * 
     * 描述：审核不通过发送短信
     * @author Water Guo
     * @created 2017-4-7 下午3:56:21
     * @param serviceItem
     */
    public void  sendServiceitemMsg(Map<String,Object> serviceItem,String thyj,int sfty);
    
    
    /**
     * 
     * @Description 发送流程成功办结通知给发起人
     * @author Luffy Cai
     * @date 2021年5月28日
     * @param exeId 
     */
    public void sendSuccessMsgToStartUser(String exeId);
}
