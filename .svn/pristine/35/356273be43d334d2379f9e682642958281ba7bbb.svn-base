/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.util.RobotUtils;

/**
 * 描述   工商数据接口提交数据机器人实体类(合伙）
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class RobotOneOfHh extends  RobotNum{
    /**
     *实例化对象参数
     */
    public RobotOneOfHh(){
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        rootName= StringUtil.getString(dictionaryService.get("robotConfig",
                "robotOneName").get("DIC_DESC"));
        jobName= StringUtil.getString(dictionaryService.get("robotConfig",
                "robotOneJobNameHh").get("DIC_DESC"));
        jobUid= StringUtil.getString(dictionaryService.get("robotConfig",
                "robotOneJobUidHh").get("DIC_DESC"));
        queryStatusUrl= StringUtil.getString(dictionaryService.get("robotConfig",
                "queryStatusUrl").get("DIC_DESC"));
        updateUrl= StringUtil.getString(dictionaryService.get("robotConfig",
                "updateUrl").get("DIC_DESC"));
        triggerUrl= StringUtil.getString(dictionaryService.get("robotConfig",
                "triggerUrl").get("DIC_DESC"));
        paramType= RobotUtils.paramTypeObject;
    }
}
