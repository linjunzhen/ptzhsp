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
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述  秒批个体短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class MpIndividualExeContent implements ExeContent {
    /**
     *
     * @param exeId
     */
    @Override
    public void sendSuccessMsg(String exeId) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        //获取短信模板
        String noticeToSign= StringUtil.getString(dictionaryService.get("msgContent",
                "gtmpbjdx").get("DIC_DESC"));
        saveMsg(noticeToSign,exeId);
        //保存日志
        RobotUtils.saveLogByExeId(exeId,ExeDataService.EXE_SUCCESS);
    }

    /**
     *
     * @param exeId
     */
    @Override
    public void sendBackMsg(String exeId) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        //获取短信模板
        String noticeToSign= StringUtil.getString(dictionaryService.get("msgContent",
                "gtmpthbjdx").get("DIC_DESC"));
        saveMsg(noticeToSign,exeId);
        //保存日志
        RobotUtils.saveLogByExeId(exeId,ExeDataService.EXE_BACK);
    }

    /**
     * 因为网络问题，先保存，再用定时器发送数据出去
     */
    private void saveMsg(String msgContent,String exeId){
        //获取业务数据
        ExeDataService exeDataService= (ExeDataService) AppUtil.getBean("exeDataService");
        Map<String,Object> busMap=exeDataService.getExeAndBuscordMap(exeId);

        busMap.put("JBR_MOBILE", busMap.get("JBR_MOBILE"));
        String INDIVIDUAL_NAME=StringUtil.getString(busMap.get("INDIVIDUAL_NAME"));
        //经营者
        Map<String,Object> msgInfo1=new HashMap<>();
        String mobile1 = StringUtil.getString(busMap.get("DEALER_MOBILE"));
        String content = String.format(msgContent, INDIVIDUAL_NAME, exeId);
        msgInfo1.put("MSG_INFO",content);
        msgInfo1.put("RECEIVER_MOB",mobile1);
        msgInfo1.put("SEND_STATUS",0);
        exeDataService.saveOrUpdate(msgInfo1,"T_MESSAGE_INFO","");
        //经办人
        String mobile2 = StringUtil.getString(busMap.get("JBR_MOBILE"));
        if(StringUtils.isNotEmpty(mobile2) && !mobile1.equals(mobile2)){//与经营者不同时发送短信
            Map<String,Object> msgInfo2=new HashMap<>();
            msgInfo2.put("MSG_INFO",content);
            msgInfo2.put("RECEIVER_MOB",mobile2);
            msgInfo2.put("SEND_STATUS",0);
            exeDataService.saveOrUpdate(msgInfo2,"T_MESSAGE_INFO","");
        }
    }
}
