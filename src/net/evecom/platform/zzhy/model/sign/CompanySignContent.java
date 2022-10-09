/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.sign;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.Robot;
import net.evecom.platform.zzhy.util.MsgSendUtils;

import java.util.Map;

/**
 * 描述  有限公司面签短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class CompanySignContent implements SignContent {
    /**
     * 有限公司发送面签信息
     * @param flowVars
     */
    @Override
    public void sendSignMsg(Map<String,Object> flowVars) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));
        String companyName= StringUtil.getString(flowVars.get("COMPANY_NAME"));
        String noticeToSign= StringUtil.getString(dictionaryService.get("msgContent",
                "noticeToSign").get("DIC_DESC"));
        //经办人
        String operatorMobile = StringUtil.getString(flowVars.get("OPERATOR_MOBILE"));
        String signExeId = exeId.substring(8);
        String content = String.format(noticeToSign, companyName, exeId, signExeId);
        MsgSendUtils.sendMsg(operatorMobile, content);
        //法人
        String legalMobile = StringUtil.getString(flowVars.get("LEGAL_MOBILE"));
        MsgSendUtils.sendMsg(legalMobile, content);
    }
}
