/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述  http短信发送实体类
 * @author Water Guo
 * @version 1.0
 * @created 2020年10月12日 下午5:10:02
 */
public class SendReq {
    /**
     * 集团客户名称
     */
    private String ecName;
    /**
     * 用户名
     */
    private String apId;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 手机号码逗号分隔
     */
    private String mobiles;
    /**
     * 发送短信内容
     */
    private String content;
    /**
     * 网关签名编码，必填，签名编码在中国移动集团开通帐号后分配
     * ，可以在云MAS网页端管理子系统-SMS接口管理功能中下载。
     */
    private String sign;
    /**
     * 扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")
     * ，否则添加移动公司允许的扩展码。
     */
    private String addSerial;
    /**
     * 校验码
     */
    private String mac;

    /**
     *
     * @return
     */
    public String getEcName() {
        return ecName;
    }
    /**
     *
     * @return
     */
    public void setEcName(String ecName) {
        this.ecName = ecName;
    }
    /**
     *
     * @return
     */
    public String getApId() {
        return apId;
    }
    /**
     *
     * @return
     */
    public void setApId(String apId) {
        this.apId = apId;
    }
    /**
     *
     * @return
     */
    public String getSecretKey() {
        return secretKey;
    }
    /**
     *
     * @return
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    /**
     *
     * @return
     */
    public String getMobiles() {
        return mobiles;
    }
    /**
     *
     * @return
     */
    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }
    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }
    /**
     *
     * @return
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     *
     * @return
     */
    public String getSign() {
        return sign;
    }
    /**
     *
     * @return
     */
    public void setSign(String sign) {
        this.sign = sign;
    }
    /**
     *
     * @return
     */
    public String getAddSerial() {
        return addSerial;
    }
    /**
     *
     * @return
     */
    public void setAddSerial(String addSerial) {
        this.addSerial = addSerial;
    }
    /**
     *
     * @return
     */
    public String getMac() {
        return mac;
    }
    /**
     *
     * @return
     */
    public void setMac(String mac) {
        this.mac = mac;
    }
}
