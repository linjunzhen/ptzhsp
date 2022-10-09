/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.sign;


import com.alibaba.fastjson.JSON;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.util.MsgSendUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 描述  内资合伙公司面签短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class NzJointventureSignContent implements SignContent {
    /**
     * 合伙发送面签信息
     * @param flowVars
     */
    @Override
    public void sendSignMsg(Map<String,Object> flowVars) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));
        String companyName= StringUtil.getString(flowVars.get("COMPANY_NAME"));
        String noticeToSign= StringUtil.getString(dictionaryService.get("msgContent",
                "nzJointventureSignMsg").get("DIC_DESC"));
        //经办人
        String operatorMobile = StringUtil.getString(flowVars.get("SQRSJH"));
        String signExeId = exeId.substring(8);
        String content = String.format(noticeToSign, companyName, exeId, signExeId);
        MsgSendUtils.sendMsg(operatorMobile, content);
        //执行事务合伙人
        Map<String,Object> partShip=getPartShip(flowVars);
        if(Objects.nonNull(partShip)){
            String shareholderType=StringUtil.getString(partShip.get("SHAREHOLDER_TYPE"));
            String legalMobile="";
            if(Objects.equals(shareholderType,"zrr")){
                legalMobile=StringUtil.getString(partShip.get("CONTACT_WAY"));
            }else{
                legalMobile=StringUtil.getString(partShip.get("LEGAL_MOBILE_PERSON"));
            }
            MsgSendUtils.sendMsg(legalMobile, content);
        }
    }

    /**
     * 获取执行事务合伙人信息
     * @param flowVars
     * @return
     */
    public static Map<String,Object> getPartShip(Map<String,Object> flowVars){
        String shareHolderJson=StringUtil.getString(flowVars.get("HOLDER_JSON"));
        Map<String,Object>  partShip=null;
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse(shareHolderJson);
        if(CollectionUtils.isNotEmpty(list)){
            for(Map<String,Object> map:list){
                String isPartnership=StringUtil.getString(map.get("IS_PARTNERSHIP"));
                if(Objects.equals("1",isPartnership)){
                    return map;
                }
            }
        }
       return partShip;
    }
}
