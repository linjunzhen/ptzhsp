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
 * 描述：个体业务执行机器人配置1
 * @author Rider Chen
 * @created 2021年1月13日 下午4:04:46
 */
public class RobotTwoToGt extends RobotNum {
    /**
     * 实例化对象参数
     */
    public RobotTwoToGt() {
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        rootName = StringUtil.getString(dictionaryService.get("robotConfig", "robotTwoToGtName").get("DIC_DESC"));
        jobName = StringUtil.getString(dictionaryService.get("robotConfig", "robotTwoToGtJobName").get("DIC_DESC"));
        jobUid = StringUtil.getString(dictionaryService.get("robotConfig", "robotTwoToGtJobUid").get("DIC_DESC"));
        queryStatusUrl = StringUtil.getString(dictionaryService.get("robotConfig", "queryStatusUrl").get("DIC_DESC"));
        updateUrl = StringUtil.getString(dictionaryService.get("robotConfig", "updateUrl").get("DIC_DESC"));
        triggerUrl = StringUtil.getString(dictionaryService.get("robotConfig", "triggerUrl").get("DIC_DESC"));
        paramType = RobotUtils.paramTypeObject;
    }
}
