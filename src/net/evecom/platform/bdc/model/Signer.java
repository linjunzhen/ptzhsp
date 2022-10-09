/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.model;

import net.evecom.platform.bdc.util.BdcQlcSignUtil;

import java.util.List;

/**
 * 描述   不动产全流程签章人，签章位置信息实体类
 *
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class Signer {
    /**
     * 签章人id
     */
    private String accountId;
    /**
     * 用户类型;1内部，2外部
     */
    private int accountType=2;
    /**
     *企业id/部门id;个人签署时不需要
     * 传值
     */
    private String authorizationOrganizeId;
    /**
     * 企业编号/部门编号;个人签署时不需要传值，企业签署时 authorizationOrganizeId和
     * authorizationOrganizeNo必填其一
     */
    private String authorizationOrganizeNo;
    /**
     * 是否静默签
     */
    private boolean autoSign=false;
    /**
     * 联系电话
     */
    private String contactMobile;
    /**
     * 是否可以代法人签 署；1：是，0：否；默认为0
     */
    private int legalSignFlag=0;
    /**
     * 签署序号
     */
    private int  signOrder=1;
    /**
     * 签章文档详细实体集合类
     */
    private List<SignDocDetail> signDocDetails;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAuthorizationOrganizeId() {
        return authorizationOrganizeId;
    }

    public void setAuthorizationOrganizeId(String authorizationOrganizeId) {
        this.authorizationOrganizeId = authorizationOrganizeId;
    }

    public String getAuthorizationOrganizeNo() {
        return authorizationOrganizeNo;
    }

    public void setAuthorizationOrganizeNo(String authorizationOrganizeNo) {
        this.authorizationOrganizeNo = authorizationOrganizeNo;
    }

    public boolean isAutoSign() {
        return autoSign;
    }

    public void setAutoSign(boolean autoSign) {
        this.autoSign = autoSign;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public int getLegalSignFlag() {
        return legalSignFlag;
    }

    public void setLegalSignFlag(int legalSignFlag) {
        this.legalSignFlag = legalSignFlag;
    }

    public int getSignOrder() {
        return signOrder;
    }

    public void setSignOrder(int signOrder) {
        this.signOrder = signOrder;
    }

    public List<SignDocDetail> getSignDocDetails() {
        return signDocDetails;
    }

    public void setSignDocDetails(List<SignDocDetail> signDocDetails) {
        this.signDocDetails = signDocDetails;
    }
}
