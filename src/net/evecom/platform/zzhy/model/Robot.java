/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import com.google.common.collect.Lists;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述   工商数据接口提交数据机器人实体类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public interface Robot {
    /**
     * 查询执行机状态
     *  推送提交数据--步骤一
     */
    public boolean queryRobotStatus();
    /**
     * 执行更新数据
     *  推送提交数据--步骤二
     */
    public boolean submitData(Map<String,Object> param);
    /**
     * 触发机器执行任务
     *  推送提交数据--步骤三
     */
    public boolean execTask();
}
