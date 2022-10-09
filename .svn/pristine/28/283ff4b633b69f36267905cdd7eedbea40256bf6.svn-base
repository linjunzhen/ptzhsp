/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.exesuccess;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.sign.SignContent;
import net.evecom.platform.zzhy.util.MsgSendUtils;
import net.evecom.platform.zzhy.util.RobotUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述  秒批有限公司短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class MpCompanyExeContent implements ExeContent {
    /**
     *成功短信
     * @param exeId
     */
    @Override
    public void sendSuccessMsg(String exeId) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        //获取短信模板
        String msgContent= StringUtil.getString(dictionaryService.get("msgContent",
                "exeSuccessMsg").get("DIC_DESC"));
        saveMsg(msgContent,exeId,ExeDataService.EXE_SUCCESS);
        //保存日志
        RobotUtils.saveLogByExeId(exeId,ExeDataService.EXE_SUCCESS);
    }
    /**
     *退回短信
     * @param exeId
     */
    @Override
    public void sendBackMsg(String exeId) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        //获取短信模板
        String msgContent= StringUtil.getString(dictionaryService.get("msgContent",
                "exeBackMsg").get("DIC_DESC"));
        saveMsg(msgContent,exeId,ExeDataService.EXE_BACK);
        //保存日志
        RobotUtils.saveLogByExeId(exeId,ExeDataService.EXE_BACK);
    }
    /**
     * 因为网络问题，先保存，再用定时器发送数据出去
     */
    private void saveMsg(String msgContent,String exeId,String stepName){
        //获取业务数据
        ExeDataService exeDataService= (ExeDataService) AppUtil.getBean("exeDataService");
        Map<String,Object> busMap=exeDataService.getExeAndBuscordMap(exeId);

        String companyName=StringUtil.getString(busMap.get("COMPANY_NAME"));
        //经办人
        Map<String,Object> msgInfo1=new HashMap<>();
        String operatorMobile = StringUtil.getString(busMap.get("OPERATOR_MOBILE"));
        String content = String.format(msgContent, companyName, exeId);
        msgInfo1.put("MSG_INFO",content);
        msgInfo1.put("RECEIVER_MOB",operatorMobile);
        msgInfo1.put("SEND_STATUS",0);
        exeDataService.saveOrUpdate(msgInfo1,"T_MESSAGE_INFO","");
        //法人
        String legalMobile = StringUtil.getString(busMap.get("LEGAL_MOBILE"));
        Map<String,Object> msgInfo2=new HashMap<>();
        msgInfo2.put("MSG_INFO",content);
        msgInfo2.put("RECEIVER_MOB",legalMobile);
        msgInfo2.put("SEND_STATUS",0);
        exeDataService.saveOrUpdate(msgInfo2,"T_MESSAGE_INFO","");
        if (Objects.equals(stepName, ExeDataService.EXE_BACK)) {
            //宋小琴
            Map<String, Object> msgInfo3 = new HashMap<>();
            msgInfo3.put("MSG_INFO", content);
            msgInfo3.put("RECEIVER_MOB", "13696902129");
            msgInfo3.put("SEND_STATUS", 0);
            exeDataService.saveOrUpdate(msgInfo3, "T_MESSAGE_INFO", "");
        }
    }
}
