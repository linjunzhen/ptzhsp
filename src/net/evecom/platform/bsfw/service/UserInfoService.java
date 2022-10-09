/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 前台用户管理操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface UserInfoService extends BaseService {
    /**
     * 
     * 描述 获取前台用户列表
     * 
     * @author Faker Li
     * @created 2015年11月20日 下午3:02:40
     * @param filter
     * @return
     */
    List<Map<String, Object>> findBySqlFilter(SqlFilter filter);

    /**
     * 
     * 描述 根据帐号密码判断是否存在记录
     * 1:说明激活状态用户存在 -2:用户名或者密码错误 -1:用户被删除 0 锁定
     * 
     * @author Rider Chen
     * @created 2015-11-20 下午03:12:01
     * @param username
     * @param password
     * @return
     */
    public int isExistsUser(String username, String password);
    /**
     * 
     * 描述 更新状态
     * @author Faker Li
     * @created 2015年11月20日 下午5:26:06
     * @param selectColNames
     * @param parseInt
     */
    void updateYHZT(String selectColNames, int parseInt);
    /**
     * 
     * 描述 根据帐号获取用户列表数据
     * @author Flex Hu
     * @created 2015年12月14日 下午4:22:29
     * @param accounts
     * @return
     */
    public List<Map<String,Object>> findByAccounts(String accounts);
    /**
     * 
     * 描述 更新默认收货地址
     * @author Kester Chen
     * @created 2018-6-26 下午5:09:59
     * @param string
     */
    void updateIsDefault(String string);
    /**
     * 
     * 描述  获取收货地址
     * @author Kester Chen
     * @created 2018-7-2 下午3:44:54
     * @param page
     * @param rows
     * @param string
     * @return
     */
    Map<String, Object> findfrontSjdzList(String page, String rows, String string);
    /**
     * 
     * 描述 根据id删除地址
     * @author Kester Chen
     * @created 2018-7-23 下午12:07:22
     * @param addId
     */
    public void deleteByAddrId(String[] addId);
    /**
     * 
     * 描述 根据id删除材料
     * @author Kester Chen
     * @created 2018-7-23 下午12:07:22
     * @param fileId
     */
    public void deleteByFileId(String[] fileId);
    /**
     * 
     * 描述 设置默认地址
     * @author Kester Chen
     * @created 2018-7-23 下午3:17:10
     * @param addId
     * @param userId
     */
    public void makeDefaultAddr(String addId, String userId);
    /**
     * 
     * 描述 获取我的收件地址
     * @author Kester Chen
     * @created 2018-7-30 上午11:01:22
     * @param filter
     * @return
     */
    List<Map<String, Object>> findAddrByUserId(SqlFilter filter);

    /**
     * 
     * 描述    更新闽政通登录用户至session
     * @author Danto Huang
     * @created 2020年5月19日 上午11:01:22
     * @param trustTicket
     */
    public void getMztUserInfoToSession(String trustTicket);
}
