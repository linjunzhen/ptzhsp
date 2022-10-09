/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.util;

/**
 * 描述   微信通用接口凭证
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class AccessToken {

    /** 获取到的凭证**/
    private String token;
    /** 凭证有效时间，单位：秒 **/
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
