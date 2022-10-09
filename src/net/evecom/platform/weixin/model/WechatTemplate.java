/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.util.Map;

/**
 * 微信公众号消息模板
 * 描述
 * @author Laura Song
 */
public class WechatTemplate {
    
    /**
     * 接收者openid
     */
    private String touser;
    
    /**
     * 模板ID
     */
    private String template_id;
    
    /**
     * 模板跳转链接
     */
    private String url;
    
    /**
     * 模板数据
     */
    private Map<String, TemplateData> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return template_id;
    }

    public void setTemplateId(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, TemplateData> getData() {
        return data;
    }

    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }
}

