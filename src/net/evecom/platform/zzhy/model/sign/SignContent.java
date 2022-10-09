/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.sign;


import net.evecom.platform.zzhy.model.Robot;

import java.util.Map;
import java.util.Objects;

/**
 * 描述   提交业务数据实体类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public interface SignContent {
    /**
     * 发送面签信息
     * @param flowVars
     */
    public void sendSignMsg(Map<String,Object> flowVars);
}
