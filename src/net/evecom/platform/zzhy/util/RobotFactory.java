/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.util;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 描述   机器（工厂模式）
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class RobotFactory {
    /**
     * 获取空闲的提交数据机器人、
     * @return
     */
    public static Robot getRobotOfSubmit() {
        RobotNum robotOne= new RobotOne();
        Robot robotExec1 =new RobotExec(robotOne);
        boolean flag = robotExec1.queryRobotStatus();
        if (flag) {
            return robotExec1;
        }
        RobotNum robotTwo = new RobotTwo();
        Robot robotExec2 =new RobotExec(robotTwo);
        flag = robotExec2.queryRobotStatus();
        if (flag) {
            return robotExec2;
        }
        return null;
    }
    /**
     * 获取空闲的名称查重数据机器人、
     * @return
     */
    public static Robot getRobotOfQuery() {
        RobotNum robotThree = new RobotThree();
        Robot robotExec1 =new RobotExec(robotThree);
        boolean flag = robotExec1.queryRobotStatus();
        if (flag) {
            return robotExec1;
        }
        return null;
    }
    
    /**
     * 获取空闲的名称查重数据机器人(个体)
     * @return
     */
    public static Robot getRobotOfQueryToGt() {
        RobotNum robotOneToGt = new RobotOneToGt();
        Robot robotExec1 =new RobotExec(robotOneToGt);
        boolean flag = robotExec1.queryRobotStatus();
        if (flag) {
            return robotExec1;
        }
        return null;
    }
    /**
     * 获取空闲的提交数据机器人（个体业务提交）
     * @return
     */
    public static Robot getRobotOfSubmitToGt() {
        List<Robot> list = new LinkedList<Robot>();
        // 推送的机器数量
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        int rotNum = Integer
                .parseInt(StringUtil.getString(dictionaryService.get("robotConfig", "robotNum").get("DIC_DESC")));
        for (int i = 1; i <= rotNum; i++) {
            // 构建业务提交机器
            RobotExec robotExec = RobotBuilder.getRobotBuilder().setRootName("robotToGtName" + i)
                    .setJobName("robotToGtJobName" + i).setJobUid("").build();
            if (Objects.nonNull(robotExec)) {
                list.add(robotExec);
                // return robotExec;
            }
        }
        // 随机返回机器人
        if (null != list && list.size() > 0) {
            Random r = new Random();
            int index = r.nextInt(list.size());
            return list.get(index);
        }
        return null;
    }
}
