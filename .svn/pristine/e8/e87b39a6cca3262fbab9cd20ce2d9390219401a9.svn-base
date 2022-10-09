/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

import org.apache.commons.logging.Log;

/**
 * 描述 短信发送服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface SysSendMsgService extends BaseService {
    /**
     * 
     * 描述 保存短信下发信息到短信下发记录表
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:51:39
     * @param content
     *            发送的内容
     * @param receiverId
     *            接入人
     * @param receiverMob
     *            接入人手机号
     * @param sendStatus
     *            发送状态 0： 未发送 1：已发送成功 2：发送失败
     * @param sendResult
     *            发送返回码
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public Map<String, Object> saveSendMsg(String content, String receiverId,
            String receiverMob, Integer sendStatus, String sendResult);
    
    /**
     * 
     * 描述 保存短信下发信息到短信下发记录表
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:51:39
     * @param content
     *            发送的内容
     * @param receiverId
     *            接入人
     * @param receiverMob
     *            接入人手机号
     * @param sendStatus
     *            发送状态 0： 未发送 1：已发送成功 2：发送失败
     * @param sendResult
     *            发送返回码
     * @param type
     *            工程建设标识  1：工程建设  0：非工程建设
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public Map<String, Object> saveSendMsg(String content, String receiverId,
            String receiverMob, Integer sendStatus, String sendResult,String type);
    
    
    /**
     * 描述 调用接口发送短信(发送多条  ，立即发送，旧接口，暂时停用)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午4:20:19
     * @param msgList
     *            要发送的内容:Map{[content:发送的内容]，[receiverMob: 接收人手机号]};
     * @return
     */
    public String sendMsg(List<Map<String, Object>> msgList);


    /**
     * 描述 调用接口发送短信 (立即发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午4:20:19
     * @param content
     *            :发送的内容; receiverMob : 接收人手机号
     * @return
     */
    public String sendMsg(String content, String receiverMobs);

    /**
     * 描述 市县区向省网办报送办件计时暂停信息   供定时器调用
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendMsgList();
}
