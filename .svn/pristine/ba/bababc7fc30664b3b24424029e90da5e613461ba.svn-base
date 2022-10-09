/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-22 上午8:50:01
 */
public interface OnlineApplyService extends BaseService {

    /**
     * 
     * 验证网格用户
     * @author Danto Huang
     * @created 2017-5-22 上午11:07:15
     * @param userCode
     * @param userName
     * @param userCard
     * @param userMobile
     * @return
     */
    public String checkGridUser(HttpServletRequest request);
    /**
     * 
     *  获取前台我的办件列表
     * @author Danto Huang
     * @created 2017-5-26 上午8:48:25
     * @param page
     * @param rows
     * @param yhzh
     * @param type
     * @return
     */
    public List<Map<String, Object>> findfrontWdbjList(String page, String rows,String yhzh,String type) ;
}
