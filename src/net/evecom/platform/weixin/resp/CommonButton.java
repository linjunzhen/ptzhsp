/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.resp;

/**
 * 描述  普通按钮（子按钮）
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class CommonButton extends Button{
    /**
     * 类型
     */
    private String type;
    /**
     * 键值
     */
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
