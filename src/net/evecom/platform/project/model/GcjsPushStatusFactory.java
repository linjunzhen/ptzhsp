/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.model;


import net.evecom.core.util.StringUtil;

import java.util.Map;
import java.util.Objects;

/**
 * 描述   工程建设推送状态工厂
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public   class GcjsPushStatusFactory {
    /**
     * 根据流程key获取相关的推送模板类
     * @param flowDatas
     * @return
     */
    public static GcjsPushStatusTemplate getGcjsPushStatusTemplage(Map<String, Object> flowDatas){
        String flowKey=StringUtil.getString(flowDatas.get("EFLOW_DEFKEY"));
        if(Objects.equals(flowKey,GcjsPushStatusTemplate.GCJS_120)||flowKey.indexOf("2021GCJS")>-1){
            return new GcjsPush120();
        }
        return null;
    }

}
