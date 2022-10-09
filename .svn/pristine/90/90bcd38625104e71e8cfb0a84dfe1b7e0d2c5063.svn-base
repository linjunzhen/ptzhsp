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
import org.apache.commons.lang3.StringUtils;

/**
 * 描述   获取工商数据接口提交数据机器人实体类（建造者模式）
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class RobotBuilder {
    /**
     * 执行器
     */
    private RobotNum robotNum;
    /**
     * dictionaryService
     */
    private DictionaryService dictionaryService;
    /**
     * 构造私有化
     */
    private RobotBuilder(){
        robotNum= new RobotNum();
        dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        robotNum.queryStatusUrl = StringUtil.getString(dictionaryService.get("robotConfig", "queryStatusUrl").get("DIC_DESC"));
        robotNum.updateUrl = StringUtil.getString(dictionaryService.get("robotConfig", "updateUrl").get("DIC_DESC"));
        robotNum.triggerUrl = StringUtil.getString(dictionaryService.get("robotConfig", "triggerUrl").get("DIC_DESC"));
        robotNum.paramType = RobotUtils.paramTypeObject;
    }
    /**
     * 获取建造者对象
     * @return
     */
    public static RobotBuilder getRobotBuilder(){
        return new RobotBuilder();
    }

    /**
     * 设置机器人姓名
     * @param rootName
     * @return
     */
    public RobotBuilder setRootName(String rootName){
        robotNum.rootName = StringUtil.getString(dictionaryService.get("robotConfig", rootName).get("DIC_DESC"));
        return this;
    }
    /**
     * 设置机器人任务名
     * @param jobName
     * @return
     */
    public RobotBuilder setJobName(String jobName){
        robotNum.jobName = StringUtil.getString(dictionaryService.get("robotConfig", jobName).get("DIC_DESC"));
        return this;
    }
    /**
     * 设置机器人任务id
     * @param jobUid
     * @return
     */
    public RobotBuilder setJobUid(String jobUid){
        if(StringUtils.isNotEmpty(jobUid)){
            robotNum.jobUid = StringUtil.getString(dictionaryService.get("robotConfig", jobUid).get("DIC_DESC"));
        }
        return this;
    }

    /**
     * 创建机器执行器
     * @return
     */
    public RobotExec build(){
        RobotExec robotExec =new RobotExec(robotNum);
        boolean flag = robotExec.queryRobotStatus();
        if (flag) {
            return robotExec;
        }else{
            return null;
        }
    }
}
