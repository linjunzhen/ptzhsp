/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 微信公众号用户service
 * @author Laura Song
 */
public interface WxUserService extends BaseService{

    /**
     * 根据openid 获取用户信息
     * @author Laura Song
     * @param openid
     * @return
     */
    public Map<String, Object> findUserByOpenId(String openid);
    
}
