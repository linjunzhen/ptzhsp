/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.util.RobotUtils;

/**
 * 
 * 描述：个体名称查重机器人实体类
 * 
 * @author Rider Chen
 * @created 2021年1月13日 上午10:59:17
 */
public class RobotOneToGt extends RobotNum {
    /**
     * 实例化对象参数
     */
    public RobotOneToGt() {
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        rootName = StringUtil.getString(dictionaryService.get("robotConfig", "robotOneToGtName").get("DIC_DESC"));
        jobName = StringUtil.getString(dictionaryService.get("robotConfig", "robotOneToGtJobName").get("DIC_DESC"));
        jobUid = StringUtil.getString(dictionaryService.get("robotConfig", "robotOneToGtJobUid").get("DIC_DESC"));
        queryStatusUrl = StringUtil.getString(dictionaryService.get("robotConfig", "queryStatusUrl").get("DIC_DESC"));
        updateUrl = StringUtil.getString(dictionaryService.get("robotConfig", "updateUrl").get("DIC_DESC"));
        triggerUrl = StringUtil.getString(dictionaryService.get("robotConfig", "triggerUrl").get("DIC_DESC"));
        paramType = RobotUtils.paramTypeString;
    }
}
