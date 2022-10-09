/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 用户绑定service
 * @author Sundy Sun
 * @version v1.0
 */
public interface UserBindService extends BaseService{

    /**
     * 获取到我的用户数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findUserBySql(String whereSql);
    
    /**
     * 获取到我的绑定用户数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBindBySql(String whereSql);

    /**
     * 描述:判断MAP集合中参数是否为空
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/5/26 14:47:00
     */
    public boolean isParamsEmpty(Map<String, Object> map);

    /**
     * 描述:查询个人用户账号信息
     *
     * @author Madison You
     * @created 2020/5/26 15:04:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findGryhUserInfo(Map<String,Object> variables);


    /**
     * 描述:查询法人用户账号信息
     *
     * @author Madison You
     * @created 2020/5/26 15:19:00
     * @param
     * @return
     */
    List<Map<String, Object>> findFryhUserInfo(Map<String, Object> variables);
}
