/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

/**
 * 描述   短信配置工厂类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class MsgConfigFactory {
    /**
     * msgConfig
     */
    private static MsgConfig msgConfig;
    /**
     * 获取msgConfig
     * @return
     */
    public static MsgConfig getMsgConfig()  {
        if (msgConfig == null) {
            //防止重复创建实例
            synchronized (MsgConfig.class) {
                if(msgConfig==null){
                    msgConfig=MsgConfig.getMsgConfig();
                }
            }
        }
        return msgConfig;
    }
}
