/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述 写信求诉操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface LetterService extends BaseService {

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);

    /**
     * 
     * 描述 获取办事指南咨询列表
     * @author Sundy Sun
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findfrontList(String page, String rows, String code,String title);
    /**
     * 
     * 描述 获取信件编号
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午9:46:31
     * @param letterVars
     * @return
     */
    public String getNextLetterCode(Map<String, Object> letterVars);

    /**
     * 获取诉求件数量
     * @return
     */
    public int getAllSqNum(String replyFlag);
}
