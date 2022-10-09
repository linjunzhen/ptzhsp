/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 不动产抵押权登记操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface BdcDyqscdjService extends BaseService {

    /**
     * 
     * 描述 一审数据回填
     * 
     * @author Roger Li
     * @created 2019年12月26日 上午10:46:02
     * @param args
     */
    public void setRedrawData(Map<String, Map<String, Object>> args);
    
    
    /**
     * 
     * @Description 初始化业务数据
     * @author Luffy Cai
     * @date 2020年8月25日
     * @param returnMap
     * @param flowVars  void
     */
    public void initGenValue(Map<String, Object> returnMap,Map<String, Object> flowVars);

    /**
     * 描述:查找分户办件
     *
     * @author Madison You
     * @created 2020/8/26 14:40:00
     * @param
     * @return
     */
    List<Map<String, Object>> findBdcfhData(String bdcdyh);
    
    /**
     * 描述   设置抵押权首次登记转本业务数据
     * @author Allin Lin
     * @created 2020年9月24日 下午5:28:41
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    public  void setDyqscdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution);
    
    /** 
     * 描述    回填银行申请表-抵押人字段信息
     * @author Allin Lin
     * @created 2020年9月24日 下午5:49:38
     * @param returnMap
     * @param busInfo
     * @param executions
     */
    public void initYhDjdyrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution);
    
    
    /**
     * 
     * 描述    抵押权首次登记（以及转本登记）业务数据回填
     * @author Allin Lin
     * @created 2020年12月8日 上午10:47:55
     * @param args
     */
    public void setDyqscdjData(Map<String, Map<String, Object>> args);
    
    /**
     * 
     * 描述    （抵押权首次登记）银行申请表签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:43
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bankSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;
    
}
