/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.exesuccess;


import java.util.Map;

/**
 * 描述   办件短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public interface ExeContent {
    /**
     * 发送办件退回短信
     * @param exeId
     */
    public void sendSuccessMsg(String exeId);
    /**
     * 发送办件退回短信
     * @param exeId
     */
    public void sendBackMsg(String exeId);
}
