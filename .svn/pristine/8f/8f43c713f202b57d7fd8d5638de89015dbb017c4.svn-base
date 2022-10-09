/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.data;


import net.evecom.platform.zzhy.model.*;

/**
 * 描述   合伙数据实体类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class HhData extends SubmitDataTable  {
    /**
     *
     */
    public static String  tableName="T_COMMERCIAL_NZ_JOINTVENTURE";
    /**
     *
     */
    public static String  busRecordColName="COMPANY_ID";
    /**
     *
     * @param curStepName
     */
    public HhData(String curStepName){
        setTableName(HhData.tableName);
        setBusRecordColName(busRecordColName);
        setCurStepName(curStepName);
    }

    /**
     * 获取合伙机器人
     * @return
     */
    @Override
    public Robot getRobotOfSubmit() {
        RobotNum robotOne= new RobotOneOfHh();
        Robot robotExec1 =new RobotExec(robotOne);
        boolean flag = robotExec1.queryRobotStatus();
        if (flag) {
            return robotExec1;
        }
        RobotNum robotTwo = new RobotTwoOfHh();
        Robot robotExec2 =new RobotExec(robotTwo);
        flag = robotExec2.queryRobotStatus();
        if (flag) {
            return robotExec2;
        }
        return null;
    }
}
