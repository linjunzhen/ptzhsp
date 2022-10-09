/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

import java.util.Date;
import java.util.List;

/**
 * 描述   不动产全流程签章流程启动实体类
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class SignFlowBody {
    /**
     *是否允许签署人补充签署 材料0否1是 默认：0
     */
    private int allowAddAttachment=0;
    /**
     * 第三方业务码，具有唯一性
     */
    private String bizNo;
    /**
     * 签署回调通知地址
     */
    private String callbackUrl="";
    /**
     * 流程备注
     */
    private String comments;
    /**
     * 内部发起人id
     */
    private String initiatorAccountId="";
    /**
     *发起人部门编号
     */
    private String initiatorOrganizeNo="";
    /**
     * 发起人所属部门id
     */
    private String initiatorUniqueId="";
    /**
     * 重定向地址
     */
    private String redirectUrl="https://www.tsign.cn";
    /**
     *用户进行签署操作的截止日期，默认是2天以内，格式yyyy-MM-dd HH:mm:ss
     */
    private String signValidity="";
    /**
     * 流程主题,必填
     */
    private String subject;

    /**
     *
     * @return
     */
    public String getYwId() {
        return ywId;
    }

    /**
     *
     * @param ywId
     */
    public void setYwId(String ywId) {
        this.ywId = ywId;
    }

    /**
     * 业务id(本地调用)
     */
    private String ywId;
    /**
     *签章流程文档实体类
     */
    private List<SignDoc> signDocs;
    /**
     *签章人，签章位置信息实体集合类
     */
    private List<Signer> signers;

    public SignFlowBody(){
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        initiatorAccountId= StringUtil.getString(dictionaryService.get("bdcSignConfig",
                "initiatorAccountId").get("DIC_DESC"));
        callbackUrl= StringUtil.getString(dictionaryService.get("bdcSignConfig",
                "callbackUrl").get("DIC_DESC"));
        String validDate= StringUtil.getString(dictionaryService.get("bdcSignConfig",
                "VALID_DATE").get("DIC_DESC"));
        signValidity= DateTimeUtil.getStrOfDate(
                DateTimeUtil.getNextDay(new Date(),Integer.parseInt(validDate)),"yyyy-MM-dd HH:mm:ss");
    }

    public int getAllowAddAttachment() {
        return allowAddAttachment;
    }

    public void setAllowAddAttachment(int allowAddAttachment) {
        this.allowAddAttachment = allowAddAttachment;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getInitiatorAccountId() {
        return initiatorAccountId;
    }

    public void setInitiatorAccountId(String initiatorAccountId) {
        this.initiatorAccountId = initiatorAccountId;
    }

    public String getInitiatorOrganizeNo() {
        return initiatorOrganizeNo;
    }

    public void setInitiatorOrganizeNo(String initiatorOrganizeNo) {
        this.initiatorOrganizeNo = initiatorOrganizeNo;
    }

    public String getInitiatorUniqueId() {
        return initiatorUniqueId;
    }

    public void setInitiatorUniqueId(String initiatorUniqueId) {
        this.initiatorUniqueId = initiatorUniqueId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getSignValidity() {
        return signValidity;
    }

    public void setSignValidity(String signValidity) {
        this.signValidity = signValidity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<SignDoc> getSignDocs() {
        return signDocs;
    }

    public void setSignDocs(List<SignDoc> signDocs) {
        this.signDocs = signDocs;
    }

    public List<Signer> getSigners() {
        return signers;
    }

    public void setSigners(List<Signer> signers) {
        this.signers = signers;
    }
}
