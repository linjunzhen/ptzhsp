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

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-2-28 下午4:39:15
 */
public interface SearchService extends BaseService {
    
    /**
     * 
     * 根据父编码获取服务类别列表
     * @author Danto Huang
     * @created 2017-6-13 下午6:59:14
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> getItemTypeList(String typeCode);

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-2-28 下午4:53:21
     * @return
     */
    public List<Map<String,Object>> getNameSearchList(SqlFilter filter);

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-2-28 下午4:53:21
     * @return
     */
    public List<Map<String,Object>> getTypeSearchList(SqlFilter filter);

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-2-28 下午4:53:21
     * @return
     */
    public List<Map<String,Object>> getTopItemList(String page, String rows);
    /**
     * 
     * 个人喜好数据列表
     * @author Danto Huang
     * @created 2017-5-17 上午10:24:28
     * @param page
     * @param rows
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getPersonalPrefer(String page, String rows, String userId);
    /**
     * 
     * 办过类似服务
     * @author Danto Huang
     * @created 2017-5-24 上午11:14:08
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getAlsoDoneItme(SqlFilter filter);
    /**
     * 
     * 我的足迹
     * @author Danto Huang
     * @created 2017年7月3日 下午4:31:06
     * @param page
     * @param rows
     * @param yhzh
     * @return
     */
    public List<Map<String,Object>> getMyTrack(String page, String rows,String yhzh);
    /**
     * 
     * 描述    根据事项编码获取办事指南信息
     * @author Danto Huang
     * @created 2018年9月7日 上午9:56:26
     * @param itemCode
     * @return
     */
    public Map<String,Object> findGuideByItemCode(String itemCode);    

    /**
     * 
     * 描述    获取流程设置数据
     * @author Danto Huang
     * @created 2018年9月7日 下午2:47:28
     * @param itemId
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findBllc(String itemId,String defId);
}
