/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface WHomeService extends BaseService{
    
    /**
     * 
     * 描述 接受微信方发来的请求
     * @param request
     * @return
     * @see net.evecom.wxsystem.service.WechatService#getPost(javax.servlet.http.HttpServletRequest)
     */
    public String getWechatPost(HttpServletRequest request,Map<String, String> requestMap);
    /**
     * 设置响应文本消息
     * 方法：@return
     */
    public String getNoBindTextResp(String toUser);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 17:33:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getWxBookDepartList();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 17:53:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getWxBookBusinessChooseList(HttpServletRequest request);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 20:39:00
     * @param 
     * @return 
     */
    public List<Map<String, Object>> getWxBookTimeList(HttpServletRequest request);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/31 14:17:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getWxBookAppointList(Map<String,Object> loginMember);

}
