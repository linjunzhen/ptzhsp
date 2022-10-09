/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述   群发移动短信实体类
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class MsgConfig {
    /**
     *
     */
    private String msgUrl;
    /**
     *
     */
    private String userAccount;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String ecName;
    /**
     *
     */
    private String msgSign;

    /**
     *
     */
    private MsgConfig(){

    }
    /**
     * 获取单例实例
     * @return
     */
    public static MsgConfig  getMsgConfig(){
        MsgConfig msgConfig=new MsgConfig();
        //获取短信字典发送配置
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        String msgUrl= StringUtil.getString(dictionaryService.get("msgSendConf",
                "msgUrl").get("DIC_DESC"));
        String userAccount= StringUtil.getString(dictionaryService.get("msgSendConf",
                "userAccount").get("DIC_DESC"));
        String password= StringUtil.getString(dictionaryService.get("msgSendConf",
                "password").get("DIC_DESC"));
        String ecName= StringUtil.getString(dictionaryService.get("msgSendConf",
                "ecName").get("DIC_DESC"));
        String msgSign= StringUtil.getString(dictionaryService.get("msgSendConf",
                "msgSign").get("DIC_DESC"));
        msgConfig.setEcName(ecName);
        msgConfig.setMsgSign(msgSign);
        msgConfig.setMsgUrl(msgUrl);
        msgConfig.setPassword(password);
        msgConfig.setUserAccount(userAccount);
        return msgConfig;
    }
    /**
     *
     */
    public String getMsgUrl() {
        return msgUrl;
    }
    /**
     *
     */
    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }
    /**
     *
     */
    public String getUserAccount() {
        return userAccount;
    }
    /**
     *
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    /**
     *
     */
    public String getPassword() {
        return password;
    }
    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     *
     */
    public String getEcName() {
        return ecName;
    }
    /**
     *
     */
    public void setEcName(String ecName) {
        this.ecName = ecName;
    }
    /**
     *
     */
    public String getMsgSign() {
        return msgSign;
    }
    /**
     *
     */
    public void setMsgSign(String msgSign) {
        this.msgSign = msgSign;
    }
}
